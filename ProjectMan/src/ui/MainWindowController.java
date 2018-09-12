<<<<<<< HEAD:ProjectMan/src/ui/MainWindowController.java
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
import backend.datatypes.Team;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;
import backend.datatypes.TableItemEmployee;

/**
 *
 * @author manfr
 */
public class MainWindowController implements Initializable {
    
    private LoginWindowController main;

    
    @FXML
    private TableView<TableItemEmployee> timeTable;
    @FXML
    private TableColumn<TableItemEmployee, String> nameColumn, lastNameColumn, positionColumn, hourColumn, accessColumn;
    @FXML
    private Button addTeamMember;
    @FXML
    private MenuButton teamMenuButton;
    
//    List<Team> teams;
    public ObservableList<TableItemEmployee> tableInfo = FXCollections.observableArrayList();
    
    
    private String loggedInUserRights;
    public void setLoggedInUserRights(String loggedInUserRights)  //TODO: use this to enable or disable features
    {
        this.loggedInUserRights = loggedInUserRights;
    }

    
    @FXML
    public void TeamMemberWindowInitializer(ActionEvent e)
    {
        FXMLLoader loader = null;
        try
        {
            loader = new FXMLLoader(getClass().getResource("AddNewEmployee.fxml"));
            Parent root = loader.load();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Enter employee details");
            primaryStage.setScene(new Scene(root));
            primaryStage.showAndWait();     //TODO: make the main window in the background inaccessible
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
        if (loader != null && loader.getController() != null)
        {
            System.out.println(((AddNewEmployeeController)loader.getController()).returnEmployee());     //get generated employee object through this ((AddNewEmployeeController)loader.getController()).returnEmployee()
        }
    }
    
    @FXML
    public void TeamSetupWindowInitializer(ActionEvent e)
    {
        //Testavimui skirti duomenys
        tableInfo.add(new TableItemEmployee("12345678", "Tomas", "Mikna", "Programmer", "Available", "40"));  
        tableInfo.add(new TableItemEmployee("54621123", "Manfredas", "Šiurkus", "Programmer", "Available", "40"));
        tableInfo.add(new TableItemEmployee("78954632", "Vilius", "Minkevicius", "Programmer", "Available", "40"));
        tableInfo.add(new TableItemEmployee("87845163", "Edvinas", "Šmita", "Programmer", "Available", "40"));
        tableInfo.add(new TableItemEmployee("74451567", "Teodoras", "Šaulys", "Programmer", "Available", "40"));
        timeTable.setItems(tableInfo);
        timeTable.refresh();
    }
    
    @FXML
    public void ShowTableInfo(ActionEvent e)
    {
       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        hourColumn.setCellValueFactory(new PropertyValueFactory<>("Hours"));
        accessColumn.setCellValueFactory(new PropertyValueFactory<>("Availability"));
    }    
    
    public void init (LoginWindowController main)
    {
        this.main=main;
    }
    
    
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectman;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import backend.Employee;
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
    
    List<Team> teams = new ArrayList();
    List<Employee> employees = new ArrayList();
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
            newTeamCreationWindowController.setAllEmployees(employees);
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
    public void AddNewWindowInitializer(ActionEvent e)
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
            Employee createdEmployee = newEmployeeController.returnEmployee();     //get your brand shining new generated employee object here! Limited time offer!
            
            employees.add(createdEmployee);
            System.out.println(createdEmployee);
        }
        
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
>>>>>>> master:ProjectMan/src/projectman/MainWindowController.java
