 package controlador.persona;

import com.github.sarxos.webcam.WebcamShutdownHook;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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
    private boolean camaraActiva = false; 

    public WebCamCTR(FrmPersona frmPersona, FrmPersonaCTR ctrFrmPersona, VtnPrincipal vtnPrin) {
        this.frmPersona = frmPersona;
        this.ctrFrmPersona = ctrFrmPersona;
        this.vtnWebCam = new VtnWebCam(vtnPrin, false);
        vtnWebCam.setLocationRelativeTo(vtnPrin);
        vtnWebCam.setVisible(true);
    }

    public void iniciarCamara() {
        //vtnWebCam.getBtnCapturarFoto().addActionListener(e -> capturarFoto());
        vtnWebCam.getBtnCapturarFoto().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                //Consulto los mouse listeners de el panel y le paso el evento
                for(MouseListener ml: vtnWebCam.getPanelCam().getMouseListeners()){
                    ml.mouseClicked(e);
                }
            }
        });
        
        vtnWebCam.getBtnGuardarFoto().addActionListener(e -> guardarFoto());
        vtnWebCam.getBtnCancelar().addActionListener(e -> cancelarFoto());
        
        //Evento de cama guardado  
        vtnWebCam.getPanelCam().addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (!camaraActiva) {
                    camaraActiva = true;
                }else{
                    capturarFoto(); 
                    camaraActiva = false;
                }
            }
        }); 
        
    }
    
    public void capturarFoto() {
        System.out.println("Se dio click en capturar foto");
        try {
            byte[] imagen = vtnWebCam.getPanelCam().getBytes();
            if (imagen != null) {
                is = new ByteArrayInputStream(imagen);
                BufferedImage bi = ImageIO.read(is);
                if (bi != null) {

                    ImageIcon icono = new ImageIcon(bi);
                    foto = icono.getImage().getScaledInstance(
                            vtnWebCam.getLbl_Imagen().getWidth(), vtnWebCam.getLbl_Imagen().getHeight(), Image.SCALE_SMOOTH);
                    vtnWebCam.getLbl_Imagen().setIcon(new ImageIcon(foto));
                    //Guardamos en un archivo
                    ImageIO.write(bi, "png", new File("./foto.png"));
                    //Modificamos el borde por el normal
                    vtnWebCam.getPanelCam().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
                } else {
                    JOptionPane.showMessageDialog(vtnWebCam, "Primero debe actiavar la camara, \ndandole click al recuado idicado.");
                    vtnWebCam.getPanelCam().setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(47, 76, 113), 2));
                }
            }
        } catch (IOException ex) {
            System.out.println("Este es un error al tomar una foto " + ex.getMessage());
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
            //vtnWebCam.getPanelCam().setACTIVARCAMARA(false);
            WebcamShutdownHook WebcamShutdownHook;
            vtnWebCam.getPanelCam().isACTIVARCAMARA();
//            [shutdown-hook-1] INFO com.github.sarxos.webcam.WebcamShutdownHook - Automatic Integrated Camera 0 deallocation
//            [shutdown-hook-1] INFO com.github.sarxos.webcam.Webcam - Disposing webcam Integrated Camera 0
//            
        } else {
            JOptionPane.showMessageDialog(vtnWebCam, "Aun no se a tomado una foto.");
        }

    }

    private void cancelarFoto() {
        vtnWebCam.dispose();
        System.out.println("Se dio click en cancelar");
        //vtnWebCam.getPanelCam().setACTIVARCAMARA(false);
    }
    
    private void clickCamara(){
        
    }

}
