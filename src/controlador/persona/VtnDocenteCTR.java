package controlador.persona;

import controlador.docente.JDFinContratacionCTR;
import controlador.principal.DVtnCTR;
import controlador.principal.VtnPrincipalCTR;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.estilo.TblEstilo;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.docente.RolDocenteBD;
import modelo.docente.RolPeriodoBD;
import modelo.docente.RolPeriodoMD;
import modelo.periodolectivo.PeriodoLectivoBD;
import modelo.periodolectivo.PeriodoLectivoMD;
import modelo.persona.DocenteBD;
import modelo.persona.DocenteMD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.validaciones.TxtVBuscador;
import modelo.validaciones.Validar;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import vista.persona.FrmDocente;
import vista.persona.FrmPersona;
import vista.persona.VtnDocente;

/**
 *
 * @author Johnny
 */
public class VtnDocenteCTR extends DVtnCTR {

    //array de docente modelo
    //vector de titulos de la tabla en el iniciar
    // vtnMateriaCTR basarme en ese---->para el crud
    private final VtnDocente vtnDocente;
    private final DocenteBD docente;
    private final RolPeriodoBD rolPer;
    private final RolDocenteBD rolDoc;
    private final PeriodoLectivoBD prd;
    private DocenteMD d;
    //Lista de todos los periodos lectivos
    private ArrayList<PeriodoLectivoMD> periodos;
    private ArrayList<RolPeriodoMD> roles;
    private ArrayList<DocenteMD> docentesMD;
    private FrmDocente frmDocente;

    private PersonaMD perEditar;
    private final PersonaBD per;
    private final String tipoDocntes[] = {"Fin de Contrato"};

    public VtnDocenteCTR(VtnDocente vtnDocente,
            VtnPrincipalCTR ctrPrin) {
        super(ctrPrin);
        this.vtnDocente = vtnDocente;
        this.rolPer = new RolPeriodoBD(ctrPrin.getConecta());
        this.prd = new PeriodoLectivoBD(ctrPrin.getConecta());
        this.rolDoc = new RolDocenteBD(ctrPrin.getConecta());
        //Cambiamos el estado del cursos
        docente = new DocenteBD(ctrPrin.getConecta());
        per = new PersonaBD(ctrPrin.getConecta());
    }

