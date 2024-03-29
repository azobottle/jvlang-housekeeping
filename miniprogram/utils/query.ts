import { MiniprogramEndpoint } from "../generated/endpoints"
import { _app_store_listeners, _bind_store } from "./store";
import { app } from "./util";

export type AppEnv = {
  is_dev: boolean
  backend_server_host: string
}

var __read_env_promise: Promise<AppEnv>

async function __init_env(): Promise<AppEnv> {
  try {
    console.debug('[init_env] Start')
    const query_localhost = await (async () => {
      switch (wx.getAccountInfoSync().miniProgram.envVersion) {
        // 审核的时候可能是 develop 也可能是 trial
        case "develop":
          return await new Promise<boolean>((resolve) => {
            wx.showModal({
              title: '[初始化] 向本地服务器localhost:8088发起请求？',
              confirmText: '是的',
              cancelText: '不',
              success(res) {
                if (res.errMsg) {
                  console.debug('[init_env] ErrMsg in check env modal ! ', res.errMsg)
                }
                if (res.cancel) {
                  resolve(false)
                  return
                }
                if (res.confirm) {
                  resolve(true)
                  return
                }
                console.error('[init_env] Illegal modal return value !', res)
                resolve(false);
              },
              fail(err) {
                console.warn('[init_env] Failed in check env modal ! error is ', err)
                resolve(false)
              }
            })
          });
        case "trial":
          return false;
        case "release":
          return false;
        default:
          throw new Error('Unknown miniprogram env');
      }
    })();
    const backend_server_host = query_localhost ? 'http://localhost:8088' : 'http://localhost:8088'
    console.info(`[init_env] 后端服务器地址`, backend_server_host)
    const res = {
      is_dev: query_localhost,
      backend_server_host
    }
    app().globalData._env = res;
    return res;
  } finally {
    console.debug('[init_env] End')
  }

}

export async function read_env(): Promise<AppEnv> {
  const existed = app().globalData._env;
  if (existed != null) {
    console.debug('[init_env] already inited')
    return existed
  }
  if (__read_env_promise == null) {
    __read_env_promise = __init_env();
  }
  const res = await __read_env_promise
  return res;
}

export const backend_server_host = async () => (await read_env()).backend_server_host

export async function query_login() {
  const sb = _bind_store(_app_store_listeners());
  if (sb.read('user') != null) {
    console.warn('UI逻辑不合理。已经有登陆信息了，不应当要求用户登陆。')
  }

  const { code } = await wx.login();

  await MiniprogramEndpoint.login(code);

  // query()
  sb.set('user', {
    auth_token: '114514'
  })
}

