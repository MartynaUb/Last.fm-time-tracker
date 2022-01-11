package lt.martyna.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseRoot {
    public Toptracks toptracks;

    public ResponseRoot() {
    }

    public ResponseRoot(Toptracks toptracks) {
        this.toptracks = toptracks;
    }

    public Toptracks getToptracks() {
        return toptracks;
    }


    public void setToptracks(Toptracks toptracks) {
        this.toptracks = toptracks;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseInfo {
        private String user;
        private String totalPages;
        private String page;

        public ResponseInfo() {
        }

        public ResponseInfo(String totalPages) {
            this.totalPages = totalPages;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(String totalPages) {
            this.totalPages = totalPages;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }


    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Toptracks {

        private List<Track> track;
        @JsonProperty("@attr")
        private ResponseInfo responseInfo;

        public Toptracks() {
        }

        public Toptracks(List<Track> track, ResponseInfo responseInfo) {
            this.track = track;
            this.responseInfo = responseInfo;
        }

        public List<Track> getTrack() {
            return track;
        }

        public void setTrack(List<Track> track) {
            this.track = track;
        }

        public ResponseInfo getResponseInfo() {
            return responseInfo;
        }

        public void setResponseInfo(ResponseInfo responseInfo) {
            this.responseInfo = responseInfo;
        }


    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Artist {
        private String name;

        public Artist() {
        }

        public Artist(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Track {
        private String mbid;
        private String name;
        private Artist artist;
        private String duration;
        private String playcount;

        public Track() {
        }

        public Track(String mbid, String name, Artist artist, String duration, String playcount) {
            this.mbid = mbid;
            this.name = name;
            this.artist = artist;
            this.duration = duration;
            this.playcount = playcount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Artist getArtist() {
            return artist;
        }

        public void setArtist(Artist artist) {
            this.artist = artist;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getPlaycount() {
            return playcount;
        }

        public void setPlaycount(String playcount) {
            this.playcount = playcount;
        }


        public String getMbid() {
            return mbid;
        }

        public void setMbid(String mbid) {
            this.mbid = mbid;
        }
    }
}