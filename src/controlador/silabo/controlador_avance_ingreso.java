/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.silabo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.sound.midi.Soundbank;
import javax.swing.JOptionPane;
import modelo.AvanceSilabo.AvanceUnidades;
import modelo.AvanceSilabo.AvanceUnidadesDB;
import modelo.AvanceSilabo.SeguimientoSilabo;
import modelo.AvanceSilabo.SeguimientoSilaboDB;
import modelo.ConexionBD;
import modelo.curso.CursoMD;
import modelo.silabo.CursoMDS;
import modelo.silabo.CursosBDS;
import modelo.silabo.SilaboMD;
import modelo.unidadSilabo.UnidadSilaboBD;
import modelo.unidadSilabo.UnidadSilaboMD;
import modelo.usuario.UsuarioBD;
import vista.principal.VtnPrincipal;
import vista.silabos.frmAvanceSilabo;

/**
 *
 * @author HP
 */
public class controlador_avance_ingreso {

    private final UsuarioBD usuario = null;
    private ConexionBD conexion;
    VtnPrincipal vtnPrincipal;
    CursoMD curso;
    SilaboMD silabo;
    List<CursoMDS> lista_curso;
    frmAvanceSilabo avanceSi;
    private List<UnidadSilaboMD> unidadesSilabo;
    List<AvanceUnidades> UnidadesA = new ArrayList<AvanceUnidades>();
   
    String observacion;
    int id_u;
    String avancePorcentaje;
    int id_curso;
    SeguimientoSilaboDB avanceSiDB;
    AvanceUnidadesDB uniDB;

    public controlador_avance_ingreso(ConexionBD conexion, VtnPrincipal vtnPrincipal, CursoMD curso,
            SilaboMD silabo) {
        this.conexion = conexion;
        this.vtnPrincipal = vtnPrincipal;
        this.curso = curso;
        this.silabo = silabo;
        
    }

    public void init() {
        conexion.conectar();
        avanceSiDB=new SeguimientoSilaboDB(conexion);
        avanceSi = new frmAvanceSilabo();
        uniDB=new AvanceUnidadesDB(conexion);
        vtnPrincipal.getDpnlPrincipal().add(avanceSi);
        avanceSi.setTitle("CREAR UN AVANCE DE SILABO");
        avanceSi.show();
        avanceSi.setLocation((vtnPrincipal.getDpnlPrincipal().getSize().width - avanceSi.getSize().width) / 2,
                (vtnPrincipal.getDpnlPrincipal().getSize().height - avanceSi.getSize().height) / 2);

        muestraInformacion();
        avanceSi.getBntGuardar().addActionListener(e->guardaAvanceEnca());

        //ContenidosPorUnidad(unidadesSilabo);
    }

    public void muestraInformacion() {
        lista_curso = CursosBDS.ConsultarCursoCarreraDocente(conexion, curso.getId());
        cargarCampos(lista_curso);
        unidadesSilabo = UnidadSilaboBD.consultarUnidadesPlanClase(conexion, silabo.getIdSilabo());
        LLENAR_COMBO_UNIDADES(unidadesSilabo);
        ContenidosPorUnidad(unidadesSilabo);
        avanceSi.getCbxUnidad().addActionListener((e) -> {

            recupera();
            ContenidosPorUnidad(unidadesSilabo);
            devuelveUnidad();
            avanceSi.getTxrObservaciones().setText(null);
            avanceSi.getSpnCumplimiento().setValue(0);

        });

    }

    public void cargarCampos(List<CursoMDS> lista) {
        for (CursoMDS cursoMDS : lista) {
            avanceSi.getTxtCarrera().setText(cursoMDS.getId_carrera().getNombre());
            avanceSi.getTxtCarrera().setEnabled(false);
            avanceSi.getTxtDocente().setText(cursoMDS.getId_persona().getPrimerNombre() + " " + cursoMDS.getId_persona().getPrimerApellido());
            avanceSi.getTxtDocente().setEnabled(false);
            avanceSi.getTxtAsignatura().setText(cursoMDS.getId_materia().getNombre());
            avanceSi.getTxtAsignatura().setEnabled(false);

            avanceSi.getTxtParalelo().setText(cursoMDS.getCurso_nombre());
            avanceSi.getTxtParalelo().setEnabled(false);

            int numeroAlm = CursosBDS.numero(conexion, curso.getId());
            avanceSi.getTxtNumeroAlumnos().setText(String.valueOf(numeroAlm));
        }
    }

