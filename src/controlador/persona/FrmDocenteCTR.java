//https://serprogramador.es/programando-mensajes-de-dialogo-en-java-parte-1/
//http://codejavu.blogspot.com/2013/12/ejemplo-joptionpane.html
package controlador.persona;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import vista.persona.FrmDocente;
import vista.persona.FrmPersona;

import vista.principal.VtnPrincipal;

public class FrmDocenteCTR {

    //Para saber si se esta editando una persona  
    private boolean editar = false;
    private int idPersona = 0;
    private final VtnPrincipal vtnPrin;
    private VtnDocenteCTR docenteVtn;
    private final FrmDocente frmDocente;
    private final DocenteBD docente;
    private boolean guardar = false;
    private ArrayList<String> info = new ArrayList();
    private DocenteBD per = new DocenteBD();

    //Para verificar si existe la persona tipo docente  
    private boolean existeDocente = false;
    FrmPersona persona = new FrmPersona();

    public FrmDocenteCTR(VtnPrincipal vtnPrin, FrmDocente frmDocente, DocenteBD docente) {
        this.vtnPrin = vtnPrin;
        this.frmDocente = frmDocente;
        this.docente = docente;
        vtnPrin.getDpnlPrincipal().add(frmDocente);
        frmDocente.show();
    }

    public FrmDocenteCTR(VtnPrincipal vtnPrin, FrmDocente frmDocente) {
        this.vtnPrin = vtnPrin;
        this.frmDocente = frmDocente;
        //Inicializamos persona
        this.docente = new DocenteBD();
        vtnPrin.getDpnlPrincipal().add(frmDocente);
        frmDocente.show();
    }

    private void abrirFrmPersona() {
        FrmPersona frmPersona = new FrmPersona();
        FrmPersonaCTR ctrFrmPersona = new FrmPersonaCTR(vtnPrin, frmPersona);
        ctrFrmPersona.iniciar();
    }

    public void iniciar() {
        frmDocente.getBtnBuscarPersona().addActionListener(e -> buscarPersona());
        frmDocente.getBtnGuardar().addActionListener(e -> guardarDocente());
        //Accion de buscar una persona  
        //frmDocente.getBtnBuscarPersona().addActionListener(e -> consular());
    }

    public void guardarDocente() {

        if (existeDocente) {
            per.setIdPersona(Integer.parseInt(info.get(0)));
            //docente.setIdPersona(idPersona);
            System.out.println(info + "soy info{0} de guardar Docente en FRMDOCNETE");
        } else {
           // DocenteMD per = docente.buscarDocente(frmDocente.getTxtIdentificacion().getText());
            //editar(per);
            guardar = false;
        }
        guardar = true;
        String codigo, docenteTipoTiempo, estado;
        int docenteCategoria, idDocente;
        boolean docenteOtroTrabajo, docenteCapacitador;
        LocalDate fechaInicioContratacion, fechaFinContratacion;

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
        String fechaInicio = frmDocente.getJdcFechaInicioContratacion().getText().toUpperCase();
        String fecIni[] = fechaInicio.split("/");
        LocalDate fechaIni = LocalDate.of(Integer.parseInt(fecIni[2]),
                Integer.parseInt(fecIni[1]), Integer.parseInt(fecIni[0]));
        fechaInicioContratacion = fechaIni;
        String fechaFin = frmDocente.getJdcFechaFinContratacion().getText();
        String fecFinC[] = fechaFin.split("/");
        LocalDate fechaFin1 = LocalDate.of(Integer.parseInt(fecFinC[2]),
                Integer.parseInt(fecFinC[1]), Integer.parseInt(fecFinC[0]));
        fechaFinContratacion = fechaFin1;
        estado = null;

        if (guardar) {
            //Llenar directo por el constructor
            // DocenteBD per = new DocenteBD();
            //Pasamos la informacion de la foto 
//            per.setIdPersona((Integer.parseInt(info.get(0))));
            per.setCodigo(codigo);
            per.setFechaInicioContratacion(fechaInicioContratacion);
            per.setFechaFinContratacion(fechaFinContratacion);
            per.setEstado(estado);
            per.setDocenteCapacitador(docenteCapacitador);
            per.setDocenteCategoria(docenteCategoria);
            per.setDocenteOtroTrabajo(docenteOtroTrabajo);
            per.setDocenteTipoTiempo(docenteTipoTiempo);

            if (editar) {
                if (idPersona > 0) {
                    per.editarDocente(idPersona);
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
                }
            } else {
                //  per.buscarPersona(frmDocente.getTxtIdentificacion().getText());
                per.InsertarDocente();
                JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
            }
        }

    }

