<template>
  <div class="container">
    <router-link  to ="/" class="logo">
      BoozyCalc
    </router-link>

    <ul class="nav">
      <li>
        <router-link v-if="!this.loggedIn"  to="/start"> log in </router-link>
        <div v-if="this.loggedIn" class="logout" @click="logout"> log out </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "BoozyHeader",
  components: {},
  computed:{
    loggedIn(){
      return this.$store.state.auth.status.loggedIn;
    }
  },
  methods:{
    logout() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/start');
    },
  }
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

.container ul li a,div:hover{
  color: #CFE5EE;
}

.container ul li a,div{
  font-weight: bold;
  color: #2A2F30;
  cursor: pointer;
}

.container a{
  color: #2A2F30;
  text-decoration: none;
}

</style>