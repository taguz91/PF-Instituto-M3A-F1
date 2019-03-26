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
import controlador.docente.FrmDocenteMateriaCTR;
import controlador.docente.VtnDocenteMateriaCTR;
import controlador.estilo.AnimacionCarga;
import controlador.login.LoginCTR;
import controlador.materia.VtnMateriaCTR;
import controlador.notas.VtnNotasAlumnoCursoCTR;
import controlador.periodoLectivoNotas.VtnTipoNotasCTR;
import controlador.persona.FrmAlumnoCTR;
import controlador.persona.FrmDocenteCTR;
import controlador.persona.FrmPersonaCTR;
import controlador.persona.VtnAlumnoCTR;
import controlador.persona.VtnDocenteCTR;
import controlador.persona.VtnPersonaCTR;
import controlador.prdlectivo.FrmPrdLectivoCTR;
import controlador.prdlectivo.VtnPrdLectivoCTR;
import controlador.silabo.ControladorSilabos;
import controlador.usuario.VtnRolCTR;
import controlador.usuario.VtnUsuarioCTR;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.alumno.AlumnoCursoBD;
import modelo.persona.DocenteBD;
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
import vista.docente.VtnDocenteMateria;
import vista.materia.VtnMateria;
import vista.notas.VtnNotasAlumnoCurso;
import vista.periodoLectivoNotas.VtnTipoNotas;
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
    private final RolBD rolSeleccionado;
    private final UsuarioBD usuario;
    private final ConectarDB conecta;
    //Agregamos la animacion 
    public AnimacionCarga carga;
    //Para ver que tanttas ventanas abrimos
    private int numVtns = 0;
    //Icono de la aplicacion  
    private final ImageIcon icono;
    private final Image ista; 

    public VtnPrincipalCTR(VtnPrincipal vtnPrin, RolBD rolSeleccionado, 
            UsuarioBD usuario, ConectarDB conecta, ImageIcon icono, Image ista) {
        this.vtnPrin = vtnPrin;
        this.rolSeleccionado = rolSeleccionado;
        this.usuario = usuario;
        this.conecta = conecta;
        //Inciamos la carga pero la detenemos
        this.carga = new AnimacionCarga(vtnPrin.getBtnEstado());
        this.icono = icono;
        this.ista = ista;
        vtnPrin.setIconImage(ista);
        //carga.iniciar();
        //Le pasamos el icono  
        vtnPrin.setTitle("PF M3A");
        vtnPrin.setVisible(true);
        InitPermisos();
    }

    public void iniciar() {
        //Iniciamos los shortcuts 
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
        vtnPrin.getMnCtDocenteMateria().addActionListener(e -> abrirVtnDocenteMateria());

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
        vtnPrin.getBtnDocenteMateria().addActionListener(e -> abrirFrmDocenteMateria());

        //Para los menus  
        vtnPrin.getMnIgAlumno().addActionListener(e -> abrirFrmAlumno());
        vtnPrin.getMnIgCarrera().addActionListener(e -> abrirFrmCarrera());
        vtnPrin.getMnIgCurso().addActionListener(e -> abrirFrmCurso());
        vtnPrin.getMnIgDocente().addActionListener(e -> abrirFrmDocente());
        vtnPrin.getMnIgPersona().addActionListener(e -> abrirFrmPersona());
        vtnPrin.getMnIgPrdLectivo().addActionListener(e -> abrirFrmPrdLectivo());
        vtnPrin.getMnIgInscripcion().addActionListener(e -> abrirFrmInscripcion());
        vtnPrin.getMnIgMatricula().addActionListener(e -> abrirFrmMatricula());
        vtnPrin.getMnIgDocenteMt().addActionListener(e -> abrirFrmDocenteMateria());

        //menus grupo 16
        vtnPrin.getMnCtUsuarios().addActionListener(e -> mnCtUsuariosActionPerformance(e));
        vtnPrin.getMnCtRoles().addActionListener(e -> mnCtRolesActionPerformance(e));
        vtnPrin.getBtnCerrarSesion().addActionListener(e -> btnCerrarSesionActionPerformance(e));
        vtnPrin.getMnCtNotas().addActionListener(e -> abrirVtnNotasAlumnoCurso(e));
        vtnPrin.getMnCtTipoNotas().addActionListener(e -> btnTipoNotas(e));

        controladorSilabo();
        
        carga.start();
    }

    private void abrirVtnPersona() {
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

    private void abrirVtnDocente() {
        VtnDocente vtnDocente = new VtnDocente();
        eventoInternal(vtnDocente);
        if (numVtns < 5) {
            carga.detener();
            VtnDocenteCTR ctrVtnDocente = new VtnDocenteCTR(vtnPrin, vtnDocente, conecta, this, rolSeleccionado);
            ctrVtnDocente.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirVtnAlumno() {
        VtnAlumno vtnAlumno = new VtnAlumno();
        eventoInternal(vtnAlumno);
        if (numVtns < 5) {
            VtnAlumnoCTR ctrVtnAlumno = new VtnAlumnoCTR(vtnPrin, vtnAlumno, conecta, this, rolSeleccionado);
            ctrVtnAlumno.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirVtnCarrera() {
        VtnCarrera vtnCarrera = new VtnCarrera();
        eventoInternal(vtnCarrera);
        if (numVtns < 5) {
            VtnCarreraCTR ctrVtnCarrera = new VtnCarreraCTR(vtnPrin, vtnCarrera, conecta, this, rolSeleccionado);
            ctrVtnCarrera.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirVtnCurso() {
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

    private void abrirVtnPrdLectivo() {
        VtnPrdLectivo vtnPrdLectivo = new VtnPrdLectivo();
        eventoInternal(vtnPrdLectivo);
        if (numVtns < 5) {
            VtnPrdLectivoCTR ctrVtnPrdLectivo = new VtnPrdLectivoCTR(vtnPrin, vtnPrdLectivo, conecta, this, rolSeleccionado);
            ctrVtnPrdLectivo.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirVtnAlumnoCurso() {
        VtnAlumnoCurso vtnAlmnCurso = new VtnAlumnoCurso();
        eventoInternal(vtnAlmnCurso);
        if (numVtns < 5) {
            VtnAlumnoCursoCTR ctrVtnAlmnCurso = new VtnAlumnoCursoCTR(vtnPrin, vtnAlmnCurso, conecta, this, rolSeleccionado);
            ctrVtnAlmnCurso.iniciar();
        } else {

        }
        errorNumVentanas();
    }

    private void abrirVtnMateria() {
        VtnMateria vtnMateria = new VtnMateria();
        eventoInternal(vtnMateria);
        if (numVtns < 5) {
            VtnMateriaCTR ctrVtnMateria = new VtnMateriaCTR(vtnPrin, vtnMateria, conecta, this, rolSeleccionado);
            ctrVtnMateria.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirVtnAlumnoCarrera() {
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

    private void abrirVtnMallaAlumnos() {
        VtnMallaAlumno vtnMallaAlm = new VtnMallaAlumno();

        VtnMallaAlumnoCTR ctrMalla = new VtnMallaAlumnoCTR(vtnPrin, vtnMallaAlm, conecta, this, rolSeleccionado);
        ctrMalla.iniciar();
    }

    private void abrirVtnDocenteMateria() {
        VtnDocenteMateria vtn = new VtnDocenteMateria();
        eventoInternal(vtn);
        if (numVtns < 5) {
            VtnDocenteMateriaCTR ctrVtn = new VtnDocenteMateriaCTR(vtnPrin, vtn, conecta, this, rolSeleccionado);
            ctrVtn.iniciar();
        }
    }

    //Para abrir todos los formularios
    private void abrirFrmPersona() {
        FrmPersona frmPersona = new FrmPersona();
        eventoInternal(frmPersona);
        if (numVtns < 5) {
            FrmPersonaCTR ctrFrmPersona = new FrmPersonaCTR(vtnPrin, frmPersona, conecta, this);
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
            FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(vtnPrin, frmDocente, conecta, this);
            ctrFrmDocente.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirFrmAlumno() {
        FrmAlumno frmAlumno = new FrmAlumno();
        eventoInternal(frmAlumno);
        if (numVtns < 5) {
            FrmAlumnoCTR ctrFrmAlumno = new FrmAlumnoCTR(vtnPrin, frmAlumno, conecta, this, rolSeleccionado);
            ctrFrmAlumno.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirFrmCarrera() {
        FrmCarrera frmCarrera = new FrmCarrera();
        eventoInternal(frmCarrera);
        if (numVtns < 5) {
            FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(vtnPrin, frmCarrera, conecta, this);
            ctrFrmCarrera.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirFrmCurso() {
        FrmCurso frmCurso = new FrmCurso();
        eventoInternal(frmCurso);
        if (numVtns < 5) {
            FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(vtnPrin, frmCurso, conecta, this);
            ctrFrmCurso.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    private void abrirFrmPrdLectivo() {
        FrmPrdLectivo frmPrdLectivo = new FrmPrdLectivo();
        eventoInternal(frmPrdLectivo);
        if (numVtns < 5) {
            FrmPrdLectivoCTR ctrFrmPrdLectivo = new FrmPrdLectivoCTR(vtnPrin, frmPrdLectivo, conecta, this);
            ctrFrmPrdLectivo.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    private void abrirFrmInscripcion() {
        FrmAlumnoCarrera frmMatricula = new FrmAlumnoCarrera();
        eventoInternal(frmMatricula);
        if (numVtns < 5) {
            FrmAlumnoCarreraCTR ctrFrmAlumn = new FrmAlumnoCarreraCTR(vtnPrin, frmMatricula, conecta, this);
            ctrFrmAlumn.iniciar();
        }
    }

    private void abrirFrmMatricula() {
        FrmAlumnoCurso frmAlmCurso = new FrmAlumnoCurso();
        eventoInternal(frmAlmCurso);
        if (numVtns < 5) {
            FrmAlumnoCursoCTR ctrFrmMatri = new FrmAlumnoCursoCTR(vtnPrin, frmAlmCurso, conecta, this);
            ctrFrmMatri.iniciar();
        }
    }

    private void abrirFrmDocenteMateria() {
        FrmDocenteMateria frm = new FrmDocenteMateria();
        eventoInternal(frm);
        if (numVtns < 5) {
            FrmDocenteMateriaCTR ctrFrm = new FrmDocenteMateriaCTR(vtnPrin, frm, conecta, this);
            ctrFrm.iniciar();
        }
    }
    
    private void controladorSilabo(){
        
        ControladorSilabos c= new ControladorSilabos(usuario,vtnPrin);
        
        c.iniciarControlador();
    
    }

    private void abrirVtnNotasAlumnoCurso(ActionEvent e) {

        VtnNotasAlumnoCursoCTR vtnNotas = new VtnNotasAlumnoCursoCTR(vtnPrin, new VtnNotasAlumnoCurso(), new AlumnoCursoBD(conecta), usuario, conecta);
        vtnNotas.Init();
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

    public void estadoCargaVtn(String vtn) {
        vtnPrin.getLblEstado().setText("Inciando la ventana de " + vtn + ". Por favor espere la información se cargara en breve.");
    }

    public void estadoCargaVtnFin(String vtn) {
        vtnPrin.getLblEstado().setText("Termino de iniciarse la ventana de " + vtn + ", cualquier error comunicarse a 0968796010.");
    }

    public void estadoCargaFrm(String frm) {
        vtnPrin.getLblEstado().setText("Inciando el formulario de " + frm + " ...");
    }

    public void estadoCargaFrmFin(String frm) {
        vtnPrin.getLblEstado().setText("Formulario de " + frm + " iniciado.");
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
        VtnUsuarioCTR vtn = new VtnUsuarioCTR(vtnPrin, new VtnUsuario(), rolSeleccionado, conecta);
        vtn.Init();
    }

    private void mnCtRolesActionPerformance(ActionEvent e) {
        VtnRolCTR vtn = new VtnRolCTR(vtnPrin, new VtnRol(), new RolBD(), rolSeleccionado);
        vtn.Init();
    }

    private void btnCerrarSesionActionPerformance(ActionEvent e) {
        vtnPrin.dispose();
        LoginCTR login = new LoginCTR(new Login(), new UsuarioBD());
        login.Init();
    }

    private void btnTipoNotas(ActionEvent e) {
        VtnTipoNotasCTR vtn = new VtnTipoNotasCTR(vtnPrin, new VtnTipoNotas(), new TipoDeNotaBD(), rolSeleccionado);
        vtn.Init();
    }

    private void InitPermisos() {
        for (AccesosMD object : AccesosBD.SelectWhereACCESOROLidRol(rolSeleccionado.getId())) {
            if (object.getNombre().equals("SILABO")) {
                vtnPrin.getMnCtUsuarios().setEnabled(true);
            }
        }
    }

    public ImageIcon getIcono() {
        return icono;
    }

    public Image getIsta() {
        return ista;
    }
    
    public void setIconJIFrame(JInternalFrame jif){
        jif.setFrameIcon(icono);
    }
    
    public void setIconJDialog(JDialog jd){
        jd.setIconImage(ista);
    }
}
