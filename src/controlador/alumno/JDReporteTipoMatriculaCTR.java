package controlador.alumno;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoMD;
import utils.Descarga;
import vista.alumno.JDReporteExcel;

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
    private final JDReporteExcel vtn;
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
        this.vtn = new JDReporteExcel(ctrPrin.getVtnPrin(), false);
    }

    public void iniciar() {
        iniciarTbl();
        vtn.getBtnReporte().addActionListener(e -> seleccioneTipoMatricula());
        vtn.getBtnEgresados().addActionListener(e -> clickReporteEgresados());
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
            reporteJSON("");
        } else {
            reporteJSON(np.toString());
        }
    }

    private void reporteJSON(String tipo) {
        int[] ss = vtn.getTblPeriodos().getSelectedRows();
        String ids = "[";
        for (int s : ss) {
            ids += periodos.get(s).getID() + ",";
        }
        ids = ids.substring(0, ids.length() - 1);
        ids += "]";
        String JSON = "{\"ids_periodos\":" + ids
                + ", \"tipo_matricula\": "
                + "\"" + tipo + "\"}";

        if (tipo.length() == 0) {
            tipo = "TODOS";
        }
        String nombre = tipo + LocalDate.now().toString()
                .replace(":", "|")
                .replace(".", "");
        Descarga.excelWithPost(
                nombre,
                "matriculas/reporte/tipo-matricula",
                JSON,
                "Error al descargar el reporte "
                + "por tipo de matricula, vuelva a "
                + "intentarlo mas tarde."
        );
    }

    private void clickReporteEgresados() {
        int[] ss = vtn.getTblPeriodos().getSelectedRows();
        String ids = "[";
        for (int s : ss) {
            ids += periodos.get(s).getID() + ",";
        }
        ids = ids.substring(0, ids.length() - 1);
        ids += "]";

        String JSON = "{\"idsPeriodos\":" + ids
                + ", \"tipoReporte\": "
                + "\"EGRESADOS\"}";

        String url = "alumnos/reporte/estados/finales";
        String nombre = "Egresados" + LocalDate.now().toString()
                .replace(":", "|")
                .replace(".", "");

        Descarga.excelWithPost(
                nombre,
                url,
                JSON,
                "El reporte de egresados no lo pudimos descargar.\n"
        );
    }

}
