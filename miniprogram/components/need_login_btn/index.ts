import { query_login } from "../../utils/query"

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
      await query_login()
    },
  }
})