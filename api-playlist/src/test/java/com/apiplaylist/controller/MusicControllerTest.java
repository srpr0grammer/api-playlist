package com.apiplaylist.controller;


import com.apiplaylist.models.dto.MusicDTO;
import com.apiplaylist.models.entity.Music;
import com.apiplaylist.repository.UserRepository;
import com.apiplaylist.security.SecurityFilter;
import com.apiplaylist.security.TokenService;
import com.apiplaylist.service.AuthorizationService;
import com.apiplaylist.service.MusicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MusicController.class)
@ActiveProfiles("test")
public class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private MusicService musicService;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private ApplicationContext context;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private SecurityFilter securityFilter;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setupSecurityContext() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void testCreateMusic() throws Exception {
        MusicDTO musicDTO = new MusicDTO(/* seus atributos aqui */);
        Music mockMusic = new Music(/* seus atributos aqui */);
        mockMusic.setId(1L);

        String musicJson = new ObjectMapper().writeValueAsString(musicDTO);

        when(musicService.createMusic(any(Music.class))).thenReturn(mockMusic);
        when(modelMapper.map(any(MusicDTO.class), eq(Music.class))).thenReturn(mockMusic);
        when(modelMapper.map(mockMusic, MusicDTO.class)).thenReturn(musicDTO);

        mockMvc.perform(post("/api/music")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(musicJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void testGetAllMusic() throws Exception {
        Music mockMusic = new Music(/* seus atributos aqui */);
        MusicDTO musicDTO = new MusicDTO(/* seus atributos aqui */);
        List<Music> musicList = Collections.singletonList(mockMusic);
        List<MusicDTO> dtoList = Collections.singletonList(musicDTO);

        when(musicService.getAllMusic()).thenReturn(musicList);
        when(modelMapper.map(mockMusic, MusicDTO.class)).thenReturn(musicDTO);

        mockMvc.perform(get("/api/music")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(dtoList)));
    }

    @Test
    public void testGetMusicById() throws Exception {
        Long musicId = 1L;
        Music mockMusic = new Music(/* seus atributos aqui */);
        mockMusic.setId(musicId);
        MusicDTO musicDTO = new MusicDTO(/* seus atributos aqui */);

        when(musicService.getMusicById(musicId)).thenReturn(mockMusic);
        when(modelMapper.map(mockMusic, MusicDTO.class)).thenReturn(musicDTO);

        mockMvc.perform(get("/api/music/" + musicId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(musicDTO)));
    }
    @Test
    public void testDeleteMusic() throws Exception {
        Long musicId = 1L;

        doNothing().when(musicService).deleteMusic(musicId);

        mockMvc.perform(delete("/api/music/" + musicId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    
}
