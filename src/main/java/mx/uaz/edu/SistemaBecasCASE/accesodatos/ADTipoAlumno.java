package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.TipoAlumno;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADTipoAlumno {
    public ADTipoAlumno() {
    }

    public List<TipoAlumno> obtenerTodosTipoAlumno(){
        SqlSession sesion = Config.abreSesion();
        List<TipoAlumno> lista = null;
        try{
            lista = sesion.selectList("obtenerTodosTipoAlumno");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar lostipos de alumno de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return lista;
    }


    public boolean editarTipoAlumno(TipoAlumno objetoEdicion){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("editaTipoAlumno", objetoEdicion);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar el tipo de alumno "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public  boolean agregaTipoAlumno(TipoAlumno objeto){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.insert("agregaTipoAlumno", objeto);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo agregar el tipo de alumno "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public TipoAlumno obtenerTipoAlumno(TipoAlumno tipoAlumno){
        SqlSession sesion = Config.abreSesion();
        TipoAlumno objeto = null;
        try{
            if (tipoAlumno.getTipo()!=null){
                objeto = sesion.selectOne("obtenerTipoAlumnoTipo",tipoAlumno);
            }else{
                objeto = sesion.selectOne("obtenerTipoAlumnoId",tipoAlumno);
            }
        }catch (Exception e){
            Notification.show("No se recuperar el tipo de alumno de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return objeto;
    }

}
