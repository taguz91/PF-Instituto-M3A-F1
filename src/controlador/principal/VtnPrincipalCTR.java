package controlador.principal;

import vista.materia.FrmMateria;
import controlador.carrera.FrmCarreraCTR;
import controlador.carrera.VtnCarreraCTR;
import controlador.curso.FrmCursoCTR;
import controlador.curso.VtnAlumnoCursoCTR;
import controlador.curso.VtnCursoCTR;
import controlador.materia.VtnMateriaCTR;
import controlador.persona.FrmAlumnoCTR;
import controlador.persona.FrmDocenteCTR;
import controlador.persona.FrmPersonaCTR;
import controlador.persona.VtnAlumnoCTR;
import controlador.persona.VtnDocenteCTR;
import controlador.persona.VtnPersonaCTR;
import controlador.prdlectivo.FrmPrdLectivoCTR;
import controlador.prdlectivo.VtnPrdLectivoCTR;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import modelo.persona.DocenteBD;
import modelo.persona.UsuarioMD;
import vista.carrera.FrmCarrera;
import vista.carrera.VtnCarrera;
import vista.curso.FrmCurso;
import vista.curso.VtnAlumnoCurso;
import vista.curso.VtnCurso;
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

/**
 *
 * @author Johnny
 */
public class VtnPrincipalCTR {

    private VtnPrincipal vtnPrin;
    private final UsuarioMD usuario;

    private int numVtns = 0;
    private final String ERRORNUMVTNS = "No se pueden abrir mas de 5 ventanas";

    public VtnPrincipalCTR(VtnPrincipal vtnPrin, UsuarioMD usuario) {
        this.vtnPrin = vtnPrin;
        this.usuario = usuario;

        vtnPrin.setVisible(true);
    }

    public void iniciar() {
        //Iniciamos los shotcuts 
        /*Otra forma de poner atajos de teclado
        vtnPrin.getMnCtAlmnCurso().setAccelerator(
                KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().
                        getMenuShortcutKeyMask()));
         */
        
        iniciarAtajosTeclado();
        
        //Acciones de las ventanas de consulta
        //Para el estilo 
        vtnPrin.getMnRbtnMetal().addActionListener(e -> estiloVtn());
        vtnPrin.getMnRbtnNimbus().addActionListener(e -> estiloVtn());
        vtnPrin.getMnRbtnWindows().addActionListener(e -> estiloVtn());

        //Para abrir las ventanas 
        vtnPrin.getMnCtPersona().addActionListener(e -> abrirVtnPersona());
        vtnPrin.getMnCtAlmnCurso().addActionListener(e -> abrirVtnAlumnoCurso());
        vtnPrin.getMnCtAlumno().addActionListener(e -> abrirVtnAlumno());
        vtnPrin.getMnCtCarrera().addActionListener(e -> abrirVtnCarrera());
        vtnPrin.getMnCtCurso().addActionListener(e -> abrirVtnCurso());
        vtnPrin.getMnCtDocente().addActionListener(e -> abrirVtnDocente());
        vtnPrin.getMnCtMateria().addActionListener(e -> abrirVtnMateria());
        vtnPrin.getMnCtPrdLectivo().addActionListener(e -> abrirVtnPrdLectivo());

        vtnPrin.getBtnMateria().addActionListener(e -> abrirVtnMateria());

        //Para abrir los formularios 
        vtnPrin.getBtnPersona().addActionListener(e -> abrirFrmPersona());

        vtnPrin.getBtnAlumno().addActionListener(e -> abrirFrmAlumno());
        vtnPrin.getBtnCarrera().addActionListener(e -> abrirFrmCarrera());
        vtnPrin.getBtnCurso().addActionListener(e -> abrirFrmCurso());
        vtnPrin.getBtnCursoAlumno().addActionListener(e -> abrirFrmCursoAlumno());
        vtnPrin.getBtnDocente().addActionListener(e -> abrirFrmDocente());
        vtnPrin.getBtnPrdLectivo().addActionListener(e -> abrirFrmPrdLectivo());

        //Para los menus  
        vtnPrin.getMnIgAlumno().addActionListener(e -> abrirFrmAlumno());
        vtnPrin.getMnIgCarrera().addActionListener(e -> abrirFrmCarrera());
        vtnPrin.getMnIgCurso().addActionListener(e -> abrirFrmCurso());
        vtnPrin.getMnIgAlmnCurso().addActionListener(e -> abrirFrmCursoAlumno());
        vtnPrin.getMnIgDocente().addActionListener(e -> abrirFrmDocente());
        vtnPrin.getMnIgPersona().addActionListener(e -> abrirFrmPersona());
        vtnPrin.getMnIgPrdLectivo().addActionListener(e -> abrirFrmPrdLectivo());

    }

