package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.alumno.AlumnoCursoBD;
import modelo.alumno.AlumnoCursoMD;
import modelo.estilo.TblEstilo;
import modelo.usuario.RolMD;
import modelo.validaciones.Validar;
import vista.alumno.FrmAlumnoCarrera;
import vista.alumno.VtnAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnAlumnoCursoCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnAlumnoCurso vtnAlmnCurso;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private final RolMD permisos;
    
    //Tabla  
    private DefaultTableModel mdTbl;
    //Datos
    private ArrayList<AlumnoCursoMD> almns;
    private final AlumnoCursoBD alc; 

    public VtnAlumnoCursoCTR(VtnPrincipal vtnPrin, VtnAlumnoCurso vtnAlmnCurso, 
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin; 
        this.vtnAlmnCurso = vtnAlmnCurso;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;
        
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Alumnos por curso");
        ctrPrin.setIconJIFrame(vtnAlmnCurso);
        
        this.alc = new AlumnoCursoBD(conecta);
        
        vtnPrin.getDpnlPrincipal().add(vtnAlmnCurso);  
        vtnAlmnCurso.show(); 
    }
    
    public void iniciar(){
        String titulo[] = {"Cedula", "Alumno", "Curso"};
        String datos[][] = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        TblEstilo.formatoTbl(vtnAlmnCurso.getTblAlumnoCurso());
        vtnAlmnCurso.getTblAlumnoCurso().setModel(mdTbl);
        //Llenamos la tabla 
        cargarAlumnosCurso();
        //Buscador 
        vtnAlmnCurso.getTxtbuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnAlmnCurso.getTxtbuscar().getText().trim();
                if (b.length() > 2) {
                    buscar(b);
                }else if(b.length() == 0){
                    cargarAlumnosCurso();
                }
            }
        });
        vtnAlmnCurso.getBtnbuscar().addActionListener(e -> buscar(vtnAlmnCurso.getTxtbuscar().getText().trim()));
        //Acciones de los botones
        vtnAlmnCurso.getBtnIngresar().addActionListener(e -> abrirFrmCurso());
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Alumnos por curso");
    }
    
        private void buscar(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            almns = alc.buscarAlumnosCursosTbl(b);
            llenatTbl(almns);
        } else {
            System.out.println("No ingrese caracteres especiales");
        }
    }
    
    public void abrirFrmCurso(){
        FrmAlumnoCarrera frmAlmCarrera = new FrmAlumnoCarrera();
        FrmAlumnoCarreraCTR ctr = new FrmAlumnoCarreraCTR(vtnPrin, frmAlmCarrera, conecta, ctrPrin);
        ctr.iniciar();
    }
    
    private void cargarAlumnosCurso(){
        almns = alc.cargarAlumnosCursosTbl();
        llenatTbl(almns);
    }
    
    private void llenatTbl(ArrayList<AlumnoCursoMD> almns){
        mdTbl.setRowCount(0);
        if (almns != null) {
            almns.forEach(a -> {
                Object [] valores = {a.getAlumno().getIdentificacion(),
                    a.getAlumno().getPrimerNombre() 
                        + " " + a.getAlumno().getPrimerApellido(), 
                a.getCurso().getCurso_nombre()};
                mdTbl.addRow(valores);
            });
            vtnAlmnCurso.getLblResultados().setText(almns.size() + " Resultados obtenidos.");
        }
    }
      
    private void InitPermisos() {

        for (AccesosMD obj : AccesosBD.SelectWhereACCESOROLidRol(permisos.getId())) {

//            if (obj.getNombre().equals("USUARIOS-Agregar")) {
//                vtnCarrera.getBtnIngresar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-Editar")) {
//                vista.getBtnEditar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-Eliminar")) {
//                vista.getBtnEliminar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-AsignarRoles")) {
//                vista.getBtnAsignarRoles().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-VerRoles")) {
//                vista.getBtnVerRoles().setEnabled(true);
//            }
        }
    }
}
