import MainLayout from "Frontend/views/MainLayout.js";
import { lazy } from "react";
import { createBrowserRouter, RouteObject } from "react-router-dom";
import UserGrid from "Frontend/views/autos/auto-crud-user";
import { protectRoutes } from "@hilla/react-auth";
import LoginView from "Frontend/views/authentication/LoginView";
import ServiceCrud from "Frontend/views/autos/auto-crud-service";
import RelationShifuServiceEndpointCrud from "Frontend/views/autos/auto-crud-relation-shifu-service";
import UserRoleCrud from "Frontend/views/autos/auto-crud-user-role";
import ScheduleCrud from "Frontend/views/autos/auto-crud-schedule";
import OrderGrid from "Frontend/views/autos/auto-grid-order";

const AboutView = lazy(async () => import("Frontend/views/about/AboutView.js"));

export const level1_pages = [
  { path: "/", element: <div></div>, handle: { title: "首页" } },
  { path: "/user", element: <UserGrid />, handle: { title: "用户" } },
  {
    path: "/relation-user-role",
    element: <UserRoleCrud />,
    handle: { title: "角色" },
  },
  { path: "/service", element: <ServiceCrud />, handle: { title: "服务" } },
  {
    path: "/relation-shifu-service",
    element: <RelationShifuServiceEndpointCrud />,
    handle: { title: "师傅服务关系" },
  },
  {
    path: "/schedule",
    element: <ScheduleCrud />,
    handle: { title: "排班" },
  },
  { path: "/order", element: <OrderGrid />, handle: { title: "订单" } },
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
