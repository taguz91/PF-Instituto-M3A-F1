
package controlador.silabo;


import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import modelo.ConexionBD;
import modelo.EstrategiasMetodologicas.EstrategiasMetodologicasBD;
import modelo.EstrategiasMetodologicas.EstrategiasMetodologicasMD;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.PlanClases.RecursosPlanClasesBD;
import modelo.PlanClases.RecursosPlanClasesMD;
import modelo.PlanClases.TrabajoAutonomoBD;
import modelo.PlanClases.TrabajoAutonomoMD;
import modelo.curso.CursoMD;
import modelo.estrategiasAprendizaje.EstrategiasAprendizajeMD;
import modelo.estrategiasUnidad.EstrategiasUnidadBD;
import modelo.estrategiasUnidad.EstrategiasUnidadMD;
import modelo.evaluacionSilabo.EvaluacionSilaboBD;
import modelo.evaluacionSilabo.EvaluacionSilaboMD;
import modelo.silabo.CursoMDS;
import modelo.silabo.CursosBDS;
import modelo.silabo.SilaboMD;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmPlanClase.CheckListItem;
import vista.silabos.frmPlanClase.CheckListRenderer;
import vista.silabos.frmPlanClase;




public class ControladorEditarPlanClases {
    private frmPlanClase fPlanClase;
    private PlandeClasesMD planClaseMD;
    private VtnPrincipal principal;
    private ConexionBD conexion;
    private CursoMD curso;
    private SilaboMD silabo;
    private UnidadSilaboMD unidadsilabo;
    private List<CursoMDS> lista_curso;
    private List<PlandeClasesMD> lista_plan;
    private List<TrabajoAutonomoMD> lista_tra_aut;
    private List<RecursosPlanClasesMD> lista_recursoMD;
    private List<RecursosPlanClasesMD> lista_recursoBD;
    private List<EstrategiasUnidadMD> lista_estrategiasSilabo;
    private List<UnidadSilaboMD> lista_unidadsilabo;
    private List<EvaluacionSilaboMD> lista_evaluacionesSilabo;
    private List<EstrategiasMetodologicasMD> lista_estrategias_metodologicas;
    private DefaultListModel modelo;
    
    

    public ControladorEditarPlanClases(PlandeClasesMD planClaseMD, VtnPrincipal principal, ConexionBD conexion, CursoMD curso, SilaboMD silabo, UnidadSilaboMD unidadsilabo) {
        this.planClaseMD = planClaseMD;
        this.principal = principal;
        this.conexion = conexion;
        this.curso = curso;
        this.silabo = silabo;
        this.unidadsilabo = unidadsilabo;
    }
    
    public void iniciaControlador(){
         conexion.conectar();
        fPlanClase = new frmPlanClase();
        principal.getDpnlPrincipal().add(fPlanClase);
        fPlanClase.setTitle("EDITAR "+silabo.getIdMateria().getNombre());
        fPlanClase.show();
        fPlanClase.setLocation((principal.getDpnlPrincipal().getSize().width - fPlanClase.getSize().width) / 2,
                (principal.getDpnlPrincipal().getSize().height - fPlanClase.getSize().height) / 2);
        
        fPlanClase.getBtnCancelarPC().addActionListener(a1 -> {
            fPlanClase.dispose();
        });
        
        iniciaPlanClase(planClaseMD, curso, silabo, unidadsilabo);
    }
    
