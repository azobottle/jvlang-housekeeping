import { AppLayout } from "@hilla/react-components/AppLayout.js";
import { DrawerToggle } from "@hilla/react-components/DrawerToggle.js";
import Placeholder from "Frontend/components/placeholder/Placeholder.js";
import { useRouteMetadata } from "Frontend/util/routing.js";
import { Suspense, useEffect } from "react";
import { NavLink, Outlet } from "react-router-dom";
import { level1_pages } from "Frontend/routes";

const navLinkClasses = ({ isActive }: any) => {
  return `block rounded-m p-s ${
    isActive ? "bg-primary-10 text-primary" : "text-body"
  }`;
};

export default function MainLayout() {
  const currentTitle = useRouteMetadata()?.title ?? "橘浪家政系统管理端";

  useEffect(() => {
    document.title = currentTitle;
  }, [currentTitle]);

  // 一级页面
  const nav_links = level1_pages.map((it) => {
    return (
      <NavLink className={navLinkClasses} to={it.path}>
        {it.handle.title}
      </NavLink>
    );
  });

  return (
    <AppLayout primarySection="drawer">
      <div slot="drawer" className="flex flex-col justify-between h-full p-m">
        <header className="flex flex-col gap-m">
          <h1 className="text-l m-0">橘浪家政系统管理端</h1>
          <nav>{nav_links}</nav>
        </header>
      </div>

      <DrawerToggle slot="navbar" aria-label="Menu toggle"></DrawerToggle>
      <h2 slot="navbar" className="text-l m-0">
        {currentTitle}
      </h2>

      <Suspense fallback={<Placeholder />}>
        <Outlet />
      </Suspense>
    </AppLayout>
  );
}
