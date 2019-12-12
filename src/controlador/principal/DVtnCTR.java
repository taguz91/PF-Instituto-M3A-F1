package controlador.principal;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.estilo.TblEstilo;
import modelo.validaciones.TxtVBuscador;

/**
 * En esta clase sera la padre de CTR aqui agregaremos dependencias, ya sean
 * ventanas, ctroladores que se necesiten en todos los formularios, o no pero
 * controlando los que no son necesarios
 *
 * @author Johnny
 */
public class DVtnCTR extends DCTR {

    public DefaultTableModel mdTbl;
    public int posFila;

    public DVtnCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
    }

    public void formatoBuscador(JTextField txt, JButton btn) {
        txt.addKeyListener(new TxtVBuscador(txt, btn));
    }

}
