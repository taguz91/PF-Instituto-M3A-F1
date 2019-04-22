spackage controlador.silabo;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import modelo.ConexionBD;
import modelo.curso.CursoMD;
import modelo.silabo.CursoMDS;
import modelo.silabo.CursosBDS;
import modelo.silabo.SilaboMD;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmPlanClase;

public class Controlador_plan_clases {

    private final UsuarioBD usuario;
    private ConexionBD conexion;
    private final VtnPrincipal vtnPrincipal;
    private frmPlanClase fPlanClase;
    private SilaboMD silabo;
    private CursoMD curso;
    private UnidadSilaboMD unidadsilabo;
    private List<SilaboMD> lista_silabo;
    private List<CursoMDS> lista_curso;
    private List<UnidadSilaboMD> lista_unidadsilabo;
    
    public Controlador_plan_clases(SilaboMD silabo,CursoMD curso,UnidadSilaboMD unidadsilabo,UsuarioBD usuario, VtnPrincipal vtnPrincipal) {
        this.silabo=silabo;
        this.curso=curso;
        this.unidadsilabo=unidadsilabo;
        this.usuario = usuario;
        this.vtnPrincipal = vtnPrincipal;
        this.conexion = new ConexionBD();
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
             ControladorConfiguracion_plan_clases ccpc=new ControladorConfiguracion_plan_clases(usuario, vtnPrincipal);
             ccpc.iniciarControlaador();
         });
           IniciaPlanClase(silabo, curso, unidadsilabo);
        
     }
    
    private void IniciaPlanClase(SilaboMD silabo,CursoMD curso,UnidadSilaboMD unidadsilabo){
      lista_curso=CursosBDS.ConsultarCursoCarreraDocente(conexion, curso.getId_curso());
        cargarCamposCursoCarreraDocente(lista_curso);
        
      lista_unidadsilabo=UnidadSilaboBD.consultarSilaboUnidades(conexion, silabo.getIdSilabo(), unidadsilabo.getNumeroUnidad());
        cargarCamposUnidades(lista_unidadsilabo);
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
           
           fPlanClase.getLbNumeroPlandeClase().setText("N°"+lista_unidad.getNumeroUnidad());
           
        }
    }
    
    
     
     
}

