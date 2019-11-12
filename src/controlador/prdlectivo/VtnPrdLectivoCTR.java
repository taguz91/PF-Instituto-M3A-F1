package controlador.prdlectivo;

import controlador.principal.DCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.CONS;
import modelo.carrera.CarreraBD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.prdlectivo.FrmPrdLectivo;
import vista.prdlectivo.VtnPrdLectivo;

/**
 *
 * @author Johnny
 */
public class VtnPrdLectivoCTR extends DCTR {

    private final VtnPrdLectivo vtnPrdLectivo;
    private final PeriodoLectivoBD bdPerLectivo;
    private FrmPrdLectivo frmPerLectivo;
    private List<PeriodoLectivoMD> periodos;
    private List<CarreraMD> carreras;

    public VtnPrdLectivoCTR(VtnPrdLectivo vtnPrdLectivo, VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.vtnPrdLectivo = vtnPrdLectivo;
        bdPerLectivo = new PeriodoLectivoBD(ctrPrin.getConecta());
    }

    //Inicia la funcionalidad de la Ventana de visualización de los Períodos Lectivos
    public void iniciar() {
        ctrPrin.agregarVtn(vtnPrdLectivo);
        //Validacion del txt buscar
        vtnPrdLectivo.getTxt_Buscar().addKeyListener(new TxtVBuscador(vtnPrdLectivo.getTxt_Buscar()));
        ocultarAtributo();
        //Inicio de eventos en los componentes
        vtnPrdLectivo.getTxt_Buscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnPrdLectivo.getTxt_Buscar().getText().toUpperCase();
                if (e.getKeyCode() == 10) {
                    buscaIncremental(b);
                } else if (b.length() == 0) {
                    cargarPeriodos();
                }
            }
        });
        vtnPrdLectivo.getBtnEditar().addActionListener(e -> editarPeriodo());
