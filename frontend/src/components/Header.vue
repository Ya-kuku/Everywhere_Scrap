<template>
  <nav class="navbar navbar-expand-lg">
    <div class="container">
      <a class="navbar-brand main-title" href="/"><img class="logo" :src="logo" /> Everywhere Scrap</a>

      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link" href="/user/login">Login</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/user/signup">SignUp</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/">Article</a>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
import logo from "../assets/newspaper.png"
// import axios from "axios";
// import constats from '@/lib/contstans'
export default {
  name:"header",
  data() {
    return{
      logo: logo
    }
  }
}
</script>

<style>

</style>