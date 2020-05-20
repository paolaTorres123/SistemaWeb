package mx.uaz.edu.SistemaBecasCASE.modelos;

public class Casa {
    private int idCasa;
    private String nombreCasa;
    private int capacidadTotal;
    private int numeroCuartos;
    private String calle;
    private int numExterior;
    private String colonia;
    private int codigoPostal;
    private Supervisor supervisor;
    private Estatus estatus;


    public Casa(){

    }
    public Casa(int idCasa, String nombreCasa, int capacidadTotal, int numeroCuartos, String calle, int numExterior, String colonia, int codigoPostal,Supervisor supervisor){
        this.idCasa=idCasa;
        this.nombreCasa=nombreCasa;
        this.capacidadTotal=capacidadTotal;
        this.numeroCuartos=numeroCuartos;
        this.calle=calle;
        this.numExterior=numExterior;
        this.colonia=colonia;
        this.codigoPostal=codigoPostal;
        this.supervisor=supervisor;
    }

    public int getIdCasa() {
        return idCasa;
    }

    public void setIdCasa(int idCasa) {
        this.idCasa = idCasa;
    }

    public String getNombreCasa() {
        return nombreCasa;
    }

    public void setNombreCasa(String nombreCasa) {
        this.nombreCasa = nombreCasa;
    }

    public int getCapacidadTotal() {
        return capacidadTotal;
    }

    public void setCapacidadTotal(int capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
    }

    public int getNumeroCuartos() {
        return numeroCuartos;
    }

    public void setNumeroCuartos(int numeroCuartos) {
        this.numeroCuartos = numeroCuartos;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumExterior() {
        return numExterior;
    }

    public void setNumExterior(int numExterior) {
        this.numExterior = numExterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    public String toString(){
        return nombreCasa;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

}