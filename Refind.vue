<template>
  <div class="back">
    <div class="card_container">
      <el-card class="card">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-page-header @back="goback" content="登陆页面"></el-page-header>
          </el-col>
          <el-col :span="12">
            <div class="cen">密码找回</div>
          </el-col>
          <el-col :span="6"></el-col>
        </el-row>

        <!-- 步骤条区域 -->
        <el-steps
          :active="active"
          process-status="finish"
          finish-status="success"
          align-center
        >
          <el-step title="填写账号" icon="el-icon-edit"></el-step>
          <el-step title="身份验证" icon="el-icon-upload"></el-step>
          <el-step title="设置新密码" icon="el-icon-s-data"></el-step>
          <el-step title="完成" icon="el-icon-success"></el-step>
        </el-steps>
        <div class="step_box">
          <el-form
            label-width="100px"
            :model="refindForm"
            :rules="refindFormRules"
            ref="refindFormRef"
          >
            <div class="active1" v-if="active === 0">
              <!-- 乱码验证区域 -->
              <span class="email_tip">操作验证</span>
              <!-- <el-form-item label="用户名" prop="username">
                                <el-input v-model="refindForm.username" placeholder="请输入唯一的用户名" prefix-icon="iconfont icon-user"></el-input>
                            </el-form-item> -->
              <el-form-item label="验证码" prop="code">
                <el-input
                  v-model="refindForm.code"
                  placeholder="请输入正确的验证码"
                  prefix-icon="el-icon-s-ticket"
                  style="width: 78%"
                ></el-input>
              </el-form-item>
              <div
                class="check_code"
                style="width: 125px; position: absolute; left: 31%"
                @click="refreshCode()"
              >
                <s-identify :identifyCode="identifyCode"></s-identify>
              </div>
            </div>
            <div class="active2" v-if="active === 1">
              <!-- 邮箱验证区域 -->
              <span class="email_tip">邮箱例子：159****@qq.com</span>
              <el-form-item label="绑定邮箱" prop="email">
                <el-input
                  v-model="refindForm.email"
                  placeholder="请输入用户绑定的邮箱地址"
                  prefix-icon="el-icon-s-promotion"
                ></el-input>
              </el-form-item>
              <el-form-item label="邮箱验证码" prop="emailCode">
                <el-input
                  v-model="refindForm.emailCode"
                  placeholder="请输入正确的邮箱验证码"
                  prefix-icon="el-icon-question"
                  style="width: 60%"
                ></el-input>
                <!-- 发送邮箱验证码 -->
                <el-button
                  type="warning"
                  @click="sendCode"
                  :disabled="msgBtnDisabled"
                  class="send"
                  >{{ sendBtnTxt }}</el-button
                >
              </el-form-item>
            </div>
            <div class="active3" v-if="active === 2">
              <!-- 重置密码区域 -->
              <span class="email_tip">设置新密码</span>
              <el-form-item label="新密码" prop="password">
                <el-input
                  v-model="refindForm.password"
                  placeholder="请输入新密码"
                  prefix-icon="iconfont icon-3702mima"
                  show-password
                ></el-input>
              </el-form-item>
              <el-form-item label="确认新密码" prop="rePassword">
                <el-input
                  v-model="refindForm.rePassword"
                  placeholder="请输入确认新密码"
                  prefix-icon="el-icon-s-opportunity"
                  show-password
                ></el-input>
              </el-form-item>
            </div>
            <div v-if="active === 3">
              <!-- 延迟加载区域 -->
              <el-form-item
                class="load"
                v-loading="Loading"
                element-loading-text="拼命加载中..."
                element-loading-spinner="el-icon-loading"
              >
              </el-form-item>
            </div>
            <div v-if="active === 4">
              <!-- 成功提示区域 -->
              <div class="tip_success">
                <i class="el-icon-success icon_success"></i>
                <p>感谢你的使用<br />密码已经修改成功了!</p>
              </div>
            </div>

            <!-- 按钮区域 -->
            <div class="deployBtn">
              <el-button
                type="primary"
                class="btn_left"
                @click="prev"
                v-if="active == 1 || active == 2"
                >上一步</el-button
              >
              <el-button
                type="primary"
                class="btn_next"
                @click="next()"
                v-if="active == 0 || active == 1"
                >下一步</el-button
              >
              <el-button
                type="primary"
                class="btn_next"
                @click="refind"
                v-if="active == 2"
                >提交修改</el-button
              >
              <el-button
                type="success"
                class="btn_next"
                @click="goback"
                v-if="active == 4"
                >立即返回</el-button
              >
            </div>
          </el-form>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
