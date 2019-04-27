package controlador.curso;

import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.curso.CursoMD;
import modelo.curso.SesionClaseBD;
import modelo.curso.SesionClaseMD;
import modelo.estilo.TblEstilo;
import modelo.periodolectivo.PeriodoLectivoMD;
import vista.curso.PnlHorarioClase;

/**
 *
 * @author Johnny
 */
public class PnlHorarioCursoCTR {

    private final PnlHorarioClase pnl;
    private final CursoMD curso;
    private final SesionClaseBD bd;
    private ArrayList<SesionClaseMD> sesionLunes, sesionMartes, sesionMiercoles, sesionJueves, sesionViernes,
            sesionSabado;
    private DefaultTableModel mdTbl;
    private final String[][] datos = {};
    private final String[] t = {"H", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
    private final String[] tn = {"H", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
    private final String[] hm = {"07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00"};
    private final String[] hv = {"14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00"};
    private final String[] hn = {"8:00", "9:00", "10:00", "11:00", "12:00", "13:00",
        "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};
    private final String[] hmc = {
        "<html>07:00<br>08:00</html>",
        "<html>08:00<br>09:00</html>",
        "<html>09:00<br>10:00</html>",
        "<html>10:00<br>11:00</html>",
        "<html>11:00<br>12:00</html>",
        "<html>12:00<br>13:00</html>"};
    private final String[] hvc = {
        "<html>14:00<br>15:00</html>",
        "<html>15:00<br>16:00</html>",
        "<html>16:00<br>17:00</html>",
        "<html>17:00<br>18:00</html>",
        "<html>18:00<br>19:00</html>",
        "<html>19:00<br>20:00</html>"};
    private final String[] hnc = {
        "<html>8:00<br>9:00</html>",
        "<html>9:00<br>10:00</html>",
        "<html>10:00<br>11:00</html>",
        "<html>11:00<br>12:00</html>",
        "<html>12:00<br>13:00</html>",
        "---------------------------",
        "<html>16:00<br>17:00</html>",
        "<html>17:00<br>18:00</html>",
        "<html>18:00<br>19:00</html>",
        "<html>19:00<br>20:00</html>",
        "<html>20:00<br>21:00</html>",
        "<html>21:00<br>22:00</html>"};
    private String[] hSelec, jSelec, tSelec;
    private int posI, posF, posFila, posColum;

    public PnlHorarioCursoCTR(PnlHorarioClase pnl, CursoMD curso, SesionClaseBD bd) {
        this.pnl = pnl;
        this.curso = curso;
        this.bd = bd;
    }

    public PnlHorarioCursoCTR(PnlHorarioClase pnl, String nomCurso, int idPrd, ConectarDB conecta) {
        this.pnl = pnl;
        this.bd = new SesionClaseBD(conecta);
        this.curso = new CursoMD();
        this.curso.setCurso_nombre(nomCurso);
        //Inciamos el periodo para pasarle 
        PeriodoLectivoMD p = new PeriodoLectivoMD();
        p.setId_PerioLectivo(idPrd);
        this.curso.setId_prd_lectivo(p);
    }

    public void iniciar() {
        iniciaTbl();
    }

    private void iniciaTbl() {
        switch (curso.getCurso_nombre().charAt(0)) {
            case 'M':
                mdTbl = TblEstilo.modelTblSinEditar(datos, t);
                formatoTbl(pnl.getTblHorario());
                llenarHoras(hmc);
                hSelec = hm;
                jSelec = t;
                llenarLunesViernes();
                break;
            case 'V':
                mdTbl = TblEstilo.modelTblSinEditar(datos, t);
                formatoTbl(pnl.getTblHorario());
                llenarHoras(hvc);
                hSelec = hv;
                jSelec = t;
                llenarLunesViernes();
                break;
            case 'N':
                mdTbl = TblEstilo.modelTblSinEditar(datos, tn);
                formatoTbl(pnl.getTblHorario());
                llenarHoras(hnc);
                hSelec = hn;
                jSelec = tn;
                llenarLunesSabado();
                break;
            default:

                break;
        }
    }

    private void llenarLunesViernes() {
        actualizarLunes();
        actualizarMartes();
        actualizarMiercoles();
        actuatizarJueves();
        actualizarViernes();
    }

    private void llenarLunesSabado() {
        llenarLunesViernes();
        actualizarSabado();
    }

    private void formatoTbl(JTable tbl) {
        tbl.setModel(mdTbl);
        //TblEstilo.formatoTblConColor(tbl);
        TblEstilo.formatoTblHCurso(tbl);
    }

    private void llenarHoras(String[] horas) {
        for (String h : horas) {
            Object[] v = {h};
            mdTbl.addRow(v);
        }
    }

    private void llenarDia(ArrayList<SesionClaseMD> sesiones, int dia) {
        if (sesiones != null) {
            sesiones.forEach(s -> {
                buscarClm(s, dia);
            });
        }
    }

    private void buscarClm(SesionClaseMD s, int dia) {
        for (int i = 0; i < hSelec.length; i++) {
            if (hSelec[i].equals(tranformar(s.getHoraIni()))) {
                posI = i;
                break;
            }
        }

        for (int i = 0; i < hSelec.length; i++) {
            if (hSelec[i].equals(tranformar(s.getHoraFin()))) {
                posF = i;
                break;
            }
        }

        for (int i = posI; i < posF; i++) {
            mdTbl.setValueAt("<html> <center>" + s.getId() + "" + s.getCurso().getId_curso() + "<br>"
                    + s.getCurso().getId_materia().getNombre() + "<br>"
                    + s.getCurso().getId_docente().getNombreCorto() + "</center></html>",
                    i, dia);
        }

    }

    private String horaString, minutoString;

    private String tranformar(LocalTime hora) {
        if (hora.getHour() < 10) {
            horaString = "0" + hora.getHour();
        } else {
            horaString = "" + hora.getHour();
        }

        if (hora.getMinute() < 10) {
            minutoString = "0" + hora.getMinute();
        } else {
            minutoString = "" + hora.getMinute();
        }
        return horaString + ":" + minutoString;
    }

    //Todos los metodos publicos de esta clase
    public void actualizarLunes() {
        sesionLunes = bd.cargarHorarioCursoPorDia(curso.getCurso_nombre(), 1,
                curso.getId_prd_lectivo().getId_PerioLectivo());
        llenarDia(sesionLunes, 1);
    }

    public void actualizarMartes() {
        sesionMartes = bd.cargarHorarioCursoPorDia(curso.getCurso_nombre(), 2,
                curso.getId_prd_lectivo().getId_PerioLectivo());
        llenarDia(sesionMartes, 2);
    }

    public void actualizarMiercoles() {
        sesionMiercoles = bd.cargarHorarioCursoPorDia(curso.getCurso_nombre(), 3,
                curso.getId_prd_lectivo().getId_PerioLectivo());
        llenarDia(sesionMiercoles, 3);
    }

    public void actuatizarJueves() {
        sesionJueves = bd.cargarHorarioCursoPorDia(curso.getCurso_nombre(), 4,
                curso.getId_prd_lectivo().getId_PerioLectivo());
        llenarDia(sesionJueves, 4);
    }

    public void actualizarViernes() {
        sesionViernes = bd.cargarHorarioCursoPorDia(curso.getCurso_nombre(), 5,
                curso.getId_prd_lectivo().getId_PerioLectivo());
        llenarDia(sesionViernes, 5);
    }

    public void actualizarSabado() {
        sesionSabado = bd.cargarHorarioCursoPorDia(curso.getCurso_nombre(), 6,
                curso.getId_prd_lectivo().getId_PerioLectivo());
        llenarDia(sesionSabado, 6);
    }

}
