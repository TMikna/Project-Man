/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import backend.datatypes.Employee;
import backend.datatypes.Project;
import backend.datatypes.Team;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.sun.javafx.scene.control.skin.DatePickerSkin;    //import javafx.scene.control.skin.DatePickerSkin;  //java 10
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import backend.server.DataStatic;
import java.util.logging.Logger;

/**
 *
 * @author manfr
 */
public class MainWindowController implements Initializable, SelfAwareController
{
    
    @FXML
    private TableView<Employee> timeTable, employeesTable;
    @FXML
    private TableView<Team> teamsTable;
    @FXML
    private TableColumn<Employee, String> nameColumn, lastNameColumn, positionColumn, hourColumn, accessColumn;
    @FXML
    private TableColumn<Team, String> teamsName, teamsProject, teamsEmployeeCount, teamsManpower, teamsEdit;
    @FXML
    private TableColumn<Employee, String> employeesName, employeesID, employeesOccupation, employeesContacts, employeesAccess;
    @FXML
    private TableColumn<Employee, Double> employeesHourCount;
    @FXML
    private Button addTeamMember;
    @FXML
    private Accordion weekView;
    @FXML
    private AnchorPane userSettingsAnchor, monthViewAnchor, testAnchor;
    
    public ObservableList<Employee> tableInfo = FXCollections.observableArrayList();
    List<ChoiceBox<Integer>> settingsFrom, settingsTo;  //My Day Tab -> settings
    
    private Stage stage;
    private Scene scene;
    private Window window;
    
    private Employee loggedInUser;  //TODO: use this to enable or disable features
    
    public MainWindowController(Employee loggedInUser)
    {
        this.loggedInUser = loggedInUser;
    }
    
    @Override
    public void whoAmI(Stage stage, Scene scene, Window window)
    {
        this.stage = stage;
        this.scene = scene;
        this.window = window;
    }
    
    @FXML
    public void TeamMemberWindowInitializer()
    {
        
    }
    
    @FXML
    public void TeamSetupWindowInitializer()
    {
    // TODO Manfedas fix this, you are creating TeamCreation now
        FXMLControllerExtractor<TeamCreationWindowController> teamCreationWindow = new FXMLControllerExtractor<>("/ui/fxml/TeamCreationWindow.fxml",
                "Create new team", window, new TeamCreationWindowController(DataStatic.getEmployees()));
        Team createdTeam = teamCreationWindow.getController().getTeam();
        if (createdTeam != null)
        {
            DataStatic.add(createdTeam);
        }
    }
    
    @FXML
    public void AddNewEmployeeInitializer()
    {
        FXMLControllerExtractor<AddNewEmployeeController> employeeCreationWindow = new FXMLControllerExtractor<>("/ui/fxml/AddNewEmployee.fxml", "Enter employee details", window, new AddNewEmployeeController());
        //Employee createdEmployee = employeeCreationWindow.getController().returnEmployee();
       // if (createdEmployee != null)
        //{
            //DataStatic.add(createdEmployee);
            ObservableList<Employee> tempList = FXCollections.observableArrayList();
            tempList.addAll(DataStatic.getEmployees());
            employeesTable.setItems(tempList);
            employeesTable.refresh();
       // }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myTeamTabInit();
        
        allTeamsTabInit();
    
        myDayTab_SettingsTabInit(); //  For now order is important
        myDayTab_WeekTabInit();     //
        
        myDayTab_MonthTabInit();
        
        /*TableRowExpanderColumn<Employee> column = new TableRowExpanderColumn<>(param -> {
            HBox editor = new HBox(10);
            TextField text = new TextField(param.getValue().getName()), text2 = new TextField(param.getValue().getLastName());  //3rd party library test for easy way to edit table data - may or may not be used - ask Edvinas if interested
            Button save = new Button("Save employee");
            editor.getChildren().addAll(text, text2, save);
            return editor;
        });
        column.setText("column");
        teamsTable.getColumns().add(0, column);
        teamsTable.getItems().add(loggedInUser);
        teamsTable.getItems().add(new Employee("test0", "test0", "test", "test", "test", 2, 2, Employee.ADMIN));
        teamsTable.getItems().add(new Employee("test1", "test1", "test", "test", "test", 2, 2, Employee.ADMIN));
        teamsTable.getItems().add(new Employee("test2", "test2", "test", "test", "test", 2, 2, Employee.ADMIN));*/
    }
    
    private void updatePersonalDayTableColumns(int from, int count, TableView<TableColumn<String, String>> table)
    {
        table.getColumns().clear();
        for (int i = 0; i < count; ++i)
        {
            //[Tomas] TODO: maybe put to backend classes?
            table.getColumns().add(new TableColumn<>((from + i > 24 ? from + i - 24 : from + i)
                    + "-" + (from + i + 1 > 24 ? from + i - 23 : from + i + 1)));
        }
    }
    
