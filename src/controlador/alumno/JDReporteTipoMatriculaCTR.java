package controlador.alumno;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.AlumnoMatriculaBD;
import modelo.alumno.EgresadoBD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoMD;
import utils.ToExcel;
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
        "TODAS",
        "ORDINARIA",
        "EXTRAORDINARIA",
        "ESPECIAL"
    };
    private final AlumnoMatriculaBD AMBD = AlumnoMatriculaBD.single();
    private final EgresadoBD EBD = EgresadoBD.single();

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
        vtn.getBtnNumAlumnos().addActionListener(e -> clickReporteNumeroAlumnos());
        vtn.getBtnNumAlmnJornada().addActionListener(e -> clickReporteNumeroAlumnosJornada());
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
                "TODAS"
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
        String ids = getIDSelect();
        String nombre = tipo + LocalDate.now().toString()
                .replace(":", "|")
                .replace(".", "");
        
        List<List<String>> alumnos = AMBD.getPorTipoMatricula(ids, tipo);
        List<String> cols = new ArrayList<>();
        cols.add("Cedula/Identificacion");
        cols.add("Primer Nombre");
        cols.add("Seguno Nombre");
        cols.add("Primer Apellido");
        cols.add("Segundo Apellido");
        cols.add("Fecha Matricula");
        cols.add("Tipo Matricula");
        ToExcel excel = new ToExcel();
        excel.exportarExcel(
                cols,
                alumnos,
                nombre
        );
        
    }

    private void clickReporteEgresados() {
        String ids = getIDSelect();
        List<List<String>> alumnos = EBD.getReportesEgresadosExcel(ids);
        List<String> cols = new ArrayList<>();
        cols.add("CÓDIGO DEL IST");
        cols.add("NOMBRE DEL INSTITUTO");
        cols.add("PROVINCIA");
        cols.add("CÓDIGO DE LA CARRERA");
        cols.add("CARRERA");
        cols.add("MODALIDAD DE ESTUDIOS");
        cols.add("TIPO DE IDENTIFICACIÓN");
        cols.add("NRO. DE IDENTIFICACIÓN");
        cols.add("APELLIDOS Y NOMBRES");
        cols.add("NACIONALIDAD");
        cols.add("TRABAJO DE TITULACIÓN FINALIZADO S/N");
        ToExcel excel = new ToExcel();
        excel.exportarExcel(
                cols,
                alumnos,
                "Alumnos Egresados " + ids
        );
    }

    private void clickReporteNumeroAlumnos() {
        String ids = getIDSelect();
        List<List<String>> alumnos = AMBD.getNumeroAlumnos(ids);
        List<String> cols = new ArrayList<>();
        cols.add("CARRERA");
        cols.add("PERIODO");
        cols.add("HOMBRES");
        cols.add("MUJERES");
        cols.add("TOTAL");
        ToExcel excel = new ToExcel();
        excel.exportarExcel(
                cols,
                alumnos,
                "Número Alumnos " + ids
        );
    }
    
    private void clickReporteNumeroAlumnosJornada() {
        String ids = getIDSelect();
        List<List<String>> alumnos = AMBD.getNumeroAlumnosPorJornada(ids);
        List<String> cols = new ArrayList<>();
        cols.add("CARRERA");
        cols.add("PERIODO");
        cols.add("CURSOS MATUTINA");
        cols.add("HOMBRES MATUTINA");
        cols.add("MUJERES MATUTINA");
        cols.add("ALUMNOS MATUTINA");
        cols.add("CURSOS VESPERTINA");
        cols.add("HOMBRES VESPERTINA");
        cols.add("MUJERES VESPERTINA");
        cols.add("ALUMNOS VESPERTINA");
        cols.add("CURSOS NOCTURNA");
        cols.add("HOMBRES NOCTURNA");
        cols.add("MUJERES NOCTURNA");
        cols.add("ALUMNOS NOCTURNA");
        ToExcel excel = new ToExcel();
        excel.exportarExcel(
                cols,
                alumnos,
                "Número Alumnos Jornada " + ids
        );
    }

    private String getIDSelect() {
        int[] ss = vtn.getTblPeriodos().getSelectedRows();
        String ids = "";
        for (int s : ss) {
            ids += periodos.get(s).getID() + ",";
        }
        ids = ids.substring(0, ids.length() - 1);
        return ids;
    }

}
