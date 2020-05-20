package mx.uaz.edu.SistemaBecasCASE.vistas;
        import com.mysql.cj.util.StringUtils;
        import com.vaadin.data.HasValue;
        import com.vaadin.data.provider.ListDataProvider;
        import com.vaadin.icons.VaadinIcons;
        import com.vaadin.shared.ui.ValueChangeMode;
        import com.vaadin.ui.*;
        import com.vaadin.ui.components.grid.HeaderRow;
        import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADEstatus;
        import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADResponsable;
        import mx.uaz.edu.SistemaBecasCASE.modelos.Estatus;
        import mx.uaz.edu.SistemaBecasCASE.modelos.Responsable;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
        import org.vaadin.dialogs.ConfirmDialog;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Set;

public class ListaResponsables  extends VerticalLayout {
    private Grid<Responsable> gridResponsable;
    private Button btnEliminar;
    private Button btnReactivar;
    private Button btnDesactivar;
    ADResponsable adResponsable;
    private ADEstatus adEstatus;
    private ComboBox<Estatus> estatus;
    private HorizontalLayout layoutBotones;
    private List<Estatus> listaEstatus;
    private boolean repetido;
    private TextField apMaternoResponsable;
    private TextField emailResponsable;
    private TextField telefonoResponsable;
    private HeaderRow filtro;
    ListDataProvider<Responsable>dataProvider;


            public ListaResponsables() {
                estatus = new ComboBox<>();
                adEstatus = new ADEstatus();
                gridResponsable = new Grid<Responsable>(Responsable.class);
                adResponsable = new ADResponsable();
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

                gridResponsable.setItems(adResponsable.obtenerTodosResponsables());
                gridResponsable.setColumns("nombreResponsable", "apPaterno", "apMaterno", "email", "telefono",
                        "estatus", "subsede", "funcion");

                gridResponsable.getColumn("nombreResponsable").setCaption("Nombre del Responsable");
                gridResponsable.getColumn("apPaterno").setCaption("Apellido Paterno");
                gridResponsable.getColumn("apMaterno").setCaption("Apellido Materno");
                gridResponsable.getColumn("estatus").setCaption("Estatus");
                gridResponsable.getColumn("funcion").setCaption("Funcion");
                gridResponsable.getColumn("subsede").setCaption("Subsede");
                gridResponsable.getColumn("email").setCaption("Email");
                gridResponsable.getColumn("telefono").setCaption("Teléfono");
                gridResponsable.setWidth("100%");
                gridResponsable.setSelectionMode(Grid.SelectionMode.MULTI);

                aplicarFiltros(adResponsable.obtenerTodosResponsables());


                gridResponsable.addComponentColumn(responsable -> {
                    Button button = new Button("", VaadinIcons.EDIT);
                    button.setPrimaryStyleName("btn btn-danger");
                    button.addClickListener(clickEvent -> {
                        TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-responsables");
                        tabSheet.setSelectedTab(0);
                        tabSheet.replaceComponent(tabSheet.getTab(0).getComponent(), new FormResponsableLogica(responsable));
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
                this.addComponents(estatus,gridResponsable,layoutBotones);

            }

    private void aplicarFiltroApMaternoR(ListDataProvider<Responsable> dataProvider){
        if(apMaternoResponsable.getValue() != null){
            dataProvider.addFilter(responsable ->
                    responsable.getApMaterno() != null ?
                            StringUtils.startsWithIgnoreCase(responsable.getApMaterno(), apMaternoResponsable.getValue())
                            : StringUtils.startsWithIgnoreCase(" ", apMaternoResponsable.getValue())
            );
        }else{

        }
    }

    private void aplicarFiltroEmailR(ListDataProvider<Responsable> dataProvider) {
        if (emailResponsable.getValue() != null) {
            dataProvider.addFilter(responsable ->
                    responsable.getEmail() != null ?
                            StringUtils.startsWithIgnoreCase(responsable.getEmail(), emailResponsable.getValue())
                            : StringUtils.startsWithIgnoreCase(" ", emailResponsable.getValue())
            );
        } else {

        }
    }
    private void aplicarFiltroTelefonoR(ListDataProvider<Responsable> dataProvider) {
        if (telefonoResponsable.getValue() != null) {
            dataProvider.addFilter(responsable ->
                    responsable.getTelefono() != null ?
                            StringUtils.startsWithIgnoreCase(responsable.getTelefono(), telefonoResponsable.getValue())
                            : StringUtils.startsWithIgnoreCase(" ", telefonoResponsable.getValue())
            );
        } else {
            System.out.println("Aquí se hace el cambio");
        }

    }
    public void aplicarFiltros(List<Responsable> listaResponsables){
        ListDataProvider<Responsable>dataProvider=
                new ListDataProvider<>(listaResponsables);

        gridResponsable.setDataProvider(dataProvider);

        if(filtro==null){
            filtro=gridResponsable.appendHeaderRow();
        }
        //Primer filtro
        TextField nombreResponsable =new TextField();
        nombreResponsable.addValueChangeListener(event -> dataProvider.addFilter(
                responsable -> StringUtils.startsWithIgnoreCase
                        (responsable.getNombreResponsable(),nombreResponsable.getValue())
        ));

        nombreResponsable.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridResponsable.getColumn("nombreResponsable")).setComponent(nombreResponsable);
        nombreResponsable.setSizeFull();
        nombreResponsable.setPlaceholder("Nombre");
        //Segundo filtro
        TextField apPaterno =new TextField();
        apPaterno.addValueChangeListener(event -> dataProvider.addFilter(
                responsable -> StringUtils.startsWithIgnoreCase
                        (responsable.getApPaterno(),apPaterno.getValue())
        ));

        apPaterno.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridResponsable.getColumn("apPaterno")).setComponent(apPaterno);
        apPaterno.setSizeFull();
        apPaterno.setPlaceholder("Apellido Paterno");

        //tercer filtro
        apMaternoResponsable =new TextField();
        apMaternoResponsable.addValueChangeListener(event -> {aplicarFiltroApMaternoR(dataProvider);}
        );

        apMaternoResponsable.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridResponsable.getColumn("apMaterno")).setComponent(apMaternoResponsable);
        apMaternoResponsable.setSizeFull();
        apMaternoResponsable.setPlaceholder("Apellido Materno");


        //cuarto filtro
        emailResponsable =new TextField();
        emailResponsable.addValueChangeListener(event -> {aplicarFiltroEmailR(dataProvider);}
        );

        emailResponsable.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridResponsable.getColumn("email")).setComponent(emailResponsable);
        emailResponsable.setSizeFull();
        emailResponsable.setPlaceholder("Email");

        //quinto filtro
        telefonoResponsable =new TextField();
        telefonoResponsable.addValueChangeListener(event -> {aplicarFiltroTelefonoR(dataProvider);}
        );

        telefonoResponsable.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridResponsable.getColumn("telefono")).setComponent(telefonoResponsable);
        telefonoResponsable.setSizeFull();
        telefonoResponsable.setPlaceholder("Teléfono");

