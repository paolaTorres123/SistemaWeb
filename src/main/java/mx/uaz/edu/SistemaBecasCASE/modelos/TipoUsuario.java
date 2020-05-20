package mx.uaz.edu.SistemaBecasCASE.modelos;

public class TipoUsuario {
    private int id_tipo_usuario;
    private String tipo;

    public TipoUsuario() {
    }

    public TipoUsuario(int id_tipo_usuario, String tipo) {
        this.id_tipo_usuario = id_tipo_usuario;
        this.tipo = tipo;
    }

    public int getId_tipo_usuario() {
        return id_tipo_usuario;
    }

    public void setId_tipo_usuario(int id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
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
