package controlador.alumno;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.CONS;
import modelo.alumno.MatriculaBD;
import modelo.alumno.MatriculaMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.alumno.VtnMatricula;

/**
 *
 * @author Johnny
 */
public class VtnMatriculaCTR extends DVtnCTR {

    private final VtnMatricula vtnMatri;
    private final MatriculaBD matr;
    private ArrayList<MatriculaMD> matriculas;

    //Para combos
    private ArrayList<PeriodoLectivoMD> periodos;
    private final PeriodoLectivoBD prd;
    private int posPrd;

    /**
     * Mostramos todas las matriculas realizadas
     *
     * @param ctrPrin
     * @param vtnMatri
     */
    public VtnMatriculaCTR(VtnPrincipalCTR ctrPrin, VtnMatricula vtnMatri) {
        super(ctrPrin);
        this.matr = new MatriculaBD(ctrPrin.getConecta());
        this.vtnMatri = vtnMatri;
        this.prd = new PeriodoLectivoBD(ctrPrin.getConecta());
    }

    /**
     * Iniciamos todas las dependencias
     */
    public void iniciar() {
        //Iniciamos la tabla
        String[] t = {"Periodo", "Cedula", "Alumno", "Fecha"};
        String[][] d = {};
        iniciarTbl(t, d, vtnMatri.getTblMatricula());
        //Tamaño de columnas 
        TblEstilo.columnaMedida(vtnMatri.getTblMatricula(), 1, 100);

        llenarCmbPrd();
        cargarMatriculas();

        iniciarAcciones();
        formatoBuscador(vtnMatri.getTxtBuscar(), vtnMatri.getBtnBuscar());
        iniciarBuscador();
        ctrPrin.agregarVtn(vtnMatri);
        InitPermisos();

    }

    /**
     * Si aun no pasan mas de 30 dias desde que incio el periodo retornara
     * verdadero.
     *
     * @return
     */
    private boolean validarFecha() {
        LocalDate fi = prd.buscarFechaInicioPrd(matriculas.get(posFila).getPeriodo().getId_PerioLectivo());
        LocalDate fa = LocalDate.now();
        return fa.isBefore(fi.plusMonths(1));
    }

    /**
     * Click anular para mostrarnos el formulario de anulacion de matricula.
     */
    private void clickAnular() {
        posFila = vtnMatri.getTblMatricula().getSelectedRow();
        if (posFila >= 0) {
            if (validarFecha()) {
                JDAnularMatriculaCTR ctr = new JDAnularMatriculaCTR(ctrPrin, matriculas.get(posFila));
                ctr.iniciar();
            } else {
                JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Ya pasaron mas de 30 dias ya no se puede anular la matricula.");
            }
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar una fila primero.");
        }
    }

    /**
     * Para mostrar el formulario de editar matricula, aqui podemos cambiar de
     * cursos Paralelo Ciclo
     */
    private void clickEditar() {
        posFila = vtnMatri.getTblMatricula().getSelectedRow();
        if (posFila >= 0) {
            JDEditarMatriculaCTR ctr = new JDEditarMatriculaCTR(ctrPrin, matriculas.get(posFila));
            ctr.iniciar();

        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar una fila primero.");
        }
    }

