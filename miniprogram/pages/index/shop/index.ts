import { IEvent } from "../../../utils/event"

import { UserEndpoint } from "../../../generated/endpoints";

// pages/index/shop/index.ts
Component({
  behaviors: [require("../../../mixin/vroute")],
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
    async to_good_page(e: IEvent<{ goodid: string }>) {
      const { goodid } = e.currentTarget.dataset;
      console.debug('to good page', goodid)
      await wx.navigateTo({
        url: `/pages/good/index?goodid=${goodid}`
      })
    },
  },

  lifetimes: {
    ready: async function () {
      console.debug('user endpoint get', await UserEndpoint.get(1))
    }
  }
})