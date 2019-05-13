package vista.alumno;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Johnny
 */
public class FrmAlumnoCurso extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmAlumnoCurso
     */
    public FrmAlumnoCurso() {
        initComponents();
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JComboBox<String> getCmbCurso() {
        return cmbCurso;
    }

    public JComboBox<String> getCmbPrdLectivo() {
        return cmbPrdLectivo;
    }

    public JTable getTblMateriasPen() {
        return tblMateriasPen;
    }

    public JTable getTblMateriasSelec() {
        return tblMateriasSelec;
    }

    public JButton getBtnPasar1() {
        return btnPasar1;
    }

    public JButton getBtnPasarTodos() {
        return btnPasarTodos;
    }

    public JButton getBtnRegresar1() {
        return btnRegresar1;
    }

    public JButton getBtnRegresarTodos() {
        return btnRegresarTodos;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblAlumnos() {
        return tblAlumnos;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JLabel getLblErrorBuscar() {
        return lblErrorBuscar;
    }

    public JLabel getLblErrorPrdLectivo() {
        return lblErrorPrdLectivo;
    }

    public JButton getBtnMtCursadas() {
        return btnMtCursadas;
    }

    public JButton getBtnReprobadas() {
        return btnReprobadas;
    }

    public JButton getBtnAnuladas() {
        return btnAnuladas;
    }

    public JButton getBtnHorarioAlmn() {
        return btnHorarioAlmn;
    }

    public JButton getBtnHorarioCurso() {
        return btnHorarioCurso;
    }

    public JButton getBtnPendientes() {
        return btnPendientes;
    }

    public JLabel getLblNumMatriculas() {
        return lblNumMatriculas;
    }

    public JLabel getLblNumMatriculasClases() {
        return lblNumMatriculasClases;
    }

    public JComboBox<String> getCmbTipoMatricula() {
        return cmbTipoMatricula;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbPrdLectivo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblAlumnos = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        lblErrorBuscar = new javax.swing.JLabel();
        lblErrorPrdLectivo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMateriasSelec = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMateriasPen = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbCurso = new javax.swing.JComboBox<>();
        btnPasar1 = new javax.swing.JButton();
        btnPasarTodos = new javax.swing.JButton();
        btnRegresar1 = new javax.swing.JButton();
        btnRegresarTodos = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnHorarioCurso = new javax.swing.JButton();
        btnHorarioAlmn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnError = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblNumMatriculas = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnMtCursadas = new javax.swing.JButton();
        btnPendientes = new javax.swing.JButton();
        btnReprobadas = new javax.swing.JButton();
        btnAnuladas = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblNumMatriculasClases = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cmbTipoMatricula = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setTitle("Matrícula en un periodo");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Seleccione un período lectivo:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, -1, -1));

        cmbPrdLectivo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel1.add(cmbPrdLectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 35, 460, -1));

        jLabel2.setText("Seleccione un alumno:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 70, -1, -1));

        tblAlumnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane3.setViewportView(tblAlumnos);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 121, 460, 106));

        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 92, 430, 23));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icons8_Search_15px.png"))); // NOI18N
        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 30, 25));

        lblErrorBuscar.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorBuscar.setText("No debe ingresar caracteres especiales");
        jPanel1.add(lblErrorBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 300, -1));

        lblErrorPrdLectivo.setForeground(new java.awt.Color(204, 0, 0));
        lblErrorPrdLectivo.setText("Debe seleccionar un período lectivo.");
        jPanel1.add(lblErrorPrdLectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 260, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 486, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Clase"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblMateriasSelec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane1.setViewportView(tblMateriasSelec);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 74, 330, 158));

        tblMateriasPen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane2.setViewportView(tblMateriasPen);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 74, 300, 158));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Seleccione las materias que tomará el alumno en este curso.");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 52, -1, -1));

        jLabel4.setText("Seleccione un curso:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 23, -1, -1));

        jPanel2.add(cmbCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 18, 153, -1));

        btnPasar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icons8_Chevron_Right_20px.png"))); // NOI18N
        jPanel2.add(btnPasar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 74, 40, -1));

        btnPasarTodos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icons8_Double_Right_20px.png"))); // NOI18N
        jPanel2.add(btnPasarTodos, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 112, 40, -1));

        btnRegresar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icons8_Chevron_Left_20px_1.png"))); // NOI18N
        jPanel2.add(btnRegresar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 162, 40, -1));

        btnRegresarTodos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icons8_Double_Left_20px.png"))); // NOI18N
        jPanel2.add(btnRegresarTodos, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 200, 40, -1));

        jLabel6.setText("Ver horarios:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, 30));

        btnHorarioCurso.setText("Curso");
        jPanel2.add(btnHorarioCurso, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 70, -1));

        btnHorarioAlmn.setText("Alumno");
        jPanel2.add(btnHorarioAlmn, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 239, 730, 234));

        btnGuardar.setText("Guardar");

        btnError.setForeground(new java.awt.Color(153, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnError, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnError, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 479, 736, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(105, 105, 105))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setText("Número de matriculas:");
        jLabel7.setToolTipText("");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        lblNumMatriculas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumMatriculas.setToolTipText("");
        lblNumMatriculas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel4.add(lblNumMatriculas, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 30, 20));

        jLabel5.setText("Ver marterias:");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        btnMtCursadas.setText("Cursadas");
        jPanel4.add(btnMtCursadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 100, -1));

        btnPendientes.setText("Pendientes");
        jPanel4.add(btnPendientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 100, -1));

        btnReprobadas.setText("Reprobadas");
        jPanel4.add(btnReprobadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 100, -1));

        btnAnuladas.setText("Anuladas");
        jPanel4.add(btnAnuladas, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 100, -1));

        jLabel9.setText("Número de matriculas en clases:");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 170, 20));

        lblNumMatriculasClases.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumMatriculasClases.setToolTipText("");
        lblNumMatriculasClases.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel4.add(lblNumMatriculasClases, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 30, 20));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 0, 241, 160));

        jLabel10.setText("Tipo matricula:");

        cmbTipoMatricula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "ORDINARIA", "EXTRAORDINARIA", "ESPECIAL" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cmbTipoMatricula, 0, 229, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipoMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(492, 166, 241, 61));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnuladas;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel btnError;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnHorarioAlmn;
    private javax.swing.JButton btnHorarioCurso;
    private javax.swing.JButton btnMtCursadas;
    private javax.swing.JButton btnPasar1;
    private javax.swing.JButton btnPasarTodos;
    private javax.swing.JButton btnPendientes;
    private javax.swing.JButton btnRegresar1;
    private javax.swing.JButton btnRegresarTodos;
    private javax.swing.JButton btnReprobadas;
    private javax.swing.JComboBox<String> cmbCurso;
    private javax.swing.JComboBox<String> cmbPrdLectivo;
    private javax.swing.JComboBox<String> cmbTipoMatricula;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblErrorBuscar;
    private javax.swing.JLabel lblErrorPrdLectivo;
    private javax.swing.JLabel lblNumMatriculas;
    private javax.swing.JLabel lblNumMatriculasClases;
    private javax.swing.JTable tblAlumnos;
    private javax.swing.JTable tblMateriasPen;
    private javax.swing.JTable tblMateriasSelec;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
