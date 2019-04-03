package vista.persona;

import com.toedter.calendar.JDateChooser;
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

    private JButton btnInsertar;

    /**
     * Creates new form Docente
     */
    public FrmDocente() {

        initComponents();

    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
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

    public JButton getBtnInsertar() {
        return btnInsertar;
    }

    public void setBtnInsertar(JButton btnInsertar) {
        this.btnInsertar = btnInsertar;
    }

    public JDateChooser getJdcFechaFinContratacion() {
        return jdcFechaFinContratacion;
    }

    public void setJdcFechaFinContratacion(JDateChooser jdcFechaFinContratacion) {
        this.jdcFechaFinContratacion = jdcFechaFinContratacion;
    }

    public JDateChooser getJdcFechaInicioContratacion() {
        return jdcFechaInicioContratacion;
    }

    public void setJdcFechaInicioContratacion(JDateChooser jdcFechaInicioContratacion) {
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

    public JButton getBtnBuscarPersona() {
        return btnBuscarPersona;
    }

    public void setBtnBuscarPersona(JButton btnBuscarPersona) {
        this.btnBuscarPersona = btnBuscarPersona;
    }

    public JLabel getLblDatosPersona() {
        return lblDatosPersona;
    }

    public void setLblDatosPersona(JLabel lblDatosPersona) {
        this.lblDatosPersona = lblDatosPersona;
    }

    public JButton getBtnRegistrarPersona() {
        return btnRegistrarPersona;
    }

    public void setBtnRegistrarPersona(JButton btnRegistrarPersona) {
        this.btnRegistrarPersona = btnRegistrarPersona;
    }

    public JButton getBtnEditar() {
        return btnInsertar;
    }

    public JLabel getLblError() {
        return lblError;
    }

    public void setLblError(JLabel lblError) {
        this.lblError = lblError;
    }

    public JComboBox<String> getCmbTipoIdentificacion() {
        return cmbTipoIdentificacion;
    }

    public void setCmbTipoIdentificacion(JComboBox<String> cmbTipoIdentificacion) {
        this.cmbTipoIdentificacion = cmbTipoIdentificacion;
    }

    public JLabel getLblId() {
        return lblId;
    }

    public void setLblId(JLabel lblId) {
        this.lblId = lblId;
    }

    public JLabel getLblAbreviaturaDocente() {
        return lblAbreviaturaDocente;
    }

    public void setLblAbreviaturaDocente(JLabel lblAbreviaturaDocente) {
        this.lblAbreviaturaDocente = lblAbreviaturaDocente;
    }

    public JLabel getLblTituloDocente() {
        return lblTituloDocente;
    }

    public void setLblTituloDocente(JLabel lblTituloDocente) {
        this.lblTituloDocente = lblTituloDocente;
    }

    public JTextField getTxtAbreviaturaDocente() {
        return txtAbreviaturaDocente;
    }

    public void setTxtAbreviaturaDocente(JTextField txtAbreviaturaDocente) {
        this.txtAbreviaturaDocente = txtAbreviaturaDocente;
    }

    public JTextField getTxtTituloDocente() {
        return txtTituloDocente;
    }

    public void setTxtTituloDocente(JTextField txtTituloDocente) {
        this.txtTituloDocente = txtTituloDocente;
    }

    public JLabel getLblDatoAbreviatura() {
        return lblDatoAbreviatura;
    }

    public void setLblDatoAbreviatura(JLabel lblDatoAbreviatura) {
        this.lblDatoAbreviatura = lblDatoAbreviatura;
    }

    public JLabel getLblDatoTitulo() {
        return lblDatoTitulo;
    }

    public void setLblDatoTitulo(JLabel lblDatoTitulo) {
        this.lblDatoTitulo = lblDatoTitulo;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIdentificacion = new javax.swing.JLabel();
        txtIdentificacion = new javax.swing.JTextField();
        lblOtroTrabajo = new javax.swing.JLabel();
        cbxOtroTrabajo = new javax.swing.JCheckBox();
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
        jSeparator2 = new javax.swing.JSeparator();
        btnBuscarPersona = new javax.swing.JButton();
        lblDatosPersona = new javax.swing.JLabel();
        btnRegistrarPersona = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        jdcFechaInicioContratacion = new com.toedter.calendar.JDateChooser();
        jdcFechaFinContratacion = new com.toedter.calendar.JDateChooser();
        cmbTipoIdentificacion = new javax.swing.JComboBox<>();
        lblId = new javax.swing.JLabel();
        lblAbreviaturaDocente = new javax.swing.JLabel();
        lblTituloDocente = new javax.swing.JLabel();
        txtAbreviaturaDocente = new javax.swing.JTextField();
        txtTituloDocente = new javax.swing.JTextField();
        lblDatoTitulo = new javax.swing.JLabel();
        lblDatoAbreviatura = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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

        lblIdentificacion.setText("Tipo de Identificacion");
        getContentPane().add(lblIdentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        txtIdentificacion.setToolTipText("Ingrese su Cedula o numero de Pasaporte");
        txtIdentificacion.setEnabled(false);
        txtIdentificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdentificacionActionPerformed(evt);
            }
        });
        getContentPane().add(txtIdentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 140, -1));

        lblOtroTrabajo.setText("Otro Trabajo");
        getContentPane().add(lblOtroTrabajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 230, -1, 10));

        cbxOtroTrabajo.setToolTipText("Seleccione si el Docente tiene otro trabajo");
        cbxOtroTrabajo.setEnabled(false);
        getContentPane().add(cbxOtroTrabajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 220, -1, 20));

        lblFechaInicioContratacion.setText("Fecha de Contratacion");
        getContentPane().add(lblFechaInicioContratacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, 10));

        lblDocenteCapacitador.setText("Docente Capacitador");
        getContentPane().add(lblDocenteCapacitador, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, -1, 10));

        cbxDocenteCapacitador.setToolTipText("Seleccione si el Docente es Capacitador");
        cbxDocenteCapacitador.setEnabled(false);
        getContentPane().add(cbxDocenteCapacitador, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, -1, 20));

        lblFechaFinContratacion.setText("Fecha fin contratacion");
        getContentPane().add(lblFechaFinContratacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 10));

        lblTipoTiempo.setText("Tipo Tiempo");
        getContentPane().add(lblTipoTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, 10));

        cmbTipoTiempo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE", "TIEMPO COMPLETO", "TIEMPO PARCIAL", "MEDIO TIEMPO", "POR HORAS", "" }));
        cmbTipoTiempo.setToolTipText("Seleccione el tipo de tiempo del Docente");
        cmbTipoTiempo.setEnabled(false);
        getContentPane().add(cmbTipoTiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 130, 20));

        spnCategoria.setModel(new javax.swing.SpinnerNumberModel(3, 3, 8, 1));
        spnCategoria.setToolTipText("Categoria que ocupa el Docente");
        spnCategoria.setAutoscrolls(true);
        spnCategoria.setEnabled(false);
        getContentPane().add(spnCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 72, 20));

        lblCategoria.setText("Categoria");
        getContentPane().add(lblCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, 10));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 630, 10));

        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("Presione para guardar los cambios ya realizados");
        btnGuardar.setEnabled(false);
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 290, -1, -1));

        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Presione si desee cancelar, no se guardaran los cambios");
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 290, -1, -1));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 10, 180));
        getContentPane().add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 630, 10));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 630, 10));

        btnBuscarPersona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icons8_Search_15px.png"))); // NOI18N
        btnBuscarPersona.setToolTipText("Buscar Docente");
        getContentPane().add(btnBuscarPersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 30, 20));
        getContentPane().add(lblDatosPersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 270, 20));

        btnRegistrarPersona.setText("Registrar Persona");
        btnRegistrarPersona.setToolTipText("Registrar una nueva persona");
        getContentPane().add(btnRegistrarPersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, -1, -1));
        getContentPane().add(lblError, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 50, 140, 20));

        jdcFechaInicioContratacion.setToolTipText("Elija la fecha en la que se da inicio al contrato del docente");
        jdcFechaInicioContratacion.setDateFormatString("dd/MM/yyyy");
        jdcFechaInicioContratacion.setEnabled(false);
        jdcFechaInicioContratacion.setMaxSelectableDate(new java.util.Date(127174514462000L));
        jdcFechaInicioContratacion.setMinSelectableDate(new java.util.Date(-315597538000L));
        getContentPane().add(jdcFechaInicioContratacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 130, -1));

        jdcFechaFinContratacion.setToolTipText("Elija la fecha en la que culmina o finaliza el contrato del docente");
        jdcFechaFinContratacion.setDateFormatString("dd/MM/yyyy");
        jdcFechaFinContratacion.setEnabled(false);
        getContentPane().add(jdcFechaFinContratacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 130, -1));

        cmbTipoIdentificacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE", "CEDULA", "PASAPORTE" }));
        getContentPane().add(cmbTipoIdentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 140, -1));

        lblId.setText("Identificacion");
        getContentPane().add(lblId, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        lblAbreviaturaDocente.setText("Abreviatura Docente*");
        getContentPane().add(lblAbreviaturaDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 170, -1, -1));

        lblTituloDocente.setText("Titulo Docente*");
        getContentPane().add(lblTituloDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, -1, -1));

        txtAbreviaturaDocente.setEnabled(false);
        getContentPane().add(txtAbreviaturaDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 110, -1));

        txtTituloDocente.setEnabled(false);
        getContentPane().add(txtTituloDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 210, -1));
        getContentPane().add(lblDatoTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 180, 20));
        getContentPane().add(lblDatoAbreviatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, 210, 20));

        getAccessibleContext().setAccessibleDescription("");
        getAccessibleContext().setAccessibleParent(this);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdentificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdentificacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdentificacionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarPersona;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRegistrarPersona;
    private javax.swing.JCheckBox cbxDocenteCapacitador;
    private javax.swing.JCheckBox cbxOtroTrabajo;
    private javax.swing.JComboBox<String> cmbTipoIdentificacion;
    private javax.swing.JComboBox<String> cmbTipoTiempo;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private com.toedter.calendar.JDateChooser jdcFechaFinContratacion;
    private com.toedter.calendar.JDateChooser jdcFechaInicioContratacion;
    private javax.swing.JLabel lblAbreviaturaDocente;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblDatoAbreviatura;
    private javax.swing.JLabel lblDatoTitulo;
    private javax.swing.JLabel lblDatosPersona;
    private javax.swing.JLabel lblDocenteCapacitador;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblFechaFinContratacion;
    private javax.swing.JLabel lblFechaInicioContratacion;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblIdentificacion;
    private javax.swing.JLabel lblOtroTrabajo;
    private javax.swing.JLabel lblTipoTiempo;
    private javax.swing.JLabel lblTituloDocente;
    private javax.swing.JSpinner spnCategoria;
    private javax.swing.JTextField txtAbreviaturaDocente;
    private javax.swing.JTextField txtIdentificacion;
    private javax.swing.JTextField txtTituloDocente;
    // End of variables declaration//GEN-END:variables
}
