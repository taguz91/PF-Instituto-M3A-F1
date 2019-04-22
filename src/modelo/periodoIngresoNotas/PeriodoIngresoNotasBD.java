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
import modelo.ResourceManager;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.tipoDeNota.TipoDeNotaBD;
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

    private static final String TABLA = " \"PeriodoIngresoNotas\" ";
    private static final String PRIMARY_KEY = " id_perd_ingr_notas ";

    public boolean insertar() {
        String INSERT = "INSERT INTO " + TABLA + " \n"
                + "(perd_notas_fecha_inicio,perd_notas_fecha_cierre,id_prd_lectivo,id_tipo_nota) \n"
                + "VALUES\n"
                + "("
                + " '" + java.sql.Date.valueOf(getFechaInicio()) + "',"
                + " '" + java.sql.Date.valueOf(getFechaCierre()) + "',"
                + "  " + getPeriodoLectivo().getId_PerioLectivo() + ","
                + "  " + getTipoNota().getIdTipoNota() + ""
                + ");";

        System.out.println(INSERT);

        return ResourceManager.Statement(INSERT) == null;
    }

    public static List<PeriodoIngresoNotasMD> selectAll() {

        String SELECT = "SELECT \"PeriodoLectivo\".prd_lectivo_nombre,\n"
                + "    \"PeriodoIngresoNotas\".perd_notas_fecha_inicio,\n"
                + "    \"PeriodoIngresoNotas\".perd_notas_fecha_cierre,\n"
                + "    \"TipoDeNota\".tipo_nota_nombre,\n"
                + "    \"PeriodoIngresoNotas\".perd_notas_estado,\n"
                + "    \"TipoDeNota\".tipo_nota_estado,\n"
                + "    \"PeriodoIngresoNotas\".id_prd_lectivo,\n"
                + "    \"PeriodoIngresoNotas\".id_tipo_nota,\n"
                + "    \"PeriodoIngresoNotas\".id_perd_ingr_notas\n"
                + "   FROM ((\"PeriodoLectivo\"\n"
                + "     JOIN \"PeriodoIngresoNotas\" ON ((\"PeriodoIngresoNotas\".id_prd_lectivo = \"PeriodoLectivo\".id_prd_lectivo)))\n"
                + "     JOIN \"TipoDeNota\" ON ((\"PeriodoIngresoNotas\".id_tipo_nota = \"TipoDeNota\".id_tipo_nota)))"
                + "WHERE\n"
                + "\"public\".\"PeriodoLectivo\".perd_notas_estado IS TRUE;";
        return selectFromView(SELECT);
    }

    public boolean editar(int Pk) {
        String UPDATE = "";

        return ResourceManager.Statement(UPDATE) == null;

    }

    public boolean eliminar(int PK) {
        String DELETE = "UPDATE " + TABLA
                + " SET "
                + " perd_notas_estado= " + false + " "
                + " WHERE "
                + " " + PRIMARY_KEY + "=" + PK + " ";

        return ResourceManager.Statement(DELETE) == null;
    }

    private static List<PeriodoIngresoNotasMD> selectFromView(String QUERY) {
        List<PeriodoIngresoNotasMD> Lista = new ArrayList<>();

        ResultSet rs = ResourceManager.Query(QUERY);
        try {

            while (rs.next()) {
                PeriodoIngresoNotasMD periodo = new PeriodoIngresoNotasMD();

                periodo.setIdPeriodoIngreso(rs.getInt("id_perd_ingr_notas"));
                periodo.setFechaInicio(rs.getDate("perd_notas_fecha_inicio").toLocalDate());
                periodo.setFechaCierre(rs.getDate("perd_notas_fecha_cierre").toLocalDate());

                periodo.setPeriodoLectivo(PeriodoLectivoBD.selectWhere(rs.getInt("id_prd_lectivo")));
                periodo.setTipoNota(TipoDeNotaBD.selectWhere(rs.getInt("id_tipo_nota")));
                periodo.setEstado(rs.getBoolean("perd_notas_estado"));

                Lista.add(periodo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Lista;
    }

}
