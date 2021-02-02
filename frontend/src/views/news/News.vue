<template>
  <div class="topmg">
    <div class="news-head"> 
      <div class="news-title">{{ date }} <i @click="reload" style="cursor:pointer;color:#F5dF4D;" class="fas fa-sync fa-xs"></i></div>
      
    </div>
    <div class="container">
      <div>
        <div class="headline-title">경제</div>
        <div class="news-headline">
          <div v-for="h in economy_head" :key="h.id">
            <div class="headline-body">{{ h.title }}</div>
          </div>
        </div>
      </div>
      <div>
        <div class="headline-title">사회</div>
        <div class="news-headline">
          <div v-for="h in economy_head" :key="h.id">
            <div class="headline-body">{{ h.title }}</div>
          </div>
        </div>
      </div>
      <div>
        <div class="headline-title">IT/과학</div>
        <div class="news-headline">
          <div v-for="h in economy_head" :key="h.id">
            <div class="headline-body">{{ h.title }}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="news-body container">
      <div>
        <div v-for="h in economy" :key="h.id">
          <div class="headline-body">{{ h.title }}</div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import '../../style/news.css'

import axios from 'axios'
import constants from '../../lib/constants'

export default {
  name:"News",
  props: {
    cate: {
      type: Number,
      default:0,
    }
  },
  data() {
    return {
      dateCheck:'',
      date:'',
      economy_head:[],
      society_head:[],
      itscience_head:[],
      economy: [],
      society: [],
      itscience: [],
    }
  },
  created() {
    this.getToday();
    this.getHead();
    this.getNews();
  },
  methods: {
    getToday() {
      let today = new Date();
      let year = today.getFullYear();
      let month = new String(today.getMonth() + 1);
      let day = new String(today.getDate());
      let time = today.getHours();
      if (month.length == 1){
        month = "0" + month;
      } 
      if (day.length == 1) {
        day = "0" + day;
      }
      this.dateCheck = year + month + day;
      this.date = year +'년 ' + month + '월 ' + day + '일 ' + time + '시'
    },
    getHead() {
      axios.get(constants.SERVER_URL + '/news/economy/headline', { params : { date:this.dateCheck } })
      .then((res) => {
        this.economy_head = res.data.object.headline
      })
      .catch((err) => {console.log(err)})
    },
    getNews() {
      axios.get(constants.SERVER_URL + '/news/economy')
      .then((res) => {
        this.economy = res.data.object
      })
      .catch((err) => {console.log(err)})

      axios.get(constants.SERVER_URL + '/news/society')
      .then((res) => {
        this.society = res.data.object
      })
      .catch((err) => {console.log(err)})

      axios.get(constants.SERVER_URL + '/news/itscience')
      .then((res) => {
        // this.itscience = res.data.object
        for(var i=0;i<res.data.object.length;i++){
          let tmp = res.data.object[i].main
          console.log(tmp)
          for (var j = 0;j<tmp.length;j++) {
            console.log(tmp[j])
            this.itscience.push(tmp[j])
          }
          console.log(this.itscience)
        }
        console.log(this.itscience)
      })
      .catch((err) => {console.log(err)})
    },
    reload() {
      this.$router.go()
    }
  }
}
</script>

<style>

</style>