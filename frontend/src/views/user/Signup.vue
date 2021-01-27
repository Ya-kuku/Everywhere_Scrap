<template>
  <div class="container">
      <form>
        <div class="mb-3">
            <label for="Email" class="form-label">Email address</label>
            <input v-model="email" type="email" class="form-control" id="Email">
        </div>
        <div class="mb-3">
            <label for="Password" class="form-label">Password</label>
            <input v-model="password" type="password" class="form-control" id="Password">
        </div>
        <div class="mb-3">
            <label for="nickname" class="form-label">nickname</label>
            <input v-model="nickname" type="nickname" class="form-control" id="nickname">
        </div>
        <div class="mb-3">
            <label for="phone" class="form-label">phone number</label>
            <input v-model="phone" type="phone" class="form-control" id="phone">
        </div>
        <button type="submit" class="btn btn-primary" @click="signup">Submit</button>
        </form>
  </div>
</template>

<script>
import axios from 'axios';
import constants from '../../lib/constants'

export default {
    name: 'Signup',
    data() {
        return {
            email: "",
            password: "",
            passwordconfirm: "",
            nickname: "",
            phone: "",
        }
    },

    methods: {
        signup() {
            let signupData = {
                email: this.email,
                password: this.password,
                passwordconfirm: this.passwordconfirm,
                nickname: this.nickname,
                phone: this.phone
            }
            axios.post(`${constants.SERVER_URL}/account/signup/`,signupData)
            .then((res)=> {
                console.log(res)
                console.log(signupData)
            })
            .catch((err) => {
                console.log(err)
            })
        }
    },
}
</script>

<style>

</style>