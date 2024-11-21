<template>
  <div class="wrap">
    <div class="container">
      <!-- 表单区域 -->
      <el-form
        ref="registerFormRef"
        :model="registerForm"
        :rules="registerFormRules"
      >
        <h1 style="color: black;margin: 0; text-align: center; font-size: 2em">
          用户注册
        </h1>
        <br />
        <el-form-item prop="email">
          <el-input
            v-model="registerForm.email"
            placeholder="邮箱地址"
            prefix-icon="el-icon-s-promotion"
          ></el-input>
        </el-form-item>
        <el-form-item prop="username">
          <el-input
            v-model="registerForm.username"
            placeholder="姓名"
            prefix-icon="el-icon-s-custom"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="registerForm.password"
            placeholder="密码"
            prefix-icon="el-icon-s-goods"
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="registerForm.rePassword"
            placeholder="确认密码"
            prefix-icon="el-icon-s-goods"
            show-password
          ></el-input>
        </el-form-item>

        <div class="change_link" style="text-align: center">
          <span class="text">已经有账号了？</span>
          <span class="to_login" @click="toLogin">去登录</span>
        </div>

        <div class="btn">
          <el-button
            type="success"
            round
            class="register_btn"
            @click="register()"
            >注 册</el-button
          >
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    // 邮箱地址自定义校验变量
    const validateEmail = (rule, value, callback) => {
      const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (value === '') {
        callback(new Error('请填写电子邮箱'))
      } else if (value !== '' && !regEmail.test(value)) {
        callback(new Error('邮箱格式不正确'))
      }
      callback()
    }
    return {
      sendBtnTxt: '发送验证码', // 按钮显示内容
      msgBtnDisabled: false, // 按钮禁用状态
      timer: null, // 计时器
      countdown: 30, // 时间

      // 后台返回的有效验证码
      checkCode: '',
      // 注册表单
      registerForm: {
        username: '',
        password: '',
        rePassword: '',
        email: '',
        code: ''
      },
      // 注册表单验证规则
      registerFormRules: {
        username: [
          { required: true, message: '请填写姓名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请填写密码', trigger: 'blur' }
        ],
        rePassword: [
          { required: true, message: '请填写确认密码', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请填写电子邮箱', trigger: 'change' },
          { validator: validateEmail, trigger: 'blur' }
        ]
      }

    }
  },
  methods: {
    register () {
      this.$refs.registerFormRef.validate(async (valid) => {
        if (this.registerForm.password !== this.registerForm.rePassword) {
          this.$message.error('确认密码与密码不一致！')
          return
        }
        if (!valid) return
        const { data: res } = await this.$http.post('/user/create', this.registerForm)
        // if (res.code === -4) return this.$message.warning('用户名已被注册！')
        if (res.code === -5) return this.$message.warning('该邮箱已被注册！')
        else if (res.code !== 200) return this.$message.error(res.info)
        this.$message.success('注册账号成功！')
        // 清空表单
        this.$refs.registerFormRef.resetFields()
        setTimeout(() => {
          this.toLogin()
        }, 200)
      })
    },
    toLogin () {
      this.$router.push('/login')
    }

  }

}
</script>

<style lang="less" scoped>
body {
  margin: 0;
  padding: 0;
  font: 16px/20px microsft yahei;
}

