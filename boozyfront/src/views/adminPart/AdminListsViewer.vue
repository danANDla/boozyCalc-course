<template>
  <dialog-window v-model:show="ingrsDialogVisible">
    <div class="form-container">
      <add-ingredient-form @submitData="sendIngredient"
                           @input="this.ingredientsAddIsError = false; this.ingredientsAddErrorText=''"
                           :is-error="ingredientsAddIsError"
                           :error-text="ingredientsAddErrorText"
                           :prev-ingredient="prevIngredient">
      </add-ingredient-form>
    </div>
  </dialog-window>
  <dialog-window v-model:show="ingrsSureVisible">
    <div class="form-container">
      <are-you-sure @sure="sure('ingrs', ingrSureId)" @notsure="notsure('ingrs')"> Are you sure you want to delete
        {{ ingrSureName }}?
      </are-you-sure>
    </div>
  </dialog-window>

  <dialog-window v-model:show="cocksDialogVisible">
    <div class="form-container">
      <add-cocktail-form @submitData="sendCocktail"
                         @input="this.cocksAddIsError = false; this.cocksAddErrorText=''"
                         :is-error="cocksAddIsError"
                         :error-text="cocksAddErrorText"
                         :ingredients="ingredients"
                         :cocktail-types="cocktailTypes"
                         :prev-cocktail="currentCocktail">
      </add-cocktail-form>
    </div>
  </dialog-window>

  <dialog-window v-model:show="cocktailsInfoVisible">
    <div class="info-container">
      <cocktail-info :cocktail="currentCocktail" :ingredients="ingredients"></cocktail-info>
    </div>
  </dialog-window>

  <dialog-window v-model:show="cocksSureVisible">
    <div class="form-container">
      <are-you-sure @sure="sure('cocks', cocksSureId)" @notsure="notsure('cocks')"> Are you sure you want to delete
        {{ cocksSureName }}?
      </are-you-sure>
    </div>
  </dialog-window>

  <dialog-window v-model:show="productsDialogVisible">
    <div class="form-container">
      <add-product-form @submitData="sendProduct"
                        @input="this.productsAddIsError = false; this.productsAddErrorText=''"
                        :is-error="productsAddIsError"
                        :error-text="productsAddErrorText"
                        :ingredients="ingredients"
                        :prev-product="prevProduct">
      </add-product-form>
    </div>
  </dialog-window>
  <dialog-window v-model:show="productsSureVisible">
    <div class="form-container">
      <are-you-sure @sure="sure('products', productsSureId)" @notsure="notsure('products')"> Are you sure you want to delete
        {{ productsSureName }}?
      </are-you-sure>
    </div>
  </dialog-window>

  <div class="list-container">
    <div>
      <my-tabz
          :data="['Cocktails', 'Ingredients', 'Products']"
          main-color="#CFE5EE"
          @clickedTab="tabsHandler"
      />
    </div>
    <div v-if="page==='ingredients'">
      <typed-item-section v-bind:items="ingredients" type-name="" :user-group="1"
                          @addItem="showIngredientsDialog"
                          @deleteItem="showSureIngredient"
                          @editItem="showIngredientsEditDialog"></typed-item-section>
    </div>
    <div v-else-if="page==='cocktails'">
      <typed-item-section v-bind:items="cocktails" type-name="" :user-group="1"
                          @addItem="showCocktailsDialog"
                          @deleteItem="showSureCocktail"
                          @editItem="showCocktailsEditDialog"
                          @showItem="showCocktailsInfo"></typed-item-section>
    </div>
    <div v-else>
      <typed-item-section v-bind:items="products" type-name="" :user-group="1"
                          @addItem="showProductsDialog"
                          @deleteItem="showSureProduct"
                          @editItem="showProductsEditDialog"
                          v-bind:ingredients="ingredients"
                          v-bind:page="'products'"></typed-item-section>
    </div>
  </div>
</template>

<script>

