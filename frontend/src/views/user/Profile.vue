<template>
  <div class="topmg">
    <div class="container">
      <div class="pf-head">
          <div class="pf-titles">
            <div class="pf-title">
              <div class="pf-nickname">{{profileData.nickname}}</div><span>님의 페이지</span>
            </div>
            <div class="pf-btns">
              <button class="pf-btn" @click="doDelete" type="submit">탈퇴</button>
            </div>
          </div>
          <div class="pf-description">
            <span class="badge rounded-pill">E-Mail</span><span>{{profileData.email}}</span>
          </div>
          <div class="pf-description">
            <span class="badge rounded-pill">Phone</span><span>{{profileData.phone}}</span>
          </div>
      </div>
      <div class="pf-wc">
        <img class="pf-img" :src="wordcloud" />
      </div>
    </div>
    <div class="container pf-body">
      <div class="pf-cate">
        <button>경제</button>
        <button>사회</button>
        <button>IT/과학</button>
      </div>
    </div>
  </div>
</template>

<script>
import wordcloud from '../../assets/wordcloud.png'

import axios from 'axios';
import Swal from "sweetalert2";
import constants from '../../lib/constants';

export default {
    name: "Profile",
    data() {
        return {
          wordcloud:wordcloud,
          profileData:[],
        }
    },
    created() {
      this.userInfo()
    },
    mounted() {
      this.login_check()
    },
    methods: {
      login_check(){
        if (this.$cookies.isKey('Auth-Token')) {
          this.isLoggedIn = true
        } else{
          this.isLoggedIn = false
        }
      },
      userInfo() {
        const token = this.$cookies.get("Auth-Token");
        axios
        .get(constants.SERVER_URL + '/account/profile', { params: { Token: token } })
        .then((res)=> {
          this.profileData = res.data.object;
          console.log(res.data.object)
        })
      },
      doDelete() {
        Swal.fire({ icon:"warning", text:"Everywhere Scrap", title: "탈퇴하시겠습니까?", dangerMode: true, showDenyButton: true, denyButtonText: `OK`, showCancelButton: true, showConfirmButton:false })
        .then((res) => {
            if (res.isDenied) {
              axios
              .delete(constants.SERVER_URL + "/account/delete", { params: { uid: this.profileData.uid } })
              .then((res) => {
                this.$cookies.remove("Auth-Token");
                const Toast = Swal.mixin({
                  toast: true,
                  position: 'top-end',
                  showConfirmButton: false,
                  timer: 1000,
                  timerProgressBar: true,
                })
                Toast.fire({
                  icon: 'success',
                  title: res.data.data
                })
                setTimeout(() => {
                  this.$router.push("/")
                  this.$router.go();
                },1200)
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
                  title: err.response.data
                })
                setTimeout(() => {
                  this.$router.go();
                },1200)
              });
            }
          }
        );
      }
    }
}
</script>

<style>

</style>