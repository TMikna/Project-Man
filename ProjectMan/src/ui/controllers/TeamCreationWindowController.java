/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import backend.datatypes.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author manfr
 */
public class TeamCreationWindowController implements Initializable, SelfAwareController {

    @FXML
    TableView<Employee> employeeCatalog;
    @FXML
    TableColumn<Employee, String>  NameColumn, LastNameColumn, PositionColumn;
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
        
        System.out.println("Hello");
        ObservableList<Employee> tableInfo = FXCollections.observableArrayList();
        tableInfo.addAll(allEmployees);
        employeeCatalog.setItems(tableInfo);
        employeeCatalog.refresh();
    }
        
    @FXML
    public void CloseTheProgram(ActionEvent e)
    {
        stage.close();
    }
    
    @FXML
    public void onTeamCreateAttempt(ActionEvent e)
    {
        if(!teamNameTBox.getText().isEmpty()){
            newTeam = new Team(selectedEmployees, teamNameTBox.getText());
        }
        else return;
        
        stage.close();
    }
    
    @FXML
    public Team getTeam()
    {
        return newTeam;
    }
}
