<template>
  <div class="topmg">
        <div style="display:flex;justify-content:center;">
            <div class="detail-head"> 
                <div class="news-title">{{ date }} <i @click="reload" style="cursor:pointer;color:#F5dF4D;" class="fas fa-sync fa-xs"></i></div>
            </div>
            <button @click="goNews" class="go-news">
                <i class="fas fa-home fa-lg"></i> NEWS 홈으로
            </button>
        </div>
        <div class="container">
            <div class="detail-headline">
                <div class="detail-title">{{category.toUpperCase()}}</div>
                <div class="detail-title-div">
                    <div v-for="h in headline" :key="h.id">
                        <div class="detail-title-body">{{ h.title }}</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container detail-div">
            <div class="detail-body">
                <div class="detail-body-title">전체 NEWS</div>
                <div v-for="news in main" :key="news.id">
                    <div class="headline-body">{{ news.title }}</div>
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
            //     time = time - 12
            // }
            this.dateCheck = year + month + day + time_check;
            this.date = year +'년 ' + month + '월 ' + day + '일 ' + time + '시'
        },
        getHead() {
            axios.get(constants.SERVER_URL + '/news/'+this.category+'/headline', { params : { date:this.dateCheck } })
            .then((res) => {
                this.headline = res.data.object
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
        },
        reload() {
            this.$router.go()
        },
        goNews() {
            const path = `/news`
            if (this.$route.path !== path) {
                this.$router.push({
                name: constants.URL_TYPE.NEWS.NEWS,
                })
            }
        }
    }
}
</script>

<style>

</style>