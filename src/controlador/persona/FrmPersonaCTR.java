package controlador.persona;

import java.awt.Image;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
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
        String identificacion = frmPersona.getTxtIdentificacion().getText(); 
        String priNombre = frmPersona.getTxtPrimerNombre().getText(); 
        String segNombre = frmPersona.getTxtSegundoNombre().getText(); 
        String priApellido = frmPersona.getTxtPrimerApellido().getText(); 
        String segApellido = frmPersona.getTxtSegundoApellido().getText(); 
        String fechaNac = frmPersona.getJdcFechaNacimiento().getDateFormat().getCalendar().toString(); 
        String 
        System.out.println("Fecha: "+fechaNac);
        fechaNac = frmPersona.getJdcFechaNacimiento().getText();
        System.out.println("Fecha nacimiento: "+fechaNac);
        String fec [] = fechaNac.split("/"); 
        System.out.println(fec[0]+" | "+ fec[1] + " | "+ fec[2]);
        LocalDate fecha = LocalDate.of(Integer.parseInt(fec[2]),
                Integer.parseInt(fec[1]), Integer.parseInt(fec[0])); 
        System.out.println(fecha);
        System.out.println(fecha.getYear());
    }
   

    public void buscarPersona() {

    }

    public void salirBoton() {
        
    }
}
