package controlador.prdlectivo;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.carrera.CarreraMD;
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

    public VtnPrdLectivoCTR(VtnPrincipal vtnPrin, VtnPrdLectivo vtnPrdLectivo,
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.vtnPrdLectivo = vtnPrdLectivo;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;

        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Peridos lectivos");
        ctrPrin.setIconJIFrame(vtnPrdLectivo);
        bdPerLectivo = new PeriodoLectivoBD(conecta);

        vtnPrin.getDpnlPrincipal().add(vtnPrdLectivo);
        vtnPrdLectivo.show();
    }

    public void iniciar() {

        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnPrdLectivo.getTxt_Buscar().getText().toUpperCase();
                if (b.length() > 2) {
                    buscaIncremental(b);
                }

            }
        };

        //Validacion del txt buscar
        vtnPrdLectivo.getTxt_Buscar().addKeyListener(new TxtVBuscador(vtnPrdLectivo.getTxt_Buscar()));
        ocultarAtributo();
        llenarTabla();
        vtnPrdLectivo.getTxt_Buscar().addKeyListener(kl);
        vtnPrdLectivo.getBtnEditar().addActionListener(e -> editarPeriodo());
        vtnPrdLectivo.getBtnEliminar().addActionListener(e -> eliminarPeriodo());
        vtnPrdLectivo.getBtnIngresar().addActionListener(e -> abrirFrmPrdLectivo());
        vtnPrdLectivo.getBtnBuscar().addActionListener(e -> buscaIncremental(vtnPrdLectivo.getTxt_Buscar().getText()));
        //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Periodos lectivos");
    }

    public void abrirFrmPrdLectivo() {
        FrmPrdLectivo vista = new FrmPrdLectivo();
        FrmPrdLectivoCTR formulario = new FrmPrdLectivoCTR(vtnPrin, vista, conecta, ctrPrin);
        formulario.iniciar();
    }

    public void ocultarAtributo() {
        modelo.estilo.TblEstilo.ocualtarID(vtnPrdLectivo.getTblPrdLectivo());
    }

    public void llenarTabla() {
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnPrdLectivo.getTblPrdLectivo().getModel();
        for (int i = vtnPrdLectivo.getTblPrdLectivo().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        periodos = bdPerLectivo.cargarPeriodos();
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < periodos.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            String dia_Inicio, mes_Inicio, anio_Inicio;
            String dia_Fin, mes_Fin, anio_Fin;
            dia_Inicio = String.valueOf(periodos.get(i).getFecha_Inicio().getDayOfMonth());
            mes_Inicio = String.valueOf(periodos.get(i).getFecha_Inicio().getMonthValue());
            anio_Inicio = String.valueOf(periodos.get(i).getFecha_Inicio().getYear());
            dia_Fin = String.valueOf(periodos.get(i).getFecha_Fin().getDayOfMonth());
            mes_Fin = String.valueOf(periodos.get(i).getFecha_Fin().getMonthValue());
            anio_Fin = String.valueOf(periodos.get(i).getFecha_Fin().getYear());
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(i).getId_PerioLectivo(), i, 0);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(i).getCarrera().getNombre(), i, 1);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(i).getNombre_PerLectivo(), i, 2);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Inicio + "/" + mes_Inicio + "/" + dia_Inicio, i, 3);
            vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Fin + "/" + mes_Fin + "/" + dia_Fin, i, 4);
        }
        if (periodos.isEmpty()) {
            vtnPrdLectivo.getLblResultados().setText("0 Resultados obtenidos.");
        } else {
            vtnPrdLectivo.getLblResultados().setText(String.valueOf(periodos.size()) + " Resultados obtenidos.");
        }
    }

    public void buscaIncremental(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            DefaultTableModel modelo_Tabla;
            modelo_Tabla = (DefaultTableModel) vtnPrdLectivo.getTblPrdLectivo().getModel();
            for (int i = vtnPrdLectivo.getTblPrdLectivo().getRowCount() - 1; i >= 0; i--) {
                modelo_Tabla.removeRow(i);
            }
            List<PeriodoLectivoMD> lista = bdPerLectivo.capturarPeriodos(aguja);
            int columnas = modelo_Tabla.getColumnCount();
            for (int i = 0; i < lista.size(); i++) {
                modelo_Tabla.addRow(new Object[columnas]);
                String dia_Inicio, mes_Inicio, anio_Inicio;
                String dia_Fin, mes_Fin, anio_Fin;
                dia_Inicio = String.valueOf(lista.get(i).getFecha_Inicio().getDayOfMonth());
                mes_Inicio = String.valueOf(lista.get(i).getFecha_Inicio().getMonthValue());
                anio_Inicio = String.valueOf(lista.get(i).getFecha_Inicio().getYear());
                dia_Fin = String.valueOf(lista.get(i).getFecha_Fin().getDayOfMonth());
                mes_Fin = String.valueOf(lista.get(i).getFecha_Fin().getMonthValue());
                anio_Fin = String.valueOf(lista.get(i).getFecha_Fin().getYear());
                vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getId_PerioLectivo(), i, 0);
                vtnPrdLectivo.getTblPrdLectivo().setValueAt(bdPerLectivo.capturarNomCarrera(lista.get(i).getCarrera().getId()).getNombre(), i, 1);
                vtnPrdLectivo.getTblPrdLectivo().setValueAt(lista.get(i).getNombre_PerLectivo(), i, 2);
                vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Inicio + "/" + mes_Inicio + "/" + dia_Inicio, i, 3);
                vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Fin + "/" + mes_Fin + "/" + dia_Fin, i, 4);
            }
            if (lista.isEmpty()) {
                vtnPrdLectivo.getLblResultados().setText("0 Resultados obtenidos.");
            } else {
                vtnPrdLectivo.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
            }
        }
    }

    public void busquedaNormal(String aguja) {

        System.out.println("Entro");
        for (int i = 0; i < periodos.size(); i++) {
            if (periodos.get(i).getNombre_PerLectivo().contains(aguja.toUpperCase())
                    || periodos.get(i).getCarrera().getNombre().contains(aguja.toUpperCase())) {
                DefaultTableModel modelo_Tabla;
                modelo_Tabla = (DefaultTableModel) vtnPrdLectivo.getTblPrdLectivo().getModel();
                for (int y = vtnPrdLectivo.getTblPrdLectivo().getRowCount() - 1; y >= 0; y--) {
                    modelo_Tabla.removeRow(y);
                }
                int columnas = modelo_Tabla.getColumnCount();
                for (int a = 0; a < periodos.size(); a++) {
                    modelo_Tabla.addRow(new Object[columnas]);
                    String dia_Inicio, mes_Inicio, anio_Inicio;
                    String dia_Fin, mes_Fin, anio_Fin;
                    dia_Inicio = String.valueOf(periodos.get(a).getFecha_Inicio().getDayOfMonth());
                    mes_Inicio = String.valueOf(periodos.get(a).getFecha_Inicio().getMonthValue());
                    anio_Inicio = String.valueOf(periodos.get(a).getFecha_Inicio().getYear());
                    dia_Fin = String.valueOf(periodos.get(a).getFecha_Fin().getDayOfMonth());
                    mes_Fin = String.valueOf(periodos.get(a).getFecha_Fin().getMonthValue());
                    anio_Fin = String.valueOf(periodos.get(a).getFecha_Fin().getYear());
                    vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(a).getId_PerioLectivo(), a, 0);
                    vtnPrdLectivo.getTblPrdLectivo().setValueAt(bdPerLectivo.capturarNomCarrera(periodos.get(a).getCarrera().getId()).getNombre(), a, 1);
                    vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(a).getNombre_PerLectivo(), a, 2);
                    vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Inicio + "/" + mes_Inicio + "/" + dia_Inicio, a, 3);
                    vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Fin + "/" + mes_Fin + "/" + dia_Fin, a, 4);
                }

                if (periodos.isEmpty()) {
                    vtnPrdLectivo.getLblResultados().setText("0 Resultados obtenidos.");
                } else {
                    vtnPrdLectivo.getLblResultados().setText(String.valueOf(periodos.size()) + " Resultados obtenidos.");
                }
            } else {
//                DefaultTableModel modelo_Tabla;
//                modelo_Tabla = (DefaultTableModel) vtnPrdLectivo.getTblPrdLectivo().getModel();
//                for (int y = vtnPrdLectivo.getTblPrdLectivo().getRowCount() - 1; y >= 0; y--) {
//                    modelo_Tabla.removeRow(y);
//                }
//                periodos = bdPerLectivo.cargarPeriodos();
//                int columnas = modelo_Tabla.getColumnCount();
//                for (int a = 0; a < periodos.size(); a++) {
//                    modelo_Tabla.addRow(new Object[columnas]);
//                    String dia_Inicio, mes_Inicio, anio_Inicio;
//                    String dia_Fin, mes_Fin, anio_Fin;
//                    dia_Inicio = String.valueOf(periodos.get(i).getFecha_Inicio().getDayOfMonth());
//                    mes_Inicio = String.valueOf(periodos.get(i).getFecha_Inicio().getMonthValue());
//                    anio_Inicio = String.valueOf(periodos.get(i).getFecha_Inicio().getYear());
//                    dia_Fin = String.valueOf(periodos.get(i).getFecha_Fin().getDayOfMonth());
//                    mes_Fin = String.valueOf(periodos.get(i).getFecha_Fin().getMonthValue());
//                    anio_Fin = String.valueOf(periodos.get(i).getFecha_Fin().getYear());
//                    vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(i).getId_PerioLectivo(), i, 0);
//                    vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(i).getCarrera().getNombre(), i, 1);
//                    vtnPrdLectivo.getTblPrdLectivo().setValueAt(periodos.get(i).getNombre_PerLectivo(), i, 2);
//                    vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Inicio + "/" + mes_Inicio + "/" + dia_Inicio, i, 3);
//                    vtnPrdLectivo.getTblPrdLectivo().setValueAt(anio_Fin + "/" + mes_Fin + "/" + dia_Fin, i, 4);
//                }
//                if (periodos.isEmpty()) {
//                    vtnPrdLectivo.getLblResultados().setText("0 Resultados obtenidos.");
//                } else {
//                    vtnPrdLectivo.getLblResultados().setText(String.valueOf(periodos.size()) + " Resultados obtenidos.");
//                }
            }
        }

        List<PeriodoLectivoMD> lista = bdPerLectivo.capturarPeriodos(aguja);

    }

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

    public void eliminarPeriodo() {
        PeriodoLectivoMD periodo;
        if (capturarFila() == null) {
            JOptionPane.showMessageDialog(null, "No se puede Eliminar si no selecciona a un Alumno");
        } else {
            int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar este Período Lectivo? ", " Elinimar Período Lectivo ", dialog);
            if (result == 0) {
                periodo = capturarFila();
                if (bdPerLectivo.eliminarPeriodo(periodo) == true) {
                    JOptionPane.showMessageDialog(null, "Datos Eliminados Satisfactoriamente");
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR AL ALUMNO");
                }
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

}
