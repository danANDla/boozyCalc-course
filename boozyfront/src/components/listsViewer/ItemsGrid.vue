<template>
  <div class="grid-container">

    <div :class="[this.isRed[index]===0 ? itemContainer : redItemContainer]" v-for="(item,index) in items"
         :ref="setItemRef">
      <div class="item-navbar">
        <div class="nav-option">
          <div class="nav-icon">
            <font-awesome-icon icon="fa-solid fa-bars"/>
          </div>
        </div>
        <div class="nav-option" @mouseover="this.makeRed(index)" @mouseleave="this.makeNotRed(index)">
          <div class="nav-icon-trash" @click="deleteItem(item.id, item.name)">
            <font-awesome-icon icon="fas fa-trash"/>
          </div>
        </div>
      </div>
      <div class="item-body">
        <div class="item-name">{{ item.name }}</div>
        <div class="item-info">{{ item.description }}</div>
      </div>
    </div>
    <div class="add-item-btn-container" @click="addItem">
      <font-awesome-icon icon="fas fa-plus"></font-awesome-icon>
    </div>

  </div>
</template>

<script>
export default {
  name: "ItemsGrid",
  props: {
    items: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      itemRefs: [],
      isRed: [],
      itemContainer: 'item-container',
      redItemContainer: 'red-item-container'
    }
  },
  methods: {
    setItemRef(el) {
      if (el) {
        this.itemRefs.push(el)
        this.isRed.push(0)
      }
    },
    addItem() {
      this.$emit('addItem')
    },
    deleteItem(id, name) {
      this.$emit('deleteItem', id, name)
    },
    makeRed: function (index) {
      this.isRed[index] = 1
    },
    makeNotRed: function (index) {
      this.isRed[index] = 0
    }
  },
  beforeUpdate() {
    this.itemRefs = []
  },
}
</script>

<style scoped>
.grid-container {
  padding: 10px;
  display: grid;

  gap: 10px;
  grid-template-columns: repeat(auto-fill, 200px);
}

::-webkit-scrollbar {
  width: 1px;
}

.item-container, .add-item-btn-container {
  background-color: #69AAB8;
  font-size: 20px;
  border: skyblue 1px solid;
  height: 200px;
  display: grid;
  grid-template-rows: 40px 1fr;
  transition: all 0.2s;
}

.red-item-container {
  background-color: darkred;
  font-size: 20px;
  border: darkred 1px solid;
  height: 200px;
  display: grid;
  grid-template-rows: 40px 1fr;
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
  display: flex;
  justify-content: end;
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

.item-body {
  display: flex;
  flex-direction: column;
  align-items: start;
  overflow-x: hidden;
  overflow-y: scroll;
}

.item-body div {
  width: 100%;
}

.item-info {
  overflow-x: hidden;
  overflow-y: scroll;
}

.item-container {
}

.item-name {
  font-size: x-large;
  font-weight: bold;
}
</style>