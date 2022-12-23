<template>
  <dialog-window v-model:show="ingrsDialogVisible">
    <div class="form-container">
      <add-ingredient-form @submitData="sendIngredient"
                           @input="this.ingrAddIsError = false; this.ingrAddErrorText=''"
                           :is-error="ingrAddIsError"
                           :error-text="ingrAddErrorText">
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
                         :ingredients="ingredients">
      </add-cocktail-form>
    </div>
  </dialog-window>
  <dialog-window v-model:show="cocksSureVisible">
    <div class="form-container">
      <are-you-sure @sure="sure('cocks', cocksSureId)" @notsure="notsure('cocks')"> Are you sure you want to delete
        {{ cocksSureName }}?
      </are-you-sure>
    </div>
  </dialog-window>

  <div class="list-container">
    <div>
      <my-tabz
          :data="['Cocktails', 'Ingredients']"
          main-color="#CFE5EE"
          @clickedTab="tabsHandler"
      />
    </div>
    <div v-if="page==='ingredients'">
      <typed-item-section v-bind:items="ingredients" type-name="" @addItem="showIngredientsDialog"
                          @deleteItem="showSureIngredient"></typed-item-section>
    </div>
    <div v-else>
      <typed-item-section v-bind:items="cocktails" type-name="" @addItem="showCocktailsDialog"
                          @deleteItem="showSureCocktail"></typed-item-section>
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
  components: {AddCocktailForm, DialogWindow, RectButton, AddIngredientForm, TypedItemSection, Toggle},
  data() {
    return {
      ingredients: this.$store.state.items.ingredients,
      cocktails: this.$store.state.items.cocktails,
      api_url: "http://127.0.0.1:8080/api/",
      page: 0,
      ingrsDialogVisible: false,
      ingrsSureVisible: false,
      ingrSureName: String,
      ingrSureId: Number,
      ingrAddIsError: false,
      ingrAddErrorText: "",

      cocksSureVisible: false,
      cocksDialogVisible: false,
      cocksSureName: String,
      cocksSureId: Number,
      cocksAddIsError: false,
      cocksAddErrorText: ""
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
    showIngredientsDialog(id) {
      this.ingrAddIsError = false
      this.ingrAddErrorText = ""
      this.ingrsDialogVisible = true
    },
    tabsHandler: function (r) {
      this.page = r.tab.toLowerCase()
    },
    async sendIngredient(newIngredient) {
      let status = false
      let errorText = ""
      await axios.post(this.api_url + 'ingredients/add', newIngredient)
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
        this.ingrsDialogVisible = false
      } else {
        console.log("is Error")
        this.ingrAddIsError = true
        this.ingrAddErrorText = errorText
      }
    },
    showSureIngredient(id, name) {
      this.ingrSureId = id
      this.ingrSureName = name
      this.ingrsSureVisible = true
    },
    sure: function (type, id) {
      if (type === 'ingrs') {
        this.deleteIngredient(id)
        this.ingrsSureVisible = false
        this.ingrSureId = -1
        this.ingrSureName = ""
      } else {
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
      } else {
        this.cocksSureVisible = false
        this.cocksSureId = -1
        this.cockSureName = ""
      }
    },
    async deleteIngredient(id) {
      const response = await axios.delete(this.api_url + 'ingredients?id=' + id)
      console.log(response)
      await this.fetchIngredients()
    },
    showCocktailsDialog() {
      this.cocksAddIsError = false
      this.cocksAddErrorText = ""
      this.cocksDialogVisible = true
    },
    async sendCocktail(newCocktail) {
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
        await axios.post(this.api_url + 'cocktails/add', newCocktail)
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
    showSureCocktail(id, name) {
      this.cocksSureId = id
      this.cocksSureName = name
      this.cocksSureVisible = true
      console.log('trying delete ' + id)
    },
    async deleteCocktail(id) {
      const response = await axios.delete(this.api_url + 'cocktails?id=' + id)
      console.log(response)
      await this.fetchCocktails()
    },
  },
  mounted() {
    console.log("Fetching")
    this.fetchIngredients()
    this.fetchCocktails()
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