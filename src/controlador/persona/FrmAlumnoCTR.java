package controlador.persona;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;
import modelo.persona.PersonaMD;
import modelo.persona.SectorEconomicoBD;
import modelo.persona.SectorEconomicoMD;
import vista.persona.FrmAlumno;
import vista.persona.VtnAlumno;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmAlumnoCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmAlumno frmAlumno;
    private AlumnoBD bdAlumno;
    private static int cont = 0; // Variable de Acceso para permitir buscar los datos de la persona mediante el evento de Teclado
    private boolean editar = false;
    //Para cargar los sectores economico  
    private SectorEconomicoBD sectorE= new SectorEconomicoBD(); 
        

    public FrmAlumnoCTR(VtnPrincipal vtnPrin, FrmAlumno frmAlumno) {
        this.vtnPrin = vtnPrin;
        this.frmAlumno = frmAlumno;

        vtnPrin.getDpnlPrincipal().add(frmAlumno);
        frmAlumno.show();
    }

    public void iniciar() {
        ActionListener Cancelar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmAlumno.setVisible(false);
            }
        };

        FocusListener Buscar = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cont++;
            }

            @Override
            public void focusLost(FocusEvent e) {
                buscarCedula(cont);
            }
        };

        iniciarSectores();
        iniciarAnios();
        frmAlumno.getTxt_Cedula().addFocusListener(Buscar);
        frmAlumno.getBtn_Buscar().addActionListener(e -> buscarPersona());
        frmAlumno.getBtn_Guardar().addActionListener(e -> guardarAlumno());
        frmAlumno.getBtn_Cancelar().addActionListener(Cancelar);
    }

    public void buscarPersona() {
        VtnAlumno alumno = new VtnAlumno();
        VtnAlumnoCTR c = new VtnAlumnoCTR(vtnPrin, alumno);
        c.iniciar();
    }

    public void buscarCedula(int cont) {
        if (cont == 1) {
            if (frmAlumno.getTxt_Cedula().getText().length() >= 3) {
                List<PersonaMD> p = bdAlumno.capturarPersona(frmAlumno.getTxt_Cedula().getText());
                if (p.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Cedula Incorrecta");
                    cont = 0;
                } else {
                    AlumnoMD alumno = bdAlumno.buscarPersona(p.get(0).getIdPersona());
                    if (alumno.getTipo_Colegio() == null) {
                        frmAlumno.getTxt_Nombre().setText(alumno.getPrimerNombre() + " " + alumno.getSegundoNombre()
                                + " " + alumno.getPrimerApellido() + " " + alumno.getSegundoApellido());
                        cont = 0;
                        JOptionPane.showMessageDialog(null, "Esta persona no esta registrada como alumno");
                    } else {
                        frmAlumno.getTxt_Nombre().setText(alumno.getPrimerNombre() + " " + alumno.getSegundoNombre()
                                + " " + alumno.getPrimerApellido() + " " + alumno.getSegundoApellido());
                        frmAlumno.getCmBx_TipoColegio().setSelectedItem(alumno.getTipo_Colegio());
                        frmAlumno.getCmBx_TipoBachillerato().setSelectedItem(alumno.getTipo_Bachillerato());
                        frmAlumno.getSpnr_Anio().setValue(Integer.valueOf(alumno.getAnio_graduacion()));
                        frmAlumno.getCmBx_Modalidad().setSelectedItem(alumno.getModalidad());
                        frmAlumno.getTxt_TlSuperior().setText(alumno.getTitulo_Superior());
                        frmAlumno.getTxt_Ocupacion().setText(alumno.getOcupacion());
                        frmAlumno.getCmBx_SecEconomico().setSelectedItem(sectorE.capturarSector(alumno.getId_SecEconomico()).getDescrip_SecEconomico());
                        frmAlumno.getCmBx_ForPadre().setSelectedItem(alumno.getFormacion_Padre());
                        frmAlumno.getCmBx_ForMadre().setSelectedItem(alumno.getFormacion_Madre());
                        frmAlumno.getTxt_NomContacto().setText(alumno.getNom_Contacto());
                        frmAlumno.getCmBx_Parentesco().setSelectedItem(alumno.getParentesco_Contacto());
                        frmAlumno.getTxt_ConEmergency().setText(alumno.getContacto_Emergencia());
                        frmAlumno.getChkBx_EdcSuperior().setSelected(alumno.isEducacion_Superior());
                        frmAlumno.getChkBx_Pension().setSelected(alumno.isPension());
                        frmAlumno.getChkBx_Trabaja().setSelected(alumno.isTrabaja());
                        cont = 0;
                    }
                }
            } else {
                cont = 0;
            }
        }
    }

    public void iniciarAnios() {
        SpinnerNumberModel s = new SpinnerNumberModel();
        s.setMinimum(1980);
        s.setMaximum(2018);
        s.setStepSize(1);
        frmAlumno.getSpnr_Anio().setModel(s);
        frmAlumno.getSpnr_Anio().setValue(1980);
    }

    public void iniciarSectores() {
        List<SectorEconomicoMD> sector = sectorE.capturarSectores();
        for (int i = 0; i < sector.size(); i++) {
            frmAlumno.getCmBx_SecEconomico().addItem(sector.get(i).getDescrip_SecEconomico());
        }
        frmAlumno.getCmBx_SecEconomico().addItem("Ninguno");
    }

    public void guardarAlumno() {
        if (editar == false) {
            AlumnoBD persona = new AlumnoBD();
            this.bdAlumno = pasarDatos(persona, frmAlumno);
            if (bdAlumno.guardarAlumno(sectorE.capturarIdSector(frmAlumno.getCmBx_SecEconomico().getSelectedItem().toString())) == true) {
                JOptionPane.showMessageDialog(null, "Datos grabados correctamente");
                reiniciarComponentes(frmAlumno);
            } else {
                JOptionPane.showMessageDialog(null, "Error en grabar los datos");
            }
        } else {
            AlumnoBD persona = new AlumnoBD();
            frmAlumno.getTxt_Cedula().setText(bdAlumno.getIdentificacion());
            frmAlumno.getTxt_Nombre().setText(bdAlumno.getPrimerNombre() + " " + bdAlumno.getSegundoNombre()
                    + " " + bdAlumno.getPrimerApellido() + " " + bdAlumno.getSegundoApellido());
            frmAlumno.getCmBx_TipoColegio().setSelectedItem(bdAlumno.getTipo_Colegio());
            frmAlumno.getCmBx_TipoBachillerato().setSelectedItem(bdAlumno.getTipo_Bachillerato());
            frmAlumno.getCmBx_NvAcademico().setSelectedItem(bdAlumno.getNivel_Academico());
            frmAlumno.getTxt_TlSuperior().setText(bdAlumno.getTitulo_Superior());
            frmAlumno.getChkBx_EdcSuperior().setSelected(bdAlumno.isEducacion_Superior());
            frmAlumno.getCmBx_Modalidad().setSelectedItem(bdAlumno.getModalidad());
            frmAlumno.getTxt_Ocupacion().setText(bdAlumno.getOcupacion());
            frmAlumno.getSpnr_Anio().setValue(Integer.valueOf(bdAlumno.getAnio_graduacion()));
            frmAlumno.getChkBx_Pension().setSelected(bdAlumno.isPension());
            frmAlumno.getChkBx_Trabaja().setSelected(bdAlumno.isTrabaja());
            frmAlumno.getCmBx_SecEconomico().setSelectedItem(sectorE.capturarSector(bdAlumno.getId_SecEconomico()).getDescrip_SecEconomico());
            frmAlumno.getCmBx_ForPadre().setSelectedItem(bdAlumno.getFormacion_Padre());
            frmAlumno.getCmBx_ForMadre().setSelectedItem(bdAlumno.getFormacion_Madre());
            frmAlumno.getTxt_NomContacto().setText(bdAlumno.getNom_Contacto());
            frmAlumno.getCmBx_Parentesco().setSelectedItem(bdAlumno.getParentesco_Contacto());
            frmAlumno.getTxt_ConEmergency().setText(bdAlumno.getContacto_Emergencia());
            persona = pasarDatos(bdAlumno, frmAlumno);
            if (persona.editarAlumno(persona.capturarPersona(frmAlumno.getTxt_Cedula().getText()).get(0).getIdPersona()) == true) {
                JOptionPane.showMessageDialog(null, "Datos editados correctamente");
                reiniciarComponentes(frmAlumno);
                editar = false;
            } else {
                JOptionPane.showMessageDialog(null, "Error en editar los datos");
            }
        }
    }

    public void editar(AlumnoBD bdAlumno) {
        editar = true;
        this.bdAlumno = bdAlumno;
    }

    public void reiniciarComponentes(FrmAlumno frmAlumno) {
        frmAlumno.getTxt_Cedula().setText("");
        frmAlumno.getTxt_Nombre().setText("");
        frmAlumno.getCmBx_TipoColegio().setSelectedItem("|SELECCIONE|");
        frmAlumno.getCmBx_TipoBachillerato().setSelectedItem("|SELECCIONE|");
        frmAlumno.getCmBx_NvAcademico().setSelectedItem("|SELECCIONE|");
        frmAlumno.getTxt_TlSuperior().setText("");
        frmAlumno.getChkBx_EdcSuperior().setSelected(false);
        frmAlumno.getCmBx_Modalidad().setSelectedItem("|SELECCIONE|");
        frmAlumno.getTxt_Ocupacion().setText("");
        frmAlumno.getChkBx_Pension().setSelected(false);
        frmAlumno.getSpnr_Anio().setValue(1980); //Valor no predefinido
        frmAlumno.getChkBx_Trabaja().setSelected(false);
        frmAlumno.getCmBx_SecEconomico().setSelectedItem("|SELECCIONE|");
        frmAlumno.getCmBx_ForPadre().setSelectedItem("|SELECCIONE|");
        frmAlumno.getCmBx_ForMadre().setSelectedItem("|SELECCIONE|");
        frmAlumno.getTxt_NomContacto().setText("");
        frmAlumno.getCmBx_Parentesco().setSelectedItem("|SELECCIONE|");
        frmAlumno.getTxt_ConEmergency().setText("");
    }

    public AlumnoBD pasarDatos(AlumnoBD persona, FrmAlumno vista){
        persona.setIdPersona(persona.capturarPersona(vista.getTxt_Cedula().getText()).get(0).getIdPersona());
        System.out.println(persona.getId_Alumno());
        persona.setTipo_Colegio(vista.getCmBx_TipoColegio().getSelectedItem().toString());
        persona.setTipo_Bachillerato(vista.getCmBx_TipoBachillerato().getSelectedItem().toString());
        persona.setAnio_graduacion(vista.getSpnr_Anio().getValue().toString());
        persona.setEducacion_Superior(vista.getChkBx_EdcSuperior().isSelected());
        persona.setTitulo_Superior(vista.getTxt_TlSuperior().getText().toUpperCase());
        persona.setNivel_Academico(vista.getCmBx_NvAcademico().getSelectedItem().toString());
        persona.setPension(vista.getChkBx_Pension().isSelected());
        persona.setOcupacion(vista.getTxt_Ocupacion().getText().toUpperCase());
        persona.setTrabaja(vista.getChkBx_Trabaja().isSelected());
        persona.setFormacion_Padre(vista.getCmBx_ForPadre().getSelectedItem().toString());
        persona.setFormacion_Madre(vista.getCmBx_ForMadre().getSelectedItem().toString());
        persona.setNom_Contacto(vista.getTxt_NomContacto().getText().toUpperCase());
        persona.setParentesco_Contacto(vista.getCmBx_Parentesco().getSelectedItem().toString());
        persona.setContacto_Emergencia(vista.getTxt_ConEmergency().getText());
        return persona;
    }
    
}
