import { EndpointRequestInit } from "./@hilla/frontend";

export default {
  async call(endpoint: string, method: string, params: any, init: EndpointRequestInit): Promise<any> {
    // const csrfHeaders = getCsrfTokenHeadersForEndpointRequest(document);

    const request = new Promise((resolve, reject) => {
      wx.request({
        url: `http://localhost:8088/connect/${endpoint}/${method}`,
        header: {
          "Accept": "application/json",
          "Content-Type": "application/json",
        },
        method: "POST",
        data: params !== void 0 ? JSON.stringify(params, (_, value) => value === void 0 ? null : value) : void 0,
        responseType: 'text',
        dataType: '其他',
        timeout: 7500,
        success(res) {
          const { statusCode, data } = res;
          if (typeof data !== 'string') {
            reject(new Error('Response data not string'));
            return
          }
          if (statusCode >= 400) {
            reject(new Error('Failed response : ' + JSON.stringify(res)));
            return
          }
          try {
            const data2 = JSON.parse(data, (_, value) => value === null ? void 0 : value);
            resolve(data2)
          } catch (err) {
            reject(new Error(`Parse response body to json failed , body is ${data} , cause by : ${err}`))
          }
        },
        fail(err) {
          resolve(new Error('Request failed : ' + err.errno + ' - ' + err.errMsg))
        }
      })
    });

    return await request;

    // {
    //   body: params !== void 0 ? JSON.stringify(params, (_, value) => value === void 0 ? null : value) : void 0,
    //   headers,
    //   method: "POST"
    // };



    // const initialContext = {
    //   endpoint,
    //   method,
    //   params,
    //   request
    // };
    // async function responseHandlerMiddleware(context, next) {
    //   const response = await next(context);
    //   await assertResponseIsOk(response);
    //   const text = await response.text();
    //   return JSON.parse(text, (_, value) => value === null ? void 0 : value);
    // }
    // async function fetchNext(context) {
    //   $wnd.Vaadin?.connectionState?.loadingStarted();
    //   try {
    //     const response = await fetch(context.request, { signal: init?.signal });
    //     $wnd.Vaadin?.connectionState?.loadingFinished();
    //     return response;
    //   } catch (error) {
    //     if (error instanceof Error && error.name === "AbortError") {
    //       $wnd.Vaadin?.connectionState?.loadingFinished();
    //     } else {
    //       $wnd.Vaadin?.connectionState?.loadingFailed();
    //     }
    //     return Promise.reject(error);
    //   }
    // }
    // const middlewares = [responseHandlerMiddleware, ...this.middlewares];
    // const chain = middlewares.reduceRight(
    //   (next, middleware) => (
    //     // Compose and return the new chain step, that takes the context and
    //     // invokes the current middleware with the context and the further chain
    //     // as the next argument
    //     async (context) => {
    //       if (typeof middleware === "function") {
    //         return middleware(context, next);
    //       }
    //       return middleware.invoke(context, next);
    //     }
    //   ),
    //   // Initialize reduceRight the accumulator with `fetchNext`
    //   fetchNext
    // );
    // return chain(initialContext);
  }
}