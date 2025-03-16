<script>
import axios from 'axios';

export default {
	data() {
		return {
			form: {
				username: '',
				password: '',
				nickname: '',
			},
			avatarFile: null
		};
	},
	methods: {
		handleAvatarChange(e) {
			const file = e.target.files[0];
			if (file) {
				this.avatarFile = file;
				alert('头像已选择: ' + file.name);
			}
		},
		triggerFileInput() {
			// 触发隐藏的文件输入框
			document.getElementById('avatar').click();
		},
		async handleRegister() {
			try {
				const formData = new FormData();
				formData.append('username', this.form.username);
				formData.append('password', this.form.password);
				formData.append('nickname', this.form.nickname);
				if (this.avatarFile) {
					formData.append('avatar', this.avatarFile);
				}

				const response = await axios.post('/api/auth/register', formData, {
					headers: {
						'Content-Type': 'multipart/form-data'
					}
				});

				console.log("注册成功", response.data);
				if (response.data.statusCode === 'C0000') {
					alert('注册成功');
					await this.$router.push('/login'); // 跳转到登录页面
				} else {
					alert(response.data.statusMessage || '注册失败');
				}
			} catch (error) {
				console.error('注册失败:', error);
				alert('注册请求出错，请稍后重试');
			}
		},
		goLogin() {
			this.$router.push('/login'); // 跳转到登录页面
		}
	}
};
</script>
<template>
	<div id="all">
		<div class="wrapper">
			<div class="form-wrapper sign-in">
				<form @submit.prevent="handleRegister">
					<h2>注册</h2>
					<div class="input-group">
						<input id="username" v-model="form.username" required type="text">
						<label for="username">账号</label>
					</div>
					<div class="input-group">
						<input id="password" v-model="form.password" required type="password">
						<label for="password">密码</label>
					</div>
					<div class="input-group">
						<input id="nickname" v-model="form.nickname" required type="text">
						<label for="nickname">昵称</label>
					</div>
					<div class="input-group">
						<!-- 隐藏的文件输入框 -->
						<input id="avatar" style="display: none;" type="file" @change="handleAvatarChange">
						<!-- 自定义上传按钮 -->
						<button class="btn-upload" type="button" @click="triggerFileInput">上传头像</button>
					</div>
					<button class="btn-register" type="submit">注册</button>
					<div class="sign-link">
						<p>已有账号? <a class="signup-link" href="#" @click="goLogin">登录</a></p>
					</div>
				</form>
			</div>
		</div>
	</div>
</template>


<style scoped>
* {
	margin: 0;
}

#all {
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	background: linear-gradient(#2196f3, #e91e63);
}

.wrapper {
	display: flex;
	position: relative;
	width: 400px;
	height: 600px; /* 调整高度以适应更多输入框 */
}

.form-wrapper {
	position: absolute;
	top: 0;
	left: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100%;
	height: 100%;
	background: #fff;
	box-shadow: 0 0 10px rgba(0, 0, 0, .2);
}

h2 {
	font-size: 30px;
	color: #555;
	text-align: center;
}

.input-group {
	position: relative;
	width: 320px;
	margin: 20px 0; /* 调整间距 */
}

.input-group label {
	position: absolute;
	top: 50%;
	left: 5px;
	transform: translateY(-50%);
	font-size: 16px;
	color: #333;
	padding: 0 5px;
	pointer-events: none;
	transition: .5s;
}

.input-group input {
	width: 100%;
	height: 40px;
	font-size: 16px;
	color: #333;
	padding: 0 10px;
	background: transparent;
	border: 1px solid #333;
	outline: none;
	border-radius: 5px;
}

.input-group input:focus ~ label,
.input-group input:valid ~ label {
	top: 0;
	font-size: 12px;
	background: #fff;
}

.btn-register {
	width: 100%;
	height: 40px;
	background: linear-gradient(to right, #2196f3, #e91e63);
	box-shadow: 0 2px 10px rgba(0, 0, 0, .4);
	font-size: 16px;
	color: #fff;
	font-weight: 500;
	cursor: pointer;
	border-radius: 5px;
	border: none;
	outline: none;
	margin-top: 10px;
}

.btn-register:hover {
	background: linear-gradient(to right, #1e88e5, #d81b60);
}

.btn-upload {
	width: 100%;
	height: 40px;
	background: #f0f0f0;
	border: 1px solid #ccc;
	border-radius: 5px;
	font-size: 16px;
	color: #333;
	cursor: pointer;
}

.btn-upload:hover {
	background: #e0e0e0;
}

.sign-link {
	font-size: 14px;
	text-align: center;
	margin: 25px 0;
}

.sign-link p {
	color: #333;
}

.sign-link p a {
	color: #e91e63;
	text-decoration: none;
	cursor: pointer;
}

.sign-link p a:hover {
	text-decoration: underline;
}
</style>