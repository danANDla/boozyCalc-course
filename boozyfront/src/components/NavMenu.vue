<template>
  <div class="container">
    <div class="menu">
      <div class="item">
        <router-link to="/items" v-slot="{ href, navigate}">
          <menu-nav-button :href="href" @click="navigate">Ingredients | Cocktails</menu-nav-button>
        </router-link>
      </div>
      <div class="item">
        <menu-nav-button>Stock Manager</menu-nav-button>
      </div>
      <div class="item">
        <menu-nav-button>Parties</menu-nav-button>
      </div>
    </div>
  </div>
</template>

<script>
import MenuNavButton from "@/components/UI/MenuNavButton";
export default {
  name: "NavMenu",
  components: {MenuNavButton}

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