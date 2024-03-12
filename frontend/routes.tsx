import MainLayout from "Frontend/views/MainLayout.js";
import { lazy } from "react";
import { createBrowserRouter, RouteObject } from "react-router-dom";
import RoleCrud from "Frontend/views/autos/auto-grid-role";
import UserGrid from "Frontend/views/autos/auto-grid-user";
import RelationUserRoleCrud from "Frontend/views/autos/auto-crud-relation-user-role";
import {protectRoutes} from "@hilla/react-auth";
import LoginView from "Frontend/views/authentication/LoginView";

const AboutView = lazy(async () => import("Frontend/views/about/AboutView.js"));

export const routes = protectRoutes([
  {
    element: <MainLayout/>,
    handle: {title: "Hilla CRM"},
    children: [
      {path: "/role", element: <RoleCrud/>, handle: {title: "角色"}},
      {path: "/user", element: <UserGrid/>, handle: {title: "用户"}},
      {
        path: "/relation-user-role",
        element: <RelationUserRoleCrud/>,
        handle: {title: "用户角色关系"},
      },
      {path: "/about", element: <AboutView/>, handle: {title: "About"}},
    ],
  },
  { path: '/login', element: <LoginView /> },
] as RouteObject[]);

export default createBrowserRouter(routes);
