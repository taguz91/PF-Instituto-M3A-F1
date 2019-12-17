/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo.avance;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import modelo.AvanceSilabo.SeguimientoSilaboBD;
import modelo.AvanceSilabo.SeguimientoSilaboMD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.CursosBDS;
import modelo.silabo.MateriasBDS;
import modelo.silabo.PeriodoLectivoBDS;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmConfiguracionSeguimientoSilabo;

/**
 *
 * @author Daniel
 */
public class ControladorConfiguracionAvanceSilabo {

    private int id_silabo = -1;
    private final UsuarioBD usuario;
    private List<CarreraMD> carreras_docente;
    private List<SilaboMD> silabosDocente;
    private frmConfiguracionSeguimientoSilabo avance;
    private List<CursoMD> cursoSilabo;
    private int id_periodo_lectivo = -1;
    private List<PeriodoLectivoMD> periodosCarrera;
    private final VtnPrincipal vtnPrincipal;
    private List<MateriaMD> materias_Silabos;
    private List<SeguimientoSilaboMD> count;

    public ControladorConfiguracionAvanceSilabo(UsuarioBD usuario, VtnPrincipal vtnPrincipal) {
        this.usuario = usuario;
        this.vtnPrincipal = vtnPrincipal;

    }

    public void init() {
        avance = new frmConfiguracionSeguimientoSilabo();
        vtnPrincipal.getDpnlPrincipal().add(avance);
        avance.setTitle("CREAR UN AVANCE DE SILABO");
        avance.show();
        avance.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - avance.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - avance.getSize().height) / 2);

        avance.getBtnCancelar().addActionListener((e) -> {
            avance.dispose();
            vtnPrincipal.getMnCAvanceSilabo().doClick();
        });
        avance.getBtnSiguiente().addActionListener(a1 -> {
            if (validarSeguiSilaboExistente() == true) {
                avance.dispose();
                controlador_avance_ingreso cas = new controlador_avance_ingreso(vtnPrincipal, cursos_seleccionado(), silabo_seleccionado());
                cas.init();
                avance.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "YA EXISTE EL SEGUIMIENTO DE SILABO DE ESTE CURSO", "Aviso", JOptionPane.ERROR_MESSAGE);

            }

        });
        avance.getCbxCarrera().addActionListener(a -> clickCmbCarreras());
        avance.getCbxSilabos().addActionListener(a -> clickCmbSilabos());
        avance.getCmb_perido().addActionListener(a -> clickComboPeriodos());
        estadoCmb_silbo(false);
        estadoCmb_cursoUnidDES(false);
        CARGAR_COMBO_CARRERAS();
    }

    private void CARGAR_COMBO_CARRERAS() {
        avance.getCbxCarrera().removeAllItems();
        carreras_docente = CarrerasBDS.consultar(usuario.getUsername());
        if (carreras_docente == null) {
            JOptionPane.showMessageDialog(null, "No tiene carreras asignadas");
        } else {
            avance.getCbxCarrera().addItem("SELECCIONE UNA CARRERA!");
            carreras_docente.forEach((cmd) -> {
                avance.getCbxCarrera().addItem(cmd.getNombre());
            });
        }
    }

    private void LLENAR_COMBO_SILABOS(List<MateriaMD> materias) {
        avance.getCbxSilabos().removeAllItems();
        if (materias != null) {
            avance.getCbxSilabos().addItem("SELECCIONE UN SILABO!");
            materias_Silabos.forEach(m -> {
                avance.getCbxSilabos().addItem(m.getNombre());
            });
            avance.getCbxSilabos().setSelectedIndex(0);
        }
    }

    private void LLENA_COMBO_PERIODOS_CARRERA(List<PeriodoLectivoMD> periodos) {
        avance.getCmb_perido().removeAllItems();
        if (periodos != null) {
            avance.getCmb_perido().addItem("SELECCIONE SU PERIODO ACTUAL!");
            periodos.forEach(pl -> {
                avance.getCmb_perido().addItem(pl.getNombre());
            });
            avance.getCmb_perido().setSelectedIndex(0);
        }
    }

    private void LLENAR_COMBO_CURSOS(List<CursoMD> cursos) {
        avance.getCbxCurso().removeAllItems();
        if (cursos != null) {
            cursos.forEach(cs -> {
                avance.getCbxCurso().addItem(String.valueOf(cs.getNombre()));
            });
        }
    }

    private void clickCmbCarreras() {
        int posC = avance.getCbxCarrera().getSelectedIndex();
        if (posC > 0) {
            estado_comboPeriodos(true);
            String carrera = carreras_docente.get(posC - 1).getNombre();
            periodosCarrera = PeriodoLectivoBDS.single().consultarPeriodos(carrera);
            LLENA_COMBO_PERIODOS_CARRERA(periodosCarrera);

        } else {
            estado_comboPeriodos(false);
        }
    }

    private void clickCmbSilabos() {
        int posC = avance.getCbxCarrera().getSelectedIndex();
        int posS = avance.getCbxSilabos().getSelectedIndex();

        if (posS > 0) {
            estadoCmb_cursoUnidDES(true);
            String materia_silabo = materias_Silabos.get(posS - 1).getNombre();
            cursoSilabo = CursosBDS.Consultarcursos(usuario.getPersona().getIdPersona(), getid_periodo(), materia_silabo);
            LLENAR_COMBO_CURSOS(cursoSilabo);

            if (avance.getCbxCurso().getItemCount() != 0) {
                avance.getBtnSiguiente().setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "NO PUEDE REALIZAR UN SEGUIMIENTO DE SILABO DE ESTA MATERIA", "Aviso", JOptionPane.ERROR_MESSAGE);
                avance.getBtnSiguiente().setEnabled(false);
            }

        } else {
            clickComboPeriodos();
            estadoCmb_cursoUnidDES(false);
            avance.getBtnSiguiente().setEnabled(false);
        }
    }

    private void clickComboPeriodos() {
        int posC = avance.getCbxCarrera().getSelectedIndex();
        int posP = avance.getCmb_perido().getSelectedIndex();

        if (posP > 0 && posC > 0) {
            estadoCmb_silbo(true);
            String carrera = carreras_docente.get(posC - 1).getNombre();
            String nombre_periodo = periodosCarrera.get(posP - 1).getNombre();
            materias_Silabos = MateriasBDS.consultarSilabo2(carrera, usuario.getPersona().getIdPersona(), nombre_periodo);
            LLENAR_COMBO_SILABOS(materias_Silabos);

        } else {
            clickCmbCarreras();
            estadoCmb_silbo(false);
        }
    }

    private void estadoCmb_silbo(boolean estado) {
        avance.getCbxSilabos().setEnabled(estado);
        avance.getCbxSilabos().removeAllItems();
    }

    private void estadoCmb_cursoUnidDES(boolean estado) {
        avance.getCbxCurso().setEnabled(estado);
        avance.getCbxCurso().removeAllItems();
        avance.getCbxCurso().setEnabled(estado);
        avance.getCbxCurso().removeAllItems();

    }

    private int getIdSilabo() {
        String silabo = avance.getCbxSilabos().getSelectedItem().toString();
        silabosDocente = cargar_silabo();
        silabosDocente
                .stream()
                .filter(item -> item.getMateria().getNombre().equals(silabo))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    id_silabo = obj.getID();
                });
        return id_silabo;
    }

    public List<SilaboMD> cargar_silabo() {
        String[] parametros = {avance.getCbxCarrera().getSelectedItem().toString(),
            String.valueOf(usuario.getPersona().getIdPersona()), avance.getCmb_perido().getSelectedItem().toString()};
        List<SilaboMD> silabosdocente = SilaboBD.consultarSilabo1(parametros);

        return silabosdocente;

    }

    private void estado_comboPeriodos(boolean estado) {
        avance.getCmb_perido().setEnabled(estado);
        avance.getCmb_perido().removeAllItems();

    }

    private List<PeriodoLectivoMD> cargarPeriodos() {
        List<PeriodoLectivoMD> periodos = PeriodoLectivoBDS.single().consultarPeriodos(avance.getCbxCarrera().getSelectedItem().toString());
        return periodos;
    }

    private int getid_periodo() {
        String nombre_periodo = avance.getCmb_perido().getSelectedItem().toString();
        periodosCarrera = cargarPeriodos();
        periodosCarrera
                .stream()
                .filter(item -> item.getNombre().equals(nombre_periodo))
                .collect(Collectors.toList())
                .forEach(obj -> {
                    id_periodo_lectivo = obj.getID();
                });
        return id_periodo_lectivo;
    }

    private SilaboMD silabo_seleccionado() {
        silabosDocente = cargar_silabo();
        Optional<SilaboMD> silaboSeleccionado = silabosDocente.stream().
                filter(s -> s.getMateria().getNombre().equals(avance.getCbxSilabos().getSelectedItem().toString())).
                findFirst();

        return silaboSeleccionado.get();
    }

    private CursoMD cursos_seleccionado() {
        Optional<CursoMD> cursoSeleccionado = cursoSilabo.stream().
                filter(s -> s.getNombre().equals(avance.getCbxCurso().getSelectedItem().toString())).
                findFirst();
        return cursoSeleccionado.get();
    }

    private boolean validarSeguiSilaboExistente() {
        boolean valid = true;
        count = SeguimientoSilaboBD.consultarSeguimientoExistentes2(cursos_seleccionado().getId());
        for (SeguimientoSilaboMD ss : count) {

            if (ss.getCurso().getId() == cursos_seleccionado().getId()) {
                valid = false;
            }
        }
        return valid;
    }

}
