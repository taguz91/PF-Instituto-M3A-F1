/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.reportesExcel;

import utils.ToExcel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import modelo.ConnDBPool;

/**
 *
 * @author MrRainx
 */
public class ReportesExcel {

    private final ConnDBPool CON = ConnDBPool.single();

    private static ReportesExcel INSTANCE = null;

    public static ReportesExcel single() {

        if (INSTANCE == null) {
            INSTANCE = new ReportesExcel();
        }

        return INSTANCE;
    }

    public static String EGRESADOS = ""
            + " SELECT\n"
            + "                '' AS \"CÓDIGO DEL IST\",\n"
            + "                'ISTA' AS \"NOMBRE DEL INSTITUTO\",\n"
            + "                'AZUAY' AS \"PROVINCIA\",\n"
            + "                carrera_codigo AS \"CÓDIGO DE LA CARRERA\",\n"
            + "                carrera_nombre AS \"CARRERA\",\n"
            + "                carrera_modalidad AS \"MODALIDAD DE ESTUDIOS\",\n"
            + "                '' AS \"TIPO DE IDENTIFICACIÓN\",\n"
            + "                p.persona_identificacion AS \"NRO. DE IDENTIFICACIÓN\",\n"
            + "                p.persona_primer_apellido || ' ' ||\n"
            + "                p.persona_segundo_apellido || ' ' ||\n"
            + "                p.persona_primer_nombre || ' ' ||\n"
            + "                p.persona_segundo_nombre AS \"APELLIDOS Y NOMBRES\",\n"
            + "                consultar_pais(p.id_lugar_natal) AS \"NACIONALIDAD\",\n"
            + "                CASE WHEN trabajo_titulacion THEN 'SI' ELSE 'NO' END\n"
            + "                AS \"TRABAJO DE TITULACIÓN FINALIZADO S/N\"\n"
            + "                \n"
            + "                FROM alumno.\"Egresados\" e\n"
            + "                JOIN public.\"AlumnosCarrera\" ac\n"
            + "                ON ac.id_almn_carrera = e.id_almn_carrera\n"
            + "                JOIN public.\"Carreras\" c\n"
            + "                ON ac.id_carrera = c.id_carrera\n"
            + "                JOIN public.\"Alumnos\" a\n"
            + "                ON a.id_alumno = ac.id_alumno\n"
            + "                JOIN public.\"Personas\" p\n"
            + "                ON p.id_persona = a.id_persona\n"
            + "                WHERE id_prd_lectivo IN (?)"
            + "";

    public static String RETIRADOS = ""
            + "                SELECT\n"
            + "                    '' AS \"CÓDIGO DEL IST\",\n"
            + "                    'ISTA' AS \"NOMBRE DEL INSTITUTO\",\n"
            + "                    'AZUAY' AS \"PROVINCIA\",\n"
            + "                    carrera_codigo AS \"CÓDIGO DE LA CARRERA\",\n"
            + "                    carrera_nombre AS \"CARRERA\",\n"
            + "                    carrera_modalidad AS \"MODALIDAD DE ESTUDIOS\",\n"
            + "                    '' AS \"TIPO DE IDENTIFICACIÓN\",\n"
            + "                    p.persona_identificacion AS \"NRO. DE IDENTIFICACIÓN\",\n"
            + "                    p.persona_primer_apellido || ' ' ||\n"
            + "                    p.persona_segundo_apellido || ' ' ||\n"
            + "                    p.persona_primer_nombre || ' ' ||\n"
            + "                    p.persona_segundo_nombre AS \"APELLIDOS Y NOMBRES\",\n"
            + "                    consultar_pais(p.id_lugar_natal) AS \"NACIONALIDAD\",\n"
            + "                    r.fecha_retiro AS \"FECHA DEL DOCUMENTO HABILITANTE PRESENTADO\"\n"
            + "                    \n"
            + "                    FROM alumno.\"Retirados\" r\n"
            + "                    JOIN public.\"AlumnosCarrera\" ac\n"
            + "                    ON ac.id_almn_carrera = r.id_almn_carrera\n"
            + "                    JOIN public.\"Carreras\" c\n"
            + "                    ON ac.id_carrera = c.id_carrera\n"
            + "                    JOIN public.\"Alumnos\" a\n"
            + "                    ON a.id_alumno = ac.id_alumno\n"
            + "                    JOIN public.\"Personas\" p\n"
            + "                    ON p.id_persona = a.id_persona\n"
            + "                    WHERE id_prd_lectivo IN (?);"
            + "";

