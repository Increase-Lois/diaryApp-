package com.technophiles.diaryapp.services;

import com.technophiles.diaryapp.dtos.UserDTO;
import com.technophiles.diaryapp.dtos.requests.CreateAccountRequest;
import com.technophiles.diaryapp.dtos.requests.UpdateUserRequest;
import com.technophiles.diaryapp.exceptions.DiaryApplicationException;
import com.technophiles.diaryapp.mappers.UserMapper;
import com.technophiles.diaryapp.mappers.UserMapperImpl;
import com.technophiles.diaryapp.models.Diary;
import com.technophiles.diaryapp.models.User;
import com.technophiles.diaryapp.repositories.DiaryRepository;
import com.technophiles.diaryapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private UserMapper userMapper = new UserMapperImpl();

    @Autowired
     DiaryRepository diaryRepository;

    @Override
    public UserDTO createAccount(CreateAccountRequest createAccountRequest) {
        Optional<User> optionalUser = userRepository.findUserByEmail(createAccountRequest.getEmail());
        if (optionalUser.isPresent())
            throw new DiaryApplicationException("user exists!!");
        User user = new User();
        user.setEmail(createAccountRequest.getEmail());
        user.setPassword(createAccountRequest.getPassword());
        user.setDiaries(new HashSet<>());
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public UserDTO findUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new DiaryApplicationException
                ("User with id does not exist"));
        return userMapper.userToUserDTO(user);
    }

    @Override
    public String updateUser(String id, UpdateUserRequest updateRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new DiaryApplicationException("User Account Does not exist"));
        boolean isUpdated = false;
        if (!(updateRequest.getEmail()== null || updateRequest.getEmail().trim().equals(""))){
            user.setEmail(updateRequest.getEmail());
            isUpdated=true;
        }
        if(!(updateRequest.getPassword() == null || updateRequest.getPassword().trim().equals(""))){
            user.setPassword(updateRequest.getPassword());
            isUpdated=true;
        }

        if(isUpdated){
            userRepository.save(user);
        }
        return "User details updated successfully";
    }


    @Override
    public User findUserByIdInternal(String userId) {
        return userRepository.findById(userId).orElseThrow(()-> new DiaryApplicationException("User does not exist"));
    }

    @Override
    public void deleteByEmail(String email) {
      User user =  userRepository.findUserByEmail(email).orElseThrow(()-> new DiaryApplicationException("User not found"));
      userRepository.delete(user);
    }


    @Override
    public Diary addNewDiary(String userId, Diary diary) {
        User user = userRepository.findById(userId).orElseThrow(()-> new DiaryApplicationException("User does not exist"));
        Diary savedDiary = diaryRepository.save(diary);
        user.getDiaries().add(diary);
        userRepository.save(user);
        return savedDiary;
    }


}
