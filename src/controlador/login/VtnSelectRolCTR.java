package controlador.login;

import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import utils.CONS;
import modelo.ConnDBPool;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
import utils.CONBD;
import vista.usuario.VtnSelectRol;

/**
 *
 * @author MrRainx
 */
public class VtnSelectRolCTR extends CONBD {

    private final VtnSelectRol vista;
    private RolBD modelo;
    private final UsuarioBD usuario;

    private List<RolBD> rolesDelUsuario;

    /**
     */
    public VtnSelectRolCTR() {
        this.vista = new VtnSelectRol();
        this.modelo = new RolBD();
        this.usuario = CONS.USUARIO;
        vista.setIconImage(CONS.getImage());
        registroIngreso(vista);
    }

    private void registroIngreso(JFrame vtn) {
        vtn.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                inicioSesion();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                cierreSesion();
            }

        });
    }

    public void inicioSesion() {
        String nsql = "INSERT INTO public.\"HistorialUsuarios\"(\n"
                + "	usu_username, historial_fecha, historial_tipo_accion, historial_nombre_tabla, historial_pk_tabla)\n"
                + "	VALUES ('" + usuario.getUsername() + "', now(), 'INICIO SESION', 'SISTEMA', 0);";
        if (CON.executeNoSQL(nsql)) {
            System.out.println("Iniciamos como: " + usuario.getUsername());
        }
    }

    public void cierreSesion() {
        String nsql = " INSERT INTO public.\"HistorialUsuarios\"(\n"
                + "  	usu_username, historial_fecha, historial_tipo_accion, historial_nombre_tabla, historial_pk_tabla)\n"
                + "  	VALUES ('" + usuario.getUsername() + "', now(), 'CIERRE SESION', 'SISTEMA', 0);";
        if (CON.executeNoSQL(nsql)) {
            System.out.println("Salimos del sistema como: " + usuario.getUsername());
        }
        ConnDBPool pool = new ConnDBPool();
        pool.closePool();
    }

    //Inits
    public void Init() {
        rolesDelUsuario = modelo.SelectWhereUSUARIOusername(usuario.getUsername());
        vista.getLblUsuario().setText(usuario.getUsername());
        System.out.println("Llamamos al rol una vez");

        rellenarCombo();
        InitEventos();

        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }

    private void InitEventos() {
        vista.getBtnSeleccionar().addActionListener(e -> ingresar());
        vista.getBtnCancelar().addActionListener(e -> {
            cierreSesion();
            System.exit(0);
        });
        vista.getCmbRoles().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    ingresar();
                }
            }
        });
    }

    //Metodos de Apoyo
    private void rellenarCombo() {
        vista.getCmbRoles().removeAllItems();
        rolesDelUsuario.forEach(obj -> {
            vista.getCmbRoles().addItem(obj.getNombre());
        });

    }

    private void setModelo() {

        modelo = rolesDelUsuario
                .stream()
                .filter(item -> item.getNombre().equals(vista.getCmbRoles().getSelectedItem().toString()))
                .findFirst()
                .orElse(null);

    }

    private void ingresar() {

        setModelo();
        CONS.setRol(modelo);
        CONS.refreshPermisos();
        VtnPrincipalCTR vtn = new VtnPrincipalCTR(this);
        vtn.iniciar();
        vista.dispose();
    }

}
