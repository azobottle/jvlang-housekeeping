import { get_store_in_mixined } from "../../utils/store"

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
    login() {
      this.hasBehavior
      get_store_in_mixined(this).set('user', {
        auth_token: '114514'
      })
    },
  }
})