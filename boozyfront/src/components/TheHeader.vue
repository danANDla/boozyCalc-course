<template>
  <div class="container">
    <router-link  to ="/" class="logo">
      BoozyCalc
    </router-link>

    <ul class="nav">
      <li>
        <router-link to="/"> welcome </router-link>
      </li>
      <li>
        <router-link to="/"> log in </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "BoozyHeader",
  components: {}

}
</script>

<style scoped>

.container{
  height: 100%;
  padding: 0 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: skyblue;
}

.logo{
  font-size: x-large;
  font-weight: bold;
}

.container ul{
  list-style: none;
  display: flex;
}

.container ul li{
  margin-left: 25px;
}

.container ul li a:hover{
  color: #CFE5EE;
}

.container ul li a{
  font-weight: bold;
  color: #2A2F30;
}

.container a{
  color: #2A2F30;
  text-decoration: none;
}
</style>