// 引入验证码组件
import SIdentify from '@/components/sIdentify.vue'

export default {
  components: { SIdentify },

  data () {
    // 验证码校验变量
    var checkcodeValid = (rule, value, callback) => {
      if (value !== '' && value !== this.identifyCodeCheck) {
        this.refreshCode()
        callback(new Error('验证码不正确！'))
      }
      callback()
    }
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
    // 邮箱验证码校验
    var validateEamilCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请填写验证码'))
      } else if (value !== '' && Number(value) !== Number(this.checkEmailCode)) {
        // console.log('value' + value)
        // console.log('checkcode' + this.checkCode)
        // console.log(value !== this.checkCode) // 这里是true，说明这两个是不同的实例，所以使用Number类型比较
        callback(new Error('验证码无效！'))
      }
      callback()
    }
    // 确认密码验证
    var validateRePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('确认密码不能为空'))
      } else if (value !== '' && value !== this.refindForm.password) {
        // console.log('确认密码验证')
        callback(new Error('确认密码与密码不一致！'))
      }
      callback()
    }

    return {
      active: 0,
      labelPosition: 'top',
      identifyCode: '', // 显示的验证码
      identifyCodeCheck: '', // 实际比较的验证码
      identifyCodes: '123456789abcdwerwshdjeJKDHRJHKPLMKQ', // 验证码字符集合
      Loading: true,

      checkEmailCode: '', // 用于验证邮箱验证码

      sendBtnTxt: '发送验证码', // 按钮显示内容
      msgBtnDisabled: false, // 按钮禁用状态
      timer: null, // 计时器
      countdown: 30, // 时间
      // 找回密码数据表单
      refindForm: {
        username: '爱吃狗粮的小忆',
        password: '',
        rePassword: '',
        email: '',
        emailCode: '',
        code: '' // 验证码框
      },
      // 找回密码数据表单验证规则
      refindFormRules: {
        username: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
        ],
        rePassword: [
          { required: true, message: '确认密码不能为空', trigger: 'blur' },
          { validator: validateRePassword, trigger: 'blur' },
          { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请填写电子邮箱', trigger: 'blur' },
          { validator: validateEmail, trigger: 'blur' }
        ],
        emailCode: [
          { required: true, message: '请填写邮箱验证码', trigger: 'blur' },
          { validator: validateEamilCode, trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { validator: checkcodeValid, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    prev () {
      if (--this.active < 0) { this.active = 0 }
    },
    next () {
      switch (this.active) {
        case 0:
          if (this.refindForm.code === '') { return this.$message.warning('验证码未填写！') }
          if (this.identifyCodeCheck !== this.refindForm.code && this.refindForm.code !== '') { return this.$message.warning('验证码错误！') }
          break
        case 1:
          var regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
          if (this.refindForm.email === '') return this.$message.warning('邮箱地址未填写！')
          if (this.refindForm.email !== '' && this.refindForm.emailCode === '') return this.$message.warning('邮箱验证码不能为空！')
          if (this.refindForm.emailCode !== '' && Number(this.refindForm.emailCode) !== Number(this.checkEmailCode)) return
          if (this.refindForm.email !== '' && !regEmail.test(this.refindForm.email)) return
          break
      }
      // console.log(this.active)
      if (this.active++ > 3) { this.active = 0 }
    },
    refind () {
      // if (this.refindForm.password === '' || this.refindForm.rePassword === '') { return this.$message.warning('密码和确认密码不能为空！') }
      // if (this.refindForm.password !== '' && this.refindForm.rePassword !== '' && this.refindForm.password.length >= 6 && this.refindForm.rePassword === this.refindForm.password) {
      //   this.active += 1
      //   // http请求处理

      //   // 显示加载延迟效果
      //   this.LoadingTo()
      // }
      this.$refs.refindFormRef.validate(async valid => {
        if (!valid) return
        // http请求处理
        const { data: res } = await this.$http.post('/user/findPassword', {
          email: this.refindForm.email,
          code: this.refindForm.emailCode,
          password: this.refindForm.password
        })
        // console.log(res)
        if (res.code !== 200) return this.$message.error('找回密码失败！')

        this.active += 1
        // 显示加载延迟效果
        this.LoadingTo()
      })
    },
    // 返回登陆页面
    goback () {
      this.$router.push('/login')
    },
    // 延迟加载效果
    LoadingTo () {
      setTimeout(() => {
        this.Loading = !this.Loading
        this.active += 1
        this.$message.success('密码修改成功！')
      }, 2500)
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
    },
    // 发送验证码
    async sendCode () {
      // const { data: res } = await this.$http.post(`/email/sendFindPasswordCode/${this.refindForm.email}`)

      const { data: res } = await this.$http.post('/email/sendFindPasswordCode', { email: this.refindForm.email })
      if (res.code !== 200) return this.$message.warning('该邮箱未注册！')

      // console.log(res)
      this.$message.success('发送验证码成功！')
      console.log('验证码：' + res.data.code) // 测试使用，获得验证码
      this.$notify({
        title: '【个人博客系统】',
        message: `您的验证码：${res.data.code}，验证码十分钟内有效，请妥善保管`,
        type: 'success',
        duration: 0
      })
      // 赋值给 checkEmailCode,做预校验
      this.checkEmailCode = res.data.code
      this.getCode()
    },
    // 发送验证码30秒倒计时
    getCode () {
      // console.log(this.sendCode())
      // // 发送验证码
      // if (this.sendCode() !== true) {
      //   return
      // }

      // 倒计时
      if (!this.timer) {
        this.timer = setInterval(() => {
          if (this.countdown > 0 && this.countdown <= 30) {
            this.countdown--
            if (this.countdown !== 0) {
              this.sendBtnTxt = '重新发送(' + this.countdown + ')'
              this.msgBtnDisabled = true
            } else {
              clearInterval(this.timer)
              this.sendBtnTxt = '获取验证码'
              this.countdown = 30
              this.timer = null
              this.msgBtnDisabled = false
            }
          }
        }, 1000)
      }
    }
  },
  created () {
    this.refreshCode()
  }
}
</script>

<style lang="less" scoped>
.back {
  height: 100%;

  // background-image: url("../assets/img/refind_bg.jpg");
  // background-size:auto;/* 默认值，不改变背景图片的高度和宽度 */
  // background-size:100px 50px;/* 第一个值为宽，第二个值为高，当设置一个值时，将其作为图片宽度来等比缩放 */

  background: linear-gradient(to bottom right, #3ba7e6, #bae24d);
  background-size: cover; /* 将背景图片等比缩放填满整个容器 */
  display: flex;
  justify-content: center;
  align-items: center;
}
.card_container {
  // border:1px solid black;
  width: 90%;
  height: 80%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.card {
  width: 80%;
  height: 100%;
  box-sizing: border-box;
  opacity: 0.89;
  .el-page-header {
    margin-bottom: 60px;
  }
}
.step_box {
  height: 390px;
  width: 75%;
  margin: 0 auto;
  border: 1px solid #409eff;
  position: relative;
  margin-top: 1%;

  display: flex;
  justify-content: center;
  align-items: center;
}
.deployBtn {
  margin-top: 25px;
  position: absolute;
  display: flex;
  justify-content: center;
  bottom: 0;
  left: 50%;
  transform: translate(-50%, -50%);
  .btn_next {
    width: 250px;
  }
}

.el-form {
  // border:1px solid red;
  width: 80%;
  height: 350px;
  position: absolute;
  top: 3%;
  display: flex;
  justify-content: center;
  padding-top: 8%;

  box-sizing: border-box;
  .load {
    width: 4em;
    height: 4em;
    font-size: 2.2em;
  }
  .input {
    width: 50%;
  }
  .el-form-item {
    width: 100%;
    padding-left: 15%;
    padding-right: 20%;
  }
  div {
    width: 100%;
  }
}

.email_tip {
  position: relative;
  left: 20%;
  bottom: 20px;
  font-size: 18px;
  color: #a5a5a5;
}
.tip_success {
  text-align: center;
  .icon_success {
    font-size: 8em;
    color: rgb(68, 196, 36);
  }
  p {
    margin-top: 20px;
    font-size: 1em;
    color: #999;
  }
}
.cen {
  font-size: 1.9em;
  text-align: center;
  color: #409eff;
  letter-spacing: 0.5em;
}
.input_send {
  width: 40%;
}
.send {
  position: absolute;
  right: 0;
}
.check_code {
  margin-top: 5px;
  //   float: right;
  //   right: 0;
  border: 1px solid #d3dce6;
  border-radius: 5px;
}
</style>
