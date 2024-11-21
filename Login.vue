<template>
  <div class="login_container">
    <!-- 登录区域 -->
    <div class="box">
      <!-- 轮播图区域 -->
      <div class="carousel_box">
        <el-carousel height="400px">
          <el-carousel-item v-for="item in imagesbox" :key="item.id">
            <img :src="item.idView" class="image" />
          </el-carousel-item>
        </el-carousel>
      </div>

      <!-- 登录区域 -->
      <div class="login_box">
        <!-- 头像区域 -->
        <div class="avatar_box">
          <img src="../assets/logo1.jpg" alt="" />
        </div>
        <!-- 登录表单区域 -->
        <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="loginFormRules"
          class="login_form"
        >
          <!-- 邮箱 -->
          <el-form-item prop="email">
            <el-input
              v-model="loginForm.email"
              placeholder="电子邮箱"
              prefix-icon="iconfont icon-user"
            ></el-input>
          </el-form-item>
          <!-- 密码 -->
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              placeholder="密码"
              prefix-icon="iconfont icon-3702mima"
              type="password"
              show-password
            ></el-input>
          </el-form-item>
          <!-- 验证码 -->
          <el-form-item prop="checkcode">
            <el-input
              v-model="loginForm.checkcode"
              placeholder="验证码"
              prefix-icon="el-icon-s-ticket"
              class="input"
            ></el-input>
            <div class="check_code" @click="refreshCode()">
              <s-identify :identifyCode="identifyCode"></s-identify>
            </div>
          </el-form-item>
          <!-- 按钮区域 -->
          <el-form-item class="btns">
            <el-button type="primary" @click="login" class="btn_login"
              >登录</el-button
            >
          </el-form-item>
          <div>
            <router-link to="register" class="link_l router_link"
              >注册</router-link
            >
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
// 引入验证码组件
import SIdentify from '@/components/sIdentify.vue'
const Base64 = require('js-base64').Base64

export default {
  components: { SIdentify }, // 声明组件

  data () {
    // 邮箱地址自定义校验变量
    var validateEmail = (rule, value, callback) => {
      var regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (value === '') {
        callback(new Error('请填写电子邮箱'))
      } else if (value !== '' && !regEmail.test(value)) {
        callback(new Error('邮箱格式不正确'))
      }
      callback()
    }
    // 验证码校验变量
    var checkcodeValid = (rule, value, callback) => {
      if (value !== '' && value !== this.identifyCodeCheck) {
        this.checkcode = ''
        this.refreshCode()
        callback(new Error('验证码不正确！'))
      }
      callback()
    }

    return {
      // 验证码设置
      identifyCode: '',
      identifyCodeCheck: '', // 实际比较的验证码
      identifyCodes: '123456789abcdwerwshdjeJKDHRJHKPLMKQ', // 验证码字符集合

      // 这是登录表单的数据绑定
      loginForm: {
        email: '',
        password: '',
        checkcode: ''
      },
      rememberMe: false,
      // 这是表单的验证规则对象
      loginFormRules: {
        // 验证用户名是否合法
        email: [
          { required: true, message: '请输入登录邮箱', trigger: 'blur' },
          { validator: validateEmail, trigger: 'blur' }
        ],
        // 验证用密码是否合法
        password: [
          { required: true, message: '请输入登录密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
        ],
        checkcode: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { validator: checkcodeValid, trigger: 'blur' }
        ]
      },
      // 轮播图内容
      imagesbox: [
        { id: 0, idView: require('../assets/img/carousel/a1.jpg') },
        { id: 1, idView: require('../assets/img/carousel/a2.jpg') },
        { id: 2, idView: require('../assets/img/carousel/a3.jpg') },
        { id: 3, idView: require('../assets/img/carousel/a4.jpg') }
      ]
    }
  },
  methods: {
    // 点击重置按钮，重置登录表单
    resetLoginForm () {
      //   console.log(this)
      this.$refs.loginFormRef.resetFields()
    },
    login () {
      this.$refs.loginFormRef.validate(async (valid) => {
        if (!valid) return
        console.log(this.rememberMe)

        const { data: res } = await this.$http.post('/login', { email: this.loginForm.email, password: this.loginForm.password })
        console.log(res)
        if (res.code !== 200) {
          return this.$message.error(res.info)
        } else {
          if (this.rememberMe) {
            this.setCookie('email', this.loginForm.email, 7)
            this.setCookie('password', Base64.encode(this.loginForm.password), 7)
          } else {
            this.clearCookie('email')
            this.clearCookie('password')
          }
          this.$message.success('登录成功')

          // 将登录成功之后的 token，保存到客户端的 localStorage 中
          localStorage.setItem('token', res.data.token)

          // 通过编程式导航跳转到 /home
          this.$router.push('/view')
          window.location.reload()
        }
      })
      // // 用于开发时，注释前面，点击登录直接跳转到 后端页面
      // this.$router.push('/back')
    },
    // 设置cookie
    setCookie (cName, value, expiredays) {
      var exdate = new Date()
      exdate.setDate(exdate.getDate() + expiredays)
      document.cookie = cName + '=' + escape(value) + ';expires' + exdate.toGMTString() + ';path=/'
    },
    // 读取cookie
    getCookie (cName) {
      if (document.cookie.length > 0) {
        var cStart = document.cookie.indexOf(cName + '=')
        if (cStart !== -1) {
          cStart = cStart + cName.length + 1
          var cEnd = document.cookie.indexOf(';', cStart)
          if (cEnd === -1) {
            cEnd = document.cookie.length
          }
          return unescape(document.cookie.substring(cStart, cEnd))
        }
      }
    },
    // 检查cookie
    checkCookie (cName) {
      var myCookie = this.getCookie(cName)
      console.log(myCookie)
      if (myCookie !== null && myCookie !== '') return true
      else return false
    },
    // 清除cookie
    clearCookie (name) {
      this.setCookie(name, '', -1)
    },
    // 刷新验证码
    refreshCode () {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
    },
    randomNum (min, max) {
      max = max + 1
      return Math.floor(Math.random() * (max - min) + min)
    },
    // 随机生成验证码字符串
    makeCode (data, len) {
      for (let i = 0; i < len; i++) {
        this.identifyCode += data[this.randomNum(0, data.length - 1)]
        this.identifyCodeCheck = this.identifyCode.toLowerCase()
      }
    }
  },
  created () {
    this.refreshCode()

    var email = this.getCookie('email')
    var password = this.getCookie('password')
    if (email && password) {
      this.loginForm.email = email
      this.loginForm.password = Base64.decode(password)
      this.rememberMe = true
    }
  }
}

