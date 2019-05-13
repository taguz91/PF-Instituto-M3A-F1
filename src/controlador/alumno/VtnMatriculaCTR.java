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

    public VtnMatriculaCTR(VtnPrincipalCTR ctrPrin, VtnMatricula vtnMatri) {
        super(ctrPrin);
        this.matr = new MatriculaBD(ctrPrin.getConecta());
        this.vtnMatri = vtnMatri;
        this.prd = new PeriodoLectivoBD(ctrPrin.getConecta());
    }

    public void iniciar() {
        //Iniciamos la tabla
        String[] t = {"Periodo", "Cedula", "Alumno", "Fecha",};
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
        System.out.println("Fecha: " + fi);
        System.out.println("Fecha mas 30: " + fi.plusMonths(1));
        System.out.println("Esto es: " + fa.isBefore(fi.plusMonths(1)));
        return fa.isBefore(fi.plusMonths(1));
    }

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

    private void clickEditar() {
        posFila = vtnMatri.getTblMatricula().getSelectedRow();
        if (posFila >= 0) {
            if (validarFecha()) {
                JDEditarMatriculaCTR ctr = new JDEditarMatriculaCTR(ctrPrin, matriculas.get(posFila));
                ctr.iniciar();
            } else {
                JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Ya pasaron mas de 30 dias ya no se puede editar la matricula.");
            }
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Debe seleccionar una fila primero.");
        }
    }

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

    private void buscar(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            matriculas = matr.buscarMatriculas(aguja);
            llenarTbl(matriculas);
        }
    }

    private void iniciarAcciones() {
        vtnMatri.getCmbPeriodos().addActionListener(e -> clickPrd());
        vtnMatri.getBtnImprimirFicha().addActionListener(e -> clickImprimirFicha());
        vtnMatri.getBtnHistoria().addActionListener(e -> llamaReporteMatriculaPeriodo());
        vtnMatri.getBtnIngresar().addActionListener(e -> abrirFrm());
        vtnMatri.getBtnEditar().addActionListener(e -> clickEditar());
        vtnMatri.getBtnAnular().addActionListener(e -> clickAnular());
    }

    private void cargarMatriculas() {
        matriculas = matr.cargarMatriculas();
        llenarTbl(matriculas);
    }

    private void clickPrd() {
        posPrd = vtnMatri.getCmbPeriodos().getSelectedIndex();
        if (posPrd > 0) {
            matriculas = matr.cargarMatriculasPorPrd(periodos.get(posPrd - 1).getId_PerioLectivo());
            llenarTbl(matriculas);
        } else {
            cargarMatriculas();
        }
    }

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

    private void abrirFrm() {
        ctrPrin.abrirFrmMatricula();
    }

    private void clickImprimirFicha() {
        posFila = vtnMatri.getTblMatricula().getSelectedRow();
        if (posFila > 0) {
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

}
