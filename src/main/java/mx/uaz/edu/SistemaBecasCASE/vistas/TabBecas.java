package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class TabBecas extends VerticalLayout {

    private TabSheet tab;

    public TabBecas(){
        tab = new TabSheet();
        tab.setId("tab-becas");
        tab.addTab(new ListaBecasAlimentacion(), "Becas de Alimentación");
        tab.addTab(new ListaBecasHospedaje(), "Becas de Hospedaje");
        this.addComponent(tab);
        //VaadinSession.getCurrent().setAttribute("formulario",this);
    }


}
