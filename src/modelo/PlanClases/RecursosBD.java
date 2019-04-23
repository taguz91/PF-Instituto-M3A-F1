/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.PlanClases;

import modelo.ConexionBD;

/**
 *
 * @author ANDRES BERMEO
 */
public class RecursosBD extends RecursosMD {
      private ConexionBD conexion;

    public RecursosBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    public RecursosBD(ConexionBD conexion, int id_recurso, String nombre_recursos, char tipo_recurso) {
        super(id_recurso, nombre_recursos, tipo_recurso);
        this.conexion = conexion;
    }
      
    
}
