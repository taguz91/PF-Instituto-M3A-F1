package controlador.silabo.frm;

import com.toedter.calendar.JDateChooser;
import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;
import modelo.estilo.TblEstilo;
import modelo.estrategiasUnidad.EstrategiasUnidadMD;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;
import modelo.referencias.ReferenciasMD;
import modelo.referenciasSilabo.ReferenciaSilaboMD;
import modelo.silabo.NEWEstrategiaUnidadBD;
import modelo.silabo.NEWReferenciaSilaboBD;
import modelo.silabo.NEWSilaboBD;
import modelo.silabo.NEWTipoActividadBD;
import modelo.silabo.NEWUnidadSilaboBD;
import modelo.silabo.SilaboMD;
import modelo.tipoActividad.TipoActividadMD;
import modelo.unidadSilabo.UnidadSilaboMD;
import modelo.validaciones.Validar;
import vista.silabos.NEWFrmSilabo;

/**
 *
 * @author gus
 */
public class FRMSilaboCTR extends DCTR {

    private final NEWFrmSilabo FRM_GESTION = new NEWFrmSilabo();
    // Conexiones a base de datos
    private final NEWSilaboBD SBD = NEWSilaboBD.single();
    private final NEWUnidadSilaboBD USBD = NEWUnidadSilaboBD.single();
    private final NEWEstrategiaUnidadBD EUBD = NEWEstrategiaUnidadBD.single();
    private final NEWReferenciaSilaboBD RSBD = NEWReferenciaSilaboBD.single();
    private final NEWTipoActividadBD TABD = NEWTipoActividadBD.single();

    // Listas para guardar los contenidos del formulario 
    private List<UnidadSilaboMD> unidades;
    protected List<EstrategiasUnidadMD> estrategias;
    protected List<EvaluacionSilaboMD> evaluaciones;
    protected List<TipoActividadMD> tiposActividad;
    protected List<ReferenciasMD> biblioteca;
    protected List<ReferenciaSilaboMD> referenciasSilabo;

    // Titulo de las tablas  
    private static final String[] TITULO_GESTION = {
        "Indicador", "Instrumento",
        "Valoración", "Fecha Envío",
        "Fecha Presentación"
    };
    private static final String[] TITULO_ESTRATEGIAS = {
        "X", "Titulo"
    };
    // Todos los modelos de las tablas de gestion
    private final DefaultTableModel mdTblAD = TblEstilo.modelTblSinEditar(TITULO_GESTION);
    private final DefaultTableModel mdTblAC = TblEstilo.modelTblSinEditar(TITULO_GESTION);
    private final DefaultTableModel mdTblGP = TblEstilo.modelTblSinEditar(TITULO_GESTION);
    private final DefaultTableModel mdTblGA = TblEstilo.modelTblSinEditar(TITULO_GESTION);
    private final DefaultTableModel mdTblES = TblEstilo.modelTblSinEditar(TITULO_ESTRATEGIAS);

    // Para tener referencia de un silabo 
    private final SilaboMD silabo;
    // Para guardar la unidad  
    private UnidadSilaboMD unidadSelec;
    // El numero de horas docencia, practica y autonomas 
    private double numHD = 0;
    private double numHP = 0;
    private double numHA = 0;

    public FRMSilaboCTR(
            VtnPrincipalCTR ctrPrin,
            SilaboMD silabo
    ) {
        super(ctrPrin);
        this.silabo = silabo;
    }

    public void nuevo(int num) {
        crearSilaboNuevo(num);
        iniciarSilabo();
        iniciarVentana();
    }

    public void referenciado() {
        cargarDatosSilabo();
        iniciarSilabo();
        // Buscamos sin el id de las unidades
        unidades = USBD.getBySilaboParaReferencia(silabo.getID());
        iniciarVentana();
    }

    public void editar() {
        cargarDatosSilabo();
        // Buscamos con el id de unidades
        unidades = USBD.getBySilabo(silabo.getID());
        iniciarVentana();
    }

    /**
     * Comprobamos si existe o no un silabo anterior para iniciar el formulario
     */
    private void iniciarVentana() {
        // Siempre iniciamos el estado del boton en falso para que 
        // no pueda guardar hasta realizar cambios
        estadoBtnGuardar(false);
        // Titulo de la unidad  
        FRM_GESTION.setTitle(
                silabo.getPeriodo().getNombre()
                + " | "
                + silabo.getMateria().getNombre()
        );
        actualizarHoras();
        ctrPrin.agregarVtn(FRM_GESTION);
        iniciarCMBUnidad();
        mostrarUnidad();
        iniciarEventoSPNHoras();
        iniciarEventosFecha();
    }

