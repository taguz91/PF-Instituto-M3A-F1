package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.alumno.AlumnoCursoMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import vista.alumno.JDMateriasCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class JDMateriasCursoCTR {

    private final JDMateriasCurso jdMat;
    private final CursoBD cur;
    private final VtnPrincipal vtnPrin;
    private final VtnPrincipalCTR ctrPrin;
    private final AlumnoCursoMD almCurso;
    private final ConectarDB conecta;
    //Guardaremos los cursos que consultamos
    private ArrayList<CursoMD> cursos;
    private DefaultTableModel mdTbl;

    public JDMateriasCursoCTR(AlumnoCursoMD almCurso, VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin, ConectarDB conecta) {
        this.almCurso = almCurso;
        this.vtnPrin = vtnPrin;
        this.ctrPrin = ctrPrin;
        this.conecta = conecta;
        this.cur = new CursoBD(conecta);
        this.jdMat = new JDMateriasCurso(vtnPrin, false);
    }

    public void iniciar() {
        jdMat.setLocationRelativeTo(vtnPrin);
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
                almCurso.getCurso().getCurso_nombre());
        System.out.println("Numero de cursos devuletos "+cursos.size());
        llenarTbl(cursos);
    }

    private void llenarTbl(ArrayList<CursoMD> cursos) {
        mdTbl.setRowCount(0);
        if (!cursos.isEmpty()) {
            cursos.forEach(c -> {
                Object[] v = {c.getId_materia().getNombre(),
                    c.getId_docente().getPrimerNombre() + " "
                    + c.getId_docente().getPrimerApellido()};
                mdTbl.addRow(v);
            });
        }
    }

}
