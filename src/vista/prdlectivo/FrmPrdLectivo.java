/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.prdlectivo;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 *
 * @author Johnny
 */
public class FrmPrdLectivo extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmPrdLectivo
     */
    public FrmPrdLectivo() {
        initComponents();
    }

    public JButton getBtn_Cancelar() {
        return Btn_Cancelar;
    }

    public void setBtn_Cancelar(JButton Btn_Cancelar) {
        this.Btn_Cancelar = Btn_Cancelar;
    }

    public JButton getBtn_Guardar() {
        return Btn_Guardar;
    }

    public void setBtn_Guardar(JButton Btn_Guardar) {
        this.Btn_Guardar = Btn_Guardar;
    }

    public JComboBox<String> getCbx_Carreras() {
        return Cbx_Carreras;
    }

    public void setCbx_Carreras(JComboBox<String> Cbx_Carreras) {
        this.Cbx_Carreras = Cbx_Carreras;
    }

    public JDateChooser getJdc_FechaFin() {
        return jdc_FechaFin;
    }

    public void setJdc_FechaFin(JDateChooser jdc_FechaFin) {
        this.jdc_FechaFin = jdc_FechaFin;
    }

    public JDateChooser getJdc_FechaInicio() {
        return jdc_FechaInicio;
    }

    public void setJdc_FechaInicio(JDateChooser jdc_FechaInicio) {
        this.jdc_FechaInicio = jdc_FechaInicio;
    }

    public JTextField getTxt_Nombre() {
        return Txt_Nombre;
    }

    public void setTxt_Nombre(JTextField Txt_Nombre) {
        this.Txt_Nombre = Txt_Nombre;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JLabel getjLabel2() {
        return jLabel2;
    }

    public void setjLabel2(JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }

    public JLabel getjLabel4() {
        return jLabel4;
    }

    public void setjLabel4(JLabel jLabel4) {
        this.jLabel4 = jLabel4;
    }

    public JLabel getjLabel5() {
        return jLabel5;
    }

    public void setjLabel5(JLabel jLabel5) {
        this.jLabel5 = jLabel5;
    }

    public JLabel getjLabel6() {
        return jLabel6;
    }

    public void setjLabel6(JLabel jLabel6) {
        this.jLabel6 = jLabel6;
    }

    public JSeparator getjSeparator1() {
        return jSeparator1;
    }

    public void setjSeparator1(JSeparator jSeparator1) {
        this.jSeparator1 = jSeparator1;
    }

    public JTextField getTxtObservacion() {
        return txtObservacion;
    }

    public void setTxtObservacion(JTextField txtObservacion) {
        this.txtObservacion = txtObservacion;
    }

    public JLabel getLbl_ErrCarrera() {
        return Lbl_ErrCarrera;
    }

    public void setLbl_ErrCarrera(JLabel Lbl_ErrCarrera) {
        this.Lbl_ErrCarrera = Lbl_ErrCarrera;
    }

    public JLabel getLbl_ErrNombre() {
        return Lbl_ErrNombre;
    }

    public void setLbl_ErrNombre(JLabel Lbl_ErrNombre) {
        this.Lbl_ErrNombre = Lbl_ErrNombre;
    }

    public JLabel getLbl_ErrObservacion() {
        return Lbl_ErrObservacion;
    }

    public void setLbl_ErrObservacion(JLabel Lbl_ErrObservacion) {
        this.Lbl_ErrObservacion = Lbl_ErrObservacion;
    }

    public JLabel getLbl_ErrFecInicio() {
        return Lbl_ErrFecInicio;
    }

    public void setLbl_ErrFecInicio(JLabel Lbl_ErrFecInicio) {
        this.Lbl_ErrFecInicio = Lbl_ErrFecInicio;
    }

    public JLabel getLbl_ErrFecFin() {
        return Lbl_ErrFecFin;
    }

    public void setLbl_ErrFecFin(JLabel Lbl_ErrFecFin) {
        this.Lbl_ErrFecFin = Lbl_ErrFecFin;
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        txtObservacion = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        Btn_Guardar = new javax.swing.JButton();
        Btn_Cancelar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Txt_Nombre = new javax.swing.JTextField();
        Cbx_Carreras = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Lbl_ErrCarrera = new javax.swing.JLabel();
        Lbl_ErrNombre = new javax.swing.JLabel();
        Lbl_ErrObservacion = new javax.swing.JLabel();
        Lbl_ErrFecInicio = new javax.swing.JLabel();
        Lbl_ErrFecFin = new javax.swing.JLabel();
        jdc_FechaInicio = new com.toedter.calendar.JDateChooser();
        jdc_FechaFin = new com.toedter.calendar.JDateChooser();

        setClosable(true);
        setIconifiable(true);
        setTitle("Formulario Periodo Lectivo");

        jLabel4.setText("Observación *");

        Btn_Guardar.setText("Guardar");

        Btn_Cancelar.setText("Cancelar");

        jLabel5.setText("Carrera *");

        jLabel6.setText("Nombre");

        Cbx_Carreras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "|SELECCIONE|" }));

        jLabel1.setText("Fecha de Inicio *");

        jLabel2.setText("Fecha de Conclusion *");

        Lbl_ErrCarrera.setForeground(new java.awt.Color(204, 0, 0));
        Lbl_ErrCarrera.setText("Seleccione una Carrera");

        Lbl_ErrNombre.setForeground(new java.awt.Color(204, 0, 0));
        Lbl_ErrNombre.setText("Ingrese solo letras");

        Lbl_ErrObservacion.setForeground(new java.awt.Color(204, 0, 0));
        Lbl_ErrObservacion.setText("Ingrese una Observacion");

        Lbl_ErrFecInicio.setForeground(new java.awt.Color(204, 0, 0));
        Lbl_ErrFecInicio.setText("Fecha Incorrecta");

        Lbl_ErrFecFin.setForeground(new java.awt.Color(204, 0, 0));
        Lbl_ErrFecFin.setText("Fecha Incorrecta");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Cbx_Carreras, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Lbl_ErrCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel6)
                .addGap(23, 23, 23)
                .addComponent(Txt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(Lbl_ErrNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(189, 189, 189)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jdc_FechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110)
                .addComponent(jdc_FechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(Lbl_ErrFecInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110)
                .addComponent(Lbl_ErrFecFin, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel4)
                .addGap(21, 21, 21)
                .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(Lbl_ErrObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(260, 260, 260)
                .addComponent(Btn_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(Btn_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Cbx_Carreras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(Lbl_ErrCarrera)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(Txt_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(Lbl_ErrNombre)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdc_FechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdc_FechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Lbl_ErrFecInicio)
                    .addComponent(Lbl_ErrFecFin))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(Lbl_ErrObservacion)
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Btn_Cancelar)
                    .addComponent(Btn_Guardar)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Cancelar;
    private javax.swing.JButton Btn_Guardar;
    private javax.swing.JComboBox<String> Cbx_Carreras;
    private javax.swing.JLabel Lbl_ErrCarrera;
    private javax.swing.JLabel Lbl_ErrFecFin;
    private javax.swing.JLabel Lbl_ErrFecInicio;
    private javax.swing.JLabel Lbl_ErrNombre;
    private javax.swing.JLabel Lbl_ErrObservacion;
    private javax.swing.JTextField Txt_Nombre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private com.toedter.calendar.JDateChooser jdc_FechaFin;
    private com.toedter.calendar.JDateChooser jdc_FechaInicio;
    private javax.swing.JTextField txtObservacion;
    // End of variables declaration//GEN-END:variables
}
