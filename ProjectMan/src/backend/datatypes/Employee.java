package backend.datatypes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author manfr
 */
public class Employee {
    
    private static final int FIVE = 5; // Can't find better name
    
    private String Name;
    private String LastName;
    private String ID;                  // Unique!
    private String password;            // I think it's OK here, we dont't need much safety now
    private String position;            // Or Occupation
    private double hourlyRate;          // Money, earned per hour
    private double dailyHours;          // Average working time every day. //TODO later might change in custom every day input
    private double workedHours = 0; 
    private String privileges;
    
    //TODO find best data type
    private String[] Teams = new String[FIVE];
    //TODO implement and use this if will be spare time in later steps
    private double workedHoursThisMonth = 0;

    public Employee(String name,
                    String lastName,
                    String id,
                    String password,
                    String position,
                    double hourlyRate,
                    double dailyHours,
                    String privileges)
    {
        this.Name = name;
        this.LastName = lastName;
        this.ID = id;
        this.password = password;
        this.position = position;
        this.hourlyRate = hourlyRate;
        this.dailyHours = dailyHours;
        this.privileges = privileges;
    }
    
    @Override
    public String toString()
    {
        return "Name: " + Name + "; surname: " + LastName + "; ID: " + ID + "; password: " + password + "; position: " + position + "; hourly: " + hourlyRate + "; hrs/day: " + dailyHours + "; worked: " + workedHours + "; privileges: " + privileges + ".";
    }
    
//================================================================================
// Accessors                                                   @author Tomas.Mikna   
//================================================================================

    
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

    public String getID() 
    {
        return ID;
    }

    public void setID(String ID) 
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
}
