package vista.curso;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

/**
 *
 * @author arman
 */
public class FrmCurso extends javax.swing.JInternalFrame {

   
    public FrmCurso() {
        initComponents();
    }

    public JFormattedTextField getTxtCapacidad() {
        return txtCapacidad;
    }

    public JComboBox getCbxCiclo() {
        return cbxCiclo;
    }

    public void setCbxCiclo(JComboBox cbxCiclo) {
        this.cbxCiclo = cbxCiclo;
    }

    public JComboBox<String> getCbxDocente() {
        return cbxDocente;
    }

    public void setCbxDocente(JComboBox<String> cbxDocente) {
        this.cbxDocente = cbxDocente;
    }

    public JComboBox<String> getCbxJornada() {
        return cbxJornada;
    }

    public void setCbxJornada(JComboBox<String> cbxJornada) {
        this.cbxJornada = cbxJornada;
    }

    public JComboBox<String> getCbxMateria() {
        return cbxMateria;
    }

    public void setCbxMateria(JComboBox<String> cbxMateria) {
        this.cbxMateria = cbxMateria;
    }

    public JComboBox getCbxParalelo() {
        return cbxParalelo;
    }

    public void setCbxParalelo(JComboBox cbxParalelo) {
        this.cbxParalelo = cbxParalelo;
    }

    public JComboBox<String> getCbxPeriodoLectivo() {
        return cbxPeriodoLectivo;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JLabel getLblErrorCapacidad() {
        return lblErrorCapacidad;
    }

    public JLabel getLblErrorCiclo() {
        return lblErrorCiclo;
    }

    public JLabel getLblErrorDocente() {
        return lblErrorDocente;
    }

    public JLabel getLblErrorJornada() {
        return lblErrorJornada;
    }

    public JLabel getLblErrorMateria() {
        return lblErrorMateria;
    }

    public JLabel getLblErrorParalelo() {
        return lblErrorParalelo;
    }

    public JLabel getLblErrorPrdLectivo() {
        return lblErrorPrdLectivo;
    }

    public JButton getBtnGuardarContinuar() {
        return btnGuardarContinuar;
    }

    public JLabel getLblError() {
        return lblError;
    }
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cbxPeriodoLectivo = new javax.swing.JComboBox<>();
        lblPeriodoLectivo = new javax.swing.JLabel();
        lblCiclo = new javax.swing.JLabel();
        cbxCiclo = new javax.swing.JComboBox();
        lblParalelo = new javax.swing.JLabel();
        cbxParalelo = new javax.swing.JComboBox();
        lblJornada = new javax.swing.JLabel();
        lblCapacidad = new javax.swing.JLabel();
        txtCapacidad = new javax.swing.JFormattedTextField();
        cbxJornada = new javax.swing.JComboBox<>();
        lblErrorPrdLectivo = new javax.swing.JLabel();
        lblErrorJornada = new javax.swing.JLabel();
        lblErrorCiclo = new javax.swing.JLabel();
        lblErrorParalelo = new javax.swing.JLabel();
        lblErrorCapacidad = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        pnlClase = new javax.swing.JPanel();
        lblDocete = new javax.swing.JLabel();
        cbxDocente = new javax.swing.JComboBox<>();
        cbxMateria = new javax.swing.JComboBox<>();
        lblMateria = new javax.swing.JLabel();
        lblErrorMateria = new javax.swing.JLabel();
        lblErrorDocente = new javax.swing.JLabel();
        btnGuardarContinuar = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Curso-Clase");
        setMinimumSize(new java.awt.Dimension(454, 361));
        setPreferredSize(new java.awt.Dimension(454, 361));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Curso", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbxPeriodoLectivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "|SELECCIONE|", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxPeriodoLectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 400, -1));

        lblPeriodoLectivo.setText("Período Lectivo:");
        jPanel1.add(lblPeriodoLectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        lblCiclo.setText("Ciclo:");
        jPanel1.add(lblCiclo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        cbxCiclo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "1", "2", "3", "4", "5", "6" }));
        jPanel1.add(cbxCiclo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        lblParalelo.setText("Paralelo:");
        jPanel1.add(lblParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 50, -1));

