package controlador.ube;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.swing.table.DefaultTableModel;
import modelo.reporte.FichasBD;
import utils.ToExcel;
import vista.ube.VtnAlumnosSinResponderFS;

/**
 *
 * @author gus
 */
public class VtnAlumnosSinResponderFSCTR extends DCTR {

    private DefaultTableModel mdTbl;
    // Ventana  
    private final VtnAlumnosSinResponderFS VTN = new VtnAlumnosSinResponderFS();
    // BD  
    private final FichasBD FBD = FichasBD.single();
    // Datos 
    private List<List<String>> fichas, todasFichas;
    // Periodos  
    private String periodosCmb = "";

    public VtnAlumnosSinResponderFSCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
    }

    public void iniciar() {
        iniciarTbl();
        cargarDatos();
        clickCmbPeriodo();
        VTN.getBtnExportarExcel().addActionListener(e -> exportarExcel());

        ctrPrin.agregarVtn(VTN);
        listenerTxtBuscarLocal(
                VTN.getTxtBuscar(),
                VTN.getBtnBuscar(),
                buscarFun()
        );
    }

    private void clickCmbPeriodo() {
        VTN.getCmbPeriodo().addActionListener(e -> {
            if (!VTN.getCmbPeriodo().getSelectedItem().toString().equals("Seleccione")) {
                buscar(VTN.getCmbPeriodo().getSelectedItem().toString());
            }

        });

    }

    private Function<String, Void> buscarFun() {
        return t -> {
            buscar(t.toUpperCase().trim());
            return null;
        };
    }

    private void buscar(String aguja) {
        fichas = new ArrayList<>();
        todasFichas.forEach(tf -> {
            if (tf.get(0).contains(aguja)
                    || tf.get(1).contains(aguja)
                    || tf.get(2).contains(aguja)
                    || tf.get(3).contains(aguja)) {
                fichas.add(tf);
            }
        });
        llenatTbl(fichas);
    }

    private void cargarDatos() {
        fichas = FBD.getFichasSinResponder();
        todasFichas = fichas;
        VTN.getCmbPeriodo().removeAllItems();
        VTN.getCmbPeriodo().addItem("Seleccione");
        llenatTbl(fichas);
    }

    private void llenatTbl(List<List<String>> fichas) {
        mdTbl.setRowCount(0);
        fichas.forEach(r -> {
            mdTbl.addRow(r.toArray());
            if (!periodosCmb.contains(r.get(3))) {
                periodosCmb += r.get(3);
                VTN.getCmbPeriodo().addItem(r.get(3));
            }
        });
        VTN.getLblResultados().setText(fichas.size() + " Resultados obtenidos.");
    }

    private void iniciarTbl() {
        String[] titulo = {
            "IDENTIFICACIÓN",
            "ALUMNO",
            "CORREO",
            "PERIODO",
            "FECHA INGRESO",
            "FECHA ULTIMA MODIFICACION"
        };

        mdTbl = iniciarTbl(
                VTN.getTblAlumnos(),
                titulo
        );
    }

    private void exportarExcel() {
        List<String> cols = new ArrayList<>();
        cols.add("IDENTIFICACIÓN");
        cols.add("ALUMNO");
        cols.add("CORREO");
        cols.add("FECHA INGRESO");
        cols.add("FECHA ULTIMA MODIFICACION");
        ToExcel excel = new ToExcel();
        excel.exportarExcel(
                cols,
                fichas,
                "NOMBRE"
        );
    }

}
