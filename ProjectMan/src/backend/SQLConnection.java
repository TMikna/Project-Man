/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;
import java.sql.*;
import java.util.Properties;

/**
 * @author teo
 */
public class SQLConnection {
     
     private static SQLConnection instance;
     
     private SQLConnection(String url, String user, String password) throws SQLException
     {
          Properties properties = new Properties();
          properties.setProperty("user", user);
          properties.setProperty("password", password); // TODO: replace plaintext password
          properties.setProperty("ssl", "true");
          Connection connection = DriverManager.getConnection(url, properties);
     }
     
     public static SQLConnection getInstance(String url, String user, String password) throws SQLException
     {
          if(instance == null)
               instance = new SQLConnection(url, user, password);
          return instance;     
     }
     
}