        cbxParalelo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "A", "B", "C", "D", "E", "F" }));
        jPanel1.add(cbxParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, -1, -1));

        lblJornada.setText("Jornada:");
        jPanel1.add(lblJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        lblCapacidad.setText("Capacidad:");
        jPanel1.add(lblCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, 70, -1));
        jPanel1.add(txtCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 30, -1));

        cbxJornada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "MATUTINO", "VESPERTINO", "NOCTURNO" }));
        jPanel1.add(cbxJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 350, -1));

        lblErrorPrdLectivo.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorPrdLectivo.setText("Debe seleccionar un periodo lectivo.");
        jPanel1.add(lblErrorPrdLectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 386, -1));

        lblErrorJornada.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorJornada.setText("Debe seleccionar una jornada.");
        jPanel1.add(lblErrorJornada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 386, -1));

        lblErrorCiclo.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorCiclo.setText("Debe seleccionar un ciclo.");
        jPanel1.add(lblErrorCiclo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 125, -1));

        lblErrorParalelo.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorParalelo.setText("Debe seleccionar un paralelo.");
        jPanel1.add(lblErrorParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 149, -1));

        lblErrorCapacidad.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorCapacidad.setText("Campo obligatorio.");
        jPanel1.add(lblErrorCapacidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, 92, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 420, 160));

        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("Guardar y salir.");
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 290, -1, -1));

        pnlClase.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clase", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(51, 51, 51))); // NOI18N
        pnlClase.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDocete.setText("Docente:");
        pnlClase.add(lblDocete, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 50, -1));

        cbxDocente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "|SELECCIONE|", "Item 2", "Item 3", "Item 4" }));
        pnlClase.add(cbxDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 340, -1));

        cbxMateria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "|SELECCIONE|", "Item 2", "Item 3", "Item 4" }));
        pnlClase.add(cbxMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 340, -1));

        lblMateria.setText("Materia:");
        pnlClase.add(lblMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 50, -1));

        lblErrorMateria.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorMateria.setText("Debe seleccionar una materia.");
        pnlClase.add(lblErrorMateria, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 390, -1));

        lblErrorDocente.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorDocente.setText("Debe seleccionar un docente.");
        pnlClase.add(lblErrorDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 390, -1));

        getContentPane().add(pnlClase, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 420, 100));

        btnGuardarContinuar.setText("Guardar y continuar ingresando");
        btnGuardarContinuar.setToolTipText("Guardar y continuar ingresando en el mismo curso.");
        getContentPane().add(btnGuardarContinuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, -1, -1));

        lblError.setForeground(new java.awt.Color(204, 0, 0));
        getContentPane().add(lblError, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 390, 15));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarContinuar;
    private javax.swing.JComboBox cbxCiclo;
    private javax.swing.JComboBox<String> cbxDocente;
    private javax.swing.JComboBox<String> cbxJornada;
    private javax.swing.JComboBox<String> cbxMateria;
    private javax.swing.JComboBox cbxParalelo;
    private javax.swing.JComboBox<String> cbxPeriodoLectivo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCapacidad;
    private javax.swing.JLabel lblCiclo;
    private javax.swing.JLabel lblDocete;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblErrorCapacidad;
    private javax.swing.JLabel lblErrorCiclo;
    private javax.swing.JLabel lblErrorDocente;
    private javax.swing.JLabel lblErrorJornada;
    private javax.swing.JLabel lblErrorMateria;
    private javax.swing.JLabel lblErrorParalelo;
    private javax.swing.JLabel lblErrorPrdLectivo;
    private javax.swing.JLabel lblJornada;
    private javax.swing.JLabel lblMateria;
    private javax.swing.JLabel lblParalelo;
    private javax.swing.JLabel lblPeriodoLectivo;
    private javax.swing.JPanel pnlClase;
    private javax.swing.JFormattedTextField txtCapacidad;
    // End of variables declaration//GEN-END:variables
}
