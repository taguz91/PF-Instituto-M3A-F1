package controlador.version;

import java.io.File;
import javax.swing.JOptionPane;
import modelo.CONS;
import modelo.version.VersionMD;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author Johnny
 */
public class Descomprime {

    private final VersionMD version;
    private boolean segIntento = false;

    public Descomprime(VersionMD version) {
        this.version = version;
    }

    public void descomprime() {
        File zipFile = new File(version.getNombre());
        ZipFile zf;
        try {
            zf = new ZipFile(zipFile);
            zf.extractAll(CONS.getDir());
            eliminarZip(zipFile);
        } catch (ZipException ex) {
            System.out.println("No salio bien debemos informarlo: " + ex.getMessage());
            if (!segIntento) {
                errorAlDescromprimir();
            } else {
                errorPermiso();
            }
        }
    }

    private void errorAlDescromprimir() {
        int r = JOptionPane.showConfirmDialog(null, "No pudimos guardar el archivo. \n"
                + "¿Desea intentarlo de nuevo?");
        if (r == JOptionPane.YES_OPTION) {
            segIntento = true;
            descomprime();
        }
    }

    private void errorPermiso() {
        JOptionPane.showMessageDialog(null, "Tuvimos incovenientes la instalar el sistema, \n"
                + "debera hacerlo de forma manual. \n"
                + "Indicaciones: \n"
                + "1. Debera extraer el .zip que se encuentra en \n"
                + "" + CONS.getDir() + "\n"
                + "2. Ahora solo incie el sistema.");
    }

    private void eliminarZip(File zipFile) {
        if (zipFile.delete()) {
            JOptionPane.showMessageDialog(null, "Actualizamos todo de forma correcta.\n"
                    + "Vuelva a abrir el programa.");
            System.exit(0);
        }
    }

}
