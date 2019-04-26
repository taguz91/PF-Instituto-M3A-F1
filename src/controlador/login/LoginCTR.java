package controlador.login;

import controlador.Libraries.Effects;
import controlador.usuario.VtnSelectRolCTR;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import modelo.ConectarDB;
import modelo.propiedades.Propiedades;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
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

    private final boolean carga = true;

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
        vista.getBtnIngresar().addActionListener(e -> login());

        Effects.btnHover(vista.getBtnIngresar(), vista.getLblBtnHover(), new Color(139, 195, 74), new Color(235, 192, 36));
        vista.getTxtPassword().addKeyListener(eventoText());

        vista.getTxtUsername().addKeyListener(eventoText());

        vista.getBtnDevMode().addActionListener(e -> clickDevMode());

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

    //METODOS DE APOYO
    private void login() {

        if (carga) {

            new Thread(() -> {

                Effects.setLoadCursorInWindow(vista);
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
                    Effects.setDefaultCursorInWindow(vista);
                } catch (NullPointerException e) {
                    vista.getLblAvisos().setVisible(true);
                    vista.getLblAvisos().setText("Revise la Informacion Ingresada");
                    Effects.setDefaultCursorInWindow(vista);
                }

            }).start();
        }

    }

    //EVENTOS
    private KeyAdapter eventoText() {
        return new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    login();
                }
            }
        };
    }

    private void clickDevMode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
