package controlador.persona;

import controlador.carrera.VtnCarreraCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.estilo.TblEstilo;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.usuario.RolMD;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.persona.FrmDocente;
import vista.persona.FrmPersona;
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

    private DefaultTableModel mdTbl;

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
        ctrPrin.setIconJIFrame(vtnDocente);
        docente = new DocenteBD(conecta);
        vtnPrin.getDpnlPrincipal().add(vtnDocente);
        vtnDocente.show();
    }

    public void iniciar() {
        String[] titulo = {"Nombres Completos", "Celular", "Correo", "Tipo Contrato"};
        String[][] datos = {};

        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        vtnDocente.getTblDocente().setModel(mdTbl);
        TblEstilo.formatoTbl(vtnDocente.getTblDocente());
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 0, 250);
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 1, 85);
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 2, 200);
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 3, 140);

        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnDocente.getTxtBuscar().getText().toUpperCase().trim();
                if (b.length() > 2) {
                    buscaIncremental(vtnDocente.getTxtBuscar().getText().toUpperCase());
                }
                /// buscar();
            }
        };
        cargarDocentes();
        vtnDocente.getBtnEditar().addActionListener(e -> editar());
        vtnDocente.getBtnIngresar().addActionListener(e -> abrirFrmDocente());
        vtnDocente.getBtnEliminar().addActionListener(e -> eliminarDocente());
        vtnDocente.getTxtBuscar().addKeyListener(kl);
<<<<<<< HEAD

=======
        vtnDocente.getBtnReporteDocente().addActionListener(e -> llamaReporteDocente());
>>>>>>> d6708c7996af14dcbe84898671d8d2b9b082839e
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Docentes");
    }

    private void cargarDocentes() {
        docentesMD = docente.cargarDocentes();
        llenarTabla(docentesMD);
    }

    public void llenarTabla(ArrayList<DocenteMD> docentesMD) {
        mdTbl.setRowCount(0);
        if (docentesMD != null) {
            docentesMD.forEach(d -> {
                Object[] valores = {d.getPrimerApellido() + " " + d.getSegundoApellido() + " " + d.getPrimerNombre() + " " + d.getSegundoNombre(),
                    d.getCelular(), d.getCorreo(), d.getDocenteTipoTiempo()};
                mdTbl.addRow(valores);
            });
            vtnDocente.getLblResultados().setText(String.valueOf(docentesMD.size()) + " Resultados obtenidos.");
        } else {
            vtnDocente.getLblResultados().setText("0 Resultados obtenidos.");
        }

    }

    public void abrirFrmDocente() {
        frmDocente = new FrmDocente();
        FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(vtnPrin, frmDocente, conecta, ctrPrin);
        ctrFrmDocente.iniciar();
        vtnDocente.dispose();
    }

    public void abrirFrmPersona() {

    }

    public void buscaIncremental(String aguja) {
        docentesMD = docente.buscar(aguja);
        llenarTabla(docentesMD);
    }

    public void editar() {
        int posFila = vtnDocente.getTblDocente().getSelectedRow();
        System.out.println(posFila + " metodo editar de vtnDocenteCTR");
        if (posFila >= 0) {

            int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una Opcion",
                    "Selector de Opciones", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,// null para icono por defecto.
                    new Object[]{"Editar Datos Personales", "Editar Datos de Docente"}, "Editar Datos de Docente");
            if (seleccion == 0) {
              
            

                FrmPersona frmPersona = new FrmPersona();
                FrmPersonaCTR ctrFrmPersona = new FrmPersonaCTR(vtnPrin, frmPersona, conecta, ctrPrin);
                ctrFrmPersona.iniciar();
                ctrFrmPersona.editar(docentesMD.get(posFila));
                vtnDocente.dispose();
            } else {
                if (seleccion == 1) {
                    FrmDocente frmDoc = new FrmDocente();
                    FrmDocenteCTR ctrFrm = new FrmDocenteCTR(vtnPrin, frmDoc, conecta, ctrPrin);
                    ctrFrm.iniciar();
                    frmDoc.getBtnRegistrarPersona().setVisible(false);
                    //Le pasamos la persona de nuestro lista justo la persona seleccionada
                    ctrFrm.habilitarComponentesDocente();

                    ctrFrm.editar(docentesMD.get(posFila));
                    //vtnDocente.getTblDocente().setVisible(false);
                    vtnDocente.dispose();
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA !");
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

    public void eliminarDocente() {
        DocenteMD docentemd = new DocenteMD();
        int posFila = vtnDocente.getTblDocente().getSelectedRow();
        System.out.println(posFila + " metodo editar de vtnDocenteCTR");
        if (posFila >= 0) {
            int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar un Docente? ", " Eliminar Docente ", dialog);
            if (result == 0) {
                String observacion = JOptionPane.showInputDialog("¿Por que motivo elimina este Docente?");
                if (observacion != null) {
                    docentemd.setEstado(observacion.toUpperCase());
                    if (docente.eliminarDocente(docentemd, docentesMD.get(0).getIdPersona()) == true) {
                        JOptionPane.showMessageDialog(null, "Datos Eliminados Satisfactoriamente");
                        cargarDocentes();

                    } else {
                        JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR AL DOCENTE !");
                    }
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA !");
        }
    }
      public void llamaReporteDocente() {
        JasperReport jr;
        String path = "./src/vista/reportes/repDocentes.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
            Map parametro = new HashMap();
            parametro.put("cedula",String.valueOf(mdTbl.getValueAt(vtnDocente.getTblDocente().getSelectedRow(),1)));
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle("Reporte de Materias por Carrera");

        } catch (JRException ex) {
            Logger.getLogger(VtnCarreraCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
