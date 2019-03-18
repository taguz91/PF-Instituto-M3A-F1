
package vista.notas_Grupo_16;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 *
 * @author Alejandro
 */
public class VtnNotasAlumnoCurso extends javax.swing.JInternalFrame {

    public VtnNotasAlumnoCurso() {
        initComponents();
    }

    public JButton getBtn_imprimir() {
        return btn_imprimir;
    }

    public void setBtn_imprimir(JButton btn_imprimir) {
        this.btn_imprimir = btn_imprimir;
    }

    public JComboBox<String> getCmb_asignatura() {
        return cmb_asignatura;
    }

    public void setCmb_asignatura(JComboBox<String> cmb_asignatura) {
        this.cmb_asignatura = cmb_asignatura;
    }

    public JComboBox<String> getCmb_carrera() {
        return cmb_carrera;
    }

    public void setCmb_carrera(JComboBox<String> cmb_carrera) {
        this.cmb_carrera = cmb_carrera;
    }

    public JComboBox<String> getCmb_ciclo() {
        return cmb_ciclo;
    }

    public void setCmb_ciclo(JComboBox<String> cmb_ciclo) {
        this.cmb_ciclo = cmb_ciclo;
    }

    public JComboBox<String> getCmb_docente() {
        return cmb_docente;
    }

    public void setCmb_docente(JComboBox<String> cmb_docente) {
        this.cmb_docente = cmb_docente;
    }

    public JComboBox<String> getCmb_jornada() {
        return cmb_jornada;
    }

    public void setCmb_jornada(JComboBox<String> cmb_jornada) {
        this.cmb_jornada = cmb_jornada;
    }

    public JComboBox<String> getCmb_paralelo() {
        return cmb_paralelo;
    }

    public void setCmb_paralelo(JComboBox<String> cmb_paralelo) {
        this.cmb_paralelo = cmb_paralelo;
    }

    public JComboBox<String> getCmb_periodolectivo() {
        return cmb_periodolectivo;
    }

    public void setCmb_periodolectivo(JComboBox<String> cmb_periodolectivo) {
        this.cmb_periodolectivo = cmb_periodolectivo;
    }

    public JTable getTbl_notas() {
        return tbl_notas;
    }

    public void setTbl_notas(JTable tbl_notas) {
        this.tbl_notas = tbl_notas;
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_periodolectivo = new javax.swing.JLabel();
        lbl_carrera = new javax.swing.JLabel();
        lbl_ciclo = new javax.swing.JLabel();
        lbl_paralelo = new javax.swing.JLabel();
        lbl_docente = new javax.swing.JLabel();
        lbl_asignatura = new javax.swing.JLabel();
        lbl_jornada = new javax.swing.JLabel();
        cmb_periodolectivo = new javax.swing.JComboBox<>();
        cmb_carrera = new javax.swing.JComboBox<>();
        cmb_ciclo = new javax.swing.JComboBox<>();
        cmb_paralelo = new javax.swing.JComboBox<>();
        cmb_jornada = new javax.swing.JComboBox<>();
        cmb_asignatura = new javax.swing.JComboBox<>();
        cmb_docente = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_notas = new javax.swing.JTable();
        btn_imprimir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Notas");

        lbl_periodolectivo.setText("Periodo Lectivo:");

        lbl_carrera.setText("Carrera:");

        lbl_ciclo.setText("Ciclo:");

        lbl_paralelo.setText("Paralelo:");

        lbl_docente.setText("Docente:");

        lbl_asignatura.setText("Asignatura:");

        lbl_jornada.setText("Jornada:");

        cmb_ciclo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmb_docente.setEnabled(false);

        tbl_notas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "N°", "Cedula", "Apellidos", "Nombres", "APORTE 1   /30", "EXAMEN INTERCICLO", "TOTAL INTERCICLO", "APORTE 2  /30", "EXAMEN FINAL  /25", "/25 SUSP.", "NOTA FINAL", "ESTADO", "Nro. Faltas", "% Faltas"
            }
        ));
        tbl_notas.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_notas);
        if (tbl_notas.getColumnModel().getColumnCount() > 0) {
            tbl_notas.getColumnModel().getColumn(0).setPreferredWidth(30);
        }

        btn_imprimir.setText("Impromir");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_periodolectivo)
                            .addComponent(lbl_ciclo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_carrera, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmb_ciclo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_paralelo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmb_paralelo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmb_carrera, 0, 300, Short.MAX_VALUE)
                            .addComponent(cmb_periodolectivo, 0, 300, Short.MAX_VALUE))
                        .addGap(128, 128, 128)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(lbl_jornada))
                            .addComponent(lbl_asignatura, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_docente, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmb_jornada, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
                                .addComponent(btn_imprimir))
                            .addComponent(cmb_docente, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmb_asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_periodolectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_docente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_periodolectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_docente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_carrera, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_carrera, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_ciclo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_jornada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_paralelo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_ciclo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_paralelo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_jornada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_imprimir;
    private javax.swing.JComboBox<String> cmb_asignatura;
    private javax.swing.JComboBox<String> cmb_carrera;
    private javax.swing.JComboBox<String> cmb_ciclo;
    private javax.swing.JComboBox<String> cmb_docente;
    private javax.swing.JComboBox<String> cmb_jornada;
    private javax.swing.JComboBox<String> cmb_paralelo;
    private javax.swing.JComboBox<String> cmb_periodolectivo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_asignatura;
    private javax.swing.JLabel lbl_carrera;
    private javax.swing.JLabel lbl_ciclo;
    private javax.swing.JLabel lbl_docente;
    private javax.swing.JLabel lbl_jornada;
    private javax.swing.JLabel lbl_paralelo;
    private javax.swing.JLabel lbl_periodolectivo;
    private javax.swing.JTable tbl_notas;
    // End of variables declaration//GEN-END:variables
}
