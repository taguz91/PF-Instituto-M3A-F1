package controlador.docente;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.CONS;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.docente.DocenteMateriaBD;
import modelo.docente.DocenteMateriaMD;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import vista.docente.VtnDocenteMateria;

/**
 *
 * @author Johnny
 */
public class VtnDocenteMateriaCTR extends DVtnCTR {

    private final VtnDocenteMateria vtnDm;

    private final DocenteMateriaBD dm;
    private final CarreraBD car;
    private final MateriaBD mat;

    private ArrayList<CarreraMD> carreras;
    private ArrayList<MateriaMD> materias;
    private ArrayList<DocenteMateriaMD> dms;
    private ArrayList<Integer> ciclos;

    public VtnDocenteMateriaCTR(VtnDocenteMateria vtnDm, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.vtnDm = vtnDm;
        //Inciamos todos las clases para realizar las consultas
        this.dm = new DocenteMateriaBD(ctrPrin.getConecta());
        this.car = new CarreraBD(ctrPrin.getConecta());
        this.mat = new MateriaBD(ctrPrin.getConecta());
    }

    public void iniciar() {
        //Iniciamos la tabla
        String[] titulo = {"Cédula", "Docente", "Materia"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        vtnDm.getTblDocentesMateria().setModel(mdTbl);
        TblEstilo.formatoTbl(vtnDm.getTblDocentesMateria());
        TblEstilo.columnaMedida(vtnDm.getTblDocentesMateria(), 0, 100);
        //Desabilitamos el combo de materia y ciclo, se activaran al escoger una carrera
        estadoCmbCicloYMateria(false);
        llenarCmbCarrera();
        //Buscador
        vtnDm.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnDm.getTxtBuscar().getText().trim();
                if (e.getKeyCode() == 10) {
                    buscar(b);
                } else if (b.length() == 0) {
                    cargarDocenteMaterias();
                }
            }
        });
        //Validacion del buscar
        vtnDm.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnDm.getTxtBuscar(),
                vtnDm.getBtnBuscar()));
        //Acciones de los combos
        vtnDm.getCmbCarrera().addActionListener(e -> clickCarreras());
        vtnDm.getCmbCiclo().addActionListener(e -> clickCiclo());
        vtnDm.getCmbMateria().addActionListener(e -> clickMateria());
        //Acciones de los botones
        vtnDm.getBtnBuscar().addActionListener(e -> buscar(vtnDm.getTxtBuscar().getText().trim()));
        vtnDm.getBtnIngresar().addActionListener(e -> ingresar());
        vtnDm.getBtnEliminar().addActionListener(e -> eliminar());

        cargarDocenteMaterias();

        ctrPrin.agregarVtn(vtnDm);
        InitPermisos();
    }

    /**
     * Eliminamos el docente materia
     *
     * @param aguja
     */
    private void eliminar() {
        int pos = vtnDm.getTblDocentesMateria().getSelectedRow();
        if (pos >= 0) {
            dm.eliminar(dms.get(pos).getId());
            buscar(vtnDm.getTxtBuscar().getText().trim());
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila primero.");
        }
    }

    //Buscador
    private void buscar(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            dms = dm.buscar(aguja);
            llenarTblDocenteMateria(dms);
        }
    }

    private void ingresar() {
        ctrPrin.abrirFrmDocenteMateria();
        vtnDm.dispose();
        ctrPrin.cerradoJIF();
    }

    private void cargarDocenteMaterias() {
        dms = dm.cargarDocenteMateria();
        llenarTblDocenteMateria(dms);
    }

    //Este metodo nos habilitara o inavilitara los combos siempre borrando todo antes
    public void estadoCmbCicloYMateria(boolean estado) {
        vtnDm.getCmbCiclo().setEnabled(estado);
        vtnDm.getCmbCiclo().removeAllItems();
        vtnDm.getCmbMateria().setEnabled(estado);
        vtnDm.getCmbMateria().removeAllItems();
    }

    //Al seleecionar una carrera se llenara los combos de ciclos y materias
    private void clickCarreras() {
        int posCar = vtnDm.getCmbCarrera().getSelectedIndex();
        if (posCar > 0) {
            estadoCmbCicloYMateria(true);
            int idCar = carreras.get(posCar - 1).getId();
//            materias = mat.cargarMateriasCarreraCmb(idCar);
//            llenarCmbMaterias(materias);
            ciclos = mat.cargarCiclosCarrera(idCar);
            llenarCmbCiclo(ciclos);

            dms = dm.cargarDocenteMateriaPorCarrera(idCar);
            llenarTblDocenteMateria(dms);
        } else {
            estadoCmbCicloYMateria(false);
        }
    }

    //Al seleccionar ciclos se deben cargar solo las materias de ese ciclo
    private void clickCiclo() {
        int posCar = vtnDm.getCmbCarrera().getSelectedIndex();
        int posCic = vtnDm.getCmbCiclo().getSelectedIndex();
        if (posCar > 0 && posCic > 0) {
            materias = mat.cargarMateriaPorCarreraCiclo(carreras.get(posCar - 1).getId(),
                    posCic);
            llenarCmbMaterias(materias);
            dms = dm.cargarDocenteMateriaPorCarreraYCiclo(carreras.get(posCar - 1).getId(),
                    posCic);
            llenarTblDocenteMateria(dms);
        } else {
            clickCarreras();
        }
    }

    //Al hacer click en una materia
    private void clickMateria() {
        int posMat = vtnDm.getCmbMateria().getSelectedIndex();
        if (posMat > 0) {
            dms = dm.cargarDocenteMateriaPorMateria(materias.get(posMat - 1).getId());
            llenarTblDocenteMateria(dms);
        } else {
            clickCiclo();
        }
    }

    private void llenarCmbCarrera() {
        vtnDm.getCmbCarrera().removeAllItems();
        carreras = car.cargarCarrerasCmb();
        if (carreras != null) {
            vtnDm.getCmbCarrera().addItem("Todas");
            carreras.forEach(c -> {
                vtnDm.getCmbCarrera().addItem(c.getCodigo());
            });
        }
    }

    private void llenarCmbMaterias(ArrayList<MateriaMD> materias) {
        vtnDm.getCmbMateria().removeAllItems();
        if (materias != null) {
            vtnDm.getCmbMateria().addItem("Todos");
            materias.forEach(m -> {
                vtnDm.getCmbMateria().addItem(m.getNombre());
            });
            vtnDm.getCmbMateria().setSelectedIndex(0);
        }
    }

    private void llenarCmbCiclo(ArrayList<Integer> ciclos) {
        vtnDm.getCmbCiclo().removeAllItems();
        if (ciclos != null) {
            vtnDm.getCmbCiclo().addItem("Todos");
            ciclos.forEach((c) -> {
                vtnDm.getCmbCiclo().addItem(c + "");
            });
            vtnDm.getCmbCiclo().setSelectedIndex(0);
        }
    }

    private void llenarTblDocenteMateria(ArrayList<DocenteMateriaMD> dms) {
        mdTbl.setRowCount(0);
        if (dms != null) {
            dms.forEach(o -> {
                Object[] valores = {o.getDocente().getIdentificacion(),
                    o.getDocente().getPrimerNombre() + " "
                    + o.getDocente().getPrimerApellido(), o.getMateria().getNombre()};
                mdTbl.addRow(valores);
            });
            vtnDm.getLblResultados().setText(dms.size() + " Resultados obtenidos.");
        }
    }

    private void InitPermisos() {
        vtnDm.getBtnIngresar().getAccessibleContext().setAccessibleName("Materia-Docentes-Ingresar");
       vtnDm.getBtnEliminar().getAccessibleContext().setAccessibleName("Materia-Docentes-Eliminar");

        CONS.activarBtns(vtnDm.getBtnIngresar(), vtnDm.getBtnEliminar());
    }

}
