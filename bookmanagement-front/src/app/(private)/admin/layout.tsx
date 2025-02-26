"use client";
import { useEffect, useRef, useState } from "react";
import { MenuItem, Sidebar } from "../(components)/(menu)/Sidebar";
import { Navbar } from "../(components)/(navbar)/Navbar";
import { MdSpaceDashboard } from "react-icons/md";
import { PiUsersThreeFill } from "react-icons/pi";
import { TbReportAnalytics } from "react-icons/tb";
import { IoMdClose } from "react-icons/io";
import { ImBooks } from "react-icons/im";
import { FiMenu } from "react-icons/fi";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export default function AdminLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    const [isSidebarOpen, setIsSidebarOpen] = useState<boolean>(false);
    const sidebarRef = useRef(null);

    // close sidebar in click out
    useEffect(() => {
        const handleClickOutside = (event) => {
            if (isSidebarOpen && sidebarRef.current && !sidebarRef.current.contains(event.target)) {
                setIsSidebarOpen(false);
            }
        };
        // Adiciona o event listener ao montar o componente
        document.addEventListener("mousedown", handleClickOutside);

        // Remove o event listener ao desmontar o componente
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [isSidebarOpen]);

    // Itens do menu para o layout de admin
    const adminMenuItems = (
        <>
            <MenuItem key={"admin"} href="/admin/dashboard" label="Dashboard" icon={<MdSpaceDashboard size={16} />} />
            <MenuItem key={"books"} href="/admin/books" label="Books" icon={<ImBooks size={16} />} />
            <MenuItem key={"users"} href="/admin/users" label="Users" icon={<PiUsersThreeFill size={16} />} />
            <MenuItem key={"reports"} href="/admin/reports" label="Reports" icon={<TbReportAnalytics size={16} />} />
        </>
    );
    return (
        <div className="h-screen flex">
            <div ref={sidebarRef}>
                <Sidebar isOpen={isSidebarOpen} menuItems={adminMenuItems} />
            </div>
            <div className="flex-1 flex flex-col  overflow-y-auto">
                <Navbar />
                <main className="flex-1 flex justify-center">
                    <div className=" w-full bg-zinc-100 shadow-xl p-4 ">
                        <div className="mt-2">
                            {children} <ToastContainer />
                        </div>
                    </div>
                </main>
                {/* Botão de menu flutuante */}
                <button
                    className="fixed bottom-4 right-4 md:hidden z-50 p-3 bg-zinc-950 text-white rounded-full shadow-lg flex items-center justify-center"
                    onClick={() => setIsSidebarOpen(!isSidebarOpen)}
                >
                    {isSidebarOpen ? <IoMdClose size={24} /> : <FiMenu size={24} />}
                </button>
            </div>
        </div>
    );
}
