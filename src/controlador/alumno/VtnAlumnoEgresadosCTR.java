package controlador.alumno;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.Egresado;
import modelo.alumno.Retirado;
import modelo.alumno.RetiradoBD;
import modelo.estilo.TblEstilo;
import vista.alumno.VtnAlumnoEgresados;

/**
 *
 * @author gus
 */
public class VtnAlumnoEgresadosCTR extends DCTR {
    
    private final VtnAlumnoEgresados vtn;
    private List<Egresado> egresados, todosEgresados;
    private DefaultTableModel mdTbl;
    private RetiradoBD RBD = RetiradoBD.single();
    private static final String[] TITULO = {
        "Carrera", "Cédula",
        "Alumno", "Fecha egreso"
    };
    
    
    public VtnAlumnoEgresadosCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.vtn = new VtnAlumnoEgresados();
    }
    
    public void iniciar() {
        
    }
    
    private void iniciarVtn(){
        mdTbl = TblEstilo.modelTblSinEditar(TITULO);
        vtn.getTblEgresados().setModel(mdTbl);
        iniciarBuscador();
    }
    
    private void iniciarBuscador(){
        vtn.getBtnBuscar().addActionListener(e -> buscar(
                vtn.getTxtBuscar().getText().trim()
        ));
        vtn.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscar(vtn.getTxtBuscar().getText().trim());
            }
        });
    }
    
    private void buscar(String aguja){
        egresados = new ArrayList();
        todosEgresados.forEach(e -> {
            if (e.getAlmnCarrera().getAlumno()
                    .getNombreCompleto().toLowerCase()
                    .contains(aguja.toLowerCase())) {
                egresados.add(e);
            }
        });
        llenarTbl(egresados);
    }
    
    private void llenarTbl(List<Egresado> egresados) {
        mdTbl.setRowCount(0);
        if (egresados != null) {
            egresados.forEach(r -> {
                Object[] valores = {
                    r.getAlmnCarrera().getCarrera().getCodigo(),
                    r.getAlmnCarrera().getAlumno().getIdentificacion(),
                    r.getAlmnCarrera().getAlumno().getPrimerApellido()
                    + " " + r.getAlmnCarrera().getAlumno().getSegundoApellido()
                    + " " + r.getAlmnCarrera().getAlumno().getPrimerNombre()
                    + " " + r.getAlmnCarrera().getAlumno().getSegundoNombre(),
                    r.getFechaEgreso().toString()
                };
                mdTbl.addRow(valores);
            });
            vtn.getLblResultados().setText(egresados.size() + " Resultados obtenidos.");
        }
    }
    
}
