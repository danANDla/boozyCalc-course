export const itemsModule = {
    state: () => ({
        ingredients: [],
        cocktails: [],
        products: [],
        cocktailTypes: [],
        parties: [],
        purchases: []
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
        }
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
        }
    },
    actions: {},
    namespaced: true
}