package controlador.usuario;

import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;
import modelo.ConectarDB;
import modelo.usuario.RolBD;
import modelo.usuario.RolMD;
import vista.principal.VtnPrincipal;
import vista.usuario.VtnSelectRol;

/**
 *
 * @author MrRainx
 */
public class VtnSelectRolCTR {

    private VtnSelectRol vista;
    private RolBD modelo;

    private String username;
    List<RolMD> rolesDelUsuario;

    public VtnSelectRolCTR(VtnSelectRol vista, RolBD modelo, String username) {
        this.vista = vista;
        this.modelo = modelo;
        this.username = username;
    }

    //Inits
    public void Init() {
        rolesDelUsuario = RolBD.SelectWhereUSUARIOusername(username);

        rellenarCombo();
        InitEventos();

        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }

    private void InitEventos() {
        vista.getBtnSeleccionar().addActionListener(e -> btnSeleccionarActionPerformance(e));
        vista.getBtnCancelar().addActionListener(e -> btnCancelarActionPerformance(e));
    }

    //Metodos de Apoyo
    private void rellenarCombo() {
        rolesDelUsuario.forEach(obj -> {
            vista.getCmbRoles().addItem(obj.getNombre());
        });

    }

    private void setObjFromCombo() {

        String nombreRol = (String) vista.getCmbRoles().getSelectedItem();

        rolesDelUsuario
                .stream()
                .filter(item -> item.getNombre().equals(nombreRol))
                .collect(Collectors.toList())
                .forEach(obj -> {

                    modelo.setId(obj.getId());
                    modelo.setNombre(obj.getNombre());

                });

    }

    //Procesadores de Eventos
    private void btnSeleccionarActionPerformance(ActionEvent e) {

        setObjFromCombo();

        VtnPrincipalCTR vtn = new VtnPrincipalCTR(new VtnPrincipal(), modelo, new ConectarDB(""));
        vtn.iniciar();

        vista.dispose();
    }

    private void btnCancelarActionPerformance(ActionEvent e) {

        System.exit(0);

    }

}
