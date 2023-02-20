<template>
  <div class="list-container">
    <div
        :class="[this.isColor[index]===0 ? itemContainer : this.isColor[index]===2 ? whiteItemContainer : redItemContainer]"
        v-for="(item,index) in items"
        :ref="setItemRef">

      <div class="cocktail-container" v-if="page==='cocktails'" @click="showItem(item.id)">
        <div class="item-name"> {{ item.name }}</div>
        <div v-show="!ingredientsLoading" class="item-recipe"> {{ getIngredientsString(item.ingredients) }}</div>
      </div>

      <div class="menu-container" v-else-if="page==='menu'" @click="showItem(item)">
        <div class="cocktail-container">
          <div class="item-name"> {{ cocktails.find(x => x.id === item).name }}</div>
          <div class="item-recipe">
            {{ getIngredientsString(cocktails.find(x => x.id === item).ingredients) }}
          </div>
        </div>

        <div v-if="this.availableCocktails.find(x => x.cocktail_id === item).quantity > 0"
             class="quantity">
          <div>
            {{ this.availableCocktails.find(x => x.cocktail_id === item).quantity }}
          </div>
        </div>
        <div v-if="this.availableCocktails.find(x => x.cocktail_id === item).quantity > 0"
             class="quantity-green"/>
        <div v-else class="quantity-red"/>
      </div>

      <div class="menu-container" v-else-if="page==='orderMenu'" @click="showItem(item)">
        <div class="cocktail-container">
          <div class="item-name"> {{ this.cocktails.find(x => x.id === item.cocktail_id).name }}</div>
          <div class="item-recipe">
            {{ getIngredientsString(this.cocktails.find(x => x.id === item.cocktail_id).ingredients) }}
          </div>
        </div>

        <div class="quantity">
          <div>
            {{ item.quantity }}
          </div>
        </div>
      </div>

      <div class="invites-container" v-else-if="page==='invites'">
        <div class="item-name" @click="showItem(item.person_id)">{{
            users.find(x => x.id === item.person_id).name
          }}
        </div>
        <div class="item-info"  @click="showItem(item.person_id)">
          {{ getGroupedOrderString(item.person_id) }}
        </div>
        <div class="add-item-btn-container" @click="addOrder(item.person_id)"
             v-if="userGroup===1">
          <font-awesome-icon icon="fas fa-plus"></font-awesome-icon>
        </div>
      </div>

      <div class="products-container" v-else-if="page==='products' && this.ingredients !== undefined"
           @click="showItem(item.id)">
        <div class="item-name">{{ item.name }}</div>
        <div class="item-info"> {{ item.ingredientName }}</div>
        <div class="item-info"> {{ item.price }}</div>
      </div>

      <div class="parties-container" v-else-if="page==='parties'" @click="showItem(item.id)">
        <div class="item-name">{{ item.name }}</div>
        <div class="item-info"> {{ formatDate(item.date) }}</div>
        <div class="item-info"> {{ item.location }}</div>
      </div>

      <div class="purchases-container" v-else-if="page==='purchases'" @click="showItem(item.id)">
        <div class="item-name">{{ products.find(x => x.id === item.product_id).name }}</div>
        <div class="item-info">{{
            products.find(x => x.id === item.product_id).ingredientName
          }}
        </div>
        <div  class="item-info">{{ products.find(x => x.id === item.product_id).price }}</div>
        <div class="item-name"> {{ item.quantity }}</div>
      </div>

      <div class="products-container" v-else @click="showItem(item.id)">
        <div class="item-name">{{ item.name }}</div>
      </div>

      <div class="item-navbar" v-if="userGroup===1 && page!=='menu' && page !=='orderMenu' && page!=='invites'">
        <div class="nav-option" @mouseover="this.makeWhite(index)" @mouseleave="this.makeNotWhite(index)">
          <div v-if="page==='purchases'" class="nav-icon" @click="editItem(item.product_id)">
            <font-awesome-icon icon="fa-solid fa-pen"/>
          </div>
          <div v-else class="nav-icon" @click="editItem(item.id)">
            <font-awesome-icon icon="fa-solid fa-pen"/>
          </div>
        </div>
        <div class="nav-option" @mouseover="this.makeRed(index)" @mouseleave="this.makeNotRed(index)">
          <div v-if="page==='purchases'" class="nav-icon"
               @click="deleteItem(item.product_id, products.find(x => x.id === item.product_id).name)">
            <font-awesome-icon icon="fas fa-trash"/>
          </div>
          <div v-else class="nav-icon-trash" @click="deleteItem(item.id, item.name)">
            <font-awesome-icon icon="fas fa-trash"/>
          </div>
        </div>
      </div>
    </div>
    <div class="add-item-btn-container" @click="addItem"
         v-if="userGroup===1 && page!=='invites' && page!=='menu' && page !=='ingredients' && page !=='products' && page !== 'orderMenu'">
      <font-awesome-icon icon="fas fa-plus"></font-awesome-icon>
    </div>

  </div>
