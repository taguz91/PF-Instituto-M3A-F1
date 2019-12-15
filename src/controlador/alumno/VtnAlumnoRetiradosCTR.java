package controlador.alumno;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.Retirado;
import modelo.alumno.RetiradoBD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import vista.alumno.VtnAlumnoRetirados;

/**
 *
 * @author gus
 */
public class VtnAlumnoRetiradosCTR extends DCTR {

    // Ventana  
    private final VtnAlumnoRetirados vtn;
    // Tabla  
    private static final String[] TITULO = {
        "Carrera", "Cédula",
        "Alumno", "Fecha retiro",
        "Motivo"
    };
    private DefaultTableModel mdTbl;
    // Para los retirados 
    private final RetiradoBD RBD = RetiradoBD.single();
    private List<Retirado> todosRetirados, retiradosBuscado;
    // Para los combos  
    private final CarreraBD carr = CarreraBD.single();
    private ArrayList<CarreraMD> carreras;
    // Para ver los retirados  
    private final JDRetirarAlumnoCTR RCTR;

    public VtnAlumnoRetiradosCTR(
            VtnPrincipalCTR ctrPrin
    ) {
        super(ctrPrin);
        this.vtn = new VtnAlumnoRetirados();
        this.RCTR = new JDRetirarAlumnoCTR(ctrPrin);
    }

    public void iniciar() {
        cargarCmbCarreras();
        iniciarVtn();
        cargarAlmnsRetirados();
        vtnCargada = true;
        ctrPrin.agregarVtn(vtn);
    }

    private void iniciarVtn() {
        mdTbl = TblEstilo.modelTblSinEditar(TITULO);
        vtn.getTblRetirados().setModel(mdTbl);
        vtn.getBtnEditar().addActionListener(e -> clickEditar());
        vtn.getBtnEliminar().addActionListener(e -> clickEliminar());
        vtn.getBtnRepRetirados().addActionListener(e -> clickReportePorPeriodo());
        vtn.getCmbCarrera().addActionListener(e -> clickPeriodo());
        vtn.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscar(vtn.getTxtBuscar().getText().trim());
            }
        });
        vtn.getBtnBuscar().addActionListener(e -> buscar(
                vtn.getTxtBuscar().getText().trim()
        ));
    }

    private void buscar(String aguja) {
        retiradosBuscado = new ArrayList();
        todosRetirados.forEach(r -> {
            if (r.getAlmnCarrera().getAlumno()
                    .getNombreCompleto().toLowerCase()
                    .trim().contains(aguja)
                    || r.getMotivo().toLowerCase().contains(aguja)
                    || r.getPeriodo().getNombre().toLowerCase().contains(aguja)) {
                retiradosBuscado.add(r);
            }
        });
        // Llenamos la tabla 
        llenarTblAlmnRetirado(retiradosBuscado);
    }

    private void clickPeriodo() {
        if (vtnCargada) {
            cargarAlmnsRetirados();
        }
    }

    private void clickEditar() {
        int posFila = vtn.getTblRetirados().getSelectedRow();
        if (posFila >= 0) {
            RCTR.editar(retiradosBuscado.get(posFila));
        } else {
            errorNoSeleccionoFila();
        }
    }

    private void clickEliminar() {
        int posFila = vtn.getTblRetirados().getSelectedRow();
        if (posFila >= 0) {
            if (RBD.eliminar(retiradosBuscado.get(posFila).getId())) {
                JOptionPane.showMessageDialog(vtn, "Eliminamos correctamente.");
                mdTbl.removeRow(posFila);
            }
        } else {
            errorNoSeleccionoFila();
        }
    }

    private void cargarAlmnsRetirados() {
        int posCar = vtn.getCmbCarrera().getSelectedIndex();
        if (posCar > 0) {
            todosRetirados = RBD.getByCarrera(
                    carreras.get(posCar - 1).getId()
            );
        } else {
            todosRetirados = RBD.getAllTbl();
        }
        retiradosBuscado = todosRetirados;
        llenarTblAlmnRetirado(todosRetirados);
    }

    private void llenarTblAlmnRetirado(List<Retirado> retirados) {
        mdTbl.setRowCount(0);
        if (retirados != null) {
            retirados.forEach(r -> {
                Object[] valores = {
                    r.getAlmnCarrera().getCarrera().getCodigo(),
                    r.getAlmnCarrera().getAlumno().getIdentificacion(),
                    r.getAlmnCarrera().getAlumno().getPrimerApellido()
                    + " " + r.getAlmnCarrera().getAlumno().getSegundoApellido()
                    + " " + r.getAlmnCarrera().getAlumno().getPrimerNombre()
                    + " " + r.getAlmnCarrera().getAlumno().getSegundoNombre(),
                    r.getFechaRetiro().toString(),
                    r.getMotivo()
                };
                mdTbl.addRow(valores);
            });
            vtn.getLblResultados().setText(retirados.size() + " Resultados obtenidos.");
        }
    }

    private void cargarCmbCarreras() {
        carreras = carr.cargarCarrerasCmb();
        if (carreras != null) {
            vtn.getCmbCarrera().removeAllItems();
            vtn.getCmbCarrera().addItem("Todos");
            carreras.forEach((c) -> {
                vtn.getCmbCarrera().addItem(c.getCodigo());
            });
        }
    }

    private void clickReportePorPeriodo() {
        
    }

}
