package lt.martyna.response;

public class ArtistResponse {
    private final String artist;
    private final int playCount;
    private final String totalPlayTime;

    public ArtistResponse(String artist, int playCount, int totalPlayTime) {
        this.artist = artist;
        this.playCount = playCount;
        this.totalPlayTime = totalListenedTimeForArtist(totalPlayTime);
    }

    public String getArtist() {
        return artist;
    }

    public int getPlayCount() {
        return playCount;
    }

    public String getTotalPlayTime() {
        return totalPlayTime;
    }

    private String totalListenedTimeForArtist(int totalTimeInSeconds) {
        int numberOfDays = totalTimeInSeconds / 86400;
        int numberOfHours = (totalTimeInSeconds % 86400) / 3600;
        int numberOfMinutes = ((totalTimeInSeconds % 86400) % 3600) / 60;
        int numberOfSeconds = ((totalTimeInSeconds % 86400) % 3600) % 60;

        return numberOfDays + " days, " + numberOfHours + " hours, " + numberOfMinutes + " minutes, " + numberOfSeconds + " seconds";
    }
}
