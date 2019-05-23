/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.referencias;

import modelo.ConectarDB;
import modelo.ReferenciasB.ReferenciaBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmEditarBiblioteca;

/**
 *
 * @author Usuario
 */
public class ControladorEditar {
    private frmEditarBiblioteca vista;
    private ReferenciaBD modelo ;
    private  VtnPrincipal vtnPrin;
 private ConectarDB conecta;
    public ControladorEditar(frmEditarBiblioteca vista, ReferenciaBD modelo) {
        this.vista = vista;
        this.modelo = modelo;
        vista.setVisible(true);
    }
    
    
}
