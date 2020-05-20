package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Alumno;
import mx.uaz.edu.SistemaBecasCASE.modelos.Beca;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADBeca {

    public ADBeca() {

    }

    public List<Beca> obtenerTodasBecas(){
        SqlSession sesion = Config.abreSesion();
        List<Beca> becas = null;
        try{
            becas = sesion.selectList("obtenerTodasBecas");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar las becas de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return becas;
    }

    public boolean agregaBeca(Beca beca){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.insert("agregaBeca", beca);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo registrar la solicitud "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public List<Beca> obtenerBecasHospedaje(){
        SqlSession sesion = Config.abreSesion();
        List<Beca> becas = null;
        try{
            becas = sesion.selectList("obtenerBecasHospedaje");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar las becas de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return becas;
    }

    public List<Beca> obtenerBecasAlimentacion(){
        SqlSession sesion = Config.abreSesion();
        List<Beca> becas = null;
        try{
            becas = sesion.selectList("obtenerBecasAlimentacion");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar las becas de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return becas;
    }

    public boolean eliminarBecas(List<Beca> becas) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();
        try {
            sesion.delete("eliminarBecas", becas);
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("No se puedieron eliminar la(s) beca(s) de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public boolean rechazarBeca(List<Beca> becas) {
        boolean ok = false;
        if(!becas.isEmpty()){
            SqlSession sesion = Config.abreSesion();
            try {
                sesion.update("rechazarBeca", becas);
                sesion.commit();
                ok = true;
            } catch (Exception e) {
                Notification.show("No se pudo rechazar la Beca " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
            } finally {
                sesion.close();
            }
        }
        return ok;
    }

    public boolean aceptarBeca(List<Beca> becas) {
        boolean ok = false;
        if(!becas.isEmpty()) {
            SqlSession sesion = Config.abreSesion();
            try {
                sesion.update("aceptarBeca", becas);
                sesion.commit();
                ok = true;
            } catch (Exception e) {
                Notification.show("No se pudo aceptar la Beca " + e.getMessage(), Notification.Type.ERROR_MESSAGE);
            } finally {
                sesion.close();
            }
        }
        return ok;
    }

}