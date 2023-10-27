package br.com.b3social.emailservice.producers;

import br.com.b3social.emailservice.api.dtos.EmailDto;
import br.com.b3social.emailservice.domain.models.Email;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceProducers {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void teste(Email email) {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmailDe("reynaldoscrip@gmail.com");
        emailDto.setEmailPara(email.getEmailPara());
        emailDto.setAssunto(email.getAssunto());
        emailDto.setTexto(email.getTexto());
        this.rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }

    public void publishMessageEmailServiceAcaoSocial(Email email) {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmailDe("reynaldoscrip@gmail.com");
        emailDto.setEmailPara(email.getEmailPara());
        emailDto.setAssunto(email.getAssunto());
        emailDto.setTexto(email.getTexto());

        this.rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
    
}