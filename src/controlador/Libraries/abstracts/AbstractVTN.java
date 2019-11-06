package controlador.Libraries.abstracts;

import controlador.Libraries.Effects;
import controlador.principal.VtnPrincipalCTR;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MrRainx
 * @param <V> Vista
 * @param <M> Modelo
 */
public abstract class AbstractVTN<V extends JInternalFrame, M> {

    private V vista;
    private final VtnPrincipalCTR desktop;
    private JTable table;
    private M modelo;
    private DefaultTableModel tableM;
    private List<M> lista;

    public AbstractVTN(VtnPrincipalCTR desktop) {
        this.desktop = desktop;
    }

    public V getVista() {
        return vista;
    }

    public void setVista(V vista) {
        this.vista = vista;
    }

    public VtnPrincipalCTR getDesktop() {
        return desktop;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
        this.tableM = (DefaultTableModel) this.table.getModel();

    }

    public M getModelo() {
        return modelo;
    }

    public void setModelo(M modelo) {
        this.modelo = modelo;
    }

    public DefaultTableModel getTableM() {
        return tableM;
    }

    public List<M> getLista() {
        return lista;
    }

    public void setLista(List<M> lista) {
        this.lista = lista;
    }

    /**
     * ESTE METODO INICIA Y CENTRA LA VENTANA
     */
    public void Init() {
        Effects.addInDesktopPane(vista, desktop.getVtnPrin().getDpnlPrincipal());
    }

    protected void cargarTabla(Consumer<M> cargador) {
        lista.forEach(cargador);
    }

}
