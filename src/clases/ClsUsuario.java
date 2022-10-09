package clases;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Clases.ClsConnect;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Samuel
 */
public class ClsUsuario {

    /*private String usuario;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String contraseña1;
    private String contraseña2;
    private String fechaNac;*/
    private String query;

    /*private ImageIcon Img;
    private Icon icono;
    private JLabel label;*/
    public String claveMurci(String texto) {
        texto = texto.replace('m', '0');
        texto = texto.replace('M', '0');
        texto = texto.replace('u', '1');
        texto = texto.replace('U', '1');
        texto = texto.replace('r', '2');
        texto = texto.replace('R', '2');
        texto = texto.replace('c', '3');
        texto = texto.replace('C', '3');
        texto = texto.replace('i', '4');
        texto = texto.replace('I', '4');
        texto = texto.replace('e', '5');
        texto = texto.replace('E', '5');
        texto = texto.replace('l', '6');
        texto = texto.replace('L', '6');
        texto = texto.replace('a', '7');
        texto = texto.replace('A', '7');
        texto = texto.replace('g', '8');
        texto = texto.replace('G', '8');
        texto = texto.replace('o', '9');
        texto = texto.replace('O', '9');
        return texto;
    }

    /*public ClsUsuario(JLabel label){
        this.label = label;
    }*/
    public void nuevoUsuario(String codigo, String nombre1, String nombre2, String apellido1, String apellido2, String fechaNac, String usuario,
            String pass1, String pass2) {

        //this.codigo = codigo;
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
                        + "', 1"
                        + ");";

                System.out.println("query = " + query);
                cn.procedimiento(query);
                JOptionPane.showMessageDialog(null, "Usuario creado exitosamente");
                cn.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Usuario no creado error\n, " + e);
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
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
                        + ");";

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
}
