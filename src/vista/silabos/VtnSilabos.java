package vista.silabos;

import controlador.notas.ux.RowStyle;
import java.awt.Color;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import vista.AbstracView;

/**
 *
 * @author MrRainx
 */
public class VtnSilabos extends AbstracView {

    public VtnSilabos() {
        initComponents();
        InitDiseño();
        lblEstado.setVisible(false);
    }

    private void InitDiseño() {
        tbl.setRowHeight(23);
        centrarCabecera(tbl);
        centrarCeldas(tbl);

        RowStyle rowStyle = new RowStyle(5);
        rowStyle.setEstados(new HashMap<String, Color>() {
            {
                put("PENDIENTE", new Color(0, 0, 0));
                put("APROBADO", new Color(37, 107, 187));
                put("REVISAR", new Color(214, 48, 12));
            }
        });
        Color bgColor = new Color(252, 252, 252);
        rowStyle.setBgColor(bgColor);

        tbl.setDefaultRenderer(Object.class, rowStyle);

    }

    public JComboBox<String> getCmbPeriodo() {
        return cmbPeriodo;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
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

    public JButton getBtnImprimir() {
        return btnImprimir;
    }

    public JButton getBtnInformacion() {
        return btnInformacion;
    }

    public JButton getBtnRefresh() {
        return btnRefresh;
    }

    public JLabel getLblEstado() {
        return lblEstado;
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
        cmbPeriodo = new javax.swing.JComboBox<>();
        lblCarrera = new javax.swing.JLabel();
        btnImprimir = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnInformacion = new javax.swing.JButton();
        lblEstado = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Silabos");
        setVisible(true);

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "No.", "Asignatura", "Periodo", "Ultima Actualizacion", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl.setShowHorizontalLines(false);
        tbl.getTableHeader().setReorderingAllowed(false);
        srcSilabos.setViewportView(tbl);
        if (tbl.getColumnModel().getColumnCount() > 0) {
            tbl.getColumnModel().getColumn(0).setMinWidth(40);
            tbl.getColumnModel().getColumn(0).setPreferredWidth(40);
            tbl.getColumnModel().getColumn(0).setMaxWidth(40);
            tbl.getColumnModel().getColumn(1).setMinWidth(40);
            tbl.getColumnModel().getColumn(1).setPreferredWidth(40);
            tbl.getColumnModel().getColumn(1).setMaxWidth(40);
            tbl.getColumnModel().getColumn(3).setPreferredWidth(220);
            tbl.getColumnModel().getColumn(4).setMinWidth(110);
            tbl.getColumnModel().getColumn(4).setPreferredWidth(110);
            tbl.getColumnModel().getColumn(4).setMaxWidth(110);
            tbl.getColumnModel().getColumn(5).setMinWidth(100);
            tbl.getColumnModel().getColumn(5).setPreferredWidth(100);
            tbl.getColumnModel().getColumn(5).setMaxWidth(100);
        }

        lblBuscar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblBuscar.setText("Buscar:");

        btnEditar.setText("Editar");

        btnEliminar.setText("Eliminar");

        btnNuevo.setText("Nuevo");

        lblCarrera.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCarrera.setText("Periodo lectivo:");

        btnImprimir.setText("Imprimir");

        btnRefresh.setText("Refrescar");

        btnInformacion.setText("Informacion");

        lblEstado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(0, 204, 51));
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstado.setText("${ESTADO}");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(srcSilabos)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCarrera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBuscar)
                            .addComponent(cmbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnImprimir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInformacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(srcSilabos, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblEstado)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnInformacion;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox<String> cmbPeriodo;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JScrollPane srcSilabos;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
