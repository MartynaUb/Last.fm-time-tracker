package lt.martyna.controller;

import lt.martyna.repository.TrackRepository;
import lt.martyna.repository.UserRepository;
import lt.martyna.entity.Track;
import lt.martyna.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
class TrackControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    @Transactional
    public void cleanUp() {
        trackRepository.deleteAll();
        userRepository.deleteAll();
    }

    private final User user = new User("username");
    private final Track track = new Track("artist", "title", 12, 2, user);
    @Test
    public void getTracks_shouldReturnTracks() throws Exception {
        User savedUser = userRepository.save(user);
        trackRepository.save(track);

        this.mockMvc.perform(get("/"+savedUser.getId()+"/track")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"artist\":\"artist\",\"songTitle\":\"title\",\"duration\":\"0:12\",\"playCount\":2,\"totalPlayTime\":\"0 days, 0 hours, 0 minutes, 24 seconds\"}")));
    }
}
