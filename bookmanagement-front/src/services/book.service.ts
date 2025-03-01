import { httpClient } from "@/http";
import { Book } from "@/models/Book";
import { AxiosResponse } from "axios";

const resourceURL: string = "/books";

export const useBookService = () => {
    const save = async (book: Book): Promise<{ message?: string; error?: string }> => {
        try {
            const response: AxiosResponse<{ message: string }> = await httpClient.post(resourceURL, book);
            return { message: response.data.message };
        } catch (error: any) {
            if (error.response) {
                return { error: error.response.data.error || "An unexpected error occurred." };
            } else {
                return { error: "Network error or server unreachable." };
            }
        }
    };

    interface BookResponse {
        book?: Book;
        error?: string;
    }

    const getBook = async (id: string): Promise<BookResponse> => {
        try {
            const url: string = `${resourceURL}/${id}`;
            const response: AxiosResponse<Book> = await httpClient.get(url);
            return { book: response.data };
        } catch (error: any) {
            if (error.response) {
                return { error: error.response.data.error || "An unexpected error occurred." };
            } else {
                return { error: "Network error or server unreachable." };
            }
        }
    };

    const update = async (id: string, book: Book): Promise<{ message?: string; error?: string }> => {
        try {
            const response: AxiosResponse<{ message: string }> = await httpClient.put(`${resourceURL}/${id}`, book);
            return { message: response.data.message };
        } catch (error: any) {
            if (error.response) {
                return { error: error.response.data.error || "An unexpected error occurred." };
            } else {
                return { error: "Network error or server unreachable." };
            }
        }
    };

    const restoreBook = async (id: string): Promise<{ message?: string; error?: string }> => {
        try {
            const response = await httpClient.patch(`/books/trash/${id}/restore`);
            return { message: response.data.message };
        } catch (error: any) {
            return { error: error.response?.data.error || "Failed to restore book" };
        }
    };

    const softDelete = async (id: string): Promise<{ message?: string; error?: string }> => {
        try {
            const response: AxiosResponse<{ message: string }> = await httpClient.delete(`${resourceURL}/${id}`);
            return { message: response.data.message };
        } catch (error: any) {
            if (error.response) {
                return { error: error.response.data.error || "An unexpected error occurred." };
            } else {
                return { error: "Network error or server unreachable." };
            }
        }
    };

    const permanentDelete = async (id: string): Promise<{ message?: string; error?: string }> => {
        try {
            const response: AxiosResponse<{ message: string }> = await httpClient.delete(`${resourceURL}/trash/${id}`);
            return { message: response.data.message };
        } catch (error: any) {
            if (error.response) {
                return { error: error.response.data.error || "An unexpected error occurred." };
            } else {
                return { error: "Network error or server unreachable." };
            }
        }
    };

    return {
        save,
        getBook,
        update,
        restoreBook,
        softDelete,
        permanentDelete,
    };
};
