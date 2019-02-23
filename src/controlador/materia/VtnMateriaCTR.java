package controlador.materia;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
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
        String titulo[] = {"id", "Codigo", "Nombre", "Ciclo", "Docencia", "Practicas", "Autonomas", "Presencial", "Total"};
        String datos[][] = {};
        //Usamos el modelo que no nos deja editar los campos
        mdTblMat = TblEstilo.modelTblSinEditar(datos, titulo);
        //Le pasamos el modelo a la tabla  v
        vtnMateria.getTblMateria().setModel(mdTblMat);
        //Ocusltamos el id  
        TblEstilo.ocualtarID(vtnMateria.getTblMateria());
        //Pasamos el estilo a la tabla 
        TblEstilo.formatoTbl(vtnMateria.getTblMateria());
        //Pasamos la columna de codigo para que sea de 20  
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 1, 70);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 3, 35);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 4, 65);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 5, 65);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 6, 65);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 7, 65);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 8, 35);

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
        carreras.forEach((car) -> {
            vtnMateria.getCmbCarreras().addItem(car.getNombre());
        });
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
                    mt.getCodigo(), mt.getNombre(),
                    mt.getCiclo(),mt.getHorasDocencia(), 
                    mt.getHorasPracticas(), mt.getHorasAutoEstudio(), 
                    mt.getHorasPresenciales(), mt.getTotalHoras()};
                mdTblMat.addRow(valores);
            }
        }
    }

}
