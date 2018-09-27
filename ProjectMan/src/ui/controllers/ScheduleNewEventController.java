package ui.controllers;

import backend.datatypes.Employee;
import backend.datatypes.Event;
import backend.datatypes.Team;
import backend.logic.Statics;
import backend.server.DataStatic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ScheduleNewEventController implements Initializable, SelfAwareController
{
    @FXML
    private RadioButton importanceOptional, importanceMandatory, scalePersonal, scaleTeam, scaleProject, scaleCompany, scaleGlobal, scaleOther;  //scaleTeam is for team members; scaleProject - team leaders; scaleCompany - project leaders; global - absolutely everyone
    @FXML
    private DatePicker eventDate;
    @FXML
    private ChoiceBox<Integer> fromHr, toHr, reminderHr;    //TODO would look and feel way nicer if minutes could be selected but it might be quite a hassle for the user and also to code
    @FXML
    private ChoiceBox<Team> teamSelect;
    @FXML
    private CheckBox reminder;
    @FXML
    private TextField eventName;
    @FXML
    private TextArea eventDescription;
    
    
    private Employee user;
    private LocalDate startingDate;
    
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
    
    public ScheduleNewEventController(Employee user, LocalDate startingDate)
    {
        this.user = user;
        this.startingDate = startingDate;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        eventDate.setValue(startingDate);
        eventDate.setConverter(new StringConverter<LocalDate>()
        {
            @Override
            public String toString(LocalDate object)
            {
                return object.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        
            @Override
            public LocalDate fromString(String string)
            {
                return LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(string));
            }
        });
        eventDate.setOnAction(event -> {
            changeDate(eventDate.getValue());
        });
        
        teamSelect.setItems(FXCollections.observableArrayList(Statics.getAllTeamsOfAnEmployee(user)));
        teamSelect.setOnAction(event -> teamSelect.setStyle(null));
    
        ObservableList<Integer> hrs = FXCollections.observableArrayList();
        for (int i = 0; i < 24; ++i)
        {
            hrs.add(i);
        }
        fromHr.setItems(hrs);
        toHr.setItems(hrs);
        reminderHr.setItems(hrs);
    
        Consumer<RadioButton> disableAndUnselect = radioButton -> {
            radioButton.setDisable(true);
            if (radioButton.isSelected())
            {
                radioButton.setSelected(false);
                scalePersonal.setSelected(true);
            }
        };
        switch (user.getPrivileges())
        {
            case NO_ACCESS:
            {
                //shouldnt event be able to open this window
                return;
            }
            case EMPLOYEE:
            {
                scaleTeam.setDisable(false);
                disableAndUnselect.accept(scaleProject);
                disableAndUnselect.accept(scaleCompany);
                disableAndUnselect.accept(scaleGlobal);
                scaleOther.setDisable(false);
            
                disableReminderForOptional();
            
                importanceOptional.setOnAction(event -> {
                    scaleTeam.setDisable(false);
                    disableAndUnselect.accept(scaleProject);
                    disableAndUnselect.accept(scaleCompany);
                    disableAndUnselect.accept(scaleGlobal);
                    scaleOther.setDisable(false);
                
                    disableReminderForOptional();
                });
            
                importanceMandatory.setOnAction(event -> {
                    disableAndUnselect.accept(scaleTeam);
                    disableAndUnselect.accept(scaleProject);
                    disableAndUnselect.accept(scaleCompany);
                    disableAndUnselect.accept(scaleGlobal);
                    disableAndUnselect.accept(scaleOther);
                
                    enableReminderForMandatory();
                });
                break;
            }
            case TEAM_MANAGER:  //TODO change to check if leader of selected team
            {
                scaleTeam.setDisable(false);
                scaleProject.setDisable(false);
                disableAndUnselect.accept(scaleCompany);
                disableAndUnselect.accept(scaleGlobal);
                scaleOther.setDisable(false);
            
                disableReminderForOptional();
            
                importanceOptional.setOnAction(event -> {
                    scaleTeam.setDisable(false);
                    scaleProject.setDisable(false);
                    disableAndUnselect.accept(scaleCompany);
                    disableAndUnselect.accept(scaleGlobal);
                    scaleOther.setDisable(false);
                
                    disableReminderForOptional();
                });
            
                importanceMandatory.setOnAction(event -> {
                    scaleTeam.setDisable(false);
                    disableAndUnselect.accept(scaleProject);
                    disableAndUnselect.accept(scaleCompany);
                    disableAndUnselect.accept(scaleGlobal);
                    disableAndUnselect.accept(scaleOther);
                
                    enableReminderForMandatory();
                });
                break;
            }
            case PROJECT_MANAGER:
            {
                scaleTeam.setDisable(false);
                scaleProject.setDisable(false);
                scaleCompany.setDisable(false);
                disableAndUnselect.accept(scaleGlobal);
                scaleOther.setDisable(false);
            
                disableReminderForOptional();
            
                importanceOptional.setOnAction(event -> {
                    scaleTeam.setDisable(false);
                    scaleProject.setDisable(false);
                    scaleCompany.setDisable(false);
                    disableAndUnselect.accept(scaleGlobal);
                    scaleOther.setDisable(false);
                
                    disableReminderForOptional();
                });
            
                importanceMandatory.setOnAction(event -> {
                    scaleTeam.setDisable(false);
                    scaleProject.setDisable(false);
                    disableAndUnselect.accept(scaleCompany);
                    disableAndUnselect.accept(scaleGlobal);
                    disableAndUnselect.accept(scaleOther);
                
                    enableReminderForMandatory();
                });
                break;
            }
            case COMPANY_MANAGER:
            case ADMIN:
            {
                scaleTeam.setDisable(false);
                scaleProject.setDisable(false);
                scaleCompany.setDisable(false);
                scaleGlobal.setDisable(false);
                scaleOther.setDisable(false);
            
                enableReminderForMandatory();
                break;
            }
        }
    }
    
    private void disableReminderForOptional()
    {
        reminder.setDisable(true);
        reminder.setSelected(false);
        reminderHr.setDisable(true);
        reminderHr.getSelectionModel()
                  .clearSelection();
    }
    private void enableReminderForMandatory()
    {
        reminder.setDisable(false);
        reminderHr.setDisable(!reminder.isSelected());
        reminder.setOnAction(event1 -> {
            reminderHr.setDisable(!reminder.isSelected());
            reminderHr.getSelectionModel().clearSelection();
        });
    }
    
    private void changeDate(LocalDate date)
    {
        this.startingDate = date;
    }
    
    @FXML
    private void onSave()
    {
        boolean correctInfo = true;
        if (toHr.getSelectionModel().isEmpty())
        {
            toHr.setStyle("-fx-background-color: red");
            correctInfo = false;
        }
        if (fromHr.getSelectionModel().isEmpty())
        {
            fromHr.setStyle("-fx-background-color: red");
            correctInfo = false;
        }
        if (eventName.getText().isEmpty())
        {
            eventName.setStyle("-fx-background-color: red");
            correctInfo = false;
        }
        if (!correctInfo)
        {
            return;
        }
        
        
        if (scalePersonal.isSelected())
        {
            DataStatic.getEvents().add(new Event(
                importanceMandatory.isSelected(),
                Event.Scale.PERSONAL,
                reminder.isSelected(),
                Arrays.asList(user),
                startingDate,
                fromHr.getValue(),
                toHr.getValue(),
                reminder.isSelected() ? reminderHr.getValue() : -1,
                eventName.getText(),
                eventDescription.getText())
            );
            stage.close();
        } else if (scaleTeam.isSelected())
        {
            if (teamSelect.getSelectionModel().isEmpty())
            {
                teamSelect.setStyle("-fx-background-color: red");
                return;
            }
            DataStatic.getEvents().add(new Event(
                    importanceMandatory.isSelected(),
                    Event.Scale.TEAM,
                    reminder.isSelected(),
                    teamSelect.getSelectionModel().getSelectedItem().getEmployeeList(),
                    startingDate,
                    fromHr.getValue(),
                    toHr.getValue(),
                    reminder.isSelected() ? reminderHr.getValue() : -1,
                    eventName.getText(),
                    eventDescription.getText())
            );
            stage.close();
            //same but with a list of teammates
        } else if (scaleProject.isSelected())
        {
            //same but with a list of projectmates
        } else if (scaleCompany.isSelected())
        {
            //same but with a list of teammates
        } else if (scaleOther.isSelected())
        {
            //not really same - need to get a custom list
        }
    }
}
