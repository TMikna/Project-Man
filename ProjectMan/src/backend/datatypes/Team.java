package backend.datatypes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author manfr
 */
public class Team extends ArrayList<MutablePair<Employee, Double>>  //now using pair because each employee needs to have separate work hours on each team
{
    private String teamName;
    private double manpower;
    private Project project;
    
    public Team(String teamName, List<Employee> employeeList)
    {
        super(employeeList.stream().map(employee -> new MutablePair<>(employee, 0.0)).collect(Collectors.toList()));
        this.teamName = teamName;
    }
            
    public Team(String teamName, List<Employee> employeeList, Project project)
    {
        super(employeeList.stream().map(employee -> new MutablePair<>(employee, 0.0)).collect(Collectors.toList()));
        this.teamName = teamName;
        this.project = project;
    }
    
    public List<Employee> getEmployeeList()
    {
        return stream().map(MutablePair::getKey).collect(Collectors.toList());
    }
    
    public String getTeamName()
    {
        return teamName;
    }
    
    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }
    
    public double getManpower()
    {
        return manpower;
    }
    
    public void setManpower(double manpower)
    {
        this.manpower = manpower;
    }
    
    public Project getProject()
    {
        return project;
    }
    
    public void setProject(Project project)
    {
        this.project = project;
    }
    
    @Override
    public String toString()
    {
        return "Komanda: " + teamName;
    }
}
