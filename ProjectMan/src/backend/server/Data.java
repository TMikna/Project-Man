/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.server;

import backend.datatypes.Employee;
import backend.datatypes.Team;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TM
 */
//TODO: CONSIDER how to do: Static or usuall class?
public class Data {
    // TODO: move lists to Data
    private static List<Team> teams = new ArrayList();
    private static List<Employee> employees = new ArrayList();
            
    public static void add (Employee employee)
    {
        employees.add(employee);
    }
    
    public static void add (Team team)
    {
        teams.add(team);
    }
    
    public List<Team> getTeams() {
        return teams;
    }
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
    public List<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    
}
