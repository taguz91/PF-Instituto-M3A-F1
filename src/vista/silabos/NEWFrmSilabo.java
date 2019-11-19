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

    public JDateChooser getDchFechaFin() {
        return dchFechaFin;
    }

    public JDateChooser getDchFechaInicio() {
        return dchFechaInicio;
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

    public JTextField getTxtTitulo() {
        return txtTitulo;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JComboBox<String> getCmbEstrategias() {
        return cmbEstrategias;
    }

    public JLabel getLblAgregarEstrategia() {
        return lblAgregarEstrategia;
    }

    public JLabel getLblAgregarUnidad() {
        return lblAgregarUnidad;
    }

    public JLabel getLblEliminarUnidad() {
        return lblEliminarUnidad;
    }

    public JLabel getLblEstrategiaSelec() {
        return lblEstrategiaSelec;
    }

    public JLabel getLblQuitarEstrategia() {
        return lblQuitarEstrategia;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        btnEditar = new javax.swing.JButton();
        lblEstrategiaSelec = new javax.swing.JLabel();
        cmbEstrategias = new javax.swing.JComboBox<>();
        lblQuitarEstrategia = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setMinimumSize(new java.awt.Dimension(1208, 565));
        setPreferredSize(new java.awt.Dimension(1208, 565));

        lblUnidad.setText("Seleccione la Unidad:");

        lblTitulo.setText("Titulo de la Unidad:");

        lblFechaInicio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblFechaInicio.setText("Fecha de Inicio de Unidad:");
        lblFechaInicio.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblFechaFin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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

        lblAgregarEstrategia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAgregarEstrategia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icono_agregar.png"))); // NOI18N
        lblAgregarEstrategia.setToolTipText("Agregar Nueva Estrategia");

        btnGuardar.setText("Guardar");

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

        lblTotalHdocencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalHdocencia.setText("${actual}/${materia}");

        lblTotalHpracticas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalHpracticas.setText("${actual}/${materia}");

        lblTotalHmateria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalHmateria.setText("${actual}/${materia}");

        btnEditar.setText("Editar");

        cmbEstrategias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEstrategias.setToolTipText("Para agregar una estrategia debe agregarle con un enter");

        lblQuitarEstrategia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQuitarEstrategia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/img/icono_eliminar_unidad.png"))); // NOI18N
        lblQuitarEstrategia.setToolTipText("Eliminar Unidad");

        javax.swing.GroupLayout pnlUnidadLayout = new javax.swing.GroupLayout(pnlUnidad);
        pnlUnidad.setLayout(pnlUnidadLayout);
        pnlUnidadLayout.setHorizontalGroup(
            pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUnidadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addComponent(lblFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dchFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(lblFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 112, Short.MAX_VALUE)
                                .addGap(0, 113, Short.MAX_VALUE)
                                .addComponent(dchFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUnidad)
                                    .addComponent(cmbUnidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAgregarUnidad)
                                    .addComponent(lblEliminarUnidad))
                                .addGap(32, 32, 32)
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSiguiente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardar))
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                                        .addComponent(lblHorasDocencia, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnHdocencia, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(13, 13, 13)
                                        .addComponent(lblTotalHdocencia, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                                        .addComponent(lblHorasAutonomas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spnHautonomas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblTotalHpracticas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                            .addComponent(lblTotalHmateria, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))))
                                .addGap(0, 8, Short.MAX_VALUE))))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbpEvaluacion, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblContenidos, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scrObjetivos, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                            .addComponent(scrContenidos))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cmbEstrategias, javax.swing.GroupLayout.Alignment.LEADING, 0, 326, Short.MAX_VALUE)
                                    .addComponent(lblEstrategiaSelec, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblQuitarEstrategia, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUnidadLayout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(lblAgregarEstrategia, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlUnidadLayout.createSequentialGroup()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlUnidadLayout.createSequentialGroup()
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(467, 467, 467)
                                .addComponent(lblTotalGestion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblAcumuladoGestion, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addComponent(lblObjetivos, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(75, 75, 75)
                                .addComponent(lblEstrategiasPredeterminadas)
                                .addGap(76, 76, 76)
                                .addComponent(lblHorasPracticas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spnHpracticas, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(4, 4, 4)))
                .addGap(75, 75, 75))
        );
        pnlUnidadLayout.setVerticalGroup(
            pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUnidadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addComponent(lblTitulo)
                                .addGap(1, 1, 1)
                                .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnCancelar)
                                    .addComponent(btnSiguiente)
                                    .addComponent(btnGuardar))
                                .addGap(18, 18, 18)
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblHorasDocencia)
                                    .addComponent(spnHdocencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTotalHdocencia))
                                .addGap(2, 2, 2)))
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHorasAutonomas)
                            .addComponent(spnHautonomas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotalHmateria)))
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addComponent(lblUnidad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblAgregarUnidad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEliminarUnidad)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dchFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaInicio)
                            .addComponent(dchFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaFin))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblObjetivos)
                        .addComponent(lblTotalHpracticas))
                    .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHorasPracticas)
                        .addComponent(spnHpracticas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEstrategiasPredeterminadas)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlUnidadLayout.createSequentialGroup()
                        .addComponent(scrObjetivos, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblContenidos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrContenidos))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUnidadLayout.createSequentialGroup()
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlUnidadLayout.createSequentialGroup()
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAgregarEstrategia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbEstrategias, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEstrategiaSelec, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblQuitarEstrategia, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUnidadLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAcumuladoGestion)
                    .addComponent(lblTotalGestion)
                    .addComponent(btnAgregar)
                    .addComponent(btnQuitar)
                    .addComponent(btnEditar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbpEvaluacion, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addGap(43, 43, 43))
        );

        jScrollPane1.setViewportView(pnlUnidad);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1231, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                .addContainerGap())
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
    private javax.swing.JComboBox<String> cmbEstrategias;
    private javax.swing.JComboBox<String> cmbUnidad;
    private com.toedter.calendar.JDateChooser dchFechaFin;
    private com.toedter.calendar.JDateChooser dchFechaInicio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAcumuladoGestion;
    private javax.swing.JLabel lblAgregarEstrategia;
    private javax.swing.JLabel lblAgregarUnidad;
    private javax.swing.JLabel lblContenidos;
    private javax.swing.JLabel lblEliminarUnidad;
    private javax.swing.JLabel lblEstrategiaSelec;
    private javax.swing.JLabel lblEstrategiasPredeterminadas;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblHorasAutonomas;
    private javax.swing.JLabel lblHorasDocencia;
    private javax.swing.JLabel lblHorasPracticas;
    private javax.swing.JLabel lblObjetivos;
    private javax.swing.JLabel lblQuitarEstrategia;
    private javax.swing.JLabel lblResultados;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTotalGestion;
    private javax.swing.JLabel lblTotalHdocencia;
    private javax.swing.JLabel lblTotalHmateria;
    private javax.swing.JLabel lblTotalHpracticas;
    private javax.swing.JLabel lblUnidad;
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
    private javax.swing.JTable tblAprendizajeColaborativo;
    private javax.swing.JTable tblAsistidaDocente;
    private javax.swing.JTable tblAutonoma;
    private javax.swing.JTable tblEstrategias;
    private javax.swing.JTable tblPractica;
    private javax.swing.JTabbedPane tbpEvaluacion;
    private javax.swing.JTextArea txrContenidos;
    private javax.swing.JTextArea txrObjetivos;
    private javax.swing.JTextArea txrResultados;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables

}
