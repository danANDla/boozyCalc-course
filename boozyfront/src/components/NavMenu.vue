<template>
  <div class="container">
    <div class="menu" v-if="this.userGroup === 1">
      <div class="item">
        <router-link to="/admin/lists" v-slot="{ href, navigate}">
          <menu-nav-button :href="href" @click="navigate">Ingredients | Cocktails</menu-nav-button>
        </router-link>
      </div>
      <div class="item">
        <router-link to="/admin/parties" v-slot="{ href, navigate}">
          <menu-nav-button :href="href" @click="navigate">Parties</menu-nav-button>
        </router-link>
      </div>
    </div>

    <div class="menu" v-if="this.userGroup === 0">
      <div class="item">
        <router-link to="/parties" v-slot="{ href, navigate}">
          <menu-nav-button :href="href" @click="navigate">Parties</menu-nav-button>
        </router-link>
      </div>
      <div class="item">
        <router-link to="/items" v-slot="{ href, navigate}">
          <menu-nav-button :href="href" @click="navigate">Cocktails</menu-nav-button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import MenuNavButton from "@/components/UI/MenuNavButton";
export default {
  name: "NavMenu",
  components: {MenuNavButton},
  props:{
    userGroup: {
      type: Number,
      required: true
    }
  }
}
</script>

<style scoped>
.container{
  width: 60%;
}
.menu{
  height: fit-content;
  display: flex;
  flex-direction: column;
  background-color: black;
  color: black;
  align-items: center;
  justify-content: start;
}

.item{
  width: 100%;
  display: flex;
  justify-content: start;
  background-color: white;
}
.item > *{
  width: 100%;
}
</style>