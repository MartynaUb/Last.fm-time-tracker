package lt.martyna.response;

import lt.martyna.entity.Track;

public class TrackResponse {
    private final long id;
    private final String artist;
    private final String songTitle;
    private final String duration;
    private final int playCount;
    private final String totalPlayTime;

    public TrackResponse(Track track) {
        this.id = track.getId();
        this.artist = track.getArtist();
        this.songTitle = track.getSongTitle();
        this.playCount = track.getPlayCount();
        this.duration = formatDuration(track.getDuration());
        this.totalPlayTime = totalListenedTimeForTrack(track.getTotalPlayTime());
    }


    public long getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getDuration() {
        return duration;
    }

    public int getPlayCount() {
        return playCount;
    }

    public String getTotalPlayTime() {
        return totalPlayTime;
    }

    private String formatDuration(int duration) {
        int minutesPart = duration / 60;
        int secondsPart = duration % 60;

        return minutesPart + ":" + (secondsPart < 10 ? "0" : "") + secondsPart;
    }

    private String totalListenedTimeForTrack(int totalTimeInSeconds) {
        int numberOfDays = totalTimeInSeconds / 86400;
        int numberOfHours = (totalTimeInSeconds % 86400) / 3600;
        int numberOfMinutes = ((totalTimeInSeconds % 86400) % 3600) / 60;
        int numberOfSeconds = ((totalTimeInSeconds % 86400) % 3600) % 60;

        return numberOfDays + " days, " + numberOfHours + " hours, " + numberOfMinutes + " minutes, " + numberOfSeconds + " seconds";
    }
}
