<template>
  <div class="topmg">
    <div class="news-title"> 
      <div class="news-title-name">
        {{ date }} <i @click="reload" style="cursor:pointer;color:#F5dF4D;" class="fas fa-sync fa-xs"></i>
      </div>
    </div>
    <div class="container">
      <div>
        <div class="headline-title">
          <div @click="getHead(1)">경제</div>
        </div>
      </div>
      <div>
        <div class="headline-title">
          <div @click="getHead(2)">사회</div>
        </div>
      </div>
      <div>
        <div class="headline-title">
          <div @click="getHead(3)">IT/과학</div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="news-headline">
        <button v-if="num==1" @click="goNews(num)" class="go-news-btn"><i style="cursor:pointer;" class="fas fa-home"></i> 경제</button>
        <button v-if="num==2" @click="goNews(num)" class="go-news-btn"><i style="cursor:pointer;" class="fas fa-home"></i> 사회</button>
        <button v-if="num==3" @click="goNews(num)" class="go-news-btn"><i style="cursor:pointer;" class="fas fa-home"></i> IT/과학</button>
        <div v-for="h in head" :key="h.id">
          <a target="_blank" :href="h.url"><div class="headline-body">{{ h.title }}</div></a>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="news-body">
        <div class="news-body-title">전체 NEWS</div>
        <div v-for="news in allnews" :key="news.id">
          <div @click="goContent(news.locate)" class="headline-body">{{ news.title }}</div>
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
      num:1,
      head:[],
      locate:'',
      allnews: [],
    }
  },
  created() {
    this.getToday();
    this.getHead(1);
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
      let time_check = today.getHours();
      if (time_check > 7 && time_check < 11){
          time_check = 8
      }
      else if (time_check > 10 && time_check < 14) {
        time_check = 11
      }
      else if (time_check > 13 && time_check < 17) {
        time_check = 14
      }
      else if (time_check > 16 && time_check < 20) {
        time_check = 17
      }
      else {
        time_check = 17
      }
      // if (time > 13) {
      //   time = time - 12
      // }
      this.dateCheck = year + month + day + time_check;
      this.date = year +'년 ' + month + '월 ' + day + '일 ' + time + '시';
    },
    getHead(tmp) {
      this.num = tmp
      if (tmp == 1) {
        axios.get(constants.SERVER_URL + '/news/economy/headline', { params : { date:this.dateCheck } })
        .then((res) => {
          this.head = []
          for (var i=1;i<9;i++) {
            this.head.push(res.data.object[i])
          }
        })
        .catch((err) => {console.log(err)})
      } else if (tmp == 3) {
        axios.get(constants.SERVER_URL + '/news/itscience/headline', { params : { date:this.dateCheck } })
        .then((res) => {
          this.head = []
          for (var i=1;i<9;i++) {
            this.head.push(res.data.object[i])
          }
        })
        .catch((err) => {console.log(err)})
      } else {
        axios.get(constants.SERVER_URL + '/news/society/headline', { params : { date:this.dateCheck } })
        .then((res) => {
          this.head = []
          for (var i=1;i<9;i++) {
            this.head.push(res.data.object[i])
          }
        })
        .catch((err) => {console.log(err)})
      }
    },
    getNews() {
      axios.get(constants.SERVER_URL + '/news/all', { params : { date: this.dateCheck } })
      .then((res) => {
        this.allnews = res.data.object
      })
      .catch((err) => {console.log(err)})
    },
    reload() {
      this.$router.go()
    },
    goNews(category) {
      this.$router.push({name:'Newsdetail', params:{cate:category}})
    },
    goContent(locate) {
      let path = locate.slice(6,-4).split('/')
      this.$router.push({name:'Newscontents', params:{cate:path[0], day:path[1], time:path[2], num:path[3]}})
    },
  }
}
</script>

<style>

</style>