package vista.silabos;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Daniel
 */
public class frmPlanClase extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmPlanClase
     */
    public frmPlanClase() {
        initComponents();
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("vista/img/logo.png"));
        this.setFrameIcon(icon);
       
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public void setBtnEditar(JButton btnEditar) {
        this.btnEditar = btnEditar;
    }

    public JButton getBtmnGuardarPc() {
        return btmnGuardarPc;
    }

    public JButton getBtnAgregarPC() {
        return btnAgregarPC;
    }

    public JButton getBtnCancelarPC() {
        return btnCancelarPC;
    }

    public JButton getBtnQuitarPC() {
        return btnQuitarPC;
    }

    public JDateChooser getjDateChooserFechaFinPC() {
        return jDateChooserFechaFinPC;
    }

    public JDateChooser getjDateChooserFechaInicioPC() {
        return jDateChooserFechaInicioPC;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public JScrollPane getjScrollPane10() {
        return jScrollPane10;
    }

    public JScrollPane getjScrollPane11() {
        return jScrollPane11;
    }

    public JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    public JScrollPane getjScrollPane3() {
        return jScrollPane3;
    }

    public JScrollPane getjScrollPane4() {
        return jScrollPane4;
    }

    public JScrollPane getjScrollPane5() {
        return jScrollPane5;
    }

    public JScrollPane getjScrollPane7() {
        return jScrollPane7;
    }

    public JScrollPane getjScrollPane8() {
        return jScrollPane8;
    }

    public JScrollPane getjScrollPane9() {
        return jScrollPane9;
    }

    public JList getJlisRecursos() {
        return jlisRecursos;
    }

    public JList<String> getJlistInstrumentoEvaluacion() {
        return jlistInstrumentoEvaluacion;
    }

    public JLabel getLbAsignatura() {
        return lbAsignatura;
    }

    public JLabel getLbCarrera() {
        return lbCarrera;
    }

    public JLabel getLbCicloparalelo() {
        return lbCicloparalelo;
    }

    public JLabel getLbCod_Asignatura() {
        return lbCod_Asignatura;
    }

    public JLabel getLbContenidosPC() {
        return lbContenidosPC;
    }

    public JLabel getLbDocente() {
        return lbDocente;
    }

    public JLabel getLbDuracion() {
        return lbDuracion;
    }

    public JLabel getLbEstrategiasPC() {
        return lbEstrategiasPC;
    }

    public JLabel getLbFechaInicioPC() {
        return lbFechaInicioPC;
    }

    public JLabel getLbFechafinPC() {
        return lbFechafinPC;
    }

    public JLabel getLbIns_Evaluacion() {
        return lbIns_Evaluacion;
    }

    public JLabel getLbNumeroPlandeClase() {
        return lbNumeroPlandeClase;
    }

    public JLabel getLbObjetivoPC() {
        return lbObjetivoPC;
    }

    public JLabel getLbObservacionesPC() {
        return lbObservacionesPC;
    }

    public JLabel getLbPlandeClase() {
        return lbPlandeClase;
    }

    public JLabel getLbRecursosPC() {
        return lbRecursosPC;
    }

    public JLabel getLbResultadosAprendizaje() {
        return lbResultadosAprendizaje;
    }

    public JLabel getLbTituloUnidad() {
        return lbTituloUnidad;
    }

    public JLabel getLbTrabajoAutonomo() {
        return lbTrabajoAutonomo;
    }

    public JLabel getLbUnidad() {
        return lbUnidad;
    }

    public JList<String> getListAnticipacionPC() {
        return listAnticipacionPC;
    }

    public JList<String> getListConsolidacionPC() {
        return listConsolidacionPC;
    }

    public JList<String> getListConstruccionPC() {
        return listConstruccionPC;
    }

    public JTabbedPane getTbpEstrategiasPC() {
        return tbpEstrategiasPC;
    }

    public JTextArea getTxrContenidosPC() {
        return txrContenidosPC;
    }

    public JTextArea getTxrObjetivoPC() {
        return txrObjetivoPC;
    }

    public JTextArea getTxrObservacionesPc() {
        return txrObservacionesPc;
    }

    public JTextArea getTxrResultadosAprendizaje() {
        return txrResultadosAprendizaje;
    }

    public JTextArea getTxrTrabajoAutonomo() {
        return txrTrabajoAutonomo;
    }

    public JTextField getTxtAsignatura() {
        return txtAsignatura;
    }

    public JTextField getTxtCarrera() {
        return txtCarrera;
    }

    public JTextField getTxtCicloParalelo() {
        return txtCicloParalelo;
    }

    public JTextField getTxtCod_Asignatura() {
        return txtCod_Asignatura;
    }

    public JTextField getTxtDocente() {
        return txtDocente;
    }

    public JTextField getTxtDuracion() {
        return txtDuracion;
    }

    public JTextField getTxtTituloUnidad() {
        return txtTituloUnidad;
    }

    public JTextField getTxtUnidad() {
        return txtUnidad;
    }

    public JTextField getTxt_estrategias() {
        return txt_estrategias;
    }

   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txrObjetivoPC = new javax.swing.JTextArea();
        txtDocente = new javax.swing.JTextField();
        lbPlandeClase = new javax.swing.JLabel();
        lbDocente = new javax.swing.JLabel();
        txtCarrera = new javax.swing.JTextField();
        lbCarrera = new javax.swing.JLabel();
        lbNumeroPlandeClase = new javax.swing.JLabel();
        lbAsignatura = new javax.swing.JLabel();
        txtAsignatura = new javax.swing.JTextField();
        lbCod_Asignatura = new javax.swing.JLabel();
        txtCod_Asignatura = new javax.swing.JTextField();
        txtTituloUnidad = new javax.swing.JTextField();
        lbTituloUnidad = new javax.swing.JLabel();
        jDateChooserFechaInicioPC = new com.toedter.calendar.JDateChooser();
        lbFechaInicioPC = new javax.swing.JLabel();
        lbDuracion = new javax.swing.JLabel();
        txtDuracion = new javax.swing.JTextField();
        lbCicloparalelo = new javax.swing.JLabel();
        txtCicloParalelo = new javax.swing.JTextField();
        lbObjetivoPC = new javax.swing.JLabel();
        lbContenidosPC = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlistInstrumentoEvaluacion = new javax.swing.JList<>();
        lbEstrategiasPC = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jlisRecursos = new javax.swing.JList();
        lbRecursosPC = new javax.swing.JLabel();
        lbIns_Evaluacion = new javax.swing.JLabel();
        jDateChooserFechaFinPC = new com.toedter.calendar.JDateChooser();
        lbFechafinPC = new javax.swing.JLabel();
        lbTrabajoAutonomo = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txrContenidosPC = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txrTrabajoAutonomo = new javax.swing.JTextArea();
        lbObservacionesPC = new javax.swing.JLabel();
        btmnGuardarPc = new javax.swing.JButton();
        btnCancelarPC = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        txrObservacionesPc = new javax.swing.JTextArea();
        txtUnidad = new javax.swing.JTextField();
        lbUnidad = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txrResultadosAprendizaje = new javax.swing.JTextArea();
        lbResultadosAprendizaje = new javax.swing.JLabel();
        tbpEstrategiasPC = new javax.swing.JTabbedPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        listAnticipacionPC = new javax.swing.JList<>();
        jScrollPane9 = new javax.swing.JScrollPane();
        listConstruccionPC = new javax.swing.JList<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        listConsolidacionPC = new javax.swing.JList<>();
        btnAgregarPC = new javax.swing.JButton();
        btnQuitarPC = new javax.swing.JButton();
        txt_estrategias = new javax.swing.JTextField();
        btnEditar = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txrObjetivoPC.setEditable(false);
        txrObjetivoPC.setColumns(20);
        txrObjetivoPC.setLineWrap(true);
        txrObjetivoPC.setRows(5);
        txrObjetivoPC.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txrObjetivoPC);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 159, 349, 94));
        getContentPane().add(txtDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 95, 294, -1));

        lbPlandeClase.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lbPlandeClase.setText("Plan de Clase");
        getContentPane().add(lbPlandeClase, new org.netbeans.lib.awtextra.AbsoluteConstraints(584, 16, -1, -1));

        lbDocente.setText("Docente:");
        getContentPane().add(lbDocente, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 97, -1, -1));

        txtCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCarreraActionPerformed(evt);
            }
        });
        getContentPane().add(txtCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 58, 294, -1));

        lbCarrera.setText("Carrera:");
        getContentPane().add(lbCarrera, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 62, -1, -1));

        lbNumeroPlandeClase.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lbNumeroPlandeClase.setText("Unidad N°");
        getContentPane().add(lbNumeroPlandeClase, new org.netbeans.lib.awtextra.AbsoluteConstraints(714, 16, 115, -1));

        lbAsignatura.setText("Asignatura:");
        getContentPane().add(lbAsignatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(404, 60, -1, -1));

        txtAsignatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAsignaturaActionPerformed(evt);
            }
        });
        getContentPane().add(txtAsignatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(472, 58, 279, -1));

        lbCod_Asignatura.setText("Código asignatura: ");
        getContentPane().add(lbCod_Asignatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(769, 60, -1, -1));

        txtCod_Asignatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCod_AsignaturaActionPerformed(evt);
            }
        });
        getContentPane().add(txtCod_Asignatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(893, 58, 114, -1));

        txtTituloUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloUnidadActionPerformed(evt);
            }
        });
        getContentPane().add(txtTituloUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(1001, 95, 320, -1));

        lbTituloUnidad.setText("Titulo de la Unidad:");
        getContentPane().add(lbTituloUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(893, 97, -1, -1));
        getContentPane().add(jDateChooserFechaInicioPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(474, 95, 100, -1));

        lbFechaInicioPC.setText("Fecha de inicio:");
        getContentPane().add(lbFechaInicioPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(387, 97, -1, -1));

        lbDuracion.setText("Duración:");
        getContentPane().add(lbDuracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(1184, 60, -1, -1));

        txtDuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDuracionActionPerformed(evt);
            }
        });
        getContentPane().add(txtDuracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(1248, 58, 73, -1));

        lbCicloparalelo.setText("Ciclo-Paralelo:");
        getContentPane().add(lbCicloparalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1021, 60, -1, -1));

        txtCicloParalelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCicloParaleloActionPerformed(evt);
            }
        });
        getContentPane().add(txtCicloParalelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1101, 58, 60, -1));

        lbObjetivoPC.setText("Objetivos:");
        getContentPane().add(lbObjetivoPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 132, -1, -1));

        lbContenidosPC.setText("Contenidos:");
        getContentPane().add(lbContenidosPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(821, 132, -1, -1));

        jlistInstrumentoEvaluacion.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jlistInstrumentoEvaluacion);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(821, 456, 500, 72));

        lbEstrategiasPC.setText("Descripción");
        getContentPane().add(lbEstrategiasPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(821, 282, -1, -1));

        jScrollPane4.setViewportView(jlisRecursos);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 303, 349, 225));

        lbRecursosPC.setText("Recursos:");
        getContentPane().add(lbRecursosPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 282, -1, -1));

        lbIns_Evaluacion.setText("Estrategias Unidad:");
        getContentPane().add(lbIns_Evaluacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(821, 435, -1, -1));
        getContentPane().add(jDateChooserFechaFinPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(658, 95, 93, -1));

        lbFechafinPC.setText("Fecha de fin:");
        getContentPane().add(lbFechafinPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(583, 99, -1, -1));

        lbTrabajoAutonomo.setText("Trabajo Autónomo");
        getContentPane().add(lbTrabajoAutonomo, new org.netbeans.lib.awtextra.AbsoluteConstraints(387, 282, -1, -1));

        txrContenidosPC.setColumns(20);
        txrContenidosPC.setLineWrap(true);
        txrContenidosPC.setRows(5);
        txrContenidosPC.setWrapStyleWord(true);
        jScrollPane5.setViewportView(txrContenidosPC);

        getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(821, 158, 500, 94));

        txrTrabajoAutonomo.setColumns(20);
        txrTrabajoAutonomo.setLineWrap(true);
        txrTrabajoAutonomo.setRows(5);
        txrTrabajoAutonomo.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txrTrabajoAutonomo);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(387, 303, 416, 116));

        lbObservacionesPC.setText("Observaciones:");
        getContentPane().add(lbObservacionesPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(387, 431, 88, -1));

        btmnGuardarPc.setText("Guardar");
        getContentPane().add(btmnGuardarPc, new org.netbeans.lib.awtextra.AbsoluteConstraints(1246, 540, -1, -1));

        btnCancelarPC.setText("Atrás");
        btnCancelarPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPCActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelarPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 540, 75, -1));

        txrObservacionesPc.setColumns(20);
        txrObservacionesPc.setLineWrap(true);
        txrObservacionesPc.setRows(5);
        txrObservacionesPc.setWrapStyleWord(true);
        jScrollPane7.setViewportView(txrObservacionesPc);

        getContentPane().add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(387, 453, 416, 75));
        getContentPane().add(txtUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(829, 95, 33, -1));

        lbUnidad.setText("Unidad:");
        getContentPane().add(lbUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(769, 97, 48, -1));

        txrResultadosAprendizaje.setEditable(false);
        txrResultadosAprendizaje.setColumns(20);
        txrResultadosAprendizaje.setLineWrap(true);
        txrResultadosAprendizaje.setRows(5);
        txrResultadosAprendizaje.setWrapStyleWord(true);
        jScrollPane8.setViewportView(txrResultadosAprendizaje);

        getContentPane().add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(387, 158, 415, 94));

        lbResultadosAprendizaje.setText("Resultados de Aprendizaje:");
        getContentPane().add(lbResultadosAprendizaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(387, 137, -1, -1));

        jScrollPane11.setViewportView(listAnticipacionPC);

        tbpEstrategiasPC.addTab("Anticipacion", jScrollPane11);

        jScrollPane9.setViewportView(listConstruccionPC);

        tbpEstrategiasPC.addTab("Construccion", jScrollPane9);

        jScrollPane10.setViewportView(listConsolidacionPC);

        tbpEstrategiasPC.addTab("Consolidacion", jScrollPane10);

        getContentPane().add(tbpEstrategiasPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(821, 334, 409, 83));

        btnAgregarPC.setText("Agregar");
        getContentPane().add(btnAgregarPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(1242, 303, -1, -1));

        btnQuitarPC.setText("Quitar");
        getContentPane().add(btnQuitarPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(1242, 334, 73, -1));

        txt_estrategias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_estrategiasActionPerformed(evt);
            }
        });
        getContentPane().add(txt_estrategias, new org.netbeans.lib.awtextra.AbsoluteConstraints(821, 303, 409, 28));

        btnEditar.setText("Editar");
        getContentPane().add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1242, 370, 73, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAsignaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAsignaturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAsignaturaActionPerformed

    private void txtCod_AsignaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCod_AsignaturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCod_AsignaturaActionPerformed

    private void txtTituloUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloUnidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloUnidadActionPerformed

    private void txtDuracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDuracionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDuracionActionPerformed

    private void txtCicloParaleloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCicloParaleloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCicloParaleloActionPerformed

    private void txtCarreraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCarreraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCarreraActionPerformed

    private void btnCancelarPCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarPCActionPerformed

    private void txt_estrategiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_estrategiasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_estrategiasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btmnGuardarPc;
    private javax.swing.JButton btnAgregarPC;
    private javax.swing.JButton btnCancelarPC;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnQuitarPC;
    private com.toedter.calendar.JDateChooser jDateChooserFechaFinPC;
    private com.toedter.calendar.JDateChooser jDateChooserFechaInicioPC;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JList jlisRecursos;
    private javax.swing.JList<String> jlistInstrumentoEvaluacion;
    private javax.swing.JLabel lbAsignatura;
    private javax.swing.JLabel lbCarrera;
    private javax.swing.JLabel lbCicloparalelo;
    private javax.swing.JLabel lbCod_Asignatura;
    private javax.swing.JLabel lbContenidosPC;
    private javax.swing.JLabel lbDocente;
    private javax.swing.JLabel lbDuracion;
    private javax.swing.JLabel lbEstrategiasPC;
    private javax.swing.JLabel lbFechaInicioPC;
    private javax.swing.JLabel lbFechafinPC;
    private javax.swing.JLabel lbIns_Evaluacion;
    private javax.swing.JLabel lbNumeroPlandeClase;
    private javax.swing.JLabel lbObjetivoPC;
    private javax.swing.JLabel lbObservacionesPC;
    private javax.swing.JLabel lbPlandeClase;
    private javax.swing.JLabel lbRecursosPC;
    private javax.swing.JLabel lbResultadosAprendizaje;
    private javax.swing.JLabel lbTituloUnidad;
    private javax.swing.JLabel lbTrabajoAutonomo;
    private javax.swing.JLabel lbUnidad;
    private javax.swing.JList<String> listAnticipacionPC;
    private javax.swing.JList<String> listConsolidacionPC;
    private javax.swing.JList<String> listConstruccionPC;
    private javax.swing.JTabbedPane tbpEstrategiasPC;
    private javax.swing.JTextArea txrContenidosPC;
    private javax.swing.JTextArea txrObjetivoPC;
    private javax.swing.JTextArea txrObservacionesPc;
    private javax.swing.JTextArea txrResultadosAprendizaje;
    private javax.swing.JTextArea txrTrabajoAutonomo;
    private javax.swing.JTextField txtAsignatura;
    private javax.swing.JTextField txtCarrera;
    private javax.swing.JTextField txtCicloParalelo;
    private javax.swing.JTextField txtCod_Asignatura;
    private javax.swing.JTextField txtDocente;
    private javax.swing.JTextField txtDuracion;
    private javax.swing.JTextField txtTituloUnidad;
    private javax.swing.JTextField txtUnidad;
    private javax.swing.JTextField txt_estrategias;
    // End of variables declaration//GEN-END:variables
 public static class CheckListItem {

        private String label;
        private boolean isSelected = false;

        public CheckListItem(String label) {
            this.label = label;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        public String toString() {
            return label;
        }
    }

    public static class CheckListRenderer extends JCheckBox implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
            setEnabled(list.isEnabled());
            setSelected(((CheckListItem) value).isSelected());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
            return this;
        }
    }

    public static class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            setComponentOrientation(list.getComponentOrientation());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setSelected(isSelected);
            setEnabled(list.isEnabled());

            setText(value == null ? "" : value.toString());

            return this;
        }
    }
}
