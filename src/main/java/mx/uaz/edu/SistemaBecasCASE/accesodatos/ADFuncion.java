package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Funcion;
import mx.uaz.edu.SistemaBecasCASE.modelos.Subsede;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADFuncion {

    public ADFuncion() {
    }

    public boolean agregaFuncion(Funcion funcion){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try {
            sesion.insert("agregaFuncion", funcion);
            sesion.commit();
            ok=true;

        } catch (Exception e) {
            Notification.show("No se puede registra la funcion"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public boolean editarFuncion(Funcion funcion){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try {
            sesion.update("editarFuncion", funcion);
            sesion.commit();
            ok = true;

        } catch (Exception e) {
            Notification.show("No se puede guardar la información del la función "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public Funcion buscaFuncion(Funcion funcion){
        SqlSession sesion = Config.abreSesion();
        Funcion fun = null;
        try {

            fun = sesion.selectOne("buscaFuncion", funcion);
        } catch (Exception e) {
            Notification.show("No se puedo buscar la función "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return fun;
    }

    public List<Funcion> obtenerTodasFunciones(){
        SqlSession sesion = Config.abreSesion();
        List<Funcion> funciones = null;
        try {
            funciones = sesion.selectList("obtenerTodasFunciones");
        } catch (Exception e) {
            Notification.show("No se puedieron recuperar las Funciones de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return funciones;
    }

    public boolean eliminarFuncion(List<Funcion> funcion) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();
        try {
            int resultado= sesion.delete("eliminarFuncion", funcion);
            sesion.commit();
            if(resultado >=1){
                ok=true;
            }
        } catch (Exception e) {
            Notification.show("No se puedieron eliminar la funcion " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            sesion.close();
        }
        return ok;

    }

}
