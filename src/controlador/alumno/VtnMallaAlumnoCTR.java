package controlador.alumno;

import controlador.carrera.VtnCarreraCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
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
import modelo.alumno.AlumnoCarreraBD;
import modelo.alumno.AlumnoCarreraMD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.alumno.MallaAlumnoBD;
import modelo.alumno.MallaAlumnoMD;
import modelo.usuario.RolMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.alumno.VtnMallaAlumno;
import vista.principal.VtnPrincipal;

/**
 * Informacion de toda la malla de una alumno 
 * @author Johnny
 */
public class VtnMallaAlumnoCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnMallaAlumno vtnMallaAlm;
    private final ConectarDB conecta;
    private final MallaAlumnoBD mallaAlm;
    private final VtnPrincipalCTR ctrPrin;
    private final String[] cmbEstado = {"Seleccione", "Cursado", "Matriculado", "Pendiente", "Reprobado", "Retirado"};
    private final RolMD permisos;

    private ArrayList<MallaAlumnoMD> mallas = new ArrayList();
    //Para cargar los combos 
    private final AlumnoCarreraBD almCar;
    private ArrayList<AlumnoCarreraMD> alumnos;

    private final CarreraBD car;
    private ArrayList<CarreraMD> carreras = new ArrayList();
    //Modelo de la tabla  
    private DefaultTableModel mdlTbl;

    public VtnMallaAlumnoCTR(VtnPrincipal vtn, VtnMallaAlumno vtnMallaAlm,
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtn;
        this.vtnMallaAlm = vtnMallaAlm;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Malla alumnos");
        ctrPrin.setIconJIFrame(vtnMallaAlm);
        this.almCar = new AlumnoCarreraBD(conecta);
        this.mallaAlm = new MallaAlumnoBD(conecta);
        this.car = new CarreraBD(conecta);
        vtnPrin.getDpnlPrincipal().add(vtnMallaAlm);
        vtnMallaAlm.show();
    }
    
    /**
     * Iniciamos dependencias y formatos de tabla.
     */
    public void iniciar() {
        //Iniciamos la tabla  
        //El boton de reportes sale en inactivo
       // vtnMallaAlm.getBtnReporteMallaAlumno().setEnabled(false);

        String titulo[] = {"id", "Alumno", "Materia", "Estado", "Ciclo", "Matrícula", "Nota 1", "Nota 2", "Nota 3"};
        String datos[][] = {};
        mdlTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        TblEstilo.formatoTbl(vtnMallaAlm.getTblMallaAlumno());
        vtnMallaAlm.getTblMallaAlumno().setModel(mdlTbl);
        TblEstilo.ocualtarID(vtnMallaAlm.getTblMallaAlumno());

        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 4, 60);
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 5, 60);
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 6, 60);
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 7, 60);
        TblEstilo.columnaMedida(vtnMallaAlm.getTblMallaAlumno(), 8, 60);

        cargarCmbCarrera();

        //Inciiamos los combos en falso 
        vtnMallaAlm.getCmbAlumnos().setEnabled(false);
        vtnMallaAlm.getCmbEstado().setEnabled(false);

        vtnMallaAlm.getCmbCarreras().addActionListener(e -> clickCmbCarrera());
        vtnMallaAlm.getCmbAlumnos().addActionListener(e -> cargarPorAlumno());
        vtnMallaAlm.getCmbEstado().addActionListener(e -> cargarPorEstado());
        vtnMallaAlm.getBtnIngNota().addActionListener(e -> ingresarNota());
        vtnMallaAlm.getBtnBuscar().addActionListener(e -> buscarMalla(
                vtnMallaAlm.getTxtBuscar().getText().trim()));
        //Agregamos la validacion de buscador 
        vtnMallaAlm.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnMallaAlm.getTxtBuscar()));
        //Buscador
        vtnMallaAlm.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String a = vtnMallaAlm.getTxtBuscar().getText().trim();
                if (a.length() >= 10) {
                    buscarMalla(a);
                }
            }
        });
        vtnMallaAlm.getBtnReporteMallaAlumno().addActionListener(e -> llamaReporteMallaALumno());
        //Validacion del buscador 
        vtnMallaAlm.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnMallaAlm.getTxtBuscar(),
                vtnMallaAlm.getBtnBuscar()));
        //Modificamos el cmb para que sea editable  
        vtnMallaAlm.getCmbAlumnos().setEditable(true);
        //Buscar en el combo

        vtnMallaAlm.getCmbAlumnos().getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                int l = 5;
                String a = vtnMallaAlm.getCmbAlumnos().getEditor().getItem().toString().trim();
                if (Validar.esNumeros(a)) {
                    l = 10;
                }
                if (e.getKeyCode() != 38 && e.getKeyCode() != 40
                        && e.getKeyCode() != 37 && e.getKeyCode() != 39
                        && a.length() >= l && e.getKeyCode() != 13
                        && e.getKeyCode() != 10) {
                    buscarAlumno(a);
                }
            }
        });

        //Evento de clcik en la tabla 
        vtnMallaAlm.getTblMallaAlumno().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //clickTbl();
            }
        });
        //Prueba cargando todos los datos 
        //Funciona de la patada
        Instant iniBusqueda = Instant.now();
        cargarMallas();
        Instant terBusqueda = Instant.now();
        System.out.println("El tiempo que tardó en buscar malla alumnos es: "
                + Duration.between(iniBusqueda, terBusqueda).toMillis() + " milisegundos");
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Malla alumnos");
    }
    
    /**
     * Cargamos todas las mallas de un alumno
     */
    private void cargarMallas() {
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Malla alumnos");

        mallas = mallaAlm.cargarMallasTbl();
        llenarTbl(mallas);
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Malla alumnos");
    }
    
    /**
     * Buscamos la malla de una alumno desde el combo box  
     * se filtra por carrera seleccionada tambien.
     * @param aguja 
     */
    private void buscarAlumno(String aguja) {
        int posCar = vtnMallaAlm.getCmbCarreras().getSelectedIndex();
        if (Validar.esLetrasYNumeros(aguja)) {
            alumnos = almCar.buscarAlumnoCarrera(carreras.get(posCar - 1).getId(),
                    aguja);
            llenarCmbAlumno(alumnos);
            vtnMallaAlm.getCmbAlumnos().showPopup();
            vtnMallaAlm.getCmbAlumnos().getEditor().setItem(aguja);
        }
    }
    
    /**
     * Buscamos la malla de un alumno por cedula o nombre.
     * @param aguja 
     */
    private void buscarMalla(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            //Cambiamos el estado del cursos  
            vtnPrin.setCursor(new Cursor(3));
            ctrPrin.estadoCargaVtn("Malla alumnos");

            mallas = mallaAlm.buscarMallaAlumno(aguja);
            llenarTbl(mallas);
            //Cuando termina de cargar todo se le vuelve a su estado normal.
            vtnPrin.setCursor(new Cursor(0));
            ctrPrin.estadoCargaVtnFin("Malla alumnos");
        }
    }
    
    /**
     * Cargamos la malla de un alumno
     */
    private void cargarPorAlumno() {
        int posAlm = vtnMallaAlm.getCmbAlumnos().getSelectedIndex();
        if (posAlm > 0) {
            //Cambiamos el estado del cursos  
            vtnPrin.setCursor(new Cursor(3));
            ctrPrin.estadoCargaVtn("Malla alumnos");

            mallas = mallaAlm.cargarMallasPorEstudiante(alumnos.get(posAlm - 1).getId());

            vtnMallaAlm.getCmbEstado().setEnabled(true);

            //Guardamos la posicion del alumno seleccionado 
            posFila = vtnMallaAlm.getCmbAlumnos().getSelectedIndex() - 1;
            if (posFila >= 0) {
                idAlmnSeleccionado = alumnos.get(posFila).getId();
                //Se activa el boton de reporte tambien 
                vtnMallaAlm.getBtnReporteMallaAlumno().setEnabled(true);
            }

            llenarTbl(mallas);
            cargarCmbEstado();
            //Cuando termina de cargar todo se le vuelve a su estado normal.
            vtnPrin.setCursor(new Cursor(0));
            ctrPrin.estadoCargaVtnFin("Malla alumnos");
        } else {
            //Borramos todos los datos de la tabla si no se selecciona ninguno 
            mdlTbl.setRowCount(0);
            vtnMallaAlm.getCmbEstado().removeAllItems();
        }
    }
    
    /**
     * Ingresamos una nota para pruebas.
     */
    private void ingresarNota() {
        int pos = vtnMallaAlm.getTblMallaAlumno().getSelectedRow();
        if (pos >= 0) {
            MallaAlumnoMD malla = mallas.get(pos);
            if (malla.getMallaNumMatricula() > 0) {
                if (malla.getEstado().equals("C")) {
                    JOptionPane.showMessageDialog(vtnPrin, "Ya cursó ésta materia, no se puede ingresar  una nota.");
                } else {
                    String nota = JOptionPane.showInputDialog(
                            "Ingrese la nota de \n" + malla.getMateria().getNombre() + "\n"
                            + "Número de matrícula: " + malla.getMallaNumMatricula());
                    if (Validar.esNota(nota)) {
                        mallaAlm.ingresarNota(malla.getId(), malla.getMallaNumMatricula(), Double.parseDouble(nota));
                    } else {
                        JOptionPane.showMessageDialog(vtnPrin, "Ingrese una nota válida.");
                        ingresarNota();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(vtnPrin, "No se encuentra matrícula en esta \n"
                        + "materia, no puede ingresar su nota.");
            }
        } else {
            JOptionPane.showMessageDialog(vtnPrin, "Debe seleccionar una fila para poder ingresar una nota.");
        }
    }
    
    /**
     * Cargamos la malla de un alumno por estado
     */
    private void cargarPorEstado() {
        int posAlm = vtnMallaAlm.getCmbAlumnos().getSelectedIndex();
        int posEst = vtnMallaAlm.getCmbEstado().getSelectedIndex();
        if (posAlm > 0 && posEst > 0) {
            //Cambiamos el estado del cursos  
            vtnPrin.setCursor(new Cursor(3));
            ctrPrin.estadoCargaVtn("Malla alumnos");

            mallas = mallaAlm.cargarMallaAlumnoPorEstado(
                    alumnos.get(posAlm - 1).getId(), cmbEstado[posEst]);
            llenarTbl(mallas);
            //Cuando termina de cargar todo se le vuelve a su estado normal.
            vtnPrin.setCursor(new Cursor(0));
            ctrPrin.estadoCargaVtnFin("Malla alumnos");
        } else if (posAlm > 0) {
            cargarPorAlumno();
        }
    }
    
    /**
     * Llenamos la tabla con los datos requeridos
     * @param mallas ArrayList<MallaAlumnoMD> 
     */
    private void llenarTbl(ArrayList<MallaAlumnoMD> mallas) {
        mdlTbl.setRowCount(0);
        if (mallas != null) {
            mallas.forEach((m) -> {
                Object valores[] = {m.getId(), m.getAlumnoCarrera().getAlumno().getPrimerNombre()
                    + " " + m.getAlumnoCarrera().getAlumno().getSegundoNombre()
                    + " " + m.getAlumnoCarrera().getAlumno().getPrimerApellido()
                    + " " + m.getAlumnoCarrera().getAlumno().getSegundoApellido(),
                    m.getMateria().getNombre(),
                    m.getEstado(),
                    m.getMallaCiclo(), m.getMallaNumMatricula(), m.getNota1(),
                    m.getNota2(), m.getNota3()};
                mdlTbl.addRow(valores);
            });
            vtnMallaAlm.getLblResultados().setText(mallas.size() + " Resultados obtenidos.");
        } else {
            vtnMallaAlm.getLblResultados().setText("0 Resultados obtenidos.");
        }

    }
    
    /**
     * Cargamos las carreras en un combo 
     */
    private void cargarCmbCarrera() {
        carreras = car.cargarCarrerasCmb();
        vtnMallaAlm.getCmbCarreras().removeAllItems();
        if (carreras != null) {
            vtnMallaAlm.getCmbCarreras().addItem("Seleccione");
            carreras.forEach(c -> {
                vtnMallaAlm.getCmbCarreras().addItem(c.getCodigo());
            });
        }
    }
    
    /**
     * Al hacer click en un combo de carreras, se activa el combo
     * de alumnos y se carga el combo de estado, si no se selecciona
     * una carrera, se desactiva el combo y se eliminan todos los items 
     * del combo alumnos y estado.
     */
    private void clickCmbCarrera() {
        int posCar = vtnMallaAlm.getCmbCarreras().getSelectedIndex();
        if (posCar > 0) {
            vtnMallaAlm.getCmbAlumnos().setEnabled(true);
            cargarCmbEstado();
        } else {
            vtnMallaAlm.getCmbAlumnos().removeAllItems();
            vtnMallaAlm.getCmbAlumnos().setEnabled(false);
            vtnMallaAlm.getCmbEstado().removeAllItems();
        }
    }
    
    /**
     * Llenamos el combo alumnos con los alumnos encontrados.
     * @param alumnos 
     */
    private void llenarCmbAlumno(ArrayList<AlumnoCarreraMD> alumnos) {
        vtnMallaAlm.getCmbAlumnos().removeAllItems();
        if (alumnos != null) {
            vtnMallaAlm.getCmbAlumnos().addItem("");
            alumnos.forEach((a) -> {
                vtnMallaAlm.getCmbAlumnos().addItem(a.getAlumno().getPrimerApellido() + " "
                        + a.getAlumno().getSegundoApellido() + " "
                        + a.getAlumno().getPrimerNombre() + " "
                        + a.getAlumno().getSegundoNombre());
            });
        }
    }
    
    /**
     * Cargamos el combo estados con los estados por defecto del sistema.
     */
    private void cargarCmbEstado() {
        vtnMallaAlm.getCmbEstado().removeAllItems();
        for (String e : cmbEstado) {
            vtnMallaAlm.getCmbEstado().addItem(e);
        }
        vtnMallaAlm.getCmbEstado().setSelectedIndex(0);
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
    //Para saber el alumno seleccionado 
    private int posFila = -1;
    private int idAlmnSeleccionado = 0;
    
    /**
     * Llamamos al reporte de la malla alumno
     */
    public void llamaReporteMallaALumno() {

        JasperReport jr;
        String path = "./src/vista/reportes/repMalaAlumno.jasper";
        File dir = new File("./");
        System.out.println("Direccion: " + dir.getAbsolutePath());
        try {
//            if (idAlmnSeleccionado > 0) {
//                //int posFila = vtnMallaAlm.getTblMallaAlumno().getSelectedRow();
//                Map parametro = new HashMap();
//                parametro.put("consulta", mallaAlm.getSql());
//                System.out.println(parametro);
//                jr = (JasperReport) JRLoader.loadObjectFromFile(path);
//                JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
//                JasperViewer view = new JasperViewer(print, false);
//                view.setVisible(true);
//                view.setTitle("Reporte de Malla de Alumno");
//            }
            
            Map parametro = new HashMap();
                parametro.put("consulta", mallaAlm.getSql());
                System.out.println(parametro);
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                JasperPrint print = JasperFillManager.fillReport(jr, parametro, conecta.getConecction());
                JasperViewer view = new JasperViewer(print, false);
                view.setVisible(true);
                view.setTitle("Reporte de Malla de Alumno");
        } catch (JRException ex) {
            Logger.getLogger(VtnCarreraCTR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   /* private void clickTbl() {
        posFila = vtnMallaAlm.getTblMallaAlumno().getSelectedRow();
        idAlmnSeleccionado = mallas.get(posFila).getAlumnoCarrera().getId();
        System.out.println("Este es el id que sale "+idAlmnSeleccionado);
        //Activamos el boton  
        vtnMallaAlm.getBtnReporteMallaAlumno().setEnabled(true);
    }
*/
}
