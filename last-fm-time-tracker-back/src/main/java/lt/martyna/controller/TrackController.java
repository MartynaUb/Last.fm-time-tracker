package lt.martyna.controller;

import lt.martyna.service.TrackService;
import lt.martyna.response.TrackResponse;
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
@RequestMapping("/{userId}/track")
public class TrackController {

    private final TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public List<TrackResponse> tracks(@PathVariable("userId") long userId,
                                      @PageableDefault(direction = Sort.Direction.DESC, sort = "totalPlayTime", value = 20) Pageable pageable) {
        return trackService.getTracks(userId, pageable)
                .stream()
                .map(TrackResponse::new)
                .collect(Collectors.toList());
    }
}
