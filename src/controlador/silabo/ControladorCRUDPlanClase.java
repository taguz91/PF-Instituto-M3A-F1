/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.silabo.CarrerasBDS;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmCRUDPlanClase;
import vista.silabos.frmPlanClase;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Daniel
 */
public class ControladorCRUDPlanClase {

    private frmCRUDPlanClase plan;
    private UsuarioBD usuario;
    private ConexionBD conexion;
    private frmPlanClase clase;
    private VtnPrincipal principal;
    private List<CarreraMD> carrerasDocente;

    public ControladorCRUDPlanClase(frmCRUDPlanClase plan, UsuarioBD usuario) {
        this.plan = plan;
        this.usuario = usuario;
        //iniciarControlador();
    }

//    public void BuscarCarrera(VtnPrincipal vtnPrincipal) {
//        ControladorConfiguracion_plan_clases ConP = new ControladorConfiguracion_plan_clases(usuario, vtnPrincipal);
//        String clave = plan.getTxtBuscarPLC().getText();
//        CarrerasBDS.consultar(conexion, clave);
//    }
    public List<CarreraMD> cargarCarreras() {
        List<CarreraMD> carrerasDocentes = CarrerasBDS.consultar(conexion, usuario.getUsername());
        for (int i = 0; i < carrerasDocentes.size(); i++) {
            carrerasDocentes.get(i).getNombre();
        }

        return carrerasDocentes;
    }

    public void inicarControlador() {
        conexion.conectar();

        plan = new frmCRUDPlanClase();
        principal.add(plan);
        plan.setTitle("Plan de Clase");
        plan.show();

        plan.setLocation((principal.getDpnlPrincipal().getSize().width - plan.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - plan.getSize().height) / 2);
        cargarCarreras();
//        MouseListener m2 = new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                clase.setVisible(true);
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//        };
//        KeyListener k1 = new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent ke) {
//                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void keyPressed(KeyEvent ke) {
//                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//
//            @Override
//            public void keyReleased(KeyEvent ke) {
//
//            }
//
//        };

//        plan.getBtnNuevoPLC().addMouseListener(m2);
    }

}
