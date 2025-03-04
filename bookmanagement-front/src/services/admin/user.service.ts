import { httpClient } from "@/http";
import { AxiosResponse } from "axios";

const resourceURL: string = "/users";

export const useUserService = () => {
    interface UserResponse {
        user?: User;
        error?: string;
    }
    const getUser = async (id: string): Promise<UserResponse> => {
        try {
            const url: string = `${resourceURL}/${id}`;
            const response: AxiosResponse<User> = await httpClient.get(url);
            return { user: response.data };
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

    return {
        getUser,
        permanentDelete,
    };
};
