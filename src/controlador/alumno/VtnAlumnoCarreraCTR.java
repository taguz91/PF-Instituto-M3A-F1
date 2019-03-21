package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.alumno.AlumnoCarreraBD;
import modelo.alumno.AlumnoCarreraMD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.usuario.RolMD;
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
    private final RolMD permisos;
    private final VtnPrincipalCTR ctrPrin;

    private ArrayList<AlumnoCarreraMD> almnsCarr;

    //Modelo de la tabla
    private DefaultTableModel mdTbl;

    private final CarreraBD carr;
    private ArrayList<CarreraMD> carreras;

    public VtnAlumnoCarreraCTR(VtnPrincipal vtnPrin, VtnAlumnoCarrera vtnAlmCar, ConectarDB conecta, RolMD permisos, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.vtnAlmCar = vtnAlmCar;
        this.almnCar = new AlumnoCarreraBD(conecta);
        this.conecta = conecta;
        this.carr = new CarreraBD(conecta);
        this.permisos = permisos;
        this.ctrPrin = ctrPrin;

        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Alumnos por carrera");

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
        
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Alumnos por carrera");
    }

    private void cargarAlmnsCarrera() {
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
                    a.getFechaRegistro().getDayOfMonth() + "/"
                    + a.getFechaRegistro().getMonth() + "/"
                    + a.getFechaRegistro().getYear()};
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
