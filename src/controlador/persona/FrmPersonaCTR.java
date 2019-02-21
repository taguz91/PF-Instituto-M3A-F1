package controlador.persona;

import java.awt.Image;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.persona.PersonaBD;
import modelo.validaciones.Validar;
import vista.persona.FrmPersona;
import vista.persona.VtnPersona;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Lina
 */
public class FrmPersonaCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmPersona frmPersona;
    private final VtnPersona vtnPer;
    private PersonaBD per; 

    public FrmPersonaCTR(VtnPrincipal vtnPrin, FrmPersona frmPersona, VtnPersona vtnPer) {
        this.vtnPrin = vtnPrin;
        this.frmPersona = frmPersona;
        this.vtnPer = vtnPer;
        //Inicializamos persona
        this.per = new PersonaBD(); 
        vtnPrin.getDpnlPrincipal().add(frmPersona);
        frmPersona.show();
    }

    public void iniciar() {
        frmPersona.getBtnBuscarFoto().addActionListener(e -> buscarFoto());
        frmPersona.getBtnGuardarPersona().addActionListener(e -> pruebas());
      //  frmPersona.getBtnBuscarPersona().addActionListener(e -> buscarPersona());
        frmPersona.getBtnCancelar().addActionListener(e -> salirBoton());
    }

    public void buscarFoto() {
        frmPersona.getLblFoto().setIcon(null);
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int estado = j.showOpenDialog(null);
        if (estado == JFileChooser.APPROVE_OPTION) {
            try {
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

    public void pruebas() {
        System.out.println("CADENAAAA ");
        String idiomaRaiz = frmPersona.getCmbIdiomas().getSelectedItem().toString();
        System.out.println(idiomaRaiz + "htdfrggf");
        String idioma[] = idiomaRaiz.split(" ");
        System.out.println("este solo e sel idioma: " + idioma[0]);
    }

    public void guardarPersona() {

        //Fecha actual usada para validaciones  
        LocalDate fechaActual = LocalDate.now();
        //Para validar todo  
        boolean guardar = true;
        //unicamente tocoa validar eso es lo feo :$$$$ 

        //Todas las variables del formulario
        String identificacion, priNombre, segNombre, priApellido, segApellido,
                fechaNac, estadoCivil, tipoSangre, genero, sexo, etnia, carnetConadis = null,
                tipoDiscapacidad, porcentajeDiscapacidad, idiomaRaiz = null, telefono,
                callePrin, calleSec, referencia, celular, numCasa, sector,
                zonaResidencia, correo;

        boolean discapcidad;
        
        identificacion = frmPersona.getTxtIdentificacion().getText();

        int tipoIdentifi = frmPersona.getCmbTipoId().getSelectedIndex();
        if (tipoIdentifi == 0) {
            if (!Validar.esCedula(identificacion)) {
                guardar = false;
                //Mostrar error 
            } else {
                //Ocultamos el lbl de error 
            }
        } else {
            //Validar cuando es pasaporte 
        }

        priNombre = frmPersona.getTxtPrimerNombre().getText();
        if (!Validar.esLetras(priNombre)) {
            guardar = false;
            //Mostramos el error. 
        } else {
            //ocualta el error lbl de error 
        }
        segNombre = frmPersona.getTxtSegundoNombre().getText();
        if (!Validar.esLetras(segNombre)) {
            guardar = false;
            //Mostrar el error 
        } else {
            //ocualta el error 
        }
        priApellido = frmPersona.getTxtPrimerApellido().getText();
        if (!Validar.esLetras(priApellido)) {
            guardar = false;
            //Mostrar error
        } else {
            //Ocultamos error
        }
        if (!Validar.esLetras(priApellido)) {
            guardar = false;
            //Mostrar error
        } else {
            //Ocultamos error
        }
        segApellido = frmPersona.getTxtSegundoApellido().getText();
        if (!Validar.esLetras(segApellido)) {
            guardar = false;
            //Mostrar error

        } else {
            //Ocultamos error
        }

        fechaNac = frmPersona.getJdcFechaNacimiento().getText();
        //Auxiliar para transformar de tipo texto a tipo LocalDate
        String fec[] = fechaNac.split("/");

        if (Integer.parseInt(fec[2]) > fechaActual.getYear()
                || Integer.parseInt(fec[2]) > (fechaActual.getYear() - 16)) {
            guardar = false;
            //Mostramos error 
        } else {
            LocalDate fecha = LocalDate.of(Integer.parseInt(fec[2]),
                    Integer.parseInt(fec[1]), Integer.parseInt(fec[0]));
            //ocultamos el error 
        }

        if (frmPersona.getCmbEstadoCivil().getSelectedIndex() < 1) {
            guardar = false;
            //Mostramos error 
        } else {
            estadoCivil = frmPersona.getCmbEstadoCivil().getSelectedItem().toString();
            //ocultamos el error 
        }

        if (frmPersona.getCmbGenero().getSelectedIndex() < 1) {
            guardar = false;
            //Mostrar error
        } else {
            genero = frmPersona.getCmbGenero().getSelectedItem().toString();
            //Ocultar error
        }

        if (frmPersona.getCmbSexo().getSelectedIndex() < 1) {
            guardar = false;
            //Mostrar error
        } else {
            sexo = frmPersona.getCmbSexo().getSelectedItem().toString();
            //Ocultar error
        }

        if (frmPersona.getCmbEtnia().getSelectedIndex() < 1) {
            guardar = false;
            //Mostrar error
        } else {
            etnia = frmPersona.getCmbEtnia().getSelectedItem().toString();
            //Ocultar error
        }

        if (frmPersona.getCmbTipoSangre().getSelectedIndex() < 1) {
            guardar = false;
            //mostrar error

        } else {
            tipoSangre = frmPersona.getCmbTipoSangre().getSelectedItem().toString();
            //Ocultamos el error
        }

        discapcidad = frmPersona.getCbxDiscapacidad().isSelected();
        if (discapcidad) {

            carnetConadis = frmPersona.getTxtCarnetConadis().getText();

            tipoDiscapacidad = frmPersona.getCmbTipoDiscapacidad().getSelectedItem().toString();
            
            porcentajeDiscapacidad = frmPersona.getTxtPorcentaje().getText();
            if (!Validar.esNumeros(porcentajeDiscapacidad)) {
                guardar = false;
                //mostramos error 
            } else {
                //ocultamos error

            }

        }

        if(frmPersona.getCmbIdiomas().getSelectedIndex() < 1){
            //Mostrar error
            guardar = false;
            
        }else{
            //Ocultar error
        idiomaRaiz = frmPersona.getCmbIdiomas().getSelectedItem().toString();
        
        }
        
        
        telefono = frmPersona.getTxtTelefono().getText();
        if(!Validar.esNumeros(telefono)){
            //Mostrar error
            guardar = false;
        }else{
            //Ocultar error
            
        }
        
        //para saber como me devuelve el dato
        System.out.println(idiomaRaiz + "htdfrggf");
        String idioma[] = idiomaRaiz.split(" ");
        System.out.println("este solo e sel idioma: " + idioma[0]);

        //Esto deberia ser automatico 
        String fechaReg;
        //Este dato no lo tenemos en base de datos 
        //String codigoPostal = frmPersona.getTxtCodigoPostal().getText();

        callePrin = frmPersona.getTxtCallePrincipal().getText();
        if(!Validar.esLetras(callePrin)){
            //Mostrar error
            guardar = false;
        }else{
            //Ocultar error
        }
        
        calleSec = frmPersona.getTxtCalleSecundaria().getText();
        if(!Validar.esLetras(calleSec)){
            //Mostrar error
            guardar = false;
            
        }else{
            //Ocultar error
        }

        
        referencia = frmPersona.getTxtReferencia().getText();
        if(!Validar.esLetras(referencia)){
            //Mostrar error
            guardar = false;
        }else{
            //Ocultar error
        }
        
        celular = frmPersona.getTxtCelular().getText();
        if(!Validar.esNumeros(celular)){
            //mostrar error
            guardar = false;
        }else{
            //ocualtar error
        }

        //Esto creo que deberiamos cambiarlo para hacerlo de otra manera 
        String cantonReside = frmPersona.getCmbCantonReside().getSelectedItem().toString();
        String provinciaReside = frmPersona.getCmbProvinciaReside().getSelectedItem().toString();
        String parroquiaReside = frmPersona.getCmbParroquiaReside().getSelectedItem().toString();
        numCasa = frmPersona.getTxtNumeroCasa().getText();
        sector = frmPersona.getTxtSector().getText();
        zonaResidencia = frmPersona.getCmbTipoResidencia().getSelectedItem().toString();

        //Esto igual deberiamos hacerlo de otra manera.
        String nacionalidad = frmPersona.getCmbNacionalidad().getSelectedItem().toString();
        String pronvicia = frmPersona.getCmbProvincia().getSelectedItem().toString();
        String canton = frmPersona.getCmbCanton().getSelectedItem().toString();

        //Crear validacion para correo
        correo = frmPersona.getTxtCorreo().getText();

        if (guardar) {
            //Llenar directo por el constructor
            PersonaBD persona = new PersonaBD();
           
//           String identificacion, priNombre, segNombre, priApellido, segApellido,
//                fechaNac, estadoCivil, tipoSangre, genero, sexo, etnia, carnetConadis = null,
//                tipoDiscapacidad, porcentajeDiscapacidad, idiomaRaiz = null, telefono,
//                callePrin, calleSec, referencia, celular, numCasa, sector,
//                zonaResidencia, correo;
//
//        boolean discapcidad;
//        
           // PersonaBD persona = new PersonaBD(, tipo, lugarNatal, lugarResidencia, foto, identificacion, priApellido, segApellido, priNombre, segNombre, fechaActual, genero, 0, estadoCivil, etnia, idiomaRaiz, tipoSangre, telefono, celular, correo, fechaActual, discapcidad, tipoDiscapacidad, 0, carnetConadis, callePrin, numCasa, calleSec, referencia, sector, idiomaRaiz, zonaResidencia, discapcidad)
            persona.setCallePrincipal(callePrin);
            persona.setCalleSecundaria(calleSec);
            persona.setCarnetConadis(carnetConadis);
            
             
        } else {
            System.out.println("Existen errores en los formularios");
        }

    }

    public void buscarPersona(String aguja ) {
        per = per.buscarPersonaCedula(aguja);
             if(per != null){
                 frmPersona.getTxtPrimerNombre().setText(per.getPrimerNombre());
                 
                 
             }else{
                 
             }
       
    }

    public void salirBoton() {

    }
}
