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
public class DBUtilities {
    //the following three variables are required for connecting a database, designed for the project manager
    final String DBURL = "jdbc:derby://localhost:1527/ProjectManDB";
    final String user = "psadmin"; //should be set in a constructor in case of several users, same for pass
    final String password = "TOP2018"; 
    final String[] employeeColumns = {"NAME", "LASTNAME", "ID", "PASSWORD", "HOURLYRATE", "DAILYHOURS", "WORKEDHOURS", "USERMODE"};
    
    Boolean active = true; //set false if you don't want database utilities, if let's say they don't work
    
    //Selects all values from the table STAFF from scheme APP and retunrs them in a Result Set
    ResultSet selectStaff() throws SQLException {
        try (
            Connection conn = DriverManager.getConnection(DBURL, user, password);
            Statement stmt = conn.createStatement();
        ) {
            String strSelect = "SELECT id, name, lastname, password, hourlyrate, dailyhours, usermode from APP.STAFF";
            return stmt.executeQuery(strSelect);
        }
    }
    //add employee to the database
    void addEmployee (Employee newCommer) throws SQLException {
        try (
            Connection conn = DriverManager.getConnection(DBURL, user, password);
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