    public void buscarPersona() {
        String identificacion = frmDocente.getTxtIdentificacion().getText();
        info = docente.buscarPersona(identificacion);
        try {
            if (info != null) {
                //Llenamos los datos en el lbl 
                frmDocente.getLblDatosPersona().setForeground(Color.black);
                frmDocente.getLblDatosPersona().setText(info.get(0) + " " + info.get(1));
                frmDocente.getBtnRegistrarPersona().setVisible(false);
                System.out.println("Numero de datos: " + info.size());
            }
            info = docente.buscarPersonaDocente(identificacion);
            try {
                if (info != null) {
                    //Llenamos los datos en el lbl 
                    frmDocente.getLblDatosPersona().setText(info.get(0) + " " + info.get(1));
                    System.out.println("Numero de datos: " + info.size());
                    habilitarComponentesDocente();
                    //Indicamos que encontramos al docente  
                    existeDocente = true;
                }

            } catch (Exception w) {
                frmDocente.getLblDatosPersona().setForeground(Color.red);
                frmDocente.getLblDatosPersona().setText("No se encuentra dentro de la lista Docentes");
                frmDocente.getBtnRegistrarPersona().setVisible(false);
                inhabilitarComponentesDocente();

            }
        } catch (Exception e) {
            frmDocente.getLblDatosPersona().setForeground(Color.red);
            frmDocente.getLblDatosPersona().setText("No existe");
            frmDocente.getBtnRegistrarPersona().setVisible(true);
            frmDocente.getBtnRegistrarPersona().addActionListener(i -> abrirFrmPersona());
            frmDocente.getBtnRegistrarPersona().setVisible(true);
            inhabilitarComponentesDocente();

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

    public void editar(DocenteMD doc) {
       // existeDocente = true;
        editar = true;
        idPersona = doc.getIdPersona();
        System.out.println("Id de la persona que editaremos " + idPersona);
//        System.out.println(doc.getFechaInicioContratacion().format(DateTimeFormatter.ISO_DATE));
//        System.out.println(doc.getFechaInicioContratacion().getDayOfMonth());
        //  frmDocente.getJdcFechaFinContratacion().setText(doc.getFechaInicioContratacion().format(DateTimeFormatter.ISO_DATE));
        frmDocente.getTxtIdentificacion().setText(doc.getCodigo());
        frmDocente.getSpnCategoria().setValue(doc.getDocenteCategoria());
        frmDocente.getCmbTipoTiempo().setSelectedItem(doc.getDocenteTipoTiempo());
        frmDocente.getCbxDocenteCapacitador().setSelected(doc.isDocenteCapacitador());
        frmDocente.getCbxOtroTrabajo().setSelected(doc.isDocenteOtroTrabajo());
        habilitarComponentesDocente();
        frmDocente.getTxtIdentificacion().setEnabled(false);
        frmDocente.getBtnRegistrarPersona().setVisible(false);
    }

    private void consular() {
        String identificacion = frmDocente.getTxtIdentificacion().getText();
        //  buscarPersona();
        DocenteMD per = docente.buscarDocente(identificacion);
        if (per != null) {
            editar(per);
        }
    }
}
