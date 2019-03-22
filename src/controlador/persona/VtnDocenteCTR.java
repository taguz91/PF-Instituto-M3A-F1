package controlador.persona;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.usuario.RolMD;
import vista.persona.FrmDocente;
import vista.persona.VtnDocente;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnDocenteCTR {

    //array de docente modelo
    //vector de titulos de la tabla en el iniciar
    // vtnMateriaCTR basarme en ese---->para el crud
    private final VtnPrincipal vtnPrin;
    private final VtnDocente vtnDocente;
    private final DocenteBD docente;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private final RolMD permisos;

    private ArrayList<DocenteMD> docentesMD;
    private FrmDocente frmDocente;

    public VtnDocenteCTR(VtnPrincipal vtnPrin, VtnDocente vtnDocente, 
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.vtnDocente = vtnDocente;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos; 
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Docentes");

        docente = new DocenteBD(conecta);
        vtnPrin.getDpnlPrincipal().add(vtnDocente);
        vtnDocente.show();
    }

    public void iniciar() {
        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                buscaIncremental(vtnDocente.getTxtBuscar().getText().toUpperCase());
                /// buscar();
            }
        };
        vtnDocente.getBtnEditar().addActionListener(e -> editar());
        vtnDocente.getBtnIngresar().addActionListener(e -> abrirFrmDocente());
        vtnDocente.getTxtBuscar().addKeyListener(kl);
        llenarTabla();
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Docentes");
    }

    public void llenarTabla() {
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnDocente.getTblDocente().getModel();
        for (int i = vtnDocente.getTblDocente().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        docentesMD = docente.cargarDocentes();
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < docentesMD.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            vtnDocente.getTblDocente().setValueAt(String.valueOf(docentesMD.get(i).getIdPersona()), i, 0);
            vtnDocente.getTblDocente().setValueAt(docentesMD.get(i).getIdentificacion(), i, 1);
            vtnDocente.getTblDocente().setValueAt(docentesMD.get(i).getPrimerNombre()
                    + " " + docentesMD.get(i).getSegundoNombre() + " " + docentesMD.get(i).getPrimerApellido()
                    + " " + docentesMD.get(i).getSegundoApellido(), i, 2);
            vtnDocente.getTblDocente().setValueAt(String.valueOf(docentesMD.get(i).getFechaInicioContratacion()), i, 3);
            vtnDocente.getTblDocente().setValueAt(String.valueOf(docentesMD.get(i).getFechaFinContratacion()), i, 4);
            vtnDocente.getTblDocente().setValueAt(String.valueOf(docentesMD.get(i).getDocenteTipoTiempo()), i, 5);
        }

        vtnDocente.getLblResultados().setText(String.valueOf(docentesMD.size()) + " Resultados obtenidos.");
    }

    public void abrirFrmDocente() {
        frmDocente = new FrmDocente();
        FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(vtnPrin, frmDocente, conecta, ctrPrin);
        ctrFrmDocente.iniciar();
    }

    public void buscaIncremental(String aguja) {
        System.out.println(aguja);
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnDocente.getTblDocente().getModel();
        for (int i = vtnDocente.getTblDocente().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<DocenteMD> lista = docente.buscar(aguja);
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            vtnDocente.getTblDocente().setValueAt(String.valueOf(lista.get(i).getIdPersona()), i, 0);
            vtnDocente.getTblDocente().setValueAt(lista.get(i).getIdentificacion(), i, 1);
            vtnDocente.getTblDocente().setValueAt(lista.get(i).getPrimerNombre()
                    + " " + lista.get(i).getSegundoNombre() + " " + lista.get(i).getPrimerApellido()
                    + " " + lista.get(i).getSegundoApellido(), i, 2);
            vtnDocente.getTblDocente().setValueAt(String.valueOf(lista.get(i).getFechaInicioContratacion()), i, 3);
            vtnDocente.getTblDocente().setValueAt(String.valueOf(lista.get(i).getFechaFinContratacion()), i, 4);
            vtnDocente.getTblDocente().setValueAt(String.valueOf(lista.get(i).getDocenteTipoTiempo()), i, 5);
        }
        vtnDocente.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
    }

    public void editar() {
        int posFila = vtnDocente.getTblDocente().getSelectedRow();
        System.out.println(posFila + " metodo editar de vtnDocenteCTR");
        if (posFila >= 0) {
            FrmDocente frmDoc = new FrmDocente();
            FrmDocenteCTR ctrFrm = new FrmDocenteCTR(vtnPrin, frmDoc, conecta, ctrPrin);
            ctrFrm.iniciar();
            //Le pasamos la persona de nuestro lista justo la persona seleccionada
            ctrFrm.editar(docentesMD.get(posFila));
            vtnDocente.getTblDocente().setVisible(false);
            vtnDocente.dispose();

        } else {

        }
    }
     
    private void InitPermisos() {
        for (AccesosMD obj : AccesosBD.SelectWhereACCESOROLidRol(permisos.getId())) {

//            if (obj.getNombre().equals("USUARIOS-Agregar")) {
//                vtnCarrera.getBtnIngresar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-Editar")) {
//                vista.getBtnEditar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-Eliminar")) {
//                vista.getBtnEliminar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-AsignarRoles")) {
//                vista.getBtnAsignarRoles().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-VerRoles")) {
//                vista.getBtnVerRoles().setEnabled(true);
//            }
        }
    }

}

// desde personas probando
/*  public void buscar() {
        String busqueda = vtnDocente.getTxtBuscar().getText();
        busqueda = busqueda.trim();
        if (busqueda.length() > 2) {
            docentesMD = docente.buscar(busqueda);
        } else if (busqueda.length() == 0) {
            docentesMD = docente.cargarDocentes();
        }
       llenarTabla();
    }*/
