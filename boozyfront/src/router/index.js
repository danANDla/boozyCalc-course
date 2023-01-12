import AdminHomePage from "@/views/adminPart/AdminHomePage";
import {createRouter, createWebHistory } from "vue-router";
import AdminListsViewer from "@/views/adminPart/AdminListsViewer";
import UserListsViewer from "@/views/userPart/UserListsViewer";
import WelcomePage from "@/views/WelcomePage";
import UserMainPage from "@/views/userPart/UserMainPage";
import Main from "@/views/Main"

const routes = [
    {
        path: '/',
        component: Main,
        children: [
            {
                path: "items",
                name: "ingredients-and-cocktails",
                component: UserMainPage,
                children: [
                    {
                        path: "",
                        name: "ingredients-and-cocktails",
                        component: UserListsViewer
                    }
                ]
            },
            {
                path: 'admin',
                component: AdminHomePage,
                children:[
                    {
                        path: "lists",
                        name: "items",
                        component: AdminListsViewer
                    },
                ]
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes: routes
})

export default router;