    /**
     * Iniciamos el buscador de la ventana
     */
    private void iniciarBuscador() {
        vtnMatri.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                if (e.getKeyCode() == 10) {
                    buscar(vtnMatri.getTxtBuscar().getText().trim());
                } else if (vtnMatri.getTxtBuscar().getText().length() == 0) {
                    cargarMatriculas();
                }
            }
        });
    }

    /**
     * Funcion usada para buscar
     *
     * @param aguja
     */
    private void buscar(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            matriculas = matr.buscarMatriculas(aguja);
            llenarTbl(matriculas);
        }
    }

    /**
     * Iniciamos todas las acciones de esta ventana
     */
    private void iniciarAcciones() {
        vtnMatri.getCmbPeriodos().addActionListener(e -> clickPrd());
        vtnMatri.getBtnImprimirFicha().addActionListener(e -> clickImprimirFicha());
        vtnMatri.getBtnHistoria().addActionListener(e -> llamaReporteMatriculaPeriodo());
        vtnMatri.getBtnIngresar().addActionListener(e -> abrirFrm());
        vtnMatri.getBtnEditar().addActionListener(e -> clickEditar());
        vtnMatri.getBtnAnular().addActionListener(e -> clickAnular());
        vtnMatri.getBtnCartaCompromiso().addActionListener(e -> clickCartaCompromiso());
        vtnMatri.getBtnNumMatricula().addActionListener(e -> clickNumMatricula());
    }

    /**
     * Cargamos todas las matriculas que existan
     */
    private void cargarMatriculas() {
        matriculas = matr.cargarMatriculas();
        llenarTbl(matriculas);
    }

    /**
     * Al seleccionar un periodo se cargan las matriculas por ese periodo Si no
     * seleeciona ningun periodo se cargan todas las matriculas
     */
    private void clickPrd() {
        posPrd = vtnMatri.getCmbPeriodos().getSelectedIndex();
        if (posPrd > 0) {
            matriculas = matr.cargarMatriculasPorPrd(periodos.get(posPrd - 1).getId_PerioLectivo());
            llenarTbl(matriculas);
        } else {
            cargarMatriculas();
        }
    }

    /**
     * Llenamos el combo del periodo lectivo
     */
    private void llenarCmbPrd() {
        periodos = prd.cargarPrdParaCmbVtn();
        vtnMatri.getCmbPeriodos().removeAllItems();
        if (periodos != null) {
            vtnMatri.getCmbPeriodos().addItem("Seleccione");
            periodos.forEach(p -> {
                vtnMatri.getCmbPeriodos().addItem(p.getNombre_PerLectivo());
            });
        }
    }

    /**
     * Llenamos la tabla de matriculas
     *
     * @param matriculas
     */
    private void llenarTbl(ArrayList<MatriculaMD> matriculas) {
        mdTbl.setRowCount(0);
        if (matriculas != null) {
            matriculas.forEach(m -> {
                Object[] v = {m.getPeriodo().getNombre_PerLectivo(),
                    m.getAlumno().getIdentificacion(),
                    m.getAlumno().getNombreCompleto(),
                    m.getSoloFecha(), m.getSoloHora()};
                mdTbl.addRow(v);
            });
            vtnMatri.getLblNumResultados().setText(matriculas.size() + " Resultados obtenidos.");
        } else {
            vtnMatri.getLblNumResultados().setText("0 Resultados obtenidos.");
        }
    }

    /**
     * Abrimos e formulario de matricula
     */
    private void abrirFrm() {
        ctrPrin.abrirFrmMatricula();
    }

    /**
     * Para imprimir la ficha de matricula, preguntamos Si quierela ficha con
     * foto o sin foto
     */
    private void clickImprimirFicha() {
        posFila = vtnMatri.getTblMatricula().getSelectedRow();
        if (posFila >= 0) {
            int s = JOptionPane.showOptionDialog(vtnMatri,
                    "Reporte de matricula\n"
                    + "¿Elegir el tipo de reporte?", "Ficha matricula",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Con Foto", "Sin Foto",
                        "Cancelar"}, "Con Foto");
            switch (s) {
                case 0:
                    llamaReporteMatricula();
                    break;
                case 1:
                    llamaReporteMatriculaSinFoto();
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar una persona antes.");
        }
    }

    /**
     * Elegimos el tipo de carta compromiso que queremos
     */
    private void clickCartaCompromiso() {
        posFila = vtnMatri.getTblMatricula().getSelectedRow();
        if (posFila >= 0) {
            int s = JOptionPane.showOptionDialog(vtnMatri,
                    "Reporte de matricula\n"
                    + "¿Elegir el tipo de carta compromiso?", "Cartas compromiso",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Acta", "Segunda Matricula", "Tercera Matricula",
                        "Cancelar"}, "Acta");
            switch (s) {
                case 0:
                    llamaReporteActa();
                    break;
                case 1:
                    llamaReporteCartaNumMatricula(2, "SEGUNDA MATRÍCULA");
                    break;
                case 2:
                    llamaReporteCartaNumMatricula(3, "TERCERA MATRÍCULA");
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar una persona antes.");
        }
    }

    private void clickNumMatricula() {
        posPrd = vtnMatri.getCmbPeriodos().getSelectedIndex();
        if (posPrd > 0) {
            int s = JOptionPane.showOptionDialog(vtnMatri,
                    "Reporte de  numero de matricula\n"
                    + "Elija el numero de matricula", "Numero de matricula",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[]{"Primera Matricula", "Segunda Matricula", "Tercera Matricula",
                        "Completo", "Cancelar"}, "Completo");
            switch (s) {
                case 0:
                    llamarReporteNumMatricula(1);
                    break;
                case 1:
                    llamarReporteNumMatricula(2);
                    break;
                case 2:
                    llamarReporteNumMatricula(3);
                    break;
                case 3:
                    llamarReporteMatriculados(posPrd);
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar un periodo lectivo.");
        }
    }

    private String selecCurso() {
        ArrayList<String> cursos = matr.cursosMatriculado(matriculas.get(posFila).getAlumno().getId_Alumno(),
                matriculas.get(posFila).getPeriodo().getId_PerioLectivo());
        Object np = JOptionPane.showInputDialog(null,
                "Cursos en los que se matriculo: ", "Matricula",
                JOptionPane.QUESTION_MESSAGE, null,
                cursos.toArray(), "Seleccione");
        //Se es null significa que no selecciono nada
        if (np == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un curso");
            selecCurso();
            return null;
        } else {
            return np.toString();
        }
    }

    /**
     * Llamamos el reporte de la matricula, completo este nos imprime con foto
     */
    private void llamaReporteMatricula() {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/reportes/repImpresionMatricula.jasper"));
            Map parametro = new HashMap();
            parametro.put("cedula", matriculas.get(posFila).getAlumno().getIdentificacion());
            parametro.put("idPeriodo", matriculas.get(posFila).getPeriodo().getId_PerioLectivo());
            parametro.put("usuario", ctrPrin.getUsuario().getUsername());
            ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Matricula");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }

    /**
     * Llamamos el reporte de la matricula, sin foto.
     */
    private void llamaReporteMatriculaSinFoto() {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/reportes/repMatriculaSinFoto.jasper"));
            Map parametro = new HashMap();
            parametro.put("cedula", matriculas.get(posFila).getAlumno().getIdentificacion());
            parametro.put("idPeriodo", matriculas.get(posFila).getPeriodo().getId_PerioLectivo());
            parametro.put("usuario", ctrPrin.getUsuario().getUsername());
            ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Matricula | Sin foto");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }

    /**
     * Llamamos al reporte de matriculas por periodo, todo el historial de las
     * matriculas en este periodo separado por ciclo
     */
    private void llamaReporteMatriculaPeriodo() {
        int posCombo = vtnMatri.getCmbPeriodos().getSelectedIndex();
        if (posCombo > 0) {
            try {
                JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/reportes/repMatriculadosPeriodo.jasper"));
                Map parametro = new HashMap();
                parametro.put("periodo", periodos.get(posCombo - 1).getId_PerioLectivo());
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte Historial de Matrícula por Periodo");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un periodo lectivo, del combo.");
        }
    }

    /**
     * Llamamos el reporte de carta compromiso
     */
    private void llamaReporteActa() {
        String curso = selecCurso();
        if (curso != null) {
            try {
                JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/reportes/cartaCompromiso.jasper"));
                Map parametro = new HashMap();
                parametro.put("idAlumno", matriculas.get(posFila).getAlumno().getId_Alumno());
                parametro.put("idPeriodo", matriculas.get(posFila).getPeriodo().getId_PerioLectivo());
                parametro.put("curso", curso);
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Matricula");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "error" + ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No selecciono un curso.");
        }
    }

    private void llamaReporteCartaNumMatricula(int numMatricula, String matricula) {
        String curso = selecCurso();
        if (curso != null) {
            try {
                JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/reportes/cartaNumMatricula.jasper"));
                Map parametro = new HashMap();
                parametro.put("idAlumno", matriculas.get(posFila).getAlumno().getId_Alumno());
                parametro.put("idPeriodo", matriculas.get(posFila).getPeriodo().getId_PerioLectivo());
                parametro.put("curso", curso);
                parametro.put("numMatricula", numMatricula);
                parametro.put("matricula", matricula);
                System.out.println("Parametros: " + parametro);
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Matricula");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No selecciono un curso.");
        }
    }

    private void InitPermisos() {
        vtnMatri.getBtnIngresar().getAccessibleContext().setAccessibleName("Matricula-Ingresar");
        vtnMatri.getBtnAnular().getAccessibleContext().setAccessibleName("Matricula-Anular");
        vtnMatri.getBtnEditar().getAccessibleContext().setAccessibleName("Matricula-Editar");
        vtnMatri.getBtnCartaCompromiso().getAccessibleContext().setAccessibleName("Matricula-Reporte-Carta");
        vtnMatri.getBtnHistoria().getAccessibleContext().setAccessibleName("Matricula-Reporte-Historial");
        vtnMatri.getBtnImprimirFicha().getAccessibleContext().setAccessibleName("Matricula-Imprimir");

        CONS.activarBtns(vtnMatri.getBtnIngresar(), vtnMatri.getBtnAnular(),
                vtnMatri.getBtnEditar(), vtnMatri.getBtnCartaCompromiso(),
                vtnMatri.getBtnHistoria(), vtnMatri.getBtnImprimirFicha());
    }

    private void llamarReporteNumMatricula(int numMatricula) {
        posPrd = vtnMatri.getCmbPeriodos().getSelectedIndex();
        if (posPrd > 0) {
            try {
                JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/reportes/repNumMatriculaPeriodo.jasper"));
                Map parametro = new HashMap();
                parametro.put("matricula", numMatricula);
                parametro.put("periodo", periodos.get(posPrd - 1).getId_PerioLectivo());
                System.out.println("Parametros: " + parametro);
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Matricula");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un periodo lectivo.");
        }
    }

    private void llamarReporteMatriculados(int posPrd) {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/reportes/repMatriculasPeriodo.jasper"));
            Map parametro = new HashMap();
            parametro.put("periodo", periodos.get(posPrd - 1).getId_PerioLectivo());
            System.out.println("Parametros: " + parametro);
            ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Matricula");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

}
