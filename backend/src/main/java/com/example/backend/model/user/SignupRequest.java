// package com.example.backend.model.user;

// import javax.validation.Valid;
// import javax.validation.constraints.NotNull;
// import javax.validation.constraints.Pattern;

// import io.swagger.annotations.ApiModelProperty;
// import lombok.Data;
// import lombok.ToString;


// @Valid
// @ToString
// @Data

// public class SignupRequest {
//     @ApiModelProperty(required = true)
//     @NotNull
//     @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$/i")
//     String email;
//     @ApiModelProperty(required = true)
//     @NotNull
//     @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
//     String password;
//     @ApiModelProperty(required = true)
//     @NotNull
//     String nickname;
//     @ApiModelProperty(required = true)
//     @NotNull
//     String phone;

//     // public String getEmail() {
//     //     return email;
//     // }

//     // public void setEmail(String email) {
//     //     this.email = email;
//     // }

//     // public String getPassword() {
//     //     return password;
//     // }

//     // public void setPassword(String password) {
//     //     this.password = password;
//     // }

//     // public String getNickname() {
//     //     return nickname;
//     // }

//     // public void setNickname(String nickname) {
//     //     this.nickname = nickname;
//     // }
//     // public String getPhone() {
//     //     return phone;
//     // }

//     // public void setPhone(String phone) {
//     //     this.phone = phone;
//     // }
// }
