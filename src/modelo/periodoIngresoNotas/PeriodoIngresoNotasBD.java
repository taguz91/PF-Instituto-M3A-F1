/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.periodoIngresoNotas;

import java.time.LocalDate;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.tipoDeNota.TipoDeNotaMD;

/**
 *
 * @author MrRainx
 */
public class PeriodoIngresoNotasBD extends PeriodoIngresoNotasMD {

    public PeriodoIngresoNotasBD(int idPeriodoIngreso, LocalDate fechaInicio, LocalDate fechaCierre, PeriodoLectivoMD idPeriodoLectivo, TipoDeNotaMD idTipoNota, boolean estado) {
        super(idPeriodoIngreso, fechaInicio, fechaCierre, idPeriodoLectivo, idTipoNota, estado);
    }

    public PeriodoIngresoNotasBD() {
    }

    public PeriodoIngresoNotasBD(PeriodoIngresoNotasMD obj) {
        this.setIdPeriodoIngreso(obj.getIdPeriodoIngreso());
        this.setFechaInicio(obj.getFechaInicio());
        this.setFechaCierre(obj.getFechaCierre());
        this.setEstado(obj.isEstado());
        this.setPeriodoLectivo(obj.getPeriodoLectivo());
        this.setTipoNota(obj.getTipoNota());
    }


}
