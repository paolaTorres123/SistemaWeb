package mx.uaz.edu.SistemaBecasCASE.modelos;

public class TipoAlumno {
    private int id_tipo_alumno;
    private String tipo;

    public TipoAlumno() {
    }

    public TipoAlumno(int id_tipo_alumno, String tipo) {
        this.id_tipo_alumno = id_tipo_alumno;
        this.tipo = tipo;
    }

    public int getId_tipo_alumno() {
        return id_tipo_alumno;
    }

    public void setId_tipo_alumno(int id_tipo_alumno) {
        this.id_tipo_alumno = id_tipo_alumno;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return  tipo;
    }
}
