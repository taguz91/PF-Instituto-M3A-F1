/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import com.placeholder.PlaceHolder;
import static controlador.silabo.ControladorSilaboCRUD.x;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
import modelo.silabo.CarrerasBDS;
import modelo.silabo.MateriasBDS;
import modelo.silabo.PeriodoLectivoBDS;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;

import modelo.tipoActividad.TipoActividadBD;
import modelo.tipoActividad.TipoActividadMD;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmConfiguracionSilabo;
import vista.silabos.frmGestionSilabo;
import vista.silabos.frmGestionSilabo.CheckListItem;
import vista.silabos.frmGestionSilabo.CheckListRenderer;
import vista.silabos.frmReferencias;
import vista.silabos.frmSilabos;

import net.sf.jasperreports.engine.JasperExportManager;
import java.util.HashMap;
import java.util.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Andres Ullauri
 */
public class ControladorSilaboC {

    private SilaboBD silaboNuevo;

    private SilaboMD silaboAnterior;

    private VtnPrincipal principal;

    private frmConfiguracionSilabo configuracion;

    private frmGestionSilabo gestion;

    private frmReferencias bibliografia;

    private UsuarioBD usuario;

    private ConexionBD conexion;

    private List<CarreraMD> carrerasDocente;

    private List<SilaboMD> silabosAnteriores;

    private List<PeriodoLectivoMD> periodosCarrera;

    private List<MateriaMD> materiasDocente;

    private List<UnidadSilaboMD> unidadesSilabo;

    private List<EstrategiasUnidadMD> estrategiasSilabo;

    private List<EstrategiasAprendizajeMD> estrategiasAprendizaje;

    private List<EvaluacionSilaboMD> evaluacionesSilabo;

    private List<TipoActividadMD> tiposActividad;

    private List<ReferenciasMD> biblioteca;

    private List<ReferenciaSilaboMD> referenciasSilabo;
    

    private DefaultListModel modeloBase;

    private static Integer idEvaluacionSig = 0;
    private Integer idEvaluacion;
    private boolean retroceso = false;

    public ControladorSilaboC(VtnPrincipal principal, UsuarioBD usuario, ConexionBD conexion) {
        this.principal = principal;
        this.usuario = usuario;
        this.conexion = conexion;

    }

    public void iniciarControlador() {

        if (conexion.getCon() == null) {
            conexion.conectar();
        }

        configuracion = new frmConfiguracionSilabo();

        principal.getDpnlPrincipal().add(configuracion);

        configuracion.setTitle("Silabo");

        configuracion.show();

        configuracion.setLocation((principal.getDpnlPrincipal().getSize().width - configuracion.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - configuracion.getSize().height) / 2);

        cargarComboCarreras();

        configuracion.getCmbCarrera().addActionListener((ActionEvent ae) -> {

            Optional<CarreraMD> carreraSeleccionada = carrerasDocente.stream().
                    filter(c -> c.getNombre().equals(configuracion.getCmbCarrera().getSelectedItem().toString())).
                    findFirst();

            periodosCarrera = PeriodoLectivoBDS.consultar(conexion, carreraSeleccionada.get().getId());
            //configuracion.getCmbPeriodo().removeAllItems();
            configuracion.getCmbAsignatura().removeAllItems();
            System.out.println("-------------------" + periodosCarrera.get(0).getNombre_PerLectivo());
            cargarComboMaterias(carreraSeleccionada.get().getId(), periodosCarrera.get(0).getId_PerioLectivo());
            //cargarComboPeriodos(carreraSeleccionada.get().getId());

        });

        configuracion.getBtnSiguiente().addActionListener((ActionEvent ae) -> {

            Optional<MateriaMD> materiaSeleccionada = materiasDocente.stream().
                    filter(m -> m.getNombre().equals(configuracion.getCmbAsignatura().getSelectedItem().toString())).
                    findFirst();

            if (configuracion.getCmbPeriodo().getItemCount() > 0) {

                System.out.println("-----------------------------entro");
                for (SilaboMD s : silabosAnteriores) {
                    System.out.println(materiaSeleccionada.get().getId() + " - " + s.getIdMateria() + " - " + s.getIdPeriodoLectivo().getNombre_PerLectivo() + " - " + configuracion.getCmbPeriodo().getSelectedItem().toString());
                }
                Optional<SilaboMD> silaboSeleccionado = silabosAnteriores.stream().
                        filter(s -> s.getIdMateria().getId() == (materiaSeleccionada.get().getId()) && s.getIdPeriodoLectivo().getNombre_PerLectivo().equals(configuracion.getCmbPeriodo().getSelectedItem().toString())).
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

        configuracion.getCmbPeriodo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

//                configuracion.getCmbPeriodo().removeAllItems();
//                cargarComboPeriodos(carreraSeleccionada.get().getId());
//                if (periodosCarrera == null) {
//                    System.out.println("periodos nulos");
//                } else {
//                    System.out.println("periodos no nulos");
//                    for (PeriodoLectivoMD p : periodosCarrera) {
//                        System.out.println(p.getNombre_PerLectivo());
//                    }
//                }
//                if (configuracion.getCmbPeriodo().getItemCount() > 0) {
//                    Optional<CarreraMD> carreraSeleccionada = carrerasDocente.stream().
//                            filter(c -> c.getNombre().equals(configuracion.getCmbCarrera().getSelectedItem().toString())).
//                            findFirst();
//                    Optional<PeriodoLectivoMD> periodoSeleccionado = periodosCarrera.stream().
//                            filter(p -> (p.getNombre_PerLectivo()).equals(configuracion.getCmbPeriodo().getSelectedItem().toString())).
//                            findFirst();
//
//                    configuracion.getCmbAsignatura().removeAllItems();
//                    cargarComboMaterias(carreraSeleccionada.get().getId(), periodoSeleccionado.get().getId_PerioLectivo());
//
//                }
//
            }
        });

        configuracion.getCmbCarrera().setSelectedIndex(0);
    }

