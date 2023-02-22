<template>
   <router-view/>
</template>

<script>
export default {
  name: "Main.vue",
  computed:{
    loggedIn(){
      return this.$store.state.auth.status.loggedIn;
    }
  },
  mounted() {
    if (!this.loggedIn){
      this.$router.push("/start")
    }
  },
}
</script>

<style scoped>

</style>