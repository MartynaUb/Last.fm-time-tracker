package lt.martyna.service;

import lt.martyna.client.LastFmClient;
import lt.martyna.repository.ArtistRepository;
import lt.martyna.repository.TrackRepository;
import lt.martyna.repository.UserRepository;
import lt.martyna.entity.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrackService {
    private final TrackRepository trackRepository;

    @Autowired
    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<Track> getTracks(long userId, Pageable pageable) {
        return trackRepository.findByUserId(userId, pageable);
    }

    @Transactional
    public void refreshTracks(long userId, List<Track> tracks) {
        trackRepository.deleteAllByUserId(userId);
        trackRepository.saveAll(tracks);
    }


}
