import MainLayout from "Frontend/views/MainLayout.js";
import {lazy} from "react";
import {createBrowserRouter, RouteObject} from "react-router-dom";
import RoleCrud from "Frontend/views/autos/auto-crud-role";
import UserGrid from "Frontend/views/autos/auto-crud-user";
import RelationUserRoleCrud from "Frontend/views/autos/auto-crud-relation-user-role";
import {protectRoutes} from "@hilla/react-auth";
import LoginView from "Frontend/views/authentication/LoginView";
import ServiceCrud from "Frontend/views/autos/auto-crud-service";
import RelationShifuServiceEndpointCrud from "Frontend/views/autos/auto-crud-relation-shifu-service";
import ScheduleCrud from "Frontend/views/autos/auto-crud-schedule";
import OrderGrid from "Frontend/views/autos/auto-grid-order";

const AboutView = lazy(async () => import("Frontend/views/about/AboutView.js"));

export const routes = protectRoutes([
    {
        element: <MainLayout/>,
        handle: {title: "Hilla CRM"},
        children: [
            {path: "/role", element: <RoleCrud/>, handle: {title: "角色"}},
            {path: "/user", element: <UserGrid/>, handle: {title: "用户"}},
            {path: "/relation-user-role", element: <RelationUserRoleCrud/>, handle: {title: "用户角色关系"}},
            {path: "/service", element: <ServiceCrud/>, handle: {title: "服务"}},
            {
                path: "/relation-shifu-service",
                element: <RelationShifuServiceEndpointCrud/>,
                handle: {title: "师傅服务关系"}
            },
            {path: "/schedule", element: <ScheduleCrud/>, handle: {title: "排班"}},
            {path: "/order", element: <OrderGrid/>, handle: {title: "订单"}},
            {path: "/about", element: <AboutView/>, handle: {title: "About"}},
        ],
    },
    {path: '/login', element: <LoginView/>},
] as RouteObject[]);

export default createBrowserRouter(routes);
