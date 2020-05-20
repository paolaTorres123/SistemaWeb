package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.mysql.cj.util.StringUtils;

import com.vaadin.ui.components.grid.HeaderRow;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADSubsede;
import mx.uaz.edu.SistemaBecasCASE.modelos.Comedor;
import mx.uaz.edu.SistemaBecasCASE.modelos.Subsede;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListaSubsedes extends VerticalLayout {
    private Grid<Subsede> gridSubsede;
    ADSubsede adSubsede;
    private HeaderRow filtroRenglones;
    private  Button eliminar;

    public ListaSubsedes() {

        gridSubsede = new Grid<Subsede>(Subsede.class);
        adSubsede = new ADSubsede();
        gridSubsede.setItems(adSubsede.obtenerTodasSubsedes());
        gridSubsede.setColumns("nombreSubsede");
        gridSubsede.getColumn("nombreSubsede").setCaption("Nombre de Subsede");

        gridSubsede.setWidth("100%");
        gridSubsede.setSelectionMode(Grid.SelectionMode.MULTI);
        aplicarFiltrados(adSubsede.obtenerTodasSubsedes());

        gridSubsede.addComponentColumn(subsede -> {
            Button button = new Button("", VaadinIcons.EDIT);
            button.setPrimaryStyleName("btn btn-danger");
            button.addClickListener(clickEvent -> {
                TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-subsedes");

                tabSheet.setSelectedTab(0);
                tabSheet.replaceComponent(tabSheet.getTab(0).getComponent(), new FormSubsedeLogica(subsede));
            });
            return button;

        }).setCaption("Editar");
        eliminar= new Button("Eliminar");
        eliminar.setPrimaryStyleName("btn btn-danger");
        eliminar.addClickListener(new ListaSubsedes.Botones());
        this.addComponents( gridSubsede,eliminar);

    }


    public void aplicarFiltrados(List<Subsede> listaFunciones){
        ListDataProvider<Subsede> dataProvider=
                new ListDataProvider<>(listaFunciones);
        gridSubsede.setDataProvider(dataProvider);
        if(filtroRenglones==null){
            filtroRenglones=gridSubsede.appendHeaderRow();
        }

        TextField nombreSubsede =new TextField();
        nombreSubsede.addValueChangeListener(event ->dataProvider.addFilter(
                funcion -> StringUtils.startsWithIgnoreCase(funcion.getNombreSubsede(),nombreSubsede.getValue())
        ) );

        nombreSubsede.setValueChangeMode(ValueChangeMode.EAGER);

        filtroRenglones.getCell(gridSubsede.getColumn("nombreSubsede")).setComponent(nombreSubsede);
        nombreSubsede.setSizeFull();
        nombreSubsede.setPlaceholder("Nombre Subsede");

    }

    class Botones implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {

            if (clickEvent.getButton() == eliminar) {
                if (!gridSubsede.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar eliminación:",
                            "¿Deseas relamente eliminar los registros?",
                            "Eliminar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        Set<Subsede> subsede = gridSubsede.getSelectedItems();
                                        List<Subsede> subsedes = new ArrayList<>();
                                       subsedes.addAll(subsede);
                                        boolean ok = adSubsede.eliminarSubsede(subsedes);
                                        if (ok) {
                                            gridSubsede.setItems(adSubsede.obtenerTodasSubsedes());
                                            Notification.show("Registros eliminados...", Notification.Type.WARNING_MESSAGE);
                                        } else {
                                            Notification.show("Algunos registros no pudieron ser eliminados porque " +
                                                    "se encuentran asociados a otros registros", Notification.Type.WARNING_MESSAGE);
                                        }
                                        aplicarFiltrados(adSubsede.obtenerTodasSubsedes());
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos un comedor para eliminar", Notification.Type.WARNING_MESSAGE);
            }
        }
    }
}
