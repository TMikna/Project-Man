/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This is a serializable version of Employee class, that can be used to store data of non serializable type
 * Employee objects will effectively be serializable as they can be constructed from simpleEmployee objects.
 * @author vilius
 */
public class SimpleEmployee implements Serializable{
    private String Name;
    private String LastName;
    private UUID ID;                  // Unique!
    private String password;            // I think it's OK here, we dont't need much safety now
    private String position;            // Or Occupation
    private double hourlyRate;          // Money, earned per hour
    private double dailyHours;          // Average working time every day. //TODO later might change in custom every day input
    private double workedHours = 0; 
    private Employee.AccessRights privileges;
    private String email;
    private String phoneNumber;
    
     //TODO find best data type
    private String[] Teams = new String[5];
    //TODO implement and use this if will be spare time in later steps
    private double workedHoursThisMonth = 0;

    private List<Team> personalTeams = new ArrayList();
    private List<Double> workHoursInTeams = new ArrayList();
    //Constructor analogous to the Employee's constructor
    public SimpleEmployee(String name,
                    String lastName,
                    UUID id,
                    String password,
                    String position,
                    double hourlyRate,
                    double dailyHours,
                    String email,
                    String phoneNumber,
                    Employee.AccessRights privileges
                    )
    {
        this.Name = name;
        this.LastName = lastName;
        this.ID = id;
        this.password = password;
        this.position = position;
        this.hourlyRate = hourlyRate;
        this.dailyHours = dailyHours;
        this.privileges = privileges;
        this.email = email;
        this.phoneNumber = phoneNumber;
        
        //Following 2 are not serializable. Might be if we create classes that extend them and implement serializable.
        //this.MemberCB = new CheckBox(); 
        //this.HOnThisTeam = new SimpleStringProperty("");
    }
    
    //Constructor, which is based on the object that is to be serialized
    public SimpleEmployee (Employee emp)
    {
        this.Name = emp.getName();
        this.LastName = emp.getLastName();
        this.ID = emp.getID();
        this.password = emp.getPassword();
        this.position = emp.getPassword();
        this.hourlyRate = emp.getHourlyRate();
        this.dailyHours = emp.getDailyHours();
        this.privileges = emp.getPrivileges();
        this.email = emp.getEmail();
        this.phoneNumber = emp.getPhoneNumber();
    }
    @Override
    public String toString()
    {
        return "Name: " + Name + "; surname: " + LastName + "; ID: " + ID + "; password: " + password + "; position: " + position + 
                "; hourly: " + hourlyRate + "; hrs/day: " + dailyHours + "email: " + getEmail() + "phoneNumber: " + getPhoneNumber() +
                "; worked: " + workedHours + "; privileges: " + privileges + ".";
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
  
    public Employee.AccessRights getPrivileges()
    {
        return privileges;
    }
    
    public void setPrivileges(Employee.AccessRights privileges)
    {
        this.privileges = privileges;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Team> getPersonalTeams() {
        return personalTeams;
    }

    public void setPersonalTeams(List<Team> personalTeams) {
        this.personalTeams = personalTeams;
    }
    
}
