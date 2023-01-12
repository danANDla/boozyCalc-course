<template>
  <form @submit.prevent>
    <div class="form-container">
      <div class="error-container" v-if="isError">
        {{errorText}}
      </div>
      <div>
        <my-input
            v-model="cocktail.name"
            type="text"
            placeholder="cocktail name"
        />
      </div>
      <div>
        <my-input
            v-model="cocktail.description"
            type="text"
            placeholder="description"
        />
      </div>
      <div>
        <drop-down v-bind:value="this.cocktail.type_id"
                   v-on:change="this.cocktail.type_id = parseInt($event.target.value)"
                   :itemList="cocktailTypes">
          choose type of cocktail
        </drop-down>
      </div>
      <div class="recipe">
        <div>
          recipe
        </div>
        <div>
          <item-picker :item-list="ingredients" v-model:pickedItems="this.cocktail.ingredients"> </item-picker>
        </div>
      </div>
      <div class="btn-container" v-if="!isEdit">
        <rect-button btn-type="purple" @click="submitData"> add </rect-button>
      </div>
      <div class="btn-container" v-else>
        <rect-button btn-type="purple" @click="editData"> edit </rect-button>
      </div>
    </div>
  </form>
</template>

<script>
import RectButton from "@/components/UI/RectButton";
import MyInput from "@/components/UI/MyInput";
import ItemPicker from "@/components/UI/ItemPicker";

export default {
  name: "AddCocktailForm",
  components: {ItemPicker, RectButton, MyInput},
  props:{
    isError:{
      type: Boolean,
      default: false
    },
    errorText:{
      type: String,
      default: ""
    },
    ingredients:{
      type: Array,
      default: ""
    },
    cocktailTypes:{
      type: Array,
      required: true
    },
    prevCocktail:{
      required: false
    }
  },
  data(){
    return{
      cocktail:{
        name: '',
        description: '',
        ingredients: [],
        id: undefined,
        type_id: undefined
      },
      isEdit: false
    }
  },
  methods:{
    submitData(){
      this.$emit('submitData', { cocktail: this.cocktail, url: 'add'})
    },

    editData(){
      this.$emit('submitData', { cocktail: this.cocktail, url: 'edit'})
    },
  },
  mounted(){
    if(this.prevCocktail !== undefined){
      this.cocktail.name = this.prevCocktail.name;
      this.cocktail.description = this.prevCocktail.description;
      this.cocktail.id = this.prevCocktail.id;
      this.cocktail.ingredients = this.prevCocktail.ingredients;
      this.cocktail.type_id = this.prevCocktail.type_id;
      this.isEdit = true;
    }
  }
}
</script>

<style scoped>
form{
  width: 100%;
  display: flex;
  background-color: #3D5E6B;
  justify-content: center;
}
.form-container{
  width: 100%;
  padding: 10px;
  display:flex;
  flex-direction: column;
}
.form-container > div{
  padding: 10px;
}

.btn-container{
  display: flex;
  justify-content: end;
  align-items: center;
}

.error-container{
  text-align: center;
  font-size: 15px;
  color: #d4aa70;
}

.recipe{
  display: flex;
  flex-direction: column;
}
</style>
