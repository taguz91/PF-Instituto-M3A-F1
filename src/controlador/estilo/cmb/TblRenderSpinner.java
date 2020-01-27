package controlador.estilo.cmb;

import java.awt.Component;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.validaciones.Validar;

/**
 *
 * @author gus
 */
public class TblRenderSpinner extends DefaultTableCellRenderer {

    private final JSpinner spn;

    public TblRenderSpinner(JSpinner spn) {
        this.spn = spn;
        iniciarEventos();
    }

    private void iniciarEventos() {
        spn.setVisible(true);
        spn.setOpaque(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
            boolean focused, int row, int column) {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);

        if (value != null) {
            if (Validar.esNumeros(value.toString())) {
                spn.setValue(Integer.parseInt(value.toString()));
                spn.getValue();
            }
        }
        return spn;
    }

}