    private void iniciarCMBUnidad() {
        unidades.forEach(u -> {
            FRM_GESTION.getCmbUnidad()
                    .addItem("Unidad " + u.getNumeroUnidad());
        });
        FRM_GESTION.getCmbUnidad().setSelectedIndex(0);
        FRM_GESTION.getCmbUnidad().addActionListener(e -> mostrarUnidad());
    }

    private void iniciarSilabo() {
        // Todos los modelos de las tablas  
        FRM_GESTION.getTblAprendizajeColaborativo()
                .setModel(mdTblAC);
        FRM_GESTION.getTblAsistidaDocente()
                .setModel(mdTblAD);
        FRM_GESTION.getTblAutonoma()
                .setModel(mdTblGA);
        FRM_GESTION.getTblPractica()
                .setModel(mdTblGP);
        FRM_GESTION.getTblEstrategias()
                .setModel(mdTblES);
        // Iniciamo los arrays para guardar las diferentes cosas
        evaluaciones = new ArrayList<>();
        biblioteca = new ArrayList<>();
        tiposActividad = TABD.getAll();
        // Actualizamos el total de gestion que tenemos
        mostrarTotalGestion();
    }

    private void crearSilaboNuevo(int numUnidades) {
        unidades = new ArrayList<>();
        estrategias = new ArrayList<>();
        referenciasSilabo = new ArrayList<>();
        // Agregamos el numero de unidades que indico 
        // en el formulario de configuracion
        for (int i = 1; i <= numUnidades; i++) {
            UnidadSilaboMD us = new UnidadSilaboMD();
            us.setNumeroUnidad(i);
            unidades.add(us);
        }
    }

    private void cargarDatosSilabo() {
        // Igual para editar
        estrategias = EUBD.getBySilabo(silabo.getID());
        // Igual al editar 
        referenciasSilabo = RSBD.getBySilabo(silabo.getID());
    }

    private void estadoBtnGuardar(boolean estado) {
        FRM_GESTION.getBtnGuardar().setEnabled(estado);
    }

    /**
     * Para obtener la unidad seleccionada
     */
    private void actualizarUnidadSeleccionada() {
        unidadSelec = unidades.get(
                FRM_GESTION.getCmbUnidad().getSelectedIndex()
        );
    }

    /**
     * Al seleccionar una unidad del combo realizamos todas las siguientes
     * acciones.
     */
    private void mostrarUnidad() {
        // Actualizamos la unidad seleccionada
        actualizarUnidadSeleccionada();

        // Actualizamos los txt del formulario 
        FRM_GESTION.getTxtTitulo().setText(
                unidadSelec.getTituloUnidad()
        );
        FRM_GESTION.getTxrContenidos().setText(
                unidadSelec.getContenidosUnidad()
        );
        FRM_GESTION.getTxrObjetivos().setText(
                unidadSelec.getObjetivoEspecificoUnidad()
        );
        FRM_GESTION.getTxrResultados().setText(
                unidadSelec.getResultadosAprendizajeUnidad()
        );

        // Actualizamos las fechas  
        if (unidadSelec.getFechaFinUnidad() != null) {
            FRM_GESTION.getDchFechaInicio().setDate(
                    Date.from(unidadSelec.getFechaInicioUnidad()
                            .atStartOfDay(ZoneId.systemDefault())
                            .toInstant()
                    )
            );
        } else {
            FRM_GESTION.getDchFechaInicio().setDate(null);
        }

        if (unidadSelec.getFechaFinUnidad() != null) {
            FRM_GESTION.getDchFechaFin().setDate(
                    Date.from(unidadSelec.getFechaFinUnidad()
                            .atStartOfDay(ZoneId.systemDefault())
                            .toInstant()
                    )
            );
        } else {
            FRM_GESTION.getDchFechaFin().setDate(null);
        }

        // Actualizamos las horas de docencia  
        FRM_GESTION.getSpnHdocencia().setValue(
                unidadSelec.getHorasDocenciaUnidad()
        );
        FRM_GESTION.getSpnHpracticas().setValue(
                unidadSelec.getHorasPracticaUnidad()
        );
        FRM_GESTION.getSpnHautonomas().setValue(
                unidadSelec.getHorasAutonomoUnidad()
        );

        // Actualizamos las estrategias 
        // Debemos revisar  lo de estrategias 
        // Actualizamos las evaluaciones 
        cargarEvaluaciones();
    }

