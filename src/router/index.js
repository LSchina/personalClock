import { createRouter, createWebHashHistory } from 'vue-router'
const router = createRouter({
    // 指定路由模式
    history: createWebHashHistory(),
    // 路由地址
    routes: [
        {
            /*最常见404匹配规则：没有找到对应路径地址 */
            path: "/:pathMath(.*)*",
            name: "notfound",
            component: () => import("../views/404/404.vue"),
        },
        {
            path: "/backLogin",
            component: () => import("../views/BackLogin.vue")
        },
        {
            path: "/",
            redirect:'/theme'
        },
        {
            path: "/login",
            component: () => import("../views/Login.vue")
        },
        {
            path: "/front",
            component: () => import("../views/front/Home.vue"),
            redirect:'/front/index',
            children:[
                {
                    path:'/front/index',
                    component: () => import("../views/front/Index.vue")
                },
                {
                    path:'/front/system',
                    component: () => import("../views/front/System.vue")
                },
                {
                    path:'/front/task',
                    component: () => import("../views/front/Task.vue")
                },
                {
                    path:'/front/ding',
                    component: () => import("../views/front/Ding.vue")
                },
               {
                    path:'/front/add',
                    component: () => import("../views/front/TaskAdd.vue")
                },
                {
                    path: '/front/exitMe',
                    component: () => import("../views/front/ExitMessage.vue")
                },
                {
                    path: '/front/chart',
                    component: () => import("../views/front/Chart.vue")
                },
                {
                    path: '/front/rank',
                    component: () => import("../views/front/Rank.vue")
                },

            ]
        },
        {
            path: "/theme",
            component: () => import("../views/front/Theme.vue")
        },
        {
            path: "/back",
            component: () => import("../views/back/Home.vue"),
            redirect:"/back/index",
            children:[
                {
                    path: "/back/index",
                    component: () => import("../views/back/Index.vue")
                },
                {
                    path: "/back/user",
                    component: () => import("../views/back/User.vue")
                },
                {
                    path: "/back/rank",
                    component: () => import("../views/back/Rank.vue")
                },
                {
                    path: "/back/admin",
                    component: () => import("../views/back/Admin.vue")
                },
                {
                    path: "/back/poster",
                    component: () => import("../views/back/Poster.vue")
                },
                {
                    path: "/back/ding",
                    component: () => import("../views/back/Ding.vue")
                },
                {
                    path: "/back/task",
                    component: () => import("../views/back/Task.vue")
                },

                {
                    path: "/back/system",
                    component: () => import("../views/back/System.vue")
                },
                {
                    path: "/back/recommend",
                    component: () => import("../views/back/Recommend.vue")
                },
                {
                    path: "/back/savePoster",
                    component: () => import("../views/back/savePoster.vue")
                },
            ]
        },
    ]
})
export default router