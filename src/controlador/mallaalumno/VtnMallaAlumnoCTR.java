package controlador.mallaalumno;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.carrera.AlumnoCarreraBD;
import modelo.carrera.AlumnoCarreraMD;
import modelo.estilo.TblEstilo;
import modelo.mallaalumno.MallaAlumnoBD;
import modelo.mallaalumno.MallaAlumnoMD;
import vista.mallaalumno.VtnMallaAlumno;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnMallaAlumnoCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnMallaAlumno vtnMallaAlm;
    private final MallaAlumnoBD mallaAlm = new MallaAlumnoBD();    
    
    ArrayList<MallaAlumnoMD> mallas = new ArrayList();
    //Para cargar los combos 
    AlumnoCarreraBD almCar = new AlumnoCarreraBD();
    ArrayList<AlumnoCarreraMD> alumnos = new ArrayList();

    //Modelo de la tabla  
    DefaultTableModel mdlTbl;    
    
    public VtnMallaAlumnoCTR(VtnPrincipal vtn, VtnMallaAlumno vtnMallaAlm) {
        this.vtnPrin = vtn;
        this.vtnMallaAlm = vtnMallaAlm;
        
        vtnPrin.getDpnlPrincipal().add(vtnMallaAlm);
        vtnMallaAlm.show();
    }
    
    public void iniciar() {
        //Iniciamos la tabla  
        String titulo[] = {"id", "Alumno", "Materia", "Ciclo", "Matricula", "Nota 1", "Nota 2", "Nota 3"};
        String datos[][] = {};
        mdlTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        TblEstilo.formatoTbl(vtnMallaAlm.getTblMallaAlumno());
        vtnMallaAlm.getTblMallaAlumno().setModel(mdlTbl);
        TblEstilo.ocualtarID(vtnMallaAlm.getTblMallaAlumno());
        
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 3, 60);
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 4, 60);
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 5, 60);
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 6, 60);
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 7, 60);
        
        cargarCmbAlumno();
        cargarDatos();
        
        vtnMallaAlm.getCmbAlumnos().addActionListener(e -> cargarPorAlumno());
    }
    
    public void cargarDatos() {
        mallas = mallaAlm.cargarMallas();        
        llenarTbl(mallas);
    }
    
    public void cargarPorAlumno() {
        int posAlm = vtnMallaAlm.getCmbAlumnos().getSelectedIndex();        
        if (posAlm > 0) {
            mallas = mallaAlm.cargarMallasPorEstudiante(alumnos.get(posAlm - 1).getAlumno().getId_Alumno());            
            llenarTbl(mallas);
        } else {
            cargarDatos();
        }
    }
    
    public void llenarTbl(ArrayList<MallaAlumnoMD> mallas) {
        mdlTbl.setRowCount(0);
        if (mallas != null) {
            mallas.forEach((m) -> {
                Object valores[] = {m.getId(), m.getAlumno().getPrimerNombre()
                    + " " + m.getAlumno().getPrimerApellido(), m.getMateria().getNombre(),
                    m.getMallaCiclo(), m.getMallaNumMatricula(), m.getNota1(),
                    m.getNota2(), m.getNota3()};
                mdlTbl.addRow(valores);                
            });
        }
    }
    
    private void cargarCmbAlumno() {
        alumnos = almCar.cargarAlumnoCarrera();        
        if (alumnos != null) {
            vtnMallaAlm.getCmbAlumnos().removeAllItems();
            vtnMallaAlm.getCmbAlumnos().addItem("Todos");            
            alumnos.forEach((a) -> {
                vtnMallaAlm.getCmbAlumnos().addItem(a.getAlumno().getPrimerNombre()
                        + " " + a.getAlumno().getPrimerApellido());                
            });
        }
    }
    
}
