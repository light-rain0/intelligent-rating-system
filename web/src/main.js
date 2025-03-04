import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import service from "../axios";

Vue.prototype.$http.axios = service

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
