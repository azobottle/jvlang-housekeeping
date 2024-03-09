import { IEvent } from "../utils/event"

/**
 * 这个 mixin 主要是用于控制路由。使用方法可参考 app_nav_bar 组件 :
 * 
 * ```wxml
 * <view data-vroute="{{item.vroute}}" bind:tap="switch_vroute">
 *   {{ current_vroute }}
 * </view>
 * ```
 * 
 * switch_vroute 方法会触发 switch_vroute 事件，
 * 它会冒泡给父父父组件，因此只要使用得当的话一般无需使用 `bind:switch_vroute` 去捕获。
 * 
 */
module.exports = Behavior({
  properties: {
    current_vroute: String
  },
  data: {

  },
  methods: {
    switch_vroute(e: IEvent<{ vroute: string }>) {
      const { vroute } = e.currentTarget.dataset
      this.triggerEvent('switch_vroute', { vroute }, {
        bubbles: true
      })
    }
  }
})