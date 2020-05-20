package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class CuartoTab extends VerticalLayout {
    private TabSheet tab;

    public CuartoTab(){
        tab = new TabSheet();
        tab.setId("tab-cuarto");
        tab.addTab(new FormCuartoLogica(),"Alta");
        tab.addTab(new CuartoLista(),"Lista");
        this.addComponent(tab);
        VaadinSession.getCurrent().setAttribute("formulario",this);
    }

}
