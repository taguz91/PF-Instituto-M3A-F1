package controlador.persona;

import controlador.carrera.VtnCarreraCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import modelo.ConectarDB;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;
import modelo.persona.PersonaMD;
import modelo.persona.SectorEconomicoBD;
import modelo.persona.SectorEconomicoMD;
import modelo.usuario.RolMD;
import modelo.validaciones.CmbValidar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.persona.FrmAlumno;
import vista.persona.FrmPersona;
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
    private final VtnPrincipalCTR ctrPrin;
    private final ConectarDB conecta;
    private final RolMD permisos;
    private static int cont = 0; // Variable de Acceso para permitir buscar los datos de la persona mediante el evento de Teclado
    private boolean editar = false; //Variable de permiso para editar mediante el filtro de datos del evento de teclado
    private boolean editar_2 = false; //Variable de permiso de edicion cuando se filtran los datos de la Ventana de Alumno
    private static int validar = 0; //Variable para saber a que textFiel se valida
    private boolean identificacion = false; //Variable para saber si es una Cédula o un Pasaporte
    private List<AlumnoMD> Alumnos = new ArrayList();
    private List<SectorEconomicoMD> Sectores = new ArrayList();

    private final SectorEconomicoBD sectorE;

    //Para cargar los sectores economico  
    /**
     * Este es el Constructor del Formulario Alumno
     *
     * @param vtnPrin. Se debe insertar la Ventana Principal para su uso
     * @param frmAlumno. Se debe insertar el Formulario del Alumno para su uso
     * @param conecta. Se debe insertar un objeto de la Clase Conecta
     * @param ctrPrin
     * @param permisos
     */
    public FrmAlumnoCTR(VtnPrincipal vtnPrin, FrmAlumno frmAlumno, ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.frmAlumno = frmAlumno;
        this.conecta = conecta;
        this.sectorE = new SectorEconomicoBD(conecta);
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaFrm("Alumno");
        ctrPrin.setIconJIFrame(frmAlumno);
        vtnPrin.getDpnlPrincipal().add(frmAlumno);
        frmAlumno.show();
        bdAlumno = new AlumnoBD(conecta);
    }

    public void iniciar() {
        //Validaciones de los Combo Box
        CmbValidar combo_TipoColegio = new CmbValidar(frmAlumno.getCmBx_TipoColegio(), frmAlumno.getLbl_ErrTipColegio());
        CmbValidar combo_TipoBachi = new CmbValidar(frmAlumno.getCmBx_TipoBachillerato(), frmAlumno.getLbl_ErrTipBachillerato());
        CmbValidar combo_NivAcade = new CmbValidar(frmAlumno.getCmBx_NvAcademico(), frmAlumno.getLbl_ErrNvAcademico());
        CmbValidar combo_SectEcono = new CmbValidar(frmAlumno.getCmBx_SecEconomico(), frmAlumno.getLbl_ErrSecEconomico());
        CmbValidar combo_ForPadre = new CmbValidar(frmAlumno.getCmBx_ForPadre(), frmAlumno.getLbl_ErrForPadre());
        CmbValidar combo_ForMadre = new CmbValidar(frmAlumno.getCmBx_ForMadre(), frmAlumno.getLbl_ErrForMadre());
        CmbValidar combo_Parentesco = new CmbValidar(frmAlumno.getCmBx_Parentesco(), frmAlumno.getLbl_ErrParentesco());

        ActionListener Cancelar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmAlumno.dispose();
            }
        };

        FocusListener Buscar = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cont++;
            }

            @Override
            public void focusLost(FocusEvent e) {
                String cedula = frmAlumno.getTxt_Cedula().getText();
                if (identificacion == true) {
                    if (!cedula.equals("")) {
                        if (cedula.length() == 10) {
                            if (modelo.validaciones.Validar.esCedula(cedula) == false) {
                                frmAlumno.getLbl_ErrCedula().setText("Ingrese una cédula válida");
                                frmAlumno.getLbl_ErrCedula().setVisible(true);
                                reiniciarComponentes(frmAlumno);
                                cont = 0;
                            } else {
                                frmAlumno.getLbl_ErrCedula().setVisible(false);
                                buscarCedula();
                            }
                        } else if (cedula.length() < 10) {
                            frmAlumno.getLbl_ErrCedula().setText("La cédula lleva 10 números");
                            frmAlumno.getLbl_ErrCedula().setVisible(true);
                            reiniciarComponentes(frmAlumno);
                            cont = 0;
                        }
                    } else {
                        frmAlumno.getLbl_ErrCedula().setVisible(false);
                        cont = 0;
                    }
                } else {
                    if (!cedula.equals("")) {
                        if (modelo.validaciones.Validar.esLetrasYNumeros(cedula) == true) {
                            frmAlumno.getLbl_ErrCedula().setVisible(false);
                            buscarCedula();
                        }
                    } else {
                        frmAlumno.getLbl_ErrCedula().setText("El pasaporte contiene solo letras y números");
                        frmAlumno.getLbl_ErrCedula().setVisible(true);
                        cont = 0;
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

        KeyListener titulo_Superior = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                validar = 1;
                validarComponentes(frmAlumno.getTxt_TlSuperior().getText());
                habilitarGuardar();
            }
        };

        KeyListener ocupacion = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                validar = 2;
                validarComponentes(frmAlumno.getTxt_Ocupacion().getText());
                habilitarGuardar();
            }
        };

        KeyListener nombre_Contacto = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                validar = 3;
                validarComponentes(frmAlumno.getTxt_NomContacto().getText());
                habilitarGuardar();
            }
        };

        KeyListener num_Contacto = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                validar = 4;
                validarComponentes(frmAlumno.getTxt_ConEmergency().getText());
                habilitarGuardar();
            }
        };

        KeyListener cedula = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (identificacion == true) {
                    String cedula = frmAlumno.getTxt_Cedula().getText();
                    char car = e.getKeyChar();
                    if (car < '0' || car > '9') {
                        e.consume();
                    }
                    if (cedula.length() >= 10) {
                        e.consume();
                    }
                    habilitarGuardar();
                } else {
                    String cedula = frmAlumno.getTxt_Cedula().getText();
                    char car = e.getKeyChar();
                    if (modelo.validaciones.Validar.esLetrasYNumeros(car + "") == false) {
                        e.consume();
                    }
                    habilitarGuardar();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        frmAlumno.getCbx_Identificacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (frmAlumno.getCbx_Identificacion().getSelectedItem().toString().equals("CÉDULA")) {
                    identificacion = true;
                    frmAlumno.getTxt_Cedula().setEnabled(true);
                    frmAlumno.getCbx_Identificacion().setBorder(
                            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
                } else if (frmAlumno.getCbx_Identificacion().getSelectedItem().toString().equals("PASAPORTE")) {
                    identificacion = false;
                    frmAlumno.getTxt_Cedula().setEnabled(true);
                    frmAlumno.getCbx_Identificacion().setBorder(
                            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
                } else {
                    frmAlumno.getCbx_Identificacion().setBorder(
                            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
                    frmAlumno.getTxt_Cedula().setEnabled(false);
                }
            }
        });

        iniciaDatos(); //Captura los datos en Listas para su utilizacion
        habilitarGuardar(); //Compara si es que lo componentes estan llenos para habilitar el Boton Guardar
        iniciarSectores(); //Inserta los sectores existenten en el Combo Box
        iniciarAnios(); //Se da un rango de años para insertar en el Formulario
        iniciarComponentes();
        //Se añaden los eventos a los componentes
        frmAlumno.getCmBx_TipoColegio().addActionListener(combo_TipoColegio);
        frmAlumno.getCmBx_TipoColegio().addPropertyChangeListener(habilitar_Guardar);
        frmAlumno.getCmBx_TipoBachillerato().addActionListener(combo_TipoBachi);
        frmAlumno.getCmBx_TipoBachillerato().addPropertyChangeListener(habilitar_Guardar);
        frmAlumno.getCmBx_NvAcademico().addActionListener(combo_NivAcade);
        frmAlumno.getCmBx_NvAcademico().addPropertyChangeListener(habilitar_Guardar);
        frmAlumno.getCmBx_SecEconomico().addActionListener(combo_SectEcono);
        frmAlumno.getCmBx_SecEconomico().addPropertyChangeListener(habilitar_Guardar);
        frmAlumno.getCmBx_ForPadre().addActionListener(combo_ForPadre);
        frmAlumno.getCmBx_ForPadre().addPropertyChangeListener(habilitar_Guardar);
        frmAlumno.getCmBx_ForMadre().addActionListener(combo_ForMadre);
        frmAlumno.getCmBx_ForMadre().addPropertyChangeListener(habilitar_Guardar);
        frmAlumno.getCmBx_Parentesco().addActionListener(combo_Parentesco);
        frmAlumno.getCmBx_Parentesco().addPropertyChangeListener(habilitar_Guardar);
        frmAlumno.getTxt_Cedula().addFocusListener(Buscar);
        frmAlumno.getTxt_Cedula().addKeyListener(cedula);
        frmAlumno.getTxt_TlSuperior().addKeyListener(titulo_Superior);
        frmAlumno.getTxt_Ocupacion().addKeyListener(ocupacion);
        frmAlumno.getTxt_NomContacto().addKeyListener(nombre_Contacto);
        frmAlumno.getTxt_ConEmergency().addKeyListener(num_Contacto);

        frmAlumno.getBtn_Buscar().addActionListener(e -> buscarPersona());
        frmAlumno.getBtn_Guardar().addActionListener(e -> guardarAlumno());
        frmAlumno.getBtn_Cancelar().addActionListener(Cancelar);
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaFrmFin("Alumno");
    }

    //Muestra la ventana para visualizar los Alumnos registrado
    public void buscarPersona() {
        VtnAlumno alumno = new VtnAlumno();
        VtnAlumnoCTR c = new VtnAlumnoCTR(vtnPrin, alumno, conecta, ctrPrin, permisos);
        c.iniciar();
    }

    //Captura los datos de Sectores en la Base de Datos
    public void iniciaDatos() {
        //Alumnos = bdAlumno.filtrarAlumno();
        Sectores = sectorE.capturarSectores();
    }

    //Devuelve un boolean para verificar si existen errores en el formulario
    public boolean confirmarErrores() {
        boolean error = false;
        if (frmAlumno.getLbl_ErrCedula().isVisible() == false
                && frmAlumno.getLbl_ErrConEmergencia().isVisible() == false
                && frmAlumno.getLbl_ErrForMadre().isVisible() == false
                && frmAlumno.getLbl_ErrForPadre().isVisible() == false
                && frmAlumno.getLbl_ErrNomContacto().isVisible() == false
                && frmAlumno.getLbl_ErrNvAcademico().isVisible() == false
                && frmAlumno.getLbl_ErrOcupacion().isVisible() == false
                && frmAlumno.getLbl_ErrParentesco().isVisible() == false
                && frmAlumno.getLbl_ErrSecEconomico().isVisible() == false
                && frmAlumno.getLbl_ErrTiSuperior().isVisible() == false
                && frmAlumno.getLbl_ErrTipBachillerato().isVisible() == false
                && frmAlumno.getLbl_ErrTipColegio().isVisible() == false) {
            error = false;
        } else {
            error = true;
        }
        return error;
    }

    //Este método busca al estudiante ingresado por medio de la Cédula en el formulario
    public void buscarCedula() {
        if (cont == 1) {

            boolean error = false;
            String cedula;
            cedula = frmAlumno.getTxt_Cedula().getText();

            if (!cedula.equals("")) {

                if (error == false) {

                    PersonaMD p = bdAlumno.filtrarPersona(frmAlumno.getTxt_Cedula().getText());
                    if (p.getIdentificacion() == null) {
                        reiniciarComponentes(frmAlumno);
                        iniciarComponentes();
                        int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
                        int result = JOptionPane.showConfirmDialog(null, "Usted no esta registrado en el Sistema ¿DESEA HACERLO? ", " Registrar Persona ", dialog);
                        if (result == 0) {
                            //ConectarDB conectar = new ConectarDB("ROOT", "password", "Persona");
                            FrmPersona frmPersona = new FrmPersona();
                            FrmPersonaCTR ctrPers = new FrmPersonaCTR(vtnPrin, frmPersona, conecta, ctrPrin);
                            ctrPers.iniciar();
                            frmPersona.getTxtIdentificacion().setText(cedula);
                            if (modelo.validaciones.Validar.esNumeros(cedula) == true) {
                                frmPersona.getCmbTipoId().setSelectedItem("CEDULA");
                            } else {
                                frmPersona.getCmbTipoId().setSelectedItem("PASAPORTE");
                            }
                            frmAlumno.dispose();
                            ctrPrin.cerradoJIF();
                        }
                        cont = 0;
                    } else {
                        AlumnoMD alumno = bdAlumno.buscarPersona(p.getIdPersona());
                        Integer idAlumno = alumno.getId_Alumno();
                        Font negrita = new Font("Tahoma", Font.BOLD, 13);
                        frmAlumno.getTxt_Nombre().setFont(negrita);
//                        frmAlumno.getTxt_Nombre().setText(p.get(0).getPrimerNombre() + " " + p.get(0).getSegundoNombre()
//                                + " " + p.get(0).getPrimerApellido() + " " + p.get(0).getSegundoApellido());
                        frmAlumno.getTxt_Nombre().setText(p.getPrimerNombre() + " " + p.getSegundoNombre() + " "
                                + p.getPrimerApellido() + " " + p.getSegundoApellido());
                        habilitarGuardar();
                        if (alumno.getId_Alumno() == 0) {
                            reiniciarComponentes(frmAlumno);
                            iniciarComponentes();
//                                frmAlumno.getTxt_Nombre().setText(alumno.getPrimerNombre() + " " + alumno.getSegundoNombre()
//                                        + " " + alumno.getPrimerApellido() + " " + alumno.getSegundoApellido());
                            cont = 0;
                            JOptionPane.showMessageDialog(null, "Esta persona no esta registrada como alumno");
                        } else {
//                                frmAlumno.getTxt_Nombre().setText(alumno.getPrimerNombre() + " " + alumno.getSegundoNombre()
//                                        + " " + alumno.getPrimerApellido() + " " + alumno.getSegundoApellido());
                            frmAlumno.getCmBx_TipoColegio().setSelectedItem(alumno.getTipo_Colegio());
                            frmAlumno.getCmBx_TipoBachillerato().setSelectedItem(alumno.getTipo_Bachillerato());
                            frmAlumno.getSpnr_Anio().setValue(Integer.valueOf(alumno.getAnio_graduacion()));
                            frmAlumno.getCmBx_NvAcademico().setSelectedItem(alumno.getNivel_Academico());
                            frmAlumno.getTxt_TlSuperior().setText(alumno.getTitulo_Superior());
                            frmAlumno.getTxt_Ocupacion().setText(alumno.getOcupacion());
                            SectorEconomicoMD sector = sectorE.capturarSector(alumno.getSectorEconomico().getId_SecEconomico());
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
                            habilitarGuardar();
                        }
                    }
                } else {
                    cont = 0;
                }
            } else {
                cont = 0;
            }

        }
    }

    //Muestra un mensaje de error de ingreso dependiendo del componente
    public void validarComponentes(String texto) {
        if (validar == 1) {
            if (modelo.validaciones.Validar.esLetras(texto) == false && texto.equals("") == false) {
                frmAlumno.getLbl_ErrTiSuperior().setVisible(true);
            } else {
                frmAlumno.getLbl_ErrTiSuperior().setVisible(false);
            }
            validar = 0;
        } else if (validar == 2) {
            if (modelo.validaciones.Validar.esLetras(texto) == false && texto.equals("") == false) {
                frmAlumno.getLbl_ErrOcupacion().setVisible(true);
            } else {
                frmAlumno.getLbl_ErrOcupacion().setVisible(false);
            }
            validar = 0;
        } else if (validar == 3) {
            if (modelo.validaciones.Validar.esLetras(texto) == false && texto.equals("") == false) {
                frmAlumno.getLbl_ErrNomContacto().setVisible(true);
            } else {
                frmAlumno.getLbl_ErrNomContacto().setVisible(false);
            }
            validar = 0;
        } else if (validar == 4) {
            if (modelo.validaciones.Validar.esNumeros(texto) == false && texto.equals("") == false) {
                frmAlumno.getLbl_ErrConEmergencia().setVisible(true);
            } else {
                frmAlumno.getLbl_ErrConEmergencia().setVisible(false);
            }
            validar = 0;
        } else if (validar == 5) {
            if (modelo.validaciones.Validar.esNumeros(texto) == false && texto.equals("") == false) {
                frmAlumno.getLbl_ErrCedula().setText("Ingrese solo números");
                frmAlumno.getLbl_ErrCedula().setVisible(true);
            } else {
                frmAlumno.getLbl_ErrCedula().setVisible(false);
            }
            validar = 0;
        }
    }

    //Habilita el boton Guardar cuando los siguientes componentes NO estan vacios
    public void habilitarGuardar() {

        String titulo_Superior, nombre_Contacto, contacto_Emergencia, cedula, nombre;
        String tipo_Colegio, tipo_Bachillerato, nivel_Academico, sector_Economico, parentesco;

        cedula = frmAlumno.getTxt_Cedula().getText();
        nombre = frmAlumno.getTxt_Nombre().getText();
        titulo_Superior = frmAlumno.getTxt_TlSuperior().getText();
        nombre_Contacto = frmAlumno.getTxt_NomContacto().getText();
        contacto_Emergencia = frmAlumno.getTxt_ConEmergency().getText();
        tipo_Colegio = frmAlumno.getCmBx_TipoColegio().getSelectedItem().toString();
        tipo_Bachillerato = frmAlumno.getCmBx_TipoBachillerato().getSelectedItem().toString();
        nivel_Academico = frmAlumno.getCmBx_NvAcademico().getSelectedItem().toString();
        sector_Economico = frmAlumno.getCmBx_SecEconomico().getSelectedItem().toString();
        parentesco = frmAlumno.getCmBx_Parentesco().getSelectedItem().toString();

        if (confirmarErrores() == false) {
            if (cedula.equals("") == false && nombre.equals("") == false && titulo_Superior.equals("") == false
                    && nombre_Contacto.equals("") == false && contacto_Emergencia.equals("") == false && tipo_Colegio.equals("|SELECCIONE|") == false
                    && tipo_Bachillerato.equals("|SELECCIONE|") == false && sector_Economico.equals("|SELECCIONE|") == false
                    && parentesco.equals("|SELECCIONE|") == false) {
                frmAlumno.getBtn_Guardar().setEnabled(true);
            } else {
                frmAlumno.getBtn_Guardar().setEnabled(false);
            }
        } else {
            frmAlumno.getBtn_Guardar().setEnabled(false);
        }
    }

    //La visibilidad de los errores permanecen ocultos
    public void iniciarComponentes() {
        frmAlumno.getCbx_Identificacion().setToolTipText("Seleccione un Tipo de Identificación");
        frmAlumno.getTxt_Cedula().setToolTipText("Ingrese una Identificación válida y espere la respuesta del Sistema");
        frmAlumno.getTxt_Nombre().setToolTipText("El nombre se filtrará automáticamente");
        frmAlumno.getCmBx_TipoColegio().setToolTipText("Seleccione el Tipo de la Institución a la que cursó");
        frmAlumno.getCmBx_TipoBachillerato().setToolTipText("Seleccione el Tipo de Bachillerato que cursó");
        frmAlumno.getCmBx_NvAcademico().setToolTipText("Seleccione hasta que Nivel Académico alcanzó");
        frmAlumno.getTxt_TlSuperior().setToolTipText("Ingrese el Título de Bachiller");
        frmAlumno.getTxt_Ocupacion().setToolTipText("Ingrese si tiene una Ocupación");
        frmAlumno.getSpnr_Anio().setToolTipText("Ingrese el Año en el consiguió el Título de Bachiller");
        frmAlumno.getCmBx_SecEconomico().setToolTipText("Seleccione un Sector si trabaja, sino seleccione \"Ninguno\"");
        frmAlumno.getCmBx_ForPadre().setToolTipText("Seleccione el Nivel de Formación del Padre");
        frmAlumno.getCmBx_ForMadre().setToolTipText("Seleccione el Nivel de Formación de la Madre");
        frmAlumno.getTxt_NomContacto().setToolTipText("Ingrese el Nombre de un Contacto confiable y a disposición");
        frmAlumno.getCmBx_Parentesco().setToolTipText("Seleccione el Parentesco con respecto al usuario ingresado");
        frmAlumno.getTxt_ConEmergency().setToolTipText("Ingrese el Número telefónico del Contacto");
        frmAlumno.getBtn_Guardar().setToolTipText("Se habilitará después que los campos con \"*\" esten llenos");
        frmAlumno.getBtn_Buscar().setToolTipText("Se abrirá una Ventana con todos los Estudiantes");

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
        frmAlumno.getLbl_ErrTipoIdenti().setVisible(false);
        frmAlumno.getTxt_Nombre().setEnabled(false);
        frmAlumno.getTxt_Cedula().setEnabled(false);
        //frmAlumno.getBtn_Guardar().setEnabled(false);
    }

    //Sirve para insertar un rango de años
    public void iniciarAnios() {
        SpinnerNumberModel s = new SpinnerNumberModel();
        s.setMinimum(1980);
        s.setMaximum(2018);
        s.setStepSize(1);
        frmAlumno.getSpnr_Anio().setModel(s);
        frmAlumno.getSpnr_Anio().setValue(1980);
    }

    /**
     * Inicia los Sectores extraídos de la Lista en el Combo box
     */
    public void iniciarSectores() {
        for (int i = 0; i < Sectores.size(); i++) {
            frmAlumno.getCmBx_SecEconomico().addItem(Sectores.get(i).getDescrip_SecEconomico().toUpperCase());
        }
    }

    //Guarda o Edita al Alumno insertado dependiendo el boolean
    public void guardarAlumno() {

        if (editar == false && editar_2 == false) {
            AlumnoBD persona = new AlumnoBD(conecta);
            this.bdAlumno = pasarDatos(persona);
            if (bdAlumno.guardarAlumno(sectorE.capturarIdSector(frmAlumno.getCmBx_SecEconomico().getSelectedItem().toString())) == true) {
                JOptionPane.showMessageDialog(null, "Datos grabados correctamente");
                botonreporteAlumno();
                frmAlumno.dispose();
                ctrPrin.cerradoJIF();
//                reiniciarComponentes(frmAlumno);
//                iniciarComponentes();
            } else {
                JOptionPane.showMessageDialog(null, "Error en grabar los datos");
            }
        } else if (editar == true) {
            AlumnoBD persona = new AlumnoBD(conecta);
            persona = pasarDatos(bdAlumno);
            if (persona.editarAlumno(persona.capturarPersona(frmAlumno.getTxt_Cedula().getText()).get(0).getIdPersona()) == true) {
                JOptionPane.showMessageDialog(null, "Datos editados correctamente");
                botonreporteAlumno();
                frmAlumno.dispose();
                ctrPrin.cerradoJIF();
//                reiniciarComponentes(frmAlumno);
//                iniciarComponentes();
                editar = false;
            } else {
                JOptionPane.showMessageDialog(null, "Error en editar los datos");
            }
        } else if (editar_2 == true) {
            AlumnoBD persona = new AlumnoBD(conecta);
            persona = pasarDatos(bdAlumno);
            if (persona.editarAlumno(persona.capturarPersona(frmAlumno.getTxt_Cedula().getText()).get(0).getIdPersona()) == true) {
                JOptionPane.showMessageDialog(null, "Datos editados correctamente");
                botonreporteAlumno();
                frmAlumno.dispose();
                ctrPrin.cerradoJIF();
//                reiniciarComponentes(frmAlumno);
//                iniciarComponentes();
                editar_2 = false;
            } else {
                JOptionPane.showMessageDialog(null, "Error en editar los datos");
            }
        }
    }

    //Inserta los datos extraídos del objeto personas en los componentes para su Edición
    public void editar(AlumnoMD persona) {
        editar = true;
        //El alumno que nos pasamos lo llenamos en el formulario  
        Font negrita = new Font("Tahoma", Font.BOLD, 13);
        frmAlumno.getTxt_Nombre().setFont(negrita);
        if (modelo.validaciones.Validar.esNumeros(persona.getIdentificacion()) == true) {
            frmAlumno.getCbx_Identificacion().setSelectedItem("CÉDULA");
        } else {
            frmAlumno.getCbx_Identificacion().setSelectedItem("PASAPORTE");
        }
        frmAlumno.getTxt_Cedula().setText(persona.getIdentificacion());
        frmAlumno.getTxt_Nombre().setText(persona.getPrimerNombre() + " " + persona.getSegundoNombre()
                + " " + persona.getPrimerApellido() + " " + persona.getSegundoApellido());
        frmAlumno.getCmBx_TipoColegio().setSelectedItem(persona.getTipo_Colegio());
        frmAlumno.getCmBx_TipoBachillerato().setSelectedItem(persona.getTipo_Bachillerato());
        frmAlumno.getCmBx_NvAcademico().setSelectedItem(persona.getNivel_Academico());
        frmAlumno.getTxt_TlSuperior().setText(persona.getTitulo_Superior());
        frmAlumno.getChkBx_EdcSuperior().setSelected(persona.isEducacion_Superior());
        frmAlumno.getTxt_Ocupacion().setText(persona.getOcupacion());
        frmAlumno.getSpnr_Anio().setValue(Integer.valueOf(persona.getAnio_graduacion()));
        frmAlumno.getChkBx_Pension().setSelected(persona.isPension());
        frmAlumno.getChkBx_Trabaja().setSelected(persona.isTrabaja());
        frmAlumno.getCmBx_SecEconomico().setSelectedItem(sectorE.capturarSector(persona.getSectorEconomico().getId_SecEconomico()).getDescrip_SecEconomico().toUpperCase());
        frmAlumno.getCmBx_ForPadre().setSelectedItem(persona.getFormacion_Padre());
        frmAlumno.getCmBx_ForMadre().setSelectedItem(persona.getFormacion_Madre());
        frmAlumno.getTxt_NomContacto().setText(persona.getNom_Contacto());
        frmAlumno.getCmBx_Parentesco().setSelectedItem(persona.getParentesco_Contacto());
        frmAlumno.getTxt_ConEmergency().setText(persona.getContacto_Emergencia());
        habilitarGuardar();
    }

    //Se limpian los registros del Formulario
    public void reiniciarComponentes(FrmAlumno frmAlumno) {
        //frmAlumno.getTxt_Cedula().setText("");
        //frmAlumno.getTxt_Nombre().setText("");
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

    //Se extraen los datos de los componentes insertado y los devuelve en un Objeto
    public AlumnoBD pasarDatos(AlumnoBD persona) {
        //List<PersonaMD> user = new ArrayList();
        PersonaMD user = new PersonaMD();
        Integer sectorEco = null;
        user = persona.filtrarPersona(frmAlumno.getTxt_Cedula().getText());
        SectorEconomicoMD sector = new SectorEconomicoMD();
        persona.setIdPersona(user.getIdPersona());
        persona.setIdentificacion(user.getIdentificacion());
        sector.setId_SecEconomico(sectorE.capturarIdSector(frmAlumno.getCmBx_SecEconomico().getSelectedItem().toString()).getId_SecEconomico());
        persona.setSectorEconomico(sector);
        persona.setTipo_Colegio(frmAlumno.getCmBx_TipoColegio().getSelectedItem().toString());
        persona.setTipo_Bachillerato(frmAlumno.getCmBx_TipoBachillerato().getSelectedItem().toString());
        persona.setAnio_graduacion(frmAlumno.getSpnr_Anio().getValue().toString());
        persona.setEducacion_Superior(frmAlumno.getChkBx_EdcSuperior().isSelected());
        persona.setTitulo_Superior(frmAlumno.getTxt_TlSuperior().getText().toUpperCase());
        persona.setNivel_Academico(frmAlumno.getCmBx_NvAcademico().getSelectedItem().toString());
        persona.setPension(frmAlumno.getChkBx_Pension().isSelected());
        persona.setOcupacion(frmAlumno.getTxt_Ocupacion().getText().toUpperCase());
        persona.setTrabaja(frmAlumno.getChkBx_Trabaja().isSelected());
        if (frmAlumno.getCmBx_ForPadre().getSelectedItem().toString().equals("|SELECCIONE|")
                || frmAlumno.getCmBx_ForPadre().getSelectedItem().equals("NINGUNO")) {
            persona.setFormacion_Padre(null);
        } else {
            persona.setFormacion_Padre(frmAlumno.getCmBx_ForPadre().getSelectedItem().toString());
        }
        if (frmAlumno.getCmBx_ForMadre().getSelectedItem().toString().equals("|SELECCIONE|")
                || frmAlumno.getCmBx_ForMadre().getSelectedItem().equals("NINGUNO")) {
            persona.setFormacion_Madre(null);
        } else {
            persona.setFormacion_Madre(frmAlumno.getCmBx_ForMadre().getSelectedItem().toString());
        }
        persona.setNom_Contacto(frmAlumno.getTxt_NomContacto().getText().toUpperCase());
        persona.setParentesco_Contacto(frmAlumno.getCmBx_Parentesco().getSelectedItem().toString());
        persona.setContacto_Emergencia(frmAlumno.getTxt_ConEmergency().getText());
        return persona;
    }

    public void llamaReporteAlumno() {
        JasperReport jr;
        String path = "/vista/reportes/repAlumno.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
            Map parametro = new HashMap();
            parametro.put("cedula_alumno", frmAlumno.getTxt_Cedula().getText());
            System.out.println("parametro del reporte" + parametro);
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            conecta.mostrarReporte(jr, parametro, "Reporte de Alumno");
//            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
//            JasperViewer view = new JasperViewer(print, false);
//            view.setVisible(true);
//            view.setTitle("Reporte de Alumno");
        } catch (JRException ex) {
            Logger.getLogger(VtnCarreraCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void botonreporteAlumno() {
        int s = JOptionPane.showOptionDialog(vtnPrin,
                "Registro de persona \n"
                + "¿Dessea Imprimir el Registro realizado ?", "REPORTE ALUMNO",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"SI", "NO"}, "NO");
        switch (s) {
            case 0:
                llamaReporteAlumno();
                break;
            case 1:

                break;
            default:
                break;
        }
    }

}
