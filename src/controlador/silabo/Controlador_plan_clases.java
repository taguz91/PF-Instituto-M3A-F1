package controlador.silabo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.silabos.frmPlanClase;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import modelo.ConexionBD;
import modelo.EstrategiasMetodologicas.EstrategiasMetodologicasBD;
import modelo.EstrategiasMetodologicas.EstrategiasMetodologicasMD;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.PlanClases.RecursosBD;
import modelo.PlanClases.RecursosMD;
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
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmPlanClase.CheckListItem;
import vista.silabos.frmPlanClase.CheckListRenderer;


public class Controlador_plan_clases {
    
    private PlandeClasesBD plan_clase;
    private final UsuarioBD usuario;
    private PlandeClasesMD plan_claseMD;
    private ConexionBD conexion;
    private final VtnPrincipal vtnPrincipal;
    private frmPlanClase fPlanClase;
    private SilaboMD silabo;
    private CursoMD curso;
    private RecursosPlanClasesMD recursos_planMD;
    private EstrategiasMetodologicasMD estrate_metoMD;
    private List<EvaluacionSilaboMD> lista_evualacion_unidad;
    private List<EstrategiasMetodologicasMD> lista_estrategias_metodologicas_antici;
    private List<RecursosPlanClasesMD> lista_recursoMD;
    private List<RecursosPlanClasesMD> lista_recursoMD1;
    private UnidadSilaboMD unidadsilabo;
    private List<CursoMDS> lista_curso;
    private List<UnidadSilaboMD> lista_unidadsilabo;
    private List<EstrategiasUnidadMD> lista_estrategiasSilabo;
    private List<EvaluacionSilaboMD> lista_evaluacionesSilabo;
    private DefaultListModel modelo;
    
