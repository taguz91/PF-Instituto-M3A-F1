//https://serprogramador.es/programando-mensajes-de-dialogo-en-java-parte-1/
//http://codejavu.blogspot.com/2013/12/ejemplo-joptionpane.html
package controlador.persona;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
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
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;

    private ArrayList<String> info = new ArrayList();
    private DocenteBD per;

    //Para verificar si existe la persona tipo docente  
    private boolean existeDocente = false;
    FrmPersona persona = new FrmPersona();

    String codigo, docenteTipoTiempo, estado;
    int docenteCategoria, idDocente;
    boolean docenteOtroTrabajo, docenteCapacitador;
    LocalDate fechaInicioContratacion, fechaFinContratacion;

    public FrmDocenteCTR(VtnPrincipal vtnPrin, FrmDocente frmDocente, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmDocente = frmDocente;
        this.docente = new DocenteBD(conecta);
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;        
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaFrm("Docente");
        
        this.per = new DocenteBD(conecta);
        vtnPrin.getDpnlPrincipal().add(frmDocente);
        frmDocente.show();
    }

    private void abrirFrmPersona() {
        FrmPersona frmPersona = new FrmPersona();
        FrmPersonaCTR ctrFrmPersona = new FrmPersonaCTR(vtnPrin, frmPersona, conecta, ctrPrin);
        ctrFrmPersona.iniciar();
    }

    public void iniciar() {
        frmDocente.getBtnBuscarPersona().addActionListener(e -> buscarPersona());
        frmDocente.getBtnGuardar().addActionListener(e -> guardarDocente());
        //Accion de buscar una persona  
        //frmDocente.getBtnBuscarPersona().addActionListener(e -> consular());
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaFrmFin("Alumno por carrera");
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

        String fechaFin = frmDocente.getJdcFechaFinContratacion().getText().toUpperCase();
        String fecFinC[] = fechaFin.split("/");
        LocalDate fechaFin1 = LocalDate.of(Integer.parseInt(fecFinC[2]),
                Integer.parseInt(fecFinC[1]), Integer.parseInt(fecFinC[0]));
        fechaFinContratacion = fechaFin1;
        estado = null;

        if (guardar) {
            //Llenar directo por el constructor
            //DocenteBD per = new DocenteBD();
            //Pasamos la informacion de la foto 
//           per.setIdPersona((Integer.parseInt(info.get(0))));
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
                } else {
                    //  per.buscarPersona(frmDocente.getTxtIdentificacion().getText());
                    per.InsertarDocente();
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
                }
            } else {

            }
        }

    }

    public void buscarPersona() {
        info = docente.buscarPersonaDocente(frmDocente.getTxtIdentificacion().getText());
       
        if(info!=null){
            existeDocente=true;
            System.out.println("existe docente");
        }else{
            existeDocente=false;
            System.out.println("no existe docente");
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
        editar = true;
        idPersona = doc.getIdPersona();
        System.out.println("Id de la persona que editaremos " + idPersona);

        /* Calendar calendar_Ini = Calendar.getInstance();
        calendar_Ini.clear();
        calendar_Ini.set(doc.getFechaInicioContratacion().getYear(), doc.getFechaInicioContratacion().getMonthValue() , doc.getFechaInicioContratacion().getDayOfMonth());
       
        Calendar calendar_FinC = Calendar.getInstance();
        calendar_FinC.clear();
        calendar_FinC.set(doc.getFechaFinContratacion().getYear(), doc.getFechaFinContratacion().getMonthValue() , doc.getFechaFinContratacion().getDayOfMonth());
        System.out.println(doc.getFechaFinContratacion().getYear());
        System.out.println(doc.getFechaFinContratacion().getMonthValue());
        System.out.println(doc.getFechaFinContratacion().getDayOfMonth());
        
        
        frmDocente.getJdcFechaInicioContratacion().setSelectedDate(calendar_Ini);
       
        frmDocente.getJdcFechaFinContratacion().setSelectedDate(calendar_FinC);*/
        frmDocente.getTxtIdentificacion().setText(doc.getCodigo());
        frmDocente.getSpnCategoria().setValue(doc.getDocenteCategoria());
        frmDocente.getCmbTipoTiempo().setSelectedItem(doc.getDocenteTipoTiempo());
        frmDocente.getCbxDocenteCapacitador().setSelected(doc.isDocenteCapacitador());
        frmDocente.getCbxOtroTrabajo().setSelected(doc.isDocenteOtroTrabajo());
        habilitarComponentesDocente();
        frmDocente.getTxtIdentificacion().setEnabled(false);
        frmDocente.getBtnRegistrarPersona().setVisible(false);
    }

}
