package mx.uaz.edu.SistemaBecasCASE.modelos;

public class Estatus {

    private int idEstatus;
    private String nombreEstatus;

    public Estatus(int idEstatus, String estatus) {
        this.idEstatus = idEstatus;
        this.nombreEstatus = estatus;
    }

    public Estatus() {
    }

    public int getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getEstatus() {
        return nombreEstatus;
    }

    public void setEstatus(String estatus) {
        this.nombreEstatus = estatus;
    }

    @Override
    public String toString() {
        return  nombreEstatus;
    }
}
