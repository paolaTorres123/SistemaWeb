package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADComedor;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADMunicipio;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADSupervisor;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADUsuario;
import mx.uaz.edu.SistemaBecasCASE.modelos.Comedor;
import mx.uaz.edu.SistemaBecasCASE.modelos.Supervisor;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;

import java.util.ArrayList;
import java.util.List;

public class FormSupervisorLogica extends FormSupervisor {

    private Binder<Supervisor> binder;
    private Supervisor supervisor;
    private ADSupervisor adSupervisor;
    private boolean edicion;
    private Usuario usuario;
    private ADUsuario adUsuario;

    public FormSupervisorLogica(){
        supervisor = new Supervisor();
        edicion = false;
        generarVista();
    }

    public FormSupervisorLogica(Supervisor supervisor){
        this.supervisor = supervisor;
        edicion = true;
        generarVista();
    }

    public FormSupervisorLogica(Usuario usuario){
        supervisor = new Supervisor();
        edicion = false;
        this.usuario = usuario;
        generarVista();
    }

    public void generarVista(){
        binder = new Binder<Supervisor>();
        binder.setBean(supervisor);
        adUsuario = new ADUsuario();
        adSupervisor = new ADSupervisor();

        binder.forField(tfNombreSupervisor)
                .asRequired("El nombre del supervisor es requerido")
                .withValidator(nombre -> nombre.length() >= 4 && nombre.length() <= 100,
                        "El nombre del supervisor debe contener al menos 4 caracteres y no más de 100 caracteres")
                .bind(Supervisor::getNombreSupervisor, Supervisor::setNombreSupervisor);


        binder.forField(tfApPaterno)
                .asRequired("El apellido paterno del supervisor es requerido")
                .withValidator(apellidoPaterno -> apellidoPaterno.length() >= 2 && apellidoPaterno.length() <= 60,
                        "El apellido paterno debe contener al menos 2 caracteres y no más de 60 caracteres")
                .bind(Supervisor::getApPaterno, Supervisor::setApPaterno);

        binder.forField(tfApMaterno)
                //.asRequired("El apellido paterno del supervisor es requerido")
                .withValidator(apellidoMaterno -> apellidoMaterno.length() <= 60,
                        "El apellido materno debe contener menos de 60 caracteres")
                .bind(Supervisor::getApMaterno, Supervisor::setApMaterno);

        String regexTelefono = "^(\\d{10})$";

        binder.forField(tfTelefonoCelular)
                .asRequired("El número de celular del supervisor es requerido")
                .withValidator(new RegexpValidator("El teléfono debe estar conformado por 10 dígitos",regexTelefono))
                .withValidator(telefono -> telefono.toString().length() == 10,
                        "El número telefónico debe contener 10 caracteres")
                .bind(Supervisor::getTelefonoCelular, Supervisor::setTelefonoCelular);

        binder.forField(tfEmailSupervisor)
                .asRequired("El correo electrónico del supervisor es requerido")
                .withValidator(email -> email.length() >= 2 && email.length() <= 320,
                        "El correo electrónico debe contener menos de 320 caracteres")
                .withValidator(new EmailValidator("El formato de correo es incorrecto"))
                .bind(Supervisor::getEmailSupervisor, Supervisor::setEmailSupervisor);

        if(edicion){
            btnAgregar.setCaption("Guardar");
            btnCancelar.setVisible(false);
        }

        btnAgregar.addClickListener(new FormSupervisorLogica.Botones());
        btnCancelar.addClickListener(new FormSupervisorLogica.Botones());
    }

    class Botones implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            if (clickEvent.getButton()==btnCancelar){
                regresaPestaña();
            }


            if(clickEvent.getButton() == btnAgregar){
                if (binder.validate().isOk()) {

                    boolean ok = false;
                    String mensaje = "Supervisor correctamente registrado";
                    if(edicion) {
                        ok = adSupervisor.editarSupervisor(supervisor);
                        mensaje = "Supervisor correctamente actualizado";
                    }else {
                        if(usuario != null){
                            supervisor.setId_usuario(usuario.getId_usuario());
                        }
                        ok = adSupervisor.agregaSupervisor(supervisor);
                    }
                    if(ok) {
                        if(usuario != null){
                            if(adUsuario.confirmaCuenta(usuario)){
                                mensaje = "Cuenta confirmada correctamente";
                                Notification.show(mensaje);
                                UI.getCurrent().setContent(new Login());
                            }
                        } else {
                            Notification.show(mensaje);
                            TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-supervisores");
                            tabSheet.setSelectedTab(1);
                            tabSheet.replaceComponent(tabSheet.getTab(1).getComponent(), new ListaSupervisores());
                        }
                        supervisor = new Supervisor();
                        binder.setBean(supervisor);
                    }
                }
                else
                    Notification.show("Datos incorrectos", Notification.Type.ERROR_MESSAGE);
            }

        }
        private void regresaPestaña(){
            supervisor= null;
            TabSheet tab = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-supervisores");
            tab.replaceComponent(tab.getTab(1).getComponent(),new ListaSupervisores());
            tab.setSelectedTab(1);
            supervisor= null;
        }
    }
}
