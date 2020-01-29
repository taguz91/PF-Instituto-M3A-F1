package controlador.Libraries.abstracts;

import controlador.Libraries.Effects;
import controlador.principal.VtnPrincipalCTR;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import utils.CONS;
import modelo.usuario.UsuarioMD;

/**
 *
 * @author MrRainx
 * @param <V> Vista
 * @param <M> Modelo
 */
public abstract class AbstractVTN<V extends JInternalFrame, M> {

    protected V vista;
    protected final VtnPrincipalCTR desktop;
    protected JTable table;
    protected M modelo;
    protected DefaultTableModel tableM;
    protected List<M> lista;
    protected boolean showTableMessage = true;

    protected final UsuarioMD user = CONS.USUARIO;

    public AbstractVTN(VtnPrincipalCTR desktop) {
        this.desktop = desktop;
    }

    public void setTable(JTable table) {
        this.table = table;
        this.tableM = (DefaultTableModel) this.table.getModel();

    }

    public V getVista() {
        return vista;
    }

    public void setVista(V vista) {
        this.vista = vista;
    }

    public M getModelo() {
        return modelo;
    }

    public void setModelo(M modelo) {
        this.modelo = modelo;
    }

    public List<M> getLista() {
        return lista;
    }

    public void setLista(List<M> lista) {
        this.lista = lista;
    }

    public DefaultTableModel getTableM() {
        return tableM;
    }

    public Integer getSelectedRow() {
        int row = table.getSelectedRow();
        if (row == -1) {
            if (showTableMessage) {
                JOptionPane.showMessageDialog(vista, "DEBE SELECCIONA UN REGISTRO DE LA TABLA PRIMERO", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
            return row;
        }
        return row;
    }

    /**
     * ESTE METODO INICIA Y CENTRA LA VENTANA
     */
    public void Init() {
        Effects.addInDesktopPane(vista, desktop.getVtnPrin().getDpnlPrincipal());
    }

    protected void cargarTabla(Consumer<M> cargador) {
        tableM.setRowCount(0);
        if (lista != null) {
            lista.forEach(cargador);
        }
    }

}
