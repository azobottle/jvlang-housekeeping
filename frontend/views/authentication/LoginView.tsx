import { useState } from "react";
import { useAuth } from "Frontend/views/authentication/auth";
import { LoginOverlay } from "@hilla/react-components/LoginOverlay";
import { Navigate } from "react-router-dom";

export default function LoginView() {
  const { state, login } = useAuth();
  const [hasError, setError] = useState<boolean>();
  const [url, setUrl] = useState<string>();
  const [errorMessage, setErrorMessage] = useState<string>();

  if (state.user && url) {
    const path = new URL(url, document.baseURI).pathname;
    return <Navigate to={path} replace />;
  }

  return (
    <LoginOverlay
      opened
      error={hasError}
      i18n={{
        form: {
          title: "橘浪家政后台",
          username: "用户名",
          password: "密码",
          submit: "登陆",
          forgotPassword: "忘记密码",
        },
        errorMessage: {
          title: "登录失败",
          message: errorMessage ?? "未知错误",
        },
      }}
      noForgotPassword
      onLogin={async ({ detail: { username, password } }) => {
        const response = await fetch("/login", {
          method: "POST",
          headers: {
            "content-type": "application/json",
          },
          body: JSON.stringify({
            username,
            password,
          }),
        });
        const auth_token = response.headers.get("x-jvlang-set-auth");
        if (response.ok && auth_token) {
          setUrl("/");
          localStorage.setItem("auth_token", auth_token);
        } else {
          setError(true);
          setErrorMessage((await response.json())["message"]);
        }
      }}
    />
  );
}
