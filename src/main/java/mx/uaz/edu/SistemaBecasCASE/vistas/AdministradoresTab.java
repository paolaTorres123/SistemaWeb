package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class AdministradoresTab extends VerticalLayout {
    private TabSheet tab;

    public AdministradoresTab(){
        tab = new TabSheet();
        tab.setId("tab-admins");
        tab.addTab(new AdministradorNuevoFormLogica(), "Alta");
        tab.addTab(new AdministradoresLista(), "Lista");
        tab.setSelectedTab(1);
        this.addComponent(tab);

        VaadinSession.getCurrent().setAttribute("formularioAdministradores",this);
    }
}
