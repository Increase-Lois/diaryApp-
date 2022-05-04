package com.technophiles.diaryapp.controllers;


import com.technophiles.diaryapp.dtos.requests.CreateAccountRequest;
import com.technophiles.diaryapp.dtos.responses.APIResponse;
import com.technophiles.diaryapp.exceptions.DiaryApplicationException;
import com.technophiles.diaryapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewUserAccount(@RequestBody CreateAccountRequest accountRequest){
        try{
            APIResponse response = APIResponse.builder().payLoad(userService.createAccount(accountRequest)).message("account created successfully")
                    .isSuccessful(true)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch(DiaryApplicationException exception){
            APIResponse response = APIResponse.builder()
                    .message(exception.getMessage())
                    .isSuccessful(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
