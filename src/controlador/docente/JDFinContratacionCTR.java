package controlador.docente;

import controlador.principal.DependenciasCTR;
import controlador.principal.VtnPrincipalCTR;
import java.time.LocalDate;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import vista.docente.JDFinContratacion;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Lina
 */
public class JDFinContratacionCTR extends DependenciasCTR {

    private PeriodoLectivoBD plBD;
    private DocenteBD dc;
    private DocenteMD docentesMD;
    private String cedula;
    private final JDFinContratacion frmFinContrato;
    private static LocalDate fechaInicio;
    private DocenteMD docente;
    private DefaultTableModel mdTbl;

    public JDFinContratacionCTR(ConectarDB conecta, VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin) {
        super(conecta, vtnPrin, ctrPrin);
        this.frmFinContrato = new JDFinContratacion(vtnPrin, false);
        this.frmFinContrato.setLocationRelativeTo(vtnPrin);
        this.frmFinContrato.setVisible(true);
        this.frmFinContrato.setTitle("Fin de Contrato");

    }

}
