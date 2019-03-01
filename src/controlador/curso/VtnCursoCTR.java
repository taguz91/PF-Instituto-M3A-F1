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
        String titulo[] = {"id", "Periodo", "Materia", "Docente", "Ciclo", "Curso", "Capacidad"};
        String datos[][] = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        TblEstilo.formatoTbl(vtnCurso.getTblCurso());
        vtnCurso.getTblCurso().setModel(mdTbl);
        TblEstilo.ocualtarID(vtnCurso.getTblCurso());
        //le pasamos un tamaño a las columnas
        TblEstilo.columnaMedida(vtnCurso.getTblCurso(), 1, 150);
        TblEstilo.columnaMedida(vtnCurso.getTblCurso(), 4, 60);
        TblEstilo.columnaMedida(vtnCurso.getTblCurso(), 5, 60);
        TblEstilo.columnaMedida(vtnCurso.getTblCurso(), 6, 70);

        cargarCursos();

        vtnCurso.getBtnIngresar().addActionListener(e -> abrirFrmCurso());
        vtnCurso.getBtnEditar().addActionListener(e -> editarCurso());
    }

    public void abrirFrmCurso() {
        FrmCurso frmCurso = new FrmCurso();
        FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(vtnPrin, frmCurso);
        ctrFrmCurso.iniciar();
        
        
    }

    private void editarCurso() {
        int fila = vtnCurso.getTblCurso().getSelectedRow();
        if (fila >= 0) {
            FrmCurso frmCurso = new FrmCurso();
            FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(vtnPrin, frmCurso);
            ctrFrmCurso.iniciar();
            ctrFrmCurso.editar(cursos.get(fila));
            vtnCurso.dispose(); 
        }
    }

    public void cargarCursos() {
        cursos = curso.cargarCursos();

        llenarTbl(cursos);
    }

    private void llenarTbl(ArrayList<CursoMD> cursos) {
        mdTbl.setRowCount(0);
        if (cursos != null) {
            cursos.forEach((c) -> {
                Object valores[] = {
                    c.getId_curso(),
                    c.getId_prd_lectivo().getNombre_PerLectivo(),
                    c.getId_materia().getNombre(),
                    c.getId_docente().getPrimerNombre() + " "
                    + c.getId_docente().getPrimerApellido(),
                    c.getCurso_ciclo(),
                    c.getCurso_nombre(),
                    c.getCurso_capacidad()
                };
                mdTbl.addRow(valores);
            });
            vtnCurso.getLblResultados().setText(cursos.size() + " Resultados obtenidos.");
        } else {
            vtnCurso.getLblResultados().setText("0 Resultados obtenidos.");
        }
    }

}
