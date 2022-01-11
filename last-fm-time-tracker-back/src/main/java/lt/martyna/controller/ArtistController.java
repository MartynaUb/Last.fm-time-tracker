package lt.martyna.controller;

import lt.martyna.response.ArtistResponse;
import lt.martyna.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{userId}/artist")
public class ArtistController {
    public ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<ArtistResponse> artists(@PathVariable("userId") long userId,
                                        @PageableDefault(direction = Sort.Direction.DESC, sort = "totalPlayTime") Pageable pageable) {
        return artistService.getArtists(userId, pageable)
                .stream()
                .map(artist -> new ArtistResponse(artist.getArtist(), artist.getPlayCount(), artist.getTotalPlayTime()))
                .collect(Collectors.toList());
    }
}
