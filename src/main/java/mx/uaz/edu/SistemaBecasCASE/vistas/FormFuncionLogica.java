package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADFuncion;
import mx.uaz.edu.SistemaBecasCASE.modelos.Funcion;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;

public class FormFuncionLogica extends FormFuncionDesign {
    private Binder<Funcion> binder;
    private Funcion funcion;
    private boolean edicion;
    private ADFuncion adFuncion;

    public FormFuncionLogica() {
        funcion = new Funcion();
        edicion = false;
        enlazar();
    }

    public FormFuncionLogica(Funcion funcion) {
        this.funcion = funcion;
        edicion = true;
        enlazar();
    }

    public void enlazar(){
        binder = new Binder<Funcion>();
        binder.setBean(funcion);
        adFuncion = new ADFuncion();

        binder.forField(tfNombreFuncion)
                .withValidator(nomFuncion -> nomFuncion.length() >= 3,
                        "Debe contener al menos 3 caracteres")
                .bind(Funcion::getFuncion, Funcion::setFuncion);

        if(edicion){
            btnAgregarFuncion.setCaption("Guardar");
            btnRegresarFuncion.setVisible(true);
        }

        btnAgregarFuncion.addClickListener(new Botones());
        btnRegresarFuncion.addClickListener(new Botones());
    }

    class Botones implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {

            if(clickEvent.getButton()==btnRegresarFuncion){
                TabResponsable tabfuncion =new TabResponsable();

                VerticalLayout verticalLayout = (VerticalLayout)BuscarComponentes.findComponentById(UI.getCurrent(),"vl-principal");
                verticalLayout.removeAllComponents();
                verticalLayout.addComponent(new TabResponsable());
            }

            if(clickEvent.getButton() == btnAgregarFuncion){
                if (binder.validate().isOk()) {

                    boolean ok = false;
                    String mensaje = "Función correctamente registrado";
                    if(edicion) {
                        ok = adFuncion.editarFuncion(funcion);
                        mensaje = "Función correctamente actualizado";
                    }else
                        ok = adFuncion.agregaFuncion(funcion);
                    if(ok) {
                        Notification.show(mensaje);
                        funcion= new Funcion();
                        binder.setBean(funcion);
                        TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(),"tab-funcion");

                        tabSheet.setSelectedTab(1);
                        tabSheet.replaceComponent(tabSheet.getTab(1).getComponent(), new ListaFunciones());
                    }
                }
                else
                    Notification.show("Datos incorrectos", Notification.Type.ERROR_MESSAGE);
            }

        }
    }

}
