package modelo.ReferenciasB;

import modelo.ConectarDB;

public class ReferenciaBD extends ReferenciasMD {

    private final ConectarDB conectar;

    public ReferenciaBD(ConectarDB conectar) {
        this.conectar = conectar;
    }

    public boolean insertarReferencia() {
        String nsql = "INSERT INTO public.\"Referencias\"(\n"
                + "codigo_referencia,descripcion_referencia,tipo_referencia,existe_en_biblioteca)\n"
                + " values ('" + getCodigo_referencia() + "','" + getDescripcion_referencia() + "','"+getTipo_referencia()+"',"+isExiste_en_biblioteca() + ");";
        if (conectar.nosql(nsql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }

    }
}
