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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
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

    public FrmPrdLectivoCTR(VtnPrincipal vtnPrin, FrmPrdLectivo frmPrdLectivo, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmPrdLectivo = frmPrdLectivo;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaFrm("Periodo lectivo");

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
                    Font negrita = new Font("Tahoma", Font.BOLD, 13);
                    frmPrdLectivo.getTxt_Nombre().setFont(negrita);
                    frmPrdLectivo.getTxt_Nombre().setText(sacarIniciales(frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString()));
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
               if (modelo.validaciones.Validar.esLetras(frmPrdLectivo.getTxtObservacion().getText()) == false && 
                       frmPrdLectivo.getTxtObservacion().getText().equals("") == false) {
                   frmPrdLectivo.getLbl_ErrObservacion().setText("Ingrese solo letras");
                   frmPrdLectivo.getLbl_ErrObservacion().setVisible(true);
            } else {
                frmPrdLectivo.getLbl_ErrObservacion().setVisible(false);
            }
                habilitarGuardar();
            }
        };

        SelectionChangedListener cambioFecha = new SelectionChangedListener() {
            @Override
            public void onSelectionChange(SelectionChangedEvent sce) {
                
                if(cont == 1){
                    System.out.println("cont: " + cont);
                    cambiarFecha();
                } else if(cont == 2){
                    System.out.println("cont: " + cont);
                    String date_Inicio = frmPrdLectivo.getDcr_FecInicio().getText();
                    String fec_Inicio[] = date_Inicio.split("/");
                    String date_Fin = frmPrdLectivo.getDcr_FecConclusion().getText();
                    System.out.println("FECHA: " + date_Fin);
                    String fec_Fin[] = date_Fin.split("/");
                    cont = 1;
                }

                String date_Inicio = frmPrdLectivo.getDcr_FecInicio().getText();
                String fec_Inicio[] = date_Inicio.split("/");
                String date_Fin = frmPrdLectivo.getDcr_FecConclusion().getText();
                System.out.println("Fecha: " + date_Fin);
                String fec_Fin[] = date_Fin.split("/");

                LocalDate dia_Inicio = LocalDate.now();
                LocalDate dia_Fin = LocalDate.now();
                dia_Inicio.withDayOfMonth(Integer.parseInt(fec_Inicio[0]));
                dia_Inicio.withMonth(Integer.parseInt(fec_Inicio[1]));
                dia_Inicio.withYear(Integer.parseInt(fec_Inicio[2]));

                dia_Fin.withDayOfMonth(Integer.parseInt(fec_Fin[0]));
                dia_Fin.withMonth(Integer.parseInt(fec_Fin[1]));
                dia_Fin.withYear(Integer.parseInt(fec_Fin[2]));

                System.out.println("Fecha Inicio: " + dia_Inicio.toString());
                System.out.println("Fecha Fin: " + dia_Fin.toString());
                
                if (dia_Inicio.isBefore(dia_Fin) == true) {
                    System.out.println("Fecha correcta");
                    frmPrdLectivo.getLbl_ErrFecFin().setVisible(false);
                } else {
                    System.out.println("Fecha incorrecta");
                    frmPrdLectivo.getLbl_ErrFecFin().setText("La Fecha debe ser mayor 4 meses a la Fecha Inicial");
                    frmPrdLectivo.getLbl_ErrFecFin().setVisible(true);
                }

//                if (Integer.parseInt(fec_Inicio[1]) >= 9 && Integer.parseInt(fec_Inicio[1]) <= 12) {
//                    System.out.println("fecha compleja");
//                    switch (fec_Inicio[1]) {
//                        case "09":
//                            if (Integer.parseInt(fec_Fin[1]) <= 12 || Integer.parseInt(fec_Fin[1]) == 1 && Integer.parseInt(fec_Fin[2]) == Integer.parseInt(fec_Inicio[2])) {
//                                frmPrdLectivo.getLbl_ErrFecFin().setText("La Fecha debe ser mayor 4 meses a la Fecha Inicial");
//                                frmPrdLectivo.getLbl_ErrFecFin().setVisible(true);
//                            } else {
//                                frmPrdLectivo.getLbl_ErrFecFin().setVisible(false);
//                            }
//                            break;
//                        case "10":
//                            if (Integer.parseInt(fec_Fin[1]) <= 12 || Integer.parseInt(fec_Fin[1]) <= 2 && Integer.parseInt(fec_Fin[2]) == Integer.parseInt(fec_Inicio[2])) {
//                                frmPrdLectivo.getLbl_ErrFecFin().setText("La Fecha debe ser mayor 4 meses a la Fecha Inicial");
//                                frmPrdLectivo.getLbl_ErrFecFin().setVisible(true);
//                            } else {
//                                frmPrdLectivo.getLbl_ErrFecFin().setVisible(false);
//                            }
//                            break;
//                        case "11":
//                            if (Integer.parseInt(fec_Fin[1]) <= 12 || Integer.parseInt(fec_Fin[1]) <= 3 && Integer.parseInt(fec_Fin[2]) == Integer.parseInt(fec_Inicio[2])) {
//                                frmPrdLectivo.getLbl_ErrFecFin().setText("La Fecha debe ser mayor 4 meses a la Fecha Inicial");
//                                frmPrdLectivo.getLbl_ErrFecFin().setVisible(true);
//                            } else {
//                                frmPrdLectivo.getLbl_ErrFecFin().setVisible(false);
//                            }
//                            break;
//                        case "12":
//                            if (Integer.parseInt(fec_Fin[1]) <= 12 || Integer.parseInt(fec_Fin[1]) <= 4 && Integer.parseInt(fec_Fin[2]) == Integer.parseInt(fec_Inicio[2])) {
//                                frmPrdLectivo.getLbl_ErrFecFin().setText("La Fecha debe ser mayor 4 meses a la Fecha Inicial");
//                                frmPrdLectivo.getLbl_ErrFecFin().setVisible(true);
//                            } else {
//                                frmPrdLectivo.getLbl_ErrFecFin().setVisible(false);
//                            }
//                            break;
//                    }
//                } else {
//                    System.out.println("fecha simple");
//                    if (Integer.parseInt(fec_Inicio[1]) + 4 >= Integer.parseInt(fec_Fin[1]) && Integer.parseInt(fec_Fin[1]) < Integer.parseInt(fec_Inicio[0])) {
//                        frmPrdLectivo.getLbl_ErrFecFin().setText("La Fecha debe ser mayor 4 meses a la Fecha Inicial");
//                        frmPrdLectivo.getLbl_ErrFecFin().setVisible(true);
//                    } else {
//                        frmPrdLectivo.getLbl_ErrFecFin().setVisible(false);
//                    }
//                }
            }
        };

        iniciarCarreras();
        iniciarComponentes();
        iniciarFechas();
        habilitarGuardar();
        frmPrdLectivo.getCbx_Carreras().addActionListener(rellenarNombre);
        //frmPrdLectivo.getDcr_FecConclusion().addSelectionChangedListener(cambioFecha);
        frmPrdLectivo.getBtn_Guardar().addActionListener(e -> guardarPeriodo());
        frmPrdLectivo.getBtn_Cancelar().addActionListener(Cancelar);
        frmPrdLectivo.getCbx_Carreras().addActionListener(combo_Carreras);
        frmPrdLectivo.getTxtObservacion().addKeyListener(observacion);
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaFrmFin("Periodo lectivo");
    }

    public void iniciarCarreras() {
        List<CarreraMD> sector = bdPerLectivo.capturarCarrera();
        for (int i = 0; i < sector.size(); i++) {
            frmPrdLectivo.getCbx_Carreras().addItem(sector.get(i).getNombre());
        }
    }
    
    public void habilitarGuardar(){
        String carrera, nombre, observacion;
        
        carrera = frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString();
        nombre = frmPrdLectivo.getTxt_Nombre().getText();
        observacion = frmPrdLectivo.getTxtObservacion().getText();
        
        if(carrera.equals("|SELECCIONE|") == false || nombre.equals("") == false || 
                observacion.equals("") == false){
            frmPrdLectivo.getBtn_Guardar().setEnabled(true);
        } else{
            frmPrdLectivo.getBtn_Guardar().setEnabled(false);
        }
        
    }
    
    public void cambiarFecha(){
        cont = 2;
        System.out.println("Entro en metodo");
        LocalDate fechaActual = LocalDate.now();
        Calendar calendario = Calendar.getInstance();
        calendario.clear();
        calendario.set(fechaActual.getYear(), fechaActual.getMonthValue() - 1, fechaActual.getDayOfMonth());
        frmPrdLectivo.getDcr_FecConclusion().setSelectedDate(calendario);
    }

    public void iniciarFechas() {

        LocalDate fechaActual = LocalDate.now();
        Calendar calendario = Calendar.getInstance();
        calendario.clear();
        calendario.set(fechaActual.getYear(), fechaActual.getMonthValue() - 1, fechaActual.getDayOfMonth());
        frmPrdLectivo.getDcr_FecInicio().setSelectedDate(calendario);
        frmPrdLectivo.getDcr_FecConclusion().setSelectedDate(calendario);
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

    public void guardarPeriodo() {

        String carreras, nombre_Periodo, observacion, fecha_Inicio, fecha_Fin;
        boolean error = false, vacio = false;
        LocalDate fechaActual = LocalDate.now();

        carreras = frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString();
        nombre_Periodo = frmPrdLectivo.getTxt_Nombre().getText();
        fecha_Inicio = frmPrdLectivo.getDcr_FecInicio().getText();
        String fec[] = fecha_Inicio.split("/");
        observacion = frmPrdLectivo.getTxtObservacion().getText();
        fecha_Fin = frmPrdLectivo.getDcr_FecConclusion().getText();
        String fec_Fin[] = fecha_Fin.split("/");

        if (carreras.equals("|SELECCIONE|")) {
            vacio = true;
            frmPrdLectivo.getLbl_ErrCarrera().setVisible(true);
        }

        if (modelo.validaciones.Validar.esLetrasYNumeros(nombre_Periodo) == false && nombre_Periodo.contains("/") == false) {
            error = true;
            frmPrdLectivo.getLbl_ErrNombre().setVisible(true);
        } else if (nombre_Periodo.equals("")) {
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

        if (Integer.parseInt(fec_Fin[1]) < Integer.parseInt(fec[1]) || Integer.parseInt(fec_Fin[1]) < Integer.parseInt(fec[1]) + 4) {
            error = true;
            frmPrdLectivo.getLbl_ErrFecFin().setVisible(true);
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
                CarreraMD carrera = new CarreraMD();
                carrera.setId(bdPerLectivo.capturarIdCarrera(frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString()).getId());
                periodo = pasarDatos(periodo, carrera);
                if (bdPerLectivo.guardarPeriodo(periodo, carrera) == true) {
                    JOptionPane.showMessageDialog(null, "Datos grabados correctamente");
                    reiniciarComponentes(frmPrdLectivo);
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
                    reiniciarComponentes(frmPrdLectivo);
                    editar = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Error en editar los datos");
                }
            }
        }
    }

    public PeriodoLectivoMD pasarDatos(PeriodoLectivoMD periodo, CarreraMD carrera) {
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
        frmPrdLectivo.getDcr_FecInicio().setSelectedDate(calendar_Inicio);
        frmPrdLectivo.getDcr_FecConclusion().setSelectedDate(calendar_Fin);
        frmPrdLectivo.getTxtObservacion().setText(mdPerLectivo.getObservacion_PerLectivo());
    }

}
