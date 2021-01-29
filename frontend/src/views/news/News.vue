<template>
  <div class="topmg container">
    <h3>{{cate}}</h3>
  </div>
</template>

<script>
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

    }
  },
  methods: {

  }
}
</script>

<style>

</style>