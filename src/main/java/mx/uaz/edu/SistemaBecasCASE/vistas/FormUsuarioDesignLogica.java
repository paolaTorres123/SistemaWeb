package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADTipoUsuario;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADUsuario;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;
import mx.uaz.edu.SistemaBecasCASE.utils.Broadcaster;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import mx.uaz.edu.SistemaBecasCASE.utils.Hash;

public class FormUsuarioDesignLogica extends FormUsuarioDesign {

    Binder<Usuario> binder;
    String regexContra = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[{punct}])[A-Za-z\\d$@$!%*?&#.$($)$-$_]{8,32}[^'\\s]$";
    Usuario usuario;
    boolean edicion, ingresar;
    String password;
    ADTipoUsuario adTipoUsuario;

    public FormUsuarioDesignLogica(){
        usuario = new Usuario();
        edicion = false;
        ingresar = false;
        password = "";
        inicializaVista();
    }

    public FormUsuarioDesignLogica(Usuario usuario){
        this.usuario = usuario;
        edicion = true;
        ingresar = false;
        password = usuario.getContrasena();
        inicializaVista();
    }

    public FormUsuarioDesignLogica(boolean ingresar){
        usuario = new Usuario();
        edicion = false;
        this.ingresar = ingresar;
        password = "";
        inicializaVista();
    }




    public void inicializaVista(){
        binder = new Binder<Usuario>();
        adTipoUsuario = new ADTipoUsuario();
        binder.setBean(usuario);

        binder.forField(nombreUsuario)
                .asRequired("El usuario es requerido")
                .bind(Usuario::getNombre_usuario, Usuario::setNombre_usuario);

        if(edicion){
            binder.forField(contrasena).bind(Usuario::getContrasena, Usuario::setContrasena);
            binder.forField(contrasenaConf).bind(Usuario::getContrasena, Usuario::setContrasena);
        }else{
            binder.forField(contrasena)
                    .withValidator(new RegexpValidator("Contraseña inválida",regexContra))
                    .bind(Usuario::getContrasena, Usuario::setContrasena);
        }

        binder.forField(email)
                .withValidator(new EmailValidator("Formato de Email inválido!"))
                .bind(Usuario::getEmail, Usuario::setEmail);

        cbTipoUsuario.setItems(adTipoUsuario.obtenerTodosTipoUsuario());
        binder.forField(cbTipoUsuario)
                .bind(Usuario::getTipo_usuario, Usuario::setTipo_usuario);

        btnRegistrar.addClickListener(new Botones());
        btnCancelar.addClickListener(new Botones());

        if(edicion) {
            btnRegistrar.setCaption("Guardar");
            btnCancelar.setVisible(false);
        }
    }

    class Botones implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {

            if(clickEvent.getButton() == btnRegistrar){
                boolean cambio = false;
                if(!contrasena.getValue().equals(password) && password.length() > 7){
                    binder.forField(contrasena)
                            .withValidator(new RegexpValidator("Contraseña inválida",regexContra))
                            .bind(Usuario::getContrasena, Usuario::setContrasena);
                    cambio = true;
                }
                if(contrasena.getValue().equals(contrasenaConf.getValue())) {
                    if (binder.validate().isOk()) {
                        ADUsuario adUsuario = new ADUsuario();
                        boolean ok = false;
                        String mensaje = "Usuario correctamente registrado";
                        if (edicion) {
                            if (cambio)
                                usuario.setContrasena(Hash.sha1(usuario.getContrasena()));
                            ok = adUsuario.editarUsuario(usuario);
                            mensaje = "Usuario correctamente actualizado";
                        } else {
                            ok = adUsuario.agregaUsuario(usuario);
                        }
                        if (ok) {
                            //Notification.show(mensaje);
                            usuario = new Usuario();
                            binder.setBean(usuario);
                            contrasenaConf.setValue("");
                            if(!ingresar) {
                                TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-usuarios");
                                tabSheet.setSelectedTab(1);
                            }else if(ingresar){
                                UI.getCurrent().setContent(new Login());
                            }
                            Broadcaster.broadcast("usuarios");
                        }
                    } else
                        Notification.show("Datos incorrectos", "Verifica tu información", Notification.Type.ERROR_MESSAGE);
                }else{
                    Notification.show("Contraseñas incorrectas", "Las contraseñas no coinciden, favor de verificarlas", Notification.Type.ERROR_MESSAGE);
                }
            }else if (clickEvent.getButton() == btnCancelar){
                if(ingresar){
                    UI.getCurrent().setContent(new Login());
                }else{
                    regresaPestaña();
                }
            }

        }
        private void regresaPestaña(){
            usuario= null;
            TabSheet tab = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-usuarios");
            tab.replaceComponent(tab.getTab(1).getComponent(),new ListaUsuarios());
            tab.setSelectedTab(1);
            usuario= null;
        }
    }

}