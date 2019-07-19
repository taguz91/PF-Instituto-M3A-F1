/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.CursosBDS;
import modelo.silabo.PeriodoLectivoBDS;
import modelo.usuario.UsuarioBD;
import vista.silabos.frmConfiguracionSeguimientoSilabo;
import vista.silabos.frmCRUDAvanceSilabo;

/**
 *
 * @author Daniel
 */
public class ControladorConfiguracionAvanceSilabo {

    private final UsuarioBD usuario;
    private ConexionBD conexion;
    private boolean esCordinador = false;
    private List<CarreraMD> carreras_docente;
    private frmConfiguracionSeguimientoSilabo avance;
    private frmCRUDAvanceSilabo seguimiento;
    private List<CursoMD> lista_curso;
    private int id_periodo_lectivo = -1;
    private List<PeriodoLectivoMD> periodosCarrera;

    public ControladorConfiguracionAvanceSilabo(UsuarioBD usuario, ConexionBD conexion, List<CarreraMD> carreras_docente, frmConfiguracionSeguimientoSilabo avance, frmCRUDAvanceSilabo seguimiento, List<CursoMD> lista_curso) {
        this.usuario = usuario;
        this.conexion = conexion;
        this.carreras_docente = carreras_docente;
        this.avance = avance;
        this.seguimiento = seguimiento;
        this.lista_curso = lista_curso;

    }

    private void CARGAR_COMBO_CARRERAS() {

        avance.getCbxCarrera().removeAllItems();
        carreras_docente = new ArrayList<>();
        if (esCordinador) {
            carreras_docente.add(new CarrerasBDS(conexion).retornaCarreraCoordinador(usuario.getUsername()));
        } else {
            carreras_docente = CarrerasBDS.consultar(conexion, usuario.getUsername());
        }

        carreras_docente.forEach((cmd) -> {
            avance.getCbxCarrera().addItem(cmd.getNombre());
        });

        avance.getCbxCarrera().setSelectedIndex(0);
    }

    private CursoMD curso_selecc() {
        int seleccion = seguimiento.getTlbAvanceSilabo().getSelectedRow();
        lista_curso = CursosBDS.Consultarcursos(conexion, usuario.getPersona().getIdPersona(), getid_periodo(), seguimiento.getTlbAvanceSilabo().getValueAt(seleccion, 2).toString());
        Optional<CursoMD> curso_selecccionado = lista_curso.stream().filter(lc -> lc.getNombre().equals(seguimiento.getTlbAvanceSilabo().getValueAt(seleccion, 3).toString())).findFirst();
        return curso_selecccionado.get();
    }
   private List<PeriodoLectivoMD> cargarPeriodos() {
        List<PeriodoLectivoMD> periodos = PeriodoLectivoBDS.consultarPeriodosPlanDeClse(conexion, avance.getCbxCarrera().getSelectedItem().toString());
        return periodos;
    }
    private int getid_periodo() {
        String nombre_periodo = avance.getCbxPeriodo().getSelectedItem().toString();
        periodosCarrera = cargarPeriodos();
        periodosCarrera
                .stream()
                .filter(item -> item.getNombre_PerLectivo().equals(nombre_periodo))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    id_periodo_lectivo = obj.getId_PerioLectivo();
                });

        return id_periodo_lectivo;
    }
}
