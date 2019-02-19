package controlador.persona;

import java.awt.Image;
import java.io.IOException;
import java.time.LocalDate;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import vista.persona.FrmPersona;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Lina
 */
public class FrmPersonaCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmPersona frmPersona;

    public FrmPersonaCTR(VtnPrincipal vtnPrin, FrmPersona frmPersona) {
        this.vtnPrin = vtnPrin;
        this.frmPersona = frmPersona;

        vtnPrin.getDpnlPrincipal().add(frmPersona);
        frmPersona.show();
    }

    public void iniciar() {
        frmPersona.getBtnBuscarFoto().addActionListener(e -> buscarFoto());
        frmPersona.getBtnGuardarPersona().addActionListener(e -> guardarPersona());
        frmPersona.getBtnBuscarPersona().addActionListener(e -> buscarPersona());
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

    public void guardarPersona() {
        //Fecha actual usada para validaciones  
        LocalDate fechaActual = LocalDate.now();
        //Para validar todo  
        boolean guardar = true;
        //unicamente tocoa validar eso es lo feo :$$$$

        String identificacion = frmPersona.getTxtIdentificacion().getText();
        String priNombre = frmPersona.getTxtPrimerNombre().getText();
        String segNombre = frmPersona.getTxtSegundoNombre().getText();
        String priApellido = frmPersona.getTxtPrimerApellido().getText();
        String segApellido = frmPersona.getTxtSegundoApellido().getText();
        String fechaNac = frmPersona.getJdcFechaNacimiento().getText();
        String fec[] = fechaNac.split("/");
        LocalDate fecha = LocalDate.of(Integer.parseInt(fec[2]),
                Integer.parseInt(fec[1]), Integer.parseInt(fec[0]));
        String estadoCivil = frmPersona.getCmbEstadoCivil().getSelectedItem().toString();
        String genero = frmPersona.getCmbGenero().getSelectedItem().toString();
        String sexo = frmPersona.getCmbSexo().getSelectedItem().toString();
        String etnia = frmPersona.getCmbEtnia().getSelectedItem().toString();
        String tipoSangre = frmPersona.getCmbTipoSangre().getSelectedItem().toString();
        boolean discapcidad = frmPersona.getCbxDiscapacidad().isSelected();
        String carnetConadis = frmPersona.getTxtCarnetConadis().getText();
        String tipoDiscapacidad = frmPersona.getCmbTipoDiscapacidad().getSelectedItem().toString();
        String porcentajeDiscapacidad = frmPersona.getTxtPorcentaje().getText();
        String idiomaRaiz = frmPersona.getCmbIdiomas().getSelectedItem().toString();
        String telefono = frmPersona.getTxtTelefono().getText();
        //para saber como me devuelve el dato
        System.out.println(idiomaRaiz);
        
        //Esto deberia ser automatico 
        String fechaReg;
        //Este dato no lo tenemos en base de datos 
        String codigoPostal = frmPersona.getTxtCodigoPostal().getText();

        String callePrin = frmPersona.getTxtCallePrincipal().getText();
        String referencia = frmPersona.getTxtReferencia().getText();
        String celular = frmPersona.getTxtCelular().getText();
        //Esto creo que deberiamos cambiarlo para hacerlo de otra manera 
        String cantonReside = frmPersona.getCmbCantonReside().getSelectedItem().toString();
        String provinciaReside = frmPersona.getCmbProvinciaReside().getSelectedItem().toString();
        String parroquiaReside = frmPersona.getCmbParroquiaReside().getSelectedItem().toString();
        String numCasa = frmPersona.getTxtNumeroCasa().getText();
        String sector = frmPersona.getTxtSector().getText();
        String zonaResidencia = frmPersona.getCmbTipoResidencia().getSelectedItem().toString();

        //Esto igual deberiamos hacerlo de otra manera.
        String nacionalidad = frmPersona.getCmbNacionalidad().getSelectedItem().toString();
        String pronvicia = frmPersona.getCmbProvincia().getSelectedItem().toString();
        String canton = frmPersona.getCmbCanton().getSelectedItem().toString();

        String correo = frmPersona.getTxtCorreo().getText();

        System.out.println(idiomaRaiz);
        if (guardar) {

        } else {
            System.out.println("Existen errores en los formularios");
        }

    }

    public void buscarPersona() {

    }

    public void salirBoton() {

    }
}
