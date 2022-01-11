package lt.martyna.service;

import lt.martyna.entity.Artist;
import lt.martyna.client.LastFmClient;
import lt.martyna.entity.Track;
import lt.martyna.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LastFmServiceTest {
    private final User user = new User("username");
    private final List<Track> tracks = List.of(new Track("artist", "title", 12, 2, user),
            new Track("artist", "title2", 12, 1, user));
    private final List<Artist> artists = List.of(new Artist("artist", user, tracks));
    private final ArtistService artistService = mock(ArtistService.class);
    private final TrackService trackService = mock(TrackService.class);
    private final UserService userService = mock(UserService.class);
    private final LastFmClient lastFmClient = mock(LastFmClient.class);
    private final LastFmService lastFmService = new LastFmService(artistService, trackService, userService, lastFmClient);

    @Test
    void upsertUser_shouldReturnUserAndNotCallUpsert_forExistingUser() {
        when(userService.findUserByUsername(any())).thenReturn(Optional.of(user));

        User actualUser = lastFmService.upsertUser(user.getUsername());

        assertEquals(user.getUsername(), actualUser.getUsername());
        verify(userService, never()).saveUser(any());
        verifyNoInteractions(lastFmClient);
        verifyNoInteractions(trackService);
        verifyNoInteractions(artistService);
    }

    @Test
    void upsertUser_shouldThrowException_nonExistentUserInDBAndLastFm() {
        when(userService.findUserByUsername(any())).thenReturn(Optional.empty());
        when(lastFmClient.checkIfUserExists(any())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> lastFmService.upsertUser("Martyna_ub"));
    }

    @Test
    void upsertUser_shouldSave_ExistingLastFmUser() {
        when(userService.findUserByUsername(any())).thenReturn(Optional.empty());
        when(lastFmClient.checkIfUserExists(any())).thenReturn(true);
        when(userService.saveUser(any())).thenReturn(user);
        when(lastFmClient.fetchUserTracks(any())).thenReturn(tracks);

        User actualUser = lastFmService.upsertUser("username");

        assertEquals(user.getUsername(), actualUser.getUsername());
        verify(lastFmClient).fetchUserTracks(user);
        verify(artistService).refreshArtists(user.getId(), artists);
        verify(trackService).refreshTracks(user.getId(), tracks);
    }

}