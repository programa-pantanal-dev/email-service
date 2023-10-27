package br.com.b3social.emailservice.domain.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.b3social.emailservice.domain.models.enums.StatusEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long emailId;
    private String emailDe;
    private String emailPara;
    private String assunto;
    private String texto;
    private String microsservico;
    @DateTimeFormat()
    private LocalDateTime dataEnvioEmail;
    private StatusEmail statusEmail;
    
}

