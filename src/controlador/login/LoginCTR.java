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
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import modelo.ConectarDB;
import modelo.ResourceManager;
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

        vista.getBtnIngSU().addActionListener(e -> btnIngSUActionPerformance(e));
        vista.getBtnDevMode().addActionListener(e -> clickDevMode());

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
                vista.getTxtPassword().setEnabled(false);
                vista.getTxtUsername().setEnabled(false);
                vista.getBtnIngresar().setEnabled(false);
                Middlewares.setLoadCursorInWindow(vista);

                USERNAME = vista.getTxtUsername().getText();
                PASSWORD = vista.getTxtPassword().getText();
                ResourceManager.USERNAME = USERNAME;
                ResourceManager.PASSWORD = PASSWORD;

                modelo.setUsername(vista.getTxtUsername().getText());
                modelo.setPassword(vista.getTxtPassword().getText());

                try {
                    List<UsuarioMD> Lista = modelo.SelectWhereUsernamePassword();

                    if (!Lista.isEmpty()) {

                        modelo.setPersona(Lista.get(0).getPersona());

                        vista.dispose();

                        VtnSelectRolCTR vtn = new VtnSelectRolCTR(new VtnSelectRol(), new RolBD(), modelo, new ConectarDB(USERNAME, PASSWORD, "Login"), icono, ista, false);
                        vtn.Init();
                        ResourceManager.cerrarConexion();
                    } else {
                        vista.getTxtPassword().setEnabled(false);
                        vista.getTxtUsername().setEnabled(false);
                        vista.getBtnIngresar().setEnabled(false);
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

    private void LoginGenerico() {
        boolean entrar = true;
        JPasswordField pass = new JPasswordField();
        int o = JOptionPane.showConfirmDialog(vista, pass, "Ingrese contraseña",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        pass.setFocusable(true);
        pass.requestFocus();
        pass.selectAll();

        if (o == JOptionPane.OK_OPTION) {
            String c = new String(pass.getPassword());
            if (c.length() > 3) {
                if (c.charAt(c.length() - 1) != c.charAt(1)) {
                    entrar = false;
                }

                if (c.charAt(c.length() - 2) != c.charAt(2)) {
                    entrar = false;
                }

                if (entrar) {
                    iniciarModoDesarrollo("ROOT", "RUTH");
                } else if (c.length() == 0) {
                    LoginGenerico();
                } else {
                    JOptionPane.showMessageDialog(null, "Esponja entrar aqui es peligroso!!!", "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

        }
    }

    //Ingreso como desarrollador
    private void clickDevMode() {
        boolean entrar = true;
        JPasswordField pass = new JPasswordField();
        int o = JOptionPane.showConfirmDialog(vista, pass, "Ingrese una contraseña!",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        pass.setFocusable(true);
        pass.requestFocus();
        pass.selectAll();

        if (o == JOptionPane.OK_OPTION) {
            String c = new String(pass.getPassword());
            if (c.length() > 3) {
                if (c.charAt(c.length() - 1) != c.charAt(0)) {
                    entrar = false;
                }
                if (c.charAt(c.length() - 3) != c.charAt(2)) {
                    entrar = false;
                }
                if (entrar) {
                    iniciarModoDesarrollo("ROOT", "RUTH");
                } else if (c.length() == 0) {
                    LoginGenerico();
                } else {
                    JOptionPane.showMessageDialog(null, "Esponja es mejor que corras!!!", "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

        }
    }

    private void iniciarModoDesarrollo(String user, String pass) {
        USERNAME = user;
        PASSWORD = pass;

        modelo.setUsername(user);
        modelo.setPassword(pass);

        ConectarDB conecta = new ConectarDB(USERNAME, PASSWORD);
        try {
            List<UsuarioMD> Lista = modelo.SelectWhereUsernamePassword();
            if (!Lista.isEmpty()) {
                modelo.setPersona(Lista.get(0).getPersona());
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
        if (conecta.getConecction("Obtenemos la conexion desde modo desarrollo.") != null) {
            vista.dispose();
        } else {
            vista.getLblAvisos().setVisible(true);
            vista.getLblAvisos().setText("No se puede conectar.");
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

    private void btnIngSUActionPerformance(ActionEvent e) {
        LoginGenerico();
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
