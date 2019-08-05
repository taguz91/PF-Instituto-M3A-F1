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

        lbEstrategiasPC.setText("Descripción");

        jScrollPane4.setViewportView(jlisRecursos);

        lbRecursosPC.setText("Recursos:");

        lbIns_Evaluacion.setText("Estrategias Unidad:");

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

        jScrollPane11.setViewportView(listAnticipacionPC);

        tbpEstrategiasPC.addTab("Anticipacion", jScrollPane11);

        jScrollPane9.setViewportView(listConstruccionPC);

        tbpEstrategiasPC.addTab("Construccion", jScrollPane9);

        jScrollPane10.setViewportView(listConsolidacionPC);

        tbpEstrategiasPC.addTab("Consolidacion", jScrollPane10);

        btnAgregarPC.setText("Agregar");

        btnQuitarPC.setText("Quitar");

        txt_estrategias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_estrategiasActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(584, 584, 584)
                .addComponent(lbPlandeClase)
                .addGap(18, 18, 18)
                .addComponent(lbNumeroPlandeClase, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbCarrera)
                .addGap(14, 14, 14)
                .addComponent(txtCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(lbAsignatura)
                .addGap(12, 12, 12)
                .addComponent(txtAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbCod_Asignatura)
                .addGap(30, 30, 30)
                .addComponent(txtCod_Asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(lbCicloparalelo)
                .addGap(12, 12, 12)
                .addComponent(txtCicloParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(lbDuracion)
                .addGap(18, 18, 18)
                .addComponent(txtDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbDocente)
                .addGap(11, 11, 11)
                .addComponent(txtDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lbFechaInicioPC)
                .addGap(13, 13, 13)
                .addComponent(jDateChooserFechaInicioPC, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(lbFechafinPC)
                .addGap(12, 12, 12)
                .addComponent(jDateChooserFechaFinPC, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(lbTituloUnidad)
                .addGap(16, 16, 16)
                .addComponent(txtTituloUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbObjetivoPC)
                .addGap(325, 325, 325)
                .addComponent(lbResultadosAprendizaje)
                .addGap(302, 302, 302)
                .addComponent(lbContenidosPC))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbRecursosPC)
                .addGap(327, 327, 327)
                .addComponent(lbTrabajoAutonomo)
                .addGap(345, 345, 345)
                .addComponent(lbEstrategiasPC))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbObservacionesPC, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_estrategias, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnAgregarPC))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tbpEstrategiasPC, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnQuitarPC, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbIns_Evaluacion)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(1160, 1160, 1160)
                .addComponent(btnCancelarPC, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(btmnGuardarPc))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbPlandeClase)
                    .addComponent(lbNumeroPlandeClase))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lbCarrera))
                    .addComponent(txtCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lbAsignatura))
                    .addComponent(txtAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lbCod_Asignatura))
                    .addComponent(txtCod_Asignatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lbCicloparalelo))
                    .addComponent(txtCicloParalelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lbDuracion))
                    .addComponent(txtDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lbDocente))
                    .addComponent(txtDocente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lbFechaInicioPC))
                    .addComponent(jDateChooserFechaInicioPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lbFechafinPC))
                    .addComponent(jDateChooserFechaFinPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lbUnidad))
                    .addComponent(txtUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lbTituloUnidad))
                    .addComponent(txtTituloUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbObjetivoPC)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(lbResultadosAprendizaje))
                    .addComponent(lbContenidosPC))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbRecursosPC)
                    .addComponent(lbTrabajoAutonomo)
                    .addComponent(lbEstrategiasPC))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lbObservacionesPC)
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_estrategias, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarPC))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tbpEstrategiasPC, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnQuitarPC)
                                .addGap(13, 13, 13)
                                .addComponent(btnEditar)))
                        .addGap(18, 18, 18)
                        .addComponent(lbIns_Evaluacion)
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelarPC)
                    .addComponent(btmnGuardarPc)))
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
