package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADAlumno;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADMunicipio;
import mx.uaz.edu.SistemaBecasCASE.modelos.Alumno;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;

import java.util.ArrayList;

public class AlumnoEdicionFormLogica extends AlumnoEdicionForm{
    private Binder<Alumno> binder;
    private Alumno usr;

    public AlumnoEdicionFormLogica(Alumno usr){
        this.usr=usr;
        binder = new Binder<Alumno>();
        binder.setBean(this.usr);
        enlazar();
    }

    private void enlazar(){

        binder.forField(tfNombre).asRequired("El nombre de Alumno es obligatorio")
                .bind(Alumno::getNombre,Alumno::setNombre);

        binder.forField(tfApPaterno).asRequired("El apellido paterno es obligatorio")
                .bind(Alumno::getAp_paterno,Alumno::setAp_paterno);

        binder.forField(tfApMaterno).asRequired("El correo es obligatorio")
                .bind(Alumno::getAp_materno,Alumno::setAp_materno);

        dfFechNac.setDateFormat("dd-MM-yyy");
        binder.forField(dfFechNac).asRequired("La fecha de nacimiento es obligatoria")
                .bind(Alumno::getFecha_nacLD,Alumno::setFecha_nacLD);

        binder.forField(tfEmail)
                .withValidator(new EmailValidator("El formato de correo es incorrecto"))
                .bind(Alumno::getEmail, Alumno::setEmail);

        binder.forField(tfTelefono).asRequired("El teléfono es obligatorio")
                .withValidator(telefono -> telefono.toString().length() <= 14,
                        "Un número tiene como máximo 14 dígitos")
                .bind(Alumno::getTelefono,Alumno::setTelefono);

        binder.forField(tfCurp).asRequired("La CURP es obligatoria")
                .withValidator(curp -> tfCurp.getValue().length() == 13 || tfCurp.getValue().length() == 10,
                        "Un número tiene como máximo 14 dígitos")
                .bind(Alumno::getCurp,Alumno::setCurp);

        cbMunicipio.setItems(new ArrayList<>(new ADMunicipio().obtenerTodosMunicipios()));
        binder.forField(cbMunicipio)
                .asRequired("El municipio es obligatorio")
                .bind(Alumno::getMunicipio, Alumno::setMunicipio);

        binder.forField(tfColonia).asRequired("La calle es obligatoria")
                .bind(Alumno::getColonia,Alumno::setColonia);

        binder.forField(tfCalle).asRequired("La calle es obligatoria")
                .bind(Alumno::getCalle,Alumno::setCalle);

        binder.forField(tfNumeroExt).asRequired("El número exterior es obligatorio")
                .withValidator(nex -> nex.length()<=5,"El número exterior es menor de 6 dígitos")
                .bind(Alumno::getNum_exterior,Alumno::setNum_exterior);

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
                .bind(Alumno::getCodigo_postalFormat,Alumno::setCodigo_postalFormat);

        btEdita.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (binder.validate().isOk()) {
                    ADAlumno adAlumno = new ADAlumno();
                    if(adAlumno.editaAlumno(usr)){
                        String mensaje = "Editado Correctamente";
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
        TabSheet tab = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-alumnos");
        tab.replaceComponent(tab.getTab(0).getComponent(),new AlumnoNuevoFormLogica());
        tab.getTab(0).setCaption("Alta");
        tab.replaceComponent(tab.getTab(1).getComponent(),new AlumnosLista());
        tab.setSelectedTab(1);
    }
}
