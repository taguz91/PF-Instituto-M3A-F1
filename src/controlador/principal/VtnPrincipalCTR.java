package controlador.principal;

import controlador.Libraries.Effects;
import controlador.accesos.VtnAccesosCTR;
import controlador.alumno.FrmAlumnoCarreraCTR;
import controlador.carrera.FrmCarreraCTR;
import controlador.carrera.VtnCarreraCTR;
import controlador.curso.FrmCursoCTR;
import controlador.alumno.VtnAlumnoCursoCTR;
import controlador.curso.VtnCursoCTR;
import controlador.alumno.FrmAlumnoCursoCTR;
import controlador.alumno.VtnAlumnoCarreraCTR;
import controlador.alumno.VtnAlumnosRetiradosCTR;
import controlador.alumno.VtnMallaAlumnoCTR;
import controlador.alumno.VtnMatriculaCTR;
import controlador.docente.FrmDocenteMateriaCTR;
import controlador.docente.FrmRolPeriodoCTR;
import controlador.docente.VtnDocenteMateriaCTR;
import controlador.docente.VtnRolPeriodosCTR;
import controlador.estilo.AnimacionCarga;
import controlador.login.LoginCTR;
import controlador.materia.FrmMateriasCTR;
import controlador.materia.VtnMateriaCTR;
import controlador.notas.VtnActivarNotasCTR;
import controlador.notas.VtnNotasCTR;
import controlador.periodoLectivoNotas.IngresoNotas.VtnPeriodoIngresoNotasCTR;
import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import controlador.persona.FrmAlumnoCTR;
import controlador.persona.FrmDocenteCTR;
import controlador.persona.FrmPersonaCTR;
import controlador.persona.VtnAlumnoCTR;
import controlador.persona.VtnDocenteCTR;
import controlador.persona.VtnPersonaCTR;
import controlador.prdlectivo.FrmPrdLectivoCTR;
import controlador.prdlectivo.VtnPrdLectivoCTR;
import controlador.silabo.ControladorCRUD;
import controlador.silabo.ControladorCRUDPlanClase;
import controlador.silabo.ControladorSilaboC;
import controlador.usuario.VtnHistorialUserCTR;
import controlador.usuario.Roles.VtnRolCTR;
import controlador.usuario.VtnSelectRolCTR;
import controlador.usuario.VtnUsuarioCTR;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import modelo.ConectarDB;
import modelo.ConexionBD;
import modelo.ResourceManager;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.alumno.AlumnoCursoBD;
import modelo.periodoIngresoNotas.PeriodoIngresoNotasBD;
import modelo.propiedades.Propiedades;
import modelo.tipoDeNota.IngresoNotasBD;
import modelo.tipoDeNota.TipoDeNotaBD;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
import vista.Login;
import vista.alumno.FrmAlumnoCarrera;
import vista.carrera.FrmCarrera;
import vista.carrera.VtnCarrera;
import vista.alumno.FrmAlumnoCurso;
import vista.alumno.VtnAlumnoCarrera;
import vista.curso.FrmCurso;
import vista.alumno.VtnAlumnoCurso;
import vista.curso.VtnCurso;
import vista.alumno.VtnMallaAlumno;
import vista.docente.FrmDocenteMateria;
import vista.docente.FrmRolesPeriodos;
import vista.docente.VtnDocenteMateria;
import vista.docente.VtnRolesPeriodos;
import vista.materia.VtnMateria;
import vista.notas.VtnActivarNotas;
import vista.notas.VtnNotasAlumnoCurso;
import vista.periodoLectivoNotas.VtnPeriodoIngresoNotas;
import vista.periodoLectivoNotas.VtnTipoNotas;
import vista.persona.FrmAlumno;
import vista.persona.FrmDocente;
import vista.persona.FrmPersona;
import vista.persona.VtnAlumno;
import vista.persona.VtnDocente;
import vista.persona.VtnPersona;
import vista.prdlectivo.FrmPrdLectivo;
import vista.prdlectivo.VtnPrdLectivo;
import vista.principal.VtnBienvenida;
import vista.principal.VtnPrincipal;
import vista.usuario.VtnHistorialUsuarios;
import vista.usuario.VtnRol;
import vista.usuario.VtnUsuario;
import vista.accesos.VtnAccesos;
import vista.alumno.VtnAlumnosRetirados;
import vista.alumno.VtnMatricula;
import vista.materia.FrmMaterias;

/**
 *
 * @author Johnny
 */
public class VtnPrincipalCTR {

    private final VtnPrincipal vtnPrin;
    private final RolBD rolSeleccionado;
    private final UsuarioBD usuario;
    private final ConectarDB conecta;
    private final ConexionBD conexion;
    private final VtnSelectRolCTR ctrSelecRol;
    //Agregamos la animacion 
    public AnimacionCarga carga;
    //Para ver que tanttas ventanas abrimos
    private int numVtns = 0;
    //Icono de la aplicacion  
    private final ImageIcon icono;
    private final Image ista;
    private final VtnBienvenida vtnBienvenida;
    //Para hacer los accesos
    private List<AccesosMD> accesos;
    //Constantes de accesos, para las ventanas y menus
    public final int ACCESOS_ALUMNOS = 0, ACCESOS_PERIODO_LECTIVO = 1,
            ACCESOS_DOCENTES = 2, ACCESOS_PERSONAS = 3, ACCESOS_CARRERAS = 4,
            ACCESOS_CURSOS = 5, ACCESOS_MATERIAS = 6, ACCESOS_ALUMNOS_CARRERA = 7,
            ACCESOS_ALUMNO_CURSO = 8, ACCESOS_CURSO = 9, ACCESOS_DOCENTE_MATERIA = 10;
    //Matriz de permisos