import TypedItemSection from "@/components/listsViewer/TypedItemSection";
import Toggle from "@vueform/toggle"
import axios from 'axios';
import AddIngredientForm from "@/components/listsViewer/AddIngredientForm";
import RectButton from "@/components/UI/RectButton";
import DialogWindow from "@/components/UI/DialogWindow";
import AddCocktailForm from "@/components/listsViewer/AddCocktailForm";
import AddProductForm from "@/components/listsViewer/AddProductForm";
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
  components: {CocktailInfo, AddProductForm, AddCocktailForm, DialogWindow, RectButton, AddIngredientForm, TypedItemSection, Toggle},
  data() {
    return {
      ingredients: this.$store.state.items.ingredients,
      cocktails: this.$store.state.items.cocktails,
      products: this.$store.state.items.products,
      cocktailTypes: this.$store.state.items.cocktailTypes,
      api_url: "http://127.0.0.1:8080/api/",
      page: 'cocktails',

      prevProduct: undefined,
      prevIngredient: undefined,
      currentCocktail: undefined,

      ingrsDialogVisible: false,
      ingrsSureVisible: false,
      ingrSureName: String,
      ingrSureId: Number,
      ingredientsAddIsError: false,
      ingredientsAddErrorText: "",

      cocksSureVisible: false,
      cocksDialogVisible: false,
      cocktailsInfoVisible: false,
      cocksSureName: String,
      cocksSureId: Number,
      cocksAddIsError: false,
      cocksAddErrorText: "",

      productsSureVisible: false,
      productsDialogVisible: false,
      productsSureName: String,
      productsSureId: Number,
      productsAddIsError: false,
      productsAddErrorText: ""
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
    async fetchCocktailTypes() {
      try {
        const response = await axios.get(this.api_url + 'cocktails/allTypes')
        console.log(response)
        this.$store.commit("items/updateCocktailTypes", response.data)
        this.cocktailTypes = this.$store.state.items.cocktailTypes
      } catch (e) {
        alert(e.message)
      }
    },
    tabsHandler: function (r) {
      this.page = r.tab.toLowerCase()
    },
    sure: function (type, id) {
      if (type === 'ingrs') {
        this.deleteIngredient(id)
        this.ingrsSureVisible = false
        this.ingrSureId = -1
        this.ingrSureName = ""
      }
      else if(type === 'products'){
        this.deleteProduct(id)
        this.productsSureVisible = false
        this.productsSureId = -1
        this.productsSureName = ""
      }
      else {
        console.log(type + ' ' + id)
        this.deleteCocktail(id)
        this.cocksSureVisible = false
        this.cocksSureId = -1
        this.cockSureName = ""
      }
    },
    notsure: function (type) {
      if (type === 'ingrs') {
        this.ingrsSureVisible = false
        this.ingrSureId = -1
        this.ingrSureName = ""
      } else if (type === 'products') {
        this.productsSureVisible = false
        this.productsSureId = -1
        this.productsSureName = ""
      } else {
        this.cocksSureVisible = false
        this.cocksSureId = -1
        this.cockSureName = ""
      }
    },
    showIngredientsDialog(id) {
      this.prevIngredient = undefined
      this.ingredientsAddIsError = false
      this.ingredientsAddErrorText = ""
      this.ingrsDialogVisible = true
    },
    showIngredientsEditDialog(id) {
      this.prevIngredient = this.ingredients.find(x => x.id === id)
      this.ingredientsAddIsError = false
      this.ingredientsAddErrorText = ""
      this.ingrsDialogVisible = true
    },
    async sendIngredient(args) {
      let newIngredient = args.ingredient
      let status = false
      let errorText = ""
      let badNewItem = false
      if (newIngredient.name === "") {
        this.ingredientsAddIsError = true
        badNewItem = true
        this.ingredientsAddErrorText = "empty name field"
      }
      if(!badNewItem){
        await axios.post(this.api_url + 'ingredients/' + args.url, newIngredient)
            .then(function (response) {
              status = true;
              console.log(response.status.valueOf())
            })
            .catch(function (error) {
              if (error.response) {
                // Request made and server responded
                console.log(error.response.data);
                errorText = error.response.data
              } else if (error.request) {
                // The request was made but no response was received
                console.log(error.request);
              } else {
                // Something happened in setting up the request that triggered an Error
                console.log('Error', error.message);
              }
            })
        if (status === true) {
          await this.fetchIngredients()
          await this.fetchProducts()
          this.ingrsDialogVisible = false
        } else {
          console.log("is Error")
          this.ingrAddIsError = true
          this.ingrAddErrorText = errorText
        }
      }
    },
    showSureIngredient(id, name) {
      this.ingrSureId = id
      this.ingrSureName = name
      this.ingrsSureVisible = true
    },
    async deleteIngredient(id) {
      const response = await axios.delete(this.api_url + 'ingredients?id=' + id)
      console.log(response)
      await this.fetchIngredients()
      await this.fetchProducts()
    },
    showCocktailsDialog() {
      this.cocksAddIsError = false
      this.cocksAddErrorText = ""
      this.cocksDialogVisible = true
    },
    showCocktailsEditDialog(id) {
      this.currentCocktail = this.cocktails.find(x => x.id === id)
      console.log(this.currentCocktail)
      this.cocksAddIsError = false
      this.cocksAddErrorText = ""
      this.cocksDialogVisible = true
    },
    showSureCocktail(id, name) {
      this.cocksSureId = id
      this.cocksSureName = name
      this.cocksSureVisible = true
      console.log('trying delete ' + id)
    },
    showCocktailsInfo(id){
      this.cocktailsInfoVisible = true
      console.log('showCocktailsInfo', id)
      this.currentCocktail = this.cocktails.find(x => x.id === id)
    },
    async sendCocktail(args) {
      let newCocktail = args.cocktail
      let badNewItem = false
      if (newCocktail.ingredients.length === 0) {
        this.cocksAddIsError = true
        badNewItem = true
        this.cocksAddErrorText = "empty recipe"
      } else {
        for (let t in newCocktail.ingredients) {
          console.log(newCocktail.ingredients[t].ingredientId)
          if (newCocktail.ingredients[t].ingredientId === -1 || newCocktail.ingredients[t].amount <= 0) {
            this.cocksAddIsError = true
            badNewItem = true
            this.cocksAddErrorText = "bad ingredient pick"
          }
        }
      }
      if (newCocktail.name === "") {
        this.cocksAddIsError = true
        badNewItem = true
        this.cocksAddErrorText = "empty name field"
      }
      if (!badNewItem) {
        console.log(newCocktail)
        let status = false
        let errorText = ""
        await axios.post(this.api_url + 'cocktails/' + args.url, newCocktail)
            .then(function (response) {
              status = true;
              console.log(response.status.valueOf())
            })
            .catch(function (error) {
              if (error.response) {
                // Request made and server responded
                console.log(error.response.data);
                errorText = error.response.data
              } else if (error.request) {
                // The request was made but no response was received
                console.log(error.request);
              } else {
                // Something happened in setting up the request that triggered an Error
                console.log('Error', error.message);
              }
            })
        if (status === true) {
          await this.fetchCocktails()
          this.cocksDialogVisible = false
        } else {
          console.log("is Error")
          this.cocksAddIsError = true
          this.cocksAddErrorText = errorText
        }
      }
    },
    async deleteCocktail(id) {
      const response = await axios.delete(this.api_url + 'cocktails?id=' + id)
      console.log(response)
      await this.fetchCocktails()
    },
    showProductsDialog() {
      this.prevProduct = undefined
      this.productsAddIsError = false
      this.productsAddErrorText = ""
      this.productsDialogVisible = true
    },
    showProductsEditDialog(id) {
      this.prevProduct = this.products.find(x => x.id === id)
      this.productsAddIsError = false
      this.productsAddErrorText = ""
      this.productsDialogVisible = true
    },
    async sendProduct(args) {
      let newProduct = args.product;
      let status = false
      let errorText = ""
      let badNewItem = false
      if (newProduct.price <= 0 || newProduct.ingredientId === -1) {
        this.productsAddIsError = true
        badNewItem = true
        this.productsAddErrorText = "bad ingredient pick"
      }
      if (newProduct.name === "") {
        this.productsAddIsError = true
        badNewItem = true
        this.productsAddErrorText = "empty name field"
      }
      if(!badNewItem){
        await axios.post(this.api_url + 'products/' + args.url, newProduct)
            .then(function (response) {
              status = true;
              console.log(response.status.valueOf())
            })
            .catch(function (error) {
              if (error.response) {
                console.log(error.response.data);
                errorText = error.response.data
              } else if (error.request) {
                console.log(error.request);
              } else {
                console.log('Error', error.message);
              }
            })
        if (status === true) {
          await this.fetchProducts()
          this.productsDialogVisible = false
        } else {
          console.log("is Error")
          this.productsAddIsError = true
          this.productsAddErrorText = errorText
        }
      }
    },
    showSureProduct(id, name) {
      this.productsSureId = id
      this.productsSureName = name
      this.productsSureVisible = true
      console.log('trying delete ' + id)
    },
    async deleteProduct(id) {
      const response = await axios.delete(this.api_url + 'products?id=' + id)
      console.log(response)
      await this.fetchProducts()
    }
  },
  mounted() {
    console.log("Fetching")
    this.fetchIngredients()
    this.fetchCocktails()
    this.fetchProducts()
    this.fetchCocktailTypes()
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