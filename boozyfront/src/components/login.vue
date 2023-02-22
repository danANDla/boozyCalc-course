<template>
  <div class="login">
    <div class="signin">
      <div class="login-text">
        <div class="wrappers">
          <div class="value-label">login</div>
          <div>
            <my-input v-model="login" type="text"/>
          </div>
        </div>
      </div>
      <div class="pass-text">
        <div class="wrappers">
          <div class="value-label">password</div>
          <div>
            <my-input v-model="pass" type="text"/>
          </div>
        </div>
      </div>
      <div class="error-msg">
        {{this.error}}
      </div>
      <div class="btn-block">
        <rect-button btn-type="purple" @click="signIn"> sign in </rect-button>
      </div>
    </div>

    <div class="signup">
      <div class="btn-block">
        <rect-button btn-type="purple" @click="signUp"> sign up </rect-button>
      </div>
    </div>
  </div>
</template>

<script>
import RectButton from "@/components/UI/RectButton";
export default {
  name: "login",
  components: {RectButton},
  props:{
    error: String
  },
  data(){
    return{
      login : "",
      pass : "",
      status: "",
      token: ""
    }
  },
  methods:{
    signIn : function(){
      if(this.pass !== "" && this.login !== "" && this.login !== undefined && this.pass !== undefined){
        this.$emit("logIn", this.login, this.pass);
      }
      else {
        if (this.pass === "" || this.pass === undefined) {
        }
        if(this.login === "" || this.login === undefined){
        }
      }
    },
    signUp: function (){
      this.$router.push({name: "Registration"})
    }
  }
}
</script>

<style scoped>
.login{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: fit-content;
}
.signin, .signup{
  width: fit-content;
  background: #202020;
  padding: 23px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.signin{
  margin-bottom: 10px;
}
.value-label{
  margin-bottom: 6px;
  font-weight: 500;
}
.wrappers > div{
  display: block;
}
.login-text{
  margin-bottom: 30px;
}
.btn-block{
  display: flex;
  flex-direction: column;
  width: 100%;
}
.signup{
  display: flex;
  flex-direction: column;
  align-items: center;
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

.error-msg{
  text-align: center;
  color: #c1364c;
  margin-top: 10px;
  height: 20px;
  margin-bottom: 10px;
}
</style>