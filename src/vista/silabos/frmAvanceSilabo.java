/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.silabos;

import com.toedter.calendar.JDateChooser;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Daniel
 */
public class frmAvanceSilabo extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmAvanceSilabo
     */
    public frmAvanceSilabo() {
        initComponents();
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("vista/img/logo.png"));
        this.setFrameIcon(icon);
    }

    public JComboBox<String> getCbxTipoReporte() {
        return CbxTipoReporte;
    }

    public void setCbxTipoReporte(JComboBox<String> CbxTipoReporte) {
        this.CbxTipoReporte = CbxTipoReporte;
    }

    public JComboBox<String> getCbxUnidad() {
        return CbxUnidad;
    }

    public void setCbxUnidad(JComboBox<String> CbxUnidad) {
        this.CbxUnidad = CbxUnidad;
    }

    public JButton getBntGuardar() {
        return bntGuardar;
    }

    public void setBntGuardar(JButton bntGuardar) {
        this.bntGuardar = bntGuardar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public JDateChooser getDchFechaEntrega() {
        return dchFechaEntrega;
    }

    public void setDchFechaEntrega(JDateChooser dchFechaEntrega) {
        this.dchFechaEntrega = dchFechaEntrega;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    public void setjScrollPane2(JScrollPane jScrollPane2) {
        this.jScrollPane2 = jScrollPane2;
    }

    public JLabel getLblAsignatura() {
        return lblAsignatura;
    }

    public void setLblAsignatura(JLabel lblAsignatura) {
        this.lblAsignatura = lblAsignatura;
    }

    public JLabel getLblCarrera() {
        return lblCarrera;
    }

    public void setLblCarrera(JLabel lblCarrera) {
        this.lblCarrera = lblCarrera;
    }

    public JLabel getLblContenidos() {
        return lblContenidos;
    }

    public void setLblContenidos(JLabel lblContenidos) {
        this.lblContenidos = lblContenidos;
    }

    public JLabel getLblCumplimiento() {
        return lblCumplimiento;
    }

    public void setLblCumplimiento(JLabel lblCumplimiento) {
        this.lblCumplimiento = lblCumplimiento;
    }

    public JLabel getLblDocente() {
        return lblDocente;
    }

    public void setLblDocente(JLabel lblDocente) {
        this.lblDocente = lblDocente;
    }

    public JLabel getLblFechaEntrega() {
        return lblFechaEntrega;
    }

    public void setLblFechaEntrega(JLabel lblFechaEntrega) {
        this.lblFechaEntrega = lblFechaEntrega;
    }

    public JLabel getLblNumeroAlumnos() {
        return lblNumeroAlumnos;
    }

    public void setLblNumeroAlumnos(JLabel lblNumeroAlumnos) {
        this.lblNumeroAlumnos = lblNumeroAlumnos;
    }
    

    public JLabel getLblParalelo() {
        return lblParalelo;
    }

    public void setLblParalelo(JLabel lblParalelo) {
        this.lblParalelo = lblParalelo;
    }

    public JLabel getLblTemasDictados() {
        return lblTemasDictados;
    }

    public void setLblTemasDictados(JLabel lblTemasDictados) {
        this.lblTemasDictados = lblTemasDictados;
    }

    public JLabel getLblTipoReporte() {
        return lblTipoReporte;
    }

    public void setLblTipoReporte(JLabel lblTipoReporte) {
        this.lblTipoReporte = lblTipoReporte;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JLabel getLblUnidad1() {
        return lblUnidad1;
    }

    public void setLblUnidad1(JLabel lblUnidad1) {
        this.lblUnidad1 = lblUnidad1;
    }

    public JSeparator getSeparador() {
        return separador;
    }

    public void setSeparador(JSeparator separador) {
        this.separador = separador;
    }

    public JSpinner getSpnCumplimiento() {
        return spnCumplimiento;
    }

    public void setSpnCumplimiento(JSpinner spnCumplimiento) {
        this.spnCumplimiento = spnCumplimiento;
    }

    public JTextArea getTxrContenidos() {
        return txrContenidos;
    }

    public void setTxrContenidos(JTextArea txrContenidos) {
        this.txrContenidos = txrContenidos;
    }

    public JTextArea getTxrObservaciones() {
        return txrObservaciones;
    }

    public void setTxrObservaciones(JTextArea txrObservaciones) {
        this.txrObservaciones = txrObservaciones;
    }

    public JTextField getTxtAsignatura() {
        return txtAsignatura;
    }

    public void setTxtAsignatura(JTextField txtAsignatura) {
        this.txtAsignatura = txtAsignatura;
    }

    public JTextField getTxtCarrera() {
        return txtCarrera;
    }

    public void setTxtCarrera(JTextField txtCarrera) {
        this.txtCarrera = txtCarrera;
    }

    public JTextField getTxtDocente() {
        return txtDocente;
    }

    public void setTxtDocente(JTextField txtDocente) {
        this.txtDocente = txtDocente;
    }

    public JTextField getTxtNumeroAlumnos() {
        return txtNumeroAlumnos;
    }

    public void setTxtNumeroAlumnos(JTextField txtNumeroAlumnos) {
        this.txtNumeroAlumnos = txtNumeroAlumnos;
    }

    public JTextField getTxtParalelo() {
        return txtParalelo;
    }

    public void setTxtParalelo(JTextField txtParalelo) {
        this.txtParalelo = txtParalelo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        CbxTipoReporte = new javax.swing.JComboBox<>();
        lblTipoReporte = new javax.swing.JLabel();
        lblContenidos = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txrObservaciones = new javax.swing.JTextArea();
        CbxUnidad = new javax.swing.JComboBox<>();
        lblTemasDictados = new javax.swing.JLabel();
        txtCarrera = new javax.swing.JTextField();
        lblCarrera = new javax.swing.JLabel();
        txtParalelo = new javax.swing.JTextField();
        lblParalelo = new javax.swing.JLabel();
        lblAsignatura = new javax.swing.JLabel();
        txtAsignatura = new javax.swing.JTextField();
        lblDocente = new javax.swing.JLabel();
        txtDocente = new javax.swing.JTextField();
        lblNumeroAlumnos = new javax.swing.JLabel();
        txtNumeroAlumnos = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        bntGuardar = new javax.swing.JButton();
        dchFechaEntrega = new com.toedter.calendar.JDateChooser();
        lblFechaEntrega = new javax.swing.JLabel();
        lblCumplimiento = new javax.swing.JLabel();
        spnCumplimiento = new javax.swing.JSpinner();
        lblUnidad1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txrContenidos = new javax.swing.JTextArea();
        separador = new javax.swing.JSeparator();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitulo.setText("Informe de Seguimiento del Silabo");
        getContentPane().add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(272, 7, -1, -1));

        CbxTipoReporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Reporte Correspondiente a", "Interciclo", "Fin de ciclo" }));
        CbxTipoReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbxTipoReporteActionPerformed(evt);
            }
        });
        getContentPane().add(CbxTipoReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 47, -1, -1));

        lblTipoReporte.setText("Reporte correspondiente a:");
        getContentPane().add(lblTipoReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 30, 154, -1));

        lblContenidos.setText("Contenidos");
        getContentPane().add(lblContenidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 232, 153, -1));

        txrObservaciones.setColumns(20);
        txrObservaciones.setRows(5);
        jScrollPane1.setViewportView(txrObservaciones);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 345, 113));

        getContentPane().add(CbxUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 184, 320, -1));

        lblTemasDictados.setText("Temas dictados:");
        getContentPane().add(lblTemasDictados, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 163, 153, -1));

        txtCarrera.setEditable(false);
        getContentPane().add(txtCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 83, 250, -1));

        lblCarrera.setText("Carrera:");
        getContentPane().add(lblCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 85, 51, -1));

        txtParalelo.setEditable(false);
        getContentPane().add(txtParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 120, 51, -1));

        lblParalelo.setText("Paralelo:");
        getContentPane().add(lblParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 51, -1));

        lblAsignatura.setText("Asignatura:");
        getContentPane().add(lblAsignatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 85, 64, -1));

        txtAsignatura.setEditable(false);
        getContentPane().add(txtAsignatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 83, 279, -1));

        lblDocente.setText("Docente:");
        getContentPane().add(lblDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 122, 64, -1));

        txtDocente.setEditable(false);
        getContentPane().add(txtDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 120, 243, -1));

        lblNumeroAlumnos.setText("Numero de alumnos:");
        getContentPane().add(lblNumeroAlumnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(568, 122, -1, -1));

        txtNumeroAlumnos.setEditable(false);
        getContentPane().add(txtNumeroAlumnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(678, 120, 40, -1));

        btnCancelar.setText("Cancelar");
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(554, 402, -1, -1));

        bntGuardar.setText("Guardar");
        getContentPane().add(bntGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(636, 402, -1, -1));
        getContentPane().add(dchFechaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 190, 110, 20));

        lblFechaEntrega.setText("Fecha Entrega Informe:");
        getContentPane().add(lblFechaEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 190, -1, -1));

        lblCumplimiento.setText("Cumplimiento %:");
        getContentPane().add(lblCumplimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, -1, -1));

        spnCumplimiento.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 50));
        spnCumplimiento.setToolTipText("%");
        getContentPane().add(spnCumplimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 52, -1));

        lblUnidad1.setText("Observaciones:");
        getContentPane().add(lblUnidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(374, 232, 153, -1));

        txrContenidos.setEditable(false);
        txrContenidos.setColumns(20);
        txrContenidos.setRows(5);
        jScrollPane2.setViewportView(txrContenidos);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 253, 333, 113));
        getContentPane().add(separador, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 151, 706, 10));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CbxTipoReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxTipoReporteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CbxTipoReporteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CbxTipoReporte;
    private javax.swing.JComboBox<String> CbxUnidad;
    private javax.swing.JButton bntGuardar;
    private javax.swing.JButton btnCancelar;
    private com.toedter.calendar.JDateChooser dchFechaEntrega;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAsignatura;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblContenidos;
    private javax.swing.JLabel lblCumplimiento;
    private javax.swing.JLabel lblDocente;
    private javax.swing.JLabel lblFechaEntrega;
    private javax.swing.JLabel lblNumeroAlumnos;
    private javax.swing.JLabel lblParalelo;
    private javax.swing.JLabel lblTemasDictados;
    private javax.swing.JLabel lblTipoReporte;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUnidad1;
    private javax.swing.JSeparator separador;
    private javax.swing.JSpinner spnCumplimiento;
    private javax.swing.JTextArea txrContenidos;
    private javax.swing.JTextArea txrObservaciones;
    private javax.swing.JTextField txtAsignatura;
    private javax.swing.JTextField txtCarrera;
    private javax.swing.JTextField txtDocente;
    private javax.swing.JTextField txtNumeroAlumnos;
    private javax.swing.JTextField txtParalelo;
    // End of variables declaration//GEN-END:variables
}
