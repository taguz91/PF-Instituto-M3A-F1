package controlador.version;

import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.util.ArrayList;
import modelo.version.VersionBD;
import modelo.version.VersionMD;
import vista.version.JDVersion;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class JDVersionCTR extends DVtnCTR {

    private final JDVersion jd;
    private ArrayList<VersionMD> versiones;
    private final VersionBD ver;

    public JDVersionCTR(VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.jd = new JDVersion(vtnPrin, false);
        jd.setLocationRelativeTo(vtnPrin);
        jd.setVisible(true);
        this.ver = new VersionBD(ctrPrin.getConecta());
    }

    public void iniciar() {
        //Para que salta la linea autimaticamente
        ctrPrin.eventoJDCerrar(jd);
        //Formato de la tabla
        formatoTbl();
        //Iniciar el formulario 
        iniciarAcciones();
        cargarDatos();
    }

    private void iniciarAcciones() {
        jd.getBtnIngresarVersion().addActionListener(e -> ingresar());
        jd.getCbxEliminados().addActionListener(e -> clickEliminados());
    }

    private void formatoTbl() {
        String[] t = {"Autor", "Nombre", "Version"};
        String[][] d = {};
        iniciarTbl(t, d, jd.getTblVersiones());
    }

    private void clickEliminados() {
        if (jd.getCbxEliminados().isSelected()) {
            cargarEliminados();
        } else {
            cargarDatos();
        }
    }

    private void cargarEliminados() {
        versiones = ver.cargarVersionesEliminadas();
        llenarTbl(versiones);
    }

    private void cargarDatos() {
        versiones = ver.cargarVersionesActivas();
        llenarTbl(versiones);
    }

    private void llenarTbl(ArrayList<VersionMD> versiones) {
        mdTbl.setRowCount(0);
        if (versiones != null) {
            versiones.forEach(v -> {
                Object[] val = {v.getNombreSinExtension(), v.getVersion(), v.getUsername()};
                mdTbl.addRow(val);
            });
        }
    }

    private void ingresar() {
        FrmVersionCTR ctr = new FrmVersionCTR(ctrPrin, ver);
        ctr.iniciar();
    }

}
