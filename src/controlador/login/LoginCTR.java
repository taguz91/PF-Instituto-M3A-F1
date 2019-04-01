package controlador.login;

import controlador.principal.VtnPrincipalCTR;
import controlador.usuario.VtnSelectRolCTR;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.ImageIcon;
import modelo.ConectarDB;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
import modelo.usuario.UsuarioMD;
import vista.Login;
import vista.principal.VtnPrincipal;
import vista.usuario.VtnSelectRol;

/**
 *
 * @author MrRainx
 */
public class LoginCTR {

    public static String USERNAME = "";
    public static String PASSWORD = "";

    private final Login vista; //LO QUE VA A VISUALIZAR
    private final UsuarioBD modelo; // CON LO QUE VA A TRABAJAR
    //Icono de la aplicacion
    private final ImageIcon icono;
    private final Image ista;

    public LoginCTR(Login vista, UsuarioBD modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.icono = new ImageIcon(getClass().getResource("/vista/img/logo.png"));
        this.ista = icono.getImage();
        vista.setIconImage(ista);
    }

    //Inits
    public void Init() {
        vista.getLblAvisos().setText("");

        InitEventos();

        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        //ocusltamos el error
        vista.getLblAvisos().setVisible(false);
    }

    private void InitEventos() {
        vista.getBtnIngresar().addActionListener(e -> btnIngresarActionPerformance(e));

        vista.getTxtPassword().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtOnKeyRelessed(e);
            }
        });
        vista.getTxtUsername().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtOnKeyRelessed(e);
            }
        });

        vista.getBtnIngSU().addActionListener(e -> btnIngSUActionPerformance(e));

        //Evento para ingresar rapido como JHONNY
        vista.getTxtUsername().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String txt = vista.getTxtUsername().getText().trim();
                if (txt.length() < 2) {
                    ingresoVeloz(e.getKeyChar() + "");
                }
            }
        });
    }

    private void ingresoVeloz(String c) {
        if (c.equalsIgnoreCase("J")) {
            vista.getTxtUsername().setText("JHONNY");
            vista.getTxtPassword().setText("ROOT");
        } else if (c.equalsIgnoreCase("R")) {
            vista.getTxtUsername().setText("ROOT");
            vista.getTxtPassword().setText("ROOT");
        }
    }

    //Metodos de Apoyo
    private void Login() {

        modelo.setUsername(vista.getTxtUsername().getText());
        modelo.setPassword(vista.getTxtPassword().getText());

        USERNAME = vista.getTxtUsername().getText();
        PASSWORD = vista.getTxtPassword().getText();

        try {
            List<UsuarioMD> Lista = modelo.SelectWhereUsernamePassword();

            if (!Lista.isEmpty()) {

                modelo.setPersona(Lista.get(0).getPersona());

                vista.dispose();

                VtnSelectRolCTR vtn = new VtnSelectRolCTR(new VtnSelectRol(), new RolBD(), modelo, new ConectarDB(USERNAME, PASSWORD, " LOGIN "), icono, ista);

                // VtnSelectRolCTR vtn = new VtnSelectRolCTR(new VtnSelectRol(), new RolBD(), modelo, new ConectarDB("postgres", "qwerty79", " LOGIN "));
                vtn.Init();

            } else {
                vista.getLblAvisos().setVisible(true);
                vista.getLblAvisos().setText("Revise la Informacion Ingresada");
            }

        } catch (NullPointerException e) {
            vista.getLblAvisos().setVisible(true);
            vista.getLblAvisos().setText("Revise la Informacion Ingresada");
        }

    }

    private void LoginGenerico() {

        USERNAME = "ROOT";
        PASSWORD = "ROOT";
        VtnPrincipalCTR ventanaPrincipal = new VtnPrincipalCTR(new VtnPrincipal(), new RolBD(), new UsuarioBD(), new ConectarDB("postgres", vista.getTxtPassword().getText(), "LoginGenerico"), icono, ista);
        //VtnPrincipalCTR ventanaPrincipal = new VtnPrincipalCTR(new VtnPrincipal(), new RolBD(), new UsuarioBD(), new ConectarDB("ROOT", "ROOT", "LoginGenerico"), icono, ista);

        ventanaPrincipal.iniciar();
        vista.setVisible(false);

    }

    //Procesadores de eventos
    private void btnIngresarActionPerformance(ActionEvent e) {
        Login();
    }

    private void txtOnKeyRelessed(KeyEvent e) {

        if (e.getKeyCode() == 10) {
            Login();
        }

    }

    private void btnIngSUActionPerformance(ActionEvent e) {
        LoginGenerico();
    }

}
