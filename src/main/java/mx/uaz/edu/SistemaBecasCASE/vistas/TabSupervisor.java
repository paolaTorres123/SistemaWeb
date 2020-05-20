package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class TabSupervisor extends VerticalLayout {

    private TabSheet tab;

    public TabSupervisor(){
        tab = new TabSheet();
        tab.setId("tab-supervisores");
        tab.addTab(new FormSupervisorLogica(), "Alta Supervisores");
        tab.addTab(new ListaSupervisores(), "Lista de Supervisores");
        this.addComponent(tab);
        VaadinSession.getCurrent().setAttribute("formulario",this);
    }
}
