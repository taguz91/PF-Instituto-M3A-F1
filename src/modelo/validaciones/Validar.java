package modelo.validaciones;

/**
 *
 * @author Usuario
 */
public class Validar {

    //Esto nos devuelve verdadero si toda la cadena enviada contiene letras
    public static boolean esLetras(String cadena) {
        //Es letras si continene uno de estos elementos puede contener espacios 
        return cadena.matches("[A-Za-záéíóúAÉÍÓÚÑñ\\s]+");
    }

    public static boolean esNumeros(String cadena) {
        //Si la cadena no contine solo numeros se retorna falso
        return cadena.matches("[0-9]+");
    }
    
    public static boolean esTelefono(String cadena){
        return cadena.matches("[0-9]{8,10}");
    }

    public static boolean esAnio(String cadena) {
        return cadena.matches("[0-9]{4}");
    }

    public static boolean esMes(String cadena) {
        if (cadena.matches("[0-9]{1,2}")) {
            int mes = Integer.parseInt(cadena);
            return mes > 0 && mes <= 12;
        } else {
            return false;
        }
    }

    public static boolean esDia(String cadena) {
        if (cadena.matches("[0-9]{1,2}")) {
            int dia = Integer.parseInt(cadena);
            return dia > 0 && dia <= 31;
        } else {
            return false;
        }
    }

    public static boolean esDinero(String cadena) {
        return cadena.matches("[0-9\\.]+"); 
    }

    public static boolean esCedula(String cadena) {
        if (cadena.matches("[0-9]{10}")) {
            String codProv = cadena.substring(0, 2);
            if (Integer.parseInt(codProv) > 0 && Integer.parseInt(codProv) <= 24) {
                int sumPares = 0;
                int sumImpares = 0;
                int impar; 
                char [] cedula = cadena.toCharArray();
                //Sumamos los impares y pares de nuestro array
                for (int i = 0; i < cedula.length - 1; i++) {
                    if ((i+1)%2 == 0) {
                        sumPares+= Integer.parseInt(cedula[i]+""); 
                    }else{
                        impar = Integer.parseInt(cedula[i]+"") * 2; 
                        if (impar > 9) {
                            impar-= 9; 
                        }
                        sumImpares+= impar; 
                    } 
                }
                int total = sumPares + sumImpares; 
                //Obtenemos la descena inmediata  
                int decena = (Integer.parseInt((total+"").substring(0, 1)) + 1 )* 10;  
                //Restamos la decena menos el total de las sumas  
                int  validador = decena - total; 
                if (validador == 10) {
                    validador = 0; 
                }
                return Integer.parseInt(cedula[9]+"") == validador;
                
            } else {
                System.out.println("No pertenece a ninguna provincia del ecuador.");
                return false;
            }
        } else {
            System.out.println("No contiene solo numeros. O no contine diez digitos.");
            return false;
        }
    }
}
