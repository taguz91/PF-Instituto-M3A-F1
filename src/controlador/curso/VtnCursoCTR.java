package controlador.curso;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
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
    private final ConectarDB conecta;
    
    private final CursoBD curso;
    ArrayList<CursoMD> cursos;
    //modelo de la tabla 
    DefaultTableModel mdTbl;
    //Para cargar el combo periodos  
    PeriodoLectivoBD per;
    ArrayList<PeriodoLectivoMD> periodos;
    //Para guardanos los nombres de los cursos  
    ArrayList<String> nombresC;

    public VtnCursoCTR(VtnPrincipal vtnPrin, VtnCurso vtnCurso, ConectarDB conecta) {
        this.vtnPrin = vtnPrin;
        this.vtnCurso = vtnCurso;
        this.conecta = conecta;
        this.per = new PeriodoLectivoBD(conecta);

        vtnPrin.getDpnlPrincipal().add(vtnCurso);
        vtnCurso.show();

        //Inicializamos el curso  
        curso = new CursoBD(conecta);
    }

    public void iniciar() {
        cargarCmbPrdLectio();
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
        //Le damos una accion al combo de periodos lectivos 
        vtnCurso.getCmbPeriodoLectivo().addActionListener(e -> cargarCursosPorPeriodo());
        //Le damos una accion al combo de cursos  
        vtnCurso.getCmbCurso().addActionListener(e -> cargarCursosPorNombre());
    }

    public void abrirFrmCurso() {
        FrmCurso frmCurso = new FrmCurso();
        FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(vtnPrin, frmCurso, conecta);
        ctrFrmCurso.iniciar();
    }

    private void editarCurso() {
        int fila = vtnCurso.getTblCurso().getSelectedRow();
        if (fila >= 0) {
            FrmCurso frmCurso = new FrmCurso();
            FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(vtnPrin, frmCurso, conecta);
            ctrFrmCurso.iniciar();
            ctrFrmCurso.editar(cursos.get(fila));
            vtnCurso.dispose();
        }
    }

    public void cargarCursos() {
        //Cargamos el combo con los nombres de cursos 
        nombresC = curso.cargarNombreCursos();
        cargarCmbCursos(nombresC);
        //Cargamos los cursos y llenamos la tabla
        cursos = curso.cargarCursos();
        llenarTbl(cursos);
    }

    private void cargarCursosPorPeriodo() {
        int posPrd = vtnCurso.getCmbPeriodoLectivo().getSelectedIndex();
        if (posPrd > 0) {
            //Cargamos el combo de cursos por el periodo  
            nombresC = curso.cargarNombreCursosPorPeriodo(periodos.get(posPrd - 1).getId_PerioLectivo());
            cargarCmbCursos(nombresC);
            //Cargamos los cursos por periodo
            cursos = curso.cargarCursosPorPeriodo(periodos.get(posPrd - 1).getId_PerioLectivo());
            llenarTbl(cursos);
        } else {
            cargarCursos();
        }
    }

    private void cargarCursosPorNombre() {
        int posNom = vtnCurso.getCmbCurso().getSelectedIndex();
        int posPrd = vtnCurso.getCmbPeriodoLectivo().getSelectedIndex();
        if (posNom == 0) {
            cargarCursosPorPeriodo();
        } else if (posNom > 0 && posPrd == 0) {
            cursos = curso.cargarCursosPorNombre(vtnCurso.getCmbCurso().getSelectedItem().toString());
            llenarTbl(cursos);
        } else if (posNom > 0 && posPrd > 0) {
            cursos = curso.cargarCursosPorNombreYPrdLectivo(vtnCurso.getCmbCurso().getSelectedItem().toString(),
                    periodos.get(posPrd - 1).getId_PerioLectivo());
            llenarTbl(cursos);
        }
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

    private void cargarCmbPrdLectio() {
        periodos = per.cargarPeriodos();
        if (periodos != null) {
            vtnCurso.getCmbPeriodoLectivo().removeAllItems();
            vtnCurso.getCmbPeriodoLectivo().addItem("Todos");
            periodos.forEach((p) -> {
                vtnCurso.getCmbPeriodoLectivo().addItem(p.getNombre_PerLectivo());
            });
        }
    }

    private void cargarCmbCursos(ArrayList<String> nombresC) {
        vtnCurso.getCmbCurso().removeAllItems();
        if (nombresC != null) {
            vtnCurso.getCmbCurso().addItem("Todos");
            nombresC.forEach(n -> {
                vtnCurso.getCmbCurso().addItem(n);
            });
            vtnCurso.getCmbCurso().addItem("--BUG--");
        }
    }
}
