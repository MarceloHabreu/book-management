"use client";
import Image from "next/image";
import React, { useEffect, useState } from "react";
import IconBook from "@/public/imgs/iconBook.png";
import { MdOutlineClose } from "react-icons/md";
import { useRouter } from "next/navigation";
import { useFormik } from "formik";

import { toast } from "react-toastify";
import { useUserService } from "@/services/admin/user.service";

const formSchema: Partial<User> = {
    name: "",
    email: "",
};

interface ViewUserParams {
    params: Promise<{ id: string }>;
}

const ViewUser = ({ params: paramsPromise }: ViewUserParams) => {
    const router = useRouter();
    const [isLoading, setIsLoading] = useState<boolean>(true);

    const params = React.use(paramsPromise);

    const { id } = params;
    const { getUser } = useUserService();

    const formik = useFormik<User>({
        initialValues: { ...formSchema } as User,
        enableReinitialize: true,
        onSubmit: () => {},
    });

    useEffect(() => {
        if (id) {
            const fetchUser = async () => {
                setIsLoading(true);
                const result = await getUser(id);
                console.log(id);

                if (result.user) {
                    formik.setValues(result.user);
                } else if (result.error) {
                    toast.error(result.error);
                }
                setIsLoading(false);
            };
            fetchUser();
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
                        <h1 className="text-lg sm:text-xl text-[#151619] font-semibold">View User</h1>
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
                        {/* Seção de Informações do User */}
                        <div className="flex-1">
                            <div className="border-b border-zinc-600 mb-5">
                                <span className="text-gray-700 font-semibold">User ID:</span>{" "}
                                <span className="text-gray-900">{formik.values.id}</span>
                            </div>
                            <div className="border-b border-zinc-600 mb-5">
                                <span className="text-gray-700 font-semibold">Name:</span>{" "}
                                <span className="text-gray-900">{formik.values.name}</span>
                            </div>
                            <div className="border-b border-zinc-600 mb-5">
                                <span className="text-gray-700 font-semibold">Email:</span>{" "}
                                <span className="text-gray-900">{formik.values.email}</span>
                            </div>
                        </div>

                        {/* Separador Vertical */}
                        <span className="hidden md:block border border-solid border-zinc-200 h-80 self-center"></span>

                        {/* Seção "Saved by" Centralizada */}
                        <div className="flex-1 flex items-center justify-center">
                            <div className="text-center">
                                <h3 className="text-lg font-semibold mb-2 text-gray-800">Logged in:</h3>
                                <div className="flex flex-col">
                                    <p className="text-gray-900">{formik.values.created_at}</p>
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

export default ViewUser;
