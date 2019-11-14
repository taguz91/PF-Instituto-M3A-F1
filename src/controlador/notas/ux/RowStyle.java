package controlador.notas.ux;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author MrRainx
 */
public class RowStyle extends DefaultTableCellRenderer {

    private final int columna;
    private Map<String, Color> estados;

    public RowStyle(int columna) {
        this.columna = columna;
    }

    public void setEstados(Map<String, Color> estados) {
        this.estados = estados;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        setBackground(Color.white);
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        try {

            String valor = table.getValueAt(row, this.columna).toString();
            if (estados == null) {
                if (valor.equalsIgnoreCase("APROBADO")) {
                    setForeground(new Color(37, 107, 187));
                } else if (valor.equalsIgnoreCase("REPROBADO")) {
                    setForeground(new Color(214, 48, 12));
                } else {
                    setForeground(new Color(0, 0, 0));
                }
            } else {
                estilizarPorEstados(valor);
            }

            setHorizontalAlignment(CENTER);
            setFont(new Font("Arial", Font.PLAIN, 11));
            table.setSelectionBackground(Color.lightGray);

            setHorizontalAlignment(0);

        } catch (NumberFormatException | NullPointerException e) {

        }

        return this;
    }

    private void estilizarPorEstados(String valor) {
        Color color = estados.entrySet()
                .stream()
                .filter(item -> item.getKey().equals(valor))
                .map(c -> c.getValue())
                .findFirst()
                .orElse(null);

        setForeground(color);

    }

}
