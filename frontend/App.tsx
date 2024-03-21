import router from "Frontend/routes.js";

import { RouterProvider } from "react-router-dom";
import { AuthProvider } from "Frontend/views/authentication/auth";
import client from "Frontend/generated/connect-client.default";
import "toastify-js/src/toastify.css";
import Toastify from "toastify-js";

client.middlewares = [
  async function (context, next) {
    const { request } = context;
    const existed_auth_token = localStorage.getItem("auth_token");
    if (existed_auth_token) {
      request.headers.set("Authorization", existed_auth_token);
    }

    const response = await next(context);

    if (response.status == 401) {
      window.location.assign("/jvlang/login");
      throw "never return";
    }

    if (response.status >= 400) {
      try {
        const message = JSON.parse(await response.clone().text())["message"];
        Toastify({
          text: message,
          duration: 3000,
          newWindow: true,
          close: true,
          gravity: "top", // `top` or `bottom`
          position: "right", // `left`, `center` or `right`
          stopOnFocus: true, // Prevents dismissing of toast on hover
          style: {
            // background: "linear-gradient(to right, #00b09b, #96c93d)",
          },
          onClick: function () {}, // Callback after click
        }).showToast();
      } catch (err) {
        console.error("Can't parse json for response body", response);
      }
    }

    return response;
  },
];

export default function App() {
  return (
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  );
}
