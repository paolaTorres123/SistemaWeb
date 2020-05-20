package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class TabSubsede extends VerticalLayout {

    private TabSheet tab;

    public TabSubsede(){

        tab = new TabSheet();

        tab.setId("tab-subsedes");
        tab.addTab(new FormSubsedeLogica(), "Alta Subsede");
        tab.addTab(new ListaSubsedes(), "Lista de Subsedes");
        this.addComponent(tab);
        VaadinSession.getCurrent().setAttribute("formulario",this);

    }
}
