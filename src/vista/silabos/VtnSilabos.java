package vista.silabos;

import controlador.notas.ux.RowStyle;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import vista.AbstracView;

/**
 *
 * @author Andres Ullauri
 */
public class VtnSilabos extends AbstracView {

    /**
     * Creates new form frmSilabos
     */
    public VtnSilabos() {
        initComponents();
        bgImprimir.setVisible(false);
        InitDiseño();
    }

    private void InitDiseño() {
        tbl.setRowHeight(23);
        centrarCabecera(tbl);
        centrarCeldas(tbl);

    }

    public JComboBox<String> getCmbCarrera() {
        return cmbCarrera;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnImprimir() {
        return btnImprimir;
    }

    public JButton getBtnNuevo() {
        return btnNuevo;
    }

    public JScrollPane getSrcSilabos() {
        return srcSilabos;
    }

    public JTable getTbl() {
        return tbl;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JCheckBox getChxDualSemanas() {
        return chxDualSemanas;
    }

    public JPanel getBgImprimir() {
        return bgImprimir;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        srcSilabos = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        lblBuscar = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        cmbCarrera = new javax.swing.JComboBox<>();
        lblCarrera = new javax.swing.JLabel();
        bgImprimir = new javax.swing.JPanel();
        btnGenerar = new javax.swing.JButton();
        chbSilabo = new javax.swing.JCheckBox();
        chbProgramaAnalitico = new javax.swing.JCheckBox();
        lblSeleccionDocumento = new javax.swing.JLabel();
        chxDualSemanas = new javax.swing.JCheckBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setVisible(true);

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "No.", "Asignatura", "Periodo", "Estado", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl.setShowHorizontalLines(false);
        tbl.getTableHeader().setReorderingAllowed(false);
        srcSilabos.setViewportView(tbl);
        if (tbl.getColumnModel().getColumnCount() > 0) {
            tbl.getColumnModel().getColumn(0).setMinWidth(0);
            tbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbl.getColumnModel().getColumn(0).setMaxWidth(0);
            tbl.getColumnModel().getColumn(1).setMinWidth(40);
            tbl.getColumnModel().getColumn(1).setPreferredWidth(40);
            tbl.getColumnModel().getColumn(1).setMaxWidth(40);
            tbl.getColumnModel().getColumn(3).setPreferredWidth(220);
            tbl.getColumnModel().getColumn(3).setMaxWidth(220);
            tbl.getColumnModel().getColumn(4).setMinWidth(140);
            tbl.getColumnModel().getColumn(4).setPreferredWidth(140);
            tbl.getColumnModel().getColumn(4).setMaxWidth(140);
            tbl.getColumnModel().getColumn(5).setPreferredWidth(20);
        }

        lblBuscar.setText("Buscar:");

        btnEditar.setText("Editar");

        btnEliminar.setText("Eliminar");

        btnNuevo.setText("Nuevo");

        btnImprimir.setText("Imprimir");

        lblCarrera.setText("Carrera:");

        bgImprimir.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones de Impresion"));

        btnGenerar.setText("Generar");

        chbSilabo.setText("Silabo tradicionales");

        chbProgramaAnalitico.setText("Silabo Duales");

        lblSeleccionDocumento.setText("Seleccione el documento:");

        chxDualSemanas.setText("Silabos Duales / Semanas");

        javax.swing.GroupLayout bgImprimirLayout = new javax.swing.GroupLayout(bgImprimir);
        bgImprimir.setLayout(bgImprimirLayout);
        bgImprimirLayout.setHorizontalGroup(
            bgImprimirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgImprimirLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bgImprimirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGenerar, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(chbProgramaAnalitico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chxDualSemanas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chbSilabo, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSeleccionDocumento))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bgImprimirLayout.setVerticalGroup(
            bgImprimirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgImprimirLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(lblSeleccionDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chbProgramaAnalitico, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chbSilabo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chxDualSemanas, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBuscar)
                            .addComponent(lblCarrera))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbCarrera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnNuevo)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditar)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminar)
                                .addGap(38, 38, 38)))
                        .addGap(50, 50, 50)
                        .addComponent(btnImprimir)
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(srcSilabos, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bgImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnImprimir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCarrera)
                    .addComponent(cmbCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(srcSilabos, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bgImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtnGenerar() {
        return btnGenerar;
    }

    public JCheckBox getChbProgramaAnalitico() {
        return chbProgramaAnalitico;
    }

    public JCheckBox getChbSilabo() {
        return chbSilabo;
    }

    public JLabel getLblSeleccionDocumento() {
        return lblSeleccionDocumento;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bgImprimir;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JCheckBox chbProgramaAnalitico;
    private javax.swing.JCheckBox chbSilabo;
    private javax.swing.JCheckBox chxDualSemanas;
    private javax.swing.JComboBox<String> cmbCarrera;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblSeleccionDocumento;
    private javax.swing.JScrollPane srcSilabos;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
