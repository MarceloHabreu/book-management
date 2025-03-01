"use client";
import Image from "next/image";
import React, { useEffect, useState } from "react";
import IconBook from "@/public/imgs/iconBook.png";
import { MdOutlineClose } from "react-icons/md";
import { useRouter } from "next/navigation";
import { useFormik } from "formik";
import { Book } from "@/models/Book";
import * as Yup from "yup";
import { useBookService } from "@/services/book.service";
import { toast } from "react-toastify";

const formSchema: Partial<Book> = {
    title: "",
    author: "",
};

interface ViewBookParams {
    params: Promise<{ id: string }>;
}

const ViewBook = ({ params: paramsPromise }: ViewBookParams) => {
    const router = useRouter();
    const [isLoading, setIsLoading] = useState<boolean>(true);

    const params = React.use(paramsPromise);

    const { id } = params;
    const { getBook, update } = useBookService();

    const validationScheme = Yup.object().shape({
        title: Yup.string().trim().required("Title is required").min(4, "Title must be at least 4 characters"),
        author: Yup.string().trim().required("Author is required").min(4, "Author name must be at least 4 characters"),
    });

    const formik = useFormik<Book>({
        initialValues: { ...formSchema } as Book,
        enableReinitialize: true,
        validationSchema: validationScheme,
        onSubmit: () => {},
    });

    useEffect(() => {
        if (id) {
            const fetchBook = async () => {
                setIsLoading(true);
                const result = await getBook(id);
                if (result.book) {
                    formik.setValues(result.book);
                } else if (result.error) {
                    toast.error(result.error);
                }
                setIsLoading(false);
            };
            fetchBook();
        }
    }, [id]);

    if (isLoading) {
        return (
            <div className="flex h-screen justify-center items-center">
                <h2 className="text-xl text-center">Loading...</h2>
            </div>
        );
    }

    return (
        <div className="flex justify-center items-center min-h-screen bg-zinc-100">
            {/* Contêiner Principal */}
            <div className="bg-white p-6 md:p-8 rounded-2xl shadow-md w-full max-w-2xl sm:w-11/12 md:w-full">
                {/* Cabeçalho */}
                <div className="flex justify-between items-center mb-4">
                    <div className="flex gap-2 items-center">
                        <span className="p-2 bg-zinc-200 rounded-lg">
                            <Image src={IconBook} alt="iconBook" width={20} height={20} />
                        </span>
                        <h1 className="text-lg sm:text-xl text-[#151619] font-semibold">View Book</h1>
                    </div>
                    <button onClick={() => router.back()}>
                        <MdOutlineClose
                            size={24}
                            className="p-1 text-zinc-400 border border-zinc-600 border-solid rounded-md"
                        />
                    </button>
                </div>
                <div className="border border-solid border-zinc-300 w-full mb-4"></div>

                {/* Formulário */}
                <form className="flex flex-col gap-6 border border-solid border-zinc-100 rounded-md p-6">
                    <div className="flex flex-col md:flex-row gap-6">
                        {/* Seção de Informações do Livro */}
                        <div className="flex-1">
                            <div className="border-b border-zinc-600 mb-5">
                                <span className="text-gray-700 font-semibold">Book ID:</span>{" "}
                                <span className="text-gray-900">{formik.values.id}</span>
                            </div>
                            <div className="border-b border-zinc-600 mb-5">
                                <span className="text-gray-700 font-semibold">Title:</span>{" "}
                                <span className="text-gray-900">{formik.values.title}</span>
                            </div>
                            <div className="border-b border-zinc-600 mb-5">
                                <span className="text-gray-700 font-semibold">Author:</span>{" "}
                                <span className="text-gray-900">{formik.values.author}</span>
                            </div>
                            <div className="border-b border-zinc-600 mb-5">
                                <span className="text-gray-700 font-semibold">Availability:</span>{" "}
                                <span className="text-gray-900">
                                    {formik.values.isBorrowed ? "Borrowed" : "Available"}
                                </span>
                            </div>
                            <div className="border-b border-zinc-600 mb-5">
                                <span className="text-gray-700 font-semibold">Created At:</span>{" "}
                                <span className="text-gray-900">{formik.values.created_at}</span>
                            </div>
                            <div className="border-b border-zinc-600 mb-5">
                                <span className="text-gray-700 font-semibold">Updated At:</span>{" "}
                                <span className="text-gray-900">{formik.values.updated_at}</span>
                            </div>
                        </div>

                        {/* Separador Vertical */}
                        <span className="hidden md:block border border-solid border-zinc-200 h-80 self-center"></span>

                        {/* Seção "Saved by" Centralizada */}
                        <div className="flex-1 flex items-center justify-center">
                            <div className="text-center">
                                <h3 className="text-lg font-semibold mb-2 text-gray-800">Saved by:</h3>
                                <div className="flex flex-col">
                                    <p className="text-gray-900">Marcelo Henrique</p>
                                    <p className="text-gray-900">(Admin)</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    {/* Botão "CLOSE" Centralizado e Maior */}
                    <div className="flex justify-center w-full">
                        <button
                            type="button"
                            className="w-full max-w-xs bg-black text-white py-3 px-6 rounded-md font-semibold text-base hover:bg-zinc-800 transition-colors"
                            aria-label="Close form"
                            onClick={() => router.back()}
                        >
                            CLOSE
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default ViewBook;
