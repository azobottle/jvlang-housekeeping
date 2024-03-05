// custom-tab-bar/index.ts

Component({

  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    // 这个是从 ./app.json 中修改而来的。
    // 理应使用 resolveJsonModule 去导入，但是微信小程序的 pack 挂钩设计太辣鸡。
    // 所以只能丑陋的复制粘贴了...
    list: [
      {
        pagePath: "/pages/index/index",
        text: "主页",
        icon: 'home',
      },
      {
        pagePath: "/pages/shop/index",
        text: "介绍",
        icon: 'space_dashboard',
      },
      {
        pagePath: "/pages/eventstream/index",
        text: "活跃",
        icon: "favorite"
      },
      {
        pagePath: "/pages/order/index",
        text: "日程",
        icon: "event_note"
      },
      {
        pagePath: "/pages/mine/index",
        text: "我的",
        icon: "add_reaction"
      }
    ],
  },

  /**
   * 组件的方法列表
   */
  methods: {

  }
})