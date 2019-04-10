/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.sql.SQLException;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;

/**
 *
 * @author Andres Ullauri
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        
        ConexionBD cbd= new ConexionBD();
        
        cbd.conectar();
        
        /*for (CarreraMD cmd: CarrerasBDS.consultar(cbd,9)){
            System.out.println(cmd.getNombre());
        }*/
        
        cbd.desconectar();
        
    }
    
}
