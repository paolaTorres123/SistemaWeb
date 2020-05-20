package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Alumno;
import mx.uaz.edu.SistemaBecasCASE.modelos.ProgramaAcademico;
import mx.uaz.edu.SistemaBecasCASE.utils.CadenaAleatoria;
import mx.uaz.edu.SistemaBecasCASE.utils.EnviarCorreo;
import mx.uaz.edu.SistemaBecasCASE.utils.Hash;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADAlumno {

    public ADAlumno() {

    }

    public boolean agregaAlumno(Alumno usuario){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;

        try{
            //usuario.setCadena(new CadenaAleatoria().getCadenaAleatoria(45));
            //usuario.setContraseña((Hash.sha1(usuario.getContraseña())));
            sesion.insert("agregaAlumno", usuario);
            sesion.commit();
            enviarCorreo(usuario);
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo registrar el alumno"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public boolean editaAlumno(Alumno usuario){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("editaAlumno", usuario);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar el alumno"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public boolean actualizaCorreoAlumno(Alumno usuario){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("actualizaCorreoAlumno", usuario);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar correo del alumno"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    /*
    public boolean cambiaContraseñaAlumno(Alumno usuario){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            //usuario.setContraseña(Hash.sha1(usuario.getContraseña()));
            sesion.update("cambiaContraAlumno", usuario);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar la contraseña"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }
    */

    public boolean cambiaEstatusAlumnos(List<Alumno> usuarios) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();
        try {
            usuarios.forEach(alumno ->{
                sesion.update("cambiaEstatusAlumnos", (Alumno) alumno);
            });
            sesion.commit();
            ok = true;
        } catch (Exception e) {
            Notification.show("No se puedieron eliminar el(los) alumno(s) de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public Alumno buscaAlumnoSolicitud(Alumno usuario){
        SqlSession sesion = Config.abreSesion();
        Alumno user = null;
        try{
            user = sesion.selectOne("buscaAlumnoSolicitud", usuario);
        }catch (Exception e){
            Notification.show("No se pudo encontrar el usuario"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return user;
    }


    public Alumno buscaAlumno(Alumno usuario){
        SqlSession sesion = Config.abreSesion();
        Alumno user = null;
        try{
            user = sesion.selectOne("buscaAlumno", usuario);
        }catch (Exception e){
            Notification.show("No se pudo encontrar el usuario"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return user;
    }


    public List<Alumno> obtenerTodosAlumnos(){
        SqlSession sesion = Config.abreSesion();
        List<Alumno> usuarios = null;
        try{
            usuarios = sesion.selectList("obtenerTodosAlumnos");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los alumnos de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return usuarios;
    }

    public List<Alumno> obtenerTodosAlumnosActivos(){
        SqlSession sesion = Config.abreSesion();
        List<Alumno> usuarios = null;
        try{
            usuarios = sesion.selectList("obtenerTodosAlumnosActivos");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los alumnos de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return usuarios;
    }

    public List<Alumno> obtenerTodosAlumnosExpirados(){
        SqlSession sesion = Config.abreSesion();
        List<Alumno> usuarios = null;
        try{
            usuarios = sesion.selectList("obtenerTodosAlumnosExpirados");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los alumnos de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return usuarios;
    }

    public boolean confirmaCuenta(Alumno usuario) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();
        try {
            Alumno user =  sesion.selectOne("buscaAlumno", usuario);
            if (user != null) {
                //usuario.setConfirmado('C');//C -> CONFIRMADO, N -> NO CONFIRMADO
                sesion.update("editaAlumno", usuario);
                sesion.commit();
                ok = true;
            }
        }
        catch (Exception e) {
            Notification.show("No se pudo activar la cuenta"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public void enviarCorreo(Alumno usuario){
        EnviarCorreo enviarCorreo = new EnviarCorreo();
        /**
        if(enviarCorreo.sendMail(usuario.getNombre(),usuario.getAp_paterno(),usuario.getEmail(),usuario.getCadena())){
            Notification.show("Se envió el correo para activación de manera exitosa a: "+usuario.getEmail());
        }
         */
    }

}
