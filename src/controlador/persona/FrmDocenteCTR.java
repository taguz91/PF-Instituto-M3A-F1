package controlador.persona;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.util.Calendar;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.validaciones.TxtVCedula;
import modelo.validaciones.Validar;
import vista.persona.FrmDocente;
import vista.persona.FrmPersona;

import vista.principal.VtnPrincipal;

public class FrmDocenteCTR {

    //Para saber si se esta editando una persona  
    private boolean editar = false;
    private int idDocente = 0;
    private int idPersona = 0;
    private final VtnPrincipal vtnPrin;
    private VtnDocenteCTR docenteVtn;
    private final FrmDocente frmDocente;
    private DocenteBD docente;
    private boolean guardar = false;
    private ConectarDB conecta;
    private static int cont = 0; // Variable de Acceso para permitir buscar los datos de la persona mediante el evento de Teclado
    private static int validar = 0; //Variable para saber a que textFiel se valida
    //Para ver si existe una persona  
    private PersonaBD per;
    private final VtnPrincipalCTR ctrPrin;
   private DocenteMD d;

    private ArrayList<String> info = new ArrayList();

    // private DocenteBD per;
    //Para verificar si existe la persona tipo docente  
    private boolean existeDocente = false;
    FrmPersona persona = new FrmPersona();
 

    public FrmDocenteCTR(VtnPrincipal vtnPrin, FrmDocente frmDocente, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmDocente = frmDocente;
        this.conecta = conecta;
        this.docente = new DocenteBD(conecta);
        this.per = new PersonaBD(conecta);

        this.ctrPrin = ctrPrin;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaFrm("Docente");
        ctrPrin.setIconJIFrame(frmDocente);

        vtnPrin.getDpnlPrincipal().add(frmDocente);
        frmDocente.show();
    }

    FrmDocenteCTR(VtnPrincipal vtnPrin, FrmPersona frmPersona, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void abrirFrmPersona() {
        FrmPersona frmPersona = new FrmPersona();
        FrmPersonaCTR ctrFrmPersona = new FrmPersonaCTR(vtnPrin, frmPersona, conecta, ctrPrin);
        ctrFrmPersona.iniciar();
    }

    public void iniciar() {
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
                buscarCedula();
            }
        };

