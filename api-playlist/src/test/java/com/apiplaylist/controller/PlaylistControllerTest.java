package com.apiplaylist.controller;


import com.apiplaylist.models.dto.MusicDTO;
import com.apiplaylist.models.dto.PlaylistDTO;
import com.apiplaylist.models.entity.Playlist;
import com.apiplaylist.models.entity.User;
import com.apiplaylist.models.enums.UserRole;
import com.apiplaylist.repository.UserRepository;
import com.apiplaylist.security.SecurityFilter;
import com.apiplaylist.security.TokenService;
import com.apiplaylist.service.AuthorizationService;
import com.apiplaylist.service.PlaylistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PlaylistController.class)
@ActiveProfiles("test")
public class PlaylistControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private PlaylistService playlistService;
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
    public void testCreatePlaylist() throws Exception {
        MusicDTO sampleMusic = new MusicDTO(null, "Imagine", "John Lennon", "Imagine", "1971", "Rock");
        List<MusicDTO> musicList = Collections.singletonList(sampleMusic);
        PlaylistDTO playlistDTO = new PlaylistDTO(null, "My Playlist", "Description", musicList);

        ObjectMapper objectMapper = new ObjectMapper();
        String playlistJson = objectMapper.writeValueAsString(playlistDTO);

        Playlist mockPlaylist = new Playlist();
        mockPlaylist.setId(1L);
        mockPlaylist.setNome("My Playlist");

        when(modelMapper.map(any(PlaylistDTO.class), eq(Playlist.class))).thenReturn(mockPlaylist);
        when(playlistService.createPlaylist(mockPlaylist)).thenReturn(mockPlaylist);
        when(modelMapper.map(mockPlaylist, PlaylistDTO.class)).thenReturn(playlistDTO);

        mockMvc.perform(post("/api/playlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playlistJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    public void testGetAllPlaylists() throws Exception {
        MusicDTO sampleMusic = new MusicDTO(null, "Imagine", "John Lennon", "Imagine", "1971", "Rock");
        List<MusicDTO> musicList = Collections.singletonList(sampleMusic);
        PlaylistDTO playlistDTO = new PlaylistDTO(1L, "My Playlist", "Description", musicList);
        List<PlaylistDTO> dtoList = Collections.singletonList(playlistDTO);

        Playlist mockPlaylist = new Playlist();
        mockPlaylist.setId(1L);
        mockPlaylist.setNome("My Playlist");
        List<Playlist> playlistList = Collections.singletonList(mockPlaylist);

        when(playlistService.getAllPlaylists()).thenReturn(playlistList);
        when(modelMapper.map(mockPlaylist, PlaylistDTO.class)).thenReturn(playlistDTO);

        mockMvc.perform(get("/api/playlist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtoList)));
    }

    @Test
    public void testGetPlaylistById() throws Exception {
        Long playlistId = 1L;

        MusicDTO sampleMusic = new MusicDTO(null, "Imagine", "John Lennon", "Imagine", "1971", "Rock");
        List<MusicDTO> musicList = Collections.singletonList(sampleMusic);
        PlaylistDTO playlistDTO = new PlaylistDTO(1L, "My Playlist", "Description", musicList);

        Playlist mockPlaylist = new Playlist();
        mockPlaylist.setId(1L);
        mockPlaylist.setNome("My Playlist");

        when(playlistService.getPlaylistById(playlistId)).thenReturn(mockPlaylist);
        when(modelMapper.map(mockPlaylist, PlaylistDTO.class)).thenReturn(playlistDTO);

        mockMvc.perform(get("/api/playlist/" + playlistId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(playlistDTO)));
    }

    @Test
    public void testDeletePlaylist() throws Exception {
        Long playlistId = 1L;

        doNothing().when(playlistService).deletePlaylist(playlistId);

        mockMvc.perform(delete("/api/playlist/" + playlistId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
