package mx.uaz.edu.SistemaBecasCASE.modelos;

public class Cuarto {
    private int id_cuarto;
    private int num_cuarto;
    private Casa casa;
    private int capacidad_cuarto;
    private Estatus estatus;
    private Casa id_casa;



    public Cuarto(){

    }
    public Cuarto(int id_cuarto, int num_cuarto, Casa casa, int capacidad_cuarto, Estatus
                  estatus,Casa id_casa){
        this.id_cuarto=id_cuarto;
        this.num_cuarto=num_cuarto;
        this.casa=casa;
        this.capacidad_cuarto=capacidad_cuarto;
        this.estatus=estatus;
        this.id_casa=id_casa;


    }
    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public int getId_cuarto() {
        return id_cuarto;
    }

    public void setId_cuarto(int id_cuarto) {
        this.id_cuarto = id_cuarto;
    }

    public int getNum_cuarto() {
        return num_cuarto;
    }

    public void setNum_cuarto(int num_cuarto) {
        this.num_cuarto = num_cuarto;
    }



    public int getCapacidad_cuarto() {
        return capacidad_cuarto;
    }

    public void setCapacidad_cuarto(int capacidad_cuarto) {
        this.capacidad_cuarto = capacidad_cuarto;
    }
    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }
    public Casa getId_casa() {
        return id_casa;
    }

    public void setId_casa(Casa id_casa) {
        this.id_casa = id_casa;
    }
}

