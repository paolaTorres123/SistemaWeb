package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.converter.StringToFloatConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADAlumno;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADEstatusEconomico;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADMunicipio;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADTipoAlumno;
import mx.uaz.edu.SistemaBecasCASE.modelos.Alumno;
import mx.uaz.edu.SistemaBecasCASE.modelos.Estatus;
import mx.uaz.edu.SistemaBecasCASE.modelos.TipoAlumno;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import mx.uaz.edu.SistemaBecasCASE.utils.CadenaAleatoria;

import java.util.ArrayList;

public class AlumnoNuevoFormLogica extends AlumnoNuevoForm {
    private Binder<Alumno> binder;
    private Alumno usr;

    public AlumnoNuevoFormLogica(){
        usr = new Alumno();
        binder = new Binder<Alumno>();
        tfCalf.setVisible(false);
        tfMatricula.setVisible(false);
        binder.setBean(usr);
        tfCalf.setValue("0");
        tfMatricula.setValue("0");
        enlazar();
    }

    private void enlazar(){

        binder.forField(tfNombre).asRequired("El nombre de Alumno es obligatorio")
                .bind(Alumno::getNombre,Alumno::setNombre);

        binder.forField(tfApPaterno).asRequired("El apellido paterno es obligatorio")
                .bind(Alumno::getAp_paterno,Alumno::setAp_paterno);

        binder.forField(tfApMaterno)
                .bind(Alumno::getAp_materno,Alumno::setAp_materno);

        dfFechNac.setDateFormat("dd-MM-yyyy");
        binder.forField(dfFechNac).asRequired("La fecha de nacimiento es obligatoria")
                .bind(Alumno::getFecha_nacLD,Alumno::setFecha_nacLD);

        binder.forField(tfEmail).asRequired("El correo es obligatorio")
                .withValidator(new EmailValidator("El formato de correo es incorrecto"))
                .bind(Alumno::getEmail, Alumno::setEmail);

        binder.forField(tfTelefono).asRequired("El teléfono es obligatorio")
                .withValidator(telefono -> telefono.length() <= 14,
                        "Un número tiene como máximo 14 dígitos")
                .bind(Alumno::getTelefono,Alumno::setTelefono);

        binder.forField(tfCurp).asRequired("La CURP es obligatoria")
            .withValidator(curp -> tfCurp.getValue().length() == 18,
                "Un número tiene como máximo 14 dígitos")
            .bind(Alumno::getCurp,Alumno::setCurp);

        cbTipoAlumno.setItems(new ArrayList<>(new ADTipoAlumno().obtenerTodosTipoAlumno()));
        binder.forField(cbTipoAlumno).asRequired("El tipo de usuario es obligatorio")
                .bind(Alumno::getTipo_alumno,Alumno::setTipo_alumno);

        binder.forField(tfMatricula).asRequired("La matricula debe ser proporcionada")
            .withValidator(mat -> tfMatricula.getValue().length()<=15,"La matrícula no debe ser mayor de 15 dígitos")
            .bind(Alumno::getMatricula,Alumno::setMatricula);

        binder.forField(tfCalf).asRequired("La calificación debe ser proporcionada")
                .withConverter(new StringToFloatConverter("Debe ser numérico"))
                .withValidator(mat -> tfCalf.getValue().length()<=4,"No debe ser mayor de 4 dígitos")
                .withValidator(calf -> calf <= 10 && calf >= 0,"La calificación es en escala del 1 al 10")
                .bind(Alumno::getPromedio_general,Alumno::setPromedio_general);

        cbEstatusEc.setItems(new ArrayList<>(new ADEstatusEconomico().obtenerTodosEstatusEconomicos()));
        binder.forField(cbEstatusEc).asRequired("El estatus económico del alumno debe ser proporcionado")
                .bind(Alumno::getEstatus_economico,Alumno::setEstatus_economico);

        binder.forField(taComentarios).withValidator(comentario -> comentario.length()<=400 && comentario.length() >=0,
                "El comentario no debe pasar de los 400 carácteres")
                .bind(Alumno::getComentario_adicional,Alumno::setComentario_adicional);

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

        btRegistra.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (binder.validate().isOk()) {
                    ADAlumno adAlumno = new ADAlumno();
                    usr.setEstatus(new Estatus(1,"Activo"));//1->ACTIVO, 2->NO ACTIVO
                    if(adAlumno.agregaAlumno(usr)){
                        String mensaje = "";
                        mensaje = "Agregado Correctamente";
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

        cbTipoAlumno.addValueChangeListener(new HasValue.ValueChangeListener<TipoAlumno>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<TipoAlumno> valueChangeEvent) {
                if (valueChangeEvent.getValue().getId_tipo_alumno()==2 ){
                    tfCalf.setVisible(true);
                    tfMatricula.setVisible(true);
                }else{
                    tfCalf.setVisible(false);
                    tfMatricula.setVisible(false);
                    tfCalf.setValue("0");
                    tfMatricula.setValue("0");
                }
            }
        });

        taComentarios.addValueChangeListener(new HasValue.ValueChangeListener<String>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<String> valueChangeEvent) {
                int caracteres = valueChangeEvent.getValue().length();
                if (caracteres>0){
                    lblNumCarCom.setValue("Quedan"+(400-caracteres)+".");
                }else{
                    lblNumCarCom.setValue("");
                }
            }
        });
    }

    private void regresaPestaña(){
        usr = null;
        TabSheet tab = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-alumnos");
        tab.replaceComponent(tab.getTab(1).getComponent(),new AlumnosLista());
        tab.setSelectedTab(1);
        usr = null;
    }
}
