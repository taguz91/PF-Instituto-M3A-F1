package controlador.prdlectivo;

import controlador.persona.FrmAlumnoCTR;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoBD;
import modelo.persona.PersonaMD;
import vista.prdlectivo.FrmPrdLectivo;
import vista.prdlectivo.VtnPrdLectivo;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnPrdLectivoCTR {
    
    private final VtnPrincipal vtnPrin;
    private final VtnPrdLectivo vtnPrdLectivo; 
    private PeriodoLectivoBD bdPerLectivo;

    public VtnPrdLectivoCTR(VtnPrincipal vtnPrin, VtnPrdLectivo vtnPrdLectivo) {
        this.vtnPrin = vtnPrin;
        this.vtnPrdLectivo = vtnPrdLectivo;
        
        bdPerLectivo = new PeriodoLectivoBD();
        
        vtnPrin.getDpnlPrincipal().add(vtnPrdLectivo);   
        vtnPrdLectivo.show(); 
    }
    
    public void iniciar(){
        
        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                buscaIncremental(vtnPrdLectivo.getTxt_Buscar().getText());
            }
        };
        
        llenarTabla();
        vtnPrdLectivo.getTxt_Buscar().addKeyListener(kl);
        vtnPrdLectivo.getBtnIngresar().addActionListener(e -> abrirFrmPrdLectivo());
    }
    
    public void abrirFrmPrdLectivo(){
        FrmPrdLectivo vista = new FrmPrdLectivo();
        FrmPrdLectivoCTR formulario = new FrmPrdLectivoCTR(vtnPrin, vista);
        formulario.iniciar();
    }
    
    public void llenarTabla(){
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnPrdLectivo.getTblPrdLectivo().getModel();
        for (int i = vtnPrdLectivo.getTblPrdLectivo().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<PeriodoLectivoMD> lista = bdPerLectivo.llenarTabla();
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getId_PerioLectivo(), i, 0);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(bdPerLectivo.capturarNomCarrera(lista.get(i).getId()), i, 1);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getNombre_PerLectivo(), i, 2);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getFecha_Inicio().toString(), i, 3);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getFecha_Fin().toString(), i, 4);
        }
        vtnPrdLectivo.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
    }
    
    public void buscaIncremental(String aguja) {
        System.out.println(aguja);
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnPrdLectivo.getTblPrdLectivo().getModel();
        for (int i = vtnPrdLectivo.getTblPrdLectivo().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<PeriodoLectivoMD> lista = new ArrayList<PeriodoLectivoMD>();
        lista.add(bdPerLectivo.capturarPeriodos(aguja));
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getId_PerioLectivo(), i, 0);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(bdPerLectivo.capturarNomCarrera(lista.get(i).getId()), i, 1);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getNombre_PerLectivo(), i, 2);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getFecha_Inicio().toString(), i, 3);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getFecha_Fin().toString(), i, 4);
        }
        if(lista.isEmpty()){
            vtnPrdLectivo.getLblResultados().setText("0 Resultados obtenidos.");
        } else{
            vtnPrdLectivo.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
        }
    }
    
    public PeriodoLectivoBD capturarFila() {
        int i = vtnPrdLectivo.getTblPrdLectivo().getSelectedRow();
        if (i >= 0) {
            PeriodoLectivoBD periodo = new PeriodoLectivoBD();
            periodo =  (PeriodoLectivoBD) bdPerLectivo.capturarPerLectivo(Integer.valueOf(vtnPrdLectivo.getTblPrdLectivo().getValueAt(i, 0).toString()));
            return periodo;
        } else {
            JOptionPane.showMessageDialog(null, "Advertencia!! | Seleccione una fila");
            return null;
        }
    }
    
    public void editarPeriodo() {
        FrmPrdLectivo vista = new FrmPrdLectivo();
        FrmPrdLectivoCTR form = new FrmPrdLectivoCTR(vtnPrin, vista);
        PeriodoLectivoBD periodo = new PeriodoLectivoBD();
        if (capturarFila() == null) {
            JOptionPane.showMessageDialog(null, "No se puede Editar si no selecciona a un Alumno");
        } else {
            periodo = capturarFila();
            form.editar(periodo);
            vtnPrdLectivo.setVisible(false);
            vista.setVisible(true);
        }
    }
    
}
