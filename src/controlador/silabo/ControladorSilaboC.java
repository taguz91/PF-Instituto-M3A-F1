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
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.estrategiasAprendizaje.EstrategiasAprendizajeBD;
import modelo.estrategiasAprendizaje.EstrategiasAprendizajeMD;
import modelo.estrategiasUnidad.EstrategiasUnidadBD;
import modelo.estrategiasUnidad.EstrategiasUnidadMD;
import modelo.evaluacionSilabo.EvaluacionSilaboBD;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
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
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.VtnConfigSilabo;
import vista.silabos.frmGestionSilabo;
import vista.silabos.frmGestionSilabo.CheckListItem;
import vista.silabos.frmGestionSilabo.CheckListRenderer;
import vista.silabos.frmReferencias;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres Ullauri
 */
public class ControladorSilaboC extends AbstractSilaboCTR {

    private SilaboBD silaboNuevo;
    private SilaboMD silaboAnterior;
    private VtnConfigSilabo configuracion = new VtnConfigSilabo();
    private frmGestionSilabo gestion;
    private frmReferencias bibliografia;
    private UsuarioBD usuario;
    private List<CarreraMD> carrerasDocente;
    private List<SilaboMD> silabosAnteriores;
    private List<PeriodoLectivoMD> periodosCarrera;
    private List<MateriaMD> materiasDocente;

    private static Integer idEvaluacionSig = 0;
    private Integer idEvaluacion;
    private boolean retroceso = false;

    private boolean cambioSilabo = false;

    public ControladorSilaboC(SilaboMD modelo, VtnPrincipal vtnPrincipal, ConexionBD conexion) {
        super(modelo, vtnPrincipal, conexion);
    }

