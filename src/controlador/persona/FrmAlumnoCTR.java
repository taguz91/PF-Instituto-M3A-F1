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
import modelo.validaciones.Validar;
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
    private boolean editar_2 = false;
    //Para cargar los sectores economico  
    private SectorEconomicoBD sectorE = new SectorEconomicoBD();

    public FrmAlumnoCTR(VtnPrincipal vtnPrin, FrmAlumno frmAlumno) {
        this.vtnPrin = vtnPrin;
        this.frmAlumno = frmAlumno;

        vtnPrin.getDpnlPrincipal().add(frmAlumno);
        frmAlumno.show();
        bdAlumno = new AlumnoBD();
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
                buscarCedula();
            }
        };

        iniciarSectores();
        iniciarAnios();
        iniciarComponentes();
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

    public void buscarCedula() {
        if (cont == 1) {

            boolean error = false;
            String cedula;
            cedula = frmAlumno.getTxt_Cedula().getText();

            if (modelo.validaciones.Validar.esNumeros(cedula) == false) {
                error = true;
                frmAlumno.getLbl_ErrCedula().setVisible(true);
            }

            if (error == false) {
                System.out.println(cont);
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
                            frmAlumno.getCmBx_NvAcademico().setSelectedItem(alumno.getNivel_Academico());
                            frmAlumno.getTxt_TlSuperior().setText(alumno.getTitulo_Superior());
                            frmAlumno.getTxt_Ocupacion().setText(alumno.getOcupacion());
                            SectorEconomicoMD sector = sectorE.capturarSector(alumno.getId_SecEconomico());
                            frmAlumno.getCmBx_SecEconomico().setSelectedItem(sector.getDescrip_SecEconomico().toUpperCase());
                            frmAlumno.getCmBx_ForPadre().setSelectedItem(alumno.getFormacion_Padre());
                            frmAlumno.getCmBx_ForMadre().setSelectedItem(alumno.getFormacion_Madre());
                            frmAlumno.getTxt_NomContacto().setText(alumno.getNom_Contacto());
                            frmAlumno.getCmBx_Parentesco().setSelectedItem(alumno.getParentesco_Contacto());
                            frmAlumno.getTxt_ConEmergency().setText(alumno.getContacto_Emergencia());
                            frmAlumno.getChkBx_EdcSuperior().setSelected(alumno.isEducacion_Superior());
                            frmAlumno.getChkBx_Pension().setSelected(alumno.isPension());
                            frmAlumno.getChkBx_Trabaja().setSelected(alumno.isTrabaja());
                            cont = 0;
                            editar_2 = true;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese una Cedúla para filtrar los datos");
                    cont = 0;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Advertencia!! Revise su Cédula");
                cont = 0;
                frmAlumno.getLbl_ErrCedula().setVisible(false);
            }
        }
    }

    public void iniciarComponentes() {
        frmAlumno.getLbl_ErrCedula().setVisible(false);
        frmAlumno.getLbl_ErrTipColegio().setVisible(false);
        frmAlumno.getLbl_ErrTipBachillerato().setVisible(false);
        frmAlumno.getLbl_ErrTiSuperior().setVisible(false);
        frmAlumno.getLbl_ErrNvAcademico().setVisible(false);
        frmAlumno.getLbl_ErrOcupacion().setVisible(false);
        frmAlumno.getLbl_ErrSecEconomico().setVisible(false);
        frmAlumno.getLbl_ErrForPadre().setVisible(false);
        frmAlumno.getLbl_ErrForMadre().setVisible(false);
        frmAlumno.getLbl_ErrNomContacto().setVisible(false);
        frmAlumno.getLbl_ErrParentesco().setVisible(false);
        frmAlumno.getLbl_ErrConEmergencia().setVisible(false);
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
            frmAlumno.getCmBx_SecEconomico().addItem(sector.get(i).getDescrip_SecEconomico().toUpperCase());
        }
    }

    public void guardarAlumno() {

        String titulo_Superior, ocupacion, nombre_Contacto, contacto_Emergencia;
        String tipo_Colegio, tipo_Bachillerato, nivel_Academico, sector_Economico, for_Padre, for_Madre, parentesco;
        boolean vacio = false, error = false;

        titulo_Superior = frmAlumno.getTxt_TlSuperior().getText();
        ocupacion = frmAlumno.getTxt_Ocupacion().getText();
        nombre_Contacto = frmAlumno.getTxt_NomContacto().getText();
        contacto_Emergencia = frmAlumno.getTxt_ConEmergency().getText();
        tipo_Colegio = frmAlumno.getCmBx_TipoColegio().getSelectedItem().toString();
        tipo_Bachillerato = frmAlumno.getCmBx_TipoBachillerato().getSelectedItem().toString();
        nivel_Academico = frmAlumno.getCmBx_NvAcademico().getSelectedItem().toString();
        sector_Economico = frmAlumno.getCmBx_SecEconomico().getSelectedItem().toString();
        for_Padre = frmAlumno.getCmBx_ForPadre().getSelectedItem().toString();
        for_Madre = frmAlumno.getCmBx_ForMadre().getSelectedItem().toString();
        parentesco = frmAlumno.getCmBx_Parentesco().getSelectedItem().toString();

        if (modelo.validaciones.Validar.esLetras(titulo_Superior) == false) {
            error = true;
            frmAlumno.getLbl_ErrTiSuperior().setVisible(true);
        } else if (titulo_Superior.equals("")) {
            vacio = true;
            frmAlumno.getLbl_ErrTiSuperior().setText("Ingrese un Título");
            frmAlumno.getLbl_ErrTiSuperior().setVisible(true);
        }

        if (modelo.validaciones.Validar.esLetras(ocupacion) == false) {
            error = true;
            frmAlumno.getLbl_ErrOcupacion().setVisible(true);
        } else if (ocupacion.equals("")) {
            vacio = true;
            frmAlumno.getLbl_ErrOcupacion().setText("Ingrese una Ocupación");
            frmAlumno.getLbl_ErrOcupacion().setVisible(true);
        }

        if (modelo.validaciones.Validar.esLetras(nombre_Contacto) == false) {
            error = true;
            frmAlumno.getLbl_ErrNomContacto().setVisible(true);
        } else if (nombre_Contacto.equals("")) {
            vacio = true;
            frmAlumno.getLbl_ErrNomContacto().setText("Ingrese un Nombre");
            frmAlumno.getLbl_ErrNomContacto().setVisible(true);
        }

        if (modelo.validaciones.Validar.esNumeros(contacto_Emergencia) == false) {
            error = true;
            frmAlumno.getLbl_ErrConEmergencia().setVisible(true);
        } else if (contacto_Emergencia.equals("")) {
            vacio = true;
            frmAlumno.getLbl_ErrConEmergencia().setText("Ingrese un Contacto");
            frmAlumno.getLbl_ErrConEmergencia().setVisible(true);
        }

        if (tipo_Colegio.equals("|SELECCIONE|")) {
            vacio = true;
            frmAlumno.getLbl_ErrTipColegio().setVisible(true);
        } else if (tipo_Bachillerato.equals("|SELECCIONE|")) {
            vacio = true;
            frmAlumno.getLbl_ErrTipBachillerato().setVisible(true);
        } else if (nivel_Academico.equals("|SELECCIONE|")) {
            vacio = true;
            frmAlumno.getLbl_ErrNvAcademico().setVisible(true);
        } else if (sector_Economico.equals("|SELECCIONE|")) {
            vacio = true;
            frmAlumno.getLbl_ErrSecEconomico().setVisible(true);
        } else if (for_Padre.equals("|SELECCIONE|")) {
            vacio = true;
            frmAlumno.getLbl_ErrForPadre().setVisible(true);
        } else if (for_Madre.equals("|SELECCIONE|")) {
            vacio = true;
            frmAlumno.getLbl_ErrForMadre().setVisible(true);
        } else if (parentesco.equals("|SELECCIONE|")) {
            vacio = true;
            frmAlumno.getLbl_ErrParentesco().setVisible(false);
        }

        if (error == true) {
            JOptionPane.showMessageDialog(null, "Advertencia!! Revise los campo/s, valores incorrectos");
            iniciarComponentes();
        } else if (vacio == true) {
            JOptionPane.showMessageDialog(null, "Advertencia!! Falta por introducir dato/s");
            iniciarComponentes();
        } else {
            if (editar == false && editar_2 == false) {
                AlumnoBD persona = new AlumnoBD();
                this.bdAlumno = pasarDatos(persona);
                if (bdAlumno.guardarAlumno(sectorE.capturarIdSector(frmAlumno.getCmBx_SecEconomico().getSelectedItem().toString())) == true) {
                    JOptionPane.showMessageDialog(null, "Datos grabados correctamente");
                    reiniciarComponentes(frmAlumno);
                } else {
                    JOptionPane.showMessageDialog(null, "Error en grabar los datos");
                }
            } else if (editar == true) {
                AlumnoBD persona = new AlumnoBD();
                persona = pasarDatos(bdAlumno);
                if (persona.editarAlumno(persona.capturarPersona(frmAlumno.getTxt_Cedula().getText()).get(0).getIdPersona()) == true) {
                    JOptionPane.showMessageDialog(null, "Datos editados correctamente");
                    reiniciarComponentes(frmAlumno);
                    editar = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Error en editar los datos");
                }
            } else if (editar_2 == true) {
                AlumnoBD persona = new AlumnoBD();
                persona = pasarDatos(bdAlumno);
                if (persona.editarAlumno(persona.capturarPersona(frmAlumno.getTxt_Cedula().getText()).get(0).getIdPersona()) == true) {
                    JOptionPane.showMessageDialog(null, "Datos editados correctamente");
                    reiniciarComponentes(frmAlumno);
                    editar_2 = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Error en editar los datos");
                }
            }
        }
    }

    public void editar(AlumnoMD persona) {
        editar = true;
        //El alumno que nos pasamos lo llenamos en el formulario  
        frmAlumno.getTxt_Cedula().setText(persona.getIdentificacion());
        frmAlumno.getTxt_Nombre().setText(bdAlumno.getPrimerNombre() + " " + bdAlumno.getSegundoNombre()
                + " " + bdAlumno.getPrimerApellido() + " " + bdAlumno.getSegundoApellido());
        frmAlumno.getCmBx_TipoColegio().setSelectedItem(bdAlumno.getTipo_Colegio());
        frmAlumno.getCmBx_TipoBachillerato().setSelectedItem(bdAlumno.getTipo_Bachillerato());
        frmAlumno.getCmBx_NvAcademico().setSelectedItem(bdAlumno.getNivel_Academico());
        frmAlumno.getTxt_TlSuperior().setText(bdAlumno.getTitulo_Superior());
        frmAlumno.getChkBx_EdcSuperior().setSelected(bdAlumno.isEducacion_Superior());
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

    }

    public void reiniciarComponentes(FrmAlumno frmAlumno) {
        frmAlumno.getTxt_Cedula().setText("");
        frmAlumno.getTxt_Nombre().setText("");
        frmAlumno.getCmBx_TipoColegio().setSelectedItem("|SELECCIONE|");
        frmAlumno.getCmBx_TipoBachillerato().setSelectedItem("|SELECCIONE|");
        frmAlumno.getCmBx_NvAcademico().setSelectedItem("|SELECCIONE|");
        frmAlumno.getTxt_TlSuperior().setText("");
        frmAlumno.getChkBx_EdcSuperior().setSelected(false);
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

    public AlumnoBD pasarDatos(AlumnoBD persona) {
        persona.setIdPersona(persona.capturarPersona(frmAlumno.getTxt_Cedula().getText()).get(0).getIdPersona());
        System.out.println("Id: " + persona.getIdPersona());
        persona.setId_SecEconomico(sectorE.capturarIdSector(frmAlumno.getCmBx_SecEconomico().getSelectedItem().toString()).getId_SecEconomico());
        persona.setTipo_Colegio(frmAlumno.getCmBx_TipoColegio().getSelectedItem().toString());
        persona.setTipo_Bachillerato(frmAlumno.getCmBx_TipoBachillerato().getSelectedItem().toString());
        persona.setAnio_graduacion(frmAlumno.getSpnr_Anio().getValue().toString());
        persona.setEducacion_Superior(frmAlumno.getChkBx_EdcSuperior().isSelected());
        persona.setTitulo_Superior(frmAlumno.getTxt_TlSuperior().getText().toUpperCase());
        persona.setNivel_Academico(frmAlumno.getCmBx_NvAcademico().getSelectedItem().toString());
        persona.setPension(frmAlumno.getChkBx_Pension().isSelected());
        persona.setOcupacion(frmAlumno.getTxt_Ocupacion().getText().toUpperCase());
        persona.setTrabaja(frmAlumno.getChkBx_Trabaja().isSelected());
        persona.setFormacion_Padre(frmAlumno.getCmBx_ForPadre().getSelectedItem().toString());
        persona.setFormacion_Madre(frmAlumno.getCmBx_ForMadre().getSelectedItem().toString());
        persona.setNom_Contacto(frmAlumno.getTxt_NomContacto().getText().toUpperCase());
        persona.setParentesco_Contacto(frmAlumno.getCmBx_Parentesco().getSelectedItem().toString());
        persona.setContacto_Emergencia(frmAlumno.getTxt_ConEmergency().getText());
        return persona;
    }

}
