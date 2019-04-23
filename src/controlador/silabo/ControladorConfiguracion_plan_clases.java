package controlador.silabo;

import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.CursosBDS;
import modelo.silabo.MateriasBDS;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmConfiguraciónPlanClase;

public class ControladorConfiguracion_plan_clases {
    private int id_silabo=-1;
    private final UsuarioBD usuario;
    private ConexionBD conexion;
    private frmConfiguraciónPlanClase frm_cong_PlanClase;
    private final VtnPrincipal vtnPrincipal;
    private List<SilaboMD> silabosDocente;
    private List<MateriaMD> materias_Silabos;
    private List<CarreraMD>  silabos_docente;
    private List<UnidadSilaboMD> unidadesSilabo;
    private List<CursoMD> cursosSilabo;
    public ControladorConfiguracion_plan_clases(UsuarioBD usuario, VtnPrincipal vtnPrincipal, ConexionBD conexion) {
        this.usuario = usuario;
        this.vtnPrincipal = vtnPrincipal;
        this.conexion = conexion;
    }

    public void iniciarControlaador() {
        conexion.conectar();

        // ControladorCRUDPlanClase ccpc= new ControladorCRUDPlanClase(plan, usuario);
        frm_cong_PlanClase = new frmConfiguraciónPlanClase();
        vtnPrincipal.getDpnlPrincipal().add(frm_cong_PlanClase);
        frm_cong_PlanClase.setTitle("Configuración Plan de Clases");
        frm_cong_PlanClase.show();

        frm_cong_PlanClase.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - frm_cong_PlanClase.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - frm_cong_PlanClase.getSize().height) / 2);

        frm_cong_PlanClase.getBtn_cancelar().addActionListener((e) -> {
            frm_cong_PlanClase.dispose();
        });
        frm_cong_PlanClase.getBtn_siguiente().addActionListener(a1 -> {
            frm_cong_PlanClase.dispose();
            Controlador_plan_clases cpc=new 
        Controlador_plan_clases(silabo_seleccionado(),cursos_seleccionado(),unidad_seleccionada(),usuario, vtnPrincipal, conexion);
            cpc.iniciaControlador();
        });
        
       silabos_docente=cargarComboCarreras();
      silabosDocente=cargar_silabo();
       
       frm_cong_PlanClase.getCmb_carreras().addActionListener((ActionEvent ae) -> {
           Optional<CarreraMD> carreraSeleccionada = silabos_docente.stream().
                    filter(c -> c.getNombre().equals(frm_cong_PlanClase.getCmb_carreras().getSelectedItem().toString())).
                    findFirst();
           silabosDocente=cargar_silabo(carreraSeleccionada.get().getId());
          
           Optional<SilaboMD> SilaboSeleccionado = null;
                   if (SilaboSeleccionado!=null) {
                    SilaboSeleccionado=silabosDocente.stream().
                    filter(c -> c.getIdMateria().getNombre().equals(frm_cong_PlanClase.getCmb_silabos().getSelectedItem().toString())).
                    findFirst();
                     unidadesSilabo=cargarUnidades(SilaboSeleccionado.get().getIdSilabo());
           }
            
//            cursosSilabo=cargarCursosSilabos(SilaboSeleccionado.get().getIdSilabo(), usuario.getPersona().getIdPersona());
       });
         cursosSilabo=cargarCursosSilabos(221, usuario.getPersona().getIdPersona());
             
        unidadesSilabo=cargarUnidades(221);
        frm_cong_PlanClase.getCmb_silabos().addActionListener((ActionEvent ae) -> {
            
            Optional<SilaboMD> SilaboSeleccionado =null;
            if(SilaboSeleccionado!=null){
            SilaboSeleccionado= silabosDocente.stream().
                    filter(c -> c.getIdMateria().getNombre().equals(frm_cong_PlanClase.getCmb_silabos().getSelectedItem().toString())).
                    findFirst();
            System.out.println(SilaboSeleccionado.get().getIdSilabo()+"iddddddddddddddddddddddddddddddd_silaboooooooooooooooooooooooooooooooooooooo");
            
            }
            else{
                System.out.println("NO HAY ID DE SILABO");
            }
       });
