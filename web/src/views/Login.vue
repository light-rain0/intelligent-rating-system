<script>

import axios from "axios";

export default {
	data() {
		return {
			loginForm: {
				username: 'admin',
				password: 'admin'
			}
		}
	},
	methods: {
		goRegister() {
			this.$router.push('/register');
		},
		async handleLogin() {
			axios.post(
				"/api/auth/login",
				this.loginForm
			)
				.then(res => res.data)
				.then(data => {
					console.log("token", data.data);
					if (data.statusCode === 'C00000') {
						// 页面跳转
						// this.$router.push('/html-file');
						this.$router.push('/html-file');
						// window.location.href = "/../../public/home.html"
					} else {
						//登录错误提示
						this.$message.success("hhh")
						// alert(data.statusMessage);
					}
				})
				.catch(err => {
				});
		}
	}
}

</script>

<template>
	<div id="all">
		<div class="wrapper">
			<div class="form-wrapper sign-in">
				<form action="">
					<h2>登录</h2>
					<div class="input-group">
						<input v-model="loginForm.username" required type="text">
						<label for="">username</label>
					</div>
					<div class="input-group">
						<input v-model="loginForm.password" required type="password">
						<label for="">password</label>
					</div>
					<button class="btn" type="button" @click="handleLogin">登录</button>
					<div class="sign-link">
						<p>没有账号? <a class="signup-link" href="#" @click="goRegister">注册</a></p>
					</div>
				</form>
			</div>
		</div>
	</div>
</template>

<style lang="css">

* {
	margin: 0;
}

#all {
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	background: linear-gradient(#2196f3, #e91e63)
}

.wrapper {
	display: flex;
	position: relative;
	width: 400px;
	height: 500px;
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
	margin: 30px 0;
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


.btn {
	position: relative;
	top: 0;
	left: 0;
	width: 100%;
	height: 40px;
	background: linear-gradient(to right, #2196f3,
	#e91e63);
	box-shadow: 0 2px 10px rgba(0, 0, 0, .4);
	font-size: 16px;

	color: #fff;
	font-weight: 500;
	cursor: pointer;
	border-radius: 5px;
	border: none;
	outline: none;
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
	color: #e91e63

}
</style>