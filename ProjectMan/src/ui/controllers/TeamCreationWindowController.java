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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import projectman.SelfAwareController;
import backend.datatypes.Team;

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
    TableView<Employee> employeeCatalog;
    @FXML
 //   TableColumn<Employee, String>  NameColumn, LastNameColumn, PositionColumn;
    TableColumn<Employee, String>  NameColumn, LastNameColumn, PositionColumn, teamWorkHoursColumn;
    @FXML
    TableColumn<Employee, Double> HourlyRateColumn, WorkHoursColumn, IDColumn;
    @FXML
    TextField teamNameTBox;
    
    List<Employee> allEmployees;
    List<Employee> selectedEmployees = new ArrayList();
    Team newTeam;
    
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
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        PositionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        HourlyRateColumn.setCellValueFactory(new PropertyValueFactory<>("hourlyRate"));
        WorkHoursColumn.setCellValueFactory(new PropertyValueFactory<>("dailyHours"));
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        teamWorkHoursColumn.setCellValueFactory(new PropertyValueFactory<>("hoursOnThisTeam"));
    }    
        
    @FXML
    public void AddToTeam(ActionEvent e)
    {
        selectedEmployees.add(employeeCatalog.getSelectionModel().getSelectedItem());
        employeeCatalog.getItems().remove(employeeCatalog.getSelectionModel().getSelectedItem());
    }
        
    
    @FXML
    public void CloseTheProgram()
    {
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
        for(Employee emp : selectedEmployees)
        {
        // [Tomas] nežinau kieno kodas ir ar jo reik
        //    emp.addpersonalTeams(newTeam);
        }
        ((Stage)teamNameTBox.getScene().getWindow()).close();
    }
    
    @FXML
    public Team getTeam()
    {
        return newTeam;
    }
    
    public void setAllEmployees(List<Employee> allEmployees)
    {
        this.allEmployees = allEmployees;
        ObservableList<Employee> tableInfo = FXCollections.observableArrayList();
        tableInfo.addAll(allEmployees);
        employeeCatalog.setItems(tableInfo);
        employeeCatalog.refresh();
    }
}
