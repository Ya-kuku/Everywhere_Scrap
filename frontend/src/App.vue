<template>
  <div id="app">
    <Header />
    <router-view />
  </div>
</template>

<script>
import './style/style.js'
import Header from './components/Header'

export default {
    name: 'App',
    components: {
      Header,
    },
}
</script>

<style>
</style>
