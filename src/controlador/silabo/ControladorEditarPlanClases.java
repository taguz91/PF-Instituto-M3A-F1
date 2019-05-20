
package controlador.silabo;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import vista.silabos.frmPlanClase;




public class ControladorEditarPlanClases {
    private final UsuarioBD usuario;
    private frmPlanClase fPlanClase;
    private PlandeClasesMD planClaseMD;
    private VtnPrincipal principal;
    private ConexionBD conexion;
    private CursoMD curso;
    private SilaboMD silabo;
    private UnidadSilaboMD unidadsilabo;
    private PlandeClasesMD plan_claseMD;
    private List<CursoMDS> lista_curso;
    private List<PlandeClasesMD> lista_plan;
    private List<RecursosPlanClasesMD> lista_recursoMD;
     private List<RecursosPlanClasesMD> lista_recursoMD1;
    private List<RecursosPlanClasesMD> lista_recursoBD;
    private List<EstrategiasUnidadMD> lista_estrategiasSilabo;
    private List<UnidadSilaboMD> lista_unidadsilabo;
    private List<EvaluacionSilaboMD> lista_evaluacionesSilabo;
      private List<EstrategiasMetodologicasMD> lista_estrategias_metodologicas_antici;
    private List<EstrategiasMetodologicasMD> lista_estrategias_metodologicas;
    private List<EvaluacionSilaboMD> lista_evualacion_unidad;
    private DefaultListModel modelo;
    
    ArrayList array_Anticipacion=new ArrayList();
    ArrayList array_Construccion=new ArrayList();
    ArrayList array_Consolidacion=new ArrayList();
    DefaultListModel modelo_anticipacion;
    DefaultListModel modelo_Construccion;
    DefaultListModel modelo_Consolidacion;

