package lt.martyna.service;

import lt.martyna.entity.Artist;
import lt.martyna.client.LastFmClient;
import lt.martyna.entity.Track;
import lt.martyna.entity.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LastFmService {
    private final ArtistService artistService;
    private final TrackService trackService;
    private final UserService userService;
    private final LastFmClient lastFmClient;

    public LastFmService(ArtistService artistService, TrackService trackService, UserService userService, LastFmClient lastFmClient) {
        this.artistService = artistService;
        this.trackService = trackService;
        this.userService = userService;
        this.lastFmClient = lastFmClient;
    }

    @Transactional
    public void upsertTracksAndArtists(User user) {
        List<Track> tracks = new ArrayList<>(lastFmClient.fetchUserTracks(user));

        Map<String, List<Track>> tracksByArtist =
                tracks.stream().collect(Collectors.groupingBy(Track::getArtist));
        List<Artist> artists = tracksByArtist
                .entrySet()
                .stream()
                .map(entry -> new Artist(entry.getKey(), user, entry.getValue()))
                .collect(Collectors.toList());

        trackService.refreshTracks(user.getId(), tracks);
        artistService.refreshArtists(user.getId(), artists);
    }

    public User upsertUser(String username) {
        Optional<User> possibleUser = userService.findUserByUsername(username);
        return possibleUser.orElseGet(() -> {
            if (lastFmClient.checkIfUserExists(username)) {
                User user = userService.saveUser(username);
                upsertTracksAndArtists(user);
                return user;
            } else {
                throw new IllegalArgumentException(String.format("Failed to find user [%s] in Last.fm", username));
            }
        });
    }
}
