package mx.uaz.edu.SistemaBecasCASE.modelos;

public class TipoSolicitud {

    int id_tipo_solicitud;
    String solicitud;

    public TipoSolicitud() {
    }

    public TipoSolicitud(int id_tipo_solicitud) {
        this.id_tipo_solicitud = id_tipo_solicitud;
    }

    public TipoSolicitud(int id_tipo_solicitud, String solicitud) {
        this.id_tipo_solicitud = id_tipo_solicitud;
        this.solicitud = solicitud;
    }

    public int getId_tipo_solicitud() {
        return id_tipo_solicitud;
    }

    public void setId_tipo_solicitud(int id_tipo_solicitud) {
        this.id_tipo_solicitud = id_tipo_solicitud;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    @Override
    public String toString() {
        return solicitud;
    }
}
