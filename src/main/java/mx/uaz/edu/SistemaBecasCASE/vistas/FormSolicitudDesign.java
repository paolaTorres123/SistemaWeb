package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.CheckBox;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class FormSolicitudDesign extends CssLayout {
    protected Label lbTitulo;
    protected CssLayout formSolicita;
    protected RadioButtonGroup<String> rbTipoAlumno;
    protected Button btnContinuarS1;
    protected CssLayout formTramitar;
    protected TextField txMatricula;
    protected PasswordField pfContrasena;
    protected TextField txEmail;
    protected Button btnContinuarS2;
    protected CssLayout formLlenar;
    protected HorizontalLayout lbNombreSolicitante;
    protected TextField txSolicitante;
    protected TextField txCarrera;
    protected RadioButtonGroup<String> rbTipoSolicitud;
    protected CheckBoxGroup<String> chTipoBca;
    protected Button btnContinuarS3;
    protected CssLayout formRegistro;
    protected Label lbRegistro;
    protected Link linkRegistro;
    protected Label lbRegistro2;
    protected CheckBox chAcepto;
    protected Button btnContinuarS4;
    protected Button btnRegresar;

    public FormSolicitudDesign() {
        Design.read(this);
    }
}
