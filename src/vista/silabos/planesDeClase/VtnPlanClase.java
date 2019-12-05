/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.silabos.planesDeClase;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import vista.AbstracView;

/**
 *
 * @author Daniel
 */
public class VtnPlanClase extends AbstracView {

    public VtnPlanClase() {
        initComponents();

    }

    public JButton getBtn_editar_fecha() {
        return btn_editar_fecha;
    }

    public void setBtn_editar_fecha(JButton btn_editar_fecha) {
        this.btn_editar_fecha = btn_editar_fecha;
    }

    public JButton getBtnEditarPLC() {
        return btnEditarPLC;
    }

    public void setBtnEditarPLC(JButton btnEditarPLC) {
        this.btnEditarPLC = btnEditarPLC;
    }

    public JButton getBtnImplimirPlan() {
        return btnImplimirPlan;
    }

    public void setBtnImplimirPlan(JButton btnImplimirPlan) {
        this.btnImplimirPlan = btnImplimirPlan;
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

    public JComboBox<String> getCmb_periodos() {
        return Cmb_periodos;
    }

    public void setCmb_periodos(JComboBox<String> Cmb_periodos) {
        this.Cmb_periodos = Cmb_periodos;
    }

    public void setBtnNuevoPLC(JButton btnNuevoPLC) {
        this.btnNuevoPLC = btnNuevoPLC;
    }

    public JLabel getLbBuscarPLC() {
        return lbBuscarPLC;
    }

    public void setLbBuscarPLC(JLabel lbBuscarPLC) {
        this.lbBuscarPLC = lbBuscarPLC;
    }

    public JTable getTbl() {
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

    public JComboBox<String> getCmbJornadas() {
        return CmbJornadas;
    }

    public void setCmbJornadas(JComboBox<String> CmbJornadas) {
        this.CmbJornadas = CmbJornadas;
    }

    public JComboBox<String> getCmb_Carreras() {
        return Cmb_Carreras;
    }

    public void setCmb_Carreras(JComboBox<String> Cmb_Carreras) {
        this.Cmb_Carreras = Cmb_Carreras;
    }

    public JButton getBtnCopiar() {
        return btnCopiar;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscarPLC = new javax.swing.JTextField();
        lbBuscarPLC = new javax.swing.JLabel();
        btnNuevoPLC = new javax.swing.JButton();
        btnEditarPLC = new javax.swing.JButton();
        btnEliminarPLC = new javax.swing.JButton();
        Cmb_Carreras = new javax.swing.JComboBox<>();
        Carreras = new javax.swing.JLabel();
        CmbJornadas = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tlbTablaPLC = new javax.swing.JTable();
        Cmb_periodos = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnImplimirPlan = new javax.swing.JButton();
        btn_editar_fecha = new javax.swing.JButton();
        btnCopiar = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);

        lbBuscarPLC.setText("Buscar:");

        btnNuevoPLC.setText("Nuevo");

        btnEditarPLC.setText("Editar");

        btnEliminarPLC.setText("Eliminar");

        Carreras.setText("Carrera:");

        jLabel2.setText("Jornada:");

        tlbTablaPLC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PlanCod", "Docente", "Materia", "Curso", "Unidad", "Estado", "Estado", "Fecha"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tlbTablaPLC.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tlbTablaPLC.getTableHeader().setResizingAllowed(false);
        tlbTablaPLC.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tlbTablaPLC);
        if (tlbTablaPLC.getColumnModel().getColumnCount() > 0) {
            tlbTablaPLC.getColumnModel().getColumn(0).setMinWidth(0);
            tlbTablaPLC.getColumnModel().getColumn(0).setPreferredWidth(0);
            tlbTablaPLC.getColumnModel().getColumn(0).setMaxWidth(0);
            tlbTablaPLC.getColumnModel().getColumn(1).setPreferredWidth(250);
            tlbTablaPLC.getColumnModel().getColumn(1).setMaxWidth(250);
            tlbTablaPLC.getColumnModel().getColumn(3).setPreferredWidth(80);
            tlbTablaPLC.getColumnModel().getColumn(3).setMaxWidth(80);
            tlbTablaPLC.getColumnModel().getColumn(4).setPreferredWidth(50);
            tlbTablaPLC.getColumnModel().getColumn(4).setMaxWidth(50);
            tlbTablaPLC.getColumnModel().getColumn(5).setPreferredWidth(80);
            tlbTablaPLC.getColumnModel().getColumn(5).setMaxWidth(80);
            tlbTablaPLC.getColumnModel().getColumn(6).setPreferredWidth(80);
            tlbTablaPLC.getColumnModel().getColumn(6).setMaxWidth(80);
        }

        jLabel3.setText("Periodo:");

        btnImplimirPlan.setText("Imprimir ");

        btn_editar_fecha.setText("Editar Fecha");

        btnCopiar.setText("Copiar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbBuscarPLC)
                                .addComponent(Carreras)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CmbJornadas, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(Cmb_periodos, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtBuscarPLC, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Cmb_Carreras, javax.swing.GroupLayout.Alignment.LEADING, 0, 393, Short.MAX_VALUE))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnNuevoPLC)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnEditarPLC)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnEliminarPLC)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnImplimirPlan))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btn_editar_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnCopiar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarPLC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbBuscarPLC)
                    .addComponent(btnNuevoPLC)
                    .addComponent(btnEditarPLC)
                    .addComponent(btnEliminarPLC)
                    .addComponent(btnImplimirPlan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cmb_Carreras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Carreras)
                    .addComponent(btn_editar_fecha)
                    .addComponent(btnCopiar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cmb_periodos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CmbJornadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Carreras;
    private javax.swing.JComboBox<String> CmbJornadas;
    private javax.swing.JComboBox<String> Cmb_Carreras;
    private javax.swing.JComboBox<String> Cmb_periodos;
    private javax.swing.JButton btnCopiar;
    private javax.swing.JButton btnEditarPLC;
    private javax.swing.JButton btnEliminarPLC;
    private javax.swing.JButton btnImplimirPlan;
    private javax.swing.JButton btnNuevoPLC;
    private javax.swing.JButton btn_editar_fecha;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbBuscarPLC;
    private javax.swing.JTable tlbTablaPLC;
    private javax.swing.JTextField txtBuscarPLC;
    // End of variables declaration//GEN-END:variables
}
