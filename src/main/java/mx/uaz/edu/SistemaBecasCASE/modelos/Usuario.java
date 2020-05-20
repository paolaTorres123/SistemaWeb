package mx.uaz.edu.SistemaBecasCASE.modelos;

public class Usuario {

    private int id_usuario;
    private String nombre_usuario, contrasena, cadena, email, confirmado;
    private TipoUsuario tipo_usuario;

    public Usuario() {
    }

    public Usuario(String nombre_usuario, String cadena) {
        this.nombre_usuario = nombre_usuario;
        this.cadena = cadena;
    }

    public Usuario(int id_usuario, String nombre_usuario, String contrasena, String cadena, String email, String confirmado, TipoUsuario tipo_usuario) {
        this.id_usuario = id_usuario;
        this.nombre_usuario = nombre_usuario;
        this.contrasena = contrasena;
        this.cadena = cadena;
        this.email = email;
        this.confirmado = confirmado;
        this.tipo_usuario = tipo_usuario;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmado() {
        return confirmado;
    }

    public void setConfirmado(String confirmado) {
        this.confirmado = confirmado;
    }

    public TipoUsuario getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(TipoUsuario tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    @Override
    public String toString() {
        return nombre_usuario;
    }
}
