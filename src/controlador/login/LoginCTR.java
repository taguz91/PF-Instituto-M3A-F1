package controlador.login;

import controlador.Libraries.Middlewares;
import controlador.usuario.VtnSelectRolCTR;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import modelo.ConectarDB;
import modelo.ResourceManager;
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

    public static String USERNAME = "";
    public static String PASSWORD = "";

    private final Login vista; //LO QUE VA A VISUALIZAR
    private final UsuarioBD modelo; // CON LO QUE VA A TRABAJAR
    //Icono de la aplicacion
    private final ImageIcon icono;
    private final Image ista;

    //validacion
    private boolean carga = true;

    public LoginCTR(Login vista, UsuarioBD modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.icono = new ImageIcon(getClass().getResource("/vista/img/logo.png"));
        this.ista = icono.getImage();
        vista.setIconImage(ista);
    }

    //Inits
    public void Init() {
        btnHover();
        //Ocultamos el boton que ya no se usa
        //vista.getBtnIngSU().setEnabled(false);
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

        //Evento para ingresar rapido como JHONNY
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
                //vista.getTxtPassword().
            }
        }
    }

    //Metodos de Apoyo
    private void Login() {

        if (carga) {

            new Thread(() -> {
                Middlewares.setLoadCursorInWindow(vista);

                USERNAME = vista.getTxtUsername().getText();
                PASSWORD = vista.getTxtPassword().getText();

                ConectarDB conectar = new ConectarDB(USERNAME, PASSWORD, "Login");

                Map<Object, Object> properties = new HashMap<>();

                properties.put("username", USERNAME);
                properties.put("password", PASSWORD);

                Propiedades.generateUserProperties(properties);
                
                ResourceManager.setConecct(conectar.getConecction("Al iniciar la aplicacion"));
                
                modelo.setUsername(vista.getTxtUsername().getText());
                modelo.setPassword(vista.getTxtPassword().getText());

                try {
                    List<UsuarioMD> Lista = modelo.SelectWhereUsernamePassword();

                    if (!Lista.isEmpty()) {

                        modelo.setPersona(Lista.get(0).getPersona());

                        vista.dispose();
                        VtnSelectRolCTR vtn = new VtnSelectRolCTR(new VtnSelectRol(), new RolBD(), modelo, conectar, icono, ista, false);
                        vtn.Init();
                    } else {

                        vista.getLblAvisos().setVisible(true);
                        vista.getLblAvisos().setText("Revise la Informacion Ingresada");
                    }

                } catch (NullPointerException e) {
                    vista.getLblAvisos().setVisible(true);
                    vista.getLblAvisos().setText("Revise la Informacion Ingresada");
                }

            }).start();
        }
    }


    /**
     * Recibimos la contraseña para ingresar al sistema
     *
     * @param e
     */
    //Procesadores de eventos
    private void btnIngresarActionPerformance(ActionEvent e) {
        Login();
    }

    private void txtOnKeyRelessed(KeyEvent e) {

        if (e.getKeyCode() == 10) {
            Login();
        }

    }

    /**
     * Animacion de hover en el boton
     */
    private void btnHover() {
        vista.getBtnIngresar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                vista.getLblBtnHover().setBackground(new Color(139, 195, 74));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                vista.getLblBtnHover().setBackground(new Color(235, 192, 36));
            }
        });
    }

}
