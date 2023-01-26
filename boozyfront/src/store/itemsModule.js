export const itemsModule = {
    state: () => ({
        ingredients: [],
        cocktails: [],
        products: [],
        cocktailTypes: [],
        parties: [],
        purchases: [],
        users: [],
        invites:[]
    }),
    getters: {
        getIngredients(state) {
            return state.ingredients
        },
        getCocktails(state){
            return state.cocktails
        },
        getProducts(state){
            return state.products
        },
        getCocktailTypes(state){
            return state.cocktailTypes
        },
        getParties(state){
            return state.parties
        },
        getPurchases(state){
            return state.purchases
        },
        getUsers(state){
            return state.users
        },
        getInvites(state){
            return state.invites
        },
    },
    mutations: {
        updateIngredients(state, newArray) {
            state.ingredients = newArray
        },
        updateCocktails(state, newArray){
            state.cocktails = newArray
        },
        updateProducts(state, newArray){
            state.products = newArray
        },
        updateCocktailTypes(state, newArray){
            state.cocktailTypes = newArray
        },
        updateParties(state, newParty){
            state.parties = newParty
        },
        updatePurchases(state, newArray){
            state.purchases = newArray
        },
        updateUsers(state, newArray){
            state.users = newArray
        },
        updateInvites(state, newArray){
            state.invites = newArray
        },
    },
    actions: {},
    namespaced: true
}