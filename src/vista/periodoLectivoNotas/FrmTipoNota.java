/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.periodoLectivoNotas;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTable;

public class FrmTipoNota extends JInternalFrame {

    public FrmTipoNota() {
        initComponents();

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("vista/img/logo.png"));
        this.setFrameIcon(icon);
        InitDiseño();
    }

    private void InitDiseño() {
        tblTipoNota.setRowHeight(20);
    }

    public JTable getTblTipoNota() {
        return tblTipoNota;
    }

    public void setTblTipoNota(JTable tblTipoNota) {
        this.tblTipoNota = tblTipoNota;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JLabel getLblNombreCarrera() {
        return lblNombreCarrera;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JComboBox<String> getCmbPeriodoLectivo() {
        return cmdPeriodoLectivo;
    }

    public void setCmdPeriodoLectivo(JComboBox<String> cmdPeriodoLectivo) {
        this.cmdPeriodoLectivo = cmdPeriodoLectivo;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblCarrera = new javax.swing.JLabel();
        cmdPeriodoLectivo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTipoNota = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblNombreCarrera = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        setClosable(true);
        setIconifiable(true);
        setMinimumSize(new java.awt.Dimension(778, 400));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(778, 400));
        setRequestFocusEnabled(false);

        btnCancelar.setText("Cancelar");

        btnGuardar.setText("Guardar");

        lblCarrera.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCarrera.setText("Periodo Lectivo:");

        tblTipoNota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Minimo", "Maximo", "ID"
            }
        ));
        tblTipoNota.setSelectionBackground(new java.awt.Color(153, 153, 153));
        jScrollPane1.setViewportView(tblTipoNota);
        if (tblTipoNota.getColumnModel().getColumnCount() > 0) {
            tblTipoNota.getColumnModel().getColumn(1).setMinWidth(60);
            tblTipoNota.getColumnModel().getColumn(1).setMaxWidth(60);
            tblTipoNota.getColumnModel().getColumn(2).setMinWidth(60);
            tblTipoNota.getColumnModel().getColumn(2).setMaxWidth(60);
            tblTipoNota.getColumnModel().getColumn(3).setMinWidth(0);
            tblTipoNota.getColumnModel().getColumn(3).setPreferredWidth(0);
            tblTipoNota.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Carrera:");

        lblNombreCarrera.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdPeriodoLectivo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNombreCarrera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdPeriodoLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmdPeriodoLectivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblNombreCarrera;
    private javax.swing.JTable tblTipoNota;
    // End of variables declaration//GEN-END:variables
}
