package controlador.persona;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
    private Image foto;

    public WebCamCTR(FrmPersona frmPersona, FrmPersonaCTR ctrFrmPersona, VtnPrincipal vtnPrin) {
        this.frmPersona = frmPersona;
        this.ctrFrmPersona = ctrFrmPersona; 
        this.vtnWebCam = new VtnWebCam(vtnPrin, true);
        vtnWebCam.setLocationRelativeTo(vtnPrin);
    }

    public void iniciarCamara() {
        vtnWebCam.getBtnCapturarFoto().addActionListener(e -> capturarFoto());
        vtnWebCam.getBtnGuardarFoto().addActionListener(e -> guardarFoto());
        vtnWebCam.getBtnCancelar().addActionListener(e -> cancelarFoto());
    }

    public void capturarFoto() {
        try {
            byte[] imagen = vtnWebCam.getPanelCam().getBytes();
            is = new ByteArrayInputStream(imagen);
            BufferedImage bi = ImageIO.read(is);
            ImageIcon icono = new ImageIcon(bi);
            foto = icono.getImage().getScaledInstance(
                    vtnWebCam.getLbl_Imagen().getWidth(), vtnWebCam.getLbl_Imagen().getHeight(), Image.SCALE_SMOOTH);
            vtnWebCam.getLbl_Imagen().setIcon(new ImageIcon(foto));
        } catch (IOException ex) {
            Logger.getLogger(FrmPersonaCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void guardarFoto() {
        Image foto_Nueva;
        foto_Nueva = foto.getScaledInstance(frmPersona.getLblFoto().getWidth(), frmPersona.getLblFoto().getHeight(), Image.SCALE_SMOOTH);
        frmPersona.getLblFoto().setIcon(new ImageIcon(foto_Nueva));
        vtnWebCam.dispose();
    }

    private void cancelarFoto() {
        vtnWebCam.dispose();
    }

}
