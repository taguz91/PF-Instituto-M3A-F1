package controlador.silabo;

import com.placeholder.PlaceHolder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import modelo.ConexionBD;
import modelo.estrategiasAprendizaje.EstrategiasAprendizajeBD;
import modelo.estrategiasAprendizaje.EstrategiasAprendizajeMD;
import modelo.estrategiasUnidad.EstrategiasUnidadBD;
import modelo.estrategiasUnidad.EstrategiasUnidadMD;
import modelo.evaluacionSilabo.EvaluacionSilaboBD;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;
import modelo.referencias.ReferenciasBD;
import modelo.referencias.ReferenciasMD;

import modelo.referenciasSilabo.ReferenciaSilaboBD;
import modelo.referenciasSilabo.ReferenciaSilaboMD;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;
import modelo.tipoActividad.TipoActividadBD;
import modelo.tipoActividad.TipoActividadMD;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmGestionSilabo;
import vista.silabos.frmGestionSilabo.CheckListItem;
import vista.silabos.frmGestionSilabo.CheckListRenderer;
import vista.silabos.frmReferencias;

/**
 *
 * @author Andres Ullauri
 */
public class ControladorSilaboU {
    /*
    private static Integer idEvaluacionSig = 0;

    private Integer idEvaluacion;

    private boolean cambioSilabo;

    private boolean retroceso = false;

    public ControladorSilaboU(SilaboMD modelo, VtnPrincipal vtnPrincipal, ConexionBD conexion) {
        super(modelo, vtnPrincipal, conexion);
    }


    public void iniciarSilabo(SilaboMD silabo) {

        vista.getBtnGuardar().setEnabled(false);

        cambioSilabo = false;

        unidades = UnidadSilaboBD.consultar(conexion, silabo.getID(), 1);

        estrategias = EstrategiasUnidadBD.cargarEstrategiasU(conexion, silabo.getID());

        evaluaciones = EvaluacionSilaboBD.recuperarEvaluaciones(conexion, silabo.getID());

        biblioteca = new ArrayList<>();

        referenciasSilabo = ReferenciaSilaboBD.recuperarReferencias(conexion, silabo.getID());

        tiposActividad = TipoActividadBD.consultar(conexion);

        mostrarTotalGestion();

        cargarReferencias(referenciasSilabo);
        unidades.forEach((umd) -> {
            vista.getCmbUnidad().addItem("Unidad " + umd.getNumeroUnidad());
        });

        vista.getBtnGuardar().setEnabled(false);

        vista.getCmbUnidad().addActionListener(e -> mostrarUnidad());

        vista.getTxtTitulo().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {

                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                unidadSeleccionada.setTituloUnidad(vista.getTxtTitulo().getText());
                actualizarUnidad(unidadSeleccionada);

            }

        });

        vista.getTxrObjetivos().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {

                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                unidadSeleccionada.setObjetivoEspecificoUnidad(vista.getTxrObjetivos().getText());
                actualizarUnidad(unidadSeleccionada);

            }

        });

        vista.getTxrContenidos().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {

                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                unidadSeleccionada.setContenidosUnidad(vista.getTxrContenidos().getText());
                actualizarUnidad(unidadSeleccionada);

            }

        });

        vista.getTxrResultados().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                unidadSeleccionada.setResultadosAprendizajeUnidad(vista.getTxrResultados().getText());
                actualizarUnidad(unidadSeleccionada);
            }

        });

        vista.getLblAgregarEstrategia().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                PlaceHolder holder = new PlaceHolder(vista.getTxtNuevaEstrategia(), "Ingrese la nueva estrategia...");
                vista.getTxtNuevaEstrategia().setEnabled(true);
                vista.getLblGuardarEstrategia().setEnabled(true);
                vista.getLstEstrategiasPredeterminadas().requestFocusInWindow();
            }

        });

        vista.getLblGuardarEstrategia().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int limite = 110;
                if (vista.getTxtNuevaEstrategia().getText().length() <= limite) {
                    boolean existe = false;

                    EstrategiasAprendizajeBD nuevaEstrategia = new EstrategiasAprendizajeBD(conexion, vista.getTxtNuevaEstrategia().getText());

                    List<EstrategiasAprendizajeMD> estrategias = EstrategiasAprendizajeBD.consultar(conexion);

                    for (EstrategiasAprendizajeMD e : estrategias) {

                        if (e.getDescripcionEstrategia().toUpperCase().trim().equals(vista.getTxtNuevaEstrategia().getText().toUpperCase().trim())) {
                            existe = true;
                            JOptionPane.showMessageDialog(null, "La estrategia que intentó ingresar ya existe", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }

                    }

                    if (!existe) {
                        if (vista.getTxtNuevaEstrategia().getText().isEmpty() || vista.getTxtNuevaEstrategia().getText().equals("Ingrese la nueva estrategia...")) {
                            JOptionPane.showMessageDialog(null, "No ha ingresado ninguna estrategia", "Aviso", JOptionPane.WARNING_MESSAGE);

                        } else {
                            nuevaEstrategia.insertar();
                            JOptionPane.showMessageDialog(null, "Nueva estrategia guardada correctamente.");

                        }
                    }

                    vista.getTxtNuevaEstrategia().setText("");
                    vista.getTxtNuevaEstrategia().setEnabled(false);
                    vista.getLblGuardarEstrategia().setEnabled(false);

                    cargarEstrategias(seleccionarUnidad());
                } else {
                    JOptionPane.showMessageDialog(null, "El texto excede el numero máximo de caracteres! Ingrese de nuevo");
                }
            }

        });

        vista.getLstEstrategiasPredeterminadas().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {

                int index = vista.getLstEstrategiasPredeterminadas().locationToIndex(event.getPoint());
                frmGestionSilabo.CheckListItem item = (frmGestionSilabo.CheckListItem) vista.getLstEstrategiasPredeterminadas().getModel().getElementAt(index);
                item.setSelected(!item.isSelected());
                vista.getLstEstrategiasPredeterminadas().repaint(vista.getLstEstrategiasPredeterminadas().getCellBounds(index, index));
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

                estrategias.removeIf(e -> {
                    return e.getIdUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad();
                });

                for (int i = 0; i < vista.getLstEstrategiasPredeterminadas().getModel().getSize(); i++) {
                    frmGestionSilabo.CheckListItem item2 = (frmGestionSilabo.CheckListItem) vista.getLstEstrategiasPredeterminadas().getModel().getElementAt(i);

                    if (item2.isSelected()) {

                        String estrategia = vista.getLstEstrategiasPredeterminadas().getModel().getElementAt(i).toString();

                        Optional<EstrategiasAprendizajeMD> estrategiaSeleccionada = EstrategiasAprendizajeBD.consultar(conexion).stream().
                                filter(e -> e.getDescripcionEstrategia().equals(estrategia)).
                                findFirst();

                        estrategias.add(new EstrategiasUnidadMD(estrategiaSeleccionada.get(), unidadSeleccionada));
                        cambioSilabo = true;
                        vista.getBtnGuardar().setEnabled(true);
                        System.out.println(estrategias.size() + "------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>><< TAMAÑO DEL ARRAY LIST");

                        System.out.println(estrategiaSeleccionada.get().getDescripcionEstrategia() + " - " + unidadSeleccionada.getNumeroUnidad());

                    }
                }

            }
        });

        vista.getDchFechaInicio().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

                if (vista.getDchFechaInicio().getDate() != null) {
                    LocalDate fechaInicio = vista.getDchFechaInicio().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (unidadSeleccionada.getFechaFinUnidad() == null) {
                        unidadSeleccionada.setFechaInicioUnidad(fechaInicio);
                        actualizarUnidad(unidadSeleccionada);
                    } else {
                        if (unidadSeleccionada.getFechaFinUnidad().isAfter(fechaInicio.minus(1, ChronoUnit.DAYS))
                                || unidadSeleccionada.getFechaFinUnidad().equals(fechaInicio)) {
                            unidadSeleccionada.setFechaInicioUnidad(fechaInicio);
                            actualizarUnidad(unidadSeleccionada);
                        } else {
                            JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser posterior a la fecha de fin", "Alerta", JOptionPane.WARNING_MESSAGE);
                            vista.getDchFechaInicio().setDate(Date.from(unidadSeleccionada.getFechaFinUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant().minus(1, ChronoUnit.DAYS)));
                        }
                    }
                }

            }

        });

        vista.getDchFechaFin().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

                if (vista.getDchFechaFin().getDate() != null) {
                    LocalDate fechaFin = vista.getDchFechaFin().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (unidadSeleccionada.getFechaInicioUnidad() == null) {
                        unidadSeleccionada.setFechaFinUnidad(fechaFin);
                        actualizarUnidad(unidadSeleccionada);
                    } else {
                        if (unidadSeleccionada.getFechaInicioUnidad().isBefore(fechaFin.plus(1, ChronoUnit.DAYS))
                                || unidadSeleccionada.getFechaFinUnidad().equals(fechaFin)) {

                            unidadSeleccionada.setFechaFinUnidad(fechaFin);
                            actualizarUnidad(unidadSeleccionada);
                        } else {
                            JOptionPane.showMessageDialog(null, "La fecha de fin no puede ser anterior a la fecha de inicio", "Alerta", JOptionPane.WARNING_MESSAGE);

                            vista.getDchFechaFin().setDate(Date.from(unidadSeleccionada.getFechaInicioUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));
                        }
                    }

                }

            }

        });

        vista.getDchFechaPresentacionAD().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (vista.getDchFechaEnvioAD().getDate() != null && vista.getDchFechaPresentacionAD().getDate() != null) {
                    LocalDate fechaPresentacion = vista.getDchFechaPresentacionAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = vista.getDchFechaEnvioAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (fechaPresentacion.isBefore(fechaEnvio)) {

                        JOptionPane.showMessageDialog(null, "La fecha de presentación no puede ser anterior a la fecha de envío", "Alerta", JOptionPane.WARNING_MESSAGE);

                        vista.getDchFechaPresentacionAD().setDate(Date.from(fechaEnvio.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        vista.getDchFechaEnvioAD().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (vista.getDchFechaPresentacionAD().getDate() != null && vista.getDchFechaEnvioAD().getDate() != null) {
                    LocalDate fechaPresentacion = vista.getDchFechaPresentacionAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = vista.getDchFechaEnvioAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (fechaEnvio.isAfter(fechaPresentacion)) {

                        JOptionPane.showMessageDialog(null, "La fecha de envío no puede ser posterior a la fecha de presentación", "Alerta", JOptionPane.WARNING_MESSAGE);

                        vista.getDchFechaEnvioAD().setDate(Date.from(fechaPresentacion.atStartOfDay(ZoneId.systemDefault()).toInstant().minus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        vista.getDchFechaPresentacionAC().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (vista.getDchFechaEnvioAC().getDate() != null && vista.getDchFechaPresentacionAC().getDate() != null) {
                    LocalDate fechaPresentacion = vista.getDchFechaPresentacionAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = vista.getDchFechaEnvioAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (fechaPresentacion.isBefore(fechaEnvio)) {

                        JOptionPane.showMessageDialog(null, "La fecha de presentación no puede ser anterior a la fecha de envío", "Alerta", JOptionPane.WARNING_MESSAGE);

                        vista.getDchFechaPresentacionAC().setDate(Date.from(fechaEnvio.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        vista.getDchFechaEnvioAC().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (vista.getDchFechaPresentacionAC().getDate() != null && vista.getDchFechaEnvioAC().getDate() != null) {
                    LocalDate fechaPresentacion = vista.getDchFechaPresentacionAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = vista.getDchFechaEnvioAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (fechaEnvio.isAfter(fechaPresentacion)) {

                        JOptionPane.showMessageDialog(null, "La fecha de envío no puede ser posterior a la fecha de presentación", "Alerta", JOptionPane.WARNING_MESSAGE);

                        vista.getDchFechaEnvioAC().setDate(Date.from(fechaPresentacion.atStartOfDay(ZoneId.systemDefault()).toInstant().minus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        vista.getDchFechaPresentacionP().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (vista.getDchFechaEnvioP().getDate() != null && vista.getDchFechaPresentacionP().getDate() != null) {
                    LocalDate fechaPresentacion = vista.getDchFechaPresentacionP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = vista.getDchFechaEnvioP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (fechaPresentacion.isBefore(fechaEnvio)) {

                        JOptionPane.showMessageDialog(null, "La fecha de presentación no puede ser anterior a la fecha de envío", "Alerta", JOptionPane.WARNING_MESSAGE);

                        vista.getDchFechaPresentacionP().setDate(Date.from(fechaEnvio.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        vista.getDchFechaEnvioP().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (vista.getDchFechaPresentacionP().getDate() != null && vista.getDchFechaEnvioP().getDate() != null) {
                    LocalDate fechaPresentacion = vista.getDchFechaPresentacionP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = vista.getDchFechaEnvioP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (fechaEnvio.isAfter(fechaPresentacion)) {

                        JOptionPane.showMessageDialog(null, "La fecha de envío no puede ser posterior a la fecha de presentación", "Alerta", JOptionPane.WARNING_MESSAGE);

                        vista.getDchFechaEnvioP().setDate(Date.from(fechaPresentacion.atStartOfDay(ZoneId.systemDefault()).toInstant().minus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        vista.getDchFechaPresentacionA().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (vista.getDchFechaEnvioA().getDate() != null && vista.getDchFechaPresentacionA().getDate() != null) {
                    LocalDate fechaPresentacion = vista.getDchFechaPresentacionA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = vista.getDchFechaEnvioA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (fechaPresentacion.isBefore(fechaEnvio)) {

                        JOptionPane.showMessageDialog(null, "La fecha de presentación no puede ser anterior a la fecha de envío", "Alerta", JOptionPane.WARNING_MESSAGE);

                        vista.getDchFechaPresentacionA().setDate(Date.from(fechaEnvio.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        vista.getDchFechaEnvioA().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (vista.getDchFechaPresentacionA().getDate() != null && vista.getDchFechaEnvioA().getDate() != null) {
                    LocalDate fechaPresentacion = vista.getDchFechaPresentacionA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = vista.getDchFechaEnvioA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (fechaEnvio.isAfter(fechaPresentacion)) {

                        JOptionPane.showMessageDialog(null, "La fecha de envío no puede ser posterior a la fecha de presentación", "Alerta", JOptionPane.WARNING_MESSAGE);

                        vista.getDchFechaEnvioA().setDate(Date.from(fechaPresentacion.atStartOfDay(ZoneId.systemDefault()).toInstant().minus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        vista.getSpnHorasDocencia().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {

                int acumuladoDocencia = 0;
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                for (UnidadSilaboMD umd : unidades) {
                    if (umd.getNumeroUnidad() != unidadSeleccionada.getNumeroUnidad()) {
                        acumuladoDocencia = acumuladoDocencia + umd.getHorasDocenciaUnidad();
                    }

                }

                if ((acumuladoDocencia + (int) vista.getSpnHorasDocencia().getValue()) > silabo.getMateria().getHorasDocencia()) {
                    vista.getSpnHorasDocencia().setValue(silabo.getMateria().getHorasDocencia() - acumuladoDocencia);
                    JOptionPane.showMessageDialog(null, "Esta materia debe cumplir con el número exacto de " + silabo.getMateria().getHorasDocencia() + " horas de gestión docente", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                unidadSeleccionada.setHorasDocenciaUnidad((int) (vista.getSpnHorasDocencia().getValue()));

            }

        });

        vista.getSpnHorasPracticas().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {

                int acumuladoPractica = 0;
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                for (UnidadSilaboMD umd : unidades) {
                    if (umd.getNumeroUnidad() != unidadSeleccionada.getNumeroUnidad()) {
                        acumuladoPractica = acumuladoPractica + umd.getHorasPracticaUnidad();
                    }

                }

                if ((acumuladoPractica + (int) vista.getSpnHorasPracticas().getValue()) > silabo.getMateria().getHorasPracticas()) {
                    vista.getSpnHorasPracticas().setValue(silabo.getMateria().getHorasPracticas() - acumuladoPractica);
                    JOptionPane.showMessageDialog(null, "Esta materia debe cumplir con el número exacto de " + silabo.getMateria().getHorasPracticas() + " horas de gestión práctica", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                unidadSeleccionada.setHorasPracticaUnidad((int) (vista.getSpnHorasPracticas().getValue()));

            }

        });

        vista.getSpnHorasAutonomas().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {

                int acumuladoAutonomo = 0;
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                for (UnidadSilaboMD umd : unidades) {
                    if (umd.getNumeroUnidad() != unidadSeleccionada.getNumeroUnidad()) {
                        acumuladoAutonomo = acumuladoAutonomo + umd.getHorasAutonomoUnidad();
                    }

                }

                if ((acumuladoAutonomo + (int) vista.getSpnHorasAutonomas().getValue()) > silabo.getMateria().getHorasAutoEstudio()) {
                    vista.getSpnHorasAutonomas().setValue(silabo.getMateria().getHorasAutoEstudio() - acumuladoAutonomo);
                    JOptionPane.showMessageDialog(null, "Esta materia debe cumplir con el número exacto de " + silabo.getMateria().getHorasAutoEstudio() + " horas de gestión autónoma", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                unidadSeleccionada.setHorasAutonomoUnidad((int) (vista.getSpnHorasAutonomas().getValue()));

            }

        });

        vista.getLblAgregarUnidad().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                agregarUnidad();
            }

        });

        vista.getLblEliminarUnidad().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                if (unidades.size() > 1) {
                    int reply = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar esta unidad?", "Eliminar", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        eliminarUnidad();
                        JOptionPane.showMessageDialog(null, "Unidad eliminada correctamente");

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El silabo debe tener como mínimo una unidad", "Aviso", JOptionPane.ERROR_MESSAGE);
                }

            }

        });

        vista.getBtnAgregarAD().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!vista.getTxtInstrumentoAD().getText().isEmpty()
                        && !vista.getTxtIndicadorAD().getText().isEmpty()
                        && vista.getDchFechaEnvioAD().getDate() != null
                        && vista.getDchFechaPresentacionAD().getDate() != null) {
                    String[] infoE = {"Gestión de Docencia", "Asistido por el Docente"};
                    agregarEvaluacion(seleccionarTipoActividad(infoE), seleccionarUnidad(), 1);

                } else {
                    JOptionPane.showMessageDialog(null, "Es neceario que especifique todos campos requeridos para la evaluación", "Aviso", JOptionPane.ERROR_MESSAGE);

                }

            }

        });

        vista.getBtnAgregarAC().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!vista.getTxtInstrumentoAC().getText().isEmpty()
                        && !vista.getTxtIndicadorAC().getText().isEmpty()
                        && vista.getDchFechaEnvioAC().getDate() != null
                        && vista.getDchFechaPresentacionAC().getDate() != null) {
                    String[] infoE = {"Gestión de Docencia", "Aprendizaje Colaborativo"};
                    agregarEvaluacion(seleccionarTipoActividad(infoE), seleccionarUnidad(), 2);

                } else {
                    JOptionPane.showMessageDialog(null, "Es neceario que especifique todos campos requeridos para la evaluación", "Aviso", JOptionPane.ERROR_MESSAGE);

                }

            }

        });

        vista.getBtnAgregarP().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!vista.getTxtInstrumentoP().getText().isEmpty()
                        && !vista.getTxtIndicadorP().getText().isEmpty()
                        && vista.getDchFechaEnvioP().getDate() != null
                        && vista.getDchFechaPresentacionP().getDate() != null) {
                    String[] infoE = {"Gestión de la Práctica", "Aprendizaje Colaborativo"};
                    agregarEvaluacion(seleccionarTipoActividad(infoE), seleccionarUnidad(), 3);

                } else {
                    JOptionPane.showMessageDialog(null, "Es neceario que especifique todos campos requeridos para la evaluación", "Aviso", JOptionPane.ERROR_MESSAGE);

                }

            }

        });

        vista.getBtnAgregarA().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!vista.getTxtInstrumentoA().getText().isEmpty()
                        && !vista.getTxtIndicadorA().getText().isEmpty()
                        && vista.getDchFechaEnvioA().getDate() != null
                        && vista.getDchFechaPresentacionA().getDate() != null) {
                    String[] infoE = {"Gestión de Trabajo Autónomo", "Aprendizaje Colaborativo"};
                    agregarEvaluacion(seleccionarTipoActividad(infoE), seleccionarUnidad(), 4);

                } else {
                    JOptionPane.showMessageDialog(null, "Es neceario que especifique todos campos requeridos para la evaluación", "Aviso", JOptionPane.ERROR_MESSAGE);

                }

            }

        });

        vista.getTblAsistidaDocente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                vista.getBtnQuitarAD().setEnabled(true);

            }

        });

        vista.getTblAprendizajeColaborativo().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                vista.getBtnQuitarAC().setEnabled(true);

            }

        });

        vista.getTblPractica().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                vista.getBtnQuitarP().setEnabled(true);

            }

        });

        vista.getTblAutonoma().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                vista.getBtnQuitarA().setEnabled(true);

            }

        });

        vista.getBtnQuitarAD().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                quitarEvaluacionAD((DefaultTableModel) vista.getTblAsistidaDocente().getModel(), 1);
                vista.getBtnQuitarAD().setEnabled(false);
            }

        });

        vista.getBtnQuitarAC().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                quitarEvaluacionAC((DefaultTableModel) vista.getTblAprendizajeColaborativo().getModel(), 2);
                vista.getBtnQuitarAC().setEnabled(false);
            }

        });

        vista.getBtnQuitarP().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                quitarEvaluacionP((DefaultTableModel) vista.getTblPractica().getModel(), 3);
                vista.getBtnQuitarP().setEnabled(false);
            }

        });

        vista.getBtnQuitarA().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                quitarEvaluacionA((DefaultTableModel) vista.getTblAutonoma().getModel(), 4);
                vista.getBtnQuitarA().setEnabled(false);
            }

        });

        vista.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int reply = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar el proceso?", "Cancelar", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    vista.dispose();
                    vtnPrincipal.getMnCtSilabos().doClick();
                }
            }

        });
        vista.getBtnGuardar().addActionListener(e -> ejecutar(e));


        vista.getBtnSiguiente().addActionListener(e -> ejecutar3(e, silabo));

        mostrarUnidad();
    }

    private boolean accion = true;
    private boolean accion2 = true;
    private boolean accion3 = true;

    private void ejecutar(ActionEvent e) {

        if (accion) {
            new Thread(() -> {
                accion = false;
                vista.getBtnGuardar().setEnabled(false);
                vista.getBtnSiguiente().setEnabled(false);
                vista.getBtnCancelar().setEnabled(false);
                vtnPrincipal.getLblEstado().setText("Guardando cambios en el silabo... Espere por favor");
                System.out.println("============" + modelo.getID());
                new SilaboBD(conexion).eliminar(modelo);

                if (guardarSilabo()) {
                    JOptionPane.showMessageDialog(null, "Cambios guardados exitosamente");
                    vtnPrincipal.getLblEstado().setText("");
                    cambioSilabo = false;

                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ControladorSilaboC.class.getName()).log(Level.SEVERE, null, ex);
                }

                vista.getBtnSiguiente().setEnabled(true);
                vista.getBtnCancelar().setEnabled(true);
                vtnPrincipal.getLblEstado().setText("");

                accion = true;
            }).start();
        }

    }

    private void ejecutar2(ActionEvent e) {

        if (accion2) {
            new Thread(() -> {
                accion2 = false;

                if (validarBiblioBase()) {
                    bibliografia.getBtnFinalizar().setEnabled(false);
                    bibliografia.getBtnCancelar().setEnabled(false);
                    vtnPrincipal.getLblEstado().setText("Guardando silabo... Espere por favor");

                    new SilaboBD(conexion).eliminar(modelo);

                    if (guardarSilabo()) {
                        JOptionPane.showMessageDialog(null, "Silabo guardado exitosamente");

                        vista.dispose();
                        bibliografia.dispose();
                        vtnPrincipal.getMnCtSilabos().doClick();

                    }

                    vtnPrincipal.getLblEstado().setText("");
                    bibliografia.getBtnFinalizar().setEnabled(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Debe agregar al menos una referencia base ", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                accion2 = true;

            }).start();
        }

    }

    private void ejecutar3(ActionEvent e, SilaboMD silabo) {

        if (accion3) {
            new Thread(() -> {
                accion3 = false;
                if (validarCampos()) {

                    if (cambioSilabo) {
                        vista.getBtnGuardar().setEnabled(false);
                        vista.getBtnSiguiente().setEnabled(false);
                        vista.getBtnCancelar().setEnabled(false);
                        vtnPrincipal.getLblEstado().setText("Guardando cambios en el silabo... Espere por favor");

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ControladorSilaboC.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        new SilaboBD(conexion).eliminar(silabo);

                        if (guardarSilabo()) {
                            JOptionPane.showMessageDialog(null, "Cambios guardados exitosamente");
                            cambioSilabo = false;

                        }

                        vtnPrincipal.getLblEstado().setText("");
                        if (!retroceso) {
                            vista.setVisible(false);
                            citarReferencias(silabo, bibliografia);
                            retroceso = true;
                        } else {
                            vista.setVisible(false);
                            bibliografia.setVisible(true);

                        }
                    }

                    vtnPrincipal.getLblEstado().setText("");
                    if (!retroceso) {
                        vista.setVisible(false);
                        citarReferencias(silabo, bibliografia);
                        retroceso = true;
                    } else {
                        vista.setVisible(false);
                        bibliografia.setVisible(true);

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No ha completado correctamente los campos necesarios", "Aviso", JOptionPane.ERROR_MESSAGE);

                }

                vista.getBtnSiguiente().setEnabled(true);
                vista.getBtnCancelar().setEnabled(true);

                accion3 = true;

            }).start();
        }

    }

    public void citarReferencias(SilaboMD silabo, frmReferencias bibliografia) {

        vtnPrincipal.getDpnlPrincipal().add(bibliografia);

        bibliografia.setTitle(silabo.getMateria().getNombre());

        bibliografia.show();

        bibliografia.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - bibliografia.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - bibliografia.getSize().height) / 2);

        bibliografia.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {

                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    cargarBiblioteca();
                }

            }

        });

        bibliografia.getCmbBiblioteca().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                cargarBiblioteca();
            }

        });

        bibliografia.getTblBiblioteca().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                bibliografia.getBtnAgregarBibliografiaBase().setEnabled(true);

            }

        });

        bibliografia.getBtnAgregarBibliografiaBase().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                agregarBibliografiaBase();

            }

        });

        bibliografia.getLstBibliografiaBase().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                if (bibliografia.getLstBibliografiaBase().getSelectedIndex() != -1) {
                    bibliografia.getBtnQuitarBibliografiaBase().setEnabled(true);
                }

            }

        });

        bibliografia.getBtnQuitarBibliografiaBase().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                quitarBibliografiaBase();
            }

        });

        bibliografia.getBtnFinalizar().addActionListener(e -> ejecutar2(e));

        bibliografia.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int reply = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar el proceso?", "Cancelar", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    bibliografia.dispose();
                    vista.dispose();
                    vtnPrincipal.getMnCtSilabos().doClick();
                }
            }

        });

        bibliografia.getBtnAtras().addActionListener(b -> bibliografia.setVisible(false));
        bibliografia.getBtnAtras().addActionListener(b -> vista.setVisible(true));

        cargarBiblioteca();
    }

    public UnidadSilaboMD seleccionarUnidad() {

        Optional<UnidadSilaboMD> unidadSeleccionada = unidades.stream().
                filter(u -> u.getNumeroUnidad() == (vista.getCmbUnidad().getSelectedIndex() + 1)).
                findFirst();

        return unidadSeleccionada.get();
    }

    public void actualizarUnidad(UnidadSilaboMD unidadSeleccionada) {

        unidadSeleccionada.setBandera(true);
        unidades.set(unidadSeleccionada.getNumeroUnidad() - 1, unidadSeleccionada);
        cambioSilabo = true;
        vista.getBtnGuardar().setEnabled(true);

    }

    public void mostrarUnidad() {

        UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

        vista.getTxtTitulo().setText(unidadSeleccionada.getTituloUnidad());
        vista.getTxrContenidos().setText(unidadSeleccionada.getContenidosUnidad());
        vista.getTxrObjetivos().setText(unidadSeleccionada.getObjetivoEspecificoUnidad());
        vista.getTxrResultados().setText(unidadSeleccionada.getResultadosAprendizajeUnidad());

        if (unidadSeleccionada.getFechaInicioUnidad() != null) {
            vista.getDchFechaInicio().setDate(Date.from(unidadSeleccionada.getFechaInicioUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        } else {
            vista.getDchFechaInicio().setDate(null);

        }

        if (unidadSeleccionada.getFechaFinUnidad() != null) {
            vista.getDchFechaFin().setDate(Date.from(unidadSeleccionada.getFechaFinUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        } else {
            vista.getDchFechaFin().setDate(null);
        }

        vista.getSpnHorasDocencia().setValue(unidadSeleccionada.getHorasDocenciaUnidad());
        vista.getSpnHorasPracticas().setValue(unidadSeleccionada.getHorasPracticaUnidad());
        vista.getSpnHorasAutonomas().setValue(unidadSeleccionada.getHorasAutonomoUnidad());

        cargarEstrategias(unidadSeleccionada);

        cargarEvaluaciones((DefaultTableModel) vista.getTblAsistidaDocente().getModel(), 1);

        cargarEvaluaciones((DefaultTableModel) vista.getTblAprendizajeColaborativo().getModel(), 2);

        cargarEvaluaciones((DefaultTableModel) vista.getTblPractica().getModel(), 3);

        cargarEvaluaciones((DefaultTableModel) vista.getTblAutonoma().getModel(), 4);

    }

    public void cargarEstrategias(UnidadSilaboMD unidadSeleccionada) {

        DefaultListModel modeloEstrategias = new DefaultListModel();

        vista.getLstEstrategiasPredeterminadas().setCellRenderer(new CheckListRenderer());
        vista.getLstEstrategiasPredeterminadas().setModel(modeloEstrategias);

        EstrategiasAprendizajeBD.consultar(conexion).forEach((emd) -> {
            modeloEstrategias.addElement(new CheckListItem(emd.getDescripcionEstrategia()));
        });

        for (int i = 0; i < vista.getLstEstrategiasPredeterminadas().getModel().getSize(); i++) {
            CheckListItem item = (CheckListItem) vista.getLstEstrategiasPredeterminadas().getModel().getElementAt(i);

            for (EstrategiasUnidadMD emd : estrategias) {

                if (emd.getIdUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad()
                        && modeloEstrategias.get(i).toString().equals(emd.getIdEstrategia().getDescripcionEstrategia())) {

                    item.setSelected(true);
                    System.out.println("-------------------------------paso_por aki_   __________________");
                }
            }
        }
    }

    public void agregarUnidad() {

        UnidadSilaboMD nuevaUnidad = new UnidadSilaboMD();
        nuevaUnidad.setNumeroUnidad(unidades.size() + 1);
        unidades.add(nuevaUnidad);
        vista.getCmbUnidad().addItem("Unidad " + unidades.size());
        JOptionPane.showMessageDialog(null, "Nueva unidad agregada");

        vista.getCmbUnidad().setSelectedIndex(unidades.size() - 1);

    }

    public void eliminarUnidad() {

        UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

        unidades.removeIf(u -> u.getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad());

        estrategias.removeIf(e -> e.getIdUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad());

        evaluaciones.removeIf(e -> e.getIdUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad());

        for (EstrategiasUnidadMD emd : estrategias) {
            if (emd.getIdUnidad().getNumeroUnidad() > unidadSeleccionada.getNumeroUnidad()) {
                emd.getIdUnidad().setNumeroUnidad(emd.getIdUnidad().getNumeroUnidad() - 1);
            }
        }

        for (EvaluacionSilaboMD esd : evaluaciones) {
            if (esd.getIdUnidad().getNumeroUnidad() > unidadSeleccionada.getNumeroUnidad()) {
                esd.getIdUnidad().setNumeroUnidad(esd.getIdUnidad().getNumeroUnidad() - 1);

            }
        }

        for (UnidadSilaboMD umd : unidades) {
            if (umd.getNumeroUnidad() > unidadSeleccionada.getNumeroUnidad()) {
                umd.setNumeroUnidad(umd.getNumeroUnidad() - 1);

            }
        }

        List<String> comboNuevo = new ArrayList<>();

        for (int i = 1; i <= unidades.size(); i++) {
            comboNuevo.add("Unidad " + (i));
        }

        vista.getCmbUnidad().setModel(new DefaultComboBoxModel(comboNuevo.toArray()));

        if (unidadSeleccionada.getNumeroUnidad() == 1) {
            vista.getCmbUnidad().setSelectedIndex(0);

        } else if (unidadSeleccionada.getNumeroUnidad() == unidades.size() + 1) {

            vista.getCmbUnidad().setSelectedIndex(unidadSeleccionada.getNumeroUnidad() - 2);
        } else {
            vista.getCmbUnidad().setSelectedIndex(unidadSeleccionada.getNumeroUnidad() - 1);
        }

    }

    public boolean validarLimiteEvaluaciones(double valor) {

        double total = 0;

        total = evaluaciones.stream().map((emd) -> emd.getValoracion()).reduce(total, (accumulator, _item) -> accumulator + _item);

        return (total + valor) <= 60.0;

    }

    public TipoActividadMD seleccionarTipoActividad(String[] infoE) {

        Optional<TipoActividadMD> tipoSeleccionado = tiposActividad.stream().
                filter(a -> a.getNombreTipoActividad().equals(infoE[0]) && a.getNombreSubtipoActividad().equals(infoE[1])).
                findFirst();

        return tipoSeleccionado.get();

    }

    public void agregarEvaluacion(TipoActividadMD tipo, UnidadSilaboMD unidad, int p) {

        switch (p) {
            case 1:

                if (validarLimiteEvaluaciones((double) (vista.getSpnValoracionAD().getValue()))) {
                    ++idEvaluacionSig;
                    idEvaluacion = idEvaluacionSig;
                    evaluaciones.add(new EvaluacionSilaboMD(idEvaluacion, vista.getTxtIndicadorAD().getText(), vista.getTxtInstrumentoAD().getText(), (double) (vista.getSpnValoracionAD().getValue()), vista.getDchFechaEnvioAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), vista.getDchFechaPresentacionAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), tipo, unidad));
                    cargarEvaluaciones((DefaultTableModel) vista.getTblAsistidaDocente().getModel(), 1);
                    limpiarEvaluacionesAD();
                    vista.getBtnGuardar().setEnabled(true);
                    cambioSilabo = true;
                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder los 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                break;
            case 2:

                if (validarLimiteEvaluaciones((double) (vista.getSpnValoracionAC().getValue()))) {
                    ++idEvaluacionSig;
                    idEvaluacion = idEvaluacionSig;
                    evaluaciones.add(new EvaluacionSilaboMD(idEvaluacion, vista.getTxtIndicadorAC().getText(), vista.getTxtInstrumentoAC().getText(), (double) (vista.getSpnValoracionAC().getValue()), vista.getDchFechaEnvioAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), vista.getDchFechaPresentacionAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), tipo, unidad));
                    cargarEvaluaciones((DefaultTableModel) vista.getTblAprendizajeColaborativo().getModel(), 2);
                    limpiarEvaluacionesAC();
                    vista.getBtnGuardar().setEnabled(true);
                    cambioSilabo = true;
                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder los 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                break;
            case 3:
                if (validarLimiteEvaluaciones((double) (vista.getSpnValoracionP().getValue()))) {
                    ++idEvaluacionSig;
                    idEvaluacion = idEvaluacionSig;
                    evaluaciones.add(new EvaluacionSilaboMD(idEvaluacion, vista.getTxtIndicadorP().getText(), vista.getTxtInstrumentoP().getText(), (double) (vista.getSpnValoracionP().getValue()), vista.getDchFechaEnvioP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), vista.getDchFechaPresentacionP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), tipo, unidad));
                    cargarEvaluaciones((DefaultTableModel) vista.getTblPractica().getModel(), 3);
                    limpiarEvaluacionesP();
                    vista.getBtnGuardar().setEnabled(true);
                    cambioSilabo = true;
                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder los 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                break;
            case 4:
                if (validarLimiteEvaluaciones((double) (vista.getSpnValoracionA().getValue()))) {
                    ++idEvaluacionSig;
                    idEvaluacion = idEvaluacionSig;
                    evaluaciones.add(new EvaluacionSilaboMD(idEvaluacion, vista.getTxtIndicadorA().getText(), vista.getTxtInstrumentoA().getText(), (double) (vista.getSpnValoracionA().getValue()), vista.getDchFechaEnvioA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), vista.getDchFechaPresentacionA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), tipo, unidad));
                    cargarEvaluaciones((DefaultTableModel) vista.getTblAutonoma().getModel(), 4);
                    limpiarEvaluacionesA();
                    vista.getBtnGuardar().setEnabled(true);
                    cambioSilabo = true;
                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder el valor de 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }
                break;
        }

        mostrarTotalGestion();
    }

    public void cargarEvaluaciones(DefaultTableModel modeloTabla, int p) {

        UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

        switch (p) {
            case 1:

                modeloTabla.setRowCount(0);
                break;
            case 2:
                modeloTabla.setRowCount(0);

                break;
            case 3:
                modeloTabla.setRowCount(0);

                break;
            case 4:
                modeloTabla.setRowCount(0);

                break;
        }

        for (EvaluacionSilaboMD emd : evaluaciones) {

            if (emd.getIdUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad()
                    && emd.getIdTipoActividad().getIdTipoActividad() == p) {

                modeloTabla.addRow(new Object[]{
                    emd.getIndicador(),
                    emd.getInstrumento(),
                    emd.getValoracion(),
                    emd.getFechaEnvio(),
                    emd.getFechaPresentacion(),
                    emd.getIdEvaluacion()
                });

            }

        }
    }

    public void limpiarEvaluacionesAD() {

        vista.getTxtInstrumentoAD().setText(null);
        vista.getTxtIndicadorAD().setText(null);
        vista.getSpnValoracionAD().setValue(1.0d);
        vista.getDchFechaEnvioAD().setDate(null);
        vista.getDchFechaPresentacionAD().setDate(null);

    }

    public void limpiarEvaluacionesAC() {

        vista.getTxtInstrumentoAC().setText(null);
        vista.getTxtIndicadorAC().setText(null);
        vista.getSpnValoracionAC().setValue(1.0d);

        vista.getDchFechaPresentacionAC().setDate(null);
        vista.getDchFechaEnvioAC().setDate(null);

    }

    public void limpiarEvaluacionesP() {

        vista.getTxtInstrumentoP().setText(null);
        vista.getTxtIndicadorP().setText(null);
        vista.getSpnValoracionP().setValue(1.0d);
        vista.getDchFechaEnvioP().setDate(null);
        vista.getDchFechaPresentacionP().setDate(null);

    }

    public void limpiarEvaluacionesA() {

        vista.getTxtInstrumentoA().setText(null);
        vista.getTxtIndicadorA().setText(null);
        vista.getSpnValoracionA().setValue(1.0d);
        vista.getDchFechaEnvioA().setDate(null);
        vista.getDchFechaPresentacionA().setDate(null);

    }

    public void cargarBiblioteca() {

        DefaultTableModel modeloTabla;

        modeloTabla = (DefaultTableModel) bibliografia.getTblBiblioteca().getModel();

        if (bibliografia.getCmbBiblioteca().getSelectedIndex() == 0) {
            biblioteca = ReferenciasBD.consultarBiblioteca(conexion, bibliografia.getTxtBuscar().getText());
        } else {

            biblioteca = ReferenciasBD.consultarVirtual(conexion, bibliografia.getTxtBuscar().getText());
        }

        modeloTabla.setRowCount(0);

        for (ReferenciasMD rmd : biblioteca) {

            modeloTabla.addRow(new Object[]{
                rmd.getCodigoReferencia(),
                rmd.getDescripcionReferencia()
            });

        }

        bibliografia.getTblBiblioteca().setModel(modeloTabla);

    }

    public void agregarBibliografiaBase() {

        List<String> b = new ArrayList<>();

        ReferenciasMD referenciaSeleccionada = seleccionarReferencia();

        boolean nuevo = true;

        int i = 0;

        while (nuevo && i < referenciasSilabo.size()) {
            if (referenciasSilabo.
                    get(i).getIdReferencia().
                    getDescripcionReferencia().equals(referenciaSeleccionada.getDescripcionReferencia())) {
                nuevo = false;
            }

            i++;
        }

        if (nuevo) {
            ReferenciaSilaboMD rsm = new ReferenciaSilaboMD(referenciaSeleccionada, modelo);
            referenciasSilabo.add(rsm);

        }

        referenciasSilabo.forEach((rsm) -> {
            if (rsm.getIdReferencia().getTipoReferencia().equals("Base")) {
                b.add("• " + rsm.getIdReferencia().getDescripcionReferencia());
            }

        });
        tablaM = new DefaultListModel<>();

        b.forEach((s) -> {
            tablaM.addElement(s);
        });

        bibliografia.getLstBibliografiaBase().setModel(tablaM);

    }

    public void agregarBibliografiaNoBase() {

        referenciasSilabo.removeIf(r -> r.getIdReferencia().getTipoReferencia().equals("Complementaria") || r.getIdReferencia().getTipoReferencia().equals("Linkografia"));

        ReferenciasMD complementaria = new ReferenciasMD(String.valueOf(modelo.getID()), bibliografia.getTxrBibliografiaComplementaria().getText(), "Complementaria");
        ReferenciasMD linkografia = new ReferenciasMD(String.valueOf(modelo.getID()), bibliografia.getTxrLinkografia().getText(), "Linkografia");

        referenciasSilabo.add(new ReferenciaSilaboMD(complementaria, modelo));
        referenciasSilabo.add(new ReferenciaSilaboMD(linkografia, modelo));

    }

    public void quitarBibliografiaBase() {

        String seleccion = bibliografia.getLstBibliografiaBase().getModel().getElementAt(bibliografia.getLstBibliografiaBase().getSelectedIndex()).substring(2);

        referenciasSilabo.removeIf(p -> p.getIdReferencia().getDescripcionReferencia().equals(seleccion));

        tablaM.remove(bibliografia.getLstBibliografiaBase().getSelectedIndex());

        if (bibliografia.getLstBibliografiaBase().getSelectedIndex() != -1) {
            bibliografia.getBtnQuitarBibliografiaBase().setEnabled(true);
        } else {
            bibliografia.getBtnQuitarBibliografiaBase().setEnabled(false);
        }
    }

    public ReferenciasMD seleccionarReferencia() {

        int seleccion = bibliografia.getTblBiblioteca().getSelectedRow();
        Optional<ReferenciasMD> referenciaSeleccionada = biblioteca.stream().
                filter(r -> r.getCodigoReferencia().equals(bibliografia.getTblBiblioteca().getValueAt(seleccion, 0))).
                findFirst();

        return referenciaSeleccionada.get();
    }

    public void quitarEvaluacionAD(DefaultTableModel modeloTabla, int p) {
        System.out.println("ANTESSS ");
        evaluaciones.forEach(es -> {
            System.out.println(es.getInstrumento());
        });
        evaluaciones.removeIf(e -> e.getIdEvaluacion() == vista.getTblAsistidaDocente().getValueAt(vista.getTblAsistidaDocente().getSelectedRow(), 5));
        System.out.println("DESPUESSSS ");
        evaluaciones.forEach(es -> {
            System.out.println(es.getInstrumento());
        });
        cargarEvaluaciones(modeloTabla, p);
        mostrarTotalGestion();
        cambioSilabo = true;
        vista.getBtnGuardar().setEnabled(true);

    }

    public void quitarEvaluacionAC(DefaultTableModel modeloTabla, int p) {

        evaluaciones.removeIf(e -> e.getIdEvaluacion() == vista.getTblAprendizajeColaborativo().getValueAt(vista.getTblAprendizajeColaborativo().getSelectedRow(), 5));

        cargarEvaluaciones(modeloTabla, p);
        mostrarTotalGestion();
        cambioSilabo = true;
        vista.getBtnGuardar().setEnabled(true);
    }

    public void quitarEvaluacionP(DefaultTableModel modeloTabla, int p) {

        evaluaciones.removeIf(e -> e.getIdEvaluacion() == vista.getTblPractica().getValueAt(vista.getTblPractica().getSelectedRow(), 5));

        cargarEvaluaciones(modeloTabla, p);
        mostrarTotalGestion();
        cambioSilabo = true;
        vista.getBtnGuardar().setEnabled(true);
    }

    public void quitarEvaluacionA(DefaultTableModel modeloTabla, int p) {

        evaluaciones.removeIf(e -> e.getIdEvaluacion() == vista.getTblAutonoma().getValueAt(vista.getTblAutonoma().getSelectedRow(), 5));

        cargarEvaluaciones(modeloTabla, p);
        mostrarTotalGestion();
        cambioSilabo = true;
        vista.getBtnGuardar().setEnabled(true);
    }

    public void cargarReferencias(List<ReferenciaSilaboMD> referenciasSilabo) {

        List<String> b = new ArrayList<>();

        for (int i = 0; i < referenciasSilabo.size(); i++) {

            if (referenciasSilabo.get(i).getIdReferencia().getTipoReferencia().equals("Base")) {
                b.add("• " + referenciasSilabo.get(i).getIdReferencia().getDescripcionReferencia());

            } else if (referenciasSilabo.get(i).getIdReferencia().getTipoReferencia().equals("Complementaria")) {

                bibliografia.getTxrBibliografiaComplementaria().setText(referenciasSilabo.get(i).getIdReferencia().getDescripcionReferencia());

            } else if (referenciasSilabo.get(i).getIdReferencia().getTipoReferencia().equals("Linkografia")) {

                bibliografia.getTxrLinkografia().setText(referenciasSilabo.get(i).getIdReferencia().getDescripcionReferencia());

            }

        }

        tablaM = new DefaultListModel<>();
        b.forEach((s) -> {
            tablaM.addElement(s);
        });

        bibliografia.getLstBibliografiaBase().setModel(tablaM);

    }

    public int insertarUnidades() {

        modelo.setID(SilaboBD.consultarUltimo(conexion, modelo.getMateria().getId(), modelo.getPeriodo().getId()).getID());

        for (UnidadSilaboMD umd : unidades) {

            umd.getIdSilabo().setID(modelo.getID());
            UnidadSilaboBD ubd = new UnidadSilaboBD(conexion);
            ubd.insertar(umd, umd.getIdSilabo().getID());

            for (EstrategiasUnidadMD emd : estrategias) {

                if (emd.getIdUnidad().getNumeroUnidad() == umd.getNumeroUnidad()) {
                    EstrategiasUnidadBD ebd = new EstrategiasUnidadBD(conexion);
                    ebd.insertar(emd, UnidadSilaboBD.consultarUltima(conexion, umd.getIdSilabo().getID(), umd.getNumeroUnidad()).getIdUnidad());
                }

            }

            for (EvaluacionSilaboMD evd : evaluaciones) {

                if (evd.getIdUnidad().getNumeroUnidad() == umd.getNumeroUnidad()) {
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ ENRTRAMOS " + umd.getNumeroUnidad());
                    EvaluacionSilaboBD esd = new EvaluacionSilaboBD(conexion);
                    esd.insertar(evd, UnidadSilaboBD.consultarUltima(conexion, umd.getIdSilabo().getID(), umd.getNumeroUnidad()).getIdUnidad());
                }

            }
        }

        return modelo.getID();
    }

    public void insertarReferencias(int is) {

        agregarBibliografiaNoBase();

        for (int i = 0; i < referenciasSilabo.size() - 2; i++) {
            ReferenciaSilaboBD rbd = new ReferenciaSilaboBD(conexion);

            rbd.insertar(referenciasSilabo.get(i), is, 0);

        }

        ReferenciasBD r1 = new ReferenciasBD(conexion);
        r1.insertar(referenciasSilabo.get(referenciasSilabo.size() - 2).getIdReferencia(), 0);
        ReferenciaSilaboBD rbd1 = new ReferenciaSilaboBD(conexion);
        rbd1.insertar(referenciasSilabo.get(referenciasSilabo.size() - 2), is, 0);

        ReferenciasBD r2 = new ReferenciasBD(conexion);
        r2.insertar(referenciasSilabo.get(referenciasSilabo.size() - 1).getIdReferencia(), 0);
        ReferenciaSilaboBD rbd2 = new ReferenciaSilaboBD(conexion);
        rbd2.insertar(referenciasSilabo.get(referenciasSilabo.size() - 1), is, 0);

    }

    public boolean guardarSilabo() {

        try {
            new SilaboBD(conexion).insertar(modelo);

            int is = insertarUnidades();

            insertarReferencias(is);

            unidades = UnidadSilaboBD.consultar(conexion, modelo.getID(), 1);

            for (UnidadSilaboMD umd : unidades) {

                umd.setBandera(false);

            }

            biblioteca = new ArrayList<>();

            tiposActividad = TipoActividadBD.consultar(conexion);

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se ha perdido conexion, por favor intente guardar otra vez", "Error", JOptionPane.ERROR_MESSAGE);
            vista.getBtnGuardar().setEnabled(true);
            vista.getBtnSiguiente().setEnabled(true);
            bibliografia.getBtnFinalizar().setEnabled(true);
            cambioSilabo = false;
            return false;
        }

    }

    public boolean validarCampos() {

        boolean control = true;

        int contador = 0;
        double aprovechamiento = 0.0;

        for (int i = 0; i < unidades.size(); i++) {

            if (unidades.get(i).getTituloUnidad() == null) {
                control = false;
            }

            if (unidades.get(i).getObjetivoEspecificoUnidad() == null) {
                control = false;
            }

            if (unidades.get(i).getResultadosAprendizajeUnidad() == null) {
                control = false;
            }

            if (unidades.get(i).getContenidosUnidad() == null) {
                control = false;
            }

            if (unidades.get(i).getFechaInicioUnidad() == null) {
                control = false;
            }

            if (unidades.get(i).getFechaFinUnidad() == null) {
                control = false;
            }

            for (int j = 0; j < estrategias.size(); j++) {
                if (estrategias.get(j).getIdUnidad().getNumeroUnidad() == (unidades.get(i).getNumeroUnidad())) {
                    contador++;
                }

            }

            for (int k = 0; k < evaluaciones.size(); k++) {
                aprovechamiento = aprovechamiento + evaluaciones.get(k).getValoracion();
            }

            if (contador == 0) {
                control = false;
            }

            if (aprovechamiento < 60.0) {
                control = false;
            }

        }

        return control;

    }

    public void mostrarTotalGestion() {

        double total = 0;

        total = evaluaciones.stream().map((emd) -> emd.getValoracion()).reduce(total, (accumulator, _item) -> accumulator + _item);

        vista.getLblAcumuladoGestion().setText(total + "/60");

    }

    public boolean validarBiblioBase() {

        boolean control = false;

        for (ReferenciaSilaboMD rsd : referenciasSilabo) {

            if (rsd.getIdReferencia().getTipoReferencia().equals("Base")) {
                control = true;
            }

        }

        return control;

    }
     */
}
