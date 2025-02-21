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
    const thisPage = usePathname();

    return (
        <aside
            className={`w-60 bg-zinc-950 text-white pt-6 transition-transform duration-300 transform fixed top-0 left-0 h-full z-40 ${
                isOpen ? "translate-x-0" : "-translate-x-full"
            } md:static md:translate-x-0`}
        >
            {/* Section Logo */}
            <div className="flex flex-col items-center mb-4 pl2 text-white">
                <Image src={logoWhite} alt="logoLibrary" width={70} height={70} />
                <h1 className="text-3xl font-light mt-2">BookFlow</h1>
                <p className="text-xs uppercase">Library</p>
            </div>

            {/* Menu Items */}
            <div className="flex flex-col space-y-2">
                {React.Children.map(menuItems, (item) => {
                    if (React.isValidElement<MenuItemsProps>(item) && item.type === MenuItem) {
                        const isActive = thisPage === item.props.href;
                        return React.cloneElement(item, { isActive });
                    }
                    return item;
                })}

                <a
                    href="/logout"
                    className="pl-8 w-full flex items-center p-2 text-sm text-white hover:bg-white hover:text-black  hover:font-medium transition-colors duration-200 "
                >
                    <TbLogout className="" /> <span className="flex-1 text-center mr-8">Log Out</span>
                </a>
            </div>
        </aside>
    );
};

interface MenuItemsProps {
    href?: string;
    label?: string;
    icon?: JSX.Element;
    isActive?: boolean;
}

export const MenuItem: React.FC<MenuItemsProps> = ({ href, label, icon, isActive }) => {
    return (
        <a
            href={href}
            className={`w-full flex items-center p-2 text-sm text-white hover:bg-white hover:text-black  hover:font-medium transition-colors duration-200 ${
                isActive ? "bg-white text-black font-semibold" : ""
            }`}
        >
            {/* Ícone alinhado à esquerda */}
            {icon && <span className="ml-6">{icon}</span>}

            {/* Texto centralizado */}
            <span className="flex-1 text-center mr-8">{label}</span>
        </a>
    );
};
