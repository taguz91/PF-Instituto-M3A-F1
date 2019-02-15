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
import controlador.persona.VtnAlumnoCTR;
import controlador.persona.VtnDocenteCTR;
import controlador.prdlectivo.FrmPrdLectivoCTR;
import controlador.prdlectivo.VtnPrdLectivoCTR;
import modelo.materia.MateriaBD;
import vista.carrera.FrmCarrera;
import vista.carrera.VtnCarrera;
import vista.curso.FrmCurso;
import vista.curso.VtnAlumnoCurso;
import vista.curso.VtnCurso;
import vista.materia.VtnMateria;
import vista.persona.FrmAlumno;
import vista.persona.FrmDocente;
import vista.persona.VtnAlumno;
import vista.persona.VtnDocente;
import vista.prdlectivo.FrmPrdLectivo;
import vista.prdlectivo.VtnPrdLectivo;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnPrincipalCTR {

    private VtnPrincipal vtnPrin;

    public VtnPrincipalCTR(VtnPrincipal vtnPrin) {
        this.vtnPrin = vtnPrin;

        vtnPrin.setVisible(true);
    }

    public void iniciar() {
        //Para el estilo 
        vtnPrin.getMnRbtnMetal().addActionListener(e -> estiloVtn());
        vtnPrin.getMnRbtnNimbus().addActionListener(e -> estiloVtn());
        vtnPrin.getMnRbtnWindows().addActionListener(e -> estiloVtn());

        //Para abrir las ventanas 
        vtnPrin.getMnCtAlmnCurso().addActionListener(e -> abrirVtnAlumnoCurso());
        vtnPrin.getMnCtAlumno().addActionListener(e -> abrirVtnAlumno());
        vtnPrin.getMnCtCarrera().addActionListener(e -> abrirVtnCarrera());
        vtnPrin.getMnCtCurso().addActionListener(e -> abrirVtnCurso());
        vtnPrin.getMnCtDocente().addActionListener(e -> abrirVtnDocente());
        vtnPrin.getMnCtMateria().addActionListener(e -> abrirVtnMateria());
        vtnPrin.getMnCtPrdLectivo().addActionListener(e -> abrirVtnPrdLectivo());
        
        vtnPrin.getBtnMateria().addActionListener(e -> abrirVtnMateria()); 

        //Para abrir los formularios 
        vtnPrin.getBtnAlumno().addActionListener(e -> abrirFrmAlumno()); 
        vtnPrin.getBtnCarrera().addActionListener(e -> abrirFrmCarrera()); 
        vtnPrin.getBtnCurso().addActionListener(e -> abrirFrmCurso());
        vtnPrin.getBtnCursoAlumno().addActionListener(e -> abrirFrmCursoAlumno());
        vtnPrin.getBtnDocente().addActionListener(e -> abrirFrmDocente());
        vtnPrin.getBtnPrdLectivo().addActionListener(e -> abrirFrmPrdLectivo());

    }

    public void abrirVtnDocente() {
        VtnDocente vtnDocente = new VtnDocente();
        VtnDocenteCTR ctrVtnDocente = new VtnDocenteCTR(vtnPrin, vtnDocente);
        ctrVtnDocente.iniciar();
    }

    public void abrirVtnAlumno() {
        VtnAlumno vtnAlumno = new VtnAlumno();
        VtnAlumnoCTR ctrVtnAlumno = new VtnAlumnoCTR(vtnPrin, vtnAlumno);
        ctrVtnAlumno.iniciar();
    }

    public void abrirVtnCarrera() {
        VtnCarrera vtnCarrera = new VtnCarrera();
        VtnCarreraCTR ctrVtnCarrera = new VtnCarreraCTR(vtnPrin, vtnCarrera);
        ctrVtnCarrera.iniciar();
    }

    public void abrirVtnCurso() {
        VtnCurso vtnCurso = new VtnCurso();
        VtnCursoCTR ctrVtnCurso = new VtnCursoCTR(vtnPrin, vtnCurso);
        ctrVtnCurso.iniciar();
    }

    public void abrirVtnPrdLectivo() {
        VtnPrdLectivo vtnPrdLectivo = new VtnPrdLectivo();
        VtnPrdLectivoCTR ctrVtnPrdLectivo = new VtnPrdLectivoCTR(vtnPrin, vtnPrdLectivo);
        ctrVtnPrdLectivo.iniciar();
    }

    public void abrirVtnAlumnoCurso() {
        VtnAlumnoCurso vtnAlmnCurso = new VtnAlumnoCurso();
        VtnAlumnoCursoCTR ctrVtnAlmnCurso = new VtnAlumnoCursoCTR(vtnPrin, vtnAlmnCurso);
        ctrVtnAlmnCurso.iniciar();
    }

    public void abrirVtnMateria() {
        MateriaBD materia = new MateriaBD();
        VtnMateria vtnMateria = new VtnMateria();
        VtnMateriaCTR ctrVtnMateria = new VtnMateriaCTR(vtnPrin, vtnMateria, materia);
        ctrVtnMateria.iniciar();
    }

    //Para abrir todos los formularios
    public void abrirFrmDocente() {
        FrmDocente frmDocente  = new FrmDocente(); 
        FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(vtnPrin, frmDocente);
        ctrFrmDocente.iniciar();
    }

    public void abrirFrmAlumno() {
        FrmAlumno frmAlumno = new FrmAlumno();
        FrmAlumnoCTR ctrFrmAlumno = new FrmAlumnoCTR(vtnPrin, frmAlumno);
        ctrFrmAlumno.iniciar();
    }

    public void abrirFrmCarrera() {
        FrmCarrera frmCarrera = new FrmCarrera(); 
        FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(vtnPrin, frmCarrera); 
        ctrFrmCarrera.iniciar();
    }

    public void abrirFrmCurso() {
        FrmCurso frmCurso = new FrmCurso();
        FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(vtnPrin, frmCurso); 
        ctrFrmCurso.iniciar();
    }

    public void abrirFrmPrdLectivo() {
        FrmPrdLectivo frmPrdLectivo = new FrmPrdLectivo(); 
        FrmPrdLectivoCTR ctrFrmPrdLectivo = new FrmPrdLectivoCTR(vtnPrin, frmPrdLectivo); 
        ctrFrmPrdLectivo.iniciar();
    }

    public void abrirFrmCursoAlumno() {

    }

    public void abrirFrmMateria() {
        FrmMateria frmMate = new FrmMateria();

        vtnPrin.getDpnlPrincipal().add(frmMate);
        frmMate.show();
    }

    public void estiloVtn() {
        String estilo = "Windows";

        if (vtnPrin.getMnRbtnMetal().isSelected()) {
            estilo = "Metal";
        } else if (vtnPrin.getMnRbtnNimbus().isSelected()) {
            estilo = "Nimbus";
        }

        vtnPrin.dispose();

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (estilo.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VtnPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        vtnPrin = new VtnPrincipal();
        vtnPrin.setVisible(true);
    }

}
