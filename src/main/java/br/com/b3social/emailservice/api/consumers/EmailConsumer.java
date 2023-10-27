package br.com.b3social.emailservice.api.consumers;

import br.com.b3social.emailservice.api.dtos.EmailDto;
import br.com.b3social.emailservice.domain.models.Email;
import br.com.b3social.emailservice.domain.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.email.name}" )
    public void listenEmailQueue(@Payload EmailDto emailDto) {
        var email = new Email();
        BeanUtils.copyProperties(emailDto, email); 
        emailService.enviandoEmail(email);   
    }
                    
}
