import { IEvent } from "./event";
import { _bind_store, _app_store_listeners } from "./store";

export type ToastOption = {
  summary: string
  detail?: string
  icon?: 'error',
  timeout?: number,
};

export type ToastState = ToastOption & { id: number, startTime: number, openDetail: boolean, close_manully: boolean }

export const DEFAULT_TIMEOUT = 5000;

var __next_toast_id = 1;

/**
 * 经典小吐司弹出提示。
 * 
 * 需要配合 toast_scope 组件一起使用，需要让 <toast_scope> 作为 Page 的最外层的元素。 
 */
export const toast = (_option: ToastOption) => {
  const sb = _bind_store(_app_store_listeners());
  const timeout = _option.timeout && _option.timeout > 0 ? _option.timeout : DEFAULT_TIMEOUT;
  const id = __next_toast_id++;
  sb.set("toasts", [...sb.read("toasts"), {
    id,
    ..._option,
    startTime: new Date().getTime(),
    openDetail: false,
    close_manully: false,
  }]);
  (function check_remove_on_timeout() {
    setTimeout(() => {
      const sb2 = _bind_store(_app_store_listeners());
      const toasts = sb.read("toasts");
      if (toasts.length <= 0) {
        return
      }
      const idx = toasts.findIndex(it => it.id === id);
      if (idx < 0) {
        return
      }
      if (toasts[idx].close_manully) {
        return
      }
      const res = [...toasts];
      res.splice(idx, 1);
      sb2.set("toasts", res);
    }, timeout)
  })()
}

export function toast_ui(e: IEvent<{ toastoption: ToastOption }>) {
  return toast(e.currentTarget.dataset.toastoption)
}