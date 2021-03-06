/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import backend.datatypes.Employee;
import backend.datatypes.MutablePair;
import backend.datatypes.Project;
import backend.datatypes.Team;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import backend.datatypes.Team;
import backend.server.DataStatic;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author manfr
 */
public class TeamCreationWindowController implements Initializable, SelfAwareController
{

    @FXML
    TableView<Employee> employeeCatalog, teamPersonelTW;
    @FXML
    TableColumn<Employee, String>  NameColumn, LastNameColumn, PositionColumn, teamWorkHoursColumn, teamPersonelColumn, assignedHoursColumn;

    @FXML
    TableColumn<Employee, Double> HourlyRateColumn, WorkHoursColumn, IDColumn;
    @FXML
    TextField teamNameTBox, projectTextBox;

    private List<Employee> allEmployees = new ArrayList();      //Galima perkelti i kita klase, arba jei jau toks listas yra atvesti patha i sita, bet nebutina
    
    private List<Employee> selectedEmployees = new ArrayList(); //Neliesti, vidinis listas
    private List<Employee> changedCellEmployees = new ArrayList();
    private Team newTeam;
    private Team TeamToEdit;
    private boolean edit = false;
    
    public TeamCreationWindowController(List<Employee> allEmployees)
    {
        this.allEmployees = allEmployees;
        edit = false;
    }
    
    public TeamCreationWindowController(List<Employee> allEmployees, boolean edit, Team TeamToEdit)
    {
        this.allEmployees = allEmployees;
        this.edit = edit;
        this.TeamToEdit = TeamToEdit;
        
    }
    
