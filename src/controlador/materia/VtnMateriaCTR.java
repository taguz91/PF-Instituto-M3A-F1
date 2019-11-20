package controlador.materia;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import utils.CONS;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.materia.FrmMaterias;
import vista.materia.FrmRequisitos;
import vista.materia.VtnMateria;

/**
 *
 * @author Johnny
 */
public class VtnMateriaCTR extends DVtnCTR {

    private final VtnMateria vtnMateria;
    private final MateriaBD materia;
    private final CarreraBD carrerBD;

    //Aqui guardamos todas las materias
    private ArrayList<MateriaMD> materias;
    //Para el combo de filtrar por carrera
    private ArrayList<CarreraMD> carreras;
    //Ciclos de una carrera
    private ArrayList<Integer> ciclos;

    /**
     * Iniciamos las dependencias de base de datos.
     *
     * @param vtnMateria
     * @param ctrPrin
     */
    public VtnMateriaCTR(VtnMateria vtnMateria, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.vtnMateria = vtnMateria;
        this.materia = new MateriaBD(ctrPrin.getConecta());
        this.carrerBD = new CarreraBD(ctrPrin.getConecta());
        InitPermisosDocente();
    }

    /**
     * Iniciamos dependencias: Eventos de botones y formato de la tabla
     */
    public void iniciar() {
        vtnMateria.getBtnRequisitos().addActionListener(e -> abrirFrmRequisito());
        // vtnMateria.getBtnReporteMaterias().setEnabled(false);
        String titulo[] = {"id", "Carrera", "Código", "Nombre", "Ciclo", "Docencia", "Prácticas", "Autónomas", "Presencial", "Total"};
        String datos[][] = {};
        //Usamos el modelo que no nos deja editar los campos
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        //Le pasamos el modelo a la tabla  v
        vtnMateria.getTblMateria().setModel(mdTbl);
        //Ocusltamos el id
        TblEstilo.ocualtarID(vtnMateria.getTblMateria());
        //Pasamos el estilo a la tabla
        TblEstilo.formatoTbl(vtnMateria.getTblMateria());
        //Pasamos la columna de codigo para que sea de 20
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 1, 70);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 2, 70);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 4, 40);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 5, 70);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 6, 70);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 7, 70);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 8, 70);
        TblEstilo.columnaMedida(vtnMateria.getTblMateria(), 9, 40);
        //Iniciamos el combo de ciclos
        vtnMateria.getCmbCiclo().removeAllItems();
        vtnMateria.getCmbCiclo().addItem("Todos");
        vtnMateria.getLblError().setVisible(false);

        cargarCmbCarreras();
        materias = materia.cargarMaterias();
        cargarTblMaterias();
        vtnMateria.getCmbCarreras().addActionListener(e -> filtrarPorCarrera());
        vtnMateria.getCmbCiclo().addActionListener(e -> filtrarPorCarreraPorCiclo());
        vtnMateria.getBtnIngresarMateria().addActionListener(e -> ingresarMaterias());
        vtnMateria.getBtnEditarMateria().addActionListener(e -> editarMaterias());
//        vtnMateria.getBtnEliminarMateria().addActionListener(e -> eliminarMaterias());
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

        ctrPrin.agregarVtn(vtnMateria);
        vtnMateria.getBtnEliminarMateria().setVisible(false);
        InitPermisos();
    }

    /**
     * Informacion de la materia, nos indican sus materias de co y pre
     * requisitos.
     */
    private void infoMateria() {
        int pos = vtnMateria.getTblMateria().getSelectedRow();
        if (pos >= 0) {
            MateriaMD mt = materia.buscarMateriaInfo(materias.get(pos).getId());
            JDMateriaInfoCTR info = new JDMateriaInfoCTR(mt, ctrPrin, materia);
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

    private void InitPermisosDocente() {
        if (ctrPrin.getRolSeleccionado().getNombre().equalsIgnoreCase("Docente")) {
            vtnMateria.getBtnEliminarMateria().setEnabled(false);
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
        mdTbl.setRowCount(0);
        vtnMateria.getLblResultados().setText(materias.size() + " Resultados obtenidos.");
        if (!materias.isEmpty()) {
            materias.forEach((mt) -> {
                Object valores[] = {mt.getId(),
                    obtenerCodigoCarrera(mt.getCarrera().getId()),
                    mt.getCodigo(), mt.getNombre(),
                    mt.getCiclo(), mt.getHorasDocencia(),
                    mt.getHorasPracticas(), mt.getHorasAutoEstudio(),
                    mt.getHorasPresenciales(), mt.getTotalHoras()};
                mdTbl.addRow(valores);
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
        try {
            Map parametro = new HashMap();
            parametro.put("consulta", materia.getSql());
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Materias por Carrera");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }

    private void InitPermisos() {
        vtnMateria.getBtnIngresarMateria().getAccessibleContext().setAccessibleName("Materias-Ingresar");
        vtnMateria.getBtnRequisitos().getAccessibleContext().setAccessibleName("Materias-Requisitos");
        vtnMateria.getBtnInfo().getAccessibleContext().setAccessibleName("Materias-Informacion");
        vtnMateria.getBtnEditarMateria().getAccessibleContext().setAccessibleName("Materias-Editar");
        vtnMateria.getBtnEliminarMateria().getAccessibleContext().setAccessibleName("Materias-Eliminar");
        vtnMateria.getBtnReporteMaterias().getAccessibleContext().setAccessibleName("Materias-Reporte");
        
        
        
        CONS.activarBtns(vtnMateria.getBtnIngresarMateria(), vtnMateria.getBtnRequisitos(),
                vtnMateria.getBtnInfo(), vtnMateria.getBtnEditarMateria(), 
                vtnMateria.getBtnEliminarMateria(), vtnMateria.getBtnReporteMaterias());
        
    }

    public void abrirFrmRequisito() {
        posFila = vtnMateria.getTblMateria().getSelectedRow();
        if (posFila >= 0) {

            FrmRequisitos frmreq = new FrmRequisitos();
            VtnRequisitosCTR vtnreq = new VtnRequisitosCTR(ctrPrin, frmreq, materia, materias.get(posFila));
            vtnreq.iniciar();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una materia");
        }

    }

    private void ingresarMaterias() {
        ctrPrin.abrirFrmMateria();
        vtnMateria.dispose();
        ctrPrin.cerradoJIF();
    }

    private void editarMaterias() {
        posFila = vtnMateria.getTblMateria().getSelectedRow();
        if (posFila >= 0) {
            vtnMateria.getLblError().setVisible(false);
            FrmMaterias frmMateria = new FrmMaterias();
            FrmMateriasCTR ctrFrm = new FrmMateriasCTR(frmMateria, ctrPrin, this);
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
        posFila = vtnMateria.getTblMateria().getSelectedRow();
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

    private String obtenerCodigoCarrera(int idCarrera) {
        String cod = "";
        for (int i = 0; i < carreras.size(); i++) {
            if (carreras.get(i).getId() == idCarrera) {
                cod = carreras.get(i).getCodigo();
                break;
            }
        }
        return cod;
    }

    public void actualizarVtn() {
        if (vtnMateria.getTxtBuscar().getText().length() > 0) {
            buscar();
        } else {
            filtrarPorCarreraPorCiclo();
        }
    }

}
