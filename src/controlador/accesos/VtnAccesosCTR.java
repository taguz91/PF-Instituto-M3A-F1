package controlador.accesos;

import controlador.Libraries.Effects;
import controlador.principal.VtnPrincipalCTR;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import modelo.accesos.AccesosBD;
import vista.accesos.VtnAccesos;

public class VtnAccesosCTR {

    private final VtnPrincipalCTR desktop;
    private final VtnAccesos vista;
    private final AccesosBD modelo;

    private DefaultTableModel tabla;
    private List<AccesosBD> listaAccesos;

    public VtnAccesosCTR(VtnPrincipalCTR desktop) {
        this.desktop = desktop;
        this.vista = new VtnAccesos();
        this.modelo = new AccesosBD();
    }

    public void setListaAccesos(List<AccesosBD> listaAccesos) {
        this.listaAccesos = listaAccesos;
    }

    //Inits
    public void Init() {
        Effects.addInDesktopPane(vista, desktop.getVtnPrin().getDpnlPrincipal());

        tabla = (DefaultTableModel) vista.getTblAccesosDeRol().getModel();

        listaAccesos = modelo.SelectAll();
        cargarTabla();

    }

    //Meotodos de Apoyo
    protected void cargarTabla() {

        listaAccesos.stream().forEach(obj -> {
            tabla.addRow(new Object[]{
                tabla.getDataVector().size() + 1,
                obj.getIdAccesos(),
                obj.getNombre()
            });
        });
        vista.getLblResultados().setText((tabla.getDataVector().size() + 1) + "Resultados");

    }
}
