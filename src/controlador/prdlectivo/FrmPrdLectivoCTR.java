package controlador.prdlectivo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
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

        bdPerLectivo = new PeriodoLectivoBD();

        vtnPrin.getDpnlPrincipal().add(frmPrdLectivo);
        frmPrdLectivo.show();
    }

    public void iniciar() {

        ActionListener Cancelar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmPrdLectivo.dispose();
            }
        };

        iniciarCarreras();
        iniciarComponentes();
        frmPrdLectivo.getBtn_Guardar().addActionListener(e -> guardarPeriodo());
        frmPrdLectivo.getBtn_Cancelar().addActionListener(Cancelar);
    }

    public void iniciarCarreras() {
        List<PeriodoLectivoMD> sector = bdPerLectivo.capturarCarrera();
        for (int i = 0; i < sector.size(); i++) {
            frmPrdLectivo.getCbx_Carreras().addItem(sector.get(i).getNombre());
        }
    }

    public void iniciarComponentes() {
        frmPrdLectivo.getLbl_ErrCarrera().setVisible(false);
        frmPrdLectivo.getLbl_ErrNombre().setVisible(false);
        frmPrdLectivo.getLbl_ErrFecInicio().setVisible(false);
        frmPrdLectivo.getLbl_ErrObservacion().setVisible(false);
    }

    public void guardarPeriodo() {

        String carreras, nombre_Periodo, observacion, fecha_Inicio;
        boolean error = false, vacio = false;
        LocalDate fechaActual = LocalDate.now();

        carreras = frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString();
        nombre_Periodo = frmPrdLectivo.getTxt_Nombre().getText();
        fecha_Inicio = frmPrdLectivo.getDcr_FecInicio().getText();
        String fec[] = fecha_Inicio.split("/");
        observacion = frmPrdLectivo.getTxtObservacion().getText();

        if (carreras.equals("|SELECCIONE|")) {
            vacio = true;
            frmPrdLectivo.getLbl_ErrCarrera().setVisible(true);
        }

        if (modelo.validaciones.Validar.esLetras(carreras) == false) {
            error = true;
            frmPrdLectivo.getLbl_ErrNombre().setVisible(true);
        } else if (carreras.equals("")) {
            vacio = true;
            frmPrdLectivo.getLbl_ErrNombre().setText("Ingrese un Nombre");
            frmPrdLectivo.getLbl_ErrNombre().setVisible(true);
        }

        if (observacion.equals("")) {
            vacio = true;
            frmPrdLectivo.getLbl_ErrObservacion().setVisible(true);
        }

        if (Integer.parseInt(fec[2]) > fechaActual.getYear()) {
            error = true;
            frmPrdLectivo.getLbl_ErrFecInicio().setVisible(true);
        }

        if (error == true) {
            JOptionPane.showMessageDialog(null, "Advertencia!! Revise que esten ingresados correctamente los campos");
            iniciarComponentes();
        } else if (vacio == true) {
            JOptionPane.showMessageDialog(null, "Advertencia!! Campos Vacio/s");
            iniciarComponentes();
        } else {
            if (editar == false) {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo = pasarDatos(periodo);
                if (bdPerLectivo.guardarPeriodo(periodo) == true) {
                    JOptionPane.showMessageDialog(null, "Datos grabados correctamente");
                    reiniciarComponentes(frmPrdLectivo);
                } else {
                    JOptionPane.showMessageDialog(null, "Error en grabar los datos");
                }
            } else {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo = pasarDatos(bdPerLectivo);
                /*if (bdPerLectivo.editarPeriodo(periodo) == true) {
                    JOptionPane.showMessageDialog(null, "Datos editados correctamente");
                    reiniciarComponentes(frmPrdLectivo);
                    editar = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Error en editar los datos");
                }*/
            }
        }
    }

    public PeriodoLectivoMD pasarDatos(PeriodoLectivoMD periodo) {
        LocalDate fechaActual = LocalDate.now();
        String date_Inicio = frmPrdLectivo.getDcr_FecInicio().getText();
        String fec_Inicio[] = date_Inicio.split("/");
        String date_Fin = frmPrdLectivo.getDcr_FecConclusion().getText();
        String fec_Fin[] = date_Fin.split("/");

        LocalDate fecha_Inicio = fechaActual;
        fecha_Inicio = LocalDate.of(Integer.parseInt(fec_Inicio[2]),
                Integer.parseInt(fec_Inicio[1]),
                Integer.parseInt(fec_Inicio[0]));
        LocalDate fecha_Fin = fechaActual;
        fecha_Fin = LocalDate.of(Integer.parseInt(fec_Fin[2]), Integer.parseInt(fec_Fin[1]), Integer.parseInt(fec_Fin[0]));


        periodo.setId(bdPerLectivo.capturarIdCarrera(frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString()).getId());
        periodo.setNombre_PerLectivo(frmPrdLectivo.getTxt_Nombre().getText());
        periodo.setFechaInicio(fecha_Inicio);
        periodo.setFechaFin(fecha_Fin);
        periodo.setObservacion_PerLectivo(frmPrdLectivo.getTxtObservacion().getText());
        return periodo;
    }

    public void reiniciarComponentes(FrmPrdLectivo vista) {
        vista.getCbx_Carreras().setSelectedItem("|SELECCIONE|");
        vista.getTxt_Nombre().setText("");
        vista.getTxtObservacion().setText("");
    }

    public void editar(PeriodoLectivoMD mdPerLectivo) {
        editar = true;
        String dia_Inicio, mes_Inicio, anio_Inicio;
        String dia_Fin, mes_Fin, anio_Fin;
        dia_Inicio = String.valueOf(mdPerLectivo.getFecha_Inicio().getDayOfMonth());
        mes_Inicio = String.valueOf(mdPerLectivo.getFecha_Inicio().getMonthValue());
        anio_Inicio = String.valueOf(mdPerLectivo.getFecha_Inicio().getYear());
        dia_Fin = String.valueOf(mdPerLectivo.getFecha_Fin().getDayOfMonth());
        mes_Fin = String.valueOf(mdPerLectivo.getFecha_Fin().getMonthValue());
        anio_Fin = String.valueOf(mdPerLectivo.getFecha_Fin().getYear());
//        LocalDate fechaActual = LocalDate.now();
//        String date_Inicio = frmPrdLectivo.getDcr_FecInicio().getText();
//        String fec_Inicio[] = date_Inicio.split("/");
//        String date_Fin = frmPrdLectivo.getDcr_FecConclusion().getText();
//        String fec_Fin[] = date_Fin.split("/");
//        LocalDate fecha_Inicio = fechaActual;
//        fecha_Inicio = LocalDate.of(Integer.parseInt(fec_Inicio[2]),
//                Integer.parseInt(fec_Inicio[1]),
//                Integer.parseInt(fec_Inicio[0]));
//        LocalDate fecha_Fin = fechaActual;
//        fecha_Fin = LocalDate.of(Integer.parseInt(fec_Fin[2]), Integer.parseInt(fec_Fin[1]), Integer.parseInt(fec_Fin[0]));
        
        frmPrdLectivo.getCbx_Carreras().setSelectedItem(mdPerLectivo.getNombre());
        frmPrdLectivo.getTxt_Nombre().setText(mdPerLectivo.getNombre_PerLectivo());
        frmPrdLectivo.getDcr_FecInicio().setText(dia_Inicio + "/" + mes_Inicio + "/" + anio_Inicio);
        frmPrdLectivo.getDcr_FecConclusion().setText(dia_Fin + "/" + mes_Fin + "/" + anio_Fin);
        frmPrdLectivo.getTxtObservacion().setText(mdPerLectivo.getObservacion_PerLectivo());
    }

}
