/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import backend.datatypes.Employee;
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
import projectman.SelfAwareController;
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
    TableColumn<Employee, String>  NameColumn, LastNameColumn, PositionColumn, teamWorkHoursColumn, teamPersonelColumn;

    @FXML
    TableColumn<Employee, Double> HourlyRateColumn, WorkHoursColumn, IDColumn;
    @FXML
    TextField teamNameTBox;

    private List<Employee> allEmployees = new ArrayList();      //Galima perkelti i kita klase, arba jei jau toks listas yra atvesti patha i sita, bet nebutina
    
    private List<Employee> selectedEmployees = new ArrayList(); //Neliesti, vidinis listas
    private List<Employee> changedCellEmployees = new ArrayList();
    private Team newTeam;
    
    public TeamCreationWindowController(List<Employee> allEmployees)
    {
        this.allEmployees = allEmployees;
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
        allEmployees = DataStatic.getEmployees();
        ObservableList<Employee> tableInfo = FXCollections.observableArrayList();
        tableInfo.addAll(allEmployees);
        employeeCatalog.setItems(tableInfo);
        employeeCatalog.refresh();
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        PositionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        HourlyRateColumn.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));
        WorkHoursColumn.setCellValueFactory(new PropertyValueFactory<>("dailyHours"));
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        teamWorkHoursColumn.setCellValueFactory(new PropertyValueFactory<>("HOnThisTeam"));
        
        teamWorkHoursColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        teamPersonelTW.setOnMouseClicked(this::delTeamPersonel);
        
        teamPersonelColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
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
    public void delTeamPersonel(MouseEvent e)         //Metodas atsakingas uz zmoniu pasalinima is grupes kurimo metu.
    {
        System.out.println("Event target::: " + e.getTarget());
        Employee node = teamPersonelTW.getSelectionModel().getSelectedItem();
        if(node == null)
            return;
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
    }
    
    @FXML
    public void onTeamCreateAttempt()
    {
        if(!teamNameTBox.getText().isEmpty()){
            newTeam = new Team(teamNameTBox.getText(), selectedEmployees);
        }
        else return;
        
        stage.close();
        for(Employee emp : selectedEmployees) //For each visiem employee, kurie yra teame, priskiriamas team pavadinimas ir valandos. (Toliau: Employee klaseje)
        {
        // [Tomas] nežinau kieno kodas ir ar jo reik
            emp.addpersonalTeams(newTeam);
        }
        for(Employee emp : changedCellEmployees)
        {
            emp.setHOnthisTeam("");
        }
        ((Stage)teamNameTBox.getScene().getWindow()).close();
    }
    
    @FXML
    public Team getTeam()
    {
        return newTeam;
    }
}
