"use client";
import { Column } from "primereact/column";
import { DataTable } from "primereact/datatable";
import { IoIosAddCircle } from "react-icons/io";
import "primereact/resources/themes/saga-blue/theme.css";
import "primereact/resources/primereact.min.css";
import { CiSearch } from "react-icons/ci";
import useSWR from "swr";
import { AxiosResponse } from "axios";
import { Book } from "@/models/Book";
import { httpClient } from "@/http";
import { HiPencilSquare } from "react-icons/hi2";
import { BiSolidTrash } from "react-icons/bi";
import { AiOutlineEye } from "react-icons/ai";
import { useRouter } from "next/navigation";

export default function Books() {
    const router = useRouter();
    const handleNavigateCreate = () => {
        router.push("/admin/books/create");
    };
    const handleNavigateEdit = () => {
        router.push("/admin/books/edit");
    };

    const {
        data: result,
        error,
        isLoading,
    } = useSWR<AxiosResponse<Book[]>>(`/books`, (url: string) => httpClient.get(url));

    if (isLoading) {
        return (
            <div className="flex h-screen justify-center items-center">
                <h2 className="text-xl text-center">Loading...</h2>
            </div>
        );
    }
    const actionTemplate = (record: Book) => (
        <div className="flex space-x-2">
            <button className="text-black" onClick={handleNavigateEdit}>
                <HiPencilSquare />
            </button>
            <button className="text-black">
                <BiSolidTrash />
            </button>
            <button className="text-black">
                <AiOutlineEye />
            </button>
        </div>
    );
    return (
        <>
            <div className="flex justify-between mb-6">
                <h1 className="text-xl font-semibold">Book Management</h1>
                <div className="flex gap-3 md:flex-row flex-col-reverse items-end">
                    <button
                        className="md:w-1/2 w-1/2 items-center bg-zinc-900 p-2 rounded-xl text-white text-sm flex gap-1  shadow-sm"
                        onClick={handleNavigateCreate}
                    >
                        <IoIosAddCircle size={18} /> Add Book
                    </button>
                    <div className="relative w-full">
                        {/* Ícone de Pesquisa */}
                        <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                            <CiSearch className="h-5 w-5 text-gray-400" />
                        </div>

                        {/* Campo de Busca */}
                        <input
                            className="w-full rounded-xl p-2 pl-10 text-sm shadow-sm bg-zinc-200 text-black placeholder:text-gray-400 focus:outline-none focus:ring-2 focus:ring-zinc-900"
                            type="text"
                            placeholder="Search by Name or Category"
                        />
                    </div>
                </div>
            </div>

            <DataTable
                className="rounded-2xl shadow-lg overflow-hidden no-pointer"
                paginator={true}
                stripedRows
                paginatorTemplate="RowsPerPageDropdown FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink"
                selectionMode="single"
                value={result?.data}
                rows={7}
                totalRecords={result?.data.length}
            >
                <Column
                    field="id"
                    headerClassName="text-gray border-b-2 border-gray-700 pl-8"
                    bodyClassName="pl-8"
                    header="ID"
                ></Column>
                <Column field="title" headerClassName="text-gray  border-b-2 border-gray-700" header="Title"></Column>
                <Column field="author" headerClassName="text-gray  border-b-2 border-gray-700" header="Author"></Column>
                <Column
                    field="isBorrowed"
                    headerClassName="text-gray  border-b-2 border-gray-700"
                    header="Availability"
                ></Column>
                <Column
                    headerClassName="text-gray  border-b-2 border-gray-700 pr-8" // Centralizado + padding à direita
                    body={actionTemplate}
                    header="Actions"
                ></Column>
            </DataTable>
        </>
    );
}
