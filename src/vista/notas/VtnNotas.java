package vista.notas;

import controlador.notas.ux.RowStyle;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import vista.AbstracView;

/**
 *
 * @author MrRainx
 */
public class VtnNotas extends AbstracView {

    public VtnNotas() {

        initComponents();
        InitDiseño();
    }

    private void InitDiseño() {
        tblTrad.setRowHeight(23);
        tblDual.setRowHeight(23);
        tblTrad.setDefaultRenderer(Object.class, new RowStyle(13));
        tblDual.setDefaultRenderer(Object.class, new RowStyle(12));

        centrarCabecera(tblTrad);

        centrarCabecera(tblDual);

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

    public JTable getTblTrad() {
        return tblTrad;
    }

    public JScrollPane getjScrollPane2() {
        return tabDuales;
    }

    public JLabel getLblResultados() {
        return lblResultados;
    }

    public JLabel getLblHoras() {
        return lblHoras;
    }

    public JScrollPane getTabDuales() {
        return tabDuales;
    }

    public JTabbedPane getTabPane() {
        return tabPane;
    }

    public JTable getTblDual() {
        return tblDual;
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
        tblTrad = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDual = new javax.swing.JTable();
        btnImprimir1 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Notas");
        setMaximumSize(new java.awt.Dimension(1280, 720));
        setMinimumSize(new java.awt.Dimension(1060, 550));

        lbl_periodolectivo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_periodolectivo.setText("Período lectivo:");
        lbl_periodolectivo.setMinimumSize(null);

        lbl_carrera.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_carrera.setText("Carrera:");
        lbl_carrera.setMinimumSize(null);

        lbl_ciclo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_ciclo.setText("Ciclo:");
        lbl_ciclo.setMinimumSize(null);

        lbl_docente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_docente.setText("Docente:");
        lbl_docente.setMinimumSize(null);

        lbl_asignatura.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_asignatura.setText("Asignatura:");
        lbl_asignatura.setMinimumSize(null);

        cmbPeriodoLectivo.setMinimumSize(null);

        cmbCiclo.setMinimumSize(null);

        cmbAsignatura.setMinimumSize(null);

        cmbDocente.setMinimumSize(null);

        btnImprimir.setText("Imprimir");
        btnImprimir.setMinimumSize(null);

        lblCarrera.setMinimumSize(null);

        btnVerNotas.setText("Cargar Notas");
        btnVerNotas.setEnabled(false);

        txtBuscar.setMinimumSize(null);

        btnBuscar.setText("Buscar");
        btnBuscar.setMinimumSize(null);

        lblResultados.setText("0 Resultados");

        lblEstado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(153, 255, 153));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Horas Presenciales:");
        jLabel1.setMinimumSize(null);

        lblHoras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHoras.setMinimumSize(null);

        tabPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        tblTrad.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Cedula", "P. Apellido", "S.Apellido", "P.Nombre", "S.Nombre", "Aporte 1", "Ex. Interciclo", "T. Interciclo", "Aporte 2", "Ex. Final", "Ex. Recuperacion", "Not. Final", "Estado", "Faltas", "% Faltas", "Asistencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true, false, true, true, true, false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTrad.setSelectionBackground(new java.awt.Color(102, 102, 102));
        tblTrad.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblTrad.getTableHeader().setReorderingAllowed(false);
        tabDuales.setViewportView(tblTrad);
        if (tblTrad.getColumnModel().getColumnCount() > 0) {
            tblTrad.getColumnModel().getColumn(0).setMinWidth(40);
            tblTrad.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblTrad.getColumnModel().getColumn(0).setMaxWidth(40);
            tblTrad.getColumnModel().getColumn(1).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(2).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(3).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(4).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(5).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(6).setMinWidth(55);
            tblTrad.getColumnModel().getColumn(6).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(7).setMinWidth(80);
            tblTrad.getColumnModel().getColumn(7).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(8).setMinWidth(80);
            tblTrad.getColumnModel().getColumn(8).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(9).setMinWidth(55);
            tblTrad.getColumnModel().getColumn(9).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(10).setMinWidth(70);
            tblTrad.getColumnModel().getColumn(10).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(11).setMinWidth(80);
            tblTrad.getColumnModel().getColumn(11).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(12).setMinWidth(70);
            tblTrad.getColumnModel().getColumn(12).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(13).setMinWidth(85);
            tblTrad.getColumnModel().getColumn(13).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(14).setMinWidth(50);
            tblTrad.getColumnModel().getColumn(14).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(15).setMinWidth(60);
            tblTrad.getColumnModel().getColumn(15).setMaxWidth(100);
            tblTrad.getColumnModel().getColumn(16).setMinWidth(70);
            tblTrad.getColumnModel().getColumn(16).setMaxWidth(100);
        }

        tabPane.addTab("Carrera Tradicional", tabDuales);

        tblDual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Identificacion", "P. Apellido", "S. Apellido", "P. Nombre", "S. Nombre", "Aporte 1", "Aporte 2", "Total G. Aula", "Ex. final", "Ex. Recuperacion", "Nota Final", "Estado", "Faltas", "% Faltas", "Asistencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true, false, true, true, true, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDual.setColumnSelectionAllowed(true);
        tblDual.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblDual);
        tblDual.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tblDual.getColumnModel().getColumnCount() > 0) {
            tblDual.getColumnModel().getColumn(0).setMinWidth(50);
            tblDual.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblDual.getColumnModel().getColumn(0).setMaxWidth(50);
            tblDual.getColumnModel().getColumn(1).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(2).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(3).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(4).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(5).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(6).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(7).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(8).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(9).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(10).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(11).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(12).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(13).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(14).setMaxWidth(100);
            tblDual.getColumnModel().getColumn(15).setMaxWidth(100);
        }

        tabPane.addTab("Carreras Duales", jScrollPane1);

        btnImprimir1.setText("Ver Informacion de los Tipos de Nota");

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
                            .addComponent(lbl_periodolectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_ciclo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_carrera, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnVerNotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113)
                        .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnImprimir1)))
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
                                    .addComponent(lbl_periodolectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbPeriodoLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_carrera, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl_docente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_ciclo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVerNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPane, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnImprimir1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnImprimir1;
    private javax.swing.JButton btnVerNotas;
    private javax.swing.JComboBox<String> cmbAsignatura;
    private javax.swing.JComboBox<String> cmbCiclo;
    private javax.swing.JComboBox<String> cmbDocente;
    private javax.swing.JComboBox<String> cmbPeriodoLectivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JTable tblDual;
    private javax.swing.JTable tblTrad;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

}
