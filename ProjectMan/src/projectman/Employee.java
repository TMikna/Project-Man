/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectman;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author manfr
 */
public class Employee {
    
    private static final int FIVE = 5; // Can't find better name
    
    private SimpleStringProperty Name;       //Pakeiciau duomenu tipa, kad butu lengviau padaryt edittable lentele
    private SimpleStringProperty LastName;   // @manfr
    private String ID;                  // Unique!
    private String password;            // I think it's OK here, we dont't need much safety now
    private String position;            // Or Occupation
    private double hourlyRate;          // Money, earned per hour
    private double dailyHours;          // Average working time every day. //TODO later might change in custom every day input
    private double workedHours = 0; 
    //TODO find best data type
    private String[] Teams = new String[FIVE];
    //TODO implement and use this if will be spare time in later steps
    private double workedHoursThisMonth = 0;

    
//================================================================================
// Accessors                                                   @author Tomas.Mikna   
//================================================================================
    public Employee(String ID, String LastName, String Name)
    {
        this.ID = ID;
        this.Name = new SimpleStringProperty(Name);
        this.LastName = new SimpleStringProperty(LastName);
    }
    
    public String getName() 
    {
        return Name.get();
    }

    public void setName(String Name) 
    {
        this.Name.set(Name);
    }

    public String getLastName() 
    {
        return LastName.get();
    }

    public void setLastName(String LastName) 
    {
        this.LastName.set(LastName);
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
