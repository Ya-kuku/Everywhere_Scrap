import Vue from 'vue'
import VueRouter from 'vue-router'
import constants from '../lib/constants.js'

import Main from '../views/main/Main.vue'
import Login from '../views/user/Login.vue'
import Signup from '../views/user/Signup.vue'
import Profile from '../views/user/Profile.vue'
import News from '../views/news/News.vue'
import Newsdetail from '../views/news/NewsDetail.vue'
import Newscontents from '../views/news/NewsContents.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: constants.URL_TYPE.MAIN,
    component: Main,
  },
  {
    path: '/user/login',
    name: constants.URL_TYPE.USER.LOGIN,
    component: Login,
  },
  {
    path: '/user/signup',
    name: constants.URL_TYPE.USER.SIGNUP,
    component: Signup,
  },
  {
    path: '/user/profile',
    name: constants.URL_TYPE.USER.PROFILE,
    component: Profile,
  },
  {
    path: '/news',
    name: constants.URL_TYPE.NEWS.NEWS,
    component: News,
    props: true,
  },
  {
    path: '/news/:cate',
    name: constants.URL_TYPE.NEWS.NEWSDETAIL,
    component: Newsdetail,
    props: true,
  },
  {
    path: '/news/:locate',
    name: constants.URL_TYPE.NEWS.NEWSCONTENTS,
    component: Newscontents,
    props: true,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
