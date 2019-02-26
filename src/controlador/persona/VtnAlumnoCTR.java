package controlador.persona;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.persona.AlumnoBD;
import modelo.persona.AlumnoMD;
import modelo.persona.PersonaMD;
import vista.persona.FrmAlumno;
import vista.persona.VtnAlumno;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class VtnAlumnoCTR {

    private final VtnPrincipal vtnPrin;
    private final VtnAlumno vtnAlumno;
    private FrmAlumno frmAlumno;
    private AlumnoBD bdAlumno;

    public VtnAlumnoCTR(VtnPrincipal vtnPrin, VtnAlumno vtnAlumno) {
        this.vtnPrin = vtnPrin;
        this.vtnAlumno = vtnAlumno;

        vtnPrin.getDpnlPrincipal().add(vtnAlumno);
        vtnAlumno.show();
        //Inicializamos la clase de alumno  
        bdAlumno = new AlumnoBD();
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
                buscaIncremental(vtnAlumno.getTxtBuscar().getText());
            }
        };

        ActionListener Cancelar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vtnAlumno.setVisible(false);
            }

        };

        llenarTabla();
        vtnAlumno.getTxtBuscar().addKeyListener(kl);
        vtnAlumno.getBtnEditar().addActionListener(e -> editarAlumno());
        vtnAlumno.getBtnIngresar().addActionListener(e -> abrirFrmAlumno());
    }

    public void abrirFrmAlumno() {
        frmAlumno = new FrmAlumno();
        FrmAlumnoCTR ctrFrmAlumno = new FrmAlumnoCTR(vtnPrin, frmAlumno);
        ctrFrmAlumno.iniciar();
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
                    + lista.get(i).getSegundoNombre(), i, 2);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerApellido()
                    + lista.get(i).getSegundoApellido(), i, 3);
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
                    + lista.get(i).getSegundoNombre(), i, 2);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getPrimerApellido()
                    + lista.get(i).getSegundoApellido(), i, 3);
            vtnAlumno.getTblAlumno().setValueAt(lista.get(i).getCorreo(), i, 4);
        }
    }

    public AlumnoBD capturarFila() {
        int i = vtnAlumno.getTblAlumno().getSelectedRow();
        if (i >= 0) {
            AlumnoBD alumno = new AlumnoBD();
            alumno = (AlumnoBD) bdAlumno.buscarPersona(Integer.valueOf(vtnAlumno.getTblAlumno().getValueAt(i, 0).toString()));
            return alumno;
        } else {
            JOptionPane.showMessageDialog(null, "Advertencia!! | Seleccione una fila");
            return null;
        }
    }

    public void editarAlumno() {
        FrmAlumnoCTR form = new FrmAlumnoCTR(vtnPrin, frmAlumno);
        AlumnoBD alumno = new AlumnoBD();
        if (capturarFila() == null) {
            JOptionPane.showMessageDialog(null, "No se puede Editar si no selecciona a un Alumno");
        } else {
            alumno = capturarFila();
            form.editar(alumno);
            vtnAlumno.setVisible(false);
            frmAlumno.setVisible(true);
        }
    }

}
