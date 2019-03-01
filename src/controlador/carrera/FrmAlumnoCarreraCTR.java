package controlador.carrera;

import java.util.ArrayList;
import modelo.carrera.AlumnoCarreraBD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
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
        //Ocultamos el error  
        frmAlmCarrera.getLblError().setVisible(false);

        cargarCmbAlumnos();
        cargarCmbCarreras();

        frmAlmCarrera.getBtnGuardar().addActionListener(e -> guardar());
    }

    private void guardar() {
        boolean guardar = true;
        int posAlm = frmAlmCarrera.getCmbAlumnos().getSelectedIndex();
        int posCar = frmAlmCarrera.getCmbCarreras().getSelectedIndex();

        if (posAlm < 1 || posCar < 1) {
            guardar = false;
            frmAlmCarrera.getLblError().setVisible(true);
        } else {
            frmAlmCarrera.getLblError().setVisible(false);
        }

        if (guardar) {
            almnCarrera.setAlumno(alumnos.get(posAlm - 1));
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

    public void cargarCmbAlumnos() {
        alumnos = almn.cargarAlumnos();
        System.out.println("Num de alumnos " + alumnos.size());
        if (alumnos != null) {
            frmAlmCarrera.getCmbAlumnos().removeAllItems();
            frmAlmCarrera.getCmbAlumnos().addItem("Seleccione");
            alumnos.forEach((a) -> {
                frmAlmCarrera.getCmbAlumnos().addItem(a.getPrimerNombre() + " " + a.getPrimerApellido());
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
