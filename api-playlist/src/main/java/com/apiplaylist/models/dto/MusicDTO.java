package com.apiplaylist.models.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicDTO {

    private Long id;
    private String titulo;
    private String artista;
    private String album;
    private String ano;
    private String genero;
}
