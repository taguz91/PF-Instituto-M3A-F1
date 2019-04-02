package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.alumno.AlumnoCarreraBD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.alumno.MallaAlumnoBD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import vista.alumno.FrmAlumnoCarrera;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmAlumnoCarreraCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmAlumnoCarrera frmAlmCarrera;
    private final ConectarDB conecta;
    private final AlumnoCarreraBD almnCarrera;
    private final VtnPrincipalCTR ctrPrin;

    //Modelo de la tabla  
    private DefaultTableModel mdTbl;

    //Para rellenar los combo box
    private final AlumnoBD almn;
    private ArrayList<AlumnoMD> alumnos;

    private final CarreraBD carr;
    private ArrayList<CarreraMD> carreras;

    private final MateriaBD mat;
    private ArrayList<MateriaMD> materias;
    //Para guardar la malla
    private final MallaAlumnoBD malla;

    private final String[] MODALIDADES = {"PRESENCIAL", "SEMIPRESENCIAL", "DISTANCIA", "DUAL"};

    public FrmAlumnoCarreraCTR(VtnPrincipal vtnPrin, FrmAlumnoCarrera frmAlmCarrera, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmAlmCarrera = frmAlmCarrera;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaFrm("Alumno por carrera");
        ctrPrin.setIconJIFrame(frmAlmCarrera);
        this.almnCarrera = new AlumnoCarreraBD(conecta);
        this.mat = new MateriaBD(conecta);
        this.malla = new MallaAlumnoBD(conecta);
        this.almn = new AlumnoBD(conecta);
        this.carr = new CarreraBD(conecta);

        vtnPrin.getDpnlPrincipal().add(frmAlmCarrera);
        frmAlmCarrera.show();
    }

    public void iniciar() {
        ocultarErrores();
        cargarCmbModalidad();
        cargarCmbCarreras();

        String[] titulo = {"Cédula", "Nombre"};
        String[][] datos = {};

        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        frmAlmCarrera.getTblAlumnos().setModel(mdTbl);
        TblEstilo.formatoTbl(frmAlmCarrera.getTblAlumnos());

        frmAlmCarrera.getBtnGuardar().addActionListener(e -> guardar());
        frmAlmCarrera.getBtnBuscar().addActionListener(e -> buscarAlmns(
                frmAlmCarrera.getTxtBuscar().getText().trim()));
        frmAlmCarrera.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String aguja = frmAlmCarrera.getTxtBuscar().getText().trim();
                if (aguja.length() > 2) {
                    buscarAlmns(aguja);
                }
            }
        });
        frmAlmCarrera.getTxtBuscar().addKeyListener(new TxtVBuscador(frmAlmCarrera.getTxtBuscar(),
                frmAlmCarrera.getBtnBuscar()));
        //Para poder editarlo
        //frmAlmCarrera.getCmbCarreras().setEditable(true);
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaFrmFin("Alumno por carrera");
    }

    public void ocultarErrores() {
        //Ocultamos el error  
        frmAlmCarrera.getLblError().setVisible(false);
    }

    private void guardar() {
        boolean guardar = true;
        int posAlm = frmAlmCarrera.getTblAlumnos().getSelectedRow();
        int posCar = frmAlmCarrera.getCmbCarreras().getSelectedIndex();

        if (posAlm < 0 || posCar < 1) {
            guardar = false;
            frmAlmCarrera.getLblError().setVisible(true);
        } else {
            frmAlmCarrera.getLblError().setVisible(false);
        }

        if (guardar) {
            almnCarrera.setAlumno(alumnos.get(posAlm));
            almnCarrera.setCarrera(carreras.get(posCar - 1));
            if (almnCarrera.guardar()) {
                iniciarMallaAlumno(almnCarrera.getAlumno().getId_Alumno(), almnCarrera.getCarrera().getId());
            }
        }
    }

    public void iniciarMallaAlumno(int idAlumno, int idCarrera) {
        materias = mat.cargarMateriaPorCarrera(idCarrera);
        System.out.println("Materias que se deben crear " + materias.size());

        materias.forEach((m) -> {
            malla.iniciarMalla(m.getId(), idAlumno, m.getCiclo());
        });
    }

    public void buscarAlmns(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            alumnos = almn.buscarAlumnos(aguja);
            mdTbl.setRowCount(0);
            if (alumnos != null) {
                alumnos.forEach(a -> {
                    Object[] valores = {a.getIdentificacion(),
                        a.getPrimerApellido() + " "
                        + a.getSegundoApellido() + " "
                        + a.getPrimerNombre() + " "
                        + a.getSegundoNombre()};
                    mdTbl.addRow(valores);
                });
            }
        }
    }

    private void cargarCmbCarreras() {
        carreras = carr.cargarCarreras();
        if (carreras != null) {
            frmAlmCarrera.getCmbCarreras().removeAllItems();
            frmAlmCarrera.getCmbCarreras().addItem("Seleccione");
            carreras.forEach((c) -> {
                frmAlmCarrera.getCmbCarreras().addItem(c.getNombre());
            });
        }
    }

    private void cargarCmbModalidad() {
        frmAlmCarrera.getCmbModalidad().removeAllItems();
        frmAlmCarrera.getCmbModalidad().addItem("Seleccione");
        for (String m : MODALIDADES) {
            frmAlmCarrera.getCmbModalidad().addItem(m);
        }
    }
}
