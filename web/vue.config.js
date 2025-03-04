// module.exports = {
//     devServer: {
//         proxy: {
//             '/api': { // 代理前缀，前端请求以 `/api` 开头的路径时会被代理
//                 target: 'http://localhost:8080', // 后端服务器地址
//                 changeOrigin: true, // 允许跨域
//                 pathRewrite: { '^/api': '' }, // 路径重写，移除 `/api` 前缀
//             }
//         }
//     }
// };
//
// vue.config.js
module.exports = {
    devServer: {
        port: 8081, // 设置开发服务器的端口号
        host: 'localhost', // 设置开发服务器的主机名
        // open: true, // 自动打开浏览器
        proxy: {
            '/test': {
                target: 'http://localhost:8080', // 后端接口的地址
                changeOrigin: true, // 开启代理
                pathRewrite: { '^/test': '' }, // 将 /api 替换为空字符串
            },
        },
    },
};