    public void cargarComboCarreras() {

        carrerasDocente = CarrerasBDS.consultar(conexion, usuario.getUsername());

        carrerasDocente.forEach((cmd) -> {
            configuracion.getCmbCarrera().addItem(cmd.getNombre());
        });

    }

    public void cargarComboMaterias(int idCarrera, int idPeriodo) {

        String[] parametros = {usuario.getUsername(), String.valueOf(idCarrera), String.valueOf(idPeriodo)};

        materiasDocente = MateriasBDS.consultar(conexion, parametros);

        materiasDocente.forEach((cmd) -> {
            configuracion.getCmbAsignatura().addItem(cmd.getNombre());
        });

        if (configuracion.getCmbAsignatura().getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "Ya ha ingresado todos los silabos correspondientes para esta carrera en el período en curso ", "Aviso", JOptionPane.WARNING_MESSAGE);
            configuracion.getBtnSiguiente().setEnabled(false);
        } else {
            configuracion.getBtnSiguiente().setEnabled(true);
        }

    }

    public void cargarComboPeriodos(int idMateria) {

        Integer[] parametros = {idMateria, usuario.getPersona().getIdPersona()};
        silabosAnteriores = SilaboBD.consultarAnteriores(conexion, parametros);

        silabosAnteriores.forEach((prd) -> {
            configuracion.getCmbPeriodo().addItem(prd.getIdPeriodoLectivo().getNombre_PerLectivo());
        });

    }

    public void iniciarSilabo(SilaboBD silabo, SilaboMD silaboAnterior, int numUnidades) {

        tiposActividad = TipoActividadBD.consultar(conexion);

        gestion = new frmGestionSilabo();

        bibliografia = new frmReferencias();

        if (silaboAnterior == null) {
            carrerasDocente = new ArrayList<>();

            periodosCarrera = new ArrayList<>();

            materiasDocente = new ArrayList<>();

            unidadesSilabo = new ArrayList<>();

            estrategiasSilabo = new ArrayList<>();

            estrategiasAprendizaje = new ArrayList<>();

            evaluacionesSilabo = new ArrayList<>();

            biblioteca = new ArrayList<>();

            referenciasSilabo = new ArrayList<>();

            for (int i = 1; i <= numUnidades; i++) {

                UnidadSilaboMD tmp = new UnidadSilaboMD();
                tmp.setNumeroUnidad(i);
                unidadesSilabo.add(tmp);
            }
        } else {

            unidadesSilabo = UnidadSilaboBD.consultar(conexion, silaboAnterior.getIdSilabo());

            estrategiasSilabo = EstrategiasUnidadBD.cargarEstrategiasU(conexion, silaboAnterior.getIdSilabo());

            estrategiasAprendizaje = new ArrayList<>();

            evaluacionesSilabo = EvaluacionSilaboBD.recuperarEvaluaciones(conexion, silaboAnterior.getIdSilabo());

            biblioteca = new ArrayList<>();

            referenciasSilabo = ReferenciaSilaboBD.recuperarReferencias(conexion, silaboAnterior.getIdSilabo());

            tiposActividad = TipoActividadBD.consultar(conexion);

            cargarReferencias(referenciasSilabo);

        }

        principal.getDpnlPrincipal().add(gestion);

        gestion.setTitle(silabo.getIdMateria().getNombre());

        gestion.show();

        gestion.setLocation((principal.getDpnlPrincipal().getSize().width - gestion.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - gestion.getSize().height) / 2);

        unidadesSilabo.forEach((umd) -> {
            gestion.getCmbUnidad().addItem("Unidad " + umd.getNumeroUnidad());
        });

        gestion.getCmbUnidad().setSelectedIndex(0);

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

            }
        });

        gestion.getLstEstrategiasPredeterminadas().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {

                int index = gestion.getLstEstrategiasPredeterminadas().locationToIndex(event.getPoint());
                CheckListItem item = (CheckListItem) gestion.getLstEstrategiasPredeterminadas().getModel().getElementAt(index);
                item.setSelected(!item.isSelected());
                gestion.getLstEstrategiasPredeterminadas().repaint(gestion.getLstEstrategiasPredeterminadas().getCellBounds(index, index));
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

                estrategiasSilabo.removeIf(e -> {
                    return e.getIdUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad();
                });

                for (int i = 0; i < gestion.getLstEstrategiasPredeterminadas().getModel().getSize(); i++) {
                    CheckListItem item2 = (CheckListItem) gestion.getLstEstrategiasPredeterminadas().getModel().getElementAt(i);

                    if (item2.isSelected()) {

                        String estrategia = gestion.getLstEstrategiasPredeterminadas().getModel().getElementAt(i).toString();

                        Optional<EstrategiasAprendizajeMD> estrategiaSeleccionada = EstrategiasAprendizajeBD.consultar(conexion).stream().
                                filter(e -> e.getDescripcionEstrategia().equals(estrategia)).
                                findFirst();

                        estrategiasSilabo.add(new EstrategiasUnidadMD(estrategiaSeleccionada.get(), unidadSeleccionada));

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
                        if (unidadSeleccionada.getFechaFinUnidad().isAfter(fechaInicio)) {
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

        gestion.getDchFechaFin().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {

                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

                if (gestion.getDchFechaFin().getDate() != null) {
                    LocalDate fechaFin = gestion.getDchFechaFin().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (unidadSeleccionada.getFechaInicioUnidad() == null) {
                        unidadSeleccionada.setFechaFinUnidad(fechaFin);
                        actualizarUnidad(unidadSeleccionada);
                    } else {
                        if (unidadSeleccionada.getFechaInicioUnidad().isBefore(fechaFin.plus(1, ChronoUnit.DAYS))) {

                            unidadSeleccionada.setFechaFinUnidad(fechaFin);
                            actualizarUnidad(unidadSeleccionada);
                        } else {
                            JOptionPane.showMessageDialog(null, "La fecha de fin no puede ser anterior a la fecha de inicio", "Alerta", JOptionPane.WARNING_MESSAGE);

                            gestion.getDchFechaFin().setDate(Date.from(unidadSeleccionada.getFechaInicioUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant().plus(1, ChronoUnit.DAYS)));
                        }
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

        gestion.getSpnHorasDocencia().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {

                int acumuladoDocencia = 0;
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                for (UnidadSilaboMD umd : unidadesSilabo) {
                    if (umd.getNumeroUnidad() != unidadSeleccionada.getNumeroUnidad()) {
                        acumuladoDocencia = acumuladoDocencia + umd.getHorasDocenciaUnidad();
                    }

                }

                if ((acumuladoDocencia + (int) gestion.getSpnHorasDocencia().getValue()) > silabo.getIdMateria().getHorasDocencia()) {
                    gestion.getSpnHorasDocencia().setValue(silabo.getIdMateria().getHorasDocencia() - acumuladoDocencia);
                    JOptionPane.showMessageDialog(null, "Esta materia debe cumplir con el número exacto de " + silabo.getIdMateria().getHorasDocencia() + " horas de gestión docente", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                unidadSeleccionada.setHorasDocenciaUnidad((int) (gestion.getSpnHorasDocencia().getValue()));

            }

        });

        gestion.getSpnHorasPracticas().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {

                int acumuladoPractica = 0;
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                for (UnidadSilaboMD umd : unidadesSilabo) {
                    if (umd.getNumeroUnidad() != unidadSeleccionada.getNumeroUnidad()) {
                        acumuladoPractica = acumuladoPractica + umd.getHorasPracticaUnidad();
                    }

                }

                if ((acumuladoPractica + (int) gestion.getSpnHorasPracticas().getValue()) > silabo.getIdMateria().getHorasPracticas()) {
                    gestion.getSpnHorasPracticas().setValue(silabo.getIdMateria().getHorasPracticas() - acumuladoPractica);
                    JOptionPane.showMessageDialog(null, "Esta materia debe cumplir con el número exacto de " + silabo.getIdMateria().getHorasPracticas() + " horas de gestión práctica", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                unidadSeleccionada.setHorasPracticaUnidad((int) (gestion.getSpnHorasPracticas().getValue()));

            }

        });

        gestion.getSpnHorasAutonomas().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {

                int acumuladoAutonomo = 0;
                UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();
                for (UnidadSilaboMD umd : unidadesSilabo) {
                    if (umd.getNumeroUnidad() != unidadSeleccionada.getNumeroUnidad()) {
                        acumuladoAutonomo = acumuladoAutonomo + umd.getHorasAutonomoUnidad();
                    }

                }

                if ((acumuladoAutonomo + (int) gestion.getSpnHorasAutonomas().getValue()) > silabo.getIdMateria().getHorasAutoEstudio()) {
                    gestion.getSpnHorasAutonomas().setValue(silabo.getIdMateria().getHorasAutoEstudio() - acumuladoAutonomo);
                    JOptionPane.showMessageDialog(null, "Esta materia debe cumplir con el número exacto de " + silabo.getIdMateria().getHorasAutoEstudio() + " horas de gestión autónoma", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                unidadSeleccionada.setHorasAutonomoUnidad((int) (gestion.getSpnHorasAutonomas().getValue()));

            }

        });

        gestion.getLblAgregarUnidad().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                agregarUnidad();
            }

        });

        gestion.getLblEliminarUnidad().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {

                if (unidadesSilabo.size() > 1) {
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
                    limpiarEvaluacionesAD();
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
                    limpiarEvaluacionesAC();
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
                    limpiarEvaluacionesP();
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
                    limpiarEvaluacionesA();
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

        gestion.getBtnSiguiente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (validarCampos()) {

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

            }

        });

        gestion.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int reply = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar el proceso?", "Cancelar", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    gestion.dispose();
                }
            }

        });

        cargarEstrategias(unidadesSilabo.get(0));
        gestion.getCmbUnidad().setSelectedIndex(0);
    }

    public void citarReferencias(SilaboBD silabo, frmReferencias bibliografia) {

        System.out.println("------->entro");
        principal.getDpnlPrincipal().add(bibliografia);

        bibliografia.setTitle(silabo.getIdMateria().getNombre());

        bibliografia.show();

        bibliografia.setLocation((principal.getDpnlPrincipal().getSize().width - bibliografia.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - bibliografia.getSize().height) / 2);

        bibliografia.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {

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

        bibliografia.getBtnFinalizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                guardarSilabo();
                JOptionPane.showMessageDialog(null, "Silabo guardado exitosamente");
                configuracion.dispose();
                gestion.dispose();
                bibliografia.dispose();

                principal.getMnCtSilabos().doClick();
            }

        });

        bibliografia.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int reply = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar el proceso?", "Cancelar", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    bibliografia.dispose();
                    gestion.dispose();
                }
            }

        });

        bibliografia.getBtnAtras().addActionListener(b -> bibliografia.setVisible(false));
        bibliografia.getBtnAtras().addActionListener(b -> gestion.setVisible(true));

        cargarBiblioteca();
    }

    public UnidadSilaboMD seleccionarUnidad() {

        Optional<UnidadSilaboMD> unidadSeleccionada = unidadesSilabo.stream().
                filter(u -> u.getNumeroUnidad() == (gestion.getCmbUnidad().getSelectedIndex() + 1)).
                findFirst();

        return unidadSeleccionada.get();
    }

    public void actualizarUnidad(UnidadSilaboMD unidadSeleccionada) {

        unidadesSilabo.set(unidadSeleccionada.getNumeroUnidad() - 1, unidadSeleccionada);
    }

    public void mostrarUnidad() {

        /*for (int i = 0; i < gestion.getLstEstrategiasPredeterminadas().getModel().getSize(); i++) {
              CheckListItem item2 = (CheckListItem) gestion.getLstEstrategiasPredeterminadas().getModel().getElementAt(i);
              item2.setSelected(false);
        }*/
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

    public void cargarEstrategias(UnidadSilaboMD unidadSeleccionada) {

        DefaultListModel modeloEstrategias = new DefaultListModel();

        gestion.getLstEstrategiasPredeterminadas().setCellRenderer(new CheckListRenderer());
        gestion.getLstEstrategiasPredeterminadas().setModel(modeloEstrategias);

        EstrategiasAprendizajeBD.consultar(conexion).forEach((emd) -> {
            modeloEstrategias.addElement(new CheckListItem(emd.getDescripcionEstrategia()));
        });

        for (int i = 0; i < gestion.getLstEstrategiasPredeterminadas().getModel().getSize(); i++) {
            CheckListItem item = (CheckListItem) gestion.getLstEstrategiasPredeterminadas().getModel().getElementAt(i);

            for (EstrategiasUnidadMD emd : estrategiasSilabo) {

                if (emd.getIdUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad()
                        && modeloEstrategias.get(i).toString().equals(emd.getIdEstrategia().getDescripcionEstrategia())) {

                    item.setSelected(true);

                }
            }
        }
    }

    public void agregarUnidad() {

        UnidadSilaboMD nuevaUnidad = new UnidadSilaboMD();
        nuevaUnidad.setNumeroUnidad(unidadesSilabo.size() + 1);
        unidadesSilabo.add(nuevaUnidad);
        gestion.getCmbUnidad().addItem("Unidad " + unidadesSilabo.size());
        JOptionPane.showMessageDialog(null, "Nueva unidad agregada");

        gestion.getCmbUnidad().setSelectedIndex(unidadesSilabo.size() - 1);

    }

    public void eliminarUnidad() {

        UnidadSilaboMD unidadSeleccionada = seleccionarUnidad();

        unidadesSilabo.removeIf(u -> u.getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad());

        estrategiasSilabo.removeIf(e -> e.getIdUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad());

        evaluacionesSilabo.removeIf(e -> e.getIdUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad());

        for (EstrategiasUnidadMD emd : estrategiasSilabo) {
            if (emd.getIdUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad()) {
                emd.getIdUnidad().setNumeroUnidad(emd.getIdUnidad().getNumeroUnidad() - 1);
            }
        }

        for (EvaluacionSilaboMD emd : evaluacionesSilabo) {
            if (emd.getIdUnidad().getNumeroUnidad() == unidadSeleccionada.getNumeroUnidad()) {
                emd.getIdUnidad().setNumeroUnidad(emd.getIdUnidad().getNumeroUnidad() - 1);
            }
        }

        /* unidadesSilabo.stream().filter((umd) -> (umd.getNumeroUnidad() > unidadSeleccionada.getNumeroUnidad())).map((umd) -> {
            umd.setNumeroUnidad(umd.getNumeroUnidad() - 1);
            return umd;
        }).forEachOrdered((umd) -> {
            gestion.getCmbUnidad().removeItemAt(umd.getNumeroUnidad() - 1);
        });*/
        for (UnidadSilaboMD umd : unidadesSilabo) {
            if (umd.getNumeroUnidad() > unidadSeleccionada.getNumeroUnidad()) {
                //gestion.getCmbUnidad().removeItemAt(umd.getNumeroUnidad());
                umd.setNumeroUnidad(umd.getNumeroUnidad() - 1);

            }
        }

        List<String> comboNuevo = new ArrayList<>();

        for (int i = 1; i <= unidadesSilabo.size(); i++) {
            comboNuevo.add("Unidad " + (i));
        }

        gestion.getCmbUnidad().setModel(new DefaultComboBoxModel(comboNuevo.toArray()));

        if (unidadSeleccionada.getNumeroUnidad() == 1) {
            gestion.getCmbUnidad().setSelectedIndex(0);

        } else if (unidadSeleccionada.getNumeroUnidad() == unidadesSilabo.size() + 1) {

            gestion.getCmbUnidad().setSelectedIndex(unidadSeleccionada.getNumeroUnidad() - 2);
        } else {
            gestion.getCmbUnidad().setSelectedIndex(unidadSeleccionada.getNumeroUnidad() - 1);
        }

    }

    public void agregarEvaluacion(TipoActividadMD tipo, UnidadSilaboMD unidad, int p) {

        switch (p) {
            case 1:

                if (validarLimiteEvaluaciones((double) (gestion.getSpnValoracionAD().getValue()))) {
                    ++idEvaluacionSig;
                    idEvaluacion = idEvaluacionSig;
                    evaluacionesSilabo.add(new EvaluacionSilaboMD(idEvaluacion, gestion.getTxtIndicadorAD().getText(), gestion.getTxtInstrumentoAD().getText(), (double) (gestion.getSpnValoracionAD().getValue()), gestion.getDchFechaEnvioAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gestion.getDchFechaPresentacionAD().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), tipo, unidad));
                    cargarEvaluaciones((DefaultTableModel) gestion.getTblAsistidaDocente().getModel(), 1);

                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder los 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                break;
            case 2:

                if (validarLimiteEvaluaciones((double) (gestion.getSpnValoracionAC().getValue()))) {
                    ++idEvaluacionSig;
                    idEvaluacion = idEvaluacionSig;
                    evaluacionesSilabo.add(new EvaluacionSilaboMD(idEvaluacion, gestion.getTxtIndicadorAC().getText(), gestion.getTxtInstrumentoAC().getText(), (double) (gestion.getSpnValoracionAC().getValue()), gestion.getDchFechaEnvioAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gestion.getDchFechaPresentacionAC().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), tipo, unidad));
                    cargarEvaluaciones((DefaultTableModel) gestion.getTblAprendizajeColaborativo().getModel(), 2);

                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder los 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                break;
            case 3:
                if (validarLimiteEvaluaciones((double) (gestion.getSpnValoracionP().getValue()))) {
                    ++idEvaluacionSig;
                    idEvaluacion = idEvaluacionSig;
                    evaluacionesSilabo.add(new EvaluacionSilaboMD(idEvaluacion, gestion.getTxtIndicadorP().getText(), gestion.getTxtInstrumentoP().getText(), (double) (gestion.getSpnValoracionP().getValue()), gestion.getDchFechaEnvioP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gestion.getDchFechaPresentacionP().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), tipo, unidad));
                    cargarEvaluaciones((DefaultTableModel) gestion.getTblPractica().getModel(), 3);

                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder los 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }

                break;
            case 4:
                if (validarLimiteEvaluaciones((double) (gestion.getSpnValoracionA().getValue()))) {
                    ++idEvaluacionSig;
                    idEvaluacion = idEvaluacionSig;
                    evaluacionesSilabo.add(new EvaluacionSilaboMD(idEvaluacion, gestion.getTxtIndicadorA().getText(), gestion.getTxtInstrumentoA().getText(), (double) (gestion.getSpnValoracionA().getValue()), gestion.getDchFechaEnvioA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), gestion.getDchFechaPresentacionA().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), tipo, unidad));
                    cargarEvaluaciones((DefaultTableModel) gestion.getTblAutonoma().getModel(), 4);

                } else {
                    JOptionPane.showMessageDialog(null, "El total de evaluaciones no puede exceder el valor de 60 puntos", "Aviso", JOptionPane.WARNING_MESSAGE);

                }
                break;
        }
    }

    public boolean validarLimiteEvaluaciones(double valor) {

        double total = 0;

        total = evaluacionesSilabo.stream().map((emd) -> emd.getValoracion()).reduce(total, (accumulator, _item) -> accumulator + _item);

        return (total + valor) <= 60;

    }

    public TipoActividadMD seleccionarTipoActividad(String[] infoE) {

        Optional<TipoActividadMD> tipoSeleccionado = tiposActividad.stream().
                filter(a -> a.getNombreTipoActividad().equals(infoE[0]) && a.getNombreSubtipoActividad().equals(infoE[1])).
                findFirst();

        return tipoSeleccionado.get();

    }

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

        /*for (int i = 0; i < evaluaciones.size(); i++) {

            if (evaluaciones.get(i).getIdUnidad().getIdUnidad() == gestion.getCmbUnidad().getSelectedIndex() && evaluaciones.get(i).getIdTipoActividad().getIdTipoActividad() == p) {

                modeloTabla.addRow(new Object[]{
                    evaluaciones.get(i).getIndicador(),
                    evaluaciones.get(i).getInstrumento(),
                    evaluaciones.get(i).getValoracion(),
                    evaluaciones.get(i).getFechaEnvio(),
                    evaluaciones.get(i).getFechaPresentacion()
                });
            }

        }*/
        for (EvaluacionSilaboMD emd : evaluacionesSilabo) {

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

        gestion.getTxtInstrumentoAD().setText(null);
        gestion.getTxtIndicadorAD().setText(null);
        gestion.getSpnValoracionAD().setValue(1.0d);
        gestion.getDchFechaEnvioAD().setDate(null);
        gestion.getDchFechaPresentacionAD().setDate(null);

    }

    public void limpiarEvaluacionesAC() {

        gestion.getTxtInstrumentoAC().setText(null);
        gestion.getTxtIndicadorAC().setText(null);
        gestion.getSpnValoracionAC().setValue(1.0d);

        gestion.getDchFechaPresentacionAC().setDate(null);
        gestion.getDchFechaEnvioAC().setDate(null);

    }

    public void limpiarEvaluacionesP() {

        gestion.getTxtInstrumentoP().setText(null);
        gestion.getTxtIndicadorP().setText(null);
        gestion.getSpnValoracionP().setValue(1.0d);
        gestion.getDchFechaEnvioP().setDate(null);
        gestion.getDchFechaPresentacionP().setDate(null);

    }

    public void limpiarEvaluacionesA() {

        gestion.getTxtInstrumentoA().setText(null);
        gestion.getTxtIndicadorA().setText(null);
        gestion.getSpnValoracionA().setValue(1.0d);
        gestion.getDchFechaEnvioA().setDate(null);
        gestion.getDchFechaPresentacionA().setDate(null);

    }

    public void cargarBiblioteca() {

        DefaultTableModel modeloTabla;

        modeloTabla = (DefaultTableModel) bibliografia.getTblBiblioteca().getModel();

        biblioteca = ReferenciasBD.consultarBiblioteca(conexion, bibliografia.getTxtBuscar().getText());

        for (int j = bibliografia.getTblBiblioteca().getModel().getRowCount() - 1; j >= 0; j--) {

            modeloTabla.removeRow(j);
        }

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
            if (referenciasSilabo.get(i).getIdReferencia().getDescripcionReferencia().equals(referenciaSeleccionada.getDescripcionReferencia())) {
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

        modeloBase = new DefaultListModel<>();

        b.forEach((s) -> {
            modeloBase.addElement(s);
        });

        bibliografia.getLstBibliografiaBase().setModel(modeloBase);

    }

    public void agregarBibliografiaNoBase() {

        ReferenciasMD complementaria = new ReferenciasMD(String.valueOf(silaboNuevo.getIdSilabo()), bibliografia.getTxrBibliografiaComplementaria().getText(), "Complementaria");
        ReferenciasMD linkografia = new ReferenciasMD(String.valueOf(silaboNuevo.getIdSilabo()), bibliografia.getTxrLinkografia().getText(), "Linkografia");

        referenciasSilabo.add(new ReferenciaSilaboMD(complementaria, silaboNuevo));
        referenciasSilabo.add(new ReferenciaSilaboMD(linkografia, silaboNuevo));

    }

    public void quitarBibliografiaBase() {

        String seleccion = bibliografia.getLstBibliografiaBase().getModel().getElementAt(bibliografia.getLstBibliografiaBase().getSelectedIndex()).substring(2);

        referenciasSilabo.removeIf(p -> p.getIdReferencia().getDescripcionReferencia().equals(seleccion));

        modeloBase.remove(bibliografia.getLstBibliografiaBase().getSelectedIndex());
    }

    public ReferenciasMD seleccionarReferencia() {

        int seleccion = bibliografia.getTblBiblioteca().getSelectedRow();
        Optional<ReferenciasMD> referenciaSeleccionada = biblioteca.stream().
                filter(r -> r.getCodigoReferencia().equals(bibliografia.getTblBiblioteca().getValueAt(seleccion, 0))).
                findFirst();

        return referenciaSeleccionada.get();
    }

    public void quitarEvaluacionAD(DefaultTableModel modeloTabla, int p) {

        evaluacionesSilabo.removeIf(e -> e.getIdEvaluacion() == gestion.getTblAsistidaDocente().getValueAt(gestion.getTblAsistidaDocente().getSelectedRow(), 5));
        cargarEvaluaciones(modeloTabla, p);
    }

    public void quitarEvaluacionAC(DefaultTableModel modeloTabla, int p) {

        evaluacionesSilabo.removeIf(e -> e.getIdEvaluacion() == gestion.getTblAprendizajeColaborativo().getValueAt(gestion.getTblAprendizajeColaborativo().getSelectedRow(), 5));

        cargarEvaluaciones(modeloTabla, p);
    }

    public void quitarEvaluacionP(DefaultTableModel modeloTabla, int p) {

        evaluacionesSilabo.removeIf(e -> e.getIdEvaluacion() == gestion.getTblPractica().getValueAt(gestion.getTblPractica().getSelectedRow(), 5));

        cargarEvaluaciones(modeloTabla, p);
    }

    public void quitarEvaluacionA(DefaultTableModel modeloTabla, int p) {

        evaluacionesSilabo.removeIf(e -> e.getIdEvaluacion() == gestion.getTblAutonoma().getValueAt(gestion.getTblAutonoma().getSelectedRow(), 5));

        cargarEvaluaciones(modeloTabla, p);
    }

    public void insertarUnidades() {

        for (UnidadSilaboMD umd : unidadesSilabo) {
            umd.getIdSilabo().setIdSilabo(silaboNuevo.getIdSilabo());
            UnidadSilaboBD ubd = new UnidadSilaboBD(conexion);
            ubd.insertar(umd);

            for (EstrategiasUnidadMD emd : estrategiasSilabo) {

                if (emd.getIdUnidad().getNumeroUnidad() == umd.getNumeroUnidad()) {
                    EstrategiasUnidadBD ebd = new EstrategiasUnidadBD(conexion);
                    ebd.insertar(emd);
                }

            }

            for (EvaluacionSilaboMD evd : evaluacionesSilabo) {

                if (evd.getIdUnidad().getNumeroUnidad() == umd.getNumeroUnidad()) {
                    EvaluacionSilaboBD esd = new EvaluacionSilaboBD(conexion);
                    esd.insertar(evd);
                }

            }
        }
    }

    public void insertarReferencias() {

        agregarBibliografiaNoBase();

        for (int i = 0; i < referenciasSilabo.size() - 2; i++) {
            ReferenciaSilaboBD rbd = new ReferenciaSilaboBD(conexion);
            rbd.insertar(referenciasSilabo.get(i));
        }

        ReferenciasBD r1 = new ReferenciasBD(conexion);
        r1.insertar(referenciasSilabo.get(referenciasSilabo.size() - 2).getIdReferencia());
        ReferenciaSilaboBD rbd1 = new ReferenciaSilaboBD(conexion);
        rbd1.insertar(referenciasSilabo.get(referenciasSilabo.size() - 2));

        ReferenciasBD r2 = new ReferenciasBD(conexion);;
        r2.insertar(referenciasSilabo.get(referenciasSilabo.size() - 1).getIdReferencia());
        ReferenciaSilaboBD rbd2 = new ReferenciaSilaboBD(conexion);
        rbd2.insertar(referenciasSilabo.get(referenciasSilabo.size() - 1));

    }



    public void guardarSilabo() {

        silaboNuevo.insertar();
        insertarUnidades();
        insertarReferencias();
       // exportarPDF();

    
    }
//    public void guardaArchivo(String ruta) throws SQLException, FileNotFoundException {
//        String sql = "UPDATE \"Silabo\"VALUES (?)";
//        //Creamos una cadena para después prepararla
//        PreparedStatement stmt = conexion.prepareStatement(sql);
//        File archivo = new File("../PF-Instituto-M3A-F1/"+silabo.getIdMateria().getNombre()+".pdf");
//        //ruta puede ser: "c://archivo"
//        FileInputStream fis = new FileInputStream(archivo);
//        //Lo convertimos en un Stream
//        stmt.setBinaryStream(1, fis, (int) archivo.length());
//        //Asignamos el Stream al Statement
//        stmt.execute();
    
//    File file = new File("myimage.gif");
//FileInputStream fis = new FileInputStream(file);
//PreparedStatement ps = conn.prepareStatement("INSERT INTO images VALUES (?, ?)");
//ps.setString(1, file.getName());
//ps.setBinaryStream(2, fis, (int)file.length());
//ps.executeUpdate();
//ps.close();
//fis.close();
//    }

    public boolean validarCampos() {

        boolean control = true;

        int contador = 0;

        for (int i = 0; i < unidadesSilabo.size(); i++) {

            if (unidadesSilabo.get(i).getTituloUnidad() == null) {
                control = false;
            }

            if (unidadesSilabo.get(i).getObjetivoEspecificoUnidad() == null) {
                control = false;
            }

            if (unidadesSilabo.get(i).getResultadosAprendizajeUnidad() == null) {
                control = false;
            }

            if (unidadesSilabo.get(i).getContenidosUnidad() == null) {
                control = false;
            }

            if (unidadesSilabo.get(i).getFechaInicioUnidad() == null) {
                control = false;
            }

            if (unidadesSilabo.get(i).getFechaFinUnidad() == null) {
                control = false;
            }

            for (int j = 0; j < estrategiasSilabo.size(); j++) {
                if (estrategiasSilabo.get(j).getIdUnidad().getIdUnidad().equals(unidadesSilabo.get(i).getIdUnidad())) {
                    contador++;
                }
            }

            if (contador == 0) {
                control = false;
            }

        }

        return control;

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

        modeloBase = new DefaultListModel<>();
        b.forEach((s) -> {
            modeloBase.addElement(s);
        });

        bibliografia.getLstBibliografiaBase().setModel(modeloBase);

    }
}
