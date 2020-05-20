package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.UnidadAcademica;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADUnidadAcademica {
    public ADUnidadAcademica() {
    }

    public List<UnidadAcademica> obtenerTodasUnidadAcademica(){
        SqlSession sesion = Config.abreSesion();
        List<UnidadAcademica> lista = null;
        try{
            lista = sesion.selectList("obtenerTodasUnidadAcademica");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar las unidades acdémicas de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return lista;
    }

    public UnidadAcademica obtenerUnidadAcademica(UnidadAcademica unidadAcademica){
        SqlSession sesion = Config.abreSesion();
        UnidadAcademica objeto = null;
        try{
            if (unidadAcademica.getNombre()!=null)
                objeto = sesion.selectOne("obtenerUnidadAcademicaNombre",unidadAcademica);
            else objeto = sesion.selectOne("obtenerUnidadAcademicaId",unidadAcademica);
        }catch (Exception e){
            Notification.show("No se recuperar la unidad académica de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return objeto;
    }

    public boolean editarUnidadAcademica(UnidadAcademica objetoEdicion){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("editarUnidadAcademica", objetoEdicion);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar la unidad académica "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public  boolean agregaUnidadAcademica(UnidadAcademica objeto){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.insert("agregaUnidadAcademica", objeto);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo agregar la unidad académica"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }
}
