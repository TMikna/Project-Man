package backend.datatypes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author manfr
 */
public class Employee {
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
    
    private EmployeeTimes times = new EmployeeTimes();  //TODO: load this from DB for logged in user on login
    private Color optionalPersonal, optionalTeam, optionalProject, optionalCompany, optionalOther, mandatoryPersonal, mandatoryTeam, mandatoryProject, mandatoryCompany, mandatoryOther;
    
    // @Auth Manfr. Kintamasis skirtas lentelei
    private SimpleStringProperty HOnThisTeam; 
    
    
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
        this.HOnThisTeam = new SimpleStringProperty("0");
    
        optionalPersonal = new Color(0, 1, 0.75, 1);
        optionalTeam = new Color(0, 1, 0, 1);
        optionalProject = new Color(0.25, 0.5, 0, 1);
        optionalCompany = new Color(0, 0.375, 0, 1);
        optionalOther = new Color(0, 0, 1, 1);
        mandatoryPersonal = new Color(1, 1, 0, 1);
        mandatoryTeam = new Color(1, 0.5, 0, 1);
        mandatoryProject = new Color(1, 0, 0, 1);
        mandatoryCompany = new Color(0.5, 0, 0, 1);
        mandatoryOther = new Color(0.5, 0, 0.5, 1);
    }
    
    /*@author VM
        A constructor, which takes a serializable version of employee as an argument.
        Useful in case we will need to store serialized employees.
    */
    public Employee (SimpleEmployee emp)
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
    //Following Auxilary functions were written by Vilius.
    //concatenates object's fields' values into a string so that it can be added to a database
    public String toUpdateString()
    {
        return "(" + format(Name) +
                format(LastName) +
                format(getEmail()) +
                format(getPhoneNumber()) +
                format(ID) +
                format(password) +
                format(getPosition()) +
                format(hourlyRate) +
                format(dailyHours) +
                format(workedHours)     + "'" +
                privileges.toString()   + "'" +
                ");";
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
        this.getPersonalTeams().add(e);
        try{
            workHoursInTeams.add(Double.parseDouble(getHOnThisTeam()));
            if(getHOnThisTeam().toString().isEmpty())
            {
                workHoursInTeams.add(0d);
            }
        }
        catch(NumberFormatException c){
            workHoursInTeams.add(0d);
        }
    }

    
    public void setHOnthisTeam(String HOnThisTeam)
    {
        this.HOnThisTeam.set(HOnThisTeam);
    }
    
    public void setworkHoursInTeams(List<Double> workHoursInTeams)
    {
        this.workHoursInTeams = workHoursInTeams;
    }
    
    public List<Double> getworkHoursInTeams()
    {
        return workHoursInTeams;
    }
    
    public String getHOnThisTeam()
    {
        return HOnThisTeam.get();
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
    
    public EmployeeTimes getTimes()
    {
        return times;
    }
    
    public void setTimes(EmployeeTimes times)
    {
        this.times = times;
    }
    
    public Color getOptionalPersonal()
    {
        return optionalPersonal;
    }
    
    public void setOptionalPersonal(Color optionalPersonal)
    {
        this.optionalPersonal = optionalPersonal;
    }
    
    public Color getOptionalTeam()
    {
        return optionalTeam;
    }
    
    public void setOptionalTeam(Color optionalTeam)
    {
        this.optionalTeam = optionalTeam;
    }
    
    public Color getOptionalProject()
    {
        return optionalProject;
    }
    
    public void setOptionalProject(Color optionalProject)
    {
        this.optionalProject = optionalProject;
    }
    
    public Color getOptionalCompany()
    {
        return optionalCompany;
    }
    
    public void setOptionalCompany(Color optionalCompany)
    {
        this.optionalCompany = optionalCompany;
    }
    
    public Color getOptionalOther()
    {
        return optionalOther;
    }
    
    public void setOptionalOther(Color optionalOther)
    {
        this.optionalOther = optionalOther;
    }
    
    public Color getMandatoryPersonal()
    {
        return mandatoryPersonal;
    }
    
    public void setMandatoryPersonal(Color mandatoryPersonal)
    {
        this.mandatoryPersonal = mandatoryPersonal;
    }
    
    public Color getMandatoryTeam()
    {
        return mandatoryTeam;
    }
    
    public void setMandatoryTeam(Color mandatoryTeam)
    {
        this.mandatoryTeam = mandatoryTeam;
    }
    
    public Color getMandatoryProject()
    {
        return mandatoryProject;
    }
    
    public void setMandatoryProject(Color mandatoryProject)
    {
        this.mandatoryProject = mandatoryProject;
    }
    
    public Color getMandatoryCompany()
    {
        return mandatoryCompany;
    }
    
    public void setMandatoryCompany(Color mandatoryCompany)
    {
        this.mandatoryCompany = mandatoryCompany;
    }
    
    public Color getMandatoryOther()
    {
        return mandatoryOther;
    }
    
    public void setMandatoryOther(Color mandatoryOther)
    {
        this.mandatoryOther = mandatoryOther;
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
