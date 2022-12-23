<template>
  <div class="container">
    <div class="question">
      <slot></slot>
    </div>
    <div class="btns">
      <rect-button btnType="purple" @click="yesIamSure">yes</rect-button>
      <rect-button btnType="purple" @click="noImNotSure">no</rect-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "AreYouSure",
  components: {},
  methods:{
    yesIamSure(){
      this.$emit('sure')
    },
    noImNotSure(){
      this.$emit('notsure')
    }
  }
}
</script>

<style scoped>

.container{
  display: flex;
  padding: 20px;
  flex-direction: column;
  align-items: center;
}

.question{
  padding-bottom: 20px;
}
.btns{
  width: 100%;
  display: flex;
  justify-content: space-around;
}
</style>