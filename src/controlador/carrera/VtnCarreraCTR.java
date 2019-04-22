package controlador.carrera;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.usuario.RolMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.carrera.FrmCarrera;
import vista.carrera.VtnCarrera;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnCarreraCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnCarrera vtnCarrera;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private final RolMD permisos;
    private final PeriodoLectivoBD prd;

    private final CarreraBD car;
    ArrayList<CarreraMD> carreras;
    private ArrayList<PeriodoLectivoMD> periodos;

    DefaultTableModel mdTbl;

    public VtnCarreraCTR(VtnPrincipal vtnPrin, VtnCarrera vtnCarrera,
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.vtnCarrera = vtnCarrera;
        this.conecta = conecta;
        this.car = new CarreraBD(conecta);
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;
        this.prd = new PeriodoLectivoBD(conecta);
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Carreras");
        ctrPrin.setIconJIFrame(vtnCarrera);
        vtnPrin.getDpnlPrincipal().add(vtnCarrera);
        vtnCarrera.show();
    }

    public void iniciar() {
        vtnCarrera.getBtnReporteAlumnoCarrera().setEnabled(false);
        vtnCarrera.getBtnReporteDocente().setEnabled(false);
        String titutlo[] = {"id", "Codigo", "Nombre", "Fecha Inicio", "Modalidad", "Coordinador"};
        String datos[][] = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titutlo);
        vtnCarrera.getTblMaterias().setModel(mdTbl);
        TblEstilo.formatoTbl(vtnCarrera.getTblMaterias());
        TblEstilo.ocualtarID(vtnCarrera.getTblMaterias());
        TblEstilo.columnaMedida(vtnCarrera.getTblMaterias(), 1, 50);
        TblEstilo.columnaMedida(vtnCarrera.getTblMaterias(), 3, 90);
        TblEstilo.columnaMedida(vtnCarrera.getTblMaterias(), 4, 90);

        cargarCarreras();
        //Le damos accion al btn editar  
        vtnCarrera.getBtnIngresar().addActionListener(e -> abrirFrmCarrera());
        vtnCarrera.getBtnEditar().addActionListener(e -> editarCarrera());
        vtnCarrera.getBtnEliminar().addActionListener(e -> eliminarCarrera());
        vtnCarrera.getTblMaterias().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                validarBotonesReportes();
                validarBotonesReportes2();
            }
        });
        vtnCarrera.getBtnReporteAlumnoCarrera().addActionListener(e -> llamaReporteAlumnoCarrera());
        vtnCarrera.getBtnReporteDocente().addActionListener(e->botonDocentes());
        vtnCarrera.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnCarrera.getTxtBuscar().getText().trim();
                if (b.length() > 2) {
                    buscar(b);
                } else if (b.length() == 0) {
                    cargarCarreras();
                }
            }
        });
        vtnCarrera.getBtnBuscar().addActionListener(e -> buscar(vtnCarrera.getTxtBuscar().getText().trim()));
        vtnCarrera.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnCarrera.getTxtBuscar(),
                vtnCarrera.getBtnBuscar()));
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Carreras");
    }

    private void buscar(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            carreras = car.buscarCarrera(b);
            llenarTbl(carreras);
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "No debe ingresar caracteres especiales.");
        }
    }

    private void editarCarrera() {
        int fila = vtnCarrera.getTblMaterias().getSelectedRow();
        if (fila >= 0) {
            FrmCarrera frmCarrera = new FrmCarrera();
            FrmCarreraCTR ctrFrmCarrera = new FrmCarreraCTR(vtnPrin, frmCarrera, conecta, ctrPrin);
            ctrFrmCarrera.iniciar();
            ctrFrmCarrera.editar(carreras.get(fila));
        }
    }

    private void eliminarCarrera() {
        int fila = vtnCarrera.getTblMaterias().getSelectedRow();
        if (fila >= 0) {
            int r = JOptionPane.showConfirmDialog(vtnPrin, "Seguro que quiere eliminar \n"
                    + vtnCarrera.getTblMaterias().getValueAt(fila, 2).toString() + "\n"
                    + "No se podran recuperar los datos despues.");
            if (r == JOptionPane.OK_OPTION) {
                car.eliminarCarrera(carreras.get(fila).getId());
                cargarCarreras();
            }
        }
    }

    private void abrirFrmCarrera() {
        ctrPrin.abrirFrmCarrera();
        vtnCarrera.dispose();
        ctrPrin.cerradoJIF();
    }

    public void cargarCarreras() {
        carreras = car.cargarCarreras();
        llenarTbl(carreras);
    }

    public void llenarTbl(ArrayList<CarreraMD> carreras) {
        mdTbl.setRowCount(0);
        if (carreras != null) {
            carreras.forEach((c) -> {
                if (c.getCoordinador().getPrimerNombre() == null) {
                    Object valoresSD[] = {c.getId(), c.getCodigo(), c.getNombre(),
                        c.getFechaInicio(), c.getModalidad(), "SIN COORDINADOR "};
                    mdTbl.addRow(valoresSD);
                } else {
                    Object valoresCD[] = {c.getId(), c.getCodigo(), c.getNombre(),
                        c.getFechaInicio(), c.getModalidad(), c.getCoordinador().getPrimerApellido()
                        + " " + c.getCoordinador().getSegundoApellido() + " "
                        + c.getCoordinador().getPrimerNombre() + " " + c.getCoordinador().getSegundoNombre()};
                    mdTbl.addRow(valoresCD);
                }
            });
            vtnCarrera.getLblResultados().setText(carreras.size() + " Resutados obtendidos.");
        } else {
            vtnCarrera.getLblResultados().setText("0 Resutados obtendidos.");
        }

    }

    public void llamaReporteAlumnoCarrera() {

        JasperReport jr;
        String path = "/vista/reportes/repAlumnosCarrera.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
            int posFila = vtnCarrera.getTblMaterias().getSelectedRow();
            Map parametro = new HashMap();
            parametro.put("alumnoCarrera", carreras.get(posFila).getId());
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
            conecta.mostrarReporte(jr, parametro, "Reporte de alumnos por Carrera");
//            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
//            JasperViewer view = new JasperViewer(print, false);
//            view.setVisible(true);
//            view.setTitle("Reporte de Alumnos por Carrera");

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "error" + ex);
        }
    }
 public void botonDocentes() {
        int s = JOptionPane.showOptionDialog(vtnCarrera,
                "Reporte de Docentes por periodo LEctivo\n"
                + "¿Elegir el tipo de Reporte?", "REPORTE DOCENTES",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Elegir Periodo","Cancelar"}, "Cancelar");
        switch (s) {
            case 0:
                seleccionarPeriodo();
                break;
            default:
                break;
        }
    }

    public void seleccionarPeriodo() {
        periodos = prd.cargarPeriodos();
        ArrayList<String> nmPrd = new ArrayList();
        nmPrd.add("Seleccione");
        periodos.forEach(p -> {
            ///546645645645465456
            nmPrd.add(p.getNombre_PerLectivo());
        });
        Object np = JOptionPane.showInputDialog(vtnPrin,
                "Lista de periodos lectivos", "Periodos lectivos",
                JOptionPane.QUESTION_MESSAGE, null,
                nmPrd.toArray(), "Seleccione");
        System.out.println("Selecciono " + np);
        //Se es null significa que no selecciono nada
        if (np == null) {
            botonDocentes();
        } else if (np.equals("Seleccione")) {
            JOptionPane.showMessageDialog(vtnPrin, "Debe seleccionar un periodo lectivo.");
            seleccionarPeriodo();
        } else {
            int posPrd = nmPrd.indexOf(np);
            //Se le resta 1 porque al inicio se agrega uno mas 
            posPrd = posPrd - 1;
            System.out.println("El peridodo esta en la pos: " + posPrd);
            System.out.println("Id del periodo " + periodos.get(posPrd).getId_PerioLectivo());

            JasperReport jr;
            String path = "/vista/reportes/repDocentesPrdLectivo.jasper";
            File dir = new File("./");
            System.out.println("Direccion: " + dir.getAbsolutePath());
            try {
               // int posFila = vtn.getTblDocente().getSelectedRow();
                Map parametro = new HashMap();
              //  parametro.put("idDocente", docentesMD.get(posFila).getIdDocente());
                parametro.put("idPeriodo", np);
                System.out.println(parametro);
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
                JasperViewer view = new JasperViewer(print, false);
                view.setVisible(true);
                view.setTitle("Reporte de Materias del Docente por Periodos Lectivos");

            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "error" + ex);
            }
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

    public void validarBotonesReportes() {
        int selecTabl = vtnCarrera.getTblMaterias().getSelectedRow();
        if (selecTabl >= 0) {
            vtnCarrera.getBtnReporteAlumnoCarrera().setEnabled(true);
        } else {
            vtnCarrera.getBtnReporteAlumnoCarrera().setEnabled(false);
        }
    }

    public void validarBotonesReportes2() {
        int selecTabl = vtnCarrera.getTblMaterias().getSelectedRow();
        if (selecTabl >= 0) {
            vtnCarrera.getBtnReporteDocente().setEnabled(true);
        } else {
            vtnCarrera.getBtnReporteDocente().setEnabled(false);
        }
    }

}
