package mx.uaz.edu.SistemaBecasCASE.vistas;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToFloatConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import mx.uaz.edu.SistemaBecasCASE.accesodatos.*;
import mx.uaz.edu.SistemaBecasCASE.modelos.*;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class FormSolicitudDesignLogica extends FormSolicitudDesign{

    private boolean esAlumno;
    private Alumno alumno;
    private Binder<Alumno> binder;
    private ADAlumno adAlumno;
    private final String regexCURP = "^[A-Z]{1}[AEIOU]{1}[A-Z]{2}" +
            "[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" +
            "[HM]{1}" +
            "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
            "[B-DF-HJ-NP-TV-Z]{3}" +
            "[0-9A-Z]{1}" +
            "[0-9]{1}$";
    private String fechaNac;
    private Botones botonesListener;
    private ADProgramaAcademico adProgramaAcademico;
    private ADBeca adBeca;
    private Beca beca;

    public FormSolicitudDesignLogica(){
        generarVista();
    }

    public void generarVista(){
        botonesListener = new Botones();
        binder = new Binder<Alumno>();
        formRegistro.setVisible(false);
        formLlenar.setVisible(false);
        formTramitar.setVisible(false);
        alumno = new Alumno();
        esAlumno = true;
        fechaNac = "";
        adAlumno = new ADAlumno();
        adProgramaAcademico = new ADProgramaAcademico();
        adBeca = new ADBeca();
        beca = new Beca();
        binder.forField(txEmail)
                .withValidator(new EmailValidator("El formato del correo ingresado es incorrecto."))
                .asRequired("El email es requerido")
                .bind(Alumno::getEmail, Alumno::setEmail);

        String regexFecha = "^(\\d{8})$";
        binder.forField(pfContrasena)
                .withValidator(new RegexpValidator("El formato de la fecha es incorrecto", regexFecha))
                .asRequired("La Fecha de Nacimiento es requerida");

        btnContinuarS1.addClickListener(botonesListener);
        btnContinuarS2.addClickListener(botonesListener);
        btnContinuarS3.addClickListener(botonesListener);
        btnContinuarS4.addClickListener(botonesListener);
        btnRegresar.addClickListener(botonesListener);

    }

    class Botones implements Button.ClickListener{

        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            if(clickEvent.getButton() == btnContinuarS1){
                if(rbTipoAlumno.isSelected("aspirante") || rbTipoAlumno.isSelected("alumno")) {
                    if (rbTipoAlumno.getSelectedItem().get().equals("aspirante")) {
                        formTramitar.setCaption("Tramitar Beca como Aspirante");
                        txMatricula.setCaption("CURP");
                        esAlumno = false;
                        binder.forField(txMatricula)
                                .withValidator(matricula -> matricula.length() == 18,
                                        "La CURP debe contener 18 caracteres.")
                                .withValidator(new RegexpValidator("El formato de la CURP ingresada es incorrecto", regexCURP))
                                .asRequired("La CURP es requerida")
                                .bind(Alumno::getCurp, Alumno::setCurp);
                    } else if (rbTipoAlumno.getSelectedItem().get().equals("alumno")) {
                        formTramitar.setCaption("Tramitar Beca como Alumno");
                        txMatricula.setCaption("Matrícula");
                        binder.forField(txMatricula)
                                .withValidator(matricula -> matricula.length() == 8,
                                        "La Matrícula debe contener 8 caracteres.")
                                .asRequired("La Matrícula es Requerida")
                                .bind(Alumno::getMatricula, Alumno::setMatricula);
                    }
                    formSolicita.setEnabled(false);
                    formTramitar.setVisible(true);
                }else{
                    Notification.show("Debe seleccionar algún tipo de solicitante.");
                }
            }else if(clickEvent.getButton() == btnContinuarS2){
                String fecha_nac = formatearFecha(pfContrasena.getValue());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = null;
                if(binder.validate().isOk()) {
                    try {
                        fecha = sdf.parse(fecha_nac);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    if (esAlumno) {
                        alumno.setMatricula(txMatricula.getValue());
                    } else {
                        alumno.setCurp(txMatricula.getValue());
                    }

                    alumno = adAlumno.buscaAlumnoSolicitud(alumno);


                    if(alumno != null){
                        //System.out.println("al"+alumno.getFecha_nac().getTime());
                        boolean agregado = false;
                        Long dif = 2160000000L;
                        List<Beca> becas = adBeca.obtenerTodasBecas();

                        for(Beca b : becas){
                            if(b.getAlumno().getId_alumno() == alumno.getId_alumno()){
                                if( new Date().getTime() - b.getFecha_solicitud().getTime() < dif){
                                    agregado = true;
                                }
                            }
                        }

                        if(!agregado){
                            if(alumno.getFecha_nac().getTime()+ 18000000 ==  fecha.getTime()) {
                                //if(alumno.getFecha_nac().toString().equals(fecha_nac)) {
                                formTramitar.setEnabled(false);
                                formLlenar.setVisible(true);
                                txSolicitante.setValue(alumno.toString());

                                ProgramaAcademico pa = adProgramaAcademico.obtenerProgramaAlumno(alumno);
                                if(pa != null){
                                    txCarrera.setValue(pa.toString());
                                }else{
                                    txCarrera.setValue("");
                                }
                            }else{
                                if(esAlumno){
                                    Notification.show("Alumno no encontrado. Verifique los datos.");
                                }else{
                                    Notification.show("Aspirante no encontrado. Verifique los datos.");
                                }
                            }
                        }else{
                            if(esAlumno){
                                Notification.show("El alumno ingresado ya cuenta con una solicitud de beca registrada.");
                            }else{
                                Notification.show("El aspirante ingresado ya cuenta con una solicitud de beca registrada.");
                            }
                        }
                    }else{
                        if(esAlumno){
                            Notification.show("Alumno no encontrado. Verifique los datos.");
                        }else{
                            Notification.show("Aspirante no encontrado. Verifique los datos.");
                        }
                    }
                }else{
                    Notification.show("Algunos datos ingresados son incorrectos");
                }
            }else if(clickEvent.getButton() == btnContinuarS3){
                boolean agregadas = false;
                adAlumno.actualizaCorreoAlumno(alumno);
                beca.setAlumno(alumno);
                beca.setFecha_solicitud(new Date());

                    if (rbTipoSolicitud.getSelectedItem().get().equals("Nueva")) {
                        beca.setTipoSolicitud(new TipoSolicitud(1));
                    } else {
                        beca.setTipoSolicitud(new TipoSolicitud(2));
                    }

                    if (chTipoBca.getSelectedItems().contains("Alimentación")) {
                        beca.setTipoBeca(new TipoBeca(1));
                        if (adBeca.agregaBeca(beca))
                            agregadas = true;
                    }
                    if (chTipoBca.getSelectedItems().contains("Hospedaje")) {
                        beca.setTipoBeca(new TipoBeca(2));
                        if (adBeca.agregaBeca(beca))
                            agregadas = true;
                    }
                    if (agregadas) {
                        formLlenar.setEnabled(false);
                        formRegistro.setVisible(true);
                    } else {
                        System.out.println("Error");
                    }
            }else if(clickEvent.getButton() == btnContinuarS4){
                if(!chAcepto.getValue()){
                    Notification.show("Debes aceptar los Términos y Condiciones para continuar");
                }else{
                    UI.getCurrent().addWindow(new VentanaReporte(alumno.getId_alumno()));
                }
            } else if(clickEvent.getButton() == btnRegresar){
                reiniciarCampos();
            }
        }
    }

    public String formatearFecha(String fecha){

        String nueva = "";
        nueva = nueva + fecha.charAt(4);
        nueva = nueva +fecha.charAt(5);
        nueva = nueva + fecha.charAt(6);
        nueva = nueva + fecha.charAt(7);
        nueva = nueva + "-" + fecha.charAt(2) + fecha.charAt(3) + "-"
                + fecha.charAt(0) +fecha.charAt(1);
        return nueva;
    }

    public void reiniciarCampos(){
        //generarVista();
        formSolicita.setEnabled(true);
        formSolicita.setEnabled(true);
        formRegistro.setEnabled(true);
        formLlenar.setEnabled(true);
        formTramitar.setEnabled(true);
        formRegistro.setVisible(false);
        formLlenar.setVisible(false);
        formTramitar.setVisible(false);
        rbTipoSolicitud.clear();
        rbTipoAlumno.clear();
        chTipoBca.clear();

        txCarrera.setValue("");
        txEmail.setValue("");
        txMatricula.setValue("");
        txSolicitante.setValue("");
        pfContrasena.setValue("");
    }

}