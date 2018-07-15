package com.curso.jms;

import java.io.IOException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.curso.entidades.Correo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;

@Component
public class JmsOyente {
	//@Autowired

	@Autowired
	public EmailService emailService;
	
	public void SendEmail(String Asunto, String Cuerpo, String Destinatario){
		   Email email;
		try {
			email = DefaultEmail.builder()
			        .from(new InternetAddress("alertas@fridaysperu.com"))
			        .replyTo(new InternetAddress("alertas@fridaysperu.com"))
			        .to(Lists.newArrayList(
			        		new InternetAddress(Destinatario)))
			        .subject(Asunto)
			        .body(Cuerpo)
			        .build();
			   emailService.send(email);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	@JmsListener(destination="${jms.queue.destination}")
	public void miMensaje(String mensajeJson) {
		
		
			 
			ObjectMapper mapper = new ObjectMapper();
			System.out.println("Estructura Leida:" + mensajeJson);
			try {
				Correo correo =  mapper.readValue(mensajeJson, Correo.class);
				System.out.println("Se enviara correo a : " + correo.getDestinatario());
				SendEmail(correo.getAsunto(),correo.getCuerpo(),correo.getDestinatario());
				
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
}