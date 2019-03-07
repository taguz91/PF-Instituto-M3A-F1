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
import modelo.carrera.CarreraMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoBD;
import modelo.persona.PersonaMD;
import vista.persona.FrmAlumno;
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
    private FrmPrdLectivo frmPerLectivo;

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
                buscaIncremental(vtnPrdLectivo.getTxt_Buscar().getText().toUpperCase());
            }
        };
        
        ocultarAtributo();
        llenarTabla();
        vtnPrdLectivo.getTxt_Buscar().addKeyListener(kl);
        vtnPrdLectivo.getBtnEditar().addActionListener(e -> editarPeriodo());
        vtnPrdLectivo.getBtnEliminar().addActionListener(e -> eliminarPeriodo());
        vtnPrdLectivo.getBtnIngresar().addActionListener(e -> abrirFrmPrdLectivo());
    }
    
    public void abrirFrmPrdLectivo(){
        FrmPrdLectivo vista = new FrmPrdLectivo();
        FrmPrdLectivoCTR formulario = new FrmPrdLectivoCTR(vtnPrin, vista);
        formulario.iniciar();
    }
    
    public void ocultarAtributo() {
        modelo.estilo.TblEstilo.ocualtarID(vtnPrdLectivo.getTblPrdLectivo());
    }
    
    public void llenarTabla(){
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnPrdLectivo.getTblPrdLectivo().getModel();
        for (int i = vtnPrdLectivo.getTblPrdLectivo().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<PeriodoLectivoMD> lista = bdPerLectivo.cargarPeriodos();
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            String dia_Inicio, mes_Inicio, anio_Inicio;
            String dia_Fin, mes_Fin, anio_Fin;
            dia_Inicio = String.valueOf(lista.get(i).getFecha_Inicio().getDayOfMonth());
            mes_Inicio = String.valueOf(lista.get(i).getFecha_Inicio().getMonthValue());
            anio_Inicio = String.valueOf(lista.get(i).getFecha_Inicio().getYear());
            dia_Fin = String.valueOf(lista.get(i).getFecha_Fin().getDayOfMonth());
            mes_Fin = String.valueOf(lista.get(i).getFecha_Fin().getMonthValue());
            anio_Fin = String.valueOf(lista.get(i).getFecha_Fin().getYear());
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getId_PerioLectivo(), i, 0);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getCarrera().getNombre(), i, 1);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getNombre_PerLectivo(), i, 2);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Inicio + "/" + mes_Inicio + "/" + dia_Inicio, i, 3);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Fin + "/" + mes_Fin + "/" + dia_Fin, i, 4);
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
        List<PeriodoLectivoMD> lista = bdPerLectivo.capturarPeriodos(aguja);
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            String dia_Inicio, mes_Inicio, anio_Inicio;
            String dia_Fin, mes_Fin, anio_Fin;
            dia_Inicio = String.valueOf(lista.get(i).getFecha_Inicio().getDayOfMonth());
            mes_Inicio = String.valueOf(lista.get(i).getFecha_Inicio().getMonthValue());
            anio_Inicio = String.valueOf(lista.get(i).getFecha_Inicio().getYear());
            dia_Fin = String.valueOf(lista.get(i).getFecha_Fin().getDayOfMonth());
            mes_Fin = String.valueOf(lista.get(i).getFecha_Fin().getMonthValue());
            anio_Fin = String.valueOf(lista.get(i).getFecha_Fin().getYear());
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getId_PerioLectivo(), i, 0);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(bdPerLectivo.capturarNomCarrera(lista.get(i).getCarrera().getId()).getNombre(), i, 1);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getNombre_PerLectivo(), i, 2);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Inicio + "/" + mes_Inicio + "/" + dia_Inicio, i, 3);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Fin + "/" + mes_Fin + "/" + dia_Fin, i, 4);
        }
        if(lista.isEmpty()){
            vtnPrdLectivo.getLblResultados().setText("0 Resultados obtenidos.");
        } else{
            vtnPrdLectivo.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
        }
    }
    
    public PeriodoLectivoMD capturarFila() {
        int i = vtnPrdLectivo.getTblPrdLectivo().getSelectedRow();
        if (i >= 0) {
            PeriodoLectivoMD periodo = new PeriodoLectivoMD();
            periodo = bdPerLectivo.capturarPerLectivo(Integer.valueOf(vtnPrdLectivo.getTblPrdLectivo().getValueAt(i, 0).toString()));
            return periodo;
        } else {
            return null;
        }
    }
    
    public void editarPeriodo() {
        PeriodoLectivoMD periodo = capturarFila();
        CarreraMD carrera = new CarreraMD();
        if (periodo != null) {
            frmPerLectivo = new FrmPrdLectivo();
            FrmPrdLectivoCTR ctrFrm = new FrmPrdLectivoCTR(vtnPrin, frmPerLectivo);
            ctrFrm.iniciar();
            carrera.setNombre(periodo.getCarrera().getNombre());
            ctrFrm.editar(periodo,carrera);
            vtnPrdLectivo.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Advertencia!! Seleccione una fila");
        }
    }
    
    public void eliminarPeriodo(){
        PeriodoLectivoMD periodo = new PeriodoLectivoMD();
        if (capturarFila() == null) {
            JOptionPane.showMessageDialog(null, "No se puede Eliminar si no selecciona a un Alumno");
        } else {
            periodo = capturarFila();
            if(bdPerLectivo.eliminarPeriodo(periodo) == true){
                JOptionPane.showMessageDialog(null, "Datos Eliminados Satisfactoriamente");
                llenarTabla();
            } else{
                JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR AL ALUMNO");
            }
        }
    }
    
}
