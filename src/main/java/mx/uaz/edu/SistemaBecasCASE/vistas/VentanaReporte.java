package mx.uaz.edu.SistemaBecasCASE.vistas;


import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Window;

import java.io.File;

public class VentanaReporte extends Window {

    private BrowserFrame bfReporte;

    public VentanaReporte(int idUsuario) {
        setWidth("700px");
        setHeight("500px");
        setCaption("Solicitud de Beca");
        setIcon(VaadinIcons.GRID);
        setStyleName("ventana-report");

		bfReporte = new BrowserFrame("",
        		new ExternalResource("http://localhost:8090/birt-viewer/preview?__report=reportes/solicitud.rptdesign&__format=pdf&idAlumno="+idUsuario, "application/pdf"));
        bfReporte.setSizeFull();
        setContent(bfReporte);
    }
}