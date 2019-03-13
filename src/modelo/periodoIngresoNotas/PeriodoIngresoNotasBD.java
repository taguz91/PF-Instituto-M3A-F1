/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.periodoIngresoNotas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ResourceManager;

/**
 *
 * @author MrRainx
 */
public class PeriodoIngresoNotasBD extends PeriodoIngresoNotasMD {

    public PeriodoIngresoNotasBD(int idPeriodoIngreso, LocalDate fechaInicio, LocalDate fechaCierre, int idPeriodoLectivo, int idTipoNota) {
        super(idPeriodoIngreso, fechaInicio, fechaCierre, idPeriodoLectivo, idTipoNota);
    }

    public PeriodoIngresoNotasBD() {
    }

    public boolean insertar() {
        String INSERT = "";

        return ResourceManager.Statement(INSERT) == null;
    }

    public List<PeriodoIngresoNotasMD> SelectAll() {
        String SELECT = "";

        List<PeriodoIngresoNotasMD> Lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {
                Lista.add(getObjectFromRs(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoIngresoNotasBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Lista;
    }

    public List<PeriodoIngresoNotasMD> SelectOne(String Aguja) {

        String SELECT = "";

        List<PeriodoIngresoNotasMD> Lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {
            while (rs.next()) {
                Lista.add(getObjectFromRs(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeriodoIngresoNotasBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Lista;

    }

    public boolean editar(int Pk) {
        String UPDATE = "";

        return ResourceManager.Statement(UPDATE) == null;

    }

    public boolean eliminar() {
        String DELETE = "";

        return ResourceManager.Statement(DELETE) == null;
    }

    private PeriodoIngresoNotasMD getObjectFromRs(ResultSet rs) {
        PeriodoIngresoNotasMD periodo = new PeriodoIngresoNotasMD();

        try {
            periodo.setIdPeriodoIngreso(rs.getInt("id_perd_ingr_notas"));
            periodo.setFechaInicio(rs.getDate("perd_notas_fecha_inicio").toLocalDate());
            periodo.setFechaCierre(rs.getDate("perd_notas_fecha_cierre").toLocalDate());
            periodo.setIdPeriodoLectivo(rs.getInt("id_prd_lectivo"));
            periodo.setIdTipoNota(rs.getInt("id_tipo_nota"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return periodo;
    }

}
