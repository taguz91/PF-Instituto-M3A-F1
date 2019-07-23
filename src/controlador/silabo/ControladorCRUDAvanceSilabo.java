/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.CursosBDS;
import modelo.usuario.UsuarioBD;
import vista.silabos.frmCRUDAvanceSilabo;


/**
 *
 * @author Daniel
 */
public class ControladorCRUDAvanceSilabo {
   private final UsuarioBD usuario;
    private ConexionBD conexion;
       private boolean esCordinador =false;
        private List<CarreraMD> carreras_docente;
    private frmCRUDAvanceSilabo seguimiento;

    public ControladorCRUDAvanceSilabo(UsuarioBD usuario, ConexionBD conexion, List<CarreraMD> carreras_docente, frmCRUDAvanceSilabo seguimiento) {
        this.usuario = usuario;
        this.conexion = conexion;
        this.carreras_docente = carreras_docente;
        this.seguimiento = seguimiento;
    }
    
    
     
   
    
}
