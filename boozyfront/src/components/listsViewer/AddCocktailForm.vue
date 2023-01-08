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
      <div class="recipe">
        <div>
          recipe
        </div>
        <div>
          <item-picker :item-list="ingredients" v-model:pickedItems="this.cocktail.ingredients"> </item-picker>
        </div>
      </div>
      <div class="btn-container">
        <rect-button btn-type="purple" @click="submitData"> add </rect-button>
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
    }
  },
  data(){
    return{
      cocktail:{
        name: '',
        description: '',
        ingredients: []
      }
    }
  },
  methods:{
    submitData(){
      this.$emit('submitData', this.cocktail)
    },

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
