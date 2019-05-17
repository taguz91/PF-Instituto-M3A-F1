package controlador.version;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import javax.swing.JOptionPane;
import modelo.version.VersionBD;
import vista.version.FrmVersion;

/**
 *
 * @author alumno
 */
public class FrmVersionCTR extends DVtnCTR {

    private final FrmVersion frmVersion;
    private final VersionBD vbd;

    public FrmVersionCTR(VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.frmVersion = new FrmVersion(ctrPrin.getVtnPrin(), false);
        this.vbd = new VersionBD(ctrPrin.getConecta());
    }

    public void iniciar() {
        frmVersion.setLocationRelativeTo(null);
        frmVersion.setVisible(true);
        iniciarAcciones();
    }

    private void iniciarAcciones() {
        frmVersion.getBtnGuardar().addActionListener(e -> guardar());
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

        if (!url.contains(nombre)) {
            guardar = false;
        } else {
            System.out.println("No tienes el nombre del proyecto en el url algo anda mal");
        }

        if (nombre.length() < 3) {
            guardar = false;
        }

        if (url.length() < 10) {
            guardar = false;
        }

        if (version.length() < 2) {
            guardar = false;
        }

        if (username.length() < 2) {
            guardar = false;
        }

        if (notas.length() < 2) {
            guardar = false;
        }

        if (guardar) {
            vbd.setNombre(nombre);
            vbd.setNotas(notas);
            vbd.setUrl(url);
            vbd.setUsername(username);
            vbd.setVersion(version);

            if (vbd.guardar()) {
                frmVersion.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Revise su formulario que contiene errores.");
        }
    }

}
