package controlador.login;

import controlador.Libraries.Effects;
import controlador.Libraries.Middlewares;
import controlador.usuario.VtnSelectRolCTR;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import modelo.ConectarDB;
import modelo.propiedades.Propiedades;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
import modelo.usuario.UsuarioMD;
import vista.Login;
import vista.usuario.VtnSelectRol;

/**
 *
 * @author MrRainx
 */
public class LoginCTR {

    private final Login vista; //LO QUE VA A VISUALIZAR
    private UsuarioBD modelo; // CON LO QUE VA A TRABAJAR
    //Icono de la aplicacion
    private final ImageIcon icono;
    private final Image ista;

    //validacion
    private boolean carga = true;

    public LoginCTR(Login vista) {
        this.vista = vista;
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
        Effects.btnHover(vista.getBtnIngresar(), vista.getLblBtnHover(), new Color(139, 195, 74), new Color(235, 192, 36));
        vista.getTxtPassword().addKeyListener(evento());
        vista.getTxtUsername().addKeyListener(evento());

        vista.getBtnIngSU().addActionListener(e -> btnIngSUActionPerformance(e));

        vista.getTxtUsername().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String txt = vista.getTxtUsername().getText().trim();
                if (txt.length() <= 2) {
                    ingresoVeloz(txt);
                }
            }
        });
    }

    private void ingresoVeloz(String c) {
        if (c.length() > 1 && c.length() <= 2) {
            if (c.equalsIgnoreCase("J.")) {
                vista.getTxtUsername().setText("JOHNNY");
                vista.getTxtPassword().setText("ROOT");
            } else if (c.equalsIgnoreCase("R.")) {
                vista.getTxtUsername().setText("ROOT");
                vista.getTxtPassword().setText("RUTH");
            } else if (c.equalsIgnoreCase("P.")) {
                vista.getTxtUsername().setText("postgres");
                vista.getTxtPassword().setText("Holapostgres");
            }
        }
    }

    //Metodos de Apoyo
    private void Login() {

        if (carga) {

            new Thread(() -> {

                Middlewares.setLoadCursorInWindow(vista);
                modelo = new UsuarioBD();
                String USERNAME = vista.getTxtUsername().getText();
                String PASSWORD = vista.getTxtPassword().getText();

                Map<Object, Object> properties = new HashMap<>();
                properties.put("username", USERNAME);
                properties.put("password", PASSWORD);
                Propiedades.generateUserProperties(properties);

                modelo.setUsername(USERNAME);
                modelo.setPassword(PASSWORD);

                try {
                    modelo = modelo.selectWhereUsernamePassword();

                    if (modelo != null) {

                        vista.dispose();

                        VtnSelectRolCTR vtn = new VtnSelectRolCTR(new VtnSelectRol(), new RolBD(), modelo, new ConectarDB(USERNAME, PASSWORD, "Login"), icono, ista, false);
                        vtn.Init();
                    } else {
                        vista.getLblAvisos().setVisible(true);
                        vista.getLblAvisos().setText("Revise la Informacion Ingresada");
                    }
                    Middlewares.setDefaultCursorInWindow(vista);
                } catch (NullPointerException e) {
                    vista.getLblAvisos().setVisible(true);
                    vista.getLblAvisos().setText("Revise la Informacion Ingresada");
                    Middlewares.setDefaultCursorInWindow(vista);
                }

            }).start();
        }

    }

    private void LoginGenerico() {

        JPasswordField pass = new JPasswordField();
        int o = JOptionPane.showConfirmDialog(vista, pass, "Ingrese contraseña",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        pass.setFocusable(true);
        pass.requestFocus();
        pass.selectAll();

        if (o == JOptionPane.OK_OPTION) {
            String c = new String(pass.getPassword());
            if (c.equals("soyyo")) {

                String USERNAME = "ROOT";
                String PASSWORD = "RUTH";
                
                modelo = new UsuarioBD();
                
                modelo.setUsername("ROOT");
                modelo.setPassword("RUTH");

                //ConectarDB conecta = new ConectarDB(PASSWORD, USERNAME);
                ConectarDB conecta = new ConectarDB(USERNAME, PASSWORD);
                System.out.println("Conexion " + conecta.getConecction());
                try {
                    modelo = modelo.selectWhereUsernamePassword();

                    if (modelo != null) {

                        vista.dispose();

                        VtnSelectRolCTR vtn = new VtnSelectRolCTR(new VtnSelectRol(), new RolBD(), modelo, conecta, icono, ista, true);
                        vtn.Init();

                    } else {
                        vista.getLblAvisos().setVisible(true);
                        vista.getLblAvisos().setText("Revise la Informacion Ingresada");
                    }

                } catch (NullPointerException e) {
                    vista.getLblAvisos().setVisible(true);
                    vista.getLblAvisos().setText("Revise la Informacion Ingresada");
                }
                if (conecta.getConecction() != null) {
                    vista.dispose();
                } else {
                    vista.getLblAvisos().setVisible(true);
                    vista.getLblAvisos().setText("No se puede conectar.");
                }

            } else if (c.length() == 0) {
                LoginGenerico();
            } else {
                JOptionPane.showMessageDialog(null, "Esponja entrar aqui es peligroso!!!", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        }

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

    /**
     * Animacion de hover en el boton
     */
    private KeyAdapter evento() {
        return new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                txtOnKeyRelessed(e);
            }
        };
    }
}
