import {AppLayout} from "@hilla/react-components/AppLayout.js";
import {DrawerToggle} from "@hilla/react-components/DrawerToggle.js";
import Placeholder from "Frontend/components/placeholder/Placeholder.js";
import {useRouteMetadata} from "Frontend/util/routing.js";
import {Suspense, useEffect} from "react";
import {NavLink, Outlet} from "react-router-dom";

const navLinkClasses = ({isActive}: any) => {
    return `block rounded-m p-s ${
        isActive ? "bg-primary-10 text-primary" : "text-body"
    }`;
};

export default function MainLayout() {
    const currentTitle = useRouteMetadata()?.title ?? "橘浪家政系统管理端";

    useEffect(() => {
        document.title = currentTitle;
    }, [currentTitle]);

    return (
        <AppLayout primarySection="drawer">
            <div slot="drawer" className="flex flex-col justify-between h-full p-m">
                <header className="flex flex-col gap-m">
                    <h1 className="text-l m-0">橘浪家政系统管理端</h1>
                    <nav>
                        <NavLink className={navLinkClasses} to="/role">角色</NavLink>
                        <NavLink className={navLinkClasses} to="/user">用户</NavLink>
                        <NavLink className={navLinkClasses} to="/relation-user-role">用户角色关系</NavLink>
                        <NavLink className={navLinkClasses} to="/service">服务</NavLink>
                        <NavLink className={navLinkClasses} to="/relation-shifu-service">师傅服务关系</NavLink>
                        <NavLink className={navLinkClasses} to="/schedule">排班</NavLink>
                        <NavLink className={navLinkClasses} to="/order">订单</NavLink>
                        <NavLink className={navLinkClasses} to="/about">关于</NavLink>
                    </nav>
                </header>
            </div>

            <DrawerToggle slot="navbar" aria-label="Menu toggle"></DrawerToggle>
            <h2 slot="navbar" className="text-l m-0">
                {currentTitle}
            </h2>

            <Suspense fallback={<Placeholder/>}>
                <Outlet/>
            </Suspense>
        </AppLayout>
    );
}
