package lt.martyna.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Artist {
    @Id
    @GeneratedValue
    private long id;
    private String artist;
    private  int playCount;
    private int totalPlayTime;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Artist(String artist, User user, List<Track> tracks) {
        this.user = user;
        this.artist = artist;
        this.playCount = tracks.stream().mapToInt(Track::getPlayCount).sum();
        this.totalPlayTime = tracks.stream().mapToInt(Track::getTotalPlayTime).sum();
    }

    public Artist() {

    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getArtist() {
        return artist;
    }

    public int getPlayCount() {
        return playCount;
    }

    public int getTotalPlayTime() {
        return totalPlayTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist1 = (Artist) o;
        return playCount == artist1.playCount && totalPlayTime == artist1.totalPlayTime && Objects.equals(id, artist1.id) && Objects.equals(artist, artist1.artist) && Objects.equals(user, artist1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, artist, playCount, totalPlayTime, user);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", artist='" + artist + '\'' +
                ", playCount=" + playCount +
                ", totalPlayTime=" + totalPlayTime +
                ", user=" + user +
                '}';
    }
}

