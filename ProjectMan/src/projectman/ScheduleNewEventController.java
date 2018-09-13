package projectman;

import backend.Employee;
import backend.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

public class ScheduleNewEventController implements SelfAwareController
{
    @FXML
    private RadioButton importanceOptional, importanceMandatory, scalePersonal, scaleTeam, scaleProject, scaleCompany, scaleGlobal, scaleOther;  //scaleTeam is for team members; scaleProject - team leaders; scaleCompany - project leaders; global - absolutely everyone
    @FXML
    private DatePicker eventDate;
    @FXML
    private ChoiceBox<Integer> fromHr, toHr, reminderHr;
    @FXML
    private CheckBox reminder;
    @FXML
    private TextField eventName;
    @FXML
    private TextArea eventDescription;
    
    
    private Employee user = null;
    private LocalDate startingDate = null;
    
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
    
    public void setUp(Employee user, LocalDate startingDate)
    {
        this.user = user;
        this.startingDate = startingDate;
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
            case Employee.EMPLOYEE:
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
                return;
            }
            case Employee.TEAM_MANAGER:
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
                return;
            }
            case Employee.PROJECT_MANAGER:
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
                return;
            }
            case Employee.COMPANY_MANAGER:
            case Employee.ADMIN:
            {
                scaleTeam.setDisable(false);
                scaleProject.setDisable(false);
                scaleCompany.setDisable(false);
                scaleGlobal.setDisable(false);
                scaleOther.setDisable(false);
                
                enableReminderForMandatory();
                return;
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
        if (scalePersonal.isSelected())
        {
            new Event(importanceMandatory.isSelected(), reminder.isSelected(), List.of(user), startingDate, fromHr.getValue(), toHr.getValue(), reminderHr.getValue(), eventName.getText(), eventDescription.getText());
        } else if (scaleTeam.isSelected())
        {
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
