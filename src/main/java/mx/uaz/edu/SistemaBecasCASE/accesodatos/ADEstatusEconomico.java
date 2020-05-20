package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.EstatusEconomico;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADEstatusEconomico {
    public ADEstatusEconomico() {
    }

    public List<EstatusEconomico> obtenerTodosEstatusEconomicos(){
        SqlSession sesion = Config.abreSesion();
        List<EstatusEconomico> lista = null;
        try{
            lista = sesion.selectList("obtenerTodosEstatusEconomico");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los estatus económicos de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return lista;
    }

    public EstatusEconomico obtenerEstatusEconomico(EstatusEconomico estatusEconomico){
        SqlSession sesion = Config.abreSesion();
        EstatusEconomico objeto = null;
        try{
            if (estatusEconomico.getTipo()!=null)
                objeto = sesion.selectOne("obtenerEstatusEconomicoTipo",estatusEconomico);
            else objeto = sesion.selectOne("obtenerEstatusEconomicoId",estatusEconomico);
        }catch (Exception e){
            Notification.show("No se recuperar el estatus económico de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return objeto;
    }

    public boolean editarEstatusEconomico(EstatusEconomico objetoEdicion){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("editaEstatusEconomico", objetoEdicion);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar el estatus economico "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public  boolean agregaEstatusEconomico(EstatusEconomico objeto){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.insert("agregaEstatusEconomico", objeto);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo agregar el estatus economico "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }


}
