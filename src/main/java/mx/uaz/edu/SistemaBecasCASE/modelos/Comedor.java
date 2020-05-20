package mx.uaz.edu.SistemaBecasCASE.modelos;

public class Comedor {

    private int idComedor;
    private String nombreComedor;
    private String calle;
    private String numExterior;
    private String colonia;
    private String codigoPostal;
    private String telefono;
    private Municipio municipio;
    private String desayuno;
    private String comida;
    private String cena;
    private Estatus estatus;

    public Comedor(){

    }

    public Comedor(int idComedor, String nombreComedor, String calle, String numExterior, String colonia, String codigoPostal, String telefono, Municipio municipio, String desayuno, String comida, String cena) {
        this.idComedor = idComedor;
        this.nombreComedor = nombreComedor;
        this.calle = calle;
        this.numExterior = numExterior;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.telefono = telefono;
        this.municipio = municipio;
        this.desayuno = desayuno;
        this.comida = comida;
        this.cena = cena;
    }

    public int getIdComedor() {
        return idComedor;
    }

    public void setIdComedor(int idComedor) {
        this.idComedor = idComedor;
    }

    public String getNombreComedor() {
        return nombreComedor;
    }

    public void setNombreComedor(String nombreComedor) {
        this.nombreComedor = nombreComedor;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumExterior() {
        return numExterior;
    }

    public void setNumExterior(String numExterior) {
        this.numExterior = numExterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getDesayuno() {
        return desayuno;
    }

    public void setDesayuno(String desayuno) {
        this.desayuno = desayuno;
    }

    public String getComida() {
        return comida;
    }

    public void setComida(String comida) {
        this.comida = comida;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return nombreComedor;
    }
}
