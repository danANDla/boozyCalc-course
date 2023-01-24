<template>
  <div class="body-container">

    <div class="party-info">
      user
      <div>This is Bar {{ $route.params.id }}</div>
    </div>

    <div class="invites">
      INVITES
    </div>

    <div class="menu">
      MENU
    </div>
  </div>


</template>

<script>
import axios from "axios";
import router from "@/router";

export default {
  name: "AdminPartyInfo",
  data(){
    return {
      cocktails: this.$store.state.items.cocktails,
      parties: this.$store.state.items.parties,
      api_url: "http://127.0.0.1:8080/api/",
    }
  },
  methods:{
    showParty(id){
      console.log('show party', id)
      router.push({ path: `/party/{id}` }) // -> /user/eduardo
    },
    editParty(id){
      console.log('edit party', id)
    },
    deleteParty(id){
      console.log('delete party', id)
    },
    addParty(id){
      console.log('add party', id)
    },
    async fetchCocktails() {
      try {
        const response = await axios.get(this.api_url + 'cocktails/all')
        console.log(response)
        this.$store.commit("items/updateCocktails", response.data)
        this.cocktails = this.$store.state.items.cocktails
      } catch (e) {
        alert(e.message)
      }
    },
    async fetchParties() {
      try {
        const response = await axios.get(this.api_url + 'parties/all')
        console.log(response)
        this.$store.commit("items/updateParties", response.data)
        this.parties = this.$store.state.items.parties
      } catch (e) {
        alert(e.message)
      }
    },
  },
  mounted() {
    console.log("fetching")
    this.fetchCocktails()
    this.fetchParties()
  }
}
</script>

<style scoped>
.body-container{
  display: flex;
  flex-direction: column;
}

.body-container div{
  width: 100%;
  padding-left: 10px;
}
</style>
