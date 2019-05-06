
package controlador.silabo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import modelo.ConexionBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.PlanClases.RecursosPlanClasesBD;
import modelo.PlanClases.RecursosPlanClasesMD;
import modelo.curso.CursoMD;
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
    private List<RecursosPlanClasesMD> lista_recursoMD;
    private List<EstrategiasUnidadMD> lista_estrategiasSilabo;
    private List<UnidadSilaboMD> lista_unidadsilabo;
    private List<EvaluacionSilaboMD> lista_evaluacionesSilabo;
    private DefaultListModel modelo;
    
    public ControladorEditarPlanClases(PlandeClasesMD planClaseMD,CursoMD curso, VtnPrincipal principal, ConexionBD conexion) {
        this.planClaseMD = planClaseMD;
        this.principal = principal;
        this.conexion = conexion;
        this.curso=curso;
    }
    public ControladorEditarPlanClases(CursoMD curso, VtnPrincipal principal, ConexionBD conexion) {
       
        this.principal = principal;
        this.conexion = conexion;
        this.curso=curso;
    }

    public ControladorEditarPlanClases(VtnPrincipal principal, ConexionBD conexion, CursoMD curso, SilaboMD silabo, UnidadSilaboMD unidadsilabo) {
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
        fPlanClase.getTxt_buscarPCL().addKeyListener(new KeyAdapter(){
              @Override
            public void keyReleased(KeyEvent ke) {
           lista_recursoMD=RecursosPlanClasesBD.consultarRecursos(conexion,fPlanClase.getTxt_buscarPCL().getText());
           CargarRecursos(lista_recursoMD);

            }
          });
        iniciaPlanClase(planClaseMD, curso, silabo, unidadsilabo);
    }
    
    private void iniciaPlanClase(PlandeClasesMD planclase,CursoMD curso,SilaboMD silabo,UnidadSilaboMD unidadsilabo){
        lista_curso=CursosBDS.ConsultarCursoCarreraDocente(conexion, curso.getId());
        cargarCamposCursoCarreraDocente(lista_curso);
        
         lista_estrategiasSilabo=EstrategiasUnidadBD.cargarEstrategiasPlanClae(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        CargarEstrategiasUnidad(lista_estrategiasSilabo);
        
        lista_unidadsilabo=UnidadSilaboBD.consultarSilaboUnidades(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        cargarCamposUnidades(lista_unidadsilabo);
        
      lista_evaluacionesSilabo=EvaluacionSilaboBD.recuperarEvaluacionesUnidadSilabo(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        CargarEvaluacionesInstrumento(lista_evaluacionesSilabo);
                
       lista_recursoMD=RecursosPlanClasesBD.consultarRecursos(conexion,fPlanClase.getTxt_buscarPCL().getText());
       CargarRecursos(lista_recursoMD);
        
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
       fPlanClase.getJlisRecursos().setModel(modeloRecursos);
       
   }
}
