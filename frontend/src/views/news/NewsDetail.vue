<template>
  <div class="topmg">
        <div style="display:flex;justify-content:center;">
            <div class="detail-head"> 
                <div class="news-title">{{ date }} <i @click="reload" style="cursor:pointer;color:#F5dF4D;" class="fas fa-sync fa-xs"></i></div>
            </div>
            <button @click="goNews" class="go-news">
                <i class="fas fa-home fa-lg"></i> 전체 NEWS
            </button>
        </div>
        <div class="container">
            <div class="news-detail-headline">
                <div class="news-detail-title">{{category.toUpperCase()}}</div>
                <div v-for="h in headline" :key="h.id">
                    <a target="_blank" :href="h.url"><div class="detail-headline-body">{{ h.title }}</div></a>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="detail-body">
                <div class="news-body-title">전체 NEWS</div>
                <div v-for="m in pageOfMain" :key="m.id">
                    <div @click="goContent(m.locate)" class="headline-body">{{ m.title }}</div>
                </div>
            </div>
        </div>
        <div class="container">
            <jw-pagination
                class="news-pagination"
                :styles="customStyles"
                :labels="customLabels"
                :items="main"
                @changePage="onChangePage"
                :pageSize="20"
            ></jw-pagination>
        </div>
    </div>
</template>

<script>
import '../../style/news.css'

import axios from 'axios'
import constants from '../../lib/constants'

const customStyles = {
    ul: {
        margin:'1rem auto 5rem auto',
    },
    li: {
        display: "inline-block",
    },
    a: {
        borderRadius: "50%",
        width:"3.7rem",
        padding:"1rem",
        margin:"0.2rem",
        fontSize:"1.3rem"
    },
};
const customLabels = {
  first: "<<",
  last: ">>",
  previous: "<",
  next: ">",
};
export default {
    name:"Newsdetail",
    data() {
        return {
            dateCheck:'',
            date:'',
            headline:[],
            main: [],
            pageOfMain: [],
            category:'',
            cate: this.$route.path.slice(-1),

            customStyles,
            customLabels,
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
        },
        onChangePage(pageOfMain) {
            this.pageOfMain = pageOfMain;
        },
        reload() {
            this.$router.go()
        },
        goNews() {
            const path = `/news`
            if (this.$route.path !== path) {
                this.$router.push({ name: constants.URL_TYPE.NEWS.NEWS })
            }
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