import axios from 'axios';

const service = axios.create({
    baseURL: '/api', // 使用相对路径
    timeout: 5000,
});

// 请求拦截器
service.interceptors.request.use(
    (config) => {
        return config;
    },
    (error) => {
        return Promise.reject("请求失败" + error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    (response) => {
        console.log('响应拦截器触发:', response.status);
        if (response.status === 200) {
            return response.data;
        } else {
            console.error('服务器返回错误状态码:', response.status);
            return Promise.reject(new Error(response.data.message || '未知错误'));
        }
    }
);

export default service;