
package modelo.PlanClases;


public class RecursosPlanClasesMD {
    private Integer id_recursos_plan_clases;
    private PlandeClasesMD id_plan_clases;
    private RecursosMD id_recursos; 

    public RecursosPlanClasesMD() {
        this.id_plan_clases=new PlandeClasesMD();
        this.id_recursos=new RecursosMD();
    }

    public RecursosPlanClasesMD(PlandeClasesMD id_plan_clases, RecursosMD id_recursos) {
        this.id_plan_clases = id_plan_clases;
        this.id_recursos = id_recursos;
    }

    public RecursosPlanClasesMD(RecursosMD id_recursos) {
        
        this.id_recursos = id_recursos;
         this.id_plan_clases=new PlandeClasesMD();
    }
    

    public Integer getId_recursos_plan_clases() {
        return id_recursos_plan_clases;
    }

    public void setId_recursos_plan_clases(Integer id_recursos_plan_clases) {
        this.id_recursos_plan_clases = id_recursos_plan_clases;
    }

    public PlandeClasesMD getId_plan_clases() {
        return id_plan_clases;
    }

    public void setId_plan_clases(PlandeClasesMD id_plan_clases) {
        this.id_plan_clases = id_plan_clases;
    }

    public RecursosMD getId_recursos() {
        return id_recursos;
    }

    public void setId_recursos(RecursosMD id_recursos) {
        this.id_recursos = id_recursos;
    }

    @Override
    public String toString() {
        return "RecursosPlanClasesMD{" + "id_recursos_plan_clases=" + id_recursos_plan_clases + ", id_plan_clases=" + id_plan_clases + ", id_recursos=" + id_recursos + '}';
    }
    
    
    
    
}
