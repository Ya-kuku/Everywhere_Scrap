<template>
    <div>
        <img class="main-img" :src="main1" />
        <div class="container">
            <h4>경제</h4>
            <h4>사회</h4>
            <h4>IT/과학</h4>
        </div>
    </div>
</template>

<script>
import main1 from '../../assets/main1.jpg'
// import constants from '../../lib/constants';

export default {
    name: "main",
    data() {
        return {
            main1:main1,
        }
    }
}
</script>

<style>


</style>