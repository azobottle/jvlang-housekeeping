const list = [
  {
    vroute: "home",
    text: "主页",
    icon: '/assets/mdi/home_outlined.svg',
    icon_checked: '/assets/mdi/home_two_tone.svg'
  },
  {
    vroute: "shop",
    text: "发现",
    icon: '/assets/mdi/space_dashboard_outlined.svg',
    icon_checked: '/assets/mdi/space_dashboard_two_tone.svg'
  },
  {
    vroute: "shopping_cart",
    text: "购物袋",
    icon: '/assets/mdi/shopping_bag_black_24dp_outline.svg',
    icon_checked: '/assets/mdi/shopping_bag_black_24dp_two_tone.svg'
  },
  {
    vroute: "message",
    text: "消息",
    icon: '/assets/mdi/textsms_black_24dp.svg',
    icon_checked: '/assets/mdi/textsms_black_24dp_two_tone.svg'
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