    private final boolean pruebas;

    private final String[][] ACCESOS = {
        {"Alumnos", "Alumnos-Ingresar", "Alumnos-Editar", "Alumnos-Eliminar", "Alumnos-Estado"},
        {"PeriodoLectivo", "PeriodoLectivo-Cerrar-Periodo", "PeriodoLectivo-Editar", "PeriodoLectivo-Ingresar", "PeriodoLectivo-Eliminar", "PeriodoLectivo-Estado"},
        {"Docentes", "Docentes-Ingresar", "Docentes-Editar", "Docentes-Eliminar", "Docentes-Materias-Docente", "Docentes-Estado"},
        {"Personas", "Personas-Ingresar", "Personas-Editar", "Personas-Eliminar", "Personas-Estado"},
        {"Carreras", "Carreras-Eliminar", "Carreras-Editar", "Carreras-Ingresar", "Carreras-Estado"},
        {"Cursos", "Cursos-Editar", "Cursos-Ingresar"},
        {"Materias", "Materias-Informacion", "Materias-Estado"},
        {"AlumnosCarrera", "AlumnosCarrera-Ingresar"},
        {"AlumnosCursoPorPeriodo", "AlumnosCursoPorPeriodo-Ingresar"},
        {"AlumnosCurso", "AlumnosCurso-Ingresar", "AlumnosCurso-Eliminar", "AlumnosCurso-Editar"},
        {"DocenteMateria", "DocenteMateria-Ingresar"}
    };

    /**
     * Construnctor principal del sistema.
     *
     * @param vtnPrin VtnPrincipal: Ventana principal del sistema
     * @param rolSeleccionado RolBD: Rol seleccionado.
     * @param usuario UsuarioBD: Usuario que se conecto.
     * @param conecta ConectarDB: Coneccion a la base de datos G23
     * @param icono ImagenIcon: Icono del sistema.
     * @param ista Imagen: Imagen del icono del sistema.
     * @param ctrSelecRol
     * @param pruebas Para saber si estamos en pruebas
     */
    public VtnPrincipalCTR(VtnPrincipal vtnPrin, RolBD rolSeleccionado,
            UsuarioBD usuario, ConectarDB conecta, ImageIcon icono, Image ista,
            VtnSelectRolCTR ctrSelecRol, boolean pruebas) {
        this.vtnPrin = vtnPrin;
        this.rolSeleccionado = rolSeleccionado;
        this.usuario = usuario;
        this.conecta = conecta;
        this.ctrSelecRol = ctrSelecRol;
        this.vtnBienvenida = new VtnBienvenida();
        this.pruebas = pruebas;
        this.conexion = new ConexionBD(conecta);

        //Inciamos la carga pero la detenemos
        this.carga = new AnimacionCarga(vtnPrin.getBtnEstado(), vtnPrin);
        this.icono = icono;
        this.ista = ista;
        vtnPrin.setIconImage(ista);
        //Iniciamos la pantala en Fullscream 
        vtnPrin.setExtendedState(JFrame.MAXIMIZED_BOTH);
        registroIngreso(vtnPrin);
        //carga.iniciar();
        //Le pasamos el icono  
        vtnPrin.setTitle("Zero | PF M3A");
        vtnPrin.setVisible(true);
        //InitPermisos();
        //InitPermisosTesterYDocente();
    }

