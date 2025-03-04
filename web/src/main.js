
import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
// import instance from "../axios";
//
// // 挂载 axios 到 Vue.prototype.$http
// Vue.prototype.$http = instance;

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');