//        
        
        
      
//    frm_cong_PlanClase.getCmb_silabos().setSelectedIndex(0);
         frm_cong_PlanClase.getCmb_carreras().setSelectedIndex(0);
           
//           frm_cong_PlanClase.getCmb_carreras().addActionListener(a -> clickCmbCarreras());
////           frm_cong_PlanClase.getCmb_silabos().addActionListener(a-> clickCmbSilabos());
//           estadoCmb_silbo(false);
//           estadoCmb_cursoUnidDES(false);
//           CARGAR_COMBO_CARRERAS();
//           
//           cargarCursosSilabos(221,usuario.getPersona().getIdPersona());
//           cargarUnidades(221);
    }

    public List<CarreraMD> cargarComboCarreras() {
        List<CarreraMD> carrerasDocentes = CarrerasBDS.consultar(conexion, usuario.getUsername());
        carrerasDocentes.forEach((cmd) -> {
            frm_cong_PlanClase.getCmb_carreras().addItem(cmd.getNombre());
        });

        return carrerasDocentes;
    }

   
    public List<SilaboMD> cargar_silabo(int carrera){
        frm_cong_PlanClase.getCmb_silabos().removeAllItems();
         String[] parametros = {frm_cong_PlanClase.getCmb_carreras().getSelectedItem().toString(), String.valueOf(usuario.getPersona().getIdPersona())};
         List<SilaboMD> silabosdocente= SilaboBD.consultarSilabo1(conexion, parametros);
         for (SilaboMD smd : silabosdocente) {
            String estado = null;
            if (smd.getEstadoSilabo() == 0) {
                estado = "Por aprobar";
            }
            frm_cong_PlanClase.getCmb_silabos().addItem(smd.getIdMateria().getNombre());
        }
         return silabosdocente;
    }
     public List<SilaboMD> cargar_silabo(){
         String[] parametros = {frm_cong_PlanClase.getCmb_carreras().getSelectedItem().toString(), String.valueOf(usuario.getPersona().getIdPersona())};
         List<SilaboMD> silabosdocente= SilaboBD.consultarSilabo1(conexion, parametros);
         
         return silabosdocente;
         
    }
    
  
    public List<UnidadSilaboMD> cargarUnidades(int silabo){
        frm_cong_PlanClase.getCmb_unidades().removeAllItems();
        List<UnidadSilaboMD> unidades_silabo=UnidadSilaboBD.consultar(conexion, silabo);
        unidades_silabo.forEach((cmd) -> {
            frm_cong_PlanClase.getCmb_unidades().addItem(String.valueOf(cmd.getNumeroUnidad()));
        });
        return unidades_silabo;
    }
     
    public List<CursoMD> cargarCursosSilabos(int id_silabo,int id_persona){
            List<CursoMD> cursos_Silabo=CursosBDS.Consultarcursos(conexion, id_silabo,id_persona);
             cursos_Silabo.forEach((umd) -> {
                frm_cong_PlanClase.getCmb_Cursos().addItem(  umd.getCurso_nombre());
            });
             return cursos_Silabo;
    }
    private SilaboMD silabo_seleccionado(){
        Optional<SilaboMD> silaboSeleccionado = silabosDocente.stream().
                filter(s -> s.getIdMateria().getNombre().equals(frm_cong_PlanClase.getCmb_silabos().getSelectedItem().toString())).
                findFirst();

        return silaboSeleccionado.get();
    }
    
    private CursoMD cursos_seleccionado(){
        Optional<CursoMD> cursoSeleccionado=cursosSilabo.stream().
                filter(s -> s.getCurso_nombre().equals(frm_cong_PlanClase.getCmb_Cursos().getSelectedItem().toString())).
                findFirst();
        return cursoSeleccionado.get();
    }
    private UnidadSilaboMD unidad_seleccionada(){
        Optional<UnidadSilaboMD> unidadSeleccionada= unidadesSilabo.stream().
                filter(s -> s.getNumeroUnidad()==Integer.parseInt(frm_cong_PlanClase.getCmb_unidades().getSelectedItem().toString())).
                findFirst();
        return unidadSeleccionada.get();
    }
    
    
    
    private void CARGAR_COMBO_CARRERAS(){
        frm_cong_PlanClase.getCmb_carreras().removeAllItems();
        silabos_docente=CarrerasBDS.consultar(conexion, usuario.getUsername());
        if(silabosDocente!=null){
            JOptionPane.showMessageDialog(null, "No tiene carreras asignadas");
        }else{
            frm_cong_PlanClase.getCmb_carreras().addItem("Seleccione la carrera");
        silabos_docente.forEach((cmd) -> {
            frm_cong_PlanClase.getCmb_carreras().addItem(cmd.getNombre());
        });
        }
    }
    
    private void LLENAR_COMBO_SILABOS(List<MateriaMD> materias){
        frm_cong_PlanClase.getCmb_silabos().removeAllItems();
        if(materias !=null){
             frm_cong_PlanClase.getCmb_silabos().addItem("Seleccione un silabo");
            materias_Silabos.forEach(m ->{
                frm_cong_PlanClase.getCmb_silabos().addItem(m.getNombre());
            });
            frm_cong_PlanClase.getCmb_silabos().setSelectedIndex(0);
        }else{
            System.out.println("No tiene silabos en esta carrera");
        }
    }
    private void LLENAR_COMBO_UNIDADES(List<UnidadSilaboMD> unidadesSilabo){
        frm_cong_PlanClase.getCmb_unidades().removeAllItems();
        if (unidadesSilabo!=null) {
            unidadesSilabo.forEach(us -> {
                frm_cong_PlanClase.getCmb_unidades().addItem(String.valueOf(us.getNumeroUnidad()));
            });
            frm_cong_PlanClase.getCmb_unidades().removeAllItems();
        } else {
            System.out.println("siiiiiiiiiiiiiiiiiiiiiiiiimnnnnnnnnnnnnnn unidades");
        }
    }
      
