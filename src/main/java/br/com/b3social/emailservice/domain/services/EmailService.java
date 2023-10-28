package br.com.b3social.emailservice.domain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.b3social.emailservice.api.dtos.EmailDto;

@Service
public class EmailService {

    
    @Value(value = "${spring.mail.username}")
    private String email;

    @Autowired
    private JavaMailSender emailEnviar;

    public EmailDto enviandoEmail(EmailDto emailDto) {
        try{
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom(email);
            mensagem.setTo(emailDto.getEmailPara());
            mensagem.setSubject(emailDto.getAssunto());
            mensagem.setText(emailDto.getTexto());
            emailEnviar.send(mensagem);
        } catch (MailException u){
           System.err.println(u);
        } 
        return emailDto;
    }
}    

