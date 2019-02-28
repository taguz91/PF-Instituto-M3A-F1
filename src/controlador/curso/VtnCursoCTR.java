package controlador.curso;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import vista.curso.FrmCurso;
import vista.curso.VtnCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author arman
 */
public class VtnCursoCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnCurso vtnCurso;
    
    private CursoBD curso;
    ArrayList<CursoMD> cursos; 
    //modelo de la tabla 
    DefaultTableModel mdTbl; 

    public VtnCursoCTR(VtnPrincipal vtnPrin, VtnCurso vtnCurso) {
        this.vtnPrin = vtnPrin;
        this.vtnCurso = vtnCurso;

        vtnPrin.getDpnlPrincipal().add(vtnCurso);
        vtnCurso.show();
        
        //Inicializamos el curso  
        curso = new CursoBD();
    }

    public void iniciar() {
        //Iniciamos la tabla  
        String titulo []= {"id", "id_materia","id periodo lectivo","id docente","id jornada","nombre","capacidad", "curso"};
        String datos [][] = {}; 
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        
        TblEstilo.formatoTbl(vtnCurso.getTblCurso()); 
        
        cargarCursos();
    }

    public void abrirFrmCurso() {
        FrmCurso frmCurso = new FrmCurso();
        FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(vtnPrin, frmCurso);
        ctrFrmCurso.iniciar();
    }
    
    public void cargarCursos(){
        cursos  = (ArrayList<CursoMD>) curso.SelectAll();
        llenarTbl();
    }
    
    private void llenarTbl(){
        mdTbl.setRowCount(0); 
        cursos.forEach((c) -> {
            Object valores [] = {
                c.getId_curso(),
                c.getId_materia(),
                c.getId_prd_lectivo(),
                c.getId_docente(),
                c.getCurso_jornada(),
                c.getCurso_nombre(),
                c.getCurso_capacidad(),
                c.getCurso_ciclo()
            };
            mdTbl.addRow(valores); 
        });
        vtnCurso.getLblResultados().setText(cursos.size() + " Resultados obtenidos."); 
    }

}
