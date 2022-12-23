import HomePage from "@/views/HomePage";
import {createRouter, createWebHistory } from "vue-router";
import ListsViewer from "@/views/ListsViewer";
import WelcomePage from "@/views/WelcomePage";

const routes = [
        {
            path: '/',
            component: HomePage,
            children:[
                {
                    path: "",
                    name: "welcome-page",
                    component: WelcomePage
                },
                {
                    path: "items",
                    name: "ingredients-and-cocktails",
                    component: ListsViewer
                }
            ]
        }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes: routes
})

export default router;