</template>

<script>
import DialogWindow from "@/components/UI/DialogWindow";
import axios from "axios";

export default {
  name: "ItemsList",
  components: {DialogWindow},
  props: {
    items: {
      type: Array,
      required: true
    },
    page: {
      type: String,
      required: false
    },
    party_id: {
      type: Number,
      required: false
    },
    userGroup: {
      type: Number,
      required: true
    },
    cocktails: {
      type: Array,
      required: false
    },
    availableCocktails: {
      type: Array,
      required: false
    },
    ingredients: {
      type: Array,
      required: false
    },
    products: {
      type: Array,
      required: false
    },
    users: {
      type: Array,
      required: false
    },
    groupedOrders: {
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
      whiteItemContainer: 'tan-item-container',
      api_url: "http://127.0.0.1:8080/api/",
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
    addOrder(id) {
      this.$emit('addOrder', id)
    },
    deleteItem(id, name) {
      this.$emit('deleteItem', id, name)
    },
    editItem(id) {
      this.$emit('editItem', id)
    },
    showItem(id) {
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
    },
    getIngredientsString(arr) {
      let retStr = ""
      for (var i = 0; i < arr.length - 1; i++) {
        retStr += this.ingredients.find(x => x.id === arr[i].ingredientId).name
        retStr += ", "
      }
      retStr += this.ingredients.find(x => x.id === arr[arr.length - 1].ingredientId).name
      return retStr
    },
    formatDate(date) {
      var day = new Date(Date.parse(date)).getDate()
      var month = new Date(Date.parse(date)).getMonth() + 1
      var year = new Date(Date.parse(date)).getFullYear()
      if (day < 10) {
        day = '0' + day
      }

      var monthStr = ["янв", "февр", "мар", "апр", "май", "июнь", "июль", "авг", "сент", "окт", "нояб", "дек"]

      if (month < 10) {
        month = '0' + month
      }

      return day + " " + monthStr[month - 1] + " " + year
    },
    getGroupedOrderString(person_id) {
      let groupedOrder = this.groupedOrders.find(x => x.person_id === person_id)
      if (groupedOrder.items.length === 0) return ""
      let retStr = ""
      let orderItem = {
        name: undefined,
        count: undefined,
        price: undefined
      }
      for (var i = 0; i < (groupedOrder.items).length - 1; i++) {
        orderItem = groupedOrder.items[i]
        retStr += orderItem.name
        retStr += " x "
        retStr += orderItem.count
        retStr += ", "
      }
      orderItem = groupedOrder.items[groupedOrder.items.length - 1]
      retStr += orderItem.name
      retStr += " x "
      retStr += orderItem.count
      return retStr
    }
  },
  beforeUpdate() {
    this.itemRefs = []
  },
}
</script>

<style scoped>
.list-container {
  display: flex;
  width: 100%;
  flex-direction: column;
  gap: 10px;
}

::-webkit-scrollbar {
  width: 1px;
}

.item-container, .add-item-btn-container {
  font-size: 20px;
  height: fit-content;
  display: flex;
  transition: all 0.2s;
  padding-left: 10px;
}

.item-container:hover {
  background-color: white;
  color: black;
}

.red-item-container {
  background-color: darkred;
  font-size: 20px;
  height: fit-content;
  display: flex;
  transition: all 0.5s;
  padding-left: 10px;
}

.tan-item-container {
  background-color: tan;
  font-size: 20px;
  height: fit-content;
  display: flex;
  transition: all 0.5s;
  padding-left: 10px;
}

.add-item-btn-container {
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.2s;
  font-size: 30px;
  padding: 10px;
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

.products-container, .parties-container, .purchases-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  overflow-x: hidden;
  overflow-y: scroll;
  align-items: center;
  user-select: none;
}

.products-container div, .parties-container div, .purchases-container div {
  width: 100%;
  height: 100%;
  margin: 10px;
}

.cocktail-container {
  display: flex;
  flex-direction: column;
  width: 100%;
  cursor: pointer;
}

.menu-container {
  display: flex;
  flex-direction: row;
  width: 100%;
  margin: 10px;
  cursor: pointer;
  align-items: center;
}

.quantity {
  height: 100%;
  padding-right: 10px;
  align-items: center;
  display: flex;
}

.quantity-green {
  background-color: limegreen;
  height: 100%;
  width: 10px;
}

.quantity-red {
  background-color: darkred;
  height: 100%;
  width: 10px;
}

.item-recipe {
  display: flex;
  width: 50%;
  padding: 5px 0px 0px 5px;
  font-weight: lighter;
}

.item-name {
  font-size: x-large;
  font-weight: normal;
}

.invites-container {
  display: flex;
  width: 100%;
  user-select: none;
  margin: 10px;
  align-items: center;
}

.invites-container .item-name {
  flex-grow: 1;
}

.invites-container .item-info {
  flex-grow: 1;
}
</style>
