<template>
  <div class="section-container">
    <div class="section-header" v-if="typeName!==''"> {{ typeName }}</div>
    <div class="section-body">
<!--      <items-grid :items="items" @addItem="addItem" @deleteItem="deleteItem"></items-grid>-->
      <items-list @addItem="addItem" @deleteItem="deleteItem" @editItem="editItem" @showItem="showItem"
                  :items="items"
                  :ingredients="ingredients"
                  :page="page" :user-group="this.userGroup"></items-list>
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
    userGroup: {
      type: Number,
      required: true
    }
  },
  methods: {
    addItem() {
      this.$emit('addItem')
    },
    deleteItem(id, name) {
      this.$emit('deleteItem', id, name)
    },
    editItem(id) {
      this.$emit('editItem', id)
    },
    showItem(id) {
      this.$emit('showItem', id)
    }
  }
}
</script>

<style scoped>
.section-container {
  background-color: #2A2F30;
  display: flex;
  flex-direction: column;
  padding: 15px;
}

.section-header {
  font-size: 30px;
  padding: 10px;
  background-color: #69AAB8;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  width: fit-content;
  user-select: none;
}


.section-body{
  background-color: #576163;
}
</style>