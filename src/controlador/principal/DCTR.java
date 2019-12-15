package controlador.principal;

import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Function;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.estilo.TblEstilo;
import utils.CONBD;

/**
 *
 * @author Johnny
 */
public class DCTR extends CONBD {

    protected final VtnPrincipalCTR ctrPrin;
    protected boolean vtnCargada = false;

    public DCTR(VtnPrincipalCTR ctrPrin) {
        this.ctrPrin = ctrPrin;
    }

    protected void abrirJD(JDialog jd) {
        jd.setLocationRelativeTo(ctrPrin.getVtnPrin());
        jd.setVisible(true);
    }

    protected void errorNoSeleccionoFila() {
        JOptionPane.showMessageDialog(
                null,
                "No selecciono ninguna fila."
        );
    }

    protected void cursorCarga(JInternalFrame jif) {
        vtnCargada = false;
        Cursor c = new Cursor(3);
        jif.setCursor(c);
        ctrPrin.getVtnPrin().setCursor(c);
    }

    protected void cursorNormal(JInternalFrame jif) {
        Cursor c = new Cursor(0);
        jif.setCursor(c);
        ctrPrin.getVtnPrin().setCursor(c);
        vtnCargada = true;
    }

    protected DefaultTableModel iniciarTbl(JTable tbl, String[] titulo) {
        DefaultTableModel mdTbl = TblEstilo.modelTblSinEditar(titulo);
        tbl.setModel(mdTbl);
        TblEstilo.formatoTbl(tbl);
        return mdTbl;
    }

    protected void listenerTxtBuscar(
            JTextField txt,
            JButton btn,
            Function<String, Void> buscador
    ) {
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    buscador.apply(
                            txt.getText().trim()
                    );
                }
            }
        });
        if (btn != null) {
            btn.addActionListener(e -> {
                buscador.apply(txt.getText().trim());
            });
        }
    }
    
    protected void listenerTxtBuscarLocal(
            JTextField txt,
            JButton btn,
            Function<String, Void> buscador
    ) {
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscador.apply(
                        txt.getText().trim()
                );
            }
        });
        if (btn != null) {
            btn.addActionListener(e -> {
                buscador.apply(txt.getText().trim());
            });
        }
    }

}
