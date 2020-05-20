package mx.uaz.edu.SistemaBecasCASE.modelos;

public class Subsede {

    private int idSubsede;
    private String subsede;

    public Subsede() {}

    public int getIdSubsede() {
        return idSubsede;
    }

    public void setIdSubsede(int idSubsede) {
        this.idSubsede = idSubsede;
    }

    public String getNombreSubsede() {
        return subsede;
    }

    public void setNombreSubsede(String nombreSubsede) {
        this.subsede = nombreSubsede;
    }

    @Override
    public String toString() {
        return subsede;
    }
}
