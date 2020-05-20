package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Comedor;
import mx.uaz.edu.SistemaBecasCASE.modelos.Supervisor;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADSupervisor {

    public  boolean agregaSupervisor(Supervisor supervisor){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.insert("agregaSupervisor", supervisor);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo agregar el supervisor.\n" +
                    "Revisa que el teléfono o correo electrónico no ha sido registrado previamente.", Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public List<Supervisor> obtenerTodosSupervisores(){
        SqlSession sesion = Config.abreSesion();
        List<Supervisor> supervisores = null;
        try{
            supervisores = sesion.selectList("obtenerTodosSupervisores");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los supervisores de la BD ", Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return supervisores;
    }

    public List<Supervisor> obtenerSupervisoresActivos(){
        SqlSession sesion = Config.abreSesion();
        List<Supervisor> supervisores = null;
        try{
            supervisores = sesion.selectList("obtenerSupervisoresActivos");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los supervisores de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return supervisores;
    }

    public List<Supervisor> obtenerSupervisoresInactivos(){
        SqlSession sesion = Config.abreSesion();
        List<Supervisor> supervisores = null;
        try{
            supervisores = sesion.selectList("obtenerSupervisoresInactivos");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los supervisores de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return supervisores;
    }

    public boolean editarSupervisor(Supervisor supervisor){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("editaSupervisor", supervisor);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar el supervisor \\n" +
                    "Revisa que el teléfono no ha sido registrado previamente.", Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public boolean eliminarSupervisores(List<Supervisor> supervisores) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();
        try {
            int resultado = sesion.delete("eliminarSupervisores", supervisores);
            //sesion.delete("eliminarSupervisores", supervisores);
            sesion.commit();
            System.out.println("Resultado: " + resultado);
            if(resultado >= 1){
                ok = true;
            }
            //ok = true;
        } catch (Exception e) {
            Notification.show("No se pudieron eliminar el (los) supervisor(es) de la BD ", Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public boolean reactivarSupervisores(List<Supervisor> supervisores) {
        boolean ok = false;
        if(!supervisores.isEmpty()) {
            SqlSession sesion = Config.abreSesion();
            try {
                sesion.update("reactivarSupervisor", supervisores);
                sesion.commit();
                ok = true;
            } catch (Exception e) {
                Notification.show("No se pudieron reactivar el(los) supervisor(es) de la BD " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
            } finally {
                sesion.close();
            }
        }
        return ok;
    }

    public boolean desactivarSupervisores(List<Supervisor> supervisores) {
        boolean ok = false;
        if (!supervisores.isEmpty()) {
            SqlSession sesion = Config.abreSesion();
            try {
                sesion.update("desactivarSupervisor", supervisores);
                sesion.commit();
                ok = true;
            } catch (Exception e) {
                Notification.show("No se pudieron desactivar el(los) supervisor(es) de la BD " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
            } finally {
                sesion.close();
            }
        }
        return ok;
    }
}
