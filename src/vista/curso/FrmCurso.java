package vista.curso;

import javax.swing.ImageIcon;
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

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("vista/img/logo.png"));
        this.setFrameIcon(icon);
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
        setResizable(true);
        setTitle("Curso-Clase");
        setMinimumSize(new java.awt.Dimension(503, 439));
        setPreferredSize(new java.awt.Dimension(503, 439));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Curso"));

        cbxPeriodoLectivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione" }));

        lblPeriodoLectivo.setText("Período Lectivo:");

        lblCiclo.setText("Ciclo:");

        cbxCiclo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "1", "2", "3", "4", "5", "6" }));

        lblParalelo.setText("Paralelo:");

        cbxParalelo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "A", "B", "C", "D", "E", "F" }));

        lblJornada.setText("Jornada:");

        lblCapacidad.setText("Capacidad:");

        cbxJornada.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "MATUTINO", "VESPERTINO", "NOCTURNO" }));

        lblErrorPrdLectivo.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorPrdLectivo.setText("Debe seleccionar un período lectivo.");

        lblErrorJornada.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorJornada.setText("Debe seleccionar una jornada.");

        lblErrorCiclo.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorCiclo.setText("Debe seleccionar un ciclo.");

        lblErrorParalelo.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorParalelo.setText("Debe seleccionar un paralelo.");

        lblErrorCapacidad.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorCapacidad.setText("Campo obligatorio.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPeriodoLectivo)
                    .addComponent(cbxPeriodoLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblErrorPrdLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblJornada)
                        .addGap(27, 27, 27)
                        .addComponent(cbxJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblErrorJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCiclo)
                        .addGap(44, 44, 44)
                        .addComponent(cbxCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(lblErrorCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(cbxParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(lblErrorParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(txtCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(80, 80, 80)
                        .addComponent(lblErrorCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(lblPeriodoLectivo)
                .addGap(6, 6, 6)
                .addComponent(cbxPeriodoLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblErrorPrdLectivo)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxJornada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(lblErrorJornada)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblErrorCiclo)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblErrorParalelo)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCapacidad)
                    .addComponent(lblErrorCapacidad)))
        );

        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("Guardar y salir.");

        pnlClase.setBorder(javax.swing.BorderFactory.createTitledBorder("Clase"));

        lblDocete.setText("Docente:");

        cbxDocente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "|SELECCIONE|", "Item 2", "Item 3", "Item 4" }));

        cbxMateria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "|SELECCIONE|", "Item 2", "Item 3", "Item 4" }));

        lblMateria.setText("Materia:");

        lblErrorMateria.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorMateria.setText("Debe seleccionar una materia.");

        lblErrorDocente.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorDocente.setText("Debe seleccionar un docente.");

        javax.swing.GroupLayout pnlClaseLayout = new javax.swing.GroupLayout(pnlClase);
        pnlClase.setLayout(pnlClaseLayout);
        pnlClaseLayout.setHorizontalGroup(
            pnlClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClaseLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(pnlClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlClaseLayout.createSequentialGroup()
                        .addComponent(lblMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblErrorMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlClaseLayout.createSequentialGroup()
                        .addComponent(lblDocete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cbxDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblErrorDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pnlClaseLayout.setVerticalGroup(
            pnlClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClaseLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(pnlClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMateria)
                    .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(lblErrorMateria)
                .addGap(6, 6, 6)
                .addGroup(pnlClaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDocete)
                    .addComponent(cbxDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(lblErrorDocente))
        );

        btnGuardarContinuar.setText("Guardar y continuar ingresando");
        btnGuardarContinuar.setToolTipText("Guardar y continuar ingresando en el mismo curso.");

        lblError.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlClase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addComponent(btnGuardarContinuar))
                            .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65)
                        .addComponent(btnGuardar))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(pnlClase, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarContinuar)
                    .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar)))
        );

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
