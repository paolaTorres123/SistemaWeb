package mx.uaz.edu.SistemaBecasCASE.modelos;

import java.util.Date;

public class Beca {

    private int id_beca;
    private Alumno alumno;
    private Date fecha_solicitud;
    private String fecha_formateada;
    private TipoBeca tipoBeca;
    private TipoSolicitud tipoSolicitud;
    private Estatus estatus;

    public Beca() { }

    public Beca(int id_beca, Alumno alumno, Date fecha_solicitud,
                TipoBeca tipoBeca, TipoSolicitud tipoSolicitud, Estatus estatus) {
        this.id_beca = id_beca;
        this.alumno = alumno;
        this.fecha_solicitud = fecha_solicitud;
        this.tipoBeca = tipoBeca;
        this.tipoSolicitud = tipoSolicitud;
        this.estatus = estatus;
    }

    public int getId_beca() {
        return id_beca;
    }

    public void setId_beca(int id_beca) {
        this.id_beca = id_beca;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public String getFecha_formateada() {
        return fecha_formateada;
    }

    public void setFecha_formateada(String fecha_formateada) {
        this.fecha_formateada = fecha_formateada;
    }

    public Date getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(Date fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }

    public TipoBeca getTipoBeca() {
        return tipoBeca;
    }

    public void setTipoBeca(TipoBeca tipoBeca) {
        this.tipoBeca = tipoBeca;
    }

    public TipoSolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }
}
