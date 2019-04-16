package controlador.persona;

import controlador.carrera.VtnCarreraCTR;
import controlador.docente.VtnFinContratacionCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.LocalDate;
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
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.usuario.RolMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.docente.VtnFinContratacion;
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
    private final PeriodoLectivoBD prd;

    //Lista de todos los periodos lectivos
    private ArrayList<PeriodoLectivoMD> periodos;

    private ArrayList<DocenteMD> docentesMD;
    private FrmDocente frmDocente;

    private DefaultTableModel mdTbl;
    private PersonaMD perEditar;
    private final PersonaBD per;

    public VtnDocenteCTR(VtnPrincipal vtnPrin, VtnDocente vtnDocente,
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.vtnDocente = vtnDocente;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;
        this.prd = new PeriodoLectivoBD(conecta);
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Docentes");
        ctrPrin.setIconJIFrame(vtnDocente);
        docente = new DocenteBD(conecta);
        per = new PersonaBD(conecta);
        vtnPrin.getDpnlPrincipal().add(vtnDocente);
        vtnDocente.show();
    }

    public void iniciar() {
        vtnDocente.getBtnReporteDocente().setEnabled(false);
        vtnDocente.getBtnReporteDocenteMateria().setEnabled(false);
        String[] titulo = {"Nombres Completos", "Celular", "Correo", "Tipo Contrato"};
        String[][] datos = {};

        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        vtnDocente.getTblDocente().setModel(mdTbl);
        TblEstilo.formatoTbl(vtnDocente.getTblDocente());
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 1, 100);
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 2, 225);
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 3, 140);

        cargarDocentes();
        vtnDocente.getBtnEditar().addActionListener(e -> editar());
        vtnDocente.getBtnIngresar().addActionListener(e -> abrirFrmDocente());
        vtnDocente.getBtnEliminar().addActionListener(e -> eliminarDocente());
        vtnDocente.getBtnFinContratacion().addActionListener(e -> finContratacion());
        vtnDocente.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnDocente.getTxtBuscar().getText().toUpperCase().trim();
                if (b.length() > 2) {
                    buscaIncremental(vtnDocente.getTxtBuscar().getText().toUpperCase());
                }
            }
        });
        vtnDocente.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnDocente.getTxtBuscar(),
                vtnDocente.getBtnBuscar()));
        // vtnDocente.getTblDocente().addActionListener(e -> validarBotonesReportes());
        vtnDocente.getBtnReporteDocente().addActionListener(e -> llamaReporteDocente());
        vtnDocente.getBtnReporteDocenteMateria().addActionListener(e -> botonReporteMateria());
        vtnDocente.getTblDocente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                validarBotonesReportes();
            }
        });
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
                Object[] valores = {d.getPrimerApellido() + " "
                    + d.getSegundoApellido() + " " + d.getPrimerNombre()
                    + " " + d.getSegundoNombre(),
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
        ctrPrin.cerradoJIF();
    }

    public void buscaIncremental(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            docentesMD = docente.buscar(aguja);
            llenarTabla(docentesMD);
        }
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
                perEditar = per.buscarPersona(docentesMD.get(posFila).getIdPersona());
                ctrFrmPersona.editar(perEditar);
                vtnDocente.dispose();
                ctrPrin.cerradoJIF();
            } else {
                if (seleccion == 1) {
                    FrmDocente frmDoc = new FrmDocente();
                    FrmDocenteCTR ctrFrm = new FrmDocenteCTR(vtnPrin, frmDoc, conecta, ctrPrin);
                    ctrFrm.iniciar();
                    frmDoc.getBtnRegistrarPersona().setVisible(false);
                    //Le pasamos la persona de nuestro lista justo la persona seleccionada
                    ctrFrm.habilitarComponentesDocente();
                    frmDoc.getBtnGuardar().setEnabled(true);
                    ctrFrm.editar(docente.buscarDocente(docentesMD.get(posFila).getIdDocente()));
                    //vtnDocente.getTblDocente().setVisible(false);
                    vtnDocente.dispose();
                    ctrPrin.cerradoJIF();
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
        System.out.println(posFila + " metodo eliminar de vtnDocenteCTR");
        if (posFila >= 0) {
            int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar un Docente? ", " Eliminar Docente ", dialog);
            if (result == 0) {
                String observacion = JOptionPane.showInputDialog("¿Por que motivo elimina este Docente?");
                if (observacion != null) {
                    docentemd.setEstado(observacion.toUpperCase());
                    if (docente.eliminarDocente(docentemd, docentesMD.get(0).getIdDocente()) == true) {
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
            int posFila = vtnDocente.getTblDocente().getSelectedRow();
            Map parametro = new HashMap();
            parametro.put("cedula", docentesMD.get(posFila).getIdDocente());
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle("Reporte de Docente");

        } catch (JRException ex) {
            Logger.getLogger(VtnCarreraCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llamaReporteDocenteMateria() {
        JasperReport jr;
        String path = "./src/vista/reportes/repDocentesCarrera.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
            int posFila = vtnDocente.getTblDocente().getSelectedRow();
            Map parametro = new HashMap();
            parametro.put("id", docentesMD.get(posFila).getIdDocente());
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle("Reporte de Materias del Docente");

        } catch (JRException ex) {
            Logger.getLogger(VtnCarreraCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void botonReporteMateria() {
        int s = JOptionPane.showOptionDialog(vtnDocente,
                "Reporte de Materias del Docente\n"
                + "¿Elegir el tipo de Reporte?", "REPORTE MATERIAS",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Materias por Periodo", "Historial de Materias",
                    "Cancelar"}, "Historial de Materias");
        switch (s) {
            case 0:
                seleccionarPeriodo();
                break;
            case 1:
                llamaReporteDocenteMateria();
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
            nmPrd.add(p.getNombre_PerLectivo());
        });
        Object np = JOptionPane.showInputDialog(vtnPrin,
                "Lista de periodos lectivos", "Periodos lectivos",
                JOptionPane.QUESTION_MESSAGE, null,
                nmPrd.toArray(), "Seleccione");
        System.out.println("Selecciono " + np);
        //Se es null significa que no selecciono nada
        if (np == null) {
            botonReporteMateria();
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
            String path = "./src/vista/reportes/repDocenteCarreraPeriodo.jasper";
            File dir = new File("./");
            System.out.println("Direccion: " + dir.getAbsolutePath());
            try {
                int posFila = vtnDocente.getTblDocente().getSelectedRow();
                Map parametro = new HashMap();
                parametro.put("idDocente", docentesMD.get(posFila).getIdDocente());
                parametro.put("periodo", np);
                System.out.println(parametro);
                jr = (JasperReport) JRLoader.loadObjectFromFile(path);
                JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
                JasperViewer view = new JasperViewer(print, false);
                view.setVisible(true);
                view.setTitle("Reporte de Materias del Docente por Periodos Lectivos");

            } catch (JRException ex) {
                Logger.getLogger(VtnCarreraCTR.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void validarBotonesReportes() {
        int selecTabl = vtnDocente.getTblDocente().getSelectedRow();
        if (selecTabl >= 0) {
            vtnDocente.getBtnReporteDocente().setEnabled(true);
            vtnDocente.getBtnReporteDocenteMateria().setEnabled(true);
        } else {
            vtnDocente.getBtnReporteDocente().setEnabled(false);
            vtnDocente.getBtnReporteDocenteMateria().setEnabled(false);
        }
    }

    private void finContratacion() {

         int posFila = vtnDocente.getTblDocente().getSelectedRow();
         if(posFila >= 0){
             VtnFinContratacionCTR vtn_fin_contratacion = new VtnFinContratacionCTR( conecta, vtnPrin, docentesMD.get(posFila));
        vtn_fin_contratacion.iniciar();
         }else{
             JOptionPane.showMessageDialog(null, "Debe seleccionar una fila ");
         }
        
    }
}
