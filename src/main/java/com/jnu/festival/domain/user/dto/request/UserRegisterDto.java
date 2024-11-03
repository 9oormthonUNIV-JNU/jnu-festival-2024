package com.jnu.festival.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterDto (
        @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하이어야 합니다.")
        @Pattern(regexp = "^[a-zA-Z가-힣0-9]*$", message = "닉네임은 한글, 영문자, 숫자만 사용할 수 있습니다.")
        @NotBlank(message = "닉네임은 필수 항목입니다.")
        String nickname,

        @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z가-힣\\d\\W]*$", message = "비밀번호는 영문자, 한글, 숫자 및 특수문자만 사용할 수 있습니다.")
        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        String password
) {}
