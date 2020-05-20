package mx.uaz.edu.SistemaBecasCASE.modelos;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Alumno {


    private int id_alumno,codigo_postal;
    private String nombre, ap_paterno,ap_materno,email,
            telefono,comentario_adicional,calle,colonia,
            curp,num_exterior, matricula,nss, semestre;
    private float promedio_general, promedio_anterior;
    private Date fecha_nac;

    private Estatus estatus;
    private TipoAlumno tipo_alumno;
    private EstatusEconomico estatus_economico;
    private Municipio municipio;

    public Alumno() { }

    public Alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public Alumno(int id_alumno, int codigo_postal, String nombre, String ap_paterno, String ap_materno, String email,
                  String telefono, String comentario_adicional, String calle, String colonia, String curp,
                  String num_exterior, String matricula, String nss, String semestre, float promedio_general,
                  float promedio_anterior, Date fecha_nac, Estatus estatus, TipoAlumno tipo_alumno,
                  EstatusEconomico estatus_economico, Municipio municipio) {
        this.id_alumno = id_alumno;
        this.codigo_postal = codigo_postal;
        this.nombre = nombre;
        this.ap_paterno = ap_paterno;
        this.ap_materno = ap_materno;
        this.email = email;
        this.telefono = telefono;
        this.comentario_adicional = comentario_adicional;
        this.calle = calle;
        this.colonia = colonia;
        this.curp = curp;
        this.num_exterior = num_exterior;
        this.matricula = matricula;
        this.nss = nss;
        this.semestre = semestre;
        this.promedio_general = promedio_general;
        this.promedio_anterior = promedio_anterior;
        this.fecha_nac = fecha_nac;
        this.estatus = estatus;
        this.tipo_alumno = tipo_alumno;
        this.estatus_economico = estatus_economico;
        this.municipio = municipio;
    }

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public int getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(int codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAp_paterno() {
        return ap_paterno;
    }

    public void setAp_paterno(String ap_paterno) {
        this.ap_paterno = ap_paterno;
    }

    public String getAp_materno() {
        return ap_materno;
    }

    public void setAp_materno(String ap_materno) {
        this.ap_materno = ap_materno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getComentario_adicional() {
        return comentario_adicional;
    }

    public void setComentario_adicional(String comentario_adicional) {
        this.comentario_adicional = comentario_adicional;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNum_exterior() {
        return num_exterior;
    }

    public void setNum_exterior(String num_exterior) {
        this.num_exterior = num_exterior;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public float getPromedio_general() {
        return promedio_general;
    }

    public void setPromedio_general(float promedio_general) {
        this.promedio_general = promedio_general;
    }

    public float getPromedio_anterior() {
        return promedio_anterior;
    }

    public void setPromedio_anterior(float promedio_anterior) {
        this.promedio_anterior = promedio_anterior;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public TipoAlumno getTipo_alumno() {
        return tipo_alumno;
    }

    public void setTipo_alumno(TipoAlumno tipo_alumno) {
        this.tipo_alumno = tipo_alumno;
    }

    public EstatusEconomico getEstatus_economico() {
        return estatus_economico;
    }

    public void setEstatus_economico(EstatusEconomico estatus_economico) {
        this.estatus_economico = estatus_economico;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public LocalDate getFecha_nacLD() {
        if(fecha_nac == null){
            return null;
        }
        return fecha_nac.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setFecha_nacLD(LocalDate fecha_nac) {
        this.fecha_nac = Date.from(fecha_nac.atStartOfDay(ZoneId.systemDefault()).toInstant());

    }

    public String getCodigo_postalFormat() {
        return String.valueOf(codigo_postal);
    }

    public void setCodigo_postalFormat(String codigo_postal) {
        this.codigo_postal = Integer.valueOf(codigo_postal);
    }

    @Override
    public String toString() {
        return nombre + ' ' + ap_paterno+" "+ap_materno;
    }
}
