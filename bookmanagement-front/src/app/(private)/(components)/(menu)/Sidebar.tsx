import logoWhite from "@/public/imgs/logo.png";
import Image from "next/image";
import "@/app/globals.css";
import React, { JSX, ReactNode } from "react";
import { usePathname } from "next/navigation";
import { TbLogout } from "react-icons/tb";

interface SidebarProps {
    isOpen: boolean;
    menuItems?: ReactNode;
}

export const Sidebar: React.FC<SidebarProps> = ({ isOpen, menuItems }) => {
    return (
        <aside
            className={`w-60 bg-zinc-950 text-white pt-6 transition-transform duration-300 transform fixed top-0 left-0 h-full z-40 ${
                isOpen ? "translate-x-0" : "-translate-x-full"
            } md:static md:translate-x-0`}
        >
            {/* Seção de Logo */}
            <div className="flex flex-col items-center mb-4 pl-2 text-white">
                <Image src={logoWhite} alt="logoLibrary" width={70} height={70} />
                <h1 className="text-3xl font-light mt-2">BookFlow</h1>
                <p className="text-xs uppercase">Library</p>
            </div>

            {/* Container principal */}
            <div className="flex flex-col h-full">
                {/* Itens do Menu */}
                <div className="flex-1">
                    <div className="flex flex-col">{menuItems}</div>
                </div>

                {/* Botão de Logout fixado no fim */}
                <a
                    href="/logout"
                    className="pl-8 w-full flex items-center p-2 text-sm text-white hover:bg-white hover:text-black transition-colors duration-200 absolute bottom-0 mb-7"
                >
                    <TbLogout />
                    <span className="flex-1 text-center mr-8">Log Out</span>
                </a>
            </div>
        </aside>
    );
};

interface MenuItemsProps {
    href?: string;
    label?: string;
    icon?: JSX.Element;
}

export const MenuItem: React.FC<MenuItemsProps> = ({ href, label, icon }) => {
    const thisPage = usePathname();
    const isActive = href === thisPage;
    return (
        <a
            href={href}
            className={`flex items-center gap-2 justify-center p-2 text-base font-normal transition-colors duration-200 ${
                isActive ? "bg-white text-black" : "hover:bg-white hover:text-black"
            }`}
        >
            {icon}
            {label}
        </a>
    );
};