private void clickCmbCarreras(){
    int posC=frm_cong_PlanClase.getCmb_carreras().getSelectedIndex();
    if (posC>0) {
        estadoCmb_silbo(true);
        String carrera=silabos_docente.get(posC-1).getNombre();
        materias_Silabos=MateriasBDS.consultarSilabo2(conexion, carrera, usuario.getPersona().getIdPersona());
        LLENAR_COMBO_SILABOS(materias_Silabos);
    }else{
        estadoCmb_silbo(false);
    }
   } 
   private void clickCmbSilabos(){
       int posC=frm_cong_PlanClase.getCmb_carreras().getSelectedIndex();
       int posS=frm_cong_PlanClase.getCmb_silabos().getSelectedIndex();
       if(posS>0){
           estadoCmb_cursoUnidDES(true);
           
           unidadesSilabo=UnidadSilaboBD.consultar(conexion,getIdSilabo() );
           LLENAR_COMBO_UNIDADES(unidadesSilabo);
               
       }else{
           clickCmbCarreras();
           estadoCmb_cursoUnidDES(false);
       }
   }

  private void estadoCmb_silbo(boolean estado){
      frm_cong_PlanClase.getCmb_silabos().setEnabled(estado);
       frm_cong_PlanClase.getCmb_silabos().removeAllItems();
  }
  private void estadoCmb_cursoUnidDES(boolean estado){
      frm_cong_PlanClase.getCmb_Cursos().setEnabled(estado);
      frm_cong_PlanClase.getCmb_Cursos().removeAllItems();
      frm_cong_PlanClase.getCmb_unidades().setEnabled(estado);
      frm_cong_PlanClase.getCmb_unidades().removeAllItems();
      
  }
  
  private int getIdSilabo(){
      String silabo=frm_cong_PlanClase.getCmb_silabos().getSelectedItem().toString();
      silabosDocente
              .stream()
                .filter(item -> item.getIdMateria().getNombre().equals(silabo))
                .collect(Collectors.toList())
                .forEach(obj -> {
                     id_silabo= obj.getIdSilabo();
                });
      return id_silabo;
  }
}
