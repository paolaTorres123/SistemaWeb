package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.mysql.cj.util.StringUtils;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.sass.internal.util.StringUtil;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.ImageRenderer;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADComedor;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADEstatus;
import mx.uaz.edu.SistemaBecasCASE.modelos.Comedor;
import mx.uaz.edu.SistemaBecasCASE.modelos.Estatus;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.CheckedOutputStream;

public class ListaComedores extends VerticalLayout {

    private Grid<Comedor> gridComedor;
    private Button btnEliminar;
    private Button btnReactivar;
    private Button btnDesactivar;
    private ADComedor adComedor;
    private ADEstatus adEstatus;
    private ComboBox<Estatus> estatus;
    private HorizontalLayout layoutBotones;
    private List<Estatus> listaEstatus;
    private boolean repetido;
    private TextField telefono;
    private HeaderRow filtro;
    ListDataProvider<Comedor>dataProvider;

    public ListaComedores() {
        estatus = new ComboBox<>();
        adEstatus = new ADEstatus();
        gridComedor = new Grid<Comedor>(Comedor.class);
        adComedor = new ADComedor();
        btnEliminar = new Button("Eliminar");
        btnDesactivar = new Button("Desactivar");
        btnReactivar = new Button("Reactivar");
        layoutBotones = new HorizontalLayout();
        repetido = false;
        inicializaVista();
    }

    public void inicializaVista(){

        listaEstatus = adEstatus.obtenerEstatus();
        estatus.setItems(listaEstatus);
        estatus.setTextInputAllowed(false);
        estatus.setSelectedItem(listaEstatus.get(2));
        gridComedor.setItems(adComedor.obtenerTodosComedores());
        gridComedor.setColumns("nombreComedor", "calle", "colonia", "codigoPostal", "telefono",
                "desayuno","comida","cena", "estatus");

        gridComedor.getColumn("nombreComedor").setCaption("Nombre del comedor");
        gridComedor.getColumn("codigoPostal").setCaption("Código Postal");
        gridComedor.getColumn("telefono").setCaption("Teléfono");
        gridComedor.setWidth("100%");
        gridComedor.setSelectionMode(Grid.SelectionMode.MULTI);

        aplicarFiltrados(adComedor.obtenerTodosComedores());

        gridComedor.addComponentColumn(comedor -> {
            Button button = new Button("", VaadinIcons.EDIT);
            button.setPrimaryStyleName("btn btn-danger");
            button.addClickListener(clickEvent -> {
                TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-comedores");
                tabSheet.setSelectedTab(0);
                tabSheet.replaceComponent(tabSheet.getTab(0).getComponent(), new FormComedorLogica(comedor));
            });
            return button;
        });

        btnReactivar.setPrimaryStyleName("btn btn-success");
        btnReactivar.addClickListener(new Botones());

        btnDesactivar.setPrimaryStyleName("btn btn-warning");
        btnDesactivar.addClickListener(new Botones());

        btnEliminar.setPrimaryStyleName("btn btn-danger");
        btnEliminar.addClickListener(new Botones());

        estatus.addValueChangeListener(new Combo());

        layoutBotones.addComponents(btnReactivar, btnDesactivar, btnEliminar);

        this.addComponents(estatus, gridComedor, layoutBotones);
    }

    private void aplicarFiltro(ListDataProvider<Comedor>dataProvider){
        if(telefono.getValue()!=null){
            dataProvider.addFilter(comedor ->
                    comedor.getTelefono()!=null ?
                            StringUtils.startsWithIgnoreCase(comedor.getTelefono(),telefono.getValue())
                    :StringUtils.startsWithIgnoreCase(" ",telefono.getValue())
            );
        }else{
            System.out.println("Aqui hace el cambio");
        }
    }

