import MainLayout from "Frontend/views/MainLayout.js";
import { lazy } from "react";
import { createBrowserRouter, RouteObject } from "react-router-dom";
import { protectRoutes } from "@hilla/react-auth";
import LoginView from "Frontend/views/authentication/LoginView";
import { MyAutoCrud } from "./components/MyCrud";
import UserModel from "./generated/com/jvlang/housekeeping/pojo/entity/UserModel";
import { UserEndpoint, UserRoleEndpoint } from "./generated/endpoints";
import UserRoleModel from "./generated/com/jvlang/housekeeping/pojo/entity/UserRoleModel";

const AboutView = lazy(async () => import("Frontend/views/about/AboutView.js"));

export const level1_pages = [
  { path: "/", element: <div />, handle: { title: "首页" } },
  {
    path: "/user",
    handle: { title: "用户" },
    element: (
      <MyAutoCrud
        endpoint={UserEndpoint}
        model={UserModel}
        notDisplayColumns={["encodedPassword"]}
        customColumns={[
          {
            header: "角色",
            render: {
              custom_type: "span",
              text(m) {
                return (
                  m.roles
                    .valueOf()
                    ?.map((it) => it?.role)
                    ?.join(", ") ?? "(没有任何角色)"
                );
              },
            },
          },
        ]}
      />
    ),
  },
  {
    path: "/user-role",
    handle: { title: "用户角色" },
    element: <MyAutoCrud endpoint={UserRoleEndpoint} model={UserRoleModel} />,
  },
  { path: "/about", element: <AboutView />, handle: { title: "关于我们" } },
] as const;

export const routes = protectRoutes([
  {
    element: <MainLayout />,
    handle: { title: "Hilla CRM" },
    children: level1_pages,
  },
  { path: "/jvlang/login", element: <LoginView /> },
] as RouteObject[]);

export default createBrowserRouter(routes);
