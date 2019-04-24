package vista.persona;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;


/**
 *
 * @author Johnny
 */
public class VtnDocente extends javax.swing.JInternalFrame {

    /**
     * Creates new form VtnCarrera
     */
    public VtnDocente() {
        initComponents();
    }

    public JButton getBtnAsignarRol() {
        return btnAsignarRol;
    }

    public JButton getBtnReporteDocente() {
        return btnReporteDocente;
    }

    public void setBtnReporteDocente(JButton btnReporteDocente) {
        this.btnReporteDocente = btnReporteDocente;
    }

    public JButton getBtnReporteDocenteMateria() {
        return btnReporteDocenteMateria;
    }

    public void setBtnReporteDocenteMateria(JButton btnReporteDocenteMateria) {
        this.btnReporteDocenteMateria = btnReporteDocenteMateria;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnIngresar() {
        return btnIngresar;
    }

    public JLabel getLblResultados() {
        return lblResultados;
    }

    public JButton getBtnhorasAsignadas() {
        return btnhorasAsignadas;
    }

    public JButton getBtnFinContratacion() {
        return btnFinContratacion;
    }

    public void setBtnFinContratacion(JButton btnFinContratacion) {
        this.btnFinContratacion = btnFinContratacion;
    }

    
    public JTable getTblDocente() {
        return tblDocente;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JCheckBox getCbxDocentesEliminados() {
        return cbxDocentesEliminados;
    }

    public void setCbxDocentesEliminados(JCheckBox cbxDocentesEliminados) {
        this.cbxDocentesEliminados = cbxDocentesEliminados;
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnIngresar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocente = new javax.swing.JTable();
        lblResultados = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnReporteDocente = new javax.swing.JButton();
        btnReporteDocenteMateria = new javax.swing.JButton();
        cbxDocentesEliminados = new javax.swing.JCheckBox();
        btnFinContratacion = new javax.swing.JButton();
        btnAsignarRol = new javax.swing.JButton();
        btnhorasAsignadas = new javax.swing.JButton();
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Docentes");

        jLabel1.setText("Buscar:");

        btnIngresar.setText("Ingresar");

        btnEditar.setText("Editar");

        btnEliminar.setText("Eliminar");

        tblDocente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id_Persona", "Cedula", "Profesor", "Inicio Contrato", "Fin Contrato", "Tempo Trabajo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDocente);

        lblResultados.setText("0 Resultados obtenidos.");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icons8_Search_15px.png"))); // NOI18N

        jLabel6.setText("Reportes:");

        btnReporteDocente.setText("Docente");

        btnReporteDocenteMateria.setText("Materias Docente");
        btnReporteDocenteMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteDocenteMateriaActionPerformed(evt);
            }
        });

        cbxDocentesEliminados.setText("Ver Docentes Eliminados");

        btnFinContratacion.setText("Fin de Contratación");

        btnAsignarRol.setText("Asignar rol");


        btnhorasAsignadas.setText("horas asignacion ");
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblResultados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(274, 274, 274)
                        .addComponent(jLabel6)
                        .addGap(15, 15, 15)
                        .addComponent(btnhorasAsignadas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReporteDocente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReporteDocenteMateria))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btnVerRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btnAsignarRol))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFinContratacion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(227, 227, 227)
                                .addComponent(cbxDocentesEliminados)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnIngresar)
                        .addComponent(btnEditar)
                        .addComponent(btnEliminar)
                        .addComponent(btnFinContratacion)
                        .addComponent(btnAsignarRol)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxDocentesEliminados)
                    .addComponent(btnVerRol))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblResultados)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnReporteDocenteMateria)
                        .addComponent(btnReporteDocente)
                        .addComponent(jLabel6)
                        .addComponent(btnhorasAsignadas)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReporteDocenteMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteDocenteMateriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReporteDocenteMateriaActionPerformed

    private void btnVerRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerRolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVerRolActionPerformed

    public JButton getBtnVerRol() {
        return btnVerRol;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignarRol;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnFinContratacion;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnReporteDocente;
    private javax.swing.JButton btnReporteDocenteMateria;
    private javax.swing.JButton btnhorasAsignadas;
    private javax.swing.JCheckBox cbxDocentesEliminados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblResultados;
    private javax.swing.JTable tblDocente;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
