package lt.martyna.client;

import lt.martyna.entity.Track;
import lt.martyna.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LastFmClientTest {
    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final LastFmClient lastFmClient = new LastFmClient(restTemplate, "api-key");

    private final User user = new User("username");
    private final ResponseRoot.ResponseInfo responseInfo = new ResponseRoot.ResponseInfo("2");
    private final ResponseRoot.Artist artist = new ResponseRoot.Artist("artist");
    private final ResponseRoot.Track track = new ResponseRoot.Track("1", "title", artist, "12", "5");
    private final ResponseRoot.Toptracks topTracks = new ResponseRoot.Toptracks(List.of(track), responseInfo);
    private final ResponseRoot responseRoot = new ResponseRoot(topTracks);

    @Test
    public void fetchUserTracks_shouldThrowException_whenLastFmConnectionFails() {
        when(restTemplate.getForObject(anyString(), any())).thenThrow(new RestClientException("Last.fm is not available"));

        assertThrows(IllegalArgumentException.class, () -> lastFmClient.fetchUserTracks(user));
    }

    @Test
    public void fetchUserTracks_shouldReturnTracks() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(responseRoot);

        List<Track> actualTracks = lastFmClient.fetchUserTracks(user);

        assertEquals(List.of(new Track("artist", "title", 12, 5, user),
                new Track("artist", "title", 12, 5, user)), actualTracks);

        verify(restTemplate).getForObject("https://ws.audioscrobbler.com/2.0/?method=user.gettoptracks&user=username&api_key=api-key&format=json&limit=1000&page=1", ResponseRoot.class);
        verify(restTemplate).getForObject("https://ws.audioscrobbler.com/2.0/?method=user.gettoptracks&user=username&api_key=api-key&format=json&limit=1000&page=2", ResponseRoot.class);
    }

    @Test
    public void checkIfUserExists_shouldReturnFalse_whenLastFmUserNotFound(){
        when(restTemplate.getForObject(anyString(), any())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertFalse(lastFmClient.checkIfUserExists("username"));
    }

    @Test
    public void checkIfUserExists_shouldReturnTrue(){
        when(restTemplate.getForObject(anyString(), any())).thenReturn("");

        assertTrue(lastFmClient.checkIfUserExists("username"));
    }

}