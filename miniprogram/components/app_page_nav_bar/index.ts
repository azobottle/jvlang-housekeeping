// components/app_page_nav_bar/index.ts
Component({

  /**
   * 组件的属性列表
   */
  properties: {
    title: String,
    disable_back: {
      type: Boolean,
      value: false
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    height: '100px',
    show_back: false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    page_back: async function () {
      console.debug('back')
      await wx.navigateBack()
    }
  },

  lifetimes: {
    created: function () {
      wx.getSystemInfo()
        .then(si => this.setData({
          height: (si.statusBarHeight + 44) + "px"
        }))
    },
    ready() {
      this.setData({
        show_back: !this.properties.disable_back && getCurrentPages().length > 1
      })
    }
  }
})