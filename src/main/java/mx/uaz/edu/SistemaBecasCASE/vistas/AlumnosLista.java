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
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADAlumno;
import mx.uaz.edu.SistemaBecasCASE.modelos.Alumno;
import mx.uaz.edu.SistemaBecasCASE.modelos.Estatus;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AlumnosLista extends VerticalLayout{
    private String MENSAJE_AL_ACTIVOS="Alumnos Activos";
    private String MENSAJE_AL_INACTIVOS="Alumnos Inactivos";
    private String MENSAJE_AL_ACTIVOS_INACTIVOS ="Alumnos Activos e Inactivos";
    private Grid<Alumno> grid;
    private Button btEliminar,btAgregar;
    private ADAlumno adAlumno;
    private MenuBar mbOps;
    private  ListDataProvider<Alumno> dataProvider;

    public AlumnosLista() {
        grid = new Grid<Alumno>();
        adAlumno = new ADAlumno();
        mbOps =new MenuBar();
        btEliminar = new Button("Eliminar");
        btAgregar = new Button("Activar");

        instanciaLista();
        instanciaBoton();
        instanciaMenuOps();
        this.addComponents(mbOps,grid,btAgregar,btEliminar);
    }

    private void instanciaFiltros(List alumnos){
        dataProvider = new ListDataProvider<>(alumnos);
        grid.setDataProvider(dataProvider);
    }

    private void instanciaMenuOps(){
        MenuBar.Command comando;

        MenuBar.MenuItem lista = mbOps.addItem("Vistas",VaadinIcons.MENU, null);
        MenuBar.MenuItem alActivos = lista.addItem("Listar "+MENSAJE_AL_ACTIVOS);
        MenuBar.MenuItem alInActivos =lista.addItem("Listar "+MENSAJE_AL_INACTIVOS);
        MenuBar.MenuItem alAcInAc =lista.addItem("Listar "+ MENSAJE_AL_ACTIVOS_INACTIVOS);

        comando = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                if (menuItem.getText().equals("Listar "+MENSAJE_AL_ACTIVOS)){
                    grid.setItems(adAlumno.obtenerTodosAlumnosActivos());
                    grid.setCaption(MENSAJE_AL_ACTIVOS);
                    instanciaFiltros(adAlumno.obtenerTodosAlumnosActivos());
                }if (menuItem.getText().equals("Listar "+MENSAJE_AL_INACTIVOS)){
                    grid.setItems(adAlumno.obtenerTodosAlumnosExpirados());
                    grid.setCaption(MENSAJE_AL_INACTIVOS);
                    instanciaFiltros(adAlumno.obtenerTodosAlumnosExpirados());
                }if(menuItem.getText().equals("Listar "+MENSAJE_AL_ACTIVOS_INACTIVOS)) {
                    grid.setItems(adAlumno.obtenerTodosAlumnos());
                    grid.setCaption(MENSAJE_AL_ACTIVOS_INACTIVOS);
                    instanciaFiltros(adAlumno.obtenerTodosAlumnos());
                }

            }
        };
        alAcInAc.setCommand(comando);alActivos.setCommand(comando);alInActivos.setCommand(comando);
    }

    private void instanciaLista(){
        grid.setCaption(MENSAJE_AL_ACTIVOS);
        instanciaFiltros(adAlumno.obtenerTodosAlumnosActivos());
        Grid.Column<Alumno, ?>nombreCol = grid
                .addColumn(Alumno::getNombre).setCaption("Nombre");
        Grid.Column<Alumno, ?> apPaternoCol = grid
                .addColumn(Alumno::getAp_paterno).setCaption("Apellido Paterno");
        Grid.Column<Alumno, ?> apMaternoCol = grid
                .addColumn(Alumno::getAp_materno).setCaption("Apellido Materno");
        Grid.Column<Alumno, ?> matriculaCol = grid
                .addColumn(Alumno::getMatricula).setCaption("Matrícula");
        Grid.Column<Alumno, ?> curpCol = grid
                .addColumn(Alumno::getCurp).setCaption("CURP");
        grid.setWidth("100%");
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        HeaderRow filterRow = grid.appendHeaderRow();

        TextField tfNombreFiltro = new TextField();
        tfNombreFiltro.addValueChangeListener(event -> dataProvider.addFilter(
                alumno -> StringUtils.startsWithIgnoreCase(alumno.getNombre(),
                        tfNombreFiltro.getValue())));
        tfNombreFiltro.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(nombreCol).setComponent(tfNombreFiltro);
        tfNombreFiltro.setSizeFull();
        tfNombreFiltro.setPlaceholder("Filtrar");

        TextField tfApPatFiltro = new TextField();
        tfApPatFiltro.addValueChangeListener(event -> dataProvider.addFilter(
                alumno -> StringUtils.startsWithIgnoreCase(alumno.getAp_paterno(),
                        tfApPatFiltro.getValue())));
        tfApPatFiltro.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(apPaternoCol).setComponent(tfApPatFiltro);
        tfApPatFiltro.setSizeFull();
        tfApPatFiltro.setPlaceholder("Filtrar");

        TextField tfApMatFiltro = new TextField();
        tfApMatFiltro.addValueChangeListener(event -> dataProvider.addFilter(
                alumno -> StringUtils.startsWithIgnoreCase(alumno.getAp_materno(),
                        tfApMatFiltro.getValue())));
        tfApMatFiltro.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(apMaternoCol).setComponent(tfApMatFiltro);
        tfApMatFiltro.setSizeFull();
        tfApMatFiltro.setPlaceholder("Filtrar");

        TextField tfMatrFiltro = new TextField();
        tfMatrFiltro.addValueChangeListener(event -> dataProvider.addFilter(
                alumno -> StringUtils.startsWithIgnoreCase(String.valueOf(alumno.getMatricula()),
                        tfMatrFiltro.getValue())));
        tfMatrFiltro.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(matriculaCol).setComponent(tfMatrFiltro);
        tfMatrFiltro.setSizeFull();
        tfMatrFiltro.setPlaceholder("Filtrar");

        TextField tfCurpFiltro = new TextField();
        tfCurpFiltro.addValueChangeListener(event -> dataProvider.addFilter(
                alumno -> StringUtils.startsWithIgnoreCase(alumno.getCurp(),
                        tfCurpFiltro.getValue())));
        tfCurpFiltro.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(curpCol).setComponent(tfCurpFiltro);
        tfCurpFiltro.setSizeFull();
        tfCurpFiltro.setPlaceholder("Filtrar");

        grid.addComponentColumn(alumno -> {
                    Button btEdit = new Button(VaadinIcons.EDIT);
                    btEdit.setPrimaryStyleName("btn btn-primary");
                    btEdit.addClickListener(click ->{
                                TabSheet tab = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(),"tab-alumnos");
                                tab.replaceComponent(tab.getTab(0).getComponent(),new AlumnoEdicionFormLogica(alumno));
                                tab.getTab(0).setCaption("Edición");
                                tab.setSelectedTab(0);
                            }
                    );
                    return btEdit;
                }
        );

        grid.addSelectionListener(new SelectionListener<Alumno>() {
            @Override
            public void selectionChange(SelectionEvent<Alumno> selectionEvent) {
                if (grid.getCaption().equals(MENSAJE_AL_ACTIVOS)){
                    if(grid.getSelectedItems().size()>0){
                        btEliminar.setVisible(true);
                    }else{
                        btEliminar.setVisible(false);
                    }
                }if (grid.getCaption().equals(MENSAJE_AL_INACTIVOS)){
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
                                        Set<Alumno> usuarios = grid.getSelectedItems();
                                        List<Alumno> users = new ArrayList<>();
                                        users.addAll(usuarios);
                                        for(Alumno u : users){
                                            u.setEstatus(new Estatus(1,"Activo"));
                                        }
                                        boolean ok = adAlumno.cambiaEstatusAlumnos(users);
                                        if (ok){
                                            grid.setItems(adAlumno.obtenerTodosAlumnosExpirados());
                                            grid.setCaption(MENSAJE_AL_INACTIVOS);
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
                                        Set<Alumno> usuarios = grid.getSelectedItems();
                                        List<Alumno> users = new ArrayList<>();
                                        users.addAll(usuarios);
                                        for(Alumno u : users){
                                            u.setEstatus(new Estatus(2,"No Activo"));
                                        }
                                        boolean ok = adAlumno.cambiaEstatusAlumnos(users);
                                        if (ok){
                                            grid.setItems(adAlumno.obtenerTodosAlumnosActivos());
                                            grid.setCaption(MENSAJE_AL_INACTIVOS);
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