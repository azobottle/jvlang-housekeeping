import { init_hilla_client } from "./utils/query"
import { AppStore, Comp } from "./utils/store"

init_hilla_client()

export interface AppOption extends IAppOption {
  globalData: {
    userInfo?: WechatMiniprogram.UserInfo,
    _app_store_listeners: Comp<AppStore>[]
  }
  userInfoReadyCallback?: WechatMiniprogram.GetUserInfoSuccessCallback,
}

App<AppOption>({
  globalData: {
    _app_store_listeners: []
  },
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  },
})
