package br.com.b3social.emailservice.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDto {
    @NotBlank
    @Email
    private String emailPara;
    @NotBlank
    private String assunto;
    @NotBlank
    private String texto;
}
