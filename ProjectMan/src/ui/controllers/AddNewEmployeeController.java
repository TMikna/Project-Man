/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import backend.datatypes.Employee;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import backend.logic.Statics;

/**
 * FXML Controller class
 *
 * @author TM
 */

/*******************************
 * ideas TODO show number of selected employees
 * @author TM
 */

public class AddNewEmployeeController implements Initializable {
    @FXML
    private TextField nameField, surnameField, idField, passwordField, postField, wageField;
    @FXML
    private ChoiceBox<String> positionChoice;   //temporary fx:ids for testing
    
    private Employee employee = null;
    private MainWindowController mainController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        positionChoice.setItems(FXCollections.observableArrayList("sysadmin","programmer","tester","QnA","manager"));
        // TODO
       
        
        Consumer<TextField> addEmptyStringCheckerOnFocusLost = field -> {
            field.focusedProperty().addListener((arg, oldVal, newVal) -> {
                if (oldVal && !newVal)
                {
                    field.setStyle("-fx-background-color: " + (field.getText().isEmpty() ? "red" : "white"));
                }
            });
        };
    
        Consumer<TextField> addCorrectDoubleCheckerOnFocusLost = field -> {
            field.focusedProperty().addListener((arg, oldVal, newVal) -> {
                if (oldVal && !newVal)
                {
                    if (field.getText().isEmpty())
                    {
                        field.setStyle("-fx-background-color: red");
                    }
                    else
                    {
                        try
                        {
                            Double.parseDouble(field.getText());
                            field.setStyle("-fx-background-color: white");
                        }
                        catch (NumberFormatException e)
                        {
                            e.printStackTrace();
                            field.setStyle("-fx-background-color: red");
                        }
                    }
                }
            });
        };
        addEmptyStringCheckerOnFocusLost.accept(nameField);
        addEmptyStringCheckerOnFocusLost.accept(surnameField);
        addEmptyStringCheckerOnFocusLost.accept(passwordField);
        addCorrectDoubleCheckerOnFocusLost.accept(postField);
        addCorrectDoubleCheckerOnFocusLost.accept(wageField);
    }
    
    @FXML
    private void onGenerateID()
    {
        idField.setText(Statics.generateID());  //TODO: generate actually unique ID
    }
    
    @FXML
    private void onAddAttempt()
    {
        if (!nameField.getText().isEmpty()
                    && !surnameField.getText().isEmpty()
                    && !idField.getText().isEmpty()
                    && !passwordField.getText().isEmpty()
                    && !postField.getText().isEmpty()
                    && !wageField.getText().isEmpty()
                    && !positionChoice.getSelectionModel().getSelectedItem().isEmpty())
        {
            // TODO add checks if given data is suitable (now trying parseDouble(...) can cause exception)
            this.employee = new Employee(
                    nameField.getText(),
                    surnameField.getText(),
                    idField.getText(),
                    passwordField.getText(),
                    positionChoice.getValue(),
                    Double.parseDouble(wageField.getText()),
                    Double.parseDouble(postField.getText()),
                    "admin");
            //this.employee = new Employee("test", "tester","test123", "xxxtesterxxx", "toilet cleaner", 0.01, 23.45, "banned");
            ((Stage)nameField.getScene().getWindow()).close();
        }
        if (mainController != null)
            mainController.add(employee);
        System.out.println(" mainController in onAddAttempt method in AddNewEmployeeController class: "+mainController);
        //TODO not important.
        //else
            //throw exception
        
    }
    
    @FXML
    private void onCancel()
    {
        //maybe check if the user entered some data and prompt the exit
        //TODO: refactor to navigate back to MainWindow
        this.employee = null;
        ((Stage)nameField.getScene().getWindow()).close();
    }
    
    public Employee returnEmployee()
    {
        return employee;
    }

    // Sets reference to mainColtroller so we can invoke MainController functions from here
    void setMain(MainWindowController mainController) {
        this.mainController = mainController;
        System.out.println("setMainWorks");
        System.out.println(this.mainController);
    }
    
}