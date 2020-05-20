package mx.uaz.edu.SistemaBecasCASE.utils;

import com.vaadin.server.VaadinSession;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;

public class ConfigVars {
    //public static final String HOST = "http://localhost:8080/";
    public static final String APP_NAME = "SistemaBecas";
    //public static final String HOST_APP = HOST + APP_NAME;
    //public static final String HOST_APP_FOTOS = HOST + APP_NAME + "/VAADIN/themes/mytheme/layouts/images";
    //public static final String HOST_APP_LOGOS = HOST + APP_NAME + "/VAADIN/themes/mytheme/layouts/images";

    public static final Usuario ADMINISTRADOR_LOGIN = (Usuario) VaadinSession.getCurrent().getAttribute("usuario");

}
