package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Cuarto;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADCuarto {

    public boolean agregaCuarto(Cuarto cuartos){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.insert("agregaCuarto", cuartos);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo agregar el cuarto",e.getMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
        }finally {
            sesion.close();
        }
        return ok;
    }
    public boolean editarCuarto(Cuarto cuartos){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("editarCuarto", cuartos);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar el cuarto"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }
    public boolean eliminarCuarto(List<Cuarto> cuartos) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();
        try {
            sesion.delete("eliminarCuarto", cuartos);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("No se puedieron eliminar lo(s) cuarto(s) de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }


    public List<Cuarto> obtenerTodasCuarto(){
        SqlSession sesion = Config.abreSesion();

        List<Cuarto> cuarto = null;
        try{
            //Select list es porque se espera una lista
            cuarto = sesion.selectList("obtenerTodasCuarto");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los cuartos de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return cuarto;
    }
    public List<Cuarto> obtenerCuartosActivos(){
        SqlSession sesion = Config.abreSesion();
        List<Cuarto> cuartos = null;
        try{
            cuartos = sesion.selectList("obtenerCuartosActivos");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar las casas de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return cuartos;
    }
    public List<Cuarto> obtenerCuartosInactivos(){
        SqlSession sesion = Config.abreSesion();
        List<Cuarto> cuartos = null;
        try{
            cuartos = sesion.selectList("obtenerCuartosInactivos");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar las casas de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return cuartos;
    }
    public boolean reactivarCuartos(List<Cuarto> cuartos) {
        boolean ok = false;
        if(!cuartos.isEmpty()) {
            SqlSession sesion = Config.abreSesion();
            try {
                sesion.update("reactivarCuartos", cuartos);
                sesion.commit();
                ok = true;
            } catch (Exception e) {
                Notification.show("No se puedieron reactivar la(s) casa(s) de la BD " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
            } finally {
                sesion.close();
            }
        }
        return ok;
    }
    public boolean desactivarCuartos(List<Cuarto> cuartos) {
        boolean ok = false;
        if(!cuartos.isEmpty()) {
            SqlSession sesion = Config.abreSesion();
            try {
                sesion.update("desactivarCuartos", cuartos);
                sesion.commit();
                ok = true;
            } catch (Exception e) {
                Notification.show("No se puedieron desactivar la(s) casa(s) de la BD " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
            } finally {
                sesion.close();
            }
        }
        return ok;
    }

}
