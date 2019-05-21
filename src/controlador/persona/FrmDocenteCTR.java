package controlador.persona;

import controlador.principal.DCTR;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.util.Calendar;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.validaciones.TxtVCedula;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.persona.FrmDocente;
import vista.persona.FrmPersona;

public class FrmDocenteCTR extends DCTR {

    //Para saber si se esta editando una persona  
    private boolean editar = false;
    private int idDocente = 0;
    private VtnDocenteCTR docenteVtn;
    private final FrmDocente frmDocente;
    private final DocenteBD docente;
    private boolean guardar = false;
    private static int cont = 0; // Variable de Acceso para permitir buscar los datos de la persona mediante el evento de Teclado
    private static int validar = 0; //Variable para saber a que textFiel se valida
    //Para ver si existe una persona  
    private final PersonaBD per;
    private DocenteMD d;
    private TxtVCedula valCe;
    private String cedula;

    // private DocenteBD per;
    //Para verificar si existe la persona tipo docente  
    private final FrmPersona persona = new FrmPersona();

    public FrmDocenteCTR(FrmDocente frmDocente, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.frmDocente = frmDocente;
        this.docente = new DocenteBD(ctrPrin.getConecta());
        this.per = new PersonaBD(ctrPrin.getConecta());
    }

    private void abrirFrmPersona() {
        FrmPersona frmPersona = new FrmPersona();
        FrmPersonaCTR ctrFrmPersona = new FrmPersonaCTR(frmPersona, ctrPrin);
        ctrFrmPersona.iniciar();
        frmPersona.getCmbTipoId().setSelectedItem(frmDocente.getCmbTipoIdentificacion().getSelectedItem());
        frmPersona.getTxtIdentificacion().setText(cedula);
        frmDocente.dispose();
    }

    public void iniciar() {
        ctrPrin.agregarVtn(frmDocente);
        // frmDocente.getBtnBuscarPersona().addActionListener(e -> buscarPersona());
        //frmDocente.getBtnGuardar().addActionListener(e -> guardarDocente());
        //Accion de buscar una persona  
        //frmDocente.getBtnBuscarPersona().addActionListener(e -> consular());
        frmDocente.getBtnCancelar().addActionListener(e -> salirBoton());

        FocusListener Buscar = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cont++;
            }

