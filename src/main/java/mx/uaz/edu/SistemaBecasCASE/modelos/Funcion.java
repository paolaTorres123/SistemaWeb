package mx.uaz.edu.SistemaBecasCASE.modelos;

public class Funcion {
    private int idFuncion;
    private String funcion;

    public Funcion() {}

    public int getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    @Override
    public String toString() {
        return funcion;
    }
}
