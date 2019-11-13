/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import controlador.Libraries.Effects;
import controlador.principal.VtnPrincipalCTR;
import javax.swing.event.CaretEvent;
import modelo.silabo.SilaboMD;
import vista.silabos.NEWFrmSilabo;

/**
 *
 * @author Johnny, MrRainx
 */
public class NEWfrmSilaboCTR {

    private final NEWFrmSilabo vista = new NEWFrmSilabo();
    private SilaboMD modelo;
    private final VtnPrincipalCTR vtnPrin;

    public NEWfrmSilaboCTR(SilaboMD modelo, VtnPrincipalCTR vtnPrin) {
        this.modelo = modelo;
        this.vtnPrin = vtnPrin;
    }

    public void Init() {
        Effects.addInDesktopPane(vista, vtnPrin.getVtnPrin().getDpnlPrincipal());

        System.out.println("-------------------");
        System.out.println(modelo.getPeriodo().getNombre());
        System.out.println(modelo.getMateria());
        System.out.println("-------------------");
        InitEventos();
    }

    private void InitEventos() {
        vista.getTxtTitulo().addCaretListener(this::guardar);
    }

    /*
        EVENTOS
     */
    private void guardar(CaretEvent e) {
    }

}
