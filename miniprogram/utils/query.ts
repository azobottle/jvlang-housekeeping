import { _bind_store, _app_store_listeners } from "./store"
import { MiniprogramEndpoint } from "../generated/endpoints"

export const backend_server_host = 'http://localhost:8088'

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

