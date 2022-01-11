package lt.martyna.repository;

import lt.martyna.entity.Track;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, String> {
    List<Track> findByUserId(long userId, Pageable pageable);
    void deleteAllByUserId(long userId);

}
