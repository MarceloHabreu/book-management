"use client";
import Image from "next/image";
import React from "react";
import IconBook from "@/public/imgs/iconBook.png";
import { MdOutlineClose } from "react-icons/md";
import { useRouter } from "next/navigation";
import { useFormik } from "formik";
import { Book } from "@/models/Book";
import * as Yup from "yup";
import { useBookService } from "@/services/admin/book.service";
import { toast } from "react-toastify";

const formSchema: Partial<Book> = {
    title: "",
    author: "",
};

const CreateBook = () => {
    const router = useRouter();

    const handleNavigate = () => {
        router.push("/admin/books");
    };

    const service = useBookService();

    const validationScheme = Yup.object().shape({
        title: Yup.string().trim().required("Title is required").min(4, "Title must be at least 4 characters"),
        author: Yup.string().trim().required("Author is required").min(4, "Author name must be at least 4 characters"),
    });

    const formik = useFormik<Book>({
        initialValues: { ...formSchema } as Book,
        enableReinitialize: true,
        validationSchema: validationScheme,
        onSubmit: async (book) => {
            const result = await service.save(book);
            if (result.message) {
                toast.success(result.message);
                handleNavigate();
            } else if (result.error) {
                toast.error(result.error);
            }
        },
    });

    return (
        <div className="flex justify-center items-center min-h-screen bg-zinc-100">
            {/* Contêiner Principal */}
            <div className="bg-white p-6 md:p-8 rounded-2xl shadow-md w-full max-w-lg sm:w-11/12 md:w-1/2">
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
                <form onSubmit={formik.handleSubmit}>
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

                    {/* Botões */}
                    <div className="flex flex-col sm:flex-row gap-2">
                        <button
                            onClick={handleNavigate}
                            type="button"
                            className="w-full bg-[#D7D7D7] text-black py-2 sm:py-3 rounded-md font-semibold text-sm sm:text-base hover:bg-zinc-400 transition-colors"
                        >
                            CANCEL
                        </button>
                        <button
                            type="submit"
                            disabled={!formik.isValid || formik.isSubmitting}
                            className="w-full bg-black text-white py-2 sm:py-3 rounded-md font-semibold text-sm sm:text-base hover:bg-zinc-800 transition-colors disabled:bg-gray-400"
                        >
                            ADD
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default CreateBook;
