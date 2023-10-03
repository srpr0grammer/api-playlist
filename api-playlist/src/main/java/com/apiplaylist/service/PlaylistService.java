package com.apiplaylist.service;

import com.apiplaylist.models.entity.Playlist;
import com.apiplaylist.repository.MusicRepository;
import com.apiplaylist.repository.PlaylistRepository;
import com.apiplaylist.service.exception.BadRequestException;
import com.apiplaylist.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Playlist createPlaylist(Playlist playlist) {
        if(playlist.getNome() == null || playlist.getNome().isEmpty()) {
            throw new BadRequestException("Nome da lista não é válido");
        }

        return playlistRepository.save(playlist);
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist getPlaylistById(Long id) {
        return playlistRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Esta Playlist não se encontra em nosso banco de dados."));
    }

    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }

}
