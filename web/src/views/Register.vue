<script>
import axios from "../../axios";

export default {
	name: "RegisterView",
	data() {
		return {
			registerForm: {
				username: 'admin',
				password: 'admin'
			},
			confirmPasswd: ''
		}
	},
	methods: {
		goLogin() {
			this.$router.push('/login');
		},
		async handRegister() {
			if (this.registerForm.password === this.confirmPasswd) {
				console.log(("二次密码一致"+ this.registerForm.password + this.confirmPasswd))
				axios.post(
					"/api/auth/register",
					this.registerForm
				)
					.then(res => res.data)
					.then(data => {
						console.log("token", data.data);
						if (data.statusCode === 'C00000') {
							// 提示注册成功
							alert("注册成功")
							// this.$router.push('/register');
						} else {
							alert(data.statusMessage);
						}
					})
					.catch(err => {
					});
			} else {
				alert(("用户名或密码错误"+ this.registerForm.password + this.confirmPasswd))
			}

		}
	}
}
</script>

<template>
	<div id="all">
		<div class="wrapper">
			<div class="form-wrapper sign-in">
				<form action="">
					<h2>注册</h2>
					<div class="input-group">
						<input v-model.trim="registerForm.username" required type="text">
						<label for="">username</label>
					</div>
					<div class="input-group">
						<input v-model.trim="registerForm.password" required type="password">
						<label for="">password</label>
					</div>
					<div class="input-group">
						<input v-model="confirmPasswd" required type="password">
						<label for="">confirm password</label>
					</div>
					<button class="btn" type="submit" @click="handRegister()">注册</button>
					<div class="sign-link">
						<p>返回<a class="signup-link" href="#" @click="goLogin()">登录</a></p>
					</div>
				</form>
			</div>
		</div>
	</div>

</template>

<style lang="less" scoped>

* {
	margin: 0;
}

#all {
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
	background: linear-gradient(#e91e63, #2196f3);

}

.wrapper {
	margin: 0 auto;

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