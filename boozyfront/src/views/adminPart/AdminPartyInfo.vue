<template>

  <div class="body-container">

    <div class="party-info">
      <div> {{ this.party.name }}</div>
      <div> location: {{ this.party.location }}</div>
      <div> event time: {{ formatDate(this.party.date) }}</div>
    </div>

    <div class="party-items">
      <div>INVITES</div>
    </div>

    <dialog-window v-model:show="cocktailInfoVisible">
      <div class="info-container">
        <cocktail-info :cocktail="currentCocktail"></cocktail-info>
      </div>
    </dialog-window>

    <div class="party-items">
      <div>MENU</div>
      <div class="list-container">
        <items-list :items="party.menu"
                    :page="'menu'"
                    :user-group="1"
                    @showItem="showCocktailsInfo"/>
      </div>
    </div>


    <dialog-window v-model:show="purchaseDialogVisible">
      <div class="form-container">
        <add-purchase-form @submitData="sendPurchase"
                        @input="this.purchaseAddIsError = false; this.purchaseAddErrorText=''"
                        :is-error="purchaseAddIsError"
                        :error-text="purchaseAddErrorText"
                        :prev-purchase="currentPurchase">
        </add-purchase-form>
      </div>
    </dialog-window>

    <dialog-window v-model:show="purchaseSureVisible">
      <div class="form-container">
        <are-you-sure @sure="sure('parties', purchaseSureId)" @notsure="notsure('parties')"> Are you sure you want to delete
          {{ purchaseSureName }}?
        </are-you-sure>
      </div>
    </dialog-window>

    <div class="party-items">
      <div>STOCKS</div>
      <div class="list-container">
        <items-list :items="purchases"
                    :page="'purchases'"
                    :user-group="1"
                    @addItem="showPurchaseDialog"
                    @editItem="showPurchaseEditDialog"
                    @deleteItem="showSureEdit"/>

      </div>
    </div>
  </div>


</template>

<script>
import axios from "axios";
import ItemsList from "../../components/listsViewer/ItemsList";
import DialogWindow from "../../components/UI/DialogWindow";
import AreYouSure from "../../components/UI/AreYouSure";
import AddPartyForm from "../../components/partiesViewer/AddPartyForm";
import AddPurchaseForm from "../../components/partiesViewer/AddPurchaseForm";
import CocktailInfo from "../../components/listsViewer/CocktailInfo";

