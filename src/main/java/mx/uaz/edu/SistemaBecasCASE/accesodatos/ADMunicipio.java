package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Municipio;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADMunicipio {

    public ADMunicipio() {

    }

    public List<Municipio> obtenerTodosMunicipios(){
        SqlSession sesion = Config.abreSesion();
        List<Municipio> municipios = null;
        try{
            municipios = sesion.selectList("obtenerTodosMunicipios");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los municipios de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return municipios;
    }

    public Municipio obtenerMunicipio(Municipio municipio){
        SqlSession sesion = Config.abreSesion();
        Municipio objeto = null;
        try{
            if (municipio.getNombreMunicipio()!=null)
                objeto = sesion.selectOne("obtenMunicipioNombre",municipio);
            else objeto = sesion.selectOne("obtenMunicipioId",municipio);
        }catch (Exception e){
            Notification.show("No se pudo recuperar el municipio de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return objeto;
    }



}