        KeyListener cedula = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                validar = 5;
                validarComponentes(frmDocente.getTxtIdentificacion().getText());
            }
        };

        iniciarComponentes();
        frmDocente.getTxtIdentificacion().addFocusListener(Buscar);
        frmDocente.getTxtIdentificacion().addKeyListener(cedula);
        frmDocente.getBtnBuscarPersona().addActionListener(e -> buscarCedula());
        frmDocente.getBtnGuardar().addActionListener(e -> guardarDocente());
        frmDocente.getBtnRegistrarPersona().addActionListener(e -> abrirFrmPersona());
        //Validacion de la cedula
        frmDocente.getTxtIdentificacion().addKeyListener(new TxtVCedula(frmDocente.getTxtIdentificacion(),
                frmDocente.getLblError()));

        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaFrmFin("Alumno por carrera");
    }

    public void buscarCedula() {
        boolean buscar = true;
        String cedula = frmDocente.getTxtIdentificacion().getText().trim();
        if (!Validar.esCedula(cedula)) {
            buscar = false;
        }
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

                    } else {
                        System.out.println("Existe persona");
                        habilitarComponentesDocente();
                        idDocente = p.getIdPersona();

                    }

                } else {
                    System.out.println("Si existe el docente");
                    habilitarComponentesDocente();
                    editar(d);
                }
            }

        }
    }

    public void validarComponentes(String texto) {
        if (validar == 5) {
            if (modelo.validaciones.Validar.esNumeros(texto) == false && texto.equals("") == false) {
                frmDocente.getLblError().setVisible(true);
            } else {
                frmDocente.getLblError().setVisible(false);
            }
            validar = 0;
        }
    }

    public void iniciarComponentes() {
        frmDocente.getLblError().setVisible(false);
        frmDocente.getJdcFechaFinContratacion().setDate(null);
        //setSelectedDate(null);

        //frmAlumno.getBtn_Guardar().setEnabled(false);
    }

    public void guardarDocente() {

        String codigo, docenteTipoTiempo, estado;
        int docenteCategoria,idPer;
        boolean docenteOtroTrabajo, docenteCapacitador;
        LocalDate fechaInicioContratacion, fechaFinContratacion;
        guardar = true;
       idPer=idDocente;
       System.out.println(idPer);
        codigo = (frmDocente.getTxtIdentificacion().getText());
        docenteCategoria = Integer.parseInt(frmDocente.getSpnCategoria().getValue().toString());
        docenteTipoTiempo = frmDocente.getCmbTipoTiempo().getSelectedItem().toString();
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
            docente.setDocenteTipoTiempo(docenteTipoTiempo);
            if (editar) {
                if (idDocente > 0) {
                    docente.editarDocente(idDocente);
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
                }
            } else {
                docente.InsertarDocente();
                JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
            }
        }

    }

    public void editar(DocenteMD doc) {
        editar = true;
        idDocente = doc.getIdDocente();
        Calendar calendar_Ini = Calendar.getInstance();
        calendar_Ini.clear();
        calendar_Ini.set(doc.getFechaInicioContratacion().getYear(), doc.getFechaInicioContratacion().getMonthValue(), doc.getFechaInicioContratacion().getDayOfMonth());
        Calendar calendar_FinC = Calendar.getInstance();
        calendar_FinC.clear();
        if (doc.getFechaFinContratacion() != null) {
            calendar_FinC.set(doc.getFechaFinContratacion().getYear(), doc.getFechaFinContratacion().getMonthValue(), doc.getFechaFinContratacion().getDayOfMonth());
            frmDocente.getJdcFechaFinContratacion().setCalendar(calendar_Ini);
        } else {
            frmDocente.getJdcFechaFinContratacion().setCalendar(null);
        }
        frmDocente.getJdcFechaInicioContratacion().setCalendar(calendar_Ini);
        frmDocente.getTxtIdentificacion().setText(doc.getCodigo());
        frmDocente.getSpnCategoria().setValue(doc.getDocenteCategoria());
        frmDocente.getCmbTipoTiempo().setSelectedItem(doc.getDocenteTipoTiempo());
        frmDocente.getCbxDocenteCapacitador().setSelected(doc.isDocenteCapacitador());
        frmDocente.getCbxOtroTrabajo().setSelected(doc.isDocenteOtroTrabajo());

    }

    public void reiniciarComponentes(FrmDocente frmDocente) {
        frmDocente.getTxtIdentificacion().setText("");
        frmDocente.getCmbTipoTiempo().setSelectedItem("SELECCIONE");
        frmDocente.getCbxDocenteCapacitador().setSelected(false);
        frmDocente.getCbxOtroTrabajo().setSelected(false);
        frmDocente.getSpnCategoria().setValue(3);

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
        frmDocente.getBtnGuardar().setEnabled(true);
        frmDocente.getCbxDocenteCapacitador().setEnabled(true);
        frmDocente.getCmbTipoTiempo().setEnabled(true);
        frmDocente.getSpnCategoria().setEnabled(true);
        frmDocente.getJdcFechaFinContratacion().setEnabled(true);
        frmDocente.getCbxOtroTrabajo().setEnabled(true);
    }

    public void inhabilitarComponentesDocente() {
        frmDocente.getBtnGuardar().setEnabled(false);
        frmDocente.getCbxDocenteCapacitador().setEnabled(false);
        frmDocente.getCmbTipoTiempo().setEnabled(false);
        frmDocente.getSpnCategoria().setEnabled(false);
        frmDocente.getJdcFechaFinContratacion().setEnabled(false);
        frmDocente.getJdcFechaInicioContratacion().setEnabled(false);
        frmDocente.getCbxOtroTrabajo().setEnabled(false);

    }
}
