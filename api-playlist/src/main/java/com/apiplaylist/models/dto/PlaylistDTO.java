package com.apiplaylist.models.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaylistDTO {

    private Long id;
    private String nome;

    private String descricao;
    private List<MusicDTO> musicas = new ArrayList<>();
}
