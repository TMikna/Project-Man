package backend.datatypes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 *
 * @author manfr
 */
public class Employee {
    public static final int     //for convenient privilege checking (I.E. if (currentUserRights >= Employee.PROJECT_MANAGER) doSomethingThatOnlyUsersWithHigherPrivilegesCanDo();)
        ADMIN = Integer.MAX_VALUE,
        COMPANY_MANAGER = 4,
        PROJECT_MANAGER = 3,
        TEAM_MANAGER = 2,   //TODO: probably not right
        EMPLOYEE = 1,
        NO_ACCESS = Integer.MIN_VALUE;
    
    private static final int FIVE = 5; // Can't find better name
    
    private String Name;
    private String LastName;
    private UUID ID;                  // Unique!
    private String password;            // I think it's OK here, we dont't need much safety now
    private String position;            // Or Occupation
    private double hourlyRate;          // Money, earned per hour
    private double dailyHours;          // Average working time every day. //TODO later might change in custom every day input
    private double workedHours = 0; 
    private int privileges;
    
    
    // @Auth Manfr. Kintamieji skirti lentelei
    @FXML
    private CheckBox MemberCB; 
    private SimpleStringProperty hoursOnThisTeam; 
    
    
    private List<String> personalTeams = new ArrayList();
    private List<String> workHoursInTeams = new ArrayList();
    //TODO find best data type
    private String[] Teams = new String[FIVE];
    //TODO implement and use this if will be spare time in later steps
    private double workedHoursThisMonth = 0;

    public Employee(String name,
                    String lastName,
                    UUID id,
                    String password,
                    String position,
                    double hourlyRate,
                    double dailyHours,
                    int privileges)
    {
        this.Name = name;
        this.LastName = lastName;
        this.ID = id;
        this.password = password;
        this.position = position;
        this.hourlyRate = hourlyRate;
        this.dailyHours = dailyHours;
        this.privileges = privileges;
        this.MemberCB = new CheckBox();
        this.hoursOnThisTeam = new SimpleStringProperty();
    }
    
    @Override
    public String toString()
    {
        return "Name: " + Name + "; surname: " + LastName + "; ID: " + ID + "; password: " + password + "; position: " + position + "; hourly: " + hourlyRate + "; hrs/day: " + dailyHours + "; worked: " + workedHours + "; privileges: " + privileges + ".";
    }
    
//================================================================================
// Accessors                                                   @author Tomas.Mikna   
//================================================================================

    public void addpersonalTeams(Team e)
    {
        this.personalTeams.add(e);
        try{
            workHoursInTeams.add(Double.parseDouble(gethoursOnThisTeam()));
        }
        catch(NumberFormatException c){
            workHoursInTeams.add(0d);
        }
    }
    
    public void sethoursOnthisTeam(String hoursOnThisTeam)
    {
        this.hoursOnThisTeam.set(hoursOnThisTeam);
    }
    
    public String gethoursOnThisTeam()
    {
        return hoursOnThisTeam.toString();
    }
    
    
    public String getName() 
    {
        return Name;
    }

    public void setName(String Name) 
    {
        this.Name = Name;
    }

    public String getLastName() 
    {
        return LastName;
    }

    public void setLastName(String LastName) 
    {
        this.LastName = LastName;
    }

    public UUID getID()
    {
        return ID;
    }

    public void setID(UUID ID)
    {
        this.ID = ID;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPosition() 
    {
        return position;
    }

    public void setPosition(String possition) 
    {
        this.position = possition;
    }

    public double getHourlyRate() 
    {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) 
    {
        this.hourlyRate = hourlyRate;
    }

    public double getDailyHours() 
    {
        return dailyHours;
    }

    public void setDailyHours(double dailyHours) 
    {
        this.dailyHours = dailyHours;
    }

    public double getWorkedHours() 
    {
        return workedHours;
    }

    public void setWorkedHours(double workedHours) 
    {
        this.workedHours = workedHours;
    }

    public String[] getTeams() 
    {
        return Teams;
    }

    public void setTeams(String[] Teams) 
    {
        this.Teams = Teams;
    }

    public double getWorkedHoursThisMonth() 
    {
        return workedHoursThisMonth;
    }

    public void setWorkedHoursThisMonth(double workedHoursThisMonth) 
    {
        this.workedHoursThisMonth = workedHoursThisMonth;
    }
  
    public int getPrivileges()
    {
        return privileges;
    }
    
    public void setPrivileges(int privileges)
    {
        this.privileges = privileges;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(Name, employee.Name) && Objects.equals(LastName, employee.LastName) && Objects.equals(ID, employee.ID);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(Name, LastName, ID);
    }
}
