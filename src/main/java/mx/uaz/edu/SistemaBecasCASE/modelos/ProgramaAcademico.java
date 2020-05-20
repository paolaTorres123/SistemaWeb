package mx.uaz.edu.SistemaBecasCASE.modelos;

import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADMunicipio;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADUnidadAcademica;

public class ProgramaAcademico {
    private int id_programa_academico,num_exterior,codigo_postal,id_municipio,
            id_unidad_academica;
    private String nombre,calle, colonia;
    private UnidadAcademica unidad;
    private Municipio municipio;
    private char estatus;

    public ProgramaAcademico() {
    }

    public ProgramaAcademico(int id_programa_academico, int num_exterior, int codigo_postal,
                             int id_municipio,int id_unidad_academica, String nombre,
                             String calle, String colonia, char estatus) {
        this.id_programa_academico = id_programa_academico;
        this.num_exterior = num_exterior;
        this.codigo_postal = codigo_postal;
        this.id_municipio = id_municipio;
        this.id_unidad_academica=id_unidad_academica;
        this.nombre = nombre;
        this.calle = calle;
        this.colonia = colonia;
        this.estatus=estatus;

        unidad = new UnidadAcademica();
        unidad.setId_unidad_academica(id_unidad_academica);
        unidad = new ADUnidadAcademica().obtenerUnidadAcademica(unidad);
        municipio = new Municipio();
        municipio.setIdMunicipio(id_municipio);
        municipio = new ADMunicipio().obtenerMunicipio(municipio);
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
    public int getId_unidad_academica() {
        return id_unidad_academica;
    }

    public void setId_unidad_academica(int id_unidad_academica) {
        this.id_unidad_academica = id_unidad_academica;
    }

    public int getId_programa_academico() {
        return id_programa_academico;
    }

    public void setId_programa_academico(int id_programa_academico) {
        this.id_programa_academico = id_programa_academico;
    }

    public UnidadAcademica getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadAcademica unidad) {
        this.unidad = unidad;
    }

    public char getEstatus() {
        return estatus;
    }

    public void setEstatus(char estatus) {
        this.estatus = estatus;
    }

    public int getNum_exterior() {
        return num_exterior;
    }

    public void setNum_exterior(int num_exterior) {
        this.num_exterior = num_exterior;
    }

    public int getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(int codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public int getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(int id_municipio) {
        this.id_municipio = id_municipio;
    }

    public UnidadAcademica getUnidadAcademica() {
        return unidad;
    }

    public void setUnidadAcademica(UnidadAcademica unidadAcademica) {
        unidad = unidadAcademica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return  nombre;
    }
}
