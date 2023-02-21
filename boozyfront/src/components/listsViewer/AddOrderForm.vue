<template>
  <form @submit.prevent>
    <div class="container">
      <div class="error-container" v-if="isError">
        {{errorText}}
      </div>

      <div class="order-info">
        New order for {{ this.person.name }}
      </div>
      <div class="order-items">
        <div v-if="this.availableCocktailsLoading || this.cocktailsLoading || this.ingredientsLoading">
          <pulse-loader :loading="true"></pulse-loader>
        </div>
        <div v-else class="list-container">
          <items-list :items="this.availableCocktails.filter(x => x.quantity > 0)"
                      :ingredients="this.ingredients"
                      :cocktails="this.cocktails"
                      :page="'orderMenu'"
                      :party_id="this.party_id"
                      :user-group="1"
                      @selectOrder="selectCocktail"/>
        </div>
      </div>
      <div v-if="this.order.cocktail_id!==undefined" class="btn-container" @click="sendOrder()">
        order
      </div>
      <div v-else class="blocked-btn-container">
        order
      </div>
    </div>
  </form>
</template>

<script>
import MyInput from "@/components/UI/MyInput";
import RectButton from "@/components/UI/RectButton";
import ItemsList from "@/components/listsViewer/ItemsList";
import axios from "axios";
export default {
  name: "AddOrderForm",
  components: {ItemsList, RectButton, MyInput},
  props:{
    isError:{
      type: Boolean,
      default: false
    },
    errorText:{
      type: String,
      default: ""
    },
    menu: {
      type: Array,
      required: true
    },
    prevOrder:{
      required: false
    },
    cocktailsLoading: {
      type: Boolean,
      required: true
    },
    party_id: {
      required: true
    },
    person: {
      required: true
    },
  },
  data(){
    return{
      api_url: "http://127.0.0.1:8080/api/",
      order:{
        party_id: undefined,
        person_id: undefined,
        cocktail_id: undefined
      },
      isEdit: false,
      availableCocktailsLoading: true,
      ingredientsLoading: true,
      cocktailsLoading: true,
      availableCocktails: undefined,
      cocktails: this.$store.state.items.cocktails,
      ingredients: this.$store.state.items.ingredients
    }
  },
  methods:{
    selectCocktail(item){
      console.log('cocktail ' + item.cocktail_id);
      this.order.cocktail_id = item.cocktail_id
    },
    sendOrder(){
      this.order.person_id = this.person.id
      this.order.party_id = this.party_id
      console.log('cocktail ' + this.order.cocktail_id + ' selected by ' + this.order.person_id + ' person');
      this.$emit('submitData', {order: this.order, url: "add"})
    },
    getAvailableCocktails() {
      axios
          .get(this.api_url + 'parties/available?id=' + this.party_id)
          .then(response => {
            this.availableCocktails = response.data
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => this.availableCocktailsLoading = false)
    },
    fetchIngredients() {
      axios
          .get(this.api_url + 'ingredients/all')
          .then(response => {
            this.$store.commit("items/updateIngredients", response.data)
            this.ingredients = this.$store.state.items.ingredients
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => this.ingredientsLoading = false)
    },
    fetchCocktails() {
      axios
          .get(this.api_url + 'cocktails/all')
          .then(response => {
            this.$store.commit("items/updateCocktails", response.data)
            this.cocktails = this.$store.state.items.cocktails
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => this.cocktailsLoading = false)
    }
  },
  mounted() {
    this.getAvailableCocktails();
    this.fetchCocktails();
    this.fetchIngredients();
    if (this.prevOrder !== undefined) {
      this.order.cocktail_id = this.prevOrder.cocktail_id;
      this.order.party_id = this.prevOrder.party_id;
      this.order.person_id = this.prevOrder.person_id;
      this.isEdit = true;
    }
  }
}
</script>

<style scoped>
form{
  width: 100%;
  display: flex;
  justify-content: center;
}
.container{
  width: 100%;
  height: 50vh;
  display:flex;
  flex-direction: column;
}
.container div{
}

.btn-container {
  display: flex;
  background-color: #292929;
  justify-content: center;
  align-items: center;
  transition: all 0.2s;
  font-size: 30px;
  padding: 10px;
  user-select: none;
}

.blocked-btn-container {
  display: flex;
  background-color: #1c1c1c;
  color: #292929;
  justify-content: center;
  align-items: center;
  transition: all 0.2s;
  font-size: 30px;
  padding: 10px;
  user-select: none;
}

.btn-container:hover {
  background-color: #CFE5EE;
  color: skyblue;
}

.error-container{
  text-align: center;
  font-size: 15px;
  color: #d4aa70;
}

.list-container{
  background-color: #576163;
}

.order-info{
  background-color: #69AAB8;
  padding: 10px;
}

.order-items{
  overflow-y: scroll;
  overflow: overlay;
  background-color: #576163;
}

::-webkit-scrollbar {
  width: 4px;
}

::-webkit-scrollbar-track {
  border-radius: 0;
}

::-webkit-scrollbar-thumb {
  background-color: #d4aa70;
  border-radius: 0;
}
</style>
