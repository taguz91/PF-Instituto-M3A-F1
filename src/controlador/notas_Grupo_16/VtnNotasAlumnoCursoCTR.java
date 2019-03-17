package controlador.notas_Grupo_16;

import modelo.ConectarDB;
import modelo.alumno.AlumnoCursoBD;
import modelo.usuario.UsuarioBD;
import vista.notas_Grupo_16.VtnNotasAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Alejandro
 */
public class VtnNotasAlumnoCursoCTR {

    private VtnPrincipal desktop;
    private VtnNotasAlumnoCurso vista;
    private AlumnoCursoBD modelo;
    private UsuarioBD usuario;
    //Conexion
    private ConectarDB conexion;

    public VtnNotasAlumnoCursoCTR(VtnPrincipal desktop, VtnNotasAlumnoCurso vista, AlumnoCursoBD modelo, UsuarioBD usuario, ConectarDB conexion) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.usuario = usuario;
        this.conexion = conexion;
    }

    public void Init() {

    }

    private void cargarCmbPrdLectio() {

    }
}
