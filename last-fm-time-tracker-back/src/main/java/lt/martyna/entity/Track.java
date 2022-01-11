package lt.martyna.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Track {
    @Id
    @GeneratedValue
    private long id;
    private String artist;
    private String songTitle;
    private int duration;
    private int playCount;
    private int totalPlayTime;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Track() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public int getDuration() {
        return duration;
    }

    public int getPlayCount() {
        return playCount;
    }

    public int getTotalPlayTime() {
        return totalPlayTime;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public void setTotalPlayTime(int totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Track(String artist, String songTitle, int duration, int playCount, User user) {
        this.artist = artist;
        this.songTitle = songTitle;
        this.duration = duration;
        this.playCount = playCount;
        this.totalPlayTime = duration * playCount;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return id == track.id && duration == track.duration && playCount == track.playCount && totalPlayTime == track.totalPlayTime && Objects.equals(artist, track.artist) && Objects.equals(songTitle, track.songTitle) && Objects.equals(user, track.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, songTitle);
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", artist='" + artist + '\'' +
                ", songTitle='" + songTitle + '\'' +
                ", duration=" + duration +
                ", playCount=" + playCount +
                ", totalPlayTime=" + totalPlayTime +
                ", user=" + user +
                '}';
    }
}
