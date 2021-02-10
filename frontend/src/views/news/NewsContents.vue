<template>
    <div class="topmg">
        <div class="container">
            <div>
                <a target="_blank" style="text-decoration:none;" :href="maincontent.url">
                    <div class="content-title">{{maincontent.title}}</div>
                </a>
                <div class="content-keywordss">
                    <div class="content-keywords" v-for="key in maincontent.keyword" :key="key.id">
                        <span class="content-keyword">{{key}}</span>
                    </div>
                </div>
                <div class="content">
                    <div>
                        <img :src='maincontent.locate' alt="">
                    </div>
                    <div>
                        <div class="content-summary" v-for="sum in maincontent.summary" :key="sum.id">
                            {{sum}}
                        </div>
                    </div>
                    <button v-if="isLoggedIn&!flag" @click="likeNews(maincontent.url)" class="content-likenews">
                        <i class="fas fa-thumbtack"></i> 찜하기
                    </button>
                    <button v-if="isLoggedIn&flag" @click="cancleNews(maincontent.url)" class="content-likenews">
                        <i class="fas fa-thumbtack"></i> 찜취소
                    </button>
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
    name:"Newscontents",
    data() {
        return {
            maincontent: [],
            isLoggedIn: false,
            flag:false,
        }
    },
    created() {
        this.login_check()
        this.getContents()
    },
    mounted() {
        this.login_check()
    },
    methods:{
        login_check(){
            if (this.$cookies.isKey('Auth-Token')) {
                this.isLoggedIn = true
            } else{
                this.isLoggedIn = false
            }
        },
        getContents() {
            let cate = this.$route.path.slice(6,).split('/')
            let tmp = '2021' + cate[1] + cate[2]
            let index = cate[3]
            axios.get(constants.SERVER_URL + '/news/'+ cate[0] +'/contents', { params : { date:tmp, index:index } })
            .then((res) => {
                this.maincontent = res.data.object
                this.maincontent.locate = require('@/assets'+ this.maincontent.locate.slice(1,))
                this.findLikeNews()
            })
            .catch((err) => console.log(err))
        },
        likeNews(urls) {
            axios.get(constants.SERVER_URL + '/account/likenews', { params : { Token:this.$cookies.get('Auth-Token'), url:urls } })
            .then((res) => {
                console.log(res.data.object)
                this.findLikeNews()
            })
            .catch((err) => console.log(err))
        },
        cancleNews(urls) {
            axios.get(constants.SERVER_URL + '/account/likenews/delete', { params : { Token:this.$cookies.get('Auth-Token'), url:urls } })
            .then((res) => {
                console.log(res.data.object)
                this.findLikeNews()
            })
            .catch((err) => console.log(err))
        },
        findLikeNews() {
            axios.get(constants.SERVER_URL + '/account/likenews/find', { params : { Token:this.$cookies.get('Auth-Token'), url:this.maincontent.url } })
            .then((res) => {
                this.flag = res.data.object
                console.log(res.data.object)
            })
            .catch((err) => console.log(err))
        }
    }
}
</script>

<style>

</style>