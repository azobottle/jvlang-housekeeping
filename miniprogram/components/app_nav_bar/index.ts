const list = [
  {
    vroute: "home",
    text: "主页",
    icon: '/assets/mdi/home_outlined.svg',
    icon_checked: '/assets/mdi/home_two_tone.svg'
  },
  {
    vroute: "shop",
    text: "分类",
    icon: '/assets/mdi/space_dashboard_outlined.svg',
    icon_checked: '/assets/mdi/space_dashboard_two_tone.svg'
  },
  {
    vroute: "eventstream",
    text: "动态",
    icon: '/assets/mdi/favorite_border_outlined.svg',
    icon_checked: '/assets/mdi/favorite_two_tone.svg'
  },
  {
    vroute: "order",
    text: "订单",
    icon: '/assets/mdi/event_note_outlined.svg',
    icon_checked: '/assets/mdi/event_note_two_tone.svg'
  },
  {
    vroute: "mine",
    text: "我的",
    icon: '/assets/mdi/add_reaction_outlined.svg',
    icon_checked: '/assets/mdi/add_reaction_two_tone.svg'
  }
] as const

// components/appnavbar/index.ts
Component({
  behaviors: [
    require("../../mixin/vroute")
  ],
  /**
   * 组件的属性列表
   */
  properties: {
  },

  /**
   * 组件的初始数据
   */
  data: {
    list,
  },

  /**
   * 组件的方法列表
   */
  methods: {
  }
})