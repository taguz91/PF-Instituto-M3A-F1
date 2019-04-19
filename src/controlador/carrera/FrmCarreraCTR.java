package controlador.carrera;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.validaciones.CmbValidar;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.TxtVLetras;
import modelo.validaciones.Validar;
import vista.carrera.FrmCarrera;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class FrmCarreraCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmCarrera frmCarrera;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private boolean editar = false;
    private int idCarrera = 0;
    //Para cargar el combo de coordinador  
    private final DocenteBD docen;
    private ArrayList<DocenteMD> docentes;

    //Todas las modalidades que puede tener una carrera  
    private final String[] MODALIDADES = {"PRESENCIAL", "SEMIPRESENCIAL", "DISTANCIA", "DUAL"};

    //Modelo de la tabla 
    DefaultTableModel mdTbl;

    public FrmCarreraCTR(VtnPrincipal vtnPrin, FrmCarrera frmCarrera, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmCarrera = frmCarrera;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaFrm("Carrera");
        ctrPrin.setIconJIFrame(frmCarrera);
        this.docen = new DocenteBD(conecta);
        vtnPrin.getDpnlPrincipal().add(frmCarrera);
        frmCarrera.show();
    }

    public void iniciar() {
        ocultarErrores();
        cargarCmbModalidades();
        validaciones();
        //Formato tbl
        String[] titulo = {"Cédula", "Nombre"};
        String[][] datos = {};
        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        frmCarrera.getTblDocentes().setModel(mdTbl);
        TblEstilo.formatoTbl(frmCarrera.getTblDocentes());
        //Buscador 
        frmCarrera.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String a = frmCarrera.getTxtBuscar().getText().trim();
                if (a.length() > 2) {
                    buscarDocentes(a);
                }
            }
        });
        //Clcik buscar 
        frmCarrera.getBtnBuscar().addActionListener(e -> buscarDocentes(
                frmCarrera.getTxtBuscar().getText().trim()));

        frmCarrera.getBtnGuardar().addActionListener(e -> guardarYSalir());
        frmCarrera.getBtnGuardarContinuar().addActionListener(e -> guardarYContinuar());
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaFrmFin("Carrera");
    }

    private void validaciones() {
        frmCarrera.getTxtBuscar().addKeyListener(new TxtVBuscador(frmCarrera.getTxtBuscar()));
        frmCarrera.getCmbModalidad().addActionListener(new CmbValidar(frmCarrera.getCmbModalidad()));
        frmCarrera.getTxtNombre().addKeyListener(new TxtVLetras(frmCarrera.getTxtNombre(),
                frmCarrera.getLblErrorNombre()));
        frmCarrera.getTxtCodigo().addKeyListener(new TxtVLetras(frmCarrera.getTxtCodigo(),
                frmCarrera.getLblErrorCodigo()));
    }

    private void ocultarErrores() {
        frmCarrera.getLblErrorCodigo().setVisible(false);
        frmCarrera.getLblErrorNombre().setVisible(false);
    }
    
    private void guardarYSalir(){
        guardar();
        frmCarrera.dispose();
        ctrPrin.cerradoJIF();
        ctrPrin.abrirVtnCarrera();
    }
    
    private void guardarYContinuar(){
        guardar();
        borrarCampos();
    }

    private void guardar() {
        boolean guardar = true;
        SimpleDateFormat formFecha = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = frmCarrera.getJdFechaInicio().getDate();
        String fechaS = formFecha.format(fecha);
        String fec[] = fechaS.split("/");

        String dia = fec[0], mes = fec[1], anio = fec[2];

        String nombre = frmCarrera.getTxtNombre().getText();
        String codigo = frmCarrera.getTxtCodigo().getText();

        String modalidad = frmCarrera.getCmbModalidad().getSelectedItem().toString();
        int posCoord = frmCarrera.getTblDocentes().getSelectedRow();
        LocalDate fechaInicio = LocalDate.now();
        if (Validar.esNumeros(dia) && Validar.esNumeros(mes) && Validar.esNumeros(anio)) {
            try {
                fechaInicio = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes),
                        Integer.parseInt(dia));
            } catch (NumberFormatException e) {
                System.out.println("No es fecha.");
                guardar = false;
            }
        } else {
            guardar = false;
        }

        if (posCoord < 0) {
            guardar = false;
        }

        if (guardar) {
            CarreraBD car = new CarreraBD(conecta);
            car.setCodigo(codigo);
            car.setFechaInicio(fechaInicio);
            car.setModalidad(modalidad);
            car.setNombre(nombre);
            car.setCoordinador(docentes.get(posCoord));
            if (editar) {
                car.editarCarrera(idCarrera);
                editar = false;
            } else {
                car.guardarCarrera();
            }
        }
    }

    private void buscarDocentes(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            docentes = docen.buscar(aguja);
            llenarTblDocentes(docentes);
        }
    }

    private void llenarTblDocentes(ArrayList<DocenteMD> docentes) {
        mdTbl.setRowCount(0);
        if (docentes != null) {
            docentes.forEach(d -> {
                Object[] valores = {d.getIdentificacion(),
                    d.getPrimerApellido() + " " + d.getSegundoApellido()
                    + " " + d.getPrimerNombre() + " "
                    + " " + d.getSegundoNombre()};
                mdTbl.addRow(valores);
            });
        }
    }

    //Usada para el validar  
    private final Calendar fechaIni = Calendar.getInstance();

    public void editar(CarreraMD carrera) {
        frmCarrera.getTxtNombre().setText(carrera.getNombre());
        frmCarrera.getTxtCodigo().setText(carrera.getCodigo());
        fechaIni.set(carrera.getFechaInicio().getYear(), carrera.getFechaInicio().getMonthValue() - 1,
                carrera.getFechaInicio().getDayOfMonth());
        frmCarrera.getJdFechaInicio().setCalendar(fechaIni);

        frmCarrera.getCmbModalidad().setSelectedItem(carrera.getModalidad());
        System.out.println("Coordinador: "+carrera.getCoordinador().getIdentificacion());
        if (carrera.getCoordinador().getIdentificacion() != null) {
            frmCarrera.getTxtBuscar().setText(carrera.getCoordinador().getIdentificacion());
            buscarDocentes(carrera.getCoordinador().getIdentificacion());
            frmCarrera.getTblDocentes().selectAll();
        }

        editar = true;
        idCarrera = carrera.getId();
        //frmCarrera.getCmbCoordinador().setSelectedItem(carrera.getCoordinador()); 
    }

    private void borrarCampos() {
        frmCarrera.getTxtNombre().setText("");
        frmCarrera.getTxtCodigo().setText("");
        frmCarrera.getJdFechaInicio().setCalendar(null);
        frmCarrera.getCmbModalidad().setSelectedItem("Seleccioné");
        frmCarrera.getTxtBuscar().setText("");
        mdTbl.setRowCount(0);

    }

    private void cargarCmbModalidades() {
        frmCarrera.getCmbModalidad().removeAllItems();
        frmCarrera.getCmbModalidad().addItem("Seleccioné");
        for (String m : MODALIDADES) {
            frmCarrera.getCmbModalidad().addItem(m);
        }
    }

}
