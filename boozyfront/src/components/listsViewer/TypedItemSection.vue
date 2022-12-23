<template>
  <div class="section-container">
    <div class="section-header" v-if="typeName!==''"> {{ this.typeName }}</div>
    <div class="section-body">
      <items-grid :items="items" @addItem="addItem" @deleteItem="deleteItem"></items-grid>
    </div>
  </div>
</template>

<script>
import ItemsGrid from "@/components/listsViewer/ItemsGrid";

export default {
  name: "TypedItemSection",
  components: {ItemsGrid},

  props: {
    typeName: {
      type: String,
      required: true
    },
    items: {
      type: Array,
      required: true
    },
  },
  methods: {
    addItem() {
      this.$emit('addItem')
    },
    deleteItem(id, name) {
      this.$emit('deleteItem', id, name)
    }
  }
}
</script>

<style scoped>
.section-container {
  background-color: #576163;
  display: flex;
  flex-direction: column;
}

.section-header {
  font-size: 30px;
  padding: 10px;
}

.section-body {
  padding: 10px;
}

</style>