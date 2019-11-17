package controlador.silabo.frm;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.estilo.TblEstilo;
import modelo.estrategiasAprendizaje.EstrategiasAprendizajeMD;
import modelo.estrategiasAprendizaje.NEWEstrategiaAprendizajeBD;
import modelo.estrategiasUnidad.EstrategiasUnidadMD;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;
import modelo.evaluacionSilabo.NEWEvaluacionSilaboBD;
import modelo.referencias.ReferenciasMD;
import modelo.referenciasSilabo.ReferenciaSilaboMD;
import modelo.silabo.NEWEstrategiaUnidadBD;
import modelo.silabo.NEWPeriodoLectivoBD;
import modelo.silabo.NEWReferenciaSilaboBD;
import modelo.silabo.NEWSilaboBD;
import modelo.silabo.NEWTipoActividadBD;
import modelo.silabo.NEWUnidadSilaboBD;
import modelo.silabo.SilaboMD;
import modelo.tipoActividad.TipoActividadMD;
import modelo.unidadSilabo.UnidadSilaboMD;
import modelo.validaciones.Validar;
import vista.silabos.NEWFrmAcciones;
import vista.silabos.NEWFrmSilabo;

/**
 *
 * @author gus
 */
public class FRMSilaboCTR extends DCTR {

    // Formulario principal 
    private final NEWFrmSilabo FRM_GESTION = new NEWFrmSilabo();
    // Formulario de acciones  
    private final NEWFrmAcciones FRM_ACCIONES;
    // Conexiones a base de datos
    private final NEWSilaboBD SBD = NEWSilaboBD.single();
    private final NEWUnidadSilaboBD USBD = NEWUnidadSilaboBD.single();
    private final NEWEstrategiaUnidadBD EUBD = NEWEstrategiaUnidadBD.single();
    private final NEWReferenciaSilaboBD RSBD = NEWReferenciaSilaboBD.single();
    private final NEWTipoActividadBD TABD = NEWTipoActividadBD.single();
    private final NEWEvaluacionSilaboBD EVBD = NEWEvaluacionSilaboBD.single();
    private final NEWEstrategiaAprendizajeBD EABD = NEWEstrategiaAprendizajeBD.single();

    // Utilidades en este formulario 
    private final UtilsFRMSilaboCTR UFRMSCTR = UtilsFRMSilaboCTR.single();

    // Listas para guardar los contenidos del formulario 
    private List<UnidadSilaboMD> unidades;
    protected List<EstrategiasUnidadMD> estrategias;
    protected List<EvaluacionSilaboMD> evaluaciones;
    protected List<TipoActividadMD> tiposActividad;
    protected List<ReferenciasMD> biblioteca;
    protected List<ReferenciaSilaboMD> referenciasSilabo;
    // Listas para la tabla 
    protected List<EstrategiasAprendizajeMD> todasEstrategias;

