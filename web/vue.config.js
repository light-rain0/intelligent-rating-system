module.exports = {
    devServer: {
        proxy: {
            '/api': { // 代理前缀，前端请求以 `/api` 开头的路径时会被代理
                target: 'http://localhost:8080', // 后端服务器地址
                changeOrigin: true, // 允许跨域
                pathRewrite: { '^/api': '' }, // 路径重写，移除 `/api` 前缀
            }
        }
    }
};