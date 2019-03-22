package controlador.persona;

import controlador.principal.VtnPrincipalCTR;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.accesos.AccesosBD;
import modelo.accesos.AccesosMD;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;
import modelo.persona.PersonaBD;
import modelo.persona.PersonaMD;
import modelo.usuario.RolMD;
import vista.persona.FrmAlumno;
import vista.persona.FrmPersona;
import vista.persona.VtnAlumno;
import vista.principal.VtnPrincipal;

public class VtnAlumnoCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnAlumno vtnAlumno;
    private final ConectarDB conecta;
    private final VtnPrincipalCTR ctrPrin;
    private final RolMD permisos;
    
    private FrmAlumno frmAlumno;
    private final AlumnoBD bdAlumno;

    public VtnAlumnoCTR(VtnPrincipal vtnPrin, VtnAlumno vtnAlumno, 
            ConectarDB conecta, VtnPrincipalCTR ctrPrin, RolMD permisos) {
        this.vtnPrin = vtnPrin;
        this.vtnAlumno = vtnAlumno;
        this.conecta = conecta;
        this.ctrPrin = ctrPrin;
        this.permisos = permisos;
        //Cambiamos el estado del cursos  
        vtnPrin.setCursor(new Cursor(3));
        ctrPrin.estadoCargaVtn("Alumnos");

        vtnPrin.getDpnlPrincipal().add(vtnAlumno);
        vtnAlumno.show();
        //Inicializamos la clase de alumno  
        bdAlumno = new AlumnoBD(conecta);
    }

    public void iniciar() {
        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                buscaIncremental(vtnAlumno.getTxtBuscar().getText().toUpperCase());
            }
        };

        ActionListener Cancelar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vtnAlumno.setVisible(false);
            }

        };

        ocultarAtributo();
        llenarTabla();
        vtnAlumno.getTxtBuscar().addKeyListener(kl);
        vtnAlumno.getBtnEliminar().addActionListener(e -> eliminarAlumno());
        vtnAlumno.getBtnEditar().addActionListener(e -> editarAlumno());
        vtnAlumno.getBtnIngresar().addActionListener(e -> abrirFrmAlumno());
         //Cuando termina de cargar todo se le vuelve a su estado normal.
        vtnPrin.setCursor(new Cursor(0));
        ctrPrin.estadoCargaVtnFin("Alumnos");
    }

    public void abrirFrmAlumno() {
        frmAlumno = new FrmAlumno();
        FrmAlumnoCTR ctrFrmAlumno = new FrmAlumnoCTR(vtnPrin, frmAlumno, conecta, ctrPrin, permisos);
        ctrFrmAlumno.iniciar();
    }

    public void ocultarAtributo() {
        modelo.estilo.TblEstilo.ocualtarID(vtnAlumno.getTblAlumno());
    }

    public void llenarTabla() {
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnAlumno.getTblAlumno().getModel();
        for (int i = vtnAlumno.getTblAlumno().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<PersonaMD> lista = bdAlumno.llenarTabla();
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            vtnAlumno.getTblAlumno().setValueAt(String.valueOf(lista.get(i).getIdPersona()), i, 0);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdentificacion(), i, 1);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerNombre()
                    + " " + lista.get(i).getSegundoNombre(), i, 2);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerApellido()
                    + " " + lista.get(i).getSegundoApellido(), i, 3);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getCorreo(), i, 4);
        }
        vtnAlumno.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
    }

    public void buscaIncremental(String aguja) {
        System.out.println(aguja);
        DefaultTableModel modelo_Tabla;
        modelo_Tabla = (DefaultTableModel) vtnAlumno.getTblAlumno().getModel();
        for (int i = vtnAlumno.getTblAlumno().getRowCount() - 1; i >= 0; i--) {
            modelo_Tabla.removeRow(i);
        }
        List<PersonaMD> lista = bdAlumno.capturarPersona(aguja);
        int columnas = modelo_Tabla.getColumnCount();
        for (int i = 0; i < lista.size(); i++) {
            modelo_Tabla.addRow(new Object[columnas]);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdPersona(), i, 0);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getIdentificacion(), i, 1);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerNombre()
                    + " " + lista.get(i).getSegundoNombre(), i, 2);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerApellido()
                    + " " + lista.get(i).getSegundoApellido(), i, 3);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getCorreo(), i, 4);
        }
        vtnAlumno.getLblResultados().setText(String.valueOf(lista.size()) + " Resultados obtenidos.");
    }

    public AlumnoMD capturarFila() {
        int i = vtnAlumno.getTblAlumno().getSelectedRow();
        if (i >= 0) {
            AlumnoMD alumno;
            alumno = bdAlumno.buscarPersona(Integer.valueOf(vtnAlumno.getTblAlumno().getValueAt(i, 0).toString()));
            return alumno;
        } else {
            return null;
        }
    }

    public void editarAlumno() {
        AlumnoMD al = capturarFila();
        if (al != null) {
            int seleccion = JOptionPane.showOptionDialog( null,"Seleccione una Opcion",
            "Selector de Opciones",JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,null,// null para icono por defecto.
            new Object[] { "Editar Datos Personales", "Editar Datos de Alumno"},"Editar Datos de Alumno");
            if(seleccion == 1){
                frmAlumno = new FrmAlumno();
                FrmAlumnoCTR ctrFrm = new FrmAlumnoCTR(vtnPrin, frmAlumno, conecta, ctrPrin, permisos);
                ctrFrm.iniciar();
                ctrFrm.editar(al);
                vtnAlumno.dispose();

            } else if(seleccion == 0){
                ConectarDB conectar = new ConectarDB("postgres","password","Persona");
                PersonaBD extraer = new PersonaBD(conectar);

                FrmPersona frmPersona = new FrmPersona();
                PersonaMD persona = new PersonaMD();
                persona = extraer.buscarPersona(al.getIdPersona());
                FrmPersonaCTR ctrPers = new FrmPersonaCTR(vtnPrin,frmPersona,conectar, ctrPrin);
                ctrPers.iniciar();
                ctrPers.editar(persona);
                vtnAlumno.dispose();

            }
        } else {
            JOptionPane.showMessageDialog(null, "Advertencia!! Seleccione una fila");
        }
        
    }

    public void eliminarAlumno() {
         AlumnoMD alumno = new AlumnoMD();
        if (capturarFila() == null) {
            JOptionPane.showMessageDialog(null, "No se puede Eliminar si no selecciona a un Alumno");
        } else {
            int dialog = JOptionPane.YES_NO_CANCEL_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar a este Estudiante? "," Eliminar Estudiante ",dialog);
            if(result == 0)
            {
                alumno = capturarFila();
                String observacion = JOptionPane.showInputDialog("¿Por que motivo elimina este alumno?");
                if(observacion != null){
                    alumno.setObservacion(observacion.toUpperCase());
                    if(bdAlumno.eliminarAlumno(alumno,alumno.getIdPersona()) == true){
                    JOptionPane.showMessageDialog(null, "Datos Eliminados Satisfactoriamente");
                    llenarTabla();
                    } else{
                        JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR AL ALUMNO");
                    }
                }
            }
        }
    }
    
    private void InitPermisos() {
        for (AccesosMD obj : AccesosBD.SelectWhereACCESOROLidRol(permisos.getId())) {

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

}
