package controlador.login;

import controlador.Libraries.Effects;
import controlador.usuario.VtnSelectRolCTR;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import modelo.ConectarDB;
import modelo.ConnDBPool;
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
        InitEventos();
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    private void InitEventos() {
        vista.getBtnIngresar().addActionListener(e -> login());
        Effects.btnHover(vista.getBtnIngresar(), vista.getLblBtnHover(), new Color(139, 195, 74), new Color(235, 192, 36));
        vista.getTxtPassword().addKeyListener(eventoText());
        vista.getTxtUsername().addKeyListener(eventoText());
        vista.getBtnIngresar().addActionListener(e -> login());
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

    //METODOS DE APOYO
    private void activarForm(boolean estado) {
        vista.getTxtUsername().setEnabled(estado);
        vista.getTxtPassword().setEnabled(estado);
        vista.getBtnIngresar().setEnabled(estado);
    }

    private void login() {

        if (carga) {

            new Thread(() -> {
                ConnDBPool conex = null;
                try {

                    Effects.setLoadCursor(vista);
                    String USERNAME = vista.getTxtUsername().getText();
                    String PASSWORD = vista.getTxtPassword().getText();

                    activarForm(false);
                    conex = new ConnDBPool(USERNAME, PASSWORD);

                    modelo = new UsuarioBD();

                    modelo.setUsername(USERNAME);
                    modelo.setPassword(PASSWORD);

                    modelo = modelo.selectWhereUsernamePassword();

                    if (modelo != null) {

                        vista.dispose();

                        VtnSelectRolCTR vtn = new VtnSelectRolCTR(new VtnSelectRol(), new RolBD(), modelo, new ConectarDB(USERNAME, PASSWORD, "Login", conex), icono, ista, false);
                        vtn.Init();

                    } else {
                        Effects.setTextInLabel(vista.getLblAvisos(), "Revise la Informacion Ingresada", Effects.ERROR_COLOR, 2);
                        Effects.setDefaultCursor(vista);
                        conex.closePool();
                    }

                } catch (NullPointerException e) {
                    Effects.setDefaultCursor(vista);
                    Effects.setTextInLabel(vista.getLblAvisos(), "Revise la Informacion Ingresada", Effects.ERROR_COLOR, 2);

                    if (conex != null) {
                        conex.closePool();
                    }
                } finally {

                    Effects.setDefaultCursor(vista);
                    activarForm(true);
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

    private void ingresoVeloz(String c) {
        if (c.length() > 1 && c.length() <= 2) {
            if (c.equalsIgnoreCase("J.")) {
                vista.getTxtUsername().setText("JOHNNY");
            }
        }
    }

}