    /**
     * Inicia dependencias de la aplicacion. Eventos, Atajos de teclado y
     * animaciones.
     */
    public void iniciar() {
        //Le pasamos dependencias a conectar
        conecta.setVtnPrin(vtnPrin);
        //Agregamos el panel de bienvenida  
        vtnPrin.getDpnlPrincipal().add(vtnBienvenida);
        //Se le pasa el nombre de usuario que inicio sesio  
//        vtnBienvenida.getLblUser().setText(usuario.getUsername());
//        vtnBienvenida.show();
//        //Lo ponemos en pantalla completa
//        try {
//            vtnBienvenida.setMaximum(true);
//        } catch (PropertyVetoException e) {
//            System.out.println("No se maximiso");
//        }
        //Iniciamos los shortcuts 

        iniciarAtajosTeclado();
        agregarEstilos();

        //Acciones de las ventanas de consulta
        //Para el estilo 
        //Para abrir las ventanas consulta
        vtnPrin.getMnCtPersona().addActionListener(e -> abrirVtnPersona());
        vtnPrin.getMnCtAlumno().addActionListener(e -> abrirVtnAlumno());
        vtnPrin.getMnCtCarrera().addActionListener(e -> abrirVtnCarrera());
        vtnPrin.getMnCtCurso().addActionListener(e -> abrirVtnCurso());
        vtnPrin.getMnCtDocente().addActionListener(e -> abrirVtnDocente());
        vtnPrin.getMnCtMateria().addActionListener(e -> abrirVtnMateria());
        vtnPrin.getMnCtPrdLectivo().addActionListener(e -> abrirVtnPrdLectivo());
        vtnPrin.getMnCtInscripcion().addActionListener(e -> abrirVtnAlumnoCarrera());
        vtnPrin.getMnCtMallaAlumno().addActionListener(e -> abrirVtnMallaAlumnos());
        vtnPrin.getMnCtDocenteMateria().addActionListener(e -> abrirVtnDocenteMateria());
        vtnPrin.getMnCtMatricula().addActionListener(e -> abrirVtnMatricula());
        vtnPrin.getMnCtListaAlumnos().addActionListener(e -> abrirVtnAlumnoCurso());
        vtnPrin.getMnCtHistorialUsers().addActionListener(e -> abrirVtnHistorialUser());
        vtnPrin.getMnCtRolesPeriodo().addActionListener(e -> abrirVtnRolesPeriodos());
        vtnPrin.getMnCtAlmnRetirados().addActionListener(e -> abrirVtnAlmnRetirados());
        vtnPrin.getBtnMateria().addActionListener(e -> abrirFrmMateria());
        vtnPrin.getMnCtAccesos().addActionListener(e -> abrirVtnAccesos());

        //Para abrir los formularios 
        vtnPrin.getBtnPersona().addActionListener(e -> abrirFrmPersona());

        vtnPrin.getBtnAlumno().addActionListener(e -> abrirFrmAlumno());
        vtnPrin.getBtnCarrera().addActionListener(e -> abrirFrmCarrera());
        vtnPrin.getBtnCurso().addActionListener(e -> abrirFrmCurso());
        vtnPrin.getBtnDocente().addActionListener(e -> abrirFrmDocente());
        vtnPrin.getBtnPrdLectivo().addActionListener(e -> abrirFrmPrdLectivo());
        vtnPrin.getBtnInscripcion().addActionListener(e -> abrirFrmInscripcion());
        vtnPrin.getBtnMatricula().addActionListener(e -> abrirFrmMatricula());
        vtnPrin.getBtnDocenteMateria().addActionListener(e -> abrirFrmDocenteMateria());
        vtnPrin.getBtnIngresarRol().addActionListener(e -> abrirFrmRolesPeriodos());
        //Para los menus  ingresar
        vtnPrin.getMnIgAlumno().addActionListener(e -> abrirFrmAlumno());
        vtnPrin.getMnIgCarrera().addActionListener(e -> abrirFrmCarrera());
        vtnPrin.getMnIgCurso().addActionListener(e -> abrirFrmCurso());
        vtnPrin.getMnIgDocente().addActionListener(e -> abrirFrmDocente());
        vtnPrin.getMnIgPersona().addActionListener(e -> abrirFrmPersona());
        vtnPrin.getMnIgPrdLectivo().addActionListener(e -> abrirFrmPrdLectivo());
        vtnPrin.getMnIgInscripcion().addActionListener(e -> abrirFrmInscripcion());
        vtnPrin.getMnIgMatricula().addActionListener(e -> abrirFrmMatricula());
        vtnPrin.getMnIgDocenteMt().addActionListener(e -> abrirFrmDocenteMateria());
        vtnPrin.getMnIgRolesPeriodo().addActionListener(e -> abrirFrmRolesPeriodos());

        //menus grupo 16
        vtnPrin.getMnCtUsuarios().addActionListener(e -> mnCtUsuarios(e));
        vtnPrin.getMnCtRoles().addActionListener(e -> mnCtRoles(e));
        vtnPrin.getBtnCerrarSesion().addActionListener(e -> btnCerrarSesion(e));
        vtnPrin.getMnCtNotas().addActionListener(e -> abrirVtnNotasAlumnoCurso(e));
        vtnPrin.getMnCtTipoNotas().addActionListener(e -> btnTipoNotas(e));
        vtnPrin.getMnCtPrdIngrNotas().addActionListener(e -> btnPrdIngrNotas(e));
        vtnPrin.getMnCtActivarNotas().addActionListener(e -> btnActivarNotas(e));

        vtnPrin.getBtnAyuda().addActionListener(e -> abrirVtnAyuda());

        vtnPrin.getMnCtSilabos().addActionListener(al -> controladorCRUD());
        vtnPrin.getMnCtPlandeClase().addActionListener(a1 -> controladorCONFIGURACION_PLAN_DE_CLASES());
        vtnPrin.getBtnConsultarSilabo().addActionListener(al -> controladorCRUD());
        vtnPrin.getBtnIngresarSilabo().addActionListener(al -> controladorIngreso());

        vtnPrin.getBtnCambiarRol().addActionListener(e -> btnCambiarRol(e));

        carga.start();

        //Esto es para la consola 
        vtnPrin.getBtnConsola().addActionListener(e -> iniciarConsola());

        /*
            SET DIRECCION IP
         */
        String IP = Propiedades.getPropertie("ip");
        String database = Propiedades.getPropertie("database");

        vtnPrin.getLblIP().setText(IP + "/" + database);

    }

