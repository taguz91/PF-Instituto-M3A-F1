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
    
    private final String TABLA = " \"PeriodoIngresoNotas\" ";
    private final String ATRIBUTOS = " id_perd_ingr_notas, perd_notas_fecha_inicio, perd_notas_fecha_cierre, id_prd_lectivo, id_prd_lectivo, id_tipo_nota, perd_notas_estado ";
    private final String PRIMARY_KEY = " id_perd_ingr_notas ";
    
    public boolean insertar() {
        String INSERT = "INSERT INTO " + TABLA + " \n"
                + "(perd_notas_fecha_inicio,perd_notas_fecha_cierre,id_prd_lectivo,id_tipo_nota) \n"
                + "VALUES\n"
                + "("
                + " '" + getFechaInicio() + "',"
                + " '" + getFechaCierre() + "',"
                + "  " + getIdPeriodoLectivo() + ","
                + "  " + getIdTipoNota() + ""
                + ");";
        
        return ResourceManager.Statement(INSERT) == null;
    }
    
    public List<PeriodoIngresoNotasMD> SelectAll() {
        String SELECT = "SELECT " + ATRIBUTOS + " FROM " + TABLA;
        
        return selectSimple(SELECT);
    }
    
    public List<PeriodoIngresoNotasMD> SelectOne(String Aguja) {
        
        String SELECT = "";
        return selectSimple(SELECT);
        
    }
    
    public boolean editar(int Pk) {
        String UPDATE = "";
        
        return ResourceManager.Statement(UPDATE) == null;
        
    }
    
    public boolean eliminar() {
        String DELETE = "";
        
        return ResourceManager.Statement(DELETE) == null;
    }
    
    private List<PeriodoIngresoNotasMD> selectSimple(String QUERY) {
        
        List<PeriodoIngresoNotasMD> Lista = new ArrayList<>();
        
        ResultSet rs = ResourceManager.Query(QUERY);
        try {
            
            while (rs.next()) {
                PeriodoIngresoNotasMD periodo = new PeriodoIngresoNotasMD();
                
                periodo.setIdPeriodoIngreso(rs.getInt("id_perd_ingr_notas"));
                periodo.setFechaInicio(rs.getDate("perd_notas_fecha_inicio").toLocalDate());
                periodo.setFechaCierre(rs.getDate("perd_notas_fecha_cierre").toLocalDate());
                periodo.setIdPeriodoLectivo(PeriodoLectivoBD.selectWhere(rs.getInt("id_prd_lectivo")));
//                periodo.setIdTipoNota(rs.getInt("id_tipo_nota"));
                periodo.setEstado(rs.getBoolean("perd_notas_estado"));
                Lista.add(periodo);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return Lista;
    }
    
}
