package controlador.periodoLectivoNotas;

import controlador.Libraries.Middlewares;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.List;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
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
    //listas
    private List<CarreraMD> listaCarreras;
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
            listaCarreras = CarreraBD.selectIdNombreAll();
            cargarComboCarreras();
            InitEventos();
        }).start();
        try {
            Middlewares.centerFrame(vista, desktop.getDpnlPrincipal());
            desktop.getDpnlPrincipal().add(vista);
            vista.setSelected(true);
            vista.show();
        } catch (PropertyVetoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void InitEventos() {
        vista.getBtnCancelar().addActionListener(e -> btnCancelar(e));
        vista.getTxtNotaMin().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtNotaMinOnKeyReleased(e);
            }
        });
    }

    //METODOS DE APOYO
    private void cargarComboCarreras() {

        listaCarreras.stream().forEach(obj -> {
            vista.getCmbCarrera().addItem(obj.getNombre());
        });
    }

    //EVENTOS
    private void btnCancelar(ActionEvent e) {
        vista.dispose();
    }

    private void txtNotaMinOnKeyReleased(KeyEvent e) {
        
    }
}
