package controlador.alumno;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.alumno.AlumnoCarreraBD;
import modelo.alumno.AlumnoCarreraMD;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.alumno.MallaAlumnoBD;
import modelo.alumno.MallaAlumnoMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import vista.alumno.VtnMallaAlumno;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnMallaAlumnoCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnMallaAlumno vtnMallaAlm;
    private final ConectarDB conecta;
    private final MallaAlumnoBD mallaAlm;
    private final VtnPrincipalCTR ctrPrin;
    private final String[] cmbEstado = {"Seleccione", "Cursado", "Matriculado", "Pendiente", "Reprobado"};

    private ArrayList<MallaAlumnoMD> mallas = new ArrayList();
    //Para cargar los combos 
    private final AlumnoCarreraBD almCar;
    private ArrayList<AlumnoCarreraMD> alumnos;

    private final CarreraBD car;
    private ArrayList<CarreraMD> carreras = new ArrayList();
    //Modelo de la tabla  
    private DefaultTableModel mdlTbl;

    public VtnMallaAlumnoCTR(VtnPrincipal vtn, VtnMallaAlumno vtnMallaAlm, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtn;
        this.vtnMallaAlm = vtnMallaAlm;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Malla alumnos");
        
        this.almCar = new AlumnoCarreraBD(conecta);
        this.mallaAlm = new MallaAlumnoBD(conecta);
        this.car = new CarreraBD(conecta);
        vtnPrin.getDpnlPrincipal().add(vtnMallaAlm);
        vtnMallaAlm.show();
    }

    public void iniciar() {
        //Iniciamos la tabla  
        String titulo[] = {"id", "Alumno", "Materia", "Estado", "Ciclo", "Matricula", "Nota 1", "Nota 2", "Nota 3"};
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
                if (a.length() > 10) {
                    buscarMalla(a);
                }
            }
        });
        //Modificamos el cmb para que sea editable  
        vtnMallaAlm.getCmbAlumnos().setEditable(true);
        //Buscar en el combo

        vtnMallaAlm.getCmbAlumnos().getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            
            @Override
            public void keyReleased(KeyEvent e) {
                int l = 3;
                String a = vtnMallaAlm.getCmbAlumnos().getEditor().getItem().toString().trim();
                if (Validar.esNumeros(a)) {
                    l = 9;
                }
                if (e.getKeyCode() != 38 && e.getKeyCode() != 40 
                        && e.getKeyCode() != 37 && e.getKeyCode() != 39 
                        && a.length() >= l && e.getKeyCode() != 13) {
                    buscarAlumno(a);
                }
            }
        });
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Malla alumnos");
    }

    private void buscarAlumno(String aguja) {
        System.out.println(aguja);
        int posCar = vtnMallaAlm.getCmbCarreras().getSelectedIndex();
        if (Validar.esLetrasYNumeros(aguja)) {
            alumnos = almCar.buscarAlumnoCarrera(carreras.get(posCar - 1).getId(), 
                    aguja);
            llenarCmbAlumno(alumnos);
            vtnMallaAlm.getCmbAlumnos().showPopup();
            vtnMallaAlm.getCmbAlumnos().getEditor().setItem(aguja);
            
        }
    }

    private void buscarMalla(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            mallas = mallaAlm.buscarMallaAlumno(aguja);
            llenarTbl(mallas);
        }
    }

    private void cargarPorAlumno() {
        int posAlm = vtnMallaAlm.getCmbAlumnos().getSelectedIndex();
        if (posAlm > 0) {
            mallas = mallaAlm.cargarMallasPorEstudiante(alumnos.get(posAlm - 1).getId());
            vtnMallaAlm.getCmbEstado().setEnabled(true);
            llenarTbl(mallas);
            cargarCmbEstado();
        } else {
            //Borramos todos los datos de la tabla si no se selecciona ninguno 
            mdlTbl.setRowCount(0);
            vtnMallaAlm.getCmbEstado().removeAllItems();
        }
    }

    private void ingresarNota() {
        int pos = vtnMallaAlm.getTblMallaAlumno().getSelectedRow();
        if (pos >= 0) {
            MallaAlumnoMD malla = mallas.get(pos);
            double nota = Double.parseDouble(JOptionPane.showInputDialog(
                    "Ingrese la nota de \n" + malla.getMateria().getNombre() + "\n"
                    + "Numero de matricula: " + malla.getMallaNumMatricula()));
            if (malla.getMallaNumMatricula() > 0) {
                mallaAlm.ingresarNota(malla.getId(), malla.getMallaNumMatricula(), nota);
            } else {
                JOptionPane.showMessageDialog(vtnPrin, "Alumno no se encuentra matricula en esta \n"
                        + "materia, no puede ingresar su nota.");
            }
        }
    }

    private void cargarPorEstado() {
        int posAlm = vtnMallaAlm.getCmbAlumnos().getSelectedIndex();
        System.out.println("Posicion "+posAlm);
        System.out.println("Tamanio: "+alumnos.size());
        int posEst = vtnMallaAlm.getCmbEstado().getSelectedIndex();
        if (posAlm > 0 && posEst > 0) {
            mallas = mallaAlm.cargarMallaAlumnoPorEstado(
                    alumnos.get(posAlm - 1).getId(), cmbEstado[posEst]);
            llenarTbl(mallas);
        } else if (posAlm > 0) {
            cargarPorAlumno();
        }
    }

    private void llenarTbl(ArrayList<MallaAlumnoMD> mallas) {
        mdlTbl.setRowCount(0);
        if (mallas != null) {
            mallas.forEach((m) -> {
                Object valores[] = {m.getId(), m.getAlumnoCarrera().getAlumno().getPrimerNombre()
                    + " " + m.getAlumnoCarrera().getAlumno().getPrimerApellido(), m.getMateria().getNombre(),
                    m.getEstado(),
                    m.getMallaCiclo(), m.getMallaNumMatricula(), m.getNota1(),
                    m.getNota2(), m.getNota3()};
                mdlTbl.addRow(valores);
            });
        }
    }

    private void cargarCmbCarrera() {
        carreras = car.cargarCarreras();
        vtnMallaAlm.getCmbCarreras().removeAllItems();
        if (carreras != null) {
            vtnMallaAlm.getCmbCarreras().addItem("Seleccione");
            carreras.forEach(c -> {
                vtnMallaAlm.getCmbCarreras().addItem(c.getCodigo());
            });
        }
    }

    private void clickCmbCarrera() {
        int posCar = vtnMallaAlm.getCmbCarreras().getSelectedIndex();
        if (posCar > 0) {
            cargarCmbAlumno(carreras.get(posCar - 1).getId());
            vtnMallaAlm.getCmbAlumnos().setEnabled(true);
            cargarCmbEstado();
        } else {
            vtnMallaAlm.getCmbAlumnos().removeAllItems();
            vtnMallaAlm.getCmbAlumnos().setEnabled(false);
            vtnMallaAlm.getCmbEstado().removeAllItems();
        }
    }

    private void cargarCmbAlumno(int idCarrera) {
        alumnos = almCar.cargarAlumnoCarreraPorCarrera(idCarrera);
        llenarCmbAlumno(alumnos);
    }

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

    private void cargarCmbEstado() {
        vtnMallaAlm.getCmbEstado().removeAllItems();
        for (String e : cmbEstado) {
            vtnMallaAlm.getCmbEstado().addItem(e);
        }
    }
}
