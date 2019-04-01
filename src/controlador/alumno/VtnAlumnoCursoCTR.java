package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.alumno.AlumnoCursoBD;
import modelo.alumno.AlumnoCursoMD;
import modelo.curso.CursoBD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.usuario.RolMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import vista.alumno.VtnAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnAlumnoCursoCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnAlumnoCurso vtnAlmnCurso;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private final RolMD permisos;
    //Posicion de los filtros seleccinados;
    private int posPrd, posCur;

    //Tabla  
    private DefaultTableModel mdTbl;
    //Datos
    private ArrayList<AlumnoCursoMD> almns;
    private final AlumnoCursoBD alc;
    //Cargamos los periodos
    private final PeriodoLectivoBD prd;
    private ArrayList<PeriodoLectivoMD> periodos;
    //Para cargar los cursos  
    private final CursoBD cur;
    private ArrayList<String> cursos;

    public VtnAlumnoCursoCTR(VtnPrincipal vtnPrin, VtnAlumnoCurso vtnAlmnCurso,
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.vtnAlmnCurso = vtnAlmnCurso;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;

        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Alumnos por curso");
        ctrPrin.setIconJIFrame(vtnAlmnCurso);

        this.alc = new AlumnoCursoBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
        this.cur = new CursoBD(conecta);

        vtnPrin.getDpnlPrincipal().add(vtnAlmnCurso);
        vtnAlmnCurso.show();
    }

    public void iniciar() {
        String titulo[] = {"Cédula", "Alumno", "Curso"};
        String datos[][] = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        TblEstilo.formatoTbl(vtnAlmnCurso.getTblAlumnoCurso());
        vtnAlmnCurso.getTblAlumnoCurso().setModel(mdTbl);
        //Llenamos la tabla 
        cargarAlumnosCurso();
        //Cargando los datos para combos 
        cargarCmbPrds();
        //Buscador 
        vtnAlmnCurso.getTxtbuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnAlmnCurso.getTxtbuscar().getText().trim();
                if (b.length() > 2) {
                    buscar(b);
                } else if (b.length() == 0) {
                    cargarAlumnosCurso();
                }
            }
        });
        vtnAlmnCurso.getBtnbuscar().addActionListener(e -> buscar(vtnAlmnCurso.getTxtbuscar().getText().trim()));
        //Validacion del buscador
        vtnAlmnCurso.getTxtbuscar().addKeyListener(new TxtVBuscador(vtnAlmnCurso.getTxtbuscar(),
                vtnAlmnCurso.getBtnbuscar()));
        //Acciones de los botones
        vtnAlmnCurso.getBtnIngresar().addActionListener(e -> abrirFrmCurso());
        //Acciones en los combos  
        vtnAlmnCurso.getCmbPrdLectivos().addActionListener(e -> clickCmbPrd());
        vtnAlmnCurso.getCmbCursos().addActionListener(e -> clickCmbCurso());
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Alumnos por curso");
    }

    private void buscar(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            almns = alc.buscarAlumnosCursosTbl(b);
            llenatTbl(almns);
        } else {
            System.out.println("No ingrese caracteres especiales");
        }
    }

    public void abrirFrmCurso() {
        ctrPrin.abrirFrmCurso();
        vtnAlmnCurso.dispose();
        ctrPrin.cerradoJIF();
    }

    private void cargarAlumnosCurso() {
        almns = alc.cargarAlumnosCursosTbl();
        llenatTbl(almns);
    }

    private void cargarTblPorPrd() {
        if (posPrd > 0) {
            almns = alc.cargarAlumnosCursosPorPrdTbl(periodos.get(posPrd - 1).getId_PerioLectivo());
            llenatTbl(almns);
        }
    }

    private void cargarTblPorCurso() {
        if (posCur > 0) {
            almns = alc.cargarAlumnosCursosPorCursoTbl(cursos.get(posCur - 1));
            llenatTbl(almns);
        }
    }

    private void llenatTbl(ArrayList<AlumnoCursoMD> almns) {
        mdTbl.setRowCount(0);
        if (almns != null) {
            almns.forEach(a -> {
                Object[] valores = {a.getAlumno().getIdentificacion(),
                    a.getAlumno().getPrimerNombre()
                    + " " + a.getAlumno().getPrimerApellido(),
                    a.getCurso().getCurso_nombre()};
                mdTbl.addRow(valores);
            });
            vtnAlmnCurso.getLblResultados().setText(almns.size() + " Resultados obtenidos.");
        }
    }

    private void cargarCmbPrds() {
        periodos = prd.cargarPrdParaCmb();
        vtnAlmnCurso.getCmbPrdLectivos().removeAllItems();
        if (!periodos.isEmpty()) {
            vtnAlmnCurso.getCmbPrdLectivos().addItem("Todos");
            periodos.forEach(p -> {
                vtnAlmnCurso.getCmbPrdLectivos().addItem(p.getNombre_PerLectivo());
            });
        }
    }

    private void clickCmbPrd() {
        posPrd = vtnAlmnCurso.getCmbPrdLectivos().getSelectedIndex();
        cargarCursoPorPrd();
        cargarTblPorPrd();
    }

    private void clickCmbCurso() {
        posCur = vtnAlmnCurso.getCmbCursos().getSelectedIndex();
        cargarTblPorCurso();
    }

    private void cargarCursoPorPrd() {
        if (posPrd > 0) {
            cursos = cur.cargarNombreCursosPorPeriodo(periodos.get(posPrd - 1).getId_PerioLectivo());
            vtnAlmnCurso.getCmbCursos().removeAllItems();
            if (!cursos.isEmpty()) {
                vtnAlmnCurso.getCmbCursos().addItem("Todos");
                cursos.forEach(c -> {
                    vtnAlmnCurso.getCmbCursos().addItem(c);
                });
            }
        } else {
            vtnAlmnCurso.getCmbCursos().removeAllItems();
        }
    }

    private void InitPermisos() {

        for (AccesosMD obj : AccesosBD.SelectWhereACCESOROLidRol(permisos.getId())) {

//            if (obj.getNombre().equals("USUARIOS-Agregar")) {
//                vtnCarrera.getBtnIngresar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-Editar")) {
//                vista.getBtnEditar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-Eliminar")) {
//                vista.getBtnEliminar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-AsignarRoles")) {
//                vista.getBtnAsignarRoles().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-VerRoles")) {
//                vista.getBtnVerRoles().setEnabled(true);
//            }
        }
    }
}
