package controlador.carrera;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.carrera.AlumnoCarreraBD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.mallaalumno.MallaAlumnoBD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;
import vista.carrera.FrmAlumnoCarrera;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmAlumnoCarreraCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmAlumnoCarrera frmAlmCarrera;
    private final AlumnoCarreraBD almnCarrera;

    //Modelo de la tabla  
    DefaultTableModel mdTbl;

    //Para rellenar los combo box
    AlumnoBD almn = new AlumnoBD();
    ArrayList<AlumnoMD> alumnos;

    CarreraBD carr = new CarreraBD();
    ArrayList<CarreraMD> carreras;

    MateriaBD mat = new MateriaBD();
    ArrayList<MateriaMD> materias;
    //Para guardar la malla
    MallaAlumnoBD malla = new MallaAlumnoBD();

    public FrmAlumnoCarreraCTR(VtnPrincipal vtnPrin, FrmAlumnoCarrera frmAlmCarrera) {
        this.vtnPrin = vtnPrin;
        this.frmAlmCarrera = frmAlmCarrera;
        this.almnCarrera = new AlumnoCarreraBD();

        vtnPrin.getDpnlPrincipal().add(frmAlmCarrera);
        frmAlmCarrera.show();
    }

    public void iniciar() {
        String[] titulo = {"Nombre"};
        String[][] datos = {};

        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        frmAlmCarrera.getTblAlumnos().setModel(mdTbl);
        TblEstilo.formatoTbl(frmAlmCarrera.getTblAlumnos());

        //Ocultamos el error  
        frmAlmCarrera.getLblError().setVisible(false);

        cargarCmbCarreras();

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
        alumnos = almn.buscarAlumnos(aguja);
        mdTbl.setRowCount(0);
        if (alumnos != null) {
            alumnos.forEach(a -> {
                Object[] valores = {a.getPrimerApellido() + " "
                    + a.getSegundoApellido() + " "
                    + a.getPrimerNombre() + " "
                    + a.getSegundoNombre()};
                mdTbl.addRow(valores);
            });
        }
    }

    public void cargarCmbCarreras() {
        carreras = carr.cargarCarreras();
        if (carreras != null) {
            frmAlmCarrera.getCmbCarreras().removeAllItems();
            frmAlmCarrera.getCmbCarreras().addItem("Seleccione");
            carreras.forEach((c) -> {
                frmAlmCarrera.getCmbCarreras().addItem(c.getNombre());
            });
        }
    }

}
