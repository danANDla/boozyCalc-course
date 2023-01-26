<template>
  <div class="list-container">
    <div
        :class="[this.isColor[index]===0 ? itemContainer : this.isColor[index]===2 ? whiteItemContainer : redItemContainer]"
        v-for="(item,index) in items"
        :ref="setItemRef">

      <div v-if="page==='cocktails'" class="cocktail-container" @click="showItem(item.id)">
        <div class="item-name"> {{ item.name }}</div>
        <div class="item-recipe"> {{ getIngredientsString(item.ingredients) }}</div>
      </div>
      <div v-else-if="page==='menu'" class="cocktail-container" @click="showItem(item)">
        <div class="item-name">{{ cocktails.find(x => x.id === item).name }}</div>
        <div class="item-recipe"> {{ getIngredientsString(cocktails.find(x => x.id === item).ingredients) }}</div>
      </div>
      <div v-else class="item-body" @click="showItem(item.id)">
        <div v-if="page==='products' && this.ingredients !== undefined">
          <div class="item-name">{{ item.name }}</div>
          <div class="item-info"> {{ item.ingredientName }}</div>
          <div class="item-info"> {{ item.price }}</div>
        </div>
        <div v-else-if="page==='parties'">
          <div class="item-name">{{ item.name }}</div>
          <div class="item-info"> {{ formatDate(item.date) }}</div>
          <div class="item-info"> {{ item.location }}</div>
        </div>
        <div v-else-if="page==='purchases'">
          <div class="item-name">{{ products.find(x => x.id === item.product_id).name }}</div>
          <div class="item-info">{{ products.find(x => x.id === item.product_id).ingredientName }}</div>
          <div class="item-info">{{ products.find(x => x.id === item.product_id).price }}</div>
          <div class="item-name"> {{ item.quantity }} </div>
        </div>
        <div v-else>
          <div class="item-name">{{ item.name }}</div>
        </div>
      </div>

      <div class="item-navbar" v-if="userGroup===1 && page!=='menu'">
        <div class="nav-option" @mouseover="this.makeWhite(index)" @mouseleave="this.makeNotWhite(index)">
          <div v-if="page==='purchases'" class="nav-icon" @click="editItem(item.product_id)">
            <font-awesome-icon icon="fa-solid fa-pen"/>
          </div>
          <div v-else class="nav-icon" @click="editItem(item.id)">
            <font-awesome-icon icon="fa-solid fa-pen"/>
          </div>
        </div>
        <div class="nav-option" @mouseover="this.makeRed(index)" @mouseleave="this.makeNotRed(index)">
          <div v-if="page==='purchases'" class="nav-icon" @click="deleteItem(item.product_id, products.find(x => x.id === item.product_id).name)">
            <font-awesome-icon icon="fas fa-trash"/>
          </div>
          <div v-else class="nav-icon-trash" @click="deleteItem(item.id, item.name)">
            <font-awesome-icon icon="fas fa-trash"/>
          </div>
        </div>
      </div>
    </div>
    <div class="add-item-btn-container" @click="addItem" v-if="userGroup===1">
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
    userGroup: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      itemRefs: [],
      isColor: [],
      itemContainer: 'item-container',
      redItemContainer: 'red-item-container',
      whiteItemContainer: 'tan-item-container',
      ingredients: this.$store.state.items.ingredients,
      cocktails: this.$store.state.items.cocktails,
      products: this.$store.state.items.products,
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
      console.log(retStr)
      return retStr
    },
    formatDate(date) {
      var day = new Date(Date.parse(date)).getDate()
      var month = new Date(Date.parse(date)).getMonth() + 1
      if (day < 10) {
        day = '0' + day
      }
      if (month < 10) {
        month = '0' + month
      }
      return day + "." + month
    },
    async fetchCocktails() {
      try {
        const response = await axios.get(this.api_url + 'cocktails/all')
        this.$store.commit("items/updateCocktails", response.data)
        this.cocktails = this.$store.state.items.cocktails
      } catch (e) {
        alert(e.message)
      }
    },
    async fetchIngredients() {
      try {
        const response = await axios.get(this.api_url + 'ingredients/all')
        console.log(response)
        this.$store.commit("items/updateIngredients", response.data)
        this.ingredients = this.$store.state.items.ingredients
      } catch (e) {
        alert(e.message)
      }
    },
    async fetchProducts() {
      try {
        const response = await axios.get(this.api_url + 'products/all')
        console.log(response)
        this.$store.commit("items/updateProducts", response.data)
        this.products = this.$store.state.items.products
      } catch (e) {
        alert(e.message)
      }
    },
  },
  mounted() {
    this.fetchCocktails()
    this.fetchIngredients()
    this.fetchProducts()
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

.item-body {
  width: 100%;
  user-select: none;
}

.item-body div {
  width: 100%;
  display: flex;
  flex-direction: row;
  overflow-x: hidden;
  overflow-y: scroll;
  align-items: center;
  height: 100%;
}

.cocktail-container {
  display: flex;
  flex-direction: column;
  width: 100%;
  padding: 10px 10px 10px 10px;
  cursor: pointer;
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
</style>
