import MainLayout from 'Frontend/views/MainLayout.js';
import { lazy } from 'react';
import { createBrowserRouter, RouteObject } from 'react-router-dom';
import RoleGrid from "Frontend/views/autos/auto-grid-role";
import UserGrid from "Frontend/views/autos/auto-grid-user";
// import RelationUserRoleCrud from "Frontend/views/autos/auto-crud-relation-user-role";

const AboutView = lazy(async () => import('Frontend/views/about/AboutView.js'));

export const routes = [
  {
    element: <MainLayout />,
    handle: { title: 'Hilla CRM' },
    children: [
      { path: '/role', element: <RoleGrid />, handle: { title: '角色' } },
//       { path: '/user', element: <UserGrid />, handle: { title: '用户' } },
      // { path: '/relation-user-role', element: <RelationUserRoleCrud />, handle: { title: '用户角色关系' } },
      // { path: '/', element: [<RoleGrid />,<UserGrid />] },

        { path: '/about', element: <AboutView />, handle: { title: 'About' } },
    ],
  },
] as RouteObject[];

export default createBrowserRouter(routes);
