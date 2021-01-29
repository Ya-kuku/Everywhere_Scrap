<template>
    <div class="topmg container">
        <div class="login-head">
            <div class="login-title">
                LOG IN
            </div>
            <p class="login-description"><strong>Everywhere Scrap</strong> 서비스를 이용하시려면 아이디가 필요합니다.</p>
            <p class="login-description">아직 아이디가 없다면 회원가입을 해주세요.</p>
            <div class="login-signup">
                <a class="btn login-a" href="/user/signup" type="submit">Sign Up</a>
            </div>
        </div>
        <div class="login-body">
            <div class="mb-5">
                <label for="loginemail" class="form-label">Email</label>
                <input v-model="email" type="email" class="form-control" id="loginemail" placeholder="name@example.com">
            </div>
            <div class="mb-5">
                <label for="loginpassword" class="form-label">Password</label>
                <input v-model="password" type="password" class="form-control" id="loginpassword" placeholder="영어 대문자·소문자, 숫자, 특수문자 (8 ~ 10자 이상)">
            </div>
            <div class="login-btn">
                <button @click="login" type="submit">Log In</button>
            </div>
        </div>
    </div>
</template>

<script>
import '../../style/user.css'
import axios from 'axios'
import Swal from "sweetalert2";
import constants from '../../lib/constants'

export default {
    name: 'Login',
    data() {
        return {
            email:'',
            password:'',
            token:'',
            isLoggedIn:false
        }
    },
    created() {
        this.login_check();
        if (this.isLoggedIn) {
            this.$router.push({
                name:constants.URL_TYPE.MAIN
            })
        }
    },
    methods: {
        login_check() {
            if (this.$cookies.isKey('Auth-Token')) {
                this.isLoggedIn = true
            } else{
                this.isLoggedIn = false
            }
        },
        login() {
            axios.get(`${constants.SERVER_URL}/account/login`, {
                params:{
                    email: this.email,
                    password: this.password
                }
            })
            .then((res) => {
                if (res.data.status) {
                    this.token = res.data.object.accessToken;
                    this.$cookies.set("Auth-Token", this.token);
                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'top-end',
                        showConfirmButton: false,
                        timer: 1000,
                        timerProgressBar: true,
                    })
    
                    Toast.fire({
                        icon: 'success',
                        title: '로그인 되었습니다.'
                    })
                    setTimeout(() => {
                        this.$router.go()
                    },1200)

                }
            })
            .catch((err) => {
                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 1000,
                    timerProgressBar: true,
                })

                Toast.fire({
                    icon: 'error',
                    title: err.response.data.data
                })
                setTimeout(() => {
                    this.$router.go()
                },1200)

            })
        },
    }

}
</script>

<style>

</style>