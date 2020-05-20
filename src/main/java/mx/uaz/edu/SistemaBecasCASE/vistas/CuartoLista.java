package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.mysql.cj.util.StringUtils;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.sass.internal.util.StringUtil;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADCasa;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADCuarto;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADEstatus;
import mx.uaz.edu.SistemaBecasCASE.modelos.Casa;
import mx.uaz.edu.SistemaBecasCASE.modelos.Cuarto;
import mx.uaz.edu.SistemaBecasCASE.modelos.Estatus;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CuartoLista extends VerticalLayout{
    private Grid<Cuarto> gridCuarto;
    private Button btnEliminar;
    private Button btnDesactivar;
    private Button btnReactivar;
    private ADCuarto adCuarto;
    private ADEstatus adEstatus;
    private ComboBox<Estatus> estatus;
    private HorizontalLayout layoutBotones;
    private List<Estatus> listaEstatus;
    private boolean repetido;
    private HeaderRow filtro;

    public CuartoLista() {
        gridCuarto = new Grid<Cuarto>(Cuarto.class);
        adCuarto = new ADCuarto();
        estatus = new ComboBox<>();
        adEstatus = new ADEstatus();
        btnEliminar = new Button("Eliminar");
        btnDesactivar = new Button("Desactivar");
        btnReactivar = new Button("Reactivar");
        layoutBotones = new HorizontalLayout();
        repetido = false;
        inicializaVista();
    }
    public void inicializaVista() {
        listaEstatus = adEstatus.obtenerEstatus();
        estatus.setItems(listaEstatus);
        estatus.setTextInputAllowed(false);
        estatus.setSelectedItem(listaEstatus.get(2));
        gridCuarto.setItems(adCuarto.obtenerTodasCuarto());
        gridCuarto.setColumns("num_cuarto", "capacidad_cuarto", "casa","estatus");
        gridCuarto.getColumn("num_cuarto").setCaption("Número de cuarto");
        gridCuarto.getColumn("capacidad_cuarto").setCaption("Capacidad");
        gridCuarto.getColumn("casa").setCaption("Nombre casa");
        gridCuarto.setWidth("100%");
        gridCuarto.setSelectionMode(Grid.SelectionMode.MULTI);

        aplicarFiltros(adCuarto.obtenerTodasCuarto());

        gridCuarto.addComponentColumn(cuarto -> {
            Button btnEditar = new Button("", VaadinIcons.EDIT);
            btnEditar.setPrimaryStyleName("btn btn-danger");
            btnEditar.addClickListener(clickEvent -> {
                TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-cuarto");
                tabSheet.setSelectedTab(0);
                tabSheet.replaceComponent(tabSheet.getTab(0).getComponent(), new FormCuartoLogica(cuarto));

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
        this.addComponents(estatus, gridCuarto, layoutBotones);

    }

    public void aplicarFiltros(List<Cuarto> listaCuartos){
        ListDataProvider<Cuarto> dataProvider=
                new ListDataProvider<>(listaCuartos);

        gridCuarto.setDataProvider(dataProvider);

        if(filtro==null){
            filtro=gridCuarto.appendHeaderRow();
        }
        //Primer filtro
        TextField num_cuarto = new TextField();
        num_cuarto.addValueChangeListener(event -> dataProvider.addFilter(cuarto -> StringUtils.startsWithIgnoreCase(String.valueOf(cuarto.getNum_cuarto()),
                num_cuarto.getValue())));

        num_cuarto.setValueChangeMode(ValueChangeMode.EAGER);

        filtro.getCell(gridCuarto.getColumn("num_cuarto")).setComponent(num_cuarto);
        num_cuarto.setSizeFull();
        num_cuarto.setPlaceholder("Numero");

        //Segundo filtro
        TextField capacidad_cuarto = new TextField();
        capacidad_cuarto.addValueChangeListener(event -> dataProvider.addFilter(cuarto -> StringUtils.startsWithIgnoreCase(String.valueOf(cuarto.getCapacidad_cuarto()),
                capacidad_cuarto.getValue())));

        capacidad_cuarto.setValueChangeMode(ValueChangeMode.EAGER);

        filtro.getCell(gridCuarto.getColumn("capacidad_cuarto")).setComponent(capacidad_cuarto);
        capacidad_cuarto.setSizeFull();
        capacidad_cuarto.setPlaceholder("Capacidad");


        //Tercero filtro
        TextField casa = new TextField();
        casa.addValueChangeListener(event -> dataProvider.addFilter(cuarto -> StringUtils.startsWithIgnoreCase(String.valueOf(cuarto.getCasa()),
                casa.getValue())));

        casa.setValueChangeMode(ValueChangeMode.EAGER);

        filtro.getCell(gridCuarto.getColumn("casa")).setComponent(casa);
        casa.setSizeFull();
        casa.setPlaceholder("Casa");

    }
        class Botones implements Button.ClickListener{
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if(event.getButton()==btnEliminar){
                if(!gridCuarto.getSelectedItems().isEmpty()){
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar eliminación:",
                            "¿Deseas realmente eliminar los registros de los cuartos?",
                            "Eliminar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                @Override
                                public void onClose(ConfirmDialog dialog) {
                                    if(dialog.isConfirmed()){
                                        Set<Cuarto> cuartos = gridCuarto.getSelectedItems();
                                        List<Cuarto> cuarto = new ArrayList<>();
                                        cuarto.addAll(cuartos);
                                        //ADCuarto adCuarto = new ADCuarto();
                                        boolean ok = adCuarto.eliminarCuarto(cuarto);
                                        if(ok){
                                            aplicarFiltros(adCuarto.obtenerTodasCuarto());
                                            gridCuarto.setItems(adCuarto.obtenerTodasCuarto());
                                            Notification.show("Registros de cuarto eliminados",Notification.Type.WARNING_MESSAGE);
                                        }
                                        aplicarFiltros(adCuarto.obtenerTodasCuarto());
                                        estatus.setSelectedItem(listaEstatus.get(2));

                                    }
                                }
                            });
                }else
                    Notification.show("Selecciona al menos un cuarto para eliminar",Notification.Type.WARNING_MESSAGE);
            }else if (event.getButton() == btnReactivar) {
                if (!gridCuarto.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar reactivación:",
                            "¿Deseas relamente reactivar los registros?",
                            "Reactivar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        repetido = false;
                                        Set<Cuarto> cuartos = gridCuarto.getSelectedItems();
                                        List<Cuarto> cuarto = new ArrayList<>();
                                        for (Cuarto c : cuartos) {
                                            if (c.getEstatus().getIdEstatus() != 1) {
                                                cuarto.add(c);
                                            } else {
                                                repetido = true;
                                            }
                                        }
                                        if (repetido)
                                            Notification.show("Algunos registros ya se encontraban activados...", Notification.Type.WARNING_MESSAGE);
                                        //comedores.addAll(comedor);
                                        boolean ok = adCuarto.reactivarCuartos(cuarto);
                                        if (ok) {
                                            aplicarFiltros(adCuarto.obtenerTodasCuarto());
                                            gridCuarto.setItems(adCuarto.obtenerTodasCuarto());
                                            Notification.show("Registros reactivados...", Notification.Type.WARNING_MESSAGE);
                                        }
                                        aplicarFiltros(adCuarto.obtenerTodasCuarto());
                                        estatus.setSelectedItem(listaEstatus.get(2));
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos un cuarto para reactivar", Notification.Type.WARNING_MESSAGE);
            } else if (event.getButton() == btnDesactivar) {
                if (!gridCuarto.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar desactivación:",
                            "¿Deseas relamente desactivar los registros?",
                            "Desactivar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        repetido = false;
                                        Set<Cuarto> cuartos = gridCuarto.getSelectedItems();
                                        List<Cuarto> cuarto= new ArrayList<>();
                                        for (Cuarto c : cuartos) {
                                            if (c.getEstatus().getIdEstatus() != 2) {
                                                cuarto.add(c);
                                            } else {
                                                repetido = true;
                                            }
                                        }
                                        if (repetido)
                                            Notification.show("Algunos registros ya se encontraban desactivados...", Notification.Type.WARNING_MESSAGE);
                                        boolean ok = adCuarto.desactivarCuartos(cuarto);
                                        if (ok) {
                                            aplicarFiltros(adCuarto.obtenerTodasCuarto());
                                            gridCuarto.setItems(adCuarto.obtenerTodasCuarto());
                                            Notification.show("Registros desactivados...", Notification.Type.WARNING_MESSAGE);
                                        }
                                        aplicarFiltros(adCuarto.obtenerTodasCuarto());
                                        estatus.setSelectedItem(listaEstatus.get(2));
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos un cuarto para desactivar", Notification.Type.WARNING_MESSAGE);

            }
        }
    }
class Combo implements HasValue.ValueChangeListener {
    @Override
    public void valueChange(HasValue.ValueChangeEvent valueChangeEvent) {
        if(!estatus.getSelectedItem().isPresent()){

        }else{
                if (estatus.getSelectedItem().get() == listaEstatus.get(0)) {
                    gridCuarto.setItems(adCuarto.obtenerCuartosActivos());
                    aplicarFiltros(adCuarto.obtenerCuartosActivos());
                } else if (estatus.getSelectedItem().get() == listaEstatus.get(1)) {
                    gridCuarto.setItems(adCuarto.obtenerCuartosInactivos());
                    aplicarFiltros(adCuarto.obtenerCuartosInactivos());
                } else if (estatus.getSelectedItem().get() == listaEstatus.get(2)) {
                    gridCuarto.setItems(adCuarto.obtenerTodasCuarto());
                    aplicarFiltros(adCuarto.obtenerTodasCuarto());
                }
            }
        }
    }
}
