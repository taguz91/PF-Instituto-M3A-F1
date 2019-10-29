package controlador.persona;

import com.toedter.calendar.JDateChooser;
import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import modelo.lugar.LugarBD;
import modelo.lugar.LugarMD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.TxtVCedula;
import modelo.validaciones.TxtVCelular;
import modelo.validaciones.TxtVCorreo;
import modelo.validaciones.TxtVDireccion;
import modelo.validaciones.TxtVLetras;
import modelo.validaciones.TxtVNumCasa;
import modelo.validaciones.TxtVNumeros;
import modelo.validaciones.TxtVTelefono;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.persona.FrmPersona;

/**
 *
 * @author Lina
 */
public class FrmPersonaCTR extends DCTR {

    private final FrmPersona frmPersona;
    private final PersonaBD persona;
    private TxtVCedula valCe;
    private int numAccion = 2;

    private final String[] idiomas = {"Árabe", "Croata", "Francés",
        "Español", "Maltés", "Chino", "Danés", "Vietnamita", "Inglés", "Serbio",
        "Sueco", "Hindi", "Finés", "Bosnia", "Ucraniano", "Japonés", "Portugués",
        "Islandés", "Checo", "Polaco", "Catalán", "Malayo", "Búlgaro", "Rumano",
        "Coreano", "Griego", "Ruso", "Noruego", "Nynorsk", "Húngaro", "Tailandés",
        "Irlandés", "Turco", "Estonio", "Albanés", "Alemán", "Hebreo", "TH",
        "Neerlandés", "Letón", "Italiano", "Eslovaco", "Lituano", "Italiano",
        "Macedonio", "Bielorruso", "Esloveno", "Indonesio"};

    //Para cargar los paises  
    private ArrayList<LugarMD> paises;
    private ArrayList<LugarMD> distritos;
    private ArrayList<LugarMD> ciudades;
    //Lugar donde reside  
    private ArrayList<LugarMD> provincias;
    private ArrayList<LugarMD> cantones;
    private ArrayList<LugarMD> parroquias;

    //Para consultar lugares 
    private final LugarBD lug;
    //Para guardar la foto  
    private FileInputStream fis = null;
    private int lonBytes = 0;

    //Para saber si se esta editando una persona  
    private boolean editar = false;
    private int idPersona = 0;
    private boolean errorCedula = false;

    JDateChooser dateChooser = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');

    public FrmPersonaCTR(FrmPersona frmPersona, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.frmPersona = frmPersona;
        //Inicializamos persona
        this.persona = new PersonaBD(ctrPrin.getConecta());
        this.lug = new LugarBD(ctrPrin.getConecta());
        //Para iniciar los combos de paises 
        cargarPaises();
    }

