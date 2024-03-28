import { EndpointRequestInit } from "./@hilla/frontend";

export default {
  async call(endpoint: string, method: string, params: any, init: EndpointRequestInit): Promise<any> {
    // if (arguments.length < 2) {
    //   throw new TypeError(`2 arguments required, but got only ${arguments.length}`);
    // }
    // const csrfHeaders = getCsrfTokenHeadersForEndpointRequest(document);
    // const headers = {
    //   Accept: "application/json",
    //   "Content-Type": "application/json",
    //   ...csrfHeaders
    // };
    // const request = new Request(`${this.prefix}/${endpoint}/${method}`, {
    //   body: params !== void 0 ? JSON.stringify(params, (_, value) => value === void 0 ? null : value) : void 0,
    //   headers,
    //   method: "POST"
    // });
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