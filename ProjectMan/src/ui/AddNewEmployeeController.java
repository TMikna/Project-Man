/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.net.URL;
import java.util.ResourceBundle;

import backend.datatypes.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TM
 */
public class AddNewEmployeeController implements Initializable {
    @FXML
    private TextField nameField, surnameField, idField, passwordField, postField, wageField;
    @FXML
    private ChoiceBox<String> positionChoice;   //temporary fx:ids for testing
    
    private Employee employee = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onAddAttempt()
    {
        //TODO: add checks for valid input
        /*this.employee = new Employee(
                nameField.getText(),
                surnameField.getText(),
                idField.getText(),
                passwordField.getText(),
                positionChoice.getValue(),
                Double.parseDouble(wageField.getText()),
                Double.parseDouble(postField.getText()),
                "admin");*/
        this.employee = new Employee("test", "tester","test123", "xxxtesterxxx", "toilet cleaner", 0.01, 23.45, "banned");
        ((Stage)nameField.getScene().getWindow()).close();
    }
    
    public Employee returnEmployee()
    {
        return employee;
    }
}
