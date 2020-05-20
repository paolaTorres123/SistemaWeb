package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Comedor;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADComedor {

    public  boolean agregaComedor(Comedor comedor){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.insert("agregaComedor", comedor);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo agregar el comedor "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public List<Comedor> obtenerTodosComedores(){
        SqlSession sesion = Config.abreSesion();
        List<Comedor> comedores = null;
        try{
            //Select list es porque se espera una lista
            comedores = sesion.selectList("obtenerTodosComedores");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los comedores de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return comedores;
    }

    public List<Comedor> obtenerComedoresActivos(){
        SqlSession sesion = Config.abreSesion();
        List<Comedor> comedores = null;
        try{
            comedores = sesion.selectList("obtenerComedoresActivos");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los comedores de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return comedores;
    }

    public List<Comedor> obtenerComedoresInactivos(){
        SqlSession sesion = Config.abreSesion();
        List<Comedor> comedores = null;
        try{
            comedores = sesion.selectList("obtenerComedoresInactivos");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los comedores de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return comedores;
    }

    public boolean editarComedor(Comedor comedor){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("editaComedor", comedor);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar el comedor "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public boolean eliminarComedores(List<Comedor> comedores) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();
        try {
            sesion.delete("eliminarComedores", comedores);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("No se puedieron eliminar lo(s) comedores(s) de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public boolean reactivarComedores(List<Comedor> comedores) {
        boolean ok = false;
        if(!comedores.isEmpty()) {
            SqlSession sesion = Config.abreSesion();
            try {
                sesion.update("reactivarComedor", comedores);
                sesion.commit();
                ok = true;
            } catch (Exception e) {
                Notification.show("No se puedieron reactivar lo(s) comedores(s) de la BD " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
            } finally {
                sesion.close();
            }
        }
        return ok;
    }

    public boolean desactivarComedores(List<Comedor> comedores) {
        boolean ok = false;
        if(!comedores.isEmpty()) {
            SqlSession sesion = Config.abreSesion();
            try {
                sesion.update("desactivarComedor", comedores);
                sesion.commit();
                ok = true;
            } catch (Exception e) {
                Notification.show("No se puedieron desactivar lo(s) comedores(s) de la BD " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
            } finally {
                sesion.close();
            }
        }
        return ok;
    }
}
