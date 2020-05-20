package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.mysql.cj.util.StringUtils;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADComedor;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADEstatus;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADSupervisor;
import mx.uaz.edu.SistemaBecasCASE.modelos.Comedor;
import mx.uaz.edu.SistemaBecasCASE.modelos.Estatus;
import mx.uaz.edu.SistemaBecasCASE.modelos.Supervisor;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import org.vaadin.dialogs.ConfirmDialog;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ListaSupervisores extends VerticalLayout {
    private Grid<Supervisor> gridSupervisor;
    private Button btnEliminar;
    private Button btnReactivar;
    private Button btnDesactivar;
    //ADComedor adComedor;
    private ADSupervisor adSupervisor;
    private ADEstatus adEstatus;
    private ComboBox<Estatus> estatus;
    private HorizontalLayout layoutBotones;
    private List<Estatus> listaEstatus;
    private boolean repetido;
    private TextField apMaternoSupervisor;
    private HeaderRow filtroRenglones;
    ListDataProvider<Supervisor> dataProvider;

    public ListaSupervisores() {
        estatus = new ComboBox<>();
        adEstatus = new ADEstatus();
        gridSupervisor = new Grid<Supervisor>(Supervisor.class);
        adSupervisor = new ADSupervisor();

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
        gridSupervisor.setItems(adSupervisor.obtenerTodosSupervisores());
        gridSupervisor.setColumns("nombreSupervisor", "apPaterno", "apMaterno", "telefonoCelular", "emailSupervisor" , "estatus");

        gridSupervisor.getColumn("nombreSupervisor").setCaption("Nombre");
        gridSupervisor.getColumn("apPaterno").setCaption("Apellido Paterno");
        gridSupervisor.getColumn("apMaterno").setCaption("Apellido Materno");
        gridSupervisor.getColumn("telefonoCelular").setCaption("Teléfono Celular");
        gridSupervisor.setWidth("100%");
        gridSupervisor.setSelectionMode(Grid.SelectionMode.MULTI);


        //dataProvider = new ListDataProvider<>(adSupervisor.obtenerTodosSupervisores());

        //gridSupervisor.setDataProvider(dataProvider);
        aplicarFiltrados(adSupervisor.obtenerTodosSupervisores());

        //PW
        gridSupervisor.addComponentColumn(supervisor -> {
            Button button = new Button("", VaadinIcons.EDIT);
            button.setPrimaryStyleName("btn btn-danger");
            button.addClickListener(clickEvent -> {
                TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-supervisores");
                tabSheet.setSelectedTab(0);
                tabSheet.replaceComponent(tabSheet.getTab(0).getComponent(), new FormSupervisorLogica(supervisor));
            });
            return button;

        }).setCaption("Editar");

        btnEliminar.setPrimaryStyleName("btn btn-danger");
        btnEliminar.addClickListener(new ListaSupervisores.Botones());

        btnReactivar.setPrimaryStyleName("btn btn-success");
        btnReactivar.addClickListener(new ListaSupervisores .Botones());

        btnDesactivar.setPrimaryStyleName("btn btn-warning");
        btnDesactivar.addClickListener(new ListaSupervisores.Botones());

        estatus.addValueChangeListener(new ListaSupervisores.Combo());

        //btnEliminar.addClickListener(new Button.ClickListener() {

        layoutBotones.addComponents(btnReactivar, btnDesactivar, btnEliminar);

        this.addComponents(estatus, gridSupervisor, layoutBotones);
    }

    private void aplicarFiltro(ListDataProvider<Supervisor> dataProvider){
        //dataProvider.clearFilters();
        if(apMaternoSupervisor.getValue() != null){
            dataProvider.addFilter(supervisor ->
                    supervisor.getApMaterno() != null ?
                    StringUtils.startsWithIgnoreCase(supervisor.getApMaterno(), apMaternoSupervisor.getValue())
                            : StringUtils.startsWithIgnoreCase(" ", apMaternoSupervisor.getValue())
            );
        }else{
            System.out.println("Aquí se hace el cambio");
        }

    }

    public void aplicarFiltrados(List<Supervisor> listaSupervisores){

        //ListDataProvider<Supervisor> dataProvider =
        //        new ListDataProvider<>(adSupervisor.obtenerTodosSupervisores());

        ListDataProvider<Supervisor> dataProvider =
                        new ListDataProvider<>(listaSupervisores);

        gridSupervisor.setDataProvider(dataProvider);

        if(filtroRenglones==null){
            filtroRenglones = gridSupervisor.appendHeaderRow();
        }
        //Primer filtro

        TextField nombreSupervisor = new TextField();
        nombreSupervisor.addValueChangeListener( event -> dataProvider.addFilter(
                supervisor -> StringUtils.startsWithIgnoreCase
                        (supervisor.getNombreSupervisor(), nombreSupervisor.getValue())
        ));

        nombreSupervisor.setValueChangeMode(ValueChangeMode.EAGER);

        filtroRenglones.getCell(gridSupervisor.getColumn("nombreSupervisor")).setComponent(nombreSupervisor);
        nombreSupervisor.setSizeFull();
        nombreSupervisor.setPlaceholder("Nombre");

        //Segundo filtro

        TextField apPaternoSupervisor = new TextField();
        apPaternoSupervisor.addValueChangeListener( event -> dataProvider.addFilter(
                supervisor -> StringUtils.startsWithIgnoreCase
                        (supervisor.getApPaterno(), apPaternoSupervisor.getValue())
        ));

        apPaternoSupervisor.setValueChangeMode(ValueChangeMode.EAGER);

        filtroRenglones.getCell(gridSupervisor.getColumn("apPaterno")).setComponent(apPaternoSupervisor);
        apPaternoSupervisor.setSizeFull();
        apPaternoSupervisor.setPlaceholder("Apellido Paterno");

        //Tercer filtro

        apMaternoSupervisor = new TextField();
        /*apMaternoSupervisor.addValueChangeListener( event -> dataProvider.addFilter(
                supervisor -> StringUtils.startsWithIgnoreCase
                        (supervisor.getApMaterno(), apMaternoSupervisor.getValue())
        ));*/
        apMaternoSupervisor.addValueChangeListener(
                event -> {aplicarFiltro(dataProvider);}
        );

        apMaternoSupervisor.setValueChangeMode(ValueChangeMode.EAGER);

        filtroRenglones.getCell(gridSupervisor.getColumn("apMaterno")).setComponent(apMaternoSupervisor);
        apMaternoSupervisor.setSizeFull();
        apMaternoSupervisor.setPlaceholder("Apellido Materno");

        //Cuarto filtro

        TextField telefonoSupervisor = new TextField();
        telefonoSupervisor.addValueChangeListener( event -> dataProvider.addFilter(
                supervisor -> StringUtils.startsWithIgnoreCase
                        (supervisor.getTelefonoCelular(), telefonoSupervisor.getValue())
        ));

        telefonoSupervisor.setValueChangeMode(ValueChangeMode.EAGER);

        filtroRenglones.getCell(gridSupervisor.getColumn("telefonoCelular")).setComponent(telefonoSupervisor);
        telefonoSupervisor.setSizeFull();
        telefonoSupervisor.setPlaceholder("Teléfono Celular");

        //Quinto filtro

        TextField emailSupervisor = new TextField();
        emailSupervisor.addValueChangeListener( event -> dataProvider.addFilter(
                supervisor -> StringUtils.startsWithIgnoreCase
                        (supervisor.getEmailSupervisor(), emailSupervisor.getValue())
        ));

        emailSupervisor.setValueChangeMode(ValueChangeMode.EAGER);

        filtroRenglones.getCell(gridSupervisor.getColumn("emailSupervisor")).setComponent(emailSupervisor);
        emailSupervisor.setSizeFull();
        emailSupervisor.setPlaceholder("Correo Electrónico");
    }

    class Botones implements Button.ClickListener{


        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {

            if(clickEvent.getButton() == btnEliminar){
                if (!gridSupervisor.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar eliminación:",
                            "¿Deseas relamente eliminar los registros?",
                            "Eliminar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        Set<Supervisor> supervisor = gridSupervisor.getSelectedItems();
                                        List<Supervisor> supervisores = new ArrayList<>();
                                        supervisores.addAll(supervisor);
                                        boolean ok = adSupervisor.eliminarSupervisores(supervisores);
                                        if (ok) {
                                            gridSupervisor.setItems(adSupervisor.obtenerTodosSupervisores());
                                            //
                                            aplicarFiltrados(adSupervisor.obtenerTodosSupervisores());
                                            Notification.show("Registros eliminados...", Notification.Type.WARNING_MESSAGE);
                                        }else{
                                            //
                                            aplicarFiltrados(adSupervisor.obtenerTodosSupervisores());
                                            Notification.show("Algunos registros no pudieron ser eliminados porque " +
                                                    "se encuentran asociados a otros registros en la BD.", Notification.Type.WARNING_MESSAGE);
                                        }
                                        
                                        estatus.setSelectedItem(listaEstatus.get(2));
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos un supervisor para eliminar", Notification.Type.WARNING_MESSAGE);
            }else if (clickEvent.getButton() == btnReactivar){
                if (!gridSupervisor.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar reactivación:",
                            "¿Deseas relamente reactivar los registros?",
                            "Reactivar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        repetido = false;
                                        Set<Supervisor> supervisor = gridSupervisor.getSelectedItems();
                                        List<Supervisor> supervisores = new ArrayList<>();
                                        for(Supervisor s : supervisor){
                                            if(s.getEstatus().getIdEstatus() != 1){
                                                supervisores.add(s);
                                            }else{
                                                repetido = true;
                                            }
                                        }
                                        if(repetido)
                                            Notification.show("Algunos registros ya se encontraban activados...", Notification.Type.WARNING_MESSAGE);
                                        //comedores.addAll(comedor);
                                        boolean ok = adSupervisor.reactivarSupervisores(supervisores);
                                        if (ok) {
                                            gridSupervisor.setItems(adSupervisor.obtenerTodosSupervisores());
                                            //
                                            aplicarFiltrados(adSupervisor.obtenerTodosSupervisores());
                                            Notification.show("Registros reactivados...", Notification.Type.WARNING_MESSAGE);
                                        }
                                        //
                                        aplicarFiltrados(adSupervisor.obtenerTodosSupervisores());
                                        estatus.setSelectedItem(listaEstatus.get(2));
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos un supervisor para reactivar", Notification.Type.WARNING_MESSAGE);
            }else if(clickEvent.getButton() == btnDesactivar) {
                if (!gridSupervisor.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar desactivación:",
                            "¿Deseas relamente desactivar los registros?",
                            "Desactivar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        repetido = false;
                                        Set<Supervisor> supervisor = gridSupervisor.getSelectedItems();
                                        List<Supervisor> supervisores = new ArrayList<>();
                                        for(Supervisor s : supervisor){
                                            if(s.getEstatus().getIdEstatus() != 2){
                                                supervisores.add(s);
                                            }else{
                                                repetido = true;
                                            }
                                        }
                                        if(repetido)
                                            Notification.show("Algunos registros ya se encontraban desactivados...", Notification.Type.WARNING_MESSAGE);

                                        boolean ok = adSupervisor.desactivarSupervisores(supervisores);
                                        if (ok) {
                                            //
                                            aplicarFiltrados(adSupervisor.obtenerTodosSupervisores());
                                            gridSupervisor.setItems(adSupervisor.obtenerTodosSupervisores());
                                            Notification.show("Registros desactivados...", Notification.Type.WARNING_MESSAGE);
                                        }
                                        //
                                        aplicarFiltrados(adSupervisor.obtenerTodosSupervisores());
                                        estatus.setSelectedItem(listaEstatus.get(2));
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos un supervisor para desactivar", Notification.Type.WARNING_MESSAGE);

            }
        }
    }

    class Combo implements HasValue.ValueChangeListener{
        @Override
        public void valueChange(HasValue.ValueChangeEvent valueChangeEvent) {

            if(!estatus.getSelectedItem().isPresent()){
                System.out.println("Valor No presente: "  + estatus.getSelectedItem());
            }else{
                if(estatus.getSelectedItem().get() == listaEstatus.get(0)){
                    gridSupervisor.setItems(adSupervisor.obtenerSupervisoresActivos());
                    aplicarFiltrados(adSupervisor.obtenerSupervisoresActivos());
                }else if(estatus.getSelectedItem().get() == listaEstatus.get(1)){
                    gridSupervisor.setItems(adSupervisor.obtenerSupervisoresInactivos());
                    aplicarFiltrados(adSupervisor.obtenerSupervisoresInactivos());
                }else if(estatus.getSelectedItem().get() == listaEstatus.get(2)){
                    gridSupervisor.setItems(adSupervisor.obtenerTodosSupervisores());
                    aplicarFiltrados(adSupervisor.obtenerTodosSupervisores());
                }
            }
        }
    }
}
