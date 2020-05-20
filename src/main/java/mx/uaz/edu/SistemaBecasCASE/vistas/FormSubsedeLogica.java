package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADFuncion;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADSubsede;
import mx.uaz.edu.SistemaBecasCASE.modelos.Funcion;
import mx.uaz.edu.SistemaBecasCASE.modelos.Subsede;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;

public class FormSubsedeLogica extends FormSubsedeDesign {
    private Binder<Subsede> binder;
    private Subsede subsede;
    private boolean edicion;
    private ADSubsede adSubsede;

    public FormSubsedeLogica() {
        subsede = new Subsede();
        edicion = false;
        enlazar();
    }

    public FormSubsedeLogica(Subsede subsede) {
        this.subsede = subsede;
        edicion = true;
        enlazar();
    }

    public void enlazar(){
        binder = new Binder<Subsede>();
        binder.setBean(subsede);
        adSubsede = new ADSubsede();

        binder.forField(tfNombreSubsede)
                .withValidator(nomSubsede -> nomSubsede.length() >= 3,
                        "Debe contener al menos 3 caracteres")
                .bind(Subsede::getNombreSubsede, Subsede::setNombreSubsede);

        if(edicion){
            btnAgregarSubsede.setCaption("Guardar");
            btnRegresarSubsede.setVisible(true);
        }

        btnAgregarSubsede.addClickListener(new Botones());
        btnRegresarSubsede.addClickListener(new Botones());
    }

    class Botones implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            if(clickEvent.getButton()==btnRegresarSubsede){
                TabResponsable tabfuncion =new TabResponsable();

                VerticalLayout verticalLayout = (VerticalLayout)BuscarComponentes.findComponentById(UI.getCurrent(),"vl-principal");
                verticalLayout.removeAllComponents();
                verticalLayout.addComponent(new TabResponsable());
            }


            if(clickEvent.getButton() == btnAgregarSubsede){
                if (binder.validate().isOk()) {

                    boolean ok = false;
                    String mensaje = "Subsede correctamente registrado";
                    if(edicion) {
                        ok = adSubsede.editarSubsede(subsede);
                        mensaje = "Subsede correctamente actualizado";
                    }else
                        ok = adSubsede.agregaSubsede(subsede);
                    if(ok) {
                        Notification.show(mensaje);
                        subsede= new Subsede();
                        binder.setBean(subsede);
                        TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(),"tab-subsedes");
                        tabSheet.setSelectedTab(1);
                        tabSheet.replaceComponent(tabSheet.getTab(1).getComponent(), new ListaSubsedes());
                    }
                }
                else
                    Notification.show("Datos incorrectos", Notification.Type.ERROR_MESSAGE);
            }

        }
    }

}
