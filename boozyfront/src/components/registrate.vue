<template>
  <div class="registrate">
    <div class="signup">
      <div class="login-text">
        <div class="wrappers">
          <div class="value-label"> nickname <span> (tg, if exists) </span></div>
          <div>
            <my-input v-model="user.login" type="text"/>
          </div>
        </div>
      </div>
      <div class="login-text">
        <div class="wrappers">
          <div class="value-label"> real name</div>
          <div>
            <my-input v-model="user.realName" type="text"/>
          </div>
        </div>
      </div>
      <div class="pass-text">
        <div class="wrappers">
          <div class="value-label"> password</div>
          <div>
            <my-input v-model="user.pass" type="text"/>
          </div>
        </div>
      </div>
      <div class="error-msg">
        {{ this.error }}
      </div>
      <div class="btn-block">
        <rect-button btn-type="purple" @click="signUp"> create an account</rect-button>
      </div>
      <div>
        <pulse-loader :loading="this.loading"/>
      </div>
    </div>
  </div>
</template>

<script>
import RectButton from "@/components/UI/RectButton";

export default {
  name: "registrate",
  components: {RectButton},
  props: {
    error: String
  },
  data() {
    return {
      user: {
        login: "",
        realName: "",
        pass: "",
      },
      surname: "",
      message: "",
      loading: false,
      successful: false,
      error: ""


    }
  },
  computed:{
    loggedIn(){
      return this.$store.state.auth.status.loggedIn;
    }
  },
  mounted() {
    if (this.loggedIn){
      this.$router.push("/items")
    }
  },
  methods: {
    signUp: function () {
      if (this.user.pass !== "" && this.user.login !== "" && this.user.realName !== "" &&
          this.user.login !== undefined && this.user.pass !== undefined && this.user.realName !== undefined) {

        this.message = ""
        this.loading = true
        this.successful = false
        this.$store.dispatch("auth/register", this.user).then(
            (data) => {
              this.message = data.message
              this.successful = true
              this.loading = false
            },
            (error => {
              this.successful = false
              this.loading = false
              this.error = (error.response.data.message)
              let message = (error.response && error.response.data && error.response.data.message) ||
                  error.message || error.toString();
              this.error = error.response.data
              console.log(message)
            })
        )
      } else {
      }
    }
  }
}
</script>

<style scoped>
.registrate {
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  position: fixed;
  display: flex;
  align-items: center;
  justify-content: center;
}

.signup {
  width: fit-content;
  background: #202020;
  padding: 23px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.value-label {
  margin-bottom: 6px;
  font-weight: 500;
}

.wrappers > div {
  display: block;
}

.login-text {
  margin-bottom: 30px;
}

.btn-block {
  display: flex;
  flex-direction: column;
  width: 100%;
}

input {
  font-size: 16px;
  font-family: inherit;
  padding: 0.25em 0.5em;
  background-color: #fff;
  border: 4px solid #ffffff;
  border-radius: 4px;
  transition: 180ms box-shadow ease-in-out;
}

.error-msg {
  color: #c1364c;
  margin-top: 10px;
  height: 20px;
  margin-bottom: 10px;
}

.value-label span {
  color: #576163;
}
</style>