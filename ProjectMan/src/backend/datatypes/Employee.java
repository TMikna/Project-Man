package backend.datatypes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
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
public class Employee implements Serializable {
    public enum AccessRights
    {
        NO_ACCESS, EMPLOYEE, TEAM_MANAGER, PROJECT_MANAGER, COMPANY_MANAGER, ADMIN
    }
    
    private static final int FIVE = 5; // Can't find better name
    private String Name;
    private String LastName;
    private UUID ID;                  // Unique!
    private String password;            // I think it's OK here, we dont't need much safety now
    private String position;            // Or Occupation
    private double hourlyRate;          // Money, earned per hour
    private double dailyHours;          // Average working time every day. //TODO later might change in custom every day input
    private double workedHours = 0; 
    private AccessRights privileges;
    private String email;
    private String phoneNumber;
    
    // @Auth Manfr. Kintamieji skirti lentelei
    //@FXML
    //private CheckBox MemberCB; 
    //private SimpleStringProperty HOnThisTeam; 
    
    
    private List<Team> personalTeams = new ArrayList();
    private List<Double> workHoursInTeams = new ArrayList();
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
                    String email,
                    String phoneNumber,
                    AccessRights privileges
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
        //this.MemberCB = new CheckBox();
        //this.HOnThisTeam = new SimpleStringProperty("");
    }
    
    @Override
    public String toString()
    {
        return "Name: " + Name + "; surname: " + LastName + "; ID: " + ID + "; password: " + password + "; position: " + position + 
                "; hourly: " + hourlyRate + "; hrs/day: " + dailyHours + "email: " + getEmail() + "phoneNumber: " + getPhoneNumber() +
                "; worked: " + workedHours + "; privileges: " + privileges + ".";
    }
    //Following Auxilary functions were written by Vilius.
    //concatenates object's fields' values into a string so that it can be added to a database
    public String toUpdateString()
    {
        return "(" + format(Name) + format(LastName) + format(ID) + format(password) + format(hourlyRate) + format(getEmail()) + format(getPhoneNumber()) + format(workedHours) + privileges + ");";
    }
    //format fields for easier Update query String formation
    public String format(String value)
    {
        return "'" + value + "', ";
    }
    public String format(Double value)
    {
        return Double.toString(value) + ", ";
    }
    //UUID will be stored as a string
    public String format(UUID value) 
    {
        return "'" + value + "', ";
    }
//================================================================================
// Accessors                                                   @author Tomas.Mikna   
//================================================================================


    public void addpersonalTeams(Team e)
    {
        /*this.getPersonalTeams().add(e);
        try{
            workHoursInTeams.add(Double.parseDouble(getHOnThisTeam()));
        }
        catch(NumberFormatException c){
            workHoursInTeams.add(0d);
        }*/
    }

    
    /*public void setHOnthisTeam(String HOnThisTeam)
    {
        this.HOnThisTeam.set(HOnThisTeam);
    }
    
    public String getHOnThisTeam()
    {
        return HOnThisTeam.get();
    }
    */
    
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
  
    public AccessRights getPrivileges()
    {
        return privileges;
    }
    
    public void setPrivileges(AccessRights privileges)
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
