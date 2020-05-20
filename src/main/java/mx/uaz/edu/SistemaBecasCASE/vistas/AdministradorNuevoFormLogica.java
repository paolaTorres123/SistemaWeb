package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADMunicipio;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADTipoUsuario;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADAdministrador;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADUsuario;
import mx.uaz.edu.SistemaBecasCASE.modelos.Administrador;
import mx.uaz.edu.SistemaBecasCASE.modelos.Estatus;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;

import java.util.ArrayList;

public class AdministradorNuevoFormLogica extends AdministradorNuevoForm {
    private Binder<Administrador> binder;
    private Administrador usr;
    private Usuario usuario;
    private ADUsuario adUsuario;

    public AdministradorNuevoFormLogica(){
        usr = new Administrador();
        binder = new Binder<Administrador>();
        binder.setBean(usr);
        enlazar();
    }

    public AdministradorNuevoFormLogica(Usuario usuario){
        usr = new Administrador();
        binder = new Binder<Administrador>();
        binder.setBean(usr);
        this.usuario = usuario;
        enlazar();
    }

    private void enlazar(){
        adUsuario = new ADUsuario();

        binder.forField(tfNombre).asRequired("El nombre de Administrador es obligatorio")
                .bind(Administrador::getNombre, Administrador::setNombre);

        binder.forField(tfApPaterno).asRequired("El apellido paterno es obligatorio")
                .bind(Administrador::getAp_paterno, Administrador::setAp_paterno);

        binder.forField(tfApMaterno)
                .bind(Administrador::getAp_materno, Administrador::setAp_materno);

        dfFechNac.setDateFormat("dd-MM-yyy");
        binder.forField(dfFechNac).asRequired("La fecha de nacimiento es obligatoria")
                .bind(Administrador::getFecha_nacLD, Administrador::setFecha_nacLD);

        binder.forField(tfEmail).asRequired("El correo es obligatorio")
                .withValidator(new EmailValidator("El formato de correo es incorrecto"))
                .bind(Administrador::getEmail, Administrador::setEmail);

        binder.forField(tfTelefono).asRequired("El teléfono es obligatorio")
                .withValidator(telefono -> telefono.length() <= 14,
                        "Un número tiene como máximo 14 dígitos")
                .bind(Administrador::getTelefono, Administrador::setTelefono);

        cbMunicipio.setItems(new ArrayList<>(new ADMunicipio().obtenerTodosMunicipios()));
        binder.forField(cbMunicipio)
                .asRequired("El municipio es obligatorio")
                .bind(Administrador::getMunicipio, Administrador::setMunicipio);

        binder.forField(tfColonia).asRequired("La colonia es obligatori1")
                .bind(Administrador::getColonia, Administrador::setColonia);

        binder.forField(tfCalle).asRequired("La calle es obligatoria")
                .bind(Administrador::getCalle, Administrador::setCalle);

        binder.forField(tfNumeroExt).asRequired("El número exterior es obligatorio")
                .bind(Administrador::getNum_exterior, Administrador::setNum_exterior);

        binder.forField(tfCodigoPostal).asRequired("El código postal es obligatorio")
                .withConverter(new StringToIntegerConverter("Debe ser numérico"))
                .bind(Administrador::getCodigo_postal, Administrador::setCodigo_postal);

        btRegistra.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (binder.validate().isOk()) {
                    ADAdministrador adAdministrador = new ADAdministrador();
                    usr.setEstatus(new Estatus(1,"No Activo"));//1->ACTIVO, 2->NO ACTIVO
                    //usr.setCadena(new CadenaAleatoria().getCadenaAleatoria(45));
                    //usr.setConfirmado('N');//N->NO, C->CONFIRMADO
                    if(usuario != null){
                        usr.setId_usuario(usuario.getId_usuario());
                        //usr.setTipo_usuario(usuario.getTipo_usuario());
                    }
                    if (adAdministrador.agregaAdministrador(usr)) {
                        String mensaje = "";
                        if(usuario != null){
                            if(adUsuario.confirmaCuenta(usuario)){
                                mensaje = "Cuenta confirmada Correctamente";
                                Notification.show(mensaje);
                                UI.getCurrent().setContent(new Login());
                            }else{
                                mensaje = "Agregado Correctamente";
                            }
                        }else{
                            mensaje = "Agregado Correctamente";
                        }
                        Notification.show(mensaje);
                        if(usuario == null)
                            regresaPestaña();
                    }
                }
                else{
                    Notification.show("Verifica los datos",Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        btCancelar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                regresaPestaña();
            }
        });
    }

    private void regresaPestaña(){
        usr = null;
        TabSheet tab = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-admins");
        tab.replaceComponent(tab.getTab(1).getComponent(),new AdministradoresLista());
        tab.setSelectedTab(1);
    }
}
