/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.logic;

import backend.datatypes.Employee;
import backend.datatypes.Event;
import backend.datatypes.MutablePair;
import backend.datatypes.Team;
import backend.server.DataStatic;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.PopupWindow;
import ui.controllers.EventViewerController;
import ui.controllers.FxmlLoader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TM
 */
public final class Statics
{
/*****************************************************************************************
 * Constants
 *****************************************************************************************/
    
public static final String COMPANY = "Projct Man";
public static final String COMPANYDOMAIN = "projctman.com"; //[Tomas] Not sure if name is good, also could be a method for tht but since name is declared here this way avoids unessecary coding

    
    
    
/******************************************************************************************
 * Static methods
 *****************************************************************************************/

    public static void updatePersonalDayTableColumns(int from, int count, TableView<Integer> table, LocalDate day, Employee loggedInUser)
    {
        table.getColumns()
             .clear();
        table.getColumns()
             .add(new TableColumn<Integer, String>("Minutes \\ Valandos")
                 {{
                     setCellValueFactory(column -> new SimpleStringProperty(Integer.toString(column.getValue())));
                 }}
             );
        for (int i = 0; i < count; ++i)
        {
            int columnHour = from + i > 24
                        ? from + i - 24
                        : from + i;
            LocalDateTime cellDateTime = LocalDateTime.of(day, LocalTime.of(columnHour, 0));
            table.getColumns()
                 .add(new TableColumn<Integer, HBox>(Integer.toString(columnHour))
                 {{
                     setCellValueFactory(column -> new SimpleObjectProperty<>(new HBox()
                     {{
                         getChildren().setAll(DataStatic.getEvents()
                                                        .stream()
                                                        .filter(event -> event.getTargets()
                                                                              .contains(loggedInUser) && localDateTimeBetween(event.getEventStartDateTime(), cellDateTime.plusMinutes(column.getValue()), event.getEventEndDateTime()))
                                                        .map(event -> new Text(event.getEventName() + " ")
                                                        {{
                                                            setFill(eventColor(loggedInUser, event));
                                                            setOnMouseClicked(event1 -> {
                                                                if (event1.getButton().equals(MouseButton.PRIMARY) && event1.getClickCount() >= 2)
                                                                {
                                                                    System.out.println("VIEW: " + event);
                                                                    new FxmlLoader<>("/ui/fxml/EventViewer.fxml", day + ":\t" + Integer.toString(event.getFrom()) + "h - " + event.getEventEndDateTime(), new EventViewerController(event));
                                                                }
                                                            });
                                                        }})
                                                        .collect(Collectors.toList()));
                     }}));
                 }});
        }
    }
    
    private static Color eventColor(Employee loggedInUser, Event event)
    {
        switch (event.getScale())
        {
            case PERSONAL:
                return event.isImportanceMandatory() ? loggedInUser.getMandatoryPersonal() : loggedInUser.getOptionalPersonal();
            case TEAM:
                return event.isImportanceMandatory() ? loggedInUser.getMandatoryTeam() : loggedInUser.getOptionalTeam();
            case PROJECT:
                return event.isImportanceMandatory() ? loggedInUser.getMandatoryProject() : loggedInUser.getOptionalProject();
            case COMPANY:
                return event.isImportanceMandatory() ? loggedInUser.getMandatoryCompany() : loggedInUser.getOptionalCompany();
            case OTHER:
                return event.isImportanceMandatory() ? loggedInUser.getMandatoryOther() : loggedInUser.getOptionalOther();
        }
        return Color.CORNFLOWERBLUE;
    }
    
    private static boolean localDateTimeBetween(LocalDateTime before, LocalDateTime dateTime, LocalDateTime after)
    {
        return before.isBefore(dateTime) | before.isEqual(dateTime) && after.isAfter(dateTime);
    }
    
    public static List<Team> getAllTeamsOfAnEmployee(Employee employee)
    {
        return DataStatic.getTeams()
                         .stream()
                         .filter(team -> team.getEmployeeList().contains(employee))
                         .collect(Collectors.toList());
    }
    
    public static MutablePair<Employee, Double> getMutablePair(Team team, Employee employee)
    {
        List<MutablePair<Employee, Double>> possiblePairs = team.stream().filter(pair -> pair.getKey().equals(employee)).collect(Collectors.toList());
        if (possiblePairs == null || possiblePairs.isEmpty() || possiblePairs.size() > 1)
        {
            return null;
        }
        else
        {
            return possiblePairs.get(0);
        }
    }
}
