/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import com.sun.istack.internal.Nullable;
import controlador.Libraries.abstracts.AbstractVTN;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.ActionEvent;
import java.awt.event.ActionEvent;
import java.util.List;
import modelo.CONS;
import modelo.carrera.CarreraMD;
import modelo.materia.MateriaMD;
import modelo.silabo.NEWCarreraBD;
import modelo.silabo.NEWMateriaBD;
import modelo.silabo.NEWSilaboBD;
import modelo.silabo.SilaboMD;
import vista.silabos.VtnConfigSilabo;

/**
 *
 * @author MrRainx
 */
public class VtnConfigSilaboCTR extends AbstractVTN<VtnConfigSilabo, SilaboMD> {

    private final List<CarreraMD> carreras = NEWCarreraBD.single().getByUsername(CONS.USUARIO.getUsername());
    private List<MateriaMD> materias;
    private List<SilaboMD> silabosRef;
    private final String MENSAJE_SIN_SILABO_PENDIENTE = "NO TIENE SILABOS PENDIENTES PARA ESTA CARRERA";

    public VtnConfigSilaboCTR(VtnPrincipalCTR desktop) {
        super(desktop);
        vista = new VtnConfigSilabo();
    }

    @Override
    public void Init() {
        super.Init();
        cargarCmbCarreras();
        InitEventos();
    }

    private void InitEventos() {
        vista.getCmbCarrera().addActionListener(this::cmbAsignatura);
        vista.getCmbAsignatura().addActionListener(this::cmbPeriodoRef);
        vista.getCmbPeriodoRef().addActionListener(this::validarPeriodoRef);
        vista.getBtnSiguiente().addActionListener(this::btnSiguiente);
        vista.getBtnCancelar().addActionListener(this::btnCancelar);
    }

    /*
        METODOS
     */
    private int getIdCarrera() {
        return carreras.stream()
                .filter(carrera -> carrera.getNombre().equals(vista.getCmbCarrera().getSelectedItem().toString()))
                .findFirst()
                .map(c -> c.getId())
                .orElse(0);

    }

    @Nullable
    private int getIdMateria() throws NullPointerException {
        return materias.stream()
                .filter(mat -> mat.getNombre().equals(vista.getCmbAsignatura().getSelectedItem().toString()))
                .findFirst()
                .map(c -> c.getId())
                .orElse(0);
    }

    private void cargarCmbCarreras() {
        carreras.stream()
                .map(c -> c.getNombre())
                .forEach(vista.getCmbCarrera()::addItem);
    }

    private void validarPeriodoRef(ActionEvent e) {
        if (vista.getCmbPeriodoRef().getSelectedIndex() != 0) {
            vista.getSpnUnidades().setEnabled(false);
        } else {
            vista.getSpnUnidades().setEnabled(true);
        }
    }

    /*
        EVENTOS
     */
    private void cmbAsignatura(ActionEvent e) {
        vista.getCmbAsignatura().removeAllItems();
        materias = NEWMateriaBD
                .single()
                .getMateriasSinSilabo(CONS.USUARIO.getPersona().getIdentificacion(), getIdCarrera());
        materias.stream()
                .map(c -> c.getNombre())
                .forEach(vista.getCmbAsignatura()::addItem);

        if (materias.size() > 0) {
            vista.getCmbAsignatura().setEnabled(true);
            vista.getBtnSiguiente().setEnabled(true);
            vista.getSpnUnidades().setEnabled(true);
        } else {
            vista.getCmbAsignatura().setEnabled(false);
            vista.getCmbAsignatura().addItem(MENSAJE_SIN_SILABO_PENDIENTE);
            vista.getBtnSiguiente().setEnabled(false);
            vista.getSpnUnidades().setEnabled(false);
            vista.getCmbPeriodoRef().addItem(MENSAJE_SIN_SILABO_PENDIENTE);
            vista.getCmbPeriodoRef().setEnabled(false);
        }

    }

    private void cmbPeriodoRef(ActionEvent e) {
        try {
            vista.getCmbPeriodoRef().removeAllItems();
            silabosRef = NEWSilaboBD
                    .single()
                    .getSilaboRef(getIdCarrera(), getIdMateria());

            if (silabosRef.size() > 0) {
                vista.getCmbPeriodoRef().setEnabled(true);

                vista.getCmbPeriodoRef().addItem("SI TIENE PERIODOS DE REFERENCIA");
                silabosRef.stream()
                        .map(c -> c.getPeriodo().getNombre())
                        .forEach(vista.getCmbPeriodoRef()::addItem);
            } else {
                vista.getCmbPeriodoRef().setEnabled(false);
                vista.getCmbPeriodoRef().addItem("NO TIENE PERIODOS DE REFERENCIA");
            }

        } catch (NullPointerException ex) {
            vista.getCmbPeriodoRef().addItem(MENSAJE_SIN_SILABO_PENDIENTE);
            vista.getCmbPeriodoRef().setEnabled(false);
        }

    }

    private void btnSiguiente(ActionEvent e) {

    }

    private void btnCancelar(ActionEvent e) {
        vista.dispose();
        VtnSilabosCTR vtn = new VtnSilabosCTR(desktop);
        vtn.Init();
    }
}
