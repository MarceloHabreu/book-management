"use client";
import { useState } from "react";
import { MenuItem, Sidebar } from "../(components)/(menu)/Sidebar";
import { Navbar } from "../(components)/(navbar)/Navbar";
import { MdSpaceDashboard } from "react-icons/md";
import { PiUsersThreeFill } from "react-icons/pi";
import { TbReportAnalytics } from "react-icons/tb";
import { IoMdClose } from "react-icons/io";
import { ImBooks } from "react-icons/im";
import { FiMenu } from "react-icons/fi";

export default function UserLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    const [isSidebarOpen, setIsSidebarOpen] = useState<boolean>(false);

    // Itens do menu para o layout de user
    const userMenuItems = (
        <>
            <MenuItem href="/user" label="Dashboard" icon={<MdSpaceDashboard size={16} />} />
            <MenuItem href="/user/books" label="Books" icon={<ImBooks size={16} />} />
            <MenuItem href="/user/reports" label="Reports" icon={<TbReportAnalytics size={16} />} />
        </>
    );
    return (
        <html>
            <head>
                <title>BookManager</title>
            </head>
            <body>
                <div className="h-screen flex bg-zinc-100">
                    <Sidebar isOpen={isSidebarOpen} menuItems={userMenuItems} />
                    <Navbar />
                    <div className="flex-1 flex flex-col min-h-screen overflow-y-auto">
                        <main className="flex-1 p-6 flex justify-center">
                            <div className="relative w-full max-w-4xl bg-white rounded-xl shadow-xl p-8 overflow-auto">
                                <div className="mt-6">{children}</div>
                            </div>
                        </main>
                        {/* Botão de menu flutuante */}
                        <button
                            className="fixed bottom-4 right-4 md:hidden z-50 p-3 bg-zinc-950 text-white rounded-full shadow-lg"
                            onClick={() => setIsSidebarOpen(!isSidebarOpen)}
                        >
                            {isSidebarOpen ? <IoMdClose size={24} /> : <FiMenu size={24} />}
                        </button>
                    </div>
                </div>
            </body>
        </html>
    );
}
