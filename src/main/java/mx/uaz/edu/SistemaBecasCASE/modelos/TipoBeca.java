package mx.uaz.edu.SistemaBecasCASE.modelos;

public class TipoBeca {

    private int id_tipo_beca;
    private String tipo;

    public TipoBeca() { }

    public TipoBeca(int id_tipo_beca) {
        this.id_tipo_beca = id_tipo_beca;
    }

    public TipoBeca(int id_tipo_beca, String tipo) {
        this.id_tipo_beca = id_tipo_beca;
        this.tipo = tipo;
    }

    public int getId_tipo_beca() {
        return id_tipo_beca;
    }

    public void setId_tipo_beca(int id_tipo_beca) {
        this.id_tipo_beca = id_tipo_beca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo;
    }
}
