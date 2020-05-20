package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class AlumnosTab extends VerticalLayout {
    private TabSheet tab;

    public AlumnosTab(){
        tab = new TabSheet();
        tab.setId("tab-alumnos");
        tab.addTab(new AlumnoNuevoFormLogica(), "Alta");
        tab.addTab(new AlumnosLista(), "Lista");
        tab.setSelectedTab(1);
        this.addComponent(tab);

        VaadinSession.getCurrent().setAttribute("formularioAlumnos",this);
    }
}
