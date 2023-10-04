package com.apiplaylist.service;

import com.apiplaylist.models.entity.Music;
import com.apiplaylist.models.entity.Playlist;
import com.apiplaylist.repository.MusicRepository;
import com.apiplaylist.repository.PlaylistRepository;
import com.apiplaylist.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {
    @Autowired
    private MusicRepository repository;
    public Music createMusic(Music music) {
        return repository.save(music);
    }

    public List<Music> getAllMusic() {
        return repository.findAll();
    }

    public Music getMusicById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Esta Música não se encontra em nosso banco de dados."));
    }

    public void deleteMusic(Long id) {
        getMusicById(id);
        repository.deleteById(id);
    }
}
