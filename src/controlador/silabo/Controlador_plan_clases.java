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
import java.util.List;
import java.util.Optional;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import modelo.ConexionBD;
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
    private List<EstrategiasMetodologicasMD> lista_estrategias_metodologicas;
    private List<RecursosPlanClasesMD> lista_recursoMD;
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
           lista_recursoMD=new ArrayList();
          fPlanClase.getTxt_buscarPCL().addKeyListener(new KeyAdapter(){
              @Override
            public void keyReleased(KeyEvent ke) {
           lista_recursos=RecursosBD.consultarRecursos(conexion,fPlanClase.getTxt_buscarPCL().getText());
           CargarRecursos(lista_recursos);

            }
          });
      
        fPlanClase.getBtnAgregarPC().addActionListener(ba->{
//            pasarElementospanel();
     agregarEstrategiasMetologicas();
        });
        fPlanClase.getBtnQuitarPC().addActionListener(qp->{
//            eliminarElementopanel();
        });
        fPlanClase.getJlisRecursos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event ){
                
                int index=fPlanClase.getJlisRecursos().locationToIndex(event.getPoint());
                CheckListItem item=(CheckListItem) fPlanClase.getJlisRecursos().getModel().getElementAt(index);
                        item.setSelected(!item.isSelected());
                        fPlanClase.getJlisRecursos().repaint(fPlanClase.getJlisRecursos().getCellBounds(index, index));
                        lista_recursos=RecursosBD.consultarRecursos(conexion,fPlanClase.getTxt_buscarPCL().getText());
                    
                    
                 
                   for (int i = 0; i < fPlanClase.getJlisRecursos().getModel().getSize(); i++) {
                    CheckListItem item2=(CheckListItem) fPlanClase.getJlisRecursos().getModel().getElementAt(i);
                    if(item2.isSelected()){
                        String recurso= fPlanClase.getJlisRecursos().getModel().getElementAt(i).toString();
                        Optional<RecursosMD> recursoSeleccionado=RecursosBD.consultarRecursos(conexion,fPlanClase.getTxt_buscarPCL().getText()).stream().
                                filter(r -> r.getNombre_recursos().equals(recurso)).findFirst();
                        recursos_planMD=new RecursosPlanClasesMD();
                        recursos_planMD.getId_recursos().setId_recurso(recursoSeleccionado.get().getId_recurso());
                        
                        lista_recursoMD.add(new RecursosPlanClasesMD(recursoSeleccionado.get()));
                        System.out.println(lista_recursoMD.size()+"------------------>>>>>>>>>>>>>><TAMAÑ0");
//                        lista_recursoMD.add(recursos_planMD);
                        System.out.println(recursoSeleccionado.get().getNombre_recursos()+"-<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    }
                }
         
                        
            }
         });
        fPlanClase.getBtmnGuardarPc().addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                  if(guardar_plan_de_clase()==true){
                      JOptionPane.showMessageDialog(fPlanClase, "Se guardó correctamente!");
                      fPlanClase.dispose();
                      
                  }else{
                       JOptionPane.showMessageDialog(fPlanClase, "Error al guardar", "Aviso", JOptionPane.ERROR_MESSAGE);
                       
                  }

            }
            
    });
        
        
     }
    
    private void IniciaPlanClase(SilaboMD silabo,CursoMD curso,UnidadSilaboMD unidadsilabo){
      lista_curso=CursosBDS.ConsultarCursoCarreraDocente(conexion, curso.getId_curso());
        cargarCamposCursoCarreraDocente(lista_curso);
        
      lista_unidadsilabo=UnidadSilaboBD.consultarSilaboUnidades(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        cargarCamposUnidades(lista_unidadsilabo);
        
     lista_estrategiasSilabo=EstrategiasUnidadBD.cargarEstrategiasPlanClae(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        CargarEstrategiasUnidad(lista_estrategiasSilabo);
        
        for (EstrategiasUnidadMD estU : lista_estrategiasSilabo) {
            System.out.println(estU.getIdEstrategia().getIdEstrategia()+" iddddddddddddddd _____________ estrategia");
        }
        
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
        fPlanClase.getBtnQuitarPC().setEnabled(true);
        modelo=new DefaultListModel();
        modelo2=new DefaultListModel();
        modelo3=new DefaultListModel();
        
        if (fPlanClase.getjScrollPane10().isShowing()) {
          String dato1=(String) fPlanClase.getCmbxEstrategiasPC().getSelectedItem();
            if (array.contains(dato1) || array2.contains(dato1) || array3.contains(dato1)) {
                JOptionPane.showMessageDialog(null, "Esta estrategia ya esta añadida");
                for (int i = 0; i < array.size(); i++) {
                modelo.addElement(array.get(i));
            }
            fPlanClase.getListAnticipacionPC().setModel(modelo);
            } else {
            array.add(dato1);
            for (int i = 0; i < array.size(); i++) {
                modelo.addElement(array.get(i));
            }
             fPlanClase.getListAnticipacionPC().setModel(modelo);
            }
        } else if(fPlanClase.getjScrollPane11().isShowing()){
           String dato2=(String) fPlanClase.getCmbxEstrategiasPC().getSelectedItem();
           if (array.contains(dato2) || array2.contains(dato2) || array3.contains(dato2)) {
                JOptionPane.showMessageDialog(null, "Esta estrategia ya esta añadida");
                for (int i = 0; i < array2.size(); i++) {
                 modelo2.addElement(array2.get(i));
            }
            fPlanClase.getListConstruccionPC().setModel(modelo2);
            } else {
           array2.add(dato2);
           for (int i = 0; i < array2.size(); i++) {
                modelo2.addElement(array2.get(i));
            }
           fPlanClase.getListConstruccionPC().setModel(modelo2);
           }
        }else if(fPlanClase.getjScrollPane9().isShowing()){
            String dato3=(String) fPlanClase.getCmbxEstrategiasPC().getSelectedItem();
            if (array.contains(dato3) || array2.contains(dato3) || array3.contains(dato3)) {
                JOptionPane.showMessageDialog(null, "Esta estrategia ya esta añadida");
                for (int i = 0; i < array3.size(); i++) {
                modelo3.addElement(array3.get(i));
                }
                fPlanClase.getListConsolidacionPC().setModel(modelo3);
            } else {
            array3.add(dato3);
            for (int i = 0; i < array3.size(); i++) {
                modelo3.addElement(array3.get(i));
            }
            }
            fPlanClase.getListConsolidacionPC().setModel(modelo3);
        }
    }
    private void eliminarElementopanel(){
        try{
        String indice;
            System.out.println(array);
            System.out.println(array2);
            System.out.println(array3);
        if (fPlanClase.getjScrollPane10().isShowing()) {
            if (fPlanClase.getListAnticipacionPC().getSelectedIndex()==-1) {
                JOptionPane.showMessageDialog(null, "Seleccione el elemneto a quitar");
            } else {
            indice=fPlanClase.getListAnticipacionPC().getSelectedValue();
                System.out.println(indice +" AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                System.out.println(indice);
                array.remove(indice);
                modelo.removeElement(indice);
                fPlanClase.getListAnticipacionPC().setModel(modelo);
//                for (int i = 0; i < array.size(); i++) {
//                    modelo.addElement(array.get(i));
//                }
            }
        } else if(fPlanClase.getjScrollPane11().isShowing()) {
            if (fPlanClase.getListConstruccionPC().getSelectedIndex()==-1) {
                JOptionPane.showMessageDialog(null, "Seleccione el elemneto a quitar");
            } else {
            indice=fPlanClase.getListConstruccionPC().getSelectedValue();
            System.out.println(indice + "FFFFFFFFFFFFFFFFFFFFFFF");
            array2.remove(indice);
            modelo2.removeElement(indice);
            fPlanClase.getListConstruccionPC().setModel(modelo2);
//                for (int i = 0; i < array2.size(); i++) {
//                    modelo2.addElement(array2.get(i));
//                }
            }
        }else if(fPlanClase.getjScrollPane9().isShowing()){
            if (fPlanClase.getListConsolidacionPC().getSelectedIndex()==-1) {
                JOptionPane.showMessageDialog(null, "Seleccione el elemneto a quitar");
            } else {
            indice=fPlanClase.getListConsolidacionPC().getSelectedValue();
            System.out.println(indice + "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            array3.remove(indice);
            modelo3.removeElement(indice);
            fPlanClase.getListConsolidacionPC().setModel(modelo3);
//                for (int i = 0; i < array3.size(); i++) {
//                    modelo3.addElement(array3.get(i));
//                }
            }
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No se puedo realizar esta accion");
        }
    }
     

    
   
    
    
    public boolean guardar_plan_de_clase(){
        plan_claseMD=new PlandeClasesMD(curso, unidadsilabo);
        plan_claseMD.getId_curso().setId_curso(curso.getId_curso());
        plan_claseMD.getId_unidad().setIdUnidad(unidadsilabo.getIdUnidad());
        plan_claseMD.setObservaciones(fPlanClase.getTxrObservacionesPc().getText());
        new PlandeClasesBD(conexion).insertarPlanClases(plan_claseMD);  
        insertarRecursosPlanClases();
        return true;
    }
    private void insertarRecursosPlanClases(){
     new RecursosPlanClasesBD(conexion).insertarRecursosPlanClases(recursos_planMD);
    }
    
    private void insertarEstrategiasMetodologicas(){
        
    }
     
    public EstrategiasUnidadMD estrategiaSeleccionado(){
        String item=fPlanClase.getCmbxEstrategiasPC().getSelectedItem().toString();
        Optional<EstrategiasUnidadMD> estrate_selecc=lista_estrategiasSilabo.stream().
                filter(r -> r.getIdEstrategia().getDescripcionEstrategia().equals(item)).findFirst();
        return estrate_selecc.get();
        
    }
    
    private void agregarEstrategiasMetologicas(){
        EstrategiasUnidadMD estrategia_selecc=estrategiaSeleccionado();
        
        boolean nuevo=true;
        int i=0;
        
        while (nuevo && i<lista_estrategiasSilabo.size()) {            
            if(lista_estrategiasSilabo.get(i).getIdEstrategia().getDescripcionEstrategia().equals(
                    estrategia_selecc.getIdEstrategia().getDescripcionEstrategia())){
                nuevo=false;
            }
            i++;
        }
        if(nuevo){
            EstrategiasMetodologicasMD em=new EstrategiasMetodologicasMD();
                    em.getId_estrategias_unidad().setIdEstrategiaUnidad(estrategia_selecc.getIdEstrategia().getIdEstrategia());
                    em.setTipo_estrategias_metodologicas("Anticipacion");
                 lista_estrategias_metodologicas.add(em);
        }
////        lista_estrategias_metodologicas.forEach((es) -> {
////            if(es.getTipo_estrategias_metodologicas().equals("Anticipacion")){
////                
////            }
//        });
        modelo1=new DefaultListModel();
        fPlanClase.getListAnticipacionPC().setModel(modelo1);
    }
    
}

