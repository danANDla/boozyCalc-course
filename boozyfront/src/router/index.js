import AdminHomePage from "@/views/adminPart/AdminHomePage";
import {createRouter, createWebHistory } from "vue-router";
import AdminListsViewer from "@/views/adminPart/AdminListsViewer";
import UserListsViewer from "@/views/userPart/UserListsViewer";
import WelcomePage from "@/views/WelcomePage";
import UserMainPage from "@/views/userPart/UserMainPage";
import Main from "@/views/Main"
import AdminPartiesViewer from "@/views/adminPart/AdminPartiesViewer";
import AdminPartyInfo from "@/views/adminPart/AdminPartyInfo";
import Registrate from "@/components/registrate";

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
                    {
                        path: "parties",
                        name: "parties",
                        component: AdminPartiesViewer,
                    },
                    {
                        path: "party/:id",
                        name: "partyInfo",
                        component: AdminPartyInfo
                    },
                ]
            },
            {
                path: "start",
                name: "start",
                component: WelcomePage
            },
            {
                path: "register",
                name: "Registration",
                component: Registrate
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes: routes
})

export default router;