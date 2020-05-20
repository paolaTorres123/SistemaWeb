package mx.uaz.edu.SistemaBecasCASE.modelos;

public class Responsable {

    private int idResponsable;
    private int id_usuario;
    private String nombreResponsable;
    private String apPaterno,apMaterno;
    private Funcion funcion;
    private Subsede subsede;
    private String email;
    private String telefono;
    private Estatus estatus;

    public Responsable() {
    }

    public Responsable(int idResponsable, String nombreResponsable, String apPaterno, String apMaterno, Funcion funcion,
                       Subsede subsede, String email, String telefono, Estatus estatus) {

        this.idResponsable = idResponsable;
        this.nombreResponsable = nombreResponsable;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.funcion = funcion;
        this.subsede = subsede;
        this.email = email;
        this.telefono = telefono;
        this.estatus = estatus;
    }

    public Responsable(int idResponsable, int id_usuario, String nombreResponsable, String apPaterno,
                       String apMaterno, Funcion funcion, Subsede subsede, String email, String telefono, Estatus estatus) {
        this.idResponsable = idResponsable;
        this.id_usuario = id_usuario;
        this.nombreResponsable = nombreResponsable;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.funcion = funcion;
        this.subsede = subsede;
        this.email = email;
        this.telefono = telefono;
        this.estatus = estatus;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public Subsede getSubsede() {
        return subsede;
    }

    public void setSubsede(Subsede subsede) {
        this.subsede = subsede;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return nombreResponsable;
    }
}
