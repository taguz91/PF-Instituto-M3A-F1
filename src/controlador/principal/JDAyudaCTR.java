package controlador.principal;

import controlador.estilo.CambioPnlCTR;
import javax.swing.JLabel;
import vista.principal.JDAyuda;
import vista.principal.PnlAyuda;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class JDAyudaCTR {

    private final JDAyuda vtnAyuda;
    private final PnlAyuda pnlAyuda;
    private JLabel lbl;

    String[][] G23 = {{"Busqueda en Ventanas", "Se buscara automaticamente, despues de ingresar mas de 3 caracteres."},
    {"Validaciones", "Si al ingresar texto se le remarca el cuadro de rojo es debido a que ingreso un caracter no valido."},
    {"Inicio de Sesion", "Para el correcto ingreso a la aplicacion debera contar con su respectivo User y Password"},
    {"Equitetas de error", "Los campos de informacion deberan estar llenados en su totalidad para poder guardar la informacion "},
    {"Creacion de Profesor/Alumno", "Para la creacion de un nuevo profesor o alumno se debere tener anteriormente creado una Persona"},
    {"Matricula", "Para matricular un estudiante previamente debe crear un nuevo curso"},
    {"Abrir Ventanas","El numero maximo de ejecutar subventanas en la pantalla principal  son 5 "},
    {"Busqueda en VTN Malla","Se buscara automaticament e al ingresear los 10 digitos de la cedula"},
    {"Busquedas por datos","Al ingresar la cedula en el formulario (Docente,Alumno) se le buscara automaticamente si desea editarlo"},
    {"Eliminacion","Cuando se elimina un datos cambia se estado de true a false"},
    {"Atajos","Podra acceder a las distintas ventas utilizando las respectivas combinacions de teclas"}
    };
    
    
    String[][] GR16 = {{"Login" , "Solo podrán ingresar a través del usuario y contraseña asignado por el/la coordinador/a de la carrera."},
    {"Malla" , "En esta sección podrá buscar, seleccionar la carrera e ingresar notas. En el caso de estar cursando la materia, seleccionada, se le mostrara un mensaje."},
    {"Usuarios" , "Solo el usuario ROOT podrá editar, eliminar o ingresar un usuario."},
    {"Ver Roles" , "Deberá seleccionar una fila para poder visualizar cada rol que tenga un usuario."},
    {"Asignar Roles" , "Para poder asignar un rol o varios roles deberá seleccionar una fila. A tener en cuenta! el usuario ROOT no se podrá modificar, se ejecutará un mensaje en caso de que desee modificarlo."},
    {"Roles de Usuarios" , "En esta sección se podrán visualizar los permisos."},
    {"Editar Permisos" , "Primero deberá seleccionar una fila, luego aparecerá una venta en la que visualizaremos todos los permisos existentes y los cuales podremos otorgar a la persona seleccionada."},
    {"Ver Permisos" , "Primero deberá seleccionar una fila para poder visualizar los permisos que tiene esa persona."},
    {"Notas" , "En esta sección, podremos acceder a Tipo de Notas, Periodo de Ingreso de Notas e Ingreso de Notas"},
    {"Tipo de Notas" , "Se podrá eliminar,  editar, ingresar y actualizar los tipos de notas. En la tabla podra visualizar el Nombre, Valor Máximo, Valor Mínimo y Fecha de Creación."},
    {"Periodo de Ingreso de Notas" , "Se podrá eliminar, editar, ingresar y actualizar el periodo de ungreso de notas, en el cual se podra visualizar: Fecha de Inicio, Fecha de Cierre, Codigo de Periodo Lectivo y Codigo de Tipo de Notas"},
    {"Ingreso de Notas" , "Al hacer click en el botón de Ver Notas, se cargará en la tabla todos los datos de las notas de los Alumnos y aquellos que se encuentre desapobados aparecerán en rojo."},
    {"Reportes Notas" , "En el botón Imprimir, dentro de Ingreso de Notas, se podrán visualizar e imprimir los reportes de las notas solicitadas"}};

    
    public JDAyudaCTR(VtnPrincipal vtnPrin, VtnPrincipalCTR ctrPrin) {
        this.vtnAyuda = new JDAyuda(vtnPrin, false);
        this.vtnAyuda.setIconImage(ctrPrin.getIsta()); 
        this.pnlAyuda = new PnlAyuda();
        //Mostramos la ventana 
        this.vtnAyuda.setLocationRelativeTo(vtnPrin);
        this.vtnAyuda.setVisible(true);

    }

    public void iniciar() {
        CambioPnlCTR.cambioPnl(vtnAyuda.getPnlContenedor(), pnlAyuda);
        lbl = pnlAyuda.getLblMensaje();
        vtnAyuda.setModal(true);
        //Para cambiar a diferentes mensajes 
        vtnAyuda.getBtnAyudaG16().addActionListener(e -> clickG16());
        vtnAyuda.getBtnAyudaG23().addActionListener(e -> clickG23());
        //Por defecto incian las indicaciones del grupo 23 
        clickG23();
    }
    
    private void clickG23(){
        lbl.setText("");
        llenarLblG23();
        CambioPnlCTR.cambioPnl(vtnAyuda.getPnlContenedor(), pnlAyuda);
    }
    
    private void clickG16(){
        lbl.setText("");
        LlenarLblGR16();
        CambioPnlCTR.cambioPnl(vtnAyuda.getPnlContenedor(), pnlAyuda);
    }
    
    private void LlenarLblGR16(){
        //lbl = ayudaGR16.getLblMensajeGR16();
        String h = inicioHTML;
        for (String[] g : GR16){
            h = h + escribirAyuda(g[0], g[1]);
        }
        h = h + finHTML;
        lbl.setText(h);
        //pnlAyuda.add(lbl);
    }

    private void llenarLblG23() {
        //lbl = ayudaG23.getLblMensaje();
        //lbl.setText("<html>" + mensajes[0] + "</html>");
        String h = inicioHTML;
        for (String [] g : G23) {
            //System.out.println(g[0]+" "+g[1]);
            h = h + escribirAyuda(g[0], g[1]); 
        }
        h = h + finHTML;
        lbl.setText(h);
        //pnlAyuda.add(lbl);
    }

    private String escribirAyuda(String titulo, String ayuda) {
        String h = "<div class=\"titulo\"> " + titulo + "</div>"
                + "<p>" + ayuda + "</p>";
        return h;
    }

    private final String inicioHTML = "<HTML>"
            + "<style>"
            + ".titulo{"
            + "border-bottom: 2px solid #EBC024;"
            + "color: #2F4C71;"
            + "font-size: 14px;"
            + "margin: 5px 5px 1px 1px;"
            + "}"
            + "body{"
            + "padding: 0px 5px 5px 5px;"
            + "}"
            + "p{"
            + "font-size: 12px;"
            + "}"
            + "</style>"
            + "<body>";

    private final String finHTML = "</body>"
            + "</html>";

}
