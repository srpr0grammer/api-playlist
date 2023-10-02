package com.apiplaylist.repository;

import com.apiplaylist.models.Music;
import com.apiplaylist.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
}
