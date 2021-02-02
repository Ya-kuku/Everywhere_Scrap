<template>
  <div>

  </div>
</template>

<script>
import '../../style/news.css'

import axios from 'axios'
import constants from '../../lib/constants'

export default {
  name:"Economy",
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
      economy: [],
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
            console.log(this.economy_head)
        })
        .catch((err) => {console.log(err)})
    },
  }
}
</script>

<style>

</style>