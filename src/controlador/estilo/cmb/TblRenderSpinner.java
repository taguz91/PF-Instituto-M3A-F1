package controlador.estilo.cmb;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
        spn.addChangeListener((ChangeEvent e) -> {
            System.out.println("Cambiamos de listener.");
        });
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
            boolean focused, int row, int column) {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);

        if (value != null) {
            if (Validar.esNumeros(value.toString())) {
                spn.setValue(Integer.parseInt(value.toString()));
            }
            System.out.println("Valor de la tabla: " + value.toString());
        }

        if (focused) {
            spn.setOpaque(true);
        }

        return spn;
    }

}
