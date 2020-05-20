package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADMunicipio;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADAdministrador;
import mx.uaz.edu.SistemaBecasCASE.modelos.Administrador;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;

import java.util.ArrayList;

public class AdministradorFormLogica extends AdministradorForm {
    private Binder<Administrador> binder;
    private Administrador usr;

    public AdministradorFormLogica(Administrador usr){
        this.usr = usr;
        enlazar();
    }

    private void enlazar(){
        binder = new Binder<Administrador>();
        binder.setBean(usr);

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
                .withValidator(telefono -> telefono.toString().length() <= 14,
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
                .withValidator(cpost -> cpost.length()==5,"El código postal es de 5 dígitos")
                .withValidator(cpost -> {
                    try {
                        Integer.parseInt(tfCodigoPostal.getValue());
                    }catch (Exception e){
                        return false;
                    }
                    return true;
                },"El código postal debe ser numérico")
                .bind(Administrador::getCodigo_postalSFormat, Administrador::setCodigo_postalSFormat);

        btOk.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (binder.validate().isOk()) {
                    ADAdministrador adAdministrador = new ADAdministrador();
                    String mensaje = "";
                    if(adAdministrador.editaAdministrador(usr)){
                        mensaje = "Modificado Correctamente";
                        Notification.show(mensaje);
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
        binder.removeBean();
        TabSheet tab = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-admins");
        tab.replaceComponent(tab.getTab(0).getComponent(),new AdministradorNuevoFormLogica());
        tab.getTab(0).setCaption("Alta");
        tab.replaceComponent(tab.getTab(1).getComponent(),new AdministradoresLista());
        tab.setSelectedTab(1);
    }
}
