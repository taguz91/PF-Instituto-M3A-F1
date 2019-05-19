package controlador.version;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import javax.swing.JOptionPane;
import modelo.validaciones.TxaVDescripcion;
import modelo.validaciones.TxtVLetras;
import modelo.validaciones.TxtVLetrasSimple;
import modelo.validaciones.TxtVUrlZip;
import modelo.validaciones.TxtVVersion;
import modelo.validaciones.Validar;
import modelo.version.VersionBD;
import vista.version.FrmVersion;

/**
 *
 * @author alumno
 */
public class FrmVersionCTR extends DVtnCTR {

    private final FrmVersion frmVersion;
    private final VersionBD ver;

    public FrmVersionCTR(VtnPrincipalCTR ctrPrin, VersionBD ver) {
        super(ctrPrin);
        this.frmVersion = new FrmVersion(ctrPrin.getVtnPrin(), false);
        this.ver = ver;
    }

    public void iniciar() {
        frmVersion.setLocationRelativeTo(null);
        frmVersion.setVisible(true);
        //Formato de la tabla
        frmVersion.getTxtNotas().setLineWrap(true);
        frmVersion.getTxtNotas().setWrapStyleWord(true);
        iniciarAcciones();
        iniciarValidaciones();
    }

    private void iniciarAcciones() {
        frmVersion.getBtnGuardar().addActionListener(e -> guardar());
    }

    private void iniciarValidaciones() {
        frmVersion.getTxtAutor().addKeyListener(new TxtVLetras(frmVersion.getTxtAutor(),
                frmVersion.getLblError()));
        frmVersion.getTxtNombre().addKeyListener(new TxtVLetrasSimple(frmVersion.getTxtNombre(),
                frmVersion.getLblError()));
        frmVersion.getTxtUrl().addKeyListener(new TxtVUrlZip(frmVersion.getTxtUrl(),
                frmVersion.getLblError()));
        frmVersion.getTxtNotas().addKeyListener(new TxaVDescripcion(frmVersion.getTxtNotas(),
                frmVersion.getLblError()));
        frmVersion.getTxtVersion().addKeyListener(new TxtVVersion(frmVersion.getTxtVersion(),
                frmVersion.getLblError()));
    }

    private void guardar() {
        boolean guardar = true;

        String username, version, url,
                nombre, notas;

        url = frmVersion.getTxtUrl().getText().trim();
        version = frmVersion.getTxtVersion().getText().trim();
        nombre = frmVersion.getTxtNombre().getText().trim();
        username = frmVersion.getTxtAutor().getText().trim();
        notas = frmVersion.getTxtNotas().getText().trim();

        String urlNombre = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
        if (!urlNombre.equals(nombre)) {
            int r = JOptionPane.showConfirmDialog(null, "El nombre de su proyecto no es: " + urlNombre + "\n"
                    + "¿Desea escribirlo?");
            if (r == JOptionPane.YES_OPTION) {
                nombre = urlNombre;
                frmVersion.getTxtNombre().setText(nombre);
            } else {
                guardar = false;
            }
        }

        System.out.println("Nombre es: " + url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));

        if (nombre.length() < 3 || !Validar.esLetrasSimple(nombre)) {
            guardar = false;
        }

        if (url.length() < 10 || !Validar.esUrlZip(url)) {
            guardar = false;
        }

        if (version.length() < 2 || !Validar.esVersion(version)) {
            guardar = false;
        }

        if (username.length() < 2 || !Validar.esLetras(username)) {
            guardar = false;
        }

        if (notas.length() < 2 || !Validar.esDescripcion(notas)) {
            guardar = false;
        }

        if (guardar) {
            int r = JOptionPane.showConfirmDialog(frmVersion, "Se guardara: \n"
                    + "Autor: " + username + "\n"
                    + "Proyecto: " + nombre + "\n"
                    + "Version: " + version + "\n"
                    + "URL: \n"
                    + url + "\n"
                    + "Notas: \n"
                    + notas + "\n"
                    + "¿Seguro que quiere continuar?");

            if (r == JOptionPane.YES_OPTION) {
                ver.setNombre(nombre + ".zip");
                ver.setNotas(notas);
                ver.setUrl(url);
                ver.setUsername(username);
                ver.setVersion(version);

                if (ver.guardar()) {
                    frmVersion.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Revise su formulario contiene errores.");
        }
    }

}
