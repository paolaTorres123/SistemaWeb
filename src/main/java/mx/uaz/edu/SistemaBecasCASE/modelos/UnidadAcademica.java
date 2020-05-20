package mx.uaz.edu.SistemaBecasCASE.modelos;

public class UnidadAcademica {
    private int id_unidad_academica;
    private String nombre;

    public int getId_unidad_academica() {
        return id_unidad_academica;
    }

    public void setId_unidad_academica(int id_unidad_academica) {
        this.id_unidad_academica = id_unidad_academica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UnidadAcademica(int id_unidad_academica, String nombre) {
        this.id_unidad_academica = id_unidad_academica;
        this.nombre = nombre;
    }

    public UnidadAcademica() {
    }

    @Override
    public String toString() {
        return nombre ;
    }
}
