package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;
import mx.uaz.edu.SistemaBecasCASE.utils.CadenaAleatoria;
import mx.uaz.edu.SistemaBecasCASE.utils.EnviarCorreo;
import mx.uaz.edu.SistemaBecasCASE.utils.Hash;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class ADUsuario {

    public Usuario buscaUsuario(Usuario usuario){
        SqlSession sesion = Config.abreSesion();
        Usuario user = null;
        try{
            usuario.setContrasena(Hash.sha1(usuario.getContrasena()));
            user = sesion.selectOne("buscaUsuario", usuario);
        }catch (Exception e){
        }finally {
            sesion.close();
        }
        return user;
    }

    public boolean agregaUsuario(Usuario usuario){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            usuario.setConfirmado("N");
            usuario.setCadena(new CadenaAleatoria().getCadenaAleatoria(30));
            usuario.setContrasena(Hash.sha1(usuario.getContrasena()));
            sesion.insert("agregaUsuario", usuario);
            sesion.commit();
            enviarCorreo(usuario);
            ok = true;
        }catch (Exception e){
        }finally {
            sesion.close();
        }
        return ok;
    }

    public boolean editarUsuario(Usuario usuario){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("editaUsuario", usuario);
            sesion.commit();
            ok = true;
        }catch (Exception e){

        }finally {
            sesion.close();
        }
        return ok;
    }

    public boolean eliminarUsuarios(List<Usuario> usuarios) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();
        try {
            int resultado = sesion.delete("eliminarUsuarios", usuarios);
            sesion.commit();
            if(resultado >= 1){
                ok = true;
            }
        } catch (Exception e) {
        }
        finally {
            sesion.close();
        }
        return ok;
    }

    public List<Usuario> obtenerTodosUsuarios(){
        SqlSession sesion = Config.abreSesion();
        List<Usuario> usuarios = null;
        try{
            usuarios = sesion.selectList("obtenerTodosUsuarios");
        }catch (Exception e){
        }finally {
            sesion.close();
        }
        return usuarios;
    }

    public Usuario buscaUsuarioConfirm(Usuario usuario){
        SqlSession sesion = Config.abreSesion();
        Usuario user = null;
        try{
            user = sesion.selectOne("buscaUsuarioConfirm", usuario);
        }catch (Exception e){
        }finally {
            sesion.close();
        }
        return user;
    }

    public boolean confirmaCuenta(Usuario user) {
        boolean ok = false;
        SqlSession sesion = Config.abreSesion();
        try {
            if (user != null) {
                sesion.update("confirmaCuenta", user);
                sesion.commit();
                ok = true;
            }
        }
        catch (Exception e) {
        }
        finally {
            sesion.close();
        }
        return ok;
    }


    public void enviarCorreo(Usuario usuario){
        EnviarCorreo enviarCorreo = new EnviarCorreo();
        if(enviarCorreo.sendMail(usuario)){
            Notification.show("Se envió el correo para activación de manera exitosa a: "+usuario.getEmail());
        }
    }

}