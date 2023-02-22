<template>
  <div class="section-body">
    <items-list @showItem="showParty"
                :items="parties"
                :page="'parties'"
                :user-group="0"></items-list>
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
  name: "partiesViewer",
  components: {ItemsList, DialogWindow, AreYouSure, AddPartyForm},
  data(){
    return {
      cocktails: this.$store.state.items.cocktails,
      parties: this.$store.state.items.parties,
      api_url: "http://127.0.0.1:8080/api/",
    }
  },
  computed:{
    user() {
      return this.$store.getters['auth/getUser']
    }
  },
  methods:{
    showParty(id){
      console.log('show party', id)
      router.push('/party/' + id);
    },
    async fetchParties() {
      try {
        console.log(this.user)
        const response = await axios.get(this.api_url + 'parties/invited?person_id=' + this.user.id)
        this.$store.commit("items/updateParties", response.data)
        this.parties = this.$store.state.items.parties
      } catch (e) {
        alert(e.message)
      }
    },
  },
  mounted() {
    this.fetchParties()
  }
}
</script>

<style scoped>

.section-body{
  background-color: #576163;
}
</style>