            @Override
            public void focusLost(FocusEvent e) {
                //buscarCedula(frmDocente.getTxtIdentificacion().getText());
            }
        };
        FocusListener titulo = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cont++;
            }

            @Override
            public void focusLost(FocusEvent e) {
                campoTitulo();
            }
        };
        FocusListener abreviatura = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cont++;
            }

            @Override
            public void focusLost(FocusEvent e) {
                camposNulos();
            }
        };
        KeyListener TITULO = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                campoTitulo();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                validar = 5;
                //validarComponentes(frmDocente.getTxtIdentificacion().getText());
            }
        };

        KeyListener ABREVIATURA = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                camposNulos();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        inhabilitarComponentesDocente();
        iniciarComponentes();
        iniciarFechas();
        frmDocente.getTxtIdentificacion().addFocusListener(Buscar);
        //  frmDocente.getTxtIdentificacion().addKeyListener(cedula);
        frmDocente.getBtnBuscarPersona().addActionListener(e -> buscarCedula(frmDocente.getTxtIdentificacion().getText()));
        frmDocente.getBtnGuardar().addActionListener(e -> guardarDocente());
        frmDocente.getBtnRegistrarPersona().addActionListener(e -> abrirFrmPersona());
        frmDocente.getTxtAbreviaturaDocente().addActionListener(e -> camposNulos());
        frmDocente.getTxtTituloDocente().addActionListener(e -> campoTitulo());
        //Validacion de la cedula
        //frmDocente.getTxtIdentificacion().addKeyListener(new TxtVCedula(frmDocente.getTxtIdentificacion(),
        //    frmDocente.getLblError()));

        frmDocente.getTxtTituloDocente().addKeyListener(TITULO);
        frmDocente.getTxtAbreviaturaDocente().addKeyListener(ABREVIATURA);
        frmDocente.getTxtTituloDocente().addFocusListener(titulo);
        frmDocente.getTxtAbreviaturaDocente().addFocusListener(abreviatura);

        frmDocente.getTxtIdentificacion().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                //   buscarCedula(frmDocente.getTxtIdentificacion().getText());
            }

        });

        frmDocente.getTxtTituloDocente().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                campoTitulo();
            }

        });
        frmDocente.getTxtAbreviaturaDocente().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                camposNulos();
            }

        });

        valCe = new TxtVCedula(frmDocente.getTxtIdentificacion(), frmDocente.getLblError());
        frmDocente.getCmbTipoIdentificacion().addActionListener(e -> tipoID());

        //Cuando termina de cargar todo se le vuelve a su estado normal.
        ctrPrin.estadoCargaFrmFin("FORMULARIO DE REGISTRO DOCENTE");
    }

    private void tipoID() {
        int pos = frmDocente.getCmbTipoIdentificacion().getSelectedIndex();
        frmDocente.getTxtIdentificacion().setEnabled(false);
        if (pos == 1) {
            frmDocente.getTxtIdentificacion().addKeyListener(valCe);
            //Activamos el campo para ingresar los datos
            frmDocente.getTxtIdentificacion().setEnabled(true);
            inhabilitarComponentesDocente();
            reiniciarComponentes(frmDocente);
            frmDocente.getBtnRegistrarPersona().setVisible(false);

        } else if (pos == 2) {
            frmDocente.getTxtIdentificacion().removeKeyListener(valCe);
            frmDocente.getTxtIdentificacion().setBorder(
                    javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
            frmDocente.getLblError().setVisible(false);
            //Activamos el campo para ingresar los datos
            frmDocente.getTxtIdentificacion().setEnabled(true);
            inhabilitarComponentesDocente();
            reiniciarComponentes(frmDocente);
            frmDocente.getBtnRegistrarPersona().setVisible(false);

        } else if (pos == 0) {
            reiniciarComponentes(frmDocente);
            inhabilitarComponentesDocente();

        }
    }

    private void camposNulos() {

        if (frmDocente.getTxtAbreviaturaDocente().getText().equals("")) {
            frmDocente.getBtnGuardar().setEnabled(false);
            frmDocente.getLblAbreviaturaDocente().setVisible(true);
            frmDocente.getLblAbreviaturaDocente().setBackground(Color.red);
            frmDocente.getLblDatoAbreviatura().setForeground(Color.red);
            frmDocente.getLblDatoAbreviatura().setText("Llennar, campo obligatorio");
        } else {
            frmDocente.getLblDatoAbreviatura().setVisible(false);
            frmDocente.getBtnGuardar().setEnabled(true);
        }

    }

    public void campoTitulo() {
        if (frmDocente.getTxtTituloDocente().getText().equals("")) {
            frmDocente.getBtnGuardar().setEnabled(false);
            frmDocente.getLblTituloDocente().setVisible(true);
            frmDocente.getLblDatoTitulo().setForeground(Color.red);
            frmDocente.getLblDatoTitulo().setText("Llennar, campo obligatorio");

        } else {
            frmDocente.getLblDatoTitulo().setVisible(false);
            frmDocente.getBtnGuardar().setEnabled(true);
        }
    }

    public void buscarCedula(String cedula) {
        boolean buscar = true;
        frmDocente.getTxtIdentificacion().setVisible(true);
        //int tipoIdentifi;
        //   cedula = frmDocente.getTxtIdentificacion().getText().trim().toUpperCase();
        // tipoIdentifi = frmDocente.getCmbTipoIdentificacion().getSelectedIndex();
        if (frmDocente.getCmbTipoIdentificacion().getSelectedItem().toString().equals("CEDULA")) {
            if (!Validar.esCedula(cedula)) {
                guardar = false;
                frmDocente.getLblError().setVisible(true);
                frmDocente.getLblError().setForeground(Color.red);
                frmDocente.getLblError().setText("Error! Cedula invalida");
            } else {
                frmDocente.getLblError().setVisible(false);
                //Cedula no registrada: 0102380821
                if (buscar) {
                    d = docente.buscarDocenteInactivo(cedula);
                    if (d != null) {
                        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una Opcion",
                                "Selector de Opciones", JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null,// null para icono por defecto.
                                new Object[]{"Activar Docente", "No Activar"}, "Cancelar");
                        if (seleccion == 1) {
                            inhabilitarComponentesDocente();
                            frmDocente.getBtnRegistrarPersona().setVisible(false);
                        } else if (seleccion == 0) {
                            docente.activarDocente(d.getIdPersona());
                            habilitarComponentesDocente();
                            editar(d);
                        }
                    } else {
                        d = docente.buscarDocente(cedula);
                        if (d == null) {
                            System.out.println("No existe el docente");
                            PersonaMD p = per.buscarPersona(cedula);
                            if (p == null) {
                                JOptionPane.showMessageDialog(null, "No existe la persona");
                                frmDocente.getBtnRegistrarPersona().setVisible(true);
                                this.cedula = frmDocente.getTxtIdentificacion().getText();
                                inhabilitarComponentesDocente();
                                reiniciarComponentes(frmDocente);
                            } else {
                                System.out.println("Existe persona");
                                habilitarComponentesDocente();
                                idDocente = p.getIdPersona();
                                frmDocente.getLblDatosPersona().setText("Nuevo registro: [ " + p.getIdentificacion() + " ] " + p.getNombreCompleto());

                            }
                        } else {
                            System.out.println("Si existe el docente");
                            habilitarComponentesDocente();
                            frmDocente.getBtnRegistrarPersona().setVisible(false);
                            editar(d);
                            frmDocente.getLblDatosPersona().setText("[ " + d.getCodigo() + " ] " + d.getNombreCompleto());
                        }
                    }

                }

            }
        } else {
            //Validar cuando es pasaporte 

            if (buscar) {
                d = docente.buscarDocenteInactivo(cedula);
                if (d != null) {
                    int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una Opcion",
                            "Selector de Opciones", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null,// null para icono por defecto.
                            new Object[]{"Activar Docente", "No Activar"}, "Cancelar");
                    if (seleccion == 1) {
                        inhabilitarComponentesDocente();
                        frmDocente.getBtnRegistrarPersona().setVisible(false);
                    } else if (seleccion == 0) {
                        docente.activarDocente(d.getIdPersona());
                        habilitarComponentesDocente();
                        editar(d);
                    }
                } else {
                    d = docente.buscarDocente(cedula);
                    if (d == null) {
                        System.out.println("No existe el docente");
                        PersonaMD p = per.buscarPersona(cedula);
                        if (p == null) {
                            JOptionPane.showMessageDialog(null, "No existe la persona");
                            frmDocente.getBtnRegistrarPersona().setVisible(true);
                            inhabilitarComponentesDocente();
                            reiniciarComponentes(frmDocente);
                            frmDocente.getLblDatosPersona().setText("");

                        } else {
                            System.out.println("Existe persona");
                            habilitarComponentesDocente();
                            idDocente = p.getIdPersona();

                        }

                    } else {
                        System.out.println("Si existe el docente");
                        habilitarComponentesDocente();
                        frmDocente.getBtnRegistrarPersona().setVisible(false);
                        editar(d);
                        frmDocente.getLblDatosPersona().setText("[ " + d.getIdDocente() + " ] " + d.getPrimerApellido() + " " + d.getSegundoApellido() + " " + d.getPrimerNombre());
                    }
                }

            }

        }

    }

    /*public void validarComponentes(String texto) {
        if (validar == 5) {
            if (modelo.validaciones.Validar.esNumeros(texto) == false && texto.equals("") == false) {
                frmDocente.getLblError().setVisible(true);
                frmDocente.getLblError().setText("Ingrese solo numeros");
            } else {
                frmDocente.getLblError().setVisible(false);
            }
            validar = 0;
        }
    }*/
    public void iniciarComponentes() {
        frmDocente.getLblError().setVisible(false);
        frmDocente.getJdcFechaFinContratacion().setDate(null);
        //setSelectedDate(null);

        //frmAlumno.getBtn_Guardar().setEnabled(false);
    }

    public void guardarDocente() {

        String codigo, docenteTipoTiempo, estado, docenteTitulo, docenteAbreviatura, tipoId;
        int docenteCategoria, idPer;
        boolean docenteOtroTrabajo, docenteCapacitador;
        LocalDate fechaInicioContratacion, fechaFinContratacion;
        guardar = true;

        //  tipoId=frmDocente.getCmbTipoIdentificacion().getSelectedItem().toString();;
        idPer = idDocente;
        System.out.println(idPer);
        codigo = (frmDocente.getTxtIdentificacion().getText());
        docenteCategoria = Integer.parseInt(frmDocente.getSpnCategoria().getValue().toString());
        docenteTipoTiempo = frmDocente.getCmbTipoTiempo().getSelectedItem().toString();
        System.out.println("docente tipo timepo guardar docente " + docenteTipoTiempo);
        if (frmDocente.getCbxDocenteCapacitador().isSelected()) {
            docenteCapacitador = true;
        } else {
            docenteCapacitador = false;
        }
        if (frmDocente.getCbxOtroTrabajo().isSelected()) {
            docenteOtroTrabajo = true;
        } else {
            docenteOtroTrabajo = false;
        }
        frmDocente.getJdcFechaInicioContratacion().setDateFormatString("dd/MM/yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String d = sdf.format(frmDocente.getJdcFechaInicioContratacion().getDate());
        String fecIniC[] = d.split("/");
        LocalDate fechaIni = LocalDate.of(Integer.parseInt(fecIniC[0]),
                Integer.parseInt(fecIniC[1]), Integer.parseInt(fecIniC[2]));
        fechaInicioContratacion = fechaIni;

        try {
            frmDocente.getJdcFechaFinContratacion().setDateFormatString("dd/MM/yyyy");
            SimpleDateFormat fecFin = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            String fechaFin = fecFin.format(frmDocente.getJdcFechaFinContratacion().getDate());
            String fecFinC[] = fechaFin.split("/");
            LocalDate fechaFin1 = LocalDate.of(Integer.parseInt(fecFinC[0]),
                    Integer.parseInt(fecFinC[1]), Integer.parseInt(fecFinC[2]));
            fechaFinContratacion = fechaFin1;
        } catch (NullPointerException e) {

            fechaFinContratacion = null;
        }
        docenteTitulo = frmDocente.getTxtTituloDocente().getText();
        docenteAbreviatura = frmDocente.getTxtAbreviaturaDocente().getText();
        estado = null;
        System.out.println("Se dio click en guardar");
        System.out.println("Guardar = " + guardar);
        System.out.println("Editar = " + editar);

        if (guardar) {
            //Llenar directo por el constructor
            docente.setCodigo(codigo);
            docente.setIdPersona(idPer);
            docente.setFechaInicioContratacion(fechaInicioContratacion);
            docente.setFechaFinContratacion(fechaFinContratacion);
            docente.setEstado(estado);
            docente.setDocenteCapacitador(docenteCapacitador);
            docente.setDocenteCategoria(docenteCategoria);
            docente.setDocenteOtroTrabajo(docenteOtroTrabajo);
            docente.setTituloDocente(docenteTitulo);
            docente.setAbreviaturaDocente(docenteAbreviatura);
            if (!docenteTipoTiempo.equalsIgnoreCase("Seleccione")) {
                docente.setDocenteTipoTiempo(docenteTipoTiempo);
                if (editar) {
                    if (idDocente > 0) {
                        docente.editarDocente(idDocente);
                        JOptionPane.showMessageDialog(null, "Los Datos se han editado exitosamente");
                        botonreporteDocente();
                        frmDocente.dispose();
                    }
                } else {
                    docente.InsertarDocente();
                    JOptionPane.showMessageDialog(null, "Se han insertado los datos de forma correcta!");
                    botonreporteDocente();
                    frmDocente.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una opcion");
            }
        }
    }

    public void editar(DocenteMD doc) {

        // frmDocente.getCmbTipoIdentificacion().setEnabled(false);
        editar = true;
        idDocente = doc.getIdDocente();
        frmDocente.getLblDatosPersona().setText("[ " + doc.getCodigo() + " ] " + doc.getNombreCompleto());

        if (doc.getFechaInicioContratacion() != null) {
            Calendar calendar_Inicio = Calendar.getInstance();
            calendar_Inicio.clear();
            calendar_Inicio.set(doc.getFechaInicioContratacion().getYear(), doc.getFechaInicioContratacion().getMonthValue() - 1, doc.getFechaInicioContratacion().getDayOfMonth());
            frmDocente.getJdcFechaInicioContratacion().setCalendar(calendar_Inicio);
        } else {
            frmDocente.getJdcFechaInicioContratacion().setCalendar(null);
        }

        if (doc.getFechaFinContratacion() != null) {
            Calendar calendar_Fin = Calendar.getInstance();
            calendar_Fin.clear();
            calendar_Fin.set(doc.getFechaFinContratacion().getYear(), doc.getFechaFinContratacion().getMonthValue() - 1, doc.getFechaFinContratacion().getDayOfMonth());
            frmDocente.getJdcFechaFinContratacion().setCalendar(calendar_Fin);
        } else {
            frmDocente.getJdcFechaFinContratacion().setCalendar(null);
        }
        frmDocente.getTxtIdentificacion().setText(doc.getCodigo());
        frmDocente.getSpnCategoria().setValue(doc.getDocenteCategoria());
        frmDocente.getCmbTipoTiempo().setSelectedItem(doc.getDocenteTipoTiempo());
        System.out.println("docente tipo timepo guardar docente " + doc.getDocenteTipoTiempo());
        frmDocente.getCbxDocenteCapacitador().setSelected(doc.isDocenteCapacitador());
        frmDocente.getCbxOtroTrabajo().setSelected(doc.isDocenteOtroTrabajo());
        frmDocente.getTxtTituloDocente().setText(doc.getTituloDocente());
        frmDocente.getTxtAbreviaturaDocente().setText(doc.getAbreviaturaDocente());

    }

    public void reiniciarComponentes(FrmDocente frmDocente) {
        frmDocente.getTxtIdentificacion().setText("");
        frmDocente.getCmbTipoTiempo().setSelectedItem("SELECCIONE");
        frmDocente.getCbxDocenteCapacitador().setSelected(false);
        frmDocente.getCbxOtroTrabajo().setSelected(false);
        frmDocente.getSpnCategoria().setValue(3);
        iniciarFechas();
        frmDocente.getJdcFechaFinContratacion().setCalendar(null);
        frmDocente.getLblDatosPersona().setText("");
        frmDocente.getTxtTituloDocente().setText("");
        frmDocente.getTxtAbreviaturaDocente().setText("");

    }

    public void salirBoton() {
        try {
            frmDocente.setClosed(true);
        } catch (PropertyVetoException ex) {
            System.out.println(ex);
        }
    }

    public void habilitarComponentesDocente() {
        frmDocente.getJdcFechaInicioContratacion().setEnabled(true);
        // frmDocente.getBtnGuardar().setEnabled(true);
        frmDocente.getCbxDocenteCapacitador().setEnabled(true);
        frmDocente.getCmbTipoTiempo().setEnabled(true);
        frmDocente.getSpnCategoria().setEnabled(true);
        frmDocente.getJdcFechaFinContratacion().setEnabled(true);
        frmDocente.getCbxOtroTrabajo().setEnabled(true);
        frmDocente.getTxtTituloDocente().setEnabled(true);
        frmDocente.getTxtAbreviaturaDocente().setEnabled(true);
    }

    public void inhabilitarComponentesDocente() {
        frmDocente.getBtnGuardar().setEnabled(false);
        frmDocente.getCbxDocenteCapacitador().setEnabled(false);
        frmDocente.getCmbTipoTiempo().setEnabled(false);
        frmDocente.getSpnCategoria().setEnabled(false);
        frmDocente.getJdcFechaFinContratacion().setEnabled(false);
        frmDocente.getJdcFechaInicioContratacion().setEnabled(false);
        frmDocente.getCbxOtroTrabajo().setEnabled(false);
        frmDocente.getTxtTituloDocente().setEnabled(false);
        frmDocente.getTxtAbreviaturaDocente().setEnabled(false);

    }

    public void iniciarFechas() {
        LocalDate fechaActual = LocalDate.now();
        Date fechaHoy = Date.from(fechaActual.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        frmDocente.getJdcFechaInicioContratacion().setDate(fechaHoy);

    }

    public void llamaReporteDocente() {
        JasperReport jr;
        String path = "/vista/reportes/repDocentes.jasper";
        try {
            Map parametro = new HashMap();
            parametro.put("cedulaverificacion", frmDocente.getTxtIdentificacion().getText());
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Docente");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(frmDocente, "Error: " + ex);
        }
    }

    public void botonreporteDocente() {
        int s = JOptionPane.showOptionDialog(null,
                "Registro de persona \n"
                + "¿Dessea Imprimir el Registro realizado ?", "REPORTE DOCENTES",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"SI", "NO"}, "NO");
        switch (s) {
            case 0:
                llamaReporteDocente();
                break;
            case 1:

                break;
            default:
                break;
        }
    }
}
