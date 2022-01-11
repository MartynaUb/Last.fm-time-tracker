package lt.martyna.client;

import lt.martyna.entity.Track;
import lt.martyna.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LastFmClient {
    private static final String BASE_URL = "https://ws.audioscrobbler.com/2.0/";
    private static final String METHOD_FOR_TRACKS = "user.gettoptracks";
    private static final String METHOD_FOR_USER = "user.getinfo";

    private final RestTemplate restTemplate;
    private final String apiKey;


    public LastFmClient(@Autowired RestTemplate restTemplate, @Value("${last-fm.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public List<Track> fetchUserTracks(User user) {
        try {
            SinglePageData pageData = getSinglePage(user, 1);
            int totalPages = pageData.getTotalPages();
            List<Track> tracks = pageData.getTracks();

            for (int currentPageNumber = 2; currentPageNumber <= totalPages; currentPageNumber++) {
                SinglePageData currentPageData = getSinglePage(user, currentPageNumber);
                tracks.addAll(currentPageData.getTracks());
            }

            return tracks;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to fetch data from last.fm", e);
        }
    }

    private SinglePageData getSinglePage(User user, int pageNumber) {
        String url = BASE_URL + "?method=" + METHOD_FOR_TRACKS + "&user=" + user.getUsername() + "&api_key=" + apiKey + "&format=json&limit=1000&page=" + pageNumber;

        ResponseRoot responseRoot = restTemplate.getForObject(url, ResponseRoot.class);
        int totalPages = Integer.parseInt(responseRoot.toptracks.getResponseInfo().getTotalPages());
        List<Track> tracks = getTracks(responseRoot, user);

        return new SinglePageData(tracks, totalPages);
    }

    private List<Track> getTracks(ResponseRoot responseRoot, User user) {
        return responseRoot.toptracks.getTrack().stream()
                .map(track -> new Track(track.getArtist().getName(),
                        track.getName(),
                        Integer.parseInt(track.getDuration()),
                        Integer.parseInt(track.getPlaycount()),
                        user))
                .collect(Collectors.toList());
    }

    public boolean checkIfUserExists(String username) {
        try {
            String url = BASE_URL + "?method=" + METHOD_FOR_USER + "&user=" + username + "&api_key=" + apiKey + "&format=json";

            restTemplate.getForObject(url, String.class);
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    public static class SinglePageData {
        private final List<Track> tracks;
        private final int totalPages;

        public SinglePageData(List<Track> tracks, int totalPages) {
            this.tracks = tracks;
            this.totalPages = totalPages;
        }

        public List<Track> getTracks() {
            return tracks;
        }

        public int getTotalPages() {
            return totalPages;
        }
    }
}
