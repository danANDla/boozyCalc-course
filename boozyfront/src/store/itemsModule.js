export const itemsModule = {
    state: () => ({
        ingredients: [],
        cocktails: [],
        products: []
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
        }
    },
    actions: {},
    namespaced: true
}