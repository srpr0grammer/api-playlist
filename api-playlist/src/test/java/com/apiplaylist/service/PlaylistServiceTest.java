package com.apiplaylist.service;

import com.apiplaylist.models.entity.Playlist;
import com.apiplaylist.repository.PlaylistRepository;
import com.apiplaylist.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;
    @InjectMocks
    private PlaylistService playlistService;

    @Test
    public void testCreatePlaylist() {
        Playlist playlist = new Playlist();
        playlist.setNome("Test Playlist");

        when(playlistRepository.save(playlist)).thenReturn(playlist);

        Playlist createdPlaylist = playlistService.createPlaylist(playlist);

        assertEquals(playlist, createdPlaylist);
    }

    @Test
    public void testGetAllPlaylists() {
        Playlist playlist1 = new Playlist();
        playlist1.setNome("Playlist 1");

        Playlist playlist2 = new Playlist();
        playlist2.setNome("Playlist 2");

        when(playlistRepository.findAll()).thenReturn(Arrays.asList(playlist1, playlist2));

        List<Playlist> playlists = playlistService.getAllPlaylists();

        assertEquals(2, playlists.size());
        assertEquals(playlist1, playlists.get(0));
        assertEquals(playlist2, playlists.get(1));
    }

    @Test
    public void testGetPlaylistById() {
        Playlist playlist = new Playlist();
        playlist.setNome("Test Playlist");

        when(playlistRepository.findById(1L)).thenReturn(Optional.of(playlist));

        Playlist retrievedPlaylist = playlistService.getPlaylistById(1L);

        assertEquals(playlist, retrievedPlaylist);
    }

    @Test
    public void testGetPlaylistByIdNotFound() {
        when(playlistRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> {
            playlistService.getPlaylistById(1L);
        });
    }

    @Test
    public void testDeletePlaylist() {
        doNothing().when(playlistRepository).deleteById(1L);

        playlistService.deletePlaylist(1L);

        verify(playlistRepository, times(1)).deleteById(1L);
    }
}
