import { get_store_in_mixined } from "../../utils/store"
import {  backend_server_wx_login_url } from "../../utils/query"

// components/need_login/index.ts
Component({
  behaviors: [require("../../mixin/store")],

  /**
   * 组件的属性列表
   */
  properties: {
  },

  /**
   * 组件的初始数据
   */
  data: {
  },

  /**
   * 组件的方法列表
   */
  methods: {
    login: async function () {
      const s = get_store_in_mixined(this);
      if (s.read('user') != null) {
        console.warn('UI逻辑不合理。已经有登陆信息了，不应当要求用户登陆。')
      }

      const login_result = await wx.login()

      // const req_task = request({
      //   url: backend_server_wx_login_url(),
      //   data: {
      //     code: login_result.code
      //   }
      // })


      // query()
      s.set('user', {
        auth_token: '114514'
      })
    },
  }
})