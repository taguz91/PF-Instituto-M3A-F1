package vista.accesos;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import vista.AbstracView;

/**
 *
 * @author MrRainx
 */
public class FrmAccesosDeRol extends AbstracView {

    /**
     * Creates new form FmrRoles
     */
    public FrmAccesosDeRol() {
        initComponents();
        InitDiseño();
    }

    private void InitDiseño() {
        tabPermisos.setRowHeight(20);
        tabPermisos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                setHorizontalAlignment(0);
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
    }

    public JLabel getLblEstado() {
        return lblEstado;
    }

    public JLabel getLblResultados() {
        return lblResultados;
    }

    public JLabel getLblRolSeleccionado() {
        return lblRolSeleccionado;
    }

    public JTable getTabPermisos() {
        return tabPermisos;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tabPermisos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        lblRolSeleccionado = new javax.swing.JLabel();
        lblResultados = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Permisos");
        setMinimumSize(new java.awt.Dimension(660, 620));
        setPreferredSize(new java.awt.Dimension(660, 620));

        tabPermisos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Permiso", "Activo", "ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabPermisos.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tabPermisos);
        if (tabPermisos.getColumnModel().getColumnCount() > 0) {
            tabPermisos.getColumnModel().getColumn(0).setMinWidth(50);
            tabPermisos.getColumnModel().getColumn(0).setMaxWidth(50);
            tabPermisos.getColumnModel().getColumn(2).setMinWidth(100);
            tabPermisos.getColumnModel().getColumn(2).setMaxWidth(100);
            tabPermisos.getColumnModel().getColumn(3).setMinWidth(0);
            tabPermisos.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Buscar:");

        lblRolSeleccionado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRolSeleccionado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRolSeleccionado.setText("ROL SELECCIONADO");

        lblResultados.setText("Resultados");

        lblEstado.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRolSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRolSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(lblResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblResultados;
    private javax.swing.JLabel lblRolSeleccionado;
    private javax.swing.JTable tabPermisos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
