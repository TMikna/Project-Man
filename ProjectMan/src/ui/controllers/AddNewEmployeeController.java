/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import backend.datatypes.Employee;
import backend.logic.Statics;
import static backend.logic.Statics.COMPANYDOMAIN;
import backend.server.DataStatic;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * FXML Controller class
 *
 * @author TM
 */

/*******************************
 * ideas TODO show number of selected employees
 * @author TM
 */

public class AddNewEmployeeController implements Initializable, SelfAwareController
{
    @FXML
    private TextField nameField, surnameField, idField, passwordField, postField, wageField, emailField, phoneNumberField;
    @FXML
    private ChoiceBox<String> positionChoice;   //temporary fx:ids for testing
    
    private boolean correctData;
    
    private Employee employee = null;
    private UUID uuid = null;
    
    private Stage stage;
    private Scene scene;
    private Window window;
    
    public void whoAmI(Stage stage, Scene scene, Window window)
    {
        this.stage = stage;
        this.scene = scene;
        this.window = window;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        positionChoice.setItems(FXCollections.observableArrayList("sysadmin", "programmer", "tester", "QnA", "manager"));
        // TODO
        Consumer<TextField> addEmptyStringCheckerOnFocusLost = field -> {
            field.focusedProperty()
                 .addListener((arg, oldVal, newVal) -> {
                     if (oldVal && !newVal)
                     {
                         if (field.getText().isEmpty())
                         {
                             field.setStyle("-fx-background-color: red");
                             correctData = false;
                         }
                         else
                         {
                             field.setStyle(null);
                             if (field == nameField || field == surnameField)
                                 tryToSetEmail();
                         }
                     }
                 });
        };
        
        Consumer<TextField> addCorrectDoubleCheckerOnFocusLost = field -> {
            field.focusedProperty()
                 .addListener((arg, oldVal, newVal) -> {
                     if (oldVal && !newVal)
                     {
                         if (field.getText()
                                  .isEmpty())
                         {
                             field.setStyle("-fx-background-color: red");
                             correctData = false;
                         } else
                         {
                             try
                             {
                                 Double.parseDouble(field.getText());
                                 field.setStyle(null);
                             } catch (NumberFormatException e)
                             {
                                 e.printStackTrace();   //<---------------------------------------------------------------------------
                                 field.setStyle("-fx-background-color: red");
                                 correctData = false;
                             }
                         }
                     }
                 });
        };
        addEmptyStringCheckerOnFocusLost.accept(nameField);
        addEmptyStringCheckerOnFocusLost.accept(surnameField);
        addEmptyStringCheckerOnFocusLost.accept(passwordField);
        addEmptyStringCheckerOnFocusLost.accept(emailField);        
        addEmptyStringCheckerOnFocusLost.accept(phoneNumberField);
        addCorrectDoubleCheckerOnFocusLost.accept(postField);
        addCorrectDoubleCheckerOnFocusLost.accept(wageField);
        
        positionChoice.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (oldVal && !newVal)
            {
                if (positionChoice.getSelectionModel().isEmpty())
                {
                    positionChoice.setStyle("-fx-background-color: red");
                    correctData = false;
                }
                else
                {
                    positionChoice.setStyle(null);
                }
            }
        });
    }
    
    @FXML
    private void onGenerateID()
    {
        uuid = UUID.randomUUID();  //TODO?
        idField.setText(uuid.toString());
        idField.setStyle(null);
    }
    
    @FXML
    private void onAddAttempt()
    {
        correctData = true;
        CheckAllFields();
        
        if (idField.getText().isEmpty())
        {
            idField.setStyle("-fx-background-color: red");
            correctData = false;
        }
        
        if (correctData)
        {
            // TODO add checks if given data is suitable (now trying parseDouble(...) can cause exception)
            //added better checks for valid data but left the exception it is part of checking and it will always throw the exception, except we can choose not to print it (see the arrow above) [Edvinas]
            this.employee = new Employee(
                    nameField.getText(),
                    surnameField.getText(),
                    uuid,
                    passwordField.getText(),
                    positionChoice.getValue(),
                    Double.parseDouble(wageField.getText()),
                    Double.parseDouble(postField.getText()),
                    emailField.getText(),
                    phoneNumberField.getText(),
                    Employee.AccessRights.ADMIN
            );
            System.out.println("DEBUG:: Created new employee: " + employee);
            //DataStatic.add(employee);
            try {
                 backend.server.DBUtilities.getInstance().connect();
                 backend.server.DBUtilities.getInstance().addEmployee(employee);
                 backend.server.DBUtilities.getInstance().disconnect();
            } catch (SQLException e) {
                 DataStatic.add(employee); // add the employee locally in case of an DB error
                 System.out.println(e.getMessage());
            } finally {
                 stage.close();
            }
         }
    }
    
    @FXML
    private void onCancel()
    {
        //maybe check if the user entered some data and prompt the exit
        //TODO: refactor to navigate back to MainWindow <- ??? it always gave control back to main window after closing [Edvinas]
        this.employee = null;
        stage.close();
    }
    
    public Employee getEmployee()
    {
        return this.employee;
    }
    
    /***********************************
     * Helper methods
     **********************************/
     
    // Check if there is any empty fields
    private void CheckAllFields()
        {
        nameField.requestFocus();
        surnameField.requestFocus();
        positionChoice.requestFocus();
        passwordField.requestFocus();
        postField.requestFocus();
        wageField.requestFocus();
        emailField.requestFocus();
        phoneNumberField.requestFocus();
        }

    //When name and surname are valid create and input email, malso changeb it after ame or surmane change
    private void tryToSetEmail()
    {
        if (!nameField.getText().isEmpty() && !surnameField.getText().isEmpty())
            emailField.setText(nameField.getText() + "." + surnameField.getText() + "@" + Statics.COMPANYDOMAIN); // In case there will be more places to generete email move move this to Statics.java
    }
}
