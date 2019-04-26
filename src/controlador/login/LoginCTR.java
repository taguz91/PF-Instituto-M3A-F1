package controlador.login;

import controlador.Libraries.Effects;
import controlador.usuario.VtnSelectRolCTR;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import modelo.ConectarDB;
import modelo.ResourceManager;
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

        vista.getBtnIngSU().addActionListener(e -> btnIngSUActionPerformance(e));
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

                String USERNAME = "ROOT";
                String PASSWORD = "RUTH";

                modelo = new UsuarioBD();

                if (entrar) {
                    iniciarModoDesarrollo("ROOT", "RUTH");
                } else if (c.length() == 0) {
                    LoginGenerico();
                } else {
                    JOptionPane.showMessageDialog(null, "Esponja entrar aqui es peligroso!!!", "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

                //ConectarDB conecta = new ConectarDB(PASSWORD, USERNAME);
                ConectarDB conecta = new ConectarDB(USERNAME, PASSWORD);
                System.out.println("Conexion " + conecta.getConecction());
                try {
                    modelo = modelo.selectWhereUsernamePassword();

                    if (modelo != null) {

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

    //EVENTOS

    private void btnIngSUActionPerformance(ActionEvent e) {
        LoginGenerico();
    }

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
}