//        vtnPrdLectivo.getBtn_EliminarPL().addActionListener(e -> eliminarPeriodo());
        vtnPrdLectivo.getBtnIngresar().addActionListener(e -> abrirFrmPrdLectivo());
        vtnPrdLectivo.getBtnBuscar().addActionListener(e -> buscaIncremental(vtnPrdLectivo.getTxt_Buscar().getText()));
        vtnPrdLectivo.getBtnCerrarPeriodo().addActionListener(e -> cerrarPeriodo());
        vtnPrdLectivo.getBtnListaDocentesPeriodos().addActionListener(e -> ListaDocentesPeriodos());
        //Validacion del buscador
        vtnPrdLectivo.getTxt_Buscar().addKeyListener(new TxtVBuscador(vtnPrdLectivo.getTxt_Buscar(),
                vtnPrdLectivo.getBtnBuscar()));
        vtnPrdLectivo.getCmbx_Filtrar().addActionListener(e -> filtrarCarreras());
        TblEstilo.formatoTbl(vtnPrdLectivo.getTblPrdLectivo());
        TblEstilo.columnaMedida(vtnPrdLectivo.getTblPrdLectivo(), 3, 120);
        TblEstilo.columnaMedida(vtnPrdLectivo.getTblPrdLectivo(), 4, 120);
        TblEstilo.columnaMedida(vtnPrdLectivo.getTblPrdLectivo(), 5, 100);
        //Llenamos la tabla 
        cargarPeriodos();
        cargarCarreras();
        InitPermisos();
    }

    //Permite visualizar el Formulario de Período Lectivo
    public void abrirFrmPrdLectivo() {
        ctrPrin.abrirFrmPrdLectivo();
        vtnPrdLectivo.dispose();
    }

    public void cargarCarreras() {
        CarreraBD bdCarrera = new CarreraBD(ctrPrin.getConecta());
        carreras = bdCarrera.cargarCarreras();
        for (int i = 0; i < carreras.size(); i++) {
            vtnPrdLectivo.getCmbx_Filtrar().addItem(carreras.get(i).getNombre());
        }
    }

    public void filtrarCarreras() {
        String nombre;
        int idCarrera = 0;
//        List<PeriodoLectivoMD> periodos;
        if (vtnPrdLectivo.getCmbx_Filtrar().getSelectedIndex() > 0) {
            nombre = vtnPrdLectivo.getCmbx_Filtrar().getSelectedItem().toString();
            for (int i = 0; i < carreras.size(); i++) {
                if (carreras.get(i).getNombre().equals(nombre)) {
                    idCarrera = carreras.get(i).getId();
                }
            }
            periodos = bdPerLectivo.llenarPeriodosxCarreras(idCarrera);
            llenarTabla(periodos);
        } else {
            llenarTabla(periodos);
        }
    }

    //Oculta la columna del ID del Período Lectivo
    public void ocultarAtributo() {
        modelo.estilo.TblEstilo.ocualtarID(vtnPrdLectivo.getTblPrdLectivo());
    }

    /**
     * Cargamos los datos para llenar la tabla
     */
    private void cargarPeriodos() {
        periodos = bdPerLectivo.cargarPeriodos();
        llenarTabla(periodos);
    }

    //Llena con todos los Períodos Lectivo registrado las tabla localizada en la ventana de Visualización
    public void llenarTabla(List<PeriodoLectivoMD> periodos) {
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnPrdLectivo.getTblPrdLectivo().getModel();
        for (int i = vtnPrdLectivo.getTblPrdLectivo().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < periodos.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            String nombre;
            String dia_Inicio, mes_Inicio, anio_Inicio;
            String dia_Fin, mes_Fin, anio_Fin;
            dia_Inicio = String.valueOf(periodos.get(i).getFechaInicio().getDayOfMonth());
            mes_Inicio = String.valueOf(periodos.get(i).getFechaInicio().getMonthValue());
            anio_Inicio = String.valueOf(periodos.get(i).getFechaInicio().getYear());
            dia_Fin = String.valueOf(periodos.get(i).getFechaFin().getDayOfMonth());
            mes_Fin = String.valueOf(periodos.get(i).getFechaFin().getMonthValue());
            anio_Fin = String.valueOf(periodos.get(i).getFechaFin().getYear());

//            nombre = periodos.get(i).getCarrera().getCodigo() + "   " + bdPerLectivo.Meses(periodos.get(i).getFecha_Inicio()) + "   "
//                    + bdPerLectivo.Meses(periodos.get(i).getFecha_Fin());
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(i).getID(), i, 0);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(i).getCarrera().getNombre(), i, 1);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(i).getNombre(), i, 2);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Inicio + "/" + mes_Inicio + "/" + dia_Inicio, i, 3);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Fin + "/" + mes_Fin + "/" + dia_Fin, i, 4);
            if (periodos.get(i).isEstado() == true) {
                vtnPrdLectivo.getTblPrdLectivo().setValueAt("ABIERTO", i, 5);
            } else {
                vtnPrdLectivo.getTblPrdLectivo().setValueAt("CERRADO", i, 5);
            }

        }
        if (periodos.isEmpty()) {
            vtnPrdLectivo.getLblResultados().setText("0 Resultados obtenidos.");
        } else {
            vtnPrdLectivo.getLblResultados().setText(String.valueOf(periodos.size()) + " Resultados obtenidos.");
        }
    }

    //Filtra datos en la tabla ya sea por su carerra o por su nombre
    public void buscaIncremental(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            periodos = bdPerLectivo.capturarPeriodos(aguja);
            llenarTabla(periodos);
        }
    }

    //Captura una fila en específico al hacer un click
    public PeriodoLectivoMD capturarFila() {
        int i = vtnPrdLectivo.getTblPrdLectivo().getSelectedRow();
        if (i >= 0) {
            PeriodoLectivoMD periodo;
            periodo = bdPerLectivo.capturarPerLectivo(Integer.valueOf(vtnPrdLectivo.getTblPrdLectivo().getValueAt(i, 0).toString()));
            return periodo;
        } else {
            return null;
        }
    }

    //Edita un Período Lectivo en específico
    public void editarPeriodo() {
        PeriodoLectivoMD periodo = capturarFila();
        CarreraMD carrera = new CarreraMD();
        if (periodo != null) {
            frmPerLectivo = new FrmPrdLectivo();
            FrmPrdLectivoCTR ctrFrm = new FrmPrdLectivoCTR(frmPerLectivo, ctrPrin);
            ctrFrm.iniciar();
            carrera.setNombre(periodo.getCarrera().getNombre());
            ctrFrm.editar(periodo, carrera);
            vtnPrdLectivo.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Advertencia!! Seleccione una fila");
        }
    }

    //Elimina un Período Lectivo en específico
    public void eliminarPeriodo() {
        PeriodoLectivoMD periodo;
        if (capturarFila() == null) {
            JOptionPane.showMessageDialog(null, "No se puede Eliminar si no selecciona un Período Lectivo");
        } else {
            int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar este Período Lectivo? ", " Eliminar Período Lectivo ", dialog);
            if (result == 0) {
                periodo = capturarFila();
                if (bdPerLectivo.eliminarPeriodo(periodo) == true) {
                    JOptionPane.showMessageDialog(null, "Datos Eliminados Satisfactoriamente");
                    cargarPeriodos();
                } else {
                    JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR AL PERÍODO LECTIVO");
                }
            }
        }
    }

    //Cierra un Período Lectivo en específico
    public void cerrarPeriodo() {
        PeriodoLectivoMD periodo;
        if (capturarFila() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un Período Lectivo");
        } else {
            periodo = capturarFila();
            if (periodo.isEstado() == false) {
                int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
                int result = JOptionPane.showConfirmDialog(null, "El Período Lectivo que seleccionó esta Cerrado\n ¿Desea Abrir este Período Lectivo? ", " Abrir Período Lectivo ", dialog);
                if (result == 0) {
                    if (bdPerLectivo.abrirPeriodo(periodo.getID()) == true) {
                        JOptionPane.showMessageDialog(null, "Período Lectivo Abierto Satisfactoriamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo abrir este Período Lectivo");
                    }
                }
            } else {
                String num = bdPerLectivo.alumnosMatriculados(periodo.getCarrera().getId());
                int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
                int result = JOptionPane.showConfirmDialog(null, "ADVERTENCIA!!\n"
                        + "Está a punto de Cerrar un Período Lectivo\n"
                        + "Al hacer esto se pasaran las notas de todos los estudiantes matriculados en la carrera de " + periodo.getCarrera().getNombre() + "\n"
                        + "A la Malla General, esto quiere decir que las notas de " + num + " Alumnos serán cambiadas de lugar, esta acción es irreversible\n"
                        + "Si está de acuerdo con realizar esta acción deberá disponer de una excelente conexión a Internet\n"
                        + "Este proceso se tardará algunos minutos\n"
                        + "¿Esta seguro que desea cerrar este Período Lectivo? ", " Cerrar Período Lectivo ", dialog);
                if (result == 0) {
                    if (periodo.isEstado() == false) {
                        JOptionPane.showMessageDialog(null, "Este Período Lectivo ya fue cerrado");
                    } else {
                        if (bdPerLectivo.cerrarPeriodo(periodo) == true) {
                            JOptionPane.showMessageDialog(null, "Período Lectivo Cerrado Satisfactoriamente");
                            cargarPeriodos();
                        } else {
                            JOptionPane.showMessageDialog(null, "NO SE PUDO CERRAR ESTE PERÍODO LECTIVO");
                        }
                    }

                }
            }
        }
    }
 public void ListaDocentesPeriodos() {
        JasperReport jr;
        String path = "/vista/reportes/repListaDocentesPeriodos.jasper";
       int posFila = vtnPrdLectivo.getTblPrdLectivo().getSelectedRow();
        if (posFila >= 0) {
            try {
                Map parametro = new HashMap();
                parametro.put("periodo", periodos.get(posFila).getID());
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Lista de Docente por Periodo Lectivo");
                System.out.println(parametro);
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "error" + ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione primero una fila de la tabla");
        }
            
        }
    //Inicia los permisos a la Base de Datos
    private void InitPermisos() {
        
        vtnPrdLectivo.getBtnCerrarPeriodo().getAccessibleContext().setAccessibleName("Periodo-Lectivo-Cerrar Periodo");
        vtnPrdLectivo.getBtnEditar().getAccessibleContext().setAccessibleName("Periodo-Lectivo-Editar");
        vtnPrdLectivo.getBtnIngresar().getAccessibleContext().setAccessibleName("Periodo-Lectivo-Ingresar");
        
        
        
        CONS.activarBtns(vtnPrdLectivo.getBtnCerrarPeriodo(), vtnPrdLectivo.getBtnEditar(),
                vtnPrdLectivo.getBtnIngresar());

    }

}
