/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.silabos;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Daniel
 */
public class frmCRUDPlanClase extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmCRUDPlanClase
     */
    public frmCRUDPlanClase() {
        initComponents();
    }

    public JButton getBtnEditarPLC() {
        return btnEditarPLC;
    }

    public void setBtnEditarPLC(JButton btnEditarPLC) {
        this.btnEditarPLC = btnEditarPLC;
    }

    public JButton getBtnEliminarPLC() {
        return btnEliminarPLC;
    }

    public void setBtnEliminarPLC(JButton btnEliminarPLC) {
        this.btnEliminarPLC = btnEliminarPLC;
    }

    public JButton getBtnNuevoPLC() {
        return btnNuevoPLC;
    }

    public void setBtnNuevoPLC(JButton btnNuevoPLC) {
        this.btnNuevoPLC = btnNuevoPLC;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JLabel getLbBuscarPLC() {
        return lbBuscarPLC;
    }

    public void setLbBuscarPLC(JLabel lbBuscarPLC) {
        this.lbBuscarPLC = lbBuscarPLC;
    }

    public JTable getTlbTablaPLC() {
        return tlbTablaPLC;
    }

    public void setTlbTablaPLC(JTable tlbTablaPLC) {
        this.tlbTablaPLC = tlbTablaPLC;
    }

    public JTextField getTxtBuscarPLC() {
        return txtBuscarPLC;
    }

    public void setTxtBuscarPLC(JTextField txtBuscarPLC) {
        this.txtBuscarPLC = txtBuscarPLC;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscarPLC = new javax.swing.JTextField();
        lbBuscarPLC = new javax.swing.JLabel();
        btnNuevoPLC = new javax.swing.JButton();
        btnEditarPLC = new javax.swing.JButton();
        btnEliminarPLC = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tlbTablaPLC = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbBuscarPLC.setText("Buscar");

        btnNuevoPLC.setText("Nuevo");

        btnEditarPLC.setText("Editar");

        btnEliminarPLC.setText("Eliminar");

        tlbTablaPLC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Carrera", "Jornada", "Docente", "Materia", "Plan de Clase N°", "Unidad"
            }
        ));
        jScrollPane1.setViewportView(tlbTablaPLC);
        if (tlbTablaPLC.getColumnModel().getColumnCount() > 0) {
            tlbTablaPLC.getColumnModel().getColumn(5).setMinWidth(50);
            tlbTablaPLC.getColumnModel().getColumn(5).setPreferredWidth(50);
            tlbTablaPLC.getColumnModel().getColumn(5).setMaxWidth(50);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 803, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbBuscarPLC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarPLC, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevoPLC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditarPLC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarPLC)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNuevoPLC)
                        .addComponent(btnEditarPLC)
                        .addComponent(btnEliminarPLC))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBuscarPLC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbBuscarPLC)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(frmCRUDPlanClase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCRUDPlanClase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCRUDPlanClase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCRUDPlanClase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCRUDPlanClase().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditarPLC;
    private javax.swing.JButton btnEliminarPLC;
    private javax.swing.JButton btnNuevoPLC;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbBuscarPLC;
    private javax.swing.JTable tlbTablaPLC;
    private javax.swing.JTextField txtBuscarPLC;
    // End of variables declaration//GEN-END:variables
}
