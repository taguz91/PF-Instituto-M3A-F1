package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.alumno.AlumnoCursoBD;
import modelo.alumno.AlumnoCursoMD;
import modelo.curso.CursoBD;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaBD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.usuario.RolMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.alumno.VtnAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 * Aqui se visualiza el listado de alumnos por curso.
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
    private int posPrd, posCur, posCiclo;

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
    //Ciclos de una carrera 
    private ArrayList<Integer> ciclos;
    private final MateriaBD mat;

    /**
     * En el constructor se inician todas las dependencias de base de datos.
     *
     * @param vtnPrin
     * @param vtnAlmnCurso
     * @param conecta
     * @param ctrPrin
     * @param permisos
     */
    public VtnAlumnoCursoCTR(VtnPrincipal vtnPrin, VtnAlumnoCurso vtnAlmnCurso,
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.vtnAlmnCurso = vtnAlmnCurso;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;

        this.alc = new AlumnoCursoBD(conecta);
        this.prd = new PeriodoLectivoBD(conecta);
        this.cur = new CursoBD(conecta);
        this.mat = new MateriaBD(conecta);

        vtnPrin.getDpnlPrincipal().add(vtnAlmnCurso);
        vtnAlmnCurso.show();
    }

    /**
     * Iniciamos todas las dependencias de la ventana
     */
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
                if (e.getKeyCode() == 10) {
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
        vtnAlmnCurso.getCmbCiclo().addActionListener(e -> clickCmbCiclo());
        //Le damos la accion al boton
        vtnAlmnCurso.getBtnMaterias().addActionListener(e -> materiasCurso());
        //llamar al reporte
        vtnAlmnCurso.getBtnRepAlum().addActionListener(e -> validaComboReporte());
        vtnAlmnCurso.getBtnListaCiclo().addActionListener(e -> validaComboReporteCiclo());
    }

    /**
     * Se busca los alumnos, unicamente busca si se ingresa letras o numero no
     * permiten caracteres especiales.
     *
     * @param b String
     */
    private void buscar(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            almns = alc.buscarAlumnosCursosTbl(b);
            llenatTbl(almns);
        } else {
            System.out.println("No ingrese caracteres especiales");
        }
    }

    /**
     * Abrimos el formulario para la matricula de un estudiante.
     */
    public void abrirFrmCurso() {
        ctrPrin.abrirFrmMatricula();
        vtnAlmnCurso.dispose();
        ctrPrin.cerradoJIF();
    }

    /**
     * Cargamos todos los alumnos que estan matriculados en un curso.
     */
    private void cargarAlumnosCurso() {
        almns = alc.cargarAlumnosCursosTbl();
        llenatTbl(almns);
    }

    /**
     * Cargamos la informacion por periodo lectivo.
     */
    private void cargarTblPorPrd() {
        if (posPrd > 0) {
            almns = alc.cargarAlumnosCursosPorPrdTbl(periodos.get(posPrd - 1).getId_PerioLectivo());
            llenatTbl(almns);
        }
    }

    private void cargarTblPorCiclo() {
        if (posCiclo > 0) {
            almns = alc.cargarAlumnosCursosPorCicloTbl(ciclos.get(posCiclo - 1),
                    periodos.get(posPrd - 1).getId_PerioLectivo());
            llenatTbl(almns);
        }
    }

    /**
     * Cargamos la informacion por curso y periodo lectivo
     */
    private void cargarTblPorCurso() {
        if (posCur > 0) {
            almns = alc.cargarAlumnosCursosPorCursoTbl(cursos.get(posCur - 1),
                    periodos.get(posPrd - 1).getId_PerioLectivo());
            llenatTbl(almns);
        }
    }

    /**
     * Llenamos la tabla, con la informacion requerida.
     *
     * @param almns ArrayList<AlumnoCursMD>: Alumnos que se cargaran en la tabla
     */
    private void llenatTbl(ArrayList<AlumnoCursoMD> almns) {
        mdTbl.setRowCount(0);
        if (almns != null) {
            almns.forEach(a -> {
                Object[] valores = {a.getAlumno().getIdentificacion(),
                    a.getAlumno().getPrimerNombre()
                    + " " + a.getAlumno().getPrimerApellido(),
                    a.getCurso().getNombre()};
                mdTbl.addRow(valores);
            });
            vtnAlmnCurso.getLblResultados().setText(almns.size() + " Resultados obtenidos.");
        }
    }

    /**
     * Cargamos las carrerar en el combo para los filtros.
     */
    private void cargarCmbPrds() {
        periodos = prd.cargarPrdParaCmbVtn();
        vtnAlmnCurso.getCmbPrdLectivos().removeAllItems();
        if (!periodos.isEmpty()) {
            vtnAlmnCurso.getCmbPrdLectivos().addItem("Todos");
            periodos.forEach(p -> {
                vtnAlmnCurso.getCmbPrdLectivos().addItem(p.getNombre_PerLectivo());
            });
        }
    }

    private void cargarCmbCiclo() {
        ciclos = mat.cargarCiclosCarrera(periodos.get(posPrd - 1).getCarrera().getId());
        if (ciclos != null) {
            vtnAlmnCurso.getCmbCiclo().removeAllItems();
            vtnAlmnCurso.getCmbCiclo().addItem("Todos");
            ciclos.forEach((c) -> {
                vtnAlmnCurso.getCmbCiclo().addItem(c + "");
            });
        }
    }

    private void clickCmbCiclo() {
        posCiclo = vtnAlmnCurso.getCmbCiclo().getSelectedIndex();
        cargarTblPorCiclo();
    }

    private void clickCmbPrd() {
        posPrd = vtnAlmnCurso.getCmbPrdLectivos().getSelectedIndex();
        cargarCmbCiclo();
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
            if (cursos != null) {
                vtnAlmnCurso.getCmbCursos().addItem("Todos");
                cursos.forEach(c -> {
                    vtnAlmnCurso.getCmbCursos().addItem(c);
                });
            }
        } else {
            vtnAlmnCurso.getCmbCursos().removeAllItems();
        }
    }

    private void materiasCurso() {
        int posFila = vtnAlmnCurso.getTblAlumnoCurso().getSelectedRow();
        if (posFila >= 0) {
            JDMateriasCursoCTR ctrM = new JDMateriasCursoCTR(almns.get(posFila), vtnPrin, ctrPrin, conecta);
            ctrM.iniciar();
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "Debe seleccionar un alumno primero.");
        }
    }

    public void reporteAlumno() {
        JasperReport jr;
        String path = "/vista/reportes/repAlumTodoCurso.jasper";
        try {
            Map parametro = new HashMap();
            parametro.put("periodo", vtnAlmnCurso.getCmbPrdLectivos().getSelectedItem());
            parametro.put("curso", vtnAlmnCurso.getCmbCursos().getSelectedItem());
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            conecta.mostrarReporte(jr, parametro, "Reporte de Malla de Alumno");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }
   
    public void validaComboReporte() {
        int pos1 = vtnAlmnCurso.getCmbPrdLectivos().getSelectedIndex();
        int pos2 = vtnAlmnCurso.getCmbCursos().getSelectedIndex();
        if (pos1 <= 0 || pos2 <= 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un periodo y curso");
        } else {
            reporteAlumno();
        }
    }
public void reporte() {
        JasperReport jr;
        String path = "/vista/reportes/repListaAlumCiclo.jasper";
        try {
            Map parametro = new HashMap();
            parametro.put("periodo", vtnAlmnCurso.getCmbPrdLectivos().getSelectedItem());
            parametro.put("ciclo",vtnAlmnCurso.getCmbCiclo().getSelectedIndex());
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            conecta.mostrarReporte(jr, parametro, "Reporte de Malla de Alumno");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }
public void validaComboReporteCiclo() {
        int pos1 = vtnAlmnCurso.getCmbPrdLectivos().getSelectedIndex();
        int pos2 = vtnAlmnCurso.getCmbCiclo().getSelectedIndex();
        if (pos1 <= 0 || pos2 <= 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un periodo y un ciclo");
        } else {
            reporte();
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
