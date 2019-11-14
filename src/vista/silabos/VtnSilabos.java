package vista.silabos;

import controlador.notas.ux.RowStyle;
import java.awt.Color;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

    public JComboBox<String> getCmbCarrera() {
        return cmbCarrera;
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
        cmbCarrera = new javax.swing.JComboBox<>();
        lblCarrera = new javax.swing.JLabel();
        btnImprimir = new javax.swing.JButton();

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

        lblBuscar.setText("Buscar:");

        btnEditar.setText("Editar");

        btnEliminar.setText("Eliminar");

        btnNuevo.setText("Nuevo");

        lblCarrera.setText("Carrera:");

        btnImprimir.setText("Imprimir");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(srcSilabos)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                        .addComponent(btnImprimir)))
                .addContainerGap())
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
                .addComponent(srcSilabos, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbCarrera;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JScrollPane srcSilabos;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
