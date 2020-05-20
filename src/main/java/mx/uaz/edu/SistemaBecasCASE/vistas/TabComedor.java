package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class TabComedor extends VerticalLayout {

    private TabSheet tab;

    public TabComedor(){
        tab = new TabSheet();
        tab.setId("tab-comedores");
        tab.addTab(new FormComedorLogica(), "Alta Comedores");
        tab.addTab(new ListaComedores(), "Lista de Comedores");
        this.addComponent(tab);
        VaadinSession.getCurrent().setAttribute("formulario",this);
    }


}
