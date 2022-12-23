<template>
  <select @change="changeItem">
    <option disabled value=""><slot></slot></option>
    <option
        v-for="item in itemList"
        :key="item.id"
        :value="item.id">
      {{item.name}}
    </option>
  </select>
</template>

<script>
export default {
  name: "DropDown",
  props: {
    modelValue:{
      type: Number,
    },
    itemList: {
      type: Array,
      default: () => []
    }
  },
  methods:{
    changeItem(event){
      this.$emit('update:modelValue', parseInt(event.target.value))
    }
  }
}
</script>

<style scoped>
select {
  width: 100%;
  border: 1px solid skyblue;
  padding: 10px 15px;
}
</style>