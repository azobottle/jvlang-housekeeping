import { EndpointRequestInit } from "./@hilla/frontend";
import { backend_server_host } from "../utils/query";
import { toast } from "../utils/toast";

export default {
  async call(endpoint: string, method: string, params: any, init: EndpointRequestInit): Promise<any> {
    const request = new Promise((resolve, reject) => {
      wx.request({
        url: `${backend_server_host}/connect/${endpoint}/${method}`,
        header: {
          "Accept": "application/json",
          "Content-Type": "application/json",
        },
        method: "POST",
        data: params !== void 0 ? JSON.stringify(params, (_, value) => value === void 0 ? null : value) : void 0,
        responseType: "text",
        dataType: "\u5176\u4ED6",
        timeout: 7500,
        success(res) {
          const { statusCode, data } = res;
          if (typeof data !== "string") {
            toast({
              summary: `🐞 出BUG了`,
              detail: `[${endpoint} ${method}] ${statusCode} 无法解析响应数据格式 , typeof data = ${typeof data} , data = ${data}`,
              icon: `error`,
            })
            reject(new Error("Response data not string"));
            return;
          }
          if (statusCode >= 400) {
            toast({
              summary: `😢 操作失败`,
              detail: `[${endpoint} ${method}] ${statusCode} ${data}`,
              icon: `error`,
            })
            reject(new Error("Failed response : " + JSON.stringify(res)));
            return;
          }
          try {
            const data2 = JSON.parse(data, (_, value) => value === null ? void 0 : value);
            resolve(data2);
          }
          catch (err) {
            toast({
              summary: `🐞 出BUG了`,
              detail: `[${endpoint} ${method}] 解析响应体json失败 : cause = ${String(err)} , data = ${data}`,
              icon: `error`,
            })
            reject(new Error(`Parse response body to json failed , body is ${data} , cause by : ${err}`));
          }
        },
        fail(err) {
          toast({
            summary: `😢 网络请求失败，未知错误`,
            detail: `[${endpoint} ${method}] ${err.errMsg}`,
            icon: `error`,
          })
          resolve(new Error("Request failed : " + err.errno + " - " + err.errMsg));
        }
      });
    });
    return await request;
  }
};
