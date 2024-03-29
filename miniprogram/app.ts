import { AppEnv } from "./utils/query"
import { AppStore, Comp } from "./utils/store"

export interface AppOption extends IAppOption {
  globalData: {
    userInfo?: WechatMiniprogram.UserInfo,
    _app_store_listeners: Comp<AppStore>[]
    _env: null | AppEnv
  }
  userInfoReadyCallback?: WechatMiniprogram.GetUserInfoSuccessCallback,
}

App<AppOption>({
  globalData: {
    _app_store_listeners: [],
    _env: null,
  },
  onLaunch(option) {
    console.debug('[app] on launch :', option)
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  },
  onShow() {

  },
  onHide() {

  }
})
