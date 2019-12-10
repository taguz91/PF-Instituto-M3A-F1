package controlador.ube;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.swing.table.DefaultTableModel;
import modelo.reporte.FichasBD;
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
    List<List<String>> fichas, todasFichas;

    public VtnAlumnosSinResponderFSCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
    }

    public void iniciar() {
        iniciarTbl();
        cargarDatos();

        ctrPrin.agregarVtn(VTN);
        listenerTxtBuscarLocal(
                VTN.getTxtBuscar(),
                VTN.getBtnBuscar(),
                buscarFun()
        );
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
        llenatTbl(fichas);
    }

    private void llenatTbl(List<List<String>> fichas) {
        mdTbl.setRowCount(0);
        fichas.forEach(r -> {
            mdTbl.addRow(r.toArray());
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

}
