<template>
  <div>
      {{category}}
  </div>
</template>

<script>
import '../../style/news.css'

import axios from 'axios'
import constants from '../../lib/constants'

export default {
    name:"Newsdetail",
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
            headline:[],
            main: [],
            category:''
        }
    },
    created() {
        if (this.cate == 1) {
            this.category = 'economy'
        } else if (this.cate == 2) {
            this.category = 'society'
        } else {
            this.category = 'itscience'
        }
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
            axios.get(constants.SERVER_URL + '/news/'+this.category+'/headline', { params : { date:this.dateCheck } })
            .then((res) => {
                this.headline = res.data.object.headline
                console.log(this.headline)
            })
            .catch((err) => {console.log(err)})
        },
        getNews() {
            axios.get(constants.SERVER_URL + '/news/' + this.category)
            .then((res) => {
              this.main = res.data.object
            })
            .catch((err) => {console.log(err)})

            // axios.get(constants.SERVER_URL + '/news/society')
            // .then((res) => {
            //   this.society = res.data.object
            // })
            // .catch((err) => {console.log(err)})

            // axios.get(constants.SERVER_URL + '/news/itscience')
            // .then((res) => {
            //   for(var i=0;i<res.data.object.length;i++){
            //     let tmp = res.data.object[i].main
            //     for (var j in tmp){
            //       this.itscience.push(tmp[j])
            //     }
            //   }
            // })
            // .catch((err) => {console.log(err)})
        }
    }
}
</script>

<style>

</style>