package com.technophiles.diaryapp.dtos.responses;

import com.technophiles.diaryapp.dtos.UserDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class APIResponse {
    private UserDTO payLoad;
    private String message;
    private boolean isSuccessful;
}
