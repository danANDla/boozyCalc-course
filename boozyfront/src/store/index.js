import {createStore} from "vuex";
import {itemsModule} from "@/store/itemsModule";

export default createStore({
    modules: {
        items: itemsModule
    }
})