package vista.silabos.new_planes_de_clase;

import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import vista.AbstracView;

/**
 *
 * @author MrRainx
 */
public class FrmPlanDeClase extends AbstracView {

    public FrmPlanDeClase() {
        initComponents();
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnQuitar() {
        return btnQuitar;
    }

    public JLabel getLblInfo1() {
        return lblInfo1;
    }

    public JLabel getLblInfo2() {
        return lblInfo2;
    }

    public JLabel getLblInfo3() {
        return lblInfo3;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public JTable getTblRecursos() {
        return tblRecursos;
    }

    public JTable getTblEstrategias() {
        return tblEstrategias;
    }

    public JTextArea getTxtContenidos() {
        return txtContenidos;
    }

    public JTextField getTxtDescripcion() {
        return txtDescripcion;
    }

    public JList<String> getTxtEstrategiasUnidad() {
        return txtEstrategiasUnidad;
    }

    public JTextArea getTxtObjetivos() {
        return txtObjetivos;
    }

    public JTextArea getTxtObservaciones() {
        return txtObservaciones;
    }

    public JTextArea getTxtResultadosAprendizaje() {
        return txtResultadosAprendizaje;
    }

    public JTextArea getTxtTrabajoAutonomo() {
        return txtTrabajoAutonomo;
    }

    public JComboBox<String> getCmbTipo() {
        return cmbTipo;
    }

    public void setCmbTipo(JComboBox<String> cmbTipo) {
        this.cmbTipo = cmbTipo;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane4 = new javax.swing.JScrollPane();
        bg = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        lblInfo1 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtObservaciones = new javax.swing.JTextArea();
        lbObjetivoPC = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtResultadosAprendizaje = new javax.swing.JTextArea();
        lbContenidosPC = new javax.swing.JLabel();
        lbResultadosAprendizaje = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtEstrategiasUnidad = new javax.swing.JList<>();
        lbEstrategiasPC = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObjetivos = new javax.swing.JTextArea();
        lbRecursosPC = new javax.swing.JLabel();
        lblInfo2 = new javax.swing.JLabel();
        lbIns_Evaluacion = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        lbTrabajoAutonomo = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtContenidos = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblRecursos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtTrabajoAutonomo = new javax.swing.JTextArea();
        lblTitulo = new javax.swing.JLabel();
        lbObservacionesPC = new javax.swing.JLabel();
        lblInfo3 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblEstrategias = new javax.swing.JTable();
        lbEstrategiasPC1 = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);

        btnGuardar.setText("Guardar");

        lblInfo1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblInfo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo1.setText("Fecha de Inicio: {fecha_inicio}   Fecha de Fin: {fecha_fin}   Duración: {duracion}");

        btnCancelar.setText("Atrás");

        txtObservaciones.setColumns(20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setRows(5);
        txtObservaciones.setWrapStyleWord(true);
        jScrollPane7.setViewportView(txtObservaciones);

        lbObjetivoPC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbObjetivoPC.setText("Objetivos:");

        txtResultadosAprendizaje.setEditable(false);
        txtResultadosAprendizaje.setColumns(20);
        txtResultadosAprendizaje.setLineWrap(true);
        txtResultadosAprendizaje.setRows(5);
        txtResultadosAprendizaje.setWrapStyleWord(true);
        jScrollPane8.setViewportView(txtResultadosAprendizaje);

        lbContenidosPC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbContenidosPC.setText("Contenidos:");

        lbResultadosAprendizaje.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbResultadosAprendizaje.setText("Resultados de Aprendizaje:");

        jScrollPane3.setViewportView(txtEstrategiasUnidad);

        lbEstrategiasPC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbEstrategiasPC.setText("Descripción:");

        txtObjetivos.setEditable(false);
        txtObjetivos.setColumns(20);
        txtObjetivos.setLineWrap(true);
        txtObjetivos.setRows(5);
        txtObjetivos.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtObjetivos);

        lbRecursosPC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbRecursosPC.setText("Recursos:");

        lblInfo2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblInfo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo2.setText("Carrera: {carrera}   Asignatura: {asignatura}   Código Asignatura: {codigoAsignatura}");

        lbIns_Evaluacion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbIns_Evaluacion.setText("Estrategias Unidad:");

        btnAgregar.setText("Agregar");

        btnQuitar.setText("Quitar");

        lbTrabajoAutonomo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbTrabajoAutonomo.setText("Trabajo Autónomo:");

        txtContenidos.setColumns(20);
        txtContenidos.setLineWrap(true);
        txtContenidos.setRows(5);
        txtContenidos.setWrapStyleWord(true);
        jScrollPane5.setViewportView(txtContenidos);

        tblRecursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Recurso", "Check"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRecursos.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tblRecursos);
        if (tblRecursos.getColumnModel().getColumnCount() > 0) {
            tblRecursos.getColumnModel().getColumn(0).setMinWidth(40);
            tblRecursos.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblRecursos.getColumnModel().getColumn(0).setMaxWidth(40);
            tblRecursos.getColumnModel().getColumn(2).setMinWidth(40);
            tblRecursos.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblRecursos.getColumnModel().getColumn(2).setMaxWidth(40);
        }

        txtTrabajoAutonomo.setColumns(20);
        txtTrabajoAutonomo.setLineWrap(true);
        txtTrabajoAutonomo.setRows(5);
        txtTrabajoAutonomo.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtTrabajoAutonomo);

        lblTitulo.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Plan de Clase Unidad Nº {numero} {titulo}");

        lbObservacionesPC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbObservacionesPC.setText("Observaciones:");

        lblInfo3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lblInfo3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo3.setText("Curso: {curso} Docente: {docente}");

        tblEstrategias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Estrategia", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEstrategias.getTableHeader().setReorderingAllowed(false);
        jScrollPane12.setViewportView(tblEstrategias);
        if (tblEstrategias.getColumnModel().getColumnCount() > 0) {
            tblEstrategias.getColumnModel().getColumn(0).setMinWidth(40);
            tblEstrategias.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblEstrategias.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        lbEstrategiasPC1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbEstrategiasPC1.setText("Tipo:");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Anticipacion", "Consolidacion", "Construccion" }));

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblInfo3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblInfo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblInfo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 1303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbObjetivoPC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbResultadosAprendizaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5)
                            .addComponent(lbContenidosPC, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bgLayout.createSequentialGroup()
                                .addComponent(lbRecursosPC, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbTrabajoAutonomo, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(bgLayout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbObservacionesPC, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbIns_Evaluacion)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(bgLayout.createSequentialGroup()
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                                    .addGroup(bgLayout.createSequentialGroup()
                                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbEstrategiasPC1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDescripcion, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lbEstrategiasPC, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnQuitar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(2, 2, 2))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(btnGuardar)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfo2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfo3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbObjetivoPC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbResultadosAprendizaje, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbContenidosPC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(lbRecursosPC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bgLayout.createSequentialGroup()
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbTrabajoAutonomo, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbEstrategiasPC1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))
                            .addGroup(bgLayout.createSequentialGroup()
                                .addComponent(lbEstrategiasPC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addGroup(bgLayout.createSequentialGroup()
                                        .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbObservacionesPC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbIns_Evaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(jScrollPane7))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar))
                .addGap(21, 21, 21))
        );

        jScrollPane4.setViewportView(bg);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lbContenidosPC;
    private javax.swing.JLabel lbEstrategiasPC;
    private javax.swing.JLabel lbEstrategiasPC1;
    private javax.swing.JLabel lbIns_Evaluacion;
    private javax.swing.JLabel lbObjetivoPC;
    private javax.swing.JLabel lbObservacionesPC;
    private javax.swing.JLabel lbRecursosPC;
    private javax.swing.JLabel lbResultadosAprendizaje;
    private javax.swing.JLabel lbTrabajoAutonomo;
    private javax.swing.JLabel lblInfo1;
    private javax.swing.JLabel lblInfo2;
    private javax.swing.JLabel lblInfo3;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblEstrategias;
    private javax.swing.JTable tblRecursos;
    private javax.swing.JTextArea txtContenidos;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JList<String> txtEstrategiasUnidad;
    private javax.swing.JTextArea txtObjetivos;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextArea txtResultadosAprendizaje;
    private javax.swing.JTextArea txtTrabajoAutonomo;
    // End of variables declaration//GEN-END:variables

}
