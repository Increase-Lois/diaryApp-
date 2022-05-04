package com.technophiles.diaryapp.services;

import com.technophiles.diaryapp.exceptions.DiaryApplicationException;
import com.technophiles.diaryapp.models.Diary;
import com.technophiles.diaryapp.models.User;
import com.technophiles.diaryapp.repositories.DiaryRepository;
import com.technophiles.diaryapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Override
    public Diary createDiary(String title, String id) {
        User user = userRepository.findById(id).orElseThrow(()-> new DiaryApplicationException("User not found"));
        Diary diary = new Diary(title, user);
        return diaryRepository.save(diary);
    }
}