</script>

<style lang="less" scoped>
.login_container {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  // background-image: url('../assets/img/login_bg.jpg');

  background: linear-gradient(to bottom right, #3ba7e6, #bae24d);

  background-size: cover;
  // border:1px solid red;
  position: relative;
  // opacity: 0.8;
}
.head_box {
  width: 100%;
  position: absolute;
  top: 0;
  // opacity: 0.9;
  z-index: 10;
  .title {
    padding-left: 6%;
    box-shadow: 0 1px 3px -1px rgb(235, 196, 70);
  }
  .title_bg {
    // background-color:rgb(240, 209, 123);
    // background-image: url('../assets/img/login_t_b_bg.jpg');
    opacity: 0.9;
    .from_s {
      position: absolute;
      right: 6%;
    }
  }
}
.bottom_box {
  width: 100%;
  position: absolute;
  bottom: 2%;
  text-align: center;
  // border: 1px solid red;
  color: #666;
  font-size: 0.9em;
}
.login_box {
  // width: 450px;
  width: 30%;
  height: 410px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  right: 8%;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  // top: 25%;
  // transform: translate(-50%, -50%);

  .avatar_box {
    width: 130px;
    height: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    top: 0%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    display: flex;
    justify-content: center;
    align-items: center;

    img {
      width: 90%;
      height: 90%;
      border-radius: 50%;
      background-color: #eee;
      // box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }
  }
}

.login_form {
  position: absolute;
  bottom: 8%;
  width: 100%;
  padding: 0 15%;
  box-sizing: border-box;
}

.input {
  width: 50%;
}

.check_code {
  float: right;
  right: 0;
  // border: 1px solid #d3dce6;
  width: 43%;
}

.router_link {
  font-size: 12px;
  text-decoration: none;
  color: #66b1ff;
}
.router_link:hover {
  color: red;
  text-decoration: none;
}
.link_r {
  position: absolute;
  right: 15%;
  bottom: 0%;
}
.btns {
  margin-top: 5%;
  display: flex;
  // justify-content: flex-end;
  justify-content: center;
  .btn_login {
    width: 300px;
    font-size: 1.3em;
    letter-spacing: 0.4em;
  }
}

.carousel_box {
  width: 52%;
  position: absolute;
  left: 6%;
  // border:1px solid red;
  box-sizing: border-box;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  // 边框样式
  border-radius: 3px;
  background: #fbfbfb;
  border: 1px solid #ddd;
  padding: 5px;
}

.box {
  width: 90%;
  height: 80%;
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(240, 241, 166, 0.5);
  box-shadow: 0 2px 12px 0 rgba(252, 250, 250, 0.1);
  border-radius: 2%;
}
.image {
  background-size: cover; /* 将背景图片等比缩放填满整个容器 */
  // border:1px solid red;
  width: 100%;
  height: inherit; // 继承父节点的高度
}

.checkbox {
  font-size: 13px;
}
@media only screen and (max-width: 1300px) {
  .carousel_box {
    width: 0;
    display: none;
  }
  .login_box {
    height: 400px;
    width: 420px;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }
}
</style>
