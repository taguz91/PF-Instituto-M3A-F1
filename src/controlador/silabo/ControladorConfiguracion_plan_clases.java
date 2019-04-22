package controlador.silabo;

import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;
import modelo.ConexionBD;
import modelo.carrera.CarreraMD;
import modelo.curso.CursoMD;
import modelo.silabo.CarrerasBDS;
import modelo.silabo.CursosBDS;
import modelo.silabo.SilaboBD;
import modelo.silabo.SilaboMD;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmConfiguraciónPlanClase;

public class ControladorConfiguracion_plan_clases {

    private final UsuarioBD usuario;
    private ConexionBD conexion;
    private frmConfiguraciónPlanClase frm_cong_PlanClase;
    private final VtnPrincipal vtnPrincipal;
    private List<SilaboMD> silabosDocente;
    
    private List<CarreraMD>  silabos_docente;
    private List<UnidadSilaboMD> unidadesSilabo;
    private List<CursoMD> cursosSilabo;
    public ControladorConfiguracion_plan_clases(UsuarioBD usuario, VtnPrincipal vtnPrincipal) {
        this.usuario = usuario;
        this.vtnPrincipal = vtnPrincipal;
        this.conexion = new ConexionBD();
    }

    public void iniciarControlaador() {
        conexion.conectar();
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
            Controlador_plan_clases cpc=new Controlador_plan_clases(silabo_seleccionado(),cursos_seleccionado(),unidad_seleccionada(),usuario, vtnPrincipal);
            cpc.iniciaControlador();
        });
        
       silabos_docente=cargarComboCarreras();
       silabosDocente=cargar_silabo();
       
       frm_cong_PlanClase.getCmb_carreras().addActionListener((ActionEvent ae) -> {
           Optional<CarreraMD> carreraSeleccionada = silabos_docente.stream().
                    filter(c -> c.getNombre().equals(frm_cong_PlanClase.getCmb_carreras().getSelectedItem().toString())).
                    findFirst();
           silabosDocente=cargar_silabo(carreraSeleccionada.get().getId());
           System.out.println(silabosDocente.get(0).getIdSilabo()+"------------------------------------------------------silabo");
          
           Optional<SilaboMD> SilaboSeleccionado = silabosDocente.stream().
                    filter(c -> c.getIdMateria().getNombre().equals(frm_cong_PlanClase.getCmb_silabos().getSelectedItem().toString())).
                    findFirst();
            System.out.println(SilaboSeleccionado.get().getIdSilabo()+" ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
            unidadesSilabo=cargarUnidades(SilaboSeleccionado.get().getIdSilabo());
            
//            cursosSilabo=cargarCursosSilabos(SilaboSeleccionado.get().getIdSilabo(), usuario.getPersona().getIdPersona());
       });
             cursosSilabo=cargarCursosSilabos(144, usuario.getPersona().getIdPersona());
             
        frm_cong_PlanClase.getCmb_silabos().addActionListener((ActionEvent ae) -> {
            Optional<SilaboMD> SilaboSeleccionado =null;
            if(SilaboSeleccionado!=null){
            SilaboSeleccionado= silabosDocente.stream().
                    filter(c -> c.getIdMateria().getNombre().equals(frm_cong_PlanClase.getCmb_silabos().getSelectedItem().toString())).
                    findFirst();
            System.out.println(SilaboSeleccionado.get().getIdSilabo()+"iddddddddddddddddddddddddddddddd_silaboooooooooooooooooooooooooooooooooooooo");
            
            }else{
                System.out.println("NO HAY ID DE SILABO");
            }
       });
//        
        
        
      
//        frm_cong_PlanClase.getCmb_silabos().setSelectedIndex(0);
         frm_cong_PlanClase.getCmb_carreras().setSelectedIndex(0);
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
         System.out.println(silabosdocente.get(0).getIdSilabo()+" CARGAR_SILABOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO_IDDDDDD");
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
    
}
