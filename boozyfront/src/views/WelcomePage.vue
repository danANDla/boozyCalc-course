<template>
  <div>
    <main>
      <div class="content-wrapper">
        <div class="container">
          This App will help you to configure your upcoming party.
        </div>
        <login @logIn="signIn" v-model:error="error"></login>
      </div>
    </main>

    <footer>

    </footer>
  </div>
</template>

<script>
import login from "@/components/login";

async function sendReq(url, params){
  url = "http://127.0.0.1:8080/web4-1.0/api/login" + url;
  return await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    },
    body: JSON.stringify(params)
  })
}
import Login from "@/components/login";
export default {
  name: "WelcomePage",
  components: {Login},
  data(){
    return{
      token: "",
      error: "",
    }
  },
  methods: {
    signIn: function (Login, Pass){
      console.log("Login: " + login + ", Password: " + Pass)
      this.$store.dispatch("auth/login", {username: Login, password: Pass}).then(
          () => {
            if(this.$store.state.auth.user.roles.includes('ROLE_ADMIN')){
              this.$router.push("/admin/lists")
            } else{
              this.$router.push("/items")
            }
          },
          (error => {
            this.error = (error.response.data.message)
            let message = (error.response && error.response.data && error.response.data.message) ||
                error.message || error.toString();
            console.log(message)
          })
      )
    },
  },
}

</script>

<style scoped>
main{
  padding: 10px;
}
.container{
  padding: 50px;
}
.content-wrapper{
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
}

</style>