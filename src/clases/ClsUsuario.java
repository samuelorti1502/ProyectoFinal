package clases;

import Clases.ClsConnect;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Samuel
 */
public class ClsUsuario {

    private String query;
    private boolean flag = false;

    public void nuevoUsuario(String codigo, String nombre1, String nombre2, String apellido1, String apellido2, String fechaNac, String usuario,
            String pass1, String pass2) throws SQLException {

        String fechaN = null;

        fechaN = obgenerFecha(fechaNac);
        
        System.out.println("fechaN = " + fechaN);
        
        ClsConnect cn = new ClsConnect();

        try {
            
            cn.conexion("ProyectoFinal", "umg", "1234");

            CallableStatement cStmt = cn.con.prepareCall("{CALL sp_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            
            cStmt.setString(1, codigo);
            cStmt.setString(2, nombre1);
            if (nombre2.equals("")) {
                cStmt.setNull(3, java.sql.Types.VARCHAR);
            } else {
                cStmt.setString(3, nombre2);
            }
            cStmt.setString(4, apellido1);
            if (apellido2.equals("")) {
                cStmt.setNull(5, java.sql.Types.VARCHAR);
            } else {
                cStmt.setString(5, apellido2);
            }
            cStmt.setString(6, fechaN);
            cStmt.setString(7, "./src/Images/Fotos/" + codigo + ".jpg ");
            cStmt.setString(8, usuario);
            cStmt.setString(9, pass1);
            cStmt.setInt(10, 1);
            cStmt.registerOutParameter("msg", Types.VARCHAR);
            cStmt.registerOutParameter("msg1", Types.INTEGER);

            cStmt.execute();
            final ResultSet rs = cStmt.getResultSet();

            String outputValue = cStmt.getString("msg");
            int outputValue1 = cStmt.getInt("msg1");
            JOptionPane.showMessageDialog(null, outputValue);
            
            if (outputValue1 == 1) {
                this.setFlag(true);
            }

            //cStmt.close();
            //cn.close();
        } catch (Exception e) {
            //cn.con.rollback();
            e.printStackTrace();
        } finally {
            cn.con.close();
        }
    }

    public void actualizarUsuario(String codigo, String nombre1, String nombre2, String apellido1, String apellido2, String fechaNac, String usuario,
            String pass1, String pass2) {
        String fechaN = null;

        fechaN = "STR_TO_DATE('" + fechaNac + "','%d/%m/%Y')";

        if (pass1.equals(pass2)) {
            try {
                ClsConnect cn = new ClsConnect();

                cn.conexion("ProyectoFinal", "umg", "1234");

                if (nombre2.equals("")) {
                    nombre2 = "NULL,";
                } else {
                    nombre2 = "'" + nombre2 + "',";
                }

                if (apellido2.equals("")) {
                    apellido2 = "NULL,";

                } else {
                    apellido2 = "'" + apellido2 + "',";
                }

                String query = "CALL sp_usuario( "
                        + "'" + codigo + "',"
                        + "'" + nombre1 + "',"
                        + nombre2
                        + "'" + apellido1 + "',"
                        + apellido2
                        + fechaN + ","
                        + "'./src/Images/Fotos/" + codigo + ".jpg', "
                        + "'" + usuario + "',"
                        + "'" + pass1
                        + "', 2"
                        + ", @msg);"
                        + "SELECT @msg AS mensaje_error;";

                System.out.println("query = " + query);
                cn.procedimiento(query);
                JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente");
                cn.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Usuario no creado, " + e);
                e.printStackTrace();
            }
        }
    }

    public void buscarUsuario(String codigo, JTextField nom1, JTextField nom2, JTextField apellido1, JTextField apellido2,
            JTextField fechaNac, JTextField usuario) {

        try {
            ClsConnect cn = new ClsConnect();
            cn.conexion("proyectofinal", "umg", "1234");

            query = "SELECT nombre1, nombre2, apellido1, apellido2, DATE_FORMAT(fecha_nacimiento, '%d%m%Y') fecha_nacimiento, usuario "
                    + "FROM usuario WHERE codigo = '" + codigo + "' LIMIT 1;";
            ResultSet rs = cn.select(query);

            while (rs.next()) {
                nom1.setText(rs.getString("nombre1"));
                nom2.setText(rs.getString("nombre2"));
                apellido1.setText(rs.getString("apellido1"));
                apellido2.setText(rs.getString("apellido2"));
                fechaNac.setText(rs.getString("fecha_nacimiento"));
                usuario.setText(rs.getString("usuario"));
            }
        } catch (Exception e) {
            System.out.println("e = " + e);
        }

    }
    
    public String obgenerFecha(String fecha){
        
        String fechaRetorno = null;
        try {
            ClsConnect cn = new ClsConnect();
            cn.conexion("proyectofinal", "umg", "1234");

            query = "SELECT STR_TO_DATE('" + fecha + "','%d/%m/%Y') fecha";
            ResultSet rs = cn.select(query);

            while (rs.next()) {
                fechaRetorno = rs.getString("fecha");
            }
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
        return fechaRetorno;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
