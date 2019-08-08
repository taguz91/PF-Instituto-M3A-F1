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

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitulo.setText("Informe de Seguimiento del Silabo");

        CbxTipoReporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Reporte Correspondiente a", "Interciclo", "Fin de ciclo" }));
        CbxTipoReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbxTipoReporteActionPerformed(evt);
            }
        });

        lblTipoReporte.setText("Reporte correspondiente a:");

        lblContenidos.setText("Contenidos");

        txrObservaciones.setColumns(20);
        txrObservaciones.setRows(5);
        jScrollPane1.setViewportView(txrObservaciones);

        lblTemasDictados.setText("Temas dictados:");

        txtCarrera.setEditable(false);

        lblCarrera.setText("Carrera:");

        txtParalelo.setEditable(false);

        lblParalelo.setText("Paralelo:");

        lblAsignatura.setText("Asignatura:");

        txtAsignatura.setEditable(false);

        lblDocente.setText("Docente:");

        txtDocente.setEditable(false);

        lblNumeroAlumnos.setText("Numero de alumnos:");

        txtNumeroAlumnos.setEditable(false);

        btnCancelar.setText("Cancelar");

        bntGuardar.setText("Guardar");

        lblFechaEntrega.setText("Fecha Entrega Informe:");

        lblCumplimiento.setText("Cumplimiento %:");

        spnCumplimiento.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 50));
        spnCumplimiento.setToolTipText("%");

        lblUnidad1.setText("Observaciones:");

        txrContenidos.setEditable(false);
        txrContenidos.setColumns(20);
        txrContenidos.setRows(5);
        jScrollPane2.setViewportView(txrContenidos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(272, 272, 272)
                .addComponent(lblTitulo))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTipoReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(CbxTipoReporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(lblAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(lblParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(txtParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNumeroAlumnos)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(txtNumeroAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(separador, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTemasDictados, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(CbxUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCumplimiento)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(spnCumplimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(dchFechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblFechaEntrega)))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblContenidos, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(198, 198, 198)
                .addComponent(lblUnidad1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(554, 554, 554)
                .addComponent(btnCancelar)
                .addGap(8, 8, 8)
                .addComponent(bntGuardar))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(lblTitulo)
                .addGap(6, 6, 6)
                .addComponent(lblTipoReporte)
                .addGap(0, 0, 0)
                .addComponent(CbxTipoReporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCarrera)
                            .addComponent(lblAsignatura))))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblParalelo)
                    .addComponent(txtParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDocente)
                            .addComponent(lblNumeroAlumnos))))
                .addGap(4, 4, 4)
                .addComponent(separador, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(lblTemasDictados)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CbxUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCumplimiento)
                            .addComponent(spnCumplimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dchFechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaEntrega))))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContenidos)
                    .addComponent(lblUnidad1))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar)
                    .addComponent(bntGuardar)))
        );

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
