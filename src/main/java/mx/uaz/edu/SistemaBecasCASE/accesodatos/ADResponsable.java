package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Responsable;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADResponsable {

    public ADResponsable() {
    }

    public boolean agregaResponsable(Responsable responsable){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try {
            sesion.insert("agregaResponsable", responsable);
            sesion.commit();
            ok = true;

        } catch (Exception e) {
            Notification.show("No se puede registra el usuario"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public boolean editarResponsable(Responsable responsable){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try {
            sesion.update("editarResponsable", responsable);
            sesion.commit();
            ok = true;

        } catch (Exception e) {
            Notification.show("No se puede guardar la información del responsable "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public List<Responsable> obtenerTodosResponsables(){
        SqlSession sesion = Config.abreSesion();
        List<Responsable> responsables = null;
        try {
            responsables = sesion.selectList("obtenerTodosResponsables");

        } catch (Exception e) {
            Notification.show("No se puedieron recuperar los Responsablesde la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return responsables;
    }

    public List<Responsable> obtenerResponsablesActivos(){
        SqlSession sesion = Config.abreSesion();
        List<Responsable> responsables = null;
        try{
            responsables= sesion.selectList("obtenerResponsablesActivos");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los responsables de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return responsables;
    }

    public List<Responsable> obtenerResponsablesInactivos(){
        SqlSession sesion = Config.abreSesion();
        List<Responsable> responsables = null;
        try{
            responsables = sesion.selectList("obtenerResponsablesInactivos");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los comedores de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return responsables;
    }

    public boolean eliminarResponsable(List<Responsable>responsables) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();
        try {

            int resultado =sesion.delete("eliminarResponsables2", responsables);
            sesion.commit();
            if(resultado >=1){
                ok=true;
            }


        } catch (Exception e) {
            Notification.show("No se puedieron eliminar lo(s) responsables(s) de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }






    public boolean reactivarResponsables(List<Responsable> responsables) {
        boolean ok = false;
        if(!responsables.isEmpty()) {
            SqlSession sesion = Config.abreSesion();
            try {
                sesion.update("reactivarResponsable", responsables);
                sesion.commit();
                ok = true;
            } catch (Exception e) {
                Notification.show("No se puedieron reactivar lo(s) responsables(s) de la BD " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
            } finally {
                sesion.close();
            }
        }
        return ok;
    }

    public boolean desactivarResponsables(List<Responsable> responsables) {
        boolean ok = false;
        if(!responsables.isEmpty()) {
            SqlSession sesion = Config.abreSesion();
            try {
                sesion.update("desactivarResponsable", responsables);
                sesion.commit();
                ok = true;
            } catch (Exception e) {
                Notification.show("No se pudieron desactivar lo(s) responsables(s) de la BD " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
            } finally {
                sesion.close();
            }
        }
        return ok;
    }



}
