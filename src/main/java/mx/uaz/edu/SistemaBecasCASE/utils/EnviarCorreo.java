package mx.uaz.edu.SistemaBecasCASE.utils;

import com.vaadin.ui.Notification;
import mx.uaz.edu.SistemaBecasCASE.modelos.Usuario;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EnviarCorreo {
	private Properties propiedades;
	private MimeMessage mensaje;
	
	public EnviarCorreo() {
		propiedades = System.getProperties();
		propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
		//    propiedades.setProperty("mail.smtp.host", "ingsoftware.reduaz.mx");
	    propiedades.setProperty("mail.smtp.starttls.enable", "true");
		propiedades.setProperty("mail.smtp.port", "587");
		//    propiedades.setProperty("mail.smtp.port", "25");
		propiedades.setProperty("mail.smtp.auth", "true");
		     
		Session sesion = Session.getDefaultInstance(propiedades, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("sistemabecascase@gmail.com", "ojosmoros123");
		//            return new PasswordAuthentication("alexadmon@ingsoftware.reduaz.mx", "xxxx");
		        }
		    });
	    mensaje = new MimeMessage(sesion);
		try {
			mensaje.setFrom(new Address() {
				@Override
				public String getType() {
					return "sistemabecascase@gmail.com";
				}

				@Override
				public String toString() {
					return "sistemabecascase@gmail.com";
				}

				@Override
				public boolean equals(Object o) {
					return false;
				}
			});
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	public boolean sendMail(Usuario user){
		boolean ok = false;
		 try{
	       mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
	       mensaje.setSubject("Confirmación para el Sistema de Becas del CASE");
	       String html="<!DOCTYPE html>"
	       		+ "<html>"
	       		+ "<head>"
	       		+ "<title>Confirmación</title>"
	       		+ "<meta charset=\"UTF-8\">"
	       		+ "<style type=\"text/css\">"
	       		+ "h3{text-align: center;}"
	       		+ ".texto{text-align: justify;width:60%;float: left;padding: 10px 15px 10px 0px;}"
	       		+ ".container{width: 80%;margin: 10px auto;font-family: arial;font-size:14px;padding: 20px;background-color: rgb(255, 253, 253);box-shadow: 0px 0px 10px 5px silver;border-radius: 6px;}"
	       		+ "img {width:35%;}"
	       		+ "</style>"
	       		+ "</head>"
	       		+ "<body>"
	       		+ "<div class=\"container\">"
	       		+ "<h3>Confirmación de la cuenta para la Plataforma de Becas del CASE</h3>"
	       		+ "<div class='texto'>Estimad@ Usuari@ <br> Gracias por registrarte, para completar el proceso es necesario activar tu cuenta haciendo click en el siguiente enlace: <a href=\"http://localhost:8080/?user="+user.getCadena()+"&logs="+user.getNombre_usuario()+"\">Confirmación de la cuenta</a></div><div><img src=\"http://ingsoftware.reduaz.mx/~alexmauricio/pagina/templates/ingenieradesoftware/images/is.png\" ></div><br><br><br><h4>Dudas o comentarios: <a href=\"mailto:\"> sistemabecascase@gmail.com</a></h4></div></body></html>";
	       mensaje.setText(html, "ISO-8859-1", "html");
	    
	       Transport.send(mensaje);
	       ok = true;
		 }
		 catch (MessagingException e) {
			 Notification.show("No se puede enviar el correo de confirmación, error: "+e.getStackTrace().toString(),Notification.Type.ERROR_MESSAGE);
		 }
		 return ok;
	}
	
	public boolean sendMailRecuperaContra(String usuario, String mail, String nombre, String cad){
		boolean ok = false;
		 try{
	
	       mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
	       mensaje.setSubject("Cuenta Egresados -Recuperación de Contraseña-");
	       String html="<!DOCTYPE html><html><head><title>Recuperación de Contraseña</title><meta charset=\"UTF-8\"><style type=\"text/css\">h3{text-align: center;}.texto{text-align: justify;width:60%;float: left;padding: 10px 15px 10px 0px;}"+
	       ".container{width: 80%;margin: 10px auto;font-family: arial;font-size:14px;padding: 20px;background-color: rgb(255, 253, 253);box-shadow: 0px 0px 10px 5px silver;border-radius: 6px;}img {width:35%;}</style></head><body>"+
	       "<div class=\"container\"><h3>Recuperación de Contraseña de la cuenta "+usuario+" en el Sistema de Seguimiento de Egresados IS</h3>"+
	       "<div class='texto'>Estimad@ <b>"+nombre+":</b> <br> Para completar el proceso es necesario hacer click en el siguiente enlace: <a href=\"http://ingsoftware.reduaz.mx/Egresados?user="+cad+"&logs="+usuario+"&rec=ok\">Recuperar contraseña</a></div><div><img src=\"http://ingsoftware.reduaz.mx/~alexmauricio/pagina/templates/ingenieradesoftware/images/is.png\" ></div><br><br><br><h4>Dudas o comentarios: <a href=\"mailto:\"> amgdark@uaz.edu.mx</a></h4></div></body></html>";
	       mensaje.setText(html, "ISO-8859-1", "html");
	    
	       Transport.send(mensaje);
	       ok = true;
		 }
		 catch (MessagingException e) {
			 Notification.show("No se puede enviar el correo de confirmación, error: "+e.getMessage().toString(),Notification.Type.ERROR_MESSAGE);
		 }
		 return ok;
	}
	
}
