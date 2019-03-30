package controlador.prdlectivo;

import datechooser.events.SelectionChangedEvent;
import datechooser.events.SelectionChangedListener;
import java.awt.Font;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.carrera.CarreraMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.CmbValidar;
import vista.prdlectivo.FrmPrdLectivo;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmPrdLectivoCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmPrdLectivo frmPrdLectivo;
    private final PeriodoLectivoBD bdPerLectivo;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private boolean editar = false;
    private int id_PeriodoLectivo;
    private int cont = 1;
    private List<CarreraMD> carreras;

    public FrmPrdLectivoCTR(VtnPrincipal vtnPrin, FrmPrdLectivo frmPrdLectivo, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmPrdLectivo = frmPrdLectivo;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaFrm("Periodo lectivo");
        ctrPrin.setIconJIFrame(frmPrdLectivo);
        this.bdPerLectivo = new PeriodoLectivoBD(conecta);
        vtnPrin.getDpnlPrincipal().add(frmPrdLectivo);
        frmPrdLectivo.show();
    }

    public void iniciar() {

        CmbValidar combo_Carreras = new CmbValidar(frmPrdLectivo.getCbx_Carreras(), frmPrdLectivo.getLbl_ErrCarrera());

        ActionListener Cancelar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmPrdLectivo.dispose();
            }
        };

        ActionListener rellenarNombre = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString().equals("|SELECCIONE|") == false) {
                    
                    for(int i = 0; i < carreras.size(); i++){
                        if(frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString().equals(carreras.get(i).getNombre().toUpperCase())){
                            Font negrita = new Font("Tahoma", Font.BOLD, 13);
                            frmPrdLectivo.getTxt_Nombre().setFont(negrita);
                            frmPrdLectivo.getTxt_Nombre().setText(carreras.get(i).getCodigo());
                        }
                    }
                }
            }
        };

        PropertyChangeListener habilitar_Guardar = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                habilitarGuardar();
            }
        };

        KeyListener observacion = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (modelo.validaciones.Validar.esLetras(frmPrdLectivo.getTxtObservacion().getText()) == false
                        && frmPrdLectivo.getTxtObservacion().getText().equals("") == false) {
                    frmPrdLectivo.getLbl_ErrObservacion().setText("Ingrese solo letras");
                    frmPrdLectivo.getLbl_ErrObservacion().setVisible(true);
                } else {
                    frmPrdLectivo.getLbl_ErrObservacion().setVisible(false);
                }
                habilitarGuardar();
            }
        };
        
        iniciarDatos();
        iniciarCarreras();
        iniciarComponentes();
        iniciarFechas();
        habilitarGuardar();
        frmPrdLectivo.getCbx_Carreras().addActionListener(rellenarNombre);
        frmPrdLectivo.getJdc_FechaInicio().addMouseListener(new MouseAdapter(){
            public void MouseClicked(){
                habilitarGuardar();
            }
        });
        frmPrdLectivo.getJdc_FechaFin().addMouseListener(new MouseAdapter(){
            public void MouseClicked(){
                habilitarGuardar();
            }
        });
        frmPrdLectivo.getBtn_Guardar().addActionListener(e -> guardarPeriodo());
        frmPrdLectivo.getBtn_Cancelar().addActionListener(Cancelar);
        frmPrdLectivo.getCbx_Carreras().addActionListener(combo_Carreras);
        frmPrdLectivo.getTxtObservacion().addKeyListener(observacion);
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaFrmFin("Periodo lectivo");
    }
    
    public void iniciarDatos(){
        carreras = bdPerLectivo.capturarCarrera();
    }

    public void iniciarCarreras() {
        for (int i = 0; i < carreras.size(); i++) {
            frmPrdLectivo.getCbx_Carreras().addItem(carreras.get(i).getNombre());
        }
    }

    public void habilitarGuardar() {
        String carrera, nombre, observacion;

        carrera = frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString();
        nombre = frmPrdLectivo.getTxt_Nombre().getText();
        observacion = frmPrdLectivo.getTxtObservacion().getText();

        if (carrera.equals("|SELECCIONE|") == false || nombre.equals("") == false
                || observacion.equals("") == false) {
            frmPrdLectivo.getBtn_Guardar().setEnabled(true);
        } else {
            frmPrdLectivo.getBtn_Guardar().setEnabled(false);
        }

    }

    public void iniciarFechas() {

        LocalDate fechaActual = LocalDate.now();
        Date fechaHoy = Date.from(fechaActual.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        
//        Calendar calendario = Calendar.getInstance();
//        calendario.clear();
//        calendario.set(fechaActual.getYear(), fechaActual.getMonthValue() - 1, fechaActual.getDayOfMonth());
        
        frmPrdLectivo.getJdc_FechaInicio().setDate(fechaHoy);
        frmPrdLectivo.getJdc_FechaFin().setDate(fechaHoy);
    }

    public void iniciarComponentes() {
        frmPrdLectivo.getLbl_ErrCarrera().setVisible(false);
        frmPrdLectivo.getLbl_ErrNombre().setVisible(false);
        frmPrdLectivo.getLbl_ErrFecInicio().setVisible(false);
        frmPrdLectivo.getLbl_ErrFecFin().setVisible(false);
        frmPrdLectivo.getLbl_ErrObservacion().setVisible(false);
        frmPrdLectivo.getTxt_Nombre().setEnabled(false);
        frmPrdLectivo.getBtn_Guardar().setEnabled(false);
    }
    
    public LocalDate convertirDate(Date fecha){
        return Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void guardarPeriodo() {

        String carreras, nombre_Periodo, observacion, fecha_Inicio, fecha_Fin;
        boolean error = false;
        LocalDate fechaActual = LocalDate.now();

        carreras = frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString();
        nombre_Periodo = frmPrdLectivo.getTxt_Nombre().getText();
//        fecha_Inicio = frmPrdLectivo.getJdc_FechaInicio().toString();
//        String fec[] = fecha_Inicio.split("/");
        observacion = frmPrdLectivo.getTxtObservacion().getText();
//        fecha_Fin = frmPrdLectivo.getJdc_FechaFin().toString();
//        String fec_Fin[] = fecha_Fin.split("/");
//        System.out.println("FECHA: " + fecha_Inicio);
        LocalDate dia_Inicio = convertirDate(frmPrdLectivo.getJdc_FechaInicio().getDate());
        LocalDate dia_Fin = convertirDate(frmPrdLectivo.getJdc_FechaFin().getDate());
//        LocalDate dia_Inicio = LocalDate.of(Integer.parseInt(20+fec[2]), Integer.parseInt(fec[1]), Integer.parseInt(fec[0]));
//        LocalDate dia_Fin = LocalDate.of(Integer.parseInt(20+fec_Fin[2]), Integer.parseInt(fec_Fin[1]), Integer.parseInt(fec_Fin[0]));
        
            if (dia_Inicio.isAfter(dia_Fin) == true || dia_Inicio.isEqual(dia_Fin) == true) {
                error = true;
                frmPrdLectivo.getLbl_ErrFecFin().setText("Fecha Incorrecta");
                frmPrdLectivo.getLbl_ErrFecFin().setVisible(true);
            } else {
                frmPrdLectivo.getLbl_ErrFecFin().setVisible(false);
            }
        

        if (error == true) {
            JOptionPane.showMessageDialog(null, "Advertencia!! Revise que esten ingresados correctamente los campos");
            iniciarComponentes();
            habilitarGuardar();
        }else {
            if (editar == false) {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                CarreraMD carrera = new CarreraMD();
                carrera.setId(bdPerLectivo.capturarIdCarrera(frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString()).getId());
                periodo = pasarDatos(periodo, carrera);
                if (bdPerLectivo.guardarPeriodo(periodo, carrera) == true) {
                    JOptionPane.showMessageDialog(null, "Datos grabados correctamente");
                    frmPrdLectivo.dispose();
//                    reiniciarComponentes(frmPrdLectivo);
                } else {
                    JOptionPane.showMessageDialog(null, "Error en grabar los datos");
                }
            } else {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                CarreraMD carrera = new CarreraMD();
                carrera.setId(bdPerLectivo.capturarIdCarrera(frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString()).getId());
                periodo = pasarDatos(bdPerLectivo, carrera);
                periodo.setId_PerioLectivo(id_PeriodoLectivo);
                if (bdPerLectivo.editarPeriodo(periodo, carrera) == true) {
                    JOptionPane.showMessageDialog(null, "Datos editados correctamente");
                    frmPrdLectivo.dispose();
//                    reiniciarComponentes(frmPrdLectivo);
                    editar = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Error en editar los datos");
                }
            }
        }
    }

    public PeriodoLectivoMD pasarDatos(PeriodoLectivoMD periodo, CarreraMD carrera) {
        LocalDate fechaActual = LocalDate.now();
        String date_Inicio = frmPrdLectivo.getJdc_FechaInicio().toString();
        String fec_Inicio[] = date_Inicio.split("/");
        String date_Fin = frmPrdLectivo.getJdc_FechaFin().toString();
        String fec_Fin[] = date_Fin.split("/");

        LocalDate fecha_Inicio = fechaActual;
        fecha_Inicio = LocalDate.of(Integer.parseInt(20+fec_Inicio[2]),
                Integer.parseInt(fec_Inicio[1]),
                Integer.parseInt(fec_Inicio[0]));
        LocalDate fecha_Fin = fechaActual;
        fecha_Fin = LocalDate.of(Integer.parseInt(20+fec_Fin[2]), Integer.parseInt(fec_Fin[1]), Integer.parseInt(fec_Fin[0]));

        periodo.setNombre_PerLectivo(frmPrdLectivo.getTxt_Nombre().getText());
        periodo.setFecha_Inicio(fecha_Inicio);
        periodo.setFecha_Fin(fecha_Fin);
        periodo.setObservacion_PerLectivo(frmPrdLectivo.getTxtObservacion().getText());
        return periodo;
    }

    public void reiniciarComponentes(FrmPrdLectivo vista) {
        vista.getCbx_Carreras().setSelectedItem("|SELECCIONE|");
        vista.getTxt_Nombre().setText("");
        vista.getTxtObservacion().setText("");
    }

    public String sacarIniciales(String palabra) {
        String nombre = "";
        String palabras[] = palabra.split(" ");
        for (int i = 0; i < palabras.length; i++) {
            if (palabras[i].length() > 3) {
                nombre = nombre + palabras[i].charAt(0);
            }
        }
        return nombre;
    }

    public void editar(PeriodoLectivoMD mdPerLectivo, CarreraMD mdCarrera) {
        id_PeriodoLectivo = mdPerLectivo.getId_PerioLectivo();
        editar = true;
        Calendar calendar_Inicio = Calendar.getInstance();
        calendar_Inicio.clear();
        calendar_Inicio.set(mdPerLectivo.getFecha_Inicio().getYear(), mdPerLectivo.getFecha_Inicio().getMonthValue() - 1, mdPerLectivo.getFecha_Inicio().getDayOfMonth());
        Calendar calendar_Fin = Calendar.getInstance();
        calendar_Fin.clear();
        calendar_Fin.set(mdPerLectivo.getFecha_Fin().getYear(), mdPerLectivo.getFecha_Fin().getMonthValue() - 1, mdPerLectivo.getFecha_Fin().getDayOfMonth());

        frmPrdLectivo.getCbx_Carreras().setSelectedItem(mdCarrera.getNombre());
        frmPrdLectivo.getTxt_Nombre().setText(mdPerLectivo.getNombre_PerLectivo());
        frmPrdLectivo.getJdc_FechaInicio().setCalendar(calendar_Inicio);
        frmPrdLectivo.getJdc_FechaFin().setCalendar(calendar_Fin);
        frmPrdLectivo.getTxtObservacion().setText(mdPerLectivo.getObservacion_PerLectivo());
        habilitarGuardar();
    }

}
