
package controlador.notas_Grupo_16;

import java.util.ArrayList;
import modelo.ConectarDB;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import vista.notas_Grupo_16.VtnNotasAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Alejandro
 */
public class VtnNotasAlumnoCursoCTR {

    private final ConectarDB conecta;
    private VtnNotasAlumnoCurso vtnAc;
    private final VtnPrincipal vtnPrin;
    ArrayList<PeriodoLectivoMD> periodos;
    PeriodoLectivoBD per;

    public VtnNotasAlumnoCursoCTR(VtnPrincipal vtnPrin, VtnNotasAlumnoCurso VtnAc, ConectarDB conecta) {
        this.conecta = conecta;
        this.vtnAc = VtnAc;
        this.vtnPrin = vtnPrin;
        this.per = new PeriodoLectivoBD(conecta);
    }

    public void Init(){
        vtnPrin.getDpnlPrincipal().add(vtnAc);
        vtnAc.show();
        cargarCmbPrdLectivo();
    }
    
     private void cargarCmbPrdLectivo() {
        periodos = per.cargarPeriodos();
        if (periodos != null) {
            vtnAc.getCmb_periodolectivo().removeAllItems();
            vtnAc.getCmb_periodolectivo().addItem("Todos");
            periodos.forEach((p) -> {
                vtnAc.getCmb_periodolectivo().addItem(p.getNombre_PerLectivo());
            });
        }
    }
}