    private void iniciandoBtns() {
        accesos = AccesosBD.SelectWhereACCESOROLidRol(rolSeleccionado.getId());
        accesos.forEach(a -> {

            if (a.getNombre().equalsIgnoreCase(ACCESOS[ACCESOS_ALUMNOS][1])) {
                vtnPrin.getBtnAlumno().setEnabled(true);
                vtnPrin.getMnIgAlumno().setEnabled(true);
            } else {
                vtnPrin.getBtnAlumno().setEnabled(false);
                vtnPrin.getMnIgAlumno().setEnabled(false);
            }

            if (a.getNombre().equalsIgnoreCase(ACCESOS[ACCESOS_PERIODO_LECTIVO][3])) {
                vtnPrin.getBtnPrdLectivo().setEnabled(true);
                vtnPrin.getMnIgPrdLectivo().setEnabled(true);
            } else {
                vtnPrin.getBtnPrdLectivo().setEnabled(false);
                vtnPrin.getMnIgPrdLectivo().setEnabled(false);
            }

            if (a.getNombre().equalsIgnoreCase(ACCESOS[ACCESOS_ALUMNOS_CARRERA][1])) {
                vtnPrin.getBtnInscripcion().setEnabled(true);
                vtnPrin.getMnIgInscripcion().setEnabled(true);
            } else {
                vtnPrin.getBtnInscripcion().setEnabled(false);
                vtnPrin.getMnIgInscripcion().setEnabled(false);
            }

        });
    }

