package controlador.curso;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.CONS;
import modelo.curso.CursoMD;
import modelo.curso.SesionClaseBD;
import modelo.curso.SesionClaseMD;
import modelo.estilo.TblEstilo;
import vista.curso.PnlHorarioClase;

/**
 *
 * @author Johnny
 */
public class PnlHorarioClaseCTR {

    private final PnlHorarioClase pnl;
    private final CursoMD curso;
    private final SesionClaseBD bd;
    private ArrayList<SesionClaseMD> sesiones;
    private DefaultTableModel mdTbl;
    private final String[][] datos = {};
    private final String[] t = {"Dia", "Hora Inicia", "Hora Fin", "Clase"};
    //private final String[] t = {"H", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
    private final String[] hm = {"07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
        "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};

    private String[] hSelec, jSelec, tSelec;

    public PnlHorarioClaseCTR(PnlHorarioClase pnl, CursoMD curso, SesionClaseBD bd) {
        this.pnl = pnl;
        this.curso = curso;
        this.bd = bd;
    }

    public void iniciar() {
        iniciaTbl();
    }

    private void iniciaTbl() {
        mdTbl = TblEstilo.modelTblSinEditar(datos, t);
        formatoTbl(pnl.getTblHorario());
        hSelec = hm;
        jSelec = t;
        llenarHorarios();
    }

    private void llenarHorarios() {
        sesiones = bd.cargarHorarioCurso(curso);
        if (sesiones != null) {
            System.out.println("---------");
            sesiones.forEach(s -> {
                System.out.println("Dia: " + s.getDia() + "  Horas: " + s.getHoraIni() + "    " + s.getHoraFin());
                Object[] v = {CONS.getDia(s.getDia()),
                    s.getHoraIni(),
                    s.getHoraFin(),
                    s.getId() + "%Clase \n"
                    + "Editar | Eliminar"};
                mdTbl.addRow(v);
            });
            System.out.println("---------");
        }
    }

    private void formatoTbl(JTable tbl) {
        tbl.setModel(mdTbl);
        //TblEstilo.formatoTblConColor(tbl);
        TblEstilo.formatoTblFocus(tbl);
    }

    public String[] gethSelec() {
        return hSelec;
    }

    public String[] getjSelec() {
        return jSelec;
    }

    public String[] gettSelec() {
        return tSelec;
    }

    public void actualizar(int dia) {
        llenarHorarios();
    }

}