    public void abrirVtnPersona() {
        VtnPersona vtnPersona = new VtnPersona();
        eventoInternal(vtnPersona);
        if (numVtns < 5) {
            VtnPersonaCTR ctrVtnPersona = new VtnPersonaCTR(vtnPrin, vtnPersona);
            ctrVtnPersona.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirVtnDocente() {
        VtnDocente vtnDocente = new VtnDocente();
        eventoInternal(vtnDocente);
        if (numVtns < 5) {
            VtnDocenteCTR ctrVtnDocente = new VtnDocenteCTR(vtnPrin, vtnDocente);
            ctrVtnDocente.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnAlumno() {
        VtnAlumno vtnAlumno = new VtnAlumno();
        eventoInternal(vtnAlumno);
        if (numVtns < 5) {
            VtnAlumnoCTR ctrVtnAlumno = new VtnAlumnoCTR(vtnPrin, vtnAlumno);
            ctrVtnAlumno.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnCarrera() {
        VtnCarrera vtnCarrera = new VtnCarrera();
        eventoInternal(vtnCarrera);
        if (numVtns < 5) {
            VtnCarreraCTR ctrVtnCarrera = new VtnCarreraCTR(vtnPrin, vtnCarrera);
            ctrVtnCarrera.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnCurso() {
        VtnCurso vtnCurso = new VtnCurso();
        eventoInternal(vtnCurso);
        if (numVtns < 5) {
            VtnCursoCTR ctrVtnCurso = new VtnCursoCTR(vtnPrin, vtnCurso);
            ctrVtnCurso.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnPrdLectivo() {
        VtnPrdLectivo vtnPrdLectivo = new VtnPrdLectivo();
        eventoInternal(vtnPrdLectivo);
        if (numVtns < 5) {
            VtnPrdLectivoCTR ctrVtnPrdLectivo = new VtnPrdLectivoCTR(vtnPrin, vtnPrdLectivo);
            ctrVtnPrdLectivo.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirVtnAlumnoCurso() {
        VtnAlumnoCurso vtnAlmnCurso = new VtnAlumnoCurso();
        eventoInternal(vtnAlmnCurso);
        if (numVtns < 5) {
            VtnAlumnoCursoCTR ctrVtnAlmnCurso = new VtnAlumnoCursoCTR(vtnPrin, vtnAlmnCurso);
            ctrVtnAlmnCurso.iniciar();
        } else {

        }
        errorNumVentanas();
    }

    public void abrirVtnMateria() {
        VtnMateria vtnMateria = new VtnMateria();
        eventoInternal(vtnMateria);
        if (numVtns < 5) {
            VtnMateriaCTR ctrVtnMateria = new VtnMateriaCTR(vtnPrin, vtnMateria);
            ctrVtnMateria.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    //Para abrir todos los formularios
    public void abrirFrmPersona() {
        FrmPersona frmPersona = new FrmPersona();
        eventoInternal(frmPersona);
        if (numVtns < 5) {
            FrmPersonaCTR ctrFrmPersona = new FrmPersonaCTR(vtnPrin, frmPersona);
            ctrFrmPersona.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmDocente() {
        FrmDocente frmDocente = new FrmDocente();
        DocenteBD docente = new DocenteBD();
        eventoInternal(frmDocente);
        if (numVtns < 5) {
            frmDocente.getBtnRegistrarPersona().setVisible(false);
            FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(vtnPrin, frmDocente, docente);
            ctrFrmDocente.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmAlumno() {
        FrmAlumno frmAlumno = new FrmAlumno();
        eventoInternal(frmAlumno);
        if (numVtns < 5) {
            FrmAlumnoCTR ctrFrmAlumno = new FrmAlumnoCTR(vtnPrin, frmAlumno);
            ctrFrmAlumno.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmCarrera() {
        FrmCarrera frmCarrera = new FrmCarrera();
        eventoInternal(frmCarrera);
        if (numVtns < 5) {
            FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(vtnPrin, frmCarrera);
            ctrFrmCarrera.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmCurso() {
        FrmCurso frmCurso = new FrmCurso();
        eventoInternal(frmCurso);
        if (numVtns < 5) {
            FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(vtnPrin, frmCurso);
            ctrFrmCurso.iniciar();
        } else {
            errorNumVentanas();
        }
    }

    public void abrirFrmPrdLectivo() {
        FrmPrdLectivo frmPrdLectivo = new FrmPrdLectivo();
        eventoInternal(frmPrdLectivo);
        if (numVtns < 5) {
            FrmPrdLectivoCTR ctrFrmPrdLectivo = new FrmPrdLectivoCTR(vtnPrin, frmPrdLectivo);
            ctrFrmPrdLectivo.iniciar();
        } else {
            errorNumVentanas();
        }

    }

    public void abrirFrmCursoAlumno() {

    }

    public void abrirFrmMateria() {
        FrmMateria frmMate = new FrmMateria();
        eventoInternal(frmMate);
        if (numVtns < 5) {
            vtnPrin.getDpnlPrincipal().add(frmMate);
            frmMate.show();
        } else {
            errorNumVentanas();
        }

    }

    public void estiloVtn() {
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
            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                numVtns--;
            }

        });
    }

    public void errorNumVentanas() {
        JOptionPane.showMessageDialog(vtnPrin, "No se pueden abrir mas de 5 ventanas",
                "Error Ventana", JOptionPane.ERROR_MESSAGE);
    }

    public void iniciarAtajosTeclado() {
        vtnPrin.getMnCtAlmnCurso().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_L, ActionEvent.CTRL_MASK));

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
        vtnPrin.getMnIgAlmnCurso().setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_L, ActionEvent.ALT_MASK));

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
}