export default {
  name: "AdminPartyInfo",
  components: {CocktailInfo, AddPurchaseForm, ItemsList, DialogWindow, AddPartyForm, AreYouSure},
  data() {
    return {
      cocktails: this.$store.state.items.cocktails,
      parties: this.$store.state.items.parties,
      purchases: this.$store.state.items.pusrchases,
      api_url: "http://127.0.0.1:8080/api/",
      party: {
        menu: []
      },

      currentCocktail: undefined,
      cocktailInfoVisible: false,

      currentPurchase: undefined,
      purchaseSureVisible: false,
      purchaseDialogVisible: false,
      purchaseInfoVisible: false,
      purchaseSureName: String,
      purchaseSureId: Number,
      purchaseAddIsError: false,
      purchaseAddErrorText: "",
    }
  },
  methods: {
    showCocktailsInfo(id){
      this.cocktailInfoVisible = true
      this.currentCocktail = this.cocktails.find(x => x.id === id)
    },
    showPurchaseDialog() {
      this.currentPurchase = undefined
      this.purchaseAddIsError = false
      this.purchaseAddErrorText = ""
      this.purchaseDialogVisible = true
    },
    showPurchaseEditDialog(id) {
      this.currentPurchase = this.purchases.find(x => x.product_id === id)
      console.log('current', id, this.currentPurchase)
      this.purchaseAddIsError = false
      this.purchaseAddErrorText = ""
      this.purchaseDialogVisible = true
    },
    async sendPurchase(args) {
      console.log(args)
      let newPurchase = args.purchase
      let badNewItem = false
      if (newPurchase.product_id === undefined) {
        this.purchaseAddIsError = true
        badNewItem = true
        this.purchaseAddErrorText = "product not selected"
      }
      if (newPurchase.quantity === "" || newPurchase.quantity === undefined) {
        this.purchaseAddIsError = true
        badNewItem = true
        this.purchaseAddErrorText = "empty quantity field"
      }
      else if (newPurchase.quantity < 0 ) {
        this.purchaseAddIsError = true
        badNewItem = true
        this.purchaseAddErrorText = "quantity value should be above zero"
      }
      if (!badNewItem) {
        let status = false
        let errorText = ""
        newPurchase.party_id = parseInt(this.$route.params.id)
        await axios.post(this.api_url + 'purchases/' + args.url, newPurchase)
            .then(function (response) {
              status = true;
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
          await this.fetchPurchases()
          this.purchaseDialogVisible = false
        } else {
          console.log("is Error")
          this.purchaseAddIsError = true
          if (typeof errorText !== 'string') errorText = 'server error'
          this.purchaseAddErrorText = errorText.substring(0, Math.min(errorText.length, 200))
        }
      }
    },
    async deletePurchase(id) {
      const response = await axios.delete(this.api_url + 'purchases?id=' + id)
      await this.fetchParties()
    },
    showSurePurchase(id, name) {
      this.purchaseSureId = id
      this.purchaseSureName = name
      this.purchaseSureVisible = true
    },
    sure: function (type, id) {
      this.deletePurchase(id)
      this.purchaseSureVisible = false
      this.partSureId = -1
      this.purchaseSureName = ""
    },
    notsure: function (type) {
      this.purchaseSureVisible = false
      this.purchaseSureId = -1
      this.purchaseSureName = ""
    },
    async fetchCocktails() {
      try {
        const response = await axios.get(this.api_url + 'cocktails/all')
        this.$store.commit("items/updateCocktails", response.data)
        this.cocktails = this.$store.state.items.cocktails
      } catch (e) {
        alert(e.message)
      }
    },
    async fetchParties() {
      try {
        const response = await axios.get(this.api_url + 'parties/all')
        this.$store.commit("items/updateParties", response.data)
        this.parties = this.$store.state.items.parties
        this.party = this.parties.find(x => x.id === parseInt(this.$route.params.id))
      } catch (e) {
        alert(e.message)
      }
    },
    async fetchPurchases() {
      try {
        const response = await axios.get(this.api_url + 'purchases/party?id=' + this.$route.params.id)
        // const response = await axios.get(this.api_url + 'purchases/party?id=' + 1)
        // this.$store.commit("items/updatePurchases", response.data)
        // this.purchases = this.$store.state.items.purchases
        this.purchases = response.data
        console.log(this.purchases)
      } catch (e) {
        alert(e.message)
      }
    },
    formatDate(date) {
      var day = new Date(Date.parse(date)).getDate()
      var month = new Date(Date.parse(date)).getMonth() + 1
      var hour = new Date(Date.parse(date)).getHours()
      var minute = new Date(Date.parse(date)).getMinutes()
      if (day < 10) day = '0' + day
      if (month < 10) month = '0' + month
      if (hour < 10) hour = '0' + hour
      if (minute < 10) minute = '0' + minute
      return day + "." + month + " " + hour + ":" + minute
    },
  },
  mounted() {
    console.log("fetching")
    this.fetchCocktails()
    this.fetchParties()
    this.fetchPurchases()
  }
}
</script>

<style scoped>
.body-container {
  display: flex;
  flex-direction: column;
  background-color: white;
}

.body-container > div {
  width: 100%;
  background-color: black;
  /*border: skyblue 1px solid;*/
}

.item-container, .add-item-btn-container {
  font-size: 20px;
  height: fit-content;
  display: flex;
  transition: all 0.2s;
  padding: 10px;
}

.item-container:hover {
  background-color: white;
  color: black;
}

.red-item-container {
  background-color: darkred;
  font-size: 20px;
  height: fit-content;
  display: flex;
  transition: all 0.5s;
  padding: 10px;
}

.tan-item-container {
  background-color: tan;
  font-size: 20px;
  height: fit-content;
  display: flex;
  transition: all 0.5s;
  padding: 10px;
}

.add-item-btn-container {
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.2s;
  font-size: 30px;
}

.add-item-btn-container:hover {
  background-color: #CFE5EE;
  color: skyblue;
}

.item-navbar {
  padding: 10px 10px;
  width: fit-content;
  display: flex;
}

.nav-option {
  width: 20px;
  height: 20px;
  margin-left: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
}

.item-body {
  width: 100%;
  user-select: none;
}

.item-body div {
  width: 100%;
  display: flex;
  flex-direction: row;
  overflow-x: hidden;
  overflow-y: scroll;
  align-items: center;
  height: 100%;
}

.party-items {
  margin-top: 25px;
}

.party-items > *:first-child {
  padding: 10px;
  background-color: #69AAB8;
}
</style>
