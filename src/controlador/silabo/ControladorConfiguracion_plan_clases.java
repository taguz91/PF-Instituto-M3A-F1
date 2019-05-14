package controlador.silabo;

import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import modelo.ConectarDB;
import modelo.ConexionBD;
import modelo.PlanClases.PlandeClasesBD;
import modelo.PlanClases.PlandeClasesMD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.materia.MateriaMD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.CursosBDS;
import modelo.silabo.MateriasBDS;
import modelo.silabo.PeriodoLectivoBDS;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmConfiguraciónPlanClase;

public class ControladorConfiguracion_plan_clases {
    private int id_silabo=-1;
    private int id_periodo_lectivo=-1;
    private final UsuarioBD usuario;
    private ConexionBD conexion;
    private frmConfiguraciónPlanClase frm_cong_PlanClase;
    private final VtnPrincipal vtnPrincipal;
    private List<SilaboMD> silabosDocente;
    private List<MateriaMD> materias_Silabos;
    private List<CarreraMD>  silabos_docente;
    private List<UnidadSilaboMD> unidadesSilabo;
    private List<PlandeClasesMD> lista_plan_clases;
    private List<CursoMD> cursosSilabo;
    private List<PeriodoLectivoMD> periodosCarrera;
    public ControladorConfiguracion_plan_clases(UsuarioBD usuario, VtnPrincipal vtnPrincipal, ConexionBD conexion) {
        this.usuario = usuario;
        this.vtnPrincipal = vtnPrincipal;
        this.conexion = conexion;
    }

