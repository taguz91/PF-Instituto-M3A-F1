/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.notas;

import controlador.notas.ux.RowStyle;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import vista.AbstracView;

/**
 *
 * @author MrRainx
 */
public class VtnNotasFinales extends AbstracView {

    public VtnNotasFinales() {
        initComponents();
        InitDiseño();

    }

    private void InitDiseño() {
        tbl.setRowHeight(23);
        RowStyle rowStyleTrad = new RowStyle(3);
        Map<String, Color> estados = new HashMap<String, Color>() {
            {
                put("APROBADO", new Color(37, 107, 187));
                put("REPROBADO", new Color(214, 48, 12));
                put("RETIRADO", new Color(0, 0, 0));
                put("", new Color(0, 0, 0));
            }
        };

        rowStyleTrad.setEstados(estados);
        tbl.setDefaultRenderer(Object.class, rowStyleTrad);
        centrarCabecera(tbl);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_ciclo = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        lbl_docente = new javax.swing.JLabel();
        lbl_asignatura = new javax.swing.JLabel();
        cmbPeriodoLectivo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cmbCiclo = new javax.swing.JComboBox<>();
        lblHoras = new javax.swing.JLabel();
        cmbAsignatura = new javax.swing.JComboBox<>();
        cmbDocente = new javax.swing.JComboBox<>();
        btnImprimir = new javax.swing.JButton();
        lblCarrera = new javax.swing.JLabel();
        lbl_periodolectivo = new javax.swing.JLabel();
        btnVerNotas = new javax.swing.JButton();
        lbl_carrera = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();

        lbl_ciclo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_ciclo.setText("Ciclo:");
        lbl_ciclo.setMinimumSize(null);

        btnBuscar.setText("Buscar");
        btnBuscar.setMinimumSize(null);

        lbl_docente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_docente.setText("Docente:");
        lbl_docente.setMinimumSize(null);

        lbl_asignatura.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_asignatura.setText("Asignatura:");
        lbl_asignatura.setMinimumSize(null);

        cmbPeriodoLectivo.setMinimumSize(null);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Horas Presenciales:");
        jLabel1.setMinimumSize(null);

        cmbCiclo.setMinimumSize(null);

        lblHoras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHoras.setMinimumSize(null);

        cmbAsignatura.setMinimumSize(null);

        cmbDocente.setMinimumSize(null);

        btnImprimir.setText("Imprimir");
        btnImprimir.setMinimumSize(null);

        lblCarrera.setMinimumSize(null);

        lbl_periodolectivo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_periodolectivo.setText("Período lectivo:");
        lbl_periodolectivo.setMinimumSize(null);

        btnVerNotas.setText("Cargar Notas");
        btnVerNotas.setEnabled(false);

        lbl_carrera.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_carrera.setText("Carrera:");
        lbl_carrera.setMinimumSize(null);

        txtBuscar.setMinimumSize(null);

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Alumno", "Nota Final", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl);
        if (tbl.getColumnModel().getColumnCount() > 0) {
            tbl.getColumnModel().getColumn(0).setMinWidth(50);
            tbl.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbl.getColumnModel().getColumn(0).setMaxWidth(50);
            tbl.getColumnModel().getColumn(2).setMinWidth(100);
            tbl.getColumnModel().getColumn(2).setPreferredWidth(100);
            tbl.getColumnModel().getColumn(2).setMaxWidth(100);
            tbl.getColumnModel().getColumn(3).setMinWidth(100);
            tbl.getColumnModel().getColumn(3).setPreferredWidth(100);
            tbl.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
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
                                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 55, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnImprimir() {
        return btnImprimir;
    }

    public JButton getBtnVerNotas() {
        return btnVerNotas;
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

    public JLabel getLblHoras() {
        return lblHoras;
    }

    public JTable getTbl() {
        return tbl;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnVerNotas;
    private javax.swing.JComboBox<String> cmbAsignatura;
    private javax.swing.JComboBox<String> cmbCiclo;
    private javax.swing.JComboBox<String> cmbDocente;
    private javax.swing.JComboBox<String> cmbPeriodoLectivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblHoras;
    private javax.swing.JLabel lbl_asignatura;
    private javax.swing.JLabel lbl_carrera;
    private javax.swing.JLabel lbl_ciclo;
    private javax.swing.JLabel lbl_docente;
    private javax.swing.JLabel lbl_periodolectivo;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