.wrap {
  width: 100%;
  height: 100%;
  padding: 10% 0;
  position: fixed;
  opacity: 0.8;
  background: linear-gradient(to bottom right, #3ba7e6, #bae24d);
  background: -webkit-linear-gradient(to bottom right, #50a3a2, #53e3a6);
}
.container {
  // border:1px solid red;
  background: #e4eef3;
  width: 40%;
  display: flex;
  justify-content: center;
  border-radius: 40px;
  // border:1px solid black;

  .el-form {
    width: 80%;
    // border:1px solid red;
    padding: 0 15%;
    .el-form-item {
      padding: 0 5%;
    }

    opacity: 0.8;
    .el-form-item__content {
      padding: 0;
    }
    .btn {
      display: flex;
      justify-content: center;
      .register_btn {
        width: 90%;
        font-size: 1.1em;
      }
    }
  }
  h1 {
    text-align: center;
    color: #ffffff;
    font-weight: 500;
  }
}

@media only screen and (min-width: 100px) and (max-width: 1424px) {
  .container {
    width: 600px;
    height: 400px;
    .el-form {
      width: 500px;
    }
  }
}

.input {
  width: 60%;
}
.send {
  position: absolute;
  right: 0;
}

.change_link {
  padding: 0 5%;
  display: flex;
  justify-content: space-between;
  font-size: 0.9em;
}

.to_login {
  color: rgb(79, 79, 230);
  cursor: pointer;
}
.to_login:hover {
  color: red;
}
.text {
  color: #e2dfe4;
}
.wrap ul {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -20;
}
.wrap ul li {
  list-style-type: none;
  display: block;
  position: absolute;
  bottom: -120px;
  width: 15px;
  height: 15px;
  z-index: -8;
  border-radius: 50%;
  box-shadow: inset -30px -30px 75px rgba(241, 186, 7, 0.2),
    inset 0px 0px 5px rgba(86, 236, 148, 0.5),
    inset -3px -3px 5px rgba(75, 207, 58, 0.5),
    0 0 20px rgba(255, 255, 255, 0.75);
  background-color: rgba(255, 255, 255, 0.15);
  animotion: square 25s infinite;
  -webkit-animation: square 25s infinite;
}
.wrap ul li:nth-child(1) {
  left: 0;
  animation-duration: 10s;
  -moz-animation-duration: 10s;
  -o-animation-duration: 10s;
  -webkit-animation-duration: 10s;
}
.wrap ul li:nth-child(2) {
  width: 40px;
  height: 40px;
  left: 10%;
  animation-duration: 15s;
  -moz-animation-duration: 15s;
  -o-animation-duration: 15s;
  -webkit-animation-duration: 11s;
}
.wrap ul li:nth-child(3) {
  left: 20%;
  width: 25px;
  height: 25px;
  animation-duration: 12s;
  -moz-animation-duration: 12s;
  -o-animation-duration: 12s;
  -webkit-animation-duration: 12s;
}
.wrap ul li:nth-child(4) {
  width: 50px;
  height: 50px;
  left: 30%;
  -webkit-animation-delay: 3s;
  -moz-animation-delay: 3s;
  -o-animation-delay: 3s;
  animation-delay: 3s;
  animation-duration: 12s;
  -moz-animation-duration: 12s;
  -o-animation-duration: 12s;
  -webkit-animation-duration: 12s;
}
.wrap ul li:nth-child(5) {
  width: 60px;
  height: 60px;
  left: 40%;
  animation-duration: 10s;
  -moz-animation-duration: 10s;
  -o-animation-duration: 10s;
  -webkit-animation-duration: 10s;
}
.wrap ul li:nth-child(6) {
  width: 75px;
  height: 75px;
  left: 50%;
  -webkit-animation-delay: 7s;
  -moz-animation-delay: 7s;
  -o-animation-delay: 7s;
  animation-delay: 7s;
}
.wrap ul li:nth-child(7) {
  left: 60%;
  width: 30px;
  height: 30px;
  animation-duration: 8s;
  -moz-animation-duration: 8s;
  -o-animation-duration: 8s;
  -webkit-animation-duration: 8s;
}
.wrap ul li:nth-child(8) {
  width: 90px;
  height: 90px;
  left: 70%;
  -webkit-animation-delay: 4s;
  -moz-animation-delay: 4s;
  -o-animation-delay: 4s;
  animation-delay: 4s;
}
.wrap ul li:nth-child(9) {
  width: 50px;
  height: 50px;
  left: 80%;
  animation-duration: 20s;
  -moz-animation-duration: 20s;
  -o-animation-duration: 20s;
  -webkit-animation-duration: 20s;
}
.wrap ul li:nth-child(10) {
  width: 75px;
  height: 75px;
  left: 90%;
  -webkit-animation-delay: 6s;
  -moz-animation-delay: 6s;
  -o-animation-delay: 6s;
  animation-delay: 6s;
  animation-duration: 30s;
  -moz-animation-duration: 30s;
  -o-animation-duration: 30s;
  -webkit-animation-duration: 30s;
}
@keyframes square {
  0% {
    -webkit-transform: translateY(0);
    transform: translateY(0);
  }
  100% {
    bottom: 400px;
    -webkit-transform: translateY(-500);
    transform: translateY(-500);
  }
}
@-webkit-keyframes square {
  0% {
    -webkit-transform: translateY(0);
    transform: translateY(0);
  }
  100% {
    bottom: 400px;
    -webkit-transform: translateY(-500);
    transform: translateY(-500);
  }
}
</style>
