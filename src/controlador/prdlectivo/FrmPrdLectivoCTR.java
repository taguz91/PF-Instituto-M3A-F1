package controlador.prdlectivo;

import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import controlador.principal.DCTR;
import java.awt.Font;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.carrera.CarreraMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.CmbValidar;
import vista.prdlectivo.FrmPrdLectivo;

/**
 *
 * @author Johnny
 */
public class FrmPrdLectivoCTR extends DCTR {

    private final FrmPrdLectivo frmPrdLectivo;
    private final PeriodoLectivoBD bdPerLectivo;
    private boolean editar = false; //Variable de edición, donde diferencia si que se edita o se guarda
    private int id_PeriodoLectivo; //Recibe la ID de un Período Lectivo en específico
    private List<CarreraMD> carreras; //Recibe los datos de las Carreras Ingresadas

    public FrmPrdLectivoCTR(FrmPrdLectivo frmPrdLectivo, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.frmPrdLectivo = frmPrdLectivo;
        //Cambiamos el estado del cursos  
        this.bdPerLectivo = new PeriodoLectivoBD(ctrPrin.getConecta());

    }

    //Ejerce la funcionalidad de esta Ventana
    public void iniciar() {
        ctrPrin.agregarVtn(frmPrdLectivo);
        CmbValidar combo_Carreras = new CmbValidar(frmPrdLectivo.getCbx_Carreras(), frmPrdLectivo.getLbl_ErrCarrera());

        ActionListener Cancelar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmPrdLectivo.dispose();
                ctrPrin.cerradoJIF();
            }
        };

        ActionListener rellenarNombre = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString().equals("|SELECCIONE|") == false) {

                    for (int i = 0; i < carreras.size(); i++) {
                        if (frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString().equals(carreras.get(i).getNombre().toUpperCase())) {
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

        //Se aplican los métodos de inicialización
        iniciarDatos();
        iniciarCarreras();
        iniciarComponentes();
        iniciarFechas();
        habilitarGuardar();
        frmPrdLectivo.getCbx_Carreras().addActionListener(rellenarNombre);
        frmPrdLectivo.getJdc_FechaInicio().addMouseListener(new MouseAdapter() {
            public void MouseClicked() {
                habilitarGuardar();
            }
        });
        frmPrdLectivo.getJdc_FechaFin().addMouseListener(new MouseAdapter() {
            public void MouseClicked() {
                habilitarGuardar();
            }
        });
        //Asignación de Eventos a los componentes
        frmPrdLectivo.getBtn_Guardar().addActionListener(e -> guardarPeriodo());
        frmPrdLectivo.getBtn_Cancelar().addActionListener(Cancelar);
        frmPrdLectivo.getCbx_Carreras().addActionListener(combo_Carreras);
        frmPrdLectivo.getTxtObservacion().addKeyListener(observacion);
    }

    //Se capturan los datos de Carreras en la Lista
    public void iniciarDatos() {
        carreras = bdPerLectivo.capturarCarrera();
    }

    //Se insertan los datos de la Lista en el Combo box
    public void iniciarCarreras() {
        for (int i = 0; i < carreras.size(); i++) {
            frmPrdLectivo.getCbx_Carreras().addItem(carreras.get(i).getNombre());
        }
    }

    //Habilita el Botón Guardar si es que estan con datos lo componentes
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

    //Inicia los JDateChooser en la fecha actual
    public void iniciarFechas() {

        LocalDate fechaActual = LocalDate.now();
        Date fechaHoy = Date.from(fechaActual.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        frmPrdLectivo.getJdc_FechaInicio().setDateFormatString("dd/MM/yyyy");
        frmPrdLectivo.getJdc_FechaFin().setDateFormatString("dd/MM/yyyy");
        frmPrdLectivo.getJdc_FechaInicio().setDate(fechaHoy);
        frmPrdLectivo.getJdc_FechaFin().setDate(fechaHoy);
    }

    //Inicializa ocultos los labels de error 
    public void iniciarComponentes() {
        frmPrdLectivo.getCbx_Carreras().setToolTipText("Seleccione una Carrera para generar el Nombre del Período");
        frmPrdLectivo.getTxt_Nombre().setToolTipText("El nombre se genera si selecciona una Carrera");
        frmPrdLectivo.getJdc_FechaInicio().setToolTipText("Seleccione la Fecha de Inicio del Período Lectivo");
        frmPrdLectivo.getJdc_FechaFin().setToolTipText("Seleccione la Fecha de Terminación del Período Lectivo");
        frmPrdLectivo.getTxtObservacion().setToolTipText("Ingrese una Observación de este Período Lectivo");
        frmPrdLectivo.getBtn_Guardar().setToolTipText("Se habilitará después que los campos con \"*\" esten llenos");

        frmPrdLectivo.getLbl_ErrCarrera().setVisible(false);
        frmPrdLectivo.getLbl_ErrNombre().setVisible(false);
        frmPrdLectivo.getLbl_ErrFecInicio().setVisible(false);
        frmPrdLectivo.getLbl_ErrFecFin().setVisible(false);
        frmPrdLectivo.getLbl_ErrObservacion().setVisible(false);
        frmPrdLectivo.getTxt_Nombre().setEnabled(false);
        frmPrdLectivo.getBtn_Guardar().setEnabled(false);
    }

    //Convierte una Date en un LocalDate
    public LocalDate convertirDate(Date fecha) {
        return Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    //Guarda o Edita el Período Lectivo basándose en la variable boolean Local
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
        } else {
            if (editar == false) {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                CarreraMD carrera = new CarreraMD();
                carrera.setId(bdPerLectivo.capturarIdCarrera(frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString()).getId());
                periodo = pasarDatos(periodo, carrera);
                if (bdPerLectivo.guardarPeriodo(periodo, carrera) == true) {
                    JOptionPane.showMessageDialog(null, "Datos grabados correctamente");
                    frmPrdLectivo.dispose();
                    ctrPrin.cerradoJIF();
                    
                    VtnTipoNotasCTR controlador = new VtnTipoNotasCTR(ctrPrin.getVtnPrin());
                    controlador.Init();
                } else {
                    JOptionPane.showMessageDialog(null, "Error en grabar los datos");
                }
            } else {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                CarreraMD carrera = new CarreraMD();
                carrera.setId(bdPerLectivo.capturarIdCarrera(frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString()).getId());
                periodo = pasarDatos(bdPerLectivo, carrera);
                periodo.setID(id_PeriodoLectivo);
                if (bdPerLectivo.editarPeriodo(periodo, carrera) == true) {
                    JOptionPane.showMessageDialog(null, "Datos editados correctamente");
                    frmPrdLectivo.dispose();
                    ctrPrin.cerradoJIF();
                    editar = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Error en editar los datos");
                }
            }
        }
    }

    //Pasa los datos a un objeto para Guardarlos en la Base de Datos
    public PeriodoLectivoMD pasarDatos(PeriodoLectivoMD periodo, CarreraMD carrera) {

        LocalDate dia_Inicio = convertirDate(frmPrdLectivo.getJdc_FechaInicio().getDate());
        LocalDate dia_Fin = convertirDate(frmPrdLectivo.getJdc_FechaFin().getDate());

        periodo.setNombre(frmPrdLectivo.getTxt_Nombre().getText() + " ");
        periodo.setFechaInicio(dia_Inicio);
        periodo.setFechaFin(dia_Fin);
        periodo.setObservacion(frmPrdLectivo.getTxtObservacion().getText());
        return periodo;
    }

    //Reinicia los componentes sin datos
    public void reiniciarComponentes(FrmPrdLectivo vista) {
        vista.getCbx_Carreras().setSelectedItem("|SELECCIONE|");
        vista.getTxt_Nombre().setText("");
        vista.getTxtObservacion().setText("");
    }

    //Extrae las iniciales de una palabra, usado para extraer las iniciales de las Carreras
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

    //Extrae los datos de los objetos y los pasa a los componentes de un respectivo Período Lectivo
    public void editar(PeriodoLectivoMD mdPerLectivo, CarreraMD mdCarrera) {
        id_PeriodoLectivo = mdPerLectivo.getID();
        editar = true;
        Calendar calendar_Inicio = Calendar.getInstance();
        calendar_Inicio.clear();
        calendar_Inicio.set(mdPerLectivo.getFechaInicio().getYear(), mdPerLectivo.getFechaInicio().getMonthValue() - 1, mdPerLectivo.getFechaInicio().getDayOfMonth());
        Calendar calendar_Fin = Calendar.getInstance();
        calendar_Fin.clear();
        calendar_Fin.set(mdPerLectivo.getFechaFin().getYear(), mdPerLectivo.getFechaFin().getMonthValue() - 1, mdPerLectivo.getFechaFin().getDayOfMonth());

        frmPrdLectivo.getCbx_Carreras().setSelectedItem(mdCarrera.getNombre());
        frmPrdLectivo.getTxt_Nombre().setText(mdPerLectivo.getNombre());
        frmPrdLectivo.getJdc_FechaInicio().setCalendar(calendar_Inicio);
        frmPrdLectivo.getJdc_FechaFin().setCalendar(calendar_Fin);
        frmPrdLectivo.getTxtObservacion().setText(mdPerLectivo.getObservacion());
        habilitarGuardar();
    }

}
