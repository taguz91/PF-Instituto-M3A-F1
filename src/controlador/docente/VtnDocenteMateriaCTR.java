package controlador.docente;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.docente.DocenteMateriaBD;
import modelo.docente.DocenteMateriaMD;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.validaciones.Validar;
import vista.docente.FrmDocenteMateria;
import vista.docente.VtnDocenteMateria;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnDocenteMateriaCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnDocenteMateria vtnDm;
    private final ConectarDB conecta;

    private final DocenteMateriaBD dm;
    private final CarreraBD car;
    private final MateriaBD mat;

    private ArrayList<CarreraMD> carreras;
    private ArrayList<MateriaMD> materias;
    private ArrayList<DocenteMateriaMD> dms;
    private ArrayList<Integer> ciclos;

    DefaultTableModel mdTbl;

    public VtnDocenteMateriaCTR(VtnPrincipal vtnPrin, VtnDocenteMateria vtnDm, ConectarDB conecta) {
        this.vtnPrin = vtnPrin;
        this.vtnDm = vtnDm;
        this.conecta = conecta;
        //Mostramos el formulario
        vtnPrin.getDpnlPrincipal().add(vtnDm);
        vtnDm.show();
        //Inciamos todos las clases para realizar las consultas
        this.dm = new DocenteMateriaBD(conecta);
        this.car = new CarreraBD(conecta);
        this.mat = new MateriaBD(conecta);
    }

    public void iniciar() {
        //Iniciamos la tabla
        String[] titulo = {"Cedula", "Docente"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        vtnDm.getTblDocentesMateria().setModel(mdTbl);
        TblEstilo.formatoTbl(vtnDm.getTblDocentesMateria());
        //Desabilitamos el combo de materia y ciclo, se activaran al escoger una carrera
        estadoCmbCicloYMateria(false);
        llenarCmbCarrera();
        //Buscador 
        vtnDm.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String a = vtnDm.getTxtBuscar().getText().trim();
                if (a.length() > 2) {
                    buscar(a);
                }
            }
        });
        //Acciones de los combos
        vtnDm.getCmbCarrera().addActionListener(e -> clickCarreras());
        vtnDm.getCmbCiclo().addActionListener(e -> clickCiclo());
        vtnDm.getCmbMateria().addActionListener(e -> clickMateria());
        //Acciones de los botones
        vtnDm.getBtnBuscar().addActionListener(e -> buscar(vtnDm.getTxtBuscar().getText().trim()));
        vtnDm.getBtnIngresar().addActionListener(e -> ingresar());
        
        cargarDocenteMaterias();
    }

    //Buscador
    private void buscar(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            System.out.println("No es funcional");
        }
    }
    
    private void ingresar(){
        FrmDocenteMateria frm = new FrmDocenteMateria(); 
        FrmDocenteMateriaCTR ctr = new FrmDocenteMateriaCTR(vtnPrin, frm, conecta); 
        ctr.iniciar();
    }
    
    private void cargarDocenteMaterias(){
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
            materias = mat.cargarMateriaPorCarrera(idCar);
            llenarCmbMaterias(materias);
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
    private void clickMateria(){
        int posMat = vtnDm.getCmbMateria().getSelectedIndex(); 
        if (posMat > 0) {
            dms = dm.cargarDocenteMateriaPorMateria(materias.get(posMat - 1).getId()); 
            llenarTblDocenteMateria(dms);
        }else{
            clickCiclo();
        }
    }

    private void llenarCmbCarrera() {
        vtnDm.getCmbCarrera().removeAllItems();
        carreras = car.cargarCarreras();
        if (carreras != null) {
            vtnDm.getCmbCarrera().addItem("Seleccione");
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
                Object[] valores = {o.getDocente().getPrimerNombre() + " " 
                        + o.getDocente().getPrimerApellido(), o.getMateria().getNombre()};
                mdTbl.addRow(valores);
            });
        }
    }

}