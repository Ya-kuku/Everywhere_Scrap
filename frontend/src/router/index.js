import Vue from 'vue'
import VueRouter from 'vue-router'
import constants from '../lib/constants.js'

import Main from '../views/main/Main.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: constants.URL_type.MAIN,
    component: Main,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
