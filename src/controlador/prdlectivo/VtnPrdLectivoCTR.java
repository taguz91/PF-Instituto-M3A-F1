package controlador.prdlectivo;

import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.carrera.CarreraMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.usuario.RolMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import vista.prdlectivo.FrmPrdLectivo;
import vista.prdlectivo.VtnPrdLectivo;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnPrdLectivoCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnPrdLectivo vtnPrdLectivo;
    private final PeriodoLectivoBD bdPerLectivo;
    private FrmPrdLectivo frmPerLectivo;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private final RolMD permisos;
    private List<PeriodoLectivoMD> periodos;
    private List<CarreraMD> carreras;

    public VtnPrdLectivoCTR(VtnPrincipal vtnPrin, VtnPrdLectivo vtnPrdLectivo,
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.vtnPrdLectivo = vtnPrdLectivo;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;

        bdPerLectivo = new PeriodoLectivoBD(conecta);
        vtnPrin.getDpnlPrincipal().add(vtnPrdLectivo);
        vtnPrdLectivo.show();
    }

    //Inicia la funcionalidad de la Ventana de visualización de los Períodos Lectivos
    public void iniciar() {

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
        vtnPrdLectivo.getBtn_EliminarPL().addActionListener(e -> eliminarPeriodo());
        vtnPrdLectivo.getBtnIngresar().addActionListener(e -> abrirFrmPrdLectivo());
        vtnPrdLectivo.getBtnBuscar().addActionListener(e -> buscaIncremental(vtnPrdLectivo.getTxt_Buscar().getText()));
        vtnPrdLectivo.getBtnCerrarPeriodo().addActionListener(e -> cerrarPeriodo());
        //Validacion del buscador
        vtnPrdLectivo.getTxt_Buscar().addKeyListener(new TxtVBuscador(vtnPrdLectivo.getTxt_Buscar(),
                vtnPrdLectivo.getBtnBuscar()));
        TblEstilo.formatoTbl(vtnPrdLectivo.getTblPrdLectivo());
        TblEstilo.columnaMedida(vtnPrdLectivo.getTblPrdLectivo(), 3, 120);
        TblEstilo.columnaMedida(vtnPrdLectivo.getTblPrdLectivo(), 4, 120);
        TblEstilo.columnaMedida(vtnPrdLectivo.getTblPrdLectivo(), 5, 100);
        //Llenamos la tabla 
        cargarPeriodos();
    }

    //Permite visualizar el Formulario de Período Lectivo
    public void abrirFrmPrdLectivo() {
        ctrPrin.abrirFrmPrdLectivo();
        vtnPrdLectivo.dispose();
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
            dia_Inicio = String.valueOf(periodos.get(i).getFecha_Inicio().getDayOfMonth());
            mes_Inicio = String.valueOf(periodos.get(i).getFecha_Inicio().getMonthValue());
            anio_Inicio = String.valueOf(periodos.get(i).getFecha_Inicio().getYear());
            dia_Fin = String.valueOf(periodos.get(i).getFecha_Fin().getDayOfMonth());
            mes_Fin = String.valueOf(periodos.get(i).getFecha_Fin().getMonthValue());
            anio_Fin = String.valueOf(periodos.get(i).getFecha_Fin().getYear());

            nombre = periodos.get(i).getCarrera().getCodigo() + "   " + bdPerLectivo.Meses(periodos.get(i).getFecha_Inicio()) + "   "
                    + bdPerLectivo.Meses(periodos.get(i).getFecha_Fin());

            vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(i).getId_PerioLectivo(), i, 0);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(i).getCarrera().getNombre(), i, 1);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(nombre, i, 2);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Inicio + "/" + mes_Inicio + "/" + dia_Inicio, i, 3);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Fin + "/" + mes_Fin + "/" + dia_Fin, i, 4);
            if (periodos.get(i).isEstado_PerLectivo() == true) {
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
            FrmPrdLectivoCTR ctrFrm = new FrmPrdLectivoCTR(vtnPrin, frmPerLectivo, conecta, ctrPrin);
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
            if (periodo.isEstado_PerLectivo() == false) {
                int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
                int result = JOptionPane.showConfirmDialog(null, "El Período Lectivo que seleccionó esta Cerrado\n ¿Desea Abrir este Período Lectivo? ", " Abrir Período Lectivo ", dialog);
                if (result == 0) {
                    if (bdPerLectivo.abrirPeriodo(periodo.getId_PerioLectivo()) == true) {
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
                    if (periodo.isEstado_PerLectivo() == false) {
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

    //Inicia los permisos a la Base de Datos
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
