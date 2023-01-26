<template>
  <form @submit.prevent>
    <div class="form-container">
      <div class="error-container" v-if="isError">
        {{ errorText }}
      </div>
      <div>
        <my-input
            v-model="purchase.quantity"
            type="number"
            placeholder="quantity"
        />
      </div>
      <div>
        <drop-down v-bind:value="purchase.product_id"
                   v-on:change="purchase.product_id = parseInt($event.target.value)"
                   :itemList="this.products">
          choose product
        </drop-down>
      </div>
      <div class="btn-container" v-if="!isEdit">
        <rect-button btn-type="purple" @click="submitData"> add</rect-button>
      </div>
      <div class="btn-container" v-else>
        <rect-button btn-type="purple" @click="editData"> edit</rect-button>
      </div>
    </div>
  </form>
</template>

<script>
import RectButton from "@/components/UI/RectButton";
import MyInput from "@/components/UI/MyInput";
import ItemPicker from "@/components/UI/ItemPicker";
import DropDown from "../UI/DropDown";
import axios from "axios";


export default {
  name: "AddPurchaseForm",
  components: {DropDown, ItemPicker, RectButton, MyInput,},
  props: {
    isError: {
      type: Boolean,
      default: false
    },
    errorText: {
      type: String,
      default: ""
    },
    prevPurchase: {
      required: false
    }
  },
  data() {
    return {
      purchase: {
        product_id: undefined,
        quantity: undefined,
      },
      products: this.$store.state.items.products,
      isEdit: false,
    }
  },
  methods: {
    submitData() {
      this.$emit('submitData', {purchase: this.purchase, url: 'add'})
    },

    editData() {
      this.$emit('submitData', {purchase: this.purchase, url: 'edit'})
    },
    async fetchProducts() {
      try {
        var api_url =  "http://127.0.0.1:8080/api/"
        const response = await axios.get(api_url + 'products/all')
        console.log(response)
        this.$store.commit("items/updateProducts", response.data)
        this.products = this.$store.state.items.products
      } catch (e) {
        alert(e.message)
      }
    },
  },
  mounted() {
    this.fetchProducts()
    if (this.prevPurchase !== undefined) {
      console.log("is edit")
      this.purchase.product_id = this.prevPurchase.product_id;
      this.purchase.quantity = this.prevPurchase.quantity;
      console.log(this.purchase)
      this.isEdit = true;
    }
  }
}
</script>

<style scoped>
form {
  width: 100%;
  display: flex;
  background-color: #3D5E6B;
  justify-content: center;
}

.form-container {
  width: 100%;
  padding: 10px;
  display: flex;
  flex-direction: column;
}

.form-container > div {
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
</style>
