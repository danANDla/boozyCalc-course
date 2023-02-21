<template>
  <div class="container">
    <div class="cocktail-name"> {{ this.cocktail.name }}</div>

    <div class="cocktail-info">
      <div class="info-block">
        <div class="info-header">recipe</div>
        <div class="info-body">
          <ul class="recipe">
            <li class="item-container" v-for="(item,index) in this.cocktail.ingredients">
              <span class="ingredient-name"> {{ this.ingredients.find(x => x.id == item.ingredientId).name }}</span>
              <span class="ingredient-quantity"> {{ item.amount }} </span>
            </li>
          </ul>
          <div v-if="party_id!==undefined" class="needed">
            <div class="item-container" v-for="(item, index) in this.cocktail.ingredients">
              <div class="needed-remain" v-if="!neededIngredientsLoading && needed.some(x => x.ingredient_id === item.ingredientId)">
                <div class="quantity-red"/>
                <div class="quantity">{{ needed.find(x => x.ingredient_id === item.ingredientId).remain }}</div>
              </div>
              <div v-else>
                <div class="quantity-green"/>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="info-block" v-if="this.cocktail.description !== null && this.cocktail.description !== ''">
        <div class="info-header">description</div>
        <div class="info-body">
          <div>
            {{ this.cocktail.description }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DialogWindow from "@/components/UI/DialogWindow";
import axios from "axios";

export default {
  name: "CocktailInfo",
  components: {DialogWindow},
  props: {
    cocktail: {
      required: true
    },
    party_id: {
      required: false
    }
  },
  data() {
    return {
      ingredients: this.$store.state.items.ingredients,
      api_url: "http://127.0.0.1:8080/api/",
      needed: undefined,
      neededIngredientsLoading: true
    }
  },
  methods: {
    async fetchIngredients() {
      try {
        const response = await axios.get(this.api_url + 'ingredients/all')
        this.$store.commit("items/updateIngredients", response.data)
        this.ingredients = this.$store.state.items.ingredients
      } catch (e) {
        alert(e.message)
      }
    },
    getNeededIngredients() {
      axios
          .get(this.api_url + 'parties/needed?p_id=' + this.party_id + '&c_id=' + this.cocktail.id)
          .then(response => {
            this.needed = response.data
            console.log(this.needed)
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => this.neededIngredientsLoading = false)
    }
  },
  mounted() {
    this.fetchIngredients()
    this.getNeededIngredients()
  }
}
</script>

<style scoped>

.container {
  background-color: #2A2F30;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 30vw;
  height: 50vh;
  max-width: 60vw;
}

.cocktail-name {
  height: fit-content;
  font-size: xx-large;
  padding: 5px;
  width: 100%;
  text-align: center;
  background-color: #69AAB8;
}

.cocktail-info {
  display: flex;
  flex-direction: row;
  width: 100%;
  height: 100%;
}

.info-block {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  background-color: #2A2F30;
  padding: 10px;
}

.info-header {
  text-align: center;
  height: fit-content;
}

.info-body {
  background-color: #576163;
  overflow-y: scroll;
  text-align: justify;
  padding: 5px;
  margin: 5px;
  border-radius: 10px;
  display: flex;
  flex-direction: row;
}

.needed{
  display: flex;
  flex-direction: column;
}
.needed div{
  flex-grow: 1;
}

ul.recipe {
  max-width: 40em;
  padding: 0;
  overflow-x: hidden;
  overflow-y: scroll;
  list-style: none;
}

ul.recipe li:before {
  float: left;
  width: 0;
  white-space: nowrap;
  content: ". . . . . . . . . . . . . . . . . . . . " ". . . . . . . . . . . . . . . . . . . . " ". . . . . . . . . . . . . . . . . . . . " ". . . . . . . . . . . . . . . . . . . . "
}

ul.recipe span:first-child {
  padding-right: 0.33em;
  background-color: #576163;
}

ul.recipe span + span {
  float: right;
  padding-left: 0.33em;
  background-color: #576163;
}

ul.recipe div{
  float: right;
}

.quantity-red {
  background-color: darkred;
  height: 20px;
  width: 5px;
  margin-right: 5px;
}

.quantity-green {
  background-color: limegreen;
  height: 20px;
  width: 5px;
}
ul.recipe{
  flex-grow: 1;
}
div.item-container{
  display: flex;
  align-items: center;
  justify-content: center;
}

.needed-remain{
  display: flex;
  flex-direction: row;
  align-items: end;
}

.quantity{
  font-size: 10px;
}

</style>