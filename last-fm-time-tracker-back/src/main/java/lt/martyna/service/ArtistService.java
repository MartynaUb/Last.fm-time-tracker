package lt.martyna.service;

import lt.martyna.entity.Artist;
import lt.martyna.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getArtists(long userId, Pageable pageable) {
        return artistRepository.findByUserId(userId, pageable);
    }

    @Transactional
    public void refreshArtists(long userId, List<Artist> artists) {
        artistRepository.deleteAllByUserId(userId);
        artistRepository.saveAll(artists);
    }
}