    public void iniciarControlador() {

        vtnPrincipal.getDpnlPrincipal().add(configuracion);

        configuracion.setTitle("Silabo");

        configuracion.show();

        configuracion.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - configuracion.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - configuracion.getSize().height) / 2);

        cargarComboCarreras();
        /*
        configuracion.getCmbCarrera().addActionListener((ActionEvent ae) -> {

            configuracion.getCmbPeriodo().removeAllItems();

            Optional<CarreraMD> carreraSeleccionada = carrerasDocente.stream().
                    filter(c -> c.getNombre().equals(configuracion.getCmbCarrera().getSelectedItem().toString())).
                    findFirst();

            periodosCarrera = PeriodoLectivoBDS.consultar(conexion, carreraSeleccionada.get().getId());

            configuracion.getCmbAsignatura().removeAllItems();
            System.out.println("-------------------" + periodosCarrera.get(0).getNombre());
            cargarComboMaterias(carreraSeleccionada.get().getId(), periodosCarrera.get(0).getID());

        });
        configuracion.getBtnSiguiente().addActionListener((ActionEvent ae) -> {

            Optional<MateriaMD> materiaSeleccionada = materiasDocente.stream().
                    filter(m -> m.getNombre().equals(configuracion.getCmbAsignatura().getSelectedItem().toString())).
                    findFirst();

            if (configuracion.getCmbPeriodo().getItemCount() > 0) {

                System.out.println("-----------------------------entro");
                for (SilaboMD s : silabosAnteriores) {
                    System.out.println(materiaSeleccionada.get().getId() + " - " + s.getMateria() + " - " + s.getPeriodo().getNombre() + " - " + configuracion.getCmbPeriodo().getSelectedItem().toString());
                }
                Optional<SilaboMD> silaboSeleccionado = silabosAnteriores.stream().
                        filter(s -> s.getMateria().getId() == (materiaSeleccionada.get().getId()) && s.getPeriodo().getNombre().equals(configuracion.getCmbPeriodo().getSelectedItem().toString())).
                        findFirst();
                silaboAnterior = silaboSeleccionado.get();
            }

            silaboNuevo = new SilaboBD(conexion, materiaSeleccionada.get(), periodosCarrera.stream().findFirst().get());

            iniciarSilabo(silaboNuevo, silaboAnterior, (int) configuracion.getSpnUnidades().getValue());

            configuracion.dispose();

        });

        configuracion.getBtnCancelar().addActionListener((ActionEvent ae) -> {
            int reply = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar el proceso?", "Cancelar", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                configuracion.dispose();
                vtnPrincipal.getMnCtSilabos().doClick();

            }
        });

        configuracion.getCmbAsignatura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (configuracion.getCmbAsignatura().getItemCount() > 0) {
                    Optional<MateriaMD> materiaSeleccionada = materiasDocente.stream().
                            filter(m -> m.getNombre().equals(configuracion.getCmbAsignatura().getSelectedItem().toString())).
                            findFirst();

                    configuracion.getCmbPeriodo().removeAllItems();
                    cargarComboPeriodos(materiaSeleccionada.get().getId());
                }

            }

        });

        configuracion.getCmbCarrera().setSelectedIndex(0);
         */
    }

    public void cargarComboCarreras() {
/*
        carrerasDocente = CarrerasBDS.consultar(conexion, usuario.getUsername());

        carrerasDocente.forEach((cmd) -> {
            configuracion.getCmbCarrera().addItem(cmd.getNombre());
        });
*/
    }

    public void cargarComboMaterias(int idCarrera, int idPeriodo) {
        /*
        String[] parametros = {usuario.getUsername(), String.valueOf(idCarrera), String.valueOf(idPeriodo)};

        materiasDocente = MateriasBDS.consultar(conexion, parametros);

        materiasDocente.forEach((cmd) -> {
            configuracion.getCmbAsignatura().addItem(cmd.getNombre());
        });

        if (configuracion.getCmbAsignatura().getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "No tiene silabos pendientes para esta carrera dentro del periodo en curso ", "Aviso", JOptionPane.WARNING_MESSAGE);
            configuracion.getCmbAsignatura().setEnabled(false);
            configuracion.getCmbPeriodo().setEnabled(false);
            configuracion.getBtnSiguiente().setEnabled(false);
            configuracion.getSpnUnidades().setEnabled(false);
        } else {
            configuracion.getCmbAsignatura().setEnabled(true);
            configuracion.getCmbPeriodo().setEnabled(true);
            configuracion.getBtnSiguiente().setEnabled(true);

        }
         */
    }

    public void cargarComboPeriodos(int idMateria) {
/*
        Integer[] parametros = {idMateria, usuario.getPersona().getIdPersona()};
        silabosAnteriores = SilaboBD.consultarAnteriores(conexion, parametros);

        silabosAnteriores.forEach((prd) -> {
            configuracion.getCmbPeriodo().addItem(prd.getPeriodo().getNombre());
        });

        if (silabosAnteriores.size() > 0) {
            configuracion.getCmbPeriodo().setEnabled(true);
            configuracion.getSpnUnidades().setEnabled(false);
        } else {
            configuracion.getCmbPeriodo().setEnabled(false);
            configuracion.getSpnUnidades().setEnabled(true);
        }
*/
    }

    public void iniciarSilabo(SilaboBD silabo, SilaboMD silaboAnterior, int numUnidades) {

        tiposActividad = TipoActividadBD.consultar(conexion);

        gestion = new frmGestionSilabo();

        bibliografia = new frmReferencias();

        //gestion.getBtnGuardar().setEnabled(false);

        cambioSilabo = false;

        if (silaboAnterior == null) {
            carrerasDocente = new ArrayList<>();

            periodosCarrera = new ArrayList<>();

            materiasDocente = new ArrayList<>();

            unidades = new ArrayList<>();

            estrategias = new ArrayList<>();

            evaluaciones = new ArrayList<>();

            biblioteca = new ArrayList<>();

            referenciasSilabo = new ArrayList<>();

            for (int i = 1; i <= numUnidades; i++) {

                UnidadSilaboMD tmp = new UnidadSilaboMD();
                tmp.setNumeroUnidad(i);
                unidades.add(tmp);
            }
        } else {

            unidades = UnidadSilaboBD.consultar(conexion, silaboAnterior.getID(), 0);

            estrategias = EstrategiasUnidadBD.cargarEstrategiasU(conexion, silaboAnterior.getID());

            evaluaciones = new ArrayList<>();

            //evaluacionesSilabo = EvaluacionSilaboBD.recuperarEvaluaciones(conexion, silaboAnterior.getIdSilabo());
            biblioteca = new ArrayList<>();

            referenciasSilabo = ReferenciaSilaboBD.recuperarReferencias(conexion, silaboAnterior.getID());

            tiposActividad = TipoActividadBD.consultar(conexion);

            cargarReferencias(referenciasSilabo);

        }

        mostrarTotalGestion();

        vtnPrincipal.getDpnlPrincipal().add(gestion);

        gestion.setTitle(silabo.getMateria().getNombre());

        gestion.show();

        gestion.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - gestion.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - gestion.getSize().height) / 2);

        unidades.forEach((umd) -> {
            gestion.getCmbUnidad().addItem("Unidad " + umd.getNumeroUnidad());
        });

        gestion.getCmbUnidad().setSelectedIndex(0);

        gestion.getBtnGuardar().setEnabled(false);

        gestion.getCmbUnidad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                mostrarUnidad();

            }

        });

        gestion.getTxtTitulo().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {

                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                unidadSeleccionada.setTituloUnidad(gestion.getTxtTitulo().getText());
                actualizarUnidad(unidadSeleccionada);

            }

        });

        gestion.getTxrObjetivos().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {

                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                unidadSeleccionada.setObjetivoEspecificoUnidad(gestion.getTxrObjetivos().getText());
                actualizarUnidad(unidadSeleccionada);

            }

        });

        gestion.getTxrContenidos().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {

                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                unidadSeleccionada.setContenidosUnidad(gestion.getTxrContenidos().getText());
                actualizarUnidad(unidadSeleccionada);

            }

        });

        gestion.getTxrResultados().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                unidadSeleccionada.setResultadosAprendizajeUnidad(gestion.getTxrResultados().getText());
                actualizarUnidad(unidadSeleccionada);
                //Prueba CHACON
            }

        });

        gestion.getLblAgregarEstrategia().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                PlaceHolder holder = new PlaceHolder(gestion.getTxtNuevaEstrategia(), "Ingrese la nueva estrategia...");

                gestion.getTxtNuevaEstrategia().setEnabled(true);
                gestion.getLblGuardarEstrategia().setEnabled(true);
                gestion.getLstEstrategiasPredeterminadas().requestFocusInWindow();
            }

        });

        gestion.getLblGuardarEstrategia().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                int limite = 110;
                if (gestion.getTxtNuevaEstrategia().getText().length() <= limite) {

                    boolean existe = false;

                    EstrategiasAprendizajeBD nuevaEstrategia = new EstrategiasAprendizajeBD(conexion, gestion.getTxtNuevaEstrategia().getText());

                    List<EstrategiasAprendizajeMD> estrategias = EstrategiasAprendizajeBD.consultar(conexion);

                    for (EstrategiasAprendizajeMD e : estrategias) {

                        if (e.getDescripcionEstrategia().toUpperCase().trim().equals(gestion.getTxtNuevaEstrategia().getText().toUpperCase().trim())) {
                            existe = true;
                            JOptionPane.showMessageDialog(null, "La estrategia que intentó ingresar ya existe", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }

                    }

                    if (!existe) {
                        if (gestion.getTxtNuevaEstrategia().getText().isEmpty() || gestion.getTxtNuevaEstrategia().getText().equals("Ingrese la nueva estrategia...")) {
                            JOptionPane.showMessageDialog(null, "No ha ingresado ninguna estrategia", "Aviso", JOptionPane.WARNING_MESSAGE);

                        } else {
                            nuevaEstrategia.insertar();
                            JOptionPane.showMessageDialog(null, "Nueva estrategia guardada correctamente.");

                        }
                    }

                    gestion.getTxtNuevaEstrategia().setText("");
                    gestion.getTxtNuevaEstrategia().setEnabled(false);
                    gestion.getLblGuardarEstrategia().setEnabled(false);

                    cargarEstrategias(seleccionarUnidad());

                } else {
                    JOptionPane.showMessageDialog(null, "El texto excede el numero máximo de caracteres! Ingrese de nuevo");
                }

            }
        });

        gestion.getLstEstrategiasPredeterminadas().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {

                int index = gestion.getLstEstrategiasPredeterminadas().locationToIndex(event.getPoint());
                CheckListItem item = (CheckListItem) gestion.getLstEstrategiasPredeterminadas().getModel().getElementAt(index);
                item.setSelected(!item.isSelected());
                gestion.getLstEstrategiasPredeterminadas().repaint(gestion.getLstEstrategiasPredeterminadas().getCellBounds(index, index));
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

                estrategias.removeIf(e -> {
                    return e.getUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad();
                });

                for (int i = 0; i < gestion.getLstEstrategiasPredeterminadas().getModel().getSize(); i++) {
                    CheckListItem item2 = (CheckListItem) gestion.getLstEstrategiasPredeterminadas().getModel().getElementAt(i);

                    if (item2.isSelected()) {

                        String estrategia = gestion.getLstEstrategiasPredeterminadas().getModel().getElementAt(i).toString();

                        Optional<EstrategiasAprendizajeMD> estrategiaSeleccionada = EstrategiasAprendizajeBD.consultar(conexion).stream().
                                filter(e -> e.getDescripcionEstrategia().equals(estrategia)).
                                findFirst();

                        estrategias.add(new EstrategiasUnidadMD(estrategiaSeleccionada.get(), unidadSeleccionada));

                        cambioSilabo = true;
                        gestion.getBtnGuardar().setEnabled(true);

                        System.out.println(estrategiaSeleccionada.get().getDescripcionEstrategia() + " - " + unidadSeleccionada.getNumeroUnidad());

                    }
                }

            }
        });

        gestion.getDchFechaInicio().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

                if (gestion.getDchFechaInicio().getDate() != null) {
                    LocalDate fechaInicio = gestion.getDchFechaInicio().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (unidadSeleccionada.getFechaFinUnidad() == null) {
                        unidadSeleccionada.setFechaInicioUnidad(fechaInicio);
                        actualizarUnidad(unidadSeleccionada);
                    } else {
                        if (unidadSeleccionada.getFechaFinUnidad().isAfter(fechaInicio)
                                || unidadSeleccionada.getFechaFinUnidad().equals(fechaInicio)) {
                            unidadSeleccionada.setFechaInicioUnidad(fechaInicio);
                            actualizarUnidad(unidadSeleccionada);
                        } else {
                            JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser posterior a la fecha de fin", "Alerta", JOptionPane.WARNING_MESSAGE);
                            gestion.getDchFechaInicio().setDate(Date.from(unidadSeleccionada.getFechaFinUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant().minus(1, ChronoUnit.DAYS)));
                        }
                    }
                }

            }

        });

        gestion.getDchFechaFin().addPropertyChangeListener("date", (PropertyChangeEvent pce) -> {
            UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
            
            if (gestion.getDchFechaFin().getDate() != null) {
                LocalDate fechaFin = gestion.getDchFechaFin().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
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
                        
                        gestion.getDchFechaFin().setDate(Date.from(unidadSeleccionada.getFechaInicioUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));
                    }
                }

            }
        });

        gestion.getDchFechaPresentacionAD().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (gestion.getDchFechaEnvioAD().getDate() != null && gestion.getDchFechaPresentacionAD().getDate() != null) {
                    LocalDate fechaPresentacion = gestion.getDchFechaPresentacionAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = gestion.getDchFechaEnvioAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (fechaPresentacion.isBefore(fechaEnvio)) {

                        JOptionPane.showMessageDialog(null, "La fecha de presentación no puede ser anterior a la fecha de envío", "Alerta", JOptionPane.WARNING_MESSAGE);

                        gestion.getDchFechaPresentacionAD().setDate(Date.from(fechaEnvio.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        gestion.getDchFechaEnvioAD().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (gestion.getDchFechaPresentacionAD().getDate() != null && gestion.getDchFechaEnvioAD().getDate() != null) {
                    LocalDate fechaPresentacion = gestion.getDchFechaPresentacionAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = gestion.getDchFechaEnvioAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (fechaEnvio.isAfter(fechaPresentacion)) {

                        JOptionPane.showMessageDialog(null, "La fecha de envío no puede ser posterior a la fecha de presentación", "Alerta", JOptionPane.WARNING_MESSAGE);

                        gestion.getDchFechaEnvioAD().setDate(Date.from(fechaPresentacion.atStartOfDay(ZoneId.systemDefault()).toInstant().minus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        gestion.getDchFechaPresentacionAC().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (gestion.getDchFechaEnvioAC().getDate() != null && gestion.getDchFechaPresentacionAC().getDate() != null) {
                    LocalDate fechaPresentacion = gestion.getDchFechaPresentacionAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = gestion.getDchFechaEnvioAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (fechaPresentacion.isBefore(fechaEnvio)) {

                        JOptionPane.showMessageDialog(null, "La fecha de presentación no puede ser anterior a la fecha de envío", "Alerta", JOptionPane.WARNING_MESSAGE);

                        gestion.getDchFechaPresentacionAC().setDate(Date.from(fechaEnvio.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        gestion.getDchFechaEnvioAC().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (gestion.getDchFechaPresentacionAC().getDate() != null && gestion.getDchFechaEnvioAC().getDate() != null) {
                    LocalDate fechaPresentacion = gestion.getDchFechaPresentacionAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = gestion.getDchFechaEnvioAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (fechaEnvio.isAfter(fechaPresentacion)) {

                        JOptionPane.showMessageDialog(null, "La fecha de envío no puede ser posterior a la fecha de presentación", "Alerta", JOptionPane.WARNING_MESSAGE);

                        gestion.getDchFechaEnvioAC().setDate(Date.from(fechaPresentacion.atStartOfDay(ZoneId.systemDefault()).toInstant().minus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        gestion.getDchFechaPresentacionP().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (gestion.getDchFechaEnvioP().getDate() != null && gestion.getDchFechaPresentacionP().getDate() != null) {
                    LocalDate fechaPresentacion = gestion.getDchFechaPresentacionP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = gestion.getDchFechaEnvioP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (fechaPresentacion.isBefore(fechaEnvio)) {

                        JOptionPane.showMessageDialog(null, "La fecha de presentación no puede ser anterior a la fecha de envío", "Alerta", JOptionPane.WARNING_MESSAGE);

                        gestion.getDchFechaPresentacionP().setDate(Date.from(fechaEnvio.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        gestion.getDchFechaEnvioP().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (gestion.getDchFechaPresentacionP().getDate() != null && gestion.getDchFechaEnvioP().getDate() != null) {
                    LocalDate fechaPresentacion = gestion.getDchFechaPresentacionP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = gestion.getDchFechaEnvioP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (fechaEnvio.isAfter(fechaPresentacion)) {

                        JOptionPane.showMessageDialog(null, "La fecha de envío no puede ser posterior a la fecha de presentación", "Alerta", JOptionPane.WARNING_MESSAGE);

                        gestion.getDchFechaEnvioP().setDate(Date.from(fechaPresentacion.atStartOfDay(ZoneId.systemDefault()).toInstant().minus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        gestion.getDchFechaPresentacionA().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (gestion.getDchFechaEnvioA().getDate() != null && gestion.getDchFechaPresentacionA().getDate() != null) {
                    LocalDate fechaPresentacion = gestion.getDchFechaPresentacionA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = gestion.getDchFechaEnvioA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (fechaPresentacion.isBefore(fechaEnvio)) {

                        JOptionPane.showMessageDialog(null, "La fecha de presentación no puede ser anterior a la fecha de envío", "Alerta", JOptionPane.WARNING_MESSAGE);

                        gestion.getDchFechaPresentacionA().setDate(Date.from(fechaEnvio.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });

        gestion.getDchFechaEnvioA().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                if (gestion.getDchFechaPresentacionA().getDate() != null && gestion.getDchFechaEnvioA().getDate() != null) {
                    LocalDate fechaPresentacion = gestion.getDchFechaPresentacionA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate fechaEnvio = gestion.getDchFechaEnvioA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (fechaEnvio.isAfter(fechaPresentacion)) {

                        JOptionPane.showMessageDialog(null, "La fecha de envío no puede ser posterior a la fecha de presentación", "Alerta", JOptionPane.WARNING_MESSAGE);

                        gestion.getDchFechaEnvioA().setDate(Date.from(fechaPresentacion.atStartOfDay(ZoneId.systemDefault()).toInstant().minus(1, ChronoUnit.DAYS)));

                    }
                }

            }

        });
        
        
/*
        gestion.getSpnHorasDocencia().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {

                int acumuladoDocencia = 0;
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                for (UnidadSilaboMD umd : unidades) {
                    if (umd.getNumeroUnidad() != unidadSeleccionada.getNumeroUnidad()) {
                        acumuladoDocencia = acumuladoDocencia + umd.getHorasDocenciaUnidad();
                    }

                }

                if ((acumuladoDocencia + (int) gestion.getSpnHorasDocencia().getValue()) > silabo.getMateria().getHorasDocencia()) {
                    gestion.getSpnHorasDocencia().setValue(silabo.getMateria().getHorasDocencia() - acumuladoDocencia);
                    JOptionPane.showMessageDialog(null, "Esta materia debe cumplir con el número exacto de " + silabo.getMateria().getHorasDocencia() + " horas de gestión docente", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                unidadSeleccionada.setHorasDocenciaUnidad((int) (gestion.getSpnHorasDocencia().getValue()));

            }

        });

        gestion.getSpnHorasPracticas().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {

                int acumuladoPractica = 0;
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                for (UnidadSilaboMD umd : unidades) {
                    if (umd.getNumeroUnidad() != unidadSeleccionada.getNumeroUnidad()) {
                        acumuladoPractica = acumuladoPractica + umd.getHorasPracticaUnidad();
                    }

                }

                if ((acumuladoPractica + (int) gestion.getSpnHorasPracticas().getValue()) > silabo.getMateria().getHorasPracticas()) {
                    gestion.getSpnHorasPracticas().setValue(silabo.getMateria().getHorasPracticas() - acumuladoPractica);
                    JOptionPane.showMessageDialog(null, "Esta materia debe cumplir con el número exacto de " + silabo.getMateria().getHorasPracticas() + " horas de gestión práctica", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                unidadSeleccionada.setHorasPracticaUnidad((int) (gestion.getSpnHorasPracticas().getValue()));

            }

        });

        gestion.getSpnHorasAutonomas().addChangeListener((ChangeEvent ce) -> {
            int acumuladoAutonomo = 0;
            UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
            for (UnidadSilaboMD umd : unidades) {
                if (umd.getNumeroUnidad() != unidadSeleccionada.getNumeroUnidad()) {
                    acumuladoAutonomo = acumuladoAutonomo + umd.getHorasAutonomoUnidad();
                }

            }

            if ((acumuladoAutonomo + (int) gestion.getSpnHorasAutonomas().getValue()) > silabo.getMateria().getHorasAutoEstudio()) {
                gestion.getSpnHorasAutonomas().setValue(silabo.getMateria().getHorasAutoEstudio() - acumuladoAutonomo);
                JOptionPane.showMessageDialog(null, "Esta materia debe cumplir con el número exacto de " + silabo.getMateria().getHorasAutoEstudio() + " horas de gestión autónoma", "Aviso", JOptionPane.WARNING_MESSAGE);
                
            }
            
            unidadSeleccionada.setHorasAutonomoUnidad((int) (gestion.getSpnHorasAutonomas().getValue()));
        });
        
        */

        gestion.getLblAgregarUnidad().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                agregarUnidad();
            }

        });

        gestion.getLblEliminarUnidad().addMouseListener(new MouseAdapter() {
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

        gestion.getBtnAgregarAD().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!gestion.getTxtInstrumentoAD().getText().isEmpty()
                        && !gestion.getTxtIndicadorAD().getText().isEmpty()
                        && gestion.getDchFechaEnvioAD().getDate() != null
                        && gestion.getDchFechaPresentacionAD().getDate() != null) {
                    String[] infoE = {"Gestión de Docencia", "Asistido por el Docente"};
                    agregarEvaluacion(seleccionarTipoActividad(infoE), seleccionarUnidad(), 1);

                } else {
                    JOptionPane.showMessageDialog(null, "Es neceario que especifique todos campos requeridos para la evaluación", "Aviso", JOptionPane.ERROR_MESSAGE);

                }

            }

        });

        gestion.getBtnAgregarAC().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!gestion.getTxtInstrumentoAC().getText().isEmpty()
                        && !gestion.getTxtIndicadorAC().getText().isEmpty()
                        && gestion.getDchFechaEnvioAC().getDate() != null
                        && gestion.getDchFechaPresentacionAC().getDate() != null) {
                    String[] infoE = {"Gestión de Docencia", "Aprendizaje Colaborativo"};
                    agregarEvaluacion(seleccionarTipoActividad(infoE), seleccionarUnidad(), 2);

                } else {
                    JOptionPane.showMessageDialog(null, "Es neceario que especifique todos campos requeridos para la evaluación", "Aviso", JOptionPane.ERROR_MESSAGE);

                }

            }

        });

        gestion.getBtnAgregarP().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!gestion.getTxtInstrumentoP().getText().isEmpty()
                        && !gestion.getTxtIndicadorP().getText().isEmpty()
                        && gestion.getDchFechaEnvioP().getDate() != null
                        && gestion.getDchFechaPresentacionP().getDate() != null) {
                    String[] infoE = {"Gestión de la Práctica", "Aprendizaje Colaborativo"};
                    agregarEvaluacion(seleccionarTipoActividad(infoE), seleccionarUnidad(), 3);

                } else {
                    JOptionPane.showMessageDialog(null, "Es neceario que especifique todos campos requeridos para la evaluación", "Aviso", JOptionPane.ERROR_MESSAGE);

                }

            }

        });

        gestion.getBtnAgregarA().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!gestion.getTxtInstrumentoA().getText().isEmpty()
                        && !gestion.getTxtIndicadorA().getText().isEmpty()
                        && gestion.getDchFechaEnvioA().getDate() != null
                        && gestion.getDchFechaPresentacionA().getDate() != null) {
                    String[] infoE = {"Gestión de Trabajo Autónomo", "Aprendizaje Colaborativo"};
                    agregarEvaluacion(seleccionarTipoActividad(infoE), seleccionarUnidad(), 4);

                } else {
                    JOptionPane.showMessageDialog(null, "Es neceario que especifique todos campos requeridos para la evaluación", "Aviso", JOptionPane.ERROR_MESSAGE);

                }

            }

        });

        gestion.getTblAsistidaDocente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                gestion.getBtnQuitarAD().setEnabled(true);

            }

        });

        gestion.getTblAprendizajeColaborativo().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                gestion.getBtnQuitarAC().setEnabled(true);

            }

        });

        gestion.getTblPractica().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                gestion.getBtnQuitarP().setEnabled(true);

            }

        });

        gestion.getTblAutonoma().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                gestion.getBtnQuitarA().setEnabled(true);

            }

        });

        gestion.getBtnQuitarAD().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                quitarEvaluacionAD((DefaultTableModel) gestion.getTblAsistidaDocente().getModel(), 1);
                gestion.getBtnQuitarAD().setEnabled(false);
            }

        });

        gestion.getBtnQuitarAC().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                quitarEvaluacionAC((DefaultTableModel) gestion.getTblAprendizajeColaborativo().getModel(), 2);
                gestion.getBtnQuitarAC().setEnabled(false);
            }

        });

        gestion.getBtnQuitarP().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                quitarEvaluacionP((DefaultTableModel) gestion.getTblPractica().getModel(), 3);
                gestion.getBtnQuitarP().setEnabled(false);
            }

        });

        gestion.getBtnQuitarA().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                quitarEvaluacionA((DefaultTableModel) gestion.getTblAutonoma().getModel(), 4);
                gestion.getBtnQuitarA().setEnabled(false);
            }

        });

        gestion.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int reply = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar el proceso?", "Cancelar", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    gestion.dispose();
                    vtnPrincipal.getMnCtSilabos().doClick();
                }
            }

        });

        //gestion.getBtnGuardar().addActionListener(e -> ejecutar(e));
        gestion.getBtnSiguiente().addActionListener(e -> ejecutar3(e, silabo));

        cargarEstrategias(unidades.get(0));
        gestion.getCmbUnidad().setSelectedIndex(0);
    }
    private boolean accion = true;
    private boolean accion2 = true;
    private boolean accion3 = true;

    private void ejecutar(ActionEvent e) {

        if (accion) {
            new Thread(() -> {
                accion = false;
                boolean aux = false;
                gestion.getBtnGuardar().setEnabled(false);
                gestion.getBtnCancelar().setEnabled(false);

                vtnPrincipal.getLblEstado().setText("Guardando cambios en el silabo... Espere por favor");

                if (silaboNuevo.getID() == null) {
                    //guardarArchivos();
                    aux = guardarSilabo();

                } else {
                    //guardarArchivos();
                    silaboNuevo.eliminar();
                    aux = guardarSilabo();

                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ControladorSilaboC.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (aux) {
                    JOptionPane.showMessageDialog(null, "Cambios guardados exitosamente");
                    cambioSilabo = false;
                }

                vtnPrincipal.getLblEstado().setText("");

                gestion.getBtnCancelar().setEnabled(true);

                accion = true;

            }).start();
        }

    }

    private void ejecutar2(ActionEvent e) {

        if (accion2) {
            new Thread(() -> {
                accion2 = false;
                boolean aux = false;
                if (validarBiblioBase()) {
                    bibliografia.getBtnFinalizar().setEnabled(false);
                    bibliografia.getBtnCancelar().setEnabled(false);
                    vtnPrincipal.getLblEstado().setText("Guardando silabo... Espere por favor");
                    if (silaboNuevo.getID() == null) {
                        //guardarArchivos();
                        aux = guardarSilabo();
                    } else {
                        //guardarArchivos();
                        silaboNuevo.eliminar();
                        aux = guardarSilabo();

                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ControladorSilaboC.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (aux) {
                        JOptionPane.showMessageDialog(null, "Silabo guardado exitosamente");
                        configuracion.dispose();
                        gestion.dispose();
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

    private void ejecutar3(ActionEvent e, SilaboBD silabo) {
        if (accion3) {
            new Thread(() -> {
                accion3 = false;
                if (validarCampos()) {
                    boolean aux = false;
                    if (cambioSilabo) {
                        gestion.getBtnGuardar().setEnabled(false);
                        gestion.getBtnSiguiente().setEnabled(false);
                        gestion.getBtnCancelar().setEnabled(false);
                        vtnPrincipal.getLblEstado().setText("Guardando cambios en el silabo... Espere por favor");

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ControladorSilaboC.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (silaboNuevo.getID() == null) {
                            //guardarArchivos();
                            aux = guardarSilabo();
                        } else {
                            //guardarArchivos();
                            silaboNuevo.eliminar();
                            aux = guardarSilabo();
                        }

                        if (aux) {
                            JOptionPane.showMessageDialog(null, "Cambios guardados exitosamente");
                            cambioSilabo = false;
                        }
                    }

                    vtnPrincipal.getLblEstado().setText("");

                    if (!retroceso) {
                        gestion.setVisible(false);
                        citarReferencias(silabo, bibliografia);
                        retroceso = true;
                    } else {
                        gestion.setVisible(false);
                        bibliografia.setVisible(true);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No ha completado correctamente los campos necesarios", "Aviso", JOptionPane.ERROR_MESSAGE);
                }
                gestion.getBtnSiguiente().setEnabled(true);
                gestion.getBtnCancelar().setEnabled(true);

                accion3 = true;
            }).start();
        }

    }

    public void citarReferencias(SilaboBD silabo, frmReferencias bibliografia) {
        System.out.println("------->entro");
        vtnPrincipal.getDpnlPrincipal().add(bibliografia);
        cargarBiblioteca();
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

        bibliografia.getTblBiblioteca().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                bibliografia.getBtnAgregarBibliografiaBase().setEnabled(true);
            }
        });

        bibliografia.getCmbBiblioteca().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cargarBiblioteca();
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
                } else {
                    bibliografia.getBtnQuitarBibliografiaBase().setEnabled(false);
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
                    gestion.dispose();
                    vtnPrincipal.getMnCtSilabos().doClick();
                }
            }
        });

        bibliografia.getBtnAtras().addActionListener(b -> bibliografia.setVisible(false));
        bibliografia.getBtnAtras().addActionListener(b -> gestion.setVisible(true));
        cargarBiblioteca();
    }

    /**
     * AL seleccionar una unidad
     *
     * @return
     */
    public UnidadSilaboMD seleccionarUnidad() {
        Optional<UnidadSilaboMD> unidadSeleccionada = unidades.stream().
                filter(u -> u.getNumeroUnidad() == (gestion.getCmbUnidad().getSelectedIndex() + 1)).
                findFirst();
        return unidadSeleccionada.get();
    }

    /**
     * Actualizamos el numero de unidad
     *
     * @param unidadSeleccionada
     */
    public void actualizarUnidad(UnidadSilaboMD unidadSeleccionada) {
        unidadSeleccionada.setBandera(true);
        unidades.set(unidadSeleccionada.getNumeroUnidad() - 1, unidadSeleccionada);
        gestion.getBtnGuardar().setEnabled(true);
    }

    /**
     * Mostramos la unidad
     */
    private void mostrarUnidad() {
        UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

        gestion.getTxtTitulo().setText(unidadSeleccionada.getTituloUnidad());
        gestion.getTxrContenidos().setText(unidadSeleccionada.getContenidosUnidad());
        gestion.getTxrObjetivos().setText(unidadSeleccionada.getObjetivoEspecificoUnidad());
        gestion.getTxrResultados().setText(unidadSeleccionada.getResultadosAprendizajeUnidad());

        if (unidadSeleccionada.getFechaInicioUnidad() != null) {
            gestion.getDchFechaInicio().setDate(Date.from(unidadSeleccionada.getFechaInicioUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        } else {
            gestion.getDchFechaInicio().setDate(null);

        }

        if (unidadSeleccionada.getFechaFinUnidad() != null) {
            gestion.getDchFechaFin().setDate(Date.from(unidadSeleccionada.getFechaFinUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        } else {
            gestion.getDchFechaFin().setDate(null);
        }

        gestion.getSpnHorasDocencia().setValue(unidadSeleccionada.getHorasDocenciaUnidad());
        gestion.getSpnHorasPracticas().setValue(unidadSeleccionada.getHorasPracticaUnidad());
        gestion.getSpnHorasAutonomas().setValue(unidadSeleccionada.getHorasAutonomoUnidad());

        cargarEstrategias(unidadSeleccionada);

        cargarEvaluaciones((DefaultTableModel) gestion.getTblAsistidaDocente().getModel(), 1);

        cargarEvaluaciones((DefaultTableModel) gestion.getTblAprendizajeColaborativo().getModel(), 2);

        cargarEvaluaciones((DefaultTableModel) gestion.getTblPractica().getModel(), 3);

        cargarEvaluaciones((DefaultTableModel) gestion.getTblAutonoma().getModel(), 4);
    }

    /**
     * Cargamos estrategias de la unidad esto se debe revisar a fondo
     *
     * @param unidadSeleccionada
     */
    public void cargarEstrategias(UnidadSilaboMD unidadSeleccionada) {

        DefaultListModel modeloEstrategias = new DefaultListModel();

        gestion.getLstEstrategiasPredeterminadas().setCellRenderer(new CheckListRenderer());
        gestion.getLstEstrategiasPredeterminadas().setModel(modeloEstrategias);

        EstrategiasAprendizajeBD.consultar(conexion).forEach((emd) -> {
            modeloEstrategias.addElement(new CheckListItem(emd.getDescripcionEstrategia()));
        });

        for (int i = 0; i < gestion.getLstEstrategiasPredeterminadas().getModel().getSize(); i++) {
            CheckListItem item = (CheckListItem) gestion.getLstEstrategiasPredeterminadas().getModel().getElementAt(i);

            for (EstrategiasUnidadMD emd : estrategias) {

                if (emd.getUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad()
                        && modeloEstrategias.get(i).toString().equals(emd.getEstrategia().getDescripcionEstrategia())) {

                    item.setSelected(true);

                }
            }
        }
    }

    /**
     * Creamos una unidad nueva
     */
    public void agregarUnidad() {
        UnidadSilaboMD nuevaUnidad = new UnidadSilaboMD();
        nuevaUnidad.setNumeroUnidad(unidades.size() + 1);
        unidades.add(nuevaUnidad);
        gestion.getCmbUnidad().addItem("Unidad " + unidades.size());
        JOptionPane.showMessageDialog(null, "Nueva unidad agregada");
        gestion.getCmbUnidad().setSelectedIndex(unidades.size() - 1);
    }

    /**
     * Eliminamos una unidad ... eliminamos todo lo correspondiente a la misma.
     */
    public void eliminarUnidad() {
        UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

        unidades.removeIf(u -> u.getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad());

        estrategias.removeIf(e -> e.getUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad());

        evaluaciones.removeIf(e -> e.getIdUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad());

        for (EstrategiasUnidadMD emd : estrategias) {
            if (emd.getUnidad().getNumeroUnidad() > unidadSeleccionada.getNumeroUnidad()) {
                emd.getUnidad().setNumeroUnidad(emd.getUnidad().getNumeroUnidad() - 1);
            }
        }

        for (EvaluacionSilaboMD emd : evaluaciones) {
            if (emd.getIdUnidad().getNumeroUnidad() > unidadSeleccionada.getNumeroUnidad()) {
                emd.getIdUnidad().setNumeroUnidad(emd.getIdUnidad().getNumeroUnidad() - 1);
            }
        }

        for (UnidadSilaboMD umd : unidades) {
            if (umd.getNumeroUnidad() > unidadSeleccionada.getNumeroUnidad()) {
                //gestion.getCmbUnidad().removeItemAt(umd.getNumeroUnidad());
                umd.setNumeroUnidad(umd.getNumeroUnidad() - 1);

            }
        }

        List<String> comboNuevo = new ArrayList<>();

        for (int i = 1; i <= unidades.size(); i++) {
            comboNuevo.add("Unidad " + (i));
        }

        gestion.getCmbUnidad().setModel(new DefaultComboBoxModel(comboNuevo.toArray()));

        if (unidadSeleccionada.getNumeroUnidad() == 1) {
            gestion.getCmbUnidad().setSelectedIndex(0);

        } else if (unidadSeleccionada.getNumeroUnidad() == unidades.size() + 1) {

            gestion.getCmbUnidad().setSelectedIndex(unidadSeleccionada.getNumeroUnidad() - 2);
        } else {
            gestion.getCmbUnidad().setSelectedIndex(unidadSeleccionada.getNumeroUnidad() - 1);
        }

        mostrarTotalGestion();

    }

    /**
     * Agregamos una evaluacion dependiendo cual sea
     *
     * @param tipo
     * @param unidad
     * @param p
     */
    public void agregarEvaluacion(TipoActividadMD tipo, UnidadSilaboMD unidad, int p) {
        switch (p) {
            case 1:

                if (validarLimiteEvaluaciones((double) (gestion.getSpnValoracionAD().getValue()))) {
                    ++idEvaluacionSig;
                    idEvaluacion = idEvaluacionSig;
                    evaluaciones.add(new EvaluacionSilaboMD(idEvaluacion, gestion.getTxtIndicadorAD().getText(), gestion.getTxtInstrumentoAD().getText(), (double) (gestion.getSpnValoracionAD().getValue()), gestion.getDchFechaEnvioAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gestion.getDchFechaPresentacionAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), tipo, unidad));
                    cargarEvaluaciones((DefaultTableModel) gestion.getTblAsistidaDocente().getModel(), 1);
                    limpiarEvaluacionesAD();
                    cambioSilabo = true;
                    gestion.getBtnGuardar().setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder los 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);
                }

                break;
            case 2:

                if (validarLimiteEvaluaciones((double) (gestion.getSpnValoracionAC().getValue()))) {
                    ++idEvaluacionSig;
                    idEvaluacion = idEvaluacionSig;
                    evaluaciones.add(new EvaluacionSilaboMD(idEvaluacion, gestion.getTxtIndicadorAC().getText(), gestion.getTxtInstrumentoAC().getText(), (double) (gestion.getSpnValoracionAC().getValue()), gestion.getDchFechaEnvioAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gestion.getDchFechaPresentacionAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), tipo, unidad));
                    cargarEvaluaciones((DefaultTableModel) gestion.getTblAprendizajeColaborativo().getModel(), 2);
                    limpiarEvaluacionesAC();
                    cambioSilabo = true;
                    gestion.getBtnGuardar().setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder los 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                break;
            case 3:
                if (validarLimiteEvaluaciones((double) (gestion.getSpnValoracionP().getValue()))) {
                    ++idEvaluacionSig;
                    idEvaluacion = idEvaluacionSig;
                    evaluaciones.add(new EvaluacionSilaboMD(idEvaluacion, gestion.getTxtIndicadorP().getText(), gestion.getTxtInstrumentoP().getText(), (double) (gestion.getSpnValoracionP().getValue()), gestion.getDchFechaEnvioP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gestion.getDchFechaPresentacionP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), tipo, unidad));
                    cargarEvaluaciones((DefaultTableModel) gestion.getTblPractica().getModel(), 3);
                    limpiarEvaluacionesP();
                    gestion.getBtnGuardar().setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder los 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                break;
            case 4:
                if (validarLimiteEvaluaciones((double) (gestion.getSpnValoracionA().getValue()))) {
                    ++idEvaluacionSig;
                    idEvaluacion = idEvaluacionSig;
                    evaluaciones.add(new EvaluacionSilaboMD(idEvaluacion, gestion.getTxtIndicadorA().getText(), gestion.getTxtInstrumentoA().getText(), (double) (gestion.getSpnValoracionA().getValue()), gestion.getDchFechaEnvioA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gestion.getDchFechaPresentacionA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), tipo, unidad));
                    cargarEvaluaciones((DefaultTableModel) gestion.getTblAutonoma().getModel(), 4);
                    limpiarEvaluacionesA();
                    cambioSilabo = true;
                    gestion.getBtnGuardar().setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder el valor de 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }
                break;
        }
        mostrarTotalGestion();
    }

    /**
     * Validar limite de evaluacion
     *
     * @param valor
     * @return
     */
    public boolean validarLimiteEvaluaciones(double valor) {
        double total = 0;
        total = evaluaciones.stream().map((emd) -> emd.getValoracion()).reduce(total, (accumulator, _item) -> accumulator + _item);
        return (total + valor) <= 60.0;
    }

    /**
     * Mostramos el total de la gestion
     */
    public void mostrarTotalGestion() {
        double total = 0;
        total = evaluaciones.stream().map((emd) -> emd.getValoracion()).reduce(total, (accumulator, _item) -> accumulator + _item);
        gestion.getLblAcumuladoGestion().setText(total + "/60");
    }

    /**
     * Seleccionamos el tipo de actividad
     *
     * @param infoE
     * @return
     */
    public TipoActividadMD seleccionarTipoActividad(String[] infoE) {
        Optional<TipoActividadMD> tipoSeleccionado = tiposActividad.stream().
                filter(a -> a.getNombreTipoActividad().equals(infoE[0]) && a.getNombreSubtipoActividad().equals(infoE[1])).
                findFirst();
        return tipoSeleccionado.get();
    }

    /**
     * Cargamos las evaluaciones
     *
     * @param modeloTabla
     * @param p
     */
    public void cargarEvaluaciones(DefaultTableModel modeloTabla, int p) {
        UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
        switch (p) {
            case 1:

                for (int j = gestion.getTblAsistidaDocente().getRowCount() - 1; j >= 0; j--) {

                    modeloTabla.removeRow(j);
                }
                break;
            case 2:
                for (int j = gestion.getTblAprendizajeColaborativo().getRowCount() - 1; j >= 0; j--) {

                    modeloTabla.removeRow(j);
                }

                break;
            case 3:
                for (int j = gestion.getTblPractica().getRowCount() - 1; j >= 0; j--) {

                    modeloTabla.removeRow(j);
                }

                break;
            case 4:
                for (int j = gestion.getTblAutonoma().getRowCount() - 1; j >= 0; j--) {

                    modeloTabla.removeRow(j);
                }

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

    /**
     * Limpiamos evaluaciones asisitdas por docente
     */
    public void limpiarEvaluacionesAD() {
        gestion.getTxtInstrumentoAD().setText(null);
        gestion.getTxtIndicadorAD().setText(null);
        gestion.getSpnValoracionAD().setValue(1.0d);
        gestion.getDchFechaEnvioAD().setDate(null);
        gestion.getDchFechaPresentacionAD().setDate(null);
    }

    /**
     * Limpiar evaluaciones aprendizaje colaborativo
     */
    public void limpiarEvaluacionesAC() {
        gestion.getTxtInstrumentoAC().setText(null);
        gestion.getTxtIndicadorAC().setText(null);
        gestion.getSpnValoracionAC().setValue(1.0d);

        gestion.getDchFechaPresentacionAC().setDate(null);
        gestion.getDchFechaEnvioAC().setDate(null);
    }

    /**
     * Limpiar evaluaciones practicas
     */
    public void limpiarEvaluacionesP() {
        gestion.getTxtInstrumentoP().setText(null);
        gestion.getTxtIndicadorP().setText(null);
        gestion.getSpnValoracionP().setValue(1.0d);
        gestion.getDchFechaEnvioP().setDate(null);
        gestion.getDchFechaPresentacionP().setDate(null);
    }

    /**
     * Quitamos evaluaiones autonomas
     */
    public void limpiarEvaluacionesA() {
        gestion.getTxtInstrumentoA().setText(null);
        gestion.getTxtIndicadorA().setText(null);
        gestion.getSpnValoracionA().setValue(1.0d);
        gestion.getDchFechaEnvioA().setDate(null);
        gestion.getDchFechaPresentacionA().setDate(null);
    }

    /**
     * Cargamos biblioteca
     */
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

    /**
     * Agregamos bibliografias base
     */
    public void agregarBibliografiaBase() {

        List<String> b = new ArrayList<>();

        ReferenciasMD referenciaSeleccionada = seleccionarReferencia();

        boolean nuevo = true;
        boolean nuevo2 = true;

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
            ReferenciaSilaboMD rsm = new ReferenciaSilaboMD(referenciaSeleccionada, silaboNuevo);
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

    /**
     * Agregamos bibliografia no base
     */
    public void agregarBibliografiaNoBase() {
        referenciasSilabo.removeIf(r -> r.getIdReferencia().getTipoReferencia().equals("Complementaria") || r.getIdReferencia().getTipoReferencia().equals("Linkografia"));
        ReferenciasMD complementaria = new ReferenciasMD(String.valueOf(silaboNuevo.getID()), bibliografia.getTxrBibliografiaComplementaria().getText(), "Complementaria");
        ReferenciasMD linkografia = new ReferenciasMD(String.valueOf(silaboNuevo.getID()), bibliografia.getTxrLinkografia().getText(), "Linkografia");
        referenciasSilabo.add(new ReferenciaSilaboMD(complementaria, silaboNuevo));
        referenciasSilabo.add(new ReferenciaSilaboMD(linkografia, silaboNuevo));

    }

    /**
     * Quitamos biblio base
     */
    public void quitarBibliografiaBase() {
        String seleccion = bibliografia.getLstBibliografiaBase().getModel().getElementAt(bibliografia.getLstBibliografiaBase().getSelectedIndex()).substring(2);
        referenciasSilabo.removeIf(p -> p.getIdReferencia().getDescripcionReferencia().equals(seleccion));

        tablaM.remove(bibliografia.getLstBibliografiaBase().getSelectedIndex());
    }

    /**
     * Referencias
     *
     * @return
     */
    public ReferenciasMD seleccionarReferencia() {
        int seleccion = bibliografia.getTblBiblioteca().getSelectedRow();
        Optional<ReferenciasMD> referenciaSeleccionada = biblioteca.stream().
                filter(r -> r.getCodigoReferencia().equals(bibliografia.getTblBiblioteca().getValueAt(seleccion, 0))).
                findFirst();

        return referenciaSeleccionada.get();
    }

    /**
     * Quitar evaluacion asistida por docente
     *
     * @param modeloTabla
     * @param p
     */
    public void quitarEvaluacionAD(DefaultTableModel modeloTabla, int p) {
        evaluaciones.removeIf(e -> e.getIdEvaluacion() == gestion.getTblAsistidaDocente().getValueAt(gestion.getTblAsistidaDocente().getSelectedRow(), 5));
        cargarEvaluaciones(modeloTabla, p);
        mostrarTotalGestion();
        cambioSilabo = true;
        gestion.getBtnGuardar().setEnabled(true);
    }

    /**
     * Quitar evaliacion de aprendizaje colaborativo
     *
     * @param modeloTabla
     * @param p
     */
    public void quitarEvaluacionAC(DefaultTableModel modeloTabla, int p) {
        evaluaciones.removeIf(e -> e.getIdEvaluacion() == gestion.getTblAprendizajeColaborativo().getValueAt(gestion.getTblAprendizajeColaborativo().getSelectedRow(), 5));
        cargarEvaluaciones(modeloTabla, p);
        mostrarTotalGestion();
        cambioSilabo = true;
        gestion.getBtnGuardar().setEnabled(true);
    }

    /**
     * Remover evaluaciones practicas
     *
     * @param modeloTabla
     * @param p
     */
    public void quitarEvaluacionP(DefaultTableModel modeloTabla, int p) {
        evaluaciones.removeIf(e -> e.getIdEvaluacion() == gestion.getTblPractica().getValueAt(gestion.getTblPractica().getSelectedRow(), 5));
        cargarEvaluaciones(modeloTabla, p);
        mostrarTotalGestion();
        cambioSilabo = true;
        gestion.getBtnGuardar().setEnabled(true);
    }

    /**
     * Remover las evaluaciones autonoma
     *
     * @param modeloTabla
     * @param p
     */
    public void quitarEvaluacionA(DefaultTableModel modeloTabla, int p) {
        evaluaciones.removeIf(e -> e.getIdEvaluacion() == gestion.getTblAutonoma().getValueAt(gestion.getTblAutonoma().getSelectedRow(), 5));
        cargarEvaluaciones(modeloTabla, p);
        mostrarTotalGestion();
        cambioSilabo = true;
        gestion.getBtnGuardar().setEnabled(true);
    }

    public int insertarUnidades() {
        silaboNuevo.setID(SilaboBD.consultarUltimo(conexion, silaboNuevo.getMateria().getId(), silaboNuevo.getPeriodo().getID()).getID());

        for (UnidadSilaboMD umd : unidades) {
            umd.getIdSilabo().setID(silaboNuevo.getID());
            UnidadSilaboBD ubd = new UnidadSilaboBD(conexion);
            ubd.insertar(umd, umd.getIdSilabo().getID());

            umd.setIdUnidad(UnidadSilaboBD.consultarUltima(conexion, umd.getIdSilabo().getID(), umd.getNumeroUnidad()).getIdUnidad());
            for (EstrategiasUnidadMD emd : estrategias) {
                if (emd.getUnidad().getNumeroUnidad() == umd.getNumeroUnidad()) {
                    EstrategiasUnidadBD ebd = new EstrategiasUnidadBD(conexion);
                    ebd.insertar(emd, umd.getIdUnidad());
                }
            }

            for (EvaluacionSilaboMD evd : evaluaciones) {
                if (evd.getIdUnidad().getNumeroUnidad() == umd.getNumeroUnidad()) {
                    EvaluacionSilaboBD esd = new EvaluacionSilaboBD(conexion);
                    esd.insertar(evd, umd.getIdUnidad());
                }
            }
        }

        return silaboNuevo.getID();
    }

    /**
     * Guardamos las referencias
     *
     * @param is
     */
    public void insertarReferencias(int is) {

        agregarBibliografiaNoBase();

        for (int i = 0; i < referenciasSilabo.size() - 2; i++) {
            ReferenciaSilaboBD rbd = new ReferenciaSilaboBD(conexion);
            if (!rbd.getIdReferencia().isExisteEnBiblioteca()) {
                ReferenciasBD r = new ReferenciasBD(conexion);
                r.insertar(rbd.getIdReferencia(), 1);
            }
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
            silaboNuevo.insertar();

            int is = insertarUnidades();
            insertarReferencias(is);

            unidades = UnidadSilaboBD.consultar(conexion, silaboNuevo.getID(), 1);

            for (UnidadSilaboMD umd : unidades) {
                umd.setBandera(false);
            }

            biblioteca = new ArrayList<>();

            tiposActividad = TipoActividadBD.consultar(conexion);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se ha perdido conexion, por favor intente guardar otra vez ", "Error", JOptionPane.ERROR_MESSAGE);
            gestion.getBtnGuardar().setEnabled(true);
            gestion.getBtnSiguiente().setEnabled(true);
            bibliografia.getBtnFinalizar().setEnabled(true);
            cambioSilabo = false;
            return false;
        }
    }

    /**
     * Valiadamos que los campos esten llenos
     *
     * @return
     */
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
                if (estrategias.get(j).getUnidad().getNumeroUnidad() == (unidades.get(i).getNumeroUnidad())) {
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

            if (!validarHoras()) {
                control = false;
            }
        }
        return control;
    }

    /**
     * Cargamos als referencias
     *
     * @param referenciasSilabo
     */
    public void cargarReferencias(List<ReferenciaSilaboMD> referenciasSilabo) {
        List<String> b = new ArrayList<>();

        for (int i = 0; i < referenciasSilabo.size(); i++) {
            switch (referenciasSilabo.get(i).getIdReferencia().getTipoReferencia()) {
                case "Base":
                    b.add("• " + referenciasSilabo.get(i).getIdReferencia().getDescripcionReferencia());
                    break;
                case "Complementaria":
                    bibliografia.getTxrBibliografiaComplementaria().setText(referenciasSilabo.get(i).getIdReferencia().getDescripcionReferencia());
                    break;
                case "Linkografia":
                    bibliografia.getTxrLinkografia().setText(referenciasSilabo.get(i).getIdReferencia().getDescripcionReferencia());
                    break;
                default:
                    break;
            }
        }

        tablaM = new DefaultListModel<>();
        b.forEach((s) -> {
            tablaM.addElement(s);
        });

        bibliografia.getLstBibliografiaBase().setModel(tablaM);

    }

    /**
     * Validamos las horas
     *
     * @return boolean
     */
    public boolean validarHoras() {
        int totalDocencia = 0;
        int totalPractica = 0;
        int totalAutonomo = 0;
        boolean control = false;

        for (UnidadSilaboMD umd : unidades) {
            /*totalDocencia = totalDocencia + umd.getHorasDocenciaUnidad();
            totalPractica = totalPractica + umd.getHorasPracticaUnidad();
            totalAutonomo = totalAutonomo + umd.getHorasAutonomoUnidad();*/
        }
        if (totalDocencia == silaboNuevo.getMateria().getHorasDocencia()
                && totalPractica == silaboNuevo.getMateria().getHorasPracticas()
                && totalAutonomo == silaboNuevo.getMateria().getHorasAutoEstudio()) {

            control = true;
        }
        return control;
    }

    /**
     * Debe tener biblio base
     *
     * @return
     */
    public boolean validarBiblioBase() {
        boolean control = false;
        for (ReferenciaSilaboMD rsd : referenciasSilabo) {
            if (rsd.getIdReferencia().getTipoReferencia().equals("Base")) {
                control = true;
            }
        }
        return control;
    }

}