    private Stage stage;
    private Scene scene;
    private Window window;
    @Override
    public void whoAmI(Stage stage, Scene scene, Window window)
    {
        this.stage = stage;
        this.scene = scene;
        this.window = window;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        PositionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        HourlyRateColumn.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));
        WorkHoursColumn.setCellValueFactory(new PropertyValueFactory<>("dailyHours"));
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        teamWorkHoursColumn.setCellValueFactory(new PropertyValueFactory<>("HOnThisTeam"));
        assignedHoursColumn.setCellValueFactory(new PropertyValueFactory<>("HOnThisTeam"));
        
        teamWorkHoursColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        assignedHoursColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        teamPersonelTW.setOnMouseClicked(this::delTeamPersonel);
        
        teamPersonelColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        allEmployees = DataStatic.getEmployees();
        
        ObservableList<Employee> tableInfo = FXCollections.observableArrayList();
        ObservableList<Employee> listOfEmployees = FXCollections.observableArrayList();
        tableInfo.addAll(allEmployees);
        if(edit == true)
        {      
            if(TeamToEdit.getTeamName() != null)
                teamNameTBox.setText(TeamToEdit.getTeamName());
            if(TeamToEdit.getProject().getProjectName() != null)
                projectTextBox.setText(TeamToEdit.getProject().getProjectName());
            selectedEmployees = TeamToEdit.getEmployeeList();
            changedCellEmployees = TeamToEdit.getEmployeeList();
            listOfEmployees.addAll(selectedEmployees);
            teamPersonelTW.setItems(listOfEmployees);
            for(Employee emp : selectedEmployees)
            {  
                if(emp.getPersonalTeams().contains(TeamToEdit))
                    emp.setHOnthisTeam(emp.getworkHoursInTeams().get(emp.getPersonalTeams().lastIndexOf(TeamToEdit)).toString());
                tableInfo.remove(emp);
            }
        }
        
        employeeCatalog.setItems(tableInfo);
        employeeCatalog.refresh();
        
    }    
    
    @FXML
    public void changeHourEvent(CellEditEvent edittedCell)
    {
        Employee selected = employeeCatalog.getSelectionModel().getSelectedItem();
        if(selected == null)
            return;
        selected.setHOnthisTeam((String) edittedCell.getNewValue());
        employeeCatalog.refresh();
        changedCellEmployees.add(selected);
    }
    
    @FXML
    public void changeHourEvent2(CellEditEvent edittedCell)
    {
        Employee selected = teamPersonelTW.getSelectionModel().getSelectedItem();
        if(selected == null)
            return;
        selected.setHOnthisTeam((String) edittedCell.getNewValue());
        teamPersonelTW.refresh();
        changedCellEmployees.add(selected);
    }
    
    @FXML
    public void delTeamPersonel(MouseEvent e)         //Metodas atsakingas uz zmoniu pasalinima is grupes kurimo metu.
    {
        System.out.println("Event target::: " + e.getTarget().toString());
        Employee node = teamPersonelTW.getSelectionModel().getSelectedItem();
        if(node == null)
            return;
        if(edit)
        {
            if(node.getPersonalTeams().contains(TeamToEdit))
            {
                node.getworkHoursInTeams().remove(node.getPersonalTeams().indexOf(TeamToEdit));
                node.getPersonalTeams().remove(TeamToEdit);
            }
            TeamToEdit.removeEmployeeFromTeam(node);
            
            System.out.println("Block to test " + TeamToEdit.getEmployeeList());
        }
        teamPersonelTW.getItems().remove(node);
        selectedEmployees.remove(node);
        employeeCatalog.getItems().add(node);
    }
        
    @FXML
    public void AddToTeam(ActionEvent e)         //Metodas skirtas prideti personeli i teama kurimo metu. Neliesti, nebent zinot ka darot ;D
    {
        System.out.println("Debug.SelectedIteam:: " + employeeCatalog.getSelectionModel().getSelectedItem());
        if(employeeCatalog.getSelectionModel().getSelectedItem() == null)
            return;
        selectedEmployees.add(employeeCatalog.getSelectionModel().getSelectedItem());
        employeeCatalog.getItems().remove(employeeCatalog.getSelectionModel().getSelectedItem());
        ObservableList<Employee> listOfEmployees = FXCollections.observableArrayList();
        listOfEmployees.addAll(selectedEmployees);
        teamPersonelTW.setItems(listOfEmployees);
        teamPersonelTW.refresh();
    }
        
    
    @FXML
    public void CloseTheProgram()
    {
        for(Employee emp : changedCellEmployees)
        {
            emp.setHOnthisTeam("");
        }
        ((Stage)teamNameTBox.getScene().getWindow()).close();
        edit = false;
    }
    
    @FXML
    public void onTeamCreateAttempt()
    {
        if(!teamNameTBox.getText().isEmpty() & !projectTextBox.getText().isEmpty() & edit == false)
        {
            newTeam = new Team(teamNameTBox.getText(), selectedEmployees, new Project(projectTextBox.getText()));
        }
        else if(!teamNameTBox.getText().isEmpty() & !projectTextBox.getText().isEmpty())
        {
            TeamToEdit.setTeamName(teamNameTBox.getText());
            TeamToEdit.setProject(new Project(projectTextBox.getText()));
            for(Employee empl : selectedEmployees)
            {
                if(!TeamToEdit.getEmployeeList().contains(empl))
                {
                    try{
                        TeamToEdit.add(new MutablePair<Employee, Double>(empl, Double.parseDouble(empl.getHOnThisTeam())));
                    }
                    catch(Exception e)
                    {
                        TeamToEdit.add(new MutablePair<Employee, Double>(empl, 0d));
                    }
                    
                }
            }
        }
        else return;
        
        stage.close();
        for(Employee emp : selectedEmployees) //For each visiem employee, kurie yra teame, priskiriamas team pavadinimas ir valandos. (Toliau: Employee klaseje)
        {
            emp.addpersonalTeams(newTeam);
        }
        for(Employee emp : changedCellEmployees)
        {
            emp.setHOnthisTeam("");
        }
        ((Stage)teamNameTBox.getScene().getWindow()).close();
        edit = false;
    }
    
    @FXML
    public Team getTeam()
    {
        return newTeam;
    }
}
