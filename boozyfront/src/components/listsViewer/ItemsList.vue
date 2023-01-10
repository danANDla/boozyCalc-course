<template>
  <div class="list-container">
    <div :class="[this.isColor[index]===0 ? itemContainer : this.isColor[index]===2 ? whiteItemContainer : redItemContainer]" v-for="(item,index) in items"
         :ref="setItemRef"
         @click="showItem(item.id)">

      <div class="item-body">
        <div v-if="page==='products' && this.ingredients !== undefined">
          <div class="item-name">{{ item.name }}</div>
          <div class="item-info"> {{ item.ingredientName }}</div>
          <div class="item-info"> {{ item.price }}</div>
        </div>
        <div v-else>
          <div class="item-name">{{ item.name }}</div>
        </div>
      </div>

      <div class="item-navbar">
        <div class="nav-option" @mouseover="this.makeWhite(index)" @mouseleave="this.makeNotWhite(index)">
          <div class="nav-icon" @click="editItem(item.id)">
            <font-awesome-icon icon="fa-solid fa-pen" />
          </div>
        </div>
        <div class="nav-option" @mouseover="this.makeRed(index)" @mouseleave="this.makeNotRed(index)">
          <div class="nav-icon-trash" @click="deleteItem(item.id, item.name)">
            <font-awesome-icon icon="fas fa-trash"/>
          </div>
        </div>
      </div>
    </div>
    <div class="add-item-btn-container" @click="addItem">
      <font-awesome-icon icon="fas fa-plus"></font-awesome-icon>
    </div>

  </div>
</template>

<script>
export default {
  name: "ItemsList",
  props: {
    items: {
      type: Array,
      required: true
    },
    page: {
      type: String,
      required: false
    },
    ingredients: {
      type: Array,
      required: false
    },
  },
  data() {
    return {
      itemRefs: [],
      isColor: [],
      itemContainer: 'item-container',
      redItemContainer: 'red-item-container',
      whiteItemContainer: 'tan-item-container'
    }
  },
  methods: {
    setItemRef(el) {
      if (el) {
        this.itemRefs.push(el)
        this.isColor.push(0)
      }
    },
    addItem() {
      this.$emit('addItem')
    },
    deleteItem(id, name) {
      this.$emit('deleteItem', id, name)
    },
    editItem(id) {
      this.$emit('editItem', id)
    },
    showItem(id){
      this.$emit('showItem', id)
    },
    makeRed: function (index) {
      this.isColor[index] = 1
    },
    makeNotRed: function (index) {
      this.isColor[index] = 0
    },
    makeWhite: function (index) {
      this.isColor[index] = 2
    },
    makeNotWhite: function (index) {
      this.isColor[index] = 0
    }
  },
  beforeUpdate() {
    this.itemRefs = []
  },
}
</script>

<style scoped>
.list-container {
  padding: 10px;
  display: flex;
  width: 100%;
  flex-direction: column;
  gap: 10px;
}

::-webkit-scrollbar {
  width: 1px;
}

.item-container, .add-item-btn-container {
  background-color: #69AAB8;
  font-size: 20px;
  border: skyblue 1px solid;
  height: fit-content;
  display: flex;
  transition: all 0.2s;
}

.red-item-container {
  background-color: darkred;
  font-size: 20px;
  border: darkred 1px solid;
  height: fit-content;
  display: flex;
  transition: all 0.5s;
}

.tan-item-container {
  background-color: tan;
  font-size: 20px;
  border: tan 1px solid;
  height: fit-content;
  display: flex;
  transition: all 0.5s;
}

.add-item-btn-container {
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.2s;
  font-size: 30px;
}

.add-item-btn-container:hover {
  background-color: #CFE5EE;
  color: skyblue;
}

.item-navbar {
  padding: 10px 10px;
  width: fit-content;
  display: flex;
}

.nav-option {
  width: 20px;
  height: 20px;
  margin-left: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
}

.item-body{
  width: 100%;
}

.item-body  div{
  width: 100%;
  display: flex;
  flex-direction: row;
  overflow-x: hidden;
  overflow-y: scroll;
  align-items: center;
  height: 100%;
}

.item-name {
  font-size: x-large;
  font-weight: bold;
}
</style>
