package controlador.alumno;

import controlador.principal.DependenciasVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
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
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnMatriculaCTR extends DependenciasVtnCTR {

    private final VtnMatricula vtnMatri;
    private final MatriculaBD matr;
    private ArrayList<MatriculaMD> matriculas;

    //Para combos
    private ArrayList<PeriodoLectivoMD> periodos;
    private final PeriodoLectivoBD prd;
    private int posPrd;

    public VtnMatriculaCTR(ConectarDB conecta, VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin, VtnMatricula vtnMatri) {
        super(conecta, vtnPrin, ctrPrin);
        this.matr = new MatriculaBD(conecta);
        this.vtnMatri = vtnMatri;
        this.prd = new PeriodoLectivoBD(conecta);
        //Mostramos en la ventana
        vtnPrin.getDpnlPrincipal().add(vtnMatri);
        vtnMatri.show();
    }

    public void iniciar() {
        //Iniciamos la tabla
        String[] t = {"Periodo","Cedula", "Alumno", "Fecha",};
        String[][] d = {};
        iniciarTbl(t, d, vtnMatri.getTblMatricula());
        //Tamaño de columnas 
        TblEstilo.columnaMedida(vtnMatri.getTblMatricula(), 1, 100);

        llenarCmbPrd();
        cargarMatriculas();

        iniciarAcciones();
        formatoBuscador(vtnMatri.getTxtBuscar(), vtnMatri.getBtnBuscar());
        iniciarBuscador();
    }

    private void clickImprimirReporte() {
        posFila = vtnMatri.getTblMatricula().getSelectedRow();
        if (posFila >= 0) {
            llamaReporteMatricula();
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "Debe seleccionar una persona antes.");
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
        vtnMatri.getBtnImprimirFicha().addActionListener(e -> clickImprimirReporte());
        vtnMatri.getBtnIngresar().addActionListener(e -> abrirFrm());
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

    private void abrirFrm(){
        ctrPrin.abrirFrmMatricula();
    }

    private void llamaReporteMatricula() {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/vista/reportes/repImpresionMatricula.jasper"));
            Map parametro = new HashMap();
            parametro.put("cedula", matriculas.get(posFila).getAlumno().getIdentificacion());
            parametro.put("idPeriodo", matriculas.get(posFila).getPeriodo().getId_PerioLectivo());
            System.out.println(parametro);
            conecta.mostrarReporte(jr, parametro, "Reporte de Matricula");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }

}
