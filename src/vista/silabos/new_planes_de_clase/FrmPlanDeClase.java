package vista.silabos.new_planes_de_clase;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTabbedPane;
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

    public void setBtnAgregar(JButton btnAgregar) {
        this.btnAgregar = btnAgregar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public void setBtnEditar(JButton btnEditar) {
        this.btnEditar = btnEditar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JButton getBtnQuitar() {
        return btnQuitar;
    }

    public void setBtnQuitar(JButton btnQuitar) {
        this.btnQuitar = btnQuitar;
    }

    public JLabel getLblInfo1() {
        return lblInfo1;
    }

    public void setLblInfo1(JLabel lblInfo1) {
        this.lblInfo1 = lblInfo1;
    }

    public JLabel getLblInfo2() {
        return lblInfo2;
    }

    public void setLblInfo2(JLabel lblInfo2) {
        this.lblInfo2 = lblInfo2;
    }

    public JLabel getLblInfo3() {
        return lblInfo3;
    }

    public void setLblInfo3(JLabel lblInfo3) {
        this.lblInfo3 = lblInfo3;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public JTable getTblRecursos() {
        return tblRecursos;
    }

    public void setTblRecursos(JTable tblRecursos) {
        this.tblRecursos = tblRecursos;
    }

    public JTabbedPane getTbpEstrategiasPC() {
        return tbpEstrategiasPC;
    }

    public void setTbpEstrategiasPC(JTabbedPane tbpEstrategiasPC) {
        this.tbpEstrategiasPC = tbpEstrategiasPC;
    }

    public JTextArea getTxtContenidos() {
        return txtContenidos;
    }

    public void setTxtContenidos(JTextArea txtContenidos) {
        this.txtContenidos = txtContenidos;
    }

    public JTextField getTxtDescripcion() {
        return txtDescripcion;
    }

    public void setTxtDescripcion(JTextField txtDescripcion) {
        this.txtDescripcion = txtDescripcion;
    }

    public JList<String> getTxtEstrategiasUnidad() {
        return txtEstrategiasUnidad;
    }

    public void setTxtEstrategiasUnidad(JList<String> txtEstrategiasUnidad) {
        this.txtEstrategiasUnidad = txtEstrategiasUnidad;
    }

    public JTextArea getTxtObjetivos() {
        return txtObjetivos;
    }

    public void setTxtObjetivos(JTextArea txtObjetivos) {
        this.txtObjetivos = txtObjetivos;
    }

    public JTextArea getTxtObservaciones() {
        return txtObservaciones;
    }

    public void setTxtObservaciones(JTextArea txtObservaciones) {
        this.txtObservaciones = txtObservaciones;
    }

    public JTextArea getTxtResultadosAprendizaje() {
        return txtResultadosAprendizaje;
    }

    public void setTxtResultadosAprendizaje(JTextArea txtResultadosAprendizaje) {
        this.txtResultadosAprendizaje = txtResultadosAprendizaje;
    }

    public JTextArea getTxtTrabajoAutonomo() {
        return txtTrabajoAutonomo;
    }

    public void setTxtTrabajoAutonomo(JTextArea txtTrabajoAutonomo) {
        this.txtTrabajoAutonomo = txtTrabajoAutonomo;
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
        tbpEstrategiasPC = new javax.swing.JTabbedPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        listAnticipacion = new javax.swing.JList<>();
        jScrollPane9 = new javax.swing.JScrollPane();
        listConstruccion = new javax.swing.JList<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        listConsolidacion = new javax.swing.JList<>();
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
        btnEditar = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblRecursos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtTrabajoAutonomo = new javax.swing.JTextArea();
        lblTitulo = new javax.swing.JLabel();
        lbObservacionesPC = new javax.swing.JLabel();
        lblInfo3 = new javax.swing.JLabel();

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

        jScrollPane11.setViewportView(listAnticipacion);

        tbpEstrategiasPC.addTab("Anticipacion", jScrollPane11);

        jScrollPane9.setViewportView(listConstruccion);

        tbpEstrategiasPC.addTab("Construccion", jScrollPane9);

        jScrollPane10.setViewportView(listConsolidacion);

        tbpEstrategiasPC.addTab("Consolidacion", jScrollPane10);

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

        btnEditar.setText("Editar");

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
                false, false, false
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
                        .addComponent(lbRecursosPC, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(452, 452, 452)
                        .addComponent(lbEstrategiasPC, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(bgLayout.createSequentialGroup()
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(11, 11, 11)
                            .addComponent(btnGuardar))
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
                                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tbpEstrategiasPC, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(bgLayout.createSequentialGroup()
                                            .addGap(2, 2, 2)
                                            .addComponent(btnAgregar))
                                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 142, Short.MAX_VALUE))
                            .addGroup(bgLayout.createSequentialGroup()
                                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(tbpEstrategiasPC, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbIns_Evaluacion, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane7))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar))
                .addContainerGap())
        );

        jScrollPane4.setViewportView(bg);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1184, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnQuitar;
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
    private javax.swing.JLabel lbContenidosPC;
    private javax.swing.JLabel lbEstrategiasPC;
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
    private javax.swing.JList<String> listAnticipacion;
    private javax.swing.JList<String> listConsolidacion;
    private javax.swing.JList<String> listConstruccion;
    private javax.swing.JTable tblRecursos;
    private javax.swing.JTabbedPane tbpEstrategiasPC;
    private javax.swing.JTextArea txtContenidos;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JList<String> txtEstrategiasUnidad;
    private javax.swing.JTextArea txtObjetivos;
    private javax.swing.JTextArea txtObservaciones;
    private javax.swing.JTextArea txtResultadosAprendizaje;
    private javax.swing.JTextArea txtTrabajoAutonomo;
    // End of variables declaration//GEN-END:variables

}
