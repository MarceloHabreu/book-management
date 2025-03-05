"use client";
import { Column } from "primereact/column";
import { DataTable } from "primereact/datatable";
import "primereact/resources/themes/saga-blue/theme.css";
import "primereact/resources/primereact.min.css";
import { CiSearch } from "react-icons/ci";
import useSWR, { mutate } from "swr";
import { AxiosResponse } from "axios";
import { httpClient } from "@/http";
import { BiSolidTrash } from "react-icons/bi";
import { AiOutlineEye } from "react-icons/ai";
import { useRouter } from "next/navigation";
import IconDelete from "@/public/imgs/IconDelete.png";
import withReactContent from "sweetalert2-react-content";
import Swal from "sweetalert2";
import Image from "next/image";
import { MdOutlineClose } from "react-icons/md";
import { toast } from "react-toastify";
import { FaBook, FaHashtag, FaPen, FaTrash } from "react-icons/fa6";
import { useUserService } from "@/services/admin/user.service";
import { useState } from "react";
import { useDebounce } from "use-debounce";
const MySwal = withReactContent(Swal);

export default function Users() {
    const [nameFilter, setNameFilter] = useState<string>("");
    return (
        <div className="p-6 bg-gray-50 min-h-screen">
            <div className="flex flex-col md:flex-row justify-between mb-8 items-center">
                <h1 className="text-2xl font-bold text-gray-800 text-center md:text-left mb-4 md:mb-0">
                    User Management
                </h1>
                <div className=" w-full md:w-1/2">
                    <div className="relative w-full">
                        <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                            <CiSearch className="h-5 w-5 text-gray-400" />
                        </div>
                        <input
                            value={nameFilter}
                            onChange={(e) => setNameFilter(e.target.value)}
                            className="w-full rounded-xl p-2 pl-10 text-sm bg-white border border-gray-300 shadow-sm text-gray-800 placeholder:text-gray-400 focus:outline-none focus:ring-2 focus:ring-zinc-500"
                            type="text"
                            placeholder="Search by Id or Name"
                        />
                    </div>
                </div>
            </div>
            <UserTable nameFilter={nameFilter} />
        </div>
    );
}

