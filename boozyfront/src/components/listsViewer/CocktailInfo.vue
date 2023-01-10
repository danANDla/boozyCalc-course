<template>
  <div class="container">
    <div class="cocktail-name"> {{this.cocktail.name}}</div>

    <div class="cocktail-info">
      <div class="info-block">
        <div class="info-header">recipe</div>
        <div class="info-body">
          <ul class="recipe">
            <li class="item-container" v-for="(item,index) in this.cocktail.ingredients">
              <span class="ingredient-name"> {{this.ingredients.find(x => x.id == item.ingredientId).name}}</span>
              <span class="ingredient-quantity"> {{item.amount}} </span>
            </li>
          </ul>
        </div>
      </div>

      <div class="info-block" v-if="this.cocktail.description !== null">
        <div class="info-header">description</div>
        <div class="info-body">
          <div>
            {{this.cocktail.description}}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DialogWindow from "@/components/UI/DialogWindow";
export default {
  name: "CocktailInfo",
  components: {DialogWindow},
  props: {
    cocktail: {
      required: true
    },
    ingredients:{
      type: Array,
      required: true
    }
  },
  data() {
    return {
    }
  },
  mounted() {
    console.log(this.cocktail)
  }
}
</script>

<style scoped>

.container{
  background-color: #2A2F30;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 40vw;
  height: 50vh;
  max-width: 60vw;
}

.cocktail-name{
  height: fit-content;
  font-size: xx-large;
  padding: 5px;
  width: 100%;
  text-align: center;
  background-color: #69AAB8;
}

.cocktail-info{
  display: flex;
  flex-direction: row;
  width: 100%;
  height: 100%;
}

.info-block{
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  background-color: #2A2F30;
  padding: 10px;
}

.info-header{
  text-align: center;
  height: fit-content;
}

.info-body{
  background-color: #576163;
  overflow-y: scroll;
  text-align: justify;
  padding: 5px;
  margin: 5px;
  border-radius: 10px;
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
  content:
      ". . . . . . . . . . . . . . . . . . . . "
      ". . . . . . . . . . . . . . . . . . . . "
      ". . . . . . . . . . . . . . . . . . . . "
      ". . . . . . . . . . . . . . . . . . . . "
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
</style>