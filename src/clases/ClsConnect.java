/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Samuel David Ortiz
 */
public class ClsConnect {
    
    private String MySQLURL = "jdbc:mysql://localhost:3306/";
    private String dataBase;
    private String userName;
    private String userPassword;
    public Connection con = null;
    public Statement st;
    
    public void conexion(String DB, String user, String Password){
        this.setDataBase(DB);
        this.setUserName(user);
        this.setUserPassword(Password);
               
        try {
         con = DriverManager.getConnection(MySQLURL+DB, user, Password);
         if (con != null) {
            System.out.println("Database connection is successful !!!!");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
    }
    
    public ResultSet select(String query){
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ClsConnect.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ex = " + ex);
        }
        return rs;
    }

    public void insert(String query){
        try {
            st = con.createStatement();
            st.executeUpdate(query);
            System.out.println("Record is inserted in the table successfully");
        } catch (SQLException ex) {
            Logger.getLogger(ClsConnect.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void procedimiento(String query){
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(ClsConnect.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void close(){
        try {
            st.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClsConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    
}
