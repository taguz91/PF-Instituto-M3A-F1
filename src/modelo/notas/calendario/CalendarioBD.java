package modelo.notas.calendario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.ConnDBPool;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author Alejandro
 */
public class CalendarioBD extends CalendarioMD{

    private String SELECT;
    private final ConnDBPool pool;
    private Connection conn;
    private ResultSet rst;

    {
        pool = new ConnDBPool();
    }

    public ArrayList<CalendarioMD> cargarSemanas(int prd_lec) {
        SELECT = "SELECT\n" +
"public.\"CalendarioPeriodo\".clnd_prd_fecha_ini || ' / ' ||\n" +
"                public.\"CalendarioPeriodo\".clnd_prd_fecha_fin as Semanas,\n" +
"                public.\"PeriodoLectivo\".prd_lectivo_nombre\n" +
"                 FROM\n" +
"                public.\"CalendarioPeriodo\"\n" +
"                INNER JOIN public.\"PeriodoLectivo\" ON public.\"CalendarioPeriodo\".id_prd_lectivo = public.\"PeriodoLectivo\".id_prd_lectivo\n" +
"                WHERE public.\"PeriodoLectivo\".id_prd_lectivo =  "+ prd_lec +" ";
        ArrayList<CalendarioMD> semanas = new ArrayList<>();
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, null);
        System.out.println("Query: \n" + SELECT);
        try {
            while (rst.next()) {
                CalendarioMD calendario = new CalendarioMD();
                calendario.setSemanasActivas(rst.getString(1));
                semanas.add(calendario);
               
            }
        } catch (SQLException | NullPointerException e) {
            if (e instanceof SQLException) {
                System.out.println(e.getMessage());
            }
        } finally {
            pool.closeStmt();
            pool.close(rst);
            pool.close(conn);
        }
        return semanas;
    }
    
     public LocalDate fechaIniSemana (int id_prd , int num_semana){
        LocalDate fecha_ini = null;
        String SELECT = "SELECT\n" +
"\"public\".\"CalendarioPeriodo\".clnd_prd_fecha_ini\n" +
"FROM\n" +
"\"public\".\"CalendarioPeriodo\"\n" +
"WHERE id_prd_lectivo = "+ id_prd + " AND clnd_prd_numero_semana = "+ num_semana+"";
        
        conn = pool.getConnection();
        rst = pool.ejecutarQuery(SELECT, conn, null);
            try {
                while (rst.next()) {
                    fecha_ini = rst.getDate("prd_lectivo_fecha_inicio").toLocalDate();
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    System.out.println(fecha_ini);
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                }
                return fecha_ini;
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            pool.close(conn);
        }
        return fecha_ini;
    }
}
