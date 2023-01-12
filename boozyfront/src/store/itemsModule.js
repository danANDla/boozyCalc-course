export const itemsModule = {
    state: () => ({
        ingredients: [],
        cocktails: [],
        products: [],
        cocktailTypes: []
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
        }
    },
    actions: {},
    namespaced: true
}