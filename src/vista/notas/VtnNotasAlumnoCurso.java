package vista.notas;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Alejandro
 */
public class VtnNotasAlumnoCurso extends javax.swing.JInternalFrame {

    public VtnNotasAlumnoCurso() {
        initComponents();
    }

    public JButton getBtnImprimir() {
        return btnImprimir;
    }

    public JComboBox<String> getCmbAsignatura() {
        return cmbAsignatura;
    }

    public JComboBox<String> getCmbCiclo() {
        return cmbCiclo;
    }

    public JComboBox<String> getCmbDocente() {
        return cmbDocente;
    }

    public JComboBox<String> getCmbJornada() {
        return cmbJornada;
    }

    public JComboBox<String> getCmbPeriodoLectivo() {
        return cmbPeriodoLectivo;
    }

    public JComboBox<String> getCmbParalelo() {
        return cmb_paralelo;
    }

    public JLabel getLblCarrera() {
        return lblCarrera;
    }

    public JTable getTblNotas() {
        return tblNotas;
    }

    public JButton getBtnVerNotas() {
        return btnVerNotas;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_periodolectivo = new javax.swing.JLabel();
        lbl_carrera = new javax.swing.JLabel();
        lbl_ciclo = new javax.swing.JLabel();
        lbl_paralelo = new javax.swing.JLabel();
        lbl_docente = new javax.swing.JLabel();
        lbl_asignatura = new javax.swing.JLabel();
        lbl_jornada = new javax.swing.JLabel();
        cmbPeriodoLectivo = new javax.swing.JComboBox<>();
        cmbCiclo = new javax.swing.JComboBox<>();
        cmb_paralelo = new javax.swing.JComboBox<>();
        cmbJornada = new javax.swing.JComboBox<>();
        cmbAsignatura = new javax.swing.JComboBox<>();
        cmbDocente = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNotas = new controlador.notas.ux.TableStyle();//javax.swing.JTable();
        btnImprimir = new javax.swing.JButton();
        lblCarrera = new javax.swing.JLabel();
        btnVerNotas = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Notas");

        lbl_periodolectivo.setText("Periodo Lectivo:");

        lbl_carrera.setText("Carrera:");

        lbl_ciclo.setText("Ciclo:");

        lbl_paralelo.setText("Paralelo:");

        lbl_docente.setText("Docente:");

        lbl_asignatura.setText("Asignatura:");

        lbl_jornada.setText("Jornada:");

        cmbDocente.setEnabled(false);

        tblNotas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblNotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "Cedula", "Apellidos", "Nombres", "APORTE 1   /30", "EXAMEN INTERCICLO", "TOTAL INTERCICLO", "APORTE 2  /30", "EXAMEN FINAL  /25", "/25 SUSP.", "NOTA FINAL", "ESTADO", "Nro. Faltas", "% Faltas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, false, true, true, true, false, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNotas.setColumnSelectionAllowed(true);
        tblNotas.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblNotas);
        tblNotas.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tblNotas.getColumnModel().getColumnCount() > 0) {
            tblNotas.getColumnModel().getColumn(0).setPreferredWidth(25);
            tblNotas.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblNotas.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblNotas.getColumnModel().getColumn(3).setPreferredWidth(150);
        }

        btnImprimir.setText("Imprimir");

        btnVerNotas.setText("Ver Notas");
        btnVerNotas.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_periodolectivo)
                            .addComponent(lbl_ciclo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_carrera, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(cmbCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lbl_paralelo)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmb_paralelo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cmbPeriodoLectivo, 0, 300, Short.MAX_VALUE))
                            .addComponent(lblCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(128, 128, 128)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(lbl_jornada))
                            .addComponent(lbl_asignatura, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_docente, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnVerNotas))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnImprimir))
                            .addComponent(cmbDocente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 132, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_periodolectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_docente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPeriodoLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_carrera, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVerNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ciclo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_jornada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_paralelo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_paralelo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbJornada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnVerNotas;
    private javax.swing.JComboBox<String> cmbAsignatura;
    private javax.swing.JComboBox<String> cmbCiclo;
    private javax.swing.JComboBox<String> cmbDocente;
    private javax.swing.JComboBox<String> cmbJornada;
    private javax.swing.JComboBox<String> cmbPeriodoLectivo;
    private javax.swing.JComboBox<String> cmb_paralelo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lbl_asignatura;
    private javax.swing.JLabel lbl_carrera;
    private javax.swing.JLabel lbl_ciclo;
    private javax.swing.JLabel lbl_docente;
    private javax.swing.JLabel lbl_jornada;
    private javax.swing.JLabel lbl_paralelo;
    private javax.swing.JLabel lbl_periodolectivo;
    private javax.swing.JTable tblNotas;
    // End of variables declaration//GEN-END:variables
}
