package controlador.usuario;

import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;
import modelo.ConectarDB;
import modelo.usuario.RolBD;
import modelo.usuario.RolMD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.usuario.VtnSelectRol;

/**
 *
 * @author MrRainx
 */
public class VtnSelectRolCTR {
    
    private final VtnSelectRol vista;
    private final RolBD modelo;
    
    private final UsuarioBD usuario;
    List<RolMD> rolesDelUsuario;
    
    public VtnSelectRolCTR(VtnSelectRol vista, RolBD modelo, UsuarioBD usuario) {
        this.vista = vista;
        this.modelo = modelo;
        this.usuario = usuario;
    }

    //Inits
    public void Init() {
        rolesDelUsuario = RolBD.SelectWhereUSUARIOusername(usuario.getUsername());
        vista.getLblUsuario().setText(usuario.getUsername());
        
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
        
        VtnPrincipalCTR vtn = new VtnPrincipalCTR(new VtnPrincipal(), modelo, usuario, new ConectarDB(""));
        vtn.iniciar();
        
        vista.dispose();
    }
    
    private void btnCancelarActionPerformance(ActionEvent e) {
        
        System.exit(0);
        
    }
    
}
