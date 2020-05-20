package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADBeca;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.ADUsuario;
import mx.uaz.edu.SistemaBecasCASE.modelos.Beca;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;
import mx.uaz.edu.SistemaBecasCASE.utils.Broadcaster;
import mx.uaz.edu.SistemaBecasCASE.utils.BuscarComponentes;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListaBecasAlimentacion extends VerticalLayout {

    private Grid<Beca> gridBecas;
    private Button btnEliminar;
    private Button btnAceptar;
    private Button btnRechazar;
    private ADBeca adBeca;
    private boolean repetido;

    public ListaBecasAlimentacion(){
        setId("listaBecasAlimentacion");
        gridBecas = new Grid<Beca>(Beca.class);
        adBeca = new ADBeca();
        gridBecas.setItems(adBeca.obtenerBecasAlimentacion());
        gridBecas.setColumns("alumno", "fecha_formateada", "estatus",
                "tipoSolicitud");

        gridBecas.getColumn("fecha_formateada").setCaption("Fecha de Solicitud");
        gridBecas.getColumn("tipoSolicitud").setCaption("Tipo de Solicitud");
        gridBecas.setWidth("100%");
        gridBecas.setSelectionMode(Grid.SelectionMode.MULTI);

        /*
        gridBecas.addComponentColumn(usuario -> {
            //Button button = new Button("Editar");
            Button button = new Button("", VaadinIcons.EDIT);
            button.setPrimaryStyleName("btn btn-danger");
            button.addClickListener(clickEvent -> {
                TabSheet tabSheet = (TabSheet) BuscarComponentes.findComponentById(UI.getCurrent(),"tab-usuarios");
                tabSheet.setSelectedTab(0);
                tabSheet.replaceComponent(tabSheet.getTab(0).getComponent(), new FormUsuarioDesignLogica(usuario));
            });
            return button;

        });*/

        btnEliminar = new Button("Eliminar");
        btnEliminar.setPrimaryStyleName("btn btn-primary");
        btnEliminar.addClickListener(new Botones());

        btnAceptar = new Button("Aceptar");
        btnAceptar.setPrimaryStyleName("btn btn-success");
        btnAceptar.addClickListener(new Botones());

        btnRechazar = new Button("Rechazar");
        btnRechazar.setPrimaryStyleName("btn btn-warning");
        btnRechazar.addClickListener(new Botones());


        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponents(btnAceptar, btnRechazar, btnEliminar);
        this.addComponents(gridBecas, hl);
    }

    public void actualizaGrid(){
        adBeca = new ADBeca();
        gridBecas.setItems(adBeca.obtenerBecasAlimentacion());
    }

    class Botones implements Button.ClickListener{
        @Override
        public void buttonClick(Button.ClickEvent event) {

            if(event.getButton() == btnEliminar) {

                if (!gridBecas.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar eliminación:",
                            "¿Deseas relamente eliminar los registros?",
                            "Eliminar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        Set<Beca> becas = gridBecas.getSelectedItems();
                                        List<Beca> beca = new ArrayList<>();
                                        beca.addAll(becas);
                                        boolean ok = adBeca.eliminarBecas(beca);
                                        if (ok) {
                                            //Broadcaster.broadcast("becas");
                                            actualizaGrid();
                                            Notification.show("Registros eliminados...", Notification.Type.WARNING_MESSAGE);
                                        }
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos una solicitud para eliminar", Notification.Type.WARNING_MESSAGE);
            }else if (event.getButton() == btnAceptar){
                if (!gridBecas.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar aceptar:",
                            "¿Deseas relamente aceptar la(s) solicitudes de becas?",
                            "Aceptar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        repetido = false;
                                        Set<Beca> beca = gridBecas.getSelectedItems();
                                        List<Beca> becas = new ArrayList<>();
                                        for(Beca c : beca){
                                            if(c.getEstatus().getIdEstatus() != 4){
                                                becas.add(c);
                                            }else{
                                                repetido = true;
                                            }
                                        }
                                        if(repetido)
                                            Notification.show("Algunas solicitudes ya se encontraban aceptadas...", Notification.Type.WARNING_MESSAGE);
                                        //comedores.addAll(comedor);
                                        boolean ok = adBeca.aceptarBeca(becas);
                                        if (ok) {
                                            gridBecas.setItems(adBeca.obtenerBecasAlimentacion());
                                            Notification.show("Becas aceptadas...", Notification.Type.WARNING_MESSAGE);
                                        }
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos una beca para aceptar", Notification.Type.WARNING_MESSAGE);


            }else if(event.getButton() == btnRechazar){
                if (!gridBecas.getSelectedItems().isEmpty()) {
                    ConfirmDialog.show(
                            UI.getCurrent(),
                            "Confirmar rechazo:",
                            "¿Deseas relamente rechazar las solicitudes?",
                            "Rechazar", "Cancelar",
                            new ConfirmDialog.Listener() {
                                public void onClose(ConfirmDialog dialog) {
                                    if (dialog.isConfirmed()) {
                                        repetido = false;
                                        Set<Beca> beca = gridBecas.getSelectedItems();
                                        List<Beca> becas = new ArrayList<>();
                                        for(Beca c : beca){
                                            if(c.getEstatus().getIdEstatus() != 5){
                                                becas.add(c);
                                            }else{
                                                repetido = true;
                                            }
                                        }
                                        if(repetido)
                                            Notification.show("Algunas solicitudes ya se encontraban rechazadas...", Notification.Type.WARNING_MESSAGE);

                                        boolean ok = adBeca.rechazarBeca(becas);
                                        if (ok) {
                                            gridBecas.setItems(adBeca.obtenerBecasAlimentacion());
                                            Notification.show("Solicitudes rechazadas...", Notification.Type.WARNING_MESSAGE);
                                        }
                                    }
                                }
                            });
                } else
                    Notification.show("Selecciona al menos una solicitud para rechazar", Notification.Type.WARNING_MESSAGE);


            }
        }

    }

}