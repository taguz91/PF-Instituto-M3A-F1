package controlador.persona;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import vista.persona.FrmPersona;
import vista.persona.VtnWebCam;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class WebCamCTR {

    private final VtnWebCam vtnWebCam;
    private final FrmPersona frmPersona;
    private final FrmPersonaCTR ctrFrmPersona;
    private InputStream is;
    private FileInputStream fis; 
    private Image foto = null;

    public WebCamCTR(FrmPersona frmPersona, FrmPersonaCTR ctrFrmPersona, VtnPrincipal vtnPrin) {
        this.frmPersona = frmPersona;
        this.ctrFrmPersona = ctrFrmPersona;
        this.vtnWebCam = new VtnWebCam(vtnPrin, false);
        vtnWebCam.setLocationRelativeTo(vtnPrin);
        vtnWebCam.setVisible(true);
    }

    public void iniciarCamara() {
        vtnWebCam.getBtnCapturarFoto().addActionListener(e -> capturarFoto());
        vtnWebCam.getBtnGuardarFoto().addActionListener(e -> guardarFoto());
        vtnWebCam.getBtnCancelar().addActionListener(e -> cancelarFoto());
    }

    public void capturarFoto() {
        //System.out.println("Se dio click en capturar foto");
        try {
            byte[] imagen = vtnWebCam.getPanelCam().getBytes();
            if (imagen != null) {
                is = new ByteArrayInputStream(imagen);
                BufferedImage bi = ImageIO.read(is);
                ImageIcon icono = new ImageIcon(bi);
                foto = icono.getImage().getScaledInstance(
                        vtnWebCam.getLbl_Imagen().getWidth(), vtnWebCam.getLbl_Imagen().getHeight(), Image.SCALE_SMOOTH);
                vtnWebCam.getLbl_Imagen().setIcon(new ImageIcon(foto));
                //Guardamos en un archivo
                ImageIO.write(bi, "png", new File("./foto.png"));
            }
        } catch (IOException ex) {
            Logger.getLogger(FrmPersonaCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void guardarFoto() {
        //System.out.println("Se dio click en guardar foto");
        if (foto != null && is != null) {

            Image foto_Nueva;
            foto_Nueva = foto.getScaledInstance(frmPersona.getLblFoto().getWidth(), frmPersona.getLblFoto().getHeight(), Image.SCALE_SMOOTH);
            frmPersona.getLblFoto().setIcon(new ImageIcon(foto_Nueva));
            cancelarFoto();
            ctrFrmPersona.pasarFoto(is);
            
        }

    }

    private void cancelarFoto() {
        vtnWebCam.dispose();
        System.out.println("Se dio click en cancelar");
    }

}
