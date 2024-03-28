import { get_store_in_mixined } from "../../../utils/store"

// pages/index/mine/index.ts
Component({
  behaviors: [require("../../../mixin/store")],
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    a: ''
  },

  /**
   * 组件的方法列表
   */
  methods: {
    logout() {
      const s = get_store_in_mixined(this);
      console.debug('User logout : ', s.read('user'))
      s.set('user', null)
    }
  }
})