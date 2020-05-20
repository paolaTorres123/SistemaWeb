package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class TabFuncion extends VerticalLayout {

    private TabSheet tab;

    public TabFuncion(){

        tab = new TabSheet();
        tab.setId("tab-funcion"); //Se debe de llamar igual que lo de formfuncionLogica
        tab.addTab(new FormFuncionLogica(), "Alta Funciones");
        tab.addTab(new ListaFunciones(), "Lista de Funciones");
        this.addComponent(tab);
        VaadinSession.getCurrent().setAttribute("formulario",this);

    }
}
