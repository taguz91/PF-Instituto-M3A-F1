package vista.notas;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Alejandro
 */
public class VtnNotasAlumnoCurso extends javax.swing.JInternalFrame {

    private double valorMinimo;

    public VtnNotasAlumnoCurso() {

        initComponents();
        tabDuales.setVisible(false);
        jTable1.setVisible(false);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("----------->");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

//        tabPane.setVisible(false);

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

    public JComboBox<String> getCmbPeriodoLectivo() {
        return cmbPeriodoLectivo;
    }

    public JLabel getLblCarrera() {
        return lblCarrera;
    }

    public JButton getBtnVerNotas() {
        return btnVerNotas;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JLabel getLblEstado() {
        return lblEstado;
    }

    public void setLblEstado(JLabel lblEstado) {
        this.lblEstado = lblEstado;
    }

    public JTable getTblNotas() {
        return tblNotas;
    }

    public void setTblNotas(JTable tblNotas) {
        this.tblNotas = tblNotas;
    }

    public JScrollPane getjScrollPane2() {
        return tabDuales;
    }

    public boolean[] canEdit = new boolean[]{
        false, false, false, false, true, true, true, true, true, true, true, true, true, true, true
    };

    public double getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public JLabel getLblResultados() {
        return lblResultados;
    }

    public JLabel getLblHoras() {
        return lblHoras;
    }

    public JScrollPane getTabTradicionales() {
        return tabTradicionales;
    }

    public JScrollPane getTabDuales() {
        return tabDuales;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_periodolectivo = new javax.swing.JLabel();
        lbl_carrera = new javax.swing.JLabel();
        lbl_ciclo = new javax.swing.JLabel();
        lbl_docente = new javax.swing.JLabel();
        lbl_asignatura = new javax.swing.JLabel();
        cmbPeriodoLectivo = new javax.swing.JComboBox<>();
        cmbCiclo = new javax.swing.JComboBox<>();
        cmbAsignatura = new javax.swing.JComboBox<>();
        cmbDocente = new javax.swing.JComboBox<>();
        btnImprimir = new javax.swing.JButton();
        lblCarrera = new javax.swing.JLabel();
        btnVerNotas = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        lblResultados = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblHoras = new javax.swing.JLabel();
        tabPane = new javax.swing.JTabbedPane();
        tabDuales = new javax.swing.JScrollPane();
        tblNotas = new javax.swing.JTable();
        tabTradicionales = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Notas");
        setMaximumSize(new java.awt.Dimension(1243, 500));
        setMinimumSize(new java.awt.Dimension(1243, 500));

        lbl_periodolectivo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_periodolectivo.setText("Período lectivo:");

        lbl_carrera.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_carrera.setText("Carrera:");

        lbl_ciclo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_ciclo.setText("Ciclo:");

        lbl_docente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_docente.setText("Docente:");

        lbl_asignatura.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_asignatura.setText("Asignatura:");

        btnImprimir.setText("Imprimir");
        btnImprimir.setEnabled(false);

        btnVerNotas.setText("Ver Notas");
        btnVerNotas.setEnabled(false);

        btnBuscar.setText("Buscar");

        lblResultados.setText("0 Resultados");

        lblEstado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(153, 255, 153));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Horas Presenciales:");

        lblHoras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        tblNotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cedula", "P. Apellido", "S.Apellido", "P.Nombre", "S.Nombre", "Aporte 1", "Ex. Interciclo", "T. Interciclo", "Aporte 2", "Ex. Final", "Ex. Supletorio", "Not. Final", "Estado", "Nro. Faltas", "% Faltas", "Asistencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true, false, true, true, true, false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNotas.setSelectionBackground(new java.awt.Color(102, 102, 102));
        tblNotas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblNotas.getTableHeader().setReorderingAllowed(false);
        tabDuales.setViewportView(tblNotas);
        if (tblNotas.getColumnModel().getColumnCount() > 0) {
            tblNotas.getColumnModel().getColumn(0).setMinWidth(30);
            tblNotas.getColumnModel().getColumn(0).setMaxWidth(30);
            tblNotas.getColumnModel().getColumn(1).setMinWidth(90);
            tblNotas.getColumnModel().getColumn(1).setMaxWidth(90);
            tblNotas.getColumnModel().getColumn(6).setMinWidth(55);
            tblNotas.getColumnModel().getColumn(6).setMaxWidth(55);
            tblNotas.getColumnModel().getColumn(7).setMinWidth(80);
            tblNotas.getColumnModel().getColumn(7).setMaxWidth(80);
            tblNotas.getColumnModel().getColumn(8).setResizable(false);
            tblNotas.getColumnModel().getColumn(9).setMinWidth(55);
            tblNotas.getColumnModel().getColumn(9).setMaxWidth(55);
            tblNotas.getColumnModel().getColumn(10).setMinWidth(60);
            tblNotas.getColumnModel().getColumn(10).setMaxWidth(60);
            tblNotas.getColumnModel().getColumn(11).setMinWidth(90);
            tblNotas.getColumnModel().getColumn(11).setMaxWidth(90);
            tblNotas.getColumnModel().getColumn(12).setResizable(false);
            tblNotas.getColumnModel().getColumn(13).setMinWidth(100);
            tblNotas.getColumnModel().getColumn(13).setMaxWidth(100);
            tblNotas.getColumnModel().getColumn(14).setMinWidth(70);
            tblNotas.getColumnModel().getColumn(14).setMaxWidth(70);
            tblNotas.getColumnModel().getColumn(15).setMinWidth(60);
            tblNotas.getColumnModel().getColumn(15).setMaxWidth(60);
            tblNotas.getColumnModel().getColumn(16).setMinWidth(70);
            tblNotas.getColumnModel().getColumn(16).setMaxWidth(70);
        }

        tabPane.addTab("Carrera Tradicional", tabDuales);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabTradicionales.setViewportView(jTable1);

        tabPane.addTab("Carrera Dual", tabTradicionales);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tabPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(113, 113, 113)
                                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_periodolectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_ciclo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_carrera, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmbPeriodoLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lbl_asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lbl_docente, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cmbCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(183, 183, 183)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmbAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnVerNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(64, 64, 64)
                                        .addComponent(btnBuscar)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_periodolectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbPeriodoLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_carrera, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl_docente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_ciclo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVerNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPane, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnVerNotas;
    private javax.swing.JComboBox<String> cmbAsignatura;
    private javax.swing.JComboBox<String> cmbCiclo;
    private javax.swing.JComboBox<String> cmbDocente;
    private javax.swing.JComboBox<String> cmbPeriodoLectivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblHoras;
    private javax.swing.JLabel lblResultados;
    private javax.swing.JLabel lbl_asignatura;
    private javax.swing.JLabel lbl_carrera;
    private javax.swing.JLabel lbl_ciclo;
    private javax.swing.JLabel lbl_docente;
    private javax.swing.JLabel lbl_periodolectivo;
    private javax.swing.JScrollPane tabDuales;
    private javax.swing.JTabbedPane tabPane;
    private javax.swing.JScrollPane tabTradicionales;
    private javax.swing.JTable tblNotas;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

}
