package mx.uaz.edu.SistemaBecasCASE.modelos;

public class Supervisor {

    private int idSupervisor;
    private int id_usuario;
    private String nombreSupervisor;
    private String apPaterno;
    private String apMaterno;
    private String telefonoCelular;
    private String emailSupervisor;
    private Estatus estatus;


    public Supervisor(){

    }

    public Supervisor(int idSupervisor, int id_usuario, String nombreSupervisor, String apPaterno, String apMaterno, String telefonoCelular, String emailSupervisor, Estatus estatus) {
        this.idSupervisor = idSupervisor;
        this.id_usuario = id_usuario;
        this.nombreSupervisor = nombreSupervisor;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.telefonoCelular = telefonoCelular;
        this.emailSupervisor = emailSupervisor;
        this.estatus = estatus;
    }

    public Supervisor(int idSupervisor, String nombreSupervisor, String apPaterno, String apMaterno, String telefonoCelular, String emailSupervisor, Estatus estatus) {
        this.idSupervisor = idSupervisor;
        this.nombreSupervisor = nombreSupervisor;
        this.apPaterno = apPaterno;
        this.apMaterno = apMaterno;
        this.telefonoCelular = telefonoCelular;
        this.emailSupervisor = emailSupervisor;
        this.estatus = estatus;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(int idSupervisor) {
        this.idSupervisor = idSupervisor;
    }

    public String getNombreSupervisor() {
        return nombreSupervisor;
    }

    public void setNombreSupervisor(String nombreSupervisor) {
        this.nombreSupervisor = nombreSupervisor;
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

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getEmailSupervisor() {
        return emailSupervisor;
    }

    public void setEmailSupervisor(String emailSupervisor) {
        this.emailSupervisor = emailSupervisor;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {

        if(apMaterno == null){
            apMaterno = "";
        }

        return nombreSupervisor + " " + apPaterno + " " + apMaterno;
    }
}

