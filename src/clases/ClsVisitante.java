/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import Clases.ClsConnect;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Samuel
 */
public class ClsVisitante {

    private String query;
    private boolean flag = false;
    
    private JComboBox cmbTipoGestion = null;
    
    ClsConnect cn = new ClsConnect();

    public void nuevoVisitante(String nombre1, String nombre2, String apellido1, String apellido2, String tipoDoc, String noDoc,
            String nacionalidad, String mail, String tel, String foto) {

        try {
            cn.conexion("ProyectoFinal", "umg", "1234");

            CallableStatement cStmt = cn.con.prepareCall("{CALL sp_visitante(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            cStmt.setString(1, nombre1);
            if (nombre2.equals("")) {
                cStmt.setNull(2, java.sql.Types.VARCHAR);
            } else {
                cStmt.setString(2, nombre2);
            }
            cStmt.setString(3, apellido1);
            if (apellido2.equals("")) {
                cStmt.setNull(4, java.sql.Types.VARCHAR);
            } else {
                cStmt.setString(4, apellido2);
            }
            cStmt.setString(5, tipoDoc);
            cStmt.setString(6, noDoc);
            cStmt.setString(7, nacionalidad);
            cStmt.setString(8, mail);
            cStmt.setString(9, tel);
            if (foto.equals("")) {
                cStmt.setNull(10, java.sql.Types.VARCHAR);
            } else {
                cStmt.setString(10, foto);
            }
            cStmt.setInt(11, 1);

            cStmt.registerOutParameter("msg", Types.VARCHAR);
            cStmt.registerOutParameter(13, Types.INTEGER);
            
            cStmt.execute();

            String outputValue = cStmt.getString("msg");
            int outputValue1 = cStmt.getInt(13);
            JOptionPane.showMessageDialog(null, outputValue);

            if (outputValue1 == 1) {
                this.setFlag(true);
            }

            //cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClsVisitante.class.getName()).log(Level.SEVERE, null, ex);
            //cn.close();
        }

    }

    public String mensajeCombo(){
        cmbTipoGestion = new JComboBox();
        
        Object[] options = {"Genearar", "Cancelar"};
        
        String query = null;
        int opc = 0;

        try {
            cn = new ClsConnect();
            cn.conexion("proyectofinal", "umg", "1234");

            query = "SELECT tipo FROM tipo_gestion;";

            ResultSet rs = cn.select(query);

            cmbTipoGestion.removeAllItems();

            while (rs.next()) {
                cmbTipoGestion.addItem(rs.getString("tipo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClsVisitante.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //JOptionPane.showMessageDialog(null, cmbTipoGestion);
        
        opc = JOptionPane.showOptionDialog(null, cmbTipoGestion, "Tipo de gestion",JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        switch(opc){
            case 0 -> JOptionPane.showMessageDialog(null, "Formulario para generar ticket");
            case 1 -> JOptionPane.showMessageDialog(null, "Cancelar");
        }
        
        return (String)cmbTipoGestion.getSelectedItem();
    }
    
    public void crearTicket(){
        
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
