"use client";

import { useRouter } from "next/navigation";
import useSWR from "swr";
import { AxiosResponse } from "axios";
import { httpClient } from "@/http";
import { FaBook, FaUserShield, FaUsers } from "react-icons/fa";
import { useState } from "react";
import { Bar } from "react-chartjs-2";
import { Chart as ChartJS, BarElement, CategoryScale, Legend, LinearScale, Title, Tooltip } from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);
export const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

export default function Dashboard() {
    const router = useRouter();
    const [selectedYear, setSelectedYear] = useState<number>(new Date().getFullYear());

    const {
        data: dashboardResponse,
        error,
        isLoading,
    } = useSWR<
        AxiosResponse<{
            totalAdmins: number;
            totalUsers: number;
            totalBooks: number;
            totalBorrowed: number;
            loansByMonth: { month: number; quantity: number }[];
        }>
    >(`admin/dashboard?year=${selectedYear}`, (url: string) => httpClient.get(url));

    if (isLoading) {
        return (
            <div className="flex h-screen justify-center items-center">
                <h2 className="text-xl text-center">Loading...</h2>
            </div>
        );
    }

    if (error) {
        return (
            <div className="flex h-screen justify-center items-center">
                <h2 className="text-xl text-center text-red-600">Error loading dashboard</h2>
            </div>
        );
    }

    const dashboard = dashboardResponse?.data;

    const barData = {
        labels: dashboard?.loansByMonth.map((i) => (i.month !== undefined ? months[i.month - 1] : "")) || [],
        datasets: [
            {
                label: "Books Borrowed",
                backgroundColor: "rgba(33, 37, 41, 0.9)", // Escuro, neutro e elegante
                borderColor: "rgba(33, 37, 41, 1)",
                borderWidth: 1,
                borderRadius: 8,
                data: dashboard?.loansByMonth.map((i) => i.quantity) || [],
            },
        ],
    };
    const options = {
        responsive: true,
        maintainAspectRatio: false, // Permite ajuste de altura
        plugins: {
            legend: { position: "top" as const },
            title: { display: true, text: `Books Borrowed Per Month (${selectedYear})` },
        },
        scales: {
            x: { title: { display: true, text: "Months" } },
            y: { title: { display: true, text: "Number of Loans" }, beginAtZero: true },
        },
    };

    const years = Array.from({ length: 5 }, (_, i) => new Date().getFullYear() - i); // Ex.: [2025, 2024, 2023, 2022, 2021]

    return (
        <div className="p-6 bg-gray-50 min-h-screen">
            <div className="flex justify-between items-center mb-8">
                <h1 className="text-2xl font-bold text-gray-800">Admin Dashboard</h1>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                <div className="bg-white p-6 rounded-xl shadow-md border border-gray-200 flex items-center gap-4 transition-transform hover:scale-105 duration-300">
                    <FaUserShield className="text-blue-600 text-3xl" />
                    <div>
                        <h2 className="text-gray-600 text-sm font-semibold">Total Admins</h2>
                        <p className="text-2xl font-bold text-gray-800">{dashboard?.totalAdmins || 0}</p>
                    </div>
                </div>
                <div className="bg-white p-6 rounded-xl shadow-md border border-gray-200 flex items-center gap-4 transition transform hover:scale-105 duration-300">
                    <FaUsers className="text-green-600 text-3xl" />
                    <div>
                        <h2 className="text-gray-600 text-sm font-semibold">Total Users</h2>
                        <p className="text-2xl font-bold text-gray-800">{dashboard?.totalUsers || 0}</p>
                    </div>
                </div>
                <div className="bg-white p-6 rounded-xl shadow-md border border-gray-200 flex items-center gap-4 transition transform hover:scale-105 duration-300">
                    <FaBook className="text-purple-600 text-3xl" />
                    <div>
                        <h2 className="text-gray-600 text-sm font-semibold">Total Books</h2>
                        <p className="text-2xl font-bold text-gray-800">{dashboard?.totalBooks || 0}</p>
                    </div>
                </div>
                <div className="bg-white p-6 rounded-xl shadow-md border border-gray-200 flex items-center gap-4 transition transform hover:scale-105 duration-300">
                    <FaBook className="text-red-600 text-3xl" />
                    <div>
                        <h2 className="text-gray-600 text-sm font-semibold">Total Borrowed</h2>
                        <p className="text-2xl font-bold text-gray-800">{dashboard?.totalBorrowed || 0}</p>
                    </div>
                </div>
            </div>

            {/* Gráfico */}
            <div className="w-full bg-white shadow-lg rounded-xl p-4 sm:p-6 mt-8">
                <div className="flex flex-col sm:flex-row justify-between items-center mb-4">
                    <h3 className="text-lg font-semibold text-gray-800 mb-2 sm:mb-0">Loans Overview</h3>
                    <select
                        value={selectedYear}
                        onChange={(e) => setSelectedYear(parseInt(e.target.value))}
                        className="w-full sm:w-32 p-2 border border-gray-300 rounded-md text-gray-800 focus:outline-none focus:ring-2 focus:ring-gray-500"
                    >
                        {years.map((year) => (
                            <option key={year} value={year}>
                                {year}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="relative h-64 sm:h-80">
                    <Bar data={barData} options={options} />
                </div>
            </div>
        </div>
    );
}
