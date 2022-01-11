package lt.martyna.controller;

import lt.martyna.response.UserResponse;
import lt.martyna.service.LastFmService;
import lt.martyna.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final LastFmService lastFmService;

    @Autowired
    public UserController(LastFmService lastFmService) {
        this.lastFmService = lastFmService;
    }

    @PostMapping
    public UserResponse cacheUser(@RequestParam String username){
        User user = lastFmService.upsertUser(username);
        return new UserResponse(user.getUsername(), user.getId());
    }
}