    public void abrirVtnPersona() {
        VtnPersona vtnPersona = new VtnPersona();
        eventoInternal(vtnPersona);
        if (numVtns < 5) {
            carga.iniciar();
            VtnPersonaCTR ctrVtnPersona = new VtnPersonaCTR(vtnPrin, vtnPersona, conecta, this, rolSeleccionado);
            ctrVtnPersona.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnDocente() {
        VtnDocente vtnDocente = new VtnDocente();
        eventoInternal(vtnDocente);
        if (numVtns < 6) {
            VtnDocenteCTR ctrVtnDocente = new VtnDocenteCTR(vtnPrin, vtnDocente, conecta, this, rolSeleccionado);
            ctrVtnDocente.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnAlumno() {
        VtnAlumno vtnAlumno = new VtnAlumno();
        eventoInternal(vtnAlumno);
        if (numVtns < 5) {
            VtnAlumnoCTR ctrVtnAlumno = new VtnAlumnoCTR(vtnPrin, vtnAlumno, conecta, this, rolSeleccionado);
            ctrVtnAlumno.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnCarrera() {
        VtnCarrera vtnCarrera = new VtnCarrera();
        eventoInternal(vtnCarrera);
        if (numVtns < 5) {
            VtnCarreraCTR ctrVtnCarrera = new VtnCarreraCTR(vtnPrin, vtnCarrera, conecta, this, rolSeleccionado);
            ctrVtnCarrera.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnCurso() {
        VtnCurso vtnCurso = new VtnCurso();
        eventoInternal(vtnCurso);
        if (numVtns < 5) {
            carga.iniciar();
            VtnCursoCTR ctrVtnCurso = new VtnCursoCTR(vtnPrin, vtnCurso, conecta, this, rolSeleccionado);
            ctrVtnCurso.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnPrdLectivo() {
        VtnPrdLectivo vtnPrdLectivo = new VtnPrdLectivo();
        eventoInternal(vtnPrdLectivo);
        if (numVtns < 5) {
            VtnPrdLectivoCTR ctrVtnPrdLectivo = new VtnPrdLectivoCTR(vtnPrin, vtnPrdLectivo, conecta, this, rolSeleccionado);
            ctrVtnPrdLectivo.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnAlumnoCurso() {
        VtnAlumnoCurso vtnAlmnCurso = new VtnAlumnoCurso();
        eventoInternal(vtnAlmnCurso);
        if (numVtns < 5) {
            VtnAlumnoCursoCTR ctrVtnAlmnCurso = new VtnAlumnoCursoCTR(vtnPrin, vtnAlmnCurso, conecta, this, rolSeleccionado);
            ctrVtnAlmnCurso.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnMatricula() {
        VtnMatricula vtnMatri = new VtnMatricula();
        eventoInternal(vtnMatri);
        if (numVtns < 5) {
            VtnMatriculaCTR ctr = new VtnMatriculaCTR(conecta, vtnPrin, this, vtnMatri);
            ctr.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnMateria() {
        VtnMateria vtnMateria = new VtnMateria();
        eventoInternal(vtnMateria);
        if (numVtns < 5) {
            VtnMateriaCTR ctrVtnMateria = new VtnMateriaCTR(vtnPrin, vtnMateria, conecta, this, rolSeleccionado);
            ctrVtnMateria.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnAlumnoCarrera() {
        VtnAlumnoCarrera vtnAlmnCarrera = new VtnAlumnoCarrera();
        eventoInternal(vtnAlmnCarrera);
        if (numVtns < 5) {
            carga.iniciar();
            VtnAlumnoCarreraCTR ctrAlmn = new VtnAlumnoCarreraCTR(vtnPrin, vtnAlmnCarrera, conecta, rolSeleccionado, this);
            ctrAlmn.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnMallaAlumnos() {
        VtnMallaAlumno vtnMallaAlm = new VtnMallaAlumno();

        VtnMallaAlumnoCTR ctrMalla = new VtnMallaAlumnoCTR(vtnPrin, vtnMallaAlm, conecta, this, rolSeleccionado);
        ctrMalla.iniciar();
    }

    public void abrirVtnDocenteMateria() {
        VtnDocenteMateria vtn = new VtnDocenteMateria();
        eventoInternal(vtn);
        if (numVtns < 5) {
            VtnDocenteMateriaCTR ctrVtn = new VtnDocenteMateriaCTR(vtnPrin, vtn, conecta, this, rolSeleccionado);
            ctrVtn.iniciar();
        }
    }

    public void abrirVtnAlmnRetirados() {
        VtnAlumnosRetirados vtn = new VtnAlumnosRetirados();
        eventoInternal(vtn);
        if (numVtns < 5) {
            VtnAlumnosRetiradosCTR ctr = new VtnAlumnosRetiradosCTR(conecta, vtnPrin, this, vtn);
            ctr.iniciar();
        }
    }

    public void abrirVtnAyuda() {
        JDAyudaCTR ctrAyuda = new JDAyudaCTR(vtnPrin, this);
        ctrAyuda.iniciar();
    }

    public void abrirVtnHistorialUser() {
        VtnHistorialUsuarios vtn = new VtnHistorialUsuarios();
        eventoInternal(vtn);
        if (numVtns < 5) {
            VtnHistorialUserCTR ctr = new VtnHistorialUserCTR(conecta, vtnPrin, this);
            ctr.iniciar();
        }
    }

    //Para abrir todos los formularios
    public void abrirFrmPersona() {
        FrmPersona frmPersona = new FrmPersona();
        eventoInternal(frmPersona);
        if (numVtns < 5) {
            FrmPersonaCTR ctrFrmPersona = new FrmPersonaCTR(vtnPrin, frmPersona, conecta, this);
            ctrFrmPersona.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirFrmRolesPeriodos() {
        FrmRolesPeriodos frmRolPeriodo = new FrmRolesPeriodos();
        eventoInternal(frmRolPeriodo);
        if (numVtns < 5) {
            FrmRolPeriodoCTR rol = new FrmRolPeriodoCTR(vtnPrin, frmRolPeriodo, conecta, this);
            rol.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnRolesPeriodos() {
        VtnRolesPeriodos vtnRolprd = new VtnRolesPeriodos();
        eventoInternal(vtnRolprd);
        if (numVtns < 5) {
            VtnRolPeriodosCTR vtnRol = new VtnRolPeriodosCTR(vtnPrin, vtnRolprd, conecta, this);
            vtnRol.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirFrmDocente() {
        FrmDocente frmDocente = new FrmDocente();
        eventoInternal(frmDocente);
        if (numVtns < 5) {
            frmDocente.getBtnRegistrarPersona().setVisible(false);
            FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(vtnPrin, frmDocente, conecta, this);
            ctrFrmDocente.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmAlumno() {
        FrmAlumno frmAlumno = new FrmAlumno();
        eventoInternal(frmAlumno);
        if (numVtns < 5) {
            FrmAlumnoCTR ctrFrmAlumno = new FrmAlumnoCTR(vtnPrin, frmAlumno, conecta, this, rolSeleccionado);
            ctrFrmAlumno.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmMateria() {
        FrmMaterias frmMaterias = new FrmMaterias();
        eventoInternal(frmMaterias);
        if (numVtns < 5) {
            FrmMateriasCTR ctrFrmMaterias = new FrmMateriasCTR(vtnPrin, frmMaterias, conecta, this);
            ctrFrmMaterias.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirFrmCarrera() {
        FrmCarrera frmCarrera = new FrmCarrera();
        eventoInternal(frmCarrera);
        if (numVtns < 5) {
            FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(vtnPrin, frmCarrera, conecta, this);
            ctrFrmCarrera.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmCurso() {
        FrmCurso frmCurso = new FrmCurso();
        eventoInternal(frmCurso);
        if (numVtns < 5) {
            FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(vtnPrin, frmCurso, conecta, this);
            ctrFrmCurso.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirFrmPrdLectivo() {
        FrmPrdLectivo frmPrdLectivo = new FrmPrdLectivo();
        eventoInternal(frmPrdLectivo);
        if (numVtns < 5) {
            FrmPrdLectivoCTR ctrFrmPrdLectivo = new FrmPrdLectivoCTR(vtnPrin, frmPrdLectivo, conecta, this);
            ctrFrmPrdLectivo.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmInscripcion() {
        FrmAlumnoCarrera frmMatricula = new FrmAlumnoCarrera();
        eventoInternal(frmMatricula);
        if (numVtns < 5) {
            FrmAlumnoCarreraCTR ctrFrmAlumn = new FrmAlumnoCarreraCTR(vtnPrin, frmMatricula, conecta, this);
            ctrFrmAlumn.iniciar();
        }
    }

    public void abrirFrmMatricula() {
        FrmAlumnoCurso frmAlmCurso = new FrmAlumnoCurso();
        eventoInternal(frmAlmCurso);
        if (numVtns < 5) {
            FrmAlumnoCursoCTR ctrFrmMatri = new FrmAlumnoCursoCTR(vtnPrin, frmAlmCurso, conecta, this);
            ctrFrmMatri.iniciar();
        }
    }

    public void abrirFrmDocenteMateria() {
        FrmDocenteMateria frm = new FrmDocenteMateria();
        eventoInternal(frm);
        if (numVtns < 5) {
            FrmDocenteMateriaCTR ctrFrm = new FrmDocenteMateriaCTR(vtnPrin, frm, conecta, this);
            ctrFrm.iniciar();
        }
    }

    public void abrirVtnAccesos() {
        VtnAccesos vtnAcceso = new VtnAccesos();
        eventoInternal(vtnAcceso);
        if (numVtns < 5) {
            VtnAccesosCTR ctrVtnAcceso = new VtnAccesosCTR(vtnPrin, vtnAcceso, conecta, this);
            ctrVtnAcceso.Init();
        } else {
            errorNumVentanas();
        }

    }

    private void controladorCRUD() {

        ControladorCRUD c = new ControladorCRUD(usuario, vtnPrin, conexion);

        c.iniciarControlador();

    }

    private void controladorCONFIGURACION_PLAN_DE_CLASES() {
        ControladorCRUDPlanClase cP = new ControladorCRUDPlanClase(usuario, conexion, vtnPrin);
        cP.iniciaControlador();

    }

    private void controladorIngreso() {

        ControladorSilaboC c = new ControladorSilaboC(vtnPrin, usuario, new ConexionBD(conecta));

        c.iniciarControlador();

    }

    private void abrirVtnNotasAlumnoCurso(ActionEvent e) {
        VtnNotasAlumnoCurso vtn = new VtnNotasAlumnoCurso();
        eventoInternal(vtn);
        if (numVtns < 5) {
            VtnNotasCTR vtnCtr = new VtnNotasCTR(vtnPrin, vtn, usuario, rolSeleccionado);
            vtnCtr.Init();
        } else {
            errorNumVentanas();
        }
    }
    private ArrayList<String> estilos;

    /**
     * Iniciamos todos los tipos de estilo del sistema
     */
    private void agregarEstilos() {
        estilos = new ArrayList();
        ButtonGroup btnsEstilo = new ButtonGroup();
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            JRadioButtonMenuItem mi = new JRadioButtonMenuItem(info.getName());
            mi.addActionListener(e -> estiloVtn(info.getName()));
            estilos.add(info.getName());
            vtnPrin.getMnEstilo().add(mi);
            btnsEstilo.add(mi);
        }

    }

    /**
     * Se ejecuta al seleccionar un estilo en el menu de opciones. Obtendra el
     * nombre del estilo elejido y actualizara la ventana, para mostrarlo.
     */
    private void estiloVtn(String estilo) {
        System.out.println(estilo);
        try {
            VtnPrincipal.setDefaultLookAndFeelDecorated(true);
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (estilo.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            //Actualizamos la ventana para que cargue el nuevo look an field
            SwingUtilities.updateComponentTreeUI(vtnPrin);
            //Ocultamos el borde de internal de bienvenida
//            ((javax.swing.plaf.basic.BasicInternalFrameUI) vtnBienvenida.getUI()).setNorthPane(null);
//            vtnBienvenida.setBorder(null);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("No se pudo cambiar el estilo de la ventana");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Al crear una ventana, se la inicia con este envento, para contronlar de
     * que no existan mas de 5 ventanas.
     *
     * @param internal JInternalFrame
     * @see errorNumVentanas()
     */
    public void eventoInternal(JInternalFrame internal) {
        Effects.centerFrame(internal, vtnPrin.getDpnlPrincipal());
        setIconJIFrame(internal);
        internal.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                numVtns++;
                if (numVtns > 5) {
                    errorNumVentanas();
                    internal.dispose();
                }
            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                numVtns--;
            }

        });
    }

    //Se desactivara la ventana al abrir un jdialog
    public void eventoJDCerrar(JDialog jd) {
        jd.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                vtnPrin.setEnabled(true);
            }

            @Override
            public void windowOpened(WindowEvent e) {
                vtnPrin.setEnabled(false);
            }
        });
    }

    /**
     * Indicamos que cerramos la ventana para que merme el numero de ventanas
     * abiertas.
     */
    public void cerradoJIF() {
        numVtns--;
        if (numVtns < 0) {
            numVtns = 0;
        }
    }

    /**
     * Se envia el nombre de la ventana que se abre para que se escriba en la
     * barra de estado de la aplicacion.
     *
     * @param vtn String
     */
    public void estadoCargaVtn(String vtn) {
        vtnPrin.getLblEstado().setText("Inciando la ventana de " + vtn + ". Por favor espere la información se cargara en breve.");
    }

    /**
     * Al terminar de cargar la ventana, se envia el nombre de la ventana que
     * cargo para mostrarla en la barra de estado.
     *
     * @param vtn String
     */
    public void estadoCargaVtnFin(String vtn) {
        vtnPrin.getLblEstado().setText("Termino de iniciarse la ventana de " + vtn + ", cualquier error reportarlo a M3A.");
    }

    /**
     * Al abrir un formulario se envia el nombre del formulario para actualizar
     * la barra de estado.
     *
     * @param frm String
     */
    public void estadoCargaFrm(String frm) {
        vtnPrin.getLblEstado().setText("Inciando el formulario de " + frm + " ...");
    }

    /**
     * Al terminar de iniciarse un formulario se envia el nombre del formulario,
     * para actualizar la barra de estado.
     *
     * @param frm String
     */
    public void estadoCargaFrmFin(String frm) {
        vtnPrin.getLblEstado().setText("Formulario de " + frm + " iniciado.");
    }

    /**
     * Si se abren mas de 5 ventanas salta este error
     */
    private void errorNumVentanas() {
        JOptionPane.showMessageDialog(vtnPrin, "No se pueden abrir mas de 5 ventanas",
                "Error Ventana", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Se inician los atajos de teclado para formularios.
     *
     * @author: Johnny Garcia
     */
    private void iniciarAtajosTeclado() {
        vtnPrin.getMnCtAlumno().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtCarrera().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtCurso().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtDocente().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_D, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtMateria().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_M, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtPersona().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtPrdLectivo().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtInscripcion().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_I, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtMatricula().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_T, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtMallaAlumno().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_L, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtDocenteMateria().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtUsuarios().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_U, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtRoles().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtHistorialUsers().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_H, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtRolesPeriodo().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtRolesPeriodo().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtPrdIngrNotas().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtTipoNotas().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtNotas().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_G, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtActivarNotas().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_J, ActionEvent.CTRL_MASK));

        //Acciones de los formularios de ingreso
        vtnPrin.getMnIgAlumno().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgCarrera().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgCurso().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgDocente().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_D, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgPersona().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgPrdLectivo().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgInscripcion().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_I, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgMatricula().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_T, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgDocenteMt().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgPrdIngrNotas1().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgNotas1().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgRolesPeriodo().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgActivarNotas1().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_G, ActionEvent.ALT_MASK));

    }

    public int getNumVtns() {
        return numVtns;
    }

    private void mnCtUsuarios(ActionEvent e) {
        VtnUsuarioCTR vtn = new VtnUsuarioCTR(vtnPrin, new VtnUsuario(), rolSeleccionado);
        vtn.Init();
    }

    private void mnCtRoles(ActionEvent e) {
        VtnRolCTR vtn = new VtnRolCTR(vtnPrin, new VtnRol(), new RolBD(), rolSeleccionado);
        vtn.Init();
    }

    private void btnCerrarSesion(ActionEvent e) {
        ctrSelecRol.cierreSesion();
        ResourceManager.cerrarSesion();
        vtnPrin.dispose();
        LoginCTR login = new LoginCTR(new Login(), new UsuarioBD());
        login.Init();
    }

    private void btnTipoNotas(ActionEvent e) {
        VtnTipoNotasCTR vtn = new VtnTipoNotasCTR(vtnPrin, new VtnTipoNotas(), new TipoDeNotaBD(), rolSeleccionado);
        vtn.Init();
    }

    /**
     * Retornamos el icono del sistema
     *
     * @return icono ImageIcon
     */
    public ImageIcon getIcono() {
        return icono;
    }

    /**
     * Retornamos la imagen del icono.
     *
     * @return ista Image
     */
    public Image getIsta() {
        return ista;
    }

    /**
     * Cambiamos el icono de un JInternalFrame.
     *
     * @param jif JInternalFrame
     */
    public void setIconJIFrame(JInternalFrame jif) {
        jif.setFrameIcon(icono);
    }

    /**
     * Se cambia el icono de un JDialog
     *
     * @param jd JDialog
     */
    public void setIconJDialog(JDialog jd) {
        jd.setIconImage(ista);
    }

    private void btnPrdIngrNotas(ActionEvent e) {

        VtnPeriodoIngresoNotasCTR vtn = new VtnPeriodoIngresoNotasCTR(vtnPrin, new VtnPeriodoIngresoNotas(), new PeriodoIngresoNotasBD(), rolSeleccionado);

        vtn.Init();

    }

    /**
     * Iniciamos una ventana, con acceso directo a comandos NSQL para la base de
     * datos. Proposito es realizar cambios importantes si no se tiene acceso a
     * la base de datos.
     */
    private void iniciarConsola() {
        JPasswordField pass = new JPasswordField();
        int o = JOptionPane.showConfirmDialog(vtnPrin, pass, "Ingrese su contraseña",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        pass.setFocusable(true);
        pass.requestFocus();
        pass.selectAll();

        if (o == JOptionPane.OK_OPTION) {
            String c = new String(pass.getPassword());
            if (c.equals("estaesunacontra")) {
                JDConsolaBDCTR ctr = new JDConsolaBDCTR(vtnPrin, conecta, this);
                ctr.iniciar();
            } else if (c.length() == 0) {
                JOptionPane.showMessageDialog(vtnPrin, "Debe ingresar una contraseña", "Error",
                        JOptionPane.WARNING_MESSAGE);
                iniciarConsola();
            } else {
                JOptionPane.showMessageDialog(vtnPrin, "Quieto ahi esponja.", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    private void btnActivarNotas(ActionEvent e) {

        VtnActivarNotasCTR vtn = new VtnActivarNotasCTR(vtnPrin, new VtnActivarNotas(), new IngresoNotasBD(), rolSeleccionado);
        vtn.Init();
    }

    private void btnCambiarRol(ActionEvent e) {

        VtnSelectRolCTR vtn = ctrSelecRol;
        vtn.Init();
        vtnPrin.setVisible(false);
        System.gc();
    }

    private void InitPermisos() {
        List<AccesosMD> listaPermisos = AccesosBD.selectWhereLIKE(rolSeleccionado.getId(), "CONSULTAR");

        for (AccesosMD acceso : listaPermisos) {

            if (acceso.getNombre().equalsIgnoreCase("PERSONAS-CONSULTAR")) {
                vtnPrin.getMnCtPersona().setEnabled(true);
            } else {
                vtnPrin.getMnCtPersona().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("DOCENTES-CONSULTAR")) {
                vtnPrin.getMnCtDocente().setEnabled(true);
            } else {
                vtnPrin.getMnCtDocente().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("ALUMNOS-CONSULTAR")) {
                vtnPrin.getMnCtAlumno().setEnabled(true);
            } else {
                vtnPrin.getMnCtAlumno().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("CARRERAS-CONSULTAR")) {
                vtnPrin.getMnCtPersona().setEnabled(true);
            } else {
                vtnPrin.getMnCtPersona().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("CURSOS-CONSULTAR")) {
                vtnPrin.getMnCtPersona().setEnabled(true);
            } else {
                vtnPrin.getMnCtPersona().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("PERIODOS-LECTIVOS-CONSULTAR")) {
                vtnPrin.getMnCtPersona().setEnabled(true);
            } else {
                vtnPrin.getMnCtPersona().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("MATERIAS-CONSULTAR")) {
                vtnPrin.getMnCtPersona().setEnabled(true);
            } else {
                vtnPrin.getMnCtPersona().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("INSCRIPCIONES-CONSULTAR")) {
                vtnPrin.getMnCtPersona().setEnabled(true);
            } else {
                vtnPrin.getMnCtPersona().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("MATRICULAS-CONSULTAR")) {
                vtnPrin.getMnCtPersona().setEnabled(true);
            } else {
                vtnPrin.getMnCtPersona().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("MALLA-ALUMNOS-CONSULTAR")) {
                vtnPrin.getMnCtPersona().setEnabled(true);
            } else {
                vtnPrin.getMnCtPersona().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("MATERIAS-DOCENTES-CONSULTAR")) {
                vtnPrin.getMnCtPersona().setEnabled(true);
            } else {
                vtnPrin.getMnCtPersona().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("SILABOS-CONSULTAR")) {
                vtnPrin.getMnCtPersona().setEnabled(true);
            } else {
                vtnPrin.getMnCtPersona().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("HISTORIAL-USUARIOS-CONSULTAR")) {
                vtnPrin.getMnCtPersona().setEnabled(true);
            } else {
                vtnPrin.getMnCtPersona().setEnabled(false);
            }
            if (acceso.getNombre().equalsIgnoreCase("INGRESO-NOTAS-ACTIVAR-CONSULTAR")) {
                vtnPrin.getMnCtPersona().setEnabled(true);
            } else {
                vtnPrin.getMnCtPersona().setEnabled(false);
            }

        }
    }

    private void InitPermisosTesterYDocente() {
        System.out.println("Estamos en modo pruebas = " + pruebas);
        if (!pruebas) {

            if (rolSeleccionado.getNombre().equalsIgnoreCase("TESTER") || rolSeleccionado.getNombre().equalsIgnoreCase("DOCENTE")) {
                if (rolSeleccionado.getNombre().equalsIgnoreCase("DOCENTE")) {
                    vtnPrin.getMnNotas().setEnabled(true);
                    vtnPrin.getMnCtPrdIngrNotas().setEnabled(false);
                    vtnPrin.getMnCtTipoNotas().setEnabled(false);
                    vtnPrin.getMnCtActivarNotas().setEnabled(false);
                    vtnPrin.getMnCtMallaAlumno().setEnabled(false);
                    vtnPrin.getMnCtListaAlumnos().setEnabled(false);
                    vtnPrin.getMnCtAlmnRetirados().setEnabled(false);
                    vtnPrin.getMnCtSilabos().setEnabled(true);
                } else {
                    vtnPrin.getMnNotas().setEnabled(false);
                }

            }
        } else {
            System.out.println("Entre en la base de datos pruebas");
            vtnPrin.setTitle("PF M3A | Modo Pruebas Activado");
        }

        if (rolSeleccionado.getNombre().equalsIgnoreCase("SECRETARIA")) {

            vtnPrin.getMnIngresar().setEnabled(false);
            vtnPrin.getPnlMenu().setVisible(false);
            vtnPrin.getMnCtPersona().setEnabled(false);
            vtnPrin.getMnCtDocente().setEnabled(false);
            vtnPrin.getMnCtAlumno().setEnabled(false);
            vtnPrin.getMnCtCarrera().setEnabled(false);
            vtnPrin.getMnCtCurso().setEnabled(false);
            vtnPrin.getMnCtPrdLectivo().setEnabled(false);
            vtnPrin.getMnCtMateria().setEnabled(false);
            vtnPrin.getMnCtInscripcion().setEnabled(false);
            vtnPrin.getMnCtMatricula().setEnabled(false);
            vtnPrin.getMnCtDocenteMateria().setEnabled(false);
            vtnPrin.getMnCtRolesPeriodo().setEnabled(false);
            vtnPrin.getMnCtPlandeClase().setEnabled(true);
            vtnPrin.getMnCtUsuarios().setEnabled(false);
            vtnPrin.getMnCtRoles().setEnabled(false);
            vtnPrin.getMnCtHistorialUsers().setEnabled(false);
            vtnPrin.getMnCtAccesos().setEnabled(false);
            vtnPrin.getMnCtMiPerfil().setEnabled(false);

            vtnPrin.getMnCtPersona().setEnabled(true);
            vtnPrin.getMnCtAlumno().setEnabled(true);
            vtnPrin.getMnCtAlmnRetirados().setEnabled(true);
            vtnPrin.getMnCtAlmnRetirados().setEnabled(true);
            vtnPrin.getMnCtMatricula().setEnabled(true);
            vtnPrin.getMnCtListaAlumnos().setEnabled(true);
            vtnPrin.getMnCtMallaAlumno().setEnabled(true);
            vtnPrin.getMnCtDocenteMateria().setEnabled(true);
            vtnPrin.getMnCtDocente().setEnabled(true);
        }

    }

    private void registroIngreso(JFrame vtn) {
        vtn.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ctrSelecRol.cierreSesion();
            }

        });
    }

    public UsuarioBD getUsuario() {
        return usuario;
    }

}
