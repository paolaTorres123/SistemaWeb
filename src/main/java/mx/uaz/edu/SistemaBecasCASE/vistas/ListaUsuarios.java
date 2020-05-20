package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ImageRenderer;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADUsuario;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;
import mx.uaz.edu.SistemaBecasCASE.utils.Broadcaster;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListaUsuarios extends VerticalLayout {

    private Grid<Usuario> gridUsuario;
    private Button btnEliminar;
    private ADUsuario adUsuario;

    public ListaUsuarios(){
        setId("listaUsuarios");
        gridUsuario = new Grid<Usuario>(Usuario.class);
        adUsuario = new ADUsuario();
        gridUsuario.setItems(adUsuario.obtenerTodosUsuarios());
        gridUsuario.setColumns("nombre_usuario", "email", "tipo_usuario");

        gridUsuario.getColumn("email").setCaption("E-mail");
        gridUsuario.getColumn("nombre_usuario").setCaption("Usuario");
        gridUsuario.getColumn("tipo_usuario").setCaption("Tipo Usuario");
        gridUsuario.setWidth("100%");
        gridUsuario.setSelectionMode(Grid.SelectionMode.MULTI);

        gridUsuario.addComponentColumn(usuario -> {
            //Button button = new Button("Editar");
            Button button = new Button("", VaadinIcons.EDIT);
            button.setPrimaryStyleName("btn btn-danger");
            button.addClickListener(clickEvent -> {
                TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(),"tab-usuarios");
                tabSheet.setSelectedTab(0);
                tabSheet.replaceComponent(tabSheet.getTab(0).getComponent(), new FormUsuarioDesignLogica(usuario));
            });
            return button;

        });

        btnEliminar = new Button("Eliminar");
        btnEliminar.setPrimaryStyleName("btn btn-primary");

        btnEliminar.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if	(!gridUsuario.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar eliminación:",
                            "¿Deseas relamente eliminar los registros?",
                            "Eliminar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        Set<Usuario> usuarios = gridUsuario.getSelectedItems();
                                        List<Usuario> users = new ArrayList<>();
                                        users.addAll(usuarios);
                                        ADUsuario adUsuario = new ADUsuario();
                                        boolean ok = adUsuario.eliminarUsuarios(users);
                                        if (ok){
                                            Broadcaster.broadcast("usuarios");
                                            Notification.show("Registros eliminados...",Notification.Type.WARNING_MESSAGE);
                                        } else{
                                            Notification.show("Algunos registros no pudieron ser eliminados", Notification.Type.WARNING_MESSAGE);
                                        }
                                    }
                                }
                            });
                }
                else
                    Notification.show("Selecciona al menos un usuario para eliminar",Notification.Type.WARNING_MESSAGE);
            }
        });
        this.addComponents(gridUsuario, btnEliminar);
    }

    public void actualizaGrid(){
        adUsuario = new ADUsuario();
        gridUsuario.setItems(adUsuario.obtenerTodosUsuarios());
    }

}