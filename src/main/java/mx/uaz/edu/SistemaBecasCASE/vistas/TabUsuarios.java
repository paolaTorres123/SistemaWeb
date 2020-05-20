package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class TabUsuarios extends VerticalLayout {

    private TabSheet tab;

    public TabUsuarios(){
        tab = new TabSheet();
        tab.setId("tab-usuarios");
        tab.addTab(new FormUsuarioDesignLogica(), "Alta");
        tab.addTab(new ListaUsuarios(), "Lista");
        this.addComponent(tab);
        VaadinSession.getCurrent().setAttribute("formulario",this);
    }

}
