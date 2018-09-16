package backend.datatypes;

import java.util.ArrayList;
import java.util.List;

public class Project extends ArrayList<Team>
{
    private String projectName;
    
    public Project(String projectName, List<Team> teamList)
    {
        super(teamList);
        this.projectName = projectName;
    }
    
    @Override
    public String toString()
    {
        return "Projektas: " + projectName + super.toString();
    }
    
    public String getProjectName()
    {
        return projectName;
    }
    
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }
}