        //sexto filtro
        TextField funcion =new TextField();
        funcion.addValueChangeListener(event -> dataProvider.addFilter(
                responsable -> StringUtils.startsWithIgnoreCase
                        (responsable.getFuncion().toString(),funcion.getValue())
        ));

        funcion.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridResponsable.getColumn("funcion")).setComponent(funcion);
        funcion.setSizeFull();
        funcion.setPlaceholder("Función");

        //septimo filtro
        TextField subsede =new TextField();
        subsede.addValueChangeListener(event -> dataProvider.addFilter(
                responsable -> StringUtils.startsWithIgnoreCase
                        (responsable.getSubsede().toString(),subsede.getValue())
        ));

        subsede.setValueChangeMode(ValueChangeMode.EAGER);
        filtro.getCell(gridResponsable.getColumn("subsede")).setComponent(subsede);
        subsede.setSizeFull();
        subsede.setPlaceholder("Subsede");


    }







    class Botones implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            if(clickEvent.getButton() == btnEliminar){
                if (!gridResponsable.getSelectedItems().isEmpty()) {
                    System.out.println(" Entro a a eliminar");
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar eliminación:",
                            "¿Deseas relamente eliminar los registros?",
                            "Eliminar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        Set<Responsable> responsable = gridResponsable.getSelectedItems();
                                        List<Responsable> responsables = new ArrayList<>();
                                        responsables.addAll(responsable);
                                        boolean ok = adResponsable.eliminarResponsable(responsables);
                                        if (ok) {
                                            aplicarFiltros(adResponsable.obtenerTodosResponsables());
                                            gridResponsable.setItems(adResponsable.obtenerTodosResponsables());
                                            Notification.show("Registros eliminados...", Notification.Type.WARNING_MESSAGE);
                                        }else{
                                            aplicarFiltros(adResponsable.obtenerTodosResponsables());
                                            Notification.show("Algunos registros no pudieron ser eliminados porque " +
                                                    "se encuentran asociados a otros rgistros", Notification.Type.WARNING_MESSAGE);
                                        }
                                        estatus.setSelectedItem(listaEstatus.get(2));
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos un responsable para eliminar", Notification.Type.WARNING_MESSAGE);
            }else if (clickEvent.getButton() == btnReactivar){
                if (!gridResponsable.getSelectedItems().isEmpty()) {
                    System.out.println("entro a reactivar ");
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar reactivación:",
                            "¿Deseas relamente reactivar los registros?",
                            "Reactivar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        repetido = false;
                                        Set<Responsable> responsable = gridResponsable.getSelectedItems();
                                        List<Responsable> responsables = new ArrayList<>();
                                        for(Responsable r : responsable){
                                            if(r.getEstatus().getIdEstatus() != 1){
                                                responsables.add(r);
                                            }else{
                                                repetido = true;
                                            }
                                        }
                                        if(repetido)
                                            Notification.show("Algunos registros ya se encontraban activados...", Notification.Type.WARNING_MESSAGE);
                                        boolean ok = adResponsable.reactivarResponsables(responsables);
                                        if (ok) {
                                            aplicarFiltros(adResponsable.obtenerTodosResponsables());
                                            gridResponsable.setItems(adResponsable.obtenerTodosResponsables());
                                            Notification.show("Registros reactivados...", Notification.Type.WARNING_MESSAGE);

                                        }
                                        aplicarFiltros(adResponsable.obtenerTodosResponsables());
                                        estatus.setSelectedItem(listaEstatus.get(2));
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos un responsable para reactivar", Notification.Type.WARNING_MESSAGE);
            }else if(clickEvent.getButton() == btnDesactivar) {
                if (!gridResponsable.getSelectedItems().isEmpty()) {
                    System.out.println("Entre al metodo desactivar");
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar desactivación:",
                            "¿Deseas relamente desactivar los registros?",
                            "Desactivar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        repetido = false;
                                        Set<Responsable> responsable = gridResponsable.getSelectedItems();
                                        List<Responsable> responsables = new ArrayList<>();
                                        for(Responsable r : responsable){
                                            if(r.getEstatus().getIdEstatus() != 2){
                                                responsables.add(r);
                                                System.out.println("en el iff");
                                            }else{
                                                repetido = true;
                                            }
                                        }
                                        if(repetido)
                                            Notification.show("Algunos registros ya se encontraban desactivados...", Notification.Type.WARNING_MESSAGE);

                                        boolean ok = adResponsable.desactivarResponsables(responsables);
                                        if (ok) {
                                            aplicarFiltros(adResponsable.obtenerTodosResponsables());
                                            gridResponsable.setItems(adResponsable.obtenerTodosResponsables());
                                            Notification.show("Registros desactivados...", Notification.Type.WARNING_MESSAGE);
                                        }
                                        aplicarFiltros(adResponsable.obtenerTodosResponsables());
                                        estatus.setSelectedItem(listaEstatus.get(2));
                                    }
                                }
                            });
                } else
                Notification.show("Selecciona al menos un responsable para desactivar", Notification.Type.WARNING_MESSAGE);

            }
        }
    }

    class  Combo implements HasValue.ValueChangeListener{

        @Override
        public void valueChange(HasValue.ValueChangeEvent valueChangeEvent) {
            if(!estatus.getSelectedItem().isPresent()){

            }else {
                if(estatus.getSelectedItem().get() == listaEstatus.get(0)){
                    gridResponsable.setItems(adResponsable.obtenerResponsablesActivos());
                    aplicarFiltros(adResponsable.obtenerResponsablesActivos());
                }else if(estatus.getSelectedItem().get() == listaEstatus.get(1)){
                    gridResponsable.setItems(adResponsable.obtenerResponsablesInactivos());
                    aplicarFiltros(adResponsable.obtenerResponsablesInactivos());
                }else if(estatus.getSelectedItem().get() == listaEstatus.get(2)){
                    gridResponsable.setItems(adResponsable.obtenerTodosResponsables());
                    aplicarFiltros(adResponsable.obtenerTodosResponsables());
                }

            }
        }

    }



}