    public void iniciarControlaador() {
        conexion.conectar();

        frm_cong_PlanClase = new frmConfiguraciónPlanClase();
        vtnPrincipal.getDpnlPrincipal().add(frm_cong_PlanClase);
        frm_cong_PlanClase.setTitle("CREAR UN PLAN DE CLASE");
        frm_cong_PlanClase.show();

        frm_cong_PlanClase.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - frm_cong_PlanClase.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - frm_cong_PlanClase.getSize().height) / 2);

        frm_cong_PlanClase.getBtn_cancelar().addActionListener((e) -> {
            frm_cong_PlanClase.dispose();
             ControladorCRUDPlanClase cP = new ControladorCRUDPlanClase(usuario, conexion, vtnPrincipal);
             cP.iniciaControlador();
        });
        frm_cong_PlanClase.getBtn_siguiente().addActionListener(a1 -> {
            if (validarPlanClaseExistente()==true) {
            frm_cong_PlanClase.dispose();
            Controlador_plan_clases cpc=new 
        Controlador_plan_clases(silabo_seleccionado(),cursos_seleccionado(),unidad_seleccionada(),usuario, vtnPrincipal, conexion);
            cpc.iniciaControlador();
            } else {
                JOptionPane.showMessageDialog(null, "YA EXISTE UN PLAN DE CLASE DE ESTA UNIDAD", "Aviso", JOptionPane.ERROR_MESSAGE);
            }
        });
          frm_cong_PlanClase.getCmb_carreras().addActionListener(a -> clickCmbCarreras());
           frm_cong_PlanClase.getCmb_silabos().addActionListener(a-> clickCmbSilabos());
           frm_cong_PlanClase.getCmb_Periodos().addActionListener(a-> clickComboPeriodos());
           estadoCmb_silbo(false);
           estadoCmb_cursoUnidDES(false);
           CARGAR_COMBO_CARRERAS();
           frm_cong_PlanClase.getBtn_siguiente().setEnabled(false);
           estado_comboPeriodos(false);
           }
           

   
     public List<SilaboMD> cargar_silabo(){
         String[] parametros = {frm_cong_PlanClase.getCmb_carreras().getSelectedItem().toString(), String.valueOf(usuario.getPersona().getIdPersona())};
         List<SilaboMD> silabosdocente= SilaboBD.consultarSilabo1(conexion, parametros);
         
         System.out.println(silabosdocente.get(0).getIdSilabo()+" CARGAR_SILABOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO_IDDDDDD");
         return silabosdocente;
         
    }
     private List<PeriodoLectivoMD> cargarPeriodos(){
          List<PeriodoLectivoMD> periodos=PeriodoLectivoBDS.consultarPeriodosPlanDeClse(conexion,frm_cong_PlanClase.getCmb_carreras().getSelectedItem().toString()); 
          return periodos;
     }
 
    private SilaboMD silabo_seleccionado(){
        Optional<SilaboMD> silaboSeleccionado = silabosDocente.stream().
                filter(s -> s.getIdMateria().getNombre().equals(frm_cong_PlanClase.getCmb_silabos().getSelectedItem().toString())).
                findFirst();

        return silaboSeleccionado.get();
    }
    private CursoMD cursos_seleccionado(){
        Optional<CursoMD> cursoSeleccionado=cursosSilabo.stream().
                filter(s -> s.getNombre().equals(frm_cong_PlanClase.getCmb_Cursos().getSelectedItem().toString())).
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
            frm_cong_PlanClase.getCmb_carreras().addItem("SELECCIONE UNA CARRERA!");
        silabos_docente.forEach((cmd) -> {
            frm_cong_PlanClase.getCmb_carreras().addItem(cmd.getNombre());
        });
        }
    }
    private void LLENAR_COMBO_SILABOS(List<MateriaMD> materias){
        frm_cong_PlanClase.getCmb_silabos().removeAllItems();
        if(materias !=null){
             frm_cong_PlanClase.getCmb_silabos().addItem("SELECCIONE UN SILABO!");
            materias_Silabos.forEach(m ->{
                frm_cong_PlanClase.getCmb_silabos().addItem(m.getNombre());
            });
            frm_cong_PlanClase.getCmb_silabos().setSelectedIndex(0);
        }
    }
    private void LLENAR_COMBO_UNIDADES(List<UnidadSilaboMD> unidadesSilabo){
        frm_cong_PlanClase.getCmb_unidades().removeAllItems();
        if (unidadesSilabo!=null) {
            unidadesSilabo.forEach(us -> {
                
                frm_cong_PlanClase.getCmb_unidades().addItem(String.valueOf(us.getNumeroUnidad()));
            });
        } else {
            System.out.println("siiiiiiiiiiiiiiiiiiiiiiiiimnnnnnnnnnnnnnn unidades");
        }
    }
    private void LLENA_COMBO_PERIODOS_CARRERA(List<PeriodoLectivoMD> periodos){
        frm_cong_PlanClase.getCmb_Periodos().removeAllItems();
        if (periodos!=null) {
            frm_cong_PlanClase.getCmb_Periodos().addItem("SELECCIONE UN PERIODO!");
            periodos.forEach(pl-> {
                frm_cong_PlanClase.getCmb_Periodos().addItem(pl.getNombre_PerLectivo());
            });
           frm_cong_PlanClase.getCmb_Periodos().setSelectedIndex(0);           
        }
    }
    private void LLENAR_COMBO_CURSOS(List<CursoMD> cursos){
        frm_cong_PlanClase.getCmb_Cursos().removeAllItems();
        if (cursos!=null) {
            cursos.forEach(cs->{
                frm_cong_PlanClase.getCmb_Cursos().addItem(String.valueOf(cs.getNombre()));
            });
        } else {
            System.out.println("NO tiene");
        }
    }
      
