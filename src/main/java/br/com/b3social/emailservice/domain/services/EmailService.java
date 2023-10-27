package br.com.b3social.emailservice.domain.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.b3social.emailservice.domain.models.Email;
import br.com.b3social.emailservice.domain.models.enums.StatusEmail;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailEnviar;

    public Email enviandoEmail(Email email) {
        email.setDataEnvioEmail(LocalDateTime.now());
        try{
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom(email.getEmailDe());
            mensagem.setTo(email.getEmailPara());
            mensagem.setSubject(email.getAssunto());
            mensagem.setText(email.getTexto());
            emailEnviar.send(mensagem);
            email.setStatusEmail(StatusEmail.SENT);
        } catch (MailException u){
            email.setStatusEmail(StatusEmail.ERROR);
        } 
        return email;
    }
}            


