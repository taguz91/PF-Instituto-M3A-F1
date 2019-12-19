package controlador.login;

import controlador.Libraries.Effects;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import utils.CONS;
import modelo.ConnDBPool;
import modelo.usuario.UsuarioBD;
import vista.Login;

/**
 *
 * @author MrRainx
 */
public class LoginCTR {

    private final Login vista;
    private UsuarioBD modelo;

    private final boolean carga = true;

    public LoginCTR() {
        this.vista = new Login();
        vista.setIconImage(CONS.getImage());
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

            if (!vista.getTxtUsername().getText().isEmpty() && !vista.getTxtPassword().getText().isEmpty()) {

                new Thread(() -> {
                    try {

                        Effects.setLoadCursor(vista);
                        String USERNAME = vista.getTxtUsername().getText().trim();
                        String PASSWORD = vista.getTxtPassword().getText().trim();

                        activarForm(false);

                        modelo = new UsuarioBD();
                        modelo.setUsername(USERNAME);
                        modelo.setPassword(PASSWORD);

                        modelo = modelo.selectWhereUsernamePassword();

                        if (modelo != null) {

                            vista.dispose();
                            CONS.setUsuario(modelo);
                            VtnSelectRolCTR vtn = new VtnSelectRolCTR();
                            vtn.Init();

                        } else {
                            Effects.setTextInLabel(vista.getLblAvisos(), "Revise la Informacion Ingresada", CONS.ERROR_COLOR, 2);
                            Effects.setDefaultCursor(vista);
                        }

                    } catch (NullPointerException e) {
                        Effects.setDefaultCursor(vista);
                        Effects.setTextInLabel(vista.getLblAvisos(), "Revise la Informacion Ingresada", CONS.ERROR_COLOR, 2);
                    } finally {

                        Effects.setDefaultCursor(vista);
                        activarForm(true);
                    }

                }).start();
            } else {
                Effects.setTextInLabel(vista.getLblAvisos(), "RELLENE BIEN LA INFORMACION!!", CONS.ERROR_COLOR, 2);
            }
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
            } else if (c.equalsIgnoreCase("M.")) {
                vista.getTxtUsername().setText("MrRainx");
            } else if (c.equalsIgnoreCase("H.")) {
                vista.getTxtUsername().setText("0103156675");
            }
        }
    }

}