    /**
     * Cargamos las evaluaciones
     */
    private void cargarEvaluaciones() {
        mdTblAC.setRowCount(0);
        mdTblAD.setRowCount(0);
        mdTblES.setRowCount(0);
        mdTblGA.setRowCount(0);
        mdTblGP.setRowCount(0);

        evaluaciones.forEach(e -> {
            if (e.getIdUnidad().getNumeroUnidad() == unidadSelec.getNumeroUnidad()) {
                Object[] row = {
                    e.getIndicador(),
                    e.getInstrumento(),
                    e.getValoracion(),
                    e.getFechaEnvio(),
                    e.getFechaPresentacion(),
                    e.getIdEvaluacion()
                };

                switch (e.getIdTipoActividad().getIdTipoActividad()) {
                    // "Asistido por el Docente"
                    case 1:
                        mdTblAD.addRow(row);
                        break;
                    // "Aprendizaje Colaborativo"
                    case 2:
                        mdTblAC.addRow(row);
                        break;
                    // "Gestión de la Práctica"
                    case 3:
                        mdTblGP.addRow(row);
                        break;
                    // "Gestión de Trabajo Autónomo"
                    case 4:
                        mdTblGA.addRow(row);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * Mostramos el total de las evaluaciones de gestion
     */
    private void mostrarTotalGestion() {
        double total = 0;
        total = evaluaciones.stream()
                .map((emd) -> emd.getValoracion())
                .reduce(
                        total,
                        (accumulator, _item)
                        -> accumulator + _item
                );
        FRM_GESTION.getLblAcumuladoGestion().setText(total + "/60");
    }

    /**
     * Validamos las fechas que ingresamos
     */
    private void iniciarEventosFecha() {
        FRM_GESTION.getDchFechaInicio().addPropertyChangeListener(e -> validarFechaInicio());
        FRM_GESTION.getDchFechaFin().addPropertyChangeListener(e -> validarFechaFin());
    }

    private void validarFechaInicio() {
        LocalDate fecha = getFechaJDC(FRM_GESTION.getDchFechaInicio());
        if (fecha != null) {
            if (unidadSelec.getFechaFinUnidad() == null) {
                unidadSelec.setFechaInicioUnidad(fecha);
            } else {
                if (unidadSelec.getFechaFinUnidad().isAfter(fecha) 
                        || fecha.equals(unidadSelec.getFechaInicioUnidad())) {
                    unidadSelec.setFechaInicioUnidad(fecha);
                } else {
                    errorFecha("Debe indicar una fecha de inicio correcta.");
                    FRM_GESTION.getDchFechaInicio().setDate(Date
                            .from(unidadSelec.getFechaFinUnidad()
                                    .atStartOfDay(ZoneId.systemDefault())
                                    .toInstant().minus(1, ChronoUnit.DAYS)
                            )
                    );
                }
            }
        }
    }

    private void validarFechaFin() {
        LocalDate fecha = getFechaJDC(FRM_GESTION.getDchFechaFin());
        if (fecha != null) {
            if (unidadSelec.getFechaInicioUnidad() == null) {
                unidadSelec.setFechaFinUnidad(fecha);
            } else {
                if (unidadSelec.getFechaInicioUnidad()
                        .isBefore(fecha)
                        || fecha.equals(unidadSelec.getFechaInicioUnidad())) {
                    unidadSelec.setFechaFinUnidad(fecha);
                } else {
                    errorFecha("Debe indicar una fecha de fin correcta.");
                    FRM_GESTION.getDchFechaFin().setDate(Date
                            .from(unidadSelec.getFechaFinUnidad()
                                    .atStartOfDay(ZoneId.systemDefault())
                                    .toInstant().plus(1, ChronoUnit.DAYS)
                            )
                    );
                }
            }
        }
    }
    
    private void errorFecha(String msg) {
        JOptionPane.showMessageDialog(
                FRM_GESTION,
                msg,
                "Fechas incorrectas",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private LocalDate getFechaJDC(JDateChooser jdc) {
        LocalDate fecha = null;
        if (jdc.getDate() != null) {
            fecha = jdc.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return fecha;
    }

    // ***
    // De aqui para abajo comienza todo lo que es spiners de hora 
    // **
    /**
     * Este evento se usa para todos los SPN de horas al momento de actualizar
     * la hora se actualiza en su modelo y en el lbl que nos muestra las horas
     * totales
     */
    private void iniciarEventoSPNHoras() {
        System.out.println("INICIANDO LOS ESPINERS ");
        // Al actualizar horas autonomas 
        FRM_GESTION.getSpnHautonomas().addChangeListener(e -> validarHA());
        // Al actualizar horas docencia  
        FRM_GESTION.getSpnHdocencia().addChangeListener(e -> validarHD());
        // Al actualizar horas practicas  
        FRM_GESTION.getSpnHpracticas().addChangeListener(e -> validarHP());
    }

    private void validarHA() {
        double h = getHoraSPN(FRM_GESTION.getSpnHautonomas());
        if (h >= 0 && h != unidadSelec.getHorasAutonomoUnidad()) {
            double nh = h + numHA - unidadSelec.getHorasAutonomoUnidad();
            if (nh <= silabo.getMateria().getHorasAutoEstudio()) {
                numHA = nh;
                unidadSelec.setHorasAutonomoUnidad(h);
                reescribirHoras();
            } else {
                FRM_GESTION.getSpnHautonomas().setValue(unidadSelec.getHorasAutonomoUnidad());
                errorHoras("Las horas autonomas superan la hora total indicada en materia.");
            }
        }
        System.out.println("AAAAAAAAA" + h);
    }

    private void validarHD() {
        double h = getHoraSPN(FRM_GESTION.getSpnHdocencia());
        if (h >= 0 && h != unidadSelec.getHorasDocenciaUnidad()) {
            double nh = h + numHD - unidadSelec.getHorasDocenciaUnidad();
            if (nh <= silabo.getMateria().getHorasDocencia()) {
                numHD = nh;
                unidadSelec.setHorasDocenciaUnidad(h);
                reescribirHoras();
            } else {
                FRM_GESTION.getSpnHdocencia().setValue(unidadSelec.getHorasDocenciaUnidad());
                errorHoras("Las horas de docencia superan la hora total indicada en materia.");
            }
        }
        System.out.println("DDDDDDDDDDDDDDDD" + h);
    }

    private void validarHP() {
        double h = getHoraSPN(FRM_GESTION.getSpnHpracticas());
        if (h >= 0 && h != unidadSelec.getHorasPracticaUnidad()) {
            double nh = h + numHP - unidadSelec.getHorasPracticaUnidad();
            if (nh <= silabo.getMateria().getHorasPracticas()) {
                numHP = nh;
                unidadSelec.setHorasPracticaUnidad(h);
                reescribirHoras();
            } else {
                FRM_GESTION.getSpnHpracticas().setValue(unidadSelec.getHorasPracticaUnidad());
                errorHoras("Las horas practicas superan la hora total indicada en materia.");
            }
        }
        System.out.println("PPPPPPPPPPP" + h);
    }

    private double getHoraSPN(JSpinner spn) {
        double h = -1;
        String hs = spn.getValue().toString().trim();
        if (Validar.esNumerosDecimales(hs)) {
            h = Double.parseDouble(hs);
        }
        System.out.println("HORAS: " + hs);
        return h;
    }

    private void errorHoras(String msg) {
        JOptionPane.showMessageDialog(
                FRM_GESTION,
                msg,
                "Horas no permitidas",
                JOptionPane.WARNING_MESSAGE
        );
    }

    /**
     * Actualizamos las horas de materia que se muestra en el formulario
     * consultando de todas las unidades que tenemos
     */
    private void actualizarHoras() {
        unidades.forEach(u -> {
            numHD += u.getHorasDocenciaUnidad();
            numHA += u.getHorasAutonomoUnidad();
            numHP += u.getHorasPracticaUnidad();
        });
        reescribirHoras();
    }

    /**
     * Reescribimos los lbls con el numero de horas que tenemos en total
     */
    private void reescribirHoras() {
        // Numero de horas de la materia 
        FRM_GESTION.getLblTotalHdocencia().setText(numHD + "/" + silabo.getMateria().getHorasDocencia());
        FRM_GESTION.getLblTotalHmateria().setText(numHA + "/" + silabo.getMateria().getHorasAutoEstudio());
        FRM_GESTION.getLblTotalHpracticas().setText(numHP + "/" + silabo.getMateria().getHorasPracticas());
    }

    // ***
    // Termina spinners de horas //
    // **
}
