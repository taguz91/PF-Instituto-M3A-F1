package modelo.notas.calendario;

import java.time.LocalDate;
import modelo.periodolectivo.PeriodoLectivoMD;

/**
 *
 * @author Alejandro
 */

    
public class CalendarioMD {
    
 
    private int id_clnd_prd;
    private int num_sem;
    private LocalDate fecha_ini;
    private LocalDate fecha_fin;
    private PeriodoLectivoMD periodo;
    private String semanasActivas;


    public CalendarioMD(int id_clnd_prd, int num_sem, LocalDate fecha_ini, LocalDate fecha_fin, PeriodoLectivoMD periodo) {
        this.id_clnd_prd = id_clnd_prd;
        this.num_sem = num_sem;
        this.fecha_ini = fecha_ini;
        this.fecha_fin = fecha_fin;
        this.periodo = periodo;
    }


    public CalendarioMD() {
    }
    

    public int getId_clnd_prd() {
        return id_clnd_prd;
    }

    public void setId_clnd_prd(int id_clnd_prd) {
        this.id_clnd_prd = id_clnd_prd;
    }

    public int getNum_sem() {
        return num_sem;
    }

    public void setNum_sem(int num_sem) {
        this.num_sem = num_sem;
    }

    public LocalDate getFecha_ini() {
        return fecha_ini;
    }

    public void setFecha_ini(LocalDate fecha_ini) {
        this.fecha_ini = fecha_ini;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }
    
     public PeriodoLectivoMD getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoLectivoMD periodo) {
        this.periodo = periodo;
    }
    
    
    public String getSemanasActivas() {
        return semanasActivas;
    }

    public void setSemanasActivas(String semanasActivas) {
        this.semanasActivas = semanasActivas;
    }   
    
    @Override
    public String toString() {
        return "CalendarioMD{" + "id_clnd_prd =" + id_clnd_prd + ", clnd_prd_numero_semana=" + num_sem + ", clnd_prd_fecha_ini=" + fecha_ini + ", clnd_prd_fecha_fin=" + fecha_fin + ", id_prd_lectivo=" + periodo +'}';
    }
    
     public CalendarioMD get() {
        return this;
    }
  
}
