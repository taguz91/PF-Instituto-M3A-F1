package vista.silabos.new_planes_de_clase;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import vista.AbstracView;

/**
 *
 * @author MrRainx
 */
public class FrmPlanDeClase extends AbstracView {

    /**
     * Creates new form frmPlanClase
     */
    public FrmPlanDeClase() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane4 = new javax.swing.JScrollPane();
        bg = new javax.swing.JPanel();
        btmnGuardarPc = new javax.swing.JButton();
        lbltitulo3 = new javax.swing.JLabel();
        btnCancelarPC = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        txrObservacionesPc = new javax.swing.JTextArea();
        lbObjetivoPC = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txrRsAprendizaje = new javax.swing.JTextArea();
        lbContenidosPC = new javax.swing.JLabel();
        lbResultadosAprendizaje = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listInstrumentos = new javax.swing.JList<>();
        tbpEstrategiasPC = new javax.swing.JTabbedPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        listAnticipacionPC = new javax.swing.JList<>();
        jScrollPane9 = new javax.swing.JScrollPane();
        listConstruccionPC = new javax.swing.JList<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        listConsolidacionPC = new javax.swing.JList<>();
        lbEstrategiasPC = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObjetivo = new javax.swing.JTextArea();
        lbRecursosPC = new javax.swing.JLabel();
        lbltitulo = new javax.swing.JLabel();
        lbIns_Evaluacion = new javax.swing.JLabel();
        btnAgregarPC = new javax.swing.JButton();
        btnQuitarPC = new javax.swing.JButton();
        lbTrabajoAutonomo = new javax.swing.JLabel();
        txtEstrategias = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txrContenidos = new javax.swing.JTextArea();
        btnEditar = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txrTrabajoAutonomo = new javax.swing.JTextArea();
        lbltitulo1 = new javax.swing.JLabel();
        lbObservacionesPC = new javax.swing.JLabel();
        lbltitulo2 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);

        btmnGuardarPc.setText("Guardar");

        lbltitulo3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lbltitulo3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbltitulo3.setText("Fecha de Inicio: {asignatura}   Fecha de Fin: {codigoAsignatura}   Duración: {duracion}");

        btnCancelarPC.setText("Atrás");

        txrObservacionesPc.setColumns(20);
        txrObservacionesPc.setLineWrap(true);
        txrObservacionesPc.setRows(5);
        txrObservacionesPc.setWrapStyleWord(true);
        jScrollPane7.setViewportView(txrObservacionesPc);

        lbObjetivoPC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbObjetivoPC.setText("Objetivos:");

        txrRsAprendizaje.setEditable(false);
        txrRsAprendizaje.setColumns(20);
        txrRsAprendizaje.setLineWrap(true);
        txrRsAprendizaje.setRows(5);
        txrRsAprendizaje.setWrapStyleWord(true);
        jScrollPane8.setViewportView(txrRsAprendizaje);

        lbContenidosPC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbContenidosPC.setText("Contenidos:");

        lbResultadosAprendizaje.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbResultadosAprendizaje.setText("Resultados de Aprendizaje:");

        jScrollPane3.setViewportView(listInstrumentos);

        jScrollPane11.setViewportView(listAnticipacionPC);

        tbpEstrategiasPC.addTab("Anticipacion", jScrollPane11);

        jScrollPane9.setViewportView(listConstruccionPC);

        tbpEstrategiasPC.addTab("Construccion", jScrollPane9);

        jScrollPane10.setViewportView(listConsolidacionPC);

        tbpEstrategiasPC.addTab("Consolidacion", jScrollPane10);

        lbEstrategiasPC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbEstrategiasPC.setText("Descripción:");

        txtObjetivo.setEditable(false);
        txtObjetivo.setColumns(20);
        txtObjetivo.setLineWrap(true);
        txtObjetivo.setRows(5);
        txtObjetivo.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtObjetivo);

        lbRecursosPC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbRecursosPC.setText("Recursos:");

        lbltitulo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lbltitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbltitulo.setText("Carrera: {carrera}   Asignatura: {asignatura}   Código Asignatura: {codigoAsignatura}");

        lbIns_Evaluacion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbIns_Evaluacion.setText("Estrategias Unidad:");

        btnAgregarPC.setText("Agregar");

        btnQuitarPC.setText("Quitar");

        lbTrabajoAutonomo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbTrabajoAutonomo.setText("Trabajo Autónomo:");

        txrContenidos.setColumns(20);
        txrContenidos.setLineWrap(true);
        txrContenidos.setRows(5);
        txrContenidos.setWrapStyleWord(true);
        jScrollPane5.setViewportView(txrContenidos);

        btnEditar.setText("Editar");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(40);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(40);
            jTable1.getColumnModel().getColumn(2).setMinWidth(40);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(40);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(40);
        }

        txrTrabajoAutonomo.setColumns(20);
        txrTrabajoAutonomo.setLineWrap(true);
        txrTrabajoAutonomo.setRows(5);
        txrTrabajoAutonomo.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txrTrabajoAutonomo);

        lbltitulo1.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        lbltitulo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbltitulo1.setText("Plan de Clase Unidad Nº {numero} {titulo}");

        lbObservacionesPC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbObservacionesPC.setText("Observaciones:");

        lbltitulo2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lbltitulo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbltitulo2.setText("Curso: {curso} Docente: {carrera}");

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lbltitulo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbltitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbltitulo3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbltitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 1303, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(lbRecursosPC, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(452, 452, 452)
                        .addComponent(lbEstrategiasPC, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(bgLayout.createSequentialGroup()
                            .addComponent(btnCancelarPC, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(11, 11, 11)
                            .addComponent(btmnGuardarPc))
                        .addGroup(bgLayout.createSequentialGroup()
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbObservacionesPC, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbTrabajoAutonomo, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbIns_Evaluacion)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(bgLayout.createSequentialGroup()
                                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtEstrategias, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tbpEstrategiasPC, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(bgLayout.createSequentialGroup()
                                            .addGap(2, 2, 2)
                                            .addComponent(btnAgregarPC))
                                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnQuitarPC, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbltitulo1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbltitulo3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbltitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbltitulo2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbRecursosPC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTrabajoAutonomo, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEstrategiasPC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bgLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbObservacionesPC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(bgLayout.createSequentialGroup()
                                .addComponent(btnAgregarPC, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnQuitarPC, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 142, Short.MAX_VALUE))
                            .addGroup(bgLayout.createSequentialGroup()
                                .addComponent(txtEstrategias, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(tbpEstrategiasPC, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbIns_Evaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane7))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelarPC)
                    .addComponent(btmnGuardarPc))
                .addContainerGap())
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
            .addComponent(jScrollPane4)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JButton btmnGuardarPc;
    private javax.swing.JButton btnAgregarPC;
    private javax.swing.JButton btnCancelarPC;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnQuitarPC;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbContenidosPC;
    private javax.swing.JLabel lbEstrategiasPC;
    private javax.swing.JLabel lbIns_Evaluacion;
    private javax.swing.JLabel lbObjetivoPC;
    private javax.swing.JLabel lbObservacionesPC;
    private javax.swing.JLabel lbRecursosPC;
    private javax.swing.JLabel lbResultadosAprendizaje;
    private javax.swing.JLabel lbTrabajoAutonomo;
    private javax.swing.JLabel lbltitulo;
    private javax.swing.JLabel lbltitulo1;
    private javax.swing.JLabel lbltitulo2;
    private javax.swing.JLabel lbltitulo3;
    private javax.swing.JList<String> listAnticipacionPC;
    private javax.swing.JList<String> listConsolidacionPC;
    private javax.swing.JList<String> listConstruccionPC;
    private javax.swing.JList<String> listInstrumentos;
    private javax.swing.JTabbedPane tbpEstrategiasPC;
    private javax.swing.JTextArea txrContenidos;
    private javax.swing.JTextArea txrObservacionesPc;
    private javax.swing.JTextArea txrRsAprendizaje;
    private javax.swing.JTextArea txrTrabajoAutonomo;
    private javax.swing.JTextField txtEstrategias;
    private javax.swing.JTextArea txtObjetivo;
    // End of variables declaration//GEN-END:variables

}
