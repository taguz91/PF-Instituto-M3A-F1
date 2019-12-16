package modelo.asistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gus
 */
public class GenerarFechas {

    private final List<String> fechas = new ArrayList<>();
    private final NEWAsistenciaBD ABD = NEWAsistenciaBD.single();

    // Variables que nos ayudaran a generar las fechas  
    private LocalDate fechaAux;
    private int diaAumentar;
    private int diaAnterior;

    public List<String> getFechasClaseCurso(int idCurso) {
        List<AsistenciaHoras> ahs = ABD.getDiasCurso(idCurso);
        AsistenciaSesionMD as = ABD.getInfoSesion(idCurso);
        fechaAux = as.getPrdFechaInicio();
        diaAumentar = 0;
        diaAnterior = as.getDiaInicio();
        while (fechaAux.isBefore(as.getPrdFechaFin())
                || fechaAux.equals(as.getPrdFechaFin())) {
            ahs.forEach(d -> {
                if (as.getDiaInicio() == as.getDiaFin()) {
                    diaAumentar = 7;
                } else {
                    diaAumentar = getDias(diaAnterior, d.getDia());
                }
                diaAnterior = d.getDia();

                fechaAux = fechaAux.plusDays(diaAumentar);
                fechas.add(
                        fechaAux.getDayOfMonth() + "/"
                        + fechaAux.getMonthValue() + "/"
                        + fechaAux.getYear()
                );
            });
        }
        return fechas;
    }

    private int getDias(int diaInicio, int diaFin) {
        int c = 0;
        boolean continua = true;
        int diaAux = diaInicio;
        while (continua) {
            if (diaFin == diaAux) {
                continua = false;
            } else {
                c++;
                diaAux++;
                if (diaAux == 8) {
                    diaAux = 1;
                }
                if (diaAux == diaFin) {
                    continua = false;
                }
            }
        }
        return c;
    }

}
