<template>
  <div class="container">
    <router-link  to ="/" class="logo">
      BoozyCalc
    </router-link>

    <ul class="nav">
      <li>
        <div v-if="this.loggedIn" class="name"> {{ userName }} </div>
      </li>
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
    },
    userName(){
      let user = this.$store.getters['auth/getUser']
      console.log(user)
      return user.realName
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

.container ul li a,.logout:hover{
  color: #CFE5EE;
}

.container ul li a,.logout,.name{
  font-weight: bold;
  color: #2A2F30;
  cursor: pointer;
}

.name{
  cursor: inherit;
  user-select: none;
}

.container a{
  color: #2A2F30;
  text-decoration: none;
}

</style>