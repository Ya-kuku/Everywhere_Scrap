import Vue from 'vue'
import VueRouter from 'vue-router'
import constants from '../lib/constants.js'

import Main from '../views/main/Main.vue'
import Login from '../views/user/Login.vue'
import Signup from '../views/user/Signup.vue'
import Profile from '../views/user/Profile.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: constants.URL_type.MAIN,
    component: Main,
  },
  {
    path: '/user/login',
    name: constants.URL_type.USER.LOGIN,
    component: Login,
  },
  {
    path: '/user/signup',
    name: constants.URL_type.USER.SIGNUP,
    component: Signup,
  },
  {
    path: '/user/profile',
    name: constants.URL_type.USER.PROFILE,
    component: Profile,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
