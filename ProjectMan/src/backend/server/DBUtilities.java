/*
 * A class to handle all database related matters.
 * To work, instructions on how to set up javaDB must be followed.
 * If working awfully, 
 */
package backend.server;

import backend.datatypes.Employee;
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
    ZERO, NAME, SURNAME, UUID, PASSWORD, OCCUPATION, HOURLYRATE, DAILYHOURS, WORKEDHOURS, PRIVILEGES
}
public class DBUtilities {
    //the following three variables are required for connecting a database, designed for the project manager
    private String url;
    private String user;
    private String password; 
    private String[] employeeColumns = {"NAME", "LASTNAME", "ID", "PASSWORD", "HOURLYRATE", "DAILYHOURS", "WORKEDHOURS", "USERMODE"};
    private Properties properties;
    private Statement statement;
    private ResultSet results;

    
    private Connection connection = null;
    
    Boolean active = true; //set false if you don't want database utilities, if let's say they don't work
    
    private DBUtilities() //hardcoded argmenents, they probably will stay as they are
    {
        this.url = "jdbc:mysql://localhost:1527/projectman";
        this.user = "user";
        this.password = "pass";
        
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
    }
 
    public Employee getAllEmployees() throws SQLException {       
            String name, surname, password, occupation;
            UUID id;
            double hourlyrate, dailyhours, workedhours;
            int privileges;
            
            statement = connection.createStatement();
            //String strSelect = "select * from projectman.employees";
            results = statement.executeQuery("select * from projectman.employees");
            
            while(results.next())
            {
                
            }
            
            
            
            return null;
    }
    //add employee to the database
    void addEmployee (Employee newCommer) throws SQLException {
        try (
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
        ) {
            String strUpdate = "INSERT INTO APP.STAFF VALUES " + newCommer.toUpdateString();
            if (stmt.executeUpdate(strUpdate) == 0) System.out.println("Employee was not added");
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
