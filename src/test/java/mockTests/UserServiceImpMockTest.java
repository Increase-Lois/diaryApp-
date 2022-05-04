package mockTests;

import com.technophiles.diaryapp.DataConfig;
import com.technophiles.diaryapp.dtos.UserDTO;
import com.technophiles.diaryapp.dtos.requests.CreateAccountRequest;
import com.technophiles.diaryapp.models.User;
import com.technophiles.diaryapp.repositories.UserRepository;
import com.technophiles.diaryapp.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DataMongoTest
@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = "com.technophiles.diaryapp.**")
@ContextConfiguration(classes = {DataConfig.class})
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
public class UserServiceImpMockTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();

    @BeforeEach
    void setUp(){

    }

    @Test
    void testThatAnAccountCanBeCreated(){
        //given
        CreateAccountRequest accountRequest  = CreateAccountRequest.builder().email("test@gmail.com").password("pass").build();
        //when
        User user = new User("dummy id", "password","love");
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class)))
                .thenReturn(user) ;
        UserDTO userDTO = userService.createAccount(accountRequest);
        assertThat(userDTO.getId()).isEqualTo("dummy id");
        assertThat(userDTO.getEmail()).isEqualTo("password");
    userService.createAccount(accountRequest);
    }
}
