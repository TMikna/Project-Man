/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import backend.datatypes.Employee;
import backend.datatypes.Project;
import backend.datatypes.Team;
import backend.logic.Statics;
import backend.server.DataStatic;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    private TableColumn<Employee, String> employeesName, employeesID, employeesOccupation, employeesEmail, employeesPhoneNumber;
    @FXML
    private TableColumn<Team, String> teamsName, teamsProject, teamsEmployeeCount, teamsManpower;
    @FXML
    private TableColumn<Team, Button> teamsEdit;
    @FXML
    private TableColumn<Employee, Double> employeesHourCount;
    @FXML
    private ChoiceBox<Team> myTeamSelect;
    @FXML
    private Button addTeamMember;
    @FXML
    private Accordion weekView;
    @FXML
    private AnchorPane userSettingsAnchor, monthViewAnchor, testAnchor;
    @FXML
    private TextField myTeamWeekHrs, myTeamTotalHrs;
    
    private List<ChoiceBox<Integer>> settingsFrom, settingsTo;  //My Day Tab -> settings
    
    private Stage stage;
    private Scene scene;
    private Window window;
    
    private Employee loggedInUser;  //TODO: use this to enable or disable features
    private Team selectedTeam;
    
    public MainWindowController(Employee loggedInUser)
    {
        this.loggedInUser = loggedInUser;
    }
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        myTeamTabInit();
        
        allTeamsTabInit();
    
        myDayTab_SettingsTabInit(); //  For now order is important
        myDayTab_WeekTabInit();     //
        
        myDayTab_MonthTabInit();
        updateEmployeeList(); // populate employee list on init
    }
    
    @Override
    public void whoAmI(Stage stage, Scene scene, Window window)
    {
        this.stage = stage;
        this.scene = scene;
        this.window = window;
    }
    @FXML
    private void updateTeamTable()
    {
        Team testTeam = new Team("chuliganai" + Integer.toString(tempTestNotFinalJustForTesting), new ArrayList<Employee>()
        {{
            add(loggedInUser);
        }});
        DataStatic.getTeams()
                  .add(testTeam);
        Project testProject = new Project("testProject" + Integer.toString(tempTestNotFinalJustForTesting++), new ArrayList<Team>()
        {{
            add(testTeam);
        }});
        testTeam.setProject(testProject);
        DataStatic.getProjects()
                  .add(testProject);
    
        teamsTable.getItems()
                  .clear();
        teamsTable.setItems(FXCollections.observableArrayList(DataStatic.getTeams()));
    }
    
    @FXML
    public void updateEmployeeList()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " not found");
            return;
        }
        
        try {
            backend.server.DBUtilities.getInstance().connect();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return;
        }
        try {
            backend.server.DBUtilities.getInstance().getAllEmployees();
            employeesTable.setItems(FXCollections.observableArrayList(DataStatic.getEmployees()));
            employeesTable.refresh();
            backend.server.DBUtilities.getInstance().disconnect();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }



    /**********************************************************
     *New windows Initializers
     *********************************************************/
    
    @FXML
    public void TeamMemberWindowInitializer()
    {
        
    }
    
    @FXML
    public void TeamSetupWindowInitializer()
    {
    // TODO Manfedas fix this, you are creating TeamCreation now
        FxmlLoader<TeamCreationWindowController> teamCreationWindow = new FxmlLoader<>("/ui/fxml/TeamCreationWindow.fxml",
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
        AddNewEmployeeController addNewEmployeeController = new AddNewEmployeeController();
            new FxmlLoader<>(
                    "/ui/fxml/AddNewEmployee.fxml",
                    "Enter employee details",
                    window,
                    addNewEmployeeController);
        
        if (addNewEmployeeController.getEmployee() != null)
            DataStatic.add(addNewEmployeeController.getEmployee());
        employeesTable.setItems(FXCollections.observableArrayList(DataStatic.getEmployees()));
        employeesTable.refresh();
    }
    
    //TODO Wtf?
    private int tempTestNotFinalJustForTesting = 0;
    
    private void myTeamTabInit()
    {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        hourColumn.setCellValueFactory(param -> new SimpleStringProperty(Double.toString(Statics.getMutablePair(selectedTeam, param.getValue()).getValue())));  //TODO check for null
        accessColumn.setCellValueFactory(new PropertyValueFactory<>("Availability"));
        
        employeesName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        employeesID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        employeesOccupation.setCellValueFactory(new PropertyValueFactory<>("position"));
        employeesHourCount.setCellValueFactory(new PropertyValueFactory<>("dailyHours"));
        
        myTeamSelect.setItems(FXCollections.observableArrayList(Statics.getAllTeamsOfAnEmployee(loggedInUser)));
    
        myTeamSelect.setOnAction(event -> {
            selectedTeam = myTeamSelect.getSelectionModel().getSelectedItem();
            myTeamWeekHrs.setText(null);
            timeTable.getItems().clear();
            if (selectedTeam != null)
            {
                timeTable.setItems(FXCollections.observableArrayList(selectedTeam.getEmployeeList()));
                myTeamWeekHrs.setDisable(false);
            }
            else
            {
                myTeamWeekHrs.setText(null);
                myTeamWeekHrs.setDisable(true);
            }
        });
        
        myTeamWeekHrs.setOnAction(event -> {
            if (myTeamWeekHrs.getText().isEmpty())
            {
                myTeamWeekHrs.setText("0");
                myTeamWeekHrs.setStyle(null);
            }
            else
            {
                try
                {
                    double hours = Double.parseDouble(myTeamWeekHrs.getText());
                    Statics.getMutablePair(selectedTeam, loggedInUser).setValue(hours);
                    timeTable.getItems().clear();
                    timeTable.setItems(FXCollections.observableArrayList(selectedTeam.getEmployeeList()));
                } catch (NumberFormatException e)
                {
                    //e.printStackTrace();
                }
            }
        });
    
        myTeamTotalHrs.setText(Double.toString(Statics.getAllTeamsOfAnEmployee(loggedInUser)    //TODO: call this when updates happen
                                                      .stream()
                                                      .mapToDouble(team -> Statics.getMutablePair(team, loggedInUser)
                                                                                  .getValue())
                                                      .sum()) + " | " + loggedInUser.getDailyHours());  //TODO: add weekly hour count variables in employee class (along with the weekly settings)
        
        employeesEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeesPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }
    
    private void allTeamsTabInit()
    {
        teamsName.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        teamsProject.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProject() == null ? "No project assigned" : param.getValue().getProject().getProjectName()));    //TODO: remove project field from team
        teamsEmployeeCount.setCellValueFactory(param -> new SimpleStringProperty(Integer.toString(param.getValue().size())));
        teamsManpower.setCellValueFactory(new PropertyValueFactory<>("manpower"));
        teamsEdit.setCellValueFactory(column -> new SimpleObjectProperty<>(new Button("Redaguoti")
        {{
            setOnAction(event -> System.out.println(column.getValue()));    //TODO: create a team editing window
        }}));
    }
    
    /*********************************************
     * myDayTabs initializers       @auth Edvinas
     * TODO mnaybe crete new Controller class for myDayTab
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
            Statics.updatePersonalDayTableColumns(fromInt, hrCountInt, table);
        
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
                    Statics.updatePersonalDayTableColumns(fromInt1, hrCountInt1, table);
                });
            });
        
            int finalI = i;
            newEventButton.setOnAction(event -> new FxmlLoader<>(
                    "/ui/fxml/ScheduleNewEvent.fxml",
                    "Naujas ivykis",
                    window,
                    new ScheduleNewEventController(loggedInUser, LocalDate.now().plusDays(finalI-weekDay))
            ));
        }
    }
    
    private void myDayTab_MonthTabInit()
    {
        DatePicker pickerObject = new DatePicker(LocalDate.now());
        Node dateView = new DatePickerSkin(pickerObject).getPopupContent();
        dateView.setLayoutX(15);
        dateView.setLayoutY(15);
    
        dateView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() > 1)
            {
                new FxmlLoader<>(
                        "/ui/fxml/ScheduleNewEvent.fxml",
                        "Naujas ivykis",
                        window,
                        new ScheduleNewEventController(loggedInUser, pickerObject.getValue()));
            }
        });
    
        monthViewAnchor.getChildren().add(dateView);
    }
}
