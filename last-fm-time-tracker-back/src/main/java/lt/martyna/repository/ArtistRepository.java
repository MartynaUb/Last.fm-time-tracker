package lt.martyna.repository;

import lt.martyna.entity.Artist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository  extends JpaRepository<Artist, String> {
    List<Artist> findByUserId(long userId, Pageable pageable);
    void deleteAllByUserId(long userId);
}
