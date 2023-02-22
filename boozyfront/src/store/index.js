import {createStore} from "vuex";
import {itemsModule} from "@/store/itemsModule";
import {auth} from "@/store/authModule"

export default createStore({
    modules: {
        items: itemsModule,
        auth: auth
    }
})