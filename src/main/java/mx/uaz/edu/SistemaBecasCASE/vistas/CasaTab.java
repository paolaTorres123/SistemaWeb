package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class CasaTab extends VerticalLayout {
    private TabSheet tab;

    public CasaTab(){
        tab = new TabSheet();
        tab.setId("tab-casa");
        tab.addTab(new FormCasaLogica(),"Alta");
        tab.addTab(new CasaLista(),"Lista");
        this.addComponent(tab);
        VaadinSession.getCurrent().setAttribute("formulario",this);
    }
}