const UserTable = ({ nameFilter }: { nameFilter: string }) => {
    const router = useRouter();
    const { permanentDelete } = useUserService();

    const handleNavigateView = (id: string) => {
        router.push(`/admin/users/${id}`);
    };

    const [debouncedNameFilter] = useDebounce(nameFilter, 700);

    const handleDeleteClick = (user: User) => {
        MySwal.fire({
            html: (
                <div className="flex flex-col gap-2 p-2 ">
                    <div className="flex justify-between ">
                        <div className="flex gap-2 items-center justify-center">
                            <span className="bg-zinc-200 rounded-lg">
                                <Image src={IconDelete} alt="iconDelete" width={40} height={40} />
                            </span>
                            <h1 className="text-lg sm:text-xl text-[#151619] font-semibold">Ban Confirmation</h1>
                        </div>
                        <button onClick={() => Swal.close()}>
                            <MdOutlineClose
                                size={24}
                                className="p-1 text-zinc-400 border border-zinc-600 border-solid rounded-md"
                            />
                        </button>
                    </div>
                    <div className="border border-solid border-zinc-300 w-full mb-4"></div>
                    <div className="flex flex-col gap-4">
                        <h2 className="text-gray-700 text-center">
                            Are you sure you want to delete this User Account? This action is irreversible!
                        </h2>
                        <textarea
                            rows={5}
                            cols={50}
                            placeholder="Inform the user of the reason for the ban"
                            className="w-full rounded-xl p-2 text-sm text-center bg-white border border-gray-300 shadow-sm text-gray-800 placeholder:text-gray-400 focus:outline-none focus:ring-2 focus:ring-zinc-500"
                        />
                        <div className="bg-zinc-200 p-4 rounded-lg border border-gray-300">
                            <p className="text-lg text-gray-800 font-bold mb-3">User Details:</p>
                            <div className="flex flex-col gap-2">
                                <div className="flex justify-between  items-center">
                                    <div className="flex items-center gap-2">
                                        <FaHashtag size={16} className="text-gray-500" />
                                        <span className="text-gray-600 font-medium">User ID:</span>
                                    </div>
                                    <span className="text-gray-900 bg-white px-2 py-1 rounded-md border border-gray-300">
                                        {user.id}
                                    </span>
                                </div>
                                <div className="flex justify-between items-center">
                                    <div className="flex items-center gap-2">
                                        <FaBook size={16} className="text-gray-500" />
                                        <span className="text-gray-600 font-medium">Name:</span>
                                    </div>
                                    <span className="text-gray-900 bg-white px-2 py-1 rounded-md border border-gray-300">
                                        {user.name}
                                    </span>
                                </div>
                                <div className="flex justify-between items-center">
                                    <div className="flex items-center gap-2">
                                        <FaPen size={16} className="text-gray-500" />
                                        <span className="text-gray-600 font-medium">Email:</span>
                                    </div>
                                    <span className="text-gray-900 bg-white px-2 py-1 rounded-md border border-gray-300">
                                        {user.email}
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            ),
            showConfirmButton: true,
            confirmButtonText: "CONFIRM",
            confirmButtonColor: "black",
            customClass: {
                popup: "rounded-2xl shadow-md w-full max-w-lg sm:w-11/12 md:w-1/2",
                confirmButton:
                    "w-full bg-black text-white py-2 px-8 sm:py-3 rounded-xl font-semibold text-sm sm:text-base hover:bg-zinc-800 transition-colors disabled:bg-gray-400",
            },
            buttonsStyling: false,
            allowOutsideClick: true,
            preConfirm: async () => {
                const result = await permanentDelete(user.id || "");
                if (result.error) {
                    toast.error(result.error);
                    return false;
                }
                toast.success(result.message);
                return true;
            },
        }).then((result) => {
            if (result.isConfirmed) {
                mutate(`/admin/users?name=${debouncedNameFilter}`);
            }
        });
    };

    const {
        data: result,
        error,
        isLoading,
    } = useSWR<AxiosResponse<User[]>>(`/admin/users?name=${debouncedNameFilter}`, (url: string) => httpClient.get(url));

    if (isLoading) {
        return (
            <div className="flex h-screen justify-center items-center">
                <h2 className="text-xl text-center">Loading...</h2>
            </div>
        );
    }

    const actionTemplate = (record: User) => (
        <div className="flex space-x-2">
            <button
                className="text-red-600 hover:text-red-800 transition-colors disabled:text-zinc-400"
                title="Move to Trash"
                onClick={() => handleDeleteClick(record)}
            >
                <BiSolidTrash size={18} />
            </button>
            <button
                className="text-blue-900 hover:text-blue-800 transition-colors"
                title="View Details"
                onClick={() => handleNavigateView(record.id || "")}
            >
                <AiOutlineEye size={18} />
            </button>
        </div>
    );

    if (isLoading) {
        return (
            <div className="flex h-screen justify-center items-center">
                <h2 className="text-xl text-center text-gray-600">Loading...</h2>
            </div>
        );
    }

    return (
        <DataTable
            className="rounded-2xl shadow-lg overflow-hidden bg-white border border-gray-200"
            paginator={true}
            stripedRows
            paginatorTemplate="RowsPerPageDropdown FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink"
            selectionMode="single"
            value={result?.data}
            rows={7}
            totalRecords={result?.data.length}
            emptyMessage={<div className="text-center font-base text-zinc-400">No registered users</div>}
        >
            <Column
                field="id"
                headerClassName="text-gray-700 font-semibold border-b-2 border-gray-300 pl-8 bg-gray-100"
                bodyClassName="pl-8 text-gray-800"
                header="ID"
            />
            <Column
                field="name"
                headerClassName="text-gray-700 font-semibold border-b-2 border-gray-300 bg-gray-100"
                bodyClassName="text-gray-800"
                header="Name"
            />
            <Column
                field="email"
                headerClassName="text-gray-700 font-semibold border-b-2 border-gray-300 bg-gray-100"
                bodyClassName="text-gray-800"
                header="Email"
            />

            <Column
                headerClassName="text-gray-700 font-semibold border-b-2 border-gray-300 pr-8 bg-gray-100"
                body={actionTemplate}
                header="Actions"
            />
        </DataTable>
    );
};
