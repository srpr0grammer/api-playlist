package com.apiplaylist.service;

import com.apiplaylist.models.entity.Playlist;
import com.apiplaylist.repository.PlaylistRepository;
import com.apiplaylist.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository repository;
    public Playlist createPlaylist (Playlist playlist) {
        return  repository.save(playlist);
    }

    public List<Playlist> getAllPlaylists() {
        return repository.findAll();
    }

    public Playlist getPlaylistById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Esta Playlist não se encontra em nosso banco de dados."));
    }

    public void deletePlaylist(Long id) {
        repository.deleteById(id);
    }
}
