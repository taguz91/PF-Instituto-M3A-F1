package modelo.notas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ConnDBPool;
import modelo.alumno.AlumnoCursoMD;
import modelo.tipoDeNota.TipoDeNotaMD;

/**
 *
 * @author MrRainx
 */
public class NotasBD extends NotasMD {

    private final ConnDBPool pool;
    private Connection conn;
    private ResultSet rs;

    {
        pool = new ConnDBPool();
    }

    public NotasBD(int idNota, double notaValor, AlumnoCursoMD alumnoCurso, TipoDeNotaMD tipoDeNota) {
        super(idNota, notaValor, alumnoCurso, tipoDeNota);
    }

    public NotasBD() {
    }

    public List<NotasBD> selectWhere(AlumnoCursoMD alumnnoCurso) {
        String SELECT = "SELECT\n"
                + "\"public\".\"Notas\".id_nota,\n"
                + "\"public\".\"Notas\".nota_valor,\n"
                + "\"public\".\"Notas\".id_tipo_nota,\n"
                + "\"public\".\"TipoDeNota\".tipo_nota_nombre\n"
                + "FROM\n"
                + "\"public\".\"Notas\"\n"
                + "INNER JOIN \"public\".\"TipoDeNota\" ON \"public\".\"Notas\".id_tipo_nota = \"public\".\"TipoDeNota\".id_tipo_nota\n"
                + "WHERE\n"
                + "\"public\".\"Notas\".id_almn_curso = ?\n"
                + "ORDER BY \"Notas\".nota_valor ASC";

        List<NotasBD> lista = new ArrayList<>();
        Map<Integer, Object> parametros = new HashMap<>();
        parametros.put(1, alumnnoCurso.getId());

        try {
            conn = pool.getConnection();
            rs = pool.ejecutarQuery(SELECT, conn, parametros);
            while (rs.next()) {
                NotasBD nota = new NotasBD();

                nota.setIdNota(rs.getInt("id_nota"));
                nota.setNotaValor(rs.getDouble("nota_valor"));
                TipoDeNotaMD tipoDeNota = new TipoDeNotaMD();
                tipoDeNota.setNombre(rs.getString("tipo_nota_nombre"));
                nota.setTipoDeNota(tipoDeNota);
                lista.add(nota);
            }

        } catch (SQLException e) {
            Logger.getLogger(NotasBD.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            pool.closeStmt().close(rs).close(conn);
        }

        return lista;
    }
    private boolean ejecutar = false;

    public synchronized boolean editar() {
        new Thread(() -> {
            String UPDATE = "UPDATE \"Notas\" \n"
                    + "SET nota_valor = " + getNotaValor() + " \n"
                    + "WHERE \n"
                    + "\"public\".\"Notas\".id_nota = " + getIdNota();
            System.out.println(UPDATE);
            conn = pool.getConnection();
            ejecutar = pool.ejecutar(UPDATE, conn, null) == null;
        }).start();
        return ejecutar;
    }

}
