package vista.persona;

import datechooser.beans.DateChooserCombo;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author ana96
 */
public class FrmDocente extends javax.swing.JInternalFrame {

    /**
     * Creates new form Docente
     */
    public FrmDocente() {
        initComponents();
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
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


    public JCheckBox getCbxDocenteCapacitador() {
        return cbxDocenteCapacitador;
    }

    public void setCbxDocenteCapacitador(JCheckBox cbxDocenteCapacitador) {
        this.cbxDocenteCapacitador = cbxDocenteCapacitador;
    }

    public JCheckBox getCbxOtroTrabajo() {
        return cbxOtroTrabajo;
    }

    public void setCbxOtroTrabajo(JCheckBox cbxOtroTrabajo) {
        this.cbxOtroTrabajo = cbxOtroTrabajo;
    }

    public JComboBox<String> getCmbCedula() {
        return cmbCedula;
    }

    public void setCmbCedula(JComboBox<String> cmbCedula) {
        this.cmbCedula = cmbCedula;
    }

    public JComboBox<String> getCmbTipoTiempo() {
        return cmbTipoTiempo;
    }

    public void setCmbTipoTiempo(JComboBox<String> cmbTipoTiempo) {
        this.cmbTipoTiempo = cmbTipoTiempo;
    }

    public JSeparator getjSeparator1() {
        return jSeparator1;
    }

    public void setjSeparator1(JSeparator jSeparator1) {
        this.jSeparator1 = jSeparator1;
    }

    public JSeparator getjSeparator2() {
        return jSeparator2;
    }

    public void setjSeparator2(JSeparator jSeparator2) {
        this.jSeparator2 = jSeparator2;
    }

    public JSeparator getjSeparator4() {
        return jSeparator4;
    }

    public void setjSeparator4(JSeparator jSeparator4) {
        this.jSeparator4 = jSeparator4;
    }

    public JSeparator getjSeparator5() {
        return jSeparator5;
    }

    public void setjSeparator5(JSeparator jSeparator5) {
        this.jSeparator5 = jSeparator5;
    }

    public DateChooserCombo getJdcFechaFinContratacion() {
        return jdcFechaFinContratacion;
    }

    public void setJdcFechaFinContratacion(DateChooserCombo jdcFechaFinContratacion) {
        this.jdcFechaFinContratacion = jdcFechaFinContratacion;
    }

    public DateChooserCombo getJdcFechaInicioContratacion() {
        return jdcFechaInicioContratacion;
    }

    public void setJdcFechaInicioContratacion(DateChooserCombo jdcFechaInicioContratacion) {
        this.jdcFechaInicioContratacion = jdcFechaInicioContratacion;
    }

    public JLabel getLblCategoria() {
        return lblCategoria;
    }

    public void setLblCategoria(JLabel lblCategoria) {
        this.lblCategoria = lblCategoria;
    }

    public JLabel getLblDocenteCapacitador() {
        return lblDocenteCapacitador;
    }

    public void setLblDocenteCapacitador(JLabel lblDocenteCapacitador) {
        this.lblDocenteCapacitador = lblDocenteCapacitador;
    }

    public JLabel getLblFechaFinContratacion() {
        return lblFechaFinContratacion;
    }

    public void setLblFechaFinContratacion(JLabel lblFechaFinContratacion) {
        this.lblFechaFinContratacion = lblFechaFinContratacion;
    }

    public JLabel getLblFechaInicioContratacion() {
        return lblFechaInicioContratacion;
    }

    public void setLblFechaInicioContratacion(JLabel lblFechaInicioContratacion) {
        this.lblFechaInicioContratacion = lblFechaInicioContratacion;
    }

    public JLabel getLblIdentificacion() {
        return lblIdentificacion;
    }

    public void setLblIdentificacion(JLabel lblIdentificacion) {
        this.lblIdentificacion = lblIdentificacion;
    }

    public JLabel getLblOtroTrabajo() {
        return lblOtroTrabajo;
    }

    public void setLblOtroTrabajo(JLabel lblOtroTrabajo) {
        this.lblOtroTrabajo = lblOtroTrabajo;
    }

    public JLabel getLblTipoId() {
        return lblTipoId;
    }

    public void setLblTipoId(JLabel lblTipoId) {
        this.lblTipoId = lblTipoId;
    }

    public JLabel getLblTipoTiempo() {
        return lblTipoTiempo;
    }

    public void setLblTipoTiempo(JLabel lblTipoTiempo) {
        this.lblTipoTiempo = lblTipoTiempo;
    }

    public JSpinner getSpnCategoria() {
        return spnCategoria;
    }

    public void setSpnCategoria(JSpinner spnCategoria) {
        this.spnCategoria = spnCategoria;
    }

    public JTextField getTxtIdentificacion() {
        return txtIdentificacion;
    }

    public void setTxtIdentificacion(JTextField txtIdentificacion) {
        this.txtIdentificacion = txtIdentificacion;
    }

  
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTipoId = new javax.swing.JLabel();
        cmbCedula = new javax.swing.JComboBox<>();
        lblIdentificacion = new javax.swing.JLabel();
        txtIdentificacion = new javax.swing.JTextField();
        lblOtroTrabajo = new javax.swing.JLabel();
        cbxOtroTrabajo = new javax.swing.JCheckBox();
        jdcFechaInicioContratacion = new datechooser.beans.DateChooserCombo();
        lblFechaInicioContratacion = new javax.swing.JLabel();
        lblDocenteCapacitador = new javax.swing.JLabel();
        cbxDocenteCapacitador = new javax.swing.JCheckBox();
        lblFechaFinContratacion = new javax.swing.JLabel();
        lblTipoTiempo = new javax.swing.JLabel();
        cmbTipoTiempo = new javax.swing.JComboBox<>();
        spnCategoria = new javax.swing.JSpinner();
        lblCategoria = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jdcFechaFinContratacion = new datechooser.beans.DateChooserCombo();
        jSeparator2 = new javax.swing.JSeparator();

        setClosable(true);
        setIconifiable(true);
        setTitle("Ingreso Docente");
        setAutoscrolls(true);
        setName("infrmIngresoDocente"); // NOI18N
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        setVisible(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTipoId.setText("Tipo Id.*");
        getContentPane().add(lblTipoId, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        cmbCedula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE", "CEDULA", "PASAPORTE" }));
        cmbCedula.setToolTipText("Escoja el tipo de identificacion ");
        getContentPane().add(cmbCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 130, -1));

        lblIdentificacion.setText("Identificacion*");
        getContentPane().add(lblIdentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        txtIdentificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdentificacionActionPerformed(evt);
            }
        });
        getContentPane().add(txtIdentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 150, -1));

        lblOtroTrabajo.setText("Otro Trabajo");
        getContentPane().add(lblOtroTrabajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, -1, 10));
        getContentPane().add(cbxOtroTrabajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 130, -1, 20));

        jdcFechaInicioContratacion.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    jdcFechaInicioContratacion.setFormat(2);
    getContentPane().add(jdcFechaInicioContratacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 130, 20));

    lblFechaInicioContratacion.setText("Fecha de Contratacion");
    getContentPane().add(lblFechaInicioContratacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 10));

    lblDocenteCapacitador.setText("Docente Capacitador");
    getContentPane().add(lblDocenteCapacitador, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, -1, 10));
    getContentPane().add(cbxDocenteCapacitador, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, -1, 20));

    lblFechaFinContratacion.setText("Fecha fin contratacion");
    getContentPane().add(lblFechaFinContratacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, 10));

    lblTipoTiempo.setText("Tipo Tiempo");
    getContentPane().add(lblTipoTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, 10));

    cmbTipoTiempo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE", "TIEMPO COMPLETO", "TIEMPO PARCIAL", "MEDIO TIEMPO", "POR HORAS", "" }));
    getContentPane().add(cmbTipoTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 130, 20));

    spnCategoria.setModel(new javax.swing.SpinnerNumberModel(3, 3, 8, 1));
    spnCategoria.setAutoscrolls(true);
    getContentPane().add(spnCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 72, 20));

    lblCategoria.setText("Categoria");
    getContentPane().add(lblCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, 10));
    getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 530, 10));

    btnGuardar.setText("Guardar");
    getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 270, -1, -1));

    btnCancelar.setText("Cancelar");
    getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 270, -1, -1));

    jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
    getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 10, 180));
    getContentPane().add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 530, 10));
    getContentPane().add(jdcFechaFinContratacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 130, -1));
    getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 530, 10));

    getAccessibleContext().setAccessibleDescription("");
    getAccessibleContext().setAccessibleParent(this);

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdentificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdentificacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdentificacionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JCheckBox cbxDocenteCapacitador;
    private javax.swing.JCheckBox cbxOtroTrabajo;
    private javax.swing.JComboBox<String> cmbCedula;
    private javax.swing.JComboBox<String> cmbTipoTiempo;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private datechooser.beans.DateChooserCombo jdcFechaFinContratacion;
    private datechooser.beans.DateChooserCombo jdcFechaInicioContratacion;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblDocenteCapacitador;
    private javax.swing.JLabel lblFechaFinContratacion;
    private javax.swing.JLabel lblFechaInicioContratacion;
    private javax.swing.JLabel lblIdentificacion;
    private javax.swing.JLabel lblOtroTrabajo;
    private javax.swing.JLabel lblTipoId;
    private javax.swing.JLabel lblTipoTiempo;
    private javax.swing.JSpinner spnCategoria;
    private javax.swing.JTextField txtIdentificacion;
    // End of variables declaration//GEN-END:variables
}
