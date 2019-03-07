package controlador.mallaalumno;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.carrera.AlumnoCarreraBD;
import modelo.carrera.AlumnoCarreraMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import modelo.mallaalumno.AlumnoCursoBD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import vista.mallaalumno.FrmAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmAlumnoCursoCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmAlumnoCurso frmAlmCurso;
    private final AlumnoCursoBD almnCurso;

    //Modelos para las tablas que seleecionan el curso 
    DefaultTableModel mdMatPen, mdMatSelec;

    //Para cargar los combos de periodo 
    private final PeriodoLectivoBD prd = new PeriodoLectivoBD();
    private ArrayList<PeriodoLectivoMD> periodos;
    //Cargar los combos de alumnos  
    private final AlumnoCarreraBD almCar = new AlumnoCarreraBD();
    private ArrayList<AlumnoCarreraMD> alumnos;
    //Cargar los cursos  
    private final CursoBD cur = new CursoBD();
    private ArrayList<CursoMD> cursosPen;
    private ArrayList<String> nombreCursos;
    //Para guardarlas materias que registraremos en base de datos  
    private ArrayList<CursoMD> cursosSelec = new ArrayList();

    public FrmAlumnoCursoCTR(VtnPrincipal vtnPrin, FrmAlumnoCurso frmAlmCurso) {
        this.vtnPrin = vtnPrin;
        this.frmAlmCurso = frmAlmCurso;
        almnCurso = new AlumnoCursoBD();

        vtnPrin.getDpnlPrincipal().add(frmAlmCurso);
        frmAlmCurso.show();
    }

    public void iniciar() {
        //Cargamos el combo con los periodos 
        cargarCmbPrdLectivo();

        //Pasamos los modelos a las tablas 
        String[] titulo1 = {"Materias no seleccionadas"};
        String[] titulo2 = {"Materias seleccionadas"};
        String[][] datos1 = {};
        String[][] datos2 = {};
        mdMatPen = TblEstilo.modelTblSinEditar(datos1, titulo1);
        mdMatSelec = TblEstilo.modelTblSinEditar(datos2, titulo2);
        frmAlmCurso.getTblMateriasPen().setModel(mdMatPen);
        frmAlmCurso.getTblMateriasSelec().setModel(mdMatSelec);
        TblEstilo.formatoTbl(frmAlmCurso.getTblMateriasPen());
        TblEstilo.formatoTbl(frmAlmCurso.getTblMateriasSelec());

        //Acciones de los combos  
        frmAlmCurso.getCmbPrdLectivo().addActionListener(e -> clickPrdLectivo());
        frmAlmCurso.getCmbCurso().addActionListener(e -> cargarMaterias());
        //Le damos una accion a los botones de clase
        frmAlmCurso.getBtnPasar1().addActionListener(e -> pasarUnaMateria());
        frmAlmCurso.getBtnPasarTodos().addActionListener(e -> pasarTodasMaterias());
        frmAlmCurso.getBtnRegresar1().addActionListener(e -> regresarUnaMateria());
        frmAlmCurso.getBtnRegresarTodos().addActionListener(e -> regresarTodasMaterias());

    }

    private void cargarCmbPrdLectivo() {
        periodos = prd.cargarPeriodos();
        if (periodos != null) {
            frmAlmCurso.getCmbPrdLectivo().removeAllItems();
            frmAlmCurso.getCmbPrdLectivo().addItem("Seleccione");
            periodos.forEach((p) -> {
                frmAlmCurso.getCmbPrdLectivo().addItem(p.getNombre_PerLectivo());
            });
        }
    }

    private void clickPrdLectivo() {
        int posPrd = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        if (posPrd > 0) {
            cargarCmbAlumnos(posPrd);
            cargarCmbCursos(posPrd);
        }
    }

    private void cargarCmbAlumnos(int posPrd) {
        frmAlmCurso.getCmbAlumnos().removeAllItems();
        alumnos = almCar.cargarAlumnoCarreraPorCarrera(
                periodos.get(posPrd - 1).getCarrera().getId());
        if (alumnos != null) {
            frmAlmCurso.getCmbAlumnos().addItem("Seleccione");
            alumnos.forEach(a -> {
                frmAlmCurso.getCmbAlumnos().addItem(
                        a.getAlumno().getPrimerApellido() + " "
                        + a.getAlumno().getSegundoApellido() + " "
                        + a.getAlumno().getPrimerNombre() + " "
                        + a.getAlumno().getSegundoNombre());
            });
        }

    }

    private void cargarCmbCursos(int posPrd) {
        frmAlmCurso.getCmbCurso().removeAllItems();
        nombreCursos = cur.cargarNombreCursosPorPeriodo(periodos.get(posPrd - 1).getId_PerioLectivo());
        if (nombreCursos != null) {
            frmAlmCurso.getCmbCurso().addItem("Seleccione");
            nombreCursos.forEach(c -> {
                frmAlmCurso.getCmbCurso().addItem(c);
            });
        }
    }

    private void cargarMaterias() {
        int posPrd = frmAlmCurso.getCmbPrdLectivo().getSelectedIndex();
        int posCurso = frmAlmCurso.getCmbCurso().getSelectedIndex();
        //Borramos los datos existentes
        
        if (posPrd > 0 && posCurso > 0) {
            cursosPen = cur.cargarCursosPorNombreYPrdLectivo(
                    frmAlmCurso.getCmbCurso().getSelectedItem().toString(),
                    periodos.get(posPrd - 1).getId_PerioLectivo());
            llenarTblMatPen(cursosPen);
        }
    }
    
    private void llenarTblMatPen(ArrayList<CursoMD> cursos){
        mdMatPen.setRowCount(0);
        if (cursos != null) {
            cursos.forEach(c -> {
                Object[] valores = {c.getId_materia().getNombre()};
                mdMatPen.addRow(valores);
            });
        }
    }
    
    private void llenarTblMatSelec(ArrayList<CursoMD> cursosSelec){
        mdMatSelec.setRowCount(0);
        if (cursosSelec != null) {
            cursosSelec.forEach(c -> {
                Object[] valores = {c.getId_materia().getNombre()};
                mdMatSelec.addRow(valores);
            });
        }
    }
    
    private void pasarUnaMateria(){
        int posMat = frmAlmCurso.getTblMateriasPen().getSelectedRow(); 
        if (posMat >= 0) {
            //Agregamos en la lista
            cursosSelec.add(cursosPen.get(posMat));
            //Quitamos de la lista que los almacena 
            cursosPen.remove(posMat); 
            //Rellenamos las dos tablas con los item seleccionados
            llenarTblMatPen(cursosPen);
            llenarTblMatSelec(cursosSelec);
        }
    }
    
    private void pasarTodasMaterias(){
        //Pasamos todos las materias que nos quedan en la tabla cursos 
        //La rrellenamos en cursos seleccionados
        cursosPen.forEach(c -> cursosSelec.add(c)); 
        //Borramos todas las materias de cursos 
        cursosPen = new ArrayList(); 
        llenarTblMatPen(cursosPen);
        llenarTblMatSelec(cursosSelec);
    }
    
    private void regresarUnaMateria(){
        int posMat = frmAlmCurso.getTblMateriasSelec().getSelectedRow(); 
        if (posMat >= 0) {
            //Pasamos u cursod e selecciona a la lista de no seleccinados
            cursosPen.add(cursosSelec.get(posMat)); 
            //Eliminamos la materia que fue pasada 
            cursosSelec.remove(posMat); 
            //Volvemos a llenar las tablas
            llenarTblMatPen(cursosPen);
            llenarTblMatSelec(cursosSelec);
        }
    }
    
    private void regresarTodasMaterias(){
        cursosSelec.forEach(c -> cursosPen.add(c));
        //Reiniciamos el array para borrar todos los datos
        cursosSelec = new ArrayList(); 
        llenarTblMatPen(cursosPen);
        llenarTblMatSelec(cursosSelec);
    }

}
