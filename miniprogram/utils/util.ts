

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
