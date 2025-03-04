// import axios from 'axios';
//
// const service = axios.create({
//     baseURL: '/api', // 使用相对路径
//     timeout: 5000,
// });

// src/utils/axios.js
import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://localhost:8080', // 后端接口的基础路径
    timeout: 5000, // 请求超时时间
    headers: {
        'Content-Type': 'application/json',
    },
});
// 请求拦截器
// instance.interceptors.request.use(
//     (config) => {
//         return config;
//     },
//     (error) => {
//         return Promise.reject("请求失败" + error);
//     }
// );

// 响应拦截器
axios.interceptors.response.use(
    (response) => {
        if (response.data.statusCode !== "C00000") {
            alert(response.data.statusMessage);
            return Promise.reject(response.data.statusMessage);
        }

        return response;
    },
    (error) => {
        console.log(error);
    }
);

export default instance;




