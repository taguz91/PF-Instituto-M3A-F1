package controlador.curso;

import controlador.carrera.VtnCarreraCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.estilo.TblEstilo;
import modelo.jornada.JornadaMD;
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
import vista.curso.FrmCurso;
import vista.curso.VtnCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author arman
 */
public class VtnCursoCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnCurso vtnCurso;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private final RolMD permisos;

    private final CursoBD curso;
    private CursoMD cursoEdit;
    private ArrayList<CursoMD> cursos;
    //modelo de la tabla 
    private DefaultTableModel mdTbl;
    //Para cargar el combo periodos  
    private final PeriodoLectivoBD prd;
    private ArrayList<PeriodoLectivoMD> periodos;
    //Para guardanos los nombres de los cursos  
    private ArrayList<String> nombresC;
   

    public VtnCursoCTR(VtnPrincipal vtnPrin, VtnCurso vtnCurso, ConectarDB conecta,
            VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.vtnCurso = vtnCurso;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Cursos");
        this.prd = new PeriodoLectivoBD(conecta);
        ctrPrin.setIconJIFrame(vtnCurso);
        vtnPrin.getDpnlPrincipal().add(vtnCurso);
        vtnCurso.show();

        //Inicializamos el curso  
        curso = new CursoBD(conecta);
    }

    public void iniciar() {
        cargarCmbPrdLectio();
        //Iniciamos la tabla  
        String titulo[] = {"id", "Materia", "Docente", "Ciclo", "Curso", "Capacidad"};
        String datos[][] = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        TblEstilo.formatoTbl(vtnCurso.getTblCurso());
        vtnCurso.getTblCurso().setModel(mdTbl);
        TblEstilo.ocualtarID(vtnCurso.getTblCurso());
        //le pasamos un tamaño a las columnas
        TblEstilo.columnaMedida(vtnCurso.getTblCurso(), 3, 60);
        TblEstilo.columnaMedida(vtnCurso.getTblCurso(), 4, 60);
        TblEstilo.columnaMedida(vtnCurso.getTblCurso(), 5, 70);

        cargarNombreCursos();
        cargarCursos();
        vtnCurso.getBtnIngresar().addActionListener(e -> abrirFrmCurso());
        vtnCurso.getBtnEditar().addActionListener(e -> editarCurso());
        //Le damos una accion al combo de periodos lectivos 
        vtnCurso.getCmbPeriodoLectivo().addActionListener(e -> cargarCursosPorPeriodo());
        //Le damos una accion al combo de cursos  
        vtnCurso.getCmbCurso().addActionListener(e -> cargarCursosPorNombre());
        //Buscador 
        vtnCurso.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnCurso.getTxtBuscar().getText().trim();
                if (b.length() > 2) {
                    buscar(b);
                } else if (b.length() == 0) {
                    cargarCursos();
                }
            }
        });
      
        vtnCurso.getBtnBuscar().addActionListener(e -> buscar(vtnCurso.getTxtBuscar().getText().trim()));
        //Validacion del buscador 
        vtnCurso.getBtnBuscar().addKeyListener(new TxtVBuscador(vtnCurso.getTxtBuscar(),
                vtnCurso.getBtnBuscar()));
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Cursos");
          vtnCurso.getBtnListaAlumnos().addActionListener(e -> reporteListaAlumnos());
        //ctrPrin.carga.detener();
    }

    public void abrirFrmCurso() {
        ctrPrin.abrirFrmCurso();
        vtnCurso.dispose();
        ctrPrin.cerradoJIF();
    }

    private void editarCurso() {
        int fila = vtnCurso.getTblCurso().getSelectedRow();
        if (fila >= 0) {
            FrmCurso frmCurso = new FrmCurso();
            FrmCursoCTR ctrFrmCurso = new FrmCursoCTR(vtnPrin, frmCurso, conecta, ctrPrin);
            ctrFrmCurso.iniciar();
            cursoEdit = curso.buscarCurso(
                    Integer.parseInt(vtnCurso.getTblCurso().getValueAt(fila, 0).toString()));
            ctrFrmCurso.editar(cursoEdit);
            vtnCurso.dispose();
            ctrPrin.cerradoJIF();
        }
    }

    private void buscar(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            cursos = curso.buscarCursos(b);
            llenarTbl(cursos);
        } else {
            System.out.println("No ingrese caracteres especiales");
        }
    }

    public void cargarCursos() {
        //Cargamos los cursos y llenamos la tabla
        cursos = curso.cargarCursos();
        llenarTbl(cursos);
    }

    public void cargarNombreCursos() {
        //Cargamos el combo con los nombres de cursos 
        nombresC = curso.cargarNombreCursos();
        cargarCmbCursos(nombresC);
    }

    private void cargarCursosPorPeriodo() {
        int posPrd = vtnCurso.getCmbPeriodoLectivo().getSelectedIndex();
        if (posPrd > 0) {
            //Cargamos el combo de cursos por el periodo  
            nombresC = curso.cargarNombreCursosPorPeriodo(periodos.get(posPrd - 1).getId_PerioLectivo());
            cargarCmbCursos(nombresC);
            //Cargamos los cursos por periodo
            cursos = curso.cargarCursosPorPeriodo(periodos.get(posPrd - 1).getId_PerioLectivo());
            llenarTbl(cursos);
        } else {
            cargarCursos();
        }
    }

    private void cargarCursosPorNombre() {
        int posNom = vtnCurso.getCmbCurso().getSelectedIndex();
        int posPrd = vtnCurso.getCmbPeriodoLectivo().getSelectedIndex();
        if (posNom == 0) {
            cargarCursosPorPeriodo();
        } else if (posNom > 0 && posPrd == 0) {
            cursos = curso.cargarCursosPorNombre(vtnCurso.getCmbCurso().getSelectedItem().toString());
            llenarTbl(cursos);
        } else if (posNom > 0 && posPrd > 0) {
            cursos = curso.cargarCursosPorNombreYPrdLectivo(vtnCurso.getCmbCurso().getSelectedItem().toString(),
                    periodos.get(posPrd - 1).getId_PerioLectivo());
            llenarTbl(cursos);
        }
    }

    private void llenarTbl(ArrayList<CursoMD> cursos) {
        mdTbl.setRowCount(0);
        if (cursos != null) {
            cursos.forEach((c) -> {
                Object valores[] = {
                    c.getId_curso(),
                    c.getId_materia().getNombre(),
                    c.getId_docente().getPrimerNombre() + " "
                    + c.getId_docente().getPrimerApellido(),
                    c.getCurso_ciclo(),
                    c.getCurso_nombre(),
                    c.getCurso_capacidad()
                };
                mdTbl.addRow(valores);
            });
            vtnCurso.getLblResultados().setText(cursos.size() + " Resultados obtenidos.");
        } else {
            vtnCurso.getLblResultados().setText("0 Resultados obtenidos.");
        }
    }

    private void cargarCmbPrdLectio() {
        periodos = prd.cargarPeriodos();
        vtnCurso.getCmbPeriodoLectivo().removeAllItems();
        if (periodos != null) {
            vtnCurso.getCmbPeriodoLectivo().addItem("Todos");
            periodos.forEach((p) -> {
                vtnCurso.getCmbPeriodoLectivo().addItem(p.getNombre_PerLectivo());
            });
        }
    }
  public void reporteListaAlumnos() {
        JasperReport jr;
        String path = "./src/vista/reportes/repListaAlumnos.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
             int posFila = vtnCurso.getTblCurso().getSelectedRow();
            Map parametro = new HashMap();
           // parametro.put("idDocente", cursos.get(posFila).getId_docente());
            parametro.put("ciclo", cursos.get(posFila).getCurso_ciclo());
            parametro.put("paralelo", vtnCurso.getCmbCurso().getSelectedItem());
            parametro.put("periodo", periodos.get(posFila).getNombre_PerLectivo());
            parametro.put("idDocente",cursos.get(posFila).getId_docente().getIdDocente());
           // parametro.put("jornada", jornada.get(posFila).getNombre());
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
            JasperViewer view = new JasperViewer(print, false);
            view.setVisible(true);
            view.setTitle("Lista de estudiantes");

        } catch (JRException ex) {
            Logger.getLogger(VtnCarreraCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void cargarCmbCursos(ArrayList<String> nombresC) {
        vtnCurso.getCmbCurso().removeAllItems();
        if (nombresC != null) {
            vtnCurso.getCmbCurso().addItem("Todos");
            nombresC.forEach(n -> {
                vtnCurso.getCmbCurso().addItem(n);
            });
            vtnCurso.getCmbCurso().setSelectedIndex(0);
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
