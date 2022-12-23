<template>
  <div class="dialog" v-if="show" @click="hideDialog">
    <div @click.stop class="content">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "DialogWindow",
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  methods:{
    hideDialog(){
      this.$emit('update:show', false)
    }
  }
}
</script>

<style scoped>
.dialog {
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.7);
  position: fixed;
  display: flex;
  align-items: center;
  justify-content: center;
}

.content{
  background-color: #3D5E6B;
}
</style>