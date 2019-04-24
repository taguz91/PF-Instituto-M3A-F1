package controlador.silabo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import modelo.ConexionBD;
import modelo.PlanClases.RecursosBD;
import modelo.PlanClases.RecursosMD;
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
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmGestionSilabo.CheckListItem;
import vista.silabos.frmGestionSilabo.CheckListRenderer;
import vista.silabos.frmPlanClase;

public class Controlador_plan_clases {

    private final UsuarioBD usuario;
    private ConexionBD conexion;
    private final VtnPrincipal vtnPrincipal;
    private frmPlanClase fPlanClase;
    private SilaboMD silabo;
    private CursoMD curso;
    private UnidadSilaboMD unidadsilabo;
    private List<CursoMDS> lista_curso;
    private List<UnidadSilaboMD> lista_unidadsilabo;
    private List<EstrategiasUnidadMD> lista_estrategiasSilabo;
    private List<EvaluacionSilaboMD> lista_evaluacionesSilabo;
    private DefaultListModel modelo;
    private List<RecursosMD> lista_recursos; 
    
    //JLIST 
     ArrayList array=new ArrayList();
    ArrayList array2=new ArrayList();
    ArrayList array3=new ArrayList();
    DefaultListModel modelo1;
    DefaultListModel modelo2;
    DefaultListModel modelo3;
    public Controlador_plan_clases(SilaboMD silabo,CursoMD curso,UnidadSilaboMD unidadsilabo,
            UsuarioBD usuario, VtnPrincipal vtnPrincipal, ConexionBD conexion) {
        this.silabo=silabo;
        this.curso=curso;
        this.unidadsilabo=unidadsilabo;
        this.usuario = usuario;
        this.vtnPrincipal = vtnPrincipal;
        this.conexion = conexion;
    }

    public void iniciaControlador() {
        conexion.conectar();
        fPlanClase = new frmPlanClase();
        vtnPrincipal.getDpnlPrincipal().add(fPlanClase);
        fPlanClase.setTitle("Plan de Clases");
        fPlanClase.show();
        fPlanClase.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - fPlanClase.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - fPlanClase.getSize().height) / 2);
         fPlanClase.getBtnCancelarPC().addActionListener(a1 -> {
             fPlanClase.dispose();
           ControladorConfiguracion_plan_clases cp=new ControladorConfiguracion_plan_clases(usuario, vtnPrincipal, conexion);
           cp.iniciarControlaador();
            
         });
           IniciaPlanClase(silabo, curso, unidadsilabo);
          fPlanClase.getTxt_buscarPCL().addKeyListener(new KeyAdapter(){
              @Override
            public void keyReleased(KeyEvent ke) {
           lista_recursos=RecursosBD.consultarRecursos(conexion,fPlanClase.getTxt_buscarPCL().getText());
           CargarRecursos(lista_recursos);

            }
          });
      
        fPlanClase.getBtnAgregarPC().addActionListener(ba->{
            pasarElementospanel();
        });
        fPlanClase.getBtnQuitarPC().addActionListener(qp->{
            eliminarElementopanel();
        });
     }
    
    private void IniciaPlanClase(SilaboMD silabo,CursoMD curso,UnidadSilaboMD unidadsilabo){
      lista_curso=CursosBDS.ConsultarCursoCarreraDocente(conexion, curso.getId_curso());
        cargarCamposCursoCarreraDocente(lista_curso);
        
      lista_unidadsilabo=UnidadSilaboBD.consultarSilaboUnidades(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        cargarCamposUnidades(lista_unidadsilabo);
        
     lista_estrategiasSilabo=EstrategiasUnidadBD.cargarEstrategiasPlanClae(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        CargarEstrategiasUnidad(lista_estrategiasSilabo);
        
      lista_evaluacionesSilabo=EvaluacionSilaboBD.recuperarEvaluacionesUnidadSilabo(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        CargarEvaluacionesInstrumento(lista_evaluacionesSilabo);
        
      lista_recursos=RecursosBD.consultarRecursos(conexion,fPlanClase.getTxt_buscarPCL().getText());
      CargarRecursos(lista_recursos);
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
    private void CargarEvaluacionesInstrumento(List<EvaluacionSilaboMD> lista){
        fPlanClase.getJlistInstrumentoEvaluacion().removeAll();
        modelo=new DefaultListModel();
        for (EvaluacionSilaboMD evaluacionSilaboMD : lista) {
            modelo.addElement(evaluacionSilaboMD.getInstrumento());
        }
        fPlanClase.getJlistInstrumentoEvaluacion().setModel(modelo);
    }
 
   private void CargarRecursos(List<RecursosMD> lista_recursos){    
     fPlanClase.getJlisRecursos().removeAll();
     DefaultListModel modeloRecursos = new DefaultListModel();
     fPlanClase.getJlisRecursos().setCellRenderer(new CheckListRenderer());
     fPlanClase.getJlisRecursos().setModel(modeloRecursos);
       for (RecursosMD lista_recurso : lista_recursos) {
           modeloRecursos.addElement(new CheckListItem(lista_recurso.getNombre_recursos()));
       }
       fPlanClase.getJlisRecursos().setModel(modeloRecursos);
   }
    
   private void pasarElementospanel(){
        modelo=new DefaultListModel();
        modelo2=new DefaultListModel();
        modelo3=new DefaultListModel();
        if (fPlanClase.getjScrollPane10().isShowing()) {
          fPlanClase.getListAnticipacionPC().setModel(modelo);
          String dato1=(String) fPlanClase.getCmbxEstrategiasPC().getSelectedItem();
          array.add(dato1);
            for (int i = 0; i < array.size(); i++) {
                modelo.addElement(array.get(i));
            }
        } else if(fPlanClase.getjScrollPane11().isShowing()){
           fPlanClase.getListConstruccionPC().setModel(modelo2);
           String dato2=(String) fPlanClase.getCmbxEstrategiasPC().getSelectedItem();
           array2.add(dato2);
           for (int i = 0; i < array2.size(); i++) {
                modelo2.addElement(array2.get(i));
            }
        }else if(fPlanClase.getjScrollPane9().isShowing()){
            fPlanClase.getListConsolidacionPC().setModel(modelo3);
            String dato3=(String) fPlanClase.getCmbxEstrategiasPC().getSelectedItem();
            array3.add(dato3);
            for (int i = 0; i < array3.size(); i++) {
                modelo3.addElement(array3.get(i));
            }
        }
    }
    private void eliminarElementopanel(){
        int indice;
        if (fPlanClase.getjScrollPane10().isShowing()) {
            indice=fPlanClase.getListAnticipacionPC().getSelectedIndex();
            modelo.remove(indice);
        } else if(fPlanClase.getjScrollPane11().isShowing()) {
            indice=fPlanClase.getListConstruccionPC().getSelectedIndex();
            modelo2.remove(indice);
        }else if(fPlanClase.getjScrollPane9().isShowing()){
            indice=fPlanClase.getListConsolidacionPC().getSelectedIndex();
            modelo3.remove(indice);
        }
    }
}

