package controlador.alumno;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoMD;
import vista.alumno.JDReporteTipoMatricula;

/**
 *
 * @author gus
 */
public class JDReporteTipoMatriculaCTR extends DCTR {

    private final List<PeriodoLectivoMD> periodos;
    // Modelo de la tabla  
    private final DefaultTableModel mdTbl = TblEstilo
            .modelTblSinEditar(new String[]{"Periodo"});
    // Ventana  
    private final JDReporteTipoMatricula vtn;
    // Opciones del combo de tipo matricula
    private final String[] TIPO_MATRICULAS = {
        "Seleccione",
        "ORDINARIA",
        "EXTRAORDINARIA",
        "ESPECIAL"
    };

    public JDReporteTipoMatriculaCTR(
            VtnPrincipalCTR ctrPrin,
            List<PeriodoLectivoMD> periodos
    ) {
        super(ctrPrin);
        this.periodos = periodos;
        this.vtn = new JDReporteTipoMatricula(ctrPrin.getVtnPrin(), false);
    }

    public void iniciar() {
        iniciarTbl();
        vtn.getBtnReporte().addActionListener(e -> seleccioneTipoMatricula());
        vtn.setLocationRelativeTo(ctrPrin.getVtnPrin());
        vtn.setVisible(true);
        ctrPrin.eventoJDCerrar(vtn);
    }

    private void iniciarTbl() {
        vtn.getTblPeriodos().setModel(mdTbl);
        periodos.forEach(p -> {
            mdTbl.addRow(new Object[]{
                p.getNombre()
            });
        });
    }    
    
    private void seleccioneTipoMatricula() {
        Object np = JOptionPane.showInputDialog(
                null,
                "Seleccione el tipo de matricula", 
                "Tipos de matricula",
                JOptionPane.QUESTION_MESSAGE, 
                null,
                TIPO_MATRICULAS, 
                "Seleccione"
        );
        
        if (np == null) {
            // No se lecciono nada 
        } else if (np.equals("Seleccione")) {
            // Cargamos todos los alumnos 
            // sin importar su tipo de matricula
        } else {
            
        }
    }

}
