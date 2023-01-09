<template>
  <div class="section-container">
    <div class="section-header" v-if="typeName!==''"> {{ typeName }}</div>
    <div class="section-body">
<!--      <items-grid :items="items" @addItem="addItem" @deleteItem="deleteItem"></items-grid>-->
      <items-list @addItem="addItem" @deleteItem="deleteItem"
                  :items="items"
                  :ingredients="ingredients"
                  :page="page"></items-list>
    </div>
  </div>
</template>

<script>
import ItemsList from "@/components/listsViewer/ItemsList";

export default {
  name: "TypedItemSection",
  components: {ItemsList},

  props: {
    typeName: {
      type: String,
      required: true
    },
    items: {
      type: Array,
      required: true
    },
    page:{
      type: String,
      required: false
    },
    ingredients: {
      type: Array,
      required: false
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