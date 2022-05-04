package com.technophiles.diaryapp.dtos.requests;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {
    private String email;
    private String password;
}
