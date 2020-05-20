package mx.uaz.edu.SistemaBecasCASE.accesodatos;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Alumno;
import mx.uaz.edu.SistemaBecasCASE.modelos.ProgramaAcademico;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ADProgramaAcademico {
    public ADProgramaAcademico() {
    }

    public List<ProgramaAcademico> obtenerTodosProgramaAcademico(){
        SqlSession sesion = Config.abreSesion();
        List<ProgramaAcademico> lista = null;
        try{
            lista = sesion.selectList("obtenerTodosProgramaAcademico");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los programas académicos de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return lista;
    }

    public ProgramaAcademico obtenerProgramaAcademico(ProgramaAcademico programaAcademico){
        SqlSession sesion = Config.abreSesion();
        ProgramaAcademico objeto = null;
        try{
            if (objeto.getNombre()!=null){
                objeto = sesion.selectOne("obtenerProgramaAcademicoNombre",programaAcademico);
            }else{
                objeto = sesion.selectOne("obtenerProgramaAcademicoId",programaAcademico);
            }
        }catch (Exception e){
            Notification.show("No se recuperar el programa académico de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return objeto;
    }

    public boolean editarProgramaAcademico(ProgramaAcademico objetoEdicion){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("editarProgramaAcademico", objetoEdicion);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo actualizar el programa académico "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public  boolean agregaProgramaAcademico(ProgramaAcademico objeto){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.insert("agregaProgramaAcademico", objeto);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo agregar el programa académico"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public boolean cambiaEstatusProgramaAademico(ProgramaAcademico objeto){
        SqlSession sesion = Config.abreSesion();
        boolean ok = false;
        try{
            sesion.update("cambiaEstatusProgramaAademico", objeto);
            sesion.commit();
            ok = true;
        }catch (Exception e){
            Notification.show("No se pudo cambiar el estatus del programa académico"+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return ok;
    }

    public List<ProgramaAcademico> obtenerTodosProgramaAcademicoExpirado(){
        SqlSession sesion = Config.abreSesion();
        List<ProgramaAcademico> lista = null;
        try{
            lista = sesion.selectList("obtenerTodosProgramaAcademicoExpirado");
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los programas académicos de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return lista;
    }

    public ProgramaAcademico obtenerProgramaAlumno(Alumno alumno){
        SqlSession sesion = Config.abreSesion();
        ProgramaAcademico programa = null;
        try{
            programa = sesion.selectOne("buscaProgramaAlumno", alumno);
        }catch (Exception e){
            Notification.show("No se pudieron recuperar los alumnos de la BD "+e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }finally {
            sesion.close();
        }
        return programa;
    }
}
