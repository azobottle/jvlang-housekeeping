import { IEvent } from "../../../utils/event"

import { GoodCatalogEndpoint, UserEndpoint } from "../../../generated/endpoints";
import GoodCatalog from "../../../../frontend/generated/com/jvlang/housekeeping/pojo/entity/GoodCatalog";
import { ft1 } from "../../../utils/util";

// pages/index/shop/index.ts
Component({
  behaviors: [require("../../../mixin/vroute")],
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    list: [] as GoodCatalog[],
    parentCatalogId: null as null | number,
    checked_id: null as null | number,
    // ↓  Computed properties
    current_list: [] as GoodCatalog[],
  },

  observers: {
    "list, parentCatalogId": function (list: GoodCatalog[], parentCatalogId: null | number) {
      const current_list = list.filter(it => it.parentCatalogId == parentCatalogId);
      const parent = list.find(it => it.id == parentCatalogId);
      if (parent) {
        current_list.splice(0, 0, parent)
      }
      console.debug('current_list', current_list)
      this.setData({
        current_list,
        // checked_id: [...current_list].shift()?.id
      })
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    click_tab(e: IEvent<{ item: GoodCatalog }>) {
      const target = e.currentTarget.dataset.item;
      if (this.data.parentCatalogId == target.id) {
        this.setData({
          parentCatalogId: ft1(target.parentCatalogId, null)
        }, () => {
          console.debug('ddd', this.data)
        })
      } else if (this.data.list.find(it => it.parentCatalogId == target.id)) {
        this.setData({
          parentCatalogId: ft1(target.id, null)
        })
      } else {
        this.setData({
          checked_id: ft1(target.id, null)
        })
      }
    },
    async to_good_page(e: IEvent<{ goodid: string }>) {
      const { goodid } = e.currentTarget.dataset;
      console.debug('to good page', goodid)
      await wx.navigateTo({
        url: `/pages/good/index?goodid=${goodid}`
      })
    },
  },

  lifetimes: {
    created() {
      (async () => {
        const list = await GoodCatalogEndpoint.listAll();
        this.setData({
          list,
          // checked_id: list.filter(it => it.parentCatalogId == undefined).shift()?.id
        })
      })()
    },
    ready: async function () {
      console.debug('user endpoint get', await UserEndpoint.me())
    }
  }
})