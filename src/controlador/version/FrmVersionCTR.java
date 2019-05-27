package controlador.version;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import modelo.validaciones.TxaVDescripcion;
import modelo.validaciones.TxtVLetrasSimple;
import modelo.validaciones.TxtVUrlZip;
import modelo.validaciones.TxtVVersion;
import modelo.validaciones.Validar;
import modelo.version.VersionBD;
import modelo.version.VersionMD;
import vista.version.FrmVersion;

/**
 *
 * @author alumno
 */
public class FrmVersionCTR extends DVtnCTR {

    private final FrmVersion frmVersion;
    private final JDVersionCTR ctrVersion;
    private final VersionBD ver;
    private boolean editar = false;
    private int idVersion = 0;

    public FrmVersionCTR(VtnPrincipalCTR ctrPrin, VersionBD ver,
            JDVersionCTR ctrVersion) {
        super(ctrPrin);
        this.ctrVersion = ctrVersion;
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
        frmVersion.getTxtAutor().addKeyListener(new TxtVLetrasSimple(frmVersion.getTxtAutor(),
                frmVersion.getLblError()));
        frmVersion.getTxtNombre().addKeyListener(new TxtVLetrasSimple(frmVersion.getTxtNombre(),
                frmVersion.getLblError()));
        frmVersion.getTxtUrl().addKeyListener(new TxtVUrlZip(frmVersion.getTxtUrl(),
                frmVersion.getLblError()));
        frmVersion.getTxtNotas().addKeyListener(new TxaVDescripcion(frmVersion.getTxtNotas(),
                frmVersion.getLblError()));
        frmVersion.getTxtVersion().addKeyListener(new TxtVVersion(frmVersion.getTxtVersion(),
                frmVersion.getLblError()));
        //Para agregar el nombre autimaticament en el url 
        frmVersion.getTxtUrl().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                agregarNombre();
            }
        });
    }

    /**
     * Se ingresa el nombre en base a la url ingresada automaticamente.
     */
    private void agregarNombre() {
        String url = frmVersion.getTxtUrl().getText().trim();
        if (Validar.esUrlZip(url)) {
            String urlNombre = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
            frmVersion.getTxtNombre().setText(urlNombre);
        }

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

        if (!Validar.esLetrasSimple(nombre)) {
            guardar = false;
        }

        if (!Validar.esUrlZip(url)) {
            guardar = false;
        } else {
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
        }

        if (!Validar.esVersion(version)) {
            guardar = false;
        }

        if (!Validar.esLetrasSimple(username)) {
            guardar = false;
        }

        if (!Validar.esDescripcion(notas)) {
            guardar = false;
        }

        //Si hasta aqui todo esta bien consultamos si no existe ya esa version. 
        if (guardar && !editar) {
            VersionMD v = ver.existeVersion(nombre + ".zip", version);
            if (v != null) {
                if (v.getUsername() != null) {
                    JOptionPane.showMessageDialog(frmVersion, "Ya existe esta version fue publicada por: "
                            + v.getUsername());
                    guardar = false;
                }
            }
        }

        if (guardar) {
            boolean completo;
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

                if (editar) {
                    completo = ver.editar(idVersion);
                } else {
                    completo = ver.guardar();
                }
                //Si todo se completo correctamente cerramos la ventana.
                if (completo) {
                    frmVersion.dispose();
                    ctrVersion.cargarDatos();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Revise su formulario contiene errores.");
        }
    }

    public void editar(int idVersion) {
        this.idVersion = idVersion;
        editar = true;
        VersionMD v = ver.consultarVersion(idVersion);
        if (v != null) {
            frmVersion.getTxtAutor().setText(v.getUsername());
            frmVersion.getTxtNombre().setText(v.getNombreSinExtension());
            frmVersion.getTxtNotas().setText(v.getNotas());
            frmVersion.getTxtUrl().setText(v.getUrl());
            frmVersion.getTxtVersion().setText(v.getVersion());
        } else {
            JOptionPane.showMessageDialog(frmVersion, "No se puede editar, no se encontro el registro.");
            editar = false;
        }
    }

}
