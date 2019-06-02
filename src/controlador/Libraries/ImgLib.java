package controlador.Libraries;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import org.postgresql.util.Base64;

/**
 *
 * @author MrRainx
 */
public class ImgLib {

    public static ImageIcon JFCToImageIcon(JFileChooser findFile) {

        ImageIcon image = null;

        try {

            URI uri = findFile.getSelectedFile().toURI();

            try (FileInputStream input = new FileInputStream(new File(uri))) {

                BufferedImage bffImage = ImageIO.read(input);

                if (bffImage == null) {

                    return null;

                } else {
                    image = new ImageIcon(bffImage);
                }

            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return image;

    }

    public static FileInputStream getImgFileFromJFC(JFileChooser JFileChooser) {

        try {
            return new FileInputStream(new File(JFileChooser.getSelectedFile().toPath().toUri()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImgLib.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void setImageInLabel(Image image, JLabel label) {

        if (image != null && label.getHeight() != 0 && label.getWidth() != 0) {
            label.setSize(label.getWidth(), label.getHeight());

            System.out.println("->" + label.getHeight());
            System.out.println("-->" + label.getWidth());

            Icon icon = new ImageIcon(image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));

            label.setIcon(icon);
        }
    }

    public static String getStringPathFromJFC(JFileChooser JfileChooser) {
        return JfileChooser.getSelectedFile().toPath().toString();
    }

    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        BufferedImage buffImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = buffImage.createGraphics();
        g2D.drawImage(image, 0, 0, null);
        g2D.dispose();
        return buffImage;

    }

    public static String setImageInBase64(Image image) {

        if (image != null) {

            String base = null;

            ByteArrayOutputStream byteAOS = new ByteArrayOutputStream();

            try {
                BufferedImage img = toBufferedImage(image);

                ImageIO.write(img, "PNG", byteAOS);

                base = Base64.encodeBytes(byteAOS.toByteArray());

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            return base;
        } else {
            return null;
        }

    }

    public static Image byteToImage(byte[] byteFoto) {

        if (byteFoto != null) {
            return new ImageIcon(byteFoto).getImage();
        }
        return null;

    }

}