    /**
     * Metodo iniciar donde se ejecuta todos las acciones d elos botones
     */
    public void iniciar() {
        ctrPrin.agregarVtn(frmPersona);
        //Desactivamos el campo de identificacion porque debe ingresar primero el tipo de identificacion
        frmPersona.getTxtIdentificacion().setEnabled(false);
        //Ocultamos todos los erores del formulario 
        iniciarComponentes();
        cargarIdiomas();
        desactivarDiscapacidad(false);
        desactivarCategoriaMigratoria(false);
        //Cuando se realice una accion en alguno de esos combos 
        iniciarValidaciones();

        //Cuando se realice una accion en alguno de esos combos
        frmPersona.getCmbNacionalidad().addActionListener(e -> cargarDistritosPais());
        frmPersona.getCmbProvincia().addActionListener(e -> cargarCiudadesDistrito());
        frmPersona.getCmbPaisReside().addActionListener(e -> cargarProvinciasResidencia());
        frmPersona.getCmbProvinciaReside().addActionListener(e -> cargarCantonesProvincia());
        frmPersona.getCmbCantonReside().addActionListener(e -> cargarParroquiaCanton());
        frmPersona.getCmbParroquiaReside().addActionListener(e -> cargarCodigoPostal());
        frmPersona.getCbxDiscapacidad().addActionListener(e -> activarDiscapacidad());
        frmPersona.getCbxCategoriaMigratoria().addActionListener(e -> activarCategoriaMigratoria());
        frmPersona.getBtnBuscarFoto().addActionListener(e -> buscarFoto());
        frmPersona.getBtnCapturarFoto().addActionListener(e -> capturarFotoWebCam());
        frmPersona.getBtnGuardarPersona().addActionListener(e -> guardarPersona());
        frmPersona.getBtnCancelar().addActionListener(e -> salirBoton());
        // frmPersona.getBtnBuscarPersona().addActionListener(e -> buscarPersona());

        //Accion de buscar una persona  
        frmPersona.getBtnBuscarPersona().addActionListener(e -> consultar());

        frmPersona.getTxtIdentificacion().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                buscarIdentificacion();
            }
        });

        //Validacion de la cedula
        valCe = new TxtVCedula(frmPersona.getTxtIdentificacion(), frmPersona.getLblErrorIdentificacion());
        frmPersona.getTxtIdentificacion().addKeyListener(valCe);

        frmPersona.getCmbTipoId().addActionListener(e -> tipoID());
        cerrarVtn();
    }

    /**
     * Metodo para cerrar una ventana sin que se muestre el mensaje de cedula no
     * valida
     */
    private void cerrarVtn() {
        frmPersona.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                frmPersona.getTxtIdentificacion().setText("");
            }
        });
    }

    /**
     *
     * @return Devuelve un boolean para verificar si existen errores en el
     * formulario
     */
    public boolean confirmaError() {
        //boolean error = false;
        if (frmPersona.getLblErrorCallePrin().isVisible() == false
                && frmPersona.getLblErrorCalleSec().isVisible() == false
                && frmPersona.getLblErrorCanton().isVisible() == false
                && frmPersona.getLblErrorCantonReside().isVisible() == false
                && frmPersona.getLblErrorCarnetConadis().isVisible() == false
                && frmPersona.getLblErrorCelular().isVisible() == false
                && frmPersona.getLblErrorCodigoPostal().isVisible() == false
                && frmPersona.getLblErrorCorreo().isVisible() == false
                && frmPersona.getLblErrorEstadoCivil().isVisible() == false
                && frmPersona.getLblErrorEtnia().isVisible() == false
                && frmPersona.getLblErrorGenero().isVisible() == false
                && frmPersona.getLblErrorIdentificacion().isVisible() == false
                && frmPersona.getLblErrorIdioma().isVisible() == false
                && frmPersona.getLblErrorNacionalidad().isVisible() == false
                && frmPersona.getLblErrorPaisReside().isVisible() == false
                && frmPersona.getLblErrorPaisReside().isVisible() == false
                //&& frmPersona.getLblErrorParroquiaReside().isVisible() == false
                //                && frmPersona.getLblErrorEspecifiqueDiscapacidad().isVisible() == false
                && frmPersona.getLblErrorCategoriaMigratoria().isVisible() == false
                && frmPersona.getLblErrorPorcentaje().isVisible() == false
                && frmPersona.getLblErrorPriApellido().isVisible() == false
                && frmPersona.getLblErrorPriNombre().isVisible() == false
                && frmPersona.getLblErrorProvincia().isVisible() == false
                && frmPersona.getLblErrorProvinciaReside().isVisible() == false
                && frmPersona.getLblErrorReferencia().isVisible() == false
                && frmPersona.getLblErrorSector().isVisible() == false
                && frmPersona.getLblErrorSegApellido().isVisible() == false
                && frmPersona.getLblErrorSegNombre().isVisible() == false
                && frmPersona.getLblErrorSexo().isVisible() == false
                && frmPersona.getLblErrorTelefono().isVisible() == false
                && frmPersona.getLblErrorTipoResidencia().isVisible() == false
                && frmPersona.getLblErrorTipoSangre().isVisible() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Metodo que pierde el foco al buscar una persona por su identificacion y a
     * su vez activa una persona cuando su cedula ya existe en la base de datos
     * pero esta en estado inactivo.
     */
    public void buscarIdentificacion() {
        errorCedula = false;
        String cedula = frmPersona.getTxtIdentificacion().getText();

        if (!cedula.equals("")) {
            if (valCe.isValidarCedula()) {
                if (!Validar.esCedula(cedula)) {
                    errorCedula = true;
                    frmPersona.getLblErrorIdentificacion().setText("Cédula incorrecta.");
                    frmPersona.getLblErrorIdentificacion().setVisible(true);
                } else {
                    frmPersona.getLblErrorIdentificacion().setVisible(false);
                }
            }

            if (!errorCedula) {
                PersonaMD per = persona.existePersona(cedula);
                editar = true;
                if (per != null) {
                    if (per.isPersonaActiva() == false) {
                        int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
                        int result = JOptionPane.showConfirmDialog(null, "Esta persona se encuentra eliminada.\n ¿Desea Activarla ? ", " Activar Persona", dialog);
                        if (result == 0) {
                            if (persona.activarPersonaIdentificacion(per.getIdentificacion()) == true) {
                                JOptionPane.showMessageDialog(null, "Persona activada correctamente");
                                editar(per);
                            } else {
                                JOptionPane.showMessageDialog(null, "NO SE PUDO ACTUALIZAR A LA PERSONA");
                            }
                        }
                    } else if (per.isPersonaActiva()) {
                        editar(per);
                        numAccion = 2;
                        iniciarValidaciones();
                    }
                } else {
                    numAccion = 0;
                    borrarCampos();
                    iniciarComponentes();
                    editar = false;
                    iniciarValidaciones();
                }
            }
        } else {
            borrarCampos();
            iniciarComponentes();
        }
    }

    /**
     * Metodo para habilitar o deshabiltar el combo TipoID
     */
    private void tipoID() {
        int pos = frmPersona.getCmbTipoId().getSelectedIndex();
        frmPersona.getTxtIdentificacion().setEnabled(false);
        switch (pos) {
            case 1:
                //Activamos el campo para ingresar los datos
                valCe.activarValidacion();
                frmPersona.getTxtIdentificacion().setEnabled(true);
                break;
            case 2:
                valCe.desactivarValidacion();
                frmPersona.getTxtIdentificacion().setBorder(
                        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
                frmPersona.getLblErrorIdentificacion().setVisible(false);
                //Activamos el campo para ingresar los datos
                frmPersona.getTxtIdentificacion().setEnabled(true);
                break;
            default:
                frmPersona.getTxtIdentificacion().setEnabled(false);
                break;
        }
    }

    /**
     * Metodo para desactivar la discapacidad que se ejecuta dentro del metodo
     * activarDiscapacidad()
     *
     * @param estado
     */
    private void desactivarDiscapacidad(boolean estado) {

        if (estado) {
            frmPersona.getLblTipoDiscapacidad().setVisible(estado);
            frmPersona.getCmbTipoDiscapacidad().setVisible(estado);
            frmPersona.getLblCarnetConadis().setVisible(estado);
            frmPersona.getTxtCarnetConadis().setVisible(estado);
            frmPersona.getLblPorcentaje().setVisible(estado);
            frmPersona.getTxtPorcentaje().setVisible(estado);

            if (estado) {
                frmPersona.getCmbTipoDiscapacidad().addActionListener(new CmbValidar(
                        frmPersona.getCmbTipoDiscapacidad(), frmPersona.getLblErrorTipoDiscapacidad()));
            }
        } else {
            frmPersona.getLblTipoDiscapacidad().setVisible(estado);
            frmPersona.getCmbTipoDiscapacidad().setVisible(estado);
            frmPersona.getLblErrorTipoDiscapacidad().setVisible(estado);
            frmPersona.getLblCarnetConadis().setVisible(estado);
            frmPersona.getTxtCarnetConadis().setVisible(estado);
            frmPersona.getLblErrorCarnetConadis().setVisible(estado);
            frmPersona.getLblPorcentaje().setVisible(estado);
            frmPersona.getTxtPorcentaje().setVisible(estado);
            frmPersona.getLblErrorPorcentaje().setVisible(estado);
        }
    }

    /**
     * Metodo para desactivar la categoria migratoria que se ejecuta dentro del
     * metodo activarCategoriaMigratoria()
     *
     * @param estado
     */
    public void desactivarCategoriaMigratoria(boolean estado) {
        if (estado) {
            frmPersona.getCmbCategoriaMigratoria().setVisible(estado);
            //frmPersona.getLblErrorCategoriaMigratoria().setVisible(estado);

            if (estado) {
                frmPersona.getCmbCategoriaMigratoria().addActionListener(new CmbValidar(
                        frmPersona.getCmbCategoriaMigratoria(), frmPersona.getLblErrorCategoriaMigratoria()));
            }
        } else {
            frmPersona.getCmbCategoriaMigratoria().setVisible(estado);
            frmPersona.getLblErrorCategoriaMigratoria().setVisible(estado);
        }

    }

    private void activarCategoriaMigratoria() {
        boolean categoriaM = frmPersona.getCbxCategoriaMigratoria().isSelected();
        desactivarCategoriaMigratoria(categoriaM);

    }

    //Metodo que se ejecuta en el iniciar para desactivar los campos de Discapacidad
    private void activarDiscapacidad() {
        boolean discapacidad = frmPersona.getCbxDiscapacidad().isSelected();
        desactivarDiscapacidad(discapacidad);
    }

    /**
     * Metodo para ejecutar todas las validaciones
     */
    private void iniciarValidaciones() {

        PropertyChangeListener habilitar_Guardar = (PropertyChangeEvent evt) -> {
            habilitarBtnGuardar();
        };

        if (numAccion > 1) {

            //Validar los Combo box
            frmPersona.getCmbIdiomas().addActionListener(new CmbValidar(
                    frmPersona.getCmbIdiomas(), frmPersona.getLblErrorIdioma()));
            frmPersona.getCmbIdiomas().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbCanton().addActionListener(new CmbValidar(
                    frmPersona.getCmbCanton(), frmPersona.getLblErrorCanton()));
            frmPersona.getCmbCanton().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbCantonReside().addActionListener(new CmbValidar(
                    frmPersona.getCmbCantonReside(), frmPersona.getLblErrorCantonReside()));
            frmPersona.getCmbCantonReside().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbEstadoCivil().addActionListener(new CmbValidar(
                    frmPersona.getCmbEstadoCivil(), frmPersona.getLblErrorEstadoCivil()));
            frmPersona.getCmbEstadoCivil().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbEtnia().addActionListener(new CmbValidar(
                    frmPersona.getCmbEtnia(), frmPersona.getLblErrorEtnia()));
            frmPersona.getCmbEtnia().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbGenero().addActionListener(new CmbValidar(
                    frmPersona.getCmbGenero(), frmPersona.getLblErrorGenero()));
            frmPersona.getCmbGenero().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbNacionalidad().addActionListener(new CmbValidar(
                    frmPersona.getCmbNacionalidad(), frmPersona.getLblErrorNacionalidad()));
            frmPersona.getCmbNacionalidad().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbPaisReside().addActionListener(new CmbValidar(
                    frmPersona.getCmbPaisReside(), frmPersona.getLblErrorPaisReside()));
            frmPersona.getCmbPaisReside().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbParroquiaReside().addActionListener(new CmbValidar(
                    frmPersona.getCmbParroquiaReside(), frmPersona.getLblErrorParroquiaReside()));
            frmPersona.getCmbParroquiaReside().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbProvincia().addActionListener(new CmbValidar(
                    frmPersona.getCmbProvincia(), frmPersona.getLblErrorProvincia()));
            frmPersona.getCmbProvincia().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbProvinciaReside().addActionListener(new CmbValidar(
                    frmPersona.getCmbProvinciaReside(), frmPersona.getLblErrorProvinciaReside()));
            frmPersona.getCmbProvinciaReside().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbSexo().addActionListener(new CmbValidar(
                    frmPersona.getCmbSexo(), frmPersona.getLblErrorSexo()));
            frmPersona.getCmbSexo().addPropertyChangeListener(habilitar_Guardar);

            ////////////////
            frmPersona.getCmbTipoDiscapacidad().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbTipoId().addActionListener(new CmbValidar(
                    frmPersona.getCmbTipoId()));
            frmPersona.getCmbTipoId().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbTipoResidencia().addActionListener(new CmbValidar(
                    frmPersona.getCmbTipoResidencia(), frmPersona.getLblErrorTipoResidencia()));
            frmPersona.getCmbTipoResidencia().addPropertyChangeListener(habilitar_Guardar);

            frmPersona.getCmbTipoSangre().addActionListener(new CmbValidar(
                    frmPersona.getCmbTipoSangre(), frmPersona.getLblErrorTipoSangre()));
            frmPersona.getCmbTipoSangre().addPropertyChangeListener(habilitar_Guardar);
            frmPersona.getCmbCategoriaMigratoria().addPropertyChangeListener(habilitar_Guardar);
        }
        //Validar los txt
        frmPersona.getTxtCallePrincipal().addKeyListener(new TxtVDireccion(
                frmPersona.getTxtCallePrincipal(), frmPersona.getLblErrorCallePrin()));
        frmPersona.getTxtCallePrincipal().addPropertyChangeListener(habilitar_Guardar);

        frmPersona.getTxtCalleSecundaria().addKeyListener(new TxtVDireccion(
                frmPersona.getTxtCalleSecundaria(), frmPersona.getLblErrorCalleSec()));

        frmPersona.getTxtCelular().addKeyListener(new TxtVCelular(
                frmPersona.getTxtCelular(), frmPersona.getLblErrorCelular()));

        frmPersona.getTxtCodigoPostal().addKeyListener(new TxtVNumeros(
                frmPersona.getTxtCodigoPostal(), frmPersona.getLblErrorCodigoPostal()));
        frmPersona.getTxtCodigoPostal().addPropertyChangeListener(habilitar_Guardar);

        frmPersona.getTxtPorcentaje().addKeyListener(new TxtVNumeros(
                frmPersona.getTxtPorcentaje(), frmPersona.getLblErrorPorcentaje()));
        frmPersona.getTxtPorcentaje().addPropertyChangeListener(habilitar_Guardar);

        frmPersona.getTxtPrimerApellido().addKeyListener(new TxtVLetras(
                frmPersona.getTxtPrimerApellido(), frmPersona.getLblErrorPriApellido()));
        frmPersona.getTxtPrimerApellido().addPropertyChangeListener(habilitar_Guardar);

        frmPersona.getTxtPrimerNombre().addKeyListener(new TxtVLetras(
                frmPersona.getTxtPrimerNombre(), frmPersona.getLblErrorPriNombre()));
        frmPersona.getTxtPrimerNombre().addPropertyChangeListener(habilitar_Guardar);

        frmPersona.getTxtReferencia().addKeyListener(new TxtVLetras(
                frmPersona.getTxtReferencia(), frmPersona.getLblErrorReferencia()));

        frmPersona.getTxtSector().addKeyListener(new TxtVLetras(
                frmPersona.getTxtSector(), frmPersona.getLblErrorSector()));

        frmPersona.getTxtSegundoApellido().addKeyListener(new TxtVLetras(
                frmPersona.getTxtSegundoApellido(), frmPersona.getLblErrorSegApellido()));
        frmPersona.getTxtSegundoApellido().addPropertyChangeListener(habilitar_Guardar);

        frmPersona.getTxtSegundoNombre().addKeyListener(new TxtVLetras(
                frmPersona.getTxtSegundoNombre(), frmPersona.getLblErrorSegNombre()));
        frmPersona.getTxtSegundoNombre().addPropertyChangeListener(habilitar_Guardar);

        frmPersona.getTxtTelefono().addKeyListener(new TxtVTelefono(
                frmPersona.getTxtTelefono(), frmPersona.getLblErrorTelefono()));
        frmPersona.getTxtCorreo().addKeyListener(new TxtVCorreo(
                frmPersona.getTxtCorreo(), frmPersona.getLblErrorCorreo()));

        frmPersona.getTxtNumeroCasa().addKeyListener(new TxtVNumCasa(frmPersona.getTxtNumeroCasa(),
                frmPersona.getLblErrorNumeroCasa()));
        frmPersona.getTxtNumeroCasa().addPropertyChangeListener(habilitar_Guardar);
    }

    /**
     * Metodo para buscar la foto dentro de los archivos del equipo
     */
    public void buscarFoto() {
        frmPersona.getLblFoto().setIcon(null);
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int estado = j.showOpenDialog(null);
        if (estado == JFileChooser.APPROVE_OPTION) {
            try {
                //Para guardar la foto  
                fis = new FileInputStream(j.getSelectedFile());
                lonBytes = (int) j.getSelectedFile().length();

                System.out.println("Longitud de foto buscada " + lonBytes);
                System.out.println("FIle input stream " + fis);

                Image icono = ImageIO.read(j.getSelectedFile()).getScaledInstance(frmPersona.getLblFoto().getWidth(),
                        frmPersona.getLblFoto().getHeight(), Image.SCALE_SMOOTH);
                frmPersona.getLblFoto().setIcon(new ImageIcon(icono));
                frmPersona.getLblFoto().updateUI();
            } catch (IOException e) {
                //mensaje humane getMesagge()
                JOptionPane.showMessageDialog(null, "No se puedo cargar la imagen");
                System.out.println("Nose puedo cargar la imagen" + e.getMessage());
            }
        }
    }

    /**
     * Metodo para capturar una foto desde WebCam
     */
    private void capturarFotoWebCam() {
        WebCamCTR ctrCam = new WebCamCTR(frmPersona, this, ctrPrin);
        ctrCam.iniciarCamara();
        habilitarBtnGuardar();
    }

    /**
     * Metodo para habilitar el boton guardar despues de que todos los campos
     * obligatorios estén llenos
     */
    public void habilitarBtnGuardar() {

        String TipoId, Identificacion, PriNombre, PriApellido, FechaNaci,
                EstadoCivil, TipoSangre, Genero, Sexo, Etnia, IdiomaRaiz, CallePrin,
                TipoResidencia, TipoDiscapacidad = null, CarnetConadis = null,
                PorcentajeDiscapacidad = null, comboCategoriaMigratoria;
        boolean Discapacidad, categoriaMigratoria;

        TipoId = frmPersona.getCmbTipoId().getSelectedItem().toString();
        Identificacion = frmPersona.getTxtIdentificacion().getText();
        PriNombre = frmPersona.getTxtPrimerNombre().getText();
        PriApellido = frmPersona.getTxtPrimerApellido().getText();
        EstadoCivil = frmPersona.getCmbEstadoCivil().getSelectedItem().toString();
        IdiomaRaiz = frmPersona.getCmbIdiomas().getSelectedItem().toString();
        Sexo = frmPersona.getCmbSexo().getSelectedItem().toString();
        Genero = frmPersona.getCmbGenero().getSelectedItem().toString();
        TipoSangre = frmPersona.getCmbTipoSangre().getSelectedItem().toString();
        Etnia = frmPersona.getCmbEtnia().getSelectedItem().toString();
        CallePrin = frmPersona.getTxtCallePrincipal().getText();
        categoriaMigratoria = frmPersona.getCbxCategoriaMigratoria().isSelected();
        if (confirmaError() == false) {
            if (TipoId.equals("SELECCIONE") == false
                    && Identificacion.equals("") == false
                    && PriNombre.equals("") == false
                    && PriApellido.equals("") == false
                    && EstadoCivil.equals("SELECCIONE") == false
                    && IdiomaRaiz.equals("SELECCIONE") == false
                    && Sexo.equals("SELECCIONE") == false
                    && Genero.equals("SELECCIONE") == false
                    && TipoSangre.equals("SELECCIONE") == false
                    && Etnia.equals("SELECCIONE") == false
                    && CallePrin.equals("") == false) {
                frmPersona.getBtnGuardarPersona().setEnabled(true);
            } else {
                frmPersona.getBtnGuardarPersona().setEnabled(false);
            }
        } else {
            frmPersona.getBtnGuardarPersona().setEnabled(false);
        }
    }

    /**
     * Metodo donde se guarda una nueva persona, validandolas y guardandolo todo
     * en mayusculas y sin espacios
     */
    public void guardarPersona() {

        //Fecha actual usada para validaciones  
        Date fecha;
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaNacimiento = fechaActual;
        //Para validar todo  
        boolean guardar = true;

        String identificacion, priNombre, segNombre,
                priApellido, segApellido,
                fechaNac, estadoCivil = "a", tipoSangre = "a", genero = "a",
                sexo = "a", etnia = "a", carnetConadis = "a",
                tipoDiscapacidad = "a", porcentajeDiscapacidad = "a",
                idiomaRaiz = "a", telefono, callePrin, tipoResidencia = "a",
                calleSec, referencia, comboCategoriaMigra = "a",
                celular, numCasa, sector,
                zonaResidencia, correo, especifiqueDiscapacidad = "a";

        boolean discapacidad, categoriaMigra;
        int tipoIdentifi;

        identificacion = frmPersona.getTxtIdentificacion().getText().trim().toUpperCase();

        tipoIdentifi = frmPersona.getCmbTipoId().getSelectedIndex();
        if (tipoIdentifi == 1) {
            if (!Validar.esCedula(identificacion)) {
                guardar = false;
                frmPersona.getLblErrorIdentificacion().setVisible(true);
            } else {
                frmPersona.getLblErrorIdentificacion().setVisible(false);
            }
        } else {
            //Validar cuando es pasaporte 
        }
        priNombre = frmPersona.getTxtPrimerNombre().getText().trim().toUpperCase();
        if (!Validar.esLetras(priNombre)) {
            guardar = false;
            frmPersona.getLblErrorPriNombre().setVisible(true);
        } else {
            frmPersona.getLblErrorPriNombre().setVisible(false);
        }
        segNombre = frmPersona.getTxtSegundoNombre().getText().trim().toUpperCase();

        priApellido = frmPersona.getTxtPrimerApellido().getText().trim().toUpperCase();
        if (!Validar.esLetras(priApellido)) {
            guardar = false;
            frmPersona.getLblErrorPriApellido().setVisible(true);

        } else {
            frmPersona.getLblErrorPriApellido().setVisible(false);
        }
        segApellido = frmPersona.getTxtSegundoApellido().getText().trim().toUpperCase();

        if (frmPersona.getJdfechaNacimiento().isValid()) {
            fecha = frmPersona.getJdfechaNacimiento().getDate();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //Se lo pasa a un string para poder validarlo
            fechaNac = sdf.format(fecha);
            String fec[] = fechaNac.split("/");

            if (Integer.parseInt(fec[2]) > fechaActual.getYear()
                    || Integer.parseInt(fec[2]) > (fechaActual.getYear() - 16)) {
                guardar = false;
                frmPersona.getLblErrorFecNac().setVisible(true);
            } else {
                fechaNacimiento = LocalDate.of(Integer.parseInt(fec[2]),
                        Integer.parseInt(fec[1]), Integer.parseInt(fec[0]));
                frmPersona.getLblErrorFecNac().setVisible(false);
            }
        } else {
            System.out.println("No es valida la fecha");
            frmPersona.getLblErrorFecNac().setVisible(false);
        }

        if (frmPersona.getCmbEstadoCivil().getSelectedIndex() < 1) {
            guardar = false;
            frmPersona.getLblErrorEstadoCivil().setVisible(true);
        } else {
            estadoCivil = frmPersona.getCmbEstadoCivil().getSelectedItem().toString();
            frmPersona.getLblErrorEstadoCivil().setVisible(false);
        }

        if (frmPersona.getCmbGenero().getSelectedIndex() < 1) {
            guardar = false;
            frmPersona.getLblErrorGenero().setVisible(true);
        } else {
            genero = frmPersona.getCmbGenero().getSelectedItem().toString();
            frmPersona.getLblErrorGenero().setVisible(false);
        }

        if (frmPersona.getCmbSexo().getSelectedIndex() < 1) {
            guardar = false;
            frmPersona.getLblErrorSexo().setVisible(true);
        } else {
            sexo = frmPersona.getCmbSexo().getSelectedItem().toString();
            frmPersona.getLblErrorSexo().setVisible(false);
        }

        if (frmPersona.getCmbEtnia().getSelectedIndex() < 1) {
            guardar = false;
            frmPersona.getLblErrorEtnia().setVisible(true);
        } else {
            etnia = frmPersona.getCmbEtnia().getSelectedItem().toString();
            frmPersona.getLblErrorEtnia().setVisible(false);
        }

        if (frmPersona.getCmbTipoResidencia().getSelectedIndex() < 1) {
            guardar = false;
            frmPersona.getLblTipoDiscapacidad().setVisible(true);
        } else {
            tipoResidencia = frmPersona.getCmbTipoResidencia().getSelectedItem().toString();
            frmPersona.getLblErrorTipoResidencia().setVisible(false);
        }

        if (frmPersona.getCmbTipoSangre().getSelectedIndex() < 1) {
            guardar = false;
            frmPersona.getLblErrorTipoSangre().setVisible(true);
        } else {
            tipoSangre = frmPersona.getCmbTipoSangre().getSelectedItem().toString();
            frmPersona.getLblErrorTipoSangre().setVisible(false);
        }

        categoriaMigra = frmPersona.getCbxCategoriaMigratoria().isSelected();
        if (categoriaMigra) {
            if (frmPersona.getCmbCategoriaMigratoria().getSelectedIndex() < 1) {
                frmPersona.getLblErrorCategoriaMigratoria().setVisible(true);
            } else {
                comboCategoriaMigra = frmPersona.getCmbCategoriaMigratoria().getSelectedItem().toString();
                frmPersona.getLblErrorCategoriaMigratoria().setVisible(false);
            }
        }

        discapacidad = frmPersona.getCbxDiscapacidad().isSelected();
        if (discapacidad) {

            if (frmPersona.getCmbTipoDiscapacidad().getSelectedIndex() < 1) {
                frmPersona.getLblErrorTipoDiscapacidad().setVisible(true);
            } else {
                tipoDiscapacidad = frmPersona.getCmbTipoDiscapacidad().getSelectedItem().toString();
                frmPersona.getLblErrorTipoDiscapacidad().setVisible(false);
            }

            porcentajeDiscapacidad = frmPersona.getTxtPorcentaje().getText().trim().toUpperCase();
            if (!Validar.esNumeros(porcentajeDiscapacidad)) {
                guardar = false;
                frmPersona.getLblErrorPorcentaje().setVisible(true);
            } else {
                porcentajeDiscapacidad = frmPersona.getTxtPorcentaje().getText();
                frmPersona.getLblErrorPorcentaje().setVisible(false);
            }
            
            carnetConadis = frmPersona.getTxtCarnetConadis().getText();
        }

        if (frmPersona.getCmbIdiomas().getSelectedIndex() < 1) {
            guardar = false;
            frmPersona.getLblErrorIdioma().setVisible(true);
        } else {
            idiomaRaiz = frmPersona.getCmbIdiomas().getSelectedItem().toString();
            frmPersona.getLblErrorIdioma().setVisible(false);
        }

        telefono = frmPersona.getTxtTelefono().getText().trim().toUpperCase();

        callePrin = frmPersona.getTxtCallePrincipal().getText().trim().toUpperCase();
        if (!Validar.esDireccion(callePrin)) {
            frmPersona.getLblErrorCallePrin().setVisible(true);
            guardar = false;
        } else {
            frmPersona.getLblErrorCallePrin().setVisible(false);
        }

        calleSec = frmPersona.getTxtCalleSecundaria().getText().trim().toUpperCase().toUpperCase();
        referencia = frmPersona.getTxtReferencia().getText().trim().toUpperCase();
        celular = frmPersona.getTxtCelular().getText().trim().toUpperCase();
        // Lugares en donde reside y vive 
        LugarMD lugarNac = null, lugarRes = null;
        //Aqui preguntamos siempre que sea mayor a la posicion 0 porque 
        //Tambien que siempre sea menor iguala al numero de resultados que 
        //tenga porque el ultimo siempre sera otro.
        //Si es otro se guardaria el aterior y no pasara
        int posNac = frmPersona.getCmbNacionalidad().getSelectedIndex();
        if (posNac > 0 && posNac <= paises.size()) {
            frmPersona.getLblErrorNacionalidad().setVisible(false);
            lugarNac = paises.get(posNac - 1);
            int posDis = frmPersona.getCmbProvincia().getSelectedIndex();
            if (posDis > 0 && posDis <= distritos.size()) {
                frmPersona.getLblErrorProvincia().setVisible(false);
                lugarNac = distritos.get(posDis - 1);
                int posCi = frmPersona.getCmbCanton().getSelectedIndex();
                if (posCi > 0 && posCi <= ciudades.size()) {
                    frmPersona.getLblErrorCanton().setVisible(false);
                    lugarNac = ciudades.get(posCi - 1);
                } else {
                    frmPersona.getLblErrorCanton().setVisible(true);
                }
            } else {
                frmPersona.getLblErrorProvincia().setVisible(true);
            }
        } else {
            frmPersona.getLblErrorNacionalidad().setVisible(true);
        }

        int posPro = frmPersona.getCmbProvinciaReside().getSelectedIndex();
        if (posPro > 0 && posPro <= provincias.size()) {
            frmPersona.getLblErrorProvinciaReside().setVisible(false);
            lugarRes = provincias.get(posPro - 1);
            int posCa = frmPersona.getCmbCantonReside().getSelectedIndex();
            if (posCa > 0 && posCa <= cantones.size()) {
                frmPersona.getLblErrorCantonReside().setVisible(false);
                lugarRes = cantones.get(posCa - 1);
                int posPr = frmPersona.getCmbParroquiaReside().getSelectedIndex();
                if (posPr > 0 && posPr <= parroquias.size()) {
                    frmPersona.getLblErrorParroquiaReside().setVisible(false);
                    //Editar el codigo postal

                    lugarRes = parroquias.get(posPr - 1);
                    //
                } else {
                    //frmPersona.getLblErrorParroquiaReside().setVisible(true);
                }
            } else {
                frmPersona.getLblErrorCantonReside().setVisible(true);
            }
        } else {
            frmPersona.getLblErrorProvinciaReside().setVisible(true);
        }
        //Esto creo que deberiamos cambiarlo para hacerlo de otra manera 
        numCasa = frmPersona.getTxtNumeroCasa().getText().trim().toUpperCase();
        sector = frmPersona.getTxtSector().getText().trim().toUpperCase();
        zonaResidencia = frmPersona.getCmbTipoResidencia().getSelectedItem().toString().trim().toUpperCase();
        correo = frmPersona.getTxtCorreo().getText().trim();

        if (guardar) {
            PersonaBD per = new PersonaBD(ctrPrin.getConecta());
            //Pasamos la informacion de la foto 
            per.setFile(fis);
            per.setLogBytes(lonBytes);
            per.setIdentificacion(identificacion);
            per.setPrimerNombre(priNombre);
            per.setSegundoNombre(segNombre);
            per.setPrimerApellido(priApellido);
            per.setSegundoApellido(segApellido);
            per.setFechaNacimiento(fechaNacimiento);
            per.setEstadoCivil(estadoCivil);
            per.setCelular(celular);
            per.setTelefono(telefono);
            per.setIdiomaRaiz(idiomaRaiz);
            per.setIdioma(idiomaRaiz);
            per.setTipoSangre(tipoSangre);
            per.setGenero(genero);
            per.setSexo(sexo.charAt(0));
            per.setEtnia(etnia);
            per.setCorreo(correo);
            if (discapacidad) {
                per.setDiscapacidad(discapacidad);
                per.setCarnetConadis(carnetConadis);
                per.setTipoDiscapacidad(tipoDiscapacidad);
                per.setPorcentajeDiscapacidad(Integer.parseInt(porcentajeDiscapacidad));
            }
            if (categoriaMigra) {
                per.setCategoriaMigratoria(comboCategoriaMigra);
            }
            per.setLugarNatal(lugarNac);
            per.setLugarResidencia(lugarRes);
            //Codigo postal
            per.setTipoResidencia(zonaResidencia);
            per.setCallePrincipal(callePrin);
            per.setCalleSecundaria(calleSec);
            per.setReferencia(referencia);
            per.setSector(sector);
            per.setNumeroCasa(numCasa);
            per.setFechaRegistro(fechaActual);

            if (editar) {
                if (idPersona > 0) {
                    System.out.println("idPersona" + idPersona);
                    if (fis != null) {
                        if (per.editarPersonaConFoto(idPersona)) {
                            JOptionPane.showMessageDialog(null, "Datos Editados Correctamente.");
                            botonreportepersona();
                            borrarCamposConId();
                            iniciarComponentes();
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo editar,\n"
                                    + "Revise su conexion a internet. ");
                        }

                    } else {
                        if (per.editarPersona(idPersona)) {
                            JOptionPane.showMessageDialog(null, "Datos Editados Correctamente.");
                            botonreportepersona();
                            borrarCamposConId();
                            iniciarComponentes();
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo editar,\n"
                                    + "Revise su conexion a internet. ");
                        }

                    }
                    editar = false;
                }
            } else {
                if (fis != null) {
                    per.insertarPersonaConFoto();
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
                    botonreportepersona();
                    borrarCampos();
                    iniciarComponentes();

                } else {
                    per.insertarPersona();
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
                    botonreportepersona();
                    borrarCampos();
                    iniciarComponentes();

                }
            }
            frmPersona.dispose();
            ctrPrin.cerradoJIF();

        } else {
            JOptionPane.showMessageDialog(null, "Existen errores en los campos\nRevise su información!!");
        }

    }

    public void salirBoton() {
        frmPersona.dispose();
    }

    private void consultar() {
        String identificacion = frmPersona.getTxtIdentificacion().getText();
        PersonaMD per = persona.buscarPersona(identificacion);
        if (per != null) {
            editar(per);
        }
    }

    /**
     * Metodo en donde editamos una persona extrayendo sus datos por parametro
     *
     * @param per
     */
    public void editar(PersonaMD per) {
        //Seteamos los datos en el formulario  
        boolean discapacidad;
        idPersona = per.getIdPersona();
        System.out.println("id" + idPersona);
        editar = true;
        String cedula;
        cedula = per.getIdentificacion();

        Date fecha = new Date();
        Calendar fecha_Nacimiento = Calendar.getInstance();
        fecha_Nacimiento.clear();
        fecha_Nacimiento.set(per.getFechaNacimiento().getYear(), per.getFechaNacimiento().getMonthValue() - 1, per.getFechaNacimiento().getDayOfMonth());

        if (modelo.validaciones.Validar.esCedula(cedula) == true) {
            frmPersona.getCmbTipoId().setSelectedItem("CEDULA");
        } else {
            frmPersona.getCmbTipoId().setSelectedItem("PASAPORTE");
        }
        frmPersona.getTxtIdentificacion().setEnabled(true);
        //frmPersona.getCmbTipoId().setSelectedItem(per.getIdPersona());
        frmPersona.getTxtIdentificacion().setText(per.getIdentificacion());
        frmPersona.getTxtCallePrincipal().setText(per.getCallePrincipal());
        frmPersona.getTxtCalleSecundaria().setText(per.getCalleSecundaria());
        frmPersona.getTxtCelular().setText(per.getCelular());
        frmPersona.getTxtCorreo().setText(per.getCorreo());
        frmPersona.getTxtNumeroCasa().setText(per.getNumeroCasa());
        frmPersona.getTxtPrimerApellido().setText(per.getPrimerApellido());
        frmPersona.getTxtPrimerNombre().setText(per.getPrimerNombre());
        frmPersona.getTxtReferencia().setText(per.getReferencia());
        frmPersona.getTxtSector().setText(per.getSector());
        frmPersona.getTxtSegundoApellido().setText(per.getSegundoApellido());
        frmPersona.getTxtSegundoNombre().setText(per.getSegundoNombre());
        frmPersona.getTxtTelefono().setText(per.getTelefono());
        //frmPersona.getJdfechaNacimiento().setDate(fecha);
        if (per.getEstadoCivil() == null) {
            frmPersona.getCmbEstadoCivil().setSelectedItem("SELECCIONE");
        } else {
            frmPersona.getCmbEstadoCivil().setSelectedItem(per.getEstadoCivil());
        }
        //Discapacidad
        if (per.isDiscapacidad() == false) {
            activarDiscapacidad();
            frmPersona.getCbxDiscapacidad().setSelected(false);
            frmPersona.getCmbTipoDiscapacidad().setSelectedItem("SELECCIONE");
            frmPersona.getTxtCarnetConadis().setText("");
            frmPersona.getTxtPorcentaje().setText("");
        } else {

            frmPersona.getCbxDiscapacidad().setSelected(per.isDiscapacidad());
            activarDiscapacidad();
            frmPersona.getCmbTipoDiscapacidad().setSelectedItem(per.getTipoDiscapacidad());
            frmPersona.getTxtCarnetConadis().setText(per.getCarnetConadis());
            frmPersona.getTxtPorcentaje().setText(per.getPorcentajeDiscapacidad() + "");
        }

        if (per.getIdiomaRaiz() == null) {
            frmPersona.getCmbIdiomas().setSelectedItem("SELECCIONE");
        } else {
            frmPersona.getCmbIdiomas().setSelectedItem(per.getIdiomaRaiz());
        }

        if (per.getCategoriaMigratoria() == null) {
            frmPersona.getCmbCategoriaMigratoria().setSelectedItem("SELECCIONE");
        } else {
            frmPersona.getCbxCategoriaMigratoria().isSelected();
            activarCategoriaMigratoria();
            frmPersona.getCmbCategoriaMigratoria().setSelectedItem(per.getCategoriaMigratoria());
        }

        // frmPersona.getJdcFechaNacimiento().setSelectedDate(fecha_Nacimiento);
        frmPersona.getJdfechaNacimiento().setCalendar(fecha_Nacimiento);
        String sexo = per.getSexo() + "";
        if ("H".equals(sexo)) {
            sexo = "HOMBRE";
        } else {
            sexo = "MUJER";
        }
        frmPersona.getCmbSexo().setSelectedItem(sexo);

        frmPersona.getCmbTipoSangre().setSelectedItem(per.getTipoSangre().trim());
        frmPersona.getCmbGenero().setSelectedItem(per.getGenero());
        frmPersona.getCmbEtnia().setSelectedItem(per.getEtnia());
        frmPersona.getCmbTipoResidencia().setSelectedItem(per.getTipoResidencia());
        //Codigo postal
        frmPersona.getCmbNacionalidad().setSelectedItem(per.getLugarNatal());
        frmPersona.getCmbProvincia().setSelectedItem(per.getLugarNatal());
        frmPersona.getCmbCanton().setSelectedItem(per.getLugarNatal());

        frmPersona.getCmbProvinciaReside().setSelectedItem(per.getLugarResidencia());
        frmPersona.getCmbParroquiaReside().setSelectedItem(per.getLugarResidencia());
        frmPersona.getCmbPaisReside().setSelectedItem(per.getLugarResidencia());
        frmPersona.getCmbCantonReside().setSelectedItem(per.getLugarResidencia());

        //Cargar foto
        if (per.getFoto() != null) {
            Image icono = per.getFoto().getScaledInstance(frmPersona.getLblFoto().getWidth(),
                    frmPersona.getLblFoto().getHeight(), Image.SCALE_SMOOTH);
            frmPersona.getLblFoto().setIcon(new ImageIcon(icono));
            frmPersona.getLblFoto().updateUI();
        } else {
            //Si existe uan foto la borramos  
            frmPersona.getLblFoto().setIcon(null);
            frmPersona.getLblFoto().updateUI();
        }
        //Cargamos los combos de lugar  
        int nvlLugarNac = 0;
        if (per.getLugarNatal() != null) {
            nvlLugarNac = Integer.parseInt(per.getLugarNatal().getNivel());
        }
        String ciudad = null, distrito = null;
        if (nvlLugarNac == 3) {
            //Guardamos el nombre del ciudad/canton para luego selecionarlo
            ciudad = per.getLugarNatal().getNombre();
            per.setLugarNatal(lug.buscar(per.getLugarNatal().getIdReferencia()));
        }
        //Ahora preguntamos nuevamente el nivel del lugar 
        nvlLugarNac = Integer.parseInt(per.getLugarNatal().getNivel());
        if (nvlLugarNac == 2) {
            //Guardamos el nombre del distrito/provincia para luego selecionarlo
            distrito = per.getLugarNatal().getNombre();
            per.setLugarNatal(lug.buscar(per.getLugarNatal().getIdReferencia()));
        }
        //Ahora preguntamos nuevamente el nivel 
        nvlLugarNac = Integer.parseInt(per.getLugarNatal().getNivel());
        if (nvlLugarNac == 1) {
            cargarPaises();
            frmPersona.getCmbNacionalidad().setSelectedItem(per.getLugarNatal().getNombre());
            //Seteamos el nuevo lugar de una
            if (distrito != null) {
                frmPersona.getCmbProvincia().setSelectedItem(distrito);
            }
            if (ciudad != null) {
                frmPersona.getCmbCanton().setSelectedItem(ciudad);
            }
        }
        int nvlLugarRes;
        System.out.println("Nivel de lugar residencia  " + per.getLugarResidencia().getNivel());
        if (per.getLugarResidencia().getNivel() == null) {
            nvlLugarRes = 0;
        } else {
            nvlLugarRes = Integer.parseInt(per.getLugarResidencia().getNivel());
        }
        String parroquia = null, canton = null, provincia = null;
        if (nvlLugarRes == 4) {
            //Guardamos el nombre del parroquia para luego selecionarlo
            parroquia = per.getLugarResidencia().getNombre();
            per.setLugarResidencia(lug.buscar(per.getLugarResidencia().getIdReferencia()));
        }
        //Ahora preguntamos nuevamente el nivel del lugar 
        if (per.getLugarResidencia().getNivel() == null) {
            nvlLugarRes = 0;
        } else {
            nvlLugarRes = Integer.parseInt(per.getLugarResidencia().getNivel());
        }
        if (nvlLugarRes == 3) {
            //Guardamos el nombre del canton para luego selecionarlo
            canton = per.getLugarResidencia().getNombre();
            per.setLugarResidencia(lug.buscar(per.getLugarResidencia().getIdReferencia()));
        }
        //Ahora preguntamos nuevamente el nivel del lugar 
        if (per.getLugarResidencia().getNivel() == null) {
            nvlLugarRes = 0;
        } else {
            nvlLugarRes = Integer.parseInt(per.getLugarResidencia().getNivel());
        }
        if (nvlLugarRes == 2) {
            //Guardamos el nombre del provincia para luego selecionarlo
            provincia = per.getLugarResidencia().getNombre();
            per.setLugarResidencia(lug.buscar(per.getLugarResidencia().getIdReferencia()));
        }
        //Ahora preguntamos nuevamente el nivel 
        if (per.getLugarResidencia().getNivel() == null) {
            nvlLugarRes = 0;
        } else {
            nvlLugarRes = Integer.parseInt(per.getLugarResidencia().getNivel());
        }
        System.out.println("Pais reside " + per.getLugarResidencia().getNombre());
        if (nvlLugarRes == 1) {
            frmPersona.getCmbPaisReside().setSelectedItem(per.getLugarResidencia().getNombre());
            //Seteamos el nuevo lugar de una
            if (provincia != null) {
                frmPersona.getCmbProvinciaReside().setSelectedItem(provincia);
            }
            if (canton != null) {
                frmPersona.getCmbCantonReside().setSelectedItem(canton);
            }
            if (parroquia != null) {
                frmPersona.getCmbParroquiaReside().setSelectedItem(parroquia);
            }
        }
        iniciarValidaciones();
    }

    /**
     * Metodo para ocultar errores
     */
    public void iniciarComponentes() {
        frmPersona.getCmbTipoId().setToolTipText("Seleccione un Tipo de Identificación");
        frmPersona.getTxtIdentificacion().setToolTipText("Ingrese una Identificación válida y espere la respuesta del Sistema");
        frmPersona.getTxtPrimerNombre().setToolTipText("Ingrese su primer nombre");
        frmPersona.getTxtSegundoNombre().setToolTipText("Ingrese su segundo nombre");
        frmPersona.getTxtPrimerApellido().setToolTipText("Ingrese su primer apellido");
        frmPersona.getTxtSegundoApellido().setToolTipText("Ingrese su segundo apellido");
        frmPersona.getTxtCallePrincipal().setToolTipText("Ingrese su calle principal");
        frmPersona.getTxtCalleSecundaria().setToolTipText("Ingrese su calle secundaria");
        frmPersona.getTxtCarnetConadis().setToolTipText("Ingrese un carnet de conadis valido");
        frmPersona.getTxtCelular().setToolTipText("Ingrese su numero de celular");
        frmPersona.getTxtCodigoPostal().setToolTipText("Ingrese su codigo postal");
        frmPersona.getTxtCorreo().setToolTipText("Ingrese su correo");
        frmPersona.getTxtNumeroCasa().setToolTipText("Ingrese el numero de casa");
        frmPersona.getTxtPorcentaje().setToolTipText("Ingrese el porcentaje de discapacidad");
        frmPersona.getTxtReferencia().setToolTipText("Ingrese una referencia de su direccion");
        frmPersona.getTxtSector().setToolTipText("Ingrese el sector donde vive");
        frmPersona.getTxtTelefono().setToolTipText("Ingrese su numero convencional");
        frmPersona.getLblErrorCategoriaMigratoria().setVisible(false);
        frmPersona.getLblErrorCallePrin().setVisible(false);
        frmPersona.getLblErrorCalleSec().setVisible(false);
        frmPersona.getLblErrorCanton().setVisible(false);
        frmPersona.getLblErrorCantonReside().setVisible(false);
        frmPersona.getLblErrorCarnetConadis().setVisible(false);
        frmPersona.getLblErrorCelular().setVisible(false);
        frmPersona.getLblErrorCodigoPostal().setVisible(false);
        frmPersona.getLblErrorCorreo().setVisible(false);
        frmPersona.getLblErrorEstadoCivil().setVisible(false);
        frmPersona.getLblErrorEtnia().setVisible(false);
        frmPersona.getLblErrorFecNac().setVisible(false);
        frmPersona.getLblErrorGenero().setVisible(false);
        frmPersona.getLblErrorIdentificacion().setVisible(false);
        frmPersona.getLblErrorIdioma().setVisible(false);
        frmPersona.getLblErrorNacionalidad().setVisible(false);
        frmPersona.getLblErrorNumeroCasa().setVisible(false);
        frmPersona.getLblErrorParroquiaReside().setVisible(false);
        frmPersona.getLblErrorPorcentaje().setVisible(false);
        frmPersona.getLblErrorPriApellido().setVisible(false);
        frmPersona.getLblErrorPriNombre().setVisible(false);
        frmPersona.getLblErrorProvincia().setVisible(false);
        frmPersona.getLblErrorProvinciaReside().setVisible(false);
        frmPersona.getLblErrorReferencia().setVisible(false);
        frmPersona.getLblErrorSector().setVisible(false);
        frmPersona.getLblErrorSegApellido().setVisible(false);
        frmPersona.getLblErrorSegNombre().setVisible(false);
        frmPersona.getLblErrorSexo().setVisible(false);
        frmPersona.getLblErrorTelefono().setVisible(false);
        frmPersona.getLblErrorTipoDiscapacidad().setVisible(false);
        frmPersona.getLblErrorTipoResidencia().setVisible(false);
        frmPersona.getLblErrorTipoSangre().setVisible(false);
        frmPersona.getLblErrorPaisReside().setVisible(false);
    }

    //Metodo para cargar idiomas en el combo box
    private void cargarIdiomas() {
        frmPersona.getCmbIdiomas().removeAllItems();
        frmPersona.getCmbIdiomas().addItem("SELECCIONE");
        for (String i : idiomas) {
            frmPersona.getCmbIdiomas().addItem(i.toUpperCase());
        }
    }

    //Metodos para los combos de residencia y ciudades natales
    private void cargarPaises() {
        paises = lug.buscarPaises();
        paises = reordenarArray(paises, "ECUADOR");
        frmPersona.getCmbNacionalidad().removeAllItems();
        frmPersona.getCmbNacionalidad().addItem("SELECCIONE");
        //Cargamos el otro combo de paises 
        frmPersona.getCmbPaisReside().removeAllItems();
        frmPersona.getCmbPaisReside().addItem("SELECCIONE");
        paises.forEach((l) -> {
            frmPersona.getCmbNacionalidad().addItem(l.getNombre());
            frmPersona.getCmbPaisReside().addItem(l.getNombre());
        });
    }

    //Reordenamos paises para que ecuador este primero  
    private ArrayList<LugarMD> reordenarArray(ArrayList<LugarMD> lugares, String primerItem) {
        ArrayList<LugarMD> lugaresOrdenados = new ArrayList();
        lugares.forEach((l) -> {
            if (l.getNombre().equalsIgnoreCase(primerItem)) {
                lugaresOrdenados.add(l);
            }
        });

        lugares.forEach((l) -> {
            if (!l.getNombre().equalsIgnoreCase(primerItem)) {
                lugaresOrdenados.add(l);
            }
        });
        return lugaresOrdenados;
    }

    public void cargarDistritosPais() {
        int posNac = frmPersona.getCmbNacionalidad().getSelectedIndex();
        if (posNac > 0) {
            frmPersona.getLblErrorProvincia().setVisible(false);
            distritos = lug.buscarPorReferencia(paises.get(posNac - 1).getId());
            distritos = reordenarArray(distritos, "PROVINCIA DEL AZUAY");
            cargarCmbLugares(frmPersona.getCmbProvincia(), distritos);
        } else {
            frmPersona.getLblErrorProvincia().setVisible(true);
        }
    }

    public void cargarCiudadesDistrito() {
        int posDis = frmPersona.getCmbProvincia().getSelectedIndex();
        if (posDis > 0) {
            frmPersona.getLblErrorCanton().setVisible(false);
            ciudades = lug.buscarPorReferencia(distritos.get(posDis - 1).getId());
            cargarCmbLugares(frmPersona.getCmbCanton(), ciudades);
        } else {
            frmPersona.getLblErrorCanton().setVisible(true);
        }
    }

    private void cargarProvinciasResidencia() {
        int posPaisRe = frmPersona.getCmbPaisReside().getSelectedIndex();
        if (posPaisRe > 0) {
            frmPersona.getLblErrorPaisReside().setVisible(false);
            provincias = lug.buscarPorReferencia(paises.get(posPaisRe - 1).getId());
            provincias = reordenarArray(provincias, "PROVINCIA DEL AZUAY");
            cargarCmbLugares(frmPersona.getCmbProvinciaReside(), provincias);
        } else {
            frmPersona.getLblErrorPaisReside().setVisible(true);
        }
    }

    public void cargarCantonesProvincia() {
        int posPr = frmPersona.getCmbProvinciaReside().getSelectedIndex();
        if (posPr > 0) {
            frmPersona.getLblErrorCantonReside().setVisible(false);
            cantones = lug.buscarPorReferencia(provincias.get(posPr - 1).getId());
            cargarCmbLugares(frmPersona.getCmbCantonReside(), cantones);
        } else {
            frmPersona.getLblErrorCantonReside().setVisible(true);
        }
    }

    public void cargarParroquiaCanton() {
        int posCt = frmPersona.getCmbCantonReside().getSelectedIndex();
        if (posCt > 0) {
            frmPersona.getLblErrorParroquiaReside().setVisible(false);
            parroquias = lug.buscarPorReferencia(cantones.get(posCt - 1).getId());
            cargarCmbLugares(frmPersona.getCmbParroquiaReside(), parroquias);
            System.out.println("Cantones " + cantones.get(posCt - 1).getId());
        } else {
            frmPersona.getLblErrorParroquiaReside().setVisible(true);
        }
    }

    public void cargarCmbLugares(JComboBox cmb, ArrayList<LugarMD> lug) {
        cmb.removeAllItems();
        cmb.addItem("SELECCIONE");
        lug.forEach((l) -> {
            cmb.addItem(l.getNombre());
        });
        cmb.addItem("OTRO");
    }

    public void cargarCodigoPostal() {

        int posPr = frmPersona.getCmbProvinciaReside().getSelectedIndex();
        int posPa = frmPersona.getCmbParroquiaReside().getSelectedIndex();
        int codigo;
        if (posPa > 0 && posPa < parroquias.size()) {
            //cantones.get(posPa).getId();
            frmPersona.getTxtCodigoPostal().setText(parroquias.get(posPa - 1).getCodigo());

        } else {
//            lug.editarLugar(cantones.get(posPa).getId());
            frmPersona.getTxtCodigoPostal().setText("");
            // codigo = frmPersona.getTxtCodigoPostal().setText(lug.editarLugar(cantones.get(posPa).getId()));

        }
    }

    private void borrarCampos() {

        frmPersona.getTxtCallePrincipal().setText("");
        frmPersona.getTxtCalleSecundaria().setText("");
        frmPersona.getTxtCelular().setText("");
        frmPersona.getTxtCorreo().setText("");
        frmPersona.getTxtNumeroCasa().setText("");
        frmPersona.getTxtPrimerApellido().setText("");
        frmPersona.getTxtPrimerNombre().setText("");
        frmPersona.getTxtReferencia().setText("");
        frmPersona.getTxtSector().setText("");
        frmPersona.getTxtSegundoApellido().setText("");
        frmPersona.getTxtSegundoNombre().setText("");
        frmPersona.getTxtTelefono().setText("");
        frmPersona.getCmbEstadoCivil().setSelectedIndex(0);
        frmPersona.getCmbTipoResidencia().setSelectedIndex(0);
        frmPersona.getCmbIdiomas().setSelectedIndex(0);
        frmPersona.getCmbSexo().setSelectedIndex(0);
        frmPersona.getCmbTipoSangre().setSelectedIndex(0);
        frmPersona.getCmbGenero().setSelectedIndex(0);
        frmPersona.getCmbEtnia().setSelectedIndex(0);

        //Codigo postal
        frmPersona.getCmbNacionalidad().setSelectedIndex(0);
        frmPersona.getCmbProvincia().setSelectedIndex(0);
        frmPersona.getCmbCanton().setSelectedIndex(0);

        frmPersona.getCmbProvinciaReside().setSelectedIndex(0);
        frmPersona.getCmbParroquiaReside().setSelectedIndex(0);
        frmPersona.getCmbPaisReside().setSelectedIndex(0);
        frmPersona.getCmbCantonReside().setSelectedIndex(0);

        //Discapacidad
        frmPersona.getCbxDiscapacidad().setSelected(false);
        frmPersona.getCmbTipoDiscapacidad().setSelectedIndex(0);
        frmPersona.getTxtCarnetConadis().setText("");
        frmPersona.getTxtPorcentaje().setText("");
        //Cargar foto
        frmPersona.getLblFoto().setIcon(null);
        frmPersona.getLblFoto().updateUI();

        //Cargamos los combos de lugar  
        frmPersona.getCmbNacionalidad().setSelectedIndex(0);

        frmPersona.getCmbPaisReside().setSelectedIndex(0);

        //Seteamos el nuevo lugar de una
        frmPersona.getCmbProvinciaReside().setSelectedIndex(0);
        frmPersona.getCmbCantonReside().setSelectedIndex(0);
        frmPersona.getCmbParroquiaReside().setSelectedIndex(0);
        frmPersona.getCbxCategoriaMigratoria().setSelected(false);
        frmPersona.getCmbCategoriaMigratoria().setSelectedIndex(0);
    }

    public void borrarCamposConId() {
        borrarCampos();
        frmPersona.getCmbTipoId().setSelectedIndex(0);
        frmPersona.getTxtIdentificacion().setText("");
    }

    public void pasarFoto(InputStream is) {
        try {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);

            File salida = new File("./foto.png");
            fis = new FileInputStream(salida);
            lonBytes = (int) salida.length();

            System.out.println("Tomada la foto " + fis);
            System.out.println("Longitud " + lonBytes);
        } catch (IOException ex) {
            System.out.println("No se pudo tranformar");
            System.out.println(ex.getMessage());
        }
    }

    public void llamaReportePersona() {
        JasperReport jr;
        String path = "/vista/reportes/repPersona.jasper";
        try {
            Map parametro = new HashMap();
            parametro.put("cedula", frmPersona.getTxtIdentificacion().getText());
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Persona");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    public void botonreportepersona() {
        int s = JOptionPane.showOptionDialog(null,
                "Registro de persona \n"
                + "¿Dessea Imprimir el Registro realizado ?", "REPORTE PERSONAS",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"SI", "NO"}, "NO");
        switch (s) {
            case 0:
                llamaReportePersona();
                break;
            case 1:

                break;
            default:
                break;
        }
    }

}
