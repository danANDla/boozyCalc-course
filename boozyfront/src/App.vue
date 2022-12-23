<template>
  <div class="app">
    <router-view> </router-view>
  </div>
</template>

<script>

export default {
  methods:{
  }
}
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600&display=swap");

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

html, body {
  background: #2A2F30;
  font-family: "Montserrat", sans-serif;
  font-weight: 300;
  color: white;
  font-size: 20px;
}
</style>