/*
 * A class to handle all database related matters.
 * To work, instructions on how to set up javaDB must be followed.
 * If working awfully, 
 */
package backend.server;

import backend.datatypes.Employee;
import backend.datatypes.Employee.AccessRights;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;

/**
 *
 * @author vilius
 * to do:
 * create new tables:
 * 1)projects;
 * 2)project handlers;
 * Change columns:
 * 1) select correct types: user mode should be Int, 
 * 2) select suitable lengt for varchar types: ID is UUID, but stored as a String will require 40 char space
 * Decide on whether to make many unique selects, inserts, updates and deletes or make generic ones
 */

enum Column {
    ZERO, NAME, SURNAME, UUID, PASSWORD, OCCUPATION, HOURLYRATE, DAILYHOURS, WORKEDHOURS, EMAIL, PHONENUMBER, PRIVILEGES, END
}
public class DBUtilities {
    //the following three variables are required for connecting a database, designed for the project manager
    private String url;
    private String user;
    private String password; 
    private String[] employeeColumns = {"NAME", "LASTNAME", "UUID", "PASSWORD", "HOURLYRATE", "DAILYHOURS", "WORKEDHOURS", "EMAIL", "PHONENUMBER", "USERMODE"};
    private Properties properties;
    private Statement statement;
    private ResultSet results;

    
    private static Connection connection = null;
    private static DBUtilities dbutil = null;
    
    public static DBUtilities getInstance()
    {
        if(dbutil == null)
            dbutil = new DBUtilities();
        return dbutil;
    }
    
    public static DBUtilities getInstance(String url, String user, String password)
    {
        if(dbutil == null)
            dbutil = new DBUtilities(url, user, password);
        return dbutil;
    }

    
    Boolean active = true; //set false if you don't want database utilities, if let's say they don't work
    
    private DBUtilities() //hardcoded argmenents, they probably will stay as they are
    {
        this.url = "jdbc:mysql://localhost/projectman?"
                + "useUnicode=true&"
                + "useJDBCCompliantTimezoneShift=true&"
                + "useLegacyDatetimeCode=false&"
                + "serverTimezone=UTC";
        this.user = "projectman";
        this.password = "projectman";
        properties = new Properties();
        
        properties.put("url", this.url);
        properties.put("user", this.user);
        properties.put("password", this.password);
    }
    
    private DBUtilities(String url, String user, String password)
    {
        properties.put("url", url);
        properties.put("user", user);
        properties.put("password", password);
 
    }
    
    public void connect() throws SQLException
    {
        connection = DriverManager.getConnection(url, user, password);
    }
    public void connect(String DBURL, String user, String password) throws SQLException
    {
        connection = DriverManager.getConnection(DBURL, user, password);
    }
    
    public void disconnect() throws SQLException
    {
        if(results != null)
            results.close();
        if (statement != null)
            statement.close();
        if(connection != null)
            connection.close();
        dbutil = null;
    }
 
    public void getAllEmployees() throws SQLException { // this uses DataStatic, also clears out the list before writing to it
            String name, surname, password, occupation, privileges, email, phonenumber;
            UUID id;
            double hourlyrate, dailyhours, workedhours;
            
            try {
            statement = connection.createStatement();
            //String strSelect = "select * from projectman.employees";
            results = statement.executeQuery("select * from projectman.employees");
            DataStatic.getEmployees().clear();
            
            while(results.next())
            {
                name = results.getString("name");
                surname = results.getString("surname");
                id = UUID.fromString(results.getString("UUID"));
                password = results.getString("password");
                occupation = results.getString("occupation");
                hourlyrate = results.getDouble("hourlyrate");
                dailyhours = results.getDouble("dailyhours");
                workedhours = results.getDouble("workedhours");
                privileges = results.getString("privileges");
                email = results.getString("email");
                phonenumber = results.getString("phonenumber");
                DataStatic.getEmployees().add(new Employee(
                        name,
                        surname,
                        id,
                        password,
                        occupation,
                        hourlyrate,
                        dailyhours,
                        email,
                        phonenumber,
                        Employee.AccessRights.valueOf(privileges)
                ));
            }
            } finally {
                statement.close();
            }
    }
    //add employee to the database and DataStatic class
    public void addEmployee (Employee newCommer) throws SQLException {
            DataStatic.getEmployees().add(newCommer);
            try {
                statement = connection.createStatement();
                String strUpdate = "INSERT INTO projectman.employees VALUES " + newCommer.toUpdateString();
                if (statement.executeUpdate(strUpdate) == 0) System.out.println("Employee was not added");
            } finally {
                statement.close();
            }
    }
    
