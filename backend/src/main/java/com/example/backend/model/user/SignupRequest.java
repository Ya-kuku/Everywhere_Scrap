package com.example.backend.model.user;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Valid
@Data
@Document(collection="account")

public class SignupRequest {

    @ApiModelProperty(required = true)
    @NotNull(message="이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+", message="이메일 형식에 맞지 않습니다.")
    String email;
    
    @ApiModelProperty(required = true)
    @NotNull(message="닉네임은 필수 입력 값입니다.")
    String nickname;

    @ApiModelProperty(required = true)
    @NotNull(message="전화번호는 필수 입력 값입니다.")
    @Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$", message="전화번호 형식이 맞지 않습니다.")
    String phone;

    @ApiModelProperty(required = true)
    @NotNull(message="비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message="비밀번호는 영어 대·소문자, 숫자, 특수기호가 적어도 1개 이상 포함되어야 합니다.")
    String password;

    

}
