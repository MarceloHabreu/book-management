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

    return {
        save,
    };
};
