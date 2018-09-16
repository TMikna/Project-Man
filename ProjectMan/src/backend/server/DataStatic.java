/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.server;

import backend.datatypes.Employee;
import backend.datatypes.Event;
import backend.datatypes.Project;
import backend.datatypes.Team;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TM
 */
//TODO: CONSIDER how to do: Static or usuall class?
public class DataStatic {
    private static List<Event> events = new ArrayList<>();
    private static List<Project> projects = new ArrayList<>();
    private static List<Team> teams = new ArrayList<>();
    private static List<Employee> employees = new ArrayList<>();
    
    public static void add (Employee employee)
    {
        employees.add(employee);
    }
    
    public static void add (Team team)
    {
        teams.add(team);
    }
    
    public static List<Team> getTeams() {
        return teams;
    }
    public static void setTeams (List<Team> teams) {
        //[Tomas] Not sure if works 
        DataStatic.teams = teams;
    }
    public static List<Employee> getEmployees() {
        return DataStatic.employees;
    }
    public static void setEmployees(List<Employee> employees) {
        //[Tomas] Not sure if works 
        DataStatic.employees = employees;
    }
    
    public static List<Event> getEvents()
    {
        return events;
    }
    
    public static List<Project> getProjects()
    {
        return projects;
    }
}
