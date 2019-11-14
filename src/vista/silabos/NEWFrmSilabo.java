package vista.silabos;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import vista.AbstracView;

/**
 *
 * @author MrRainx
 */
public class NEWFrmSilabo extends AbstracView {

    public NEWFrmSilabo() {
        initComponents();
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnQuitar() {
        return btnQuitar;
    }

    public JButton getBtnSiguiente() {
        return btnSiguiente;
    }

    public JComboBox<String> getCmbUnidad() {
        return cmbUnidad;
    }

    public JDateChooser getDchFechaEnvio() {
        return dchFechaEnvio;
    }

    public JDateChooser getDchFechaFin() {
        return dchFechaFin;
    }

    public JDateChooser getDchFechaInicio() {
        return dchFechaInicio;
    }

    public JDateChooser getDchFechaPresentacion() {
        return dchFechaPresentacion;
    }

    public JLabel getLblAcumuladoGestion() {
        return lblAcumuladoGestion;
    }

    public JLabel getLblTotalGestion() {
        return lblTotalGestion;
    }

    public JLabel getLblTotalHdocencia() {
        return lblTotalHdocencia;
    }

    public JLabel getLblTotalHmateria() {
        return lblTotalHmateria;
    }

    public JLabel getLblTotalHpracticas() {
        return lblTotalHpracticas;
    }

    public JSpinner getSpnHautonomas() {
        return spnHautonomas;
    }

    public JSpinner getSpnHdocencia() {
        return spnHdocencia;
    }

    public JSpinner getSpnHpracticas() {
        return spnHpracticas;
    }

    public JSpinner getSpnValoracion() {
        return spnValoracion;
    }

    public JTable getTblAprendizajeColaborativo() {
        return tblAprendizajeColaborativo;
    }

    public JTable getTblAsistidaDocente() {
        return tblAsistidaDocente;
    }

    public JTable getTblAutonoma() {
        return tblAutonoma;
    }

    public JTable getTblEstrategias() {
        return tblEstrategias;
    }

    public JTable getTblPractica() {
        return tblPractica;
    }

    public JTabbedPane getTbpEvaluacion() {
        return tbpEvaluacion;
    }

    public JTextArea getTxrContenidos() {
        return txrContenidos;
    }

    public JTextArea getTxrObjetivos() {
        return txrObjetivos;
    }

    public JTextArea getTxrResultados() {
        return txrResultados;
    }

    public JTextArea getTxtIndicador() {
        return txtIndicador;
    }

    public JTextField getTxtInstrumento() {
        return txtInstrumento;
    }

    public JTextField getTxttxtBuscarEstrategias() {
        return txtBuscarEstrategias;
    }

    public JTextField getTxtTitulo() {
        return txtTitulo;
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlgAddActividad = new javax.swing.JDialog();
        dchFechaPresentacion = new com.toedter.calendar.JDateChooser();
        lblFechaPresentacionAD = new javax.swing.JLabel();
        lblFechaEnvioAD = new javax.swing.JLabel();
        dchFechaEnvio = new com.toedter.calendar.JDateChooser();
        spnValoracion = new javax.swing.JSpinner();
        lblValoracionAD = new javax.swing.JLabel();
        lblInstrumentoAD = new javax.swing.JLabel();
        txtInstrumento = new javax.swing.JTextField();
        lblIndicadorAD = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtIndicador = new javax.swing.JTextArea();
        lblAccionActividades = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlUnidad = new javax.swing.JPanel();
        cmbUnidad = new javax.swing.JComboBox<>();
        txtTitulo = new javax.swing.JTextField();
        lblUnidad = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblFechaInicio = new javax.swing.JLabel();
        dchFechaInicio = new com.toedter.calendar.JDateChooser();
        dchFechaFin = new com.toedter.calendar.JDateChooser();
        lblFechaFin = new javax.swing.JLabel();
        lblObjetivos = new javax.swing.JLabel();
        lblResultados = new javax.swing.JLabel();
        lblContenidos = new javax.swing.JLabel();
        tbpEvaluacion = new javax.swing.JTabbedPane();
        pnlAsistidaDocente = new javax.swing.JPanel();
        scrAsistidaDocente = new javax.swing.JScrollPane();
        tblAsistidaDocente = new javax.swing.JTable();
        pnlAprendizajeColaborativo = new javax.swing.JPanel();
        scrAprendizajeColaborativo = new javax.swing.JScrollPane();
        tblAprendizajeColaborativo = new javax.swing.JTable();
        pnlPractica = new javax.swing.JPanel();
        scrPractica = new javax.swing.JScrollPane();
        tblPractica = new javax.swing.JTable();
        pnlAutonoma = new javax.swing.JPanel();
        scrAutonoma = new javax.swing.JScrollPane();
        tblAutonoma = new javax.swing.JTable();
        scrResultados = new javax.swing.JScrollPane();
        txrResultados = new javax.swing.JTextArea();
        scrObjetivos = new javax.swing.JScrollPane();
        txrObjetivos = new javax.swing.JTextArea();
        scrContenidos = new javax.swing.JScrollPane();
        txrContenidos = new javax.swing.JTextArea();
        btnCancelar = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        spnHpracticas = new javax.swing.JSpinner();
        lblHorasPracticas = new javax.swing.JLabel();
        lblHorasDocencia = new javax.swing.JLabel();
        lblHorasAutonomas = new javax.swing.JLabel();
        spnHautonomas = new javax.swing.JSpinner();
        spnHdocencia = new javax.swing.JSpinner();
        lblEstrategiasPredeterminadas = new javax.swing.JLabel();
        lblEliminarUnidad = new javax.swing.JLabel();
        lblAgregarUnidad = new javax.swing.JLabel();
        lblAgregarEstrategia = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        lblTotalGestion = new javax.swing.JLabel();
        lblAcumuladoGestion = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEstrategias = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblTotalHdocencia = new javax.swing.JLabel();
        lblTotalHpracticas = new javax.swing.JLabel();
        lblTotalHmateria = new javax.swing.JLabel();
        txtBuscarEstrategias = new javax.swing.JTextField();
        btnEditar = new javax.swing.JButton();

        lblFechaPresentacionAD.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaPresentacionAD.setText("Fecha Presentación:");

        lblFechaEnvioAD.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaEnvioAD.setText("Fecha Envío:");

        spnValoracion.setModel(new javax.swing.SpinnerNumberModel(1.0d, 1.0d, 60, 0.5d));

        lblValoracionAD.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblValoracionAD.setText("Valoración:");

        lblInstrumentoAD.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblInstrumentoAD.setText("Instrumento:");

        lblIndicadorAD.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblIndicadorAD.setText("Indicador:");

        txtIndicador.setColumns(20);
        txtIndicador.setRows(5);
        jScrollPane3.setViewportView(txtIndicador);

        lblAccionActividades.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAccionActividades.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAccionActividades.setText("${Accion Actual}");

        jButton1.setText("Cancelar");

        jButton2.setText("Guardar");

        javax.swing.GroupLayout dlgAddActividadLayout = new javax.swing.GroupLayout(dlgAddActividad.getContentPane());
        dlgAddActividad.getContentPane().setLayout(dlgAddActividadLayout);
        dlgAddActividadLayout.setHorizontalGroup(
            dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgAddActividadLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dlgAddActividadLayout.createSequentialGroup()
                        .addGroup(dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(dlgAddActividadLayout.createSequentialGroup()
                                .addGroup(dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(dlgAddActividadLayout.createSequentialGroup()
                                        .addComponent(lblFechaEnvioAD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dchFechaEnvio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(dlgAddActividadLayout.createSequentialGroup()
                                        .addComponent(lblValoracionAD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnValoracion, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(dlgAddActividadLayout.createSequentialGroup()
                                        .addGroup(dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblFechaPresentacionAD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dchFechaPresentacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(182, 182, 182)
                                .addComponent(jButton2))
                            .addGroup(dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(dlgAddActividadLayout.createSequentialGroup()
                                    .addComponent(lblInstrumentoAD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtInstrumento))
                                .addGroup(dlgAddActividadLayout.createSequentialGroup()
                                    .addComponent(lblIndicadorAD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblAccionActividades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        dlgAddActividadLayout.setVerticalGroup(
            dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgAddActividadLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblAccionActividades, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInstrumentoAD, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInstrumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIndicadorAD, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValoracionAD, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnValoracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFechaEnvioAD, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dchFechaEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFechaPresentacionAD, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dchFechaPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(dlgAddActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setMinimumSize(new java.awt.Dimension(1208, 565));
        setPreferredSize(new java.awt.Dimension(1208, 565));

        lblUnidad.setText("Seleccione la Unidad:");

        lblTitulo.setText("Titulo de la Unidad:");

        lblFechaInicio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaInicio.setText("Fecha de Inicio de Unidad:");

        lblFechaFin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaFin.setText("Fecha de Fin de Unidad:");

        lblObjetivos.setText("Objetivos Específicos de la Unidad:");

        lblResultados.setText("Resultados de Aprendizaje:");

        lblContenidos.setText("Contenidos de la Unidad:");

        pnlAsistidaDocente.setLayout(new javax.swing.BoxLayout(pnlAsistidaDocente, javax.swing.BoxLayout.LINE_AXIS));

        tblAsistidaDocente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indicador", "Instrumento", "Valoración", "Fecha  Envío", "Fecha  Presentación", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAsistidaDocente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblAsistidaDocente.getTableHeader().setReorderingAllowed(false);
        scrAsistidaDocente.setViewportView(tblAsistidaDocente);
        if (tblAsistidaDocente.getColumnModel().getColumnCount() > 0) {
            tblAsistidaDocente.getColumnModel().getColumn(0).setPreferredWidth(270);
            tblAsistidaDocente.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblAsistidaDocente.getColumnModel().getColumn(2).setMinWidth(80);
            tblAsistidaDocente.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblAsistidaDocente.getColumnModel().getColumn(2).setMaxWidth(80);
            tblAsistidaDocente.getColumnModel().getColumn(3).setMinWidth(150);
            tblAsistidaDocente.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblAsistidaDocente.getColumnModel().getColumn(3).setMaxWidth(150);
            tblAsistidaDocente.getColumnModel().getColumn(4).setMinWidth(150);
            tblAsistidaDocente.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblAsistidaDocente.getColumnModel().getColumn(4).setMaxWidth(150);
            tblAsistidaDocente.getColumnModel().getColumn(5).setMinWidth(0);
            tblAsistidaDocente.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblAsistidaDocente.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        pnlAsistidaDocente.add(scrAsistidaDocente);

        tbpEvaluacion.addTab("Gestión Docente - Asistida Docente", pnlAsistidaDocente);

        pnlAprendizajeColaborativo.setLayout(new javax.swing.BoxLayout(pnlAprendizajeColaborativo, javax.swing.BoxLayout.LINE_AXIS));

        tblAprendizajeColaborativo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indicador", "Instrumento", "Valoración", "Fecha  Envío", "Fecha  Presentación", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAprendizajeColaborativo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblAprendizajeColaborativo.getTableHeader().setReorderingAllowed(false);
        scrAprendizajeColaborativo.setViewportView(tblAprendizajeColaborativo);
        if (tblAprendizajeColaborativo.getColumnModel().getColumnCount() > 0) {
            tblAprendizajeColaborativo.getColumnModel().getColumn(0).setPreferredWidth(270);
            tblAprendizajeColaborativo.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblAprendizajeColaborativo.getColumnModel().getColumn(2).setMinWidth(90);
            tblAprendizajeColaborativo.getColumnModel().getColumn(2).setPreferredWidth(90);
            tblAprendizajeColaborativo.getColumnModel().getColumn(2).setMaxWidth(90);
            tblAprendizajeColaborativo.getColumnModel().getColumn(3).setMinWidth(150);
            tblAprendizajeColaborativo.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblAprendizajeColaborativo.getColumnModel().getColumn(3).setMaxWidth(150);
            tblAprendizajeColaborativo.getColumnModel().getColumn(4).setMinWidth(150);
            tblAprendizajeColaborativo.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblAprendizajeColaborativo.getColumnModel().getColumn(4).setMaxWidth(150);
            tblAprendizajeColaborativo.getColumnModel().getColumn(5).setMinWidth(0);
            tblAprendizajeColaborativo.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblAprendizajeColaborativo.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        pnlAprendizajeColaborativo.add(scrAprendizajeColaborativo);

        tbpEvaluacion.addTab("Gestión Docente - Aprendizaje Colaborativo", pnlAprendizajeColaborativo);

        pnlPractica.setLayout(new javax.swing.BoxLayout(pnlPractica, javax.swing.BoxLayout.LINE_AXIS));

        tblPractica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indicador", "Instrumento", "Valoración", "Fecha  Envío", "Fecha  Presentación", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPractica.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblPractica.getTableHeader().setReorderingAllowed(false);
        scrPractica.setViewportView(tblPractica);
        if (tblPractica.getColumnModel().getColumnCount() > 0) {
            tblPractica.getColumnModel().getColumn(0).setPreferredWidth(270);
            tblPractica.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblPractica.getColumnModel().getColumn(2).setMinWidth(90);
            tblPractica.getColumnModel().getColumn(2).setPreferredWidth(90);
            tblPractica.getColumnModel().getColumn(2).setMaxWidth(90);
            tblPractica.getColumnModel().getColumn(3).setMinWidth(150);
            tblPractica.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblPractica.getColumnModel().getColumn(3).setMaxWidth(150);
            tblPractica.getColumnModel().getColumn(4).setMinWidth(150);
            tblPractica.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblPractica.getColumnModel().getColumn(4).setMaxWidth(150);
            tblPractica.getColumnModel().getColumn(5).setMinWidth(0);
            tblPractica.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblPractica.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        pnlPractica.add(scrPractica);

        tbpEvaluacion.addTab("Gestión Práctica", pnlPractica);

        pnlAutonoma.setLayout(new javax.swing.BoxLayout(pnlAutonoma, javax.swing.BoxLayout.LINE_AXIS));

        tblAutonoma.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indicador", "Instrumento", "Valoración", "Fecha  Envío", "Fecha  Presentación", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAutonoma.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblAutonoma.getTableHeader().setReorderingAllowed(false);
        scrAutonoma.setViewportView(tblAutonoma);
        if (tblAutonoma.getColumnModel().getColumnCount() > 0) {
            tblAutonoma.getColumnModel().getColumn(0).setPreferredWidth(270);
            tblAutonoma.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblAutonoma.getColumnModel().getColumn(2).setMinWidth(90);
            tblAutonoma.getColumnModel().getColumn(2).setPreferredWidth(90);
            tblAutonoma.getColumnModel().getColumn(2).setMaxWidth(90);
            tblAutonoma.getColumnModel().getColumn(3).setMinWidth(150);
            tblAutonoma.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblAutonoma.getColumnModel().getColumn(3).setMaxWidth(150);
            tblAutonoma.getColumnModel().getColumn(4).setMinWidth(150);
            tblAutonoma.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblAutonoma.getColumnModel().getColumn(4).setMaxWidth(150);
            tblAutonoma.getColumnModel().getColumn(5).setMinWidth(0);
            tblAutonoma.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblAutonoma.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        pnlAutonoma.add(scrAutonoma);

        tbpEvaluacion.addTab("Gestión Autónoma", pnlAutonoma);

        txrResultados.setColumns(20);
        txrResultados.setLineWrap(true);
        txrResultados.setRows(5);
        txrResultados.setWrapStyleWord(true);
        scrResultados.setViewportView(txrResultados);

        txrObjetivos.setColumns(20);
        txrObjetivos.setLineWrap(true);
        txrObjetivos.setRows(5);
        txrObjetivos.setWrapStyleWord(true);
        scrObjetivos.setViewportView(txrObjetivos);

        txrContenidos.setColumns(20);
        txrContenidos.setLineWrap(true);
        txrContenidos.setRows(5);
        txrContenidos.setWrapStyleWord(true);
        scrContenidos.setViewportView(txrContenidos);

        btnCancelar.setText("Cancelar");

        btnSiguiente.setText("Siguiente");

        spnHpracticas.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 100.0d, 1.0d));

        lblHorasPracticas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblHorasPracticas.setText("Horas Prácticas:");

        lblHorasDocencia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblHorasDocencia.setText("Horas Docencia:");

        lblHorasAutonomas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblHorasAutonomas.setText("Horas Autónomas:");

        spnHautonomas.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 100.0d, 1.0d));

        spnHdocencia.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 100.0d, 1.0d));

        lblEstrategiasPredeterminadas.setText("Seleccione la(s) Estrategias de Enseñanza:");

        lblEliminarUnidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEliminarUnidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icono_eliminar_unidad.png"))); // NOI18N
        lblEliminarUnidad.setToolTipText("Eliminar Unidad");

        lblAgregarUnidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icono_agregar.png"))); // NOI18N
        lblAgregarUnidad.setToolTipText("Agregar Unidad");

        lblAgregarEstrategia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icono_agregar.png"))); // NOI18N
        lblAgregarEstrategia.setToolTipText("Agregar Nueva Estrategia");

        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);

        lblTotalGestion.setText("Total de Gestion de  Aula:");

        lblAcumuladoGestion.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAcumuladoGestion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAcumuladoGestion.setText("0/60");

        tblEstrategias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "X", "Titulo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEstrategias.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblEstrategias);
        if (tblEstrategias.getColumnModel().getColumnCount() > 0) {
            tblEstrategias.getColumnModel().getColumn(0).setMinWidth(0);
            tblEstrategias.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblEstrategias.getColumnModel().getColumn(0).setMaxWidth(0);
            tblEstrategias.getColumnModel().getColumn(1).setMinWidth(35);
            tblEstrategias.getColumnModel().getColumn(1).setPreferredWidth(35);
            tblEstrategias.getColumnModel().getColumn(1).setMaxWidth(35);
        }

        btnAgregar.setText("Agregar");

        btnQuitar.setText("Quitar");
        btnQuitar.setEnabled(false);

        lblTotalHdocencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalHdocencia.setText("${actual}/${materia}");

        lblTotalHpracticas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalHpracticas.setText("${actual}/${materia}");

        lblTotalHmateria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalHmateria.setText("${actual}/${materia}");

        btnEditar.setText("Editar");

        javax.swing.GroupLayout pnlUnidadLayout = new javax.swing.GroupLayout(pnlUnidad);
        pnlUnidad.setLayout(pnlUnidadLayout);
        pnlUnidadLayout.setHorizontalGroup(
            pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUnidadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tbpEvaluacion, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlUnidadLayout.createSequentialGroup()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrObjetivos)
                            .addComponent(scrContenidos)
                            .addComponent(lblObjetivos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblContenidos, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addComponent(txtBuscarEstrategias)
                                .addGap(18, 18, 18)
                                .addComponent(lblAgregarEstrategia))
                            .addComponent(lblEstrategiasPredeterminadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrResultados, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(lblResultados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlUnidadLayout.createSequentialGroup()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUnidad)
                            .addComponent(cmbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAgregarUnidad)
                            .addComponent(lblEliminarUnidad))
                        .addGap(18, 18, 18)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitulo)
                            .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addComponent(lblFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dchFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(lblFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dchFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHorasAutonomas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHorasPracticas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spnHpracticas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spnHautonomas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addComponent(lblHorasDocencia, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnHdocencia, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTotalHdocencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotalHmateria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotalHpracticas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnSiguiente)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar))
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar)
                        .addGap(18, 18, 18)
                        .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotalGestion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAcumuladoGestion, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlUnidadLayout.setVerticalGroup(
            pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUnidadLayout.createSequentialGroup()
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblUnidad)
                                    .addComponent(lblTitulo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dchFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblFechaInicio))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFechaFin)
                                    .addComponent(dchFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblHorasDocencia)
                                    .addComponent(spnHdocencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTotalHdocencia))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblHorasPracticas)
                                    .addComponent(spnHpracticas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTotalHpracticas))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblHorasAutonomas)
                                    .addComponent(spnHautonomas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTotalHmateria)))))
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(lblAgregarUnidad)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEliminarUnidad)))
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblObjetivos)
                            .addComponent(lblEstrategiasPredeterminadas)
                            .addComponent(lblResultados))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrObjetivos, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblContenidos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrContenidos, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(scrResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUnidadLayout.createSequentialGroup()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBuscarEstrategias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAgregarEstrategia))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAcumuladoGestion)
                    .addComponent(lblTotalGestion)
                    .addComponent(btnAgregar)
                    .addComponent(btnQuitar)
                    .addComponent(btnEditar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbpEvaluacion, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSiguiente)
                    .addComponent(btnGuardar))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(pnlUnidad);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JComboBox<String> cmbUnidad;
    private com.toedter.calendar.JDateChooser dchFechaEnvio;
    private com.toedter.calendar.JDateChooser dchFechaFin;
    private com.toedter.calendar.JDateChooser dchFechaInicio;
    private com.toedter.calendar.JDateChooser dchFechaPresentacion;
    private javax.swing.JDialog dlgAddActividad;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAccionActividades;
    private javax.swing.JLabel lblAcumuladoGestion;
    private javax.swing.JLabel lblAgregarEstrategia;
    private javax.swing.JLabel lblAgregarUnidad;
    private javax.swing.JLabel lblContenidos;
    private javax.swing.JLabel lblEliminarUnidad;
    private javax.swing.JLabel lblEstrategiasPredeterminadas;
    private javax.swing.JLabel lblFechaEnvioAD;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblFechaPresentacionAD;
    private javax.swing.JLabel lblHorasAutonomas;
    private javax.swing.JLabel lblHorasDocencia;
    private javax.swing.JLabel lblHorasPracticas;
    private javax.swing.JLabel lblIndicadorAD;
    private javax.swing.JLabel lblInstrumentoAD;
    private javax.swing.JLabel lblObjetivos;
    private javax.swing.JLabel lblResultados;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotalGestion;
    private javax.swing.JLabel lblTotalHdocencia;
    private javax.swing.JLabel lblTotalHmateria;
    private javax.swing.JLabel lblTotalHpracticas;
    private javax.swing.JLabel lblUnidad;
    private javax.swing.JLabel lblValoracionAD;
    private javax.swing.JPanel pnlAprendizajeColaborativo;
    private javax.swing.JPanel pnlAsistidaDocente;
    private javax.swing.JPanel pnlAutonoma;
    private javax.swing.JPanel pnlPractica;
    private javax.swing.JPanel pnlUnidad;
    private javax.swing.JScrollPane scrAprendizajeColaborativo;
    private javax.swing.JScrollPane scrAsistidaDocente;
    private javax.swing.JScrollPane scrAutonoma;
    private javax.swing.JScrollPane scrContenidos;
    private javax.swing.JScrollPane scrObjetivos;
    private javax.swing.JScrollPane scrPractica;
    private javax.swing.JScrollPane scrResultados;
    private javax.swing.JSpinner spnHautonomas;
    private javax.swing.JSpinner spnHdocencia;
    private javax.swing.JSpinner spnHpracticas;
    private javax.swing.JSpinner spnValoracion;
    private javax.swing.JTable tblAprendizajeColaborativo;
    private javax.swing.JTable tblAsistidaDocente;
    private javax.swing.JTable tblAutonoma;
    private javax.swing.JTable tblEstrategias;
    private javax.swing.JTable tblPractica;
    private javax.swing.JTabbedPane tbpEvaluacion;
    private javax.swing.JTextArea txrContenidos;
    private javax.swing.JTextArea txrObjetivos;
    private javax.swing.JTextArea txrResultados;
    private javax.swing.JTextField txtBuscarEstrategias;
    private javax.swing.JTextArea txtIndicador;
    private javax.swing.JTextField txtInstrumento;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables

}
