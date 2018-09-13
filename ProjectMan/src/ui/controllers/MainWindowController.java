
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import backend.datatypes.Employee;
import backend.datatypes.Team;
import backend.logic.Logic;
import backend.server.Data;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sun.security.ssl.Debug;

/**
 *
 * @author manfr
 */
public class MainWindowController implements Initializable {
    
    @FXML
    private TableView<Employee> timeTable;
    @FXML
    private TableColumn<Employee, String> nameColumn, lastNameColumn, positionColumn, hourColumn, accessColumn;
    @FXML
    private Button addTeamMember;
    @FXML
    private MenuButton teamMenuButton;
    
//    List<Team> teams = new ArrayList();
//    List<Employee> employees = new ArrayList();
    public ObservableList<Employee> tableInfo = FXCollections.observableArrayList();
    
    
    private String loggedInUserRights;
    public void setLoggedInUserRights(String loggedInUserRights)  //TODO: use this to enable or disable features
    {
        this.loggedInUserRights = loggedInUserRights;
    }

    
    @FXML
    public void TeamMemberWindowInitializer(ActionEvent e)
    {
        
    }
    
    @FXML
    public void TeamSetupWindowInitializer(ActionEvent e)
    {
        FXMLLoader loader = null;
        Window mainWindow = addTeamMember.getScene().getWindow();
        try
        {
            loader = new FXMLLoader(getClass().getResource("TeamCreationWindow.fxml"));
            Parent root = loader.load();
            if (loader != null && loader.getController() != null)
            {
            TeamCreationWindowController newTeamCreationWindowController = loader.getController();
            newTeamCreationWindowController.setAllEmployees(Data.getEmployees());
            }
            Stage TeamCreationWindowStage = new Stage();
            TeamCreationWindowStage.setTitle("Create new team");
            TeamCreationWindowStage.setScene(new Scene(root));
            
            TeamCreationWindowStage.initModality(Modality.WINDOW_MODAL);
            TeamCreationWindowStage.initOwner(mainWindow);
            
            TeamCreationWindowStage.showAndWait();     //TODO: make the main window in the background inaccessible
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
        /*//Testavimui skirti duomenys
        tableInfo.addAll(employees);
        timeTable.setItems(tableInfo);
        timeTable.refresh();*/
    }
    
    @FXML
    public void AddNewEmployeeInitializer(ActionEvent e) 
    {
        FXMLLoader loader = null;
        Window mainWindow = addTeamMember.getScene().getWindow();
        try
        {
            loader = new FXMLLoader(getClass().getResource("AddNewEmployee.fxml"));
            Parent root = loader.load();
            Stage newEmployeeWindowStage = new Stage();
            newEmployeeWindowStage.setTitle("Enter employee details");
            newEmployeeWindowStage.setScene(new Scene(root));
            
            newEmployeeWindowStage.initModality(Modality.WINDOW_MODAL);
            newEmployeeWindowStage.initOwner(mainWindow);
            
            newEmployeeWindowStage.showAndWait();     //TODO: make the main window in the background inaccessible
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        if (loader != null && loader.getController() != null)
        {
            AddNewEmployeeController newEmployeeController = loader.getController();
            newEmployeeController.setMain(this);
            
//[Tomas] Changed logic, now addEmployee button press adds employee itself
//            Employee createdEmployee = newEmployeeController.returnEmployee();     //get your brand shining new generated employee object here! Limited time offer!
//            
//            if (createdEmployee != null)
//            {
//                Data.add(createdEmployee);
//                System.out.println(createdEmployee);
//            }
        }
    }
    
    protected void add (Employee employee)
    {
        Data.add(employee);
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        hourColumn.setCellValueFactory(new PropertyValueFactory<>("Hours"));
        accessColumn.setCellValueFactory(new PropertyValueFactory<>("Availability"));
    }    
}