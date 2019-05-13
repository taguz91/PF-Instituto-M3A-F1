package controlador.alumno;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.AlumnoCursoMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import vista.alumno.JDMateriasCurso;

/**
 *
 * @author Johnny
 */
public class JDMateriasCursoCTR extends DCTR {

    private final JDMateriasCurso jdMat;
    private final CursoBD cur;
    private final AlumnoCursoMD almCurso;
    //Guardaremos los cursos que consultamos
    private ArrayList<CursoMD> cursos;
    private DefaultTableModel mdTbl;

    public JDMateriasCursoCTR(AlumnoCursoMD almCurso, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.almCurso = almCurso;
        this.cur = new CursoBD(ctrPrin.getConecta());
        this.jdMat = new JDMateriasCurso(ctrPrin.getVtnPrin(), false);
    }

    public void iniciar() {
        jdMat.setLocationRelativeTo(ctrPrin.getVtnPrin());
        jdMat.setVisible(true);

        String[] titulo = {"Materia", "Docente"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        jdMat.getTblMaterias().setModel(mdTbl);
        TblEstilo.formatoTblConColor(jdMat.getTblMaterias());
        //Buscamos toda la informacion
        buscar();
        ctrPrin.eventoJDCerrar(jdMat);
    }

    private void buscar() {
        cursos = cur.buscarCursosPorAlumno(almCurso.getAlumno().getIdentificacion(),
                almCurso.getCurso().getNombre());
        System.out.println("Numero de cursos devuletos " + cursos.size());
        llenarTbl(cursos);
    }

    private void llenarTbl(ArrayList<CursoMD> cursos) {
        mdTbl.setRowCount(0);
        if (!cursos.isEmpty()) {
            cursos.forEach(c -> {
                Object[] v = {c.getMateria().getNombre(),
                    c.getDocente().getPrimerNombre() + " "
                    + c.getDocente().getPrimerApellido()};
                mdTbl.addRow(v);
            });
        }
    }

}
