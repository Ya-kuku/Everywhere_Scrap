import Vue from 'vue'
import App from './App.vue'
import router from './router'
import VueCookies from 'vue-cookies'
import JwPagination from 'jw-vue-pagination'

Vue.config.productionTip = false;
Vue.use(VueCookies)
Vue.component('jw-pagination', JwPagination)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
