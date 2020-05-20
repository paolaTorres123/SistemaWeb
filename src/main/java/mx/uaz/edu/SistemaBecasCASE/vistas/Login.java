package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADUsuario;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;

public class Login extends  CustomLayout{

    private TextField tfUsuario;
    private PasswordField pfContra;
    private Button btnIniciar, btnRegistrar,btnSolicitar;


    Usuario user;

    public Login() {
        setPrimaryStyleName("principal");
        setTemplateName("login");
        setSizeFull();
        setResponsive(true);

        user = new Usuario();
        Binder<Usuario> binder = new Binder<Usuario>();
        binder.setBean(user);

        tfUsuario = new TextField();
        tfUsuario.setPlaceholder("Usuario");
        addComponent(tfUsuario,"usuario");


        pfContra = new PasswordField();
        pfContra.setPlaceholder("Contraseña");
        addComponent(pfContra,"contraseña");


        btnIniciar = new Button("Iniciar Sesión");
        btnIniciar.setPrimaryStyleName("btn btn-primary");

        btnRegistrar = new Button("Registrar");
        btnRegistrar.setPrimaryStyleName("btn btn-success");

        btnSolicitar= new Button("Solicitar Beca");
        btnSolicitar.setPrimaryStyleName("btn btn-warning");
        btnSolicitar.setStyleName("btn-solicitar");
        btnIniciar.setStyleName("btn-solicitar");
        btnRegistrar.setStyleName("btn-solicitar");


        binder.forField(tfUsuario)
                .asRequired("El usuario es requerido")
                .bind(Usuario::getNombre_usuario, Usuario::setNombre_usuario);

        String regexContra = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[{punct}])[A-Za-z\\d$@$!%*?&#.$($)$-$_]{8,32}[^'\\s]$";

        binder.forField(pfContra)
                .withValidator(new RegexpValidator("Contraseña inválida",regexContra))
                .bind(Usuario::getContrasena, Usuario::setContrasena);

        btnSolicitar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().setContent(new Main(0));
            }
        });


        btnIniciar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                ADUsuario adUsuario = new ADUsuario();
                Usuario usuario = adUsuario.buscaUsuario(user);

                if( usuario != null){
                    if(usuario.getConfirmado().equals("C")){
                        VaadinSession.getCurrent().getSession().setMaxInactiveInterval(350);
                        VaadinSession.getCurrent().setAttribute("usuario",usuario);
                        UI.getCurrent().setContent(new Main(usuario.getTipo_usuario().getId_tipo_usuario()));
                    }else {
                        Notification.show("Debes activar tu cuenta, revisa tu correo",
                                Notification.Type.WARNING_MESSAGE);
                    }
                }else{
                    Notification.show("Datos incorrectos", "Verifica tu información",
                    Notification.Type.ERROR_MESSAGE);
                }

            }
        });

        HorizontalLayout hlB = new HorizontalLayout();
        //hlB.addComponents(btnIniciar,btnRegistrar);
        this.addComponent(btnIniciar,"botones");
        this.addComponent(btnRegistrar,"botones2");
        this.addComponent(btnSolicitar,"solicitar");

        btnRegistrar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().setContent(new FormUsuarioDesignLogica(true));
            }
        });


    }

}