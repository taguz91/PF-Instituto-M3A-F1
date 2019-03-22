package controlador.persona;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.estilo.TblEstilo;
import modelo.persona.AlumnoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import vista.persona.FrmDocente;
import vista.persona.VtnDocente;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnDocenteCTR {

    //array de docente modelo
    //vector de titulos de la tabla en el iniciar
    // vtnMateriaCTR basarme en ese---->para el crud
    private final VtnPrincipal vtnPrin;
    private final VtnDocente vtnDocente;
    private final DocenteBD docente;
    private final ConectarDB conecta;

    private ArrayList<DocenteMD> docentesMD;
    private FrmDocente frmDocente;

    private DefaultTableModel mdTbl;

    public VtnDocenteCTR(VtnPrincipal vtnPrin, VtnDocente vtnDocente, ConectarDB conecta) {
        this.vtnPrin = vtnPrin;
        this.vtnDocente = vtnDocente;
        this.conecta = conecta;
        docente = new DocenteBD(conecta);
        vtnPrin.getDpnlPrincipal().add(vtnDocente);
        vtnDocente.show();
    }

    public void iniciar() {
        String[] titulo = {"Nombres Completos", "Celular", "Correo", "Tipo Contrato"};
        String[][] datos = {};

        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        vtnDocente.getTblDocente().setModel(mdTbl);
        TblEstilo.formatoTbl(vtnDocente.getTblDocente());
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 0, 250);
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 1, 85);
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 2, 200);
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 3, 140);

        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                buscaIncremental(vtnDocente.getTxtBuscar().getText().toUpperCase());
                /// buscar();
            }
        };
        cargarDocentes();
        vtnDocente.getBtnEditar().addActionListener(e -> editar());
        vtnDocente.getBtnIngresar().addActionListener(e -> abrirFrmDocente());
        vtnDocente.getBtnEliminar().addActionListener(e -> eliminarDocente());
        vtnDocente.getTxtBuscar().addKeyListener(kl);
    }

    private void cargarDocentes() {
        docentesMD = docente.cargarDocentes();
        llenarTabla(docentesMD);
    }

    public void llenarTabla(ArrayList<DocenteMD> docentesMD) {
        mdTbl.setRowCount(0);
        if (docentesMD != null) {
            docentesMD.forEach(d -> {
                Object[] valores = {d.getPrimerApellido() + " " + d.getSegundoApellido() + " " + d.getPrimerNombre() + " " + d.getSegundoNombre(),
                     d.getCelular(), d.getCorreo(), d.getDocenteTipoTiempo()};
                mdTbl.addRow(valores);
            });
            vtnDocente.getLblResultados().setText(String.valueOf(docentesMD.size()) + " Resultados obtenidos.");
        } else {
            vtnDocente.getLblResultados().setText("0 Resultados obtenidos.");
        }

    }

    public void abrirFrmDocente() {
        DocenteBD docente = new DocenteBD(conecta);
        FrmDocente frmDocente = new FrmDocente();
        FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(vtnPrin, frmDocente, docente, conecta);
        ctrFrmDocente.iniciar();
        vtnDocente.dispose();
    }

    public void buscaIncremental(String aguja) {
        docentesMD = docente.buscar(aguja);
        llenarTabla(docentesMD);
    }

    public void editar() {
        int posFila = vtnDocente.getTblDocente().getSelectedRow();
        System.out.println(posFila + " metodo editar de vtnDocenteCTR");
        if (posFila >= 0) {
            FrmDocente frmDoc = new FrmDocente();
            FrmDocenteCTR ctrFrm = new FrmDocenteCTR(vtnPrin, frmDoc, conecta);
            ctrFrm.iniciar();
            frmDoc.getBtnRegistrarPersona().setVisible(false);
            //Le pasamos la persona de nuestro lista justo la persona seleccionada
            ctrFrm.habilitarComponentesDocente();

            ctrFrm.editar(docentesMD.get(posFila));
            //vtnDocente.getTblDocente().setVisible(false);
            vtnDocente.dispose();

        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA !");
        }
    }

    public void eliminarDocente() {
        DocenteMD docentemd = new DocenteMD();
        int posFila = vtnDocente.getTblDocente().getSelectedRow();
        System.out.println(posFila + " metodo editar de vtnDocenteCTR");
        if (posFila >= 0) {
            int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar un Docente? ", " Eliminar Docente ", dialog);
            if (result == 0) {

                String observacion = JOptionPane.showInputDialog("¿Por que motivo elimina este Docente?");
                if (observacion != null) {
                    docentemd.setEstado(observacion.toUpperCase());
                    if (docente.eliminarDocente(docentemd, docentesMD.get(0).getIdPersona()) == true) {
                        JOptionPane.showMessageDialog(null, "Datos Eliminados Satisfactoriamente");
                        llenarTabla(docentesMD);

                    } else {
                        JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR AL DOCENTE !");
                    }
                }
            }

        } else {

        }
    }
}
