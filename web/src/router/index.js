import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        redirect: '/login',
    },
    {
        path: '/login',
        component: Login,

    },
    {
        path: '/register',
        component: Register
    },
    // 跳转主页面路径
    {
        path: '/html-file',
        redirect: () => {
            window.location.href = '/home.html'; // 重定向到静态文件
            return ''; // 防止 Vue Router 报错
        }
    }
]

const router = new VueRouter({
    routes,
    mode: "history"
})
export default router
