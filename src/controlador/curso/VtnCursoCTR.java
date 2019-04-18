package controlador.curso;

import controlador.carrera.VtnCarreraCTR;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
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
    private int posFila;

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
        vtnCurso.getBtnListaAlumnos().setEnabled(false);
        cargarCmbPrdLectio();
        cargarNombreCursos();
        //Iniciamos la tabla  
        String titulo[] = {"id", "Periodo", "Materia", "Docente", "Ciclo", "Curso", "Capacidad"};
        String datos[][] = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        vtnCurso.getTblCurso().setModel(mdTbl);
        //Formato de la tabla
        TblEstilo.formatoTbl(vtnCurso.getTblCurso());
        TblEstilo.ocualtarID(vtnCurso.getTblCurso());
        //le pasamos un tamaño a las columnas
        TblEstilo.columnaMedida(vtnCurso.getTblCurso(), 4, 60);
        TblEstilo.columnaMedida(vtnCurso.getTblCurso(), 5, 60);
        TblEstilo.columnaMedida(vtnCurso.getTblCurso(), 6, 70);
        //cargarNombreCursos();
        cargarCursos();
        vtnCurso.getBtnIngresar().addActionListener(e -> abrirFrmCurso());
        vtnCurso.getBtnEditar().addActionListener(e -> editarCurso());
        vtnCurso.getBtnEliminar().addActionListener(e -> eliminarCurso());
        vtnCurso.getBtnHorario().addActionListener(e -> horario());
        vtnCurso.getCbxEliminados().addActionListener(e -> verCursosEliminados());
        //Le damos una accion al combo de periodos lectivos 
        vtnCurso.getCmbPeriodoLectivo().addActionListener(e -> cargarCursosPorPeriodo());
        //Le damos una accion al combo de cursos  
        vtnCurso.getCmbCurso().addActionListener(e -> cargarCursosPorNombre());
        //Buscador 
        vtnCurso.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnCurso.getTxtBuscar().getText().trim();
                System.out.println(e.getKeyCode());
                //10 ENter - 32 Espacio - 8 Borrar
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
        vtnCurso.getTblCurso().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                validarBotonesReportes();
            }
        });

        vtnCurso.getBtnListaAlumnos().addActionListener(e -> reporteListaAlumnos());
    }

    /**
     * T
     * Abrimos el formulario para ingresar un curso
     */
    public void abrirFrmCurso() {
        ctrPrin.abrirFrmCurso();
        vtnCurso.dispose();
        ctrPrin.cerradoJIF();
    }

    /**
     * Al seleccionar un curso se puede editar.
     */
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

    /**
     * Buscamos unicamente por nombre y cedula
     *
     * @param b
     */
    private void buscar(String b) {
        if (Validar.esLetrasYNumeros(b)) {
            cursos = curso.buscarCursos(b);
            llenarTbl(cursos);
        } else {
            System.out.println("No ingrese caracteres especiales");
        }
    }

    /**
     * Cargamos todos los cursos que existen en el isntituto, incluyendo el
     * periodo lectivo en el que se abrieron
     */
    public void cargarCursos() {
        System.out.println("Se cargaron cursos");
        cursos = curso.cargarCursos();
        llenarTbl(cursos);
    }

    public void cargarNombreCursos() {
        //Cargamos el combo con los nombres de cursos 
        nombresC = curso.cargarNombreCursos();
        cargarCmbCursos(nombresC);
    }

    private void cargarCursosPorPeriodo() {
        vtnCurso.getTxtBuscar().setText("");
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
        vtnCurso.getTxtBuscar().setText("");
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
                    c.getId_prd_lectivo().getNombre_PerLectivo(),
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
        periodos = prd.cargarPrdParaCmbVtn();
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
        String path = "/vista/reportes/repListaAlumno.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
            int posFila = vtnCurso.getTblCurso().getSelectedRow();
            Map parametro = new HashMap();
            parametro.put("curso", cursos.get(posFila).getId_curso());
            // parametro.put("jornada", jornada.get(posFila).getNombre());
            System.out.println(parametro);
            jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
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

    private void eliminarCurso() {
        int posCur = vtnCurso.getTblCurso().getSelectedRow();

        if (posCur >= 0) {
            String nom = vtnCurso.getTblCurso().getValueAt(posCur, 5).toString();
            int num = curso.numAlumnos(cursos.get(posCur).getId_curso());
            int r = JOptionPane.showConfirmDialog(vtnPrin, "Seguro quiere "
                    + vtnCurso.getBtnEliminar().getText().toLowerCase() + " el curso " + nom + "\n"
                    + "Se " + vtnCurso.getBtnEliminar().getText().toLowerCase()
                    + "an todos los alumnos de este curso: " + num);
            if (r == JOptionPane.YES_OPTION) {
                if (vtnCurso.getCbxEliminados().isSelected()) {
                    curso.activarCurso(cursos.get(posCur).getId_curso());
                } else {
                    curso.eliminarCurso(cursos.get(posCur).getId_curso());
                }
                verCursosEliminados();
            }

        } else {
            JOptionPane.showMessageDialog(vtnPrin, "Debe seleccionar una final antes.");
        }
    }

    /**
     * Cargamos los cursos que se eliminaron en la base
     */
    private void verCursosEliminados() {
        if (vtnCurso.getCbxEliminados().isSelected()) {
            cursos = curso.cargarCursosEliminados();
            llenarTbl(cursos);
            vtnCurso.getBtnEliminar().setText("Activar");
        } else {
            cursos = curso.cargarCursos();
            llenarTbl(cursos);
            vtnCurso.getBtnEliminar().setText("Eliminar");
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
        int selecTabl = vtnCurso.getTblCurso().getSelectedRow();
        if (selecTabl >= 0) {
            vtnCurso.getBtnListaAlumnos().setEnabled(true);
        } else {
            vtnCurso.getBtnListaAlumnos().setEnabled(false);
        }
    }
    
    /**
     * Este es el horario de las materias
     */
    private void horario(){
        posFila = vtnCurso.getTblCurso().getSelectedRow();
        if (posFila >= 0) {
            JDHorarioCTR ctr = new JDHorarioCTR(conecta, vtnPrin, ctrPrin, cursos.get(posFila));
            ctr.iniciar();
        }else{
            JOptionPane.showMessageDialog(vtnPrin, "Antes debe seleccionar un curso.");
        }
    }
}
