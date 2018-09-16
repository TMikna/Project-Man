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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author manfr
 */
public class TeamCreationWindowController implements Initializable {

    @FXML
    TableView<Employee> employeeCatalog;
    @FXML
    TableColumn<Employee, String>  NameColumn, LastNameColumn, PositionColumn, teamWorkHoursColumn;
    @FXML
    TableColumn<Employee, Double> HourlyRateColumn, WorkHoursColumn, IDColumn;
    @FXML
    TextField teamNameTBox;
    
    List<Employee> allEmployees = new ArrayList();
    List<Employee> selectedEmployees = new ArrayList();
    Team newTeam;
    
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
    public void CloseTheProgram(ActionEvent e)
    {
        ((Stage)teamNameTBox.getScene().getWindow()).close();
    }
    
    @FXML
    public void onTeamCreateAttempt(ActionEvent e)
    {
        if(!teamNameTBox.getText().isEmpty()){
            newTeam = new Team(selectedEmployees, teamNameTBox.getText());
        }
        else return;
        for(Employee emp : selectedEmployees)
        {
            emp.addpersonalTeams(newTeam);
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
