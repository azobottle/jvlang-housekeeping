import MainLayout from "Frontend/views/MainLayout.js";
import { lazy } from "react";
import { createBrowserRouter, RouteObject } from "react-router-dom";
import { protectRoutes } from "@hilla/react-auth";
import LoginView from "Frontend/views/authentication/LoginView";
import routes_crud from "./routes_crud";

const AboutView = lazy(async () => import("Frontend/views/about/AboutView.js"));

export const level1_pages = [
  { path: "/", element: <div />, handle: { title: "首页" } },
  { path: "/about", element: <AboutView />, handle: { title: "关于我们" } },
  ...routes_crud,
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