    public void iniciar() {
        vtnDocente.getBtnReporteDocente().setEnabled(false);
        vtnDocente.getBtnReporteDocenteMateria().setEnabled(false);
        cargarCmbTipoDocentes();
        String[] titulo = {"Cedula", "Nombres Completos", "Celular", "Correo", "Tipo Contrato"};
        String[][] datos = {};

        mdTbl = TblEstilo.modelTblSinEditar(datos, titulo);
        vtnDocente.getTblDocente().setModel(mdTbl);
        TblEstilo.formatoTbl(vtnDocente.getTblDocente());
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 0, 85);
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 1, 250);
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 2, 90);
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 3, 230);
        TblEstilo.columnaMedida(vtnDocente.getTblDocente(), 4, 125);
        cargarDocentes();
        vtnDocente.getBtnEditar().addActionListener(e -> editar());
        vtnDocente.getBtnIngresar().addActionListener(e -> abrirFrmDocente());
        vtnDocente.getBtnEliminar().addActionListener(e -> eliminarDocente());
        vtnDocente.getBtnFinContratacion().addActionListener(e -> finContratacion());
        vtnDocente.getCbxDocentesEliminados().addActionListener(e -> cargarDocentes());
        cargarTipoDocentes();
        vtnDocente.getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String b = vtnDocente.getTxtBuscar().getText().toUpperCase().trim();
                if (e.getKeyCode() == 10) {
                    buscaIncremental(b);
                } else if (b.length() == 0) {
                    cargarDocentes();
                }
            }
        });
        vtnDocente.getTxtBuscar().addKeyListener(new TxtVBuscador(vtnDocente.getTxtBuscar(),
                vtnDocente.getBtnBuscar()));
        vtnDocente.getBtnhorasAsignadas().addActionListener(e -> botonReporteHorasAsignadas());
        vtnDocente.getBtnReporteDocente().addActionListener(e -> llamaReporteDocente());
        vtnDocente.getBtnAsignarRol().addActionListener(e -> asignarRolDocente());
        vtnDocente.getBtnReporteDocenteMateria().addActionListener(e -> botonReporteMateria());
        vtnDocente.getTblDocente().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                validarBotonesReportes();
            }
        });

        ctrPrin.agregarVtn(vtnDocente);
    }

    private void cargarCmbTipoDocentes() {

        vtnDocente.getCmbTipoDocente().removeAllItems();
        vtnDocente.getCmbTipoDocente().addItem("Todos");
        for (String t : tipoDocntes) {
            vtnDocente.getCmbTipoDocente().addItem(t);
        }
    }

    public void cargarTipoDocentes() {
        String tipo = vtnDocente.getCmbTipoDocente().getSelectedItem().toString();

        switch (tipo) {

            case "Fin de Contrato":
                docentesMD = docente.cargarDocentesFinContrato();
                llenarTabla(docentesMD);
                break;

            default:
                docentesMD = docente.cargarDocentes();
                llenarTabla(docentesMD);
                break;

        }

    }

    private void cargarDocentes() {
        if (vtnDocente.getCbxDocentesEliminados().isSelected()) {
            docentesMD = docente.cargarDocentesEliminados();
            llenarTabla(docentesMD);
            vtnDocente.getBtnEditar().setText("Habilitar");
            inhabilitarBotones();
        } else {
            docentesMD = docente.cargarDocentes();
            llenarTabla(docentesMD);
            vtnDocente.getBtnEditar().setText("Editar");
            habilitarBotones();
        }
    }

    public void llenarTabla(ArrayList<DocenteMD> docentesM) {
        mdTbl.setRowCount(0);
        if (docentesM != null) {
            docentesM.forEach(dc -> {
                Object[] valores = {dc.getCodigo(), dc.getPrimerApellido() + " "
                    + dc.getSegundoApellido() + " " + dc.getPrimerNombre()
                    + " " + dc.getSegundoNombre(),
                    dc.getCelular(), dc.getCorreo(), dc.getDocenteTipoTiempo()};
                mdTbl.addRow(valores);
            });
            vtnDocente.getLblResultados().setText(String.valueOf(docentesM.size()) + " Resultados obtenidos.");
        } else {
            vtnDocente.getLblResultados().setText("0 Resultados obtenidos.");
        }

    }

    public void abrirFrmDocente() {
        frmDocente = new FrmDocente();
        FrmDocenteCTR ctrFrmDocente = new FrmDocenteCTR(frmDocente, ctrPrin);
        ctrFrmDocente.iniciar();
        vtnDocente.dispose();
        ctrPrin.cerradoJIF();
    }

    public void buscaIncremental(String aguja) {
        if (Validar.esLetrasYNumeros(aguja)) {
            if (vtnDocente.getCbxDocentesEliminados().isSelected()) {
                docentesMD = docente.buscarEliminados(aguja);
                llenarTabla(docentesMD);
            } else {
                docentesMD = docente.buscar(aguja);
                llenarTabla(docentesMD);
            }

        }
    }

    public void editar() {
        posFila = vtnDocente.getTblDocente().getSelectedRow();
        if (!vtnDocente.getCbxDocentesEliminados().isSelected()) {
            if (posFila >= 0) {
                int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una Opcion",
                        "Selector de Opciones", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null,// null para icono por defecto.
                        new Object[]{"Editar Datos Personales", "Editar Datos de Docente"}, "Editar Datos de Docente");
                if (seleccion == 0) {
                    FrmPersona frmPersona = new FrmPersona();
                    FrmPersonaCTR ctrFrmPersona = new FrmPersonaCTR(frmPersona, ctrPrin);
                    ctrFrmPersona.iniciar();
                    perEditar = per.buscarPersona(docentesMD.get(posFila).getIdPersona());
                    ctrFrmPersona.editar(perEditar);
                    vtnDocente.dispose();
                    ctrPrin.cerradoJIF();
                } else {
                    if (seleccion == 1) {
                        FrmDocente frmDoc = new FrmDocente();
                        FrmDocenteCTR ctrFrm = new FrmDocenteCTR(frmDoc, ctrPrin);
                        ctrFrm.iniciar();
                        frmDoc.getBtnRegistrarPersona().setVisible(false);
                        //Le pasamos la persona de nuestro lista justo la persona seleccionada
                        if (docentesMD.get(posFila).getCodigo().length() == 10) {
                            frmDoc.getCmbTipoIdentificacion().setSelectedItem("CEDULA");
                            ctrFrm.habilitarComponentesDocente();
                            frmDoc.getTxtIdentificacion().setEnabled(false);
                        } else {
                            frmDoc.getCmbTipoIdentificacion().setSelectedItem("PASAPORTE");
                            ctrFrm.habilitarComponentesDocente();
                            frmDoc.getTxtIdentificacion().setEnabled(false);
                        }
                        frmDoc.getCmbTipoIdentificacion().setEnabled(false);

                        frmDoc.getBtnGuardar().setEnabled(true);

                        ctrFrm.editar(docente.buscarDocente(docentesMD.get(posFila).getIdDocente()));
                        //vtnDocente.getTblDocente().setVisible(false);
                        vtnDocente.dispose();
                        ctrPrin.cerradoJIF();
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA !");
            }
        } else {
            if (posFila >= 0) {
                d = docente.buscarDocenteInactivo(docentesMD.get(posFila).getCodigo());
                if (d != null) {
                    int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una Opcion",
                            "Selector de Opciones", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null,// null para icono por defecto.
                            new Object[]{"Activar Docente", "No Activar"}, "Cancelar");
                    if (seleccion == 1) {
                        cargarDocentes();
                        System.out.println("opcion 2");
                    } else if (seleccion == 0) {
                        docente.activarDocente(docentesMD.get(posFila).getIdDocente());
                        System.out.println(docente.activarDocente(docentesMD.get(posFila).getIdDocente()) + "METODO ACTIVAR");
                        JOptionPane.showMessageDialog(null, "SE ACTIVO EL DOCENTE !");
                        cargarDocentes();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA !");
            }
        }

    }

    private void InitPermisos() {
        for (AccesosMD obj : AccesosBD.SelectWhereACCESOROLidRol(ctrPrin.getRolSeleccionado().getId())) {

//            if (obj.getNombre().equals("USUARIOS-Agregar")) {
//                vtnCarrera.getBtnIngresar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-Editar")) {
//                vista.getBtnEditar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-Eliminar")) {
//                vista.getBtnEliminar().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-AsignarRoles")) {
//                vista.getBtnAsignarRoles().setEnabled(true);
//            }
//            if (obj.getNombre().equals("USUARIOS-VerRoles")) {
//                vista.getBtnVerRoles().setEnabled(true);
//            }
        }
    }

    public void eliminarDocente() {
        DocenteMD docentemd = new DocenteMD();
        posFila = vtnDocente.getTblDocente().getSelectedRow();
        if (posFila >= 0) {
            int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar un Docente? ", " Eliminar Docente ", dialog);
            if (result == 0) {
                String observacion = JOptionPane.showInputDialog("¿Por que motivo elimina este Docente?");
                if (observacion != null) {
                    docentemd.setEstado(observacion.toUpperCase());
                    if (docente.eliminarDocente(docentemd, docentesMD.get(posFila).getIdDocente()) == true) {
                        System.out.println(docentesMD.get(posFila).getIdDocente() + " " + docentesMD.get(posFila).getNombreCompleto());
                        JOptionPane.showMessageDialog(null, "Datos Eliminados Satisfactoriamente");
                        cargarDocentes();
                    } else {
                        JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR AL DOCENTE !");
                    }
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA !");
        }
    }

    public void llamaReporteDocente() {
        JasperReport jr;
        String path = "/vista/reportes/repDocentes.jasper";
        posFila = vtnDocente.getTblDocente().getSelectedRow();
        if (posFila >= 0) {
            try {
                Map parametro = new HashMap();
                parametro.put("cedula", docentesMD.get(posFila).getIdDocente());
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Docente");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "error" + ex);
            }
        } else {
            JOptionPane.showMessageDialog(frmDocente, "Seleecione una fila primero.");
        }

    }

    public void llamaReporteDocenteMateria() {
        JasperReport jr;
        String path = "/vista/reportes/repDocentesCarrera.jasper";
        posFila = vtnDocente.getTblDocente().getSelectedRow();
        if (posFila >= 0) {
            try {

                Map parametro = new HashMap();
                parametro.put("id", docentesMD.get(posFila).getIdDocente());
                System.out.println(parametro);
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Materias del Docente");

            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "error" + ex);
            }
        } else {
            JOptionPane.showMessageDialog(frmDocente, "Seleecione una fila primero.");
        }

    }

    public void botonReporteMateria() {
        int s = JOptionPane.showOptionDialog(vtnDocente,
                "Reporte de Materias del Docente\n"
                + "¿Elegir el tipo de Reporte?", "REPORTE MATERIAS",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Materias por Periodo", "Historial de Materias",
                    "Cancelar"}, "Historial de Materias");
        switch (s) {
            case 0:
                seleccionarPeriodo();
                break;
            case 1:
                llamaReporteDocenteMateria();
                break;
            default:
                break;
        }
    }

    public void seleccionarPeriodo() {
        periodos = prd.cargarPeriodos();
        ArrayList<String> nmPrd = new ArrayList();
        nmPrd.add("Seleccione");
        periodos.forEach(p -> {
            nmPrd.add(p.getNombre_PerLectivo());
        });
        Object np = JOptionPane.showInputDialog(null,
                "Lista de periodos lectivos", "Periodos lectivos",
                JOptionPane.QUESTION_MESSAGE, null,
                nmPrd.toArray(), "Seleccione");
        //Se es null significa que no selecciono nada
        if (np == null) {
            botonReporteMateria();
        } else if (np.equals("Seleccione")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un periodo lectivo.");
            seleccionarPeriodo();
        } else {
            int posPrd = nmPrd.indexOf(np);

            JasperReport jr;
            String path = "/vista/reportes/repDocenteCarreraPeriodo.jasper";
            posFila = vtnDocente.getTblDocente().getSelectedRow();
            try {
                Map parametro = new HashMap();
                parametro.put("idDocente", docentesMD.get(posFila).getIdDocente());
                parametro.put("periodo", np);
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Materias del Docente por Periodos Lectivos");

            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        }
    }

    public void validarBotonesReportes() {
        int selecTabl = vtnDocente.getTblDocente().getSelectedRow();
        if (selecTabl >= 0) {
            vtnDocente.getBtnReporteDocente().setEnabled(true);
            vtnDocente.getBtnReporteDocenteMateria().setEnabled(true);
        } else {
            vtnDocente.getBtnReporteDocente().setEnabled(false);
            vtnDocente.getBtnReporteDocenteMateria().setEnabled(false);
        }
    }

    private void finContratacion() {

        posFila = vtnDocente.getTblDocente().getSelectedRow();
        if (posFila >= 0) {
            System.out.println("Este es el ID: " + docentesMD.get(posFila).getIdDocente());
            JDFinContratacionCTR ctr = new JDFinContratacionCTR(ctrPrin, vtnDocente.getTblDocente().getValueAt(posFila, 0).toString(),
                    docente.buscarDocente(vtnDocente.getTblDocente().getValueAt(posFila, 0).toString()).getIdDocente());
            ctr.iniciar();

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila ");
        }

    }

    private void habilitarBotones() {
        vtnDocente.getBtnEliminar().setEnabled(true);
        vtnDocente.getBtnEditar().setEnabled(true);
        vtnDocente.getBtnIngresar().setEnabled(true);
        vtnDocente.getBtnFinContratacion().setEnabled(true);
    }

    private void inhabilitarBotones() {
        vtnDocente.getBtnEliminar().setEnabled(false);
        vtnDocente.getBtnIngresar().setEnabled(false);
        vtnDocente.getBtnFinContratacion().setEnabled(false);
    }
//se selecciona el periodo y dependiendo que boton escoja muestra diferentes reportes

    public void botonReporteHorasAsignadas() {
        int s = JOptionPane.showOptionDialog(vtnDocente,
                "Reporte de Materias del Docente\n"
                + "¿desea obtener reporte de horas semanales?", "REPORTE hORAS DOCENCIA SEMANAL",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Horas por Docente", "Horas por periodo",
                    "Cancelar"}, "Historial de Materias");
        switch (s) {
            case 0:
                seleccionarPeriodohoras();
                break;
            case 1:
                seleccionarPeriodohorasCARRERA();
                break;

            default:
                break;
        }

    }

    public void asignarRolDocente() {
        posFila = vtnDocente.getTblDocente().getSelectedRow();
        if (posFila >= 0) {
            periodos = prd.cargarPeriodos();
            ArrayList<String> nmPrd = new ArrayList();
            nmPrd.add("Seleccione");
            periodos.forEach(p -> {
                nmPrd.add(p.getNombre_PerLectivo());
            });
            Object np = JOptionPane.showInputDialog(null,
                    "Lista de periodos lectivos", "Periodos lectivos",
                    JOptionPane.QUESTION_MESSAGE, null,
                    nmPrd.toArray(), 0);

            System.out.println("Posicion: ");
            if (np == null) {
                JOptionPane.showMessageDialog(null, "Seleccione un periodo");
            } else {

                System.out.println("Este es el periodo: " + np.toString());
                int posPrd = nmPrd.indexOf(np);
                System.out.println("La posicion es: " + posPrd + " El nombre es: " + periodos.get(posPrd - 1).getNombre_PerLectivo());

                selecionarRol(periodos.get(posPrd - 1).getId_PerioLectivo());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila de la tabla");
        }
    }

    private void selecionarRol(int idPrd) {
        ArrayList<String> nmRol = new ArrayList();
        roles = rolPer.cargarRolesPorPeriodo(idPrd);
        nmRol.add("Seleccione");
        roles.forEach(r -> {
            nmRol.add(r.getNombre_rol());
        });
        Object nr = JOptionPane.showInputDialog(null,
                "Lista de roles por periodos", "Roles de Docente",
                JOptionPane.QUESTION_MESSAGE, null, nmRol.toArray(), 0);
        if (nr != null) {
            if (!nr.equals("Seleccione")) {
                int posRol = nmRol.indexOf(nr);
                insertarRolDocente(roles.get(posRol - 1));
            } else {
                JOptionPane.showMessageDialog(null, "Selleccione un rol");
                selecionarRol(idPrd);
            }
        }

    }

    public void insertarRolDocente(RolPeriodoMD rol) {
        System.out.println("Ya podemos ingresar el rol: " + rol);
        posFila = vtnDocente.getTblDocente().getSelectedRow();
        rolDoc.setIdDocente(docentesMD.get(posFila));
        rolDoc.setIdRolPeriodo(rol);
        if (rolDoc.InsertarRol() == true) {
            JOptionPane.showMessageDialog(null, "Datos grabados correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "Error en grabar los datos");
        }
    }

    //SELECCIONA LOS PERIODOS PARA EL REPORTE DE HORAS POR DOCENTE
    public void seleccionarPeriodohoras() {
        periodos = prd.cargarPeriodos();
        ArrayList<String> nmPrd = new ArrayList();
        nmPrd.add("Seleccione");
        periodos.forEach(p -> {
            nmPrd.add(p.getNombre_PerLectivo());
        });
        Object np = JOptionPane.showInputDialog(null,
                "Lista de periodos lectivos", "Periodos lectivos",
                JOptionPane.QUESTION_MESSAGE, null,
                nmPrd.toArray(), "Seleccione");
        //Se es null significa que no selecciono nada
        if (np == null) {
            botonReporteHorasAsignadas();
        } else if (np.equals("Seleccione")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un periodo lectivo.");
            seleccionarPeriodohoras();
        } else {
            int posPrd = nmPrd.indexOf(np);
            //Se le resta 1 porque al inicio se agrega uno mas
            JasperReport jr;
            String path = "/vista/reportes/repSesionClase.jasper";

            try {
                posFila = vtnDocente.getTblDocente().getSelectedRow();
                Map parametro = new HashMap();
                parametro.put("iddocente", docentesMD.get(posFila).getIdDocente());
                parametro.put("idperiodolectivo", np.toString());
                System.out.println(parametro);
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Horas de docencia Semanal");
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        }
    }

    //SELECCIONA LOS PERIODOS PARA EL REPORTE DE HORAS POR CARRERA
    public void seleccionarPeriodohorasCARRERA() {
        periodos = prd.cargarPeriodos();
        ArrayList<String> nmPrd = new ArrayList();
        nmPrd.add("Seleccione");
        periodos.forEach(p -> {
            nmPrd.add(p.getNombre_PerLectivo());
        });
        Object np = JOptionPane.showInputDialog(null,
                "Lista de periodos lectivos", "Periodos lectivos",
                JOptionPane.QUESTION_MESSAGE, null,
                nmPrd.toArray(), "Seleccione");
        if (np == null) {
            botonReporteHorasAsignadas();
        } else if (np.equals("Seleccione")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un periodo lectivo.");

            seleccionarPeriodohorasCARRERA();

        } else {
            int posPrd = nmPrd.indexOf(np);
            //Se le resta 1 porque al inicio se agrega uno mas

            JasperReport jr;
            String path = "/vista/reportes/repHorasClaseCarrera.jasper";
            try {
                posFila = vtnDocente.getTblDocente().getSelectedRow();
                Map parametro = new HashMap();
                //parametro.put("iddocente", docentesMD.get(posFila).getIdDocente());
                parametro.put("periodo_nombre", np.toString());
                System.out.println(parametro);
                jr = (JasperReport) JRLoader.loadObject(getClass().getResource(path));
                ctrPrin.getConecta().mostrarReporte(jr, parametro, "Reporte de Horas de docencia Semanal");

            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
            }
        }
    }

}