    public void aplicarFiltrados(List<Comedor>listaComedores){

        ListDataProvider<Comedor> dataProvider =
                new ListDataProvider<>(listaComedores);
        gridComedor.setDataProvider(dataProvider);

        if(filtro==null){
            filtro=gridComedor.appendHeaderRow();
        }

        //Primer filtro
        TextField nombreComedor = new TextField();
        nombreComedor.addValueChangeListener(event -> dataProvider.addFilter(comedor -> StringUtils.startsWithIgnoreCase(comedor.getNombreComedor(),
                nombreComedor.getValue())));
        nombreComedor.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridComedor.getColumn("nombreComedor")).setComponent(nombreComedor);
        nombreComedor.setSizeFull();
        nombreComedor.setPlaceholder("Nombre Comedor");

        //Segundo filtro
        TextField calle = new TextField();
        calle.addValueChangeListener(event -> dataProvider.addFilter(comedor -> StringUtils.startsWithIgnoreCase(comedor.getCalle(),
                calle.getValue())));
        calle.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridComedor.getColumn("calle")).setComponent(calle);
        calle.setSizeFull();
        calle.setPlaceholder("Calle");

        //Tercer filtro
        TextField colonia = new TextField();
        colonia.addValueChangeListener(event -> dataProvider.addFilter(comedor -> StringUtils.startsWithIgnoreCase(comedor.getColonia(),
                colonia.getValue())));
        colonia.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridComedor.getColumn("colonia")).setComponent(colonia);
        colonia.setSizeFull();
        colonia.setPlaceholder("Colonia");

        //Cuarto filtro
        TextField codigoPostal = new TextField();
        codigoPostal.addValueChangeListener(event -> dataProvider.addFilter(comedor -> StringUtils.startsWithIgnoreCase(comedor.getCodigoPostal(),
                codigoPostal.getValue())));
        codigoPostal.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridComedor.getColumn("codigoPostal")).setComponent(codigoPostal);
        codigoPostal.setSizeFull();
        codigoPostal.setPlaceholder("Código Postal");

        //Quinto filtro
        telefono=new TextField();
        telefono.addValueChangeListener(
                event -> {aplicarFiltro(dataProvider);}
        );
        telefono.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridComedor.getColumn("telefono")).setComponent(telefono);
        telefono.setSizeFull();
        telefono.setPlaceholder("Teléfono");


        //Sexto filtro
        TextField desayuno = new TextField();
        desayuno.addValueChangeListener(event -> dataProvider.addFilter(comedor -> StringUtils.startsWithIgnoreCase(comedor.getDesayuno(),
                desayuno.getValue())));
        desayuno.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridComedor.getColumn("desayuno")).setComponent(desayuno);
        desayuno.setSizeFull();
        desayuno.setPlaceholder("Desayuno");

        //Septimo filtro
        TextField comida = new TextField();
        comida.addValueChangeListener(event -> dataProvider.addFilter(comedor -> StringUtils.startsWithIgnoreCase(comedor.getComida(),
                comida.getValue())));
        telefono.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridComedor.getColumn("comida")).setComponent(comida);
        comida.setSizeFull();
        comida.setPlaceholder("Comida");

        //Octavo filtro
        TextField cena = new TextField();
        cena.addValueChangeListener(event -> dataProvider.addFilter(comedor -> StringUtils.startsWithIgnoreCase(comedor.getCena(),
                cena.getValue())));
        cena.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridComedor.getColumn("cena")).setComponent(cena);
        cena.setSizeFull();
        cena.setPlaceholder("Cena");
    }

    class Botones implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {

            if(clickEvent.getButton() == btnEliminar){
                if (!gridComedor.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar eliminación:",
                            "¿Deseas relamente eliminar los registros?",
                            "Eliminar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        Set<Comedor> comedor = gridComedor.getSelectedItems();
                                        List<Comedor> comedores = new ArrayList<>();
                                        comedores.addAll(comedor);
                                        boolean ok = adComedor.eliminarComedores(comedores);
                                        if (ok) {
                                            gridComedor.setItems(adComedor.obtenerTodosComedores());
                                            Notification.show("Registros eliminados...", Notification.Type.WARNING_MESSAGE);
                                        }else{
                                            Notification.show("Algunos registros no pudieron ser eliminados porque " +
                                                    "se encuentran asociados a otros rgistros", Notification.Type.WARNING_MESSAGE);
                                        }
                                        aplicarFiltrados(adComedor.obtenerTodosComedores());
                                        estatus.setSelectedItem(listaEstatus.get(2));
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos un comedor para eliminar", Notification.Type.WARNING_MESSAGE);
            }else if (clickEvent.getButton() == btnReactivar){
                if (!gridComedor.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar reactivación:",
                            "¿Deseas relamente reactivar los registros?",
                            "Reactivar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        repetido = false;
                                        Set<Comedor> comedor = gridComedor.getSelectedItems();
                                        List<Comedor> comedores = new ArrayList<>();
                                        for(Comedor c : comedor){
                                            if(c.getEstatus().getIdEstatus() != 1){
                                                comedores.add(c);
                                            }else{
                                                repetido = true;
                                            }
                                        }
                                        if(repetido)
                                            Notification.show("Algunos registros ya se encontraban activados...", Notification.Type.WARNING_MESSAGE);
                                        //comedores.addAll(comedor);
                                        boolean ok = adComedor.reactivarComedores(comedores);
                                        if (ok) {
                                            gridComedor.setItems(adComedor.obtenerTodosComedores());
                                            Notification.show("Registros reactivados...", Notification.Type.WARNING_MESSAGE);
                                        }
                                        aplicarFiltrados(adComedor.obtenerTodosComedores());
                                        estatus.setSelectedItem(listaEstatus.get(2));
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos un comedor para reactivar", Notification.Type.WARNING_MESSAGE);
            }else if(clickEvent.getButton() == btnDesactivar) {
                if (!gridComedor.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar desactivación:",
                            "¿Deseas relamente desactivar los registros?",
                            "Desactivar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        repetido = false;
                                        Set<Comedor> comedor = gridComedor.getSelectedItems();
                                        List<Comedor> comedores = new ArrayList<>();
                                        for(Comedor c : comedor){
                                            if(c.getEstatus().getIdEstatus() != 2){
                                                comedores.add(c);
                                            }else{
                                                repetido = true;
                                            }
                                        }
                                        if(repetido)
                                            Notification.show("Algunos registros ya se encontraban desactivados...", Notification.Type.WARNING_MESSAGE);
                                        //comedores.addAll(comedor);
                                        //comedores.addAll(comedor);
                                        boolean ok = adComedor.desactivarComedores(comedores);
                                        if (ok) {
                                            gridComedor.setItems(adComedor.obtenerTodosComedores());
                                            Notification.show("Registros desactivados...", Notification.Type.WARNING_MESSAGE);
                                        }
                                        aplicarFiltrados(adComedor.obtenerTodosComedores());
                                        estatus.setSelectedItem(listaEstatus.get(2));
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos un comedor para desactivar", Notification.Type.WARNING_MESSAGE);

            }
        }
    }

    class Combo implements HasValue.ValueChangeListener{
        @Override
        public void valueChange(HasValue.ValueChangeEvent valueChangeEvent) {
            if(!estatus.getSelectedItem().isPresent()){

            }else{
                if(estatus.getSelectedItem().get() == listaEstatus.get(0)){
                    gridComedor.setItems(adComedor.obtenerComedoresActivos());
                    aplicarFiltrados(adComedor.obtenerComedoresActivos());
                }else if(estatus.getSelectedItem().get() == listaEstatus.get(1)){
                    gridComedor.setItems(adComedor.obtenerComedoresInactivos());
                    aplicarFiltrados(adComedor.obtenerComedoresInactivos());
                }else if(estatus.getSelectedItem().get() == listaEstatus.get(2)){
                    gridComedor.setItems(adComedor.obtenerTodosComedores());
                    aplicarFiltrados(adComedor.obtenerTodosComedores());
                }
            }
        }
    }
}