package controlador.principal;

import controlador.alumno.FrmAlumnoCarreraCTR;
import controlador.carrera.FrmCarreraCTR;
import controlador.carrera.VtnCarreraCTR;
import controlador.curso.FrmCursoCTR;
import controlador.alumno.VtnAlumnoCursoCTR;
import controlador.curso.VtnCursoCTR;
import controlador.alumno.FrmAlumnoCursoCTR;
import controlador.alumno.VtnAlumnoCarreraCTR;
import controlador.alumno.VtnMallaAlumnoCTR;
import controlador.login.LoginCTR;
import controlador.materia.VtnMateriaCTR;
import controlador.persona.FrmAlumnoCTR;
import controlador.persona.FrmDocenteCTR;
import controlador.persona.FrmPersonaCTR;
import controlador.persona.VtnAlumnoCTR;
import controlador.persona.VtnDocenteCTR;
import controlador.persona.VtnPersonaCTR;
import controlador.prdlectivo.FrmPrdLectivoCTR;
import controlador.prdlectivo.VtnPrdLectivoCTR;
import controlador.usuario.VtnRolCTR;
import controlador.usuario.VtnUsuarioCTR;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import modelo.ConectarDB;
import modelo.persona.DocenteBD;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
import modelo.usuario.UsuarioMD;
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
import vista.materia.VtnMateria;
import vista.persona.FrmAlumno;
import vista.persona.FrmDocente;
import vista.persona.FrmPersona;
import vista.persona.VtnAlumno;
import vista.persona.VtnDocente;
import vista.persona.VtnPersona;
import vista.prdlectivo.FrmPrdLectivo;
import vista.prdlectivo.VtnPrdLectivo;
import vista.principal.VtnPrincipal;
import vista.usuario.VtnRol;
import vista.usuario.VtnUsuario;

/**
 *
 * @author Johnny
 */
public class VtnPrincipalCTR {

    private final VtnPrincipal vtnPrin;
    private final UsuarioBD usuario;
    private final ConectarDB conecta;
    //Para ver que tanttas ventanas abrimos
    private int numVtns = 0;

    public VtnPrincipalCTR(VtnPrincipal vtnPrin, UsuarioBD usuario, ConectarDB conecta) {
        this.vtnPrin = vtnPrin;
        this.usuario = usuario;
        this.conecta = conecta;

        vtnPrin.setVisible(true);
    }

    public void iniciar() {
        //Iniciamos los shotcuts 
        iniciarAtajosTeclado();

        //Acciones de las ventanas de consulta
        //Para el estilo 
        vtnPrin.getMnRbtnMetal().addActionListener(e -> estiloVtn());
        vtnPrin.getMnRbtnNimbus().addActionListener(e -> estiloVtn());
        vtnPrin.getMnRbtnWindows().addActionListener(e -> estiloVtn());

        //Para abrir las ventanas 
        vtnPrin.getMnCtPersona().addActionListener(e -> abrirVtnPersona());
        vtnPrin.getMnCtAlumno().addActionListener(e -> abrirVtnAlumno());
        vtnPrin.getMnCtCarrera().addActionListener(e -> abrirVtnCarrera());
        vtnPrin.getMnCtCurso().addActionListener(e -> abrirVtnCurso());
        vtnPrin.getMnCtDocente().addActionListener(e -> abrirVtnDocente());
        vtnPrin.getMnCtMateria().addActionListener(e -> abrirVtnMateria());
        vtnPrin.getMnCtPrdLectivo().addActionListener(e -> abrirVtnPrdLectivo());
        vtnPrin.getMnCtInscripcion().addActionListener(e -> abrirVtnAlumnoCarrera());
        vtnPrin.getMnCtMallaAlumno().addActionListener(e -> abrirVtnMallaAlumnos());

        vtnPrin.getBtnMateria().addActionListener(e -> abrirVtnMateria());

        //Para abrir los formularios 
        vtnPrin.getBtnPersona().addActionListener(e -> abrirFrmPersona());

        vtnPrin.getBtnAlumno().addActionListener(e -> abrirFrmAlumno());
        vtnPrin.getBtnCarrera().addActionListener(e -> abrirFrmCarrera());
        vtnPrin.getBtnCurso().addActionListener(e -> abrirFrmCurso());
        vtnPrin.getBtnDocente().addActionListener(e -> abrirFrmDocente());
        vtnPrin.getBtnPrdLectivo().addActionListener(e -> abrirFrmPrdLectivo());
        vtnPrin.getBtnInscripcion().addActionListener(e -> abrirFrmInscripcion());
        vtnPrin.getBtnMatricula().addActionListener(e -> abrirFrmMatricula());

        //Para los menus  
        vtnPrin.getMnIgAlumno().addActionListener(e -> abrirFrmAlumno());
        vtnPrin.getMnIgCarrera().addActionListener(e -> abrirFrmCarrera());
        vtnPrin.getMnIgCurso().addActionListener(e -> abrirFrmCurso());
        vtnPrin.getMnIgDocente().addActionListener(e -> abrirFrmDocente());
        vtnPrin.getMnIgPersona().addActionListener(e -> abrirFrmPersona());
        vtnPrin.getMnIgPrdLectivo().addActionListener(e -> abrirFrmPrdLectivo());
        vtnPrin.getMnIgInscripcion().addActionListener(e -> abrirFrmInscripcion());
        vtnPrin.getMnIgMatricula().addActionListener(e -> abrirFrmMatricula());

        //menus grupo 16
        vtnPrin.getMnCtUsuarios().addActionListener(e -> mnCtUsuariosActionPerformance(e));
        vtnPrin.getMnCtRoles().addActionListener(e -> mnCtRolesActionPerformance(e));
        vtnPrin.getBtnCerrarSesion().addActionListener(e -> btnCerrarSesionActionPerformance(e));

    }

