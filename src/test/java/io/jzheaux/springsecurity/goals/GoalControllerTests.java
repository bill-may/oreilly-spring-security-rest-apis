package io.jzheaux.springsecurity.goals;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GoalController.class)
@RunWith(SpringRunner.class)
public class GoalControllerTests {

  @Autowired
  MockMvc mvc;

  @MockBean
  GoalRepository goalRepository;
  @MockBean
  UserRepository users;

  @Test
  @WithMockUser(username = "Bill")
  public void testMakeGoalEndpoint() throws Exception {
    when(this.goalRepository.save(any(Goal.class))).thenAnswer(mockUser -> mockUser.getArgument(0));
    this.mvc.perform(post("/goal")
                             .with(csrf())
                             .content("New Goal"))
            .andExpect(status().isOk())
    .andExpect(jsonPath("$.owner", Matchers.is("Bill")));
  }
}
