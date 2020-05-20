package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Subsede;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADSubsede {

    public ADSubsede() {
    }

    public boolean agregaSubsede(Subsede subsede){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try {
            sesion.insert("agregaSubsede", subsede);
            sesion.commit();
            ok = true;

        } catch (Exception e) {
            Notification.show("No se puede registra la subsede "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public boolean editarSubsede(Subsede subsede){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try {
            sesion.update("editarSubsede", subsede);
            sesion.commit();
            ok = true;

        } catch (Exception e) {
            Notification.show("No se puede guardar la información de subsede  "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public Subsede buscaSubsede(Subsede subsede){
        SqlSession sesion = Config.abreSesion();
        Subsede sub = null;
        try {

            sub = sesion.selectOne("buscaSubsede", subsede);
        } catch (Exception e) {
            Notification.show("No se puedo buscar la subsede"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return sub;
    }

    public List<Subsede> obtenerTodasSubsedes(){
        SqlSession sesion = Config.abreSesion();
        List<Subsede> subsedes = null;
        try {
            subsedes = sesion.selectList("obtenerTodasSubsedes");
        } catch (Exception e) {
            Notification.show("No se puedieron recuperar las subsedes de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return subsedes;
    }
    public boolean eliminarSubsede(List<Subsede> subsede) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();
        try {
            int resultado=sesion.delete("eliminarSubsede", subsede);
            sesion.commit();
            if(resultado >=1){
                ok=true;
            }
        } catch (Exception e) {
            Notification.show("No se puedieron eliminar las subsedes " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return ok;

    }
}