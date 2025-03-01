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

interface EditBookParams {
    params: Promise<{ id: string }>;
}

const EditBook = ({ params: paramsPromise }: EditBookParams) => {
    const router = useRouter();
    const [isLoading, setIsLoading] = useState<boolean>(true);

    const handleNavigate = () => {
        router.push("/admin/books");
    };

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
        onSubmit: async (book) => {
            const result = await update(book.id || "", book);
            if (result.message) {
                toast.success(result.message);
                handleNavigate();
            } else if (result.error) {
                toast.error(result.error);
            }
        },
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
                        <h1 className="text-lg sm:text-xl text-[#151619] font-semibold">Add Book</h1>
                    </div>
                    <button onClick={handleNavigate}>
                        <MdOutlineClose
                            size={24}
                            className="p-1 text-zinc-400 border border-zinc-600 border-solid rounded-md"
                        />
                    </button>
                </div>
                <div className="border border-solid border-zinc-300 w-full mb-4"></div>

                {/* Formulário */}
                <form onSubmit={formik.handleSubmit} className="md:grid grid-cols-2">
                    {/* Campo de Id */}
                    <div className="relative flex items-center mb-1 p-2 sm:p-4">
                        <input
                            type="text"
                            id="id"
                            name="id"
                            value={formik.values.id}
                            className="w-full p-2 sm:p-3 border border-gray-700 rounded-xl text-sm sm:text-base cursor-not-allowed opacity-70 bg-gray-200 text-gray-500 border-none shadow-md"
                            disabled
                        />
                        <span className="absolute right-3 text-gray-500 pr-3">🔒</span>
                    </div>
                    {/* Campo de Título */}
                    <div className="mb-1 p-2 sm:p-4">
                        <input
                            type="text"
                            id="title"
                            name="title"
                            value={formik.values.title}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            className="w-full p-2 sm:p-3 border border-gray-700 rounded-xl focus:outline-none focus:ring-2 focus:ring-zinc-500 text-sm sm:text-base"
                            placeholder="Enter book title"
                        />
                        {formik.touched.title && formik.errors.title && (
                            <p className="text-red-500 text-base mt-2">{formik.errors.title}</p>
                        )}
                    </div>
                    {/* Campo de Autor */}
                    <div className="mb-1 p-2 sm:p-4">
                        <input
                            type="text"
                            id="author"
                            name="author"
                            value={formik.values.author}
                            onChange={formik.handleChange}
                            onBlur={formik.handleBlur}
                            className="w-full p-2 sm:p-3 border border-gray-700 rounded-xl focus:outline-none focus:ring-2 focus:ring-zinc-500 text-sm sm:text-base"
                            placeholder="Enter author name"
                        />
                        {formik.touched.author && formik.errors.author && (
                            <p className="text-red-500 text-base mt-2">{formik.errors.author}</p>
                        )}
                    </div>
                    {/* Campo de Disponibilidade */}
                    <div className="relative flex items-center mb-1 p-2 sm:p-4">
                        <input
                            type="text"
                            id="isBorrowed"
                            name="isBorrowed"
                            value={formik.values.isBorrowed ? "Borrowed" : "Available"}
                            className="w-full p-2 sm:p-3 border border-gray-700 rounded-xl text-sm sm:text-base cursor-not-allowed opacity-70 bg-gray-200 text-gray-500 border-none shadow-md"
                            disabled
                        />
                        <span className="absolute right-3 text-gray-500 pr-3">🔒</span>
                    </div>
                    {/* Campo de Criação */}
                    <div className="relative flex items-center mb-1 p-2 sm:p-4">
                        <input
                            type="text"
                            id="created_at"
                            name="created_at"
                            value={formik.values.created_at}
                            className="w-full p-2 sm:p-3 border border-gray-700 rounded-xl text-sm sm:text-base cursor-not-allowed opacity-70 bg-gray-200 text-gray-500 border-none shadow-md"
                            disabled
                        />
                        <span className="absolute right-3 text-gray-500 pr-3">🔒</span>
                    </div>
                    {/* Campo de Atualização */}
                    <div className="relative flex items-center mb-1 p-2 sm:p-4">
                        <input
                            type="text"
                            id="updated_at"
                            name="updated_at"
                            value={formik.values.updated_at}
                            className="w-full p-2 sm:p-3 border border-gray-700 rounded-xl text-sm sm:text-base cursor-not-allowed opacity-70 bg-gray-200 text-gray-500 border-none shadow-md"
                            disabled
                        />
                        <span className="absolute right-3 text-gray-500 pr-3">🔒</span>
                    </div>
                    {/* Botões */}
                    <div className="flex flex-col sm:flex-row gap-2 col-span-2">
                        <button
                            onClick={handleNavigate}
                            type="button"
                            className=" w-full bg-[#D7D7D7] text-black py-2 sm:py-3 rounded-md font-semibold text-sm sm:text-base hover:bg-zinc-400 transition-colors"
                        >
                            CANCEL
                        </button>
                        <button
                            type="submit"
                            disabled={!formik.isValid || formik.isSubmitting}
                            className=" w-full bg-black text-white py-2 sm:py-3 rounded-md font-semibold text-sm sm:text-base hover:bg-zinc-800 transition-colors disabled:bg-gray-400"
                        >
                            UPDATE
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default EditBook;
