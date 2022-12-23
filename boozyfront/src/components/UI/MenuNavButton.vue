<template>
  <button>
    <slot></slot>
  </button>
</template>

<script>
export default {
  name: "MenuNavButton"
}
</script>

<style scoped>

button {
  display: inline-block;
  width: 100%;
  padding: 0.35em 0.35em;
  border: 0.1em solid #3D5E6B;
  box-sizing: border-box;
  text-decoration: none;
  font-size: 20px;
  color: #FFFFFF;
  background-color: #3D5E6B;
  text-align: start;
  transition: all 0.2s;
}

button:hover {
  background-color: skyblue;
  border: 0.1em solid skyblue;
}

</style>