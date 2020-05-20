package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADComedor;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADMunicipio;
import mx.uaz.edu.SistemaBecasCASE.modelos.Comedor;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;

import java.util.ArrayList;
import java.util.List;

public class FormComedorLogica extends FormComedor {

    private Binder<Comedor> binder;
    private Comedor comedor;
    private ADMunicipio adMunicipio;
    private ADComedor adComedor;
    private boolean edicion;

    public FormComedorLogica() {
        comedor = new Comedor();
        edicion = false;
        generarVista();
    }

    public FormComedorLogica(Comedor comedor) {
        this.comedor = comedor;
        edicion = true;
        generarVista();
    }

    public void generarVista(){
        binder = new Binder<Comedor>();
        binder.setBean(comedor);

        adMunicipio = new ADMunicipio();
        adComedor = new ADComedor();

        cboMunicipio.setItems(adMunicipio.obtenerTodosMunicipios());
        binder.forField(cboMunicipio)
                .asRequired("El Municipio es requerido")
                .bind(Comedor::getMunicipio, Comedor::setMunicipio);

        List<String> servicios = new ArrayList<>();
        servicios.add("Si");
        servicios.add("No");

        cboDesayuno.setItems(servicios);
        binder.forField(cboDesayuno)
                .asRequired("El servicio desayuno es requerido")
                .bind(Comedor::getDesayuno, Comedor::setDesayuno);
        cboComida.setItems(servicios);
        binder.forField(cboComida)
                .asRequired("El servicio de comida es requerido")
                .bind(Comedor::getComida, Comedor::setComida);
        cboCena.setItems(servicios);
        binder.forField(cboCena)
                .asRequired("El servicio de cena es requerido")
                .bind(Comedor::getCena, Comedor::setCena);


        binder.forField(txNombre)
                .withValidator(nombre -> nombre.length() >= 6,
                        "El nombre del comedor debe contener al menos 6 caracteres")
                .asRequired("El nombre es requerido")
                .bind(Comedor::getNombreComedor, Comedor::setNombreComedor);

        binder.forField(txCalle)
                .withValidator(calle -> calle.length() >= 4,
                        "La calle del comedor debe contener al menos 4 caracteres")
                .asRequired("La calle es requerida")
                .bind(Comedor::getCalle, Comedor::setCalle);

        binder.forField(txNumero)
                .bind(Comedor::getNumExterior, Comedor::setNumExterior);

        binder.forField(txColonia)
                .withValidator(colonia -> colonia.length() >= 4,
                        "La colonia del comedor debe contener al menos 4 caracteres")
                .asRequired("La colonia es requerida")
                .bind(Comedor::getColonia, Comedor::setColonia);

        String regexCod = "^(\\d{5})$";
        binder.forField(txCodPostal)
                .withValidator(new RegexpValidator("El código postal debe estar conformado por 5 dígitos",regexCod))
                .withValidator(cod -> cod.toString().length() == 5,
                        "El código postal debe contener 5 caracteres")
                .asRequired("El código postal es requerido")
                .bind(Comedor::getCodigoPostal, Comedor::setCodigoPostal);

        binder.forField(txTelefono)
                .bind(Comedor::getTelefono, Comedor::setTelefono);

        if(edicion){
            btnAgregar.setCaption("Guardar");
            btnCancelar.setVisible(false);
        }

        btnAgregar.addClickListener(new Botones());
        btnCancelar.addClickListener(new Botones());
    }

    class Botones implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {

            if(clickEvent.getButton() == btnCancelar){
                regresaPestaña();
            }

            if(clickEvent.getButton() == btnAgregar){

                if(txTelefono.getValue().length() > 0){
                    String regexTel = "^(\\d{10})$";
                    binder.forField(txTelefono)
                            .withValidator(new RegexpValidator("El teléfono debe estar conformado por 10 dígitos",regexTel))
                            .withValidator(tel -> tel.toString().length() == 10,
                                    "El número telefónico debe contener 10 caracteres")
                            .bind(Comedor::getTelefono, Comedor::setTelefono);
                }

                if (binder.validate().isOk()) {

                    boolean ok = false;
                    String mensaje = "Comedor correctamente registrado";
                    if(edicion) {
                        ok = adComedor.editarComedor(comedor);
                        mensaje = "Comedor correctamente actualizado";
                    }else
                        ok = adComedor.agregaComedor(comedor);
                    if(ok) {
                        Notification.show(mensaje);
                        comedor = new Comedor();
                        binder.setBean(comedor);
                        TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(),"tab-comedores");
                        tabSheet.setSelectedTab(1);
                        tabSheet.replaceComponent(tabSheet.getTab(1).getComponent(), new ListaComedores());
                    }
                }
                else
                    Notification.show("Datos incorrectos", Notification.Type.ERROR_MESSAGE);
            }

        }
        private void regresaPestaña(){
            comedor= null;
            TabSheet tab = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-comedores");
            tab.replaceComponent(tab.getTab(1).getComponent(),new ListaComedores());
            tab.setSelectedTab(1);
            comedor= null;
        }
    }

}