package controlador.login;

import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.List;
import modelo.ConectarDB;
import modelo.usuario.UsuarioBD;
import modelo.usuario.UsuarioMD;
import vista.Login;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public class LoginCTR {

    public static String USERNAME = "";
    public static String PASSWORD = "";

    private final Login vista; //LO QUE VA A VISUALIZAR
    private final UsuarioBD modelo; // CON LO QUE VA A TRABAJAR

    public LoginCTR(Login vista, UsuarioBD modelo) {
        this.vista = vista;
        this.modelo = modelo;
    }

    //Inits
    public void Init() {
        vista.getLblAvisos().setText("");

        InitEventos();

        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
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

    }

    //Metodos de Apoyo
    private void Login() {

        modelo.setUsername(vista.getTxtUsername().getText());
        modelo.setPassword(vista.getTxtPassword().getText());

        USERNAME = vista.getTxtUsername().getText();
        PASSWORD = vista.getTxtPassword().getText();

        List<UsuarioMD> Lista = modelo.SelectWhereUsernamePassword();

        if (!Lista.isEmpty()) {

            modelo.setIdPersona(Lista.get(0).getIdPersona());

            VtnPrincipalCTR ventanaPrincipal = new VtnPrincipalCTR(new VtnPrincipal(), modelo, new ConectarDB());
            ventanaPrincipal.iniciar();
            vista.setVisible(false);
        } else {

            vista.getLblAvisos().setText("Revise la Informacion Ingresada");
        }
    }

    private void LoginGenerico() {

        VtnPrincipalCTR ventanaPrincipal = new VtnPrincipalCTR(new VtnPrincipal(), modelo, new ConectarDB());
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
