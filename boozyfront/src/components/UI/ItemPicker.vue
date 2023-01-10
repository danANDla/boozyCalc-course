<template>
  <div class="picker-container">
    <div :class="[this.isRed[index]===0 ? itemContainer : redItemContainer]" v-for="(item,index) in pickedItems"
         :ref="setItemRef">
      <div>
        <drop-down v-bind:value="pickedItems[index].ingredientId"
                   v-on:change="pickedItems[index].ingredientId = parseInt($event.target.value)"
                   :itemList="itemList">
          choose ingredient
        </drop-down>
      </div>
      <div>
        <my-input
            v-model="pickedItems[index].amount"
            type="number"
            placeholder="ml/gr"
        />
      </div>
      <div class="item-navbar">
        <div class="nav-option" @mouseover="this.makeRed(index)" @mouseleave="this.makeNotRed(index)">
          <div class="nav-icon-trash" @click="deleteItem(index)">
            <font-awesome-icon icon="fas fa-minus"/>
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
import DropDown from "@/components/UI/DropDown";
export default {
  name: "ItemPicker",
  components: {DropDown},

  props: {
    itemList: {
      type: Array,
      required: true
    },
    pickedItems:{
      type: Array,
      default: () => []
    }
  },
  data(){
    return{
      itemRefs: [],
      isRed: [],
      itemContainer: 'item-container',
      redItemContainer: 'red-item-container',
    }
  },
  methods:{
    setItemRef(el) {
      if (el) {
        this.itemRefs.push(el)
        this.isRed.push(0)
      }
    },
    deleteItem(index) {
      this.pickedItems.splice(index, 1)
      this.isRed[index] = 0
    },
    makeRed: function (index) {
      this.isRed[index] = 1
    },
    makeNotRed: function (index) {
      this.isRed[index] = 0
    },
    addItem:function (){
      this.pickedItems.push({ingredientId: -1, amount: ""})
    }
  }
}
</script>

<style scoped>
.picker-container{
  width: 100%;
}
.item-container {
  background-color: #3D5E6B;
  display: grid;
  grid-template-columns: 6fr 3fr 1fr;
  transition: all 0.2s;
  align-items: center;
}

.red-item-container {
  background-color: darkred;
  display: grid;
  grid-template-columns: 6fr 3fr 1fr;
  transition: all 0.2s;
  align-items: center;
}

.item-container div, .red-item-container div{
  padding: 5px 3px;
}

.add-item-btn-container {
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.2s;
  font-size: 30px;
  background-color: black;
  padding: 5px 0;
}

.add-item-btn-container:hover {
  background-color: #CFE5EE;
  color: skyblue;
}

.item-navbar{
  display: flex;
  justify-content: end;
}

.nav-option{
  padding: 15px;
}
</style>