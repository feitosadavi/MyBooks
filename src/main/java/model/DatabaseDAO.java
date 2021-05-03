package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseDAO {
    public final String URL = "jdbc:mysql://localhost:3666/mybooksdb";
    public final String USER = "root";
    public final String PASSWORD = "";
    public Connection conn;
    
    public DatabaseDAO() throws Exception {
       try {
          Class.forName("com.mysql.cj.jdbc.Driver");
       } catch (ClassNotFoundException ex) {
           System.out.println("Connector Not Loaded");
       }
    }
    
    public void connect() throws Exception {
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public void disconnect() throws Exception {
        conn.close();
    }
}
