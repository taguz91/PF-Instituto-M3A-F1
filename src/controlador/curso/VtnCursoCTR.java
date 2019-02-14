package controlador.curso;

import vista.curso.FrmCurso;
import vista.curso.VtnCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnCursoCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnCurso vtnCurso;

    public VtnCursoCTR(VtnPrincipal vtnPrin, VtnCurso vtnCurso) {
        this.vtnPrin = vtnPrin;
        this.vtnCurso = vtnCurso;

        vtnPrin.getDpnlPrincipal().add(vtnCurso);
        vtnCurso.show();
    }

    public void iniciar() {

    }

    public void abrirFrmCurso() {
        FrmCurso frmCurso = new FrmCurso();
        FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(vtnPrin, frmCurso);
        ctrFrmCurso.iniciar();
    }

}