    public static String DESERTORES = ""
            + "            SELECT\n"
            + "                '' AS \"CÓDIGO DEL IST\",\n"
            + "                'ISTA' AS \"NOMBRE DEL INSTITUTO\",\n"
            + "                'AZUAY' AS \"PROVINCIA\",\n"
            + "                carrera_codigo AS \"CÓDIGO DE LA CARRERA\",\n"
            + "                carrera_nombre AS \"CARRERA\",\n"
            + "                carrera_modalidad AS \"MODALIDAD DE ESTUDIOS\",\n"
            + "                '' AS \"TIPO DE IDENTIFICACIÓN\",\n"
            + "                p.persona_identificacion AS \"NRO. DE IDENTIFICACIÓN\",\n"
            + "                p.persona_primer_apellido || ' ' ||\n"
            + "                p.persona_segundo_apellido || ' ' ||\n"
            + "                p.persona_primer_nombre || ' ' ||\n"
            + "                p.persona_segundo_nombre AS \"APELLIDOS Y NOMBRES\",\n"
            + "                consultar_pais(p.id_lugar_natal) AS \"NACIONALIDAD\", (\n"
            + "                  SELECT MAX(fecha_asistencia)\n"
            + "                  FROM public.\"Asistencia\"\n"
            + "                  WHERE id_almn_curso IN (\n"
            + "                    SELECT id_almn_curso\n"
            + "                    FROM public.\"AlumnoCurso\" ac\n"
            + "                    WHERE ac.id_alumno = m.id_alumno\n"
            + "                    AND id_curso IN (\n"
            + "                      SELECT id_curso FROM public.\"Cursos\"\n"
            + "                      WHERE id_prd_lectivo = m.id_prd_lectivo\n"
            + "                    )\n"
            + "                  ) AND numero_faltas = 0\n"
            + "                ) AS \"FECHA DE LA ULTIMA ASISTENCIA A CLASES\"\n"
            + "                \n"
            + "                FROM public.\"Matricula\" m\n"
            + "                JOIN public.\"Alumnos\" a\n"
            + "                ON a.id_alumno = m.id_alumno\n"
            + "                JOIN public.\"Personas\" p\n"
            + "                ON p.id_persona = a.id_persona\n"
            + "                JOIN public.\"PeriodoLectivo\" pl\n"
            + "                ON pl.id_prd_lectivo = m.id_prd_lectivo\n"
            + "                JOIN public.\"Carreras\" c\n"
            + "                ON c.id_carrera = pl.id_carrera\n"
            + "                WHERE m.id_prd_lectivo IN (?)\n"
            + "                AND m.id_alumno IN (\n"
            + "                  SELECT id_alumno\n"
            + "                  FROM public.\"AlumnoCurso\"\n"
            + "                  WHERE almn_curso_asistencia ILIKE '%Desertor%'\n"
            + "                  OR almn_curso_asistencia ILIKE '%Retirado%'\n"
            + "                )\n"
            + "                \n"
            + "                ORDER BY carrera_nombre,\n"
            + "                carrera_codigo;"
            + "";

    public void generarReporte(List<Integer> ids, String statement, String reportName) {

        ResultSet rs = CON.ejecutarQuery(
                CON.useINsql(ids, statement)
        );
        try {
            int colums = rs.getMetaData().getColumnCount() + 1;
            List<String> columnsName = new ArrayList<>();

            IntStream.range(1, colums).forEach(i -> {
                try {
                    columnsName.add(rs.getMetaData().getColumnName(i));
                } catch (SQLException ex) {
                    Logger.getLogger(ReportesExcel.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            List rows = new ArrayList();

            while (rs.next()) {

                List row = new ArrayList();

                for (String colum : columnsName) {
                    row.add(rs.getString(colum));
                }

                rows.add(row);
            }

            ToExcel excel = new ToExcel();
            excel.exportarExcel(columnsName, rows, reportName);
        } catch (SQLException ex) {
            Logger.getLogger(ReportesExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