     ArrayList array_Anticipacion=new ArrayList();
    ArrayList array_Construccion=new ArrayList();
    ArrayList array_Consolidacion=new ArrayList();
    DefaultListModel modelo_anticipacion;
    DefaultListModel modelo_Construccion;
    DefaultListModel modelo_Consolidacion;
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
        System.out.println("-------------------------->>>>>>>>>>>>>>>IDDD_SILABO"+silabo.getIdSilabo()+" -------------- ID_UNIDAD"+unidadsilabo.getIdUnidad());
        conexion.conectar();
        fPlanClase = new frmPlanClase();
        vtnPrincipal.getDpnlPrincipal().add(fPlanClase);
        fPlanClase.setTitle(silabo.getIdMateria().getNombre());
        fPlanClase.show();
        fPlanClase.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - fPlanClase.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - fPlanClase.getSize().height) / 2);
         fPlanClase.getBtnCancelarPC().addActionListener(a1 -> {
             fPlanClase.dispose();
           ControladorConfiguracion_plan_clases cp=new ControladorConfiguracion_plan_clases(usuario, vtnPrincipal, conexion);
           cp.iniciarControlaador();
            
         });
           IniciaPlanClase(silabo, curso, unidadsilabo);
          
      
        fPlanClase.getBtnAgregarPC().addActionListener(ba->{
            agregarEstrategiasMetologicas();
        });
        fPlanClase.getBtnQuitarPC().addActionListener(qp->{
            eliminarEstrategiasMto();
        });
        fPlanClase.getJlisRecursos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event ){
                lista_recursoMD1=new ArrayList();
                int index=fPlanClase.getJlisRecursos().locationToIndex(event.getPoint());
                CheckListItem item=(CheckListItem) fPlanClase.getJlisRecursos().getModel().getElementAt(index);
                        item.setSelected(!item.isSelected());
                        fPlanClase.getJlisRecursos().repaint(fPlanClase.getJlisRecursos().getCellBounds(index, index));
                        lista_recursoMD=RecursosPlanClasesBD.consultarRecursos(conexion);
                    
                    
                 
                   for (int i = 0; i < fPlanClase.getJlisRecursos().getModel().getSize(); i++) {
                    CheckListItem item2=(CheckListItem) fPlanClase.getJlisRecursos().getModel().getElementAt(i);
                    if(item2.isSelected()){
                        String recurso= fPlanClase.getJlisRecursos().getModel().getElementAt(i).toString();
                        Optional<RecursosMD> recursoSeleccionado=RecursosBD.consultarRecursos(conexion).stream().
                                filter(r -> r.getNombre_recursos().equals(recurso)).findFirst();
                        
                        lista_recursoMD1.add(new RecursosPlanClasesMD(recursoSeleccionado.get()));
                       
                        System.out.println(recursoSeleccionado.get().getNombre_recursos()+"-<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    }
                }
         
                        
            }
         });
        fPlanClase.getBtmnGuardarPc().addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()==true) {
                    
                  if(guardar_plan_de_clase()==true){
                      fPlanClase.dispose();
                      JOptionPane.showMessageDialog(fPlanClase, "Se guardó correctamente!");
                       ControladorCRUDPlanClase cP = new ControladorCRUDPlanClase(usuario, conexion, vtnPrincipal);
                        cP.iniciaControlador();
                  }else{
                     JOptionPane.showMessageDialog(null, "Falló al guardar ", "Aviso", JOptionPane.ERROR_MESSAGE);
                      fPlanClase.dispose();
                      
                  }
                }else{
                     JOptionPane.showMessageDialog(null, "REVISE INFORMACION INCOMPLETA", "Aviso", JOptionPane.ERROR_MESSAGE);
                }

            }
            
    });
        lista_estrategias_metodologicas_antici=new ArrayList<>();

        
     }
    
    private void IniciaPlanClase(SilaboMD silabo,CursoMD curso,UnidadSilaboMD unidadsilabo){
      lista_curso=CursosBDS.ConsultarCursoCarreraDocente(conexion, curso.getId());
        cargarCamposCursoCarreraDocente(lista_curso);
        
      lista_unidadsilabo=UnidadSilaboBD.consultarSilaboUnidades(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        cargarCamposUnidades(lista_unidadsilabo);
        
     
        
        
      lista_evaluacionesSilabo=EvaluacionSilaboBD.recuperarEvaluacionesUnidadSilabo(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        CargarEvaluacionesInstrumento(lista_evaluacionesSilabo);
        
      lista_recursoMD=RecursosPlanClasesBD.consultarRecursos(conexion);
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
    
    
    
    private void CargarEvaluacionesInstrumento(List<EvaluacionSilaboMD> lista){
        fPlanClase.getJlistInstrumentoEvaluacion().removeAll();
        modelo=new DefaultListModel();
        for (EvaluacionSilaboMD evaluacionSilaboMD : lista) {
            modelo.addElement(evaluacionSilaboMD.getInstrumento());
        }
        fPlanClase.getJlistInstrumentoEvaluacion().setModel(modelo);
        fPlanClase.getJlistInstrumentoEvaluacion().setEnabled(false);
    }
    
    private void CargarRecursos(List<RecursosPlanClasesMD> lista_recursoMD){    
     fPlanClase.getJlisRecursos().removeAll();
     DefaultListModel modeloRecursos = new DefaultListModel();
     fPlanClase.getJlisRecursos().setCellRenderer(new CheckListRenderer());
     fPlanClase.getJlisRecursos().setModel(modeloRecursos);
       for (RecursosPlanClasesMD lista_recurso_md : lista_recursoMD) {
           modeloRecursos.addElement(new CheckListItem(lista_recurso_md.getId_recursos().getNombre_recursos()));
       }
       fPlanClase.getJlisRecursos().setModel(modeloRecursos);
       
   }
    
     
    public boolean guardar_plan_de_clase(){
        
        plan_claseMD=new PlandeClasesMD(curso, unidadsilabo);
        plan_claseMD.getId_curso().setId(curso.getId());
        plan_claseMD.getId_unidad().setIdUnidad(unidadsilabo.getIdUnidad());
        plan_claseMD.setObservaciones(fPlanClase.getTxrObservacionesPc().getText());
        plan_claseMD.setTrabajo_autonomo(fPlanClase.getTxrTrabajoAutonomo().getText());
        if (new PlandeClasesBD(conexion).insertarPlanClases(plan_claseMD)) {
        insertarRecursosPlanClases();
        insertarEstrategiasMetodologicas();
        return true;
         }else{
            JOptionPane.showMessageDialog(fPlanClase, "No se guardo revise");
            return false;
        }
        
        }
    private void insertarRecursosPlanClases(){
        plan_claseMD=PlandeClasesBD.consultarUltimoPlanClase(conexion,curso.getId(),unidadsilabo.getIdUnidad());
        plan_claseMD.setId_plan_clases(plan_claseMD.getId_plan_clases());
        for (RecursosPlanClasesMD recursoPlam : lista_recursoMD1) {
            recursoPlam.getId_plan_clases().setId_plan_clases(plan_claseMD.getId_plan_clases());
            RecursosPlanClasesBD rcp=new RecursosPlanClasesBD(conexion);
             rcp.insertarRecursosPlanClases2(recursoPlam, recursoPlam.getId_plan_clases().getId_plan_clases());
        }
        
    }
    
   
   
     
//    public EstrategiasUnidadMD estrategiaSeleccionado(){
//        String item=fPlanClase.getCmbxEstrategiasPC().getSelectedItem().toString();
//        Optional<EstrategiasUnidadMD> estrate_selecc=lista_estrategiasSilabo.stream().
//                filter(r -> r.getIdEstrategia().getDescripcionEstrategia().equals(item)).findFirst();
//        return estrate_selecc.get();
//    }
    
    private void agregarEstrategiasMetologicas(){
        modelo_anticipacion= new DefaultListModel();
        modelo_Construccion = new DefaultListModel();
        modelo_Consolidacion = new DefaultListModel();
        if (fPlanClase.getjScrollPane10().isShowing()) {//Anticipacion
            if (fPlanClase.getTxt_estrategias().getText().equals("")) {
                JOptionPane.showMessageDialog(fPlanClase,"Ingrese una estrategia", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                array_Anticipacion.add(fPlanClase.getTxt_estrategias().getText());
                for (int i = 0; i < array_Anticipacion.size(); i++) {
                    modelo_anticipacion.addElement(array_Anticipacion.get(i));
                }
                fPlanClase.getListAnticipacionPC().setModel(modelo_anticipacion);
                fPlanClase.getTxt_estrategias().setText("");
            }
            
        } else if(fPlanClase.getjScrollPane11().isShowing()){//Construccion
            if (fPlanClase.getTxt_estrategias().getText().equals("")) {
                JOptionPane.showMessageDialog(fPlanClase,"Ingrese una estrategia", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                array_Construccion.add(fPlanClase.getTxt_estrategias().getText());
                for (int i = 0; i < array_Construccion.size(); i++) {
                   modelo_anticipacion.addElement(array_Construccion.get(i));
                }
                fPlanClase.getListConstruccionPC().setModel(modelo_anticipacion);
                fPlanClase.getTxt_estrategias().setText("");
            }
           
        } else if(fPlanClase.getjScrollPane9().isShowing()){//Consolidacion
            if (fPlanClase.getTxt_estrategias().getText().equals("")) {
                JOptionPane.showMessageDialog(fPlanClase,"Ingrese una estrategia", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                array_Consolidacion.add(fPlanClase.getTxt_estrategias().getText());
                for (int i = 0; i < array_Consolidacion.size(); i++) {
                   modelo_anticipacion.addElement(array_Consolidacion.get(i));
                }
                fPlanClase.getListConsolidacionPC().setModel(modelo_anticipacion);
                fPlanClase.getTxt_estrategias().setText("");
            }
        }
    }
    private void eliminarEstrategiasMto(){
         try {
            String indice;
            String indice2;
            String indice3;
            
            if (fPlanClase.getjScrollPane10().isShowing()) {
                 if (fPlanClase.getListAnticipacionPC().getSelectedIndex()==-1) {
                    JOptionPane.showMessageDialog(fPlanClase,"Seleccione el elemneto a quitar", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    indice=fPlanClase.getListAnticipacionPC().getSelectedValue();
                    modelo_anticipacion.removeElement(indice);
                    array_Anticipacion.remove(indice);
                    fPlanClase.getListAnticipacionPC().setModel(modelo_anticipacion);
                    
                    recargarElemwentos();
                }
            }else if (fPlanClase.getjScrollPane11().isShowing()){
                if (fPlanClase.getListConstruccionPC().getSelectedIndex()==-1) {
                    JOptionPane.showMessageDialog(fPlanClase,"Seleccione el elemneto a quitar", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    indice2=fPlanClase.getListConstruccionPC().getSelectedValue();
                    modelo_Construccion.removeElement(indice2);
                    array_Construccion.remove(indice2);
                    fPlanClase.getListConstruccionPC().setModel(modelo_Construccion);
                    recargarElemwentos2();
                }
            }else if (fPlanClase.getjScrollPane9().isShowing()){
                if (fPlanClase.getListConsolidacionPC().getSelectedIndex()==-1) {
                    JOptionPane.showMessageDialog(fPlanClase,"Seleccione el elemneto a quitar", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    indice3=fPlanClase.getListConsolidacionPC().getSelectedValue();
                    modelo_Consolidacion.removeElement(indice3);
                    array_Consolidacion.remove(indice3);
                    fPlanClase.getListConsolidacionPC().setModel(modelo_Consolidacion);
                    
                    recargarElemwentos3();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(fPlanClase,"No se puede realizar esta acción!!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    public void recargarElemwentos(){
        modelo_anticipacion.removeAllElements();
        for (int i = 0; i < array_Anticipacion.size(); i++) {
            modelo_anticipacion.addElement(array_Anticipacion.get(i));
            
           
        }
    }
    
    public void recargarElemwentos2(){
        modelo_Construccion.removeAllElements();
        for (int i = 0; i < array_Construccion.size(); i++) {
            modelo_Construccion.addElement(array_Construccion.get(i));
        }
    }
    public void recargarElemwentos3(){
        modelo_Consolidacion.removeAllElements();
        for (int i = 0; i < array_Consolidacion.size(); i++) {
            modelo_Consolidacion.addElement(array_Consolidacion.get(i));
        }
    }
    
    
    private boolean validarCampos(){
         boolean valid=true;
        if(fPlanClase.getTxrTrabajoAutonomo().getText().length()==0){
            
            valid= false;
        }
        if(lista_recursoMD1==null ){
            valid=false;
        }
        if(fPlanClase.getListAnticipacionPC().getModel().getSize()<=0 && fPlanClase.getListConstruccionPC().getModel().getSize()<=0 &&
                fPlanClase.getListConsolidacionPC().getModel()
                        .getSize()<=0){
            valid=false;
        }
        

            

        return valid;
    
    }
    
      
    private void insertarEstrategiasMetodologicas(){

                         for (int j = 0; j < fPlanClase.getListAnticipacionPC().getModel().getSize(); j++) {
                           if(fPlanClase.getListAnticipacionPC().getModel().getSize()>0){
                          String nombre=fPlanClase.getListAnticipacionPC().getModel().getElementAt(j).toString();
                          lista_estrategias_metodologicas_antici.add(new EstrategiasMetodologicasMD("Anticipacion", nombre));
//                            Optional<EstrategiasUnidadMD> estrate_selecc=lista_estrategiasSilabo.stream().
//                             filter(r -> r.getIdEstrategia().getDescripcionEstrategia().equals(nombre)).findFirst();
//                             System.out.println(estrate_selecc.get().getIdEstrategiaUnidad()+"----------------------->>>>>><ID_ESTRATEGIA UNIDAD----------ANTICIPACION");
//                             System.out.println(estrate_selecc.get().getIdEstrategia().getDescripcionEstrategia()+"------->>>>>DESCRIPCION--------------ANTICIPACION");
//                             lista_estrategias_metodologicas_antici.add(new EstrategiasMetodologicasMD(estrate_selecc.get(), "Anticipacion") );
                             
                             System.out.println(lista_estrategias_metodologicas_antici.size()+"---------------><<<<<<<TAMAÑO ARRAY LIST-------------------ANTICIPACION");
                           }
                        }          
                         for (int j = 0; j < fPlanClase.getListConstruccionPC().getModel().getSize(); j++) {
                             if(fPlanClase.getListConstruccionPC().getModel().getSize()>0){
                            String nombre=fPlanClase.getListConstruccionPC().getModel().getElementAt(j).toString();
                            lista_estrategias_metodologicas_antici.add(new EstrategiasMetodologicasMD("Construccion", nombre));
//                            Optional<EstrategiasUnidadMD> estrate_selecc=lista_estrategiasSilabo.stream().
//                             filter(r -> r.getIdEstrategia().getDescripcionEstrategia().equals(nombre)).findFirst();
//                            
//                             System.out.println(estrate_selecc.get().getIdEstrategiaUnidad()+"----------------------->>>>>><ID_ESTRATEGIA UNIDAD-----CONSTRUCCION");
//                             System.out.println(estrate_selecc.get().getIdEstrategia().getDescripcionEstrategia()+"------->>>>>DESCRIPCION---------CONSTRUCCION");
//                             lista_estrategias_metodologicas_antici.add(new EstrategiasMetodologicasMD(estrate_selecc.get(), "Construccion") );
                             
                             System.out.println(lista_estrategias_metodologicas_antici.size()+"---------------><<<<<<<TAMAÑO ARRAY --------CONSTRUCCIOM");
                             }
                        }          
                         for (int j = 0; j < fPlanClase.getListConsolidacionPC().getModel().getSize(); j++) {
                             if(fPlanClase.getListConsolidacionPC().getModel().getSize()>0){
                              String nombre=fPlanClase.getListConsolidacionPC().getModel().getElementAt(j).toString();
                              lista_estrategias_metodologicas_antici.add(new EstrategiasMetodologicasMD("Construccion", nombre));
//                            Optional<EstrategiasUnidadMD> estrate_selecc=lista_estrategiasSilabo.stream().
//                             filter(r -> r.getIdEstrategia().getDescripcionEstrategia().equals(nombre)).findFirst();
//                             System.out.println(estrate_selecc.get().getIdEstrategiaUnidad()+"----------------------->>>>>><ID_ESTRATEGIA UNIDAD---------CONSOLIDACION");
//                             System.out.println(estrate_selecc.get().getIdEstrategia().getDescripcionEstrategia()+"------->>>>>DESCRIPCION---------------CONSOLIDACION");
//                             lista_estrategias_metodologicas_antici.add(new EstrategiasMetodologicasMD(estrate_selecc.get(), "Consolidacion") );
                             
                             System.out.println(lista_estrategias_metodologicas_antici.size()+"---------------><<<<<<<TAMAÑO ARRAY---------CONSOLIDACION");
                             }
                        }          
        
        plan_claseMD=PlandeClasesBD.consultarUltimoPlanClase(conexion,curso.getId(),unidadsilabo.getIdUnidad());
        plan_claseMD.setId_plan_clases(plan_claseMD.getId_plan_clases());
        for(EstrategiasMetodologicasMD em:lista_estrategias_metodologicas_antici){
            em.getId_plan_clases().setId_plan_clases(plan_claseMD.getId_plan_clases());
            EstrategiasMetodologicasBD embd= new EstrategiasMetodologicasBD(conexion);
              embd.insertarEstrategiasMetodologicas2(em, em.getId_plan_clases().getId_plan_clases());
        }

    }
}


