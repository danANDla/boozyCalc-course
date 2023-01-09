<template>
  <form @submit.prevent>
    <div class="container">
      <div class="error-container" v-if="isError">
        {{ errorText }}
      </div>
      <div>
        <my-input
            v-model="product.name"
            type="text"
            placeholder="product name"
        />
      </div>
      <div>
        <my-input
            v-model="product.description"
            type="text"
            placeholder="description"
        />
      </div>
      <div>
        <drop-down v-bind:value="this.product.ingredientId"
                   v-on:change="this.product.ingredientId = parseInt($event.target.value)"
                   :itemList="ingredients">
          choose ingredient
        </drop-down>
      </div>
      <div class="pricer">
        <my-input
            v-model="this.price"
            type="number"
            placeholder="rub"
            v-on:change="updatePrice()"
        />

        <my-input
            v-model="this.amount"
            type="number"
            placeholder="ml"
            v-on:change="updatePrice()"
        />

        <!--        {{this.product.price}}-->
        <my-input
            v-model="this.product.price"
            type="number"
            placeholder="rub/ml"
            disabled
        />
      </div>
      <div class="btn-container">
        <rect-button btn-type="purple" @click="submitData"> add</rect-button>
      </div>
    </div>
  </form>
</template>

<script>
import DropDown from "@/components/UI/DropDown";
import MyInput from "@/components/UI/MyInput";
import RectButton from "@/components/UI/RectButton";

export default {
  name: "AddIngredientForm",
  components: {RectButton, MyInput, DropDown},
  props: {
    isError: {
      type: Boolean,
      default: false
    },
    errorText: {
      type: String,
      default: ""
    },
    ingredients: {
      type: Array,
      default: ""
    },
  },
  data() {
    return {
      product: {
        name: '',
        description: '',
        price: undefined,
        ingredientId: -1
      },
      amount: undefined,
      price: undefined
    }
  },
  methods: {
    submitData() {
      this.$emit('submitData', this.product)
    },
    updatePrice() {
      if (this.price <= 0) this.price = 0;
      if (this.amount <= 0) this.amount = 0;
      if (this.amount !== 0) this.product.price = Math.round(1000 * this.price / this.amount) / 1000;
    }
  },
}
</script>

<style scoped>
form {
  width: 100%;
  padding: 10px;
  display: flex;
  background-color: #3D5E6B;
  justify-content: center;
}

.container {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.container div {
  padding: 10px;
}

.btn-container {
  display: flex;
  justify-content: end;
  align-items: center;
}

.error-container {
  text-align: center;
  font-size: 15px;
  color: #d4aa70;
}

.pricer {
  display: flex;
  gap: 5px;
}

input:disabled {
  background-color: white;
}
</style>