    public void changeStringProperty(String propertyName, String newProperty, Employee employee) throws SQLException {
        
        switch(propertyName.toLowerCase()) // tried to implement this with an enum, but the compiler doesnt allow it (constant string expresion required)
        {
            case "name":
            {
                employee.setName(newProperty);
            }
            break;
            
            case "surname":
            {
                employee.setLastName(newProperty);
            }
            break;
            
            case "email":
            {
                employee.setEmail(newProperty);
            }
            break;
            
            case "phonenumber":
            {
                employee.setPhoneNumber(newProperty);
            }
            break;
            
            case "password":
            {
                employee.setPassword(newProperty);
            }
            break;
            
            case "occupation":
            {
                employee.setPosition(newProperty);
            }
            break;
   
            case "privileges":
            {
                employee.setPrivileges(AccessRights.valueOf(newProperty));
            }
            break;
        }
        try {
            statement = connection.createStatement();
            String strUpdate = "update employees set " + propertyName + " = '" + newProperty + "' where uuid = '" + employee.getID().toString() + "';";
            if (statement.executeUpdate(strUpdate) == 0) System.out.println("Data field not updated");
        } finally {
            statement.close();
        }
    }
    
    public void changeDoubleProperty(String propertyName, Double newProperty, Employee employee) throws SQLException {
        switch(propertyName.toLowerCase())
        {
            case "workedhours":
            {
                employee.setWorkedHours(newProperty);
            }
            break;
            
            case "hourlyrate":
            {
                employee.setHourlyRate(newProperty);
            }
            break;
            
            case "dailyhours":
            {
                employee.setDailyHours(newProperty);
            }
            break;
        }
        try {
            statement = connection.createStatement();
            String strUpdate = "update employees set " + propertyName + " = " + newProperty.toString() + " where uuid = '" + employee.getID().toString() + "';";
            if (statement.executeUpdate(strUpdate) == 0) System.out.println("Data field not updated");
        } finally {
            statement.close();
        }
    }
    
    public void removeEmployee(Employee employee) throws SQLException {
        DataStatic.getEmployees().remove(employee);
        try {
            statement = connection.createStatement();
            String strUpdate = "delete from 'employees' where uuid = '" + employee.getID().toString() + "';";
            if (statement.executeUpdate(strUpdate) == 0) System.out.println("Data field not updated");
        } finally {
            statement.close();
        }
 
    }

   
    
    /**
     * prints rows from a result set, until all or a specified amount is printed, 
     * @param rset - source,
     * @param names - array of Strings that relate to column names,
     * @param toPrint - boolean array which indicates which rows to print,
     * @param rows - integer, how many rows to print, negative means all.
     * @throws SQLException 
     */ 
    void printResultSet(ResultSet rset, String[] names, Boolean[] toPrint, int rows) throws SQLException
    {
        System.out.println("The records selected are:");
         int rowCount = 0;
         String result;
         while(rset.next() && rows != 0) {   // Move the cursor to the next row, return false if no more row
            result = "";
            for(int i = 0; i < names.length; i++) {
                if (toPrint[i]) result += rset.getString(names[i]) + ", ";
            }
            System.out.println(result);
            ++rowCount;
            rows--;
         }
         //System.out.println("Total number of records = " + rowCount);
    }
}
