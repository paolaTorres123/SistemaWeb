package mx.uaz.edu.SistemaBecasCASE.modelos;

public class EstatusEconomico {
    private int id_estatus_economico;
    private String tipo;

    public EstatusEconomico() {
    }

    public EstatusEconomico(int id_estatus_economico, String tipo) {
        this.id_estatus_economico = id_estatus_economico;
        this.tipo = tipo;
    }

    public int getId_estatus_economico() {
        return id_estatus_economico;
    }

    public void setId_estatus_economico(int id_estatus_economico) {
        this.id_estatus_economico = id_estatus_economico;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    @Override
    public String toString() {
        return  tipo;
    }
}
