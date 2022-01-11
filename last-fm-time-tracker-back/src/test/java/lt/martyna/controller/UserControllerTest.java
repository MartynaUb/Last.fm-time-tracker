package lt.martyna.controller;

import lt.martyna.repository.UserRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    @Transactional
    public void cleanUp() {
        userRepository.deleteAll();
    }

    private final User user = new User("Martyna_ub");

    @Test
    public void postUser_shouldReturnUser() throws Exception {
        User savedUser = userRepository.save(user);

        this.mockMvc.perform(post("/user?username=" + savedUser.getUsername())).andDo(print()).
                andExpect(status().isOk()).andExpect(content().string(containsString("\"username\":\"Martyna_ub\"}")));
    }
}