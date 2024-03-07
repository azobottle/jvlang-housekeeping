// index.ts

import { AppOption } from "../../app"
import { IEventTrigged } from "../../utils/event"

// 获取应用实例
const app = getApp<AppOption>()

Component({
  data: {
    current_vroute: '123',
  },
  observers: {
    current_vroute(current_vroute: string) {
      console.debug('current_vroute changed : ', current_vroute)
    }
  },
  methods: {
    on_switch_vroute: async function (e: IEventTrigged<'switch_vroute', { vroute: string }>) {
      this.setData({
        current_vroute: e.detail.vroute
      })
    },
  },
})
