package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;
import mx.uaz.edu.SistemaBecasCASE.utils.ConfigVars;
import org.vaadin.dialogs.ConfirmDialog;

public class Main extends CustomLayout {

    Button btnAcercaDe, btnAlumno, btnUsuario, btnResponsable, btnCasa, btnCuarto;
    Button btnSupervisor, btnComedor,btnAdministrador;

    Button btnAcercaDeRes, btnAlumnoRes, btnUsuarioRes, btnResponsableRes, btnCasaRes, btnCuartoRes;
    Button btnSupervisorRes, btnComedorRes, btnAdministradorRes, btnBecas, btnBecasRes;

    VerticalLayout vlPrincipal;
    Label labTitulo, labBrenda, labPaola, labJorge, labRaul, labMario,lblNombreUsuario;
    Botones botones;

    Button btHome,btlogOut;

    public Main(int tipoUsuario){

        if(tipoUsuario == 0){
            setTemplateName("mainSolicitud");
        }else if(tipoUsuario == 1 || tipoUsuario == 3){
            setTemplateName("main");
        }else{
            setTemplateName("mainSupervidor");
        }
        setStyleName("main");
        setSizeFull();
        setResponsive(true);

        btnAcercaDe = new Button("Acerca De");
        btnAcercaDe.setPrimaryStyleName("botones-menu");

        btnAcercaDeRes = new Button("Acerca De");
        btnAcercaDeRes.setPrimaryStyleName("botones-menu");

        labTitulo = new Label("Equipo de desarrollo :");
        labBrenda = new Label("Brenda Yasmin Barrios Becerra");
        labPaola = new Label("Paola Torres Macías");
        labJorge = new Label("Jorge Alfonso Solís Galván");
        labMario = new Label("Mario Alberto Muro Barraza");
        labRaul = new Label("Raúl Bermúdez Robles");

        botones = new Botones();

        btnAlumno = new Button("Alumnos");
        btnAlumno.setPrimaryStyleName("botones-menu");
        btnAlumno.addClickListener(botones);
        btnCasa = new Button("Casas");
        btnCasa.setPrimaryStyleName("botones-menu");
        btnCasa.addClickListener(botones);
        btnComedor = new Button("Comedores");
        btnComedor.setPrimaryStyleName("botones-menu");
        btnComedor.addClickListener(botones);
        btnCuarto = new Button("Cuartos");
        btnCuarto.setPrimaryStyleName("botones-menu");
        btnCuarto.addClickListener(botones);
        btnResponsable = new Button("Responsables");
        btnResponsable.setPrimaryStyleName("botones-menu");
        btnResponsable.addClickListener(botones);
        btnSupervisor = new Button("Supervisores");
        btnSupervisor.setPrimaryStyleName("botones-menu");
        btnSupervisor.addClickListener(botones);
        btnUsuario = new Button("Usuarios");
        btnUsuario.setPrimaryStyleName("botones-menu");
        btnAdministrador = new Button("Administradores");
        btnAdministrador.setPrimaryStyleName("botones-menu");
        btnAdministrador.addClickListener(botones);
        btnUsuario.addClickListener(botones);
        btnBecas = new Button("Becas");
        btnBecas.setPrimaryStyleName("botones-menu");
        btnBecas.addClickListener(botones);


        btnAlumnoRes = new Button("Alumnos");
        btnAlumnoRes.setPrimaryStyleName("botones-menu");
        btnAlumnoRes.addClickListener(botones);
        btnCasaRes = new Button("Casas");
        btnCasaRes.setPrimaryStyleName("botones-menu");
        btnCasaRes.addClickListener(botones);
        btnComedorRes = new Button("Comedores");
        btnComedorRes.setPrimaryStyleName("botones-menu");
        btnComedorRes.addClickListener(botones);
        btnCuartoRes = new Button("Cuartos");
        btnCuartoRes.setPrimaryStyleName("botones-menu");
        btnCuartoRes.addClickListener(botones);
        btnResponsableRes = new Button("Responsables");
        btnResponsableRes.setPrimaryStyleName("botones-menu");
        btnResponsableRes.addClickListener(botones);
        btnSupervisorRes = new Button("Supervisores");
        btnSupervisorRes.setPrimaryStyleName("botones-menu");
        btnSupervisorRes.addClickListener(botones);
        btnUsuarioRes = new Button("Usuarios");
        btnUsuarioRes.setPrimaryStyleName("botones-menu");
        btnAdministradorRes = new Button("Administradores");
        btnAdministradorRes.setPrimaryStyleName("botones-menu");
        btnAdministradorRes.addClickListener(botones);
        btnBecasRes = new Button("Becas");
        btnBecasRes.setPrimaryStyleName("botones-menu");
        btnBecasRes.addClickListener(botones);

        btHome = new Button("Inicio");
        btHome.setPrimaryStyleName(ValoTheme.BUTTON_QUIET);
        btHome.addClickListener(botones);
        btlogOut = new Button("Salir");
        btlogOut.setPrimaryStyleName(ValoTheme.BUTTON_QUIET);
        btlogOut.addClickListener(botones);

        btnUsuarioRes.addClickListener(botones);

        btnAcercaDe.addClickListener(botones);

        vlPrincipal = new VerticalLayout();
        vlPrincipal.setId("vl-principal");

        new Botones().cargaImagenHome();

        addComponent(vlPrincipal, "mi-contenedor");

        if(tipoUsuario == 1 || tipoUsuario == 3 ){
            addComponent(btnResponsable, "responsable-tab");
            addComponent(btnSupervisor, "supervisor-tab");
            addComponent(btnUsuario, "usuario-tab");
            addComponent(btnAdministrador,"admin-tab");
            addComponent(btnResponsableRes, "responsable-tabRes");
            addComponent(btnSupervisorRes, "supervisor-tabRes");
            addComponent(btnUsuarioRes, "usuario-tabRes");
            addComponent(btnAdministradorRes,"admin-tabRes");
            addComponent(btnBecas, "beca-tab");
            addComponent(btnBecasRes,"beca-tabRes");
            addComponent(btnAlumno, "alumno-tab");
            addComponent(btnCasa, "casa-tab");
            addComponent(btnComedor, "comedor-tab");
            addComponent(btnCuarto, "cuarto-tab");
            addComponent(btnAcercaDe, "acerca-de");
            addComponent(btnAlumnoRes, "alumno-tabRes");
            addComponent(btnCasaRes, "casa-tabRes");
            addComponent(btnComedorRes, "comedor-tabRes");
            addComponent(btnCuartoRes, "cuarto-tabRes");
            addComponent(btnAcercaDeRes, "acerca-deRes");
            lblNombreUsuario = new Label(((Usuario) VaadinSession.getCurrent().getAttribute("usuario")).getNombre_usuario());
            addComponent(lblNombreUsuario, "usNombre");
        }else if (tipoUsuario == 2 || tipoUsuario == 4){
            addComponent(btnAlumno, "alumno-tab");
            addComponent(btnCasa, "casa-tab");
            addComponent(btnComedor, "comedor-tab");
            addComponent(btnCuarto, "cuarto-tab");
            addComponent(btnAcercaDe, "acerca-de");
            addComponent(btnAlumnoRes, "alumno-tabRes");
            addComponent(btnCasaRes, "casa-tabRes");
            addComponent(btnComedorRes, "comedor-tabRes");
            addComponent(btnCuartoRes, "cuarto-tabRes");
            addComponent(btnAcercaDeRes, "acerca-deRes");
            lblNombreUsuario = new Label(((Usuario) VaadinSession.getCurrent().getAttribute("usuario")).getNombre_usuario());
            addComponent(lblNombreUsuario, "usNombre");
        }else if (tipoUsuario == 0){
            vlPrincipal.removeAllComponents();
            vlPrincipal.addComponent(new FormSolicitudDesignLogica());
        }


        addComponent(btlogOut, "logOut");

        addComponent(btHome,"boton-home");



    }

