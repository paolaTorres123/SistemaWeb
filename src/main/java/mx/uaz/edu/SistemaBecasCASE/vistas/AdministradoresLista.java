package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.mysql.cj.util.StringUtils;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADAdministrador;
import mx.uaz.edu.SistemaBecasCASE.modelos.Administrador;
import mx.uaz.edu.SistemaBecasCASE.modelos.Estatus;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AdministradoresLista extends VerticalLayout {
    private String MENSAJE_ADM_ACTIVOS ="Administradores Activos";
    private String MENSAJE_ADM_INACTIVOS ="Administradores Inactivos";
    private String MENSAJE_ADM_ACTIVOS_INACTIVOS ="Administradores Activos e Inactivos";
    private Grid<Administrador> grid;
    private Button btEliminar,btAgregar;
    private ADAdministrador adAdministrador;
    private MenuBar mbOps;
    private ListDataProvider<Administrador> dataProvider;

    public AdministradoresLista() {
        grid = new Grid<Administrador>();
        adAdministrador = new ADAdministrador();
        mbOps =new MenuBar();
        btEliminar = new Button("Eliminar");
        btAgregar = new Button("Re-activar");

        instanciaLista();
        instanciaBoton();
        instanciaMenuOps();
        this.addComponents(mbOps,grid,btAgregar,btEliminar);
    }

    private void instanciaMenuOps(){
        MenuBar.Command comando;

        MenuBar.MenuItem lista = mbOps.addItem("Vistas",VaadinIcons.MENU, null);
        MenuBar.MenuItem alActivos = lista.addItem("Listar "+ MENSAJE_ADM_ACTIVOS);
        MenuBar.MenuItem alInActivos =lista.addItem("Listar "+ MENSAJE_ADM_INACTIVOS);
        MenuBar.MenuItem alAcInAc =lista.addItem("Listar "+ MENSAJE_ADM_ACTIVOS_INACTIVOS);

        comando = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                if (menuItem.getText().equals("Listar "+ MENSAJE_ADM_ACTIVOS)){
                    grid.setItems(adAdministrador.obtenerTodosAdministradoresActivos());
                    grid.setCaption(MENSAJE_ADM_ACTIVOS);
                    instanciaFiltros(adAdministrador.obtenerTodosAdministradoresActivos());
                }if (menuItem.getText().equals("Listar "+ MENSAJE_ADM_INACTIVOS)){
                    grid.setItems(adAdministrador.obtenerTodosAdministradoresExpirados());
                    grid.setCaption(MENSAJE_ADM_INACTIVOS);
                    instanciaFiltros(adAdministrador.obtenerTodosAdministradoresExpirados());
                }if(menuItem.getText().equals("Listar "+ MENSAJE_ADM_ACTIVOS_INACTIVOS)) {
                    grid.setItems(adAdministrador.obtenerTodosAdministradoresAcInAc());
                    grid.setCaption(MENSAJE_ADM_ACTIVOS_INACTIVOS);
                    instanciaFiltros(adAdministrador.obtenerTodosAdministradoresAcInAc());
                }
            }
        };
        alAcInAc.setCommand(comando);alActivos.setCommand(comando);alInActivos.setCommand(comando);
    }

    private void instanciaFiltros(List admins){

        dataProvider = new ListDataProvider<>(
                admins);
        grid.setDataProvider(dataProvider);
    }

    private void instanciaLista(){
        grid.setCaption(MENSAJE_ADM_ACTIVOS);

        instanciaFiltros(adAdministrador.obtenerTodosAdministradoresActivos());

        Grid.Column<Administrador, ?>nombreCol = grid
                .addColumn(Administrador::getNombre).setCaption("Nombre");
        Grid.Column<Administrador, ?> apPaternoCol = grid
                .addColumn(Administrador::getAp_paterno).setCaption("Apellido Paterno");
        Grid.Column<Administrador, ?> apMaternoCol = grid
                .addColumn(Administrador::getAp_materno).setCaption("Apellido Materno");
        Grid.Column<Administrador, ?> telCol = grid
                .addColumn(Administrador::getTelefono).setCaption("Teléfono");
        Grid.Column<Administrador, ?> cargoCol = grid
                .addColumn(Administrador::getTipo_usuario).setCaption("Cargo");
        grid.setWidth("100%");
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        HeaderRow filterRow = grid.appendHeaderRow();

        TextField tfNombreFiltro = new TextField();
        tfNombreFiltro.addValueChangeListener(event -> dataProvider.addFilter(
                usuario -> StringUtils.startsWithIgnoreCase(usuario.getNombre(),
                        tfNombreFiltro.getValue())));
        tfNombreFiltro.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(nombreCol).setComponent(tfNombreFiltro);
        tfNombreFiltro.setSizeFull();
        tfNombreFiltro.setPlaceholder("Filtrar");

        TextField tfApPatFiltro = new TextField();
        tfApPatFiltro.addValueChangeListener(event -> dataProvider.addFilter(
                usuario -> StringUtils.startsWithIgnoreCase(usuario.getAp_paterno(),
                        tfApPatFiltro.getValue())));
        tfApPatFiltro.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(apPaternoCol).setComponent(tfApPatFiltro);
        tfApPatFiltro.setSizeFull();
        tfApPatFiltro.setPlaceholder("Filtrar");

        TextField tfApMatFiltro = new TextField();
        tfApMatFiltro.addValueChangeListener(event -> dataProvider.addFilter(
                usuario -> StringUtils.startsWithIgnoreCase(usuario.getAp_materno(),
                        tfApMatFiltro.getValue())));
        tfApMatFiltro.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(apMaternoCol).setComponent(tfApMatFiltro);
        tfApMatFiltro.setSizeFull();
        tfApMatFiltro.setPlaceholder("Filtrar");

        TextField tfTelFiltro = new TextField();
        tfTelFiltro.addValueChangeListener(event -> dataProvider.addFilter(
                usuario -> StringUtils.startsWithIgnoreCase(usuario.getTelefono(),
                        tfTelFiltro.getValue())));
        tfTelFiltro.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(telCol).setComponent(tfTelFiltro);
        tfTelFiltro.setSizeFull();
        tfTelFiltro.setPlaceholder("Filtrar");

        TextField tfCargoFiltro = new TextField();
        tfCargoFiltro.addValueChangeListener(event -> dataProvider.addFilter(
                usuario -> StringUtils.startsWithIgnoreCase(usuario.getTipo_usuario().getTipo(),
                        tfCargoFiltro.getValue())));
        tfCargoFiltro.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(cargoCol).setComponent(tfCargoFiltro);
        tfCargoFiltro.setSizeFull();
        tfCargoFiltro.setPlaceholder("Filtrar");

        grid.addComponentColumn(usuario -> {
                    Button btEdit = new Button( VaadinIcons.EDIT);
                    btEdit.setPrimaryStyleName("btn btn-primary");
                    btEdit.addClickListener(click ->{
                                TabSheet tab = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(),"tab-admins");
                                tab.replaceComponent(tab.getTab(0).getComponent(),new AdministradorFormLogica(usuario));
                                tab.getTab(0).setCaption("Edición");
                                tab.setSelectedTab(0);
                            }
                    );
                    return btEdit;
                }
        );

        grid.addSelectionListener(new SelectionListener<Administrador>() {
            @Override
            public void selectionChange(SelectionEvent<Administrador> selectionEvent) {
                if (grid.getCaption().equals(MENSAJE_ADM_ACTIVOS)){
                    if(grid.getSelectedItems().size()>0){
                        btEliminar.setVisible(true);
                    }else{
                        btEliminar.setVisible(false);
                    }
                }if (grid.getCaption().equals(MENSAJE_ADM_INACTIVOS)){
                    if(grid.getSelectedItems().size()>0){
                        btAgregar.setVisible(true);
                    }else{
                        btAgregar.setVisible(false);
                    }
                }
            }
        });
    }

    private void  instanciaBoton(){
        btAgregar.setPrimaryStyleName(ValoTheme.BUTTON_FRIENDLY);
        btAgregar.setVisible(false);
        btAgregar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if	(!grid.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar reactivación:",
                            "¿Deseas relamente reactivar los registros?",
                            "Re-Activar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        Set<Administrador> administradores = grid.getSelectedItems();
                                        List<Administrador> users = new ArrayList<>();
                                        users.addAll(administradores);
                                        for(Administrador u : users){
                                            u.setEstatus(new Estatus(1,"Activo"));
                                        }
                                        boolean ok = adAdministrador.cambiaEstatusAdministradores(users);
                                        if (ok){
                                            grid.setItems(adAdministrador.obtenerTodosAdministradoresExpirados());
                                            grid.setCaption(MENSAJE_ADM_INACTIVOS);
                                            btAgregar.setVisible(false);
                                            btEliminar.setVisible(false);
                                            Notification.show("Registros activados...",
                                                    Notification.Type.WARNING_MESSAGE);
                                        }
                                    }
                                }
                            }
                    );
                }
            }
        });

        btEliminar.setPrimaryStyleName("btn btn-danger");
        btEliminar.setVisible(false);
        btEliminar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if	(!grid.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar eliminación:",
                            "¿Deseas relamente eliminar los registros?",
                            "Eliminar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        Set<Administrador> administradores = grid.getSelectedItems();
                                        List<Administrador> users = new ArrayList<>();
                                        users.addAll(administradores);
                                        ADAdministrador adAdministrador = new ADAdministrador();
                                        for(Administrador u : users){
                                            u.setEstatus(new Estatus(2,"No Activo"));
                                        }
                                        boolean ok = adAdministrador.cambiaEstatusAdministradores(users);
                                        if (ok){
                                            grid.setItems(adAdministrador.obtenerTodosAdministradoresActivos());
                                            grid.setCaption(MENSAJE_ADM_ACTIVOS);
                                            btAgregar.setVisible(false);
                                            btEliminar.setVisible(false);
                                            Notification.show("Registros eliminados...",
                                                    Notification.Type.WARNING_MESSAGE);
                                        }
                                    }
                                }
                            }
                    );
                }
            }
        });
    }


}
