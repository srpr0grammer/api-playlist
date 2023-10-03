package com.apiplaylist.models.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {

    @NotBlank(message = "Campo nao pode ser nulo")
    private String userName;

    @NotBlank(message = "Campo nao pode ser nulo")
    private String password;
}
