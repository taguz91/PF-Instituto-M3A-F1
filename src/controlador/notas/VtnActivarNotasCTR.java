package controlador.notas;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import modelo.ConectarDB;
import modelo.alumno.AlumnoCursoBD;
import modelo.usuario.UsuarioBD;
import vista.notas.VtnActivarNotas;
import vista.notas.VtnNotasAlumnoCurso;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Alejandro
 */
public class VtnActivarNotasCTR {
    
    private final VtnPrincipal desktop;
    private final VtnActivarNotas vista;
    
    //Conexion
    private final ConectarDB conexion;
    
     //TABLA
    private DefaultTableModel tablaActivarNotas;

    public VtnActivarNotasCTR(VtnPrincipal desktop, VtnActivarNotas vista, ConectarDB conexion) {
        this.desktop = desktop;
        this.vista = vista;
        this.conexion = conexion;
    }

    //INIT
    public void Init() {
        
    }
    
     private void InitEventos() {
         

        tablaActivarNotas.addTableModelListener(new TableModelListener() {

            boolean active = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (!active && e.getType() == TableModelEvent.UPDATE) {

                    active = true;


                    active = false;
                }

            }

        });
     }

    //METODOS DE APOYO

    //EVENTOS
    
}
