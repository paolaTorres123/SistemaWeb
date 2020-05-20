package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Estatus;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADEstatus {

    public ADEstatus() {
    }

    public List<Estatus> obtenerTodosEstatus(){
        SqlSession sesion = Config.abreSesion();
        List<Estatus> estatus = null;
        try {
            estatus = sesion.selectList("obtenerTodosEstatus");
        } catch (Exception e) {
            Notification.show("No se puedieron recuperar los Estatus de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return estatus;
    }

    public List<Estatus> obtenerEstatus(){
        SqlSession sesion = Config.abreSesion();
        List<Estatus> estatus = null;
        try {
            estatus = sesion.selectList("obtenerEstatus");
        } catch (Exception e) {
            Notification.show("No se puedieron recuperar los Estatus de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return estatus;
    }


    public List<Estatus> obtenerEstatusSimple(){
        SqlSession sesion = Config.abreSesion();
        List<Estatus> estatus = null;
        try {
            estatus = sesion.selectList("obtenerEstatusSimple");
        } catch (Exception e) {
            Notification.show("No se puedieron recuperar los Estatus simples de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return estatus;
    }
}
