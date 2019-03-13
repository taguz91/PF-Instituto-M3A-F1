package controlador.alumno;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.alumno.AlumnoCarreraBD;
import modelo.alumno.AlumnoCarreraMD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.alumno.MallaAlumnoBD;
import modelo.alumno.MallaAlumnoMD;
import vista.alumno.VtnMallaAlumno;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnMallaAlumnoCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnMallaAlumno vtnMallaAlm;
    private final ConectarDB conecta;
    private final MallaAlumnoBD mallaAlm;
    private final String [] cmbEstado = {"Seleccione", "Aprobado", "Cursando", "Pendiente", "Reprobado"};
    
    private ArrayList<MallaAlumnoMD> mallas = new ArrayList();
    //Para cargar los combos 
    private final AlumnoCarreraBD almCar;
    private ArrayList<AlumnoCarreraMD> alumnos;
    
    private final CarreraBD car;
    private ArrayList<CarreraMD> carreras = new ArrayList(); 
    //Modelo de la tabla  
    private DefaultTableModel mdlTbl;    
    
    public VtnMallaAlumnoCTR(VtnPrincipal vtn, VtnMallaAlumno vtnMallaAlm, ConectarDB conecta) {
        this.vtnPrin = vtn;
        this.vtnMallaAlm = vtnMallaAlm;
        this.conecta = conecta;
        this.almCar = new AlumnoCarreraBD(conecta);
        this.mallaAlm = new MallaAlumnoBD(conecta);
        this.car = new CarreraBD(conecta); 
        vtnPrin.getDpnlPrincipal().add(vtnMallaAlm);
        vtnMallaAlm.show();
    }
    
    public void iniciar() {
        //Iniciamos la tabla  
        String titulo[] = {"id", "Alumno", "Materia", "Estado", "Ciclo", "Matricula", "Nota 1", "Nota 2", "Nota 3"};
        String datos[][] = {};
        mdlTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        TblEstilo.formatoTbl(vtnMallaAlm.getTblMallaAlumno());
        vtnMallaAlm.getTblMallaAlumno().setModel(mdlTbl);
        TblEstilo.ocualtarID(vtnMallaAlm.getTblMallaAlumno());
        
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 4, 60);
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 5, 60);
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 6, 60);
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 7, 60);
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 8, 60);
        
        cargarCmbCarrera();
        
        vtnMallaAlm.getCmbCarreras().addActionListener(e -> clickCmbCarrera());
        vtnMallaAlm.getCmbAlumnos().addActionListener(e -> cargarPorAlumno());
        vtnMallaAlm.getCmbEstado().addActionListener(e -> cargarPorEstado());
    }
    
    public void cargarPorAlumno() {
        int posAlm = vtnMallaAlm.getCmbAlumnos().getSelectedIndex(); 
        if (posAlm > 0) {
            mallas = mallaAlm.cargarMallasPorEstudiante(alumnos.get(posAlm - 1).getId());            
            llenarTbl(mallas);
        } else {
            //Borramos todos los datos de la tabla si no se selecciona ninguno 
            mdlTbl.setRowCount(0); 
        }
    }
    
    public void cargarPorEstado(){ 
        int posAlm = vtnMallaAlm.getCmbAlumnos().getSelectedIndex();
        int posEst = vtnMallaAlm.getCmbEstado().getSelectedIndex(); 
        if (posAlm > 0 && posEst > 0) {
            mallas = mallaAlm.cargarMallaAlumnoPorEstado(
                    alumnos.get(posAlm - 1).getAlumno().getId_Alumno(), cmbEstado[posEst]);
            llenarTbl(mallas);
        }else if(posAlm > 0){
            cargarPorAlumno();
        }
    }
    
    public void llenarTbl(ArrayList<MallaAlumnoMD> mallas) {
        mdlTbl.setRowCount(0);
        if (mallas != null) {
            mallas.forEach((m) -> {
                Object valores[] = {m.getId(), m.getAlumnoCarrera().getAlumno().getPrimerNombre()
                    + " " + m.getAlumnoCarrera().getAlumno().getPrimerApellido(), m.getMateria().getNombre(),
                    m.getEstado(),
                    m.getMallaCiclo(), m.getMallaNumMatricula(), m.getNota1(),
                    m.getNota2(), m.getNota3()};
                mdlTbl.addRow(valores);                
            });
        }
    }
    
    private void cargarCmbCarrera(){
        carreras = car.cargarCarreras(); 
        vtnMallaAlm.getCmbCarreras().removeAllItems();
        if (carreras != null) {
            vtnMallaAlm.getCmbCarreras().addItem("Seleccione"); 
            carreras.forEach(c -> {
                vtnMallaAlm.getCmbCarreras().addItem(c.getCodigo()); 
            });
        }
    }
    
    private void clickCmbCarrera(){
        int posCar = vtnMallaAlm.getCmbCarreras().getSelectedIndex(); 
        if (posCar > 0) {
            cargarCmbAlumno(carreras.get(posCar - 1).getId());
            cargarCmbEstado();
        }
    }
    
    private void cargarCmbAlumno(int idCarrera) {
        alumnos = almCar.cargarAlumnoCarreraPorCarrera(idCarrera);        
        vtnMallaAlm.getCmbAlumnos().removeAllItems();
        if (alumnos != null) {
            vtnMallaAlm.getCmbAlumnos().addItem("Seleccione");            
            alumnos.forEach((a) -> {
                vtnMallaAlm.getCmbAlumnos().addItem(a.getAlumno().getPrimerNombre()
                        + " " + a.getAlumno().getPrimerApellido());                
            });
        }
    }
    
    private void cargarCmbEstado(){
        vtnMallaAlm.getCmbEstado().removeAllItems();
        for(String e: cmbEstado){
            vtnMallaAlm.getCmbEstado().addItem(e); 
        }
    }
}
