import {createStore} from "vuex";
import {itemsModule} from "@/store/itemsModule";
import {auth} from "@/store/authModule";
import createPersistedState from "vuex-persistedstate";

export default createStore({
    modules: {
        items: itemsModule,
        auth: auth
    },
    plugins:[
        createPersistedState({
            storage: window.sessionStorage,
        })
    ]
})