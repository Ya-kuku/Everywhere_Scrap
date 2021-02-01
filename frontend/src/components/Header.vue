<template>
  <nav class="navbar navbar-expand-lg fixed-top">
    <div class="container">
      <a class="navbar-brand main-title" href="/"><img class="logo" :src="logo" /> Everywhere Scrap</a>

      <ul id="navbaritem" class="navbar-nav me-auto mb-2 mb-lg-0">
        <li v-if="!isLoggedIn" class="nav-item">
          <button class="nav-link" @click="goLogin">LOG IN</button>
        </li>
        <li v-if="isLoggedIn" class="nav-item">
          <button class="nav-link" @click="goNews">NEWS</button>
        </li>
        <li v-if="isLoggedIn" class="nav-item">
          <button class="nav-link" @click="goProfile">MY PAGE</button>
        </li>
        <li v-if="isLoggedIn" class="nav-item">
          <button class="nav-link" @click="doLogout">LOG OUT</button>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
import "../style/header.css"
import logo from "../assets/newspaper.png"
import constants from '../lib/constants'

export default {
  name:"Header",
  data() {
    return{
      logo: logo,
      isLoggedIn: false
    }
  },
  created() {
    this.login_check();
    window.addEventListener("scroll", this.handleScroll);
  },
  mounted() {
    this.login_check();
  },
  methods: {
    login_check(){
      if (this.$cookies.isKey('Auth-Token')) {
        this.isLoggedIn = true
      } else{
        this.isLoggedIn = false
      }
    },
    handleScroll() {
      if (
        document.body.scrollTop > 80 ||
        document.documentElement.scrollTop > 80
      ) {
        document.getElementsByClassName("navbar")[0].style.height = "5rem";
        document.getElementsByClassName("navbar")[0].style.backgroundColor = "#EFEFF0";
        document.getElementsByClassName("navbar")[0].style.boxShadow = "0 3px 3px rgba(0,0,0,0.1)";
      } else {
        document.getElementsByClassName("navbar")[0].style.height = "7rem";
        document.getElementsByClassName("navbar")[0].style.boxShadow = "0 0 0";
        document.getElementsByClassName("navbar")[0].style.backgroundColor = "transparent";
      }
    },
    goLogin() {
      this.$router.push({
        name: constants.URL_TYPE.USER.LOGIN,
      });
    },
    goNews() {
      const path = `/news`
      if (this.$route.path !== path) {
        this.$router.push({
          name: constants.URL_TYPE.NEWS.NEWS,
        })
      }
    },
    goProfile() {
      const path = `/user/profile`
      if (this.$route.path !== path) {
        this.$router.push({
          name: constants.URL_TYPE.USER.PROFILE,
        })
      }
    },
    doLogout() {
      this.$cookies.remove("Auth-Token");
      if (this.$route.path !== '/') {
        this.$router.push("/");
      }
      this.login_check();
    },
  }
}
</script>

<style>

</style>