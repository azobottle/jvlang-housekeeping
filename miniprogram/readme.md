# 小程序开发文档

把乱七八糟的都写这吧。

## 关于路由

> 我真的吐了，小程序内置的 tabbar 丑到爆。
>
> 然后我就用 custom-tab-bar ，结果他妈的一直闪烁（因为一直在创建新的 custom-tab-bar 组件，真是逆天设计）。
>
> 实在被恶心到了，决定自己糊路由。

我将自己封装的那个路由称为 vroute 。它非常轻量，简单到只需引入一个 [Behavior（其实就是mixin）](./mixin/vroute.ts) 即可开箱即用。可以点进去看看注释。

它是一个单页的虚拟路由封装。因此如果要在新的 page 中使用则需要自己定义一下组件逻辑（可以参考 `pages/index/index` 中的设计）。

它是与“Compose”相似的设计原则，即数据下传、事件上传，因此在单页应用中应当工作的很正常。

它可以在控制 this.data.current_tab 的页面组件（如 `pages/index/index` ）中通过 observers 去监听路由变化。