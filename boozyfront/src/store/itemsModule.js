export const itemsModule = {
    state: () => ({
        ingredients: [],
        cocktails: []
    }),
    getters: {
        getIngredients(state) {
            return state.ingredients
        },
        getCocktails(state){
            return state.cocktails
        }
    },
    mutations: {
        updateIngredients(state, newArray) {
            state.ingredients = newArray
        },
        updateCocktails(state, newArray){
            state.cocktails = newArray
        }
    },
    actions: {},
    namespaced: true
}