    private void abrirVtnPersona() {
        VtnPersona vtnPersona = new VtnPersona();
        eventoInternal(vtnPersona);
        if (numVtns < 5) {
            VtnPersonaCTR ctrVtnPersona = new VtnPersonaCTR(vtnPrin, vtnPersona, conecta);
            ctrVtnPersona.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    private void abrirVtnDocente() {
        VtnDocente vtnDocente = new VtnDocente();
        eventoInternal(vtnDocente);
        if (numVtns < 5) {
            VtnDocenteCTR ctrVtnDocente = new VtnDocenteCTR(vtnPrin, vtnDocente, conecta);
            ctrVtnDocente.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirVtnAlumno() {
        VtnAlumno vtnAlumno = new VtnAlumno();
        eventoInternal(vtnAlumno);
        if (numVtns < 5) {
            VtnAlumnoCTR ctrVtnAlumno = new VtnAlumnoCTR(vtnPrin, vtnAlumno, conecta);
            ctrVtnAlumno.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirVtnCarrera() {
        VtnCarrera vtnCarrera = new VtnCarrera();
        eventoInternal(vtnCarrera);
        if (numVtns < 5) {
            VtnCarreraCTR ctrVtnCarrera = new VtnCarreraCTR(vtnPrin, vtnCarrera, conecta);
            ctrVtnCarrera.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirVtnCurso() {
        VtnCurso vtnCurso = new VtnCurso();
        eventoInternal(vtnCurso);
        if (numVtns < 5) {
            VtnCursoCTR ctrVtnCurso = new VtnCursoCTR(vtnPrin, vtnCurso, conecta);
            ctrVtnCurso.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirVtnPrdLectivo() {
        VtnPrdLectivo vtnPrdLectivo = new VtnPrdLectivo();
        eventoInternal(vtnPrdLectivo);
        if (numVtns < 5) {
            VtnPrdLectivoCTR ctrVtnPrdLectivo = new VtnPrdLectivoCTR(vtnPrin, vtnPrdLectivo, conecta);
            ctrVtnPrdLectivo.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirVtnAlumnoCurso() {
        VtnAlumnoCurso vtnAlmnCurso = new VtnAlumnoCurso();
        eventoInternal(vtnAlmnCurso);
        if (numVtns < 5) {
            VtnAlumnoCursoCTR ctrVtnAlmnCurso = new VtnAlumnoCursoCTR(vtnPrin, vtnAlmnCurso, conecta);
            ctrVtnAlmnCurso.iniciar();
        } else {

        }
        errorNumVentanas();
    }

    private void abrirVtnMateria() {
        VtnMateria vtnMateria = new VtnMateria();
        eventoInternal(vtnMateria);
        if (numVtns < 5) {
            VtnMateriaCTR ctrVtnMateria = new VtnMateriaCTR(vtnPrin, vtnMateria, conecta);
            ctrVtnMateria.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirVtnAlumnoCarrera() {
        VtnAlumnoCarrera vtnAlmnCarrera = new VtnAlumnoCarrera();
        eventoInternal(vtnAlmnCarrera);
        if (numVtns < 5) {
            VtnAlumnoCarreraCTR ctrAlmn = new VtnAlumnoCarreraCTR(vtnPrin, vtnAlmnCarrera, conecta);
            ctrAlmn.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    private void abrirVtnMallaAlumnos() {
        VtnMallaAlumno vtnMallaAlm = new VtnMallaAlumno();

        VtnMallaAlumnoCTR ctrMalla = new VtnMallaAlumnoCTR(vtnPrin, vtnMallaAlm, conecta);
        ctrMalla.iniciar();
    }

    //Para abrir todos los formularios
    private void abrirFrmPersona() {
        FrmPersona frmPersona = new FrmPersona();
        eventoInternal(frmPersona);
        if (numVtns < 5) {
            FrmPersonaCTR ctrFrmPersona = new FrmPersonaCTR(vtnPrin, frmPersona, conecta);
            ctrFrmPersona.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirFrmDocente() {
        FrmDocente frmDocente = new FrmDocente();
        DocenteBD docente = new DocenteBD(conecta);
        eventoInternal(frmDocente);
        if (numVtns < 5) {
            frmDocente.getBtnRegistrarPersona().setVisible(false);
            FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(vtnPrin, frmDocente, docente, conecta);
            ctrFrmDocente.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirFrmAlumno() {
        FrmAlumno frmAlumno = new FrmAlumno();
        eventoInternal(frmAlumno);
        if (numVtns < 5) {
            FrmAlumnoCTR ctrFrmAlumno = new FrmAlumnoCTR(vtnPrin, frmAlumno, conecta);
            ctrFrmAlumno.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirFrmCarrera() {
        FrmCarrera frmCarrera = new FrmCarrera();
        eventoInternal(frmCarrera);
        if (numVtns < 5) {
            FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(vtnPrin, frmCarrera, conecta);
            ctrFrmCarrera.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirFrmCurso() {
        FrmCurso frmCurso = new FrmCurso();
        eventoInternal(frmCurso);
        if (numVtns < 5) {
            FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(vtnPrin, frmCurso, conecta);
            ctrFrmCurso.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    private void abrirFrmPrdLectivo() {
        FrmPrdLectivo frmPrdLectivo = new FrmPrdLectivo();
        eventoInternal(frmPrdLectivo);
        if (numVtns < 5) {
            FrmPrdLectivoCTR ctrFrmPrdLectivo = new FrmPrdLectivoCTR(vtnPrin, frmPrdLectivo, conecta);
            ctrFrmPrdLectivo.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirFrmInscripcion() {
        FrmAlumnoCarrera frmMatricula = new FrmAlumnoCarrera();
        eventoInternal(frmMatricula);
        if (numVtns < 5) {
            FrmAlumnoCarreraCTR ctrFrmAlumn = new FrmAlumnoCarreraCTR(vtnPrin, frmMatricula, conecta);
            ctrFrmAlumn.iniciar();
        }
    }

    private void abrirFrmMatricula() {
        FrmAlumnoCurso frmAlmCurso = new FrmAlumnoCurso();
        eventoInternal(frmAlmCurso);
        if (numVtns < 5) {
            FrmAlumnoCursoCTR ctrFrmMatri = new FrmAlumnoCursoCTR(vtnPrin, frmAlmCurso, conecta);
            ctrFrmMatri.iniciar();
        }
    }

    private void estiloVtn() {
        String estilo = "Windows";

        if (vtnPrin.getMnRbtnMetal().isSelected()) {
            estilo = "Metal";
        } else if (vtnPrin.getMnRbtnNimbus().isSelected()) {
            estilo = "Nimbus";
        }

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
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("No se pudo cambiar el estilo de la ventana");
            System.out.println(e.getMessage());
        }
    }

    public void eventoInternal(JInternalFrame internal) {
        internal.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                numVtns++;
                if (numVtns > 5) {
                    errorNumVentanas();
                }
            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                numVtns--;
            }

        });
    }

    private void errorNumVentanas() {
        JOptionPane.showMessageDialog(vtnPrin, "No se pueden abrir mas de 5 ventanas",
                "Error Ventana", JOptionPane.ERROR_MESSAGE);
    }

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
    }

    public int getNumVtns() {
        return numVtns;
    }

    private void mnCtUsuariosActionPerformance(ActionEvent e) {
        VtnUsuarioCTR vtn = new VtnUsuarioCTR(vtnPrin, new VtnUsuario(), new UsuarioBD(), usuario);
        vtn.Init();
    }

    private void mnCtRolesActionPerformance(ActionEvent e) {

        VtnRolCTR vtn = new VtnRolCTR(vtnPrin, new VtnRol(), new RolBD(), usuario);
        vtn.Init();

    }

    private void btnCerrarSesionActionPerformance(ActionEvent e) {

        vtnPrin.dispose();

        LoginCTR login = new LoginCTR(new Login(), new UsuarioBD());

        login.Init();

    }
}
