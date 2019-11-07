/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.notas;

import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.CONS;
import modelo.alumno.AlumnoCursoBD;
import modelo.curso.CursoBD;
import modelo.curso.CursoMD;
import modelo.materia.MateriaBD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.tipoDeNota.TipoDeNotaBD;
import modelo.usuario.RolBD;
import modelo.usuario.UsuarioBD;
import vista.notas.VtnNotas;
import vista.principal.VtnPrincipal;

/**
 *
 * @author MrRainx
 */
public abstract class AbstractVtn {
    
    protected final VtnPrincipal desktop;
    protected final VtnNotas vista;
    // LISTAS
    protected Map<String, DocenteMD> listaDocentes;
    protected List<PeriodoLectivoMD> listaPeriodos;
    protected List<AlumnoCursoBD> listaNotas;
    protected List<MateriaMD> listaMaterias;
    protected List<TipoDeNotaBD> listaValidaciones;

    // TABLA
    protected DefaultTableModel tablaTrad;
    protected DefaultTableModel tablaDuales;

    // JTables
    protected JTable jTblTrad;
    protected JTable jTblDual;

    // ACTIVACION DE HILOS
    protected boolean cargarTabla = true;
    
    protected final PeriodoLectivoBD peridoBD;
    protected final AlumnoCursoBD almnCursoBD;
    protected final TipoDeNotaBD tipoNotaBD;
    protected final CursoBD cursoBD;
    protected final MateriaBD materiaBD;
    protected final DocenteBD docenteBD;
    
    {
        peridoBD = new PeriodoLectivoBD();
        almnCursoBD = new AlumnoCursoBD();
        tipoNotaBD = new TipoDeNotaBD();
        cursoBD = new CursoBD();
        materiaBD = new MateriaBD();
        docenteBD = new DocenteBD();
    }
    
    public AbstractVtn(VtnPrincipal desktop, VtnNotas vista) {
        this.desktop = desktop;
        this.vista = vista;
    }

    // <editor-fold defaultstate="collapsed" desc="ENCABEZADO">
    protected void cargarComboDocente() {
        listaDocentes.entrySet().stream().map(c -> c.getKey()).forEach(vista.getCmbDocente()::addItem);
        tablaTrad.setRowCount(0);
        tablaDuales.setRowCount(0);
    }
    
    protected void cargarComboPeriodos() {
        vista.getCmbPeriodoLectivo().removeAllItems();
        vista.getLblCarrera().setText("");
        
        listaPeriodos = peridoBD.selectPeriodoWhere(getIdDocente());
        listaPeriodos.stream().map(c -> c.getNombre()).forEach(vista.getCmbPeriodoLectivo()::addItem);
        tablaTrad.setRowCount(0);
        tablaDuales.setRowCount(0);
    }
    
    protected void setLblCarrera() {
        vista.getLblCarrera()
                .setText(listaPeriodos.stream().filter(item -> item.getId() == getIdPeriodoLectivo())
                        .map(c -> c.getCarrera().getNombre()).findFirst().orElse(""));
        
    }
    
    protected void cargarComboCiclo() {
        try {
            vista.getCmbCiclo().removeAllItems();
            
            cursoBD.selectCicloWhere(getIdDocente(), getIdPeriodoLectivo()).stream()
                    .forEach(vista.getCmbCiclo()::addItem);
        } catch (NullPointerException e) {
        }
        tablaTrad.setRowCount(0);
        tablaDuales.setRowCount(0);
    }
    
    protected void cargarComboMaterias() {
        try {
            vista.getCmbAsignatura().removeAllItems();
            
            CursoMD curso = new CursoMD();
            DocenteMD docente = new DocenteMD();
            docente.setIdDocente(getIdDocente());
            curso.setDocente(docente);
            PeriodoLectivoMD periodo = new PeriodoLectivoMD();
            periodo.setPeriodo(getIdPeriodoLectivo());
            curso.setPeriodo(periodo);
            curso.setNombre(vista.getCmbCiclo().getSelectedItem().toString());
            
            listaMaterias = materiaBD.selectWhere(curso);
            
            listaMaterias.stream().map(c -> c.getNombre()).forEach(vista.getCmbAsignatura()::addItem);
            vista.getLblHoras().setText("" + listaMaterias.stream()
                    .filter(item -> item.getNombre().equals(vista.getCmbAsignatura().getSelectedItem().toString()))
                    .map(c -> c.getHorasPresenciales()).findFirst().orElse(-1));
            
            listaValidaciones = tipoNotaBD.selectWhere(getIdPeriodoLectivo());
            
            validarCombos();
        } catch (NullPointerException e) {
            vista.getCmbAsignatura().removeAllItems();
        }
        tablaTrad.setRowCount(0);
        tablaDuales.setRowCount(0);
    }
    
    protected int getIdDocente() {
        return listaDocentes.entrySet().stream()
                .filter((entry) -> (entry.getKey().equals(vista.getCmbDocente().getSelectedItem().toString())))
                .map(c -> c.getValue().getIdDocente()).findAny().get();
    }
    
    protected int getIdPeriodoLectivo() {
        try {
            String periodo = vista.getCmbPeriodoLectivo().getSelectedItem().toString();
            return listaPeriodos.stream().filter(item -> item.getNombre().equals(periodo))
                    .map(c -> c.getId()).findAny().orElse(-1);
        } catch (NullPointerException e) {
        }
        return -1;
    }
    
    private void validarCombos() {
        
        if (vista.getCmbAsignatura().getItemCount() > 0) {
            vista.getBtnVerNotas().setEnabled(true);
        } else {
            vista.getBtnVerNotas().setEnabled(false);
        }
    }
    
    protected boolean getEstado() {
        return listaPeriodos.stream()
                .filter(item -> item.getNombre().equals(vista.getCmbPeriodoLectivo().getSelectedItem().toString()))
                .map(c -> c.isEstado())
                .findFirst()
                .orElse(false);
    }
    // </editor-fold>
}
