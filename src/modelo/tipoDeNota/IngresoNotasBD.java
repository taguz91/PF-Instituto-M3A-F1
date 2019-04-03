package modelo.tipoDeNota;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ResourceManager;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteMD;

/**
 *
 * @author MrRainx
 */
public class IngresoNotasBD extends IngresoNotasMD {

    public IngresoNotasBD(int idIngresoNotas, boolean notaPrimerInterCiclo, boolean notaExamenInteCiclo, boolean notaSegundoInterCicli, boolean examenFinal, boolean examenDeRecuperacion, CursoMD curso) {
        super(idIngresoNotas, notaPrimerInterCiclo, notaExamenInteCiclo, notaSegundoInterCicli, examenFinal, examenDeRecuperacion, curso);
    }

    public IngresoNotasBD() {
    }

    public static List<IngresoNotasBD> selectAll() {

        String SELECT = "SELECT\n"
                + "\"public\".\"ViewCursosPermisosNotas\".nota_primer_inteciclo,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".nota_examen_intecilo,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".nota_segundo_inteciclo,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".nota_examen_final,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".nota_examen_de_recuperacion,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".id_ingreso_notas,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".id_curso,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".id_materia,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".id_prd_lectivo,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".id_docente,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".curso_nombre,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".materia_nombre,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".materia_codigo,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".prd_lectivo_nombre,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".persona_identificacion,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".persona_primer_apellido,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".persona_segundo_apellido,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".persona_primer_nombre,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".persona_segundo_nombre\n"
                + "FROM\n"
                + "\"public\".\"ViewCursosPermisosNotas\""
                + "WHERE\n"
                + "prd_lectivo_activo = TRUE\n"
                + "AND \n"
                + "prd_lectivo_estado = TRUE";

        return selectFromView(SELECT);
    }

    public static IngresoNotasMD selectFromViewActivos(int idCurso) {
        ResourceManager.Statements("REFRESH MATERIALIZED VIEW \"ViewCursosPermisosNotas\" \n");
        String SELECT = "SELECT\n"
                + "\"public\".\"ViewCursosPermisosNotas\".nota_primer_inteciclo,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".nota_examen_intecilo,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".nota_segundo_inteciclo,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".nota_examen_final,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".nota_examen_de_recuperacion,\n"
                + "\"public\".\"ViewCursosPermisosNotas\".id_ingreso_notas\n"
                + "FROM\n"
                + "\"public\".\"ViewCursosPermisosNotas\"\n"
                + "WHERE\n"
                + "\"ViewCursosPermisosNotas\".id_curso = " + idCurso + "";

        IngresoNotasMD ingreso = new IngresoNotasMD();

        ResultSet rs = ResourceManager.Query(SELECT);

        try {

            while (rs.next()) {
                ingreso.setIdIngresoNotas(rs.getInt("id_ingreso_notas"));

                ingreso.setNotaPrimerInterCiclo(rs.getBoolean("nota_primer_inteciclo"));
                ingreso.setNotaExamenInteCiclo(rs.getBoolean("nota_examen_intecilo"));
                ingreso.setNotaSegundoInterCiclo(rs.getBoolean("nota_segundo_inteciclo"));
                ingreso.setNotaExamenFinal(rs.getBoolean("nota_examen_final"));
                ingreso.setNotaExamenDeRecuperacion(rs.getBoolean("nota_examen_de_recuperacion"));

                System.out.println(ingreso);

            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return ingreso;
    }

    private static List<IngresoNotasBD> selectFromView(String QUERY) {
        ResourceManager.Statements("REFRESH MATERIALIZED VIEW \"ViewCursosPermisosNotas\" \n");
        List<IngresoNotasBD> lista = new ArrayList<>();
        ResultSet rs = ResourceManager.Query(QUERY);
        try {
            while (rs.next()) {

                IngresoNotasBD ingreso = new IngresoNotasBD();
                ingreso.setIdIngresoNotas(rs.getInt("id_ingreso_notas"));
                ingreso.setNotaPrimerInterCiclo(rs.getBoolean("nota_primer_inteciclo"));
                ingreso.setNotaExamenInteCiclo(rs.getBoolean("nota_examen_intecilo"));
                ingreso.setNotaSegundoInterCiclo(rs.getBoolean("nota_segundo_inteciclo"));
                ingreso.setNotaExamenFinal(rs.getBoolean("nota_examen_final"));
                ingreso.setNotaExamenDeRecuperacion(rs.getBoolean("nota_examen_de_recuperacion"));

                CursoMD curso = new CursoMD();
                curso.setId_curso(rs.getInt("id_curso"));
                curso.setCurso_nombre(rs.getString("curso_nombre"));

                MateriaMD materia = new MateriaMD();
                materia.setId(rs.getInt("id_materia"));
                materia.setNombre(rs.getString("materia_nombre"));
                materia.setCodigo(rs.getString("materia_codigo"));
                curso.setId_materia(materia);//GUARDAMOS LA MATERIA EN EL CURSO

                PeriodoLectivoMD periodo = new PeriodoLectivoMD();
                periodo.setId_PerioLectivo(rs.getInt("id_prd_lectivo"));
                periodo.setNombre_PerLectivo(rs.getString("prd_lectivo_nombre"));
                curso.setId_prd_lectivo(periodo);//GUARDAMOS EL PERIODO

                DocenteMD docente = new DocenteMD();
                docente.setIdDocente(rs.getInt("id_docente"));
                docente.setIdentificacion(rs.getString("persona_identificacion"));
                docente.setPrimerApellido(rs.getString("persona_primer_apellido"));
                docente.setSegundoApellido(rs.getString("persona_segundo_apellido"));
                docente.setPrimerNombre(rs.getString("persona_primer_nombre"));
                docente.setSegundoNombre(rs.getString("persona_segundo_nombre"));
                curso.setId_docente(docente);//GUARDAMOS AL DOCENTE

                ingreso.setCurso(curso);//GUARDAMOS EL CURSO
                lista.add(ingreso);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }

    public boolean editar(int PK) {
        String UPDATE = "UPDATE \"IngresoNotas\" \n"
                + "SET \n"
                + "nota_primer_inteciclo = " + isNotaPrimerInterCiclo() + ",\n"
                + "nota_examen_intecilo = " + isNotaExamenInteCiclo() + ",\n"
                + "nota_segundo_inteciclo = " + isNotaSegundoInterCiclo() + ",\n"
                + "nota_examen_final = " + isNotaExamenFinal() + ",\n"
                + "nota_examen_de_recuperacion = " + isNotaExamenDeRecuperacion() + "\n"
                + "WHERE\n"
                + "id_ingreso_notas = " + PK + ";\n"
                + "";

        return ResourceManager.Statement(UPDATE) == null;
    }

}
