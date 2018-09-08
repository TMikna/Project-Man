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
    private TableView<Employee> timeTable;
    @FXML
    private TableColumn<Employee, String> nameColumn, lastNameColumn, positionColumn, hourColumn, accessColumn;
    @FXML
    private Button addTeamMember;
    @FXML
    private MenuButton teamMenuButton;
    
    List<Team> teams;
    public ObservableList<Employee> tableInfo = FXCollections.observableArrayList();
    
    @FXML
    public void TeamMemberWindowInitializer(ActionEvent e)
    {
        
    }
    
    @FXML
    public void TeamSetupWindowInitializer(ActionEvent e)
    {
         tableInfo.add(new Employee("12345678", "Tomas", "Mikna"));
        tableInfo.add(new Employee("54621123", "Manfredas", "Šiurkus"));
        tableInfo.add(new Employee("78954632", "Vilius", "Minkevicius"));
        tableInfo.add(new Employee("87845163", "Edvinas", "Šmita"));
        tableInfo.add(new Employee("74451567", "Teodoras", "Šaulys"));
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
        hourColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        accessColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
    }    
    
    
}