    // Titulo de las tablas  
    private static final String[] TITULO_GESTION = {
        "IDL", "Indicador", "Instrumento",
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
    // Para saber si estamos cambiando de unidad y controlar los change listeners 
    private boolean cambioUnidad = false;
    // Bandera para saber en que panel de actividades estamos  
    private String actividadActual = "AD";
    // Total de las gestion 
    private double totalGestion = 0;
    // Actividad seleccionada al dar click en editar o eliminar 
    private EvaluacionSilaboMD evaluacionSelec;

    public FRMSilaboCTR(
            VtnPrincipalCTR ctrPrin,
            SilaboMD silabo
    ) {
        super(ctrPrin);
        this.silabo = silabo;
        FRM_ACCIONES = new NEWFrmAcciones(ctrPrin.getVtnPrin(), false);
        FRM_ACCIONES.setLocationRelativeTo(FRM_GESTION);
    }

    public void nuevo(int num) {
        crearSilaboNuevo(num);
        iniciarSilabo();
        iniciarVentana();
    }

    public void referenciado(boolean conEvaluaciones) {
        NEWPeriodoLectivoBD PBD = NEWPeriodoLectivoBD.single();
        silabo.setPeriodo(PBD.getUltimoPorPeriodo(silabo.getPeriodo().getID()));
        // Buscamos sin el id de las unidades
        unidades = USBD.getBySilaboParaReferencia(silabo.getID());
        if (conEvaluaciones) {
            System.out.println("EVALUACIONES..  ");
            evaluaciones = EVBD.getBySilaboReferencia(silabo.getID());
        }

        cargarDatosSilabo();
        iniciarSilabo();
        iniciarVentana();
    }

    public void editar() {
        cargarDatosSilabo();
        // Buscamos con el id de unidades
        unidades = USBD.getBySilabo(silabo.getID());
        evaluaciones = EVBD.getBySilabo(silabo.getID());
        iniciarVentana();
    }
    
    private void estiloTablas() {
        TblEstilo.columnaMedida(FRM_GESTION.getTblEstrategias(), 0, 20);
        TblEstilo.ColumnaCentrar(FRM_GESTION.getTblEstrategias(), 0);
    }

    /**
     * Comprobamos si existe o no un silabo anterior para iniciar el formulario
     */
    private void iniciarVentana() {
        estiloTablas();
        // Cargamos todas las estrategias  
        todasEstrategias = EABD.getAll();
        // Siempre iniciamos el estado del boton en falso para que 
        // no pueda guardar hasta realizar cambios
        // estadoBtnGuardar(false);
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
        // Iniciamos todas las acciones 
        iniciarAccionesActividades();
        iniciarEventoSPNHoras();
        iniciarEventosFecha();
        iniciarEventosFormulario();
        // Con esto detectamos en que panel se encuentra actualmente
        detectarPanel();
    }

    /**
     * Eventos de todos los txt de nuestro formulario, al escribir reescribimos
     * igual en el modelo
     */
    private void iniciarEventosFormulario() {
        FRM_GESTION.getTxtTitulo().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                unidadSelec.setTituloUnidad(
                        UFRMSCTR.getTextFromTxt(FRM_GESTION.getTxtTitulo())
                );
            }
        });
        FRM_GESTION.getTxrContenidos().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                unidadSelec.setContenidosUnidad(
                        UFRMSCTR.getTextFromTxa(FRM_GESTION.getTxrContenidos())
                );
            }
        });
        FRM_GESTION.getTxrObjetivos().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                unidadSelec.setObjetivoEspecificoUnidad(
                        UFRMSCTR.getTextFromTxa(FRM_GESTION.getTxrObjetivos())
                );
            }
        });
        FRM_GESTION.getTxrResultados().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                unidadSelec.setResultadosAprendizajeUnidad(
                        UFRMSCTR.getTextFromTxa(FRM_GESTION.getTxrResultados())
                );
            }
        });
    }

    /**
     * Eventos de todas las acciones de las actividades
     */
    private void iniciarAccionesActividades() {
        FRM_GESTION.getBtnAgregar().addActionListener(e -> nuevaActividad());
        FRM_GESTION.getBtnEditar().addActionListener(e -> editarActividad());
        FRM_GESTION.getBtnQuitar().addActionListener(e -> eliminarActividad());
        FRM_ACCIONES.getBtnGuardar().addActionListener(e -> guardarEvaluacion());
    }

    private void guardarEvaluacion() {
        if (frmEvaluacionValido()) {
            boolean guardando = false;
            // Si no tenemos seleccionada ninguna evaluacion creamos una nueva 
            if (evaluacionSelec == null) {
                guardando = true;
                evaluacionSelec = new EvaluacionSilaboMD();
            }
            evaluacionSelec.setIndicador(FRM_ACCIONES.getTxtIndicador().getText());
            evaluacionSelec.setInstrumento(FRM_ACCIONES.getTxtInstrumento().getText());
            evaluacionSelec.setValoracion(Double.parseDouble(
                    FRM_ACCIONES.getSpnValoracion().getValue().toString()
            ));
            evaluacionSelec.setFechaEnvio(
                    UFRMSCTR.getFechaJDC(FRM_ACCIONES.getDchFechaEnvio())
            );
            evaluacionSelec.setFechaPresentacion(
                    UFRMSCTR.getFechaJDC(FRM_ACCIONES.getDchFechaPresentacion())
            );

            if (guardando) {
                evaluacionSelec.getIdUnidad().setNumeroUnidad(unidadSelec.getNumeroUnidad());
                evaluacionSelec.getIdTipoActividad().setIdTipoActividad(getIdTipoActividad());
                evaluaciones.add(evaluacionSelec);
            }

            cargarEvaluaciones();
            FRM_ACCIONES.setVisible(false);
            recetearFrmActividad();
        }
    }

    private boolean frmEvaluacionValido() {
        boolean valido = true;
        if (FRM_ACCIONES.getTxtIndicador().getText().equals("")
                || FRM_ACCIONES.getTxtInstrumento().getText().equals("")
                || FRM_ACCIONES.getSpnValoracion().getValue() == null
                || FRM_ACCIONES.getDchFechaEnvio().getDate() == null
                || FRM_ACCIONES.getDchFechaPresentacion().getDate() == null) {
            valido = false;
            UFRMSCTR.errorFrmEvaluacion("Debe ingresar todos los campos");
        }

        if (valido) {
            String valor = FRM_ACCIONES.getSpnValoracion().getValue().toString();
            valido = Validar.esNumerosDecimales(valor);
            System.out.println("Valor: " + valor);
            if (!valido) {
                UFRMSCTR.errorFrmEvaluacion("La valoracion de la actividad es incorrecta.");
            }
        }

        if (valido) {
            LocalDate fe = UFRMSCTR.getFechaJDC(FRM_ACCIONES.getDchFechaEnvio());
            LocalDate fp = UFRMSCTR.getFechaJDC(FRM_ACCIONES.getDchFechaPresentacion());
            if (fp.isBefore(fe)) {
                UFRMSCTR.errorFrmEvaluacion("La fecha de presentación debe ser despues "
                        + "de la de envio.");
                valido = false;
            }
        }
        return valido;
    }

    private void nuevaActividad() {
        if (totalGestion < 60) {
            recetearFrmActividad();
            mostrarFrmActividades("Nuevo " + actividadActual);
        } else {
            JOptionPane.showMessageDialog(
                    FRM_ACCIONES,
                    "Ya cumple las 60 puntos necesarios en sus actividades.",
                    "Información Atividades",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void editarActividad() {
        String code = getActividadSeleccionada();
        if (!"".equals(code)) {
            seleccionPorIdLocal(code);
            if (evaluacionSelec != null) {
                System.out.println("Evaluacion: " + evaluacionSelec.toString());
                setearCamposActividad();
            }
        } else {
            JOptionPane.showMessageDialog(
                    FRM_ACCIONES,
                    "No selecciono ninguna actividad "
                    + actividadActual + " para editarla, "
                    + "selecciona de la tabla correspondiente",
                    "Editar Atividades",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void setearCamposActividad() {
        FRM_ACCIONES.getTxtIndicador().setText(evaluacionSelec.getIndicador());
        FRM_ACCIONES.getTxtInstrumento().setText(evaluacionSelec.getInstrumento());
        FRM_ACCIONES.getSpnValoracion().setValue(evaluacionSelec.getValoracion());
        FRM_ACCIONES.getDchFechaEnvio().setDate(
                Date.from(evaluacionSelec
                        .getFechaEnvio()
                        .atStartOfDay(ZoneId.systemDefault()).toInstant())
        );
        FRM_ACCIONES.getDchFechaPresentacion().setDate(
                Date.from(evaluacionSelec
                        .getFechaPresentacion()
                        .atStartOfDay(ZoneId.systemDefault()).toInstant())
        );
        mostrarFrmActividades("Nuevo " + actividadActual + " ID: " + evaluacionSelec.getIdLocal());
    }

    private void eliminarActividad() {
        String code = getActividadSeleccionada();
        if (!"".equals(code)) {
            int r = JOptionPane.showConfirmDialog(FRM_ACCIONES, "Esta seguro de quitar la actividad.");

            if (r == JOptionPane.YES_OPTION) {
                System.out.println("ELIMINADO BRO");
                System.out.println("CODE: " + code);
                seleccionPorIdLocal(code);
                if (evaluacionSelec != null) {
                    System.out.println("Evaluacion: " + evaluacionSelec.toString());
                    evaluaciones.remove(evaluacionSelec);
                    cargarEvaluaciones();
                }
            }

        } else {
            JOptionPane.showMessageDialog(
                    FRM_ACCIONES,
                    "No selecciono ninguna actividad para quitarla",
                    "Eliminar Atividades",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private String getActividadSeleccionada() {
        String code;
        switch (actividadActual) {
            case "AD":
                code = UFRMSCTR.getIdLocalActividad(FRM_GESTION.getTblAsistidaDocente());
                break;
            case "AC":
                code = UFRMSCTR.getIdLocalActividad(FRM_GESTION.getTblAprendizajeColaborativo());
                break;
            case "GP":
                code = UFRMSCTR.getIdLocalActividad(FRM_GESTION.getTblPractica());
                break;
            case "GA":
                code = UFRMSCTR.getIdLocalActividad(FRM_GESTION.getTblAutonoma());
                break;
            default:
                code = "";
                break;
        }
        return code;
    }

    private void recetearFrmActividad() {
        FRM_ACCIONES.getTxtIndicador().setText("");
        FRM_ACCIONES.getTxtInstrumento().setText("");
        FRM_ACCIONES.getSpnValoracion().setValue(0.1);
        FRM_ACCIONES.getDchFechaEnvio().setDate(null);
        FRM_ACCIONES.getDchFechaPresentacion().setDate(null);
    }

    private void mostrarFrmActividades(String titulo) {
        FRM_ACCIONES.getLblAccionActividades().setText(titulo);
        FRM_ACCIONES.setVisible(true);
    }

    private void seleccionPorIdLocal(String code) {
        for (EvaluacionSilaboMD e : evaluaciones) {
            if (e.getIdLocal() == Integer.parseInt(code)) {
                evaluacionSelec = e;
                break;
            } else {
                evaluacionSelec = null;
            }
        }
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
        if (evaluaciones == null) {
            evaluaciones = new ArrayList<>();
        }
        biblioteca = new ArrayList<>();
        tiposActividad = TABD.getAll();
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
        estrategias = EUBD.getBySilabo(silabo.getID());
        referenciasSilabo = RSBD.getBySilabo(silabo.getID());
        //evaluaciones = ;
    }

    private void estadoBtnGuardar(boolean estado) {
        FRM_GESTION.getBtnGuardar().setEnabled(estado);
    }

    /**
     * Detectamos el panel en el que estamos actualmente
     */
    private void detectarPanel() {
        FRM_GESTION.getTbpEvaluacion().addChangeListener(e -> {
            int selec = FRM_GESTION.getTbpEvaluacion().getSelectedIndex();
            switch (selec) {
                case 0:
                    actividadActual = "AD";
                    break;
                case 1:
                    actividadActual = "AC";
                    break;
                case 2:
                    actividadActual = "GP";
                    break;
                case 3:
                    actividadActual = "GA";
                    break;
                default:
                    actividadActual = "";
                    break;
            }
        });
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
        // Bandera para saber que estamos cambiando de unidad 
        cambioUnidad = true;
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
        // cargarEstrategias();
        // Debemos revisar  lo de estrategias 
        // Actualizamos las evaluaciones 
        cargarEvaluaciones();
        // Al terminar de cambiar de unidad 
        cambioUnidad = false;
    }

    private void cargarEstrategias() {
        mdTblES.setRowCount(0);
        todasEstrategias.forEach(e -> {
            mdTblES.addRow(new Object[]{
                "|",
                e.getDescripcionEstrategia()
            });
        });
    }

    /**
     * Cargamos las evaluaciones
     */
    private void cargarEvaluaciones() {
        // Seteamos el evaluacion seleccionada en nada cada vez que recargamos esta ventana 
        evaluacionSelec = null;
        mdTblAC.setRowCount(0);
        mdTblAD.setRowCount(0);
        mdTblES.setRowCount(0);
        mdTblGA.setRowCount(0);
        mdTblGP.setRowCount(0);
        // Actualizamos el valor total de la evaluaciones  
        totalGestion = 0;

        evaluaciones.forEach(e -> {
            // Sumamos la valoracion de nuestras evaluaciones
            totalGestion += e.getValoracion();

            if (e.getIdUnidad().getNumeroUnidad() == unidadSelec.getNumeroUnidad()) {
                Object[] row = {
                    e.getIdLocal(),
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

            // Mostramos solo las estrategias 
        });

        estrategias.forEach(e -> {
            if (e.getIdUnidad().getNumeroUnidad() == unidadSelec.getNumeroUnidad()) {
                mdTblES.addRow(new Object[]{
                    "|",
                    e.getIdEstrategia().getDescripcionEstrategia()
                });
            }
        });
        // Seteamos en el lbl  
        FRM_GESTION.getLblAcumuladoGestion().setText(totalGestion + "/60.0");
    }

    /**
     * Obtenemos el tipo de actividad que se esta guardando.
     *
     * @return
     */
    private int getIdTipoActividad() {
        int id;
        switch (actividadActual) {
            case "AD":
                id = 1;
                break;
            case "AC":
                id = 2;
                break;
            case "GP":
                id = 3;
                break;
            case "GA":
                id = 4;
                break;
            default:
                id = 0;
                break;
        }
        return id;
    }

    /**
     * Validamos las fechas que ingresamos
     */
    private void iniciarEventosFecha() {
        FRM_GESTION.getDchFechaInicio().addPropertyChangeListener(e -> {
            if (!cambioUnidad) {
                validarFechaInicio();
            }
        });
        FRM_GESTION.getDchFechaFin().addPropertyChangeListener(e -> {
            if (!cambioUnidad) {
                validarFechaFin();
            }
        });
    }

    private void validarFechaInicio() {

        LocalDate fecha = UFRMSCTR.getFechaJDC(FRM_GESTION.getDchFechaInicio());
        if (fecha != null) {
            if (unidadSelec.getFechaFinUnidad() == null) {
                unidadSelec.setFechaInicioUnidad(fecha);
            } else {
                if ((unidadSelec.getFechaFinUnidad().isAfter(fecha)
                        || fecha.equals(unidadSelec.getFechaInicioUnidad()))
                        && fecha.isAfter(silabo.getPeriodo().getFechaInicio())) {
                    unidadSelec.setFechaInicioUnidad(fecha);
                } else {
                    UFRMSCTR.errorFecha("Debe indicar una fecha de inicio correcta.");
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
        LocalDate fecha = UFRMSCTR.getFechaJDC(FRM_GESTION.getDchFechaFin());
        if (fecha != null) {
            if (unidadSelec.getFechaInicioUnidad() == null) {
                unidadSelec.setFechaFinUnidad(fecha);
            } else {
                if ((unidadSelec.getFechaInicioUnidad()
                        .isBefore(fecha)
                        || fecha.equals(unidadSelec.getFechaInicioUnidad()))
                        && fecha.isBefore(silabo.getPeriodo().getFechaFin())) {
                    unidadSelec.setFechaFinUnidad(fecha);
                } else {
                    UFRMSCTR.errorFecha("Debe indicar una fecha de fin correcta.");
                    FRM_GESTION.getDchFechaFin().setDate(Date
                            .from(unidadSelec.getFechaInicioUnidad()
                                    .atStartOfDay(ZoneId.systemDefault())
                                    .toInstant().plus(1, ChronoUnit.DAYS)
                            )
                    );
                }
            }
        }
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
        double h = UFRMSCTR.getHoraSPN(FRM_GESTION.getSpnHautonomas());
        if (h >= 0 && h != unidadSelec.getHorasAutonomoUnidad()) {
            double nh = h + numHA - unidadSelec.getHorasAutonomoUnidad();
            if (nh <= silabo.getMateria().getHorasAutoEstudio()) {
                numHA = nh;
                unidadSelec.setHorasAutonomoUnidad(h);
                reescribirHoras();
            } else {
                FRM_GESTION.getSpnHautonomas().setValue(unidadSelec.getHorasAutonomoUnidad());
                UFRMSCTR.errorHoras("Las horas autonomas superan la hora total indicada en materia.");
            }
        }
    }

    private void validarHD() {
        double h = UFRMSCTR.getHoraSPN(FRM_GESTION.getSpnHdocencia());
        if (h >= 0 && h != unidadSelec.getHorasDocenciaUnidad()) {
            double nh = h + numHD - unidadSelec.getHorasDocenciaUnidad();
            if (nh <= silabo.getMateria().getHorasDocencia()) {
                numHD = nh;
                unidadSelec.setHorasDocenciaUnidad(h);
                reescribirHoras();
            } else {
                FRM_GESTION.getSpnHdocencia().setValue(unidadSelec.getHorasDocenciaUnidad());
                UFRMSCTR.errorHoras("Las horas de docencia superan la hora total indicada en materia.");
            }
        }
    }

    private void validarHP() {
        double h = UFRMSCTR.getHoraSPN(FRM_GESTION.getSpnHpracticas());
        if (h >= 0 && h != unidadSelec.getHorasPracticaUnidad()) {
            double nh = h + numHP - unidadSelec.getHorasPracticaUnidad();
            if (nh <= silabo.getMateria().getHorasPracticas()) {
                numHP = nh;
                unidadSelec.setHorasPracticaUnidad(h);
                reescribirHoras();
            } else {
                FRM_GESTION.getSpnHpracticas().setValue(unidadSelec.getHorasPracticaUnidad());
                UFRMSCTR.errorHoras("Las horas practicas superan la hora total indicada en materia.");
            }
        }
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
