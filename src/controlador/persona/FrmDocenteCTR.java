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

/**
 *
 * @author Johnny
 */
public class FrmDocenteCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmDocente frmDocente;
    private DocenteBD docente;
    private ArrayList<String> info = new ArrayList();

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

    public void persona(FrmPersona frmPersona) {
        FrmPersonaCTR controladorPersona = new FrmPersonaCTR(vtnPrin, persona);
        vtnPrin.getDpnlPrincipal().add(persona);
        controladorPersona.iniciar();
        frmPersona.show();

    }

    public void iniciar() {
        frmDocente.getBtnBuscarPersona().addActionListener(e -> buscarPersona(frmDocente.getTxtIdentificacion().getText()));
        frmDocente.getBtnGuardar().addActionListener(e -> guardarDocente());
    }

    public void guardarDocente() {
        boolean guardar = true;
        if (existeDocente) {
            docente.setIdPersona(Integer.parseInt(info.get(0)));
        } else {
            guardar = false;
        }
        if (guardar) {
            docente.setCodigo(frmDocente.getTxtIdentificacion().getText());
            if (frmDocente.getCbxOtroTrabajo().isSelected()) {
                docente.setDocenteOtroTrabajo(true);
            } else {
                docente.setDocenteOtroTrabajo(false);
            }
            docente.setDocenteCategoria(Integer.parseInt(frmDocente.getSpnCategoria().getValue().toString()));
            String fechaInicio = frmDocente.getJdcFechaInicioContratacion().getText();
            String fecIni[] = fechaInicio.split("/");
            LocalDate fechaIni = LocalDate.of(Integer.parseInt(fecIni[2]),
                    Integer.parseInt(fecIni[1]), Integer.parseInt(fecIni[0]));
            docente.setFechaInicioContratacion(fechaIni);
            String fecFinC[] = fechaInicio.split("/");
            LocalDate fechaFin = LocalDate.of(Integer.parseInt(fecFinC[2]),
                    Integer.parseInt(fecFinC[1]), Integer.parseInt(fecFinC[0]));
            docente.setFechaFinContratacion(fechaFin);
            docente.setDocenteTipoTiempo(frmDocente.getCmbTipoTiempo().getSelectedItem().toString());
            if (frmDocente.getCbxDocenteCapacitador().isSelected()) {
                docente.setDocenteCapacitador(true);
            } else {
                docente.setDocenteCapacitador(false);
            }
            docente.setEstado(null);
            if (docente.InsertarDocente()) {
                JOptionPane.showMessageDialog(null, "Datos guardados correctamente");

            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }

            //  frmDocente.getBtnGuardar().addActionListener(e ->  docente.InsertarDocente());
        }

    }

    public void buscarPersona(String identificacion) {
        try {
            info = docente.buscarPersona(identificacion);
            if (info != null) {
                //Llenamos los datos en el lbl 
                frmDocente.getLblDatosPersona().setForeground(Color.black);
                frmDocente.getLblDatosPersona().setText(info.get(0) + " " + info.get(1));
                frmDocente.getBtnRegistrarPersona().setVisible(false);
                System.out.println("Numero de datos: " + info.size());
            }

            try {
                info = docente.existeDocente(identificacion);
                if (info != null) {
                    frmDocente.getLblDatosPersona().setForeground(Color.red);
                    frmDocente.getLblDatosPersona().setText("Ya se encuentra dentro de la lista de docentes");
                    frmDocente.getBtnRegistrarPersona().setVisible(false);
                    inhabilitarComponentesDocente();
                }

            } catch (Exception w) {
                      try {
                info = docente.buscarPersonaDocente(identificacion);
                if (info != null) {
                    //Llenamos los datos en el lbl 
                    frmDocente.getLblDatosPersona().setText(info.get(0) + " " + info.get(1));
                    System.out.println("Numero de datos: " + info.size());
                    habilitarComponentesDocente();
                    //Indicamos que encontramos al docente  
                    existeDocente = true;
                }

            } catch (Exception a) {
                frmDocente.getLblDatosPersona().setForeground(Color.red);
                frmDocente.getLblDatosPersona().setText("No se encuentra dentro de la lista Docentes");
                frmDocente.getBtnRegistrarPersona().setVisible(false);
                inhabilitarComponentesDocente();

            }

            }
     
        } catch (Exception e) {
            frmDocente.getLblDatosPersona().setForeground(Color.red);
            frmDocente.getLblDatosPersona().setText("No existe");
            frmDocente.getBtnRegistrarPersona().setVisible(true);
            frmDocente.getBtnRegistrarPersona().addActionListener(i -> persona(persona));
            frmDocente.getBtnRegistrarPersona().setVisible(true);
            inhabilitarComponentesDocente();

        }

        /*    try {
            info = docente.buscarPersona(identificacion);
            if (info != null) {
                //Llenamos los datos en el lbl 
                frmDocente.getLblDatosPersona().setForeground(Color.black);
                frmDocente.getLblDatosPersona().setText(info.get(0) + " " + info.get(1));
                frmDocente.getBtnRegistrarPersona().setVisible(false);
                System.out.println("Numero de datos: " + info.size());
            }
            try {
                info = docente.buscarPersonaDocente(identificacion);
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
            frmDocente.getBtnRegistrarPersona().addActionListener(i -> persona(persona));
            frmDocente.getBtnRegistrarPersona().setVisible(true);
            inhabilitarComponentesDocente();

        }
         */
    }

    public void habilitarComponentesDocente() {
        frmDocente.getBtnGuardar().setEnabled(true);
        frmDocente.getCbxDocenteCapacitador().setEnabled(true);
        frmDocente.getCmbTipoTiempo().setEnabled(true);
        frmDocente.getSpnCategoria().setEnabled(true);
        frmDocente.getJdcFechaFinContratacion().setEnabled(true);
        frmDocente.getJdcFechaInicioContratacion().setEnabled(true);
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