    public class Botones implements Button.ClickListener{
        private void cargaImagenHome(){
            vlPrincipal.removeAllComponents();
            Image imagenHome = new Image();
            imagenHome.setSource(new ThemeResource("layouts/img/logo/CASE.jpg"));
            imagenHome.setPrimaryStyleName("imagen-principal");
            vlPrincipal.addComponent(imagenHome);
        }

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {

            if(clickEvent.getButton() == btnAcercaDe || clickEvent.getButton() == btnAcercaDeRes){
                vlPrincipal.removeAllComponents();
                vlPrincipal.addComponent(labTitulo);
                vlPrincipal.addComponent(labPaola);
                vlPrincipal.addComponent(labBrenda);
                vlPrincipal.addComponent(labRaul);
                vlPrincipal.addComponent(labMario);
                vlPrincipal.addComponent(labJorge);
            }else if(clickEvent.getButton() == btnAlumno || clickEvent.getButton() == btnAlumnoRes){
                vlPrincipal.removeAllComponents();
                vlPrincipal.addComponent(new AlumnosTab());
            }else if(clickEvent.getButton() == btnCasa || clickEvent.getButton() == btnCasaRes){
                vlPrincipal.removeAllComponents();
                vlPrincipal.addComponent(new CasaTab());
            }else if(clickEvent.getButton() == btnComedor || clickEvent.getButton() == btnComedorRes){
                vlPrincipal.removeAllComponents();
                vlPrincipal.addComponent(new TabComedor());
            }else if(clickEvent.getButton() == btnCuarto || clickEvent.getButton() == btnCuartoRes){
                vlPrincipal.removeAllComponents();
                vlPrincipal.addComponent(new CuartoTab());
            }else if(clickEvent.getButton() == btnResponsable || clickEvent.getButton() == btnResponsableRes){
                vlPrincipal.removeAllComponents();
                vlPrincipal.addComponent(new TabResponsable());
            }else if(clickEvent.getButton() == btnSupervisor || clickEvent.getButton() == btnSupervisorRes){
                vlPrincipal.removeAllComponents();
                vlPrincipal.addComponent(new TabSupervisor());
            }else if(clickEvent.getButton() == btnUsuario || clickEvent.getButton() == btnUsuarioRes){
                vlPrincipal.removeAllComponents();
                vlPrincipal.addComponent(new TabUsuarios());
            }else if(clickEvent.getButton() == btnAdministrador || clickEvent.getButton() == btnAdministradorRes){
                vlPrincipal.removeAllComponents();
                vlPrincipal.addComponent(new AdministradoresTab());
            }else if(clickEvent.getButton() == btnBecas || clickEvent.getButton() == btnBecasRes) {
                vlPrincipal.removeAllComponents();
                vlPrincipal.addComponent(new TabBecas());
            }else if(clickEvent.getButton() == btHome){
                cargaImagenHome();
            }else if(clickEvent.getButton() == btlogOut){
                ConfirmDialog.show(
                    UI.getCurrent(),
                    "Confirmar cierre de sesión",
                    "¿Deseas salir del sistema?",
                    "Salir", "Continuar Adentro",
                    new ConfirmDialog.Listener() {
                        public void onClose(ConfirmDialog dialog) {
                            if (dialog.isConfirmed()) {
                                VaadinSession.getCurrent().close();
                                UI.getCurrent().getPage().setLocation(ConfigVars.APP_NAME);
                            }
                        }
                    });
            }
        }
    }

    public void agregaContenido(Layout layout){
        vlPrincipal.removeAllComponents();
        vlPrincipal.addComponent(layout);
    }

}