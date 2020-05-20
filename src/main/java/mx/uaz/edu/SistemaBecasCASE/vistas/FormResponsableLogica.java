package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.*;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.*;
import mx.uaz.edu.SistemaBecasCASE.modelos.Funcion;
import mx.uaz.edu.SistemaBecasCASE.modelos.Responsable;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;

public class FormResponsableLogica extends FromResponsableDesign {

    private Binder<Responsable> binder;
    private Responsable responsable;
    private Funcion funcion;
    private boolean edicion;
    private ADFuncion adFuncion;
    private ADSubsede adSubsede;
    private ADEstatus adEstatus;
    private ADResponsable adResponsable;
    private Usuario usuario;
    private ADUsuario adUsuario;


    public FormResponsableLogica() {
        responsable = new Responsable();
        edicion = false;
        enlazar();
    }

    public FormResponsableLogica(Responsable responsable) {
        this.responsable = responsable;
        edicion = true;
        enlazar();
    }

    public FormResponsableLogica(Usuario usuario) {
        responsable = new Responsable();
        edicion = false;
        this.usuario = usuario;
        enlazar();
    }

    public void enlazar(){
        binder = new Binder<Responsable>();
        binder.setBean(responsable);

        adUsuario = new ADUsuario();
        adFuncion = new ADFuncion();
        adSubsede= new ADSubsede();
        adEstatus=new ADEstatus();
        adResponsable=new ADResponsable();


        binder.forField(tfNombre)
                .withValidator(titulo -> titulo.length() >= 3,
                        "Debe contener al menos 3 caracteres")
                .asRequired("El nombre es requerido")
                .bind(Responsable::getNombreResponsable, Responsable::setNombreResponsable);


        binder.forField(tfaPaterno)
                .withValidator(apellido -> apellido.length() >= 3 && apellido.length() <= 40,
                        "Debe contener al menos 3 caracteres y no más de 40.")
                .asRequired("El apellido paterno es requerido")
                .bind(Responsable::getApPaterno, Responsable::setApPaterno);


        binder.forField(tfaMaterno)
                .withValidator(apaterno -> apaterno.length() <= 40,
                        "Debe contener menos de  40 caracteres.")
                .bind(Responsable::getApMaterno, Responsable::setApMaterno);

        binder.forField(tfEmail)
                .withValidator(new EmailValidator("Email no valido"))
                .bind(Responsable::getEmail,Responsable::setEmail);

        String regexTel = "^(\\d{10})$";

        binder.forField(tfTelefono)
                .withValidator(new RegexpValidator("El teléfono debe estar conformado por 10 dígitos",regexTel))
                .withValidator(tel -> tel.toString().length() == 10,
                        "El número telefónico debe contener 10 caracteres")
                .bind(Responsable::getTelefono, Responsable::setTelefono);


        cboEstatus.setItems(adEstatus.obtenerEstatusSimple());
        binder.forField(cboEstatus)
                .asRequired("El estatus es requerido")
                .bind(Responsable::getEstatus, Responsable::setEstatus);

        cboFuncion.setItems(adFuncion.obtenerTodasFunciones());
        binder.forField(cboFuncion)
                .asRequired("La funcion es requerido")
                .bind(Responsable::getFuncion, Responsable::setFuncion);

        cboSubsede.setItems(adSubsede.obtenerTodasSubsedes());
        binder.forField(cboSubsede)
                .asRequired("La subsede es requerido")
                .bind(Responsable::getSubsede, Responsable::setSubsede);

        if(edicion){
            btnAgregar.setCaption("Guardar");
            btnCancelar.setVisible(false);
        }

        btnAgregar.addClickListener(new Botones());
        btnAgregarFuncion.addClickListener(new Botones());
        btnAgregarSubsede.addClickListener(new Botones());
        btnCancelar.addClickListener(new Botones());
    }

    class Botones implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            if (clickEvent.getButton()==btnAgregarFuncion){
                TabFuncion tabfuncion =new TabFuncion();

                if (clickEvent.getButton()==btnCancelar){
                    regresaPestaña();
                }



                    VerticalLayout verticalLayout = (VerticalLayout)BuscarComponentes.findComponentById(UI.getCurrent(),"vl-principal");
                verticalLayout.removeAllComponents();
                verticalLayout.addComponent(new TabFuncion());
            }

            if (clickEvent.getButton()==btnAgregarSubsede){
                TabFuncion tabfuncion =new TabFuncion();

                VerticalLayout verticalLayout = (VerticalLayout)BuscarComponentes.findComponentById(UI.getCurrent(),"vl-principal");
                verticalLayout.removeAllComponents();
                verticalLayout.addComponent(new TabSubsede());
            }



            if(clickEvent.getButton() == btnAgregar){
                if (binder.validate().isOk()) {

                    boolean ok = false;
                    String mensaje = "Responsable correctamente registrado";
                    if(edicion) {
                        ok = adResponsable.editarResponsable(responsable);
                        mensaje = "Responsable correctamente actualizado";
                    }else {
                        if(usuario != null){
                            responsable.setId_usuario(usuario.getId_usuario());
                        }
                        ok = adResponsable.agregaResponsable(responsable);
                    }
                    if(ok) {
                        if(usuario != null){
                            if(adUsuario.confirmaCuenta(usuario)){
                                mensaje = "Cuenta confirmada correctamente";
                                Notification.show(mensaje);
                                UI.getCurrent().setContent(new Login());
                            }

                        }else{
                            Notification.show(mensaje);
                            TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(),"tab-responsable");
                            tabSheet.setSelectedTab(1);
                            tabSheet.replaceComponent(tabSheet.getTab(1).getComponent(), new ListaResponsables());
                        }
                        responsable= new Responsable();
                        binder.setBean(responsable);
                    }
                }
                else
                    Notification.show("Datos incorrectos", Notification.Type.ERROR_MESSAGE);
            }

        }
        private void regresaPestaña(){
            responsable= null;
            TabSheet tab = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-cuarto");
            tab.replaceComponent(tab.getTab(1).getComponent(),new ListaResponsables());
            tab.setSelectedTab(1);
            responsable= null;
        }
    }
}
