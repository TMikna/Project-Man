/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectman;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    
    @FXML
    public void TeamMemberWindowInitializer(ActionEvent e)
    {
        
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
