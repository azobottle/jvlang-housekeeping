/**
 * 为什么不用市面上的全局状态库呢？
 * 因为我讨厌让他们教我写代码（他们的API像答辩）。
 * 
 * 总共就100行不到的事情为啥要引入一个答辩依赖呢。
 */
import { AppOption } from "../app";
import { remove_item } from "./util";
import { ToastState } from "./toast"

export interface StoreBinder<D extends Record<string, any>> {
  set<K extends keyof D>(key: K, value: D[K]): Promise<void>

  read<K extends keyof D>(key: K): Readonly<D[K]>
}

export type Comp<D, D2 = { store: D }> = {
  data: D2
  setData: (data: D2, callback?: () => void) => void
};

export function _bind_store<D>(listeners: readonly Comp<D>[]): StoreBinder<D> {
  return {
    read(k) {
      return listeners[0].data.store[k]
    },
    async set(k, v) {
      console.debug('[store] set store from binder : ', { k, v, listeners })
      await Promise.all(
        listeners.map(listener => {
          const d = JSON.parse(JSON.stringify(listener.data.store));
          d[k] = v;
          const d2 = {
            store: d
          };
          return new Promise<void>((resolve, reject) => {
            try {
              listener.setData(d2, () => {
                resolve();
              });
            } catch (err) {
              reject(err);
            }
          });
        })
      );
    }
  }
}

/**
 * 应用全局属性定义于此。
 */
export const _app_store_default_data = () => ({
  user: null as null | {
    auth_token: string,
  },
  toasts: [] as ToastState[]
});

export type AppStore = ReturnType<typeof _app_store_default_data>;

export const _app_store_listeners = () => getApp<AppOption>().globalData._app_store_listeners

export const _store_mixin = Behavior({
  properties: {
  },
  data: {
    store: _app_store_default_data()
  },
  methods: {
    // store_set<K extends keyof AppStore>(key: K, value: AppStore[K]) {
    //   return _bind_store(_app_store_listeners()).set(key, value)
    // }
  },
  lifetimes: {
    attached() {
      const listeners = _app_store_listeners();
      listeners.push(this)
      console.debug('[store] push to store listener ', { listeners, this_: this })
    },
    detached() {
      const listeners = _app_store_listeners();
      if (!remove_item(listeners, this)) {
        console.warn('[store] Why remove store listener failed ? Not found item ', this)
      }
      console.debug('[store] remove from store listener ', { listeners, this_: this })
    }
  }
});

/**
 * 使用方法:
 * 
 * 1. 需要在组件或Page的 behaviors 中引入 miniprogram/mixin/store.ts 例如:
 * ```
 * behaviors: [require("../../../mixin/store")],
 * ```
 * 
 * 2. 在组件或Page中调用即可。例如:
 * 
 * ```
 * methods: {
 *   logout() {
 *     get_store_in_mixined(this).set('user', null)
 *   }
 * }
 * ```
 * 
 * @param that 组件或Page的this
 */
export function get_store_in_mixined(that: {
  // FXXKing the miniprogram stdlib author who written wrong type define .
  hasBehavior: (behavior: WechatMiniprogram.Behavior.BehaviorIdentifier) => /** it should return boolean instead of void  */ any
}) {
  const checked = that.hasBehavior(_store_mixin);
  // console.debug('[store] check hasBehavior', checked)
  if (!checked) {
    throw new Error('[store] Require use behavior \'store\' !')
  }
  return _bind_store(_app_store_listeners());
}