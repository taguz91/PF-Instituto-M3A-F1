package controlador.materia;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private DefaultTableModel mdTblMat;
    //Aqui guardamos todas las materias  
    private ArrayList<MateriaMD> materias;
    //Para el combo de filtrar por carrera
    private ArrayList<CarreraMD> carreras;
    //Ciclos de una carrera  
    private ArrayList<Integer> ciclos;

    public VtnMateriaCTR(VtnPrincipal vtnPrin, VtnMateria vtnMateria) {
        this.vtnPrin = vtnPrin;
        this.vtnMateria = vtnMateria;
        this.materia = new MateriaBD();

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
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 3, 40);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 4, 70);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 5, 70);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 6, 70);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 7, 70);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 8, 40);
        //Iniciamos el combo de ciclos
        vtnMateria.getCmbCiclo().removeAllItems();
        vtnMateria.getCmbCiclo().addItem("Todos");

        materias = materia.cargarMaterias();
        cargarTblMaterias();
        cargarCmbFiltrar();
        vtnMateria.getCmbCarreras().addActionListener(e -> filtrarPorCarrera());
        vtnMateria.getCmbCiclo().addActionListener(e -> filtrarPorCarreraPorCiclo());

        //Iniciamos el buscador  
        vtnMateria.getBtnBuscar().addActionListener(e -> buscarMaterias(vtnMateria.getTxtBuscar().getText().trim()));
        vtnMateria.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscar();
            }
        });
    }

    public void buscar() {
        String buscar = vtnMateria.getTxtBuscar().getText().trim();
        if (buscar.length() > 2) {
            buscarMaterias(buscar);
        }
    }

    public void buscarMaterias(String b) {
        materias = materia.cargarMaterias(b);
        cargarTblMaterias();
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

    private void filtrarPorCarrera() {
        int pos = vtnMateria.getCmbCarreras().getSelectedIndex();
        if (pos > 0) {
            materias = materia.cargarMateriaPorCarrera(carreras.get(pos - 1).getId());
            //Cargamos los ciclos de una carrera
            ciclos = materia.cargarCiclosCarrera(carreras.get(pos - 1).getId());
            vtnMateria.getCmbCiclo().removeAllItems();
            vtnMateria.getCmbCiclo().addItem("Todos");
            ciclos.forEach(c -> {
                vtnMateria.getCmbCiclo().addItem(c+"");
            });
        } else {
            materias = materia.cargarMaterias();
            //Borramos todos los item del combo ciclos  
            vtnMateria.getCmbCiclo().removeAllItems();
        }

        cargarTblMaterias();
    }

    private void cargarTblMaterias() {
        mdTblMat.setRowCount(0);
        vtnMateria.getLblResultados().setText(materias.size() + " Resultados obtenidos.");
        if (!materias.isEmpty()) {
            materias.forEach((mt) -> {
                Object valores[] = {mt.getId(),
                    mt.getCodigo(), mt.getNombre(),
                    mt.getCiclo(), mt.getHorasDocencia(),
                    mt.getHorasPracticas(), mt.getHorasAutoEstudio(),
                    mt.getHorasPresenciales(), mt.getTotalHoras()};
                mdTblMat.addRow(valores);
            });
        }
    }

    private void filtrarPorCarreraPorCiclo() {
        int ciclo = vtnMateria.getCmbCiclo().getSelectedIndex();
        int posCar = vtnMateria.getCmbCarreras().getSelectedIndex();
        if (ciclo > 0) {
            materias = materia.cargarMateriaPorCarreraCiclo(
                    carreras.get(posCar - 1).getId(), ciclo);
            cargarTblMaterias();
        } else {
            filtrarPorCarrera();
        }
    }

}
