package controlador.persona;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.lugar.LugarBD;
import modelo.lugar.LugarMD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.TxtVCedula;
import modelo.validaciones.TxtVCorreo;
import modelo.validaciones.TxtVDireccion;
import modelo.validaciones.TxtVLetras;
import modelo.validaciones.TxtVNumeros;
import modelo.validaciones.TxtVTelefono;
import modelo.validaciones.Validar;
import vista.persona.FrmPersona;
import vista.persona.VtnWebCam;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Lina
 */
public class FrmPersonaCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmPersona frmPersona;
    private final PersonaBD persona;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private TxtVCedula valCe;
    private int numAccion = 0;
    private Image foto;
    private VtnWebCam vtnWebCam;

    private final String[] idiomas = {"Árabe", "Croata", "Francés",
        "Español", "Maltés", "Chino", "Danés", "Vietnamita", "Inglés", "Serbio",
        "Sueco", "Hindi", "Finés", "Bosnia", "Ucraniano", "Japonés", "Portugués",
        "Islandés", "Checo", "Polaco", "Catalán", "Malayo", "Búlgaro", "Rumano",
        "Coreano", "Griego", "Ruso", "Noruego", "Nynorsk", "Húngaro", "Tailandés",
        "Irlandés", "Turco", "Estonio", "Albanés", "Alemán", "Hebreo", "TH",
        "Neerlandés", "Letón", "Italiano", "Eslovaco", "Lituano", "Italiano",
        "Macedonio", "Bielorruso", "Esloveno", "Indonesio",};

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

    public FrmPersonaCTR(VtnPrincipal vtnPrin, FrmPersona frmPersona, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmPersona = frmPersona;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaFrm("Persona");
        //Inicializamos persona
        this.persona = new PersonaBD(conecta);
        this.lug = new LugarBD(conecta);

        vtnPrin.getDpnlPrincipal().add(frmPersona);
        frmPersona.show();
        //Para iniciar los combos de paises 
        cargarPaises();
    }

    public void iniciar() {
        //Desactivamos el campo de identificacion porque debe ingresar primero el tipo de identificacion
        frmPersona.getTxtIdentificacion().setEnabled(false);
        //Ocultamos todos los erores del formulario 
        ocultarErrores();
        cargarIdiomas();
        desactivarDiscapacidad(false);
        //Cuando se realice una accion en alguno de esos combos 
        iniciarValidaciones();
        frmPersona.getCmbNacionalidad().addActionListener(e -> cargarDistritosPais());
        frmPersona.getCmbProvincia().addActionListener(e -> cargarCiudadesDistrito());
        frmPersona.getCmbPaisReside().addActionListener(e -> cargarProvinciasResidencia());
        frmPersona.getCmbProvinciaReside().addActionListener(e -> cargarCantonesProvincia());
        frmPersona.getCmbCantonReside().addActionListener(e -> cargarParroquiaCanton());
        frmPersona.getCmbParroquiaReside().addActionListener(e -> cargarCodigoPostal());
        frmPersona.getCbxDiscapacidad().addActionListener(e -> activarDiscapacidad());
        frmPersona.getBtnBuscarFoto().addActionListener(e -> buscarFoto());
        frmPersona.getBtnCapturarFoto().addActionListener(e -> capturarFotoWebCam());
        frmPersona.getBtnGuardarPersona().addActionListener(e -> guardarPersona());
        //frmPersona.getBtnBuscarPersona().addActionListener(e -> buscarPersona());
        frmPersona.getBtnCancelar().addActionListener(e -> salirBoton());
        // frmPersona.getBtnBuscarPersona().addActionListener(e -> buscarPersona());

        //Accion de buscar una persona  
        frmPersona.getBtnBuscarPersona().addActionListener(e -> consular());
        //Cogemso todos los idiomas del combo  idioma  
        //imprimiIdiomas();

        FocusListener Buscar = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("Entramos en buscar");
            }

            @Override
            public void focusLost(FocusEvent e) {
                buscarIdentificacion();
            }
        };

        frmPersona.getTxtIdentificacion().addFocusListener(Buscar);

        valCe = new TxtVCedula(
                frmPersona.getTxtIdentificacion(), frmPersona.getLblErrorIdentificacion());

        frmPersona.getCmbTipoId().addActionListener(e -> tipoID());
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaFrmFin("Persona");
    }

    public void buscarIdentificacion() {

        boolean error = false;
        String cedula;
        cedula = frmPersona.getTxtIdentificacion().getText();

        if (!cedula.equals("")) {

            if (modelo.validaciones.Validar.esCedula(cedula) == false) {
                error = true;
                frmPersona.getLblErrorIdentificacion().setVisible(true);
            }
            if (error == false) {

                PersonaMD per = persona.buscarPersona(cedula);
                editar = true;

                if (per == null) {
                    JOptionPane.showMessageDialog(null, "Persona no registrada, ingrese persona");
                    numAccion = 0;
                    borrarCampos();
                    ocultarErrores();
                    editar = true;

                } else {
                    editar(per);
                    numAccion = 2;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese una Cedúla Valida");
            }
        }
    }

    private void tipoID() {
        int pos = frmPersona.getCmbTipoId().getSelectedIndex();
        frmPersona.getTxtIdentificacion().setEnabled(false);
        if (pos == 1) {
            frmPersona.getTxtIdentificacion().addKeyListener(valCe);
            //Activamos el campo para ingresar los datos
            frmPersona.getTxtIdentificacion().setEnabled(true);
        } else if (pos == 2) {
            frmPersona.getTxtIdentificacion().removeKeyListener(valCe);
            frmPersona.getTxtIdentificacion().setBorder(
                    javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
            frmPersona.getLblErrorIdentificacion().setVisible(false);
            //Activamos el campo para ingresar los datos
            frmPersona.getTxtIdentificacion().setEnabled(true);
        }
    }

    private void desactivarDiscapacidad(boolean estado) {
        frmPersona.getLblTipoDiscapacidad().setVisible(estado);
        frmPersona.getCmbTipoDiscapacidad().setVisible(estado);
        frmPersona.getLblCarnetConadis().setVisible(estado);
        frmPersona.getTxtCarnetConadis().setVisible(estado);
        frmPersona.getLblPorcentaje().setVisible(estado);
        frmPersona.getTxtPorcentaje().setVisible(estado);
    }

    private void activarDiscapacidad() {
        boolean discapacidad = frmPersona.getCbxDiscapacidad().isSelected();
        desactivarDiscapacidad(discapacidad);

    }

    private void iniciarValidaciones() {

        if (numAccion > 1) {

            //Validar los Combo box
            frmPersona.getCmbIdiomas().addActionListener(new CmbValidar(
                    frmPersona.getCmbIdiomas(), frmPersona.getLblErrorIdioma()));
            frmPersona.getCmbCanton().addActionListener(new CmbValidar(
                    frmPersona.getCmbCanton(), frmPersona.getLblErrorCanton()));
            frmPersona.getCmbCantonReside().addActionListener(new CmbValidar(
                    frmPersona.getCmbCantonReside(), frmPersona.getLblErrorCantonReside()));
            frmPersona.getCmbEstadoCivil().addActionListener(new CmbValidar(
                    frmPersona.getCmbEstadoCivil(), frmPersona.getLblErrorEstadoCivil()));
            frmPersona.getCmbEtnia().addActionListener(new CmbValidar(
                    frmPersona.getCmbEtnia(), frmPersona.getLblErrorEtnia()));
            frmPersona.getCmbGenero().addActionListener(new CmbValidar(
                    frmPersona.getCmbGenero(), frmPersona.getLblErrorGenero()));
            frmPersona.getCmbNacionalidad().addActionListener(new CmbValidar(
                    frmPersona.getCmbNacionalidad(), frmPersona.getLblErrorNacionalidad()));
            frmPersona.getCmbPaisReside().addActionListener(new CmbValidar(
                    frmPersona.getCmbPaisReside(), frmPersona.getLblErrorPaisReside()));
            frmPersona.getCmbParroquiaReside().addActionListener(new CmbValidar(
                    frmPersona.getCmbParroquiaReside(), frmPersona.getLblErrorParroquiaReside()));
            frmPersona.getCmbProvincia().addActionListener(new CmbValidar(
                    frmPersona.getCmbProvincia(), frmPersona.getLblErrorProvincia()));
            frmPersona.getCmbProvinciaReside().addActionListener(new CmbValidar(
                    frmPersona.getCmbProvinciaReside(), frmPersona.getLblErrorProvinciaReside()));
            frmPersona.getCmbSexo().addActionListener(new CmbValidar(
                    frmPersona.getCmbSexo(), frmPersona.getLblErrorSexo()));
            frmPersona.getCmbTipoDiscapacidad().addActionListener(new CmbValidar(
                    frmPersona.getCmbTipoDiscapacidad(), frmPersona.getLblErrorTipoDiscapacidad()));
            frmPersona.getCmbTipoId().addActionListener(new CmbValidar(
                    frmPersona.getCmbTipoId()));
            frmPersona.getCmbTipoResidencia().addActionListener(new CmbValidar(
                    frmPersona.getCmbTipoResidencia(), frmPersona.getLblErrorTipoResidencia()));
            frmPersona.getCmbTipoSangre().addActionListener(new CmbValidar(
                    frmPersona.getCmbTipoSangre(), frmPersona.getLblErrorTipoSangre()));
        }
        //Validar los txt
        frmPersona.getTxtCallePrincipal().addKeyListener(new TxtVDireccion(
                frmPersona.getTxtCallePrincipal(), frmPersona.getLblErrorCallePrin()));
        frmPersona.getTxtCalleSecundaria().addKeyListener(new TxtVDireccion(
                frmPersona.getTxtCalleSecundaria(), frmPersona.getLblErrorCalleSec()));
        frmPersona.getTxtCelular().addKeyListener(new TxtVTelefono(
                frmPersona.getTxtCelular(), frmPersona.getLblErrorCelular()));
        frmPersona.getTxtCodigoPostal().addKeyListener(new TxtVNumeros(
                frmPersona.getTxtCodigoPostal(), frmPersona.getLblErrorCodigoPostal()));

        frmPersona.getTxtPorcentaje().addKeyListener(new TxtVNumeros(
                frmPersona.getTxtPorcentaje(), frmPersona.getLblErrorPorcentaje()));
        frmPersona.getTxtPrimerApellido().addKeyListener(new TxtVLetras(
                frmPersona.getTxtPrimerApellido(), frmPersona.getLblErrorPriApellido()));
        frmPersona.getTxtPrimerNombre().addKeyListener(new TxtVLetras(
                frmPersona.getTxtPrimerNombre(), frmPersona.getLblErrorPriNombre()));
        frmPersona.getTxtReferencia().addKeyListener(new TxtVLetras(
                frmPersona.getTxtReferencia(), frmPersona.getLblErrorReferencia()));
        frmPersona.getTxtSector().addKeyListener(new TxtVLetras(
                frmPersona.getTxtSector(), frmPersona.getLblErrorSector()));
        frmPersona.getTxtSegundoApellido().addKeyListener(new TxtVLetras(
                frmPersona.getTxtSegundoApellido(), frmPersona.getLblErrorSegApellido()));
        frmPersona.getTxtSegundoNombre().addKeyListener(new TxtVLetras(
                frmPersona.getTxtSegundoNombre(), frmPersona.getLblErrorSegNombre()));
        frmPersona.getTxtTelefono().addKeyListener(new TxtVTelefono(
                frmPersona.getTxtTelefono(), frmPersona.getLblErrorTelefono()));
        frmPersona.getTxtNumeroCasa().addKeyListener(new TxtVDireccion(
                frmPersona.getTxtNumeroCasa(), frmPersona.getLblErrorNumeroCasa()));
        frmPersona.getTxtCarnetConadis().addKeyListener(new TxtVNumeros(
                frmPersona.getTxtCarnetConadis(), frmPersona.getLblErrorCarnetConadis()));
        frmPersona.getTxtCorreo().addKeyListener(new TxtVCorreo(
                frmPersona.getTxtCorreo(), frmPersona.getLblErrorCorreo()));

        //pendiente carnet conadis      frmPersona.getTxtCarnetConadis().addKeyListener(new txt);
        //pendiente correo              frmPersona.getTxtCorreo().addKeyListener(new txtCorreo);
        //pendiente numero de casa      frmPersona.getTxtNumeroCasa().addKeyListener(new txt);
    }

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

    //Metodo para capturar una foto desde WebCam
    private void capturarFotoWebCam() {
        vtnWebCam = new VtnWebCam(vtnPrin, false);
        vtnWebCam.setVisible(true);
        iniciarCamara();
        // byte[] imagen = vtnWebCam.getPanelCam().getBytes();
        //vtnWebCam.getBtnGuardarFoto().addActionListener(e-> guardarFotoWeb());
    }
    
    public void iniciarCamara(){
    
        ActionListener capturarFoto = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                byte[] imagen = vtnWebCam.getPanelCam().getBytes();
                InputStream is = new ByteArrayInputStream(imagen);
                BufferedImage bi = ImageIO.read(is);
                    ImageIcon icono = new ImageIcon(bi);
                    foto = icono.getImage().getScaledInstance( 
                            vtnWebCam.getLbl_Imagen().getWidth(), vtnWebCam.getLbl_Imagen().getHeight(), Image.SCALE_SMOOTH);
                    vtnWebCam.getLbl_Imagen().setIcon(new ImageIcon(foto));
                } catch (IOException ex) {
                    Logger.getLogger(FrmPersonaCTR.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        };
        
        ActionListener guardarFoto = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                Image foto_Nueva;
                foto_Nueva = foto.getScaledInstance(frmPersona.getLblFoto().getWidth(), frmPersona.getLblFoto().getHeight(), Image.SCALE_SMOOTH);
                frmPersona.getLblFoto().setIcon(new ImageIcon(foto_Nueva));
                vtnWebCam.dispose();
            }
        };
        
        ActionListener cancelarFoto = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                vtnWebCam.setVisible(false);
            }
            
        };
        
        vtnWebCam.getBtnCapturarFoto().addActionListener(capturarFoto);
        vtnWebCam.getBtnGuardarFoto().addActionListener(guardarFoto);
        vtnWebCam.getBtnCancelar().addActionListener(cancelarFoto);
    }
        
    
                

    public void guardarPersona() {

        //Fecha actual usada para validaciones  
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaNacimiento = fechaActual;
        //Para validar todo  
        boolean guardar = true;
        //unicamente tocoa validar eso es lo feo :$$$$ 
        //Todas las variables del formulario
        String identificacion = "aa", priNombre = "aa", segNombre = "aaa",
                priApellido = "aaa", segApellido = "aaa",
                fechaNac = "aa", estadoCivil = "aaaa", tipoSangre = "aaa", genero = "aaa",
                sexo = "M", etnia = "aaa", carnetConadis = "aaaa",
                tipoDiscapacidad = "aaaa", porcentajeDiscapacidad = "aaaa",
                idiomaRaiz = "aaa", telefono = "aaaa", callePrin = "aaaa",
                calleSec = "aaa", referencia = "aaaa",
                celular = "aaaa", numCasa = "aaa", sector = "aaa",
                zonaResidencia = "aaaaa", correo = "aaaa";

        boolean discapacidad;
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
        if (!Validar.esLetras(segNombre)) {
            guardar = false;
            frmPersona.getLblErrorSegNombre().setVisible(true);
        } else {
            frmPersona.getLblErrorSegNombre().setVisible(false);
        }
        priApellido = frmPersona.getTxtPrimerApellido().getText().trim().toUpperCase();
        if (!Validar.esLetras(priApellido)) {
            guardar = false;
            frmPersona.getLblErrorPriApellido().setVisible(true);

        } else {
            frmPersona.getLblErrorPriApellido().setVisible(false);
        }
        segApellido = frmPersona.getTxtSegundoApellido().getText().trim().toUpperCase();
        if (!Validar.esLetras(segApellido)) {
            guardar = false;
            frmPersona.getLblErrorSegApellido().setVisible(true);
        } else {
            frmPersona.getLblErrorSegApellido().setVisible(false);
        }

        fechaNac = frmPersona.getJdcFechaNacimiento().getText().toUpperCase();
        //Auxiliar para transformar de tipo texto a tipo LocalDate
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

        if (frmPersona.getCmbTipoSangre().getSelectedIndex() < 1) {
            guardar = false;
            frmPersona.getLblErrorTipoSangre().setVisible(true);
        } else {
            tipoSangre = frmPersona.getCmbTipoSangre().getSelectedItem().toString();
            frmPersona.getLblErrorTipoSangre().setVisible(false);
        }

        discapacidad = frmPersona.getCbxDiscapacidad().isSelected();
        if (discapacidad) {
            carnetConadis = frmPersona.getTxtCarnetConadis().getText().trim().toUpperCase();

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

        }

        String idioma[];

        if (frmPersona.getCmbIdiomas().getSelectedIndex() < 1) {
            guardar = false;
            frmPersona.getLblErrorIdioma().setVisible(true);
        } else {
            idiomaRaiz = frmPersona.getCmbIdiomas().getSelectedItem().toString();
            System.out.println("este es el idioma " + idiomaRaiz);
            frmPersona.getLblErrorIdioma().setVisible(false);
        }

        telefono = frmPersona.getTxtTelefono().getText().trim().toUpperCase();
        if (!Validar.esTelefono(telefono)) {
            guardar = false;
            frmPersona.getLblErrorTelefono().setVisible(true);
        } else {
            frmPersona.getLblErrorTelefono().setVisible(false);
        }

        //para saber como me devuelve el dato
        //Esto deberia ser automatico 
        String fechaReg;
        //Este dato no lo tenemos en base de datos 
        //String codigoPostal = frmPersona.getTxtCodigoPostal().getText();

        callePrin = frmPersona.getTxtCallePrincipal().getText().trim().toUpperCase();
        if (!Validar.esLetras(callePrin)) {
            //Mostrar error
            guardar = false;
        } else {
            //Ocultar error
        }

        calleSec = frmPersona.getTxtCalleSecundaria().getText().trim().toUpperCase().toUpperCase();

        referencia = frmPersona.getTxtReferencia().getText().trim().toUpperCase();

        celular = frmPersona.getTxtCelular().getText().trim().toUpperCase();
        if (!Validar.esTelefono(celular)) {
            guardar = false;
            frmPersona.getLblErrorCelular().setVisible(true);
        } else {
            frmPersona.getLblErrorCelular().setVisible(false);
        }

        // Lugares en donde reside y vive 
        LugarMD lugarNac = null, lugarRes = null;

        //Esto igual deberiamos hacerlo de otra manera.
        //Aqui preguntamos siempre que sea mayor a la posicion 0 porque 
        //Ahi esta el texto seleccione  
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
                    lugarRes = parroquias.get(posPr - 1);
                } else {
                    frmPersona.getLblErrorParroquiaReside().setVisible(true);
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
        correo = frmPersona.getTxtCorreo().getText().trim().toUpperCase();

        if (guardar) {

            // PersonaBD per = new PersonaBD();
            //Llenar directo por el constructor
            PersonaBD per = new PersonaBD(conecta);

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

            System.out.println("Editar " + editar);

            if (editar) {
                if (idPersona > 0) {
                    System.out.println("idPersona" + idPersona);
                    if (fis != null) {
                        per.editarPersonaConFoto(idPersona);
                        JOptionPane.showMessageDialog(null, "Datos Guardados");
                        borrarCampos();
                        ocultarErrores();
                    } else {
                        per.editarPersona(idPersona);
                        JOptionPane.showMessageDialog(null, "Datos Guardados");
                        borrarCampos();
                        ocultarErrores();
                    }
                }
            } else {
                if (fis != null) {
                    per.insertarPersonaConFoto();
                    JOptionPane.showMessageDialog(null, "Datos Guardados");
                    borrarCampos();
                    ocultarErrores();
                } else {
                    per.insertarPersona();
                    JOptionPane.showMessageDialog(null, "Datos Guardados");
                    borrarCampos();
                    ocultarErrores();
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Existen errores en los formularios");
        }

    }

    public void salirBoton() {
        frmPersona.dispose();
    }

    private void consular() {
        String identificacion = frmPersona.getTxtIdentificacion().getText();
        PersonaMD per = persona.buscarPersona(identificacion);
        if (per != null) {
            editar(per);
        }
    }

    public void editar(PersonaMD per) {
        //Seteamos los datos en el formulario  
        activarDiscapacidad();
        boolean discapacidad;
        idPersona = per.getIdPersona();
        System.out.println("id" + idPersona);
        editar = true;

        frmPersona.getCmbTipoId().setSelectedItem(per.getIdPersona());
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
        frmPersona.getCmbEstadoCivil().setSelectedItem(per.getEstadoCivil());
        frmPersona.getCmbTipoResidencia().setSelectedItem(per.getTipoResidencia());
        frmPersona.getCmbIdiomas().setSelectedItem(per.getIdiomaRaiz());

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
        //Codigo postal
        frmPersona.getCmbNacionalidad().setSelectedItem(per.getLugarNatal());
        frmPersona.getCmbProvincia().setSelectedItem(per.getLugarNatal());
        frmPersona.getCmbCanton().setSelectedItem(per.getLugarNatal());

        frmPersona.getCmbProvinciaReside().setSelectedItem(per.getLugarResidencia());
        frmPersona.getCmbParroquiaReside().setSelectedItem(per.getLugarResidencia());
        frmPersona.getCmbPaisReside().setSelectedItem(per.getLugarResidencia());
        frmPersona.getCmbCantonReside().setSelectedItem(per.getLugarResidencia());

        //Discapacidad
        frmPersona.getCbxDiscapacidad().setSelected(per.isDiscapacidad());
        frmPersona.getCmbTipoDiscapacidad().setSelectedItem(per.getTipoDiscapacidad());
        frmPersona.getTxtCarnetConadis().setText(per.getCarnetConadis());
        frmPersona.getTxtPorcentaje().setText(per.getPorcentajeDiscapacidad() + "");

        //Cargar foto
        if (per.getFoto() != null) {
            Image icono = per.getFoto().getScaledInstance(frmPersona.getLblFoto().getWidth(),
                    frmPersona.getLblFoto().getHeight(), Image.SCALE_SMOOTH);
            frmPersona.getLblFoto().setIcon(new ImageIcon(icono));
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

        System.out.println("Nivel de lugar residencia  " + per.getLugarResidencia().getNivel());

        int nvlLugarRes = Integer.parseInt(per.getLugarResidencia().getNivel());
        String parroquia = null, canton = null, provincia = null;
        if (nvlLugarRes == 4) {
            //Guardamos el nombre del parroquia para luego selecionarlo
            parroquia = per.getLugarResidencia().getNombre();
            per.setLugarResidencia(lug.buscar(per.getLugarResidencia().getIdReferencia()));
        }
        //Ahora preguntamos nuevamente el nivel del lugar 
        nvlLugarRes = Integer.parseInt(per.getLugarResidencia().getNivel());
        if (nvlLugarRes == 3) {
            //Guardamos el nombre del canton para luego selecionarlo
            canton = per.getLugarResidencia().getNombre();
            per.setLugarResidencia(lug.buscar(per.getLugarResidencia().getIdReferencia()));
        }
        //Ahora preguntamos nuevamente el nivel del lugar 
        nvlLugarRes = Integer.parseInt(per.getLugarResidencia().getNivel());
        if (nvlLugarRes == 2) {
            //Guardamos el nombre del provincia para luego selecionarlo
            provincia = per.getLugarResidencia().getNombre();
            per.setLugarResidencia(lug.buscar(per.getLugarResidencia().getIdReferencia()));
        }
        //Ahora preguntamos nuevamente el nivel 
        nvlLugarRes = Integer.parseInt(per.getLugarResidencia().getNivel());
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

    }

    //Metodo para ocultar errores
    public void ocultarErrores() {
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
        frmPersona.getCmbIdiomas().addItem("Seleccione");
        for (String i : idiomas) {
            frmPersona.getCmbIdiomas().addItem(i.toUpperCase());
        }
    }

    //Metodos para los combos de residencia y ciudades natales
    private void cargarPaises() {
        paises = lug.buscarPaises();
        paises = ponerPrimeroPais(paises, "ECUADOR");
        frmPersona.getCmbNacionalidad().removeAllItems();
        frmPersona.getCmbNacionalidad().addItem("Seleccione");
        //Cargamos el otro combo de paises 
        frmPersona.getCmbPaisReside().removeAllItems();
        frmPersona.getCmbPaisReside().addItem("Seleccione");
        paises.forEach((l) -> {
            frmPersona.getCmbNacionalidad().addItem(l.getNombre());
            frmPersona.getCmbPaisReside().addItem(l.getNombre());
        });
    }

    //Reordenamos paises para que ecuador este primero  
    private ArrayList<LugarMD> ponerPrimeroPais(ArrayList<LugarMD> lugares, String pais) {
        ArrayList<LugarMD> lugaresOrdenados = new ArrayList();
        lugares.forEach((l) -> {
            if (l.getNombre().equalsIgnoreCase(pais)) {
                lugaresOrdenados.add(l);
            }
        });

        lugares.forEach((l) -> {
            if (!l.getNombre().equalsIgnoreCase(pais)) {
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
        } else {
            frmPersona.getLblErrorParroquiaReside().setVisible(true);
        }
    }

    public void cargarCmbLugares(JComboBox cmb, ArrayList<LugarMD> lug) {
        cmb.removeAllItems();
        cmb.addItem("Seleccione");
        lug.forEach((l) -> {
            cmb.addItem(l.getNombre());
        });
        cmb.addItem("OTRO");
    }

    public void cargarCodigoPostal() {
        int posPa = frmPersona.getCmbParroquiaReside().getSelectedIndex();
        if (posPa > 0 && posPa < parroquias.size()) {
            frmPersona.getTxtCodigoPostal().setText(parroquias.get(posPa - 1).getCodigo());
        } else {
            frmPersona.getTxtCodigoPostal().setText("");
        }
    }

    private void borrarCampos() {

        //frmPersona.getCmbTipoId().setSelectedIndex(0);
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

    }

}


