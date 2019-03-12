package controlador.alumno;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.alumno.AlumnoCarreraBD;
import modelo.alumno.AlumnoCarreraMD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import vista.alumno.VtnAlumnoCarrera;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnAlumnoCarreraCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnAlumnoCarrera vtnAlmCar;
    private final AlumnoCarreraBD almnCar;
    private final ConectarDB conecta;
    
    private ArrayList<AlumnoCarreraMD> almnsCarr;

    //Modelo de la tabla
    private DefaultTableModel mdTbl;
    
    private final CarreraBD carr;
    private ArrayList<CarreraMD> carreras;
    
    public VtnAlumnoCarreraCTR(VtnPrincipal vtnPrin, VtnAlumnoCarrera vtnAlmCar, ConectarDB conecta) {
        this.vtnPrin = vtnPrin;
        this.vtnAlmCar = vtnAlmCar;
        this.almnCar = new AlumnoCarreraBD(conecta);
        this.conecta = conecta;
        this.carr = new CarreraBD(conecta);
        
        vtnPrin.getDpnlPrincipal().add(vtnAlmCar);
        vtnAlmCar.show();
    }
    
    public void iniciar() {
        cargarCmbCarreras();
        
        String[] titulo = {"Carrera", "Alumno", "Fecha"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);        
        vtnAlmCar.getTblAlmnCarrera().setModel(mdTbl);
        //Llenamos la tabla
        cargarAlmnsCarrera();
    }
    
    private void cargarAlmnsCarrera(){
        almnsCarr = almnCar.cargarAlumnoCarrera(); 
        llenarTblAlmnCarreras(almnsCarr);
    }
    
    private void llenarTblAlmnCarreras(ArrayList<AlumnoCarreraMD> almns) {
        mdTbl.setRowCount(0);
        if (almns != null) {
            almns.forEach(a -> {
                Object[] valores = {a.getCarrera().getCodigo(),
                    a.getAlumno().getPrimerApellido() + " "
                    + a.getAlumno().getSegundoApellido() + " "
                    + a.getAlumno().getPrimerNombre() + " "
                    + a.getAlumno().getSegundoNombre(), 
                a.getFechaRegistro().getDayOfMonth() + "/" +
                        a.getFechaRegistro().getMonth()+ "/"+ 
                        a.getFechaRegistro().getYear()};
                mdTbl.addRow(valores);
            });
        }
    }
    
    private void cargarCmbCarreras() {
        carreras = carr.cargarCarreras();
        if (carreras != null) {
            vtnAlmCar.getCmbCarrera().removeAllItems();
            vtnAlmCar.getCmbCarrera().addItem("Todos");
            carreras.forEach((c) -> {
                vtnAlmCar.getCmbCarrera().addItem(c.getCodigo());
            });
        }
    }
    
}
