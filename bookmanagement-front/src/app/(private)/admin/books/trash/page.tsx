"use client";
import { Column } from "primereact/column";
import { DataTable } from "primereact/datatable";
import { IoIosAddCircle } from "react-icons/io";
import "primereact/resources/themes/saga-blue/theme.css";
import "primereact/resources/primereact.min.css";
import { CiSearch } from "react-icons/ci";
import useSWR, { mutate } from "swr";
import { AxiosResponse } from "axios";
import { Book } from "@/models/Book";
import { httpClient } from "@/http";
import { BiSolidTrash } from "react-icons/bi";
import { AiOutlineEye } from "react-icons/ai";
import { useRouter } from "next/navigation";
import IconDelete from "@/public/imgs/IconDelete.png";
import withReactContent from "sweetalert2-react-content";
import Swal from "sweetalert2";
import { useBookService } from "@/services/book.service";
import Image from "next/image";
import { MdOutlineClose } from "react-icons/md";
import { toast } from "react-toastify";
import { FaBook, FaHashtag, FaPen, FaTrash } from "react-icons/fa6";
import { FaTrashRestore } from "react-icons/fa";
import { IoArrowBackCircleOutline } from "react-icons/io5";
const MySwal = withReactContent(Swal);

export default function Books() {
    const router = useRouter();
    const { restoreBook, permanentDelete } = useBookService();

    const handleNavigateBack = () => {
        router.push(`/admin/books`);
    };
    const handleNavigateView = (id: string) => {
        router.push(`/admin/books/${id}`);
    };
    const handleRestoreClick = async (id: string) => {
        const result = await restoreBook(id);
        if (result.message) {
            toast.success(result.message);
            mutate("/books/trash");
        } else if (result.error) {
            toast.error(result.error);
        }
    };

    const handleDeleteClick = (book: Book) => {
        MySwal.fire({
            html: (
                <div className="flex flex-col gap-2 p-2 ">
                    <div className="flex justify-between ">
                        <div className="flex gap-2 items-center justify-center">
                            <span className="bg-zinc-200 rounded-lg">
                                <Image src={IconDelete} alt="iconDelete" width={40} height={40} />
                            </span>
                            <h1 className="text-lg sm:text-xl text-[#151619] font-semibold">Delete Confirmation</h1>
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
                            Are you certain you wish to proceed with the permanent deletion of the selected entry?
                        </h2>
                        <div className="bg-zinc-200 p-4 rounded-lg border border-gray-300">
                            <p className="text-lg text-gray-800 font-bold mb-3">Book Details:</p>
                            <div className="flex flex-col gap-2">
                                <div className="flex justify-between items-center">
                                    <div className="flex items-center gap-2">
                                        <FaHashtag size={16} className="text-gray-500" />
                                        <span className="text-gray-600 font-medium">Book ID:</span>
                                    </div>
                                    <span className="text-gray-900 bg-white px-2 py-1 rounded-md border border-gray-300">
                                        {book.id}
                                    </span>
                                </div>
                                <div className="flex justify-between items-center">
                                    <div className="flex items-center gap-2">
                                        <FaBook size={16} className="text-gray-500" />
                                        <span className="text-gray-600 font-medium">Title:</span>
                                    </div>
                                    <span className="text-gray-900 bg-white px-2 py-1 rounded-md border border-gray-300">
                                        {book.title}
                                    </span>
                                </div>
                                <div className="flex justify-between items-center">
                                    <div className="flex items-center gap-2">
                                        <FaPen size={16} className="text-gray-500" />
                                        <span className="text-gray-600 font-medium">Author:</span>
                                    </div>
                                    <span className="text-gray-900 bg-white px-2 py-1 rounded-md border border-gray-300">
                                        {book.author}
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
            buttonsStyling: false, // Usar Tailwind em vez de estilos padrão
            allowOutsideClick: true,
            preConfirm: async () => {
                const result = await permanentDelete(book.id || "");
                if (result.error) {
                    toast.error(result.error);
                    return false;
                }
                toast.success(result.message);
                return true;
            },
        }).then((result) => {
            if (result.isConfirmed) {
                mutate("/books/trash");
            }
        });
    };

    const { data: result, isLoading } = useSWR<AxiosResponse<Book[]>>(`/books/trash`, (url: string) =>
        httpClient.get(url)
    );

    if (isLoading) {
        return (
            <div className="flex h-screen justify-center items-center">
                <h2 className="text-xl text-center">Loading...</h2>
            </div>
        );
    }
    ("use client");

    const actionTemplate = (record: Book) => (
        <div className="flex space-x-2">
            <button
                className="text-green-600 hover:text-green-800 transition-colors"
                onClick={() => handleRestoreClick(record.id || "")}
                title="Restore"
            >
                <FaTrashRestore size={16} />
            </button>
            <button
                className="text-red-600 hover:text-red-800 transition-colors disabled:text-zinc-400"
                onClick={() => handleDeleteClick(record)}
                disabled={record.isBorrowed}
                title="Delete Permanently"
            >
                <BiSolidTrash size={18} />
            </button>
            <button
                className="text-blue-900 hover:text-blue-800 transition-colors"
                onClick={() => handleNavigateView(record.id || "")}
                title="View Details"
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
        <div className="p-6 bg-gray-50 min-h-screen">
            <div className="flex flex-col md:flex-row justify-between mb-8 items-center">
                <h1 className="text-2xl font-bold text-gray-800 text-center md:text-left mb-4 md:mb-0">Book Trash</h1>
                <div className="md:grid grid-cols-5 gap-4 flex flex-col-reverse items-end  w-full">
                    <button
                        className="sm:col-span-2 md:col-span-1 w-full md:w-full bg-gray-700 hover:bg-gray-800 text-white py-2 px-4 rounded-xl flex items-center gap-2 shadow-md transition-colors"
                        onClick={handleNavigateBack}
                    >
                        <IoArrowBackCircleOutline size={20} /> Back to Books
                    </button>
                    <div className="md:col-span-4  relative w-full">
                        <div className="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                            <CiSearch className="h-5 w-5 text-gray-400" />
                        </div>
                        <input
                            className="w-full rounded-xl p-2 pl-10 text-sm bg-white border border-gray-300 shadow-sm text-gray-800 placeholder:text-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500"
                            type="text"
                            placeholder="Search in Trash by Name or Author"
                        />
                    </div>
                </div>
            </div>

            {/* Aviso */}
            <p className="text-center text-sm text-red-600 font-medium mb-6">
                All books in this tab will be permanently deleted in 1 week
            </p>

            <DataTable
                className="rounded-2xl shadow-lg overflow-hidden bg-white border border-gray-200"
                paginator={true}
                stripedRows
                paginatorTemplate="RowsPerPageDropdown FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink"
                selectionMode="single"
                value={result?.data}
                rows={7}
                totalRecords={result?.data.length}
                emptyMessage="No books in the trash"
            >
                <Column
                    field="id"
                    headerClassName="text-gray-700 font-semibold border-b-2 border-gray-300 pl-8 bg-gray-100"
                    bodyClassName="pl-8 text-gray-800"
                    header="ID"
                />
                <Column
                    field="title"
                    headerClassName="text-gray-700 font-semibold border-b-2 border-gray-300 bg-gray-100"
                    bodyClassName="text-gray-800"
                    header="Title"
                />
                <Column
                    field="author"
                    headerClassName="text-gray-700 font-semibold border-b-2 border-gray-300 bg-gray-100"
                    bodyClassName="text-gray-800"
                    header="Author"
                />
                <Column
                    field="isBorrowed"
                    headerClassName="text-gray-700 font-semibold border-b-2 border-gray-300 bg-gray-100"
                    header="Availability"
                    body={(book) => (
                        <span
                            className={`px-2 py-1 rounded-full text-xs font-medium ${
                                book.isBorrowed ? "bg-red-100 text-red-700" : "bg-green-100 text-green-700"
                            }`}
                        >
                            {book.isBorrowed ? "Borrowed" : "Available"}
                        </span>
                    )}
                />
                <Column
                    headerClassName="text-gray-700 font-semibold border-b-2 border-gray-300 pr-8 bg-gray-100"
                    body={actionTemplate}
                    header="Actions"
                />
            </DataTable>
        </div>
    );
}
