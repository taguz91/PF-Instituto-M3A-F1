/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.curso;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

/**
 *
 * @author arman
 */
public class FrmCurso extends javax.swing.JInternalFrame {

   
    public FrmCurso() {
        initComponents();
    }

    public JFormattedTextField getFtxtCapacidad() {
        return ftxtCapacidad;
    }

    public void setFtxtCapacidad(JFormattedTextField ftxtCapacidad) {
        this.ftxtCapacidad = ftxtCapacidad;
    }

    public JComboBox getCbxCiclo() {
        return cbxCiclo;
    }

    public void setCbxCiclo(JComboBox cbxCiclo) {
        this.cbxCiclo = cbxCiclo;
    }

    public JComboBox<String> getCbxDocente() {
        return cbxDocente;
    }

    public void setCbxDocente(JComboBox<String> cbxDocente) {
        this.cbxDocente = cbxDocente;
    }

    public JComboBox<String> getCbxJornada() {
        return cbxJornada;
    }

    public void setCbxJornada(JComboBox<String> cbxJornada) {
        this.cbxJornada = cbxJornada;
    }

    public JComboBox<String> getCbxMateria() {
        return cbxMateria;
    }

    public void setCbxMateria(JComboBox<String> cbxMateria) {
        this.cbxMateria = cbxMateria;
    }

    public JComboBox getCbxParalelo() {
        return cbxParalelo;
    }

    public void setCbxParalelo(JComboBox cbxParalelo) {
        this.cbxParalelo = cbxParalelo;
    }

    public JComboBox<String> getCbxPeriodoLectivo() {
        return cbxPeriodoLectivo;
    }

    public void setCbxPeriodoLectivo(JComboBox<String> cbxPeriodoLectivo) {
        this.cbxPeriodoLectivo = cbxPeriodoLectivo;
    }



   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbxDocente = new javax.swing.JComboBox<String>();
        cbxJornada = new javax.swing.JComboBox<String>();
        cbxMateria = new javax.swing.JComboBox<String>();
        cbxParalelo = new javax.swing.JComboBox();
        cbxCiclo = new javax.swing.JComboBox();
        cbxPeriodoLectivo = new javax.swing.JComboBox<String>();
        lblPeriodoLectivo = new javax.swing.JLabel();
        lblCiclo = new javax.swing.JLabel();
        lblParalelo = new javax.swing.JLabel();
        lblMateria = new javax.swing.JLabel();
        lblJornada = new javax.swing.JLabel();
        lblDocete = new javax.swing.JLabel();
        lblCapacidad = new javax.swing.JLabel();
        ftxtCapacidad = new javax.swing.JFormattedTextField();

        setClosable(true);
        setIconifiable(true);

        cbxDocente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "|SELECCIONE|", "Item 2", "Item 3", "Item 4" }));

        cbxJornada.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "|SELECCIONE|", "MATUTINO", "VESPERTINO", "NOCTURNO" }));

        cbxMateria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "|SELECCIONE|", "Item 2", "Item 3", "Item 4" }));

        cbxParalelo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "|SELECCIONE|", "A", "B", "C", "D", "E", "F" }));

        cbxCiclo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "|SELECCIONE|", "1", "2", "3", "4", "5", "6" }));

        cbxPeriodoLectivo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "|SELECCIONE|", "Item 2", "Item 3", "Item 4" }));

        lblPeriodoLectivo.setText("Período Lectivo:");

        lblCiclo.setText("Ciclo:");

        lblParalelo.setText("Paralelo:");

        lblMateria.setText("Materia:");

        lblJornada.setText("Jornada:");

        lblDocete.setText("Docente:");

        lblCapacidad.setText("Capacidad:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblParalelo)
                    .addComponent(lblCiclo)
                    .addComponent(lblPeriodoLectivo)
                    .addComponent(lblMateria)
                    .addComponent(lblJornada)
                    .addComponent(lblCapacidad)
                    .addComponent(lblDocete))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxCiclo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxPeriodoLectivo, 0, 237, Short.MAX_VALUE)
                    .addComponent(cbxMateria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxDocente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxJornada, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxParalelo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ftxtCapacidad))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPeriodoLectivo)
                    .addComponent(cbxPeriodoLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCiclo)
                    .addComponent(cbxCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblParalelo)
                    .addComponent(cbxParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMateria)
                    .addComponent(cbxMateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblJornada)
                            .addComponent(cbxJornada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addComponent(lblDocete))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(cbxDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCapacidad)
                            .addComponent(ftxtCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbxCiclo;
    private javax.swing.JComboBox<String> cbxDocente;
    private javax.swing.JComboBox<String> cbxJornada;
    private javax.swing.JComboBox<String> cbxMateria;
    private javax.swing.JComboBox cbxParalelo;
    private javax.swing.JComboBox<String> cbxPeriodoLectivo;
    private javax.swing.JFormattedTextField ftxtCapacidad;
    private javax.swing.JLabel lblCapacidad;
    private javax.swing.JLabel lblCiclo;
    private javax.swing.JLabel lblDocete;
    private javax.swing.JLabel lblJornada;
    private javax.swing.JLabel lblMateria;
    private javax.swing.JLabel lblParalelo;
    private javax.swing.JLabel lblPeriodoLectivo;
    // End of variables declaration//GEN-END:variables
}
