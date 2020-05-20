package mx.uaz.edu;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.*;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADUsuario;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;
import mx.uaz.edu.SistemaBecasCASE.utils.Broadcaster;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import mx.uaz.edu.SistemaBecasCASE.vistas.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Viewport("width=device-width, initial-scale=1")
@StyleSheet({
        "vaadin://css/bootstrap.min.css",
        "vaadin://css/font-awesome.min.css",
        "vaadin://css/adminpro-custon-icon.css",
        "vaadin://css/meanmenu.min.css",
        "vaadin://css/jquery.mCustomScrollbar.min.css",
        "vaadin://css/animate.css",
        "vaadin://css/bootstrap-table.css",
        "vaadin://css/bootstrap-editable.css",
        "vaadin://css/normalize.css",
        "vaadin://css/c3.min.css",
        "vaadin://css/all-type-forms.css",
        "vaadin://css/style.css",
        "vaadin://css/responsive.css",
        "vaadin://css/estilos.css",
        "https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i,800",
})

@JavaScript({
        "vaadin://js/modernizr-2.8.3.min.js",
        "vaadin://js/jquery-1.11.3.min.js",
        "vaadin://js/bootstrap.min.js",
        "vaadin://js/jquery.meanmenu.js",
        "vaadin://js/jquery.mCustomScrollbar.concat.min.js",
        "vaadin://js/jquery.sticky.js",
        "vaadin://js/jquery.scrollUp.min.js",
        "vaadin://js/jquery.counterup.min.js",
        "vaadin://js/waypoints.min.js",
        "vaadin://js/counterup-active.js",
        "vaadin://js/jquery.peity.min.js",
        "vaadin://js/peity-active.js",
        "vaadin://js/jquery.sparkline.min.js",
        "vaadin://js/sparkline-active.js",
        "vaadin://js/Chart.min.js",
        "vaadin://js/flot-active.js",
        "vaadin://js/raphael.min.js",
        "vaadin://js/jquery.mapael.js",
        "vaadin://js/france_departments.js",
        "vaadin://js/world_countries.js",
        "vaadin://js/usa_states.js",
        "vaadin://js/map-active.js",
        "vaadin://js/bootstrap-table.js",
        "vaadin://js/tableExport.js",
        "vaadin://js/data-table-active.js",
        "vaadin://js/bootstrap-table-editable.js",
        "vaadin://js/bootstrap-editable.js",
        "vaadin://js/bootstrap-table-resizable.js",
        "vaadin://js/colResizable-1.5.source.js",
        "vaadin://js/bootstrap-table-export.js",
        "vaadin://js/main.js",

})
public class MyUI extends UI implements Broadcaster.BroadcastListener{

    @Override
    protected void init(VaadinRequest vaadinRequest)  {
        String cadena = null;
        String usuario = null;

        try{
            cadena = vaadinRequest.getParameter("user");
            usuario = vaadinRequest.getParameter("logs");
        }catch (Exception e){
            Notification.show("Parámetros no válidos");
        }
        if(cadena != null && usuario != null){
            Usuario user = new ADUsuario().buscaUsuarioConfirm(new Usuario(usuario, cadena));
            if(user != null){
                switch (user.getTipo_usuario().getTipo()) {
                    case "COORDINADOR CASE":
                        setContent(new AdministradorNuevoFormLogica(user));
                        break;
                    case "RESPONSABLE PROGRAMA DE BECAS":
                        setContent(new AdministradorNuevoFormLogica(user));
                        break;
                    case "RESPONSABLE SUBSEDE CASE":
                        setContent(new FormResponsableLogica(user));
                        break;
                    case "SUPERVISOR CASE":
                        setContent(new FormSupervisorLogica(user));
                        break;
                }
            }else{
                Notification.show("No se pudo activar tu cuenta",
                        "Puede ser que el link no sea correcto, contacta al administrador",
                        Notification.Type.WARNING_MESSAGE);
            }
        }else{
            Usuario user = (Usuario) VaadinSession.getCurrent().getAttribute("usuario");
            if(user != null){
                Layout layout = (Layout)VaadinSession.getCurrent().getAttribute("formulario");
                Main principal = new Main(user.getTipo_usuario().getId_tipo_usuario());
                if(layout != null)
                    principal.agregaContenido(layout);
                setContent(principal);
            }else{
                setContent(new Login());
            }
        }
        Broadcaster.register(this);
    }

    @Override
    public void receiveBroadcast(String message) {

        access(new Runnable() {
            @Override
            public void run() {
                switch (message){
                    case "usuarios":
                        ListaUsuarios usuariosLista = (ListaUsuarios) BuscarComponentes.findComponentById(UI.getCurrent(),"listaUsuarios");
                        if(usuariosLista != null)
                            usuariosLista.actualizaGrid();
                        break;
                }
                Notification.show(message, Notification.Type.TRAY_NOTIFICATION);
                //UI.getCurrent().push();
            }
        });
    }

    @Override
    public void detach() {
        Broadcaster.unregister(this);
        super.detach();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
