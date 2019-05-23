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

   

    public JButton getBtmnGuardarPc() {
        return btmnGuardarPc;
    }

    public void setBtmnGuardarPc(JButton btmnGuardarPc) {
        this.btmnGuardarPc = btmnGuardarPc;
    }

    public JButton getBtnAgregarPC() {
        return btnAgregarPC;
    }

    public JTextField getTxt_estrategias() {
        return txt_estrategias;
    }

    public void setTxt_estrategias(JTextField txt_estrategias) {
        this.txt_estrategias = txt_estrategias;
    }

    public void setBtnAgregarPC(JButton btnAgregarPC) {
        this.btnAgregarPC = btnAgregarPC;
    }

    public JButton getBtnCancelarPC() {
        return btnCancelarPC;
    }

    public void setBtnCancelarPC(JButton btnCancelarPC) {
        this.btnCancelarPC = btnCancelarPC;
    }

    public JButton getBtnQuitarPC() {
        return btnQuitarPC;
    }

    public void setBtnQuitarPC(JButton btnQuitarPC) {
        this.btnQuitarPC = btnQuitarPC;
    }

    public JDateChooser getjDateChooserFechaFinPC() {
        return jDateChooserFechaFinPC;
    }

    public void setjDateChooserFechaFinPC(JDateChooser jDateChooserFechaFinPC) {
        this.jDateChooserFechaFinPC = jDateChooserFechaFinPC;
    }

    public JDateChooser getjDateChooserFechaInicioPC() {
        return jDateChooserFechaInicioPC;
    }

    public void setjDateChooserFechaInicioPC(JDateChooser jDateChooserFechaInicioPC) {
        this.jDateChooserFechaInicioPC = jDateChooserFechaInicioPC;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JScrollPane getjScrollPane10() {
        return jScrollPane10;
    }

    public void setjScrollPane10(JScrollPane jScrollPane10) {
        this.jScrollPane10 = jScrollPane10;
    }

    public JScrollPane getjScrollPane11() {
        return jScrollPane11;
    }

    public void setjScrollPane11(JScrollPane jScrollPane11) {
        this.jScrollPane11 = jScrollPane11;
    }

    public JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    public void setjScrollPane2(JScrollPane jScrollPane2) {
        this.jScrollPane2 = jScrollPane2;
    }

    public JScrollPane getjScrollPane3() {
        return jScrollPane3;
    }

    public void setjScrollPane3(JScrollPane jScrollPane3) {
        this.jScrollPane3 = jScrollPane3;
    }

    public JScrollPane getjScrollPane4() {
        return jScrollPane4;
    }

    
    
    public void setjScrollPane4(JScrollPane jScrollPane4) {
        this.jScrollPane4 = jScrollPane4;
    }

    public JScrollPane getjScrollPane5() {
        return jScrollPane5;
    }

    public void setjScrollPane5(JScrollPane jScrollPane5) {
        this.jScrollPane5 = jScrollPane5;
    }

    public JScrollPane getjScrollPane7() {
        return jScrollPane7;
    }

    public void setjScrollPane7(JScrollPane jScrollPane7) {
        this.jScrollPane7 = jScrollPane7;
    }

    public JScrollPane getjScrollPane8() {
        return jScrollPane8;
    }

    public void setjScrollPane8(JScrollPane jScrollPane8) {
        this.jScrollPane8 = jScrollPane8;
    }

    public JScrollPane getjScrollPane9() {
        return jScrollPane9;
    }

    public void setjScrollPane9(JScrollPane jScrollPane9) {
        this.jScrollPane9 = jScrollPane9;
    }

    

    public JList getJlisRecursos() {
        return jlisRecursos;
    }

    public void setJlisRecursos(JList<String> jlisRecursos) {
        this.jlisRecursos = jlisRecursos;
    }

    public JList<String> getJlistInstrumentoEvaluacion() {
        return jlistInstrumentoEvaluacion;
    }

    public void setJlistInstrumentoEvaluacion(JList<String> jlistInstrumentoEvaluacion) {
        this.jlistInstrumentoEvaluacion = jlistInstrumentoEvaluacion;
    }



    public JLabel getLbAsignatura() {
        return lbAsignatura;
    }

    public void setLbAsignatura(JLabel lbAsignatura) {
        this.lbAsignatura = lbAsignatura;
    }

    public JLabel getLbCarrera() {
        return lbCarrera;
    }

    public void setLbCarrera(JLabel lbCarrera) {
        this.lbCarrera = lbCarrera;
    }

    public JLabel getLbCicloparalelo() {
        return lbCicloparalelo;
    }

    public void setLbCicloparalelo(JLabel lbCicloparalelo) {
        this.lbCicloparalelo = lbCicloparalelo;
    }

    public JLabel getLbCod_Asignatura() {
        return lbCod_Asignatura;
    }

    public void setLbCod_Asignatura(JLabel lbCod_Asignatura) {
        this.lbCod_Asignatura = lbCod_Asignatura;
    }

    public JLabel getLbContenidosPC() {
        return lbContenidosPC;
    }

    public void setLbContenidosPC(JLabel lbContenidosPC) {
        this.lbContenidosPC = lbContenidosPC;
    }

    public JLabel getLbDocente() {
        return lbDocente;
    }

    public void setLbDocente(JLabel lbDocente) {
        this.lbDocente = lbDocente;
    }

    public JLabel getLbDuracion() {
        return lbDuracion;
    }

    public void setLbDuracion(JLabel lbDuracion) {
        this.lbDuracion = lbDuracion;
    }

    public JLabel getLbEstrategiasPC() {
        return lbEstrategiasPC;
    }

    public void setLbEstrategiasPC(JLabel lbEstrategiasPC) {
        this.lbEstrategiasPC = lbEstrategiasPC;
    }

    public JLabel getLbFechaInicioPC() {
        return lbFechaInicioPC;
    }

    public void setLbFechaInicioPC(JLabel lbFechaInicioPC) {
        this.lbFechaInicioPC = lbFechaInicioPC;
    }

    public JLabel getLbFechafinPC() {
        return lbFechafinPC;
    }

    public void setLbFechafinPC(JLabel lbFechafinPC) {
        this.lbFechafinPC = lbFechafinPC;
    }

    public JLabel getLbIns_Evaluacion() {
        return lbIns_Evaluacion;
    }

    public void setLbIns_Evaluacion(JLabel lbIns_Evaluacion) {
        this.lbIns_Evaluacion = lbIns_Evaluacion;
    }

    public JLabel getLbNumeroPlandeClase() {
        return lbNumeroPlandeClase;
    }

    public void setLbNumeroPlandeClase(JLabel lbNumeroPlandeClase) {
        this.lbNumeroPlandeClase = lbNumeroPlandeClase;
    }

    public JLabel getLbObjetivoPC() {
        return lbObjetivoPC;
    }

    public void setLbObjetivoPC(JLabel lbObjetivoPC) {
        this.lbObjetivoPC = lbObjetivoPC;
    }

    public JLabel getLbObservacionesPC() {
        return lbObservacionesPC;
    }

    public void setLbObservacionesPC(JLabel lbObservacionesPC) {
        this.lbObservacionesPC = lbObservacionesPC;
    }

    public JLabel getLbPlandeClase() {
        return lbPlandeClase;
    }

    public void setLbPlandeClase(JLabel lbPlandeClase) {
        this.lbPlandeClase = lbPlandeClase;
    }

    public JLabel getLbRecursosPC() {
        return lbRecursosPC;
    }

    public void setLbRecursosPC(JLabel lbRecursosPC) {
        this.lbRecursosPC = lbRecursosPC;
    }

    public JLabel getLbResultadosAprendizaje() {
        return lbResultadosAprendizaje;
    }

    public void setLbResultadosAprendizaje(JLabel lbResultadosAprendizaje) {
        this.lbResultadosAprendizaje = lbResultadosAprendizaje;
    }

    public JLabel getLbTituloUnidad() {
        return lbTituloUnidad;
    }

    public void setLbTituloUnidad(JLabel lbTituloUnidad) {
        this.lbTituloUnidad = lbTituloUnidad;
    }

    public JLabel getLbTrabajoAutonomo() {
        return lbTrabajoAutonomo;
    }

    public void setLbTrabajoAutonomo(JLabel lbTrabajoAutonomo) {
        this.lbTrabajoAutonomo = lbTrabajoAutonomo;
    }

    public JLabel getLbUnidad() {
        return lbUnidad;
    }

    public void setLbUnidad(JLabel lbUnidad) {
        this.lbUnidad = lbUnidad;
    }

    public JList<String> getListAnticipacionPC() {
        return listAnticipacionPC;
    }

    public void setListAnticipacionPC(JList<String> listAnticipacionPC) {
        this.listAnticipacionPC = listAnticipacionPC;
    }

    public JList<String> getListConsolidacionPC() {
        return listConsolidacionPC;
    }

    public void setListConsolidacionPC(JList<String> listConsolidacionPC) {
        this.listConsolidacionPC = listConsolidacionPC;
    }

    public JList<String> getListConstruccionPC() {
        return listConstruccionPC;
    }

    public void setListConstruccionPC(JList<String> listConstruccionPC) {
        this.listConstruccionPC = listConstruccionPC;
    }

    public JTabbedPane getTbpEstrategiasPC() {
        return tbpEstrategiasPC;
    }

    public void setTbpEstrategiasPC(JTabbedPane tbpEstrategiasPC) {
        this.tbpEstrategiasPC = tbpEstrategiasPC;
    }

    public JTextArea getTxrContenidosPC() {
        return txrContenidosPC;
    }

    public void setTxrContenidosPC(JTextArea txrContenidosPC) {
        this.txrContenidosPC = txrContenidosPC;
    }

    public JTextArea getTxrObjetivoPC() {
        return txrObjetivoPC;
    }

    public void setTxrObjetivoPC(JTextArea txrObjetivoPC) {
        this.txrObjetivoPC = txrObjetivoPC;
    }

    public JTextArea getTxrObservacionesPc() {
        return txrObservacionesPc;
    }

    public void setTxrObservacionesPc(JTextArea txrObservacionesPc) {
        this.txrObservacionesPc = txrObservacionesPc;
    }

    public JTextArea getTxrResultadosAprendizaje() {
        return txrResultadosAprendizaje;
    }

    public void setTxrResultadosAprendizaje(JTextArea txrResultadosAprendizaje) {
        this.txrResultadosAprendizaje = txrResultadosAprendizaje;
    }

    public JTextArea getTxrTrabajoAutonomo() {
        return txrTrabajoAutonomo;
    }

    public void setTxrTrabajoAutonomo(JTextArea txrTrabajoAutonomo) {
        this.txrTrabajoAutonomo = txrTrabajoAutonomo;
    }

    public JTextField getTxtAsignatura() {
        return txtAsignatura;
    }

    public void setTxtAsignatura(JTextField txtAsignatura) {
        this.txtAsignatura = txtAsignatura;
    }

    public JTextField getTxtCarrera() {
        return txtCarrera;
    }

    public void setTxtCarrera(JTextField txtCarrera) {
        this.txtCarrera = txtCarrera;
    }

    public JTextField getTxtCicloParalelo() {
        return txtCicloParalelo;
    }

    public void setTxtCicloParalelo(JTextField txtCicloParalelo) {
        this.txtCicloParalelo = txtCicloParalelo;
    }

    public JTextField getTxtCod_Asignatura() {
        return txtCod_Asignatura;
    }

    public void setTxtCod_Asignatura(JTextField txtCod_Asignatura) {
        this.txtCod_Asignatura = txtCod_Asignatura;
    }

    public JTextField getTxtDocente() {
        return txtDocente;
    }

    public void setTxtDocente(JTextField txtDocente) {
        this.txtDocente = txtDocente;
    }

    public JTextField getTxtDuracion() {
        return txtDuracion;
    }

    public void setTxtDuracion(JTextField txtDuracion) {
        this.txtDuracion = txtDuracion;
    }

    public JTextField getTxtTituloUnidad() {
        return txtTituloUnidad;
    }

    public void setTxtTituloUnidad(JTextField txtTituloUnidad) {
        this.txtTituloUnidad = txtTituloUnidad;
    }

    public JTextField getTxtUnidad() {
        return txtUnidad;
    }

    public void setTxtUnidad(JTextField txtUnidad) {
        this.txtUnidad = txtUnidad;
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
        listConstruccionPC = new javax.swing.JList<>();
        jScrollPane9 = new javax.swing.JScrollPane();
        listConsolidacionPC = new javax.swing.JList<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        listAnticipacionPC = new javax.swing.JList<>();
        btnAgregarPC = new javax.swing.JButton();
        btnQuitarPC = new javax.swing.JButton();
        txt_estrategias = new javax.swing.JTextField();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);

        txrObjetivoPC.setEditable(false);
        txrObjetivoPC.setColumns(20);
        txrObjetivoPC.setLineWrap(true);
        txrObjetivoPC.setRows(5);
        txrObjetivoPC.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txrObjetivoPC);

        lbPlandeClase.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lbPlandeClase.setText("Plan de Clase");

        lbDocente.setText("Docente:");

        txtCarrera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCarreraActionPerformed(evt);
            }
        });

        lbCarrera.setText("Carrera:");

        lbNumeroPlandeClase.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lbNumeroPlandeClase.setText("Unidad N°");

        lbAsignatura.setText("Asignatura:");

        txtAsignatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAsignaturaActionPerformed(evt);
            }
        });

        lbCod_Asignatura.setText("Código asignatura: ");

        txtCod_Asignatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCod_AsignaturaActionPerformed(evt);
            }
        });

        txtTituloUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloUnidadActionPerformed(evt);
            }
        });

        lbTituloUnidad.setText("Titulo de la Unidad:");

        lbFechaInicioPC.setText("Fecha de inicio:");

        lbDuracion.setText("Duración:");

        txtDuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDuracionActionPerformed(evt);
            }
        });

        lbCicloparalelo.setText("Ciclo-Paralelo:");

        txtCicloParalelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCicloParaleloActionPerformed(evt);
            }
        });

        lbObjetivoPC.setText("Objetivos:");

        lbContenidosPC.setText("Contenidos:");

        jlistInstrumentoEvaluacion.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jlistInstrumentoEvaluacion);

        lbEstrategiasPC.setText("Estrategias:");

        jScrollPane4.setViewportView(jlisRecursos);

        lbRecursosPC.setText("Recursos:");

        lbIns_Evaluacion.setText("Instrumento de Evaluación:");

        lbFechafinPC.setText("Fecha de fin:");

        lbTrabajoAutonomo.setText("Trabajo Autónomo");

        txrContenidosPC.setColumns(20);
        txrContenidosPC.setLineWrap(true);
        txrContenidosPC.setRows(5);
        txrContenidosPC.setWrapStyleWord(true);
        jScrollPane5.setViewportView(txrContenidosPC);

        txrTrabajoAutonomo.setColumns(20);
        txrTrabajoAutonomo.setLineWrap(true);
        txrTrabajoAutonomo.setRows(5);
        txrTrabajoAutonomo.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txrTrabajoAutonomo);

        lbObservacionesPC.setText("Observaciones:");

        btmnGuardarPc.setText("Guardar");

        btnCancelarPC.setText("Atrás");
        btnCancelarPC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPCActionPerformed(evt);
            }
        });

        txrObservacionesPc.setColumns(20);
        txrObservacionesPc.setLineWrap(true);
        txrObservacionesPc.setRows(5);
        txrObservacionesPc.setWrapStyleWord(true);
        jScrollPane7.setViewportView(txrObservacionesPc);

        lbUnidad.setText("Unidad:");

        txrResultadosAprendizaje.setEditable(false);
        txrResultadosAprendizaje.setColumns(20);
        txrResultadosAprendizaje.setLineWrap(true);
        txrResultadosAprendizaje.setRows(5);
        txrResultadosAprendizaje.setWrapStyleWord(true);
        jScrollPane8.setViewportView(txrResultadosAprendizaje);

        lbResultadosAprendizaje.setText("Resultados de Aprendizaje:");

        jScrollPane11.setViewportView(listConstruccionPC);

        tbpEstrategiasPC.addTab("Construcción", jScrollPane11);

        jScrollPane9.setViewportView(listConsolidacionPC);

        tbpEstrategiasPC.addTab("Consolidación", jScrollPane9);

        jScrollPane10.setViewportView(listAnticipacionPC);

        tbpEstrategiasPC.addTab("Anticipación", jScrollPane10);

        btnAgregarPC.setText("Agregar");

        btnQuitarPC.setText("Quitar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbObjetivoPC)
                            .addComponent(lbRecursosPC)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbResultadosAprendizaje)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane8))
                                        .addGap(19, 19, 19))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbTrabajoAutonomo)
                                            .addComponent(lbIns_Evaluacion))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addComponent(jScrollPane7)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lbContenidosPC, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbEstrategiasPC, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addComponent(lbObservacionesPC)))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tbpEstrategiasPC, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                                            .addComponent(txt_estrategias))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAgregarPC, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnQuitarPC, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbCarrera)
                            .addComponent(lbDocente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCarrera, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(txtDocente))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbAsignatura)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbFechaInicioPC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooserFechaInicioPC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(lbFechafinPC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooserFechaFinPC, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbUnidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbCod_Asignatura))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCod_Asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(lbCicloparalelo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCicloParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbDuracion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbTituloUnidad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTituloUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbPlandeClase)
                .addGap(18, 18, 18)
                .addComponent(lbNumeroPlandeClase, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(504, 504, 504))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelarPC, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btmnGuardarPc)
                .addGap(13, 13, 13))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPlandeClase)
                    .addComponent(lbNumeroPlandeClase))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCod_Asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCicloParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbCicloparalelo)
                                .addComponent(lbDuracion)
                                .addComponent(txtDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbCod_Asignatura)
                                .addComponent(txtAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbAsignatura))
                            .addComponent(lbCarrera, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbDocente)
                                    .addComponent(lbFechaInicioPC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbUnidad)
                                        .addComponent(txtUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbTituloUnidad)
                                        .addComponent(txtTituloUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jDateChooserFechaInicioPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDateChooserFechaFinPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbFechafinPC, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lbResultadosAprendizaje)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbContenidosPC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbObjetivoPC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbEstrategiasPC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAgregarPC)
                                    .addComponent(txt_estrategias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnQuitarPC)
                                    .addComponent(tbpEstrategiasPC, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addComponent(lbObservacionesPC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnCancelarPC)
                                    .addComponent(btmnGuardarPc)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbRecursosPC)
                                    .addComponent(lbTrabajoAutonomo))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lbIns_Evaluacion)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(txtCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btmnGuardarPc;
    private javax.swing.JButton btnAgregarPC;
    private javax.swing.JButton btnCancelarPC;
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
