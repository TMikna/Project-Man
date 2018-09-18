package backend.datatypes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import backend.datatypes.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manfr
 */
public class Team extends ArrayList<Employee>
{
    private String teamName;
    private int manpower;
    private Project project;
    
    public Team(String teamName, List<Employee> employeeList)
    {
        super(employeeList);
        this.teamName = teamName;
    }
            
    public Team(String teamName, List<Employee> employeeList, Project project)
    {
        super(employeeList);
        this.teamName = teamName;
        this.project = project;
    }
    
    public String getTeamName()
    {
        return teamName;
    }
    
    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }
    
    public int getManpower()
    {
        return manpower;
    }
    
    public void setManpower(int manpower)
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
        return "Komanda: " + teamName + " " + super.toString();
    }
}
