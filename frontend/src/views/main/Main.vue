<template>
    <div>
        <img class="main-img" :src="main1" />
        <div class="container" style="margin-top:30rem;">
            <button @click="goNews(1)" class="main-btn">ECONOMY</button>
            <button @click="goNews(2)" class="main-btn">SOCIAL</button>
            <button @click="goNews(3)" class="main-btn">IT/SCIENCE</button>
        </div>
    </div>
</template>

<script>
import '../../style/main.css'
import main1 from '../../assets/main1.jpg'
import constants from '../../lib/constants';

export default {
    name: "Main",
    data() {
        return {
            main1:main1,
        }
    },
    methods: {
        goNews(cate) {
            this.$router.push({
                name: constants.URL_TYPE.NEWS.NEWSDETAIL,
                params: {cate:cate},
            })
        }
    }
}
</script>

<style>


</style>