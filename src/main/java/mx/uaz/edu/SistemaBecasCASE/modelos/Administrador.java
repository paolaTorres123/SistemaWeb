package mx.uaz.edu.SistemaBecasCASE.modelos;

import mx.uaz.edu.SistemaBecasCASE.accesodatos.*;

import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADTipoUsuario;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Administrador {
    private int id_admin,id_usuario,codigo_postal;
    private  String nombre,ap_paterno,ap_materno,email,
            calle,colonia,telefono,num_exterior;
    private Date fecha_nac;

    private Estatus estatus;

    private TipoUsuario tipo_usuario;
    private Municipio municipio;

    public Administrador() {
    }

    public Administrador(int id_admin, int id_usuario, int codigo_postal, String nombre, String ap_paterno, String ap_materno,
                         String email, String calle, String colonia, String telefono,
                         String num_exterior, Date fecha_nac, Estatus estatus,
                         TipoUsuario tipo_usuario, Municipio municipio) {
        this.id_admin=id_admin;
        this.id_usuario = id_usuario;
        this.codigo_postal = codigo_postal;
        this.nombre = nombre;
        this.ap_paterno = ap_paterno;
        this.ap_materno = ap_materno;
        this.email = email;
        this.calle = calle;
        this.colonia = colonia;
        this.telefono = telefono;
        this.num_exterior = num_exterior;
        this.fecha_nac = fecha_nac;
        this.estatus = estatus;
        this.tipo_usuario = tipo_usuario;
        this.municipio = municipio;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
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

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getNum_exterior() {
        return num_exterior;
    }

    public void setNum_exterior(String num_exterior) {
        this.num_exterior = num_exterior;
    }

    public int getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(int codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getCodigo_postalSFormat() {
        return String.valueOf(codigo_postal);
    }

    public void setCodigo_postalSFormat(String codigo_postal) {
        this.codigo_postal = Integer.parseInt(codigo_postal);
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

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
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

    public TipoUsuario getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(TipoUsuario tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    @Override
    public String toString() {
        return nombre+" "+ap_paterno+" "+ap_materno+"";
    }
}