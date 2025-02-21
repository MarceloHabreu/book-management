import React from "react";
import { FaUser } from "react-icons/fa6";
import { IoMdSettings } from "react-icons/io";

export const Navbar = () => {
    return (
        <nav className="bg-white text-black h-16 w-full flex justify-between items-center p-3 shadow-sm">
            {/* Lado esquerdo: Ícone e nome do usuário */}
            <div className="flex items-center gap-3">
                <div className="relative">
                    {/* Círculo ao redor do ícone */}
                    <div className="w-10 h-10 bg-zinc-100 rounded-full flex items-center justify-center">
                        <FaUser className="text-zinc-700 text-xl" />
                    </div>
                </div>
                <div className="flex flex-col">
                    <h2 className="text-sm font-semibold text-zinc-800">Marcelo Henrique</h2>
                    <p className="text-xs text-zinc-500">Admin/User</p>
                </div>
            </div>

            {/* Lado direito: Horário e configurações */}
            <div className="flex items-center gap-4">
                <div className="flex flex-col text-center">
                    <h2 className="text-sm font-bold text-zinc-800">12:29 PM</h2>
                    <p className="text-xs font-medium text-zinc-500">Feb 20, 2025</p>
                </div>
                <span className="border border-solid border-zinc-200 h-8"></span>
                <div className="p-2 hover:bg-zinc-100 rounded-full cursor-pointer transition-colors duration-200">
                    <IoMdSettings className="text-zinc-700 text-xl" />
                </div>
            </div>
        </nav>
    );
};
