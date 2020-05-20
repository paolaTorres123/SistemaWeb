package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADCasa;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADSupervisor;
import mx.uaz.edu.SistemaBecasCASE.modelos.Casa;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;

public class FormCasaLogica extends FormCasaDesign {
    private Binder<Casa>binder;
    private Casa casa;
    private boolean edicion;
    private ADSupervisor adSupervisor;
    private ADCasa adCasa;

    public FormCasaLogica(){
        casa =new Casa();
        edicion =false;
        enlazar();
    }
    public FormCasaLogica(Casa casa){
        this.casa = casa;
        edicion=true;
        enlazar();
    }
    public void enlazar(){
        binder = new Binder<Casa>();
        binder.setBean(casa);
        adSupervisor=new ADSupervisor();
        adCasa = new ADCasa();

        binder.forField(tfnombreCasa)
                .asRequired("El nombre de la casa es requerido")
                .bind(Casa::getNombreCasa, Casa::setNombreCasa);

        binder.forField(tfcapacidadTotalCasa)
                .withValidator(capacidad -> capacidad.length()<=2,
                        "La capacidad total debe contener al menos 3 caracteres")
                .asRequired("La capacidad total de la casa es requerida")
                .withConverter(new StringToIntegerConverter("Debe ser numérico"))
                .bind(Casa::getCapacidadTotal, Casa::setCapacidadTotal);

        binder.forField(tfnumeroCuatosCasa)
                .withValidator(numero -> numero.length()<=2,
                        "El numero de cuatos debe contener almenos 1 caracter")
                .asRequired("El numero de cuartos de la casa es requerida")
                .withConverter(new StringToIntegerConverter("Debe ser numérico"))
                .bind(Casa::getNumeroCuartos, Casa::setNumeroCuartos);

        binder.forField(tfcalleCasa)
                .asRequired("La calle donde se encuentra la casa es requerida")
                .bind(Casa::getCalle, Casa::setCalle);

        binder.forField(tfcolniaCasa)
                .asRequired("La colonia es requerida")
                .bind(Casa::getColonia, Casa::setColonia);

        binder.forField(tfnumExteriorcasa)
                .withValidator(exterior -> exterior.length()>=1,
                        "El número exterior debe contener almenos 1 caracter")
                .withConverter(new StringToIntegerConverter("Debe ser numérico"))
                .bind(Casa::getNumExterior, Casa::setNumExterior);

        cboNombreSupervisor.setItems(adSupervisor.obtenerTodosSupervisores());
        binder.forField(cboNombreSupervisor)
                .asRequired("Se debe seleccionar un nombre de supervisor")
                .bind(Casa::getSupervisor,Casa::setSupervisor);

        binder.forField(tfCódigoPostalCasa)
                .withValidator(codigo -> codigo.length()==5,
                        "El código postal debe contener 5 caracteres")
                .withConverter(new StringToIntegerConverter("Debe ser numérico"))
                .asRequired("El código postal es requerido")
                .bind(Casa::getCodigoPostal, Casa::setCodigoPostal);

        if(edicion){
            btnAgregarCasa.setCaption("Actualizar");
            btnCancelarCasa.setVisible(false);

        }

        btnAgregarCasa.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean cambio=false;
                if(binder.validate().isOk()){
                    ADCasa adCasa =new ADCasa();
                    boolean ok = false;
                    String mensaje = "Casa corectamente agregada";
                    if(edicion){
                        ok =adCasa.editarCasa(casa);
                        mensaje="Casa actualizada";
                    }else{
                        ok=adCasa.agregaCasa(casa);
                    }
                    if(ok){
                        Notification.show(mensaje);
                        casa = new Casa();
                        binder.setBean(casa);
                        TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(),"tab-casa");
                        tabSheet.setSelectedTab(1);
                        tabSheet.replaceComponent(tabSheet.getTab(1).getComponent(),new CasaLista());
                    }


                }else{
                    Notification.show("Datos incorrectos","Verifica tu informacion",
                            Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        btnCancelarCasa.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                regresaPestaña();
            }
        });

    }

    private void regresaPestaña(){
        casa= null;
        TabSheet tab = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-casa");
        tab.replaceComponent(tab.getTab(1).getComponent(),new CasaLista());
        tab.setSelectedTab(1);
        casa= null;
    }

}
