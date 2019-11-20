package controlador.principal;

import controlador.version.JDVersionCTR;
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
import controlador.alumno.VtnAlumnoMatriculaCTR;
import controlador.alumno.VtnAlumnosRetiradosCTR;
import controlador.alumno.VtnMallaAlumnoCTR;
import controlador.alumno.VtnMatriculaCTR;
import controlador.asistenciaAlumnos.FrmAsistenciaCTR;
import controlador.docente.FrmDocenteMateriaCTR;
import controlador.docente.FrmRolPeriodoCTR;
import controlador.docente.VtnDocenteMateriaCTR;
import controlador.docente.VtnRolPeriodosCTR;
import controlador.estilo.AnimacionCarga;
import controlador.fichas.salud.VtnFsaludCTR;
import controlador.login.LoginCTR;
import controlador.materia.FrmMateriasCTR;
import controlador.materia.VtnMateriaCTR;
import controlador.notas.VtnControlUBCTR;
import controlador.notas.VtnNotasCTR;
import controlador.pagos.VtnComprobantesCTR;
import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import controlador.persona.FrmAlumnoCTR;
import controlador.persona.FrmDocenteCTR;
import controlador.persona.FrmPersonaCTR;
import controlador.persona.VtnAlumnoCTR;
import controlador.persona.VtnDocenteCTR;
import controlador.persona.VtnPersonaCTR;
import controlador.prdlectivo.FrmPrdLectivoCTR;
import controlador.prdlectivo.VtnPrdLectivoCTR;
import controlador.referencias.ReferenciasCRUDCTR;
import controlador.silabo.ControladorCRUD;
import controlador.silabo.ControladorCRUDAvanceSilabo;
import controlador.silabo.ControladorCRUDPlanClase;
import controlador.silabo.ControladorSilaboC;
import controlador.silabo.VtnSilabosCTR;
import controlador.silabo.gestionActividades.VtnGestionActividadesCTR;
import controlador.ube.VtnReporteNumAlumnoCTR;
import controlador.usuario.VtnHistorialUserCTR;
import controlador.usuario.Roles.VtnRolCTR;
import controlador.usuario.VtnPerfilUsuarioCTR;
import controlador.usuario.VtnSelectRolCTR;
import controlador.usuario.VtnUsuarioCTR;
import controlador.version.VtnDitoolCTR;
import controlador.vistaReportes.VtnEstadosCTR;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
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
import utils.CONS;
import modelo.ConectarDB;
import modelo.ConexionBD;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
import modelo.version.DitoolBD;
import modelo.version.VersionMD;
import vista.alumno.FrmAlumnoCarrera;
import vista.carrera.FrmCarrera;
import vista.carrera.VtnCarrera;
import vista.alumno.FrmAlumnoCurso;
import vista.alumno.VtnAlumnoCarrera;
import vista.curso.FrmCurso;
import vista.alumno.VtnAlumnoCurso;
import vista.alumno.VtnAlumnoMatricula;
import vista.curso.VtnCurso;
import vista.alumno.VtnMallaAlumno;
import vista.docente.FrmDocenteMateria;
import vista.docente.FrmRolesPeriodos;
import vista.docente.VtnDocenteMateria;
import vista.docente.VtnRolesPeriodos;
import vista.materia.VtnMateria;
import vista.notas.VtnNotas;
import vista.persona.FrmAlumno;
import vista.persona.FrmDocente;
import vista.persona.FrmPersona;
import vista.persona.VtnAlumno;
import vista.persona.VtnDocente;
import vista.persona.VtnPersona;
import vista.prdlectivo.FrmPrdLectivo;
import vista.prdlectivo.VtnPrdLectivo;
import vista.principal.VtnPrincipal;
import vista.usuario.VtnHistorialUsuarios;
import vista.alumno.VtnAlumnosRetirados;
import vista.alumno.VtnMatricula;
import vista.asistenciaAlumnos.FrmAsistencia;
import vista.fichas.salud.VtnFichaSalud;
import vista.materia.FrmMaterias;
import vista.notas.VtnControlUB;
import vista.silabos.frmCRUDBibliografia;
import vista.ube.VtnReporteNumAlumno;
import vista.version.VtnDitool;

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

    /**
     * Construnctor principal del sistema.
     *
     * @param conecta ConectarDB: Coneccion a la base de datos G23
     * @param ctrSelecRol
     */
    public VtnPrincipalCTR(ConectarDB conecta, VtnSelectRolCTR ctrSelecRol) {
        this.vtnPrin = new VtnPrincipal();
        this.rolSeleccionado = CONS.ROL;
        this.usuario = CONS.USUARIO;
        this.conecta = conecta;
        this.ctrSelecRol = ctrSelecRol;
        this.conexion = new ConexionBD(conecta);

        //Inciamos la carga pero la detenemos
        this.carga = new AnimacionCarga(vtnPrin.getBtnEstado());
        vtnPrin.setIconImage(CONS.getImage());
        //Iniciamos la pantala en Fullscream 
        vtnPrin.setExtendedState(JFrame.MAXIMIZED_BOTH);
        registroIngreso(vtnPrin);
        //carga.iniciar();
        //Le pasamos el icono  
        vtnPrin.setTitle("Zero | PF M3A");
        vtnPrin.setVisible(true);
        InitPermisos();

        System.out.println("-------THREADs----->" + Thread.activeCount());

    }

    /**
     * Inicia dependencias de la aplicacion. Eventos, Atajos de teclado y
     * animaciones.
     */
    public void iniciar() {
        //Le pasamos dependencias a conectar
        conecta.setVtnPrin(vtnPrin);
        //Iniciamos los shortcuts 
        iniciarAtajosTeclado();
        //Accion al boton de actualizar 
        vtnPrin.getBtnActualizar().addActionListener(e -> comprobarActualizacion());

        // Seteamos el usuario y su rol  
        String userRol = CONS.USUARIO.getPersona().getPrimerNombre()
                + " " + CONS.USUARIO.getPersona().getPrimerApellido()
                + "  |  " + CONS.ROL.getNombre();
        vtnPrin.getLblUsuario().setText(userRol);
        vtnPrin.getLblUsuario().setToolTipText(userRol);

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
        vtnPrin.getMnCtAlmnMatri().addActionListener(e -> abrirVtnAlumnoMatricula());

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
        vtnPrin.getMnIgMateria().addActionListener(e -> abrirFrmMateria());
        vtnPrin.getMnBiblioteca().addActionListener(e -> abrirVentanaBiblioteca());

        //menus grupo 16
        vtnPrin.getMnCtUsuarios().addActionListener(e -> mnCtUsuarios(e));
        vtnPrin.getMnCtRoles().addActionListener(e -> mnCtRoles(e));
        vtnPrin.getBtnCerrarSesion().addActionListener(e -> btnCerrarSesion(e));
        vtnPrin.getMnCtNotas().addActionListener(e -> abrirVtnNotasAlumnoCurso(e));
        vtnPrin.getMnCtTipoNotas().addActionListener(e -> btnTipoNotas(e));
        vtnPrin.getMnCtNotasDuales().addActionListener(e -> btnNotasDuales(e));
        //vtnPrin.getMnCtActivarNotas().addActionListener(e -> btnActivarNotas(e));
        vtnPrin.getMnCtRendimientoAcademico().addActionListener(e -> abrirVtnControlUB(e));
        vtnPrin.getMnCtAsistencia().addActionListener(e -> abrirFrmAsistencia(e));
        vtnPrin.getMnCtReportesEstado().addActionListener(e -> mnctReportesEstado(e));

        vtnPrin.getBtnAyuda().addActionListener(e -> abrirVtnAyuda());

        vtnPrin.getMnCtSilabos().addActionListener(al -> btnSilabo());
        vtnPrin.getMnCtPlandeClase().addActionListener(a1 -> controladorCONFIGURACION_PLAN_DE_CLASES());
        vtnPrin.getBtnConsultarSilabo().addActionListener(al -> btnSilabo());
        vtnPrin.getBtnIngresarSilabo().addActionListener(al -> controladorIngreso());
        vtnPrin.getMnCAvanceSilabo().addActionListener(a1 -> controladorCONFIGURACION_avance_silabo());
        vtnPrin.getBtnCambiarRol().addActionListener(e -> btnCambiarRol(e));
        vtnPrin.getMnCtGestionAcademica().addActionListener(this::btnGestionAcademica);
        //esto es para el avance de silabo
        //vtnPrin.getMnCAvanceSilabo().addActionListener(ak ->c);

        vtnPrin.getMnCtComprobantes().addActionListener(e -> btnComprobantes(e));

        carga.start();

        //Esto es para la consola 
        vtnPrin.getBtnConsola().addActionListener(e -> iniciarConsola());

        vtnPrin.getLblIP().setText(CONS.DB_IP + "/Conectados/" + CONS.BD_NAME);

        vtnPrin.getMnCtMiPerfil().addActionListener(e -> btnMiperfilActionPerformance(e));
        vtnPrin.getBtn_avance_si().addActionListener(e -> controladorCONFIGURACION_avance_silabo());

        iniciarAccionesRep();
    }

    private void iniciarAccionesRep() {
        vtnPrin.getMnRepNumAlumno().addActionListener(e -> abrirVtnReporteNumAlumno());

        //Menus Fichas
        vtnPrin.getMnCtFichaSalud().addActionListener(e -> mnctFichaSalud(e));

    }

    public void abrirVtnPersona() {
        VtnPersona vtnPersona = new VtnPersona();
        eventoInternal(vtnPersona);
        if (numVtns < 5) {
            carga.iniciar();
            VtnPersonaCTR ctrVtnPersona = new VtnPersonaCTR(vtnPersona, this);
            ctrVtnPersona.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnDocente() {
        VtnDocente vtnDocente = new VtnDocente();
        eventoInternal(vtnDocente);
        if (numVtns < 6) {
            VtnDocenteCTR ctrVtnDocente = new VtnDocenteCTR(vtnDocente, this);
            ctrVtnDocente.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnAlumno() {
        VtnAlumno vtnAlumno = new VtnAlumno();
        eventoInternal(vtnAlumno);
        if (numVtns < 5) {
            VtnAlumnoCTR ctrVtnAlumno = new VtnAlumnoCTR(vtnAlumno, this);
            ctrVtnAlumno.iniciar();
        } else {
            errorNumVentanas();

        }

    }

    public void abrirVentanaBiblioteca() {
        frmCRUDBibliografia frmCRUDBibliografiaV = new frmCRUDBibliografia();
        eventoInternal(frmCRUDBibliografiaV);
        if (numVtns < 5) {
            ReferenciasCRUDCTR ReferenciasCRUDCTRV = new ReferenciasCRUDCTR(conexion, conecta, this, vtnPrin, frmCRUDBibliografiaV);
            ReferenciasCRUDCTRV.iniciarControlador();

        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnCarrera() {
        VtnCarrera vtnCarrera = new VtnCarrera();
        eventoInternal(vtnCarrera);
        if (numVtns < 5) {
            VtnCarreraCTR ctrVtnCarrera = new VtnCarreraCTR(vtnCarrera, this);
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
            VtnCursoCTR ctrVtnCurso = new VtnCursoCTR(vtnCurso, this);
            ctrVtnCurso.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnPrdLectivo() {
        VtnPrdLectivo vtnPrdLectivo = new VtnPrdLectivo();
        eventoInternal(vtnPrdLectivo);
        if (numVtns < 5) {
            VtnPrdLectivoCTR ctrVtnPrdLectivo = new VtnPrdLectivoCTR(vtnPrdLectivo, this);
            ctrVtnPrdLectivo.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnAlumnoMatricula() {
        VtnAlumnoMatricula vtn = new VtnAlumnoMatricula();
        eventoInternal(vtn);
        if (numVtns < 5) {
            VtnAlumnoMatriculaCTR ctr = new VtnAlumnoMatriculaCTR(this, vtn);
            ctr.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnAlumnoCurso() {
        VtnAlumnoCurso vtnAlmnCurso = new VtnAlumnoCurso();
        eventoInternal(vtnAlmnCurso);
        if (numVtns < 5) {
            VtnAlumnoCursoCTR ctrVtnAlmnCurso = new VtnAlumnoCursoCTR(vtnAlmnCurso, this);
            ctrVtnAlmnCurso.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    private void abrirVtnControlUB(ActionEvent e) {
        VtnControlUB vtn = new VtnControlUB();
        eventoInternal(vtn);
        if (numVtns < 5) {
            VtnControlUBCTR vtnCtr = new VtnControlUBCTR(vtnPrin, vtn, usuario, rolSeleccionado);
            vtnCtr.Init();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnMatricula() {
        VtnMatricula vtnMatri = new VtnMatricula();
        eventoInternal(vtnMatri);
        if (numVtns < 5) {
            VtnMatriculaCTR ctr = new VtnMatriculaCTR(this, vtnMatri);
            ctr.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnMateria() {
        VtnMateria vtnMateria = new VtnMateria();
        eventoInternal(vtnMateria);
        if (numVtns < 5) {
            VtnMateriaCTR ctrVtnMateria = new VtnMateriaCTR(vtnMateria, this);
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
            VtnAlumnoCarreraCTR ctrAlmn = new VtnAlumnoCarreraCTR(vtnAlmnCarrera, this);
            ctrAlmn.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnMallaAlumnos() {
        VtnMallaAlumno vtnMallaAlm = new VtnMallaAlumno();
        eventoInternal(vtnMallaAlm);
        if (numVtns < 5) {
            VtnMallaAlumnoCTR ctrMalla = new VtnMallaAlumnoCTR(vtnMallaAlm, this);
            ctrMalla.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnDocenteMateria() {
        VtnDocenteMateria vtn = new VtnDocenteMateria();
        eventoInternal(vtn);
        if (numVtns < 5) {
            VtnDocenteMateriaCTR ctrVtn = new VtnDocenteMateriaCTR(vtn, this);
            ctrVtn.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnAlmnRetirados() {
        VtnAlumnosRetirados vtn = new VtnAlumnosRetirados();
        eventoInternal(vtn);
        if (numVtns < 5) {
            VtnAlumnosRetiradosCTR ctr = new VtnAlumnosRetiradosCTR(this, vtn);
            ctr.iniciar();
        } else {
            errorNumVentanas();
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
            VtnHistorialUserCTR ctr = new VtnHistorialUserCTR(this);
            ctr.iniciar();
        }
    }

    //Para abrir todos los formularios
    public void abrirFrmPersona() {
        FrmPersona frmPersona = new FrmPersona();
        eventoInternal(frmPersona);
        if (numVtns < 5) {
            FrmPersonaCTR ctrFrmPersona = new FrmPersonaCTR(frmPersona, this);
            ctrFrmPersona.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirFrmRolesPeriodos() {
        FrmRolesPeriodos frmRolPeriodo = new FrmRolesPeriodos();
        eventoInternal(frmRolPeriodo);
        if (numVtns < 5) {
            FrmRolPeriodoCTR rol = new FrmRolPeriodoCTR(frmRolPeriodo, this);
            rol.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnRolesPeriodos() {
        VtnRolesPeriodos vtnRolprd = new VtnRolesPeriodos();
        eventoInternal(vtnRolprd);
        if (numVtns < 5) {
            VtnRolPeriodosCTR vtnRol = new VtnRolPeriodosCTR(vtnRolprd, this);
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
            FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(frmDocente, this);
            ctrFrmDocente.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmAlumno() {
        FrmAlumno frmAlumno = new FrmAlumno();
        eventoInternal(frmAlumno);
        if (numVtns < 5) {
            FrmAlumnoCTR ctrFrmAlumno = new FrmAlumnoCTR(frmAlumno, this);
            ctrFrmAlumno.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmMateria() {
        FrmMaterias frmMaterias = new FrmMaterias();
        eventoInternal(frmMaterias);
        if (numVtns < 5) {
            FrmMateriasCTR ctrFrmMaterias = new FrmMateriasCTR(frmMaterias, this);
            ctrFrmMaterias.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirFrmCarrera() {
        FrmCarrera frmCarrera = new FrmCarrera();
        eventoInternal(frmCarrera);
        if (numVtns < 5) {
            FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(frmCarrera, this);
            ctrFrmCarrera.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmCurso() {
        FrmCurso frmCurso = new FrmCurso();
        eventoInternal(frmCurso);
        if (numVtns < 5) {
            FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(frmCurso, this);
            ctrFrmCurso.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirFrmPrdLectivo() {
        FrmPrdLectivo frmPrdLectivo = new FrmPrdLectivo();
        eventoInternal(frmPrdLectivo);
        if (numVtns < 5) {
            FrmPrdLectivoCTR ctrFrmPrdLectivo = new FrmPrdLectivoCTR(frmPrdLectivo, this);
            ctrFrmPrdLectivo.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmInscripcion() {
        FrmAlumnoCarrera frmMatricula = new FrmAlumnoCarrera();
        eventoInternal(frmMatricula);
        if (numVtns < 5) {
            FrmAlumnoCarreraCTR ctrFrmAlumn = new FrmAlumnoCarreraCTR(frmMatricula, this);
            ctrFrmAlumn.iniciar();
        }
    }

    public void abrirFrmMatricula() {
        FrmAlumnoCurso frmAlmCurso = new FrmAlumnoCurso();
        eventoInternal(frmAlmCurso);
        if (numVtns < 5) {
            FrmAlumnoCursoCTR ctrFrmMatri = new FrmAlumnoCursoCTR(frmAlmCurso, this);
            ctrFrmMatri.iniciar();
        }
    }

    public void abrirFrmDocenteMateria() {
        FrmDocenteMateria frm = new FrmDocenteMateria();
        eventoInternal(frm);
        if (numVtns < 5) {
            FrmDocenteMateriaCTR ctrFrm = new FrmDocenteMateriaCTR(frm, this);
            ctrFrm.iniciar();
        }
    }

    public void abrirVtnAccesos() {
        VtnAccesosCTR vtn = new VtnAccesosCTR(this);
        vtn.Init();
    }

    private void abrirFrmAsistencia(ActionEvent e) {
        FrmAsistencia frm = new FrmAsistencia();
        eventoInternal(frm);
        if (numVtns < 5) {
            FrmAsistenciaCTR asistencia = new FrmAsistenciaCTR(vtnPrin, new FrmAsistencia(), usuario, rolSeleccionado);
            asistencia.Init();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirVtnReporteNumAlumno() {
        VtnReporteNumAlumno vtn = new VtnReporteNumAlumno();
        eventoInternal(vtn);
        if (numVtns < 5) {
            VtnReporteNumAlumnoCTR ctr = new VtnReporteNumAlumnoCTR(this, vtn);
            ctr.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    private void btnSilabo() {
        VtnSilabosCTR vtn = new VtnSilabosCTR(this);
        vtn.Init();
    }

    private void controladorCONFIGURACION_PLAN_DE_CLASES() {
        ControladorCRUDPlanClase cP = new ControladorCRUDPlanClase(usuario, rolSeleccionado, conexion, vtnPrin);
        cP.iniciaControlador();

    }

    private void controladorCONFIGURACION_avance_silabo() {
        ControladorCRUDAvanceSilabo AS = new ControladorCRUDAvanceSilabo(usuario, rolSeleccionado, vtnPrin, conexion);
        AS.initCrud();

    }

    private void controladorIngreso() {

        ControladorSilaboC c = new ControladorSilaboC(null, vtnPrin, new ConexionBD(conecta));

        c.iniciarControlador();

    }

    private void abrirVtnNotasAlumnoCurso(ActionEvent e) {
        VtnNotas vtn = new VtnNotas();
        eventoInternal(vtn);
        if (numVtns < 5) {
            VtnNotasCTR vtnCtr = new VtnNotasCTR(vtnPrin, vtn);
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
            SwingUtilities.updateComponentTreeUI(vtnPrin);
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
                /*
                if (numVtns > 5) {
                    errorNumVentanas();
                    internal.dispose();
                    numVtns = 0;
                }*/
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
                KeyEvent.VK_Z, ActionEvent.CTRL_MASK));

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

        vtnPrin.getMnCtNotasDuales().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtTipoNotas().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtNotas().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_G, ActionEvent.CTRL_MASK));

        //vtnPrin.getMnCtActivarNotas().setAccelerator(KeyStroke.getKeyStroke(
        //        KeyEvent.VK_J, ActionEvent.CTRL_MASK));
        vtnPrin.getMnCtAsistencia().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Y, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtAccesos().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_W, ActionEvent.CTRL_MASK));

        vtnPrin.getMnBiblioteca().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtAlmnRetirados().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        vtnPrin.getMnCtListaAlumnos().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

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
                KeyEvent.VK_R, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgMateria().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_K, ActionEvent.ALT_MASK));

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

        vtnPrin.getMnCtMiPerfil().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_M, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgUsuarios1().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_U, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgRoles1().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_M, ActionEvent.ALT_MASK));

        vtnPrin.getMnIgPrdLectivo().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_L, ActionEvent.ALT_MASK));

    }

    public int getNumVtns() {
        return numVtns;
    }

    private void mnCtUsuarios(ActionEvent e) {
        VtnUsuarioCTR vtn = new VtnUsuarioCTR(vtnPrin);
        vtn.Init();
    }

    private void mnCtRoles(ActionEvent e) {
        VtnRolCTR vtn = new VtnRolCTR(this);
        vtn.Init();
    }

    private void btnCerrarSesion(ActionEvent e) {
        ctrSelecRol.cierreSesion();
        vtnPrin.dispose();
        LoginCTR login = new LoginCTR();
        login.Init();
    }

    private void btnTipoNotas(ActionEvent e) {
        VtnTipoNotasCTR vtn = new VtnTipoNotasCTR(vtnPrin);
        vtn.Init();
    }

    /**
     * Retornamos la imagen del icono.
     *
     * @return ista Image
     */
    /**
     * Cambiamos el icono de un JInternalFrame.
     *
     * @param jif JInternalFrame
     */
    public void setIconJIFrame(JInternalFrame jif) {
        jif.setFrameIcon(CONS.getICONO());
    }

    /**
     * Se cambia el icono de un JDialog
     *
     * @param jd JDialog
     */
    public void setIconJDialog(JDialog jd) {
        jd.setIconImage(CONS.getImage());
    }

    private void btnNotasDuales(ActionEvent e) {

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
        //Para comprobar si puede entrar o no 
        boolean entrar = true;

        if (o == JOptionPane.OK_OPTION) {
            String c = new String(pass.getPassword());
            if (c.length() > 3) {
                if (c.charAt(c.length() - 1) != c.charAt(0)) {
                    entrar = false;
                }

                if (c.charAt(c.length() - 3) != c.charAt(2)) {
                    entrar = false;
                }

                if (entrar) {
                    JDVersionCTR ctr = new JDVersionCTR(vtnPrin, this);
                    ctr.iniciar();
                } else {
                    JOptionPane.showMessageDialog(vtnPrin, "Entrar aqui es peligroso.\n"
                            + "Es mejor que corras esponja!!!", "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
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

    }

    private void btnMiperfilActionPerformance(ActionEvent e) {

        VtnPerfilUsuarioCTR vtn = new VtnPerfilUsuarioCTR(this);
        vtn.Init();
    }

    private void btnCambiarRol(ActionEvent e) {

        VtnSelectRolCTR vtn = ctrSelecRol;
        vtn.Init();
        vtnPrin.setVisible(false);
        System.gc();
    }

    private void InitPermisos() {

        //CONSULTAR 
        vtnPrin.getMnCtPersona().getAccessibleContext().setAccessibleName("Personas-Consular");
        vtnPrin.getMnCtDocente().getAccessibleContext().setAccessibleName("Docente-Consultar");
        vtnPrin.getMnCtAlumno().getAccessibleContext().setAccessibleName("Alumnos-Consultar");
        vtnPrin.getMnCtCarrera().getAccessibleContext().setAccessibleName("Carreras-Consultar");
        vtnPrin.getMnCtCurso().getAccessibleContext().setAccessibleName("Cursos-Consultar");
        vtnPrin.getMnCtPrdLectivo().getAccessibleContext().setAccessibleName("Periodo-Lectivo-Consultar");
        vtnPrin.getMnCtMateria().getAccessibleContext().setAccessibleName("Materias-Consultar");
        vtnPrin.getMnCtInscripcion().getAccessibleContext().setAccessibleName("Inscripcion-Consultar");
        vtnPrin.getMnCtMatricula().getAccessibleContext().setAccessibleName("Matricula-Consultar");
        vtnPrin.getMnCtAlmnRetirados().getAccessibleContext().setAccessibleName("Matriculas-Anuladas-Consultar");
        vtnPrin.getMnCtListaAlumnos().getAccessibleContext().setAccessibleName("Lista-Alumnos-Consultar");
        vtnPrin.getMnCtMallaAlumno().getAccessibleContext().setAccessibleName("Malla-Alumnos-Consultar");
        vtnPrin.getMnCtDocenteMateria().getAccessibleContext().setAccessibleName("Materia-Docentes-Consultar");
        vtnPrin.getMnCtRolesPeriodo().getAccessibleContext().setAccessibleName("Roles-Periodo-Consultar");
        vtnPrin.getMnCtSilabos().getAccessibleContext().setAccessibleName("Silabos-Consultar");
        vtnPrin.getMnCtPlandeClase().getAccessibleContext().setAccessibleName("Plan-De-Clase-Consultar");
        vtnPrin.getMnCtUsuarios().getAccessibleContext().setAccessibleName("Usuarios-Consultar");
        vtnPrin.getMnCtRoles().getAccessibleContext().setAccessibleName("Roles-Usuarios-Consultar");
        vtnPrin.getMnCtHistorialUsers().getAccessibleContext().setAccessibleName("Historial-Usuarios-Consultar");
        vtnPrin.getMnCtTipoNotas().getAccessibleContext().setAccessibleName("Notas-Tipos-de-Notas-Consultar");
        vtnPrin.getMnCtNotas().getAccessibleContext().setAccessibleName("Notas-Consultar");
        vtnPrin.getMnBiblioteca().getAccessibleContext().setAccessibleName("Biblioteca-Consultar");
        vtnPrin.getMnCtAccesos().getAccessibleContext().setAccessibleName("Accesos-Consultar");
        vtnPrin.getMnCtMiPerfil().getAccessibleContext().setAccessibleName("Mi-Perfil-Consultar");
        vtnPrin.getMnCtAsistencia().getAccessibleContext().setAccessibleName("Asistencia-Consultar");
        vtnPrin.getMnCtRendimientoAcademico().getAccessibleContext().setAccessibleName("Rendimiento-Academico-Consultar");
        vtnPrin.getMnCtReportesEstado().getAccessibleContext().setAccessibleName("Asistencia-Estado-Consultar");

        //INGRESAR 
        vtnPrin.getMnIgPersona().getAccessibleContext().setAccessibleName("Persona-Ingresar");
        vtnPrin.getMnIgDocente().getAccessibleContext().setAccessibleName("Docente-Ingresar");
        vtnPrin.getMnIgAlumno().getAccessibleContext().setAccessibleName("Alumno-Ingresar");
        vtnPrin.getMnIgCarrera().getAccessibleContext().setAccessibleName("Carrera-Ingresar");
        vtnPrin.getMnIgCurso().getAccessibleContext().setAccessibleName("Curso-Ingresar");
        vtnPrin.getMnIgPrdLectivo().getAccessibleContext().setAccessibleName("Periodo-Lectivo-Ingresar");
        vtnPrin.getMnIgMateria().getAccessibleContext().setAccessibleName("Materia-Ingresar");
        vtnPrin.getMnIgInscripcion().getAccessibleContext().setAccessibleName("Inscripcion-Ingresar");
        vtnPrin.getMnIgMatricula().getAccessibleContext().setAccessibleName("Matricula-Ingresar");
        vtnPrin.getMnIgDocenteMt().getAccessibleContext().setAccessibleName("Materia-Docente-Ingresar");
        vtnPrin.getMnIgRolesPeriodo().getAccessibleContext().setAccessibleName("Roles-Periodo-Ingresar");
        vtnPrin.getMnIgSilabo().getAccessibleContext().setAccessibleName("Silabo-Ingresar");
        vtnPrin.getMnIgPlandeClase().getAccessibleContext().setAccessibleName("Plan-Clase-Ingresar");
        vtnPrin.getMnIgUsuarios1().getAccessibleContext().setAccessibleName("Usuario-Ingresar");
        vtnPrin.getMnIgRoles1().getAccessibleContext().setAccessibleName("Roles-Usuario-Ingresar");
        vtnPrin.getMnIgNotas1().getAccessibleContext().setAccessibleName("Notas-Ingresar");

        //ACCESOS DIRECTOS
        vtnPrin.getBtnPersona().getAccessibleContext().setAccessibleName("Persona-Ingresar");
        vtnPrin.getBtnDocente().getAccessibleContext().setAccessibleName("Docente-Ingresar");
        vtnPrin.getBtnAlumno().getAccessibleContext().setAccessibleName("Alumno-Ingresar");
        vtnPrin.getBtnCarrera().getAccessibleContext().setAccessibleName("Carrera-Ingresar");
        vtnPrin.getBtnCurso().getAccessibleContext().setAccessibleName("Curso-Ingresar");
        vtnPrin.getBtnPrdLectivo().getAccessibleContext().setAccessibleName("Periodo-Lectivo-Ingresar");
        vtnPrin.getBtnInscripcion().getAccessibleContext().setAccessibleName("Inscripcion-Ingresar");
        vtnPrin.getBtnMatricula().getAccessibleContext().setAccessibleName("Matricula-Ingresar");
        vtnPrin.getBtnDocenteMateria().getAccessibleContext().setAccessibleName("Docente-Ingresar");
        vtnPrin.getBtnMateria().getAccessibleContext().setAccessibleName("Materia-Ingresar");
        vtnPrin.getBtnIngresarSilabo().getAccessibleContext().setAccessibleName("Silabo-Ingresar");
        vtnPrin.getBtnConsultarSilabo().getAccessibleContext().setAccessibleName("Silabos-Consultar");
        vtnPrin.getBtnIngresarRol().getAccessibleContext().setAccessibleName("Roles-Periodo-Ingresar");

        CONS.activarBtns(
                //Accesos Directos
                vtnPrin.getBtnPersona(),
                vtnPrin.getBtnDocente(),
                vtnPrin.getBtnAlumno(),
                vtnPrin.getBtnCarrera(),
                vtnPrin.getBtnCurso(),
                vtnPrin.getBtnPrdLectivo(),
                vtnPrin.getBtnInscripcion(),
                vtnPrin.getBtnMatricula(),
                vtnPrin.getBtnDocenteMateria(),
                vtnPrin.getBtnMateria(),
                vtnPrin.getBtnIngresarSilabo(),
                vtnPrin.getBtnConsultarSilabo(),
                vtnPrin.getBtnIngresarRol(),
                //Consultas
                vtnPrin.getMnCtPersona(),
                vtnPrin.getMnCtDocente(),
                vtnPrin.getMnCtAlumno(),
                vtnPrin.getMnCtCarrera(),
                vtnPrin.getMnCtCurso(),
                vtnPrin.getMnCtPrdLectivo(),
                vtnPrin.getMnCtMateria(),
                vtnPrin.getMnCtInscripcion(),
                vtnPrin.getMnCtMatricula(),
                vtnPrin.getMnCtListaAlumnos(),
                vtnPrin.getMnCtMallaAlumno(),
                vtnPrin.getMnCtDocenteMateria(),
                vtnPrin.getMnCtRolesPeriodo(),
                vtnPrin.getMnCtAlmnRetirados(),
                vtnPrin.getMnCtSilabos(),
                vtnPrin.getMnCtPlandeClase(),
                vtnPrin.getMnCtUsuarios(),
                vtnPrin.getMnCtRoles(),
                vtnPrin.getMnCtHistorialUsers(),
                vtnPrin.getMnCtNotas(),
                vtnPrin.getMnCtTipoNotas(),
                vtnPrin.getMnCtAccesos(),
                vtnPrin.getMnCtAsistencia(),
                vtnPrin.getMnBiblioteca(),
                vtnPrin.getMnCtRendimientoAcademico(),
                vtnPrin.getMnCtReportesEstado(),
                // ingresar
                vtnPrin.getMnIgPersona(),
                vtnPrin.getMnIgDocente(),
                vtnPrin.getMnIgAlumno(),
                vtnPrin.getMnIgCarrera(),
                vtnPrin.getMnIgCurso(),
                vtnPrin.getMnIgPrdLectivo(),
                vtnPrin.getMnIgMateria(),
                vtnPrin.getMnIgInscripcion(),
                vtnPrin.getMnIgMatricula(),
                vtnPrin.getMnIgDocenteMt(),
                vtnPrin.getMnIgRolesPeriodo(),
                vtnPrin.getMnIgSilabo(),
                vtnPrin.getMnIgPlandeClase(),
                vtnPrin.getMnIgUsuarios1(),
                vtnPrin.getMnIgRoles1(),
                vtnPrin.getMnIgNotas1()
        );

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

    public VtnPrincipal getVtnPrin() {
        return vtnPrin;
    }

    public ConectarDB getConecta() {
        return conecta;
    }

    public void agregarVtn(JInternalFrame ji) {
        eventoInternal(ji);
        vtnPrin.getDpnlPrincipal().add(ji);
        ji.show();
    }

    public RolBD getRolSeleccionado() {
        return rolSeleccionado;
    }

    private void mnctReportesEstado(ActionEvent e) {

        VtnEstadosCTR vtn = new VtnEstadosCTR(this);
        vtn.Init();

    }

    private void comprobarActualizacion() {
        VtnDitool vtnDitool = new VtnDitool();
        vtnDitool.setTitle("Ditool | Version instalada: ");
        DitoolBD di = new DitoolBD("VERSION", "AZUL");
        VersionMD v = di.consultarUltimaVersion();
        if (v != null) {
            vtnPrin.setEnabled(false);
            VtnDitoolCTR ctrVtn = new VtnDitoolCTR(v, vtnDitool, vtnPrin);
            ctrVtn.iniciar();
        } else {
            JOptionPane.showMessageDialog(vtnDitool, "Posiblemente no tengamos acceso a internet. \n"
                    + "Verifique su conexion e intentelo de nuevo.");
        }
    }

    private void mnctFichaSalud(ActionEvent e) {
        VtnFsaludCTR vtn = new VtnFsaludCTR(vtnPrin, new VtnFichaSalud());
        vtn.Init();
    }

    private void btnComprobantes(ActionEvent e) {
        VtnComprobantesCTR vtn = new VtnComprobantesCTR(this);
        vtn.Init();
    }

    private void btnGestionAcademica(ActionEvent e) {

        VtnGestionActividadesCTR vtn = new VtnGestionActividadesCTR(this);
        vtn.Init();

    }

}
