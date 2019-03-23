package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.usuario.RolMD;
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
        
        vtnPrin.getDpnlPrincipal().add(vtnAlmnCurso);  
        vtnAlmnCurso.show(); 
    }
    
    public void iniciar(){
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Alumnos por curso");
    }
    
    public void abrirFrmCurso(){
        
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
