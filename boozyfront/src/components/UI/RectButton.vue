<template>
  <button v-bind:class="{isDangerous: dangerous, isSafe: safe, isPurple: purple, isRed: red}">
    <slot></slot>
  </button>
</template>

<script>
export default {
  name: "rect-button",
  props: {
    btnType: String
  },
  computed:{
    dangerous: function (){
      return this.btnType === 'dangerous'
    },
    purple: function (){
      return this.btnType === 'purple'
    },
    safe: function (){
      return this.btnType === 'safe'
    },
    red: function (){
      return this.btnType === 'red'
    },
  }

}
</script>

<style scoped>
button {
  display: inline-block;
  padding: 0.35em 1.2em;
  border: 0.1em solid #FFFFFF;
  border-radius: 0.12em;
  box-sizing: border-box;
  text-decoration: none;
  font-size: 20px;
  color: #FFFFFF;
  background-color: #292929;
  text-align: center;
  transition: all 0.2s;
}

button.isSafe:hover {
  color: #000000;
  background-color: #ffffff;
}

button.isDangerous:hover {
  color: #ffffff;
  background-color: #ab2a3d;
  border: 0.1em solid #ab2a3d;
}

button.isPurple {
  border: 0.1em solid skyblue;
  color: #FFFFFF;
  background-color: skyblue;
}

button.isPurple:hover {
  color: #3D5E6B;
  background-color: #ffffff;
  border: 0.1em solid #FFFFFF;
}

button.isRed {
  color: #FFFFFF;
  background-color: #ab2a3d;
  border: 0.1em solid #ab2a3d;
}

button.isRed:hover {
  color: #000000;
  background-color: #ffffff;
  border: 0.1em solid #FFFFFF;
}
</style>