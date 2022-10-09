/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formularios;

import Clases.ClsConnect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Samuel
 */
public class FrmPerfil extends javax.swing.JFrame {

    /**
     * Creates new form FrmPerfil
     */
    private String usuario = null;
    private int tipo = 0;
    DefaultListModel model = new DefaultListModel();
    DefaultListModel model1 = new DefaultListModel();

    ClsConnect cn;

    String itemlst = null;

    public FrmPerfil(String codigo) {
        this.setTipo(1);
        initComponents();

        this.usuario = codigo;

        System.out.println("usuario desde perfil = " + codigo);

        llenarCombo(codigo);

    }

    public FrmPerfil() {
        initComponents();
        llenarCombo2();

        llenarLista(cbUsuario.getSelectedItem().toString());
        llenarListaUsr(cbUsuario.getSelectedItem().toString());
        usuario = cbUsuario.getSelectedItem().toString();

        this.cbUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Value: " + cbUsuario.getSelectedItem().toString());
                llenarListaUsr(cbUsuario.getSelectedItem().toString());
                llenarLista(cbUsuario.getSelectedItem().toString());
                usuario = cbUsuario.getSelectedItem().toString();
            }
        });

        //System.out.println("Item: " + this.cbUsuario.getSelectedItem().toString());
    }

    public FrmPerfil(String usuario, int opc) {
        this.setTipo(2);
        initComponents();

        System.out.println("usuario desde perfil = " + usuario);

        llenarCombo(usuario);
    }

    public void llenarCombo(String usuario) {

        String query = null;

        System.out.println(this.getTipo());

        try {
            cn = new ClsConnect();
            cn.conexion("proyectofinal", "umg", "1234");

            switch (this.tipo) {
                case 1:
                    query = "SELECT usuario FROM usuario WHERE codidgo = '" + usuario + "';";
                    break;
                case 2:
                    query = "SELECT usuario FROM usuario WHERE usuario = '" + usuario + "';";
                    break;
            }

            //System.out.println("query = " + query);

            ResultSet rs = cn.select(query);

            cbUsuario.removeAllItems();

            while (rs.next()) {
                cbUsuario.addItem(rs.getString("usuario"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void llenarCombo2() {
        String query = null;

        try {
            cn = new ClsConnect();
            cn.conexion("proyectofinal", "umg", "1234");

            query = "SELECT usuario FROM usuario;";

            ResultSet rs = cn.select(query);

            cbUsuario.removeAllItems();

            while (rs.next()) {
                cbUsuario.addItem(rs.getString("usuario"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrmLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Modulos Asignados
    public void llenarLista(String usuario) { 
        model.removeAllElements();
        String query = null;
        int id = 0;
        String item = null;

        try {
            cn = new ClsConnect();
            cn.conexion("proyectofinal", "umg", "1234");

            if (usuario.equals("")) {
                query = "SELECT id, perfil FROM perfil";
            } else {
                query = "SELECT b.id, b.perfil FROM usuario_perfil a INNER JOIN perfil b ON a.id_perfil = b.id INNER JOIN usuario c"
                        + " ON a.id_usuario = c.id WHERE c.usuario = '" + usuario + "';";
            }

            ResultSet rs = cn.select(query);

            while (rs.next()) {
                id = rs.getInt("id");
                item = rs.getString("perfil");
                model.addElement(id + " - " + item);
            }
            lstPerfilesUsr.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(FrmLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Modulos Disponibles
    public void llenarListaUsr(String usuario) {
        String query = null;
        model1.removeAllElements();
        int id = 0;
        String item = null;
        
        try {
            cn = new ClsConnect();
            cn.conexion("proyectofinal", "umg", "1234");

            query = "SELECT id, perfil FROM perfil WHERE id NOT IN (SELECT id_perfil FROM usuario_perfil a INNER JOIN usuario c "
                    + "ON a.id_usuario = c.id WHERE c.usuario = '" + usuario + "')";

            //System.out.println("query = " + query);

            ResultSet rs = cn.select(query);

            while (rs.next()) {
                id = rs.getInt("id");
                item = rs.getString("perfil");
                model1.addElement(id + " - " + item);
            }
            lstPerfiles.setModel(model1);
        } catch (SQLException ex) {
            Logger.getLogger(FrmLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        cbUsuario = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstPerfiles = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstPerfilesUsr = new javax.swing.JList<>();
        btnQuitar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Permisos");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUsuario.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lblUsuario.setText("Usuario");
        lblUsuario.setPreferredSize(new java.awt.Dimension(57, 32));
        jPanel1.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 40));

        cbUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 130, 40));

        lstPerfiles.setBorder(javax.swing.BorderFactory.createTitledBorder("Disponibles"));
        lstPerfiles.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lstPerfiles);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 170, 160));

        lstPerfilesUsr.setBorder(javax.swing.BorderFactory.createTitledBorder("Asignados"));
        lstPerfilesUsr.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lstPerfilesUsr);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 170, 160));

        btnQuitar.setText("<< Quitar");
        btnQuitar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnQuitar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQuitar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });
        jPanel1.add(btnQuitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 100, -1));

        btnAgregar.setText("Agregar >>");
        btnAgregar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 100, -1));

        jPanel2.setLayout(new java.awt.GridLayout(1, 2, 20, 15));

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        jButton2.setText("Cancelar");
        jPanel2.add(jButton2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed

        //System.out.println("Usuario = " + usuario);

        itemlst = this.lstPerfiles.getSelectedValue();
        //System.out.println("item = " + itemlst);

        this.model1.removeElementAt(this.lstPerfiles.getSelectedIndex());

        this.model.addElement(itemlst);

        int pp = Integer.parseInt(itemlst.split(" - ")[0]);
        
        //System.out.println("pp = " + pp);
        
        gestionPerfil(this.usuario, pp, 1);
        
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        //this.lstPerfiles.remove(this.lstPerfiles.getSelectedIndex());

        itemlst = this.lstPerfilesUsr.getSelectedValue();
        //System.out.println("item = " + itemlst);

        this.model.removeElementAt(this.lstPerfilesUsr.getSelectedIndex());

        this.model1.addElement(itemlst);
        
        int pp = Integer.parseInt(itemlst.split(" - ")[0]);
        
        gestionPerfil(this.usuario, pp, 2);
    }//GEN-LAST:event_btnQuitarActionPerformed

    public void gestionPerfil(String usuario, int perfil, int opc) {

        try {
            //System.out.println("Usuario = " + usuario + "\nPerfil = " + perfil);
            
            cn.conexion("ProyectoFinal", "umg", "1234");
            
            CallableStatement cStmt = cn.con.prepareCall("{CALL sp_usrperfil(?,?,?,?)}");
            
            cStmt.setString(1, usuario);
            cStmt.setInt(2, perfil);
            cStmt.setInt(3, opc);
            cStmt.registerOutParameter("msg", Types.VARCHAR);
            
            cStmt.execute();
            
            String outputValue = cStmt.getString("msg");
            JOptionPane.showMessageDialog(null, outputValue);
            
            cn.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrmPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //asignarPerfil(this.usuario, 1);//Integer.parseInt(itemlst.split("-")[0]));
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPerfil().setVisible(true);
            }
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox<String> cbUsuario;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JList<String> lstPerfiles;
    private javax.swing.JList<String> lstPerfilesUsr;
    // End of variables declaration//GEN-END:variables
}
