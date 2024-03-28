import { IEvent } from "../../utils/event";
import { get_store_in_mixined } from "../../utils/store";
import { ToastState } from "../../utils/toast";

// components/toast_scope/index.ts
Component({
  behaviors: [require("../../mixin/store")],
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    tap_detail: async function (e: IEvent<{ toastid: number }>) {
      const { toastid } = e.currentTarget.dataset;
      if (toastid === undefined) {
        console.error('Missing toastid', e)
        throw new Error('Missing toastid');
      }
      const sb = get_store_in_mixined(this);
      const toasts: ToastState[] = JSON.parse(JSON.stringify(sb.read("toasts")));
      const t = toasts.find(it => it.id === toastid);
      if (!t) {
        console.warn('Not found id matched toast', {
          toastid,
          toasts
        })
        return
      }
      t.openDetail = !t.openDetail;
      t.close_manully = true;
      await sb.set("toasts", toasts)
    },
    remove_toast: async function (e: IEvent<{ toastid: number }>) {
      const { toastid } = e.currentTarget.dataset;
      if (toastid === undefined) {
        console.error('Missing toastid', e)
        throw new Error('Missing toastid');
      }
      const sb = get_store_in_mixined(this);
      const toasts: ToastState[] = JSON.parse(JSON.stringify(sb.read("toasts")));
      const idx = toasts.findIndex(it => it.id === toastid);
      if (idx < 0) {
        console.warn('Not found id matched toast', {
          toastid,
          toasts
        })
        return
      }
      toasts.splice(idx, 1)
      await sb.set("toasts", toasts)
    }
  }
})