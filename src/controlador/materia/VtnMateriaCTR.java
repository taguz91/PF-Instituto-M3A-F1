package controlador.materia;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import vista.materia.VtnMateria;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnMateriaCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnMateria vtnMateria;
    private final MateriaBD materia;

    //El modelo de la tabla materias  
    DefaultTableModel mdTblMat;
    //Aqui guardamos todas las materias  
    private ArrayList<MateriaMD> materias;
    //Para el combo de filtrar por carrera
    private ArrayList<CarreraMD> carreras;

    public VtnMateriaCTR(VtnPrincipal vtnPrin, VtnMateria vtnMateria, MateriaBD materia) {
        this.vtnPrin = vtnPrin;
        this.vtnMateria = vtnMateria;
        this.materia = materia;

        vtnPrin.getDpnlPrincipal().add(vtnMateria);
        vtnMateria.show();
    }

    public void iniciar() {
        String titulo[] = {"id", "Nombre", "Codigo"};
        String datos[][] = {};
        mdTblMat = new DefaultTableModel(datos, titulo);
        //Le pasamos el modelo a la tabla  v
        vtnMateria.getTblMateria().setModel(mdTblMat);

        materias = materia.cargarMaterias();

        cargarTblMaterias();

        cargarCmbFiltrar();

        vtnMateria.getCmbCarreras().addActionListener(e -> filtrarPorCarrera());
    }

    public void cargarCmbFiltrar() {
        //Cargamos todas las carreras 
        CarreraBD carrerBD = new CarreraBD();
        carreras = carrerBD.cargarCarreras();
        //Cargamos el combo 
        vtnMateria.getCmbCarreras().removeAllItems();
        vtnMateria.getCmbCarreras().addItem("Seleccione una carrera");
        for (CarreraMD car : carreras) {
            vtnMateria.getCmbCarreras().addItem(car.getNombre());
        }
    }

    public void filtrarPorCarrera() {
        int pos = vtnMateria.getCmbCarreras().getSelectedIndex();
        if (pos > 0) {
            materias = materia.cargarMateriaPorCarrera(carreras.get(pos - 1).getId());
        } else {
            materias = materia.cargarMaterias();
        }

        cargarTblMaterias();
    }

    public void cargarTblMaterias() {
        mdTblMat.setRowCount(0);
        vtnMateria.getLblResultados().setText(materias.size() + " Resultados obtenidos.");
        if (!materias.isEmpty()) {
            for (MateriaMD mt : materias) {
                Object valores[] = {mt.getId(),
                    mt.getNombre(), mt.getCodigo()};
                mdTblMat.addRow(valores);
            }
        }
    }

}
