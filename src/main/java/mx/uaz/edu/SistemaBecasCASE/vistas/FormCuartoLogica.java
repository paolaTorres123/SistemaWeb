package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADCasa;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADCuarto;
import mx.uaz.edu.SistemaBecasCASE.modelos.Cuarto;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;

public class FormCuartoLogica extends FormCuartoDesign {
    private Binder<Cuarto> binder;
    private Cuarto cuarto;
    private boolean edicion;
    private ADCasa adCasa;
    private ADCuarto adCuarto;

    public FormCuartoLogica(){
        cuarto =new Cuarto();
        edicion =false;
        enlazar();
    }
    public FormCuartoLogica(Cuarto cuarto){
        cuarto = new Cuarto();
        edicion=true;
        enlazar();
    }
    public void enlazar(){
        binder = new Binder<Cuarto>();
        binder.setBean(cuarto);
        adCasa=new ADCasa();
        adCuarto=new ADCuarto();


        binder.forField(tfNum_Cuarto)
                .withValidator(numero -> numero.length()<=3,
                        "El número de cuarto debe contener almenos 1 carácter")
                .asRequired("El número de cuarto de la casa es requerida")
                .withConverter(new StringToIntegerConverter("Debe ser numérico"))
                .bind(Cuarto::getNum_cuarto, Cuarto::setNum_cuarto);

        binder.forField(tfCapacidad_Cuarto)
                .withValidator(capacidad -> capacidad.length()<=2,
                        "La capacidad del cuarto debe contener al menos 2 caracteres")
                .asRequired("La capacidad del cuarto es requerida")
                .withConverter(new StringToIntegerConverter("Debe ser numérico"))
                .bind(Cuarto::getCapacidad_cuarto, Cuarto::setCapacidad_cuarto);

        bcxNombreCasa.setItems(adCasa.obtenerTodasCasas());
        binder.forField(bcxNombreCasa)
                .asRequired("El nombre de la casa es requerido")
                .bind(Cuarto::getCasa,Cuarto::setCasa);

        if(edicion){
            btnAgregar.setCaption("Actualizar");
            btnCancelar.setVisible(false);

        }

        btnAgregar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean cambio=false;
                if(binder.validate().isOk()){
                    ADCuarto adCuarto =new ADCuarto();
                    boolean ok = false;
                    String mensaje = "Cuarto corectamente agregada";
                    if(edicion){
                        ok =adCuarto.editarCuarto(cuarto);
                        mensaje="Cuarto actualizada";
                    }else{
                        ok=adCuarto.agregaCuarto(cuarto);
                    }
                    if(ok){
                        Notification.show(mensaje);
                        cuarto = new Cuarto();
                        binder.setBean(cuarto);
                        TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(),"tab-cuarto");
                        tabSheet.setSelectedTab(1);
                        tabSheet.replaceComponent(tabSheet.getTab(1).getComponent(),new CuartoLista());
                    }


                }else{
                    Notification.show("Datos incorrectos","Verifica tu información",
                            Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        btnCancelar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                regresaPestaña();
            }
        });


    }
    private void regresaPestaña(){
        cuarto= null;
        TabSheet tab = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-responsables");
        tab.replaceComponent(tab.getTab(1).getComponent(),new CuartoLista());
        tab.setSelectedTab(1);
        cuarto= null;
    }

}
