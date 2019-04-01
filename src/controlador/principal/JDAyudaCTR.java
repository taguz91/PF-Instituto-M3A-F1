package controlador.principal;

import controlador.estilo.CambioPnlCTR;
import javax.swing.JLabel;
import vista.principal.JDAyuda;
import vista.principal.PnlAyudaG23;
import vista.principal.PnlAyudaGR16;
import vista.principal.VtnPrincipal;

/**
 *
 * @author Johnny
 */
public class JDAyudaCTR {

    private final JDAyuda ayuda;
    private final PnlAyudaG23 ayudaG23;
    private final PnlAyudaGR16 ayudaGR16;

    private final String[] mensajes = {"Este es una ayuda no se que puedo hacer con la ayuda, "
        + "SELECT id_almn_carrera, ac.id_carrera, ac.id_alumno, a.id_persona, \n"
        + "persona_primer_nombre, persona_segundo_nombre,\n"
        + "persona_primer_apellido, persona_segundo_apellido,\n"
        + "persona_celular, persona_correo, persona_identificacion\n"
        + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p \n"
        + "WHERE a.id_alumno = ac.id_alumno AND\n"
        + "p.id_persona = a.id_persona AND\n"
        + "ac.id_carrera = 2 AND (\n"
        + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
        + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%%' OR\n"
        + "	persona_identificacion ILIKE '%%');"
        + ""
        + "Este es una ayuda no se que puedo hacer con la ayuda, "
        + "SELECT id_almn_carrera, ac.id_carrera, ac.id_alumno, a.id_persona, \n"
        + "persona_primer_nombre, persona_segundo_nombre,\n"
        + "persona_primer_apellido, persona_segundo_apellido,\n"
        + "persona_celular, persona_correo, persona_identificacion\n"
        + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p \n"
        + "WHERE a.id_alumno = ac.id_alumno AND\n"
        + "p.id_persona = a.id_persona AND\n"
        + "ac.id_carrera = 2 AND (\n"
        + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
        + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%%' OR\n"
        + "	persona_identificacion ILIKE '%%');"
        + ""
        + "Este es una ayuda no se que puedo hacer con la ayuda, "
        + "SELECT id_almn_carrera, ac.id_carrera, ac.id_alumno, a.id_persona, \n"
        + "persona_primer_nombre, persona_segundo_nombre,\n"
        + "persona_primer_apellido, persona_segundo_apellido,\n"
        + "persona_celular, persona_correo, persona_identificacion\n"
        + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p \n"
        + "WHERE a.id_alumno = ac.id_alumno AND\n"
        + "p.id_persona = a.id_persona AND\n"
        + "ac.id_carrera = 2 AND (\n"
        + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
        + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%%' OR\n"
        + "	persona_identificacion ILIKE '%%');"
        + ""
        + "Este es una ayuda no se que puedo hacer con la ayuda, "
        + "SELECT id_almn_carrera, ac.id_carrera, ac.id_alumno, a.id_persona, \n"
        + "persona_primer_nombre, persona_segundo_nombre,\n"
        + "persona_primer_apellido, persona_segundo_apellido,\n"
        + "persona_celular, persona_correo, persona_identificacion\n"
        + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p \n"
        + "WHERE a.id_alumno = ac.id_alumno AND\n"
        + "p.id_persona = a.id_persona AND\n"
        + "ac.id_carrera = 2 AND (\n"
        + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
        + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%%' OR\n"
        + "	persona_identificacion ILIKE '%%');"
        + ""
        + "Este es una ayuda no se que puedo hacer con la ayuda, "
        + "SELECT id_almn_carrera, ac.id_carrera, ac.id_alumno, a.id_persona, \n"
        + "persona_primer_nombre, persona_segundo_nombre,\n"
        + "persona_primer_apellido, persona_segundo_apellido,\n"
        + "persona_celular, persona_correo, persona_identificacion\n"
        + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p \n"
        + "WHERE a.id_alumno = ac.id_alumno AND\n"
        + "p.id_persona = a.id_persona AND\n"
        + "ac.id_carrera = 2 AND (\n"
        + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
        + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%%' OR\n"
        + "	persona_identificacion ILIKE '%%');"
        + ""
        + "Este es una ayuda no se que puedo hacer con la ayuda, "
        + "SELECT id_almn_carrera, ac.id_carrera, ac.id_alumno, a.id_persona, \n"
        + "persona_primer_nombre, persona_segundo_nombre,\n"
        + "persona_primer_apellido, persona_segundo_apellido,\n"
        + "persona_celular, persona_correo, persona_identificacion\n"
        + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p \n"
        + "WHERE a.id_alumno = ac.id_alumno AND\n"
        + "p.id_persona = a.id_persona AND\n"
        + "ac.id_carrera = 2 AND (\n"
        + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
        + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%%' OR\n"
        + "	persona_identificacion ILIKE '%%');"
        + ""
        + "Este es una ayuda no se que puedo hacer con la ayuda, "
        + "SELECT id_almn_carrera, ac.id_carrera, ac.id_alumno, a.id_persona, \n"
        + "persona_primer_nombre, persona_segundo_nombre,\n"
        + "persona_primer_apellido, persona_segundo_apellido,\n"
        + "persona_celular, persona_correo, persona_identificacion\n"
        + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p \n"
        + "WHERE a.id_alumno = ac.id_alumno AND\n"
        + "p.id_persona = a.id_persona AND\n"
        + "ac.id_carrera = 2 AND (\n"
        + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
        + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%%' OR\n"
        + "	persona_identificacion ILIKE '%%');"
        + "\n"
        + "\n"
        + "AQUI TERMINA ", "RE a.id_alumno = ac.id_alumno AND\n"
        + "p.id_persona = a.id_persona AND\n"
        + "ac.i",
         "ersona_celular, persona_correo, persona_identificacion\n"
        + "FROM public.\"AlumnosCarrera\" ac, public.\"Alumnos\" a, public.\"Personas\" p \n"
        + "WHERE a.id_alumno = ac.id_alumno AND\n"
        + "p.id_persona = a.id_persona AND\n"
        + "ac.id_carrera = 2 AND (\n"
        + "	persona_primer_nombre || ' ' || persona_segundo_nombre || ' ' ||\n"
        + "	persona_primer_apellido || ' ' || persona_segundo_apellido ILIKE '%%' OR\n"
        + "	persona_identificacion ILIKE '%%');"
        + "\n"
        + "\n"
        + "AQUI TERMINA ", "RE a.id_alumno = ac.id_alumno AND\n"
        + "p.id_persona = a.id_persona AND\n"
        + "ac."};

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
    {"Atajos","Podra acceder a las distintas ventas utilizando las respectivas combinacions de teclas"}};
    
    
    String[][] GR16 = {{"LogIn" , "Solo podrán ingresar a través del usuario y contraseña asignado por el/la coordinador/a de la carrera."},
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
        this.ayuda = new JDAyuda(vtnPrin, false);
        this.ayuda.setIconImage(ctrPrin.getIsta()); 
        this.ayudaG23 = new PnlAyudaG23();
        
        this.ayudaGR16 = new PnlAyudaGR16();

        //Mostramos la ventana 
        this.ayuda.setLocationRelativeTo(vtnPrin);
        this.ayuda.setVisible(true);

    }
    
    public void InitGR16(){
        CambioPnlCTR.cambioPnl(ayuda.getPnlContenedor(), ayudaGR16);
        LlenarLblGR16();
        ayuda.setModal(true);
    }


    public void iniciar() {
        CambioPnlCTR.cambioPnl(ayuda.getPnlContenedor(), ayudaG23);
        llenarLbl();
        ayuda.setModal(true);
    }
    
    private void LlenarLblGR16(){
        JLabel lbl = ayudaGR16.getLblMensajeGR16();
        String h = inicioHTML;
        for (String[] g : GR16){
            h = h + escribirAyuda(g[0], g[1]);
        }
        h = h + finHTML;
        lbl.setText(h);
        ayudaGR16.add(lbl);
    }

    private void llenarLbl() {
        JLabel lbl = ayudaG23.getLblMensaje();
        //lbl.setText("<html>" + mensajes[0] + "</html>");
        String h = inicioHTML;
        for (String [] g : G23) {
            //System.out.println(g[0]+" "+g[1]);
            h = h + escribirAyuda(g[0], g[1]); 
        }
//        h = h + escribirAyuda("Hola", mensajes[0]);
//        h = h + escribirAyuda("Hola otra vez", mensajes[1]);
////    h = h + escribirAyuda("Hola", "WDHKAUDHJAWGHDAWJDHAWJDHAWKDHAWKDHAWKDH\nAWKDHAKWDHDDDDD"
////            + "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        h = h + finHTML;
        lbl.setText(h);
        ayudaG23.add(lbl);
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
