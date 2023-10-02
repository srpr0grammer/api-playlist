package com.apiplaylist.controller;

import com.apiplaylist.models.Playlist;
import com.apiplaylist.models.dto.PlaylistDTO;
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
@RequestMapping("api/playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PlaylistDTO> createPlaylist(@Valid @RequestBody PlaylistDTO playlistDTO) {
        Playlist playlist = modelMapper.map(playlistDTO, Playlist.class);
        Playlist savedPlaylist = playlistService.createPlaylist(playlist);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPlaylist.getId())
                .toUri();

        return ResponseEntity.created(location).body(modelMapper.map(savedPlaylist, PlaylistDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylists() {
        List<Playlist> playlists = playlistService.getAllPlaylists();
        List<PlaylistDTO> playlistDTOs = playlists.stream()
                .map(playlist -> modelMapper.map(playlist, PlaylistDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(playlistDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDTO> getPlaylistById(@PathVariable Long id) {
        Playlist playlist = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(modelMapper.map(playlist, PlaylistDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }
}
