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

export default function Books() {
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
            <button className="">
                <HiPencilSquare />
            </button>
            <button className="">
                <BiSolidTrash />
            </button>
            <button className="">
                <AiOutlineEye />
            </button>
        </div>
    );
    return (
        <>
            <div className="flex justify-between mb-6">
                <h1 className="text-xl font-semibold">Book Management</h1>
                <div className="flex gap-3 md:flex-row flex-col-reverse items-end">
                    <button className="md:w-1/2 w-1/2 items-center bg-zinc-900 p-2 rounded-xl text-white text-sm flex gap-1  shadow-sm">
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

            <div className="shadow-sm">
                <DataTable
                    className="rounded-2xl overflow-hidden bg-zinc-700" // Adicionado rounded-2xl e overflow-hidden
                    paginator={true}
                    paginatorTemplate="RowsPerPageDropdown FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink"
                    selectionMode="single"
                    value={result?.data}
                    rows={5}
                    totalRecords={result?.data.length}
                    rowClassName={() => "border-b border-gray-200"}
                >
                    <Column
                        field="id"
                        headerStyle={{
                            backgroundColor: "#27272a",
                            color: "white",
                            borderTopLeftRadius: "1rem", // Arredondamento no canto superior esquerdo
                            borderBottomLeftRadius: "1rem", // Arredondamento no canto inferior esquerdo
                        }}
                        header="Code"
                    ></Column>
                    <Column
                        field="title"
                        headerStyle={{
                            backgroundColor: "#27272a",
                            color: "white",
                        }}
                        header="Title"
                    ></Column>
                    <Column
                        field="author"
                        headerStyle={{
                            backgroundColor: "#27272a",
                            color: "white",
                        }}
                        header="Author"
                    ></Column>
                    <Column
                        field="isBorrowed"
                        headerStyle={{
                            backgroundColor: "#27272a",
                            color: "white",
                        }}
                        header="Availability"
                    ></Column>
                    <Column
                        headerStyle={{
                            backgroundColor: "#27272a",
                            color: "white",
                            borderTopRightRadius: "1rem", // Arredondamento no canto superior direito
                            borderBottomRightRadius: "1rem", // Arredondamento no canto inferior direito
                        }}
                        body={actionTemplate}
                        header="Actions"
                    ></Column>
                </DataTable>
            </div>
        </>
    );
}
