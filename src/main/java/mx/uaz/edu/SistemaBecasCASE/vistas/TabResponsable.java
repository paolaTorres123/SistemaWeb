package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class TabResponsable extends VerticalLayout {

    private TabSheet tab;

    public TabResponsable(){
        tab = new TabSheet();
        tab.setId("tab-responsables");
        tab.addTab(new FormResponsableLogica(), "Alta Responsables");
        tab.addTab(new ListaResponsables(), "Lista de Responsables");
        this.addComponent(tab);
        VaadinSession.getCurrent().setAttribute("formulario",this);
    }

}
