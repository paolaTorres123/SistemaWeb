package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.TipoUsuario;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADTipoUsuario {
    public ADTipoUsuario() {
    }

    public List<TipoUsuario> obtenerTodosTipoUsuario(){
        SqlSession sesion = Config.abreSesion();
        List<TipoUsuario> lista = null;
        try{
            lista = sesion.selectList("obtenerTodosTipoUsuario");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los tipos de usuarios de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return lista;
    }

    public TipoUsuario obtenerTipoUsuario(TipoUsuario tipoUsuario){
        SqlSession sesion = Config.abreSesion();
        TipoUsuario objeto = null;
        try{
            if (objeto.getTipo()!=null){
                objeto = sesion.selectOne("obtenerTipoUsuarioTipo",tipoUsuario);
            }else{
                objeto = sesion.selectOne("obtenerTipoUsuarioId",tipoUsuario);
            }

        }catch (Exception e){
            Notification.show("No se pudo recuperar el tipo de usuario de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return objeto;
    }

    public  boolean agregaTipoUsuario(TipoUsuario objeto){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.insert("agregaTipoUsuario", objeto);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo agregar el tipo de usuario"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public boolean editarTipoUsuario(TipoUsuario objetoEdicion){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("editarTipoUsuario", objetoEdicion);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar el tipo de usuario"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }
}
