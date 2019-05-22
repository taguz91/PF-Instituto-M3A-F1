package modelo.notas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ConnDBPool;
import modelo.alumno.AlumnoCursoMD;
import modelo.tipoDeNota.TipoDeNotaMD;

/**
 *
 * @author MrRainx
 */
public class NotasBD extends NotasMD {

    private ConnDBPool pool;
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
                + "\"public\".\"Notas\".id_almn_curso = " + alumnnoCurso.getId();

        List<NotasBD> lista = new ArrayList<>();

        System.out.println(SELECT);

        try {
            conn = pool.getConnection();
            rs = pool.ejecutarQuery(SELECT, conn, null);
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
            System.out.println(e.getMessage());
        } finally {
            pool.closeStmt();
            pool.close(rs);
            pool.close(conn);
        }

//
//        if (lista.isEmpty()) {
//            String SELECT_COMPROBACION = "SELECT\n"
//                    + "\"public\".\"Cursos\".id_curso,\n"
//                    + "\"public\".\"PeriodoLectivo\".prd_lectivo_nombre,\n"
//                    + "\"public\".\"TipoDeNota\".id_tipo_nota,\n"
//                    + "\"public\".\"TipoDeNota\".tipo_nota_nombre,\n"
//                    + "\"public\".\"AlumnoCurso\".id_almn_curso,\n"
//                    + "\"public\".\"Carreras\".carrera_modalidad,\n"
//                    + "\"public\".\"Cursos\".id_curso,\n"
//                    + "\"public\".\"PeriodoLectivo\".prd_lectivo_nombre,\n"
//                    + "\"public\".\"TipoDeNota\".tipo_nota_nombre,\n"
//                    + "\"public\".\"TipoDeNota\".id_tipo_nota,\n"
//                    + "\"public\".\"AlumnoCurso\".id_almn_curso,\n"
//                    + "\"public\".\"Carreras\".carrera_modalidad\n"
//                    + "FROM\n"
//                    + "\"public\".\"Cursos\"\n"
//                    + "INNER JOIN \"public\".\"PeriodoLectivo\" ON \"public\".\"Cursos\".id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
//                    + "INNER JOIN \"public\".\"TipoDeNota\" ON \"public\".\"TipoDeNota\".id_prd_lectivo = \"public\".\"PeriodoLectivo\".id_prd_lectivo\n"
//                    + "INNER JOIN \"public\".\"AlumnoCurso\" ON \"public\".\"AlumnoCurso\".id_curso = \"public\".\"Cursos\".id_curso\n"
//                    + "INNER JOIN \"public\".\"Carreras\" ON \"public\".\"PeriodoLectivo\".id_carrera = \"public\".\"Carreras\".id_carrera\n"
//                    + "WHERE\n"
//                    + "\"AlumnoCurso\".id_almn_curso = " + alumnnoCurso.getId() + " AND\n"
//                    + "\"TipoDeNota\".tipo_nota_nombre <> 'NOTA FINAL'";
//            ResultSet info = ResourceManager.Query(SELECT_COMPROBACION);
//
//            //System.out.println(SELECT_COMPROBACION);
//
//            try {
//
//                while (info.next()) {
//                    String modalidad = info.getString("carrera_modalidad");
//
//                    if (modalidad.equalsIgnoreCase("PRESENCIAL")) {
//
//                        int id_tipo_nota = info.getInt("id_tipo_nota");
//
//                        String INSERT_NOTAS = "INSERT INTO \"Notas\" "
//                                + " ( id_tipo_nota, id_almn_curso )\n"
//                                + "VALUES\n"
//                                + "	(" + id_tipo_nota + "," + alumnnoCurso.getId() + ");";
//
//                        System.out.println(INSERT_NOTAS);
//
//                        ResourceManager.Statements(INSERT_NOTAS);
//                    }
//
//                }
//                info.close();
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//            lista = selectWhere(alumnnoCurso);
//        }
        return lista;
    }
    private boolean ejecutar = false;

    public boolean editar() {
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
