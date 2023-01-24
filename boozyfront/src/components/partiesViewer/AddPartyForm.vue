<template>
  <form @submit.prevent>
    <div class="form-container">
      <div class="error-container" v-if="isError">
        {{ errorText }}
      </div>
      <div>
        <my-input
            v-model="party.name"
            type="text"
            placeholder="party name"
        />
      </div>
      <div>
        <my-input
            v-model="party.description"
            type="text"
            placeholder="description"
        />
      </div>
      <div>
        <my-input
            v-model="party.location"
            type="text"
            placeholder="location"
        />
      </div>
      <div>
        <my-input
            v-model="date"
            @change="updateDate(this.date)"
            type="text"
            placeholder="date"
        />
      </div>
      <div class="menu">
        <div>
          menu
        </div>
        <div>
          <item-picker :page="'parties'" :item-list="cocktails" v-model:pickedItems="this.party.menu"></item-picker>
        </div>
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


export default {
  name: "AddPartyForm",
  components: {ItemPicker, RectButton, MyInput,},
  props: {
    isError: {
      type: Boolean,
      default: false
    },
    errorText: {
      type: String,
      default: ""
    },
    cocktails: {
      type: Array,
      default: ""
    },
    prevParty: {
      required: false
    }
  },
  data() {
    return {
      party: {
        name: '',
        description: '',
        location: '',
        menu: [],
        id: undefined,
        date: undefined
      },
      isEdit: false,
      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)
          + ' '
          + (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(11, 5),
    }
  },
  methods: {
    submitData() {
      this.$emit('submitData', {party: this.party, url: 'add'})
    },

    editData() {
      this.$emit('submitData', {party: this.party, url: 'edit'})
    },
    updateDate(newDate){
      console.log(newDate)
      this.party.date = new Date(Date.parse(newDate))
      console.log(this.party.date)
    }
  },
  mounted() {
    if (this.prevParty !== undefined) {
      console.log("is edit")
      this.party.name = this.prevParty.name;
      this.party.description = this.prevParty.description;
      this.party.id = this.prevParty.id;
      this.party.menu = this.prevParty.menu;
      this.party.location = this.prevParty.location;
      this.party.date = this.prevParty.date;
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

.menu {
  display: flex;
  flex-direction: column;
}
</style>
