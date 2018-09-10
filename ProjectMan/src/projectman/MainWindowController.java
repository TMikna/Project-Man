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

/**
 *
 * @author manfr
 */
public class MainWindowController implements Initializable {
    
    @FXML
    private TableView<TableItemEmployee> timeTable;
    @FXML
    private TableColumn<TableItemEmployee, String> nameColumn, lastNameColumn, positionColumn, hourColumn, accessColumn;
    @FXML
    private Button addTeamMember;
    @FXML
    private MenuButton teamMenuButton;
    
    List<Team> teams;
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
            System.out.println(createdEmployee);
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
    
    
}
