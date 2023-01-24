<template>
  <div class="section-body">
    <dialog-window v-model:show="partyDialogVisible">
      <div class="form-container">
        <add-party-form @submitData="sendParty"
                           @input="this.partyAddIsError = false; this.partyAddErrorText=''"
                           :is-error="partyAddIsError"
                           :error-text="partyAddErrorText"
                           :cocktails="cocktails"
                           :prev-cocktail="currentParty">
        </add-party-form>
      </div>
    </dialog-window>

    <dialog-window v-model:show="partySureVisible">
      <div class="form-container">
        <are-you-sure @sure="sure('parties', partySureId)" @notsure="notsure('parties')"> Are you sure you want to delete
          {{ partySureName }}?
        </are-you-sure>
      </div>
    </dialog-window>

    <items-list @addItem="showPartyDialog" @deleteItem="showSureParty" @editItem="showPartyEditDialog" @showItem="showParty"
                :items="parties"
                :page="'parties'"
                :user-group="1"></items-list>
  </div>
</template>

<script>
import axios from "axios";
import ItemsList from "@/components/listsViewer/ItemsList";
import DialogWindow from "@/components/UI/DialogWindow";
import AreYouSure from "@/components/UI/AreYouSure";
import AddPartyForm from "@/components/partiesViewer/AddPartyForm";
import router from "@/router";

export default {
  name: "adminpartiesviewer",
  components: {ItemsList, DialogWindow, AreYouSure, AddPartyForm},
  data(){
    return {
      cocktails: this.$store.state.items.cocktails,
      parties: this.$store.state.items.parties,
      api_url: "http://127.0.0.1:8080/api/",


      currentParty: undefined,
      partySureVisible: false,
      partyDialogVisible: false,
      partyInfoVisible: false,
      partySureName: String,
      partySureId: Number,
      partyAddIsError: false,
      partyAddErrorText: "",
    }
  },
  methods:{
    showParty(id){
      console.log('show party', id)
      router.push({name: 'partyInfo', params: {id: id}});
    },
    editParty(id){
      console.log('edit party', id)
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
    showPartyDialog() {
      this.partyAddIsError = false
      this.partyAddErrorText = ""
      this.partyDialogVisible = true
    },
    showPartyEditDialog(id) {
      this.currentParty = this.parties.find(x => x.id === id)
      console.log(this.currentParty)
      this.partyAddIsError = false
      this.partyAddErrorText = ""
      this.partyDialogVisible = true
    },
    showSureCocktail(id, name) {
      this.partySureId = id
      this.partySureName = name
      this.partySureVisible = true
      console.log('trying delete ' + id)
    },
    showPartyInfo(id){
      this.partyInfoVisible = true
      console.log('showPartyInfo', id)
      this.currentParty = this.parties.find(x => x.id === id)
    },
    async sendParty(args) {
      console.log(args)
      let newParty = args.party
      let badNewItem = false
      if (newParty.menu.length === 0) {
        this.partyAddIsError = true
        badNewItem = true
        this.partyAddErrorText = "empty menu"
      } else {
        for (let t in newParty.menu) {
          console.log(newParty.menu[t])
        }
      }
      if (newParty.name === "") {
        this.partyAddIsError = true
        badNewItem = true
        this.partyAddErrorText = "empty name field"
      }
      if (newParty.location === "") {
        this.partyAddIsError = true
        badNewItem = true
        this.partyAddErrorText = "empty location field"
      }
      if (!badNewItem) {
        console.log(newParty)
        let status = false
        let errorText = ""
        await axios.post(this.api_url + 'parties/' + args.url, newParty)
            .then(function (response) {
              status = true;
              console.log(response.status.valueOf())
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
          await this.fetchParties()
          this.partyDialogVisible = false
        } else {
          console.log("is Error")
          this.partyAddIsError = true
          if (typeof errorText !== 'string') errorText = 'server error'
          this.partyAddErrorText = errorText.substring(0, Math.min(errorText.length, 200))
        }
      }
    },
    async deleteParty(id) {
      const response = await axios.delete(this.api_url + 'parties?id=' + id)
      console.log(response)
      await this.fetchParties()
    },
    showSureParty(id, name) {
      this.partySureId = id
      this.partySureName = name
      this.partySureVisible = true
    },
    sure: function (type, id) {
        console.log(type + ' ' + id)
        this.deleteParty(id)
        this.partySureVisible = false
        this.partSureId = -1
        this.partySureName = ""
    },
    notsure: function (type) {
      this.partySureVisible = false
      this.partySureId = -1
      this.partySureName = ""
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

.form-container {
  width: 350px;
}
</style>