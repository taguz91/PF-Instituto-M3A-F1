package controlador.docente;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.docente.DocenteMateriaBD;
import modelo.docente.DocenteMateriaMD;
import modelo.estilo.TblEstilo;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import vista.docente.FrmDocenteMateria;

/**
 *
 * @author Johnny
 */
public class FrmDocenteMateriaCTR extends DCTR {

    private final FrmDocenteMateria frmDM;

    private final DocenteMateriaBD DMBD = DocenteMateriaBD.single();
    private DocenteMateriaMD docenMat;
    private final DocenteBD DBD = DocenteBD.single();
    private final CarreraBD CRBD = CarreraBD.single();
    private final MateriaBD MTBD = MateriaBD.single();

    private ArrayList<DocenteMD> docentes;
    private ArrayList<CarreraMD> carreras;
    private ArrayList<MateriaMD> materias;
    private ArrayList<Integer> ciclos;

    DefaultTableModel mdTbl;

    public FrmDocenteMateriaCTR(FrmDocenteMateria frmDM, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.frmDM = frmDM;
    }

    public void iniciar() {
        frmDM.getLblError().setText("");
        //Iniciamos la tabla
        String[] titulo = {"Cédula", "Docente"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        frmDM.getTblDocentes().setModel(mdTbl);
        TblEstilo.formatoTbl(frmDM.getTblDocentes());
        //Le damos formato a la tabla  
        TblEstilo.columnaMedida(frmDM.getTblDocentes(), 0, 100);
        //Desabilitamos el combo de materia y ciclo, se activaran al escoger una carrera
        estadoCmbCicloYMateria(false);
        llenarCmbCarrera();
        //Buscador 
        frmDM.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String a = frmDM.getTxtBuscar().getText().trim();
                if (e.getKeyCode() == 10) {
                    buscarDocente(a);
                }
            }
        });
        //Acciones de los combos
        frmDM.getCmbCarrera().addActionListener(e -> clickCarreras());
        frmDM.getCmbCiclo().addActionListener(e -> clickCiclo());
        //Acciones de los botones
        frmDM.getBtnBuscar().addActionListener(e -> buscarDocente(frmDM.getTxtBuscar().getText().trim()));
        frmDM.getBtnGuardar().addActionListener(e -> guardarYSalir());
        iniciarValidaciones();

        ctrPrin.agregarVtn(frmDM);
    }

    private void iniciarValidaciones() {
        frmDM.getCmbCarrera().addActionListener(new CmbValidar(frmDM.getCmbCarrera()));
        frmDM.getCmbCiclo().addActionListener(new CmbValidar(frmDM.getCmbCiclo()));
        frmDM.getCmbMateria().addActionListener(new CmbValidar(frmDM.getCmbMateria()));
        frmDM.getTxtBuscar().addKeyListener(new TxtVBuscador(frmDM.getTxtBuscar()));
    }

    private void guardarYSalir() {
        if (guardar()) {
            frmDM.dispose();
            ctrPrin.cerradoJIF();
        }

    }

    private boolean guardar() {
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
            docenMat = DMBD.existeDocenteMateria(docentes.get(posDoc).getIdDocente(),
                    materias.get(posMat - 1).getId());
            if (docenMat != null) {
                frmDM.getLblError().setText("Ya se asigno esta materia al docente.");
                guardar = false;
                if (!docenMat.isActivo()) {
                    int r = JOptionPane.showConfirmDialog(null, "Ya se asigno esta materia pero se\n"
                            + "encuentra eliminada, desea activala.");
                    if (r == JOptionPane.YES_OPTION) {
                        DMBD.activar(docenMat.getId());
                        frmDM.dispose();
                        ctrPrin.cerradoJIF();
                        ctrPrin.abrirVtnDocenteMateria();
                    }
                }
            } else {
                frmDM.getLblError().setText("");
            }
        }
        if (guardar) {
            DocenteMateriaMD dcm = new DocenteMateriaMD();
            dcm.setDocente(docentes.get(posDoc));
            dcm.setMateria(materias.get(posMat - 1));
            guardar = DMBD.guardar(dcm);
        }
        return guardar;
    }

    //Buscador
    private void buscarDocente(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            docentes = DBD.buscar(aguja);
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
            ciclos = MTBD.cargarCiclosCarrera(idCar);
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
            materias = MTBD.cargarMateriaPorCarreraCiclo(carreras.get(posCar - 1).getId(),
                    posCic);
            llenarCmbMaterias(materias);
        } else {
            clickCarreras();
        }
    }

    private void llenarCmbCarrera() {
        frmDM.getCmbCarrera().removeAllItems();
        carreras = CRBD.cargarCarreras();
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
                    d.getNombreCompleto()};
                mdTbl.addRow(valores);
            });
        }
    }

}
