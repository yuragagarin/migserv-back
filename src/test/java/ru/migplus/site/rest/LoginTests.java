package ru.migplus.site.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.migplus.site.controller.AuthController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTests {

    @Autowired
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        /*this.mockMvc.perform(post("/api/auth/signin"))
                .contentType(MediaType.APPLICATION_JSON)
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));*/
    }
}
