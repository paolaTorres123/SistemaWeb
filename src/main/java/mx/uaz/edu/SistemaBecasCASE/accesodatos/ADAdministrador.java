package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;

import mx.uaz.edu.SistemaBecasCASE.modelos.Administrador;
import mx.uaz.edu.SistemaBecasCASE.utils.CadenaAleatoria;
import mx.uaz.edu.SistemaBecasCASE.utils.EnviarCorreo;
import mx.uaz.edu.SistemaBecasCASE.utils.Hash;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADAdministrador {

    public ADAdministrador() {

    }

    public boolean agregaAdministrador(Administrador administrador){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;

        try{
            sesion.insert("agregaAdministrador", administrador);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo registrar el administrador"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
        }finally {
            sesion.close();
        }
        return ok;
    }

    public boolean editaAdministrador(Administrador administrador){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("editaAdministrador", administrador);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar el administrador"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public boolean cambiaEstatusAdministradores(List<Administrador> administradores) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();

        try {
            administradores.forEach(usuario ->{
                Administrador us = (Administrador)usuario;
                sesion.update("cambiaEstatusAdministrador", us);
            });
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("No se puedieron editar lo(s) administradores(s) de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public Administrador buscaAdministrador(Administrador administrador){
        SqlSession sesion = Config.abreSesion();
        Administrador user = null;
        try{
            user = sesion.selectOne("buscaAdministrador", administrador);
        }catch (Exception e){
            Notification.show("No se pudo encontrar el administrador"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return user;
    }

    public List<Administrador> obtenerTodosAdministradoresAcInAc(){
        SqlSession sesion = Config.abreSesion();
        List<Administrador> administradores = null;
        try{
            administradores = sesion.selectList("obtenerTodosAdministradoresAcvInAcv");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los administradores de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return administradores;
    }

    public List<Administrador> obtenerTodosAdministradoresActivos(){
        SqlSession sesion = Config.abreSesion();
        List<Administrador> administradores = null;
        try{
            administradores = sesion.selectList("obtenerTodosAdministradoresActivos");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los administradores de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return administradores;
    }

    public List<Administrador> obtenerTodosAdministradoresExpirados(){
        SqlSession sesion = Config.abreSesion();
        List<Administrador> administradores = null;
        try{
            administradores = sesion.selectList("obtenerTodosAdministradoresExpirados");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los administradores de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return administradores;
    }
}