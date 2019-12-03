package controlador.alumno;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.alumno.AlumnoCarreraBD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import vista.alumno.FrmAlumnoCarrera;

/**
 *
 * @author Johnny
 */
public class FrmAlumnoCarreraCTR extends DCTR {

    private final FrmAlumnoCarrera frmAlmCarrera;
    private final AlumnoCarreraBD almnCarrera;
    private boolean matriculado = false;
    private String carrera;

    //Modelo de la tabla  
    private DefaultTableModel mdTbl;

    //Para rellenar los combo box
    private final AlumnoBD almn;
    private ArrayList<AlumnoMD> alumnos;

    private final CarreraBD carr;
    private ArrayList<CarreraMD> carreras;

    /**
     * Iniciamos todos los modelos de base de datos.
     *
     * @param frmAlmCarrera FrmAlumnoCarrera: El formulario que se mostrara
     * @param ctrPrin VtnPrincipalCTR: Controlador principal del sitema,
     * contiene metodos para todos.
     */
    public FrmAlumnoCarreraCTR(FrmAlumnoCarrera frmAlmCarrera, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.frmAlmCarrera = frmAlmCarrera;

        this.almnCarrera = new AlumnoCarreraBD(ctrPrin.getConecta());
        this.almn = new AlumnoBD(ctrPrin.getConecta());
        this.carr = new CarreraBD(ctrPrin.getConecta());
    }

    /**
     * Iniciamos dependencias. Estilizamos tabla Cargamos combos Buscador
     * Eventos de botones
     */
    public void iniciar() {
        ocultarErrores();
        cargarCmbCarreras();

        String[] titulo = {"Cédula", "Nombre"};
        String[][] datos = {};

        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        frmAlmCarrera.getTblAlumnos().setModel(mdTbl);
        TblEstilo.formatoTbl(frmAlmCarrera.getTblAlumnos());

        frmAlmCarrera.getBtnGuardar().addActionListener(e -> guardar());
        frmAlmCarrera.getBtnBuscar().addActionListener(e -> buscarAlmns(
                frmAlmCarrera.getTxtBuscar().getText().trim()));
        frmAlmCarrera.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String aguja = frmAlmCarrera.getTxtBuscar().getText().trim();
                if (e.getKeyCode() == 10) {
                    buscarAlmns(aguja);
                }
            }
        });
        frmAlmCarrera.getTxtBuscar().addKeyListener(new TxtVBuscador(frmAlmCarrera.getTxtBuscar(),
                frmAlmCarrera.getBtnBuscar()));
        TblEstilo.columnaMedida(frmAlmCarrera.getTblAlumnos(), 0, 100);
        //Para poder editarlo
        //frmAlmCarrera.getCmbCarreras().setEditable(true);
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        ctrPrin.estadoCargaFrmFin("Alumno por carrera");
        //Accion al seleccionar un alumno 
        frmAlmCarrera.getTblAlumnos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickAlumno();
            }

        });
        //Agregamos la ventana al dpn
        ctrPrin.agregarVtn(frmAlmCarrera);
    }

    /**
     * Oculta los errores del formulario.
     */
    public void ocultarErrores() {
        //Ocultamos el error  
        frmAlmCarrera.getLblError().setVisible(false);
    }

    /**
     * Comprueba que el formulario este correcto. Si todo el formulario esta
     * lleno correctamente se insertara en base de datos.
     */
    private void guardar() {
        if (!matriculado) {
            int posAlm = frmAlmCarrera.getTblAlumnos().getSelectedRow();
            int posCar = frmAlmCarrera.getCmbCarreras().getSelectedIndex();

            boolean guardar = !buscarSiEstaMatriculado(posAlm, posCar);

            if (posAlm < 0 || posCar < 1) {
                guardar = false;
                frmAlmCarrera.getLblError().setVisible(true);
            } else {
                frmAlmCarrera.getLblError().setVisible(false);
            }

            if (guardar) {

                int r = JOptionPane.showConfirmDialog(ctrPrin.getVtnPrin(), "Se matricula a: \n"
                        + alumnos.get(posAlm).getNombreCompleto() + "\n En: \n"
                        + carreras.get(posCar - 1).getNombre());
                if (r == JOptionPane.YES_OPTION) {
                    almnCarrera.setAlumno(alumnos.get(posAlm));
                    almnCarrera.setCarrera(carreras.get(posCar - 1));
                    if (almnCarrera.guardar()) {

                        frmAlmCarrera.dispose();
                        ctrPrin.cerradoJIF();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(ctrPrin.getVtnPrin(), "Ya esta matriculado.\n " + carrera
                    + "No se puede inscribir en otra carrera.");
        }
    }

    private boolean buscarSiEstaMatriculado(int posAlmn, int posCar) {
        carrera = almnCarrera.estaMatriculadoEn(
                alumnos.get(posAlmn).getId_Alumno(),
                carreras.get(posCar - 1).getId()
        );
        if (carrera.length() > 0) {
            JOptionPane.showMessageDialog(
                    ctrPrin.getVtnPrin(),
                    alumnos.get(posAlmn).getNombreCompleto() + "\n"
                    + "Se encuentra matriculado en: \n" + carrera
            );
            return true;
        } else {
            return false;
        }
    }

    /**
     * Al seleecionar un se busca si no esta ya matriculado
     */
    private void clickAlumno() {
        int posAlmn = frmAlmCarrera.getTblAlumnos().getSelectedRow();
        int posCar = frmAlmCarrera.getCmbCarreras().getSelectedIndex();
        if (posAlmn >= 0 && posCar > 0) {
            matriculado = buscarSiEstaMatriculado(posAlmn, posCar);
        }
    }

    /**
     * Busca los alumnos de, pero antes comprueba que lo que se ingresa sea
     * unicamente letras y numeros. Para que no exista ningun inyeccion.
     *
     * @param aguja String: Se puede enviar la cedula o el nombre
     */
    public void buscarAlmns(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            frmAlmCarrera.getCmbCarreras().setSelectedIndex(0);
            alumnos = almn.buscarAlumnos(aguja);
            mdTbl.setRowCount(0);
            if (alumnos != null) {
                alumnos.forEach(a -> {
                    Object[] valores = {a.getIdentificacion(),
                        a.getPrimerApellido() + " "
                        + a.getSegundoApellido() + " "
                        + a.getPrimerNombre() + " "
                        + a.getSegundoNombre()};
                    mdTbl.addRow(valores);
                });
            }
        }
    }

    /**
     * Busca en base de datos todas las carreras que no estan eliminadas, para
     * cargarlas en el combo.
     */
    private void cargarCmbCarreras() {
        carreras = carr.cargarCarreras();
        if (carreras != null) {
            frmAlmCarrera.getCmbCarreras().removeAllItems();
            frmAlmCarrera.getCmbCarreras().addItem("Seleccione");
            carreras.forEach((c) -> {
                frmAlmCarrera.getCmbCarreras().addItem(c.getNombre());
            });
        }
    }

}
