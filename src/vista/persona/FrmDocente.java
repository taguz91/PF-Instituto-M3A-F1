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
        jLabel1 = new javax.swing.JLabel();
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

        lblIdentificacion.setText("Tipo de Identificación");

        txtIdentificacion.setToolTipText("Ingrese su Cedula o numero de Pasaporte");
        txtIdentificacion.setEnabled(false);
        txtIdentificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdentificacionActionPerformed(evt);
            }
        });

        lblOtroTrabajo.setText("Otro Trabajo");

        cbxOtroTrabajo.setToolTipText("Seleccione si el Docente tiene otro trabajo");
        cbxOtroTrabajo.setEnabled(false);

        lblFechaInicioContratacion.setText("Fecha de Contratacion");

        lblDocenteCapacitador.setText("Docente Capacitador");

        cbxDocenteCapacitador.setToolTipText("Seleccione si el Docente es Capacitador");
        cbxDocenteCapacitador.setEnabled(false);

        lblFechaFinContratacion.setText("Fecha fin contratacion");

        lblTipoTiempo.setText("Tipo Tiempo");

        cmbTipoTiempo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE", "TIEMPO COMPLETO", "TIEMPO PARCIAL", "MEDIO TIEMPO", "POR HORAS", "" }));
        cmbTipoTiempo.setToolTipText("Seleccione el tipo de tiempo del Docente");
        cmbTipoTiempo.setEnabled(false);

        spnCategoria.setModel(new javax.swing.SpinnerNumberModel(3, 3, 8, 1));
        spnCategoria.setToolTipText("Categoria que ocupa el Docente");
        spnCategoria.setAutoscrolls(true);
        spnCategoria.setEnabled(false);

        lblCategoria.setText("Categoría");

        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("Presione para guardar los cambios ya realizados");
        btnGuardar.setEnabled(false);

        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Presione si desee cancelar, no se guardaran los cambios");

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnBuscarPersona.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icons8_Search_15px.png"))); // NOI18N
        btnBuscarPersona.setToolTipText("Buscar Docente");

        btnRegistrarPersona.setText("Registrar Persona");
        btnRegistrarPersona.setToolTipText("Registrar una nueva persona");

        jdcFechaInicioContratacion.setToolTipText("Elija la fecha en la que se da inicio al contrato del docente");
        jdcFechaInicioContratacion.setEnabled(false);
        jdcFechaInicioContratacion.setMaxSelectableDate(new java.util.Date(127174514462000L));
        jdcFechaInicioContratacion.setMinSelectableDate(new java.util.Date(-315597538000L));

        jdcFechaFinContratacion.setEnabled(false);

        jLabel1.setToolTipText("Elija la fecha en la que culmina o finaliza el contrato del docente");
        jLabel1.setEnabled(false);

        cmbTipoIdentificacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONE", "CEDULA", "PASAPORTE" }));

        lblId.setText("Identificación");

        lblAbreviaturaDocente.setText("Abreviatura Docente*");

        lblTituloDocente.setText("Titulo Docente*");

        txtAbreviaturaDocente.setEnabled(false);

        txtTituloDocente.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblIdentificacion)
                .addGap(68, 68, 68)
                .addComponent(cmbTipoIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(lblId)
                .addGap(16, 16, 16)
                .addComponent(txtIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnBuscarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(470, 470, 470)
                .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblDatosPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnRegistrarPersona))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jdcFechaFinContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(170, 170, 170)
                        .addComponent(cbxDocenteCapacitador)
                        .addGap(169, 169, 169)
                        .addComponent(cbxOtroTrabajo))
                    .addComponent(lblFechaInicioContratacion)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(510, 510, 510)
                        .addComponent(lblDatoAbreviatura, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(510, 510, 510)
                        .addComponent(txtAbreviaturaDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(cmbTipoTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(380, 380, 380)
                        .addComponent(lblAbreviaturaDocente))
                    .addComponent(lblFechaFinContratacion)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(spnCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(380, 380, 380)
                        .addComponent(lblDocenteCapacitador))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jdcFechaInicioContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTipoTiempo)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(510, 510, 510)
                        .addComponent(txtTituloDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(610, 610, 610)
                        .addComponent(lblOtroTrabajo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(420, 420, 420)
                        .addComponent(lblDatoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(380, 380, 380)
                        .addComponent(lblTituloDocente))
                    .addComponent(lblCategoria)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(540, 540, 540)
                .addComponent(btnCancelar)
                .addGap(55, 55, 55)
                .addComponent(btnGuardar))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdentificacion)
                    .addComponent(cmbTipoIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblId)
                    .addComponent(txtIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDatosPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrarPersona))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jdcFechaFinContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(cbxDocenteCapacitador, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(cbxOtroTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblFechaInicioContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(lblDatoAbreviatura, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(txtAbreviaturaDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(cmbTipoTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(lblAbreviaturaDocente))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(lblFechaFinContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(spnCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(lblDocenteCapacitador, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jdcFechaInicioContratacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(lblTipoTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(txtTituloDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(lblOtroTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lblDatoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblTituloDocente))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(lblCategoria))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar)))
        );

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
    private javax.swing.JLabel jLabel1;
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
