package br.com.b3social.emailservice.api.controllers;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.b3social.emailservice.api.dtos.EmailDto;
import br.com.b3social.emailservice.domain.models.Email;
import br.com.b3social.emailservice.domain.services.EmailService;
import br.com.b3social.emailservice.producers.ServiceProducers;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;

@RestController
public class teste {


    @Autowired
    private JavaMailSender javaMailSender;

    final RabbitTemplate rabbitTemplate;


    public teste(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    @Autowired
    EmailService emailService;

    @Autowired
    ServiceProducers serviceProducers;

    Email email = new Email();


    
    @PostMapping("/email")
    public ResponseEntity<Void> receberDados(@RequestBody @Valid EmailDto emailDto) {
        String servicoOrigem = emailDto.getMicrosservico();
        if ("Acao-social".equals(servicoOrigem)) {
            BeanUtils.copyProperties(emailDto, email);
            serviceProducers.publishMessageEmailServiceAcaoSocial(email);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            BeanUtils.copyProperties(emailDto, email);
            // emailService.enviandoEmail(email);
            serviceProducers.teste(email);
            enviarEmailFormatado(email);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }


    private void enviarEmailFormatado(Email email) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("reynaldoscrip@gmail.com");
            helper.setTo(email.getEmailPara());
            helper.setSubject(email.getAssunto());

            // Criar um conteúdo HTML formatado
            String htmlContent = "<html><body>";
            htmlContent += "<h1 style=\"color: #333;\"> " + email.getAssunto() + "</h1>";
            htmlContent += "<img src=\"data:image/png;base64,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\" alt=\"Imagem\" style=\"width: 200px; height: auto;\">";
            htmlContent += "<p style=\"font-size: 16px;\"> " + email.getTexto() + "</p>";
            htmlContent += "</body></html>";

            helper.setText(htmlContent, true);

            javaMailSender.send(message);
            System.out.println("E-mail enviado com sucesso!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
}
