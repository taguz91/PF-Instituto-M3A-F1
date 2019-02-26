package controlador.prdlectivo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.AlumnoBD;
import vista.prdlectivo.FrmPrdLectivo;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmPrdLectivoCTR {
    
    private final VtnPrincipal vtnPrin;
    private final FrmPrdLectivo frmPrdLectivo; 
    private PeriodoLectivoBD bdPerLectivo;
    private boolean editar = false;

    public FrmPrdLectivoCTR(VtnPrincipal vtnPrin, FrmPrdLectivo frmPrdLectivo) {
        this.vtnPrin = vtnPrin;
        this.frmPrdLectivo = frmPrdLectivo; 
        
        vtnPrin.getDpnlPrincipal().add(frmPrdLectivo);   
        frmPrdLectivo.show(); 
    }
    
    public void iniciar(){
        
        ActionListener Cancelar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmPrdLectivo.setVisible(false);
            }
        };
        
        iniciarCarreras();
        frmPrdLectivo.getBtn_Guardar().addActionListener(e -> guardarPeriodo());
        frmPrdLectivo.getBtn_Cancelar().addActionListener(Cancelar);
    }
    
    public void iniciarCarreras(){
        List<PeriodoLectivoMD> sector = bdPerLectivo.capturarCarrera();
        for(int i = 0; i < sector.size(); i++){
            frmPrdLectivo.getCbx_Carreras().addItem(sector.get(i).getNombre()); 
        }
    }
    
    public void guardarPeriodo(){
        if(editar == false){
            PeriodoLectivoBD periodo = new PeriodoLectivoBD();
            this.bdPerLectivo = pasarDatos(periodo, frmPrdLectivo);
            if(bdPerLectivo.guardarPeriodo() == true){
                JOptionPane.showMessageDialog(null, "Datos grabados correctamente");
                reiniciarComponentes(frmPrdLectivo);
            } else{
                JOptionPane.showMessageDialog(null, "Error en grabar los datos");
            }
        } else{
            PeriodoLectivoBD periodo = new PeriodoLectivoBD();
            frmPrdLectivo.getCbx_Carreras().setSelectedItem(bdPerLectivo.capturarNomCarrera(bdPerLectivo.getId()).getNombre());
            frmPrdLectivo.getTxt_Nombre().setText(bdPerLectivo.getNombre_PerLectivo());
            //frmPrdLectivo.getDcr_FecInicio().setSelectedDate(bdPerLectivo.getFechaInicio());
            //frmPrdLectivo.getDcr_FecConclusion().setSelectedDate();
            frmPrdLectivo.getTxtObservacion().setText(bdPerLectivo.getObservacion_PerLectivo());
            periodo = pasarDatos(bdPerLectivo, frmPrdLectivo);
            if(periodo.editarPeriodo() == true){
                JOptionPane.showMessageDialog(null, "Datos editados correctamente");
                reiniciarComponentes(frmPrdLectivo);
                editar = false;
            } else {
                JOptionPane.showMessageDialog(null, "Error en editar los datos");
            }
        }
    }
    
    public PeriodoLectivoBD pasarDatos(PeriodoLectivoBD periodo, FrmPrdLectivo vista){
        String date_Inicio = vista.getDcr_FecInicio().getText();
        String fec_Inicio[] = date_Inicio.split("/"); 
        String date_Fin = vista.getDcr_FecConclusion().getText();
        String fec_Fin[] = date_Fin.split("/");
        LocalDate fecha_Inicio = LocalDate.of(Integer.parseInt(fec_Inicio[2]),
                Integer.parseInt(fec_Inicio[1]), 
                Integer.parseInt(fec_Inicio[0]));
        LocalDate fecha_Fin = LocalDate.of(Integer.parseInt(fec_Fin[2]), Integer.parseInt(fec_Fin[1]), Integer.parseInt(fec_Fin[0]));
        
        periodo.setId(bdPerLectivo.capturarIdCarrera(vista.getCbx_Carreras().getSelectedItem().toString()).getId());
        periodo.setNombre_PerLectivo(vista.getTxt_Nombre().getText());
        periodo.setFechaInicio(fecha_Inicio);
        periodo.setFechaFin(fecha_Fin);
        periodo.setObservacion_PerLectivo(vista.getTxtObservacion().getText());
        return periodo;
    }
    
    public void reiniciarComponentes(FrmPrdLectivo vista){
        vista.getCbx_Carreras().setSelectedItem("|SELECCIONE|");
        vista.getTxt_Nombre().setText("");
        vista.getTxtObservacion().setText("");
    }
    
    public void editar(PeriodoLectivoBD bdPerLectivo) {
        editar = true;
        this.bdPerLectivo = bdPerLectivo;
    }
    
}
