import { AppOption } from "../app";
import { _app_store_listeners, _bind_store } from "./store";

export type Awaited<T> = T extends null | undefined ? T : // special case for `null | undefined` 
  // when not in `--strictNullChecks` mode
  T extends object & { then(onfulfilled: infer F, ...args: infer _): any } ?
  // `await` only unwraps object types with a callable `then`. 
  // Non-object types are not unwrapped
  F extends ((value: infer V, ...args: infer _) => any) ?
  // if the argument to `then` is callable, extracts the first argument
  Awaited<V> : // recursively unwrap the value
  never : // the argument to `then` was not callable
  T; // non-object or non-thenable


export function app() {
  return getApp<AppOption>()
}

export function remove_item<T extends any>(arr: Array<T>, value: T): boolean {
  const index = arr.indexOf(value);
  if (index > -1) {
    arr.splice(index, 1)
    return true;
  }
  return false;
}

export const throttle = (that: any, callback: Function, time: number) => {
  let timer: null | number = null;
  return function () {
    if (timer) {
      return
    }
    let args = arguments;
    timer = setTimeout(() => {
      callback.apply(that, args);
      timer = null;
    }, time)
  }
}

export async function read_system_info(): Promise<WechatMiniprogram.SystemInfo> {
  return await new Promise<WechatMiniprogram.SystemInfo>((resolve, reject) => {
    wx.getSystemInfoAsync({
      success(res) {
        resolve(res)
      },
      fail(err) {
        reject(err)
      }
    })
  }).then(res => {
    _bind_store(_app_store_listeners()).set("system_info", res)
    return res;
  })
}