    private void iniciaPlanClase(PlandeClasesMD planclase,CursoMD curso,SilaboMD silabo,UnidadSilaboMD unidadsilabo){
        lista_curso=CursosBDS.ConsultarCursoCarreraDocente(conexion, curso.getId());
        cargarCamposCursoCarreraDocente(lista_curso);
        
         lista_estrategiasSilabo=EstrategiasUnidadBD.cargarEstrategiasPlanClae(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        CargarEstrategiasUnidad(lista_estrategiasSilabo);
        
        for(EstrategiasUnidadMD est:lista_estrategiasSilabo){
            System.out.println(est.getIdEstrategiaUnidad()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<---------------------------");
        }
        
        lista_unidadsilabo=UnidadSilaboBD.consultarSilaboUnidades(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        cargarCamposUnidades(lista_unidadsilabo);
        
      lista_evaluacionesSilabo=EvaluacionSilaboBD.recuperarEvaluacionesUnidadSilabo(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        CargarEvaluacionesInstrumento(lista_evaluacionesSilabo);
                
       lista_plan=PlandeClasesBD.consultarPlanClaseObservacion(conexion, planclase.getId_plan_clases());
        cargarCampoObservacion(lista_plan);
        
        lista_tra_aut=TrabajoAutonomoBD.consultarTrabajoAutonomo(conexion, planclase.getId_plan_clases());
        cargarCampoAutonomo(lista_tra_aut);
        
       lista_recursoBD=RecursosPlanClasesBD.consultarRecursosPlanClase(conexion, planclase.getId_plan_clases());
        
       lista_recursoMD=RecursosPlanClasesBD.consultarRecursos(conexion);
       CargarRecursos(lista_recursoMD);
       
       lista_estrategias_metodologicas=EstrategiasMetodologicasBD.consultarEstrategiasMetologicas(conexion, planclase.getId_plan_clases());
        cargarEstrategiaPlanClase(lista_estrategias_metodologicas);
    }
     public  void cargarCamposCursoCarreraDocente(List<CursoMDS> lista){
        for (CursoMDS cursoMDS : lista) {
        fPlanClase.getTxtCarrera().setText(cursoMDS.getId_carrera().getNombre());
        fPlanClase.getTxtCarrera().setEnabled(false);
        fPlanClase.getTxtDocente().setText(cursoMDS.getId_persona().getPrimerNombre()+" "+cursoMDS.getId_persona().getPrimerApellido());
        fPlanClase.getTxtDocente().setEnabled(false);
        fPlanClase.getTxtAsignatura().setText(cursoMDS.getId_materia().getNombre());
        fPlanClase.getTxtAsignatura().setEnabled(false);
        fPlanClase.getTxtCod_Asignatura().setText(cursoMDS.getId_materia().getCodigo());
        fPlanClase.getTxtCod_Asignatura().setEnabled(false);
        fPlanClase.getTxtCicloParalelo().setText(cursoMDS.getCurso_nombre());
        fPlanClase.getTxtCicloParalelo().setEnabled(false);
        }
    }
     private void CargarEvaluacionesInstrumento(List<EvaluacionSilaboMD> lista){
        fPlanClase.getJlistInstrumentoEvaluacion().removeAll();
        modelo=new DefaultListModel();
        for (EvaluacionSilaboMD evaluacionSilaboMD : lista) {
            modelo.addElement(evaluacionSilaboMD.getInstrumento());
        }
        fPlanClase.getJlistInstrumentoEvaluacion().setModel(modelo);
         fPlanClase.getJlistInstrumentoEvaluacion().setEnabled(false);
    }
       public void cargarCamposUnidades(List<UnidadSilaboMD> lista_unidades){
        for (UnidadSilaboMD lista_unidad : lista_unidades) {
           fPlanClase.getTxrObjetivoPC().setText(lista_unidad.getObjetivoEspecificoUnidad());
           fPlanClase.getTxrObjetivoPC().setEnabled(false);
           
           fPlanClase.getTxrContenidosPC().setText(lista_unidad.getContenidosUnidad());
            fPlanClase.getTxrContenidosPC().setEnabled(false);
           
           fPlanClase.getTxtTituloUnidad().setText(lista_unidad.getTituloUnidad());
            fPlanClase.getTxtTituloUnidad().setEnabled(false);
            
           fPlanClase.getTxtDuracion().setText(String.valueOf(lista_unidad.getHorasDocenciaUnidad()+lista_unidad.getHorasPracticaUnidad()+" horas"));
           fPlanClase.getTxtDuracion().setEnabled(false);
           
           fPlanClase.getjDateChooserFechaInicioPC().setDate(Date.from(lista_unidad.getFechaInicioUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant()));
           fPlanClase.getjDateChooserFechaInicioPC().setEnabled(false);
           
           fPlanClase.getjDateChooserFechaFinPC().setDate(Date.from(lista_unidad.getFechaFinUnidad().atStartOfDay(ZoneId.systemDefault()).toInstant()));
           fPlanClase.getjDateChooserFechaFinPC().setEnabled(false);
           
           fPlanClase.getTxrResultadosAprendizaje().setText(lista_unidad.getResultadosAprendizajeUnidad());
           fPlanClase.getTxrResultadosAprendizaje().setEnabled(false);
           
           fPlanClase.getTxtUnidad().setText(String.valueOf(lista_unidad.getNumeroUnidad()));
           fPlanClase.getTxtUnidad().setEnabled(false);
           
           fPlanClase.getLbNumeroPlandeClase().setText("Unidad N°"+lista_unidad.getNumeroUnidad());
           
        }
    }
    
      private void CargarEstrategiasUnidad(List<EstrategiasUnidadMD> lista){
        fPlanClase.getCmbxEstrategiasPC().removeAllItems();
        for (EstrategiasUnidadMD estrategiasUnidadMD : lista) {
            fPlanClase.getCmbxEstrategiasPC().addItem(estrategiasUnidadMD.getIdEstrategia().getDescripcionEstrategia());
        }
    }
      
     private void CargarRecursos(List<RecursosPlanClasesMD> lista_recursoMD){    
     fPlanClase.getJlisRecursos().removeAll();
     DefaultListModel modeloRecursos = new DefaultListModel();
     fPlanClase.getJlisRecursos().setCellRenderer(new frmPlanClase.CheckListRenderer());
     fPlanClase.getJlisRecursos().setModel(modeloRecursos);
     
       for (RecursosPlanClasesMD lista_recurso_md : lista_recursoMD) {
           modeloRecursos.addElement(new frmPlanClase.CheckListItem(lista_recurso_md.getId_recursos().getNombre_recursos()));
       }
        for (int i = 0; i < fPlanClase.getJlisRecursos().getModel().getSize(); i++) {
                    CheckListItem item=(CheckListItem) fPlanClase.getJlisRecursos().getModel().getElementAt(i);
                    for (RecursosPlanClasesMD recursosPlanClasesMD : lista_recursoBD) {
                             if (modeloRecursos.get(i).toString().equals(recursosPlanClasesMD.getId_recursos().getNombre_recursos())) {
                            item.setSelected(true);
                        }
                       }
                   
                }
       
   }
     
     private void cargarCampoAutonomo(List<TrabajoAutonomoMD> lista_tra_aut){
         for (TrabajoAutonomoMD trabajoAutonomoMD : lista_tra_aut) {
             fPlanClase.getTxrTrabajoAutonomo().setText(trabajoAutonomoMD.getAutonomo_plan_descripcion());
         }
     }
     private void cargarCampoObservacion(List<PlandeClasesMD> lista_pla){
         for (PlandeClasesMD plandeClasesMD : lista_pla) {
             fPlanClase.getTxrObservacionesPc().setText(plandeClasesMD.getObservaciones());
         }
     }
     
     private void cargarEstrategiaPlanClase(List<EstrategiasMetodologicasMD> lista_estrategias_metodo){
         DefaultListModel modelo_Anticipacion = new DefaultListModel();
         DefaultListModel modelo_Construcciom = new DefaultListModel();
         DefaultListModel modelo_Consolidacion = new DefaultListModel();
         for (int i = 0; i < lista_estrategias_metodo.size(); i++) {
             if(lista_estrategias_metodo.get(i).getTipo_estrategias_metodologicas().equals("Anticipacion")){
                 modelo_Anticipacion.addElement(lista_estrategias_metodo.get(i).getId_estrategias_unidad().getIdEstrategia().getDescripcionEstrategia());
             }else if(lista_estrategias_metodo.get(i).getTipo_estrategias_metodologicas().equals("Construccion")){
                 modelo_Construcciom.addElement(lista_estrategias_metodo.get(i).getId_estrategias_unidad().getIdEstrategia().getDescripcionEstrategia());
             }else if(lista_estrategias_metodo.get(i).getTipo_estrategias_metodologicas().equals("Consolidacion")){
                modelo_Consolidacion.addElement(lista_estrategias_metodo.get(i).getId_estrategias_unidad().getIdEstrategia().getDescripcionEstrategia());
             }
             
         }
                 fPlanClase.getListAnticipacionPC().setModel(modelo_Anticipacion);
                 fPlanClase.getListConstruccionPC().setModel(modelo_Construcciom);
                 fPlanClase.getListConsolidacionPC().setModel(modelo_Consolidacion);
     }
}
