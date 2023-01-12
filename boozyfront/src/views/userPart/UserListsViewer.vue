<template>
  <dialog-window v-model:show="cocktailsInfoVisible">
    <div class="info-container">
      <cocktail-info :cocktail="currentCocktail" :ingredients="ingredients"></cocktail-info>
    </div>
  </dialog-window>

  <div class="list-container">
    <div>
      <my-tabz
          :data="['Cocktails']"
          main-color="#CFE5EE"
          @clickedTab="tabsHandler"
      />
    </div>
    <div>
      <typed-item-section v-bind:items="cocktails" type-name=""
                          :user-group="0"
                          @showItem="showCocktailsInfo"></typed-item-section>
    </div>
  </div>
</template>

<script>

import TypedItemSection from "@/components/listsViewer/TypedItemSection";
import Toggle from "@vueform/toggle"
import axios from 'axios';
import RectButton from "@/components/UI/RectButton";
import DialogWindow from "@/components/UI/DialogWindow";
import AddCocktailForm from "@/components/listsViewer/AddCocktailForm";
import CocktailInfo from "@/components/listsViewer/CocktailInfo";

async function sendReq(url, reqMethod, params) {
  url = "http://127.0.0.1:8080/api/" + url;
  if (reqMethod === "GET") {
    return await fetch(url, {
      method: reqMethod,
    })
  } else {
    return await fetch(url, {
      method: reqMethod,
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
      },
      body: JSON.stringify(params)
    })
  }
}

export default {
  name: "listsViewer",
  components: {CocktailInfo, AddCocktailForm, DialogWindow, RectButton, TypedItemSection, Toggle},
  data() {
    return {
      ingredients: this.$store.state.items.ingredients,
      cocktails: this.$store.state.items.cocktails,
      products: this.$store.state.items.products,
      api_url: "http://127.0.0.1:8080/api/",
      page: 'cocktails',

      prevProduct: undefined,
      prevIngredient: undefined,
      currentCocktail: undefined,

      cocktailsInfoVisible: false,
    }
  },
  methods: {
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
    async fetchCocktails() {
      try {
        const response = await axios.get(this.api_url + 'cocktails/all')
        console.log(response)
        this.$store.commit("items/updateCocktails", response.data)
        this.cocktails = this.$store.state.items.cocktails
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
    tabsHandler: function (r) {
      this.page = r.tab.toLowerCase()
    },
    showCocktailsInfo(id){
      this.cocktailsInfoVisible = true
      console.log('showCocktailsInfo', id)
      this.currentCocktail = this.cocktails.find(x => x.id === id)
    },
  },
  mounted() {
    console.log("Fetching")
    this.fetchIngredients()
    this.fetchCocktails()
    this.fetchProducts()
  }
}
</script>

<style src="@vueform/toggle/themes/default.css"></style>
<style scoped>
.form-container {
  width: 350px;
}

.list-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.list-container div {
  width: 100%;
}
</style>
