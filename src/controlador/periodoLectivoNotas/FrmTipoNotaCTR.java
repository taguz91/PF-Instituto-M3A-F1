package controlador.periodoLectivoNotas;

import controlador.Libraries.Middlewares;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.tipoDeNota.TipoDeNotaBD;
import vista.periodoLectivoNotas.FrmTipoNota;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class FrmTipoNotaCTR {

    private VtnPrincipal desktop;
    private FrmTipoNota vista;
    private TipoDeNotaBD modelo;
    //Ventana Padre
    private VtnTipoNotasCTR vtnPadre;
    //(Agregar o Editar)
    private String Funcion;

    private Integer PK = null;

    public FrmTipoNotaCTR(VtnPrincipal desktop, FrmTipoNota vista, TipoDeNotaBD modelo, VtnTipoNotasCTR vtnPadre, String Funcion) {
        this.desktop = desktop;
        this.vista = vista;
        this.modelo = modelo;
        this.vtnPadre = vtnPadre;
        this.Funcion = Funcion;
    }

    //INITS
    public void Init() {

        new Thread(() -> {
            
        }).start();
    }

    //METODOS DE APOYO
    //EVENTOS
}
