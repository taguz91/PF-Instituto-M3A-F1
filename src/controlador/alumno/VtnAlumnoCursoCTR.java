package controlador.alumno;

import controlador.Libraries.Effects;
import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.CONS;
import modelo.alumno.AlumnoCursoBD;
import modelo.alumno.AlumnoCursoMD;
import modelo.curso.CursoBD;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaBD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.alumno.VtnAlumnoCurso;

/**
 * Aqui se visualiza el listado de alumnos por curso.
 *
 * @author Johnny
 */
public class VtnAlumnoCursoCTR extends DVtnCTR {

    private final VtnAlumnoCurso vtnAlmnCurso;
    //Posicion de los filtros seleccinados;
    private int posPrd, posCur, posCiclo;

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
     * @param vtnAlmnCurso
     * @param ctrPrin
     */
    public VtnAlumnoCursoCTR(VtnAlumnoCurso vtnAlmnCurso, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.vtnAlmnCurso = vtnAlmnCurso;

        this.alc = new AlumnoCursoBD(ctrPrin.getConecta());
        this.prd = new PeriodoLectivoBD(ctrPrin.getConecta());
        this.cur = new CursoBD(ctrPrin.getConecta());
        this.mat = new MateriaBD(ctrPrin.getConecta());
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
        //Acciones en los combos
        vtnAlmnCurso.getCmbPrdLectivos().addActionListener(e -> clickCmbPrd());
        vtnAlmnCurso.getCmbCursos().addActionListener(e -> clickCmbCurso());
        vtnAlmnCurso.getCmbCiclo().addActionListener(e -> clickCmbCiclo());
        //Le damos la accion al boton
        vtnAlmnCurso.getBtnMaterias().addActionListener(e -> materiasCurso());
        //llamar al reporte
        vtnAlmnCurso.getBtnRepAlum().addActionListener(e -> validaComboReporte());
        vtnAlmnCurso.getBtnListaCiclo().addActionListener(e -> validaComboReporteCiclo());
         vtnAlmnCurso.getBtnListaPeriodo().addActionListener(e -> ListaAlumnosPeriodo());
        vtnAlmnCurso.getBtnRepUBE().addActionListener(e -> btnUBE(e));

        ctrPrin.agregarVtn(vtnAlmnCurso);
        InitPermisos();
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
            almns = alc.cargarAlumnosCursosPorPrdTbl(periodos.get(posPrd - 1).getID());
            llenatTbl(almns);
        }
    }

    /**
     * Si seleecionamos un ciclo se carga los alumnos por cada ciclo
     */
    private void cargarTblPorCiclo() {
        if (posCiclo > 0) {
            almns = alc.cargarAlumnosCursosPorCicloTbl(ciclos.get(posCiclo - 1),
                    periodos.get(posPrd - 1).getID());
            llenatTbl(almns);
        }
    }

    /**
     * Cargamos la informacion por curso y periodo lectivo
     */
    private void cargarTblPorCurso() {
        if (posCur > 0) {
            almns = alc.cargarAlumnosCursosPorCursoTbl(cursos.get(posCur - 1),
                    periodos.get(posPrd - 1).getID());
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
                vtnAlmnCurso.getCmbPrdLectivos().addItem(p.getNombre());
            });
        }
    }

    /**
     * LLenamos el combo de ciclos por carrera
     */
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

    /**
     * Al hacer click en un ciclo se cargan todos los alumnos por ciclo
     */
    private void clickCmbCiclo() {
        posCiclo = vtnAlmnCurso.getCmbCiclo().getSelectedIndex();
        cargarTblPorCiclo();
    }

    /**
     * Al hacer click en un periodo se consultan todos sus ciclos y todos los
     * cursos que tiene este periodo
     */
    private void clickCmbPrd() {
        posPrd = vtnAlmnCurso.getCmbPrdLectivos().getSelectedIndex();
        cargarCmbCiclo();
        cargarCursoPorPrd();
        cargarTblPorPrd();
    }

    /**
     * Accion al hacer click en el combo de curso y llenamos la tabla por curso
     */
    private void clickCmbCurso() {
        posCur = vtnAlmnCurso.getCmbCursos().getSelectedIndex();
        cargarTblPorCurso();
    }

    /**
     * Llenamos el combo de cursos con todo los cursos que se abran en este
     * periodo
     */
    private void cargarCursoPorPrd() {
        if (posPrd > 0) {
            cursos = cur.cargarNombreCursosPorPeriodo(periodos.get(posPrd - 1).getID());
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

    /**
     * Mostramos el JD con informacion de todas las materias que tomo en este
     * curso
     */
    private void materiasCurso() {
        posFila = vtnAlmnCurso.getTblAlumnoCurso().getSelectedRow();
        if (posFila >= 0) {
            JDMateriasCursoCTR ctrM = new JDMateriasCursoCTR(almns.get(posFila), ctrPrin);
            ctrM.iniciar();
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar un alumno primero.");
        }
    }

    /**
     * Llamamos el reporte de alumnos por curso
     */
    public void reporteAlumno() {
        JasperReport jr;
        String path = "/vista/reportes/repAlumTodoCurso.jasper";
        try {
            Map parametro = new HashMap();
            parametro.put("periodo", vtnAlmnCurso.getCmbPrdLectivos().getSelectedItem());
            parametro.put("curso", vtnAlmnCurso.getCmbCursos().getSelectedItem());
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Malla de Alumno");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }

    /**
     * Validamos para poder abrir el reporte de lista de alumnos
     */
    public void validaComboReporte() {
        int pos1 = vtnAlmnCurso.getCmbPrdLectivos().getSelectedIndex();
        int pos2 = vtnAlmnCurso.getCmbCursos().getSelectedIndex();
        if (pos1 <= 0 || pos2 <= 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un periodo y curso");
        } else {
            reporteAlumno();
        }
    }

    /**
     * Llamamos el reporte de lista de alumnos
     */
    public void reporte() {
        JasperReport jr;
        String path = "/vista/reportes/repListaAlumCiclo.jasper";
        try {
            Map parametro = new HashMap();
            parametro.put("periodo", vtnAlmnCurso.getCmbPrdLectivos().getSelectedItem());
            parametro.put("ciclo", vtnAlmnCurso.getCmbCiclo().getSelectedIndex());
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte Lista de Alumnos");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }
 public void ListaAlumnosPeriodo() {
        JasperReport jr;
        String path = "/vista/reportes/repListaAlumPeriodo.jasper";
        int posCMB= vtnAlmnCurso.getCmbPrdLectivos().getSelectedIndex();
        if(posCMB<=0){
            JOptionPane.showMessageDialog(null, "Seleccione un periodo");
        }else{
           try {
            Map parametro = new HashMap();
            parametro.put("periodo", vtnAlmnCurso.getCmbPrdLectivos().getSelectedItem());
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte Lista de Alumnos");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        } 
        }
    }
    /**
     * Validamos para que se pueda abrir el reporte por periodo y ciclo
     */
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
       vtnAlmnCurso.getBtnMaterias().getAccessibleContext().setAccessibleName("Lista-Alumnos-Materias");
       vtnAlmnCurso.getBtnRepUBE().getAccessibleContext().setAccessibleName("Lista-Alumnos-Reporte-UBE");
       vtnAlmnCurso.getBtnListaCiclo().getAccessibleContext().setAccessibleName("Lista-Alumnos-Reporte-Lista Ciclo");
       vtnAlmnCurso.getBtnRepAlum().getAccessibleContext().setAccessibleName("Lista-Alumnos-Reporte-Lista Curso");
       
        CONS.activarBtns(vtnAlmnCurso.getBtnMaterias(), vtnAlmnCurso.getBtnRepUBE(),
                vtnAlmnCurso.getBtnListaCiclo(), vtnAlmnCurso.getBtnRepAlum());
    }
    
     private void btnUBE(ActionEvent e) {
        new Thread(() -> {

            int r = JOptionPane.showOptionDialog(vtnAlmnCurso, "Reporte individual\n" + "¿Elegir el tipo de Reporte?",
                    "REPORTE UBE", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new Object[]{"menos de 70 hasta interciclo", "menos de 70 final de ciclo", "Nota final"
                       },
                    "Cancelar");

            Effects.setLoadCursor(vtnAlmnCurso);

            //ReportesCTR reportes = new ReportesCTR(vista, getIdDocente());

            switch (r) {
                case 0:

                    llamaReporteUBEmenos70hastainterciclo();
                    break;

                case 1:
                    llamaReporteUBEmenos70despuesinterciclo();

                    break;

                case 2:

                   llamaReporteUBEcompleto();

                    break;

                

                default:
                    break;
            }

            try {
                sleep(500);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            Effects.setDefaultCursor(vtnAlmnCurso);
            //vista..setEnabled(true);
        }).start();
    }
       public void llamaReporteUBEmenos70hastainterciclo() {
        JasperReport jr;
        String path = "/vista/reportes/repUBNotasAporte1.jasper";
        posFila = vtnAlmnCurso.getTblAlumnoCurso().getSelectedRow();
        if (posFila >= 0) {
            try {

                Map parametro = new HashMap();
                parametro.put("cedulaalumno", almns.get(posFila).getAlumno().getIdentificacion());
                parametro.put("periodolectivo", vtnAlmnCurso.getCmbPrdLectivos().getSelectedItem().toString());
                System.out.println(parametro);
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte UBE");

            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "error" + ex);
            }
        } else {
            JOptionPane.showMessageDialog(vtnAlmnCurso, "Seleecione una fila primero.");
        }

    }
       
     public void llamaReporteUBEmenos70despuesinterciclo() {
        JasperReport jr;
        String path = "/vista/reportes/repUBNotasAporte2.jasper";
        posFila = vtnAlmnCurso.getTblAlumnoCurso().getSelectedRow();
        if (posFila >= 0) {
            try {

                Map parametro = new HashMap();
                parametro.put("cedulaalumno", almns.get(posFila).getAlumno().getIdentificacion());
                parametro.put("periodolectivo", vtnAlmnCurso.getCmbPrdLectivos().getSelectedItem().toString());
                System.out.println(parametro);
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte UBE");

            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "error" + ex);
            }
        } else {
            JOptionPane.showMessageDialog(vtnAlmnCurso, "Seleecione una fila primero.");
        }

    }  
     
     public void llamaReporteUBEcompleto() {
        JasperReport jr;
        String path = "/vista/reportes/repUBcompleto.jasper";
        posFila = vtnAlmnCurso.getTblAlumnoCurso().getSelectedRow();
        if (posFila >= 0) {
            try {

                Map parametro = new HashMap();
                parametro.put("cedulaalumno", almns.get(posFila).getAlumno().getIdentificacion());
                parametro.put("periodolectivo", vtnAlmnCurso.getCmbPrdLectivos().getSelectedItem().toString());
                System.out.println(parametro);
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte UBE");

            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "error" + ex);
            }
        } else {
            JOptionPane.showMessageDialog(vtnAlmnCurso, "Seleecione una fila primero.");
        }

    }
}
