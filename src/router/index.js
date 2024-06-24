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
            redirect:'/backLogin'
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
                    path: "/back/admin",
                    component: () => import("../views/back/Admin.vue")
                },
                {
                    path: "/back/poster",
                    component: () => import("../views/back/Poster.vue")
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
            ]
        },
    ]
})
export default router