    private void myTeamTabInit()
    {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        hourColumn.setCellValueFactory(new PropertyValueFactory<>("Hours"));
        accessColumn.setCellValueFactory(new PropertyValueFactory<>("Availability"));
        
        employeesName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        employeesID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        employeesOccupation.setCellValueFactory(new PropertyValueFactory<>("position"));
        employeesHourCount.setCellValueFactory(new PropertyValueFactory<>("dailyHours"));
    }
    
    private void allTeamsTabInit()
    {
        teamsName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        teamsProject.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProject().getProjectName()));
        teamsEmployeeCount.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().size())));
        teamsManpower.setCellValueFactory(new PropertyValueFactory<>("manpower"));
        teamsEdit.setCellFactory(param -> new TableCell<Team, String>()
        {
            @Override
            protected void updateItem(String item, boolean empty)
            {
                super.updateItem(item, empty);
                if (!empty)
                {
                    Team toEdit = (Team) getTableRow().getItem();
                    setGraphic(new Button("Redaguoti")
                    {{
                        setOnAction(event -> System.out.println(toEdit));
                    }});
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }
            }
        });
    
        Team testTeam = new Team("chuliganai", new ArrayList<Employee>(){{add(loggedInUser);}});          //
        Project testProject = new Project("testProject", new ArrayList<Team>(){{add(testTeam);}});      //  TEST DATA
        testTeam.setProject(testProject);                                                                           //
        teamsTable.getItems().add(testTeam);                                                                        //
    }
    
    /*********************************************
     * myDayTabs initializers       @auth Edvinas
     * TODO crete new Controller class for myDayTab
     ********************************************/
 
    private void myDayTab_SettingsTabInit()
    {
        List<ChoiceBox<Integer>> settingsShared = userSettingsAnchor.getChildren().stream().filter(child ->
                child instanceof ChoiceBox).map(box -> (ChoiceBox<Integer>) box).collect(Collectors.toList());
        settingsFrom = settingsShared.subList(0, 5);
        settingsTo = settingsShared.subList(5, 10);
        Button settingsChangeButton = (Button) userSettingsAnchor.getChildren().stream().filter(node ->
                node instanceof Button).collect(Collectors.toList()).get(0);    //doubt that these are needed outside of this function so I do this to avoid cluttering class-wide variables
        TextField settingsHrsPerWeek = (TextField) userSettingsAnchor.getChildren().stream().filter(node ->
                node instanceof TextField).collect(Collectors.toList()).get(0);
        settingsChangeButton.setOnAction(actionEvent -> 
            {
            boolean illegal = false;
            int countSum = 0;
            for (int i = 0; i < 5; ++i)
            {
                int fromInt = settingsFrom.get(i).getSelectionModel().getSelectedItem(), 
                        toInt = settingsTo.get(i).getSelectionModel().getSelectedItem(),
                        hrCountInt = (toInt - fromInt < 0 ? 24 + toInt - fromInt : toInt - fromInt);
                countSum += hrCountInt;
                if (hrCountInt > 12)
                {
                    settingsFrom.get(i).setStyle("-fx-background-color: red");
                    settingsTo.get(i).setStyle("-fx-background-color: red");
                    illegal = true;
                }
                else
                {
                    settingsFrom.get(i).setStyle(null);
                    settingsTo.get(i).setStyle(null);
                }
            }
            if (!illegal)
            {
                settingsHrsPerWeek.setText(Integer.toString(countSum)); //TODO: actually save the settings
            }
        });
        ObservableList<Integer> hrs = FXCollections.observableArrayList();
        for (int i = 0; i < 24; ++i)
        {
            hrs.add(i);
        }
        settingsShared.forEach(box -> box.setItems(hrs));
        for (int i = 0; i < 5; ++i)
        {
            settingsFrom.get(i).getSelectionModel().select((Integer) 8);    //TODO: actually load the settings
            settingsTo.get(i).getSelectionModel().select((Integer) 16);
        }
        settingsHrsPerWeek.setText(Integer.toString(settingsTo.stream().mapToInt(integerChoiceBox ->
            integerChoiceBox.getSelectionModel().getSelectedItem()).sum() - settingsFrom.stream().mapToInt(integerChoiceBox ->
                integerChoiceBox.getSelectionModel().getSelectedItem()).sum()));
    }
    
    private void myDayTab_WeekTabInit()
    {
        ObservableList<Integer> hrs = FXCollections.observableArrayList();
        for (int i = 0; i < 24; ++i)
        {
            hrs.add(i);
        }
        for (int i = 0; i < weekView.getPanes().size(); ++i)    //hacky way to avoid a lot of code copying since there are 5 identical panes with nodes and each of them need code
        {
            TitledPane pane = weekView.getPanes().get(i);
            int weekDay = DayOfWeek.from(LocalDate.now()).getValue() - 1;   //-1 because it starts at 1 and real programmers count from 0
            pane.setText(pane.getText() + " (" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now().plusDays(i-weekDay)) + ")");  //adds numeric dates to each day and underlines current day
            if (i == weekDay)
            {
                pane.setUnderline(true);
            }
        
            AnchorPane anchorPane = ((AnchorPane)pane.getContent());
            //very professional variable assigning probably better not to touch
            TableView<TableColumn<String, String>> table = (TableView) anchorPane.getChildren().get(0);    //very professional variable assigning probably better not to touch
            List<ChoiceBox<Integer>> choiceBoxes = anchorPane.getChildren().stream().filter(node -> 
                    node instanceof ChoiceBox).map(node -> (ChoiceBox<Integer>) node).collect(Collectors.toList());
            ChoiceBox<Integer> fromHr = choiceBoxes.get(0), toHr = choiceBoxes.get(1);
            List<Button> buttons = anchorPane.getChildren().stream().filter(node -> node instanceof Button).map(node -> 
                    (Button) node).collect(Collectors.toList());
            Button changeButton = buttons.get(0), checkButton = buttons.get(1), newEventButton = buttons.get(2);
        
            fromHr.setItems(hrs);
            fromHr.getSelectionModel().select(settingsFrom.get(i).getSelectionModel().getSelectedItem());
            toHr.setItems(hrs);
            toHr.getSelectionModel().select(settingsTo.get(i).getSelectionModel().getSelectedItem());
            int fromInt = fromHr.getSelectionModel().getSelectedItem(), toInt = toHr.getSelectionModel().getSelectedItem(),
                    hrCountInt = (toInt - fromInt < 0 ? 24 + toInt - fromInt : toInt - fromInt);
            updatePersonalDayTableColumns(fromInt, hrCountInt, table);
        
            EventHandler<ActionEvent> normalChangeAction = action -> {
                fromHr.setDisable(false);
                toHr.setDisable(false);
                checkButton.setDisable(false);
                changeButton.setDisable(true);
            };
            changeButton.setOnAction(normalChangeAction);
            checkButton.setOnAction(action -> {
                int fromInt1 = fromHr.getSelectionModel().getSelectedItem(), toInt1 = toHr.getSelectionModel().getSelectedItem(),
                        hrCountInt1 = (toInt1 - fromInt1 < 0 ? 24 + toInt1 - fromInt1 : toInt1 - fromInt1);
                if (hrCountInt1 > 12)
                {
                    checkButton.setStyle("-fx-background-color: red");    //not legal to work more than 12h/day
                    return;
                }
                /*else if ()
                {
                    TODO: possibly check weather optional or mandatory events are skipped with the selected time
                    check.setStyle("-fx-background-color: yellow");   //skipping optional events
                    check.setStyle("-fx-background-color: orange");   //skipping mandatory events
                }*/
                else
                {
                    checkButton.setStyle("-fx-background-color: #00ff00");    //all ok
                }
            
            
                changeButton.setDisable(false);
                fromHr.setOnAction(reselected -> {  //no scammerino - prevents checking if available, changing the times and applying not-checked times
                    changeButton.setDisable(true);
                    checkButton.setStyle(null);
                });
                toHr.setOnAction(reselected -> {    //no scammerino
                    changeButton.setDisable(true);
                    checkButton.setStyle(null);
                });
                changeButton.setOnAction(applyChange -> {
                    fromHr.setDisable(true);
                    fromHr.setOnAction(null);
                    toHr.setDisable(true);
                    toHr.setOnAction(null);
                
                    checkButton.setDisable(true);
                    checkButton.setStyle(null);
                
                    changeButton.setOnAction(normalChangeAction);
                    updatePersonalDayTableColumns(fromInt1, hrCountInt1, table);
                });
            });
        
            int finalI = i;
            newEventButton.setOnAction(event -> {   //Event scheduling
                FXMLControllerExtractor<ScheduleNewEventController> newEventWindow = 
                        new FXMLControllerExtractor<>("/ui/fxml/ScheduleNewEvent.fxml",
                        "Naujas ivykis", window, new ScheduleNewEventController(loggedInUser, LocalDate.now().plusDays(finalI-weekDay)));
            });
        }
    }
    
    private void myDayTab_MonthTabInit()
    {
        DatePicker pickerObject = new DatePicker(LocalDate.now());
        Node dateView = new DatePickerSkin(pickerObject).getPopupContent();
        dateView.setLayoutX(15);
        dateView.setLayoutY(15);
        pickerObject.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(observable + " " + oldValue + " " + newValue);
        });
    
        dateView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() > 1)
            {
                FXMLControllerExtractor<ScheduleNewEventController> newEventWindow = 
                        new FXMLControllerExtractor<>("/ui/fxml/ScheduleNewEvent.fxml",
                        "Naujas ivykis", window, new ScheduleNewEventController(loggedInUser, pickerObject.getValue()));
            }
        });
    
        monthViewAnchor.getChildren().add(dateView);
    }
}
