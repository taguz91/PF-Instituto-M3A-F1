package controlador.materia;

import controlador.carrera.VtnCarreraCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.usuario.RolMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.materia.VtnMateria;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnMateriaCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnMateria vtnMateria;
    private final ConectarDB conecta;
    private final MateriaBD materia;
    private final VtnPrincipalCTR ctrPrin;
    private final RolMD permisos;

    //El modelo de la tabla materias  
    private DefaultTableModel mdTblMat;
    //Aqui guardamos todas las materias  
    private ArrayList<MateriaMD> materias;
    //Para el combo de filtrar por carrera
    private ArrayList<CarreraMD> carreras;
    //Ciclos de una carrera  
    private ArrayList<Integer> ciclos;

    public VtnMateriaCTR(VtnPrincipal vtnPrin, VtnMateria vtnMateria,
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.vtnMateria = vtnMateria;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Materias");
        ctrPrin.setIconJIFrame(vtnMateria);
        this.materia = new MateriaBD(conecta);

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
        vtnMateria.getBtnReporteMaterias().addActionListener(e -> llamaReporteMaterias());
        //Iniciamos el buscador  
        vtnMateria.getBtnBuscar().addActionListener(e -> buscarMaterias(vtnMateria.getTxtBuscar().getText().trim()));
        vtnMateria.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscar();
            }
        });
        //Validacion del buscador
        vtnMateria.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnMateria.getTxtBuscar(),
                vtnMateria.getBtnBuscar()));
        vtnMateria.getBtnInfo().addActionListener(e -> infoMateria());
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Docentes");
    }

    private void infoMateria() {
        int pos = vtnMateria.getTblMateria().getSelectedRow();
        if (pos >= 0) {
            JDMateriaInfoCTR info = new JDMateriaInfoCTR(vtnPrin, conecta, materias.get(pos));
            info.iniciar();
        }
    }

    public void buscar() {
        String buscar = vtnMateria.getTxtBuscar().getText().trim();
        if (buscar.length() > 2) {
            buscarMaterias(buscar);
        }
    }

    public void buscarMaterias(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            materias = materia.cargarMaterias(b);
            cargarTblMaterias();
        }

    }

    public void cargarCmbFiltrar() {
        //Cargamos todas las carreras 
        CarreraBD carrerBD = new CarreraBD(conecta);
        carreras = carrerBD.cargarCarreras();
        //Cargamos el combo 
        vtnMateria.getCmbCarreras().removeAllItems();
        vtnMateria.getCmbCarreras().addItem("Seleccione una carrera");
        carreras.forEach((car) -> {
            vtnMateria.getCmbCarreras().addItem(car.getCodigo());
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
                vtnMateria.getCmbCiclo().addItem(c + "");
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

    public void llamaReporteMaterias() {

        JasperReport jr;
        String path = "./src/vista/reportes/repMaterias.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
            Map parametro = new HashMap();
            parametro.put("carrera",vtnMateria.getCmbCarreras().getSelectedItem());
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle("Reporte de Materias por Carrera");

        } catch (JRException ex) {
            Logger.getLogger(VtnCarreraCTR.class.getName()).log(Level.SEVERE, null, ex);
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
