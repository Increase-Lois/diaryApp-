package com.technophiles.diaryapp.services;

import com.technophiles.diaryapp.dtos.UserDTO;
import com.technophiles.diaryapp.dtos.requests.CreateAccountRequest;
import com.technophiles.diaryapp.dtos.requests.UpdateUserRequest;
import com.technophiles.diaryapp.exceptions.DiaryApplicationException;
import com.technophiles.diaryapp.models.Diary;
import com.technophiles.diaryapp.models.User;
import com.technophiles.diaryapp.repositories.DiaryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DiaryRepository diaryRepository;

    private CreateAccountRequest accountRequest;

    @BeforeEach
        void setUp (){
            accountRequest = CreateAccountRequest.builder().email("test@gmail.com").password("pass").build();
    }

    @Test
    void testCreateAccount() {
        UserDTO userDTO = userService.createAccount(accountRequest);
        assertThat(userDTO.getId()).isNotNull();
        assertThat(userDTO.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    void testThatThrowsDiaryExceptionWhenEmailAlreadyExists(){
        userService.createAccount(accountRequest);
        CreateAccountRequest accountRequest = CreateAccountRequest.builder().email("test@gmail.com").password("pass").build();
        assertThatThrownBy(()-> userService.createAccount(accountRequest)).isInstanceOf(DiaryApplicationException.class).hasMessage("user exists!!");
    }

    @Test
    void thatUserInformationCanBeGottenTest(){
       UserDTO userDTO = userService.createAccount(accountRequest);
       UserDTO userFromDatabase = userService.findUser(userDTO.getId());
        assertThat(userDTO.getId()).isEqualTo(userFromDatabase.getId());
    }

    @Test
    void updateUserInformationTest(){
        UserDTO userDTO = userService.createAccount(accountRequest);
        UpdateUserRequest updateRequest = new UpdateUserRequest("", "new password");
        String result = userService.updateUser(userDTO.getId(), updateRequest);
        assertThat(result).isEqualTo( "User details updated successfully");
        UserDTO userFromDataBase = userService.findUser(userDTO.getId());
        assertThat(userFromDataBase.getEmail()).isEqualTo("test@gmail.com");

    }

    @Test
    void testThatThrowsExceptionWhenUserIdIsNotFound(){
        userService.createAccount(accountRequest);
        String id = "null id";
        UpdateUserRequest updateRequest = UpdateUserRequest.builder().email("test@gmail.com").password("password").build();
        assertThatThrownBy(()-> userService.updateUser(id, updateRequest)).isInstanceOf(DiaryApplicationException.class).hasMessage("User Account Does not exist");

    }

    @Test
    void diaryCanBeAddedToAUserTest(){
        UserDTO userDTO = userService.createAccount(accountRequest);
        User user = userService.findUserByIdInternal(userDTO.getId());
        String diaryTitle = "diary title";
        Diary diary = new Diary(diaryTitle, user);
        Diary savedDiary = userService.addNewDiary(userDTO.getId(), diary);
        assertThat(savedDiary.getId()).isNotNull();
        assertThat(savedDiary.getTitle()).isEqualTo("diary title");

    }
    @AfterEach
    void teardown(){
        userService.deleteByEmail("test@gmail.com");
    }
}