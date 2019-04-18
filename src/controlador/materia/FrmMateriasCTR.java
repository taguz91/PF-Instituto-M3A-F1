/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.materia;

import controlador.principal.VtnPrincipalCTR;
import modelo.ConectarDB;
import modelo.carrera.CarreraMD;
import modelo.materia.EjeFormacionMD;
import modelo.materia.MateriaBD;
import vista.materia.FrmMaterias;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Lina
 */
public class FrmMateriasCTR {

    private final VtnPrincipal vtnPrin;
    private final FrmMaterias frmMaterias;
    private final MateriaBD materiaBD;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;

    public FrmMateriasCTR(VtnPrincipal vtnPrin, FrmMaterias frmMaterias, ConectarDB conecta, VtnPrincipalCTR ctrPrin) {
        this.vtnPrin = vtnPrin;
        this.frmMaterias = frmMaterias;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.materiaBD = new MateriaBD(conecta);
        vtnPrin.getDpnlPrincipal().add(frmMaterias);
        frmMaterias.show();

    }

    public void iniciar() {

        frmMaterias.getBtnGuardar().addActionListener(e -> guardarMateria());
        frmMaterias.getBtnCancelar().addActionListener(e -> cancelar());
    }

    private void guardarMateria() {

        String materiaCarrera, materiaCodigo, materiaNombre, materiaCiclo, creditos,
                ejeFormacion, materiaTipo, categoria, tipoAcreditacion, horasDocencia,
                horasPracticas, horasPresenciales, horasAutoEstudio, totalHoras,
                objetivoGeneral, objetivoEspecifico, descripcionMateria,
                organizacionCurricular, campoFormacion;

        boolean materiaNucleo;
CarreraMD carrera = null;
EjeFormacionMD eje = null;

carrera.getId();
eje.getId();

       // materiaCarrera = frmMaterias.getCbCarrera().getSelectedItem().toString();
        materiaCodigo = frmMaterias.getTxtCodigoMateria().getText().trim().toUpperCase();
        materiaNombre = frmMaterias.getTxtNombreMateria().getText().trim().toUpperCase();
        materiaCiclo = frmMaterias.getTxtMateriaCiclo().getText().trim().toUpperCase();
        creditos = frmMaterias.getCbCategoria().getSelectedItem().toString();
        //ejeFormacion = frmMaterias.getCbEjeFormacion().getSelectedItem().toString();
        materiaTipo = frmMaterias.getCbMateriaTipo().getSelectedItem().toString();
        categoria = frmMaterias.getCbCategoria().getSelectedItem().toString();
        tipoAcreditacion = frmMaterias.getCbTipoAcreditacion().getSelectedItem().toString();
        horasDocencia = frmMaterias.getTxtHorasDocencia().getText().trim().toUpperCase();
        horasPracticas = frmMaterias.getTxtHorasPracticas().getText().trim().toUpperCase();
        horasPresenciales = frmMaterias.getTxtHorasPresenciales().getText().trim().toUpperCase();
        horasAutoEstudio = frmMaterias.getTxtHorasAutoEstudio().getText().trim().toUpperCase();
        totalHoras = frmMaterias.getTxtTotalHoras().getText().trim().toUpperCase();
        objetivoGeneral = frmMaterias.getTxtObjetivoGeneral().getText().trim().toUpperCase();
        objetivoEspecifico = frmMaterias.getTxtObjetivoEspecifico().getText().trim().toUpperCase();
        descripcionMateria = frmMaterias.getTxtDescripcionMateria().getText().trim().toUpperCase();
        organizacionCurricular = frmMaterias.getTxtOrganizacionCurricular().getText().trim().toUpperCase();
        campoFormacion = frmMaterias.getTxtCampoFormacion().getText().trim().toUpperCase();
        
        MateriaBD materia = new MateriaBD(conecta);
        
        materia.setCarrera(carrera);
        materia.setCodigo(materiaCodigo);
        materia.setNombre(materiaNombre);
        materia.setCiclo(Integer.parseInt(materiaCiclo));
        materia.setCreditos(Integer.parseInt(categoria));
        materia.setEje(eje);
       // materia.setTipo(materiaTipo);
        materia.setCategoria(categoria);
       // materia.setTipoAcreditacion(tipoAcreditacion);
        materia.setHorasDocencia(Integer.parseInt(horasDocencia));
        materia.setHorasPracticas(Integer.parseInt(horasPracticas));
        materia.setHorasPresenciales(Integer.parseInt(horasPresenciales));
        materia.setHorasAutoEstudio(Integer.parseInt(horasAutoEstudio));
        materia.setTotalHoras(Integer.parseInt(totalHoras));
        materia.setObjetivo(objetivoGeneral);
        materia.setObjetivoespecifico(objetivoEspecifico);
        materia.setDescripcion(descripcionMateria);
        materia.setOrganizacioncurricular(organizacionCurricular);
        materia.setMateriacampoformacion(campoFormacion);
        
        
        
        
        
        
        
    }

    private void cancelar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