    public void guardaAvanceEnca() {
        boolean TipoRport=true;
        LocalDate date=null;
        SeguimientoSilabo avanceSiMo=new SeguimientoSilabo();
        String tipo_repor = avanceSi.getCbxTipoReporte().getSelectedItem().toString();
        if (!(tipo_repor.equalsIgnoreCase("Reporte Correspondiente a")) && avanceSi.getDchFechaEntrega().getDate()!=null ) {
            if (tipo_repor.equalsIgnoreCase("Interciclo")) {
                TipoRport = true;
            } else if (tipo_repor.equalsIgnoreCase("Fin de ciclo")) {
                TipoRport = false;
            }
            Date fecha=avanceSi.getDchFechaEntrega().getDate();
            date = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            avanceSiMo.setFecha_entrega(date);
            avanceSiMo.setId_curso(curso.getId());
            avanceSiMo.setEsInterciclo(TipoRport);
            avanceSiDB.insertaAvance(avanceSiMo);
            int id_avance_insert=avanceSiDB.ultimoAvance(curso.getId());
            
            System.out.println("id avance aqui---------------------------->"+id_avance_insert);
            for (int i = 0; i <UnidadesA.size() ; i++) {
                AvanceUnidades SA1 = new AvanceUnidades();
                SA1.setAvancePorcentaje(UnidadesA.get(i).getAvancePorcentaje());
                SA1.setId_avance(id_avance_insert);
                SA1.setId_unidad(UnidadesA.get(i).getId_unidad());
                SA1.setObservaciones(UnidadesA.get(i).getObservaciones());
                uniDB.insertaUnidadesAvance(SA1);
                System.out.println(UnidadesA.get(i).getId_unidad()+" "+UnidadesA.get(i).getAvancePorcentaje()+"  "+UnidadesA.get(i).getObservaciones());
            }
            JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Es necesario seleccionar una opción");
        }
    }

    private void LLENAR_COMBO_UNIDADES(List<UnidadSilaboMD> unidadesSilabo) {
        avanceSi.getCbxUnidad().removeAllItems();
        if (unidadesSilabo != null) {
            unidadesSilabo.forEach(us -> {

                avanceSi.getCbxUnidad().addItem(String.valueOf(us.getTituloUnidad()));

            });
        } else {
            System.out.println("siiiiiiiiiiiiiiiiiiiiiiiiimnnnnnnnnnnnnnn unidades");
        }
    }

    private void ContenidosPorUnidad(List<UnidadSilaboMD> unidadesSilabo) {

        String titulo = avanceSi.getCbxUnidad().getSelectedItem().toString();

        System.out.println(titulo);

        int id_unidad = 0;

        for (int i = 0; i < unidadesSilabo.size(); i++) {

            if (titulo.equals(unidadesSilabo.get(i).getTituloUnidad())) {
                id_u = unidadesSilabo.get(i).getIdUnidad();
                System.out.println(unidadesSilabo.get(i).getIdUnidad());

                avanceSi.getTxrContenidos().setText(unidadesSilabo.get(i).getContenidosUnidad());

            }

        }
    }

    public void devuelveUnidad() {
        if (UnidadesA.isEmpty()) {
            System.out.println("erro de array");
        } else {
            for (int j = 0; j < UnidadesA.size(); j++) {
                if (UnidadesA.get(j).getId_unidad() == id_u) {
                    avanceSi.getTxrObservaciones().setText(UnidadesA.get(j).getObservaciones());
                    avanceSi.getSpnCumplimiento().setValue(UnidadesA.get(j).getId_avance());
                }
            }

        }
    }

    public void recupera() {
         AvanceUnidades SA1 = new AvanceUnidades();

        if (avanceSi.getTxrObservaciones().getText().equals("")) {
            observacion = "N/A";
        } else {
            observacion = avanceSi.getTxrObservaciones().getText();
        }
        avancePorcentaje = avanceSi.getSpnCumplimiento().getValue().toString();

        System.out.println(observacion);
        SA1.setObservaciones(observacion);
        SA1.setId_unidad(id_u);
        SA1.setAvancePorcentaje(avancePorcentaje);
        System.out.println(SA1.getId_unidad());
        System.out.println(SA1.getObservaciones());
        System.out.println(SA1.getAvancePorcentaje());
        UnidadesA.add(SA1);
        

    }
}
