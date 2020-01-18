package controlador.prdlectivo;

import controlador.periodoLectivoNotas.tipoDeNotas.VtnTipoNotasCTR;
import controlador.principal.DCTR;
import java.awt.Font;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.Validar;
import vista.prdlectivo.FrmPrdLectivo;

/**
 *
 * @author Johnny
 */
public class FrmPrdLectivoCTR extends DCTR {

    private final FrmPrdLectivo frmPrdLectivo;
    private final PeriodoLectivoBD PLBD = PeriodoLectivoBD.single();
    private boolean editar = false; //Variable de edición, donde diferencia si que se edita o se guarda
    private int id_PeriodoLectivo; //Recibe la ID de un Período Lectivo en específico
    private List<CarreraMD> carreras; //Recibe los datos de las Carreras Ingresadas
    //Para cargar el combo de coordinador  
    private final DocenteBD DBD = DocenteBD.single();
    private ArrayList<DocenteMD> docentes;
    private DefaultTableModel mdTbl;

    public FrmPrdLectivoCTR(FrmPrdLectivo frmPrdLectivo, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.frmPrdLectivo = frmPrdLectivo;
    }

    //Ejerce la funcionalidad de esta Ventana
    public void iniciar() {
        ctrPrin.agregarVtn(frmPrdLectivo);
        CmbValidar combo_Carreras = new CmbValidar(frmPrdLectivo.getCbx_Carreras(), frmPrdLectivo.getLbl_ErrCarrera());

        ActionListener Cancelar = (ActionEvent e) -> {
            frmPrdLectivo.dispose();
            ctrPrin.cerradoJIF();
        };

        ActionListener rellenarNombre = (ActionEvent e) -> {
            if (frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString().equals("|SELECCIONE|") == false) {
                for (int i = 0; i < carreras.size(); i++) {
                    if (frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString().equals(carreras.get(i).getNombre().toUpperCase())) {
                        Font negrita = new Font("Tahoma", Font.BOLD, 13);
                        frmPrdLectivo.getTxt_Nombre().setFont(negrita);
                        frmPrdLectivo.getTxt_Nombre().setText(carreras.get(i).getCodigo());
                    }
                }
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
        frmPrdLectivo.getTxtObservacion().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (Validar.esLetras(frmPrdLectivo.getTxtObservacion().getText()) == false
                        && frmPrdLectivo.getTxtObservacion().getText().equals("") == false) {
                    frmPrdLectivo.getLbl_ErrObservacion().setText("Ingrese solo letras");
                    frmPrdLectivo.getLbl_ErrObservacion().setVisible(true);
                } else {
                    frmPrdLectivo.getLbl_ErrObservacion().setVisible(false);
                }
                habilitarGuardar();
            }
        });
        // Para la tabla  
        iniciarTblDocentes();
    }

    private void iniciarTblDocentes() {
        //Formato tbl
        String[] titulo = {"Cédula", "Nombre"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        frmPrdLectivo.getTblDocentes().setModel(mdTbl);
        TblEstilo.formatoTbl(frmPrdLectivo.getTblDocentes());
        //Buscador 
        frmPrdLectivo.getBtnBuscar().addActionListener(e -> buscarDocentes(
                frmPrdLectivo.getTxtBuscar().getText().trim()
        ));
        frmPrdLectivo.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String a = frmPrdLectivo.getTxtBuscar().getText().trim();
                if (e.getKeyCode() == 10) {
                    buscarDocentes(a);
                }
            }
        });
    }

    //Se capturan los datos de Carreras en la Lista
    public void iniciarDatos() {
        carreras = PLBD.capturarCarrera();
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

        if (carrera.equals("|SELECCIONE|") == false
                || nombre.equals("") == false
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
        frmPrdLectivo.getJdcFechaFinClases().setDateFormatString("dd/MM/yyyy");
        frmPrdLectivo.getJdc_FechaInicio().setDate(fechaHoy);
        frmPrdLectivo.getJdc_FechaFin().setDate(fechaHoy);
        frmPrdLectivo.getJdcFechaFinClases().setDate(fechaHoy);
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
        if (fecha != null) {
            return Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
            return null;
        }
    }

    //Guarda o Edita el Período Lectivo basándose en la variable boolean Local
    public void guardarPeriodo() {
        boolean error = false;

        LocalDate dia_Inicio = convertirDate(frmPrdLectivo.getJdc_FechaInicio().getDate());
        LocalDate dia_Fin = convertirDate(frmPrdLectivo.getJdc_FechaFin().getDate());
        LocalDate fechaFinClases = convertirDate(frmPrdLectivo.getJdcFechaFinClases().getDate());

        if (dia_Inicio.isAfter(dia_Fin) == true || dia_Inicio.isEqual(dia_Fin) == true) {
            error = true;
            frmPrdLectivo.getLbl_ErrFecFin().setText("Fecha Incorrecta");
            frmPrdLectivo.getLbl_ErrFecFin().setVisible(true);
        } else {
            frmPrdLectivo.getLbl_ErrFecFin().setVisible(false);
        }

        if (fechaFinClases == null) {
            error = true;
            JOptionPane.showMessageDialog(
                    frmPrdLectivo,
                    "Debe ingresar la fecha de fin de clases.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        int posCoord = frmPrdLectivo.getTblDocentes().getSelectedRow();
        if (posCoord < 0) {
            error = true;
            JOptionPane.showMessageDialog(
                    frmPrdLectivo,
                    "Debe seleccionar un coordinador.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        if (error) {
            habilitarGuardar();
        } else {
            if (!editar) {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                CarreraMD carrera = new CarreraMD();
                carrera.setId(PLBD.capturarIdCarrera(frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString()).getId());
                periodo = pasarDatos(periodo, carrera);
                periodo.setDocente(docentes.get(posCoord));
                if (PLBD.guardarPeriodo(periodo, carrera)) {
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
                    frmPrdLectivo.dispose();
                    ctrPrin.cerradoJIF();

                    VtnTipoNotasCTR controlador = new VtnTipoNotasCTR(ctrPrin.getVtnPrin());
                    controlador.Init();
                } else {
                    JOptionPane.showMessageDialog(null, "Error en guardar los datos");
                }
            } else {
                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                CarreraMD carrera = new CarreraMD();
                carrera.setId(PLBD.capturarIdCarrera(frmPrdLectivo.getCbx_Carreras().getSelectedItem().toString()).getId());
                periodo = pasarDatos(periodo, carrera);
                periodo.setID(id_PeriodoLectivo);
                periodo.setDocente(docentes.get(posCoord));
                if (PLBD.editarPeriodo(periodo, carrera)) {
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
        LocalDate finClases = convertirDate(frmPrdLectivo.getJdcFechaFinClases().getDate());

        periodo.setNombre(frmPrdLectivo.getTxt_Nombre().getText() + " ");
        if (dia_Inicio != null) {
            periodo.setFechaInicio(dia_Inicio);
        }

        if (dia_Fin != null) {
            periodo.setFechaFin(dia_Fin);
        }

        if (finClases != null) {
            periodo.setFechaFinClases(finClases);
        }

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
        for (String p : palabras) {
            if (p.length() > 3) {
                nombre = nombre + p.charAt(0);
            }
        }
        return nombre;
    }

    //Extrae los datos de los objetos y los pasa a los componentes de un respectivo Período Lectivo
    public void editar(PeriodoLectivoMD mdPerLectivo, CarreraMD mdCarrera) {
        id_PeriodoLectivo = mdPerLectivo.getID();
        editar = true;
        Calendar fechaIni = Calendar.getInstance();
        fechaIni.clear();
        fechaIni.set(
                mdPerLectivo.getFechaInicio().getYear(),
                mdPerLectivo.getFechaInicio().getMonthValue() - 1,
                mdPerLectivo.getFechaInicio().getDayOfMonth()
        );
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.clear();
        fechaFin.set(
                mdPerLectivo.getFechaFin().getYear(),
                mdPerLectivo.getFechaFin().getMonthValue() - 1,
                mdPerLectivo.getFechaFin().getDayOfMonth()
        );

        Calendar fechaClasesFin = Calendar.getInstance();
        fechaClasesFin.clear();

        if (mdPerLectivo.getFechaFinClases() != null) {
            fechaClasesFin.set(
                    mdPerLectivo.getFechaFinClases().getYear(),
                    mdPerLectivo.getFechaFinClases().getMonthValue(),
                    mdPerLectivo.getFechaFinClases().getDayOfMonth()
            );
        }

        frmPrdLectivo.getCbx_Carreras().setSelectedItem(mdCarrera.getNombre());
        frmPrdLectivo.getCbx_Carreras().setEnabled(false);
        frmPrdLectivo.getTxt_Nombre().setText(mdPerLectivo.getNombre());
        frmPrdLectivo.getJdc_FechaInicio().setCalendar(fechaIni);
        frmPrdLectivo.getJdc_FechaFin().setCalendar(fechaFin);
        frmPrdLectivo.getTxtObservacion().setText(mdPerLectivo.getObservacion());
        frmPrdLectivo.getJdcFechaFinClases().setCalendar(fechaClasesFin);
        frmPrdLectivo.getTxtBuscar().setText(mdPerLectivo.getDocente().getIdentificacion());
        frmPrdLectivo.getBtnBuscar().doClick();
        habilitarGuardar();
    }

    private void buscarDocentes(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            docentes = DBD.buscar(aguja);
            llenarTblDocentes(docentes);
        }
    }

    private void llenarTblDocentes(ArrayList<DocenteMD> docentes) {
        mdTbl.setRowCount(0);
        if (docentes != null) {
            docentes.forEach(d -> {
                Object[] valores = {d.getIdentificacion(),
                    d.getPrimerApellido() + " " + d.getSegundoApellido()
                    + " " + d.getPrimerNombre() + " "
                    + " " + d.getSegundoNombre()};
                mdTbl.addRow(valores);
            });
        }
    }

}
