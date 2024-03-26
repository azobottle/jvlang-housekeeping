import { AppStore, Comp } from "./utils/store"

// app.ts
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

    // wx.loadFontFace({
    //   family: "HanZiZhiMeiFangSongGBK",
    //   source: "/miniprogram/font/HanZiZhiMeiFangSongGBK-MianFei(God-FangSongGBK-free)-2.ttf",
    //   global: true
    // })
    //   .then(() => console.debug("Success load font ."))
    //   .catch(err => console.error("Error on load font !", err))

    // // 登录
    // wx.login({
    //   success: res => {
    //     console.log(res.code)
    //     // 发送 res.code 到后台换取 openId, sessionKey, unionId
    //   },
    // })
  },
})