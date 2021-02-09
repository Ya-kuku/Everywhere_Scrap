<template>
    <div class="topmg">
        <div class="news-body container">
            <div class="detail-body-title">News Content</div>
            <div class="headline-body">
                {{maincontent.title}}
                <div v-for="sum in maincontent.summary" :key="sum.id">
                    {{sum}}
                </div>
                {{maincontent.url}}
                {{maincontent.keyword}}
                <img :src='locate_img' alt="">
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
    props : {
        locate: {
            type: String,
            default:'',
        }
    },
    created() {
        this.getContents()
    },
    data() {
        return {
            tmp : '2021',
            index: '',
            // title:'',
            // url:'',
            // summary:'',
            // keyword: [],
            locate_img:Image,
            maincontent: []

        }
    },
    methods:{
        getContents() {
            // let cate = new Array()
            let cate = this.locate.split('/')
            this.tmp += cate[1] + cate[2]
            this.index =cate[3]
            axios.get(constants.SERVER_URL + '/news/'+cate[0]+'/contents', { params : { date:this.tmp, index:this.index } })
            .then((res) => {
                console.log(res)
                this.maincontent = res.data.object
                this.locate_img = require('@/assets'+ this.maincontent.locate.slice(1,))
                console.log(this.locate1)
        },
            )
    }
}
}
</script>

<style>

</style>