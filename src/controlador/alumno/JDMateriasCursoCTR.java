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
    
    /**
     * Un jd de informacion en el que indicamos 
     * las materias y profesores que tienen en este curso.
     * @param almCurso
     * @param ctrPrin 
     */
    public JDMateriasCursoCTR(AlumnoCursoMD almCurso, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.almCurso = almCurso;
        this.cur = new CursoBD(ctrPrin.getConecta());
        this.jdMat = new JDMateriasCurso(ctrPrin.getVtnPrin(), false);
    }
    
    /**
     * Iniciamos todas las dependecias de esta ventana 
     * Agregamos el jd a la ventana
     */
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
    
    /**
     * Buscamos todos los cursos de este alumno 
     * por nombre de curso
     */
    private void buscar() {
        cursos = cur.buscarCursosPorAlumno(almCurso.getAlumno().getIdentificacion(),
                almCurso.getCurso().getNombre());
        llenarTbl(cursos);
    }
    
    /**
     * Llenamos la tabla con la info que nos devuelve.
     * @param cursos 
     */
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
