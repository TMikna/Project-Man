/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.logic;

import backend.datatypes.Employee;
import backend.datatypes.MutablePair;
import backend.datatypes.Team;
import backend.server.DataStatic;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TM
 */
public final class Statics
{
    public static void updatePersonalDayTableColumns(int from, int count, TableView<TableColumn<String, String>> table)
    {
        table.getColumns()
             .clear();
        for (int i = 0; i < count; ++i)
        {
            table.getColumns()
                 .add(new TableColumn<>((from + i > 24
                                         ? from + i - 24
                                         : from + i) + "-" + (from + i + 1 > 24
                                                              ? from + i - 23
                                                              : from + i + 1)));
        }
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
