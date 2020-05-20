package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.mysql.cj.util.StringUtils;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADFuncion;
import mx.uaz.edu.SistemaBecasCASE.modelos.Funcion;
import mx.uaz.edu.SistemaBecasCASE.modelos.Responsable;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListaFunciones extends VerticalLayout {
    private Grid<Funcion> gridFuncion;
    ADFuncion adFuncion;
    private HeaderRow filtroRenglones;
    private  Button eliminar;


    public ListaFunciones() {

        gridFuncion = new Grid<Funcion>(Funcion.class);
        adFuncion = new ADFuncion();
        gridFuncion.setItems(adFuncion.obtenerTodasFunciones());
        gridFuncion.setColumns("funcion"); //se coloc lo del result map que esta en la clase lo del parametro derecha

        gridFuncion.getColumn("funcion").setCaption("Nombre de la Función");

        gridFuncion.setWidth("100%");
        gridFuncion.setSelectionMode(Grid.SelectionMode.MULTI);

        aplicarFiltrados(adFuncion.obtenerTodasFunciones());

        gridFuncion.addComponentColumn(funcion -> {
            Button button = new Button("", VaadinIcons.EDIT);
            button.setPrimaryStyleName("btn btn-danger");
            button.addClickListener(clickEvent -> {
                TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(), "tab-funcion");
                tabSheet.setSelectedTab(0);
                tabSheet.replaceComponent(tabSheet.getTab(0).getComponent(), new FormFuncionLogica(funcion));
            });
            return button;

        }).setId("Editar");

        eliminar= new Button("Eliminar");
        eliminar.setPrimaryStyleName("btn btn-danger");
        eliminar.addClickListener(new ListaFunciones.Botones());

        this.addComponents( gridFuncion,eliminar);

    }

    public void aplicarFiltrados(List<Funcion>listaFunciones){
        ListDataProvider<Funcion>dataProvider=
                new ListDataProvider<>(listaFunciones);
        gridFuncion.setDataProvider(dataProvider);
        if(filtroRenglones==null){
            filtroRenglones=gridFuncion.appendHeaderRow();
        }

        TextField nombrefuncion =new TextField();
        nombrefuncion.addValueChangeListener(event ->dataProvider.addFilter(
                funcion -> StringUtils.startsWithIgnoreCase(funcion.getFuncion(),nombrefuncion.getValue())
        ) );

        nombrefuncion.setValueChangeMode(ValueChangeMode.EAGER);

        filtroRenglones.getCell(gridFuncion.getColumn("funcion")).setComponent(nombrefuncion);
        nombrefuncion.setSizeFull();
        nombrefuncion.setPlaceholder("Nombre Función");

    }
    class Botones implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {

            if (clickEvent.getButton() == eliminar) {
                if (!gridFuncion.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar eliminación:",
                            "¿Deseas relamente eliminar los registros?",
                            "Eliminar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        Set<Funcion> funcion = gridFuncion.getSelectedItems();
                                        List<Funcion> funciones = new ArrayList<>();
                                        funciones.addAll(funcion);
                                        boolean ok = adFuncion.eliminarFuncion(funciones);
                                        if (ok) {
                                            gridFuncion.setItems(adFuncion.obtenerTodasFunciones());
                                            Notification.show("Registros eliminados...", Notification.Type.WARNING_MESSAGE);
                                        } else {
                                            Notification.show("Algunos registros no pudieron ser eliminados porque " +
                                                    "se encuentran asociados a otros registros", Notification.Type.WARNING_MESSAGE);
                                        }
                                        aplicarFiltrados(adFuncion.obtenerTodasFunciones());
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos un comedor para eliminar", Notification.Type.WARNING_MESSAGE);
            }
        }
    }
}
