package controlador.docente;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.docente.DocenteMateriaBD;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import vista.docente.FrmDocenteMateria;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmDocenteMateriaCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmDocenteMateria frmDM;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;

    private final DocenteMateriaBD dm;
    private final DocenteBD doc;
    private final CarreraBD car;
    private final MateriaBD mat;

    private ArrayList<DocenteMD> docentes;
    private ArrayList<CarreraMD> carreras;
    private ArrayList<MateriaMD> materias;
    private ArrayList<Integer> ciclos;

    DefaultTableModel mdTbl;

    public FrmDocenteMateriaCTR(VtnPrincipal vtnPrin, FrmDocenteMateria frmDM, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmDM = frmDM;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;        
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaFrm("Docente materia");
        //Mostramos el formulario
        vtnPrin.getDpnlPrincipal().add(frmDM);
        frmDM.show();
        //Inciamos todos las clases para realizar las consultas
        this.dm = new DocenteMateriaBD(conecta);
        this.doc = new DocenteBD(conecta);
        this.car = new CarreraBD(conecta);
        this.mat = new MateriaBD(conecta);
    }

    public void iniciar() {
        iniciarValidaciones();
        //Iniciamos la tabla
        String[] titulo = {"Cedula", "Docente"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        frmDM.getTblDocentes().setModel(mdTbl);
        TblEstilo.formatoTbl(frmDM.getTblDocentes());
        //Desabilitamos el combo de materia y ciclo, se activaran al escoger una carrera
        estadoCmbCicloYMateria(false);
        llenarCmbCarrera();
        //Buscador 
        frmDM.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String a = frmDM.getTxtBuscar().getText().trim();
                if (a.length() > 2) {
                    buscarDocente(a);
                }
            }
        });
        //Acciones de los combos
        frmDM.getCmbCarrera().addActionListener(e -> clickCarreras());
        frmDM.getCmbCiclo().addActionListener(e -> clickCiclo());
        //Acciones de los botones
        frmDM.getBtnBuscar().addActionListener(e -> buscarDocente(frmDM.getTxtBuscar().getText().trim()));
        frmDM.getBtnGuardar().addActionListener(e -> guardar());
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaFrmFin("Docente materia");
        
    }

    private void iniciarValidaciones() {
        frmDM.getCmbCarrera().addActionListener(new CmbValidar(frmDM.getCmbCarrera()));
        frmDM.getCmbCiclo().addActionListener(new CmbValidar(frmDM.getCmbCiclo()));
        frmDM.getCmbMateria().addActionListener(new CmbValidar(frmDM.getCmbMateria()));
        frmDM.getTxtBuscar().addKeyListener(new TxtVBuscador(frmDM.getTxtBuscar()));
    }

    private void guardar() {
        boolean guardar = true;
        int posMat = frmDM.getCmbMateria().getSelectedIndex();
        int posDoc = frmDM.getTblDocentes().getSelectedRow();
        if (posMat < 1) {
            guardar = false;
        }
        if (posDoc < 0) {
            guardar = false;
        }

        if (guardar) {
            dm.setDocente(docentes.get(posDoc));
            dm.setMateria(materias.get(posMat - 1));

            dm.guardar();
        }
    }

    //Buscador
    private void buscarDocente(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            docentes = doc.buscar(aguja);
            llenarTblDocentes(docentes);
        }
    }

    //Este metodo nos habilitara o inavilitara los combos siempre borrando todo antes
    public void estadoCmbCicloYMateria(boolean estado) {
        frmDM.getCmbCiclo().setEnabled(estado);
        frmDM.getCmbCiclo().removeAllItems();
        frmDM.getCmbMateria().setEnabled(estado);
        frmDM.getCmbMateria().removeAllItems();
    }

    //Al seleecionar una carrera se llenara los combos de ciclos y materias 
    private void clickCarreras() {
        int posCar = frmDM.getCmbCarrera().getSelectedIndex();
        if (posCar > 0) {
            estadoCmbCicloYMateria(true);
            int idCar = carreras.get(posCar - 1).getId();
            materias = mat.cargarMateriaPorCarrera(idCar);
            llenarCmbMaterias(materias);
            ciclos = mat.cargarCiclosCarrera(idCar);
            llenarCmbCiclo(ciclos);
        } else {
            estadoCmbCicloYMateria(false);
        }
    }

    //Al seleccionar ciclos se deben cargar solo las materias de ese ciclo
    private void clickCiclo() {
        int posCar = frmDM.getCmbCarrera().getSelectedIndex();
        int posCic = frmDM.getCmbCiclo().getSelectedIndex();
        if (posCar > 0 && posCic > 0) {
            materias = mat.cargarMateriaPorCarreraCiclo(carreras.get(posCar - 1).getId(),
                    posCic);
            llenarCmbMaterias(materias);
        } else {
            clickCarreras();
        }
    }

    private void llenarCmbCarrera() {
        frmDM.getCmbCarrera().removeAllItems();
        carreras = car.cargarCarreras();
        if (carreras != null) {
            frmDM.getCmbCarrera().addItem("Seleccione");
            carreras.forEach(c -> {
                frmDM.getCmbCarrera().addItem(c.getCodigo());
            });
        }
    }

    private void llenarCmbMaterias(ArrayList<MateriaMD> materias) {
        frmDM.getCmbMateria().removeAllItems();
        if (materias != null) {
            frmDM.getCmbMateria().addItem("Todos");
            materias.forEach(m -> {
                frmDM.getCmbMateria().addItem(m.getNombre());
            });
        }
    }

    private void llenarCmbCiclo(ArrayList<Integer> ciclos) {
        frmDM.getCmbCiclo().removeAllItems();
        if (ciclos != null) {
            frmDM.getCmbCiclo().addItem("Todos");
            ciclos.forEach((c) -> {
                frmDM.getCmbCiclo().addItem(c + "");
            });
            frmDM.getCmbCiclo().setSelectedIndex(0);
        }
    }

    private void llenarTblDocentes(ArrayList<DocenteMD> docentes) {
        mdTbl.setRowCount(0);
        if (docentes != null) {
            docentes.forEach(d -> {
                Object[] valores = {d.getIdentificacion(),
                    d.getPrimerNombre() + " " + d.getPrimerApellido()};
                mdTbl.addRow(valores);
            });
        }
    }

}
