package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.mysql.cj.util.StringUtils;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADCasa;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADEstatus;
import mx.uaz.edu.SistemaBecasCASE.modelos.Casa;
import mx.uaz.edu.SistemaBecasCASE.modelos.Estatus;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CasaLista extends VerticalLayout {
    private Grid<Casa>gridCasa;
    private Button btnEliminar;
    private Button btnDesactivar;
    private Button btnReactivar;
    private ADCasa adCasa;
    private ADEstatus adEstatus;
    private ComboBox<Estatus> estatus;
    private HorizontalLayout layoutBotones;
    private List<Estatus> listaEstatus;
    private boolean repetido;
    private HeaderRow filtro;
    ListDataProvider<Casa>dataProvider;


    public CasaLista() {
        gridCasa = new Grid<Casa>(Casa.class);
        estatus = new ComboBox<>();
        adEstatus = new ADEstatus();
        adCasa = new ADCasa();
        btnEliminar = new Button("Eliminar");
        btnDesactivar = new Button("Desactivar");
        btnReactivar = new Button("Reactivar");
        layoutBotones = new HorizontalLayout();
        repetido = false;
        inicializaVista();
    }
    public void inicializaVista() {
        listaEstatus = adEstatus.obtenerEstatus();
        estatus.setTextInputAllowed(false);
        estatus.setItems(listaEstatus);
        estatus.setSelectedItem(listaEstatus.get(2));
        gridCasa.setItems(adCasa.obtenerTodasCasas());
        gridCasa.setColumns("nombreCasa",  "calle","colonia","codigoPostal","supervisor", "capacidadTotal","numeroCuartos","estatus");
        gridCasa.getColumn("nombreCasa").setCaption("Casa");
        gridCasa.getColumn("capacidadTotal").setCaption("Capacidad");
        gridCasa.getColumn("numeroCuartos").setCaption("Cuartos");
        gridCasa.getColumn("codigoPostal").setCaption("Código Postal");
        gridCasa.getColumn("supervisor").setCaption("Nombre supervisor");
        gridCasa.getColumn("calle").setCaption("Calle");
        gridCasa.setWidth("100%");
        gridCasa.setSelectionMode(Grid.SelectionMode.MULTI);

        aplicarFiltrados(adCasa.obtenerTodasCasas());

        gridCasa.addComponentColumn(casa -> {
            Button btnEditar = new Button("", VaadinIcons.EDIT);
            btnEditar.setPrimaryStyleName("btn btn-danger");
            btnEditar.addClickListener(clickEvent -> {
                TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-casa");
                tabSheet.setSelectedTab(0);
                tabSheet.replaceComponent(tabSheet.getTab(0).getComponent(), new FormCasaLogica(casa));

            });
            return btnEditar;
        });
        btnReactivar.setPrimaryStyleName("btn btn-success");
        btnReactivar.addClickListener(new Botones());

        btnDesactivar.setPrimaryStyleName("btn btn-warning");
        btnDesactivar.addClickListener(new Botones());


        btnEliminar.setPrimaryStyleName("btn btn-primary");
        btnEliminar.addClickListener(new Botones());
        estatus.addValueChangeListener(new Combo());
        layoutBotones.addComponents(btnReactivar, btnDesactivar, btnEliminar);
        this.addComponents(estatus, gridCasa, layoutBotones);
    }
    public void aplicarFiltrados(List<Casa>listaCasas){

        ListDataProvider<Casa> dataProvider = new
                ListDataProvider<>(listaCasas);
        gridCasa.setDataProvider(dataProvider);

        if(filtro==null){
            filtro=gridCasa.appendHeaderRow();
        }

        //Primer filtro
        TextField nombreCasa = new TextField();
        nombreCasa.addValueChangeListener(event -> dataProvider.addFilter(casa -> StringUtils.startsWithIgnoreCase(casa.getNombreCasa(),
                nombreCasa.getValue())));

        nombreCasa.setValueChangeMode(ValueChangeMode.EAGER);

        filtro.getCell(gridCasa.getColumn("nombreCasa")).setComponent(nombreCasa);
        nombreCasa.setSizeFull();
        nombreCasa.setPlaceholder("Nombre");

        //Segundo filtro
        TextField calle = new TextField();
        calle.addValueChangeListener(event -> dataProvider.addFilter(casa -> StringUtils.startsWithIgnoreCase(casa.getCalle(),
                calle.getValue())));

        calle.setValueChangeMode(ValueChangeMode.EAGER);

        filtro.getCell(gridCasa.getColumn("calle")).setComponent(calle);
        calle.setSizeFull();
        calle.setPlaceholder("Calle");


        //Tercer filtro
        TextField colonia = new TextField();
        colonia.addValueChangeListener(event -> dataProvider.addFilter(casa -> StringUtils.startsWithIgnoreCase(casa.getColonia(),
                colonia.getValue())));

        colonia.setValueChangeMode(ValueChangeMode.EAGER);

        filtro.getCell(gridCasa.getColumn("colonia")).setComponent(colonia);
        colonia.setSizeFull();
        colonia.setPlaceholder("Colonia");

        //Cuarto filtro
        TextField codigoPostal = new TextField();
        codigoPostal.addValueChangeListener(event -> dataProvider.addFilter(casa -> StringUtils.startsWithIgnoreCase(String.valueOf(casa.getCodigoPostal()),
                codigoPostal.getValue())));

        codigoPostal.setValueChangeMode(ValueChangeMode.EAGER);

        filtro.getCell(gridCasa.getColumn("codigoPostal")).setComponent(codigoPostal);
        codigoPostal.setSizeFull();
        codigoPostal.setPlaceholder("Código Postal");

        //Quinto filtro
        TextField supervisor = new TextField();
        supervisor.addValueChangeListener(event -> dataProvider.addFilter(casa -> StringUtils.startsWithIgnoreCase(String.valueOf(casa.getSupervisor()),
                supervisor.getValue())));

        supervisor.setValueChangeMode(ValueChangeMode.EAGER);

        filtro.getCell(gridCasa.getColumn("supervisor")).setComponent(supervisor);
        supervisor.setSizeFull();
        supervisor.setPlaceholder("Supervisor");

        //Sexto filtro
        TextField capacidadTotal = new TextField();
        capacidadTotal.addValueChangeListener(event -> dataProvider.addFilter(casa -> StringUtils.startsWithIgnoreCase(String.valueOf(casa.getCapacidadTotal()),
                capacidadTotal.getValue())));

        capacidadTotal.setValueChangeMode(ValueChangeMode.EAGER);

        filtro.getCell(gridCasa.getColumn("capacidadTotal")).setComponent(capacidadTotal);
        capacidadTotal.setSizeFull();
        capacidadTotal.setPlaceholder("Capacidad");

        //Septimo filtro
        TextField numeroCuartos = new TextField();
        numeroCuartos.addValueChangeListener(event -> dataProvider.addFilter(casa -> StringUtils.startsWithIgnoreCase(String.valueOf(casa.getNumeroCuartos()),
                numeroCuartos.getValue())));

        numeroCuartos.setValueChangeMode(ValueChangeMode.EAGER);

        filtro.getCell(gridCasa.getColumn("numeroCuartos")).setComponent(numeroCuartos);
        numeroCuartos.setSizeFull();
        numeroCuartos.setPlaceholder("Numero de cuartos");

    }
    class Botones implements Button.ClickListener {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            if (event.getButton() == btnEliminar) {
                if (!gridCasa.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar eliminación:",
                            "¿Deseas realmente eliminar los registros de las casas?",
                            "Eliminar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                @Override
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        Set<Casa> casas = gridCasa.getSelectedItems();
                                        List<Casa> casa = new ArrayList<>();
                                        casa.addAll(casas);
                                        //ADCasa adCasa = new ADCasa();
                                        casa.addAll(casas);
                                        boolean ok = adCasa.eliminarCasa(casa);
                                        if (ok) {
                                            gridCasa.setItems(adCasa.obtenerTodasCasas());
                                            //
                                            aplicarFiltrados(adCasa.obtenerTodasCasas());
                                            Notification.show("Regitro(s) de casa eliminado(s)", Notification.Type.WARNING_MESSAGE);
                                        }else {
                                            //
                                            aplicarFiltrados(adCasa.obtenerTodasCasas());
                                            Notification.show("Algunos registros no pudieron ser eliminaos porque" +
                                                    "se encuentran asociado a otros registros", Notification.Type.WARNING_MESSAGE);
                                        }
                                        estatus.setSelectedItem(listaEstatus.get(2));
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos una casa para eliminar", Notification.Type.WARNING_MESSAGE);
                }else if (event.getButton() == btnReactivar) {
                    if (!gridCasa.getSelectedItems().isEmpty()) {
                        ConfirmDialog.show(
                                UI.getCurrent(),
                                "Confirmar reactivación:",
                                "¿Deseas relamente reactivar los registros?",
                                "Reactivar", "Cancelar",
                                new ConfirmDialog.Listener() {
                                    public void onClose(ConfirmDialog dialog) {
                                        if (dialog.isConfirmed()) {
                                            repetido = false;
                                            Set<Casa> casas = gridCasa.getSelectedItems();
                                            List<Casa> casa = new ArrayList<>();
                                            for (Casa c : casas) {
                                                if (c.getEstatus().getIdEstatus() != 1) {
                                                    casa.add(c);
                                                } else {
                                                    repetido = true;
                                                }
                                            }
                                            if (repetido)
                                                Notification.show("Algunos registros ya se encontraban activados...", Notification.Type.WARNING_MESSAGE);

                                            boolean ok = adCasa.reactivarCasas(casa);
                                            if (ok) {
                                                gridCasa.setItems(adCasa.obtenerTodasCasas());
                                                //
                                                aplicarFiltrados(adCasa.obtenerTodasCasas());
                                                Notification.show("Registros reactivados...", Notification.Type.WARNING_MESSAGE);
                                            }
                                            aplicarFiltrados(adCasa.obtenerTodasCasas());
                                            estatus.setSelectedItem(listaEstatus.get(2));
                                        }
                                    }
                                });
                    } else
                        Notification.show("Selecciona al menos un cuarto para reactivar", Notification.Type.WARNING_MESSAGE);
                } else if (event.getButton() == btnDesactivar) {
                    if (!gridCasa.getSelectedItems().isEmpty()) {
                        ConfirmDialog.show(
                                UI.getCurrent(),
                                "Confirmar desactivación:",
                                "¿Deseas relamente desactivar los registros?",
                                "Desactivar", "Cancelar",
                                new ConfirmDialog.Listener() {
                                    public void onClose(ConfirmDialog dialog) {
                                        if (dialog.isConfirmed()) {
                                            repetido = false;
                                            Set<Casa> casas = gridCasa.getSelectedItems();
                                            List<Casa> casa = new ArrayList<>();
                                            for (Casa c : casas) {
                                                if (c.getEstatus().getIdEstatus() != 2) {
                                                    casa.add(c);
                                                } else {
                                                    repetido = true;
                                                }
                                            }
                                            if (repetido)
                                                Notification.show("Algunos registros ya se encontraban desactivados...", Notification.Type.WARNING_MESSAGE);
                                            boolean ok = adCasa.desactivarCasas(casa);
                                            if (ok) {
                                                gridCasa.setItems(adCasa.obtenerTodasCasas());
                                                //
                                                aplicarFiltrados(adCasa.obtenerTodasCasas());
                                                Notification.show("Registros desactivados...", Notification.Type.WARNING_MESSAGE);
                                            }
                                            aplicarFiltrados(adCasa.obtenerTodasCasas());
                                            estatus.setSelectedItem(listaEstatus.get(2));
                                        }
                                    }
                                });
                    } else
                        Notification.show("Selecciona al menos una casa para desactivar", Notification.Type.WARNING_MESSAGE);

                }
            }
    }

    class Combo implements HasValue.ValueChangeListener {
        @Override
        public void valueChange(HasValue.ValueChangeEvent valueChangeEvent) {
            if(!estatus.getSelectedItem().isPresent()){
            }else{
                if (estatus.getSelectedItem().get() == listaEstatus.get(0)) {
                    gridCasa.setItems(adCasa.obtenerCasassActivas());
                    aplicarFiltrados(adCasa.obtenerCasassActivas());
                } else if (estatus.getSelectedItem().get() == listaEstatus.get(1)) {
                    gridCasa.setItems(adCasa.obtenerCasasInactivas());
                    aplicarFiltrados(adCasa.obtenerCasasInactivas());
                } else if (estatus.getSelectedItem().get() == listaEstatus.get(2)) {
                    gridCasa.setItems(adCasa.obtenerTodasCasas());
                    aplicarFiltrados(adCasa.obtenerTodasCasas());
                }
            }


        }
    }

}
