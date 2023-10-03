package com.apiplaylist.controller;

import com.apiplaylist.models.dto.MusicDTO;
import com.apiplaylist.models.dto.PlaylistDTO;
import com.apiplaylist.models.entity.Music;
import com.apiplaylist.models.entity.Playlist;
import com.apiplaylist.service.MusicService;
import com.apiplaylist.service.PlaylistService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/music")
public class MusicController {
    @Autowired
    private MusicService musicService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<MusicDTO> createMusic(@Valid @RequestBody MusicDTO musicDTO) {
        Music music = modelMapper.map(musicDTO, Music.class);
        Music savedMusic = musicService.createMusic(music);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMusic.getId())
                .toUri();

        return ResponseEntity.created(location).body(modelMapper.map(savedMusic, MusicDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<MusicDTO>> getAllMusic() {
        List<Music> musicList = musicService.getAllMusic();
        List<MusicDTO> musicDTOs = musicList.stream()
                .map(music -> modelMapper.map(music, MusicDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(musicDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicDTO> getMusicById(@PathVariable Long id) {
        Music music = musicService.getMusicById(id);
        return ResponseEntity.ok(modelMapper.map(music, MusicDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusic(@PathVariable Long id) {
        musicService.deleteMusic(id);
        return ResponseEntity.noContent().build();
    }
}
