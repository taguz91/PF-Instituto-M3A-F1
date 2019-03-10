package controlador.persona;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.persona.AlumnoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.persona.PersonaMD;
import vista.persona.FrmAlumno;
import vista.persona.FrmDocente;
import vista.persona.FrmPersona;
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
    private ArrayList<DocenteMD> docentesMD;
    private FrmDocente frmDocente;

    public VtnDocenteCTR(VtnPrincipal vtnPrin, VtnDocente vtnDocente) {
        this.vtnPrin = vtnPrin;
        this.vtnDocente = vtnDocente;
        docente = new DocenteBD();
        vtnPrin.getDpnlPrincipal().add(vtnDocente);
        vtnDocente.show();
    }

    public void iniciar() {
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
        vtnDocente.getBtnEditar().addActionListener(e -> editar());
        vtnDocente.getBtnIngresar().addActionListener(e -> abrirFrmDocente());
        //   vtnDocente.getBtnEditar().addActionListener(e -> editarAlumno());
        docentesMD = docente.cargarDocentes();
        vtnDocente.getTxtBuscar().addKeyListener(kl);
        llenarTabla();

    }

    public void llenarTabla() {
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnDocente.getTblDocente().getModel();
        for (int i = vtnDocente.getTblDocente().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<PersonaMD> lista = docente.llenarTabla();
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            vtnDocente.getTblDocente().setValueAt(String.valueOf(lista.get(i).getIdPersona()), i, 0);
            vtnDocente.getTblDocente().setValueAt(lista.get(i).getIdentificacion(), i, 1);
            vtnDocente.getTblDocente().setValueAt(lista.get(i).getPrimerNombre()
                    + " " + lista.get(i).getSegundoNombre() + lista.get(i).getPrimerApellido()
                    + " " + lista.get(i).getSegundoApellido(), i, 2);

        }

        vtnDocente.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
    }

    public void abrirFrmDocente() {
        DocenteBD docente = new DocenteBD();
        FrmDocente frmDocente = new FrmDocente();
        FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(vtnPrin, frmDocente, docente);
        ctrFrmDocente.iniciar();
    }

    public void buscaIncremental(String aguja) {
        System.out.println(aguja);
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnDocente.getTblDocente().getModel();
        for (int i = vtnDocente.getTblDocente().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<DocenteMD> lista = docente.buscar(aguja);
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            vtnDocente.getTblDocente().setValueAt(String.valueOf(lista.get(i).getIdPersona()), i, 0);
            vtnDocente.getTblDocente().setValueAt(lista.get(i).getIdentificacion(), i, 1);
            vtnDocente.getTblDocente().setValueAt(lista.get(i).getPrimerNombre()
                    + " " + lista.get(i).getSegundoNombre() + lista.get(i).getPrimerApellido()
                    + " " + lista.get(i).getSegundoApellido(), i, 2);

        }
        vtnDocente.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
    }

   /* public DocenteMD capturarFila() {
        int i = vtnDocente.getTblDocente().getSelectedRow();
        System.out.println(i + " metodo capturarFila de vtnDocenteCTR");
        if (i >= 0) {
            DocenteMD docenteModelo;
            docenteModelo = docente.buscarPersona(Integer.valueOf(vtnDocente.getTblDocente().getValueAt(i, 0).toString()));
            System.out.println("cPTURAR FILA METODO VTNdOCENTE" +docenteModelo);
            return docenteModelo;
        } else {
            return null;
        }
    }

     public void editarDocente() {
        DocenteMD al = capturarFila();
        if (al != null) {
            frmDocente = new FrmDocente();
            FrmDocenteCTR ctrFrm = new FrmDocenteCTR(vtnPrin, frmDocente);
            ctrFrm.iniciar();
            ctrFrm.editar(al);
            vtnDocente.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Advertencia!! Seleccione una fila");
        }

    }*/
   public void editar() { 
       FrmDocente frmPersona = new FrmDocente();
       int posFila = vtnDocente.getTblDocente().getSelectedRow();
        System.out.println(posFila + " metodo editar de vtnDocenteCTR");
        if (posFila >= 0) {
            
            FrmDocenteCTR ctrFrm = new FrmDocenteCTR(vtnPrin, frmPersona);
            ctrFrm.iniciar();
            //Le pasamos la persona de nuestro lista justo la persona seleccionada
            ctrFrm.editar(docentesMD.get(posFila));
            //vtnDocente.getTblDocente().setVisible(false);
            vtnDocente.dispose();

        } else {
           
        }
    }

}

// desde personas probando
/*  public void buscar() {
        String busqueda = vtnDocente.getTxtBuscar().getText();
        busqueda = busqueda.trim();
        if (busqueda.length() > 2) {
            docentesMD = docente.buscar(busqueda);
        } else if (busqueda.length() == 0) {
            docentesMD = docente.cargarDocentes();
        }
       llenarTabla();
    }*/
