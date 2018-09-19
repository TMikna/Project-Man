/*
 * A class to handle all database related matters.
 * To work, instructions on how to set up javaDB must be followed.
 * If working awfully, 
 */
package backend.server;

import backend.datatypes.Employee;
import backend.datatypes.SimpleEmployee;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
        this.url = "jdbc:derby://localhost:1527/ProjectManDB";
        this.user = "psadmin";
        this.password = "TOP2018";
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
 
    public void getAllEmployees() throws SQLException { // this uses DataStatic
            String name, surname, password, occupation, privileges, email, phonenumber;
            UUID id;
            double hourlyrate, dailyhours, workedhours;
            
            statement = connection.createStatement();
            //String strSelect = "select * from projectman.employees";
            results = statement.executeQuery("select * from APP.Staff");
            
            DataStatic.getEmployees().clear();
            
            while(results.next())
            {
                name = results.getString("name");
                surname = results.getString("surname");
                //id = UUID.fromString(results.getString("UUID")); //TODO fix this
                id = UUID.randomUUID();
                password = results.getString("password");
                occupation = results.getString("occupation");
                hourlyrate = results.getDouble("hourlyrate");
                dailyhours = results.getDouble("dailyhours");
                workedhours = results.getDouble("workedhours");
                email = results.getString("email");
                phonenumber = results.getString("phonenumber");
                
                
                //privileges = results.getString("privileges");
                privileges = "ADMIN";
                DataStatic.getEmployees().add(new Employee(name, surname, id, password, occupation, hourlyrate, dailyhours, email, phonenumber, Employee.AccessRights.valueOf(privileges)));
            }
    }
    //experimental add using serialization
    //Used to but doesn't work anymore???
    public void addObject (Employee newCommer) throws SQLException, FileNotFoundException, IOException 
    {
//        Connection con2 = null;
//        if (connection == null) System.out.println("NOT CONNECTING");
        connection = DriverManager.getConnection(url, user, password);
        File file = new File("students.txt");
        file.deleteOnExit();
        FileOutputStream fo = new FileOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(fo);
        SimpleEmployee semp = new SimpleEmployee(newCommer);
        output.writeObject(semp);
        output.close();
        fo.close();
          
        String strUpdate = "INSERT INTO APP.Objects (ID, Binary) VALUES " + "(?, ?)";
        PreparedStatement stmt = connection.prepareStatement(strUpdate);
        stmt.setInt(1, DataStatic.getEmployees().size() + 1);
        FileInputStream fi = new FileInputStream(file);
        stmt.setBlob(2, fi);
        System.out.println("Tried to add employee: ");
        System.out.println(newCommer);
        stmt.execute();
        connection.commit();
        fi.close();
        connection.close();
    }
    
    public void listEmployees () throws SQLException, IOException, ClassNotFoundException {
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM APP.Objects");
        Employee emp;
        System.out.println("Really trying to show you what we got");
        SimpleEmployee employee;
        while (results.next()) {
            Blob blob = results.getBlob(2);
            ObjectInputStream input = new ObjectInputStream(blob.getBinaryStream());
            employee = (SimpleEmployee) input.readObject();
            //System.out.println(employee);
            System.out.println(new backend.datatypes.Employee(employee));
            input.close();
        }
        connection.close();
        
    }
    //add employee to the database
    public void addEmployee (Employee newCommer) throws SQLException 
    {
            statement = connection.createStatement();
            String strUpdate = "INSERT INTO projectman.employees VALUES " + newCommer.toUpdateString();
            if (statement.executeUpdate(strUpdate) == 0) System.out.println("Employee was not added");
    }
    /**
     * prints rows from a result set, until all or a specified amount is printed, 
     * @param rset - source,
     * @param names - array of Strings that relate to column names,
     * @param toPrint - boolean array which indicates which rows to print,
     * @param rows - integer, how many rows to print, negative means all.
     * @throws SQLException 
     */ 
    public void printResultSet(ResultSet rset, String[] names, Boolean[] toPrint, int rows) throws SQLException
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