private void clickCmbCarreras(){
    int posC=frm_cong_PlanClase.getCmb_carreras().getSelectedIndex();
    if (posC>0) {
        estado_comboPeriodos(true);
         String carrera=silabos_docente.get(posC-1).getNombre();
        periodosCarrera=PeriodoLectivoBDS.consultarPeriodosPlanDeClse(conexion, carrera);
        LLENA_COMBO_PERIODOS_CARRERA(periodosCarrera);
        
    }else{
        estado_comboPeriodos(false);
    }
   } 
   private void clickCmbSilabos(){
       int posC=frm_cong_PlanClase.getCmb_carreras().getSelectedIndex();
       int posS=frm_cong_PlanClase.getCmb_silabos().getSelectedIndex();
       
       if(posS>0){
           estadoCmb_cursoUnidDES(true);
           String materia_silabo=materias_Silabos.get(posS -1).getNombre();
           unidadesSilabo=UnidadSilaboBD.consultar(conexion,getIdSilabo(),1 );
           LLENAR_COMBO_UNIDADES(unidadesSilabo);
           cursosSilabo=CursosBDS.Consultarcursos(conexion, usuario.getPersona().getIdPersona(), getid_periodo(), materia_silabo);
           LLENAR_COMBO_CURSOS(cursosSilabo);
           
           if (frm_cong_PlanClase.getCmb_Cursos().getItemCount()!=0) {
               frm_cong_PlanClase.getBtn_siguiente().setEnabled(true);
           } else {
              JOptionPane.showMessageDialog(null, "ESTA MATERIA NO ESTA ASIGNADA CON EL NUEVO PERIODO LECTIVO", "Aviso", JOptionPane.ERROR_MESSAGE);                      
               frm_cong_PlanClase.getBtn_siguiente().setEnabled(false);
           }
               
       }else{
           clickComboPeriodos();
           estadoCmb_cursoUnidDES(false);
           frm_cong_PlanClase.getBtn_siguiente().setEnabled(false);
       }
   }
   private void clickComboPeriodos(){
       int posC=frm_cong_PlanClase.getCmb_carreras().getSelectedIndex();
       int posP=frm_cong_PlanClase.getCmb_Periodos().getSelectedIndex();
       
       if (posP>0 && posC>0) {
          estadoCmb_silbo(true);
        String carrera=silabos_docente.get(posC-1).getNombre();
        String nombre_periodo=periodosCarrera.get(posP-1).getNombre_PerLectivo();
        materias_Silabos=MateriasBDS.consultarSilabo2(conexion, carrera, usuario.getPersona().getIdPersona(),nombre_periodo);
        LLENAR_COMBO_SILABOS(materias_Silabos);
           
           System.out.println(getid_periodo()+"----------------------------->>>>>>>>>>>>>>><<<<<<<ID PERIODO");
           
       } else {
           clickCmbCarreras();
           estadoCmb_silbo(false);
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
  private void estado_comboPeriodos(boolean estado){
       frm_cong_PlanClase.getCmb_Periodos().setEnabled(estado);
       frm_cong_PlanClase.getCmb_Periodos().removeAllItems();
      
  }
  private int getIdSilabo(){
      String silabo=frm_cong_PlanClase.getCmb_silabos().getSelectedItem().toString();
      silabosDocente=cargar_silabo();
      silabosDocente
              .stream()
                .filter(item -> item.getIdMateria().getNombre().equals(silabo))
                .collect(Collectors.toList())
                .forEach(obj -> {
                     id_silabo= obj.getIdSilabo();
                });
      return id_silabo;
  }
  
  private int getid_periodo(){
     String nombre_periodo=frm_cong_PlanClase.getCmb_Periodos().getSelectedItem().toString();
     periodosCarrera=cargarPeriodos();
     periodosCarrera
                .stream()
                .filter(item -> item.getNombre_PerLectivo().equals(nombre_periodo))
                .collect(Collectors.toList())
                .forEach(obj -> {
                     id_periodo_lectivo= obj.getId_PerioLectivo();
                });
     return id_periodo_lectivo;
  }
   
  private boolean validarPlanClaseExistente(){
      boolean valid=true;
      String[] parametros = {frm_cong_PlanClase.getCmb_carreras().getSelectedItem().toString(),String.valueOf(usuario.getPersona().getIdPersona()),  String.valueOf(getid_periodo())};
      lista_plan_clases=PlandeClasesBD.consultarPlanClaseExistente(conexion, parametros);
      for(PlandeClasesMD plmd:lista_plan_clases){
          
      if (Objects.equals(plmd.getId_unidad().getIdUnidad(), unidad_seleccionada().getIdUnidad())
             && plmd.getId_curso().getId()==cursos_seleccionado().getId() ) {
          valid=false;
      }
      }
      
  
  return valid;
  }
  
}
