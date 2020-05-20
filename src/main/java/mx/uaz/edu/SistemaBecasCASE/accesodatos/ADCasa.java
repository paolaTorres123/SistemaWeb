package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Casa;
import mx.uaz.edu.SistemaBecasCASE.modelos.Comedor;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADCasa {

    public ADCasa(){

    }
    public boolean agregaCasa(Casa casa){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;

        try{
            sesion.insert("agregaCasa", casa);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo agregar la casa"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }
    public boolean editarCasa(Casa casa){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("editarCasa", casa);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar la casa"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }
    public boolean eliminarCasa(List<Casa> casa) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();
        try {
            int resultado=sesion.delete("eliminarCasa", casa);
            sesion.commit();
            if(resultado >=1){
                ok = true;
            }

        } catch (Exception e) {
            Notification.show("No se puedieron eliminar lo(s) cuarto(s) de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public List<Casa> obtenerTodasCasas(){
        SqlSession sesion = Config.abreSesion();
        List<Casa> casa = null;
        try{
            casa = sesion.selectList("obtenerTodasCasas");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar las casas de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return casa;
    }
    public List<Casa> obtenerCasassActivas(){
        SqlSession sesion = Config.abreSesion();
        List<Casa> casas = null;
        try{
            casas = sesion.selectList("obtenerCasassActivas");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar las casas de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return casas;
    }
    public List<Casa> obtenerCasasInactivas(){
        SqlSession sesion = Config.abreSesion();
        List<Casa> casas = null;
        try{
            casas = sesion.selectList("obtenerCasasInactivas");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar las casas de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return casas;
    }
    public boolean reactivarCasas(List<Casa> casas) {
        boolean ok = false;
        if(!casas.isEmpty()) {
            SqlSession sesion = Config.abreSesion();
            try {
                sesion.update("reactivarCasas", casas);
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
    public boolean desactivarCasas(List<Casa> casas) {
        boolean ok = false;
        if(!casas.isEmpty()) {
            SqlSession sesion = Config.abreSesion();
            try {
                sesion.update("desactivarCasas", casas);
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