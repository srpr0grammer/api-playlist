package com.apiplaylist.service;


import com.apiplaylist.models.entity.Music;
import com.apiplaylist.repository.MusicRepository;
import com.apiplaylist.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class MusicServiceTest {

    @Mock
    private MusicRepository musicRepository;

    @InjectMocks
    private MusicService musicService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateMusic() {
        Music music = new Music();
        music.setTitulo("Test Music");

        when(musicRepository.save(music)).thenReturn(music);

        Music createdMusic = musicService.createMusic(music);

        assertEquals(music, createdMusic);
    }
    @Test
    void testGetAllMusic() {
        Music music1 = new Music();
        music1.setTitulo("Music 1");

        Music music2 = new Music();
        music2.setTitulo("Music 2");

        when(musicRepository.findAll()).thenReturn(Arrays.asList(music1, music2));

        List<Music> musicList = musicService.getAllMusic();

        assertEquals(2, musicList.size());
        assertEquals(music1, musicList.get(0));
        assertEquals(music2, musicList.get(1));
    }
    @Test
    void testGetMusicById() {
        Music music = new Music();
        music.setTitulo("Test Music");

        when(musicRepository.findById(1L)).thenReturn(Optional.of(music));

        Music retrievedMusic = musicService.getMusicById(1L);

        assertEquals(music, retrievedMusic);
    }
    @Test
    void testGetMusicByIdNotFound() {
        when(musicRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> {
            musicService.getMusicById(1L);
        });
    }
    @Test
    void testDeleteMusic() {
        doNothing().when(musicRepository).deleteById(1L);

        musicService.deleteMusic(1L);

        verify(musicRepository, times(1)).deleteById(1L);
    }
}
