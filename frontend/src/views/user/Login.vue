<template>
    <div class="container">
        <div>
            <div class="mb-3">
                <label for="exampleFormControlInput1" class="form-label">Email address</label>
                <input v-model="email" type="email" class="form-control" id="exampleFormControlInput1" placeholder="name@example.com">
            </div>

            <div class="mb-3">
                <label for="exampleFormControlInput2" class="form-label">Password</label>
                <input v-model="password" type="password" class="form-control" id="exampleFormControlInput2" placeholder="소문자 대문자 ㅁㄴ어라닝러">
            </div>

            <button @click="login" type="submit" variant="primary">LogIn</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import constants from '../../lib/constants'

export default {
    name: 'login',
    data() {
        return {
            email:'',
            password:'',
        }
    },
    methods: {
        login() {
            axios.get(`${constants.SERVER_URL}/account/login`, {
                params:{
                    email: this.email,
                    password: this.password
                }
            })
            .then((res) => {
                console.log(res)
                this.$router.push('/')
            })
            .error((err) => {
                console.log(err)
            })
        },
    }

}
</script>

<style>

</style>