    public ControladorEditarPlanClases(UsuarioBD usuario,PlandeClasesMD planClaseMD, VtnPrincipal principal, ConexionBD conexion, CursoMD curso, SilaboMD silabo, UnidadSilaboMD unidadsilabo) {
         this.usuario = usuario;
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
            ControladorCRUDPlanClase cP = new ControladorCRUDPlanClase(usuario, conexion, principal);
            cP.iniciaControlador();
        });
        fPlanClase.getBtnCancelarPC().setText("Cancelar");
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
                        
                        lista_recursoMD1.add(new RecursosPlanClasesMD(planClaseMD,recursoSeleccionado.get()));
                       
                        System.out.println(recursoSeleccionado.get().getNombre_recursos()+"-<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    }
                }
         
                        
            }
         });
         fPlanClase.getBtmnGuardarPc().addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validarCampos()==true){
                  if(actualizarPlanClase()==true){
                      fPlanClase.dispose();
                      JOptionPane.showMessageDialog(fPlanClase, "Se actualizó correctamente!");
                       ControladorCRUDPlanClase cP = new ControladorCRUDPlanClase(usuario, conexion, principal);
                       cP.iniciaControlador();
                  }else{
                      JOptionPane.showMessageDialog(null, "Falló al guardar", "Aviso", JOptionPane.ERROR_MESSAGE); 
                      fPlanClase.dispose();
                  }
                }else{
                      JOptionPane.showMessageDialog(null, "REVISE INFORMACION INCOMPLETA", "Aviso", JOptionPane.ERROR_MESSAGE);
                }

            }
            
    });        
        iniciaPlanClase(planClaseMD, curso, silabo, unidadsilabo);
          lista_estrategias_metodologicas_antici=new ArrayList<>();
    }
    
    private void iniciaPlanClase(PlandeClasesMD planclase,CursoMD curso,SilaboMD silabo,UnidadSilaboMD unidadsilabo){
        lista_curso=CursosBDS.ConsultarCursoCarreraDocente(conexion, curso.getId());
        cargarCamposCursoCarreraDocente(lista_curso);
        
         lista_estrategiasSilabo=EstrategiasUnidadBD.cargarEstrategiasPlanClae(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        CargarEstrategiasUnidad(lista_estrategiasSilabo);
        
        
        lista_estrategias_metodologicas_antici=EstrategiasMetodologicasBD.consultarEstrategiasMetologicas(conexion, planclase.getId_plan_clases());
        System.out.println(lista_estrategias_metodologicas_antici.size()+"---->>>tamaño desde la base de datos");
        lista_unidadsilabo=UnidadSilaboBD.consultarSilaboUnidades(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        cargarCamposUnidades(lista_unidadsilabo);
        
      lista_evaluacionesSilabo=EvaluacionSilaboBD.recuperarEvaluacionesUnidadSilabo(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        CargarEvaluacionesInstrumento(lista_evaluacionesSilabo);
                
       lista_plan=PlandeClasesBD.consultarPlanClaseObservacion(conexion, planclase.getId_plan_clases());
        cargarCampoObservacion(lista_plan);
        
        
        
       lista_recursoBD=RecursosPlanClasesBD.consultarRecursosPlanClase(conexion, planclase.getId_plan_clases());
       
        lista_recursoMD1=lista_recursoBD;
        
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
     
    
     private void cargarCampoObservacion(List<PlandeClasesMD> lista_pla){
         for (PlandeClasesMD plandeClasesMD : lista_pla) {
             fPlanClase.getTxrObservacionesPc().setText(plandeClasesMD.getObservaciones());
             fPlanClase.getTxrTrabajoAutonomo().setText(plandeClasesMD.getTrabajo_autonomo());
         }
     }
     
     private void cargarEstrategiaPlanClase(List<EstrategiasMetodologicasMD> lista_estrategias_metodo){

        modelo_anticipacion= new DefaultListModel();
        modelo_Construccion = new DefaultListModel();
        modelo_Consolidacion = new DefaultListModel(); 
         for (int i = 0; i < lista_estrategias_metodo.size(); i++) {
             if(lista_estrategias_metodo.get(i).getTipo_estrategias_metodologicas().equals("Anticipacion")){
                 modelo_anticipacion.addElement(lista_estrategias_metodo.get(i).getId_estrategias_unidad().getIdEstrategia().getDescripcionEstrategia());
                 
                 array_Anticipacion.add(lista_estrategias_metodo.get(i).getId_estrategias_unidad().getIdEstrategia().getDescripcionEstrategia());
                 
                 
             }else if(lista_estrategias_metodo.get(i).getTipo_estrategias_metodologicas().equals("Construccion")){
                 modelo_Construccion.addElement(lista_estrategias_metodo.get(i).getId_estrategias_unidad().getIdEstrategia().getDescripcionEstrategia());
                 
                 array_Construccion.add(lista_estrategias_metodo.get(i).getId_estrategias_unidad().getIdEstrategia().getDescripcionEstrategia());
                 
             }else if(lista_estrategias_metodo.get(i).getTipo_estrategias_metodologicas().equals("Consolidacion")){
                 
                modelo_Consolidacion.addElement(lista_estrategias_metodo.get(i).getId_estrategias_unidad().getIdEstrategia().getDescripcionEstrategia());
                array_Consolidacion.add(lista_estrategias_metodo.get(i).getId_estrategias_unidad().getIdEstrategia().getDescripcionEstrategia());
             }
             
         }
                 fPlanClase.getListAnticipacionPC().setModel(modelo_anticipacion);
                 fPlanClase.getListConstruccionPC().setModel(modelo_Construccion);
                 fPlanClase.getListConsolidacionPC().setModel(modelo_Consolidacion);
     }
     
     public boolean actualizarPlanClase(){
        new PlandeClasesBD(conexion).eliminarPlanClase(planClaseMD);
        
        plan_claseMD=new PlandeClasesMD(curso, unidadsilabo);
        plan_claseMD.getId_curso().setId(curso.getId());
        plan_claseMD.getId_unidad().setIdUnidad(unidadsilabo.getIdUnidad());
        plan_claseMD.setObservaciones(fPlanClase.getTxrObservacionesPc().getText());
        plan_claseMD.setTrabajo_autonomo(fPlanClase.getTxrTrabajoAutonomo().getText());
        new PlandeClasesBD(conexion).insertarPlanClases(plan_claseMD);  
        actualizarRecusosPlanClases();
        actulizarEstrategiasMetodologicas();
        return true;
     }
     
     
     private void actualizarRecusosPlanClases(){
         plan_claseMD=PlandeClasesBD.consultarUltimoPlanClase(conexion,curso.getId(),unidadsilabo.getIdUnidad());
        plan_claseMD.setId_plan_clases(plan_claseMD.getId_plan_clases());
        for (RecursosPlanClasesMD recursoPlam : lista_recursoMD1) {
            recursoPlam.getId_plan_clases().setId_plan_clases(plan_claseMD.getId_plan_clases());
            RecursosPlanClasesBD rcp=new RecursosPlanClasesBD(conexion);
             rcp.insertarRecursosPlanClases2(recursoPlam, recursoPlam.getId_plan_clases().getId_plan_clases());
        }
     }
     
      public EstrategiasUnidadMD estrategiaSeleccionado(){
        String item=fPlanClase.getCmbxEstrategiasPC().getSelectedItem().toString();
        Optional<EstrategiasUnidadMD> estrate_selecc=lista_estrategiasSilabo.stream().
                filter(r -> r.getIdEstrategia().getDescripcionEstrategia().equals(item)).findFirst();
        return estrate_selecc.get();
    }
     
    public void recargarElemwentos(){
        modelo_anticipacion.removeAllElements();
        for (int i = 0; i < array_Anticipacion.size(); i++) {
            modelo_anticipacion.addElement(array_Anticipacion.get(i));
            System.out.println(array_Anticipacion+"-----------------------<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<array_recargado_ANTICIPACION");
        }
    }
     private void agregarEstrategiasMetologicas(){
        modelo_anticipacion= new DefaultListModel();
        modelo_Construccion = new DefaultListModel();
        modelo_Consolidacion = new DefaultListModel();        
        if (fPlanClase.getjScrollPane10().isShowing()) {
            
            for (int i = 0; i < lista_estrategiasSilabo.size(); i++) {
                if(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia().equals(estrategiaSeleccionado().getIdEstrategia().getDescripcionEstrategia())){
                    if (array_Anticipacion.contains(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia()) ||
                            array_Construccion.contains(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia())||
                            array_Consolidacion.contains(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia())) {
                        JOptionPane.showMessageDialog(null, "Esta estrategia ya esta añadida");
                        for (int j = 0; j < array_Anticipacion.size(); j++) {
                            modelo_anticipacion.addElement(array_Anticipacion.get(j));
                        }
                         fPlanClase.getListAnticipacionPC().setModel(modelo_anticipacion);
                         
                    } else {
                        array_Anticipacion.add(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia());
                        for (int j = 0; j < array_Anticipacion.size(); j++) {
                            modelo_anticipacion.addElement(array_Anticipacion.get(j));
                            
                             
                        }
                         fPlanClase.getListAnticipacionPC().setModel(modelo_anticipacion);
                    }
                }
            }
        } else if(fPlanClase.getjScrollPane11().isShowing()){
            for (int i = 0; i < lista_estrategiasSilabo.size(); i++) {
                if(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia().equals(estrategiaSeleccionado().getIdEstrategia().getDescripcionEstrategia())){
                    if (array_Anticipacion.contains(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia()) ||
                            array_Construccion.contains(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia())||
                            array_Consolidacion.contains(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia())) {
                        JOptionPane.showMessageDialog(null, "Esta estrategia ya esta añadida");
                        for (int j = 0; j < array_Construccion.size(); j++) {
                            modelo_Construccion.addElement(array_Construccion.get(j));
                        }
                         fPlanClase.getListConstruccionPC().setModel(modelo_Construccion);
                    } else {
                        array_Construccion.add(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia());
                        for (int j = 0; j < array_Construccion.size(); j++) {
                            modelo_Construccion.addElement(array_Construccion.get(j));
                            
                        }
                         fPlanClase.getListConstruccionPC().setModel(modelo_Construccion);
                    }
                }
            }
            fPlanClase.getListConstruccionPC().setModel(modelo_Construccion);
        }else if(fPlanClase.getjScrollPane9().isShowing()){
            for (int i = 0; i < lista_estrategiasSilabo.size(); i++) {
                if(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia().equals(estrategiaSeleccionado().getIdEstrategia().getDescripcionEstrategia())){
                    if (array_Anticipacion.contains(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia()) ||
                            array_Construccion.contains(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia())||
                            array_Consolidacion.contains(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia())) {
                        JOptionPane.showMessageDialog(null, "Esta estrategia ya esta añadida");
                        for (int j = 0; j < array_Consolidacion.size(); j++) {
                            modelo_Consolidacion.addElement(array_Consolidacion.get(j));
                        }
                         fPlanClase.getListConsolidacionPC().setModel(modelo_Consolidacion);
                    } else {
                        array_Consolidacion.add(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia());
                        for (int j = 0; j < array_Consolidacion.size(); j++) {
                            modelo_Consolidacion.addElement(array_Consolidacion.get(j));
                            
                        }
                         fPlanClase.getListConsolidacionPC().setModel(modelo_Consolidacion);
                    }
                }
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
    
     private void actulizarEstrategiasMetodologicas(){
         for (int j = 0; j < fPlanClase.getListAnticipacionPC().getModel().getSize(); j++) {
                           if(fPlanClase.getListAnticipacionPC().getModel().getSize()>0){
                          String nombre=fPlanClase.getListAnticipacionPC().getModel().getElementAt(j).toString();
                            Optional<EstrategiasUnidadMD> estrate_selecc=lista_estrategiasSilabo.stream().
                             filter(r -> r.getIdEstrategia().getDescripcionEstrategia().equals(nombre)).findFirst();
                             System.out.println(estrate_selecc.get().getIdEstrategiaUnidad()+"----------------------->>>>>><ID_ESTRATEGIA UNIDAD----------ANTICIPACION");
                             System.out.println(estrate_selecc.get().getIdEstrategia().getDescripcionEstrategia()+"------->>>>>DESCRIPCION--------------ANTICIPACION");
                             lista_estrategias_metodologicas_antici.add(new EstrategiasMetodologicasMD(estrate_selecc.get(), "Anticipacion") );
                             
                             System.out.println(lista_estrategias_metodologicas_antici.size()+"---------------><<<<<<<TAMAÑO ARRAY LIST-------------------ANTICIPACION");
                           }
                        }          
                         for (int j = 0; j < fPlanClase.getListConstruccionPC().getModel().getSize(); j++) {
                             if(fPlanClase.getListConstruccionPC().getModel().getSize()>0){
                            String nombre=fPlanClase.getListConstruccionPC().getModel().getElementAt(j).toString();
                            Optional<EstrategiasUnidadMD> estrate_selecc=lista_estrategiasSilabo.stream().
                             filter(r -> r.getIdEstrategia().getDescripcionEstrategia().equals(nombre)).findFirst();
                            
                             System.out.println(estrate_selecc.get().getIdEstrategiaUnidad()+"----------------------->>>>>><ID_ESTRATEGIA UNIDAD-----CONSTRUCCION");
                             System.out.println(estrate_selecc.get().getIdEstrategia().getDescripcionEstrategia()+"------->>>>>DESCRIPCION---------CONSTRUCCION");
                             lista_estrategias_metodologicas_antici.add(new EstrategiasMetodologicasMD(estrate_selecc.get(), "Construccion") );
                             
                             System.out.println(lista_estrategias_metodologicas_antici.size()+"---------------><<<<<<<TAMAÑO ARRAY --------CONSTRUCCIOM");
                             }
                        }          
                         for (int j = 0; j < fPlanClase.getListConsolidacionPC().getModel().getSize(); j++) {
                             if(fPlanClase.getListConsolidacionPC().getModel().getSize()>0){
                              String nombre=fPlanClase.getListConsolidacionPC().getModel().getElementAt(j).toString();
                            Optional<EstrategiasUnidadMD> estrate_selecc=lista_estrategiasSilabo.stream().
                             filter(r -> r.getIdEstrategia().getDescripcionEstrategia().equals(nombre)).findFirst();
                             System.out.println(estrate_selecc.get().getIdEstrategiaUnidad()+"----------------------->>>>>><ID_ESTRATEGIA UNIDAD---------CONSOLIDACION");
                             System.out.println(estrate_selecc.get().getIdEstrategia().getDescripcionEstrategia()+"------->>>>>DESCRIPCION---------------CONSOLIDACION");
                             lista_estrategias_metodologicas_antici.add(new EstrategiasMetodologicasMD(estrate_selecc.get(), "Consolidacion") );
                             
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
}
