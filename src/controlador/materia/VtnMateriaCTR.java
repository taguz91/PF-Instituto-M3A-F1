package controlador.materia;

import controlador.carrera.VtnCarreraCTR;
import controlador.persona.FrmPersonaCTR;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.persona.PersonaMD;
import modelo.usuario.RolMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.materia.FrmMaterias;
import vista.materia.FrmRequisitos;
import vista.materia.VtnMateria;
import vista.persona.FrmPersona;
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
    private final CarreraBD carrerBD;

    //El modelo de la tabla materias
    private DefaultTableModel mdTblMat;
    //Aqui guardamos todas las materias
    private ArrayList<MateriaMD> materias;
    //Para el combo de filtrar por carrera
    private ArrayList<CarreraMD> carreras;
    //Ciclos de una carrera
    private ArrayList<Integer> ciclos;

    /**
     * Iniciamos las dependencias de base de datos.
     *
     * @param vtnPrin
     * @param vtnMateria
     * @param conecta
     * @param ctrPrin
     * @param permisos
     */
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
        this.carrerBD = new CarreraBD(conecta);
        vtnPrin.getDpnlPrincipal().add(vtnMateria);
        vtnMateria.show();

    }

    /**
     * Iniciamos dependencias: Eventos de botones y formato de la tabla
     */
    public void iniciar() {
        vtnMateria.getBtnRequisitos().addActionListener(e -> abrirFrmRequisito());
       // vtnMateria.getBtnReporteMaterias().setEnabled(false);
        String titulo[] = {"id", "Código", "Nombre", "Ciclo", "Docencia", "Prácticas", "Autónomas", "Presencial", "Total"};
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
        vtnMateria.getLblError().setVisible(false);

        materias = materia.cargarMaterias();
        cargarTblMaterias();
        cargarCmbCarreras();
        vtnMateria.getCmbCarreras().addActionListener(e -> filtrarPorCarrera());
        vtnMateria.getCmbCiclo().addActionListener(e -> filtrarPorCarreraPorCiclo());
        vtnMateria.getBtnIngresarMateria().addActionListener(e -> ingresarMaterias());
        vtnMateria.getBtnEditarMateria().addActionListener(e -> editarMaterias());
        vtnMateria.getBtnEliminarMateria().addActionListener(e -> eliminarMaterias());

//        vtnMateria.getCmbCarreras().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                validarBotonesReportes();
//            }
//        });
        vtnMateria.getBtnReporteMaterias().addActionListener(e -> llamaReporteMaterias());
        //Iniciamos el buscador
        vtnMateria.getBtnBuscar().addActionListener(e -> buscarMaterias(vtnMateria.getTxtBuscar().getText().trim()));
        vtnMateria.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnMateria.getTxtBuscar().getText().trim();

                if (e.getKeyCode() == 10) {
                    buscarMaterias(b);
                }
            }
        });
        //Validacion del buscador 
        vtnMateria.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnMateria.getTxtBuscar(),
                vtnMateria.getBtnBuscar()));
        vtnMateria.getBtnInfo().addActionListener(e -> infoMateria());
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Docentes");
    }

    /**
     * Informacion de la materia, nos indican sus materias de co y pre
     * requisitos.
     */
    private void infoMateria() {
        int pos = vtnMateria.getTblMateria().getSelectedRow();
        if (pos >= 0) {
            MateriaMD mt = materia.buscarMateriaInfo(materias.get(pos).getId());
            JDMateriaInfoCTR info = new JDMateriaInfoCTR(vtnPrin, conecta, mt, ctrPrin, materia);
            info.iniciar();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una materia");
        }
    }

    /**
     * BUscador
     */
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

    public void cargarCmbCarreras() {
        carreras = carrerBD.cargarCarrerasCmb();
        //Cargamos el combo
        vtnMateria.getCmbCarreras().removeAllItems();
        vtnMateria.getCmbCarreras().addItem("Seleccione una carrera");
        carreras.forEach((car) -> {
            vtnMateria.getCmbCarreras().addItem(car.getCodigo());
        });
    }

    private void filtrarPorCarrera() {
        int pos = vtnMateria.getCmbCarreras().getSelectedIndex();
        //validarBotonesReportes();
        if (pos > 0) {
            materias = materia.cargarMateriaPorCarrera(carreras.get(pos - 1).getId());
            //Cargamos los ciclos de una carrera
            ciclos = materia.cargarCiclosCarrera(carreras.get(pos - 1).getId());
            vtnMateria.getCmbCiclo().removeAllItems();
            vtnMateria.getCmbCiclo().addItem("Todos");
            ciclos.forEach(c -> {
                vtnMateria.getCmbCiclo().addItem(c + "");
            });
            vtnMateria.getCmbCiclo().setSelectedIndex(0);
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
        String path = "/vista/reportes/repMaterias.jasper";
        File dir = new File("./");
        System.out.println("Dirección: " + dir.getAbsolutePath());
        try {
            Map parametro = new HashMap();
            parametro.put("consulta", materia.getSql());
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle("Reporte de Materias por Carrera");

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
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

//    public void validarBotonesReportes() {
//        int selecTabl = vtnMateria.getCmbCarreras().getSelectedIndex();
//        if (selecTabl > 0) {
//            vtnMateria.getBtnReporteMaterias().setEnabled(true);
//        } else {
//            vtnMateria.getBtnReporteMaterias().setEnabled(false);
//        }
//    }

    public void abrirFrmRequisito() {
        int fila = vtnMateria.getTblMateria().getSelectedRow();
        if (fila >= 0) {

            FrmRequisitos frmreq = new FrmRequisitos();
            VtnRequisitosCTR vtnreq = new VtnRequisitosCTR(conecta, ctrPrin, vtnPrin, frmreq, materia, materias.get(fila));
            vtnreq.iniciar();
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "Seleccione una materia");
        }

    }

    private void ingresarMaterias() {
        ctrPrin.abrirFrmMateria();
        vtnMateria.dispose();
        ctrPrin.cerradoJIF();
    }

    private void editarMaterias() {
        int posFila = vtnMateria.getTblMateria().getSelectedRow();
        if (posFila >= 0) {
            vtnMateria.getLblError().setVisible(false);
            FrmMaterias frmMateria = new FrmMaterias();
            FrmMateriasCTR ctrFrm = new FrmMateriasCTR(vtnPrin, frmMateria, conecta, ctrPrin);
            ctrFrm.iniciar();
            //Le pasamos la persona de nuestro lista justo la persona seleccionada
            MateriaMD matEditar = materia.buscarMateria(Integer.parseInt(vtnMateria.getTblMateria().getValueAt(posFila, 0).toString()));
            ctrFrm.editarMaterias(matEditar);
            cargarTblMaterias();
        } else {
            vtnMateria.getLblError().setVisible(true);
        }

    }

    private void eliminarMaterias() {
        int posFila = vtnMateria.getTblMateria().getSelectedRow();
        if (posFila >= 0) {
            MateriaMD mate;
            System.out.println(Integer.valueOf(vtnMateria.getTblMateria().getValueAt(posFila, 0).toString()));
            mate = materia.buscarMateria(Integer.valueOf(vtnMateria.getTblMateria().getValueAt(posFila, 0).toString()));
            int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar a \n"
                    + vtnMateria.getTblMateria().getValueAt(posFila, 2) + "?", " Eliminar Materia", dialog);
            if (result == 0) {
                if (materia.elminarMateria(mate.getId()) == true) {
                    JOptionPane.showMessageDialog(null, "Datos Eliminados Satisfactoriamente");

                    cargarTblMaterias();
                    vtnMateria.getTxtBuscar().setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR A LA MATERIA");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA PARA ELIMINAR A LA MATERIA");
        }
    }

}
