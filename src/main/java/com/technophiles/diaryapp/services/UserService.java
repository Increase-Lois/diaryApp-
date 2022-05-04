package com.technophiles.diaryapp.services;

import com.technophiles.diaryapp.dtos.UserDTO;
import com.technophiles.diaryapp.dtos.requests.CreateAccountRequest;
import com.technophiles.diaryapp.dtos.requests.UpdateUserRequest;
import com.technophiles.diaryapp.models.Diary;
import com.technophiles.diaryapp.models.User;

public interface UserService {
    UserDTO createAccount(CreateAccountRequest createAccountRequest);

    UserDTO findUser(String id);

    String updateUser(String id, UpdateUserRequest updateRequest);

    Diary addNewDiary(String userId, Diary diary);

    User findUserByIdInternal(String userId);

    void deleteByEmail(String email);
}
