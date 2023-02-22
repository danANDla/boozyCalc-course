<template>

  <div class="body-container">

    <div class="party-info">
      <div> {{ this.party.name }}</div>
      <div> location: {{ this.party.location }}</div>
      <div> event time: {{ formatDate(this.party.date) }}</div>
    </div>

    <div class="party-items">
      <div>YOUR ORDER</div>
      <div v-if="invitesLoading || usersLoading || groupedOrdersLoading">
        <pulse-loader :loading="true"></pulse-loader>
      </div>
      <div v-else class="order-container">
        <div class="order-item" v-for="(item, index) in this.personalOrder.items">
          {{item.name}}
          x
          {{item.count}}
          <div class="price"> = {{Math.ceil(item.price)}}</div>
        </div>
        <div class="total">
          <div>
            total sum
          </div>
          {{Math.ceil(this.personalOrder.total)}}
        </div>
      </div>
    </div>

    <div class="party-items">
      <div>INVITES</div>
      <div v-if="invitesLoading || usersLoading || groupedOrdersLoading">
        <pulse-loader :loading="true"></pulse-loader>
      </div>
      <div v-else class="list-container">
        <items-list :items="invites"
                    :users="users"
                    :grouped-orders="groupedOrders"
                    :page="'invites'"
                    :user-group="0"
                    :party_id="this.$route.params.id"
                    @showItem="showUser"
                    @addOrder="addOrder"/>
      </div>
    </div>

    <dialog-window v-model:show="cocktailInfoVisible">
      <div class="info-container">
        <cocktail-info :cocktail="currentCocktail" :party_id="this.$route.params.id"></cocktail-info>
      </div>
    </dialog-window>

    <div class="party-items">
      <div>MENU</div>
      <div v-if="cocktailsLoading || availableCocktailsLoading || ingredientsLoading || partiesLoading">
        <pulse-loader :loading="true"></pulse-loader>
      </div>
      <div v-else class="list-container">
        <items-list :items="party.menu"
                    :available-cocktails="availableCocktails"
                    :cocktails="cocktails"
                    :ingredients="ingredients"
                    :page="'menu'"
                    :party_id="this.$route.params.id"
                    :user-group="0"
                    @showItem="showCocktailsInfo"/>
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
import AddOrderForm from "@/components/listsViewer/AddOrderForm";

export default {
  name: "UserPartyInfo",
  components: {AddOrderForm, CocktailInfo, AddPurchaseForm, ItemsList, DialogWindow, AddPartyForm, AreYouSure},
  data() {
    return {
      api_url: "http://127.0.0.1:8080/api/",
      party: {
        menu: []
      },
      cocktails: this.$store.state.items.cocktails,
      cocktailsLoading: true,
      currentCocktail: undefined,
      cocktailInfoVisible: false,

      parties: this.$store.state.items.parties,
      partiesLoading: true,

      ingredients: this.$store.state.items.ingredients,
      ingredientsLoading: true,

      products: this.$store.state.items.products,
      productsLoading: true,

      availableCocktails: undefined,
      availableCocktailsLoading: true,

      users: this.$store.state.items.users,
      usersLoading: true,
      currentUser: undefined,
      userInfoVisible: false,

      invites: undefined,
      invitesLoading: true,

      groupedOrders: [],
      groupedOrdersLoading: true,

      addOrderFormVisible: false,
      orderAddIsError: false,
      orderAddErrorText: "",
    }
  },
  computed: {
    user() {
      return this.$store.getters['auth/getUser']
    },
    personalOrder() {
      console.log(this.groupedOrders.find(x => x.person_id === this.user.id))
      return this.groupedOrders.find(x => x.person_id === this.user.id)
    }
  },
  methods: {
    showUser(id) {
      this.userInfoVisible = true
      this.currentUser = this.invites.find(x => x.person_id === id)
    },
    addOrder(id) {
      this.addOrderFormVisible = true
      this.currentUser = id
    },
    showCocktailsInfo(id) {
      this.currentCocktail = this.cocktails.find(x => x.id === id)
      this.cocktailInfoVisible = true
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
    },
    fetchParties() {
      axios
          .get(this.api_url + 'parties/all')
          .then(response => {
            this.$store.commit("items/updateParties", response.data)
            this.parties = this.$store.state.items.parties
            this.party = this.parties.find(x => x.id === parseInt(this.$route.params.id))
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => this.partiesLoading = false)
    },
    fetchUsers() {
      axios
          .get(this.api_url + 'users/all')
          .then(response => {
            this.$store.commit("items/updateUsers", response.data)
            this.users = this.$store.state.items.users
            // this.users = (this.$store.state.items.users).filter(x => x.id !== this.user.id)
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => this.usersLoading = false)
    },
    fetchInvites() {
      axios
          .get(this.api_url + 'users/invites?party_id=' + this.$route.params.id)
          .then(response => {
            this.invites = response.data
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => this.invitesLoading = false)
    },
    getAvailableCocktails() {
      axios
          .get(this.api_url + 'parties/available?id=' + this.$route.params.id)
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
    fetchProducts() {
      axios
          .get(this.api_url + 'products/all')
          .then(response => {
            this.$store.commit("items/updateProducts", response.data)
            this.products = this.$store.state.items.products
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => this.productsLoading = false)
    },
    getGroupedOrders() {
      axios
          .get(this.api_url + 'parties/grouped?id=' + this.$route.params.id)
          .then(response => {
            this.groupedOrders = response.data
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => this.groupedOrdersLoading = false)
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
    async sendOrder(args) {
      let newOrder = args.order
      let status = false
      let errorText = ""
      let badNewItem = false
      if (!badNewItem) {
        await axios.post(this.api_url + 'parties/order', newOrder)
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
          this.stocksLoading = true;
          this.groupedOrdersLoading = true;
          this.availableCocktailsLoading = true;
          this.fetchPurchases()
          this.getGroupedOrders()
          this.getAvailableCocktails()
          this.addOrderFormVisible = false
        } else {
          console.log("is Error")
          this.orderAddIsError = true
          this.orderAddErrorText = errorText
        }
      }
    },
  },
  mounted() {
    console.log("fetching")
    this.fetchIngredients()
    this.fetchCocktails()
    this.fetchParties()
    this.fetchUsers()
    this.fetchInvites()
    this.getAvailableCocktails()
    this.fetchProducts()
    this.getGroupedOrders()
    this.fetchUsers()
  }
}
</script>

<style scoped>
.body-container {
  display: flex;
  flex-direction: column;
}

.body-container > div {
  width: 100%;
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

.party-info {
  background: black;
}

.party-items {
  margin-top: 25px;
  background: black;
}

.party-items > *:first-child {
  padding: 10px;
  background-color: #69AAB8;
}

.v-spinner {
  text-align: center;
}

.price{
  color: #576163;
}

.order-container{
  display: flex;
  flex-direction: column;
}

.order-item{
  font-size: x-large;
  display: flex;
  gap: 10px;
  padding: 10px;
}
.total{
  display: flex;
  font-size: x-large;
  padding: 10px;
  background-color: #292929;
}
.total div{
  flex-grow: 1;
  text-align: end;
  padding-right: 10px;
  color: #576163;
}
</style>
