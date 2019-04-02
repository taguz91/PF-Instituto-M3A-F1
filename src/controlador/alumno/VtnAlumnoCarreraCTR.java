package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.alumno.AlumnoCarreraBD;
import modelo.alumno.AlumnoCarreraMD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.usuario.RolMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
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

    public VtnAlumnoCarreraCTR(VtnPrincipal vtnPrin, VtnAlumnoCarrera vtnAlmCar,
            ConectarDB conecta, RolMD permisos, VtnPrincipalCTR ctrPrin) {
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
        ctrPrin.setIconJIFrame(vtnAlmCar);
        vtnPrin.getDpnlPrincipal().add(vtnAlmCar);
        vtnAlmCar.show();
    }

    public void iniciar() {
        cargarCmbCarreras();

        String[] titulo = {"Carrera", "Alumno", "Cédula", "Fecha inscripción"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        //Le damos unos toques a la tabla  
        TblEstilo.formatoTbl(vtnAlmCar.getTblAlmnCarrera());
        TblEstilo.columnaMedida(vtnAlmCar.getTblAlmnCarrera(), 0, 70);
        TblEstilo.columnaMedida(vtnAlmCar.getTblAlmnCarrera(), 2, 120);
        vtnAlmCar.getTblAlmnCarrera().setModel(mdTbl);
        //Llenamos la tabla
        cargarAlmnsCarrera();
        //acciones 
        vtnAlmCar.getCmbCarrera().addActionListener(e -> clickCmbCarreras());
        //Buscador 
        vtnAlmCar.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnAlmCar.getTxtBuscar().getText().trim();
                if (b.length() > 2) {
                    buscar(b);
                } else if (b.length() == 0) {
                    cargarAlmnsCarrera();
                }
            }
        });
        vtnAlmCar.getBtnBuscar().addActionListener(e -> buscar(vtnAlmCar.getTxtBuscar().getText().trim()));
        vtnAlmCar.getBtnIngresar().addActionListener(e -> abrirFrmAlumnoCarrera());
        //Validacion buscar
        vtnAlmCar.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnAlmCar.getTxtBuscar(),
                vtnAlmCar.getBtnBuscar()));
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Alumnos por carrera");
        //ctrPrin.carga.detener();
    }
    
    private void abrirFrmAlumnoCarrera(){
        ctrPrin.abrirFrmInscripcion();
        vtnAlmCar.dispose();
        ctrPrin.cerradoJIF();
    }

    private void buscar(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            almnsCarr = almnCar.buscar(b);
            llenarTblAlmnCarreras(almnsCarr);
        } else {
            System.out.println("No ingrese caracteres especiales");
        }
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
                    a.getAlumno().getIdentificacion(),
                    a.getFechaRegistro().getDayOfMonth() + "/"
                    + a.getFechaRegistro().getMonth() + "/"
                    + a.getFechaRegistro().getYear()};
                mdTbl.addRow(valores);
            });
            vtnAlmCar.getLblResultados().setText(almns.size() + " Resultados obtenidos.");
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

    private void clickCmbCarreras() {
        int posCar = vtnAlmCar.getCmbCarrera().getSelectedIndex();
        if (posCar > 0) {
            almnsCarr = almnCar.cargarAlumnoCarreraPorCarrera(carreras.get(posCar - 1).getId());
            llenarTblAlmnCarreras(almnsCarr);
        } else {
            cargarAlmnsCarrera();
        }
    }

    private void InitPermisos() {
        for (AccesosMD obj : AccesosBD.SelectWhereACCESOROLidRol(permisos.getId())) {
//            if (obj.getNombre().equals("USUARIOS-Agregar")) {
//                vtnCarrera.getBtnIngresar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-Editar")) {
//                vista.getBtnEditar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-Eliminar")) {
//                vista.getBtnEliminar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-AsignarRoles")) {
//                vista.getBtnAsignarRoles().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-VerRoles")) {
//                vista.getBtnVerRoles().setEnabled(true);
//            }
        }
    }

}
