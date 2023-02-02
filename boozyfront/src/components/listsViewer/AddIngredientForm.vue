<template>
  <form @submit.prevent>
    <div class="container">
      <div class="error-container" v-if="isError">
        {{errorText}}
      </div>
      <div>
        <my-input
            v-model="ingredient.name"
            type="text"
            placeholder="item name"
        />
      </div>
      <div>
        <my-input
            v-model="ingredient.description"
            type="text"
            placeholder="description"
        />
      </div>
      <div>
        <drop-down v-bind:value="this.ingredient.type"
                   v-on:change="this.ingredient.type = parseInt($event.target.value)"
                   :itemList="types">
          choose type of ingredient
        </drop-down>
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
import MyInput from "@/components/UI/MyInput";
import RectButton from "@/components/UI/RectButton";
export default {
  name: "AddIngredientForm",
  components: {RectButton, MyInput},
  props:{
    isError:{
      type: Boolean,
      default: false
    },
    errorText:{
      type: String,
      default: ""
    },
    prevIngredient:{
      required: false
    }
  },
  data(){
    return{
      ingredient:{
        name: '',
        description: '',
        id: undefined,
        type: undefined
      },
      isEdit: false,
      types: this.$store.state.items.ingredientTypes
    }
  },
  methods:{
    submitData(){
      this.$emit('submitData', {ingredient: this.ingredient, url: "add"})
    },
    editData() {
      this.$emit('submitData', {ingredient: this.ingredient, url: "edit"})
    },
  },
  mounted() {
    if (this.prevIngredient !== undefined) {
      this.ingredient.name = this.prevIngredient.name;
      this.ingredient.description = this.prevIngredient.description;
      this.ingredient.id = this.prevIngredient.id;
      this.ingredient.type = this.prevIngredient.type;
      this.isEdit = true;
    }
  }
}
</script>

<style scoped>
form{
  width: 100%;
  padding: 10px;
  display: flex;
  background-color: #3D5E6B;
  justify-content: center;
}
.container{
  width: 100%;
  display:flex;
  flex-direction: column;
}
.container div{
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
</style>