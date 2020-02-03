package controlador.estilo;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gus
 */
public class TblEditarUnicaColumna extends DefaultTableModel {

    private final int colEditar;
    private final String[] titulos;
    private final Object[][] datos;

    public TblEditarUnicaColumna(String[] titulos, String[][] datos, int colEditar) {
        super();
        this.titulos = titulos;
        this.datos = datos;
        this.colEditar = colEditar;
        setDataVector(datos, titulos);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == colEditar;
    }
}
