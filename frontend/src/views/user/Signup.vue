<template>
    <div class="topmg container">
        <div class="login-head">
            <div class="login-title">
                SIGN UP
            </div>
            <p class="login-description"><strong>Everywhere Scrap</strong> 서비스에 이미 가입되어 있다면,</p>
            <p class="login-description">바로 로그인을 해주세요.</p>
            <div class="login-signup">
                <a class="btn login-a" href="/user/login" type="submit">Log In</a>
            </div>
        </div>
        <div class="login-body">
            <div class="mb-3">
                <label for="Email" class="form-label">Email</label>
                <input v-model="signupData.email" type="email" class="form-control" id="Email" placeholder="name@example.com">
            </div>
            <div class="mb-3">
                <label for="nickname" class="form-label">Nickname</label>
                <input v-model="signupData.nickname" type="nickname" class="form-control" id="nickname" placeholder="닉네임을 입력해주세요.">
            </div>
            <div class="mb-3">
                <label for="phone" class="form-label">Phone Number</label>
                <input v-model="signupData.phone" type="phone" class="form-control" id="phone" placeholder="010-0000-0000">
            </div>
            <div class="mb-3">
                <label for="Password" class="form-label">Password</label>
                <input v-model="signupData.password" type="password" class="form-control" id="Password" placeholder="영어 대문자·소문자, 숫자, 특수문자 (8자 이상)">
            </div>
            <div class="mb-3">
                <label for="Passwordconfirm" class="form-label">Password Confirm</label>
                <input v-model="signupData.passwordconfirm" type="password" class="form-control" id="Passwordconfirm" placeholder="영어 대문자·소문자, 숫자, 특수문자 (8자 이상)">
            </div>
            <button type="submit" @click="signup">Sign Up</button>
        </div>
  </div>
</template>

<script>
import axios from 'axios';
import Swal from "sweetalert2";
import constants from '../../lib/constants'

export default {
    name: 'Signup',
    data() {
        return {
            signupData : {
                email: "",
                nickname: "",
                phone: "",
                password: "",
                passwordconfirm: "",
            },
        }
    },

    methods: {
        signup() {
            axios.post(`${constants.SERVER_URL}/account/signup/`, this.signupData)
            .then((res)=> {
                if (res.data.status) {
                    if (this.signupData.password === this.signupData.passwordconfirm) {
                        const Toast = Swal.mixin({
                            toast: true,
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 1000,
                            timerProgressBar: true,
                        })
                        Toast.fire({
                            icon: 'success',
                            title: '회원가입을 축하합니다!'
                        })
                        setTimeout(() => {
                            this.$router.push({
                                name: constants.URL_TYPE.USER.LOGIN,
                            });
                        },1200)
                    } else {
                        const Toast = Swal.mixin({
                            toast: true,
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 1000,
                            timerProgressBar: true,
                        })
        
                        Toast.fire({
                            icon: 'error',
                            title: '비밀번호가 일치하지 않습니다.'
                        })
                        setTimeout(() => {
                            this.signupData.password ="";
                            this.signupData.passwordconfirm ="";
                        },1200)
                    }
                } else {
                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'top-end',
                        showConfirmButton: false,
                        timer: 1000,
                        timerProgressBar: true,
                    })
    
                    Toast.fire({
                        icon: 'error',
                        title: res.data.data
                    })
                    setTimeout(() => {
                        this.signupData.email ="";
                        this.signupData.nickname ="";
                        this.signupData.phone ="";
                        this.signupData.password ="";
                        this.signupData.passwordconfirm ="";
                    },1200)
                }
            })
            .catch((err) => console.log(err))
        }
    },
}
</script>

<style>

</style>